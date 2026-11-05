import gzip,json,sqlite3,zlib,collections,pathlib
O=pathlib.Path('rea/teamcity-reverse-engineering'); db=sqlite3.connect(O/'symbols.sqlite');db.executescript('PRAGMA journal_mode=OFF; PRAGMA synchronous=OFF; CREATE TABLE IF NOT EXISTS classes (sha TEXT PRIMARY KEY,name TEXT,super TEXT,version INTEGER,record BLOB); CREATE TABLE IF NOT EXISTS locations(location TEXT PRIMARY KEY,sha TEXT); CREATE TABLE IF NOT EXISTS methods(class_sha TEXT,name TEXT,descriptor TEXT,access INTEGER,instructions INTEGER,body_indexed INTEGER);');seen=set();c=collections.Counter();errors=[]
with gzip.open(O/'25-evidencias/symbols.jsonl.gz','rt') as f:
 for line in f:
  r=json.loads(line,strict=False);line=json.dumps(r,ensure_ascii=True);c['class_occurrences']+=1
  if 'error' in r:errors.append(r);continue
  h=r['sha256'];db.execute('INSERT OR REPLACE INTO locations VALUES (?,?)',(r['location'],h))
  if h in seen:continue
  seen.add(h);c['unique_class_bytes']+=1;db.execute('INSERT OR REPLACE INTO classes VALUES (?,?,?,?,?)',(h,r['name'],r.get('super'),r['version'],zlib.compress(line.encode())))
  for m in r['methods']:
   c['unique_class_methods']+=1
   db.execute('INSERT INTO methods VALUES (?,?,?,?,?,?)',(h,m['name'],m['descriptor'],m['access'],m.get('instruction_count'),int(m.get('body_indexed',False))))
   if m.get('body_indexed'):
    c['body_indexed_declarations']+=1;c['methods_with_code']+=bool(m.get('has_code'));c['invoke_sites']+=len(m['calls']);c['field_sites']+=len(m['field_accesses']);c['locals']+=len(m['locals']);c['invokedynamic_sites']+=sum(x['opcode']==186 for x in m['calls']);c['branch_records']+=len(m['branches'])
  c['fields']+=len(r['fields']);c['kotlin_metadata_classes']+='Lkotlin/Metadata;' in r['annotations']
  if c['unique_class_bytes']%10000==0:db.commit()
db.commit();db.executescript('CREATE INDEX IF NOT EXISTS class_name ON classes(name); CREATE INDEX IF NOT EXISTS method_name ON methods(name); CREATE INDEX IF NOT EXISTS method_class ON methods(class_sha); CREATE INDEX IF NOT EXISTS location_sha ON locations(sha);');db.close();(O/'27-cobertura').mkdir(exist_ok=True);(O/'27-cobertura/index-metrics.json').write_text(json.dumps({'counts':dict(c),'errors':errors,'limitations':['Counts deduplicated by class SHA-256, not classloader identity.','Body indexing is syntactic, not human review or behavior confirmation.','Calls denote symbolic bytecode targets; virtual dispatch, inheritance resolution and reflection remain unresolved.','Non-first-party method bodies skipped.','Instruction ordinals are ASM visitor ordinals, not JVM byte offsets.']},indent=2));print(dict(c),'errors',len(errors))
