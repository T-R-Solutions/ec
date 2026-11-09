"""HTTP real + PostgreSQL descartável; execute somente no container dedicado documentado."""
import concurrent.futures
import hashlib
import http.client
import json
import os
from pathlib import Path
import socket
import subprocess
import tempfile
import threading
import time
import re
import uuid

ROOT = Path(__file__).resolve().parents[4]
BIN = ROOT / '.local/build/ec-server-clean/debug/ec-server-app'
DATABASE = 'ec_publication_' + uuid.uuid4().hex
ENV = dict(os.environ, EC_DATABASE_URL='host=127.0.0.1 port=55439 user=postgres dbname=' + DATABASE, EC_PORT='58139')
BASE = 'http://127.0.0.1:58139/api/v1'
CONTAINER = 'ec-publication-test'


def sql(query, check=True, **parameters):
    variables = []
    for name, value in parameters.items():
        if not re.fullmatch(r'[a-z][a-z0-9_]*', name):
            raise ValueError('invalid psql variable name')
        variables.extend(['-v', name + '=' + str(value)])
    # psql reads stdin so :'name' quotes each variable as a SQL literal.
    return subprocess.run(['docker', 'exec', '-i', CONTAINER, 'psql', '-U', 'postgres', '-d', DATABASE, '-At', *variables, '-v', 'ON_ERROR_STOP=1'], input=query, text=True, capture_output=True, check=check)  # noqa: S603, S607 — Docker do PATH confiável; container literal e banco UUID, argv sem shell.


def cli(*args):
    subprocess.run([str(BIN), *args], env=ENV, check=True, capture_output=True)  # noqa: S603 — Server compilado em caminho fixo; subcomandos e argumentos dos testes locais, sem shell.


def call(method, path, token=None, body=None, failure=None, raw=None):
    headers = {'Content-Type': 'application/json', 'Connection': 'close'}
    if token:
        headers['Authorization'] = 'Bearer ' + token
    if failure:
        headers['X-Ec-Test-Failure'] = failure
    data = raw if raw is not None else (json.dumps(body).encode() if body is not None else None)
    if not path.startswith('/') or path.startswith('//') or any(c in path for c in '\r\n'):
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


def snapshot():
    return sql("SELECT (SELECT count(*) FROM revisions),(SELECT count(*) FROM receipts),(SELECT string_agg(name||':'||published||':'||effective,',' ORDER BY name) FROM projects);").stdout


def start():
    process = subprocess.Popen([str(BIN), 'serve'], env=ENV, stdout=subprocess.DEVNULL, stderr=subprocess.PIPE)  # noqa: S603 — Server compilado em caminho fixo; subcomandos e argumentos dos testes locais, sem shell.
    for _ in range(100):
        if process.poll() is not None:
            raise AssertionError('server exited')
        try:
            with socket.create_connection(('127.0.0.1', 58139), timeout=.1):
                return process
        except OSError:
            time.sleep(.05)
    raise AssertionError('server readiness')


def stop(process):
    process.terminate()
    _, stderr = process.communicate(timeout=10)
    assert not stderr, stderr.decode()


def main():
    subprocess.run(['docker', 'exec', CONTAINER, 'createdb', '-U', 'postgres', DATABASE], check=True)  # noqa: S603, S607 — Docker do PATH confiável do operador; container dedicado literal, banco UUID do harness, argv sem shell.
    cli('migrate')
    # Oracle real: apostrophe/SQL text remains one literal and cannot alter tables.
    probe = "'; DROP TABLE revisions; --"
    assert sql("SELECT :'probe'", probe=probe).stdout.strip() == probe
    assert sql('SELECT count(*) FROM revisions').stdout.strip() == '0'
    cli('migrate')
    print('PASS migration reproduzível e reaplicação sem efeito')
    process = None
    with tempfile.TemporaryDirectory(prefix='ec-publication-', dir=ROOT / '.local/build') as tmp:
        tmp = Path(tmp)
        tokens = {}
        for actor, project, role in [('alice', 'ec', 'admin'), ('bob', 'ec', 'viewer'), ('carol', 'other', 'admin'), ('operator', 'ec', 'operator'), ('expired', 'ec', 'operator'), ('revoked', 'ec', 'operator')]:
            path = tmp / actor
            cli('provision', actor, project, role, str(path), 'local')
            assert path.stat().st_mode & 0o777 == 0o600
            tokens[actor] = path.read_text()
        sql("UPDATE sessions SET expires=clock_timestamp()-interval '1 second' WHERE identity='expired';")
        cli('revoke', 'revoked')
        repo = tmp / 'source'
        subprocess.run(['git', 'init', '-q', str(repo)], check=True)  # noqa: S603, S607 — Git do PATH confiável do operador; comandos literais sobre repositório temporário criado pelo teste, argv sem shell.
        subprocess.run(['git', '-C', str(repo), '-c', 'user.name=Ec Test', '-c', 'user.email=test@example.invalid', 'commit', '--allow-empty', '-qm', 'fixture'], check=True)  # noqa: S603, S607 — Git do PATH confiável do operador; comandos literais sobre repositório temporário criado pelo teste, argv sem shell.
        commit = subprocess.check_output(['git', '-C', str(repo), 'rev-parse', 'HEAD'], text=True).strip()  # noqa: S603, S607 — Git do PATH confiável do operador; comandos literais sobre repositório temporário criado pelo teste, argv sem shell.
        cli('admit-repository', 'ec', str(repo), commit)
        yaml = (ROOT / 'application/integration/tests/fixtures/project-pipeline.yaml').read_text()
        import re
        yaml = re.sub(r'project:.*', 'project: ec', yaml)
        yaml = re.sub(r'repository:.*', 'repository: ' + str(repo), yaml)
        yaml = re.sub(r'commit:.*', 'commit: ' + commit, yaml)
        def payload(key='first', base=0, text=yaml):
            return dict(requestId=key, baseRevision=base, yaml=text)
        path = '/projects/ec/publications'
        try:
            process = start()
            before = snapshot()
            for token in [None, 'invalid', '0' * 64, tokens['expired'], tokens['revoked']]:
                assert call('POST', path, token, payload())[0] == 401
                assert call('GET', '/projects/ec', token)[0] == 401
            assert call('POST', path, tokens['bob'], payload())[0] == 403
            for method, suffix, body in [('GET', '', None), ('POST', '/publications', payload())]:
                assert call(method, '/projects/ec' + suffix, tokens['carol'], body)[0] == 404
                assert call(method, '/projects/missing' + suffix, tokens['carol'], body)[0] == 404
            assert snapshot() == before
            print('PASS sessões ausente/inválida/expirada/revogada, viewer e projeto invisível; SQL sem efeitos')
            for body, expected in [(payload(text='x: ['), 422), (payload(text=yaml+'password: unsupported\n'), 422), (payload(text=yaml.replace(commit, 'a' * 40)), 422), (payload(text=yaml.replace(str(repo), '/unregistered')), 422), (payload(text=yaml.replace('project: ec', 'project: other')), 422), (payload(text='x' * 65537), 413), ({'requestId': 'bad'}, 400)]:
                assert call('POST', path, tokens['alice'], body)[0] == expected
            assert call('POST', path, tokens['alice'], raw=b'x' * 131073)[0] == 413
            assert snapshot() == before
            print('PASS YAML/schema/segredos não suportados/referências/limites/envelope; SQL sem efeitos')
            status, receipt = call('POST', path, tokens['alice'], payload())
            assert status == 200, (status, receipt)
            assert receipt == dict(requestId='first', revision=1, baseRevision=0, sha256=hashlib.sha256(yaml.encode()).hexdigest())
            status, revision = call('GET', '/projects/ec/revisions/1', tokens['bob'])
            assert status == 200 and revision['yaml'].encode() == yaml.encode() and revision['sha256'] == receipt['sha256']
            assert revision['projection']['commit'] == commit
            print('PASS publicação autorizada, bytes exatos, SHA-256 e projeção derivados')
            assert call('POST', path, tokens['alice'], payload())[1] == receipt
            assert call('POST', path, tokens['alice'], payload(text=yaml+'# changed\n'))[0] == 409
            assert call('POST', path, tokens['alice'], payload(text='invalid'))[0] == 409
            assert call('POST', path, tokens['alice'], payload('stale'))[0] == 409
            before = snapshot()
            assert sql("UPDATE revisions SET hash='tampered';", check=False).returncode != 0
            assert sql('DELETE FROM revisions;', check=False).returncode != 0
            assert sql('DELETE FROM receipts;', check=False).returncode != 0
            assert snapshot() == before
            stop(process)
            process = start()
            assert call('POST', path, tokens['alice'], payload())[1] == receipt
            assert call('GET', '/projects/ec/receipts/first', tokens['alice'])[1] == receipt
            assert call('GET', '/projects/ec/revisions/1', tokens['bob'])[1] == revision
            print('PASS imutabilidade SQL, conflito de base/payload e replay após reinício')
            # Hold actual project lock, queue both HTTP workers, observe both blocked before release.
            locker = subprocess.Popen(['docker', 'exec', '-i', CONTAINER, 'psql', '-U', 'postgres', '-d', DATABASE, '-At'], stdin=subprocess.PIPE, stdout=subprocess.PIPE, text=True)  # noqa: S603, S607 — Docker do PATH confiável do operador; container dedicado literal, banco UUID do harness, argv sem shell.
            locker.stdin.write("BEGIN; SELECT name FROM projects WHERE name='ec' FOR UPDATE;\n")
            locker.stdin.flush()
            assert locker.stdout.readline().strip() == 'BEGIN'
            assert locker.stdout.readline().strip() == 'ec'
            with concurrent.futures.ThreadPoolExecutor(2) as pool:
                requests = [pool.submit(call, 'POST', path, tokens['operator'], payload(k, 1)) for k in ['race-a', 'race-b']]
                for _ in range(100):
                    waiting = int(sql("SELECT count(*) FROM pg_stat_activity WHERE datname=current_database() AND wait_event_type='Lock' AND query LIKE 'SELECT published%';").stdout)
                    if waiting == 2:
                        break
                    time.sleep(.05)
                assert waiting == 2, 'two workers must wait on actual lock'
                locker.stdin.write('COMMIT;\n\\q\n')
                locker.stdin.flush()
                locker.communicate(timeout=10)
                results = [r.result() for r in requests]
            assert sorted(s for s, _ in results) == [200, 409], results
            assert call('GET', '/projects/ec', tokens['alice'])[1]['published'] == 2
            assert call('GET', '/projects/ec/revisions/1', tokens['bob'])[1] == revision
            print('PASS duas publicações comprovadamente aguardando lock; uma aceita, uma 409; revisão antiga intacta')
            before = snapshot()
            assert call('POST', path, tokens['alice'], payload('rollback', 2), 'before-commit')[0] == 500
            assert snapshot() == before
            stop(process)
            process = start()
            assert snapshot() == before
            assert call('GET', '/projects/ec/receipts/rollback', tokens['alice'])[0] == 404
            print('PASS falha depois de inserts antes de commit, rollback e reinício sem estado parcial')
            assert call('POST', path, tokens['alice'], payload('crash', 2), 'crash-before-commit')[0] == 0
            assert process.wait(timeout=10) == 86
            process = start()
            assert snapshot() == before
            assert call('GET', '/projects/ec/receipts/crash', tokens['alice'])[0] == 404
            print('PASS término abrupto do Server antes de commit; PostgreSQL reverte e reinício não encontra recibo')
            status, _ = call('POST', path, tokens['alice'], payload('lost', 2), 'after-commit')
            assert status == 0
            stop(process)
            process = start()
            recovered = call('GET', '/projects/ec/receipts/lost', tokens['alice'])[1]
            assert recovered['revision'] == 3
            assert call('POST', path, tokens['alice'], payload('lost', 2))[1] == recovered
            print('PASS commit com recibo de sucesso suprimido, reinício e recuperação por consulta/replay')
            sql("UPDATE grants SET role='viewer' WHERE identity='alice' AND project='ec';")
            before = snapshot()
            assert call('POST', path, tokens['alice'], payload())[0] == 403
            assert call('GET', '/projects/ec/receipts/first', tokens['alice'])[0] == 403
            assert call('DELETE', '/session', tokens['operator'])[0] == 200
            assert call('POST', path, tokens['operator'], payload('post-revoke', 3))[0] == 401
            assert snapshot() == before
            print('PASS replay exige autorização vigente e logout revoga sessão sem efeitos de publicação')
            stored = sql("SELECT string_agg(hash,',') FROM sessions;").stdout
            assert all(token not in stored for token in tokens.values())
            print('PASS tokens só em arquivos 0600; banco guarda hashes; nenhum token emitido nas evidências')
            print(sql('SELECT version();').stdout.strip())
        finally:
            if process and process.poll() is None:
                stop(process)


if __name__ == '__main__':
    try:
        main()
    finally:
        subprocess.run(['docker', 'exec', CONTAINER, 'dropdb', '-U', 'postgres', '--if-exists', '--force', DATABASE], check=True)  # noqa: S603, S607 — Docker do PATH confiável do operador; container dedicado literal, banco UUID do harness, argv sem shell.
