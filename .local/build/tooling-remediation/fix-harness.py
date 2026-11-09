from pathlib import Path
p=Path('application/integration/tests/integration/publication_http.py')
s=p.read_text().replace('import urllib.error\nimport urllib.request\n','import re\n')
a=s.index('def sql('); b=s.index('\n\ndef cli', a)
s=s[:a]+'''def sql(query, check=True, **parameters):
    variables = []
    for name, value in parameters.items():
        if not re.fullmatch(r'[a-z][a-z0-9_]*', name):
            raise ValueError('invalid psql variable name')
        variables.extend(['-v', name + '=' + str(value)])
    # psql reads stdin so :'name' quotes each variable as a SQL literal.
    return subprocess.run(['docker', 'exec', '-i', CONTAINER, 'psql', '-U', 'postgres', '-d', DATABASE, '-At', *variables, '-v', 'ON_ERROR_STOP=1'], input=query, text=True, capture_output=True, check=check)  # noqa: S603, S607 — Docker do PATH confiável; container literal e banco UUID, argv sem shell.
''' +s[b:]
a=s.index('    request = urllib.request.Request'); b=s.index('\n\ndef snapshot',a)
s=s[:a]+'''    if not path.startswith('/') or path.startswith('//') or any(c in path for c in '\\r\\n'):
        raise ValueError('expected local API path')
    # Fixed destination, no environment proxy and no automatic redirects.
    connection = http.client.HTTPConnection('127.0.0.1', 58139, timeout=15)
    try:
        connection.request(method, '/api/v1' + path, body=data, headers=headers)
        response = connection.getresponse()
        content = response.read()
        if response.status < 400:
            assert response.headers['Cache-Control'] == 'no-store'
            return response.status, json.loads(content)
        if failure == 'after-commit':
            return response.status, None
        value = json.loads(content)
        assert set(value) == {'code', 'message', 'requestId'}
        return response.status, value
    except (http.client.RemoteDisconnected, ConnectionResetError):
        assert failure in ['after-commit', 'crash-before-commit']
        return 0, None
    finally:
        connection.close()
''' +s[b:]
p.write_text(s)
p=Path('application/integration/tests/integration/orchestration_http.py');s=p.read_text()
for var in ['a','b']:
 s=s.replace('WHERE id=" + str('+var+"['attemptId']) + ';')", 'WHERE id=:\'attempt\'", attempt='+var+"['attemptId'])")
s=s.replace("h.sql('SELECT lease_until<=clock_timestamp() FROM attempts WHERE id=' + str(a['attemptId']) + ';')", '''h.sql("SELECT lease_until<=clock_timestamp() FROM attempts WHERE id=:'attempt'", attempt=a['attemptId'])''')
s=s.replace("h.sql('SELECT state FROM attempts WHERE id=' + str(a['attemptId']) + ';')", '''h.sql("SELECT state FROM attempts WHERE id=:'attempt'", attempt=a['attemptId'])''')
s=s.replace('WHERE id=\'" + sa2[\'agentSessionId\'] + "\';")', 'WHERE id=:\'session\'", session=sa2[\'agentSessionId\'])')
p.write_text(s)
