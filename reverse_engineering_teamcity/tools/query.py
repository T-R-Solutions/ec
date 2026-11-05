"""Read-only symbol search: python3 rea/tools/query.py NAME [--full]."""
import sqlite3,json,zlib,sys,pathlib
p=pathlib.Path(__file__).resolve().parent.parent/'teamcity-reverse-engineering/symbols.sqlite';c=sqlite3.connect(f'file:{p}?mode=ro',uri=True)
for sha,name,record in c.execute('select sha,name,record from classes where name like ? limit 100',('%'+sys.argv[1].replace('.','/')+'%',)):
 r=json.loads(zlib.decompress(record));r['locations']=[x[0] for x in c.execute('select location from locations where sha=?',(sha,))]
 if '--full' not in sys.argv:r={k:r[k] for k in ('name','sha256','super','interfaces','annotations','locations')}|{'methods':[{'name':m['name'],'descriptor':m['descriptor'],'access':m['access']} for m in json.loads(zlib.decompress(record))['methods']]}
 print(json.dumps(r,ensure_ascii=True))
