from pathlib import Path
import sqlite3,zipfile,io,json,hashlib,subprocess
R=Path('/opt/jvm/TeamCity');O=Path('rea/teamcity-reverse-engineering/25-evidencias');c=sqlite3.connect('rea/teamcity-reverse-engineering/symbols.sqlite');rows=[]
for name in ['jetbrains/buildServer/configs/dsl/kotlin/KotlinCompiler','jetbrains/buildServer/configs/dsl/PluginDslCompiler','jetbrains/buildServer/configs/dsl/PluginDslCompilerProcess','jetbrains/buildServer/configs/dsl/DslCompiler']:
 loc=c.execute('select location from locations join classes using(sha) where name=? limit 1',(name,)).fetchone()[0];parts=loc.split('!/');b=(R/parts[0]).read_bytes();ah=hashlib.sha256(b).hexdigest()
 for p in parts[1:]:
  with zipfile.ZipFile(io.BytesIO(b)) as z:b=z.read(p)
 h=hashlib.sha256(b).hexdigest();dest=O/'classes'/f'{h}.class';dest.write_bytes(b);jp=O/'javap'/f'{h}.txt';d=O/'decompiled'/h;d.mkdir(exist_ok=True)
 with jp.open('w') as f:jr=subprocess.run(['javap','-p','-c','-s','-l',str(dest)],stdout=f,stderr=subprocess.STDOUT)
 with (d/'cfr.log').open('w') as f:cr=subprocess.run(['java','-Xmx512m','-jar','rea/tools/cfr-0.152.jar',str(dest),'--outputdir',str(d),'--silent','true'],stdout=f,stderr=subprocess.STDOUT)
 rows.append({'artifact':'!/'.join(parts[:-1]),'outer_artifact_sha256':ah,'artifact_sha256':None,'class':name,'class_sha256':h,'extracted':str(dest),'javap':str(jp),'javap_exit':jr.returncode,'cfr_exit':cr.returncode,'decompiled':[str(p) for p in d.rglob('*.java')]})
(O/'focus-index-nested.json').write_text(json.dumps(rows,indent=2));print(len(rows))
