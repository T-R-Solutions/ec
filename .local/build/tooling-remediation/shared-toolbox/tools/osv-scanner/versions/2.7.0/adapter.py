from toolbox.adapters import read_report, ReportError


def invocation(spec):
    if not spec.get('database') or 'max_database_age_hours' not in spec or not spec.get('config'):
        raise ValueError('osv requires a database snapshot, age policy and explicit config')
    args = ['scan', 'source', '--offline', '--all-packages', '--all-vulns', '--no-call-analysis=rust',
            '--config', '/src/' + spec['config'], '--format', 'json']
    for path in spec['paths']:
        if not path.endswith(('Cargo.lock', 'package-lock.json')):
            raise ValueError('only explicit Cargo.lock/package-lock.json supported')
        args += ['--lockfile', '/src/' + path]
    return args, {'OSV_SCANNER_LOCAL_DB_CACHE_DIRECTORY': '/out/database', 'OSV_SCALIBR_LOCAL_DB_CACHE_DIRECTORY': '/out/database'}


def classify(code, output):
    if code not in (0, 1):
        return 'operational_failure', f'osv exit {code}'
    report = read_report(output / 'stdout.log')
    results = report.get('results')
    if report.get('errors') or report.get('experimental_generic_findings') or not isinstance(results, list) or not results:
        raise ReportError('incomplete OSV report')
    count = 0
    for result in results:
        if result.get('source', {}).get('type') != 'lockfile' or not result.get('packages') or result.get('experimental_pes'):
            raise ReportError('invalid OSV lockfile inventory')
        for item in result['packages']:
            if not all(isinstance(item.get('package', {}).get(key), str) and item['package'][key] for key in ('name', 'version', 'ecosystem')):
                raise ReportError('incomplete package')
            vulnerabilities = item.get('vulnerabilities', [])
            if not isinstance(vulnerabilities, list) or any(not v.get('id') for v in vulnerabilities):
                raise ReportError('invalid vulnerability')
            count += len(vulnerabilities)
    if (code == 1) != (count > 0):
        raise ReportError('OSV exit disagrees with findings')
    return ('findings' if count else 'success'), f'{count} vulnerabilities'
