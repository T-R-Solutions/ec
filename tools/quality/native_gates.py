"""Políticas Ec sobre comandos nativos da Toolbox; sem instalação de ferramentas.

automate.sh continua sendo o coordenador. Este módulo executa somente um gate,
preserva os códigos nativos e separa diagnóstico operacional de achados.
"""

import datetime as dt
import hashlib
import json
import os
import re
import shutil
import signal
import stat
import subprocess
import sys
import time
import traceback
from pathlib import Path, PurePosixPath

if __package__ in (None, ""):
    sys.path.insert(0, str(Path(__file__).resolve().parents[2]))
from tools.quality.automation_config import (
    CODE,
    codeql_projects,
    load_config,
    select_files,
    source_files,
    verify_catalogs,
)
from tools.quality.secret_history import history_identity, history_options
from tools.quality.syft_inventory import verify_lock_inventory

AUTOMATION = load_config()
LOCKS = tuple(c["lockfile"] for c in AUTOMATION["components"].values())
SCANNERS = {t["name"]: t["tool"] for t in AUTOMATION["tasks"] if t["executor"] == "toolbox"}
CHECKS = tuple(SCANNERS)


def policy(tool):
    return AUTOMATION["policies"][tool]


class Rejected(Exception):
    """Falha de contrato ou de política, com mensagem local sem saída da ferramenta."""


class Blocked(Rejected):
    """Pré-requisito operacional ausente."""


class ExpiredDatabase(Rejected):
    """Dados identificados cuja idade ultrapassou a política."""


class MissingDatabase(Blocked):
    """Snapshot ainda não preparado."""


def require(condition, message):
    if not condition:
        raise Rejected(message)


def read_json(path):
    def unique(pairs):
        value = {}
        for key, item in pairs:
            require(key not in value, "propriedade JSON duplicada")
            value[key] = item
        return value

    require(path.is_file() and not path.is_symlink(), "relatório regular ausente")
    require(
        path.stat().st_size <= AUTOMATION["runtime"]["native_log_bytes"], "relatório excede 128 MiB"
    )
    return json.loads(
        path.read_text(),
        object_pairs_hook=unique,
        parse_constant=lambda _: (_ for _ in ()).throw(Rejected("JSON não finito")),
    )


def write_json(path, value):
    temporary = path.with_suffix(path.suffix + ".tmp")
    temporary.write_text(json.dumps(value, indent=2, ensure_ascii=False) + "\n")
    temporary.chmod(0o600)
    temporary.replace(path)


def regular(root, relative):
    path = PurePosixPath(relative)
    require(
        relative and not path.is_absolute() and ".." not in path.parts,
        "alvo deve ser relativo e interno",
    )
    current = root
    for part in path.parts:
        current = current / part
        require(not current.is_symlink(), "symlink não permitido nas entradas")
    require(current.is_file(), "alvo obrigatório ausente ou não regular")
    return current


def identity(path):
    return {
        "sha256": hashlib.sha256(path.read_bytes()).hexdigest(),
        "mode": stat.S_IMODE(path.stat().st_mode),
    }


def inventory(root, scope="all"):
    names = source_files(AUTOMATION, root, scope)
    require(names, "inventário obrigatório vazio")
    return {name: identity(regular(root, name)) for name in names}


def relative(path):
    require(isinstance(path, str), "caminho ausente do relatório")
    path = path.removeprefix("/project/").removeprefix("./")
    require(
        not path.startswith("/") and ".." not in PurePosixPath(path).parts,
        "relatório refere fonte fora do projeto",
    )
    return path


def fresh(timestamp, maximum=None):
    if maximum is None:
        maximum = AUTOMATION["databases"]["scanners"]["maximum_age_seconds"]
    require(isinstance(timestamp, str), "timestamp da base ausente")
    date = dt.datetime.fromisoformat(timestamp.replace("Z", "+00:00"))
    require(date.tzinfo is not None, "timestamp da base sem timezone")
    age = (dt.datetime.now(dt.UTC) - date).total_seconds()
    require(age >= 0, "base futura")
    if age > maximum:
        raise ExpiredDatabase("base expirada")
    return date


def database_metadata(value, tool):
    """Proveniência pública dos bytes montados; não usa a data de preparação."""
    data = value.get("database_metadata")
    require(
        isinstance(data, dict)
        and type(data.get("schema")) is int
        and data["schema"] == 1
        and data.get("snapshot") == value["database"]
        and data.get("timestamp_semantics") == "oldest_required_source",
        "proveniência da base ausente ou divergente do diagnóstico",
    )
    sources = data.get("sources")
    require(
        isinstance(sources, list)
        and len(sources) == 2
        and all(isinstance(source, dict) for source in sources),
        "fontes obrigatórias da base ausentes",
    )
    if tool == "osv-scanner":
        expected = {
            "https://osv-vulnerabilities.storage.googleapis.com/" + ecosystem + "/all.zip"
            for ecosystem in ("crates.io", "npm")
        }
        semantics = "upstream_object_generation_created"
        extra = ("generation",)
    else:
        expected = {
            "https://github.com/aquasecurity/trivy-db",
            "https://github.com/aquasecurity/trivy-java-db",
        }
        semantics = "native_database_updated_at"
        extra = ("metadata_file",)
    require(
        {source.get("url") for source in sources} == expected,
        "inventário de fontes da base incompleto ou inesperado",
    )
    dates = []
    for source in sources:
        require(
            source.get("timestamp_semantics") == semantics
            and re.fullmatch(r"[0-9a-f]{64}", source.get("sha256", "")) is not None,
            "semântica ou hash upstream da base inválido",
        )
        if tool == "osv-scanner":
            require(
                re.fullmatch(r"[0-9]+", source.get("generation", "")) is not None,
                "geração upstream OSV ausente",
            )
        else:
            filename = (
                "java-db" if source["url"].endswith("trivy-java-db") else "db"
            ) + "/metadata.json"
            require(source.get("metadata_file") == filename, "metadado nativo Trivy divergente")
        # Conferir toda a proveniência antes de permitir renovação por idade.
        dates.append(fresh(source.get("data_timestamp"), maximum=float("inf")))
    require(
        fresh(data.get("data_timestamp"), maximum=float("inf")) == min(dates),
        "timestamp agregado não representa a fonte obrigatória mais antiga",
    )
    fresh(data["data_timestamp"])
    # Persistir somente campos públicos conhecidos, sem propriedades adicionais.
    return {
        **{
            key: data[key]
            for key in ("schema", "snapshot", "data_timestamp", "timestamp_semantics")
        },
        "sources": [
            {
                key: source[key]
                for key in ("url", "sha256", "data_timestamp", "timestamp_semantics", *extra)
            }
            for source in sources
        ],
    }


def trivy_checks(value):
    """Exigir o bundle externo e a montagem observada pelo executor central."""
    checks = value.get("checks")
    require(
        isinstance(checks, dict)
        and re.fullmatch(r"[0-9a-f]{64}", checks.get("checks", "")) is not None
        and checks.get("image") == value["image"]
        and checks.get("version") == value["version"]
        and checks.get("origin") == "ghcr.io/aquasecurity/trivy-checks:2",
        "Checks Bundle Trivy ausente ou incompatível com a imagem",
    )
    native = checks.get("native", {})
    require(
        isinstance(native, dict)
        and native.get("MajorVersion") == 2
        and native.get("CustomBuild", False) is False
        and re.fullmatch(r"sha256:[0-9a-f]{64}", native.get("Digest", "")) is not None,
        "identidade nativa do Checks Bundle Trivy inválida",
    )
    stamp = dt.datetime.fromisoformat(native["DownloadedAt"].replace("Z", "+00:00"))
    require(
        stamp.tzinfo is not None and stamp <= dt.datetime.now(dt.UTC),
        "timestamp do Checks Bundle Trivy inválido",
    )
    runtime = value.get("checks_runtime", {})
    mount = runtime.get("mount", {})
    require(
        runtime.get("network") == "none"
        and mount.get("Destination") == "/cache/policy"
        and mount.get("RW") is False
        and mount.get("Type") == "bind",
        "Checks Bundle Trivy sem mount ro e rede none observados",
    )


def diagnostic(value, tool, code, *, maintenance=False):
    require(value.get("tool") == tool, "diagnóstico de outra ferramenta")
    outcome = value.get("outcome")
    require(outcome in ("native", "infrastructure", "timeout", "cancelled"), "outcome desconhecido")
    require(value.get("cleanup") != "failed", "limpeza incompleta do executor Toolbox")
    if outcome == "infrastructure":
        raise Blocked("infraestrutura Toolbox indisponível; consultar diagnóstico")
    require(outcome == "native", "executor interrompido por timeout ou cancelamento")
    require(
        type(value.get("native_exit_code")) is int and value["native_exit_code"] == code,
        "exit nativo divergente do retorno",
    )
    require(
        value.get("cleanup") == "removed" and value.get("oom_killed") is False,
        "limpeza incompleta ou processo encerrado por falta de memória",
    )
    require(
        re.fullmatch(r"sha256:[0-9a-f]{64}", value.get("image", "")) is not None
        and isinstance(value.get("version"), str)
        and value["version"],
        "imagem/versão ausente do diagnóstico",
    )
    if maintenance:
        require(
            tool in ("trivy", "grype")
            and code == 0
            and re.fullmatch(r"[0-9a-f]{64}", value.get("published_database", "")) is not None,
            "renovação não publicou uma base identificada",
        )
        return None
    if tool in ("trivy", "grype", "osv-scanner"):
        if not value.get("database"):
            raise MissingDatabase("base central ausente; preparo necessário")
        require(
            re.fullmatch(r"[0-9a-f]{64}", value.get("database", "")) is not None,
            "base central não identificada",
        )
    if tool in ("trivy", "osv-scanner"):
        return database_metadata(value, tool)
    return None


def basic_report(tool, data, code, targets):
    """Oráculos nativos dos checks que não usam o parser de segurança existente."""
    count = 0
    require(isinstance(data, list if tool == "ruff" else dict), "formato de relatório inválido")
    if tool == "ruff":
        require(code in (0, 1) and isinstance(data, list), "relatório/exit Ruff inválido")
        for item in data:
            require(
                item.get("code")
                and relative(item.get("filename")) in targets
                and isinstance(item.get("location"), dict),
                "achado Ruff incompleto",
            )
        count = len(data)
    elif tool == "shellcheck":
        require(
            code in (0, 1) and isinstance(data.get("comments"), list),
            "relatório/exit ShellCheck inválido",
        )
        for item in data["comments"]:
            require(
                relative(item.get("file")) in targets
                and type(item.get("code")) is int
                and item.get("level") in ("error", "warning", "info", "style"),
                "achado ShellCheck incompleto",
            )
        count = len(data["comments"])
    elif tool == "bandit":
        require(
            code in (0, 1) and data.get("errors") == [] and isinstance(data.get("results"), list),
            "Bandit parcial ou inválido",
        )
        metrics = data.get("metrics", {})
        require(
            sorted(relative(p) for p in metrics if p != "_totals") == sorted(targets),
            "Bandit omitiu fontes",
        )
        require(
            all(
                m.get("nosec", 0) == 0 and m.get("skipped_tests", 0) == 0 for m in metrics.values()
            ),
            "Bandit suprimiu checks",
        )
        for item in data["results"]:
            require(
                relative(item.get("filename")) in targets
                and item.get("test_id")
                and item.get("issue_severity") in ("LOW", "MEDIUM", "HIGH"),
                "achado Bandit incompleto",
            )
        count = len(data["results"])
    elif tool in ("grype", "syft"):
        require(
            code == 0 and isinstance(data.get("artifacts"), list) and data["artifacts"],
            "inventário de pacotes ausente ou produtor falhou",
        )
        require(
            all(
                isinstance(item.get("name"), str)
                and item["name"]
                and isinstance(item.get("version"), str)
                and item.get("type")
                for item in data["artifacts"]
            ),
            "pacote sem identidade no inventário",
        )

        def location(path):
            # Syft/Grype podem registrar paths a partir da raiz selecionada.
            if path.startswith("/project/"):
                return relative(path)
            path = relative(path.lstrip("/"))
            return path if path.startswith("application/") else "application/" + path

        locations = {
            location(loc["path"])
            for item in data["artifacts"]
            for loc in item.get("locations", [])
        }
        require(set(LOCKS) <= locations, "inventário omitiu lockfiles obrigatórios")
        if tool == "syft":
            return {
                "state": "measured",
                "finding_count": None,
                "artifact_count": len(data["artifacts"]),
                "measurement": True,
            }
        require(
            isinstance(data.get("matches"), list)
            and isinstance(data.get("ignoredMatches", []), list),
            "Grype inválido",
        )
        require(not data["descriptor"]["db"].get("error"), "Grype registrou erro de base")
        for match in [*data["matches"], *data.get("ignoredMatches", [])]:
            require(match.get("vulnerability", {}).get("id"), "achado Grype incompleto")
        status = data["descriptor"]["db"].get("status", data["descriptor"]["db"])
        require(
            status.get("valid") is True and not status.get("error"),
            "base Grype inválida ou sem confirmação de integridade",
        )
        fresh(status["built"])
        count = len(data["matches"]) + len(data.get("ignoredMatches", []))
        return dict(
            state="findings" if count else "passed",
            finding_count=count,
            native_finding_count=len(data["matches"]),
            native_suppressed_count=len(data.get("ignoredMatches", [])),
        )
    elif tool == "playwright":
        require(code in (0, 1) and data.get("errors") == [], "Playwright parcial ou inválido")
        stats = data["stats"]
        require(
            all(
                type(stats.get(k)) is int and stats[k] >= 0
                for k in ("expected", "unexpected", "skipped", "flaky")
            ),
            "contadores Playwright inválidos",
        )
        require(
            stats["expected"] + stats["unexpected"]
            == AUTOMATION["browser"]["playwright"]["expected_tests"]
            and stats["skipped"] == 0
            and stats["flaky"] == 0,
            "testes Playwright omitidos, flaky ou ignorados",
        )

        def specs(suites):
            return [s for suite in suites for s in suite.get("specs", [])] + [
                s for suite in suites for s in specs(suite.get("suites", []))
            ]

        cases = specs(data["suites"])
        tests = [t for s in cases for t in s["tests"]]
        require(
            len(cases)
            == len(tests)
            == len({s["title"] for s in cases})
            == AUTOMATION["browser"]["playwright"]["expected_tests"],
            "inventário Playwright incompleto ou duplicado",
        )
        for test in tests:
            require(
                test["expectedStatus"] == "passed"
                and test["status"] in ("expected", "unexpected")
                and len(test["results"]) == 1
                and test["results"][0]["status"] in ("passed", "failed", "timedOut"),
                "resultado Playwright não aprovado",
            )
        count = stats["unexpected"]
    else:
        raise Rejected("parser nativo desconhecido")
    if tool in ("ruff", "shellcheck", "bandit", "playwright"):
        require((code == 1) == (count > 0), "exit e achados inconsistentes")
    return {"state": "findings" if count else "passed", "finding_count": count}


def reviewed_findings(findings, sources, reviews):
    """A triagem não muda o relatório nativo nem aceita hashes divergentes."""
    reviewed, pending = [], []
    for finding in findings:
        path = finding["source"]
        review = reviews.get(path, {})
        digest = sources.get(path, {}).get("sha256")
        valid_digest = isinstance(digest, str) and re.fullmatch(r"[0-9a-f]{64}", digest) is not None
        match = [
            rule
            for rule in review.get("rules", [])
            if rule["id"] == finding["rule"]
            and finding["line"] in rule["lines"]
            and rule["reason"].strip()
            and valid_digest
            and digest == review.get("sha256")
        ]
        entry = dict(finding)
        if len(match) == 1:
            reviewed.append(dict(entry, reason=match[0]["reason"], sha256=digest))
        else:
            pending.append(entry)
    return dict(
        state="findings" if pending else "passed",
        finding_count=len(pending),
        native_finding_count=len(findings),
        reviewed_count=len(reviewed),
        reviewed=reviewed,
        pending=pending,
    )


def bandit_review(report, sources, reviews):
    return reviewed_findings(
        [
            dict(source=relative(item["filename"]), rule=item["test_id"], line=item["line_number"])
            for item in report["results"]
        ],
        sources,
        reviews,
    )


def gitleaks_review(report, sources, reviews, *, history=False):
    """Revisões da árvore atual jamais aprovam credenciais de outro commit."""
    return reviewed_findings(
        [
            dict(source=relative(item["File"]), rule=item["RuleID"], line=item["StartLine"])
            for item in report
        ],
        sources,
        {} if history else reviews,
    )


class Gate:
    def __init__(self, check, root, task):
        self.check, self.root, self.task = check, root, task
        self.snapshot = task / "sources"
        self.steps = []
        self.process = None
        self.cancelled = False
        self.cancel_signal = signal.SIGTERM
        self.deadline = (
            time.monotonic()
            + int(os.environ.get("EC_TASK_TIMEOUT", AUTOMATION["runtime"]["task_timeout"]))
            - AUTOMATION["runtime"]["cleanup_timeout"]
        )

    def receive(self, signum, _frame):
        self.cancelled = True
        self.cancel_signal = signum
        if self.process and self.process.poll() is None:
            self.process.send_signal(signum)

    def prepare(self):
        self.before = inventory(self.root)
        self.snapshot.mkdir()
        for name, observed in self.before.items():
            source = regular(self.root, name)
            dest = self.snapshot / name
            dest.parent.mkdir(parents=True, exist_ok=True)
            shutil.copy2(source, dest)
            require(identity(dest) == observed, "fonte mudou durante o snapshot")
        write_json(self.task / "sources.json", self.before)
        # Arquivos vazios explícitos impedem descoberta de ignores pessoais.
        (self.snapshot / ".gitleaksignore").write_text("")
        (self.snapshot / ".semgrepignore").write_text("")
        (self.snapshot / ".trivyignore").write_text("")
        (self.snapshot / ".bandit").write_text("[bandit]\n")

    def targets(self, kind):
        selected = select_files(AUTOMATION, self.before, kind)
        if kind == "sast":
            require(
                all(
                    any(n.startswith(base + "/") for n in selected)
                    for base in (c["root"] for c in AUTOMATION["components"].values())
                ),
                "SAST requer os três componentes",
            )
        require(
            selected and all(n in self.before for n in selected),
            "alvos obrigatórios ausentes ou vazios",
        )
        write_json(self.task / "targets.json", selected)
        return selected

    def run(
        self, tool, argv, *, project=None, options=(), timeout=None, maintenance=False, network=None
    ):
        require(not self.cancelled, "execução cancelada")
        require(
            not maintenance
            or (
                (
                    tool == "trivy"
                    and argv
                    in (
                        ["filesystem", "--download-db-only"],
                        ["filesystem", "--download-java-db-only"],
                    )
                )
                or (tool == "grype" and argv == ["db", "update"])
            ),
            "manutenção fora do preparo declarado de bases",
        )
        require(
            AUTOMATION["tools"].get(tool, {}).get("executor") == "toolbox",
            "ferramenta não pertence ao executor Docker",
        )
        timeout = min(
            timeout
            or AUTOMATION["tools"][tool].get("timeout", AUTOMATION["runtime"]["native_timeout"]),
            int(self.deadline - time.monotonic()),
        )
        require(timeout > 0, "prazo total do gate esgotado")
        network = network or ("bridge" if maintenance else "none")
        require(
            network == ("bridge" if maintenance else "none")
            or (
                tool == "playwright"
                and self.check == "journey"
                and network == AUTOMATION["browser"]["journey"]["network"]
            ),
            "rede fora do contrato da ferramenta",
        )
        step = self.task / f"{len(self.steps):02d}-{tool}"
        step.mkdir(mode=0o700)
        results = step / "results"
        results.mkdir(mode=0o700)
        command = [
            "bash",
            str(CODE / "scripts/toolbox.sh"),
            "exec",
            "--tool",
            tool,
            "--project-root",
            str(project or self.snapshot),
            "--results",
            str(results),
            "--network",
            network,
            "--timeout",
            str(timeout),
            *(["--maintenance"] if maintenance else []),
            "--diagnostic-file",
            str(step / "diagnostic.json"),
            *options,
            "--",
            *argv,
        ]
        # Não transportar ambiente inteiro nem registrar argv/valores no resumo.
        env = {
            k: os.environ[k]
            for k in (
                "PATH",
                "HOME",
                "LANG",
                "LC_ALL",
                "TOOLBOX_BIN",
                "TOOLBOX_STATE",
                "TOOLBOX_SELINUX_LABEL",
                *AUTOMATION["environment"]["forward"],
            )
            if k in os.environ
        }
        env.update(NO_COLOR="1", PYTHONDONTWRITEBYTECODE="1")
        with (step / "stdout.log").open("xb") as out, (step / "stderr.log").open("xb") as err:
            self.process = subprocess.Popen(
                command,
                env=env,
                stdin=subprocess.DEVNULL,
                stdout=out,
                stderr=err,
                start_new_session=True,
            )
            if self.cancelled:
                self.process.send_signal(self.cancel_signal)
            interruption = None
            try:
                deadline = time.monotonic() + timeout + AUTOMATION["runtime"]["cleanup_timeout"]
                while self.process.poll() is None:
                    if (
                        time.monotonic() >= deadline
                        or sum(p.stat().st_size for p in (step / "stdout.log", step / "stderr.log"))
                        > AUTOMATION["runtime"]["native_log_bytes"]
                    ):
                        raise subprocess.TimeoutExpired(command, timeout)
                    time.sleep(0.05)
                code = self.process.returncode
            except subprocess.TimeoutExpired:
                interruption = (
                    "executor excedeu prazo ou limite de logs; consultar diagnóstico de limpeza"
                )
                self.process.terminate()
                try:
                    self.process.wait(timeout=AUTOMATION["runtime"]["cleanup_timeout"])
                except subprocess.TimeoutExpired:
                    self.process.kill()
                    self.process.wait()
                code = self.process.returncode
            finally:
                self.process = None
        # Relatórios comuns sobrevivem mesmo quando a validação do gate falha.
        # Scanners de segredos só publicam após redação específica.
        if tool not in ("trivy", "gitleaks"):
            for filename in ("native.json", "native.sarif"):
                report = results / filename
                if report.is_file() and not report.is_symlink():
                    shutil.copyfile(report, step / (filename + ".log"))
        value = read_json(step / "diagnostic.json") if (step / "diagnostic.json").exists() else {}
        record = {
            "step": step.name,
            "tool": tool,
            "returncode": code,
            "maintenance": maintenance,
            "outcome": value.get("outcome"),
            "native_exit_code": value.get("native_exit_code"),
            "image": value.get("image"),
            "version": value.get("version"),
            "database": value.get("database"),
            "cleanup": value.get("cleanup"),
            "published_database": value.get("published_database"),
            "checks": value.get("checks", {}).get("checks")
            if isinstance(value.get("checks"), dict)
            else None,
        }
        self.steps.append(record)
        write_json(step / "exit.json", record)
        write_json(self.task / "native-executions.json", self.steps)
        require(interruption is None, interruption)
        require(
            bool(value),
            "diagnóstico ausente do executor; origem não determinada, consultar streams",
        )
        provenance = diagnostic(value, tool, code, maintenance=maintenance)
        if provenance is not None:
            write_json(
                self.task / (tool + "-database.json"),
                {"tool": tool, "image": value["image"], "version": value["version"], **provenance},
            )
        if tool == "trivy" and argv[0] != "version" and not maintenance:
            trivy_checks(value)
        require(not self.cancelled, "execução cancelada após término nativo")
        # Uma sequência não mistura versões/bases após troca central concorrente.
        for previous in self.steps[:-1]:
            if previous["tool"] == tool:
                require(
                    all(previous[k] == record[k] for k in ("image", "version")),
                    "identidade central mudou entre etapas; repetir o gate",
                )
                if (
                    not maintenance
                    and not previous.get("maintenance")
                    and not previous.get("superseded")
                ):
                    require(
                        previous["database"] == record["database"],
                        "identidade central mudou entre etapas; repetir o gate",
                    )
                if (
                    not maintenance
                    and not previous.get("maintenance")
                    and previous["checks"] is not None
                    and record["checks"] is not None
                ):
                    require(
                        previous["checks"] == record["checks"],
                        "Checks Bundle central mudou entre etapas; repetir o gate",
                    )
        return step, code

    def parse(self, kind, report, code, targets=()):
        inputs = self.task / "expected.json"
        write_json(inputs, {n: self.before.get(n, {}) for n in targets})
        result = subprocess.run(  # Parser versionado; nenhum input como código.
            [
                "bash",
                str(CODE / "scripts/stacks/ec/security/check-report.sh"),
                kind,
                str(report),
                str(code),
                str(inputs) if targets else "/dev/null",
                "/project",
            ],
            capture_output=True,
            check=False,
        )
        require(result.returncode == 0, "relatório de segurança inválido, parcial ou inconsistente")
        return json.loads(result.stdout)

    def secrets(self, project=None, history=None):
        argv = [
            "git" if history else "dir",
            "--config",
            "/project/" + policy("gitleaks"),
            "--ignore-gitleaks-allow",
            "--gitleaks-ignore-path",
            "/project/.gitleaksignore",
            "--max-decode-depth",
            str(AUTOMATION["tools"]["gitleaks"]["decode_depth"]),
            "--max-target-megabytes",
            "0",
            "--redact=100",
            "--no-banner",
            "--no-color",
            "--exit-code",
            "10",
            "--report-format",
            "json",
            "--report-path",
            "/out/native.json",
        ]
        if history:
            argv += ["--log-opts=--all --reflog --full-history --no-ext-diff --no-textconv -m"]
        argv += ["/project"]
        step, code = self.run(
            "gitleaks", argv, project=project, options=history_options(history) if history else []
        )
        report = step / "results/native.json"
        data = read_json(report)
        require(
            isinstance(data, list) and all(isinstance(item, dict) for item in data),
            "relatório Gitleaks inválido",
        )
        # Além da redação nativa, persistir somente identificação/localização.
        write_json(
            report,
            [
                {
                    k: item[k]
                    for k in (
                        "RuleID",
                        "File",
                        "Commit",
                        "Fingerprint",
                        "StartLine",
                        "EndLine",
                        "StartColumn",
                        "EndColumn",
                    )
                    if k in item
                }
                for item in data
            ],
        )
        shutil.copyfile(report, step / "native.json.log")
        return step, code

    def secrets_complete(self):
        self.targets("all")
        # Exceções para indicadores públicos dependem dos bytes importados.
        write_json(
            self.task / "rule-catalogs.json", verify_catalogs(AUTOMATION, self.snapshot, "yara-x")
        )
        step, code = self.secrets()
        tree = self.parse("secrets", step / "results/native.json", code)
        review = gitleaks_review(
            read_json(step / "results/native.json"),
            self.before,
            AUTOMATION["tools"]["gitleaks"].get("triage", {}),
        )
        write_json(self.task / "gitleaks-review.json", review)
        try:
            before = history_identity(self.root, AUTOMATION["runtime"]["probe_timeout"])
        except ValueError as error:
            raise Blocked(str(error)) from error
        write_json(self.task / "gitleaks-history.json", before)
        step, code = self.secrets(history=before)
        history = self.parse("secrets", step / "results/native.json", code)
        historical_review = gitleaks_review(
            read_json(step / "results/native.json"),
            self.before,
            AUTOMATION["tools"]["gitleaks"].get("triage", {}),
            history=True,
        )
        require(
            historical_review["finding_count"] == history["finding_count"],
            "revisão da árvore não pode aprovar o histórico",
        )
        require(
            history_identity(self.root, AUTOMATION["runtime"]["probe_timeout"]) == before,
            "histórico Git mudou durante análise",
        )
        count = review["finding_count"] + history["finding_count"]
        return dict(
            state="findings" if count else "passed",
            finding_count=count,
            native_finding_count=tree["finding_count"] + history["finding_count"],
            reviewed_count=review["reviewed_count"],
            tree_finding_count=tree["finding_count"],
            history_finding_count=history["finding_count"],
        )

    def execute(self):
        check = self.check
        if check == "codeql":
            return self.codeql()
        if check in ("grype", "syft"):
            return self.grype()
        if check == "secrets-rules":
            return self.secret_rules()
        if check == "yara-x":
            return self.yara()
        if check in ("sast", "sast-rules"):
            write_json(
                self.task / "rule-catalogs.json",
                verify_catalogs(AUTOMATION, self.snapshot, "opengrep"),
            )
        tool = SCANNERS[check]
        if check in ("ruff", "shellcheck", "bandit"):
            targets = self.targets("shell" if check == "shellcheck" else "python")
        elif check == "sast":
            targets = self.targets("sast")
        elif check == "dependencies":
            targets = self.targets("dependencies")
        else:
            targets = self.targets("all")
        native_targets = ["/project/" + n for n in targets]
        native_project = None
        if check == "ruff":
            argv = [
                "check",
                "--no-fix",
                "--ignore-noqa",
                "--no-respect-gitignore",
                "--output-format",
                "json",
                "--config",
                "/project/" + policy("ruff"),
                *native_targets,
            ]
        elif check == "shellcheck":
            argv = [
                "--format=json1",
                "--check-sourced",
                "--extended-analysis=true",
                "--severity=style",
                "--rcfile=/project/" + policy("shellcheck"),
                "--",
                *native_targets,
            ]
        elif check == "bandit":
            argv = [
                "--ignore-nosec",
                "--ini",
                "/project/.bandit",
                "-c",
                "/project/" + policy("bandit"),
                "-f",
                "json",
                "-o",
                "/out/native.json",
                *native_targets,
            ]
        elif check == "secrets":
            return self.secrets_complete()
        elif check in ("sast", "sast-rules"):
            argv = [
                "scan",
                "--json",
                "--strict",
                "--disable-version-check",
                "--no-rewrite-rule-ids",
            ]
            if check == "sast-rules":
                self.opengrep_qualification()
                # A CLI aceita um config por teste. Reproduzir os bytes locais
                # sem acrescentar catálogos que não têm fixtures Ec associadas.
                native_project = self.task / "opengrep-local-tests"
                local_rules = native_project / policy("opengrep")
                local_rules.mkdir(parents=True)
                for source in (self.snapshot / policy("opengrep")).iterdir():
                    if source.is_file():
                        shutil.copy2(source, local_rules / source.name)
                argv += ["--config", "/project/" + policy("opengrep")]
                argv += ["--test", "/project/" + policy("opengrep")]
            else:
                argv += ["--config", "/project/" + policy("opengrep")]
                argv += [
                    "--error",
                    "--disable-nosem",
                    "--no-git-ignore",
                    "--max-target-bytes",
                    "0",
                    "--taint-intrafile",
                    "--timeout",
                    "0",
                    "--timeout-threshold",
                    "0",
                    *native_targets,
                ]
        elif check == "dependencies":
            argv = [
                "scan",
                "source",
                "--offline",
                "--all-packages",
                "--format",
                "json",
                "--config",
                "/project/" + policy("osv-scanner"),
                "--output-file",
                "/out/native.json",
            ]
            for name in native_targets:
                argv += ["--lockfile", name]
        elif check == "trivy":
            # Rejeitar comentários de supressão: não há flag para ignorá-los no modo misconfig.
            for name in targets:
                path = PurePosixPath(name)
                if path.suffix not in (
                    ".tf",
                    ".tfvars",
                    ".yaml",
                    ".yml",
                    ".json",
                ) and not path.name.startswith("Dockerfile"):
                    continue
                require(
                    not re.search(
                        rb"(?i)\b(?:trivy|avd|tfsec):ignore", (self.snapshot / name).read_bytes()
                    ),
                    "supressão inline Trivy exige revisão explícita",
                )
            argv = [
                "filesystem",
                "--quiet",
                "--config",
                "/project/" + policy("trivy"),
                "--ignorefile",
                "/project/.trivyignore",
                "--format",
                "json",
                "--output",
                "/out/native.json",
                "--exit-code",
                "1",
                "--list-all-pkgs",
                "--include-dev-deps",
                "--include-non-failures",
                "--skip-db-update",
                "--skip-java-db-update",
                "--skip-check-update",
                "--offline-scan",
                "/project",
            ]
            self.trivy_rules(argv)
        elif check == "playwright":
            self.copy_build()
            argv = ["test", "--config=/project/" + policy("playwright")]
        else:
            raise Rejected("check desconhecido")
        options = (
            ["--browser", AUTOMATION["browser"]["playwright"]["browser"]]
            if check == "playwright"
            else []
        )
        step, code = self.run(tool, argv, project=native_project, options=options)
        report = step / "results/native.json"
        if check in ("ruff", "shellcheck", "sast", "sast-rules", "grype", "syft"):
            report = step / "stdout.log"
        if check in ("sast", "sast-rules", "dependencies", "trivy"):
            if check == "trivy":
                self.redact_trivy(report)
            result = self.parse(check, report, code, targets)
            if check == "trivy":
                result.update(self.trivy_licenses())
                return result
            return result
        data = read_json(report)
        result = basic_report(tool, data, code, targets)
        if tool == "bandit":
            review = bandit_review(
                data, self.before, AUTOMATION["tools"]["bandit"].get("triage", {})
            )
            write_json(self.task / "bandit-review.json", review)
            result = {
                key: value for key, value in review.items() if key not in ("reviewed", "pending")
            }
        return result

    def opengrep_qualification(self):
        # --validate baixa metachecks remotos na versão instalada. O scan estrito
        # exercita schema/padrões em cada linguagem do catálogo, sem esse download.
        folder = self.task / "opengrep-qualification"
        folder.mkdir()
        shutil.copytree(self.snapshot / policy("opengrep"), folder / policy("opengrep"))
        samples = {
            "marker.rs": "fn main() {}\n",
            "marker.py": 'verification_marker = "public"\n',
            "marker.js": 'const verification_marker = "public";\n',
            "marker.ts": 'const verification_marker: string = "public";\n',
            "marker.yaml": "verification_marker: public\n",
            "marker.tf": 'locals { verification_marker = "public" }\n',
        }
        for name, content in samples.items():
            (folder / name).write_text(content)
        paths = ["/project/" + name for name in samples]
        step, code = self.run(
            "opengrep",
            [
                "scan",
                "--json",
                "--strict",
                "--disable-version-check",
                "--no-rewrite-rule-ids",
                "--no-git-ignore",
                "--disable-nosem",
                "--max-target-bytes",
                "0",
                "--timeout",
                "0",
                "--config",
                "/project/" + policy("opengrep"),
                *paths,
            ],
            project=folder,
        )
        report = read_json(step / "stdout.log")
        require(
            code == 0
            and report.get("errors") == []
            and report.get("skipped_rules", []) == []
            and report.get("paths", {}).get("skipped", []) == []
            and sorted(report.get("paths", {}).get("scanned", [])) == sorted(paths),
            "qualificação Opengrep parcial ou catálogo incompatível com o motor instalado",
        )
        write_json(
            self.task / "opengrep-qualification.json",
            dict(
                state="passed",
                targets=paths,
                method="scan estrito offline: schema/padrões nas linguagens importadas; sem metachecks remotos",
            ),
        )

    def grype(self):
        self.prepare_database("grype", self.grype_database)
        # O JSON Grype contém matches, não o inventário completo. Analisar o
        # SBOM Syft previamente validado vincula cobertura e vulnerabilidades.
        targets = self.targets("all")
        inventory_step, inventory_code = self.run(
            "syft",
            [
                "scan",
                "dir:/project",
                "--source-name",
                "ec",
                "--config",
                "/project/" + policy("syft"),
                "--source-version",
                hashlib.sha256(json.dumps(self.before, sort_keys=True).encode()).hexdigest(),
                "--output",
                "syft-json",
            ],
        )
        inventory_path = inventory_step / "stdout.log"
        inventory = read_json(inventory_path)
        basic_report("syft", inventory, inventory_code, targets)
        dependency_counts = verify_lock_inventory(
            self.snapshot, AUTOMATION["components"], inventory
        )
        before = identity(inventory_path)
        step, code = self.run(
            "grype",
            ["sbom:/inventory.json", "--config", "/project/" + policy("grype"), "--output", "json"],
            options=[
                "--mount",
                str(inventory_path) + ":/inventory.json:ro",
                "--env",
                "GRYPE_DB_MAX_ALLOWED_BUILT_AGE="
                + str(AUTOMATION["databases"]["scanners"]["maximum_age_seconds"])
                + "s",
            ],
        )
        require(identity(inventory_path) == before, "inventário Grype mudou durante análise")
        report = read_json(step / "stdout.log")
        write_json(
            self.task / "grype-inventory.json",
            {
                "input": str(inventory_path.relative_to(self.task)),
                "identity": before,
                "artifact_count": len(inventory["artifacts"]),
                "declared_dependency_counts": dependency_counts,
                "effective_configuration": inventory.get("descriptor", {}).get("configuration"),
            },
        )
        result = basic_report(
            "grype", {**report, "artifacts": inventory["artifacts"]}, code, targets
        )
        result.update(artifact_count=len(inventory["artifacts"]), vulnerability_scanner="grype")
        return result

    def trivy_licenses(self):
        # Inventário integral; não inventar uma política jurídica para licenças.
        argv = [
            "filesystem",
            "--quiet",
            "--config",
            "/project/" + policy("trivy"),
            "--scanners",
            "license",
            "--license-full",
            "--format",
            "json",
            "--output",
            "/out/native.json",
            "--exit-code",
            "0",
            "--skip-db-update",
            "--skip-java-db-update",
            "--skip-check-update",
            "--offline-scan",
        ]
        argv += ["/project"]
        step, code = self.run("trivy", argv)
        report = step / "results/native.json"
        self.redact_trivy(report)
        data = read_json(report)
        require(
            code == 0 and data.get("SchemaVersion") == 2 and isinstance(data.get("Results"), list),
            "inventário de licenças Trivy ausente ou inválido",
        )
        licenses = [item for result in data["Results"] for item in result.get("Licenses", [])]
        require(
            all(item.get("Name") and item.get("Severity") for item in licenses),
            "licença sem identificação no relatório",
        )
        return dict(license_count=len(licenses), license_measurement=True)

    def trivy_rules(self, argv):
        database = self.prepare_database("trivy", self.trivy_database)
        write_json(self.task / "trivy-native-databases.json", database)
        self.trivy_fixtures(argv)

    def prepare_database(self, tool, inspect):
        try:
            return inspect()
        except (ExpiredDatabase, MissingDatabase):
            variable = "EC_" + tool.upper() + "_REFRESH"
            require(
                os.environ.get(variable, "0") == "1",
                f"base {tool} ausente/expirada; preparo exige {variable}=1 e download autorizado",
            )
            self.steps[-1]["superseded"] = True
            write_json(self.task / self.steps[-1]["step"] / "exit.json", self.steps[-1])
            write_json(self.task / "native-executions.json", self.steps)
            project = self.task / (tool + "-maintenance")
            project.mkdir()
            if tool == "trivy":
                for label, flag, variable in (
                    ("db", "--download-db-only", "TRIVY_SKIP_DB_UPDATE"),
                    ("java-db", "--download-java-db-only", "TRIVY_SKIP_JAVA_DB_UPDATE"),
                ):
                    self.run(
                        tool,
                        ["filesystem", flag],
                        project=project,
                        maintenance=True,
                        options=["--refresh-database", label, "--env", variable + "=false"],
                    )
            else:
                self.run(
                    tool,
                    ["db", "update"],
                    project=project,
                    maintenance=True,
                    options=["--env", "GRYPE_DB_MAX_UPDATE_CHECK_FREQUENCY=0"],
                )
            database = inspect()
            require(
                self.steps[-1]["database"] == self.steps[-2]["published_database"],
                f"base {tool} selecionada diverge da publicação de manutenção",
            )
            return database

    def grype_database(self):
        # Status nativo separa integridade da idade aplicada abaixo pela política
        # canônica. Assim uma base velha e corrompida não vira mero download.
        step, code = self.run(
            "grype",
            ["db", "status", "--output", "json"],
            options=[
                "--env",
                "GRYPE_DB_VALIDATE_AGE=false",
                "--env",
                "GRYPE_DB_VALIDATE_BY_HASH_ON_START=true",
            ],
        )
        status = read_json(step / "stdout.log")
        require(
            code == 0 and status.get("valid") is True and not status.get("error"),
            "base Grype inválida; consultar status nativo preservado",
        )
        fresh(status.get("built"))
        write_json(self.task / "grype-native-database.json", status)
        return status

    def trivy_database(self):
        version, code = self.run("trivy", ["version", "--format", "json"])
        require(code == 0, "não foi possível inspecionar a base Trivy")
        database = read_json(version / "stdout.log")
        # UpdatedAt/NextUpdate são upstream: preparo recente não renova base velha.
        for key in ("VulnerabilityDB", "JavaDB"):
            item = database[key]
            fresh(item["UpdatedAt"])
            if dt.datetime.fromisoformat(
                item["NextUpdate"].replace("Z", "+00:00")
            ) <= dt.datetime.now(dt.UTC):
                raise ExpiredDatabase("base Trivy além de NextUpdate")
        return database

    def trivy_fixtures(self, argv):
        for label in ("positive", "negative"):
            fixture = self.task / ("trivy-fixture-" + label)
            config = policy("trivy")
            (fixture / config).parent.mkdir(parents=True)
            shutil.copy2(self.snapshot / config, fixture / config)
            (fixture / ".trivyignore").write_text("")
            app = fixture / "application"
            app.mkdir()
            (app / "public.txt").write_text("ordinary public text\n")
            if label == "positive":
                (app / "Dockerfile").write_text("FROM alpine:3.20\nUSER root\n")
                (app / "secret.txt").write_text(
                    'token = "ghp_' + "Abcdef0123456789" * 2 + 'Abcdef"\n'
                )
                write_json(
                    app / "package-lock.json",
                    {
                        "name": "fixture",
                        "version": "1.0.0",
                        "lockfileVersion": 3,
                        "packages": {
                            "": {"name": "fixture", "version": "1.0.0"},
                            "node_modules/lodash": {"version": "4.17.20"},
                        },
                    },
                )
            step, code = self.run("trivy", argv, project=fixture)
            self.redact_trivy(step / "results/native.json")
            report = read_json(step / "results/native.json")
            require(
                report.get("SchemaVersion") == 2 and isinstance(report.get("Results"), list),
                "relatório de eficácia Trivy inválido",
            )
            counts = [
                sum(len(r.get("Vulnerabilities", [])) for r in report["Results"]),
                sum(len(r.get("Secrets", [])) for r in report["Results"]),
                sum(
                    sum(m.get("Status") == "FAIL" for m in r.get("Misconfigurations", []))
                    for r in report["Results"]
                ),
            ]
            require(
                (code == 1 and all(counts))
                if label == "positive"
                else (code == 0 and not any(counts)),
                "fixture Trivy não comprovou vuln/misconfig/secret ou negativo limpo",
            )

    def redact_trivy(self, report):
        result = subprocess.run(  # Filtro jq versionado.
            ["jq", "-f", str(CODE / "scripts/stacks/ec/security/redact-trivy.jq"), str(report)],
            capture_output=True,
            check=False,
        )
        require(result.returncode == 0, "relatório Trivy inválido ou suprimido")
        write_json(report, json.loads(result.stdout))
        shutil.copyfile(report, report.parent.parent / "native.json.log")

    def copy_build(self):
        configured = os.environ.get("EC_FRONTEND_BUILD_DIR")
        build = (
            Path(configured)
            if configured
            else self.root / AUTOMATION["components"]["frontend"]["root"] / "dist"
        )
        if configured:
            require(
                build == self.task.parent / "frontend-dist", "build deve pertencer ao run atual"
            )
        self.build_root = Path("/") if configured else self.root
        if not build.is_dir() or not (build / "index.html").is_file():
            raise Blocked("build estático frontend necessário antes do Playwright")
        regular(self.build_root, str((build / "index.html").relative_to(self.build_root)))
        observed = {}
        for path in sorted(build.rglob("*")):
            require(not path.is_symlink(), "symlink no build Playwright")
            if path.is_file():
                name = str(path.relative_to(self.build_root))
                observed[name] = identity(path)
                dest = (
                    self.snapshot
                    / AUTOMATION["components"]["frontend"]["root"]
                    / "dist"
                    / path.relative_to(build)
                )
                dest.parent.mkdir(parents=True, exist_ok=True)
                shutil.copy2(path, dest)
                require(identity(dest) == observed[name], "build mudou durante cópia")
        self.build_before = observed
        write_json(self.task / "build.json", observed)

    def secret_rules(self):
        for name, positive in (
            ("positive", True),
            ("negative", False),
            ("allowlist", False),
            ("allowlist-boundary", True),
            ("history-map", False),
            ("history-map-boundary", True),
            ("public-indicators", False),
            ("public-indicators-boundary", True),
        ):
            fixture = self.task / ("fixture-" + name)
            config = policy("gitleaks")
            (fixture / config).parent.mkdir(parents=True)
            shutil.copy2(self.snapshot / config, fixture / config)
            (fixture / ".gitleaksignore").write_text("")
            expected_path = "sample.txt"
            synthetic_line = None
            token = "ghp_" + "Abcdef0123456789" * 2 + "Abcdef"
            if name.startswith("history-map"):
                expected_path = "docs/validation/scripts-ec-20261105/final-inputs.json"
                sample = fixture / expected_path
                sample.parent.mkdir(parents=True, exist_ok=True)
                value = token if positive else hashlib.sha256(b"ec-integrity-probe").hexdigest()
                sample.write_text(json.dumps({"sdd/chain/api-testing.md": value}, indent=2) + "\n")
            elif name.startswith("public-indicators"):
                catalog = AUTOMATION["tools"]["yara-x"]["catalogs"][0]["root"]
                shutil.copytree(self.snapshot / catalog, fixture / catalog)
                expected_path = catalog + "/yara/apt_dubnium.yar"
                if positive:
                    sample = fixture / expected_path
                    sample.write_text(sample.read_text() + '\ntoken = "' + token + '"\n')
                    synthetic_line = next(
                        i + 1
                        for i, line in enumerate(sample.read_text().splitlines())
                        if token in line
                    )
            elif name.startswith("allowlist"):
                sample = fixture / (
                    "config/governance/profile.json" if name == "allowlist" else "sample.txt"
                )
                sample.parent.mkdir(parents=True, exist_ok=True)
                sample.write_text(
                    '  "references/api-key.md": "'
                    + hashlib.sha256(b"ec-public-rule-fixture").hexdigest()
                    + '"\n'
                )
            else:
                (fixture / "sample.txt").write_text(
                    ('token = "' + token + '" # gitleaks:allow\n') if positive else "public text\n"
                )
            step, code = self.secrets(fixture)
            parsed = self.parse("secrets", step / "results/native.json", code)
            native_findings = read_json(step / "results/native.json")
            positive_sources = [relative(item["File"]) for item in native_findings]
            if name.startswith("public-indicators"):
                observed = {
                    relative(item["File"]): identity(regular(fixture, relative(item["File"])))
                    for item in native_findings
                }
                parsed = gitleaks_review(
                    native_findings, observed, AUTOMATION["tools"]["gitleaks"].get("triage", {})
                )
                positive_sources = [item["source"] for item in parsed["pending"]]
                if positive:
                    require(
                        any(
                            item["RuleID"] == "github-pat"
                            and relative(item["File"]) == expected_path
                            and item["StartLine"] == synthetic_line
                            for item in native_findings
                        ),
                        "fixture pública não detectou a credencial sintética nova",
                    )
            require((parsed["finding_count"] > 0) == positive, "regra Gitleaks sem eficácia")
            if positive:
                require(
                    all(source == expected_path for source in positive_sources),
                    "fixture Gitleaks detectou fonte inesperada",
                )
        return {"state": "passed", "finding_count": 0, "effectiveness": True}

    def yara(self):
        from tools.quality.yara_scan import (
            EXTERNALS,
            external_arguments,
            file_context,
            parse_ndjson,
        )

        catalogs = verify_catalogs(AUTOMATION, self.snapshot, "yara-x")
        write_json(self.task / "rule-catalogs.json", catalogs)
        sources = [policy("yara-x")]
        for catalog in catalogs:
            sources += [
                str(path.relative_to(self.snapshot))
                for path in sorted((self.snapshot / catalog["root"]).rglob("*"))
                if path.suffix in (".yar", ".yara")
            ]
        inputs = self.task / "yara-inputs"
        outputs = self.task / "yara-output"
        inputs.mkdir()
        outputs.mkdir()
        options = [
            "--mount",
            str(inputs) + ":/ec-yara-inputs:ro",
            "--mount",
            str(outputs) + ":/ec-yara:rw",
        ]
        groups = dict(content=[], context=[])
        variables = re.compile(r"(?<![$.])\b(?:" + "|".join(EXTERNALS) + r")\b")
        for name in sources:
            text = (self.snapshot / name).read_text()
            require(
                not re.search(r'(?m)^\s*include\s*"', text),
                "include YARA exige importação explícita e revisada",
            )
            group = "context" if variables.search(text) else "content"
            destination = inputs / group / name
            destination.parent.mkdir(parents=True, exist_ok=True)
            shutil.copy2(self.snapshot / name, destination)
            groups[group].append(name)
        # Sentinela restrita ao teste do transporte das variáveis externas.
        probe = inputs / "context/ec_context_probe.yar"
        probe.parent.mkdir(exist_ok=True)
        probe.write_text(
            'rule Ec_Context_Probe { meta: author="Ec" '
            'strings: $probe="EC-CONTEXT-PROBE" condition: $probe and '
            'filename=="positive.fixture" and extension==".fixture" and '
            'filepath contains "positive.fixture" and owner=="ec-fixture-owner" }'
        )
        for group in groups:
            step, code = self.run(
                "yara-x",
                [
                    "compile",
                    "--path-as-namespace",
                    "--output",
                    "/ec-yara/" + group + ".yarc",
                    *external_arguments({}),
                    "/ec-yara-inputs/" + group,
                ],
                options=options,
            )
            require(
                code == 0 and (outputs / (group + ".yarc")).is_file(),
                "compilação do catálogo YARA-X falhou",
            )
            require(
                not re.search(
                    rb"\b(?:ignored_rule|unsupported_module|unknown_module)\b",
                    (step / "stderr.log").read_bytes(),
                ),
                "YARA-X descartou regras do catálogo",
            )
        rule_roots = (str(PurePosixPath(policy("yara-x")).parent) + "/", policy("opengrep") + "/")
        targets = [name for name in self.targets("all") if not name.startswith(rule_roots)]
        require(
            targets and all("\n" not in name and "\r" not in name for name in targets),
            "alvo YARA inválido",
        )
        write_json(self.task / "yara-targets.json", targets)
        for label, content in (
            ("positive", "EICAR-" + "STANDARD-ANTIVIRUS-TEST-FILE EC-CONTEXT-PROBE"),
            ("negative", "ordinary public text"),
        ):
            (self.snapshot / (label + ".fixture")).write_text(content)
        all_paths = ["/project/" + name for name in targets]
        fixtures = ["/project/positive.fixture", "/project/negative.fixture"]
        listing = outputs / "targets.list"
        listing.write_text("\n".join(all_paths + fixtures) + "\n")

        def scan(group, paths, values, listing=False):
            argv = [
                "scan",
                "--compiled-rules",
                "--output-format=ndjson",
                "--print-meta",
                "--print-namespace",
                *external_arguments(values),
            ]
            if listing:
                argv += ["--scan-list"]
            step, code = self.run(
                "yara-x",
                [
                    *argv,
                    "/ec-yara/" + group + ".yarc",
                    "/ec-yara/targets.list" if listing else paths[0],
                ],
                options=options,
            )
            require(
                code == 0 and not (step / "stderr.log").read_bytes(),
                "YARA-X parcial ou com erro operacional",
            )
            try:
                return parse_ndjson(step / "stdout.log", paths)
            except ValueError as error:
                raise Rejected(str(error)) from error

        content_hits = scan("content", all_paths + fixtures, {}, listing=True)
        require(
            any(
                item["path"] == fixtures[0] and item["identifier"] == "Ec_Eicar_Test_File"
                for item in content_hits
            )
            and not any(item["path"] == fixtures[1] for item in content_hits),
            "sentinelas do catálogo YARA-X divergentes",
        )
        count = sum(item["path"] not in fixtures for item in content_hits)
        for label, path in zip(("positive", "negative"), fixtures, strict=False):
            hits = scan(
                "context",
                [path],
                dict(
                    filename=label + ".fixture",
                    filepath=path,
                    extension=".fixture",
                    owner="ec-fixture-owner",
                    filetype="UNKNOWN",
                ),
            )
            require(
                (any(item["identifier"] == "Ec_Context_Probe" for item in hits))
                == (label == "positive")
                and (label == "positive" or not hits),
                "contexto YARA-X não comprovado",
            )
        for name in targets:
            count += len(scan("context", ["/project/" + name], file_context(self.root, name)))
        return dict(
            state="findings" if count else "passed",
            finding_count=count,
            scope="catálogo versionado e contexto real por arquivo; sem promessa universal",
            effectiveness="sentinelas EICAR/contexto e inventário completo",
            catalog_files={group: len(files) for group, files in groups.items()},
            target_count=len(targets),
        )

    def codeql(self):
        projects = codeql_projects(AUTOMATION)
        require(
            {"server": "rust", "agent": "rust", "frontend": "javascript-typescript"}.items()
            <= {p["name"]: p["language"] for p in projects}.items(),
            "três alvos CodeQL obrigatórios",
        )
        counts, outcomes = 0, []
        for project in projects:
            try:
                parsed = self.codeql_project(project)
                counts += parsed["finding_count"]
                outcomes.append(dict(target=project["name"], **parsed))
            except (Blocked, Rejected) as error:
                outcomes.append({
                    "target": project["name"],
                    "state": "blocked" if isinstance(error, Blocked) else "error",
                    "reason": str(error),
                })
            except (OSError, ValueError, KeyError, TypeError, AttributeError):
                outcomes.append({
                    "target": project["name"],
                    "state": "error",
                    "reason": "base ou relatório CodeQL inválido",
                })
        write_json(self.task / "codeql-targets.json", outcomes)
        state = (
            "error"
            if any(p["state"] == "error" for p in outcomes)
            else "blocked"
            if any(p["state"] == "blocked" for p in outcomes)
            else "findings"
            if counts
            else "passed"
        )
        return {
            "state": state,
            "finding_count": counts,
            "target_results": outcomes,
            "reason": "; ".join(
                p["target"] + ": " + p.get("reason", p["state"])
                for p in outcomes
                if p["state"] in ("error", "blocked")
            ),
            "targets": [p["name"] for p in projects],
        }

    def codeql_project(self, project):
        name, source = project["name"], project["source"]
        require(
            project in codeql_projects(AUTOMATION), "projeto CodeQL fora da configuração canônica"
        )
        extensions = {
            "rust": (".rs",),
            "python": (".py",),
            "javascript-typescript": (".ts", ".tsx", ".vue", ".js", ".jsx", ".mjs", ".cjs"),
        }
        targets = [
            n
            for n in self.before
            if (source == "." or n.startswith(source + "/"))
            and n.endswith(extensions[project["language"]])
        ]
        require(targets, "alvo CodeQL vazio")
        suite = regular(self.snapshot, project["suite"])
        require("version:" not in suite.read_text(), "packs CodeQL pertencem ao bundle central")
        options = [
            "--mount",
            str(self.task / "codeql") + ":/ec-codeql:rw",
            "--workdir",
            "/project/" + source,
        ]
        (self.task / "codeql").mkdir(exist_ok=True)
        if project["language"] == "rust":
            runtime = {}
            for key, dest in (
                ("EC_CODEQL_RUSTUP_HOME", "/ec-rustup"),
                ("EC_CODEQL_CARGO_HOME", "/ec-cargo"),
                ("EC_CODEQL_CARGO_BIN", "/ec-cargo-bin"),
            ):
                if not os.environ.get(key) or not Path(os.environ[key]).is_dir():
                    raise Blocked("CodeQL Rust requer " + key + " preparado para Linux/amd64")
                runtime[key] = Path(os.environ[key]).resolve()
                require(runtime[key] not in (Path("/"), Path.home()), "montagem Rust ampla demais")
                if key != "EC_CODEQL_CARGO_HOME":
                    options += ["--mount", str(runtime[key]) + ":" + dest + ":ro"]
            # Cargo precisa de locks/cache graváveis. Copiar só dependências preparadas;
            # não transportar config/credentials/tokens do host nem baixar pacotes.
            private_cargo = self.task / "codeql" / ("cargo-" + name)
            private_cargo.mkdir()
            dependency_inputs = {}
            for folder in ("registry", "git"):
                origin = runtime["EC_CODEQL_CARGO_HOME"] / folder
                if origin.exists():
                    require(not origin.is_symlink(), "symlink no cache Cargo")
                    for path in origin.rglob("*"):
                        require(
                            not path.is_symlink(), "symlink não revisado nas dependências Cargo"
                        )
                        if path.is_file():
                            dependency_inputs[
                                str(path.relative_to(runtime["EC_CODEQL_CARGO_HOME"]))
                            ] = identity(path)
                    shutil.copytree(origin, private_cargo / folder)
            for path, observed in dependency_inputs.items():
                require(
                    identity(private_cargo / path) == observed
                    and identity(runtime["EC_CODEQL_CARGO_HOME"] / path) == observed,
                    "dependência Cargo mudou durante cópia",
                )
            write_json(self.task / ("codeql-" + name + "-dependencies.json"), dependency_inputs)
            channel = os.environ.get("EC_RUST_TOOLCHAIN")
            if not channel or not re.fullmatch(r"[a-zA-Z0-9_.-]+", channel):
                raise Blocked("CodeQL Rust requer o toolchain ativo selecionado pelo runner")
            launchers = self.task / "codeql" / ("launchers-" + name)
            launchers.mkdir()
            binary_root = "/ec-rustup/toolchains/" + channel + "/bin/"
            for executable in ("cargo", "rustc"):
                launcher = launchers / executable
                launcher.write_text(
                    "#!/bin/sh\nexec /usr/bin/python3 /project/tools/quality/codeql_rust.py "
                    + binary_root
                    + executable
                    + " /ec-codeql/"
                    + name
                    + "-"
                    + executable
                    + '.stderr.log "$@"\n'
                )
                launcher.chmod(0o700)
            options += ["--mount", str(launchers) + ":/ec-rust-launchers:ro"]
            options += [
                "--env",
                "RUSTUP_HOME=/ec-rustup",
                "--env",
                "CARGO_HOME=/ec-codeql/cargo-" + name,
                "--env",
                "RUSTUP_TOOLCHAIN=" + channel,
                "--env",
                "RUSTUP_AUTO_INSTALL=0",
                "--env",
                "CARGO_NET_OFFLINE=true",
                "--env",
                "CARGO_TARGET_DIR=/ec-codeql/target-" + name,
                "--env",
                "PATH=/ec-rust-launchers:/ec-rustup/toolchains/"
                + channel
                + "/bin:/ec-cargo-bin:/usr/local/bin:/usr/bin:/bin",
                "--env",
                "CARGO=/ec-rust-launchers/cargo",
                "--env",
                "RUSTC=/ec-rust-launchers/rustc",
            ]
        database = "/ec-codeql/" + name
        step, code = self.run(
            "codeql",
            [
                "database",
                "create",
                database,
                "--language=" + project["language"],
                "--source-root=/project/" + source,
                "--build-mode=none",
            ],
            options=options,
        )
        require(code == 0, "criação da base CodeQL falhou")
        if project["language"] == "rust":
            from tools.quality.codeql_rust import validate_status

            try:
                validate_status(
                    self.task / "codeql", name, AUTOMATION["runtime"]["native_log_bytes"]
                )
            except ValueError as error:
                raise Rejected(str(error)) from error
        info, code = self.run(
            "codeql", ["resolve", "database", "--format=json", database], options=options
        )
        status = read_json(info / "stdout.log")
        # O resolver retorna metadados opacos; não inventar campo "finalised".
        # create finaliza a base e analyze rejeita bases não finalizadas.
        require(code == 0 and isinstance(status, dict) and status, "base CodeQL não resolvida")
        step, code = self.run(
            "codeql",
            [
                "database",
                "analyze",
                database,
                "/project/" + project["suite"],
                "--format=sarifv2.1.0",
                "--output=/out/native.sarif",
                "--sarif-add-baseline-file-info",
                "--sarif-include-diagnostics",
                "--no-sarif-group-rules-by-pack",
                "--no-sarif-add-file-contents",
                "--no-sarif-add-snippets",
            ],
            options=options,
        )
        report = read_json(step / "results/native.sarif")
        from tools.quality.sarif_report import evaluate

        require(code == 0, "análise CodeQL falhou")
        try:
            return evaluate(report, targets, source, project["language"])
        except ValueError as error:
            raise Rejected(str(error)) from error


def main():
    os.umask(0o077)
    if len(sys.argv) != 2 or sys.argv[1] not in CHECKS:
        print("uso: scanner-gate.sh " + "|".join(CHECKS), file=sys.stderr)
        return 2
    root = Path(os.environ.get("EC_ROOT", CODE)).resolve(strict=True)
    if not os.environ.get("EC_TASK_DIR"):
        print("blocked: executar pelo automate.sh para preservar evidências", file=sys.stderr)
        return 3
    task = Path(os.environ["EC_TASK_DIR"]).resolve(strict=True)
    gate = Gate(sys.argv[1], root, task)
    for sig in (signal.SIGTERM, signal.SIGINT, signal.SIGHUP):
        signal.signal(sig, gate.receive)
    result = {"state": "error", "finding_count": None}
    try:
        gate.prepare()
        result = gate.execute()
    except Blocked as error:
        result.update(state="blocked", reason=str(error))
    except Rejected as error:
        result.update(state="error", reason=str(error))
    except (
        OSError,
        ValueError,
        KeyError,
        TypeError,
        AttributeError,
        IndexError,
        subprocess.SubprocessError,
    ) as error:
        write_json(
            task / "failure.json",
            {
                "type": type(error).__name__,
                "frames": [
                    {"file": frame.filename, "line": frame.lineno, "function": frame.name}
                    for frame in traceback.extract_tb(error.__traceback__)
                ],
            },
        )
        result.update(
            state="error",
            reason=type(error).__name__
            + ": entrada, relatório ou execução inválida; consultar failure.log e streams",
        )
    finally:
        if hasattr(gate, "before"):
            try:
                require(inventory(root) == gate.before, "fontes alteradas durante execução")
                for name, observed in getattr(gate, "build_before", {}).items():
                    require(
                        identity(regular(gate.build_root, name)) == observed,
                        "build alterado durante Playwright",
                    )
            except (OSError, Rejected, subprocess.SubprocessError):
                result.update(state="error", reason="integridade das fontes/build divergente")
        if gate.cancelled:
            result.update(state="error", reason="cancelado; nenhuma aprovação")
        result["native_executions"] = gate.steps
        write_json(task / "classification.json", result)
    print(json.dumps(result, ensure_ascii=False))
    return {"passed": 0, "measured": 0, "findings": 1, "error": 2, "blocked": 3}[result["state"]]


if __name__ == "__main__":
    sys.exit(main())
