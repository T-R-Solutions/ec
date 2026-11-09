from pathlib import Path
import json,difflib,hashlib
root=Path('docs/validation/tooling-remediation-20261106');backup=Path('.local/build/tooling-remediation/shared-before');stage=Path('.local/build/tooling-remediation/shared-toolbox')
old={}
for rel in ['toolbox/config.py','toolbox/runner.py','toolbox/prepare.py','docs/references.json','docs/references.md']:
 s=(backup/rel).read_text()
 if rel=='toolbox/prepare.py':
  s=s.replace('import io\nimport zipfile\n','').replace("    if name == 'osv-scanner':\n        return update_osv_database(store, spec, lock)\n",'')
  if '\ndef update_osv_database(' in s:s=s[:s.index('\ndef update_osv_database(')].rstrip()+'\n'
 if rel=='docs/references.json':
  d=json.loads(s)
  d=[x for x in d if x.get('supports') not in ['Release API supplies SHA256; old 0.10.0 preserved; substitution test rejects mismatched download.','Local rules, test/scan modes, metrics off, strict errors, disabled suppressions; real execution required.','Explicit HTTPS database update; immutable local snapshot, age, exact lockfile inventory; scan offline.']]
  s=json.dumps(d,indent=2)+'\n'
 if rel=='docs/references.md':
  marker='\n- ShellCheck 0.11.0: [release assets]'
  if marker in s:s=s[:s.index(marker)]
 old[rel]=s
paths=list(old)+['tests/test_security_adapters.py']
for tool,ver in [('shellcheck','0.11.0'),('opengrep','1.33.1'),('osv-scanner','2.7.0')]:
 paths.extend(str(p.relative_to(stage)) for p in (stage/'tools'/tool/'versions'/ver).iterdir() if p.is_file())
patch=[]; manifest=[]
for rel in paths:
 current=(stage/rel).read_text()
 patch.extend(difflib.unified_diff(old.get(rel,'').splitlines(True),current.splitlines(True),fromfile='a/'+rel,tofile='b/'+rel))
 manifest.append({'path':rel,'sha256':hashlib.sha256(current.encode()).hexdigest()})
(root/'shared-toolbox.patch').write_text(''.join(patch));(root/'shared-inputs.json').write_text(json.dumps(manifest,indent=2)+'\n')
