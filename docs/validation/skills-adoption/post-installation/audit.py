import ast,importlib.util,json,os,re,subprocess,sys,zipfile,hashlib
from pathlib import Path
import yaml
R=Path.cwd(); O=R/'docs/validation/skills-adoption/post-installation';P=json.loads((R/'config/governance/profile.json').read_text())
rows=[r for r in P['skills'] if r['decision']!='excluded']
spec=importlib.util.spec_from_file_location('quick','/home/rodrigo/.codex/skills/.system/skill-creator/scripts/quick_validate.py');quick=importlib.util.module_from_spec(spec);spec.loader.exec_module(quick)
checks=[]
def check(name,okay,detail=''): checks.append({'name':name,'passed':bool(okay),'detail':detail})
for r in rows:
 folder=R/Path(r['path']).parent
 okay,msg=quick.validate_skill(folder);check(r['name']+' frontmatter',okay,msg)
 ui=yaml.safe_load((folder/'agents/openai.yaml').read_text());interface=ui['interface']
 check(r['name']+' UI metadata',isinstance(interface['display_name'],str) and 25<=len(interface['short_description'])<=64 and '$'+r['name'] in interface['default_prompt'])
 if 'policy' in ui: check(r['name']+' invocation policy',ui['policy'].get('allow_implicit_invocation',True) is not False,'No explicit-only instruction was requested')
for f in [*R.glob('tools/governance/**/*.py'),R/'sdd/tools/validate.py']:
 ast.parse(f.read_text());check(str(f.relative_to(R))+' syntax',True)
for f in (R/'skills_to_use').glob('*.zip'):
 with zipfile.ZipFile(f) as z:
  check(f.name+' CRC',z.testzip() is None)
  names=z.namelist();check(f.name+' names',len(names)==len(set(names)) and all(not Path(n).is_absolute() and '..' not in Path(n).parts for n in names))
# Compare every source resource to provided packages, not only copied manifests.
with zipfile.ZipFile(R/'skills_to_use/ec_skills.zip') as ec,zipfile.ZipFile(R/'skills_to_use/ox_resume.zip') as ox:
 for row in P['skills']:
  for rel,h in row['source_files'].items():
   if row['origin']=='ec':b=ec.read('new_skills/skills/'+row['name']+'/'+rel)
   elif row['origin']=='ox':b=ox.read('.codex/skills/'+row['name']+'/'+rel)
   else:b=(R/'skills_to_use/readme-lifecycle-engineering-skill/new_skills/skills'/row['name']/rel).read_bytes()
   check(row['name']+' source '+rel,hashlib.sha256(b).hexdigest()==h)
commands=[('catalog',[sys.executable,'tools/governance/validate.py'],0),('sdd-structure',[sys.executable,'sdd/tools/validate.py','--root','.','--structure-only'],0),('negative-controls',[sys.executable,'-m','unittest','discover','-s','tools/governance/tests','-v'],0),('installation-preflight',[sys.executable,'tools/governance/install_skills.py','--check'],0),('installation-gate',[sys.executable,'tools/governance/validate.py','--require-installed'],0)]
env=os.environ.copy();env['PYTHONDONTWRITEBYTECODE']='1'
for name,args,code in commands:
 proc=subprocess.run(args,cwd=R,env=env,capture_output=True,text=True)
 (O/(name+'.log')).write_text('COMMAND: '+' '.join(args)+'\nEXIT: '+str(proc.returncode)+'\n'+proc.stdout+proc.stderr)
 check(name,proc.returncode==code,'exit='+str(proc.returncode)+' expected='+str(code))
result={'status':'PASS' if all(x['passed'] for x in checks) else 'FAIL','checks':checks,'total':len(checks),'failed':[c for c in checks if not c['passed']],'python':sys.version,'yaml':yaml.__version__,'limits':['Installation gate must pass against the real .codex/skills destination.','No product runtime, remote URL checks, UI discovery or behavioral evals of all 67 skills.']}
(O/'checks.json').write_text(json.dumps(result,ensure_ascii=False,indent=2)+'\n')
print(json.dumps({k:result[k] for k in ['status','total','failed']},ensure_ascii=False,indent=2))
