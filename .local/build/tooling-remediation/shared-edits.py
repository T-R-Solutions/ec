from pathlib import Path
p=Path('.local/build/tooling-remediation/shared-toolbox/toolbox/config.py');s=p.read_text().replace("if name != 'gitleaks' and 'mode' in spec:","if name == 'opengrep' and spec.get('mode', 'scan') not in ('scan', 'test'):\n            raise ConfigError('opengrep mode: scan or test')\n        if name not in ('gitleaks', 'opengrep') and 'mode' in spec:");p.write_text(s)
p=p.with_name('runner.py');s=p.read_text().replace('import hashlib','import datetime as dt\nimport hashlib',1).replace("if name == 'grype':","if name in ('grype', 'osv-scanner'):").replace("raise ValueError('Grype database snapshot not selected')","raise ValueError('database snapshot not selected')")
s=s.replace("shutil.copytree(database, output / 'database')", """if name == 'osv-scanner':
                            age = (dt.datetime.now(dt.timezone.utc) - dt.datetime.fromisoformat(metadata['prepared_at'].replace('Z', '+00:00'))).total_seconds()
                            if not 0 <= age <= spec['max_database_age_hours'] * 3600:
                                raise ValueError('OSV database expired or future dated')
                        shutil.copytree(database, output / 'database')""")
s=s.replace("status, reason = classify(adapter(name, spec['version']), execution, output)","""status, reason = classify(adapter(name, spec['version']), execution, output)
                    if database and digest(tree_manifest(output / 'database')) != spec['database']:
                        raise ValueError('scanner changed database snapshot')
                    if name == 'osv-scanner' and status in ('success', 'findings'):
                        report = read_json(output / 'stdout.log')
                        expected = sorted('/src/' + path for path in spec['paths'])
                        if sorted(item['source']['path'] for item in report['results']) != expected:
                            raise ValueError('OSV did not scan every explicit lockfile')""")
p.write_text(s)
p=p.with_name('prepare.py');s=p.read_text().replace('import hashlib','import io\nimport zipfile\nimport hashlib',1)
s=s.replace("    if name != 'grype':", "    if name == 'osv-scanner':\n        return update_osv_database(store, spec, lock)\n    if name != 'grype':")
s+='''

def update_osv_database(store, spec, lock):
    record = lock.get('tools', {}).get('osv-scanner')
    if not record or record['version'] != spec['version']:
        raise ValueError('prepare selected OSV image first')
    staging = store.preparation_dir('osv-scanner')
    database = staging / 'data'
    sources = []
    for ecosystem in ('crates.io', 'npm'):
        url = 'https://osv-vulnerabilities.storage.googleapis.com/' + ecosystem + '/all.zip'
        content = fetch(url)
        with zipfile.ZipFile(io.BytesIO(content)) as archive:
            if not archive.namelist() or archive.testzip() is not None:
                raise ValueError('invalid OSV database archive')
        path = database / 'osv-scanner' / ecosystem / 'all.zip'
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_bytes(content)
        sources.append({'url': url, 'sha256': hashlib.sha256(content).hexdigest()})
    manifest = tree_manifest(database)
    identity = digest(manifest)
    target = store.database('osv-scanner', spec['version'], identity)
    with store.lock(['database', 'osv-scanner']):
        if not target.exists():
            target.parent.mkdir(parents=True, exist_ok=True)
            database.rename(target)
            atomic_json(target.parent / (identity + '.json'), {'schema': 1, 'tool': 'osv-scanner',
                        'version': spec['version'], 'image': record['id'], 'id': identity,
                        'prepared_at': now(), 'files': manifest, 'sources': sources,
                        'note': 'Downloaded via HTTPS from official OSV storage; local hashes bind snapshot bytes, not signatures.'})
    return {'database': identity, 'path': str(target), 'configuration_change_required': True}
'''
p.write_text(s)
