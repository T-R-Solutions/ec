import pathlib,os,hashlib,json,zipfile,io,subprocess,struct,datetime
ROOT=pathlib.Path('/opt/jvm/TeamCity'); OUT=pathlib.Path('rea/teamcity-reverse-engineering'); E=OUT/'25-evidencias'
def sha(b):return hashlib.sha256(b).hexdigest()
def dump(name,value): (OUT/name).write_text(json.dumps(value,indent=2,ensure_ascii=False))
errors=[]; artifacts=[]; archives=[]; resources=[]; counts={'files':0,'class_entries':0,'archives':0,'excluded_runtime_files':0}; seen={}
raw=open(E/'symbols.jsonl.gz','wb'); compressor=subprocess.Popen(['gzip','-c'],stdin=subprocess.PIPE,stdout=raw)
worker=subprocess.Popen(['java','-Xmx1024m','-cp','rea/tools:rea/tools/asm-9.11.1.jar','IndexClasses'],stdin=subprocess.PIPE,stdout=compressor.stdin)
def send(loc,b):
 x=loc.encode();worker.stdin.write(struct.pack('>I',len(x))+x+struct.pack('>I',len(b))+b);counts['class_entries']+=1
# Runtime trees are metadata-only; never archive-expand or export their content.
def excluded(rel):return rel.parts[0] in ('logs','temp','work') or (len(rel.parts)>1 and rel.parts[:2] in [('buildAgent','logs'),('buildAgent','work'),('buildAgent','temp')])
def archive(loc,b,depth=0):
 counts['archives']+=1; entry={'location':loc,'sha256':sha(b),'size':len(b),'depth':depth,'members':0,'classes':0,'manifests':[]};archives.append(entry)
 if depth>12: errors.append({'location':loc,'error':'archive depth limit'});return
 try:
  with zipfile.ZipFile(io.BytesIO(b)) as z:
   for info in z.infolist():
    if info.is_dir():continue
    entry['members']+=1;n=info.filename;member=loc+'!/'+n
    if n.endswith('.class'):
     send(member,z.read(info));entry['classes']+=1
    elif n.lower().endswith(('.jar','.zip','.war')):
     archive(member,z.read(info),depth+1)
    elif n.upper()=='META-INF/MANIFEST.MF' or n.endswith(('pom.properties','teamcity_build.properties')):
     text=z.read(info).decode(errors='replace');entry['manifests'].append({'member':n,'text':text})
    elif n.endswith(('.xml','.sql','.xsd','.properties','.yaml','.yml')) and (n.startswith(('META-INF/','jetbrains/')) or 'schema' in n.lower() or 'database' in n.lower()):
     resources.append({'location':member,'size':info.file_size,'sha256':sha(z.read(info))})
 except Exception as e:errors.append({'location':loc,'error':str(e)})
for base,dirs,files in os.walk(ROOT,followlinks=False):
 dirs.sort()
 for name in sorted(files):
  p=pathlib.Path(base)/name;rel=p.relative_to(ROOT);loc=str(rel)
  try:
   if p.is_symlink():artifacts.append({'path':loc,'classification':'symlink','target':os.readlink(p),'status':'not_followed'});continue
   before=p.stat();h=hashlib.sha256()
   with p.open('rb') as f:
    for chunk in iter(lambda:f.read(1024*1024),b''):h.update(chunk)
   runtime=excluded(rel);counts['files']+=1
   kind='runtime' if runtime else 'compiled' if name.endswith(('.jar','.war','.class')) else 'archive' if name.endswith('.zip') else 'source' if name.endswith(('.java','.kt','.kts')) else 'configuration' if name.endswith(('.xml','.properties','.conf')) else 'resource'
   row={'path':loc,'size':before.st_size,'sha256':h.hexdigest(),'format':p.suffix,'module':'agent' if loc.startswith('buildAgent/') else 'server','classification':kind,'origin':'installed_distribution_unverified','version':None,'dependencies':None,'status':'hash_only' if runtime else 'inventoried','stable_during_hash':before.st_mtime_ns==p.stat().st_mtime_ns};artifacts.append(row)
   if runtime:counts['excluded_runtime_files']+=1;continue
   if name.lower().endswith(('.jar','.zip','.war','.backup')) and zipfile.is_zipfile(p):archive(loc,p.read_bytes())
   elif name.endswith('.class'):send(loc,p.read_bytes())
  except Exception as e:errors.append({'location':loc,'error':str(e)})
worker.stdin.close();rc=worker.wait();compressor.stdin.close();crc=compressor.wait();raw.close()
dump('artifacts.json',{'root':str(ROOT),'files':artifacts,'archives':archives,'resource_candidates':resources,'errors':errors,'counts':counts,'worker_exit':rc,'compressor_exit':crc,'timestamp':datetime.datetime.now(datetime.timezone.utc).isoformat()})
print(json.dumps(counts),len(errors),rc,crc)
