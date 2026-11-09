"""Políticas Ec sobre comandos nativos da Toolbox; sem instalação de ferramentas.

automate.sh continua sendo o coordenador. Este módulo executa somente um gate,
preserva os códigos nativos e separa diagnóstico operacional de achados.
"""
import datetime as dt
import hashlib
import json
import os
from pathlib import Path, PurePosixPath
import re
import shutil
import signal
import stat
import subprocess
import sys
import time
from urllib.parse import unquote, urlsplit

CODE = Path(__file__).resolve().parents[2]
LOCKS = ('application/agents/Cargo.lock', 'application/server/backend/Cargo.lock',
         'application/server/frontend/package-lock.json')
CHECKS = ('ruff', 'shellcheck', 'secrets', 'secrets-rules', 'sast', 'sast-rules',
          'dependencies', 'trivy', 'codeql', 'playwright', 'grype', 'bandit', 'syft', 'yara-x')
CONFIG = 'scripts/config/native/'


class Rejected(Exception):
    """Falha de contrato ou de política, com mensagem local sem saída da ferramenta."""


class Blocked(Rejected):
    """Pré-requisito operacional ausente."""


def require(condition, message):
    if not condition:
        raise Rejected(message)


def read_json(path):
    def unique(pairs):
        value = {}
        for key, item in pairs:
            require(key not in value, 'propriedade JSON duplicada')
            value[key] = item
        return value
    require(path.is_file() and not path.is_symlink(), 'relatório regular ausente')
    require(path.stat().st_size <= 128 * 1024 * 1024, 'relatório excede 128 MiB')
    return json.loads(path.read_text(), object_pairs_hook=unique,
                      parse_constant=lambda _: (_ for _ in ()).throw(Rejected('JSON não finito')))


def write_json(path, value):
    temporary = path.with_suffix(path.suffix + '.tmp')
    temporary.write_text(json.dumps(value, indent=2, ensure_ascii=False) + '\n')
    temporary.chmod(0o600)
    temporary.replace(path)


def regular(root, relative):
    path = PurePosixPath(relative)
    require(relative and not path.is_absolute() and '..' not in path.parts,
            'alvo deve ser relativo e interno')
    current = root
    for part in path.parts:
        current = current / part
        require(not current.is_symlink(), 'symlink não permitido nas entradas')
    require(current.is_file(), 'alvo obrigatório ausente ou não regular')
    return current


def identity(path):
    return {'sha256': hashlib.sha256(path.read_bytes()).hexdigest(),
            'mode': stat.S_IMODE(path.stat().st_mode)}


def inventory(root, scope='all'):
    # Programa literal; caminhos são argumentos, nunca texto de código shell.
    result = subprocess.run(  # noqa: S603 — Biblioteca versionada e argv separado.
        ['bash', '-c', 'set -euo pipefail; source "$1"; ROOT=$2; source_files "$3"',  # noqa: S607
         'inventory', str(CODE / 'scripts/stacks/ec/inputs.sh'), str(root), scope],
        capture_output=True, check=True)
    names = sorted(set(os.fsdecode(p) for p in result.stdout.split(b'\0') if p))
    require(names, 'inventário obrigatório vazio')
    return {name: identity(regular(root, name)) for name in names}


def relative(path):
    require(isinstance(path, str), 'caminho ausente do relatório')
    path = path.removeprefix('/project/').removeprefix('./')
    require(not path.startswith('/') and '..' not in PurePosixPath(path).parts,
            'relatório refere fonte fora do projeto')
    return path


def fresh(timestamp, maximum=86400):
    require(isinstance(timestamp, str), 'timestamp da base ausente')
    date = dt.datetime.fromisoformat(timestamp.replace('Z', '+00:00'))
    require(date.tzinfo is not None, 'timestamp da base sem timezone')
    age = (dt.datetime.now(dt.timezone.utc) - date).total_seconds()
    require(0 <= age <= maximum, 'base futura ou expirada')
    return date


def database_metadata(value, tool):
    """Proveniência pública dos bytes montados; não usa a data de preparação."""
    data = value.get('database_metadata')
    require(isinstance(data, dict) and type(data.get('schema')) is int
            and data['schema'] == 1 and data.get('snapshot') == value['database']
            and data.get('timestamp_semantics') == 'oldest_required_source',
            'proveniência da base ausente ou divergente do diagnóstico')
    sources = data.get('sources')
    require(isinstance(sources, list) and len(sources) == 2
            and all(isinstance(source, dict) for source in sources),
            'fontes obrigatórias da base ausentes')
    if tool == 'osv-scanner':
        expected = {'https://osv-vulnerabilities.storage.googleapis.com/' + ecosystem + '/all.zip'
                    for ecosystem in ('crates.io', 'npm')}
        semantics = 'upstream_object_generation_created'
        extra = ('generation',)
    else:
        expected = {'https://github.com/aquasecurity/trivy-db',
                    'https://github.com/aquasecurity/trivy-java-db'}
        semantics = 'native_database_updated_at'
        extra = ('metadata_file',)
    require({source.get('url') for source in sources} == expected,
            'inventário de fontes da base incompleto ou inesperado')
    dates = []
    for source in sources:
        require(source.get('timestamp_semantics') == semantics
                and re.fullmatch(r'[0-9a-f]{64}', source.get('sha256', '')) is not None,
                'semântica ou hash upstream da base inválido')
        if tool == 'osv-scanner':
            require(re.fullmatch(r'[0-9]+', source.get('generation', '')) is not None,
                    'geração upstream OSV ausente')
        else:
            filename = ('java-db' if source['url'].endswith('trivy-java-db') else 'db') + '/metadata.json'
            require(source.get('metadata_file') == filename, 'metadado nativo Trivy divergente')
        dates.append(fresh(source.get('data_timestamp')))
    require(fresh(data.get('data_timestamp')) == min(dates),
            'timestamp agregado não representa a fonte obrigatória mais antiga')
    # Persistir somente campos públicos conhecidos, sem propriedades adicionais.
    return {**{key: data[key] for key in ('schema', 'snapshot', 'data_timestamp', 'timestamp_semantics')},
            'sources': [{key: source[key] for key in
                         ('url', 'sha256', 'data_timestamp', 'timestamp_semantics', *extra)}
                        for source in sources]}


def trivy_checks(value):
    """Exigir o bundle externo e a montagem observada pelo executor central."""
    checks = value.get('checks')
    require(isinstance(checks, dict)
            and re.fullmatch(r'[0-9a-f]{64}', checks.get('checks', '')) is not None
            and checks.get('image') == value['image'] and checks.get('version') == value['version']
            and checks.get('origin') == 'ghcr.io/aquasecurity/trivy-checks:2',
            'Checks Bundle Trivy ausente ou incompatível com a imagem')
    native = checks.get('native', {})
    require(isinstance(native, dict) and native.get('MajorVersion') == 2
            and native.get('CustomBuild', False) is False
            and re.fullmatch(r'sha256:[0-9a-f]{64}', native.get('Digest', '')) is not None,
            'identidade nativa do Checks Bundle Trivy inválida')
    stamp = dt.datetime.fromisoformat(native['DownloadedAt'].replace('Z', '+00:00'))
    require(stamp.tzinfo is not None and stamp <= dt.datetime.now(dt.timezone.utc),
            'timestamp do Checks Bundle Trivy inválido')
    runtime = value.get('checks_runtime', {})
    mount = runtime.get('mount', {})
    require(runtime.get('network') == 'none' and mount.get('Destination') == '/cache/policy'
            and mount.get('RW') is False and mount.get('Type') == 'bind',
            'Checks Bundle Trivy sem mount ro e rede none observados')


def diagnostic(value, tool, code):
    require(value.get('tool') == tool, 'diagnóstico de outra ferramenta')
    outcome = value.get('outcome')
    require(outcome in ('native', 'infrastructure', 'timeout', 'cancelled'),
            'outcome desconhecido')
    if outcome == 'infrastructure':
        raise Blocked('infraestrutura Toolbox indisponível; consultar diagnóstico')
    require(outcome == 'native', 'executor interrompido por timeout ou cancelamento')
    require(type(value.get('native_exit_code')) is int and value['native_exit_code'] == code,
            'exit nativo divergente do retorno')
    require(value.get('cleanup') == 'removed' and value.get('oom_killed') is False,
            'limpeza incompleta ou processo encerrado por falta de memória')
    require(re.fullmatch(r'sha256:[0-9a-f]{64}', value.get('image', '')) is not None
            and isinstance(value.get('version'), str) and value['version'],
            'imagem/versão ausente do diagnóstico')
    if tool in ('trivy', 'grype', 'osv-scanner'):
        if not value.get('database'):
            raise Blocked('base central ausente; preparo explícito pelo operador necessário')
        require(re.fullmatch(r'[0-9a-f]{64}', value.get('database', '')) is not None,
                'base central não identificada')
    if tool in ('trivy', 'osv-scanner'):
        return database_metadata(value, tool)


def basic_report(tool, data, code, targets):
    """Oráculos nativos dos checks que não usam o parser de segurança existente."""
    count = 0
    require(isinstance(data, list if tool == 'ruff' else dict), 'formato de relatório inválido')
    if tool == 'ruff':
        require(code in (0, 1) and isinstance(data, list), 'relatório/exit Ruff inválido')
        for item in data:
            require(item.get('code') and relative(item.get('filename')) in targets
                    and isinstance(item.get('location'), dict), 'achado Ruff incompleto')
        count = len(data)
    elif tool == 'shellcheck':
        require(code in (0, 1) and isinstance(data.get('comments'), list),
                'relatório/exit ShellCheck inválido')
        for item in data['comments']:
            require(relative(item.get('file')) in targets and type(item.get('code')) is int
                    and item.get('level') in ('error', 'warning', 'info', 'style'),
                    'achado ShellCheck incompleto')
        count = len(data['comments'])
    elif tool == 'bandit':
        require(code in (0, 1) and data.get('errors') == []
                and isinstance(data.get('results'), list), 'Bandit parcial ou inválido')
        metrics = data.get('metrics', {})
        require(sorted(relative(p) for p in metrics if p != '_totals') == sorted(targets),
                'Bandit omitiu fontes')
        require(all(m.get('nosec', 0) == 0 and m.get('skipped_tests', 0) == 0
                    for m in metrics.values()), 'Bandit suprimiu checks')
        for item in data['results']:
            require(relative(item.get('filename')) in targets and item.get('test_id')
                    and item.get('issue_severity') in ('LOW', 'MEDIUM', 'HIGH'),
                    'achado Bandit incompleto')
        count = len(data['results'])
    elif tool in ('grype', 'syft'):
        require(code == 0 and isinstance(data.get('artifacts'), list) and data['artifacts'],
                'inventário de pacotes ausente ou produtor falhou')
        require(all(isinstance(item.get('name'), str) and item['name']
                    and isinstance(item.get('version'), str) and item.get('type')
                    for item in data['artifacts']), 'pacote sem identidade no inventário')
        def location(path):
            # Syft/Grype podem registrar paths a partir da raiz selecionada.
            if path.startswith('/project/'):
                return relative(path)
            path = relative(path.lstrip('/'))
            return path if path.startswith('application/') else 'application/' + path
        locations = {location(loc['path']) for item in data['artifacts']
                     for loc in item.get('locations', [])}
        require(set(LOCKS) <= locations, 'inventário omitiu lockfiles obrigatórios')
        if tool == 'syft':
            return {'state': 'measured', 'finding_count': None,
                    'artifact_count': len(data['artifacts']), 'measurement': True}
        require(isinstance(data.get('matches'), list) and data.get('ignoredMatches', []) == [],
                'Grype inválido ou com supressões')
        require(data['descriptor']['db'].get('error') is None, 'Grype registrou erro de base')
        for match in data['matches']:
            require(match.get('vulnerability', {}).get('id'), 'achado Grype incompleto')
        status = data['descriptor']['db'].get('status', data['descriptor']['db'])
        require(status.get('valid', True) is True, 'base Grype inválida')
        fresh(status['built'])
        count = len(data['matches'])
    elif tool == 'playwright':
        require(code in (0, 1) and data.get('errors') == [], 'Playwright parcial ou inválido')
        stats = data['stats']
        require(all(type(stats.get(k)) is int and stats[k] >= 0
                    for k in ('expected', 'unexpected', 'skipped', 'flaky')),
                'contadores Playwright inválidos')
        require(stats['expected'] + stats['unexpected'] == 2 and stats['skipped'] == 0
                and stats['flaky'] == 0, 'testes Playwright omitidos, flaky ou ignorados')
        def specs(suites):
            return [s for suite in suites for s in suite.get('specs', [])] + [
                s for suite in suites for s in specs(suite.get('suites', []))]
        cases = specs(data['suites'])
        tests = [t for s in cases for t in s['tests']]
        require(len(cases) == len(tests) == 2 and len({s['title'] for s in cases}) == 2,
                'inventário Playwright incompleto ou duplicado')
        for test in tests:
            require(test['expectedStatus'] == 'passed' and test['status'] in ('expected', 'unexpected')
                    and len(test['results']) == 1
                    and test['results'][0]['status'] in ('passed', 'failed', 'timedOut'),
                    'resultado Playwright não aprovado')
        count = stats['unexpected']
    else:
        raise Rejected('parser nativo desconhecido')
    if tool in ('ruff', 'shellcheck', 'bandit', 'playwright'):
        require((code == 1) == (count > 0), 'exit e achados inconsistentes')
    return {'state': 'findings' if count else 'passed', 'finding_count': count}


class Gate:
    def __init__(self, check, root, task):
        self.check, self.root, self.task = check, root, task
        self.snapshot = task / 'sources'
        self.steps = []
        self.process = None
        self.cancelled = False
        self.cancel_signal = signal.SIGTERM
        self.deadline = time.monotonic() + int(os.environ.get('EC_TASK_TIMEOUT', '900')) - 120

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
            require(identity(dest) == observed, 'fonte mudou durante o snapshot')
        write_json(self.task / 'sources.json', self.before)
        # Arquivos vazios explícitos impedem descoberta de ignores pessoais.
        (self.snapshot / '.gitleaksignore').write_text('')
        (self.snapshot / '.semgrepignore').write_text('')
        (self.snapshot / '.trivyignore').write_text('')
        (self.snapshot / '.bandit').write_text('[bandit]\n')

    def targets(self, kind):
        names = self.before.keys()
        if kind == 'python':
            selected = [n for n in names if n.endswith('.py') and n.startswith(
                ('scripts/tests/', 'tools/', 'sdd/tools/', 'application/integration/tests/'))]
        elif kind == 'shell':
            selected = [n for n in names if n.startswith('scripts/') and n.endswith('.sh')]
        elif kind == 'sast':
            selected = [n for n in names if n.startswith(('application/agents/',
                        'application/server/backend/', 'application/server/frontend/'))
                        and n.endswith(('.rs', '.ts', '.vue'))]
            require(all(any(n.startswith(base + '/') for n in selected) for base in (
                'application/agents', 'application/server/backend', 'application/server/frontend')),
                'SAST requer os três componentes')
        elif kind == 'dependencies':
            selected = list(LOCKS)
        elif kind == 'application':
            selected = [n for n in names if n.startswith('application/')]
        else:
            selected = list(names)
        require(selected and all(n in self.before for n in selected),
                'alvos obrigatórios ausentes ou vazios')
        write_json(self.task / 'targets.json', selected)
        return selected

    def run(self, tool, argv, *, project=None, options=(), timeout=600):
        require(not self.cancelled, 'execução cancelada')
        timeout = min(timeout, int(self.deadline - time.monotonic()))
        require(timeout > 0, 'prazo total do gate esgotado')
        step = self.task / f'{len(self.steps):02d}-{tool}'
        step.mkdir(mode=0o700)
        results = step / 'results'
        results.mkdir(mode=0o700)
        command = ['bash', str(CODE / 'scripts/toolbox.sh'), 'exec', '--tool', tool,
                   '--project-root', str(project or self.snapshot), '--results', str(results),
                   '--network', 'none', '--timeout', str(timeout),
                   '--diagnostic-file', str(step / 'diagnostic.json'), *options, '--', *argv]
        # Não transportar ambiente inteiro nem registrar argv/valores no resumo.
        env = {k: os.environ[k] for k in ('PATH', 'HOME', 'LANG', 'LC_ALL', 'TOOLBOX_BIN',
               'TOOLBOX_STATE', 'DOCKER_HOST', 'DOCKER_CONTEXT', 'DOCKER_CONFIG',
               'DOCKER_TLS_VERIFY', 'DOCKER_CERT_PATH', 'TOOLBOX_SELINUX_LABEL') if k in os.environ}
        env.update(NO_COLOR='1', PYTHONDONTWRITEBYTECODE='1')
        with (step / 'stdout.log').open('xb') as out, (step / 'stderr.log').open('xb') as err:
            self.process = subprocess.Popen(command, env=env, stdin=subprocess.DEVNULL,  # noqa: S603
                                            stdout=out, stderr=err, start_new_session=True)
            if self.cancelled:
                self.process.send_signal(self.cancel_signal)
            interruption = None
            try:
                deadline = time.monotonic() + timeout + 120
                while self.process.poll() is None:
                    if time.monotonic() >= deadline or sum(
                            p.stat().st_size for p in (step / 'stdout.log', step / 'stderr.log')) > 128 * 1024 * 1024:
                        raise subprocess.TimeoutExpired(command, timeout)
                    time.sleep(0.05)
                code = self.process.returncode
            except subprocess.TimeoutExpired:
                interruption = 'executor excedeu prazo ou limite de logs; consultar diagnóstico de limpeza'
                self.process.terminate()
                try:
                    self.process.wait(timeout=120)
                except subprocess.TimeoutExpired:
                    self.process.kill()
                    self.process.wait()
                code = self.process.returncode
            finally:
                self.process = None
        value = read_json(step / 'diagnostic.json') if (step / 'diagnostic.json').exists() else {}
        record = {'step': step.name, 'tool': tool, 'returncode': code,
                  'outcome': value.get('outcome'), 'native_exit_code': value.get('native_exit_code'),
                  'image': value.get('image'), 'version': value.get('version'),
                  'database': value.get('database'), 'cleanup': value.get('cleanup'),
                  'checks': value.get('checks', {}).get('checks') if isinstance(value.get('checks'), dict) else None}
        self.steps.append(record)
        write_json(step / 'exit.json', record)
        write_json(self.task / 'native-executions.json', self.steps)
        require(interruption is None, interruption)
        require(bool(value), 'diagnóstico ausente do executor; origem não determinada, consultar streams')
        provenance = diagnostic(value, tool, code)
        if provenance is not None:
            write_json(self.task / (tool + '-database.json'), {
                'tool': tool, 'image': value['image'], 'version': value['version'], **provenance})
        if tool == 'trivy' and argv[0] != 'version':
            trivy_checks(value)
        require(not self.cancelled, 'execução cancelada após término nativo')
        # Uma sequência não mistura versões/bases após troca central concorrente.
        for previous in self.steps[:-1]:
            if previous['tool'] == tool:
                require(all(previous[k] == record[k] for k in ('image', 'version', 'database')),
                        'identidade central mudou entre etapas; repetir o gate')
                if previous['checks'] is not None and record['checks'] is not None:
                    require(previous['checks'] == record['checks'],
                            'Checks Bundle central mudou entre etapas; repetir o gate')
        return step, code

    def parse(self, kind, report, code, targets=()):
        inputs = self.task / 'expected.json'
        write_json(inputs, {n: self.before.get(n, {}) for n in targets})
        result = subprocess.run(  # noqa: S603 — Parser versionado; nenhum input como código.
            ['bash', str(CODE / 'scripts/stacks/ec/security/check-report.sh'),  # noqa: S607
             kind, str(report), str(code), str(inputs), '/project'], capture_output=True)
        require(result.returncode == 0, 'relatório de segurança inválido, parcial ou inconsistente')
        return json.loads(result.stdout)

    def secrets(self, project=None):
        step, code = self.run('gitleaks', ['dir', '--config', '/project/' + CONFIG + 'gitleaks/gitleaks.toml',
            '--ignore-gitleaks-allow', '--gitleaks-ignore-path', '/project/.gitleaksignore',
            '--redact=100', '--no-banner', '--no-color', '--exit-code', '10',
            '--report-format', 'json', '--report-path', '/out/native.json', '/project'], project=project)
        report = step / 'results/native.json'
        data = read_json(report)
        require(isinstance(data, list) and all(isinstance(item, dict) for item in data),
                'relatório Gitleaks inválido')
        # Além da redação nativa, persistir somente identificação/localização.
        write_json(report, [{k: item[k] for k in ('RuleID', 'File', 'StartLine', 'EndLine',
                                                  'StartColumn', 'EndColumn') if k in item}
                            for item in data])
        return step, code

    def execute(self):
        check = self.check
        if check == 'codeql':
            return self.codeql()
        if check == 'grype':
            return self.grype()
        if check == 'secrets-rules':
            return self.secret_rules()
        if check == 'yara-x':
            return self.yara()
        tool = {'secrets': 'gitleaks', 'sast': 'opengrep', 'sast-rules': 'opengrep',
                'dependencies': 'osv-scanner'}.get(check, check)
        if check in ('ruff', 'shellcheck', 'bandit'):
            targets = self.targets('shell' if check == 'shellcheck' else 'python')
        elif check == 'sast':
            targets = self.targets('sast')
        elif check == 'dependencies':
            targets = self.targets('dependencies')
        else:
            targets = self.targets('application' if check in ('trivy', 'grype', 'syft') else 'all')
        native_targets = ['/project/' + n for n in targets]
        if check == 'ruff':
            argv = ['check', '--no-fix', '--no-respect-gitignore', '--output-format', 'json',
                    '--config', '/project/' + CONFIG + 'ruff/ruff.toml', *native_targets]
        elif check == 'shellcheck':
            argv = ['--format=json1', '--rcfile=/project/' + CONFIG + 'shellcheck/shellcheckrc',
                    '--', *native_targets]
        elif check == 'bandit':
            argv = ['--ignore-nosec', '--ini', '/project/.bandit', '-c',
                    '/project/' + CONFIG + 'bandit/bandit.yaml', '-f', 'json', '-o',
                    '/out/native.json', *native_targets]
        elif check == 'secrets':
            step, code = self.secrets()
            return self.parse('secrets', step / 'results/native.json', code)
        elif check in ('sast', 'sast-rules'):
            argv = ['scan', '--json', '--strict', '--disable-version-check', '--no-rewrite-rule-ids',
                    '--config', '/project/scripts/rules/opengrep']
            if check == 'sast-rules':
                argv += ['--test', '/project/scripts/rules/opengrep']
            else:
                argv += ['--error', '--disable-nosem', '--no-git-ignore', '--max-target-bytes', '0',
                         *native_targets]
        elif check == 'dependencies':
            argv = ['scan', 'source', '--offline', '--all-packages', '--format', 'json',
                    '--config', '/project/' + CONFIG + 'osv-scanner/osv-scanner.toml',
                    '--output-file', '/out/native.json']
            for name in native_targets:
                argv += ['--lockfile', name]
        elif check == 'trivy':
            # Rejeitar comentários de supressão: não há flag para ignorá-los no modo misconfig.
            for name in targets:
                require(not re.search(rb'(?i)\b(?:trivy|avd|tfsec):ignore',
                                      (self.snapshot / name).read_bytes()),
                        'supressão inline Trivy exige revisão explícita')
            argv = ['filesystem', '--quiet', '--config', '/project/' + CONFIG + 'trivy/trivy.yaml',
                    '--scanners', 'vuln,misconfig,secret', '--ignorefile', '/project/.trivyignore',
                    '--format', 'json', '--output', '/out/native.json', '--exit-code', '1',
                    '--list-all-pkgs', '--include-dev-deps', '--include-non-failures',
                    '--skip-db-update', '--skip-java-db-update', '--skip-check-update',
                    '--offline-scan', '/project/application']
            self.trivy_rules(argv)
        elif check == 'grype':
            argv = ['dir:/project/application', '--config', '/project/' + CONFIG + 'grype/grype.yaml',
                    '--output', 'json']
        elif check == 'syft':
            argv = ['scan', 'dir:/project/application', '--output', 'syft-json']
        elif check == 'playwright':
            self.copy_build()
            argv = ['test', '--config=/project/' + CONFIG + 'playwright/playwright.config.mjs']
        else:
            raise Rejected('check desconhecido')
        step, code = self.run(tool, argv)
        report = step / 'results/native.json'
        if check in ('ruff', 'shellcheck', 'sast', 'sast-rules', 'grype', 'syft'):
            report = step / 'stdout.log'
        if check in ('sast', 'sast-rules', 'dependencies', 'trivy'):
            if check == 'trivy':
                self.redact_trivy(report)
            return self.parse(check, report, code, targets)
        return basic_report(tool, read_json(report), code, targets)

    def grype(self):
        # O JSON Grype contém matches, não o inventário completo. Analisar o
        # SBOM Syft previamente validado vincula cobertura e vulnerabilidades.
        targets = self.targets('application')
        inventory_step, inventory_code = self.run(
            'syft', ['scan', 'dir:/project/application', '--output', 'syft-json'])
        inventory_path = inventory_step / 'stdout.log'
        inventory = read_json(inventory_path)
        basic_report('syft', inventory, inventory_code, targets)
        before = identity(inventory_path)
        step, code = self.run('grype', [
            'sbom:/inventory.json', '--config', '/project/' + CONFIG + 'grype/grype.yaml',
            '--output', 'json'], options=['--mount', str(inventory_path) + ':/inventory.json:ro'])
        require(identity(inventory_path) == before, 'inventário Grype mudou durante análise')
        report = read_json(step / 'stdout.log')
        write_json(self.task / 'grype-inventory.json', {
            'input': str(inventory_path.relative_to(self.task)), 'identity': before,
            'artifact_count': len(inventory['artifacts'])})
        return basic_report('grype', {**report, 'artifacts': inventory['artifacts']}, code, targets)

    def trivy_rules(self, argv):
        version, code = self.run('trivy', ['version', '--format', 'json'])
        require(code == 0, 'não foi possível inspecionar a base Trivy')
        database = read_json(version / 'stdout.log')
        # UpdatedAt/NextUpdate são upstream: preparo recente não renova base velha.
        for key in ('VulnerabilityDB', 'JavaDB'):
            item = database[key]
            fresh(item['UpdatedAt'])
            require(dt.datetime.fromisoformat(item['NextUpdate'].replace('Z', '+00:00'))
                    > dt.datetime.now(dt.timezone.utc), 'base Trivy além de NextUpdate')
        write_json(self.task / 'trivy-native-databases.json', database)
        for label in ('positive', 'negative'):
            fixture = self.task / ('trivy-fixture-' + label)
            config = CONFIG + 'trivy/trivy.yaml'
            (fixture / config).parent.mkdir(parents=True)
            shutil.copy2(self.snapshot / config, fixture / config)
            (fixture / '.trivyignore').write_text('')
            app = fixture / 'application'
            app.mkdir()
            (app / 'public.txt').write_text('ordinary public text\n')
            if label == 'positive':
                (app / 'Dockerfile').write_text('FROM alpine:3.20\nUSER root\n')
                (app / 'secret.txt').write_text('token = "ghp_' + 'Abcdef0123456789' * 2 + 'Abcdef"\n')
                write_json(app / 'package-lock.json', {'name': 'fixture', 'version': '1.0.0',
                    'lockfileVersion': 3, 'packages': {'': {'name': 'fixture', 'version': '1.0.0'},
                    'node_modules/lodash': {'version': '4.17.20'}}})
            step, code = self.run('trivy', argv, project=fixture)
            self.redact_trivy(step / 'results/native.json')
            report = read_json(step / 'results/native.json')
            require(report.get('SchemaVersion') == 2 and isinstance(report.get('Results'), list),
                    'relatório de eficácia Trivy inválido')
            counts = [sum(len(r.get('Vulnerabilities', [])) for r in report['Results']),
                      sum(len(r.get('Secrets', [])) for r in report['Results']),
                      sum(sum(m.get('Status') == 'FAIL' for m in r.get('Misconfigurations', []))
                          for r in report['Results'])]
            require((code == 1 and all(counts)) if label == 'positive' else (code == 0 and not any(counts)),
                    'fixture Trivy não comprovou vuln/misconfig/secret ou negativo limpo')

    def redact_trivy(self, report):
        result = subprocess.run(  # noqa: S603 — Filtro jq versionado.
            ['jq', '-f', str(CODE / 'scripts/stacks/ec/security/redact-trivy.jq'), str(report)],  # noqa: S607
            capture_output=True)
        require(result.returncode == 0, 'relatório Trivy inválido ou suprimido')
        write_json(report, json.loads(result.stdout))

    def copy_build(self):
        build = self.root / 'application/server/frontend/dist'
        if not build.is_dir() or not (build / 'index.html').is_file():
            raise Blocked('build estático frontend necessário antes do Playwright')
        regular(self.root, 'application/server/frontend/dist/index.html')
        observed = {}
        for path in sorted(build.rglob('*')):
            require(not path.is_symlink(), 'symlink no build Playwright')
            if path.is_file():
                name = str(path.relative_to(self.root))
                observed[name] = identity(path)
                dest = self.snapshot / name
                dest.parent.mkdir(parents=True, exist_ok=True)
                shutil.copy2(path, dest)
                require(identity(dest) == observed[name], 'build mudou durante cópia')
        self.build_before = observed
        write_json(self.task / 'build.json', observed)

    def secret_rules(self):
        for name, positive in (('positive', True), ('negative', False), ('allowlist', False),
                               ('allowlist-boundary', True)):
            fixture = self.task / ('fixture-' + name)
            config = CONFIG + 'gitleaks/gitleaks.toml'
            (fixture / config).parent.mkdir(parents=True)
            shutil.copy2(self.snapshot / config, fixture / config)
            (fixture / '.gitleaksignore').write_text('')
            if name.startswith('allowlist'):
                sample = fixture / ('config/governance/profile.json' if name == 'allowlist' else 'sample.txt')
                sample.parent.mkdir(parents=True, exist_ok=True)
                sample.write_text('  "references/api-key.md": "'
                                  + hashlib.sha256(b'ec-public-rule-fixture').hexdigest() + '"\n')
            else:
                token = 'ghp_' + 'Abcdef0123456789' * 2 + 'Abcdef'
                (fixture / 'sample.txt').write_text(
                    ('token = "' + token + '" # gitleaks:allow\n') if positive else 'public text\n')
            step, code = self.secrets(fixture)
            parsed = self.parse('secrets', step / 'results/native.json', code)
            require((parsed['finding_count'] > 0) == positive, 'regra Gitleaks sem eficácia')
            if positive:
                require(all(relative(item['File']) == 'sample.txt'
                            for item in read_json(step / 'results/native.json')),
                        'fixture Gitleaks detectou fonte inesperada')
        return {'state': 'passed', 'finding_count': 0, 'effectiveness': True}

    def yara(self):
        rule = '/project/scripts/rules/yara-x/ec.yar'
        text = (self.snapshot / 'scripts/rules/yara-x/ec.yar').read_text()
        require('rule Ec_Eicar_Test_File' in text and not re.search(r'\binclude\s*"', text),
                'regras YARA vazias ou include sem revisão')
        # Cada arquivo explícito produz evidência de execução, mesmo sem achados.
        # Formato textual padrão; nenhum flag JSON presumido para versão inativa.
        targets = self.targets('application')
        for label, content in (('positive', 'EICAR-' + 'STANDARD-ANTIVIRUS-TEST-FILE'),
                               ('negative', 'ordinary public text')):
            path = self.snapshot / (label + '.fixture')
            path.write_text(content)
            step, code = self.run('yara-x', ['scan', rule, '/project/' + path.name])
            require(code == 0, 'erro operacional YARA-X')
            found = (step / 'stdout.log').read_text().strip()
            require(bool(found) == (label == 'positive')
                    and (not found or found.startswith('Ec_Eicar_Test_File ')),
                    'fixture YARA-X divergente')
        count = 0
        for name in targets:
            step, code = self.run('yara-x', ['scan', rule, '/project/' + name])
            require(code == 0 and not (step / 'stderr.log').read_bytes(), 'YARA-X parcial ou inválido')
            lines = (step / 'stdout.log').read_text().splitlines()
            require(all(line == 'Ec_Eicar_Test_File /project/' + name for line in lines),
                    'saída YARA-X desconhecida')
            count += len(lines)
        return {'state': 'findings' if count else 'passed', 'finding_count': count,
                'scope': 'somente sentinela EICAR', 'effectiveness': True}

    def codeql(self):
        projects = read_json(self.snapshot / (CONFIG + 'codeql/projects.json'))['projects']
        require([(p['name'], p['language']) for p in projects] == [
                ('server', 'rust'), ('agent', 'rust'), ('frontend', 'javascript-typescript')],
                'três alvos CodeQL obrigatórios')
        counts, outcomes = 0, []
        for project in projects:
            try:
                parsed = self.codeql_project(project)
                counts += parsed['finding_count']
                outcomes.append(dict(target=project['name'], **parsed))
            except (Blocked, Rejected) as error:
                outcomes.append({'target': project['name'], 'state': 'blocked' if isinstance(error, Blocked)
                                 else 'error', 'reason': str(error)})
            except (OSError, ValueError, KeyError, TypeError, AttributeError):
                outcomes.append({'target': project['name'], 'state': 'error',
                                 'reason': 'base ou relatório CodeQL inválido'})
        write_json(self.task / 'codeql-targets.json', outcomes)
        state = ('error' if any(p['state'] == 'error' for p in outcomes) else
                 'blocked' if any(p['state'] == 'blocked' for p in outcomes) else
                 'findings' if counts else 'passed')
        return {'state': state, 'finding_count': counts, 'target_results': outcomes,
                'targets': [p['name'] for p in projects]}

    def codeql_project(self, project):
        name, source = project['name'], project['source']
        require(source == {'server': 'application/server/backend', 'agent': 'application/agents',
                           'frontend': 'application/server/frontend'}[name], 'source CodeQL inválida')
        expected_suite = 'scripts/rules/codeql/' + (
            'codeql-rust.qls' if project['language'] == 'rust' else 'codeql.qls')
        require(project['suite'] == expected_suite, 'suite CodeQL divergente do alvo')
        targets = [n for n in self.before if n.startswith(source + '/') and n.endswith(
            ('.rs',) if project['language'] == 'rust' else ('.ts', '.vue', '.js', '.mjs'))]
        require(targets, 'alvo CodeQL vazio')
        suite = regular(self.snapshot, project['suite'])
        require('version:' not in suite.read_text(), 'packs CodeQL pertencem ao bundle central')
        options = ['--mount', str(self.task / 'codeql') + ':/ec-codeql:rw',
                   '--workdir', '/project/' + source]
        (self.task / 'codeql').mkdir(exist_ok=True)
        if project['language'] == 'rust':
            runtime = {}
            for key, dest in (('EC_CODEQL_RUSTUP_HOME', '/ec-rustup'),
                              ('EC_CODEQL_CARGO_HOME', '/ec-cargo'),
                              ('EC_CODEQL_CARGO_BIN', '/ec-cargo-bin')):
                if not os.environ.get(key) or not Path(os.environ[key]).is_dir():
                    raise Blocked('CodeQL Rust requer ' + key + ' preparado para Linux/amd64')
                runtime[key] = Path(os.environ[key]).resolve()
                require(runtime[key] not in (Path('/'), Path.home()), 'montagem Rust ampla demais')
                if key != 'EC_CODEQL_CARGO_HOME':
                    options += ['--mount', str(runtime[key]) + ':' + dest + ':ro']
            # Cargo precisa de locks/cache graváveis. Copiar só dependências preparadas;
            # não transportar config/credentials/tokens do host nem baixar pacotes.
            private_cargo = self.task / 'codeql' / ('cargo-' + name)
            private_cargo.mkdir()
            dependency_inputs = {}
            for folder in ('registry', 'git'):
                origin = runtime['EC_CODEQL_CARGO_HOME'] / folder
                if origin.exists():
                    require(not origin.is_symlink(), 'symlink no cache Cargo')
                    for path in origin.rglob('*'):
                        require(not path.is_symlink(), 'symlink não revisado nas dependências Cargo')
                        if path.is_file():
                            dependency_inputs[str(path.relative_to(runtime['EC_CODEQL_CARGO_HOME']))] = identity(path)
                    shutil.copytree(origin, private_cargo / folder)
            for path, observed in dependency_inputs.items():
                require(identity(private_cargo / path) == observed
                        and identity(runtime['EC_CODEQL_CARGO_HOME'] / path) == observed,
                        'dependência Cargo mudou durante cópia')
            write_json(self.task / ('codeql-' + name + '-dependencies.json'), dependency_inputs)
            options += ['--env', 'RUSTUP_HOME=/ec-rustup', '--env', 'CARGO_HOME=/ec-codeql/cargo-' + name,
                        '--env', 'RUSTUP_TOOLCHAIN=1.99.1', '--env', 'RUSTUP_AUTO_INSTALL=0',
                        '--env', 'CARGO_NET_OFFLINE=true', '--env', 'CARGO_TARGET_DIR=/ec-codeql/target-' + name,
                        '--env', 'PATH=/ec-cargo-bin:/usr/local/bin:/usr/bin:/bin']
        database = '/ec-codeql/' + name
        step, code = self.run('codeql', ['database', 'create', database,
            '--language=' + project['language'], '--source-root=/project/' + source,
            '--build-mode=none'], options=options, timeout=1800)
        require(code == 0, 'criação da base CodeQL falhou')
        info, code = self.run('codeql', ['resolve', 'database', '--format=json', database], options=options)
        status = read_json(info / 'stdout.log')
        # O resolver retorna metadados opacos; não inventar campo "finalised".
        # create finaliza a base e analyze rejeita bases não finalizadas.
        require(code == 0 and isinstance(status, dict) and status, 'base CodeQL não resolvida')
        step, code = self.run('codeql', ['database', 'analyze', database,
            '/project/' + project['suite'], '--format=sarifv2.1.0',
            '--output=/out/native.sarif', '--sarif-add-baseline-file-info',
            '--sarif-include-diagnostics', '--no-sarif-group-rules-by-pack',
            '--no-sarif-add-file-contents', '--no-sarif-add-snippets'], options=options, timeout=1800)
        report = read_json(step / 'results/native.sarif')
        def source_uri(value):
            uri = urlsplit(value)
            require(not uri.netloc and uri.scheme in ('', 'file'), 'URI SARIF externa')
            return relative(unquote(uri.path))
        artifacts = {source_uri(a['location']['uri']) for run in report['runs']
                     for a in run.get('artifacts', [])}
        # SARIF pode usar caminhos relativos ao source-root.
        covered = artifacts | {source + '/' + a for a in artifacts}
        require(set(targets) <= covered, 'CodeQL omitiu fontes do inventário')
        parsed = self.parse('sarif', step / 'results/native.sarif', code)
        return parsed



def main():
    os.umask(0o077)
    if len(sys.argv) != 2 or sys.argv[1] not in CHECKS:
        print('uso: scanner-gate.sh ' + '|'.join(CHECKS), file=sys.stderr)
        return 2
    root = Path(os.environ.get('EC_ROOT', CODE)).resolve(strict=True)
    if not os.environ.get('EC_TASK_DIR'):
        print('blocked: executar pelo automate.sh para preservar evidências', file=sys.stderr)
        return 3
    task = Path(os.environ['EC_TASK_DIR']).resolve(strict=True)
    gate = Gate(sys.argv[1], root, task)
    for sig in (signal.SIGTERM, signal.SIGINT, signal.SIGHUP):
        signal.signal(sig, gate.receive)
    result = {'state': 'error', 'finding_count': None}
    try:
        gate.prepare()
        result = gate.execute()
    except Blocked as error:
        result.update(state='blocked', reason=str(error))
    except Rejected as error:
        result.update(state='error', reason=str(error))
    except (OSError, ValueError, KeyError, TypeError, AttributeError, IndexError, subprocess.SubprocessError):
        result.update(state='error', reason='entrada, relatório ou execução inválida; consultar artefatos privados')
    finally:
        if hasattr(gate, 'before'):
            try:
                require(inventory(root) == gate.before, 'fontes alteradas durante execução')
                for name, observed in getattr(gate, 'build_before', {}).items():
                    require(identity(regular(root, name)) == observed, 'build alterado durante Playwright')
            except (OSError, Rejected, subprocess.SubprocessError):
                result.update(state='error', reason='integridade das fontes/build divergente')
        if gate.cancelled:
            result.update(state='error', reason='cancelado; nenhuma aprovação')
        result['native_executions'] = gate.steps
        write_json(task / 'classification.json', result)
    print(json.dumps(result, ensure_ascii=False))
    return {'passed': 0, 'measured': 0, 'findings': 1, 'error': 2, 'blocked': 3}[result['state']]


if __name__ == '__main__':
    sys.exit(main())
