"""Validate references and run an independent, dependency-free Rust contract model."""
import hashlib
import json
import re
import subprocess
import tempfile
from datetime import datetime, timezone
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
findings = ROOT / '04-validation/findings'
findings.mkdir(parents=True, exist_ok=True)
# Create the report destination before validating links to this generated report.
(findings / 'validation.json').write_text('{"status":"running"}\n')


def read(path):
    return json.loads((ROOT / path).read_text())


checks = []


def check(name, value):
    checks.append({'name': name, 'passed': bool(value)})


claims = read('01-teamcity-research/evidence/legacy-claims.json')
sources = read('01-teamcity-research/evidence/official-sources.json')
features = read('01-teamcity-research/features/features.json')
flows = read('01-teamcity-research/flows/behavioral-contracts.json')
reqs = read('02-independent-specification/requirements/requirements.json')
acceptance = read('02-independent-specification/acceptance-criteria/scenarios.json')
matrix = read('decision-matrix.json')
ids = {x['id'] for x in claims + sources}
fids = {x['id'] for x in features}
rids = {x['id'] for x in reqs}
aids = {x['id'] for x in acceptance}
for r in reqs:
    check(r['id'] + ' complete contract', all(k in r for k in [
        'objective', 'motivation', 'inputs', 'outputs', 'rules', 'invariants', 'errors',
        'intended_guarantees', 'dependencies', 'priority', 'acceptance', 'evidence']))
    check(r['id'] + ' references', bool(r['evidence']) and set(r['evidence']) <= ids
          and set(r['features']) <= fids and set(r['dependencies']) <= rids
          and bool(r['acceptance']) and set(r['acceptance']) <= aids)
for f in features:
    check(f['id'] + ' evidence', set(f['evidence']) <= ids and bool(f['evidence']))
check('each feature has behavioral flow', {f['feature_id'] for f in flows} == fids)
for f in flows:
    check(f['feature_id'] + ' behavioral contract fields', all(k in f for k in [
        'initial_event', 'received_data', 'preconditions', 'validations', 'business_rules',
        'transformations', 'intermediate_states', 'component_interactions', 'persistence',
        'concurrency', 'responses', 'possible_failures', 'recovery', 'final_result']))
check('every feature has matrix row', {m['feature_id'] for m in matrix} == fids)
for m in matrix:
    check(m['feature_id'] + ' decision references', bool(m['independent_requirements'])
          and set(m['independent_requirements']) <= rids and set(m['acceptance']) <= aids
          and bool(m['adrs'])
          and all((ROOT / f'03-rust-architecture/decisions/{a}.md').is_file() for a in m['adrs']))
for v in read('01-teamcity-research/evidence/reuse-validation.json'):
    check(v['claim'] + ' reusable original', v['matches'] and v['javap_exists'])
check('no TeamCity runtime observation fabricated', all(c['classification'] == 'STATIC_CONFIRMED' for c in claims))
for p in ROOT.rglob('*.md'):
    for link in re.findall(r'\]\(([^)]+)\)', p.read_text()):
        if not link.startswith(('http:', 'https:', '#')):
            check(str(p.relative_to(ROOT)) + ' → ' + link, (p.parent / link.split('#')[0]).exists())

prototype = ROOT / '04-validation/prototypes/lease_model.rs'
rust_version = subprocess.run(['rustc', '--version'], capture_output=True, text=True, check=True).stdout.strip()
with tempfile.TemporaryDirectory(prefix='ci-lease-model-') as tmp:
    binary = Path(tmp) / 'lease-tests'
    compiled = subprocess.run(['rustc', '--edition=2024', '--test', '-D', 'warnings', str(prototype), '-o', str(binary)], capture_output=True, text=True)
    check('Rust model compiles with warnings denied', compiled.returncode == 0)
    tested = subprocess.run([str(binary), '--test-threads=1'], capture_output=True, text=True) if compiled.returncode == 0 else None
    check('Rust lease contract tests', tested is not None and tested.returncode == 0)
    raw = (compiled.stdout + compiled.stderr) + (tested.stdout + tested.stderr if tested else '')
(findings / 'rust-model-tests.txt').write_text(raw)
result = dict(timestamp=datetime.now(timezone.utc).isoformat(), status='passed' if all(c['passed'] for c in checks) else 'failed',
              checks=checks, checks_passed=sum(c['passed'] for c in checks), checks_total=len(checks),
              rustc=rust_version, prototype_sha256=hashlib.sha256(prototype.read_bytes()).hexdigest(),
              rust_test_log='rust-model-tests.txt',
              executed_scope=['document traceability', 'selected artifact hashes (generator)', 'sequential independent lease contract model'],
              not_executed=['TeamCity runtime', 'PostgreSQL transactions or concurrency', 'HTTP transport', 'process execution', 'isolation', 'performance benchmarks'],
              product_acceptance_status='specified_not_executed',
              command='python3 rea/ci-platform-research/04-validation/tests/validate.py')
(findings / 'validation.json').write_text(json.dumps(result, ensure_ascii=False, indent=2) + '\n')
state = read('research-state.json')
state['validation_result'] = {'status': result['status'], 'checks_passed': result['checks_passed'],
                             'checks_total': result['checks_total'], 'rust_contract_tests': 12}
(ROOT / 'research-state.json').write_text(json.dumps(state, ensure_ascii=False, indent=2) + '\n')
print(json.dumps({k:result[k] for k in ['status','checks_passed','checks_total','rustc']}))
for c in checks:
    if not c['passed']:
        print('FAILED:', c['name'])
raise SystemExit(0 if result['status'] == 'passed' else 1)
