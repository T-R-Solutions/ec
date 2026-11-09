from pathlib import Path
import hashlib,json,difflib
base=Path('/home/rodrigo/Projetos/dev_audit/shared-toolbox');stage=Path('.local/build/tooling-remediation/shared-toolbox');out=Path('docs/validation/tooling-remediation-20261106')
changes=[];patch=[]
for p in sorted(stage.rglob('*')):
 if not p.is_file() or any(v in p.relative_to(stage).parts for v in ('__pycache__','.local','volume')):continue
 rel=p.relative_to(stage); old=base/rel
 if old.exists() and old.read_bytes()==p.read_bytes():continue
 changes.append({'path':str(rel),'before':hashlib.sha256(old.read_bytes()).hexdigest() if old.exists() else None,'after':hashlib.sha256(p.read_bytes()).hexdigest()})
 patch.extend(difflib.unified_diff(old.read_text().splitlines(True) if old.exists() else [],p.read_text().splitlines(True),fromfile='a/'+str(rel),tofile='b/'+str(rel)))
(out/'shared-toolbox.patch').write_text(''.join(patch));Path('.local/build/tooling-remediation/changes.json').write_text(json.dumps(changes,indent=2)+'\n')
print(len(changes),'files staged')
