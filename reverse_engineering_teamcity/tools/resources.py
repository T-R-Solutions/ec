import pathlib,hashlib,json,zipfile,shutil,xml.etree.ElementTree as ET
O=pathlib.Path('rea/teamcity-reverse-engineering');R=pathlib.Path('/opt/jvm/TeamCity');E=O/'25-evidencias/resources';E.mkdir(exist_ok=True);a=json.load(open(O/'artifacts.json'));files={f['path']:f for f in a['files']};backups=[];resources=[]
for path,row in files.items():
 if path.endswith('.BACKUP'):
  active=files.get(path[:-7]);backups.append({'backup':path,'backup_sha256':row['sha256'],'active':path[:-7],'active_sha256':active['sha256'] if active else None,'identical':active and active['sha256']==row['sha256']})
 if path in ['bin/teamcity-server.sh','bin/catalina.sh','buildAgent/bin/agent.sh','webapps/ROOT/WEB-INF/web.xml','webapps/ROOT/WEB-INF/buildServerConfigurator.xml','webapps/ROOT/WEB-INF/buildServerSpringWeb.xml'] or path.endswith('teamcity-plugin.xml'):
  dest=E/path;dest.parent.mkdir(parents=True,exist_ok=True);shutil.copyfile(R/path,dest);resources.append({'path':path,'sha256':row['sha256'],'copy':str(dest)})
sql=[]
for p in (R/'webapps/ROOT/WEB-INF/lib').glob('*.jar'):
 with zipfile.ZipFile(p) as z:
  for n in z.namelist():
   if n.endswith('.sql') or (n.endswith('.xml') and ('buildServerSpring' in n or 'buildServer' in n and 'Spring' in n)):
    b=z.read(n);h=hashlib.sha256(b).hexdigest();dest=E/(h+pathlib.Path(n).suffix);dest.write_bytes(b);sql.append({'artifact':str(p.relative_to(R)),'member':n,'sha256':h,'copy':str(dest)})
(O/'01-inventario/backup-comparison.json').write_text(json.dumps(backups,indent=2));(O/'01-inventario/resource-index.json').write_text(json.dumps(resources+sql,indent=2));print('backups',len(backups),'different',sum(not b['identical'] for b in backups),'resources',len(resources),'SQL/XML members',len(sql))
