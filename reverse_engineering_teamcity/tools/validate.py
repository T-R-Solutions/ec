from pathlib import Path
import json,sqlite3,zlib,hashlib,re,collections,sys
O=Path('rea/teamcity-reverse-engineering');E=O/'25-evidencias';fail=[];checks=collections.Counter()
def check(ok,desc):
 checks['checks']+=1
 if not ok:fail.append(desc)
def sha(p):
 h=hashlib.sha256()
 with Path(p).open('rb') as f:
  for b in iter(lambda:f.read(1048576),b''):h.update(b)
 return h.hexdigest()
c=sqlite3.connect(f'file:{O}/symbols.sqlite?mode=ro',uri=True)
check(c.execute('PRAGMA quick_check').fetchone()[0]=='ok','SQLite quick_check')
for p in O.rglob('*.json'):
 try:json.loads(p.read_text());checks['json_files']+=1
 except Exception as e:fail.append(f'{p}: {e}')
for r in json.loads((O/'20-classes-metodos/focus-index.json').read_text()):
 h=r['class_sha256'];v=c.execute('select record from classes where sha=?',(h,)).fetchone();check(bool(v),'missing class '+h)
 if not v:continue
 record=json.loads(zlib.decompress(v[0]));text=Path(r['javap']).read_text();check(sha(r['extracted'])==h,'extracted digest '+h)
 expected=collections.Counter(m['descriptor'] for m in record['methods']);expected.update(f['descriptor'] for f in record['fields']);actual=collections.Counter(re.findall(r'^\s*descriptor: (.+)$',text,re.M));check(actual==expected,'javap descriptors '+record['name'])
 expected_calls=sum(len(m['calls']) for m in record['methods']);actual_calls=len(re.findall(r'^\s*\d+:\s+invoke(?:virtual|special|static|interface|dynamic)\s',text,re.M));
 if any(m.get('body_indexed') for m in record['methods']):check(expected_calls==actual_calls,'javap invoke count '+record['name'])
 else:checks['third_party_body_comparisons_not_applicable']+=1
 checks['focus_classes']+=1
for r in json.loads((O/'evidence-index.json').read_text())['records']:
 check(sha(O/r['path'])==r['sha256'],'evidence digest '+r['path']);checks['evidence_files']+=1
for r in json.loads((O/'claims.json').read_text()):
 check(bool(r['artifact_sha256']),'missing artifact hash '+r['id']);check(bool(r['descriptors']),'missing descriptor '+r['id']);check(Path(r['evidence']['javap']).exists(),'missing evidence '+r['id'])
metrics=json.loads((O/'27-cobertura/index-metrics.json').read_text());check(c.execute('select count(*) from classes').fetchone()[0]==metrics['counts']['unique_class_bytes'],'class denominator');check(c.execute('select count(*) from methods').fetchone()[0]==metrics['counts']['unique_class_methods'],'method denominator')
result={'status':'passed' if not fail else 'failed','checks':dict(checks),'failures':fail,'scope':'Static evidence integrity; all focused method/field descriptors and eligible first-party invocation counts cross-checked against javap. Not TeamCity behavior testing.'};(O/'24-testes/static-validation.json').write_text(json.dumps(result,indent=2));print(json.dumps(result,indent=2));sys.exit(bool(fail))
