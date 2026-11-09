"""Contrato Ec/exec. Executor fixture não comprova scanner, CodeQL ou browser real."""
import copy
import datetime as dt
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
SPEC = importlib.util.spec_from_file_location('ec_native', ROOT / 'tools/quality/native_gates.py')
native = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(native)

# Fronteira Toolbox simulada: recebe argv nativo e produz fixtures documentadas.
EXECUTOR = r'''
import datetime as dt
import json
import os
from pathlib import Path
import re
import signal
import sys
import time
a=sys.argv[1:]
assert 'PGPASSWORD' not in os.environ
def arg(flag): return a[a.index(flag)+1]
tool=arg('--tool')
root=Path(arg('--project-root'))
out=Path(arg('--results'))
argv=a[a.index('--')+1:]
diag=Path(arg('--diagnostic-file'))
out.joinpath('capture.json').write_text(json.dumps(a))
assert a[0]=='exec' and '--project-write' not in a
assert '--maintenance' not in a and arg('--network')=='none'
code=0
data=None
target=lambda p: '/project/'+str(p.relative_to(root))
if tool=='ruff': data=[]
elif tool=='shellcheck': data={'comments':[]}
elif tool=='bandit':
    paths=[x for x in argv if x.endswith('.py')]
    data={'results':[],'errors':[],'metrics':{p:{'nosec':0} for p in paths}}
elif tool=='gitleaks':
    data=[]
    sample=root/'sample.txt'
    if sample.exists() and any(token in sample.read_text() for token in ('ghp_', 'references/api-key.md')) and MODE!='ineffective':
        data=[{'RuleID':'github-pat','File':'/project/sample.txt','StartLine':1,'Secret':'***'}]
        code=10
elif tool=='opengrep':
    if '--test' in argv:
        ids=set()
        for p in root.joinpath('scripts/rules/opengrep').glob('*'):
            ids.update(re.findall(r'ruleid: *(ec.[a-zA-Z0-9_.-]+)',p.read_text()))
        data={'config_missing_tests':[],'config_with_errors':[],'results':{'fixture':{
            'checks':{key:{'passed':True,'matches':{'file':{'expected_lines':[2],'reported_lines':[2]}}}
                      for key in ids}}}}
    else:
        paths=[p for p in argv if p.endswith(('.rs','.ts','.vue'))]
        data={'results':[],'errors':[],'paths':{'scanned':paths[:-1] if MODE=='partial' else paths}}
elif tool=='osv-scanner':
    paths=[argv[i+1] for i,p in enumerate(argv) if p=='--lockfile']
    data={'results':[{'source':{'path':p,'type':'lockfile'},'packages':[{
        'package':{'name':'fixture','version':'1','ecosystem':'npm'}}]} for p in paths]}
elif tool=='trivy':
    if argv[0]=='version':
        stamp=dt.datetime.now(dt.timezone.utc)
        data={key:{'UpdatedAt':stamp.isoformat(),'NextUpdate':(stamp+dt.timedelta(hours=1)).isoformat()}
              for key in ('VulnerabilityDB','JavaDB')}
        if MODE=='expired': data['VulnerabilityDB']['UpdatedAt']='2000-01-01T00:00:00Z'
    else:
        data={'SchemaVersion':2,'ArtifactName':'/project/application','Results':[
            {'Target':'/project/'+p,'Packages':[{'Name':'fixture'}]} for p in LOCKS]}
        if root.name=='trivy-fixture-positive':
            data['Results'][0].update(Vulnerabilities=[{'VulnerabilityID':'fixture'}],
                                     Secrets=[{'RuleID':'fixture'}],
                                     Misconfigurations=[{'ID':'fixture','Status':'FAIL'}])
            code=1
elif tool in ('grype','syft'):
    data={'artifacts':[{'name':'fixture','version':'1','type':'npm','locations':[{'path':'/project/'+p}]} for p in LOCKS],
          'matches':[],'ignoredMatches':[],
          'descriptor':{'db':{'built':dt.datetime.now(dt.timezone.utc).isoformat()}}}
elif tool=='playwright':
    data={'errors':[],'stats':{'expected':2,'unexpected':0,'skipped':0,'flaky':0},
          'suites':[{'specs':[{'title':t,'tests':[{'expectedStatus':'passed','status':'expected',
                     'results':[{'status':'passed'}]}]} for t in ('one','two')]}]}
elif tool=='yara-x':
    if 'EICAR-STANDARD-ANTIVIRUS-TEST-FILE' in root.joinpath(argv[-1].removeprefix('/project/')).read_text():
        print('Ec_Eicar_Test_File '+argv[-1])
elif tool=='codeql':
    assert argv[:2] != ['database','info']
    if argv[:2]==['resolve','database']: data={} if MODE=='partial' else {'fixture_metadata':True}
    if argv[1]=='analyze':
        data={'version':'2.1.0','runs':[{'tool':{'driver':{'name':'CodeQL','rules':[{'id':'fixture'}]}},
            'invocations':[{'executionSuccessful':True}],'results':[],
            'artifacts':[{'location':{'uri':'/project/'+str(p.relative_to(root))}}
                         for p in root.joinpath('application').rglob('*')
                         if p.suffix in ('.rs','.ts','.vue','.js','.mjs')]}]}
        out.joinpath('native.sarif').write_text(json.dumps(data))
        data=None
if MODE=='malformed':
    out.joinpath('native.json').write_text('{')
    print('{')
elif data is not None:
    out.joinpath('native.json').write_text(json.dumps(data))
    print(json.dumps(data))
value={'tool':tool,'outcome':'native','native_exit_code':code,'cleanup':'removed',
       'image':'sha256:'+'a'*64,'version':'fixture-1','oom_killed':False}
if tool in ('trivy','osv-scanner','grype'): value['database']='b'*64
if tool in ('trivy','osv-scanner'):
    stamp=dt.datetime.now(dt.timezone.utc).isoformat()
    sources=[]
    for label in (('crates.io','npm') if tool=='osv-scanner' else ('db','java-db')):
        source={'sha256':'d'*64,'data_timestamp':stamp}
        if tool=='osv-scanner':
            source.update(url='https://osv-vulnerabilities.storage.googleapis.com/'+label+'/all.zip',
                          generation='1234',timestamp_semantics='upstream_object_generation_created')
        else:
            source.update(url='https://github.com/aquasecurity/trivy-'+label,
                          metadata_file=label+'/metadata.json',timestamp_semantics='native_database_updated_at')
        sources.append(source)
    if MODE=='expired': sources[0]['data_timestamp']='2000-01-01T00:00:00Z'
    value['database_metadata']={'schema':1,'snapshot':'b'*64,'data_timestamp':sources[0]['data_timestamp'],
                                'timestamp_semantics':'oldest_required_source','sources':sources}
    if MODE=='missing-provenance': del value['database_metadata']
    if MODE=='divergent-database': value['database_metadata']['snapshot']='c'*64
    if MODE=='partial-provenance': value['database_metadata']['sources'].pop()
    if MODE=='wrong-oldest': value['database_metadata']['data_timestamp']=(dt.datetime.now(dt.timezone.utc)-dt.timedelta(seconds=10)).isoformat()
if tool=='trivy' and argv[0]!='version':
    value['checks']={'checks':'e'*64,'image':value['image'],'version':value['version'],
                     'origin':'ghcr.io/aquasecurity/trivy-checks:2',
                     'native':{'MajorVersion':2,'Digest':'sha256:'+'f'*64,
                               'DownloadedAt':dt.datetime.now(dt.timezone.utc).isoformat()}}
    value['checks_runtime']={'network':'none','mount':{'Type':'bind','Destination':'/cache/policy','RW':False}}
    if MODE=='missing-checks': del value['checks']
    if MODE=='writable-checks': value['checks_runtime']['mount']['RW']=True
    if MODE=='networked-checks': value['checks_runtime']['network']='bridge'
    if MODE=='mismatched-checks': value['checks']['image']='sha256:'+'c'*64
    if MODE=='rotate-checks' and int(out.parent.name.split('-')[0])>1: value['checks']['checks']='f'*64
if MODE=='environment':
    for key in ('TOOLBOX_STATE','DOCKER_HOST','DOCKER_CONTEXT','DOCKER_CONFIG','DOCKER_TLS_VERIFY','DOCKER_CERT_PATH'):
        assert os.environ[key]=='selected value '+key
if MODE=='rotate' and int(out.parent.name.split('-')[0])>0: value['image']='sha256:'+'c'*64
if MODE=='infrastructure': value.update(outcome='infrastructure',native_exit_code=None); code=125
if MODE=='cleanup': value['cleanup']='failed'
if MODE=='native125': value['native_exit_code']=125; code=125
if MODE=='timeout': value.update(outcome='timeout',native_exit_code=143); code=124
if MODE=='mutate': ORIGINAL.joinpath('README.md').write_text('changed')
if MODE=='cancel':
    def cancel(*_):
        value.update(outcome='cancelled',native_exit_code=143)
        time.sleep(0.3)
        diag.write_text(json.dumps(value))
        out.joinpath('cleanup-complete').touch()
        sys.exit(143)
    for sig in (signal.SIGTERM,signal.SIGINT,signal.SIGHUP): signal.signal(sig,cancel)
    out.joinpath('ready').touch()
    time.sleep(20)
diag.write_text(json.dumps(value))
sys.exit(code)
'''


class NativeTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory(prefix='ec native with spaces ')
        self.root = Path(self.temp.name)
        self.task = self.root / 'task'
        self.task.mkdir()
        self.env = dict(os.environ, EC_ROOT=str(self.root), EC_TASK_DIR=str(self.task),
                        PYTHONDONTWRITEBYTECODE='1')
        self.write('AGENTS.md', 'fixture')
        self.write('README.md', 'fixture')
        self.write('scripts/a file.sh', '#!/bin/bash\ntrue\n').chmod(0o755)
        self.write('tools/a file.py', 'print("public")\n')
        self.write('application/agents/src/lib.rs', 'pub fn f() {}\n')
        self.write('application/server/backend/src/lib.rs', 'pub fn f() {}\n')
        self.write('application/server/frontend/src/a.ts', 'export const a = 1\n')
        for name in native.LOCKS:
            self.write(name, 'fixture lock')
        shutil.copytree(ROOT / 'scripts/config', self.root / 'scripts/config')
        shutil.copytree(ROOT / 'scripts/rules', self.root / 'scripts/rules')
        self.executor()

    def tearDown(self):
        self.temp.cleanup()

    def write(self, name, text):
        path = self.root / name
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_text(text)
        return path

    def executor(self, mode='ok'):
        binary = self.root / 'toolbox fixture'
        binary.write_text('#!/usr/bin/python3\nMODE=' + repr(mode) + '\nORIGINAL=__import__("pathlib").Path('
                          + repr(str(self.root)) + ')\nLOCKS=' + repr(native.LOCKS) + '\n' + EXECUTOR)
        binary.chmod(0o700)
        self.env['TOOLBOX_BIN'] = str(binary)

    def invoke(self, check):
        return subprocess.run(  # noqa: S603 — Gate versionado e executor fixture explícito.
            ['/bin/bash', str(ROOT / 'scripts/stacks/ec/scanner-gate.sh'), check],
            env=self.env, capture_output=True, timeout=30)

    def classification(self):
        return json.loads((self.task / 'classification.json').read_text())

    def test_snapshot_is_complete_preserves_modes_and_excludes_local_secrets(self):
        self.write('application/server/frontend/node_modules/secret.py', 'excluded')
        self.write('application/server/frontend/.env.local', 'excluded')
        self.write('reverse_engineering_teamcity/old.py', 'excluded')
        observed = native.inventory(self.root)
        self.assertIn('scripts/a file.sh', observed)
        self.assertTrue(set(native.LOCKS) <= observed.keys())
        self.assertFalse(any('secret.py' in p or '.env' in p or 'old.py' in p for p in observed))
        gate = native.Gate('shellcheck', self.root, self.task)
        gate.prepare()
        self.assertEqual((gate.snapshot / 'scripts/a file.sh').stat().st_mode & 0o777, 0o755)
        self.assertEqual(gate.targets('shell'), ['scripts/a file.sh'])

    def test_missing_lock_and_empty_component_are_rejected(self):
        self.assertEqual(native.LOCKS, ('application/agents/Cargo.lock',
            'application/server/backend/Cargo.lock', 'application/server/frontend/package-lock.json'))
        (self.root / native.LOCKS[1]).unlink()
        gate = native.Gate('dependencies', self.root, self.task)
        gate.prepare()
        with self.assertRaises(native.Rejected):
            gate.targets('dependencies')
        (self.root / 'application/agents/src/lib.rs').unlink()
        other = self.root / 'other-task'
        other.mkdir()
        gate = native.Gate('sast', self.root, other)
        gate.prepare()
        with self.assertRaises(native.Rejected):
            gate.targets('sast')

    def test_symlink_sources_rejected(self):
        self.write('scripts/link.py', '').unlink()
        (self.root / 'scripts/link.py').symlink_to('/etc/passwd')
        self.assertEqual(self.invoke('ruff').returncode, 2)
        self.assertFalse(any(self.task.glob('??-*')))

    def test_native_gates_reach_executor_without_legacy_contract(self):
        for check in ('ruff', 'shellcheck', 'bandit', 'sast', 'sast-rules', 'secrets',
                      'secrets-rules', 'dependencies', 'trivy', 'grype', 'syft', 'yara-x'):
            with self.subTest(check=check):
                self.task = self.root / ('task-' + check)
                self.task.mkdir()
                self.env['EC_TASK_DIR'] = str(self.task)
                result = self.invoke(check)
                self.assertEqual(result.returncode, 0, result.stderr + result.stdout)
                classification = self.classification()
                self.assertTrue(classification['native_executions'])
                self.assertEqual(classification['state'], 'measured' if check == 'syft' else 'passed')
                captures = list(self.task.glob('??-*/results/capture.json'))
                for capture in captures:
                    args = json.loads(capture.read_text())
                    self.assertEqual(args[0], 'exec')
                    self.assertNotIn('consumer', args)
                    self.assertIn('--', args)
                    self.assertNotIn('--project-write', args)
                if check == 'bandit':
                    self.assertIn('--ignore-nosec', json.loads(captures[0].read_text()))
                if check == 'sast':
                    self.assertIn('--disable-nosem', json.loads(captures[0].read_text()))

    def test_operational_failures_never_approve_and_native_code_is_retained(self):
        for mode, state, expected, raw in (('infrastructure', 'blocked', 3, 125),
                                           ('cleanup', 'error', 2, 0),
                                           ('native125', 'error', 2, 125),
                                           ('timeout', 'error', 2, 124),
                                           ('malformed', 'error', 2, 0)):
            with self.subTest(mode=mode):
                self.task = self.root / ('task-' + mode)
                self.task.mkdir()
                self.env['EC_TASK_DIR'] = str(self.task)
                self.executor(mode)
                self.assertEqual(self.invoke('ruff').returncode, expected)
                doc = self.classification()
                self.assertEqual(doc['state'], state)
                self.assertEqual(doc['native_executions'][0]['returncode'], raw)

    def test_native_124_125_130_are_not_infrastructure_by_number(self):
        for code in (124, 125, 130):
            value = dict(tool='ruff', outcome='native', native_exit_code=code,
                         cleanup='removed', image='sha256:' + 'a' * 64,
                         version='fixture', oom_killed=False)
            native.diagnostic(value, 'ruff', code)
            for change in ({'outcome': 'timeout'}, {'cleanup': 'failed'}, {'native_exit_code': 0},
                           {'oom_killed': True}, {'image': 'mutable-tag'}):
                with self.assertRaises(native.Rejected):
                    native.diagnostic(dict(value, **change), 'ruff', code)

    def test_partial_sast_and_ineffective_rules_rejected(self):
        self.executor('partial')
        self.assertEqual(self.invoke('sast').returncode, 2)
        self.task = self.root / 'other-task'
        self.task.mkdir()
        self.env['EC_TASK_DIR'] = str(self.task)
        self.executor('ineffective')
        self.assertEqual(self.invoke('secrets-rules').returncode, 2)

    def test_expired_database_and_divergent_identity_rejected(self):
        for mode in ('expired', 'missing-provenance', 'divergent-database',
                     'partial-provenance', 'wrong-oldest'):
            with self.subTest(mode=mode):
                self.task = self.root / ('task-' + mode)
                self.task.mkdir()
                self.env['EC_TASK_DIR'] = str(self.task)
                self.executor(mode)
                self.assertEqual(self.invoke('dependencies').returncode, 2)
        with self.assertRaises(native.Rejected):
            native.fresh('2099-01-01T00:00:00Z')

    def test_preparation_date_does_not_renew_expired_upstream_data(self):
        self.executor('expired')
        self.invoke('dependencies')
        value = json.loads(next(self.task.glob('??-*/diagnostic.json')).read_text())
        value['database_metadata']['prepared_at'] = dt.datetime.now(dt.timezone.utc).isoformat()
        with self.assertRaises(native.Rejected):
            native.database_metadata(value, 'osv-scanner')

    def test_provenance_requires_source_generation_semantics_and_hash(self):
        self.assertEqual(self.invoke('dependencies').returncode, 0)
        value = json.loads(next(self.task.glob('??-*/diagnostic.json')).read_text())
        for key, replacement in (('generation', ''), ('timestamp_semantics', 'prepared_at'),
                                 ('sha256', 'invalid'), ('data_timestamp', '2099-01-01T00:00:00Z')):
            broken = copy.deepcopy(value)
            broken['database_metadata']['sources'][0][key] = replacement
            with self.subTest(key=key), self.assertRaises(native.Rejected):
                native.database_metadata(broken, 'osv-scanner')

    def test_trivy_requires_external_readonly_checks_and_stable_identity(self):
        for mode in ('missing-checks', 'writable-checks', 'networked-checks',
                     'mismatched-checks', 'rotate-checks'):
            with self.subTest(mode=mode):
                self.task = self.root / ('task-' + mode)
                self.task.mkdir()
                self.env['EC_TASK_DIR'] = str(self.task)
                self.executor(mode)
                self.assertEqual(self.invoke('trivy').returncode, 2)
                if mode == 'rotate-checks':
                    self.assertIn('Checks Bundle central mudou', self.classification()['reason'])

    def test_operator_environment_reaches_native_executor(self):
        for key in ('TOOLBOX_STATE', 'DOCKER_HOST', 'DOCKER_CONTEXT', 'DOCKER_CONFIG',
                    'DOCKER_TLS_VERIFY', 'DOCKER_CERT_PATH'):
            self.env[key] = 'selected value ' + key
        self.executor('environment')
        self.assertEqual(self.invoke('ruff').returncode, 0)

    def test_expired_trivy_upstream_database_rejected_before_scan(self):
        self.executor('expired')
        self.assertEqual(self.invoke('trivy').returncode, 2)
        self.assertEqual(len(self.classification()['native_executions']), 1)

    def test_trivy_trailing_and_legacy_ignore_directives_rejected_before_execution(self):
        for index, directive in enumerate(('trivy:ignore:fixture', 'tfsec:ignore:fixture', 'avd:ignore:fixture')):
            self.task = self.root / ('task-suppression-' + str(index))
            self.task.mkdir()
            self.env['EC_TASK_DIR'] = str(self.task)
            self.write('application/Dockerfile', 'FROM scratch # ' + directive + '\n')
            self.assertEqual(self.invoke('trivy').returncode, 2)
            self.assertEqual(self.classification()['native_executions'], [])

    def test_central_rotation_between_steps_invalidates_gate(self):
        self.executor('rotate')
        self.assertEqual(self.invoke('secrets-rules').returncode, 2)
        self.assertIn('identidade central mudou', self.classification()['reason'])

    def test_sbom_empty_or_missing_lock_and_grype_ignores_rejected(self):
        artifacts = [{'name': 'fixture', 'version': '1', 'type': 'npm',
                      'locations': [{'path': '/project/' + path}]} for path in native.LOCKS]
        for selected in ([], artifacts[:-1]):
            with self.assertRaises(native.Rejected):
                native.basic_report('syft', {'artifacts': selected}, 0, [])
        with self.assertRaises(native.Rejected):
            native.basic_report('grype', {'artifacts': artifacts, 'matches': [],
                                         'ignoredMatches': [{'id': 'suppressed'}]}, 0, [])

    def test_duplicate_json_and_malformed_reports_rejected(self):
        path = self.write('duplicate.json', '{"results":[],"results":[1]}')
        with self.assertRaises(native.Rejected):
            native.read_json(path)
        for data in (None, 1, {}, [], {'comments': 'invalid'}):
            with self.assertRaises(native.Rejected):
                native.basic_report('shellcheck', data, 0, ['a.sh'])

    def test_source_mutation_rejects_even_clean_scanner(self):
        self.executor('mutate')
        result = self.invoke('ruff')
        self.assertEqual(result.returncode, 2)
        self.assertIn('integridade', self.classification()['reason'])

    def test_synthetic_credentials_absent_from_metadata_and_streams(self):
        secret = 'synthetic-' + os.urandom(12).hex()
        self.env['PGPASSWORD'] = secret
        self.assertEqual(self.invoke('ruff').returncode, 0)
        for path in self.task.rglob('*'):
            if path.is_file() and 'sources' not in path.relative_to(self.task).parts:
                self.assertNotIn(secret.encode(), path.read_bytes())
        self.task = self.root / 'task-database-metadata'
        self.task.mkdir()
        self.env['EC_TASK_DIR'] = str(self.task)
        # Recibo externo legado não participa mais do gate nem renova uma base.
        path = self.write('legacy-database-record.json', secret)
        self.env['EC_OSV_SCANNER_DATABASE_RECORD'] = str(path)
        self.assertEqual(self.invoke('dependencies').returncode, 0)
        self.assertNotIn(secret, (self.task / 'osv-scanner-database.json').read_text())
        value = json.loads(next(self.task.glob('??-*/diagnostic.json')).read_text())
        value['database_metadata']['unexpected_credential'] = secret
        value['database_metadata']['sources'][0]['unexpected_credential'] = secret
        public = native.database_metadata(value, 'osv-scanner')
        self.assertNotIn(secret, json.dumps(public))

    def test_cancel_waits_for_executor_cleanup_and_never_approves(self):
        self.executor('cancel')
        for sig in (signal.SIGTERM, signal.SIGINT, signal.SIGHUP):
            with self.subTest(signal=sig):
                self.task = self.root / ('task-cancel-' + str(sig))
                self.task.mkdir()
                self.env['EC_TASK_DIR'] = str(self.task)
                process = subprocess.Popen(  # noqa: S603 — Gate e executor fixture explícitos.
                    ['/bin/bash', str(ROOT / 'scripts/stacks/ec/scanner-gate.sh'), 'ruff'],
                    env=self.env, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
                deadline = time.monotonic() + 10
                while not list(self.task.glob('??-*/results/ready')) and time.monotonic() < deadline:
                    time.sleep(0.03)
                self.assertTrue(list(self.task.glob('??-*/results/ready')))
                process.send_signal(sig)
                out, err = process.communicate(timeout=10)
                self.assertEqual(process.returncode, 2, out + err)
                self.assertTrue(list(self.task.glob('??-*/results/cleanup-complete')))
                self.assertEqual(self.classification()['state'], 'error')

    def test_playwright_uses_central_esm_without_host_node_modules(self):
        self.write('application/server/frontend/dist/index.html', '<html></html>')
        self.write('application/server/frontend/node_modules/native.node', 'host ABI must stay out')
        result = self.invoke('playwright')
        self.assertEqual(result.returncode, 0, result.stderr + result.stdout)
        self.assertFalse((self.task / 'sources/application/server/frontend/node_modules').exists())
        text = (ROOT / 'scripts/config/native/playwright/playwright.config.mjs').read_text()
        self.assertIn('/opt/pw/node_modules/@playwright/test/index.mjs', text)
        self.assertTrue((ROOT / 'application/integration/tests/e2e/frontend_firefox.py').is_file())

    def test_playwright_rejects_symlink_build_before_copy_or_execution(self):
        other = self.root / 'outside-build'
        other.mkdir()
        (other / 'index.html').write_text('private fixture must stay outside the snapshot')
        build = self.root / 'application/server/frontend/dist'
        build.symlink_to(other, target_is_directory=True)
        self.assertEqual(self.invoke('playwright').returncode, 2)
        self.assertFalse((self.task / 'sources/application/server/frontend/dist').exists())
        self.assertEqual(self.classification()['native_executions'], [])

    def test_playwright_rejects_skipped_flaky_empty_or_duplicate_tests(self):
        good = {'errors': [], 'stats': dict(expected=2, unexpected=0, skipped=0, flaky=0),
                'suites': [{'specs': [{'title': title, 'tests': [{'expectedStatus': 'passed',
                    'status': 'expected', 'results': [{'status': 'passed'}]}]} for title in ('a', 'b')]}]}
        native.basic_report('playwright', good, 0, [])
        for key in ('skipped', 'flaky', 'expected'):
            report = copy.deepcopy(good)
            report['stats'][key] = 1
            with self.assertRaises(native.Rejected):
                native.basic_report('playwright', report, 0, [])
        good['suites'][0]['specs'][1]['title'] = 'a'
        with self.assertRaises(native.Rejected):
            native.basic_report('playwright', good, 0, [])

    def test_codeql_requires_all_targets_and_explicit_rust_environment(self):
        for key in ('EC_CODEQL_RUSTUP_HOME', 'EC_CODEQL_CARGO_HOME', 'EC_CODEQL_CARGO_BIN'):
            directory = self.root / key
            directory.mkdir()
            self.env[key] = str(directory)
        result = self.invoke('codeql')
        self.assertEqual(result.returncode, 0, result.stderr + result.stdout)
        self.assertEqual(self.classification()['targets'], ['server', 'agent', 'frontend'])
        self.assertEqual(len(list(self.task.glob('??-*/results/native.sarif'))), 3)
        for capture in self.task.glob('??-*/results/capture.json'):
            args = json.loads(capture.read_text())
            self.assertNotIn('--download', args)
            self.assertNotIn('--project-write', args)
            self.assertNotIn('version:', ' '.join(args))

    def test_missing_rust_runtime_still_attempts_frontend_without_partial_approval(self):
        for key in ('EC_CODEQL_RUSTUP_HOME', 'EC_CODEQL_CARGO_HOME', 'EC_CODEQL_CARGO_BIN'):
            self.env.pop(key, None)
        result = self.invoke('codeql')
        self.assertEqual(result.returncode, 3, result.stderr + result.stdout)
        results = self.classification()['target_results']
        self.assertEqual([item['state'] for item in results], ['blocked', 'blocked', 'passed'])
        self.assertEqual(len(list(self.task.glob('??-*/results/native.sarif'))), 1)

    def test_codeql_unresolved_database_never_reaches_analysis(self):
        for key in ('EC_CODEQL_RUSTUP_HOME', 'EC_CODEQL_CARGO_HOME', 'EC_CODEQL_CARGO_BIN'):
            directory = self.root / key
            directory.mkdir()
            self.env[key] = str(directory)
        self.executor('partial')
        self.assertEqual(self.invoke('codeql').returncode, 2)
        self.assertFalse(list(self.task.glob('??-*/results/native.sarif')))

    def test_supervisor_does_not_record_synthetic_secret_in_task_argv(self):
        secret = 'synthetic-' + os.urandom(12).hex()
        tasks = [{'project': 'scripts', 'name': 'credential', 'cwd': '.', 'depends': [],
                  'timeout': 5, 'argv': ['/usr/bin/true', secret]}]
        plan = self.write('plan.json', json.dumps({'tasks': tasks, 'surfaces': ['scripts']}))
        harness = '''set -euo pipefail
CODE=$1 ROOT=$2
source "$CODE/scripts/stacks/ec/common.sh"
source "$CODE/scripts/stacks/ec/inputs.sh"
source "$CODE/scripts/stacks/ec/runtime.sh"
PLAN=$(cat "$3")
run_plan
'''
        result = subprocess.run(  # noqa: S603 — Harness literal; valores como argumentos.
            ['/bin/bash', '-c', harness, 'fixture', str(ROOT), str(self.root), str(plan)],
            env=self.env, capture_output=True, timeout=30)
        self.assertEqual(result.returncode, 0, result.stderr)
        for path in (self.root / '.local/automation/runs').rglob('*.json'):
            self.assertNotIn(secret, path.read_text())

    def test_cancel_observed_after_last_command_never_publishes_approval(self):
        plan = self.write('plan.json', json.dumps({'tasks': [{
            'project': 'scripts', 'name': 'last', 'cwd': '.', 'depends': [],
            'timeout': 5, 'argv': ['/usr/bin/true']}], 'surfaces': ['scripts']}))
        harness = '''set -euo pipefail
CODE=$1 ROOT=$2
source "$CODE/scripts/stacks/ec/common.sh"
source "$CODE/scripts/stacks/ec/inputs.sh"
source "$CODE/scripts/stacks/ec/runtime.sh"
PLAN=$(cat "$3")
# Fronteira determinística: comando já terminou quando o cancelamento é observado.
execute_task() { TASK_RC=0; TASK_CAUSE=''; CANCELLED=1; }
run_plan
'''
        result = subprocess.run(  # noqa: S603 — Harness literal; valores como argumentos.
            ['/bin/bash', '-c', harness, 'fixture', str(ROOT), str(self.root), str(plan)],
            env=self.env, capture_output=True, timeout=30)
        self.assertEqual(result.returncode, 2, result.stderr)
        path = next((self.root / '.local/automation/runs').glob('*/results.json'))
        data = json.loads(path.read_text())
        self.assertTrue(data['cancelled'])
        self.assertFalse(data['complete'])
        self.assertEqual(data['exit_code'], 2)


if __name__ == '__main__':
    unittest.main()
