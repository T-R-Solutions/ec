import gzip,json,pathlib
p=pathlib.Path('rea/teamcity-reverse-engineering/25-evidencias/symbols.jsonl.gz');q=p.with_name('symbols.normalized.jsonl.gz');n=0
with gzip.open(p,'rt') as f,gzip.open(q,'wt',compresslevel=1) as out:
 for line in f:out.write(json.dumps(json.loads(line,strict=False),ensure_ascii=True,separators=(',',':'))+'\n');n+=1
q.replace(p);print(n)
