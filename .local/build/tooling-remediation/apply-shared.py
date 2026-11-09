from pathlib import Path
import hashlib,json,shutil
base=Path('/home/rodrigo/Projetos/dev_audit/shared-toolbox');stage=Path('/home/rodrigo/Projetos/ec/.local/build/tooling-remediation/shared-toolbox');backup=stage.parent/'shared-before'
changes=json.loads((stage.parent/'changes.json').read_text())
for change in changes:
 p=base/change['path']; actual=hashlib.sha256(p.read_bytes()).hexdigest() if p.exists() else None
 if actual!=change['before']:raise SystemExit('Concurrent edit: '+str(p))
 if hashlib.sha256((stage/change['path']).read_bytes()).hexdigest()!=change['after']:raise SystemExit('Staging changed')
for change in changes:
 p=base/change['path'];p.parent.mkdir(parents=True,exist_ok=True)
 if p.exists():
  original=backup/change['path'];original.parent.mkdir(parents=True,exist_ok=True);shutil.copy2(p,original)
 shutil.copy2(stage/change['path'],p)
print('Applied',len(changes),'reviewed files; originals preserved')
