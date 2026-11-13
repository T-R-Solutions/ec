"""Contrato e projeções da única configuração da automação Ec.

Não executa configuração como código, não instala ferramentas e não contém
uma segunda tabela de tarefas. As políticas nativas são referenciadas no JSON.
"""

import argparse
import fnmatch
import glob
import hashlib
import json
import os
import re
import shutil
import stat
import subprocess
import sys
from pathlib import Path, PurePosixPath

CODE = Path(__file__).resolve().parents[2]
CONFIG_PATH = CODE / "scripts/config/automation.json"


def fields(value, required, optional=()):
    if (
        not isinstance(value, dict)
        or set(value) - set(required) - set(optional)
        or set(required) - set(value)
    ):
        raise ValueError("campos inválidos na configuração; esperados: " + ", ".join(required))


def strings(value):
    if not isinstance(value, list) or not all(
        isinstance(item, str) and item and "\x00" not in item for item in value
    ):
        raise ValueError("lista de strings não vazias necessária")
    if len(value) != len(set(value)):
        raise ValueError("lista da configuração contém itens duplicados")


def internal(value):
    if not isinstance(value, str) or not value or "\x00" in value or "\n" in value:
        raise ValueError("caminho inválido na configuração")
    path = PurePosixPath(value)
    if path.is_absolute() or ".." in path.parts:
        raise ValueError("caminho deve ser interno: " + value)
    return value


def positive(value):
    if type(value) is not int or value <= 0:
        raise ValueError("limite deve ser inteiro positivo")


def unique(pairs):
    result = {}
    for key, value in pairs:
        if key in result:
            raise ValueError("chave duplicada na configuração: " + key)
        result[key] = value
    return result


def load_config(path=CONFIG_PATH):
    if not Path(path).is_file() or Path(path).is_symlink():
        raise ValueError("configuração deve ser arquivo regular")
    with Path(path).open() as source:
        value = json.load(
            source,
            object_pairs_hook=unique,
            parse_constant=lambda _: (_ for _ in ()).throw(ValueError("JSON não finito")),
        )
    fields(
        value,
        (
            "schema",
            "components",
            "policies",
            "tools",
            "selection",
            "runtime",
            "environment",
            "discovery",
            "databases",
            "coverage",
            "services",
            "inputs",
            "tasks",
            "browser",
        ),
    )
    if type(value["schema"]) is not int or value["schema"] != 1:
        raise ValueError("schema da automação não suportado")
    fields(value["components"], ("server", "agent", "frontend"))
    for component in value["components"].values():
        fields(component, ("root", "lockfile", "language", "suite"))
        for key in ("root", "lockfile", "suite"):
            internal(component[key])
        if not component["lockfile"].startswith(component["root"] + "/"):
            raise ValueError("lockfile fora do componente")
        if component["language"] not in ("rust", "javascript-typescript"):
            raise ValueError("linguagem de componente desconhecida")
    fields(
        value["policies"],
        (
            "cargo-deny",
            "cargo-audit",
            "clippy",
            "ruff",
            "shellcheck",
            "bandit",
            "gitleaks",
            "grype",
            "syft",
            "osv-scanner",
            "trivy",
            "playwright",
            "opengrep",
            "yara-x",
        ),
    )
    for policy in value["policies"].values():
        internal(policy)
    if not isinstance(value["tools"], dict) or not value["tools"]:
        raise ValueError("inventário de ferramentas vazio")
    for name, tool in value["tools"].items():
        if not re.fullmatch(r"[a-z][a-z0-9-]*", name):
            raise ValueError("nome de ferramenta inválido")
        fields(
            tool,
            ("executor",),
            ("timeout", "triage", "decode_depth", "additional_projects", "catalogs"),
        )
        if "catalogs" in tool:
            if (
                name not in ("opengrep", "yara-x")
                or not isinstance(tool["catalogs"], list)
                or not tool["catalogs"]
            ):
                raise ValueError("catálogos de regras inválidos")
            roots = set()
            for catalog in tool["catalogs"]:
                fields(
                    catalog,
                    (
                        "repository",
                        "revision",
                        "license",
                        "root",
                        "archive_sha256",
                        "tree_sha256",
                        "file_count",
                    ),
                )
                internal(catalog["root"])
                if catalog["root"] in roots or not catalog["license"].strip():
                    raise ValueError("catálogo duplicado ou sem licença")
                roots.add(catalog["root"])
                if not re.fullmatch(r"[A-Za-z0-9_.-]+/[A-Za-z0-9_.-]+", catalog["repository"]):
                    raise ValueError("repositório de catálogo inválido")
                if not re.fullmatch(r"[0-9a-f]{40}", catalog["revision"]) or any(
                    not re.fullmatch(r"[0-9a-f]{64}", catalog[key])
                    for key in ("archive_sha256", "tree_sha256")
                ):
                    raise ValueError("proveniência de catálogo inválida")
                positive(catalog["file_count"])
                policy_root = PurePosixPath(value["policies"][name])
                if name == "yara-x":
                    policy_root = policy_root.parent
                if not catalog["root"].startswith(str(policy_root) + "/"):
                    raise ValueError("catálogo fora da política responsável")
        if name == "codeql" and "additional_projects" not in tool:
            raise ValueError("CodeQL deve declarar explicitamente os projetos adicionais")
        if name == "gitleaks" and "decode_depth" not in tool:
            raise ValueError("Gitleaks deve declarar a profundidade de decodificação")
        if "additional_projects" in tool:
            if name != "codeql" or not isinstance(tool["additional_projects"], list):
                raise ValueError("projetos adicionais pertencem somente ao CodeQL")
            seen_projects = set(value["components"])
            for project in tool["additional_projects"]:
                fields(project, ("name", "source", "language", "suite"))
                if (
                    not re.fullmatch(r"[a-z][a-z0-9_-]*", project["name"])
                    or project["name"] in seen_projects
                ):
                    raise ValueError("projeto CodeQL duplicado ou inválido")
                seen_projects.add(project["name"])
                internal(project["source"])
                internal(project["suite"])
                if project["language"] not in ("rust", "javascript-typescript", "python"):
                    raise ValueError("linguagem CodeQL não suportada pelo consumidor")
        if "decode_depth" in tool:
            if name != "gitleaks":
                raise ValueError("profundidade de decodificação pertence ao Gitleaks")
            positive(tool["decode_depth"])
        if "triage" in tool:
            if name not in ("bandit", "gitleaks") or not isinstance(tool["triage"], dict):
                raise ValueError("triagem pertence somente ao Bandit/Gitleaks")
            for path, review in tool["triage"].items():
                internal(path)
                fields(review, ("sha256", "rules"))
                if not re.fullmatch(r"[0-9a-f]{64}", review["sha256"]) or not isinstance(
                    review["rules"], list
                ):
                    raise ValueError("identidade de triagem inválida")
                seen = set()
                for rule in review["rules"]:
                    fields(rule, ("id", "lines", "reason"))
                    pattern = r"B[0-9]{3}" if name == "bandit" else r"[a-z][a-z0-9_-]*"
                    if not re.fullmatch(pattern, rule["id"]) or not rule["reason"].strip():
                        raise ValueError("regra/justificativa de triagem inválida")
                    if not rule["lines"] or any(
                        type(line) is not int or line <= 0 for line in rule["lines"]
                    ):
                        raise ValueError("linhas de triagem inválidas")
                    for line in rule["lines"]:
                        pair = (rule["id"], line)
                        if pair in seen:
                            raise ValueError("ocorrência de triagem duplicada")
                        seen.add(pair)
        if "timeout" in tool:
            positive(tool["timeout"])
        if tool["executor"] not in ("cargo", "toolbox"):
            raise ValueError("executor de ferramenta inválido")
    fields(
        value["runtime"],
        (
            "task_timeout",
            "native_timeout",
            "probe_timeout",
            "cleanup_timeout",
            "log_bytes",
            "native_log_bytes",
            "geiger_stack_bytes",
            "json_bytes",
        ),
    )
    for limit in value["runtime"].values():
        positive(limit)
    fields(value["environment"], ("defaults", "forward"))
    if not isinstance(value["environment"]["defaults"], dict):
        raise ValueError("defaults deve mapear variáveis de ambiente")
    strings(value["environment"]["forward"])
    if not all(
        re.fullmatch(r"[A-Za-z_][A-Za-z_0-9]*", key) for key in value["environment"]["forward"]
    ):
        raise ValueError("nome de variável encaminhada inválido")
    for key, item in value["environment"]["defaults"].items():
        if (
            not re.fullmatch(r"[A-Za-z_][A-Za-z_0-9]*", key)
            or not isinstance(item, str)
            or "\x00" in item
        ):
            raise ValueError("variável de ambiente inválida")
    fields(value["discovery"], ("toolbox", "rustsec"))
    for patterns in value["discovery"].values():
        strings(patterns)
        for pattern in patterns:
            pattern.format(parent="", home="", cargo_home="")
    fields(value["databases"], ("rustsec", "scanners"))
    fields(value["databases"]["rustsec"], ("maximum_age_seconds", "url", "deny_directory"))
    fields(value["databases"]["scanners"], ("maximum_age_seconds",))
    internal(value["databases"]["rustsec"]["deny_directory"])
    if not isinstance(value["databases"]["rustsec"]["url"], str) or not value["databases"][
        "rustsec"
    ]["url"].startswith("https://"):
        raise ValueError("origem RustSec deve ser URL HTTPS")
    for database in value["databases"].values():
        positive(database["maximum_age_seconds"])
    fields(value["coverage"], ("format", "minimum"))
    fields(value["coverage"]["minimum"], ("lines",))
    minimum = value["coverage"]["minimum"]["lines"]
    if (
        value["coverage"]["format"] != "lcov"
        or type(minimum) not in (int, float)
        or not 80 <= minimum <= 100
    ):
        raise ValueError("cobertura exige LCOV e pelo menos 80% de linhas")
    fields(value["browser"], ("playwright", "journey"))
    fields(
        value["browser"]["journey"],
        ("engines", "network", "startup_timeout", "command_timeout", "config"),
    )
    strings(value["browser"]["journey"]["engines"])
    if not value["browser"]["journey"]["engines"] or not set(
        value["browser"]["journey"]["engines"]
    ) <= {"chromium", "firefox"}:
        raise ValueError("navegador da jornada desconhecido")
    if value["browser"]["journey"]["network"] != "host":
        raise ValueError("jornada real requer acesso explícito aos serviços locais")
    positive(value["browser"]["journey"]["startup_timeout"])
    positive(value["browser"]["journey"]["command_timeout"])
    internal(value["browser"]["journey"]["config"])
    fields(
        value["browser"]["playwright"],
        (
            "browser",
            "port",
            "expected_tests",
            "test_timeout_ms",
            "expect_timeout_ms",
            "server_timeout_ms",
            "test_directory",
        ),
    )
    if value["browser"]["playwright"]["browser"] not in value["browser"]["journey"]["engines"]:
        raise ValueError("browser Playwright não declarado entre os motores")
    internal(value["browser"]["playwright"]["test_directory"])
    for key in (
        "port",
        "expected_tests",
        "test_timeout_ms",
        "expect_timeout_ms",
        "server_timeout_ms",
    ):
        positive(value["browser"]["playwright"][key])
    fields(value["services"], ("postgres", "http", "frontend"))
    fields(
        value["services"]["postgres"],
        ("container", "host", "port", "container_port", "user", "start_existing", "ready_timeout"),
    )
    fields(value["services"]["http"], ("host", "port"))
    fields(value["services"]["frontend"], ("port",))
    for service in value["services"].values():
        positive(service["port"])
        if service["port"] > 65535 or service.get("host", "127.0.0.1") != "127.0.0.1":
            raise ValueError("serviço de teste deve usar loopback e porta válida")
    if type(value["services"]["postgres"]["start_existing"]) is not bool:
        raise ValueError("start_existing deve ser booleano")
    positive(value["services"]["postgres"]["ready_timeout"])
    positive(value["services"]["postgres"]["container_port"])
    for key in ("container", "user"):
        item = value["services"]["postgres"][key]
        if not isinstance(item, str) or not re.fullmatch(r"[a-zA-Z0-9][a-zA-Z0-9_.-]*", item):
            raise ValueError("identificador PostgreSQL inválido")
    ports = [s["port"] for s in value["services"].values()] + [
        value["browser"]["playwright"]["port"]
    ]
    if len(ports) != len(set(ports)) or any(
        port > 65535 for port in ports + [value["services"]["postgres"]["container_port"]]
    ):
        raise ValueError("portas inválidas ou conflitantes")
    fields(
        value["inputs"],
        (
            "roots",
            "files",
            "root_patterns",
            "exclude_names",
            "exclude_paths",
            "python_roots",
            "sast_extensions",
        ),
    )
    for items in value["inputs"].values():
        strings(items)
    if any(
        "/" in pattern or pattern in (".", "..") for pattern in value["inputs"]["root_patterns"]
    ):
        raise ValueError("padrões da raiz devem selecionar somente nomes imediatos")
    for key in ("roots", "files", "exclude_paths", "python_roots"):
        for name in value["inputs"][key]:
            internal(name)
    fields(value["selection"], ("surfaces", "explicit_surface", "mandatory"))
    for items in value["selection"].values():
        strings(items)
    if not set(value["selection"]["mandatory"]) <= set(value["selection"]["surfaces"]):
        raise ValueError("superfície obrigatória desconhecida")
    tasks = value["tasks"]
    if not isinstance(tasks, list) or not tasks:
        raise ValueError("catálogo vazio")
    identifiers = set()
    for task in tasks:
        fields(
            task,
            ("project", "name", "cwd", "executor", "argv", "depends", "surfaces", "modes"),
            ("timeout", "tool"),
        )
        for key in ("project", "name"):
            if not isinstance(task[key], str) or not re.fullmatch(r"[a-z][a-z0-9_-]*", task[key]):
                raise ValueError("identificador de tarefa inválido")
        for key in ("depends", "surfaces", "modes"):
            strings(task[key])
        identifier = task["project"] + ":" + task["name"]
        unknown_surfaces = set(task["surfaces"]) - set(value["selection"]["surfaces"])
        if unknown_surfaces:
            raise ValueError(
                "superfície de tarefa desconhecida em "
                + identifier
                + ": "
                + ", ".join(sorted(unknown_surfaces))
            )
        if task["modes"] == ["dependency"] and task["surfaces"]:
            raise ValueError(
                "preparo por dependência não deve declarar superfície pública: " + identifier
            )
        if identifier in identifiers:
            raise ValueError("tarefa duplicada: " + identifier)
        identifiers.add(identifier)
        if not isinstance(task["cwd"], str):
            raise ValueError("cwd deve ser caminho ou componente")
        if task["cwd"].startswith("@"):
            if task["cwd"][1:] not in value["components"]:
                raise ValueError("cwd refere componente desconhecido")
        else:
            internal(task["cwd"])
        if task["executor"] not in ("host", "cargo", "toolbox", "browser"):
            raise ValueError("executor inválido: " + identifier)
        positive(task.get("timeout", value["runtime"]["task_timeout"]))
        if (
            not isinstance(task["argv"], list)
            or not task["argv"]
            or not all(isinstance(a, str) and "\x00" not in a for a in task["argv"])
        ):
            raise ValueError("argv inválido: " + identifier)
        for arg in task["argv"]:
            arg.format(code=str(CODE))
        if not set(task["modes"]) <= {"default", "complete", "manual", "dependency"}:
            raise ValueError("modo inválido: " + identifier)
        if task["executor"] == "cargo" and task["argv"][:2] != [
            "bash",
            "{code}/scripts/stacks/rust/check.sh",
        ]:
            raise ValueError("Cargo deve usar o executor instalado no host")
        if task["executor"] == "toolbox" and (
            task.get("tool") not in value["tools"]
            or value["tools"][task["tool"]]["executor"] != "toolbox"
        ):
            raise ValueError("ferramenta Docker não declarada")
        if task["executor"] == "toolbox" and task["argv"][:2] != [
            "bash",
            "{code}/scripts/stacks/ec/scanner-gate.sh",
        ]:
            raise ValueError("scanner deve usar o executor Toolbox")
        if task["executor"] == "browser" and (
            task.get("tool") != "playwright"
            or task["name"] not in value["browser"]["journey"]["engines"]
            or task["argv"]
            != ["python3", "application/integration/tests/e2e/frontend_browser.py", task["name"]]
        ):
            raise ValueError("jornada deve usar o harness Playwright no Docker")
    # Validar o grafo inteiro, inclusive opções não selecionadas neste run.
    ordered(tasks, identifiers)
    return value


def ordered(tasks, selected):
    by_id = {t["project"] + ":" + t["name"]: t for t in tasks}
    result, visiting, visited = [], set(), set()

    def visit(identifier):
        if identifier in visiting:
            raise ValueError("ciclo de dependências: " + identifier)
        if identifier in visited:
            return
        if identifier not in by_id:
            raise ValueError("dependência desconhecida: " + identifier)
        visiting.add(identifier)
        for dependency in by_id[identifier]["depends"]:
            visit(dependency)
        visiting.remove(identifier)
        visited.add(identifier)
        result.append(by_id[identifier])

    for identifier in by_id:
        if identifier in selected:
            visit(identifier)
    return result


def plan(config, root, surfaces=(), checks=(), scope="default"):
    selection = config["selection"]
    if scope not in ("default", "complete") or (scope == "complete" and (surfaces or checks)):
        raise ValueError("--scope complete não aceita filtros")
    if set(surfaces) - set(selection["surfaces"]):
        raise ValueError("superfície desconhecida")
    selectable = [
        t
        for t in config["tasks"]
        if t["project"] in selection["surfaces"] and t["project"] not in selection["mandatory"]
    ]
    if set(checks) - {t["name"] for t in selectable}:
        raise ValueError("check desconhecido")
    if not surfaces:
        if set(checks) & set(selection["explicit_surface"]):
            raise ValueError("coverage requer --surface server, agent ou frontend")
        surfaces = (
            sorted({t["project"] for t in selectable if t["name"] in checks})
            if checks
            else selection["surfaces"]
        )
    selected = set()
    for task in config["tasks"]:
        mandatory = task["project"] in selection["mandatory"]
        eligible = bool(set(task["surfaces"]) & set(surfaces))
        chosen = task["name"] in checks if checks else scope in task["modes"]
        if mandatory or (eligible and chosen):
            selected.add(task["project"] + ":" + task["name"])
    for check in checks:
        if not any(
            t["name"] == check and t["project"] + ":" + check in selected for t in selectable
        ):
            raise ValueError("check incompatível com superfícies: " + check)
    tasks = []
    for task in ordered(config["tasks"], selected):
        row = {k: task[k] for k in ("project", "name", "cwd", "depends", "executor")}
        if row["cwd"].startswith("@"):
            row["cwd"] = config["components"][row["cwd"][1:]]["root"]
        row.update(
            argv=[a.format(code=str(CODE)) for a in task["argv"]],
            timeout=task.get("timeout", config["runtime"]["task_timeout"]),
        )
        tasks.append(row)
    return {
        "root": str(root),
        "scope": scope,
        "surfaces": sorted(set(surfaces)),
        "tasks": tasks,
        "executed": False,
        "configuration_sha256": hashlib.sha256(
            json.dumps(config, sort_keys=True).encode()
        ).hexdigest(),
    }


def environment(config, root, inherited):
    home = inherited["HOME"]
    cargo = inherited.get("CARGO_HOME", home + "/.cargo")
    rustup = inherited.get("RUSTUP_HOME", home + "/.rustup")
    cargo_bin = inherited.get("EC_CARGO_BIN", cargo + "/bin")
    path = inherited.get("PATH", os.defpath)
    if cargo_bin:
        path += os.pathsep + cargo_bin
    result = {k: inherited.get(k, v) for k, v in config["environment"]["defaults"].items()}
    result.update(
        HOME=home, PATH=path, CARGO_HOME=cargo, RUSTUP_HOME=rustup, EC_CARGO_BIN=cargo_bin
    )
    substitutions = dict(parent=str(root.parent), home=home, cargo_home=cargo)
    if "TOOLBOX_BIN" in inherited:
        toolbox = inherited["TOOLBOX_BIN"]
    elif "TOOLBOX_ROOT" in inherited:
        toolbox = inherited["TOOLBOX_ROOT"] + "/bin/toolbox" if inherited["TOOLBOX_ROOT"] else ""
    else:
        toolbox = shutil.which("toolbox", path=path)
        if not toolbox:
            toolbox = next(
                (
                    p.format(**substitutions)
                    for p in config["discovery"]["toolbox"]
                    if os.path.isfile(p.format(**substitutions))
                    and os.access(p.format(**substitutions), os.X_OK)
                ),
                "",
            )
    result["TOOLBOX_BIN"] = toolbox
    if "TOOLBOX_STATE" in inherited:
        result["TOOLBOX_STATE"] = inherited["TOOLBOX_STATE"]
    elif toolbox and Path(toolbox).is_file():
        installation = Path(toolbox).resolve().parent.parent
        if (installation / "toolbox").is_dir():
            result["TOOLBOX_STATE"] = str(installation / ".local/state")
    if "EC_RUSTSEC_DB" in inherited:
        database = inherited["EC_RUSTSEC_DB"]
    else:
        database = cargo + "/advisory-db"
        for pattern in config["discovery"]["rustsec"]:
            candidates = sorted(
                p for p in glob.glob(pattern.format(**substitutions)) if (Path(p) / ".git").exists()
            )
            # Ambiguidade é explícita: nenhuma seleção por sorte/ordem do filesystem.
            if len(candidates) == 1:
                database = candidates[0]
                break
            if len(candidates) > 1:
                break
    result["EC_RUSTSEC_DB"] = database
    channel = inherited.get("EC_RUST_TOOLCHAIN", inherited.get("RUSTUP_TOOLCHAIN"))
    if channel is None:
        executable = shutil.which("rustup", path=path)
        channel = ""
        if executable:
            try:
                process = subprocess.run(  # rustup local, comando literal e ambiente limitado.
                    [executable, "show", "active-toolchain"],
                    cwd=root,
                    env=dict(result, RUSTUP_AUTO_INSTALL="0"),
                    capture_output=True,
                    text=True,
                    timeout=config["runtime"]["probe_timeout"],
                    check=False,
                )
            except (OSError, subprocess.TimeoutExpired):
                print(
                    "aviso: descoberta Rustup falhou; consumidores Rust identificarão o recurso ausente",
                    file=sys.stderr,
                )
            else:
                if process.returncode == 0 and process.stdout.strip():
                    channel = process.stdout.split()[0]
    result["EC_RUST_TOOLCHAIN"] = channel
    if channel:
        result["RUSTUP_TOOLCHAIN"] = channel
    for key, default in (
        ("EC_CODEQL_RUSTUP_HOME", rustup),
        ("EC_CODEQL_CARGO_HOME", cargo),
        ("EC_CODEQL_CARGO_BIN", cargo_bin),
    ):
        result[key] = inherited.get(key, default)
    for key in config["environment"]["forward"]:
        if key in inherited:
            result[key] = inherited[key]
    return result


def codeql_projects(config):
    return [
        dict(
            name=name,
            source=component["root"],
            language=component["language"],
            suite=component["suite"],
        )
        for name, component in config["components"].items()
    ] + config["tools"]["codeql"]["additional_projects"]


def verify_catalogs(config, root, tool):
    records = config["tools"][tool]["catalogs"]
    for catalog in records:
        folder = root / catalog["root"]
        if not folder.is_dir() or folder.is_symlink():
            raise ValueError("catálogo de regras ausente ou simbólico")
        files = sorted(
            (path for path in folder.rglob("*") if path.is_file() or path.is_symlink()),
            key=lambda path: path.relative_to(folder).as_posix(),
        )
        digest = hashlib.sha256()
        for path in files:
            if path.is_symlink():
                raise ValueError("symlink em catálogo de regras")
            digest.update(
                path.relative_to(folder).as_posix().encode()
                + b"\0"
                + hashlib.sha256(path.read_bytes()).hexdigest().encode()
                + b"\n"
            )
        if len(files) != catalog["file_count"] or digest.hexdigest() != catalog["tree_sha256"]:
            raise ValueError("catálogo de regras alterado desde a importação revisada")
    return records


def select_files(config, names, scope):
    """Seleção compartilhada pelos consumidores e pelo inventário declarativo."""
    inputs = config["inputs"]
    if scope == "dependencies":
        return [component["lockfile"] for component in config["components"].values()]
    if scope == "python":
        return sorted(
            n
            for n in names
            if n.endswith(".py") and n.startswith(tuple(p + "/" for p in inputs["python_roots"]))
        )
    if scope == "shell":
        return sorted(n for n in names if n.endswith((".sh", ".bash")))
    if scope == "sast":
        bases = [c["root"] for c in config["components"].values()] + inputs["python_roots"]
        # Regras e positivos do próprio scanner são verificados por sast-rules.
        rules = config["policies"]["opengrep"] + "/"
        return sorted(
            n
            for n in names
            if n.startswith(tuple(p + "/" for p in bases))
            and not n.startswith(rules)
            and (
                PurePosixPath(n).suffix in inputs["sast_extensions"]
                or PurePosixPath(n).name == "Dockerfile"
            )
        )
    if scope == "application":
        return sorted(n for n in names if n.startswith("application/"))
    return sorted(names)


def source_files(config, root, scope):
    inputs = config["inputs"]
    if scope in ("trivy", "inventory"):
        scope = "all"
    if scope == "dependencies":
        return [c["lockfile"] for c in config["components"].values()]
    bases = inputs["roots"]

    def inaccessible(error):
        raise error

    names = []
    for base in bases:
        parts = PurePosixPath(base).parts
        linked = next(
            (
                PurePosixPath(*parts[:index])
                for index in range(1, len(parts) + 1)
                if (root / PurePosixPath(*parts[:index])).is_symlink()
            ),
            None,
        )
        if linked is not None:
            # O consumidor rejeita o link; não atravessar a raiz antes de validá-la.
            names.append(linked.as_posix())
            continue
        try:
            mode = (root / base).stat().st_mode
        except FileNotFoundError:
            # Raízes sem conteúdo podem não existir; erros de acesso são diferentes.
            continue
        if not stat.S_ISDIR(mode):
            raise ValueError("raiz do inventário não é diretório: " + base)
        for directory, directories, files in os.walk(
            root / base, followlinks=False, onerror=inaccessible
        ):
            parent = Path(directory)
            for name in list(directories) + files:
                path = parent / name
                relative = path.relative_to(root).as_posix()
                excluded = (
                    any(fnmatch.fnmatchcase(name, pattern) for pattern in inputs["exclude_names"])
                    or relative in inputs["exclude_paths"]
                )
                if name in directories:
                    if excluded or path.is_symlink():
                        directories.remove(name)
                    if not path.is_symlink():
                        continue
                if excluded:
                    continue
                names.append(relative)
    if scope == "all":
        names.extend(inputs["files"])
        # Dotenv da raiz é opcional; não copiar segredos de diretórios externos.
        for pattern in inputs["root_patterns"]:
            names.extend(
                path.name for path in root.glob(pattern) if path.is_file() or path.is_symlink()
            )
    return select_files(config, set(names), scope)


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("operation", choices=("plan", "environment", "inputs", "get"))
    parser.add_argument("key", nargs="?")
    parser.add_argument("--root", type=Path, default=CODE)
    parser.add_argument("--surface", action="append", default=[])
    parser.add_argument("--check", action="append", default=[])
    parser.add_argument("--scope", default="default")
    args = parser.parse_args()
    try:
        config = load_config()
        if args.operation == "plan":
            print(
                json.dumps(
                    plan(
                        config, args.root.resolve(strict=True), args.surface, args.check, args.scope
                    )
                )
            )
        elif args.operation == "environment":
            for key, value in environment(config, args.root, os.environ).items():
                sys.stdout.buffer.write(os.fsencode(key + "=" + value) + b"\0")
        elif args.operation == "inputs":
            for name in source_files(config, args.root, args.key or "all"):
                sys.stdout.buffer.write(os.fsencode(name) + b"\0")
        else:
            value = config
            for part in (args.key or "").split("."):
                value = value[part]
            print(json.dumps(value))
    except (OSError, ValueError, KeyError, TypeError, subprocess.SubprocessError) as error:
        print("error: configuração da automação: " + str(error), file=sys.stderr)
        return 2
    return 0


if __name__ == "__main__":
    sys.exit(main())
