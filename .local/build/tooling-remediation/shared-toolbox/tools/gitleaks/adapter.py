from toolbox.adapters import read_report, ReportError


def invocation(spec):
    args = ['git' if spec.get('mode', 'files') == 'history' else 'dir',
            '--exit-code=42', '--redact=100', '--report-format=json', '--report-path=/out/native.json']
    if spec.get('config'):
        args += ['--config', '/src/' + spec['config']]
    paths = spec.get('paths', ['.'])
    if len(paths) != 1:
        raise ValueError('gitleaks: select exactly one source path per invocation')
    return args + ['--'] + paths, {'GIT_CONFIG_COUNT': '1', 'GIT_CONFIG_KEY_0': 'safe.directory', 'GIT_CONFIG_VALUE_0': '/src'}


def classify(code, output):
    if code not in [0, 42]:
        return 'operational_failure', f'gitleaks exit {code}'
    report = read_report(output / 'native.json')
    if not isinstance(report, list) or any(not isinstance(item, dict) or 'RuleID' not in item for item in report):
        raise ReportError('invalid Gitleaks report shape')
    if (code == 42) != bool(report):
        raise ReportError('Gitleaks report and exit code disagree')
    return ('findings' if report else 'success'), f'{len(report)} leaks'
