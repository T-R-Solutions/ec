from pathlib import Path
import zipfile,re,json,hashlib,subprocess,concurrent.futures
ROOT=Path('/opt/jvm/TeamCity');OUT=Path('rea/teamcity-reverse-engineering/25-evidencias');(OUT/'classes').mkdir(exist_ok=True);(OUT/'javap').mkdir(exist_ok=True);(OUT/'decompiled').mkdir(exist_ok=True)
patterns=[r'/(AgentMain2|StartupProcessor|BuildQueueImpl\$BuildQueueDefaultOrdering|HttpBasedProtocolResolver|PollingProtocol|ServerCommandsPoller|ServerCommandsExecutor|KotlinCompiler|DslCompiler|PluginDslCompiler|PluginDslCompilerProcess|VersionedSettingsUpdaterImpl|SingleBranchVcsTrigger|AllBranchesVcsTrigger)\.class$']
selected=[]
for group in ['webapps/ROOT/WEB-INF/lib','buildAgent/lib','buildAgent/launcher/lib','webapps/ROOT/WEB-INF/plugins/rest-api/server','webapps/ROOT/WEB-INF/plugins/priority-queue/server']:
 for p in sorted((ROOT/group).glob('*.jar')):
  with zipfile.ZipFile(p) as z:
   for n in z.namelist():
    if any(re.search(pat,n) for pat in patterns):
     b=z.read(n);h=hashlib.sha256(b).hexdigest();dest=OUT/'classes'/f'{h}.class';dest.write_bytes(b);selected.append({'artifact':str(p.relative_to(ROOT)),'artifact_sha256':hashlib.sha256(p.read_bytes()).hexdigest(),'class':n[:-6],'class_sha256':h,'extracted':str(dest)})
def inspect(row):
 h=row['class_sha256'];p=row['extracted'];jp=OUT/'javap'/f'{h}.txt'
 with jp.open('w') as f:r=subprocess.run(['javap','-p','-c','-s','-l',p],stdout=f,stderr=subprocess.STDOUT)
 row['javap_exit']=r.returncode;row['javap']=str(jp)
 d=OUT/'decompiled'/h;d.mkdir(exist_ok=True)
 with (d/'cfr.log').open('w') as f:r=subprocess.run(['java','-Xmx512m','-jar','rea/tools/cfr-0.152.jar',p,'--outputdir',str(d),'--silent','true'],stdout=f,stderr=subprocess.STDOUT)
 row['cfr_exit']=r.returncode;row['decompiled']=[str(x) for x in d.rglob('*.java')];return row
with concurrent.futures.ThreadPoolExecutor(max_workers=3) as pool:rows=list(pool.map(inspect,selected))
(OUT/'focus-index-more.json').write_text(json.dumps(rows,indent=2));print('inspected',len(rows))
