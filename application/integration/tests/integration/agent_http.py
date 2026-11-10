"""T005: Agent/Server Rust, Git, PostgreSQL e processos reais; banco exclusivo."""
import hashlib
import json
import os
from pathlib import Path
import signal
import subprocess
import tempfile
import time

import publication_http as h
import orchestration_http as o

AGENT = Path(os.environ.get('EC_COVERAGE_AGENT_BIN', h.ROOT / '.local/build/ec-agent-target/debug/ec-agent-app'))


def wait(predicate, timeout=35):
    deadline = time.monotonic() + timeout
    while time.monotonic() < deadline:
        value = predicate()
        if value:
            return value
        time.sleep(.025)
    raise AssertionError('observable condition timeout')


def main():
    subprocess.run(['docker', 'exec', h.CONTAINER, 'createdb', '-U', 'postgres', h.DATABASE], check=True)  # noqa: S603, S607 — Docker do PATH confiável do operador; container dedicado literal, banco UUID do harness, argv sem shell.
    server = None
    children = []
    try:
        # Deliberately start at v2, then upgrade with operational CLI.
        h.sql((h.ROOT / 'application/server/backend/migrations/001_publication.sql').read_text())
        h.sql((h.ROOT / 'application/server/backend/migrations/002_orchestration.sql').read_text())
        h.cli('migrate')
        assert h.sql('SELECT string_agg(version::text,\',\' ORDER BY version) FROM ec_migrations').stdout.strip() == '1,2,3'
        print('PASS migration banco v2 -> v3', flush=True)
        with tempfile.TemporaryDirectory(prefix='ec-agent-', dir=h.ROOT / '.local/build') as tmp:
            tmp = Path(tmp)
            repo = tmp / 'source'
            subprocess.run(['git', 'init', '-q', str(repo)], check=True)  # noqa: S603, S607 — Git do PATH confiável do operador; comandos literais sobre repositório temporário criado pelo teste, argv sem shell.
            (repo / 'pinned').write_text('first')
            def commit():
                subprocess.run(['git', '-C', str(repo), 'add', '.'], check=True)  # noqa: S603, S607 — Git do PATH confiável do operador; comandos literais sobre repositório temporário criado pelo teste, argv sem shell.
                subprocess.run(['git', '-C', str(repo), '-c', 'user.name=Ec Test', '-c', 'user.email=test@example.invalid', 'commit', '-qm', 'fixture'], check=True)  # noqa: S603, S607 — Git do PATH confiável do operador; comandos literais sobre repositório temporário criado pelo teste, argv sem shell.
                return subprocess.check_output(['git', '-C', str(repo), 'rev-parse', 'HEAD'], text=True).strip()  # noqa: S603, S607 — Git do PATH confiável do operador; comandos literais sobre repositório temporário criado pelo teste, argv sem shell.
            sha = commit()
            users = {}
            for name, project, role in [('admin', 'ec', 'admin'), ('viewer', 'ec', 'viewer'), ('foreign', 'other', 'admin')]:
                token = tmp / name
                h.cli('provision', name, project, role, str(token), 'local')
                users[name] = token.read_text()
            credential = tmp / 'agent-token'
            h.cli('provision-agent', 'linux-agent', 'ec', str(credential))
            h.cli('admit-repository', 'ec', str(repo), sha)
            server = h.start()
            rev = 0
            def publish(script, timeout=10, artifact='result.txt', commit_sha=sha):
                nonlocal rev
                yaml = '\n'.join(['apiVersion: ec/v1alpha1', 'kind: ProjectPipeline', 'project: ec', 'pipeline: build', 'repository: ' + str(repo), 'commit: ' + commit_sha, 'script: ' + json.dumps(script), 'timeoutSeconds: ' + str(timeout), 'artifact: ' + artifact, ''])
                o.ok('POST', '/projects/ec/publications', users['admin'], dict(requestId='p' + str(rev), baseRevision=rev, yaml=yaml))
                rev += 1
                return rev
            def submit(revision):
                return o.ok('POST', '/projects/ec/runs', users['admin'], dict(requestId='r' + str(revision), revisionId=revision))['runId']
            roots = []
            def agent(allow=True, extra_env=None, reuse=None):
                root = reuse or tmp / ('agent-' + str(len(roots)))
                if reuse is None:
                    root.mkdir(mode=0o700)
                    roots.append(root)
                config = root / 'config.json'
                config.write_text(json.dumps(dict(address='127.0.0.1:58139', root=str(root), credentialFile=str(credential), repositories=[str(repo)] if allow else [], once=True)))
                config.chmod(0o600)
                p = subprocess.Popen([str(AGENT), str(config)], stderr=subprocess.PIPE, stdout=subprocess.DEVNULL, env=dict(os.environ, **(extra_env or {})))  # noqa: S603 — Agent compilado em caminho fixo do checkout; configuração privada criada pelo harness, sem shell.
                children.append(p)
                return p, root
            def finish(p, run, expected):
                _, errors = p.communicate(timeout=45)
                assert p.returncode == 0, errors
                state = o.state(run, users['viewer'])
                assert state['state'] == expected, state
                assert len(state['attempts']) == 1
                assert state['result']['output'] is not None
                return state['attempts'][0]
            first = publish('cat pinned > result.txt; printf out; printf err >&2')
            run = submit(first)
            (repo / 'pinned').write_text('second')
            second_sha = commit()
            h.cli('admit-repository', 'ec', str(repo), second_sha)
            publish('printf wrong > result.txt', commit_sha=second_sha)
            p, root = agent()
            a = finish(p, run, 'succeeded')
            assert (root / f'attempt-{a["attemptId"]}/checkout/pinned').read_text() == 'first'
            url = f'/projects/ec/runs/{run}/attempts/{a["attemptId"]}/output'
            data = o.ok('GET', url, users['viewer'])
            assert data['upload']['receipt']['sha256'] == hashlib.sha256(b'first').hexdigest()
            assert b''.join(bytes.fromhex(v['data']) for v in data['parts']) == b'first'
            assert {v['stream'] for v in data['logs']} == {'stdout', 'stderr'}
            assert h.call('GET', url, users['foreign'])[0] == 404
            assert h.call('GET', url, credential.read_text())[0] == 401
            print('PASS publicação nova não altera SHA, checkout real, streams, artefato e recibo SQL/HTTP; viewer e negação cross-project', flush=True)
            for script, timeout, expected, allow in [
                ('printf failed > result.txt; exit 7', 10, 'failed', True),
                ('kill -TERM $$', 10, 'failed', True),
                ('printf timeout > result.txt; sleep 30 & echo $! > descendant; wait', 1, 'timed_out', True),
                ('printf forbidden > result.txt', 10, 'failed', False),
                ('head -c 17000000 /dev/zero & head -c 17000000 /dev/zero >&2 & wait; printf large > result.txt', 10, 'succeeded', True),
                ('ln -s /etc/passwd result.txt', 10, 'succeeded', True),
            ]:
                run = submit(publish(script, timeout))
                p, root = agent(allow)
                a = finish(p, run, expected)
                detail = o.state(run, users['viewer'])['result']['output']
                if '17000000' in script:
                    assert detail['truncated'] is True
                    assert h.sql("SELECT sum(octet_length(data)) FROM output_logs WHERE attempt_id=:'value0'", value0=a['attemptId']).stdout.strip() == '16777216'
                if 'ln -s' in script:
                    assert detail['artifactError'] is not None
                    assert h.sql("SELECT count(*) FROM output_uploads WHERE attempt_id=:'value0' AND receipt IS NOT NULL", value0=a['attemptId']).stdout.strip() == '0'
                if expected == 'timed_out':
                    pid = int((root / f'attempt-{a["attemptId"]}/checkout/descendant').read_text())
                    proc = Path('/proc') / str(pid) / 'stat'
                    assert not proc.exists() or proc.read_text().split()[2] == 'Z'
            print('PASS nonzero, timeout e descendente terminado, preparação negada sem running, drenagem simultânea >32MiB truncada em 16MiB e symlink rejeitado', flush=True)
            # Cancellation is triggered after independent SQL observes running and file exists.
            run = submit(publish('sleep 60 & echo $! > descendant; wait', 60))
            p, root = agent()
            a_id = wait(lambda: h.sql("SELECT id FROM attempts WHERE run_id=:'value0' AND state='running'", value0=run).stdout.strip())
            child_file = root / f'attempt-{a_id}/checkout/descendant'
            wait(child_file.exists)
            pid = int(child_file.read_text())
            o.ok('POST', f'/projects/ec/runs/{run}/cancel', users['admin'], {})
            finish(p, run, 'cancelled')
            proc = Path('/proc') / str(pid) / 'stat'
            assert not proc.exists() or proc.read_text().split()[2] == 'Z'
            print('PASS cancelamento observado em running, SIGTERM/escalonamento e descendente sem execução', flush=True)
            # Test-only transport hooks drop actual Rust Agent responses after commit.
            run = submit(publish('printf once >> count; printf durable > result.txt; printf log'))
            p, root = agent(extra_env={'EC_AGENT_TEST_DROP': 'reserve,logs,artifact-finish,finish'})
            a = finish(p, run, 'succeeded')
            assert (root / f'attempt-{a["attemptId"]}/checkout/count').read_text() == 'once'
            print('PASS Agent real recupera respostas perdidas reserva/log/artefato/resultado sem reexecutar script', flush=True)
            # Reconnection keeps the same session through a real Server restart.
            run = submit(publish('printf ready > ready; while [ ! -f release ]; do sleep 0.05; done; printf reconnect > result.txt', 30))
            p, root = agent()
            aid = wait(lambda: h.sql("SELECT id FROM attempts WHERE run_id=:'value0' AND state='running'", value0=run).stdout.strip())
            checkout = root / f'attempt-{aid}/checkout'
            wait((checkout / 'ready').exists)
            session_before = h.sql("SELECT session_id FROM attempts WHERE id=:'value0'", value0=aid).stdout
            h.stop(server)
            server = h.start()
            (checkout / 'release').touch()
            finish(p, run, 'succeeded')
            assert h.sql("SELECT session_id FROM attempts WHERE id=:'value0'", value0=aid).stdout == session_before
            print('PASS reconexão Agent e restart real Server preservam sessão, execução e entrega', flush=True)
            # Preparation is observable and paused only in the test build; heartbeat continues.
            gate = tmp / 'prepare-gate'
            run = submit(publish('printf never > result.txt'))
            p, root = agent(extra_env={'EC_AGENT_TEST_PREPARE_GATE': str(gate)})
            wait(Path(str(gate) + '.ready').exists)
            aid = h.sql("SELECT id FROM attempts WHERE run_id=:'value0'", value0=run).stdout.strip()
            before_lease = h.sql("SELECT lease_until FROM attempts WHERE id=:'value0'", value0=aid).stdout
            wait(lambda: h.sql("SELECT lease_until FROM attempts WHERE id=:'value0'", value0=aid).stdout != before_lease)
            o.ok('POST', f'/projects/ec/runs/{run}/cancel', users['admin'], {})
            finish(p, run, 'cancelled')
            assert not list(root.glob('attempt-*/checkout/result.txt'))
            print('PASS cancelamento em preparação, heartbeat independente e ausência de script', flush=True)
            # Lease loss stops actual descendants; server alone allocates ordinal 2.
            run = submit(publish('sleep 60 & echo $! > descendant; wait', 60))
            p, root = agent()
            aid = wait(lambda: h.sql("SELECT id FROM attempts WHERE run_id=:'value0' AND state='running'", value0=run).stdout.strip())
            child_file = root / f'attempt-{aid}/checkout/descendant'
            wait(child_file.exists)
            descendant = int(child_file.read_text())
            h.sql("UPDATE attempts SET lease_until=clock_timestamp()-interval '1 second' WHERE id=:'value0'", value0=aid)
            p.communicate(timeout=15)
            assert p.returncode == 1
            proc = Path('/proc') / str(descendant) / 'stat'
            assert not proc.exists() or proc.read_text().split()[2] == 'Z'
            # New real Agent takes the second attempt. Expire it too, proving bounded retry.
            p, root = agent()
            aid2 = wait(lambda: h.sql("SELECT id FROM attempts WHERE run_id=:'value0' AND ordinal=2 AND state='running'", value0=run).stdout.strip())
            h.sql("UPDATE attempts SET lease_until=clock_timestamp()-interval '1 second' WHERE id=:'value0'", value0=aid2)
            p.communicate(timeout=15)
            assert p.returncode == 1
            wait(lambda: o.state(run, users['viewer'])['state'] == 'failed')
            assert len(o.state(run, users['viewer'])['attempts']) == 2
            print('PASS perda lease termina trabalho real; retry exclusivo Server limitado a duas tentativas', flush=True)
            # Abrupt Agent death: inherited flock rejects restart while descendants survive.
            run = submit(publish('sleep 3 & echo $! > descendant; wait; printf done > result.txt', 20))
            p, root = agent()
            aid = wait(lambda: h.sql("SELECT id FROM attempts WHERE run_id=:'value0' AND state='running'", value0=run).stdout.strip())
            wait((root / f'attempt-{aid}/checkout/descendant').exists)
            p.kill()
            p.communicate(timeout=5)
            blocked, _ = agent(reuse=root)
            blocked.communicate(timeout=5)
            assert blocked.returncode == 1
            wait(lambda: (root / f'attempt-{aid}/checkout/result.txt').exists())
            # flock release is the independent lifecycle oracle, without trusting a PID file.
            import fcntl
            def lock_available():
                with (root / 'agent.lock').open('r') as lock:
                    try:
                        fcntl.flock(lock, fcntl.LOCK_EX | fcntl.LOCK_NB)
                        return True
                    except BlockingIOError:
                        return False
            wait(lock_available)
            restarted, _ = agent(reuse=root)
            restarted.communicate(timeout=30)
            assert restarted.returncode == 0
            state = o.state(run, users['viewer'])
            assert state['state'] == 'succeeded' and len(state['attempts']) == 2
            assert state['attempts'][0]['agentSessionId'] != state['attempts'][1]['agentSessionId']
            print('PASS queda/restart real Agent, exclusão por flock herdado, sessão nova e histórico sem autoridade antiga', flush=True)
            # Direct boundary probes supplement, never substitute, the executable Agent.
            token = o.ok('POST', '/agent/session', credential.read_text(), dict(protocolVersion=1, platform='linux', slots=1))['token']
            run = submit(publish('printf probe > result.txt'))
            a = o.reserve(token)['attempt']
            base = '/agent/attempts/' + str(a['attemptId'])
            def post(action, body, failure=None):
                return h.call('POST', base + '/' + action, token, dict(fence=a['fence'], **body), failure)
            assert post('logs', dict(sequence=2, stream='stdout', data='61'))[0] == 409
            chunk = dict(sequence=1, stream='stdout', data='ff0061')
            assert post('logs', chunk, 'after-commit')[0] == 0
            assert post('logs', chunk)[0] == 200
            assert post('logs', dict(sequence=1, stream='stderr', data='61'))[0] == 409
            assert len(o.ok('GET', base + '/output', token)['logs']) == 1
            artifact = b'proof'
            assert post('artifact-start', dict(size=len(artifact), sha256=hashlib.sha256(artifact).hexdigest()))[0] == 200
            assert post('artifact-finish', {})[0] == 409
            assert post('artifact-chunk', dict(sequence=1, data=b'wrong'.hex()))[0] == 200
            assert post('artifact-finish', {})[0] == 409
            assert h.sql("SELECT count(*) FROM output_uploads WHERE attempt_id=:'value0' AND receipt IS NOT NULL", value0=a['attemptId']).stdout.strip() == '0'
            assert post('artifact-start', dict(size=16777217, sha256='0'*64))[0] == 413
            assert h.call('POST', base + '/logs', users['admin'], dict(fence=a['fence'], **chunk))[0] == 401
            print('PASS resposta perdida log, replay, conflito, lacuna, bytes UTF-8 inválidos, staging incompleto/corrompido/excedido e autenticação separada', flush=True)
            # Integrity success and lost finalize/result responses use a fresh actual attempt.
            o.ok('POST', f'/projects/ec/runs/{run}/cancel', users['admin'], {})
            assert o.terminal(token, a, 'cancelled', None)[0] == 200
            run = submit(publish('printf probe2 > result.txt'))
            a = o.reserve(token)['attempt']
            base = '/agent/attempts/' + str(a['attemptId'])
            assert o.event(token, a, 'running')[0] == 200
            assert post('artifact-start', dict(size=5, sha256=hashlib.sha256(artifact).hexdigest()))[0] == 200
            assert post('artifact-chunk', dict(sequence=1, data=artifact.hex()))[0] == 200
            assert post('artifact-finish', {}, 'after-commit')[0] == 0
            receipt = post('artifact-finish', {})[1]
            payload = dict(outcome='succeeded', exitCode=0, output=dict(lastSequence=0, truncated=False, artifactError=None, signal=None))
            assert post('finish', payload, 'after-commit')[0] == 0
            result = post('finish', payload)[1]
            h.stop(server)
            server = h.start()
            assert post('finish', payload)[1] == result
            assert o.ok('GET', base + '/output', token)['upload']['receipt'] == receipt
            assert post('logs', dict(sequence=1, stream='stdout', data='61'))[0] == 409
            assert post('artifact-chunk', dict(sequence=2, data='61'))[0] == 409
            assert post('finish', dict(payload, exitCode=9))[0] in (409, 422)
            o.invariant()
            print('PASS artefato e resultado após resposta perdida, consulta/replay e restart Server; oráculos SQL sem duplicação', flush=True)
    finally:
        for p in children:
            if p.poll() is None:
                p.terminate()
                p.communicate(timeout=10)
        if server is not None:
            h.stop(server)
        subprocess.run(['docker', 'exec', h.CONTAINER, 'dropdb', '-U', 'postgres', '--force', h.DATABASE], check=True)  # noqa: S603, S607 — Docker do PATH confiável do operador; container dedicado literal, banco UUID do harness, argv sem shell.


if __name__ == '__main__':
    main()
