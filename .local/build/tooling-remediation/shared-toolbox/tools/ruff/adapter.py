from toolbox.adapters import read_report, ReportError


def invocation(spec):
    args = ['check', '--no-fix', '--no-cache', '--output-format=json']
    if spec.get('config'):
        args += ['--config', '/src/' + spec['config']]
    return args + ['--'] + spec.get('paths', ['.']), {}


def classify(code, output):
    if code not in [0, 1]:
        return 'operational_failure', f'ruff exit {code}'
    report = read_report(output / 'stdout.log')
    if not isinstance(report, list) or any(not isinstance(item, dict) or 'code' not in item for item in report):
        raise ReportError('invalid Ruff report shape')
    if (code == 1) != bool(report):
        raise ReportError('Ruff report and exit code disagree')
    return ('findings' if report else 'success'), f'{len(report)} diagnostics'
