import hashlib
import json
import os
from pathlib import Path
import shutil
import subprocess
import time
import tomllib

area = Path('/home/rodrigo/Projetos/ec/.local/build/cargo-cache-refresh-20261109')
home = Path('/home/rodrigo/.cargo')
plan = json.loads((area / 'plan.json').read_text())
journal_path = area / 'execution.json'
if journal_path.exists():
    raise SystemExit('Execution already recorded; inspect journal before resuming')
active = []
for process in Path('/proc').glob('[0-9]*/comm'):
    try:
        name = process.read_text().strip()
    except OSError:
        continue
    if name in {'cargo', 'rustc', 'rustup', 'cargo-geiger', 'cargo-clippy', 'cargo-llvm-cov'}:
        active.append({'pid': process.parent.name, 'name': name})
if active:
    raise SystemExit('Cargo activity detected: ' + json.dumps(active))

def digest(path):
    with path.open('rb') as file:
        return hashlib.file_digest(file, 'sha256').hexdigest()

journal = {'started_at_unix': time.time(), 'scope': str(home), 'removed': [], 'packages': [], 'builds_or_tests_executed': False,
           'installed_geiger_before': digest(home / 'bin/cargo-geiger')}

def save():
    temp = journal_path.with_suffix('.tmp')
    temp.write_text(json.dumps(journal, ensure_ascii=False, indent=2) + '\n')
    temp.replace(journal_path)

save()
for name in ['cache', 'src']:
    path = home / 'registry' / name
    if path.is_symlink() or path.resolve() != path:
        raise SystemExit('Unexpected cache path: ' + str(path))
    count = 0
    size = 0
    if path.exists():
        for base, dirs, files in os.walk(path, followlinks=False):
            for filename in files:
                item = Path(base) / filename
                if item.is_file() and not item.is_symlink():
                    count += 1
                    size += item.stat().st_size
        shutil.rmtree(path)
    journal['removed'].append({'path': str(path), 'files': count, 'bytes': size})
    save()
    print(f'Cleaned {path}: {count} files, {size} bytes', flush=True)

environment = dict(os.environ, CARGO_HOME=str(home), CARGO_NET_OFFLINE='false', RUSTUP_AUTO_INSTALL='0', CARGO_TERM_COLOR='never')
for index, entry in enumerate(plan['packages'], 1):
    manifest = area / 'requests' / entry['name'] / 'Cargo.toml'
    log = area / 'requests' / entry['name'] / 'fetch.log'
    argv = ['rustup', 'run', '1.99.1', 'cargo', 'fetch', '--manifest-path', str(manifest)]
    with log.open('wb') as output:
        result = subprocess.run(argv, cwd=manifest.parent, env=environment, stdout=output, stderr=subprocess.STDOUT)
    item = dict(entry, returncode=result.returncode, log=str(log))
    archives = list((home / 'registry/cache').glob(f"*/{entry['name']}-{entry['version']}.crate"))
    item['archives'] = [{'path': str(path), 'sha256': digest(path)} for path in archives]
    item['checksum_verified'] = any(a['sha256'] == entry['checksum'] for a in item['archives'])
    item['sources'] = [str(path) for path in (home / 'registry/src').glob(f"*/{entry['name']}-{entry['version']}/Cargo.toml")]
    item['complete'] = result.returncode == 0 and item['checksum_verified'] and bool(item['sources'])
    journal['packages'].append(item)
    save()
    print(f"{index}/{len(plan['packages'])}: {entry['name']} {entry['version']} {'prepared' if item['complete'] else 'FAILED; see log'}", flush=True)
journal['installed_geiger_after'] = digest(home / 'bin/cargo-geiger')
journal['finished_at_unix'] = time.time()
journal['complete'] = all(item['complete'] for item in journal['packages'])
save()
print('Preparation complete: ' + str(journal['complete']), flush=True)
raise SystemExit(0 if journal['complete'] else 1)
