import concurrent.futures
import json
import time
import tomllib
import urllib.request
from pathlib import Path

root = Path('/home/rodrigo/Projetos/ec')
area = root / '.local/build/cargo-cache-refresh-20261109'
summary = json.loads((root / 'docs/validation/geiger-coverage-remediation-20261109/system-cache-summary.json').read_text())
lock = tomllib.loads((root / 'tools/quality/vendor/cargo-geiger/Cargo.lock').read_text())
missing = set(summary['not_found'])
names = sorted({p['name'] for p in lock['package'] if f"{p['name']}-{p['version']}" in missing})


def metadata(name):
    url = 'https://crates.io/api/v1/crates/' + name
    request = urllib.request.Request(url, headers={'User-Agent': 'Ec local Cargo cache preparation'})
    with urllib.request.urlopen(request, timeout=45) as response:
        data = json.load(response)
    version = data['crate']['max_stable_version']
    if not version:
        raise ValueError('No stable version: ' + name)
    details = next(v for v in data['versions'] if v['num'] == version and not v['yanked'])
    return {'name': name, 'version': version, 'checksum': details['checksum'], 'rust_version': details.get('rust_version'), 'metadata_url': url}


results = []
with concurrent.futures.ThreadPoolExecutor(max_workers=4) as pool:
    futures = {pool.submit(metadata, name): name for name in names}
    for future in concurrent.futures.as_completed(futures):
        results.append(future.result())
        if len(results) % 20 == 0:
            print(f'Registry metadata: {len(results)}/{len(names)}', flush=True)
results.sort(key=lambda item: item['name'])
(area / 'plan.json').write_text(json.dumps({'source': 'crates.io API, latest non-yanked stable versions', 'packages': results}, ensure_ascii=False, indent=2) + '\n')
for entry in results:
    directory = area / 'requests' / entry['name']
    directory.mkdir(parents=True, exist_ok=True)
    (directory / 'src').mkdir(exist_ok=True)
    (directory / 'src/lib.rs').write_text('')
    (directory / 'Cargo.toml').write_text('[workspace]\n\n[package]\nname = "ec-cache-request"\nversion = "0.0.0"\nedition = "2024"\n\n[dependencies]\nrequested = { package = '+json.dumps(entry['name'])+', version = "='+entry['version']+'", default-features = false }\n')
print(f'Prepared {len(results)} independent fetch requests; no Cargo build/test executed.', flush=True)
