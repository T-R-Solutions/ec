"""Testes dos contratos shell com fixtures descartáveis e oráculos explícitos."""
import datetime as dt
import fcntl
import importlib.util
import json
import os
from pathlib import Path
import shutil
import signal
import subprocess
import tempfile
import time
import unittest

ROOT = Path(__file__).resolve().parents[2]
SCRIPTS = ROOT / 'scripts'


class ShellTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory(prefix='ec shell with spaces ')
        self.root = Path(self.temp.name)
        (self.root / 'README.md').write_text('input')
        (self.root / 'AGENTS.md').write_text('fixture')
        self.env = dict(os.environ, PYTHONDONTWRITEBYTECODE='1')
        for key in ('EC_ROOT', 'EC_TASK_DIR', 'EC_E2E_ARTIFACT_DIR'):
            self.env.pop(key, None)

    def tearDown(self):
        self.temp.cleanup()

    def shell(self, script, *args, cwd=None, env=None):
        return subprocess.run(['bash', str(SCRIPTS / script), *map(str, args)],  # noqa: S603, S607 — Bash do PATH confiável; script versionado ou programa literal, argumentos separados, sem interpolação em código shell.
                              cwd=cwd or self.root, env=env or self.env,
                              capture_output=True, timeout=30)

    def write_json(self, name, data):
        path = self.root / name
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_text(json.dumps(data))
        return path

    def task(self, name, command, depends=None, timeout=5):
        return dict(project='scripts', name=name, argv=['bash', '-c', command],
                    cwd='.', depends=depends or [], timeout=timeout)

    def start_tasks(self, tasks):
        plan = self.write_json('plan.json', {'schema': 1, 'tasks': tasks, 'surfaces': ['scripts']})
        harness = '''set -euo pipefail
umask 077
CODE=$1 ROOT=$2
source "$CODE/scripts/stacks/ec/common.sh"
source "$CODE/scripts/stacks/ec/inputs.sh"
source "$CODE/scripts/stacks/ec/runtime.sh"
PLAN=$(cat "$3")
run_plan
'''
        return subprocess.Popen(['bash', '-c', harness, 'fixture', str(ROOT), str(self.root), str(plan)],  # noqa: S603, S607 — Bash do PATH confiável; programa harness literal, caminhos passados como parâmetros posicionais.
                                cwd=self.root, env=self.env, stdout=subprocess.PIPE, stderr=subprocess.PIPE)

    def run_tasks(self, tasks):
        process = self.start_tasks(tasks)
        out, err = process.communicate(timeout=30)
        dirs = list((self.root / '.local/automation/runs').glob('run-*'))
        directory = max(dirs, key=lambda p: p.stat().st_mtime_ns)
        doc = json.loads((directory / 'results.json').read_text())
        return process.returncode, directory, doc, out, err

    def test_plan_default_and_real_workspaces(self):
        result = self.shell('automate.sh', 'plan', cwd=self.root)
        self.assertEqual(result.returncode, 0, result.stderr)
        doc = json.loads(result.stdout)
        self.assertFalse(doc['executed'])
        self.assertEqual(len(doc['surfaces']), 8)
        rust = [t for t in doc['tasks'] if t['project'] in ('server', 'agent')]
        self.assertEqual({t['cwd'] for t in rust}, {'application/agents', 'application/server/backend'})
        self.assertEqual({t['name'] for t in rust}, {'fmt', 'clippy', 'test', 'machete', 'deny', 'audit', 'geiger', 'coverage', 'harness-build'})
        for project in ('server', 'agent'):
            self.assertEqual([t['name'] for t in rust if t['project'] == project],
                             ['fmt', 'clippy', 'test', 'machete', 'deny', 'audit', 'geiger', 'coverage', 'harness-build'])
        self.assertTrue(next(t for t in doc['tasks'] if t['project'] == 'e2e')['depends'])

    def test_selective_security_keeps_rules_and_governance(self):
        result = self.shell('automate.sh', 'plan', '--check', 'sast')
        self.assertEqual(result.returncode, 0, result.stderr)
        tasks = json.loads(result.stdout)['tasks']
        self.assertEqual([t['name'] for t in tasks if t['project'] == 'security'], ['sast-rules', 'sast'])
        self.assertEqual(len([t for t in tasks if t['project'] == 'docs']), 5)

    def test_optional_checks_are_reachable(self):
        for name in ('machete', 'deny', 'audit', 'geiger', 'lint', 'build-budget',
                     'performance', 'dev', 'setup', 'trivy', 'codeql', 'secrets-rules', 'ruff', 'shellcheck', 'playwright'):
            with self.subTest(name=name):
                result = self.shell('automate.sh', 'plan', '--check', name)
                self.assertEqual(result.returncode, 0, result.stderr)
                self.assertTrue(any(t['name'] == name for t in json.loads(result.stdout)['tasks']))
        result = self.shell('automate.sh', 'plan', '--surface', 'agent', '--check', 'coverage')
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertEqual([t['name'] for t in json.loads(result.stdout)['tasks'] if t['project'] == 'agent'], ['coverage'])

    def test_invalid_cli_arguments(self):
        for args in ((), ('bad',), ('plan', '--root'), ('plan', '--surface', 'nope'),
                     ('plan', '--check', 'nope'), ('plan', '--check', 'coverage'),
                     ('plan', '--surface', 'docs', '--check', 'sast')):
            with self.subTest(args=args):
                self.assertEqual(self.shell('automate.sh', *args).returncode, 2)

    def test_plan_spaced_root_without_execution(self):
        result = self.shell('automate.sh', 'plan', '--root', self.root, '--surface', 'scripts', cwd=self.root)
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertEqual(json.loads(result.stdout)['root'], str(self.root))
        self.assertFalse((self.root / '.local').exists())

    def test_raw_bytes_exit_and_continuation(self):
        code, directory, doc, _, err = self.run_tasks([
            self.task('fail', "printf 'raw\\0\\377'; printf diagnostic >&2; exit 17"),
            self.task('next', 'true')])
        self.assertEqual(code, 2, err)
        self.assertEqual([r['returncode'] for r in doc['results']], [17, 0])
        self.assertEqual((directory / '00-scripts-fail/stdout.log').read_bytes(), b'raw\x00\xff')
        self.assertEqual((directory / '00-scripts-fail/stderr.log').read_bytes(), b'diagnostic')
        self.assertFalse(doc['complete'])

    def test_missing_tool_and_dependency_block(self):
        code, _, doc, _, err = self.run_tasks([
            self.task('missing', 'exec ec-nonexistent-tool'),
            self.task('next', 'touch forbidden', ['scripts:missing'])])
        self.assertEqual(code, 3, err)
        self.assertEqual([r['state'] for r in doc['results']], ['blocked', 'blocked'])
        self.assertFalse((self.root / 'forbidden').exists())

    def test_timeout_retains_partial_logs(self):
        code, directory, doc, _, err = self.run_tasks([self.task('slow', 'echo started; sleep 10', timeout=1)])
        self.assertEqual(code, 2, err)
        self.assertEqual(doc['results'][0]['reason'], 'timeout')
        self.assertEqual((directory / '00-scripts-slow/stdout.log').read_text(), 'started\n')

    def test_private_runs_and_retention(self):
        code, first, _, _, err = self.run_tasks([self.task('ok', 'echo evidence')])
        original = (first / 'results.json').read_bytes()
        code2, second, doc, _, _ = self.run_tasks([self.task('ok', 'true')])
        self.assertEqual((code, code2), (0, 0), err)
        self.assertTrue(doc['complete'])
        self.assertNotEqual(first, second)
        self.assertEqual((first / 'results.json').read_bytes(), original)
        self.assertEqual(first.stat().st_mode & 0o777, 0o700)
        self.assertEqual((first / 'results.json').stat().st_mode & 0o777, 0o600)

    def test_input_drift_rejects_success(self):
        code, _, doc, _, err = self.run_tasks([self.task('mutate', 'echo changed > README.md')])
        self.assertEqual(code, 2, err)
        self.assertIn('README.md', doc['changed_inputs'])

    def test_symlink_and_traversal_rejected(self):
        (self.root / 'scripts').symlink_to(self.root.parent, target_is_directory=True)
        process = self.start_tasks([self.task('ok', 'true')])
        _, err = process.communicate(timeout=10)
        self.assertEqual(process.returncode, 2, err)
        result = subprocess.run(['bash', '-c', 'source "$1"; inside "$2" ../outside',  # noqa: S603, S607 — Bash do PATH confiável; script versionado ou programa literal, argumentos separados, sem interpolação em código shell.
                                 'test', str(SCRIPTS / 'stacks/ec/common.sh'), str(self.root)], capture_output=True)
        self.assertEqual(result.returncode, 2)

    def test_lock_prevents_execution(self):
        directory = self.root / '.local/automation'
        directory.mkdir(parents=True)
        with (directory / 'workspace.lock').open('w') as stream:
            fcntl.flock(stream, fcntl.LOCK_EX | fcntl.LOCK_NB)
            process = self.start_tasks([self.task('ok', 'touch forbidden')])
            _, err = process.communicate(timeout=10)
            self.assertEqual(process.returncode, 3, err)
        self.assertFalse((self.root / 'forbidden').exists())

    def test_cancellation_stops_group_and_later_tasks(self):
        process = self.start_tasks([self.task('slow', 'echo started; sleep 20'), self.task('next', 'touch forbidden')])
        deadline = time.monotonic() + 10
        while time.monotonic() < deadline:
            logs = list((self.root / '.local/automation/runs').glob('*/00-*/stdout.log'))
            if logs and b'started' in logs[0].read_bytes():
                break
            time.sleep(0.03)
        process.send_signal(signal.SIGTERM)
        _, err = process.communicate(timeout=10)
        self.assertEqual(process.returncode, 2, err)
        self.assertFalse((self.root / 'forbidden').exists())

    def test_runner_death_releases_lock_while_workload_exists(self):
        process = self.start_tasks([self.task('slow', 'echo $$ > child.pid; exec sleep 20')])
        child_file = self.root / 'child.pid'
        deadline = time.monotonic() + 10
        while not child_file.exists() and time.monotonic() < deadline:
            time.sleep(0.03)
        self.assertTrue(child_file.exists())
        child = int(child_file.read_text())
        process.kill()
        process.wait(timeout=5)
        try:
            with (self.root / '.local/automation/workspace.lock').open('w') as stream:
                deadline = time.monotonic() + 2
                while True:
                    try:
                        fcntl.flock(stream, fcntl.LOCK_EX | fcntl.LOCK_NB)
                        break
                    except BlockingIOError:
                        if time.monotonic() >= deadline:
                            raise
                        time.sleep(0.02)
                os.kill(child, 0)  # O workload ainda existe; não reteve o lock.
        finally:
            try:
                os.killpg(os.getpgid(child), signal.SIGTERM)
            except ProcessLookupError:
                pass
            process.communicate(timeout=5)

    def test_log_overflow_fails(self):
        code, _, doc, _, err = self.run_tasks([self.task('large', 'head -c 9000000 /dev/zero')])
        self.assertEqual(code, 2, err)
        self.assertIn('log', doc['results'][0]['reason'])

    def test_log_limit_does_not_limit_workload_files(self):
        code, _, doc, _, err = self.run_tasks([self.task('file', 'head -c 18000000 /dev/zero > workload.bin')])
        self.assertEqual(code, 0, err)
        self.assertTrue(doc['complete'])
        self.assertEqual((self.root / 'workload.bin').stat().st_size, 18000000)

    def test_environment_does_not_leak_tokens(self):
        self.env['EC_TEST_SECRET_TOKEN'] = os.urandom(16).hex()
        code, _, _, _, err = self.run_tasks([self.task('env', 'test -z "${EC_TEST_SECRET_TOKEN:-}"; test "$CARGO_NET_OFFLINE" = true')])
        self.assertEqual(code, 0, err)

    def parse_report(self, kind, data, rc, expected=None):
        report = self.write_json('report.json', data)
        inputs = self.write_json('inputs.json', expected or {'a.rs': 'hash'})
        return self.shell('stacks/ec/security/check-report.sh', kind, report, rc, inputs, '/snapshot')

    def test_secrets_report_exit_consistency(self):
        finding = {'RuleID': 'fixture', 'File': '/project/a', 'StartLine': 1, 'Secret': '***'}
        for data, rc, wanted in (([], 0, 0), ([finding], 10, 0), ([], 10, 2), ([{}], 0, 2), ({}, 0, 2), ([], 7, 2)):
            with self.subTest(data=data, rc=rc):
                self.assertEqual(self.parse_report('secrets', data, rc).returncode, wanted)

    def test_sast_requires_complete_unsuppressed_inventory(self):
        good = {'results': [], 'errors': [], 'paths': {'scanned': ['/snapshot/a.rs']}}
        result = self.parse_report('sast', good, 0)
        self.assertEqual(result.returncode, 0, result.stderr)
        variants = [dict(good, errors=[{}]), dict(good, paths={'scanned': []}),
                    dict(good, paths={'scanned': ['/snapshot/a.rs'], 'skipped': ['x']}),
                    dict(good, paths={'scanned': ['/elsewhere/a.rs']}),
                    dict(good, paths={'scanned': ['../a.rs']}),
                    dict(good, results=[{'check_id': 'x', 'extra': {'is_ignored': True}}])]
        for data in variants:
            with self.subTest(data=data):
                self.assertEqual(self.parse_report('sast', data, 0).returncode, 2)

    def test_osv_requires_all_identified_lockfiles(self):
        expected = {name: 'hash' for name in ('a', 'b', 'c')}
        good = {'results': [{'source': {'type': 'lockfile', 'path': '/snapshot/' + name},
                            'packages': [{'package': {'name': 'fixture', 'version': '1', 'ecosystem': 'npm'}}]}
                           for name in expected]}
        result = self.parse_report('dependencies', good, 0, expected)
        self.assertEqual(result.returncode, 0, result.stderr)
        for change in ('missing', 'duplicate', 'empty', 'suppressed', 'identity', 'exit'):
            data = json.loads(json.dumps(good))
            if change == 'missing': data['results'].pop()
            elif change == 'duplicate': data['results'].append(data['results'][0])
            elif change == 'empty': data['results'][0]['packages'] = []
            elif change == 'suppressed': data['results'][0]['experimental_pes'] = [{}]
            elif change == 'identity': del data['results'][0]['packages'][0]['package']['name']
            with self.subTest(change=change):
                self.assertEqual(self.parse_report('dependencies', data, 1 if change == 'exit' else 0, expected).returncode, 2)

    def test_empty_rule_test_and_sarif_fail_closed(self):
        for kind, doc in [('sast-rules', {'config_missing_tests': [], 'config_with_errors': [], 'results': {}}),
                          ('sarif', {'version': '2.1.0', 'runs': [{'tool': {}, 'results': []}]})]:
            self.assertEqual(self.parse_report(kind, doc, 0).returncode, 2)


    def test_exception_metadata_and_expiry(self):
        today = dt.datetime.now(dt.timezone.utc).date()
        row = dict(id='one', tool='osv', finding='x', component='server', owner='person',
                   reason='reviewed', reviewed=str(today), expires=str(today + dt.timedelta(days=1)), control='test')
        path = self.write_json('exceptions.json', [row])
        result = self.shell('stacks/ec/security/validate-exceptions.sh', path)
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertFalse(json.loads(result.stdout)['applied'])
        for rows in ([row, row], [dict(row, expires=str(today))], [dict(row, owner='')]):
            self.write_json('exceptions.json', rows)
            self.assertEqual(self.shell('stacks/ec/security/validate-exceptions.sh', path).returncode, 2)

    def test_budget_measures_actual_bytes_and_gzip(self):
        (self.root / 'dist').mkdir()
        (self.root / 'dist/a.js').write_bytes(b'x' * 100)
        path = self.write_json('budget.json', {'directory': 'dist', 'maximum_bytes': 100})
        result = self.shell('stacks/ec/metrics/check.sh', 'budget', path)
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertEqual(json.loads(result.stdout)['total_bytes'], 100)
        self.write_json('budget.json', {'directory': 'dist', 'maximum_bytes': 99})
        self.assertEqual(self.shell('stacks/ec/metrics/check.sh', 'budget', path).returncode, 1)
        (self.root / 'dist/link').symlink_to('/etc/passwd')
        self.assertEqual(self.shell('stacks/ec/metrics/check.sh', 'budget', path).returncode, 2)

    def test_performance_uses_nearest_rank_p95(self):
        self.write_json('samples.json', {'samples': list(range(1, 21))})
        path = self.write_json('performance.json', {'report': 'samples.json', 'p95_limit': 19, 'maximum': 20})
        result = self.shell('stacks/ec/metrics/check.sh', 'performance', path)
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertEqual(json.loads(result.stdout)['p95'], 19)
        self.write_json('samples.json', {'samples': []})
        self.assertNotEqual(self.shell('stacks/ec/metrics/check.sh', 'performance', path).returncode, 0)

    def test_lcov_validates_records_and_threshold(self):
        report = self.root / 'coverage.lcov'
        report.write_text('SF:a.rs\nLF:10\nLH:8\nFNF:2\nFNH:1\nend_of_record\n')
        path = self.write_json('coverage.json', {'report': 'coverage.lcov', 'format': 'lcov', 'minimum': {'lines': 80}})
        result = self.shell('stacks/ec/metrics/check.sh', 'coverage', path)
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertEqual(json.loads(result.stdout)['metrics']['lines']['actual'], 80)
        report.write_text('SF:a.rs\nLF:10\nLH:8\n')
        self.assertEqual(self.shell('stacks/ec/metrics/check.sh', 'coverage', path).returncode, 2)

    def test_eslint_ratchet_and_errors(self):
        baseline = self.write_json('baseline.json', [{'file': 'a.ts', 'rule': 'complexity', 'maximum': 1}])
        report = self.write_json('lint.json', [{'filePath': 'a.ts', 'messages': [{'ruleId': 'complexity', 'severity': 1}]}])
        result = self.shell('stacks/frontend/eslint-ratchet.sh', report, baseline)
        self.assertEqual(result.returncode, 0, result.stderr)
        self.write_json('lint.json', [{'filePath': 'a.ts', 'messages': [{'ruleId': 'complexity', 'severity': 2}]}])
        self.assertEqual(self.shell('stacks/frontend/eslint-ratchet.sh', report, baseline).returncode, 1)

    def test_summary_escapes_markdown_and_does_not_mutate(self):
        path = self.write_json('results.json', {'schema': 1, 'results': [dict(project='x|y', task='a', state='blocked', returncode=None, reason='<tag>\nnext')]})
        original = path.read_bytes()
        result = self.shell('stacks/ec/reports/summarize.sh', path)
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertIn(b'x\\|y', result.stdout)
        self.assertIn(b'&lt;tag&gt; next', result.stdout)
        self.assertEqual(path.read_bytes(), original)

    def test_shell_syntax(self):
        self.assertEqual(self.shell('check.sh').returncode, 0)


    def fake_tool(self, name, body):
        directory = self.root / 'bin'
        directory.mkdir(exist_ok=True)
        path = directory / name
        path.write_text('#!/usr/bin/env bash\nset -euo pipefail\n' + body)
        path.chmod(0o700)
        self.env['PATH'] = str(directory) + os.pathsep + os.environ['PATH']
        return path

    def test_rust_invocation_flags_and_missing_policy(self):
        self.env['EC_ROOT'] = str(self.root)
        (self.root / 'Cargo.toml').write_text('[workspace]\n')
        (self.root / 'Cargo.lock').write_text('# fixture')
        (self.root / 'rust-toolchain.toml').write_text('[toolchain]\nchannel = "1.99.1"\n')
        self.fake_tool('rustup', '''
if [[ ${*: -1} == --version ]]; then echo 'fixture 1.0.0'; exit 0; fi
printf '%s\n' "$@" | jq -R . | jq -s .
''')
        for name in ('fmt', 'clippy', 'test', 'harness-build', 'machete', 'audit', 'deny'):
            if name in ('machete', 'audit', 'deny'):
                self.fake_tool('cargo-' + name, 'exit 0\n')
            if name == 'audit':
                (self.root / 'cargo/advisory-db/.git').mkdir(parents=True)
                self.env['EC_RUSTSEC_DB'] = str(self.root / 'cargo/advisory-db')
                self.fake_tool('git', 'date +%s\n')
                (self.root / 'scripts/config/native/cargo-audit').mkdir(parents=True, exist_ok=True)
                (self.root / 'scripts/config/native/cargo-audit/audit.toml').write_text('[database]\nfetch=false\n')
                self.env['CARGO_HOME'] = str(self.root / 'cargo')
            if name == 'deny':
                self.assertEqual(self.shell('stacks/rust/check.sh', name).returncode, 3)
                (self.root / 'scripts/config/native/cargo-deny').mkdir(parents=True, exist_ok=True)
                (self.root / 'scripts/config/native/cargo-deny/deny.toml').write_text('# reviewed fixture')
            result = self.shell('stacks/rust/check.sh', name)
            self.assertEqual(result.returncode, 0, (name, result.stderr))
            args = json.loads(result.stdout)
            self.assertEqual(args[:4], ['run', '1.99.1', 'cargo', 'build' if name == 'harness-build' else name])
            if name in ('test', 'clippy', 'harness-build'):
                self.assertIn('--offline', args)
                self.assertIn('--locked', args)
            if name == 'audit': self.assertIn('--no-fetch', args)

    def test_frontend_actual_npm_scripts_and_missing_script(self):
        self.write_json('package.json', {'scripts': {'test': 'vitest run'}})
        self.write_json('package-lock.json', {})
        self.write_json('node_modules/.package-lock.json', {})
        self.fake_tool('npm', 'printf "%s\\n" "$@" | jq -R . | jq -s .\n')
        result = self.shell('stacks/frontend/check.sh', 'test')
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertEqual(json.loads(result.stdout), ['run', 'test'])
        self.assertEqual(self.shell('stacks/frontend/check.sh', 'coverage').returncode, 3)
        self.assertEqual(self.shell('stacks/frontend/check.sh', 'performance').returncode, 3)
        result = self.shell('stacks/frontend/check.sh', 'setup')
        self.assertEqual(json.loads(result.stdout), ['ci', '--ignore-scripts'])

    def test_geiger_virtual_workspace_scans_each_member_and_preserves_failure(self):
        self.env.update(EC_ROOT=str(self.root), EC_TASK_DIR=str(self.root / 'task'))
        (self.root / 'task').mkdir()
        (self.root / 'Cargo.toml').write_text('[workspace]\n')
        (self.root / 'Cargo.lock').write_text('# fixture\n')
        (self.root / 'rust-toolchain.toml').write_text('[toolchain]\nchannel = "1.99.1"\n')
        # O segundo membro não precisa ser dependência do primeiro.
        manifests = [str(self.root / 'crates' / name / 'Cargo.toml') for name in ('app', 'independent')]
        metadata = {'workspace_members': ['app', 'independent'], 'packages': [
            {'id': name, 'manifest_path': path} for name, path in zip(('app', 'independent'), manifests)
        ]}
        self.write_json('metadata.json', metadata)
        self.fake_tool('cargo-geiger', 'exit 0\n')
        self.fake_tool('rustup', '''
if [[ ${*: -1} == --version ]]; then echo 'fixture 1.0.0'; exit 0; fi
if [[ $4 == metadata ]]; then cat "$EC_ROOT/metadata.json"; exit 0; fi
[[ $4 == geiger ]] || exit 9
printf '%s\\n' "$@" | jq -R . | jq -s . >> "$EC_ROOT/invocations.json"
[[ ${*: -1} != *independent/Cargo.toml || ! -f $EC_ROOT/fail-second ]] || exit 17
[[ ! -f $EC_ROOT/empty-report ]] || exit 0
echo 'unsafe inventory fixture'
''')
        result = self.shell('stacks/rust/check.sh', 'geiger')
        self.assertEqual(result.returncode, 0, result.stderr)
        decoder = json.JSONDecoder()
        calls = (self.root / 'invocations.json').read_text()
        for manifest in manifests:
            args, end = decoder.raw_decode(calls.lstrip())
            calls = calls.lstrip()[end:]
            self.assertEqual(args[:4], ['run', '1.99.1', 'cargo', 'geiger'])
            self.assertEqual(args[-2:], ['--manifest-path', manifest])
            for flag in ('--locked', '--offline', '--all-features'):
                self.assertIn(flag, args)
        self.assertFalse(calls.strip())
        (self.root / 'fail-second').touch()
        result = self.shell('stacks/rust/check.sh', 'geiger')
        self.assertEqual(result.returncode, 2, result.stderr)
        self.assertEqual((self.root / 'task/geiger-0-exit.txt').read_text(), '0\n')
        self.assertEqual((self.root / 'task/geiger-1-exit.txt').read_text(), '17\n')
        (self.root / 'fail-second').unlink()
        (self.root / 'empty-report').touch()
        result = self.shell('stacks/rust/check.sh', 'geiger')
        self.assertEqual(result.returncode, 2, result.stderr)
        self.assertIn(b'invent\xc3\xa1rio Geiger vazio', result.stderr)
        self.write_json('metadata.json', {'workspace_members': [], 'packages': []})
        result = self.shell('stacks/rust/check.sh', 'geiger')
        self.assertEqual(result.returncode, 2, result.stderr)
        self.assertIn(b'metadata Geiger', result.stderr)


    def test_http_harness_rejects_nonlocal_paths_before_connection(self):
        import importlib.util
        from unittest.mock import patch
        spec = importlib.util.spec_from_file_location('http_fixture', ROOT / 'application/integration/tests/integration/publication_http.py')
        module = importlib.util.module_from_spec(spec)
        spec.loader.exec_module(module)
        with patch.object(module.http.client, 'HTTPConnection') as connection:
            for path in ('file:///etc/passwd', 'https://example.invalid', '//example.invalid', '/a\r\nHost: elsewhere'):
                with self.assertRaises(ValueError):
                    module.call('GET', path)
            connection.assert_not_called()

    def test_sql_harness_passes_values_separately_and_rejects_variable_names(self):
        import importlib.util
        from unittest.mock import patch
        spec = importlib.util.spec_from_file_location('sql_fixture', ROOT / 'application/integration/tests/integration/publication_http.py')
        module = importlib.util.module_from_spec(spec)
        spec.loader.exec_module(module)
        with patch.object(module.subprocess, 'run') as run:
            with self.assertRaises(ValueError):
                module.sql('SELECT 1', **{'bad;name': 'x'})
            run.assert_not_called()
            value = "'; DROP TABLE revisions; --"
            module.sql("SELECT :'value'", value=value)
            self.assertEqual(run.call_args.kwargs['input'], "SELECT :'value'")
            self.assertIn('value=' + value, run.call_args.args[0])
            self.assertNotIn('shell', run.call_args.kwargs)

    def test_sast_rule_oracle_requires_every_positive_annotation(self):
        import re
        rules = {rule for path in (SCRIPTS / 'rules/opengrep').iterdir()
                 for rule in re.findall(r'ruleid:\s*(ec\.[\w.-]+)', path.read_text())}
        checks = {rule: {'passed': True, 'matches': {'fixture': {'expected_lines': [1], 'reported_lines': [1]}}}
                  for rule in rules}
        report = {'config_missing_tests': [], 'config_with_errors': [], 'results': {'fixture': {'checks': checks}}}
        result = self.parse_report('sast-rules', report, 0)
        self.assertEqual(result.returncode, 0, result.stderr)
        checks.pop(next(iter(checks)))
        self.assertEqual(self.parse_report('sast-rules', report, 0).returncode, 2)

    def test_trivy_and_codeql_reports(self):
        report, inputs = self.trivy_report()
        report['Results'][0]['Vulnerabilities'] = [{'VulnerabilityID': 'X'}]
        result = self.parse_report('trivy', report, 1, inputs)
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertEqual(json.loads(result.stdout)['state'], 'findings')
        self.assertEqual(self.parse_report('trivy', report, 0, inputs).returncode, 2)
        sarif = {'version': '2.1.0', 'runs': [{
            'tool': {'driver': {'name': 'CodeQL', 'rules': [{'id': 'fixture'}]}},
            'invocations': [{'executionSuccessful': True}], 'results': [{'ruleId': 'fixture'}],
        }]}
        result = self.parse_report('sarif', sarif, 0)
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertEqual(json.loads(result.stdout)['state'], 'findings')

    def test_sources_and_final_layout(self):
        self.assertFalse((ROOT / 'scripts_erase').exists())
        for path in SCRIPTS.rglob('*'):
            if path.suffix == '.py':
                self.assertIn('tests', path.relative_to(SCRIPTS).parts)
            if 'rules' not in path.relative_to(SCRIPTS).parts:
                self.assertNotIn(path.suffix, ('.ts', '.ps1'))

    def test_additional_toolbox_checks_are_selectable_without_changing_defaults(self):
        optional = {'grype', 'bandit', 'syft', 'yara-x'}
        result = self.shell('automate.sh', 'plan')
        self.assertEqual(result.returncode, 0, result.stderr)
        names = {task['name'] for task in json.loads(result.stdout)['tasks']}
        self.assertTrue(optional.isdisjoint(names))
        for check in sorted(optional):
            result = self.shell('automate.sh', 'plan', '--check', check)
            self.assertEqual(result.returncode, 0, result.stderr)
            tasks = [task for task in json.loads(result.stdout)['tasks']
                     if task['project'] != 'docs']
            self.assertEqual(len(tasks), 1)
            self.assertEqual(tasks[0]['name'], check)
            self.assertEqual(tasks[0]['project'], 'security')

    def test_toolbox_override_survives_clean_environment_including_empty(self):
        for value in ('/trusted path/toolbox', ''):
            self.env['TOOLBOX_BIN'] = value
            code, directory, _, _, err = self.run_tasks([
                self.task('override', 'test "${TOOLBOX_BIN+x}" = x && printf "%s" "$TOOLBOX_BIN"')])
            self.assertEqual(code, 0, err)
            self.assertEqual((directory / '00-scripts-override/stdout.log').read_text(), value)

    def test_selinux_policy_survives_clean_environment_including_empty(self):
        for value in ('shared', 'private', ''):
            self.env['TOOLBOX_SELINUX_LABEL'] = value
            code, directory, _, _, err = self.run_tasks([
                self.task('selinux', 'test "${TOOLBOX_SELINUX_LABEL+x}" = x && printf "%s" "$TOOLBOX_SELINUX_LABEL"')])
            self.assertEqual(code, 0, err)
            self.assertEqual((directory / '00-scripts-selinux/stdout.log').read_text(), value)

    def test_toolbox_state_and_docker_selection_survive_clean_environment(self):
        for key in ('TOOLBOX_STATE', 'DOCKER_HOST', 'DOCKER_CONTEXT', 'DOCKER_CONFIG',
                    'DOCKER_TLS_VERIFY', 'DOCKER_CERT_PATH'):
            with self.subTest(key=key):
                value = 'selected value ' + key
                self.env[key] = value
                code, directory, _, _, err = self.run_tasks([
                    self.task('selection', 'printf "%s" "$' + key + '"')])
                self.assertEqual(code, 0, err)
                self.assertEqual((directory / '00-scripts-selection/stdout.log').read_text(), value)

    def test_new_configuration_is_bound_to_input_manifest(self):
        self.write_json('scripts/config/native/codeql/projects.json', {'schema': 1})
        code, _, doc, _, err = self.run_tasks([
            self.task('mutate-tool-policy', "printf '{}' > scripts/config/native/codeql/projects.json")])
        self.assertEqual(code, 2, err)
        self.assertIn('scripts/config/native/codeql/projects.json', doc['changed_inputs'])

    def trivy_report(self):
        locks = ['application/agents/Cargo.lock', 'application/server/backend/Cargo.lock',
                 'application/server/frontend/package-lock.json']
        return ({'SchemaVersion': 2, 'ArtifactName': 'app', 'Results': [
            {'Target': '/snapshot/' + path, 'Packages': [{'Name': 'fixture'}]} for path in locks
        ]}, dict.fromkeys(locks, 'hash'))

    def test_trivy_relative_inventory_and_escaping_paths(self):
        report, inputs = self.trivy_report()
        for item in report['Results']:
            item['Target'] = item['Target'].removeprefix('/snapshot/application/')
        self.assertEqual(self.parse_report('trivy', report, 0, inputs).returncode, 0)
        for invalid in ('../agents/Cargo.lock', '/elsewhere/agents/Cargo.lock'):
            report['Results'][0]['Target'] = invalid
            self.assertEqual(self.parse_report('trivy', report, 0, inputs).returncode, 2)

    def test_trivy_secrets_and_misconfigurations_cannot_pass_as_zero_vulns(self):
        report, inputs = self.trivy_report()
        report['Results'].append({'Target': 'config', 'Secrets': [{'RuleID': 'fixture'}],
                                 'Misconfigurations': [{'ID': 'fixture', 'Status': 'FAIL'}]})
        result = self.parse_report('trivy', report, 1, inputs)
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertEqual(json.loads(result.stdout)['finding_count'], 2)
        self.assertEqual(self.parse_report('trivy', report, 0, inputs).returncode, 2)
        report['Results'][0]['ExperimentalModifiedFindings'] = [{'Status': 'ignored'}]
        self.assertEqual(self.parse_report('trivy', report, 1, inputs).returncode, 2)


if __name__ == '__main__':
    unittest.main()
