import json
import os
from pathlib import Path
import subprocess

root=Path('/home/rodrigo/Projetos/ec')
art=root/'docs/validation/frontend-20261105'
env=dict(os.environ,CARGO_HOME=str(root/'.local/build/ec-cargo-home'),CARGO_TARGET_DIR=str(root/'.local/build/ec-server-clean'))
commands=[
 ('firefox',['python3','application/integration/tests/e2e/frontend_firefox.py']),
 ('typecheck',['npm','--prefix','application/server/frontend','run','typecheck']),
 ('unit',['npm','--prefix','application/server/frontend','test']),
 ('build',['npm','--prefix','application/server/frontend','run','build']),
 ('rust-test',['cargo','+1.99.1','test','--manifest-path','application/server/backend/Cargo.toml','--locked','--offline']),
 ('rust-clippy',['cargo','+1.99.1','clippy','--manifest-path','application/server/backend/Cargo.toml','--locked','--offline','--all-targets','--all-features','--','-D','warnings']),
 ('rust-fmt',['cargo','+1.99.1','fmt','--manifest-path','application/server/backend/Cargo.toml','--all','--','--check']),
 ('server-build',['cargo','+1.99.1','build','--manifest-path','application/server/backend/Cargo.toml','--locked','--offline','--features','test-failpoints']),
 ('publication',['python3','application/integration/tests/integration/publication_http.py']),
 ('orchestration',['python3','application/integration/tests/integration/orchestration_http.py']),
 ('agent',['python3','application/integration/tests/integration/agent_http.py']),
]
results=[]
for name,command in commands:
 with (art/(name+'.log')).open('w') as f:
  result=subprocess.run(command,cwd=root,env=env,stdout=f,stderr=subprocess.STDOUT,timeout=600)
 results.append({'name':name,'command':command,'exit_code':result.returncode})
 (art/'checks.json').write_text(json.dumps(results,indent=2)+'\n')
 print(name,result.returncode,flush=True)
