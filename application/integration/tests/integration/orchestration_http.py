"""T004: HTTP/processos reais, PostgreSQL descartável e oráculos SQL independentes.

Reutiliza somente transporte e lifecycle do harness de publicação; não modela scheduler.
"""

import concurrent.futures
import hashlib
import re
import subprocess
import tempfile
import time
from pathlib import Path

import publication_http as h

RUNS = "/projects/ec/runs"
AGENT = "/agent"


def ok(method, path, token, body=None, failure=None):
    status, value = h.call(method, path, token, body, failure)
    assert status == 200, (method, path, status, value)
    return value


def state(run, token):
    return ok("GET", RUNS + "/" + str(run), token)


def reserve(token):
    return ok("POST", AGENT + "/reserve", token, {})


def event(token, a, action, **extra):
    return h.call(
        "POST",
        AGENT + "/attempts/" + str(a["attemptId"]) + "/" + action,
        token,
        dict(fence=a["fence"], **extra),
    )


def terminal(token, a, outcome="succeeded", exit_code=0):
    return event(token, a, "result", outcome=outcome, exitCode=exit_code)


def invariant():
    # SQL is an independent oracle over all persisted attempts and runs.
    query = """
    SELECT count(*) FROM (
      SELECT run_id FROM attempts WHERE state IN ('assigned','running') GROUP BY run_id HAVING count(*)>1
      UNION ALL SELECT min(run_id) FROM attempts WHERE state IN ('assigned','running') GROUP BY agent HAVING count(*)>1
      UNION ALL SELECT run_id FROM attempts GROUP BY run_id HAVING count(*)>2 OR max(ordinal)<>count(*)
      UNION ALL SELECT a.run_id FROM attempts a JOIN runs r ON r.id=a.run_id WHERE a.state IN ('assigned','running') AND r.state NOT IN ('assigned','running')
      UNION ALL SELECT id FROM runs WHERE state IN ('succeeded','failed','timed_out','cancelled') AND result IS NULL
    ) violations;
    """
    assert h.sql(query).stdout.strip() == "0"


def snapshot():
    return h.sql(
        "SELECT json_build_array((SELECT json_agg(r ORDER BY id) FROM runs r),(SELECT json_agg(a ORDER BY id) FROM attempts a),(SELECT json_agg(r ORDER BY run_id) FROM run_receipts r),(SELECT json_agg(s ORDER BY id) FROM agent_sessions s),(SELECT json_agg(g ORDER BY name) FROM agents g));"
    ).stdout


def wait_for(predicate):
    deadline = time.monotonic() + 4
    while time.monotonic() < deadline:
        if predicate():
            return
        time.sleep(0.02)
    raise AssertionError("condition deadline")


class Gate:
    """Independent PostgreSQL connection holds the exact operational guard.

    Requests are observed blocked in pg_stat_activity before releasing the guard;
    order is established by enqueueing one waiter at a time, not sleep timing.
    """

    def __enter__(self):
        self.process = subprocess.Popen(
            [
                "docker",
                "exec",
                "-i",
                h.CONTAINER,
                "psql",
                "-U",
                h.POSTGRES["user"],
                "-d",
                h.DATABASE,
                "-At",
                "-v",
                "ON_ERROR_STOP=1",
            ],
            stdin=subprocess.PIPE,
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
            text=True,
        )  # Docker do PATH confiável do operador; container dedicado literal, banco UUID do harness, argv sem shell.
        self.process.stdin.write("BEGIN; SELECT pg_advisory_xact_lock(746232); SELECT 'locked';\n")
        self.process.stdin.flush()
        while self.process.stdout.readline().strip() != "locked":
            assert self.process.poll() is None
        return self

    def waiting(self, n):
        wait_for(
            lambda: int(
                h.sql(
                    "SELECT count(*) FROM pg_stat_activity WHERE datname=current_database() AND application_name='ec-http' AND wait_event='advisory';"
                ).stdout.strip()
            )
            == n
        )

    def __exit__(self, *args):
        self.process.stdin.write("COMMIT;\n")
        self.process.stdin.flush()
        _, error = self.process.communicate(timeout=10)
        assert self.process.returncode == 0, error


def race(first, second):
    with concurrent.futures.ThreadPoolExecutor(2) as pool:
        with Gate() as gate:
            a = pool.submit(first)
            gate.waiting(1)
            b = pool.submit(second)
            gate.waiting(2)
        return a.result(), b.result()


def main():
    subprocess.run(
        ["docker", "exec", h.CONTAINER, "createdb", "-U", h.POSTGRES["user"], h.DATABASE],
        check=True,
    )  # Docker do PATH confiável do operador; container dedicado literal, banco UUID do harness, argv sem shell.
    process = None
    try:
        # Actual upgrade from v1 with data, not merely fresh schema creation.
        h.sql((h.ROOT / "application/server/backend/migrations/001_publication.sql").read_text())
        with tempfile.TemporaryDirectory(
            prefix="ec-orchestration-", dir=h.temporary_parent()
        ) as tmp:
            tmp = Path(tmp)
            users = {}
            for who, project, role in [
                ("alice", "ec", "admin"),
                ("bob", "ec", "viewer"),
                ("op", "ec", "operator"),
                ("other", "other", "admin"),
                ("expired", "ec", "operator"),
                ("revoked", "ec", "admin"),
            ]:
                path = tmp / who
                h.cli("provision", who, project, role, str(path), "local")
                users[who] = path.read_text()
            h.sql(
                "UPDATE sessions SET expires=clock_timestamp()-interval '1 second' WHERE identity='expired';"
            )
            h.cli("revoke", "revoked")
            h.sql(
                "INSERT INTO revisions VALUES('other',1,convert_to('historical v1 bytes','UTF8'),'historical-hash','{}'); UPDATE projects SET published=1,effective=1 WHERE name='other'; INSERT INTO receipts VALUES('other','other','legacy',0,1);"
            )
            legacy = h.sql(
                "SELECT row_to_json(r) FROM revisions r; SELECT row_to_json(r) FROM receipts r; SELECT row_to_json(p) FROM projects p ORDER BY name;"
            ).stdout
            before = h.sql("SELECT count(*) FROM sessions; SELECT count(*) FROM grants;").stdout
            h.cli("migrate")
            h.cli("migrate")
            assert (
                before
                == h.sql("SELECT count(*) FROM sessions; SELECT count(*) FROM grants;").stdout
            )
            assert (
                legacy
                == h.sql(
                    "SELECT row_to_json(r) FROM revisions r; SELECT row_to_json(r) FROM receipts r; SELECT row_to_json(p) FROM projects p ORDER BY name;"
                ).stdout
            )
            assert (
                h.sql(
                    "SELECT string_agg(version::text,',' ORDER BY version) FROM ec_migrations;"
                ).stdout.strip()
                == "1,2,3"
            )
            print(
                "PASS migration 001 com dados -> 002, reaplicação, revisões/ponteiros/recibos/sessões/grants preservados",
                flush=True,
            )
            admin = users["alice"]
            repo = tmp / "source"
            subprocess.run(["git", "init", "-q", str(repo)], check=True)  # Git do PATH confiável do operador; comandos literais sobre repositório temporário criado pelo teste, argv sem shell.
            subprocess.run(
                [
                    "git",
                    "-C",
                    str(repo),
                    "-c",
                    "user.name=Ec Test",
                    "-c",
                    "user.email=test@example.invalid",
                    "commit",
                    "--allow-empty",
                    "-qm",
                    "fixture",
                ],
                check=True,
            )  # Git do PATH confiável do operador; comandos literais sobre repositório temporário criado pelo teste, argv sem shell.
            commit = subprocess.check_output(
                ["git", "-C", str(repo), "rev-parse", "HEAD"], text=True
            ).strip()  # Git do PATH confiável do operador; comandos literais sobre repositório temporário criado pelo teste, argv sem shell.
            h.cli("admit-repository", "ec", str(repo), commit)
            yaml = (
                h.ROOT / "application/integration/tests/fixtures/project-pipeline.yaml"
            ).read_text()
            for key, text in [("project", "ec"), ("repository", str(repo)), ("commit", commit)]:
                yaml = re.sub(key + r":.*", key + ": " + text, yaml)
            credentials = {}
            for name, project in [
                ("linux-a", "ec"),
                ("linux-b", "ec"),
                ("foreign", "other"),
                ("disabled", "ec"),
            ]:
                path = tmp / name
                h.cli("provision-agent", name, project, str(path))
                assert path.stat().st_mode & 0o777 == 0o600
                credentials[name] = path.read_text()
            h.cli("revoke-agent", "disabled")
            process = h.start()

            def session(name="linux-a", failure=None):
                return ok(
                    "POST",
                    AGENT + "/session",
                    credentials[name],
                    dict(protocolVersion=1, platform="linux", slots=1),
                    failure,
                )

            sa = session()
            ta = sa["token"]
            tb = session("linux-b")["token"]
            foreign = session("foreign")["token"]

            def submit(key, revision=1, token=admin, failure=None):
                return h.call(
                    "POST", RUNS, token, dict(requestId=key, revisionId=revision), failure
                )

            def new(key):
                status, receipt = submit(key)
                assert status == 200, receipt
                return receipt["runId"]

            def cancel(run):
                return ok("POST", RUNS + "/" + str(run) + "/cancel", users["op"], {})

            ok(
                "POST",
                "/projects/ec/publications",
                admin,
                dict(requestId="p1", baseRevision=0, yaml=yaml),
            )
            r1 = new("fixed")
            receipt = submit("fixed")[1]
            assert receipt == ok("GET", "/projects/ec/run-receipts/fixed", admin)
            assert receipt == dict(
                requestId="fixed",
                runId=r1,
                revisionId=1,
                configHash=hashlib.sha256(yaml.encode()).hexdigest(),
                commit=commit,
            )
            # A different admitted full Git SHA and YAML publication cannot alter the run.
            subprocess.run(
                [
                    "git",
                    "-C",
                    str(repo),
                    "-c",
                    "user.name=Ec Test",
                    "-c",
                    "user.email=test@example.invalid",
                    "commit",
                    "--allow-empty",
                    "-qm",
                    "second",
                ],
                check=True,
            )  # Git do PATH confiável do operador; comandos literais sobre repositório temporário criado pelo teste, argv sem shell.
            commit2 = subprocess.check_output(
                ["git", "-C", str(repo), "rev-parse", "HEAD"], text=True
            ).strip()  # Git do PATH confiável do operador; comandos literais sobre repositório temporário criado pelo teste, argv sem shell.
            h.cli("admit-repository", "ec", str(repo), commit2)
            yaml2 = yaml.replace(commit, commit2)
            ok(
                "POST",
                "/projects/ec/publications",
                admin,
                dict(requestId="p2", baseRevision=1, yaml=yaml2),
            )
            fixed = state(r1, users["bob"])
            assert (
                fixed["revisionId"] == 1
                and fixed["commit"] == commit
                and fixed["configHash"] == receipt["configHash"]
            )
            assert submit("fixed", 2)[0] == 409
            assert submit("missing", 99)[0] == 404
            assert (
                h.call("POST", RUNS, admin, dict(requestId="bad", revisionId=1, commit=commit2))[0]
                == 400
            )
            assert h.sql("SELECT count(*) FROM runs;").stdout.strip() == "1"
            replay = race(lambda: submit("concurrent"), lambda: submit("concurrent"))
            assert replay[0] == replay[1] and replay[0][0] == 200
            cancel(replay[0][1]["runId"])
            print(
                "PASS revisão/hash/SHA fixos após nova publicação, replay concorrente e conflito sem duplicação",
                flush=True,
            )
            before = snapshot()
            for token, status in [
                (None, 401),
                ("0" * 64, 401),
                (users["bob"], 403),
                (users["other"], 404),
                (users["expired"], 401),
                (users["revoked"], 401),
                (ta, 401),
                (credentials["linux-a"], 401),
            ]:
                assert submit("denied", token=token)[0] == status
                assert h.call("POST", RUNS + "/" + str(r1) + "/cancel", token, {})[0] == status
            assert h.call("GET", RUNS + "/" + str(r1), users["other"])[0] == 404
            for token in [None, admin, credentials["linux-a"], "0" * 64]:
                assert h.call("POST", AGENT + "/reserve", token, {})[0] == 401
            assert (
                h.call(
                    "POST",
                    AGENT + "/session",
                    credentials["disabled"],
                    dict(protocolVersion=1, platform="linux", slots=1),
                )[0]
                == 401
            )
            assert (
                h.call(
                    "POST",
                    AGENT + "/session",
                    credentials["linux-a"],
                    dict(protocolVersion=2, platform="linux", slots=1),
                )[0]
                == 422
            )
            assert reserve(foreign)["attempt"] is None
            assert snapshot() == before
            print(
                "PASS autenticação, papéis, separação Agent/usuário, capabilities, revogação: nenhum efeito negado",
                flush=True,
            )
            # Different slots competing for the only queued work.
            claims = race(lambda: reserve(ta), lambda: reserve(tb))
            assert claims[0]["attempt"]["runId"] == r1 and claims[1]["attempt"] is None
            a = claims[0]["attempt"]
            assert claims[0]["configuration"]["commit"] == commit
            assert (
                ok("GET", AGENT + "/attempts/" + str(a["attemptId"]), ta)["reservationReceipt"]
                == a["reservationReceipt"]
            )
            assert h.call("GET", AGENT + "/attempts/" + str(a["attemptId"]), tb)[0] == 404
            # Same slot with another queued run: replay same durable reservation.
            r2 = new("same-slot")
            claims = race(lambda: reserve(ta), lambda: reserve(ta))
            assert (
                claims[0]["attempt"]["attemptId"]
                == claims[1]["attempt"]["attemptId"]
                == a["attemptId"]
            )
            assert state(r2, admin)["state"] == "queued"
            cancel(r2)
            invariant()
            print(
                "PASS conexões concorrentes: mesmo trabalho/diferentes slots e mesmo slot/dois trabalhos, oráculos SQL",
                flush=True,
            )
            assert terminal(ta, a)[0] == 409  # success cannot bypass preparation/running
            assert event(ta, dict(a, fence=a["fence"] + 1), "heartbeat")[0] == 409
            assert event(ta, a, "heartbeat")[0] == 200
            old_lease = a["leaseUntil"]
            a = ok("GET", AGENT + "/attempts/" + str(a["attemptId"]), ta)
            assert a["leaseUntil"] > old_lease
            assert event(ta, a, "running")[0] == 200
            assert event(ta, a, "running")[0] == 200
            before_restart = state(r1, admin)
            h.stop(process)
            process = h.start()
            assert state(r1, admin) == before_restart
            assert reserve(ta)["attempt"]["attemptId"] == a["attemptId"]
            print(
                "PASS heartbeat válido, fence rejeitado, transições, reinício real preserva lease e reserva",
                flush=True,
            )
            status, result = terminal(ta, a)
            assert status == 200 and result["state"] == "succeeded"
            assert terminal(ta, a) == (200, result)
            assert terminal(ta, a, "failed", 9)[0] == 409
            assert event(ta, a, "heartbeat")[0] == 409
            assert cancel(r1) == dict(effective=False, state="succeeded")
            print(
                "PASS recibo de resultado/replay exato, payload divergente e terminal protegido",
                flush=True,
            )
            # Expiry before reassignment; direct SQL shortens deadline, real clock crosses it.
            run = new("expiry")
            a = reserve(ta)["attempt"]
            h.stop(process)
            process = None
            h.sql(
                "UPDATE attempts SET lease_until=clock_timestamp()+interval '0.3 seconds' WHERE id=:'attempt'",
                attempt=a["attemptId"],
            )
            time.sleep(0.4)
            # Startup reconciliation expires it; old messages still rejected.
            process = h.start()
            assert event(ta, a, "heartbeat")[0] == 409
            assert terminal(ta, a)[0] == 409
            assert state(run, admin)["state"] == "queued"
            b = reserve(tb)["attempt"]
            assert (
                b["ordinal"] == 2 and b["fence"] > a["fence"] and b["attemptId"] != a["attemptId"]
            )
            assert event(ta, a, "running")[0] == 409
            assert terminal(ta, a)[0] == 409
            h.sql(
                "UPDATE attempts SET lease_until=clock_timestamp()-interval '1 second' WHERE id=:'attempt'",
                attempt=b["attemptId"],
            )
            assert event(tb, b, "heartbeat")[0] == 409
            assert reserve(ta)["attempt"] is None
            run_state = state(run, admin)
            assert (
                run_state["state"] == "failed"
                and run_state["result"]["cause"] == "attempts_exhausted"
            )
            assert [x["state"] for x in run_state["attempts"]] == ["lease_lost", "lease_lost"]
            invariant()
            print(
                "PASS expiração real/restart, heartbeat tardio não ressuscita, retry apenas perda, máximo duas tentativas",
                flush=True,
            )
            for outcome, code, expected in [
                ("failed", 17, "failed"),
                ("timed_out", None, "timed_out"),
                ("preparation_failed", None, "failed"),
            ]:
                run = new(outcome.replace("_", "-"))
                a = reserve(ta)["attempt"]
                if outcome != "preparation_failed":
                    assert event(ta, a, "running")[0] == 200
                assert terminal(ta, a, outcome, code)[0] == 200
                assert state(run, admin)["state"] == expected
                assert len(state(run, admin)["attempts"]) == 1
                assert reserve(tb)["attempt"] is None
            print("PASS falha de script, timeout e preparação sem retry automático", flush=True)
            # Session replacement invalidates old authority; new session can query old receipt.
            run = new("sessions")
            a = reserve(ta)["attempt"]
            sa2 = session()
            ta2 = sa2["token"]
            assert sa2["agentSessionId"] != sa["agentSessionId"]
            assert event(ta, a, "heartbeat")[0] == 401
            assert event(ta2, a, "heartbeat")[0] == 409
            assert terminal(ta2, a)[0] == 409
            assert (
                ok("GET", AGENT + "/attempts/" + str(a["attemptId"]), ta2)["agentSessionId"]
                == sa["agentSessionId"]
            )
            b = reserve(ta2)["attempt"]
            assert b["ordinal"] == 2 and b["fence"] > a["fence"]
            cancel(run)
            assert terminal(ta2, b, "cancelled", None)[1]["state"] == "cancelled"
            ta = ta2
            h.sql(
                "UPDATE agent_sessions SET expires=clock_timestamp()-interval '1 second' WHERE id=:'session'",
                session=sa2["agentSessionId"],
            )
            assert reserve_safe_status(ta) == 401
            ta = session()["token"]
            print(
                "PASS sessão nova invalida anterior, sessão expirada, reconexão consulta histórico/recibos",
                flush=True,
            )
            # Both deterministic concurrent orderings over the shared PostgreSQL guard.
            for cancel_first in [True, False]:
                run = new("race-" + str(cancel_first))
                a = reserve(ta)["attempt"]
                assert event(ta, a, "running")[0] == 200
                def finish(attempt=a):
                    return terminal(ta, attempt)

                def request_cancel(run_id=run):
                    return cancel(run_id)
                x, y = (
                    race(request_cancel, finish) if cancel_first else race(finish, request_cancel)
                )
                if cancel_first:
                    assert x["effective"] and x["state"] == "running"
                    assert y[0] == 200 and y[1]["state"] == "cancelled"
                    assert state(run, admin)["state"] == "cancelled"
                else:
                    assert x[1]["state"] == "succeeded"
                    assert y == dict(effective=False, state="succeeded")
                invariant()
            run = new("cancel-queued")
            assert cancel(run)["state"] == "cancelled"
            assert state(run, admin)["attempts"] == []
            run = new("cancel-expired")
            a = reserve(ta)["attempt"]
            assert cancel(run)["state"] == "assigned"
            assert state(run, admin)["state"] == "assigned"
            assert state(run, admin)["cancelRequested"] is True
            h.stop(process)
            process = None
            h.sql(
                "UPDATE attempts SET lease_until=clock_timestamp()-interval '1 second' WHERE id=:'attempt'",
                attempt=a["attemptId"],
            )
            process = h.start()
            assert state(run, admin)["state"] == "cancelled"
            assert reserve(ta)["attempt"] is None
            print(
                "PASS corridas cancelamento/conclusão nas duas ordens observadas bloqueadas, cancelamento em fila/assigned e recuperação da intenção",
                flush=True,
            )
            # Pause only the test-build periodic worker; production clock/guards remain intact.
            h.stop(process)
            h.ENV["EC_TEST_PAUSE_RECONCILER"] = "1"
            process = h.start()
            run = new("expired-authority")
            a = reserve(ta)["attempt"]
            with concurrent.futures.ThreadPoolExecutor(1) as pool:
                with Gate() as gate:
                    h.sql(
                        "UPDATE attempts SET lease_until=clock_timestamp()+interval '0.5 seconds' WHERE id=:'attempt'",
                        attempt=a["attemptId"],
                    )
                    future = pool.submit(event, ta, a, "heartbeat")
                    gate.waiting(1)
                    wait_for(
                        lambda: h.sql(
                            "SELECT lease_until<=clock_timestamp() FROM attempts WHERE id=:'attempt'",
                            attempt=a["attemptId"],
                        ).stdout.strip()
                        == "t"
                    )
                assert future.result()[0] == 409
            assert (
                h.sql(
                    "SELECT state FROM attempts WHERE id=:'attempt'", attempt=a["attemptId"]
                ).stdout.strip()
                == "assigned"
            )
            b = reserve(ta)["attempt"]
            cancel(run)
            assert terminal(ta, b, "cancelled", None)[0] == 200
            print(
                "PASS lease vence durante espera observada; heartbeat rejeitado antes de reconciliação/reserva, estado SQL ainda assigned",
                flush=True,
            )
            h.stop(process)
            del h.ENV["EC_TEST_PAUSE_RECONCILER"]
            process = h.start()
            # Submission failures before commit and lost response after commit, with real restarts.
            for failure in ["before-commit", "crash-before-commit", "after-commit"]:
                before = h.sql(
                    "SELECT count(*) FROM runs; SELECT count(*) FROM run_receipts;"
                ).stdout
                status, _ = submit(failure, failure=failure)
                if failure == "crash-before-commit":
                    assert process.wait(timeout=5) == 86
                    process = h.start()
                else:
                    assert status == 500 if failure == "before-commit" else status in (0, 500)
                    h.stop(process)
                    process = h.start()
                if failure != "after-commit":
                    assert (
                        h.sql(
                            "SELECT count(*) FROM runs; SELECT count(*) FROM run_receipts;"
                        ).stdout
                        == before
                    )
                    assert h.call("GET", "/projects/ec/run-receipts/" + failure, admin)[0] == 404
                status, rr = submit(failure)
                assert status == 200
                assert submit(failure)[1] == rr
                cancel(rr["runId"])
            print(
                "PASS rollback/queda antes do commit, resposta perdida depois do commit e reinícios reais com replay",
                flush=True,
            )
            # Reservation and result uncertainty also recover by persisted queries/replay.
            run = new("reserve-rollback")
            before = h.sql("SELECT count(*) FROM attempts;").stdout
            h.call("POST", AGENT + "/reserve", ta, {}, "crash-before-commit")
            assert process.wait(timeout=5) == 86
            process = h.start()
            assert h.sql("SELECT count(*) FROM attempts;").stdout == before
            assert state(run, admin)["state"] == "queued"
            cancel(run)
            print(
                "PASS queda real durante reserva antes do commit não consome trabalho/slot/tentativa",
                flush=True,
            )
            run = new("reserve-lost")
            assert h.call("POST", AGENT + "/reserve", ta, {}, "after-commit")[0] in (0, 500)
            h.stop(process)
            process = h.start()
            a = reserve(ta)["attempt"]
            assert a["runId"] == run and a["ordinal"] == 1
            assert event(ta, a, "running")[0] == 200
            path = AGENT + "/attempts/" + str(a["attemptId"]) + "/result"
            assert h.call(
                "POST",
                path,
                ta,
                dict(fence=a["fence"], outcome="succeeded", exitCode=0),
                "after-commit",
            )[0] in (0, 500)
            h.stop(process)
            process = h.start()
            accepted = ok("GET", AGENT + "/attempts/" + str(a["attemptId"]), ta)["resultReceipt"]
            assert terminal(ta, a) == (200, accepted)
            assert len(state(run, admin)["attempts"]) == 1
            invariant()
            print(
                "PASS recibos de reserva/resultado após resposta perdida e reinício; todos os oráculos SQL",
                flush=True,
            )
            print(
                h.sql(
                    "SELECT version(); SHOW transaction_isolation; SHOW synchronous_commit;"
                ).stdout.strip(),
                flush=True,
            )
    finally:
        if process is not None and process.poll() is None:
            h.stop(process)
        h.sql(
            "SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname=current_database() AND pid<>pg_backend_pid();",
            check=False,
        )
        subprocess.run(
            [
                "docker",
                "exec",
                h.CONTAINER,
                "dropdb",
                "-U",
                h.POSTGRES["user"],
                "--if-exists",
                h.DATABASE,
            ],
            check=True,
        )  # Docker do PATH confiável do operador; container dedicado literal, banco UUID do harness, argv sem shell.


def reserve_safe_status(token):
    return h.call("POST", AGENT + "/reserve", token, {})[0]


if __name__ == "__main__":
    main()
