from toolbox.adapters import read_report, ReportError


def invocation(spec):
    if not spec.get('database') or 'max_database_age_hours' not in spec:
        raise ValueError('grype requires explicit database snapshot and max_database_age_hours policy')
    paths = spec.get('paths', ['.'])
    if len(paths) != 1:
        raise ValueError('grype: exactly one filesystem path is supported')
    args = ['dir:/src/' + paths[0], '--output', 'json']
    if spec.get('config'):
        args += ['--config', '/src/' + spec['config']]
    return args, {'GRYPE_DB_CACHE_DIR': '/out/database', 'GRYPE_DB_AUTO_UPDATE': 'false',
                  'GRYPE_CHECK_FOR_APP_UPDATE': 'false', 'GRYPE_DB_VALIDATE_AGE': 'true',
                  'GRYPE_DB_MAX_ALLOWED_BUILT_AGE': str(spec['max_database_age_hours']) + 'h'}


def classify(code, output):
    # No --fail-on severity is supplied centrally. Any nonzero exit is operational.
    if code != 0:
        return 'operational_failure', f'grype exit {code}'
    report = read_report(output / 'stdout.log')
    if not isinstance(report.get('matches'), list) or not isinstance(report.get('descriptor'), dict):
        raise ReportError('invalid Grype report shape')
    return ('findings' if report['matches'] else 'success'), f'{len(report["matches"])} vulnerability matches'
