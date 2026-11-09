from toolbox.adapters import read_report, ReportError


def invocation(spec):
    if not spec.get('config'):
        raise ValueError('opengrep requires explicit local rules')
    args = ['scan', '--json', '--strict', '--disable-version-check']
    if spec.get('mode', 'scan') == 'test':
        args += ['--test', '/src/' + spec['config']]
    else:
        args += ['--config', '/src/' + spec['config'], '--no-git-ignore', '--disable-nosem', '--error']
        args += ['/src/' + path for path in spec['paths']]
    return args, {'SEMGREP_SEND_METRICS': 'off', 'SEMGREP_ENABLE_VERSION_CHECK': '0',
                  'OPENGREP_ENABLE_VERSION_CHECK': '0', 'SEMGREP_SETTINGS_FILE': '/tmp/settings.yml',
                  'XDG_CONFIG_HOME': '/tmp', 'XDG_CACHE_HOME': '/opt/opengrep-cache'}


def classify(code, output):
    if code not in (0, 1):
        return 'operational_failure', f'opengrep exit {code}'
    report = read_report(output / 'stdout.log')
    if isinstance(report.get('results'), dict):
        if code or report.get('config_missing_tests') != [] or report.get('config_with_errors') != [] or not report['results']:
            raise ReportError('missing or failed rule tests')
        checks = [check for result in report['results'].values() for check in result.get('checks', {}).values()]
        if not checks or any(check.get('passed') is not True or check.get('errors') or not check.get('matches') for check in checks):
            raise ReportError('empty or failed rule checks')
        for check in checks:
            if not any(match.get('expected_lines') for match in check['matches'].values()):
                raise ReportError('rule has no positive test')
            if any(match.get('expected_lines') != match.get('reported_lines') for match in check['matches'].values()):
                raise ReportError('rule match differs from oracle')
        return 'success', f'{len(checks)} rule checks'
    findings = report.get('results')
    paths = report.get('paths', {})
    if not isinstance(findings, list) or report.get('errors') != [] or not paths.get('scanned') or paths.get('skipped'):
        raise ReportError('incomplete opengrep scan')
    if any(not item.get('check_id') or item.get('extra', {}).get('is_ignored') for item in findings):
        raise ReportError('invalid or suppressed finding')
    if (code == 1) != bool(findings):
        raise ReportError('opengrep exit disagrees with findings')
    return ('findings' if findings else 'success'), f'{len(findings)} findings'
