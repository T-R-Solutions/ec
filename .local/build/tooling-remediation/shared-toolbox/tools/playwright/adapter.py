from toolbox.adapters import read_report, ReportError


def invocation(spec):
    return ['test', '--config', '/out/work/' + spec['config']], {
        'PLAYWRIGHT_JSON_OUTPUT_FILE': '/out/native.json',
        'PLAYWRIGHT_HTML_OUTPUT_DIR': '/out/html',
        'PLAYWRIGHT_HTML_OPEN': 'never',
        'PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD': '1',
        'TOOLBOX_VARIANT': spec.get('variant', 'api'),
        **spec.get('env', {}),
    }


def classify(code, output):
    if code not in [0, 1]:
        return 'operational_failure', f'playwright exit {code}'
    report = read_report(output / 'native.json')
    if report.get('errors'):
        return 'operational_failure', 'Playwright reported global/configuration errors'
    stats = report['stats']
    if not isinstance(stats, dict) or not all(type(stats.get(k)) is int for k in ['expected', 'unexpected', 'skipped', 'flaky']):
        raise ReportError('invalid Playwright statistics')
    if sum(stats[k] for k in ['expected', 'unexpected', 'flaky']) == 0:
        raise ReportError('no tests executed (all skipped or no tests found)')
    if code == 1 and stats['unexpected'] == 0:
        raise ReportError('nonzero Playwright exit without failed test evidence')
    if code == 0 and stats['unexpected']:
        raise ReportError('Playwright report and exit code disagree')
    return ('findings' if stats['unexpected'] else 'success'), str(stats)
