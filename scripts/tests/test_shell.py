"""Testes dos contratos shell com fixtures descartáveis e oráculos explícitos."""

import datetime as dt
import fcntl
import hashlib
import importlib.util
import json
import os
import shutil
import signal
import subprocess
import tempfile
import time
import unittest
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
SCRIPTS = ROOT / "scripts"


class ShellTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory(prefix="ec shell with spaces ")
        self.root = Path(self.temp.name)
        (self.root / "README.md").write_text("input")
        (self.root / "AGENTS.md").write_text("fixture")
        self.env = dict(os.environ, PYTHONDONTWRITEBYTECODE="1")
        for key in ("EC_ROOT", "EC_TASK_DIR", "EC_E2E_ARTIFACT_DIR"):
            self.env.pop(key, None)

    def runtime_directory(self):
        identity = hashlib.sha256(os.fsencode(self.root.resolve())).hexdigest()[:24]
        return (
            Path("/tmp") / f"ec-automation-{os.getuid()}-{identity}"
        )  # Mesmo namespace privado do runner; fixture removida no tearDown.

    def tearDown(self):
        shutil.rmtree(self.runtime_directory(), ignore_errors=True)
        self.temp.cleanup()

    def shell(self, script, *args, cwd=None, env=None):
        return subprocess.run(
            [
                "bash",
                str(SCRIPTS / script),
                *map(str, args),
            ],  # Bash do PATH confiável; script versionado ou programa literal, argumentos separados, sem interpolação em código shell.
            cwd=cwd or self.root,
            env=env or self.env,
            capture_output=True,
            timeout=30,
            check=False,
        )

    def write_json(self, name, data):
        path = self.root / name
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_text(json.dumps(data))
        return path

    def task(self, name, command, depends=None, timeout=5):
        return dict(
            project="scripts",
            name=name,
            argv=["bash", "-c", command],
            cwd=".",
            depends=depends or [],
            timeout=timeout,
        )

    def start_tasks(self, tasks, stderr=subprocess.PIPE):
        plan = self.write_json("plan.json", {"schema": 1, "tasks": tasks, "surfaces": ["scripts"]})
        harness = """set -euo pipefail
umask 077
CODE=$1 ROOT=$2
source "$CODE/scripts/stacks/ec/common.sh"
source "$CODE/scripts/stacks/ec/inputs.sh"
source "$CODE/scripts/stacks/ec/runtime.sh"
PLAN=$(cat "$3")
run_plan
"""
        return subprocess.Popen(
            [
                "bash",
                "-c",
                harness,
                "fixture",
                str(ROOT),
                str(self.root),
                str(plan),
            ],  # Bash do PATH confiável; programa harness literal, caminhos passados como parâmetros posicionais.
            cwd=self.root,
            env=self.env,
            stdout=subprocess.PIPE,
            stderr=stderr,
        )

    def run_tasks(self, tasks):
        process = self.start_tasks(tasks)
        out, err = process.communicate(timeout=30)
        logs = list((self.root / ".local/logs").glob("*/*-automate.results.log"))
        result = max(logs, key=lambda p: p.stat().st_mtime_ns)
        directory = result.parent / result.name.removesuffix("-automate.results.log")
        doc = json.loads(result.read_text())
        return process.returncode, directory, doc, out, err

    def test_plan_default_and_real_workspaces(self):
        result = self.shell("automate.sh", "plan", cwd=self.root)
        self.assertEqual(result.returncode, 0, result.stderr)
        doc = json.loads(result.stdout)
        self.assertFalse(doc["executed"])
        self.assertEqual(len(doc["surfaces"]), 8)
        rust = [t for t in doc["tasks"] if t["project"] in ("server", "agent")]
        self.assertEqual(
            {t["cwd"] for t in rust}, {"application/agents", "application/server/backend"}
        )
        self.assertEqual(
            {t["name"] for t in rust},
            {
                "fmt",
                "check",
                "clippy",
                "test",
                "machete",
                "deny",
                "audit",
                "geiger",
                "coverage",
                "harness-build",
            },
        )
        for project in ("server", "agent"):
            self.assertEqual(
                [t["name"] for t in rust if t["project"] == project],
                [
                    "fmt",
                    "check",
                    "clippy",
                    "test",
                    "machete",
                    "deny",
                    "audit",
                    "geiger",
                    "coverage",
                    "harness-build",
                ],
            )
        self.assertTrue(next(t for t in doc["tasks"] if t["project"] == "e2e")["depends"])

    def test_complete_scope_preserves_default_and_adds_complementary_gates(self):
        default = self.shell("automate.sh", "plan")
        complete = self.shell("automate.sh", "plan", "--scope", "complete")
        self.assertEqual(default.returncode, 0, default.stderr)
        self.assertEqual(complete.returncode, 0, complete.stderr)
        before = json.loads(default.stdout)
        after = json.loads(complete.stdout)
        self.assertEqual(after["scope"], "complete")
        self.assertFalse(after["executed"])
        self.assertEqual(after["surfaces"], before["surfaces"])
        for task in before["tasks"]:
            self.assertIn(task, after["tasks"])
        extras = [task for task in after["tasks"] if task not in before["tasks"]]
        self.assertEqual(
            {(t["project"], t["name"]) for t in extras},
            {("security", name) for name in ("grype", "bandit", "syft", "yara-x")},
        )
        self.assertEqual(len(extras), 4)
        self.assertEqual(
            len({(t["project"], t["name"]) for t in after["tasks"]}), len(after["tasks"])
        )

    def test_complete_scope_rejects_filters_and_invalid_values(self):
        for args in (
            ("--scope",),
            ("--scope", "unknown"),
            ("--scope", "complete", "--scope", "complete"),
            ("--scope", "complete", "--surface", "security"),
            ("--check", "grype", "--scope", "complete"),
        ):
            with self.subTest(args=args):
                result = self.shell("automate.sh", "plan", *args)
                self.assertNotEqual(result.returncode, 0)
                self.assertEqual(result.stdout, b"")

    def test_selective_security_keeps_rules_and_governance(self):
        result = self.shell("automate.sh", "plan", "--check", "sast")
        self.assertEqual(result.returncode, 0, result.stderr)
        tasks = json.loads(result.stdout)["tasks"]
        self.assertEqual(
            [t["name"] for t in tasks if t["project"] == "security"], ["sast-rules", "sast"]
        )
        self.assertEqual(len([t for t in tasks if t["project"] == "docs"]), 5)

    def test_optional_checks_are_reachable(self):
        for name in (
            "machete",
            "deny",
            "audit",
            "geiger",
            "lint",
            "build-budget",
            "performance",
            "dev",
            "setup",
            "trivy",
            "codeql",
            "secrets-rules",
            "ruff",
            "shellcheck",
            "playwright",
        ):
            with self.subTest(name=name):
                result = self.shell("automate.sh", "plan", "--check", name)
                self.assertEqual(result.returncode, 0, result.stderr)
                self.assertTrue(any(t["name"] == name for t in json.loads(result.stdout)["tasks"]))
        result = self.shell("automate.sh", "plan", "--surface", "agent", "--check", "coverage")
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertEqual(
            [t["name"] for t in json.loads(result.stdout)["tasks"] if t["project"] == "agent"],
            ["coverage"],
        )

    def test_invalid_cli_arguments(self):
        for args in (
            (),
            ("bad",),
            ("plan", "--root"),
            ("plan", "--surface", "nope"),
            ("plan", "--check", "nope"),
            ("plan", "--check", "coverage"),
            ("plan", "--surface", "docs", "--check", "sast"),
        ):
            with self.subTest(args=args):
                self.assertEqual(self.shell("automate.sh", *args).returncode, 2)

    def test_plan_spaced_root_without_execution(self):
        result = self.shell(
            "automate.sh", "plan", "--root", self.root, "--surface", "scripts", cwd=self.root
        )
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertEqual(json.loads(result.stdout)["root"], str(self.root))
        self.assertFalse((self.root / ".local").exists())

    def test_raw_bytes_exit_and_continuation(self):
        code, directory, doc, _, err = self.run_tasks(
            [
                self.task("fail", "printf 'raw\\0\\377'; printf diagnostic >&2; exit 17"),
                self.task("next", "true"),
            ]
        )
        self.assertEqual(code, 2, err)
        self.assertEqual([r["returncode"] for r in doc["results"]], [17, 0])
        self.assertEqual(
            Path(str(directory) + "-00-scripts-fail.stdout.log").read_bytes(), b"raw\x00\xff"
        )
        self.assertEqual(
            Path(str(directory) + "-00-scripts-fail.stderr.log").read_bytes(), b"diagnostic"
        )
        self.assertFalse(doc["complete"])

    def test_progress_is_plain_bounded_and_does_not_echo_untrusted_output(self):
        code, directory, _, out, err = self.run_tasks([
            self.task("unsafe", "printf '\\033]0;SECRET\\007' >&2; printf '\\000\\377'; exit 17"),
            self.task("next", "true"),
        ])
        self.assertEqual(code, 2)
        self.assertIn("EC · AUTOMAÇÃO".encode(), err)
        self.assertIn(b"[1/4]", err)
        self.assertIn(b"[4/4]", err)
        self.assertIn(b"FALHA", err)
        self.assertIn(b"total: 100%", err)
        self.assertIn(b"Log/relat", err)
        self.assertIn(str(directory).encode(), err)
        self.assertNotIn(b"SECRET", out + err)
        self.assertNotIn(b"\x1b", out + err)
        self.assertNotIn(b"\r", err)
        self.assertLess(len(err), 4000)
        self.assertEqual(Path(str(directory) + "-00-scripts-unsafe.stdout.log").read_bytes(), b"\x00\xff")
        self.assertEqual(Path(str(directory) + "-00-scripts-unsafe.stderr.log").read_bytes(), b"\x1b]0;SECRET\x07")

    def test_progress_tty_color_no_color_and_dumb_terminal(self):
        for no_color, term, colored, control in (
            ("", "xterm", True, True),
            ("1", "xterm", False, True),
            ("", "dumb", False, False),
        ):
            with self.subTest(no_color=no_color, term=term):
                self.env.update(NO_COLOR=no_color, TERM=term)
                master, slave = os.openpty()
                try:
                    process = self.start_tasks([self.task("ok", "true")], stderr=slave)
                finally:
                    os.close(slave)
                try:
                    process.communicate(timeout=30)
                    output = b""
                    while True:
                        try:
                            data = os.read(master, 65536)
                        except OSError:
                            break
                        if not data:
                            break
                        output += data
                finally:
                    os.close(master)
                self.assertEqual(process.returncode, 0, output)
                self.assertEqual(b"\x1b[32m" in output, colored)
                self.assertEqual(b"\x1b[2K" in output, control)
                self.assertIn(b"SUCESSO", output)

    def test_progress_rejects_success_when_service_cleanup_fails(self):
        code, _, doc, _, err = self.run_tasks([
            self.task("invalid-receipt", "printf '{' > \"$EC_RUN_DIR/services.json\""),
        ])
        self.assertEqual(code, 2, err)
        self.assertEqual(doc["service_cleanup"], "failed")
        self.assertFalse(doc["complete"])
        self.assertIn(b"FALHA", err)
        self.assertNotIn(b"total: 100%", err)
        self.assertIn("Execução incompleta".encode(), err)

    def test_progress_early_metadata_and_final_inventory_errors_never_announce_100(self):
        for command in (
            "printf '{' > \"$EC_TASK_DIR/tool.json\"",
            "ln -s . scripts",
        ):
            with self.subTest(command=command):
                code, _, doc, _, err = self.run_tasks([self.task("invalid", command)])
                self.assertEqual(code, 2, err)
                self.assertFalse(doc["complete"])
                self.assertNotIn(b"total: 100%", err)
                self.assertIn("Execução incompleta".encode(), err)

    def test_missing_tool_and_dependency_block(self):
        code, _, doc, _, err = self.run_tasks(
            [
                self.task("missing", "exec ec-nonexistent-tool"),
                self.task("next", "touch forbidden", ["scripts:missing"]),
            ]
        )
        self.assertEqual(code, 3, err)
        self.assertEqual([r["state"] for r in doc["results"]], ["blocked", "blocked"])
        self.assertFalse((self.root / "forbidden").exists())

    def test_timeout_retains_partial_logs(self):
        code, directory, doc, _, err = self.run_tasks(
            [self.task("slow", "echo started; sleep 10", timeout=1)]
        )
        self.assertEqual(code, 2, err)
        self.assertEqual(doc["results"][0]["reason"], "timeout")
        self.assertEqual(
            Path(str(directory) + "-00-scripts-slow.stdout.log").read_text(), "started\n"
        )

    def test_private_runs_and_retention(self):
        code, first, _, _, err = self.run_tasks([self.task("ok", "echo evidence")])
        original = Path(str(first) + "-automate.results.log").read_bytes()
        code2, second, doc, _, _ = self.run_tasks([self.task("ok", "true")])
        self.assertEqual((code, code2), (0, 0), err)
        self.assertTrue(doc["complete"])
        self.assertNotEqual(first, second)
        self.assertEqual(Path(str(first) + "-automate.results.log").read_bytes(), original)
        self.assertEqual(first.parent.stat().st_mode & 0o777, 0o700)
        self.assertEqual(Path(str(first) + "-automate.results.log").stat().st_mode & 0o777, 0o600)

    def test_input_drift_rejects_success(self):
        code, _, doc, _, err = self.run_tasks([self.task("mutate", "echo changed > README.md")])
        self.assertEqual(code, 2, err)
        self.assertIn("README.md", doc["changed_inputs"])

    def test_acceptance_inventory_observes_agents_config_and_active_skills(self):
        config_path = self.root / ".codex/config.toml"
        agent = self.root / ".codex/agents/reviewer.toml"
        skill = self.root / ".codex/skills/method/SKILL.md"
        for path in (config_path, agent, skill):
            path.parent.mkdir(parents=True, exist_ok=True)
            path.write_text("before")
        mutations = (
            (".codex/config.toml", "echo changed > .codex/config.toml"),
            (".codex/agents/reviewer.toml", "echo changed > .codex/agents/reviewer.toml"),
            (".codex/agents/reviewer.toml", "rm .codex/agents/reviewer.toml"),
            (
                ".codex/agents/reviewer.toml",
                "mv .codex/agents/reviewer.toml .codex/agents/renamed.toml",
            ),
            (".codex/agents/new.toml", "echo new > .codex/agents/new.toml"),
            (".codex/skills/method/SKILL.md", "echo changed > .codex/skills/method/SKILL.md"),
        )
        for expected, command in mutations:
            with self.subTest(expected=expected, command=command):
                code, _, doc, _, err = self.run_tasks([self.task("mutate", command)])
                self.assertEqual(code, 2, err)
                self.assertFalse(doc["complete"])
                self.assertIn(expected, doc["changed_inputs"])
                self.assertEqual(doc["acceptance_inventory_version"], 1)
            for path in (config_path, agent, skill):
                path.write_text("before")

    def test_cargo_lock_update_is_retained_but_requires_final_resolution_run(self):
        lock = self.root / "application/agents/Cargo.lock"
        lock.parent.mkdir(parents=True)
        lock.write_text("before\n")
        code, _, doc, _, err = self.run_tasks(
            [self.task("resolve", "printf 'after\\n' > application/agents/Cargo.lock")]
        )
        self.assertEqual(code, 2, err)
        self.assertEqual(lock.read_text(), "after\n")
        self.assertEqual(doc["results"][0]["state"], "passed")
        self.assertEqual(doc["results"][-1]["task"], "dependency-resolution")
        self.assertFalse(doc["complete"])
        self.assertEqual(
            doc["updated_lockfiles"],
            [
                {
                    "path": "application/agents/Cargo.lock",
                    "before": hashlib.sha256(b"before\n").hexdigest(),
                    "after": hashlib.sha256(b"after\n").hexdigest(),
                }
            ],
        )
        code, _, doc, _, err = self.run_tasks([self.task("stable", "true")])
        self.assertEqual(code, 0, err)
        self.assertTrue(doc["complete"])
        self.assertEqual(doc["updated_lockfiles"], [])

    def test_cargo_lock_update_does_not_hide_source_mutation(self):
        lock = self.root / "application/agents/Cargo.lock"
        lock.parent.mkdir(parents=True)
        lock.write_text("before\n")
        code, _, doc, _, err = self.run_tasks(
            [
                self.task(
                    "mutate", "echo after > application/agents/Cargo.lock; echo changed > README.md"
                )
            ]
        )
        self.assertEqual(code, 2, err)
        self.assertIn("README.md", doc["changed_inputs"])
        self.assertEqual(
            {row["task"] for row in doc["results"][1:]},
            {"input-stability", "dependency-resolution"},
        )

    def test_symlink_and_traversal_rejected(self):
        (self.root / "scripts").symlink_to(self.root.parent, target_is_directory=True)
        process = self.start_tasks([self.task("ok", "true")])
        _, err = process.communicate(timeout=10)
        self.assertEqual(process.returncode, 2, err)
        self.assertNotIn(b"total: 100%", err)
        result = subprocess.run(
            [
                "bash",
                "-c",
                'source "$1"; inside "$2" ../outside',  # Bash do PATH confiável; script versionado ou programa literal, argumentos separados, sem interpolação em código shell.
                "test",
                str(SCRIPTS / "stacks/ec/common.sh"),
                str(self.root),
            ],
            capture_output=True,
            check=False,
        )
        self.assertEqual(result.returncode, 2)

    def test_lock_prevents_execution(self):
        directory = self.runtime_directory()
        directory.mkdir(mode=0o700, exist_ok=True)
        with (directory / "workspace.lock").open("w") as stream:
            fcntl.flock(stream, fcntl.LOCK_EX | fcntl.LOCK_NB)
            process = self.start_tasks([self.task("ok", "touch forbidden")])
            _, err = process.communicate(timeout=10)
            self.assertEqual(process.returncode, 3, err)
        self.assertFalse((self.root / "forbidden").exists())

    def test_cancellation_stops_group_and_later_tasks(self):
        process = self.start_tasks(
            [
                self.task("slow", "echo started; sleep 20"),
                self.task("next", "touch forbidden"),
            ]
        )
        deadline = time.monotonic() + 10
        while time.monotonic() < deadline:
            logs = list((self.root / ".local/logs").glob("*/*-00-*.stdout.log"))
            if logs and b"started" in logs[0].read_bytes():
                break
            time.sleep(0.03)
        process.send_signal(signal.SIGTERM)
        _, err = process.communicate(timeout=10)
        self.assertEqual(process.returncode, 2, err)
        self.assertFalse((self.root / "forbidden").exists())
        self.assertIn(b"CANCELADA", err)
        self.assertNotIn(b"total: 100%", err)

    def test_runner_death_releases_lock_while_workload_exists(self):
        process = self.start_tasks([self.task("slow", "echo $$ > child.pid; exec sleep 20")])
        child_file = self.root / "child.pid"
        deadline = time.monotonic() + 10
        while not child_file.exists() and time.monotonic() < deadline:
            time.sleep(0.03)
        self.assertTrue(child_file.exists())
        child = int(child_file.read_text())
        process.kill()
        process.wait(timeout=5)
        try:
            with (self.runtime_directory() / "workspace.lock").open("w") as stream:
                deadline = time.monotonic() + 2
                while True:
                    try:
                        fcntl.flock(stream, fcntl.LOCK_EX | fcntl.LOCK_NB)
                        break
                    except BlockingIOError:
                        if time.monotonic() >= deadline:
                            raise
                        time.sleep(0.02)
                os.kill(child, 0)  # O workload ainda existe; não reteve o lock.
        finally:
            try:
                os.killpg(os.getpgid(child), signal.SIGTERM)
            except ProcessLookupError:
                # O processo de teste já terminou; não há grupo restante para limpar.
                pass
            process.communicate(timeout=5)

    def test_log_overflow_fails(self):
        code, _, doc, _, err = self.run_tasks([self.task("large", "head -c 9000000 /dev/zero")])
        self.assertEqual(code, 2, err)
        self.assertIn("log", doc["results"][0]["reason"])

    def test_log_limit_does_not_limit_workload_files(self):
        code, _, doc, _, err = self.run_tasks(
            [self.task("file", "head -c 18000000 /dev/zero > workload.bin")]
        )
        self.assertEqual(code, 0, err)
        self.assertTrue(doc["complete"])
        self.assertEqual((self.root / "workload.bin").stat().st_size, 18000000)

    def test_environment_does_not_leak_tokens(self):
        self.env["EC_TEST_SECRET_TOKEN"] = os.urandom(16).hex()
        code, _, _, _, err = self.run_tasks(
            [
                self.task(
                    "env", 'test -z "${EC_TEST_SECRET_TOKEN:-}"; test "$CARGO_NET_OFFLINE" = true'
                )
            ]
        )
        self.assertEqual(code, 0, err)

    def parse_report(self, kind, data, rc, expected=None):
        report = self.write_json("report.json", data)
        inputs = self.write_json("inputs.json", expected or {"a.rs": "hash"})
        return self.shell(
            "stacks/ec/security/check-report.sh", kind, report, rc, inputs, "/snapshot"
        )

    def test_secrets_report_exit_consistency(self):
        finding = {"RuleID": "fixture", "File": "/project/a", "StartLine": 1, "Secret": "***"}
        for data, rc, wanted in (
            ([], 0, 0),
            ([finding], 10, 0),
            ([], 10, 2),
            ([{}], 0, 2),
            ({}, 0, 2),
            ([], 7, 2),
        ):
            with self.subTest(data=data, rc=rc):
                self.assertEqual(self.parse_report("secrets", data, rc).returncode, wanted)

    def test_sast_requires_complete_unsuppressed_inventory(self):
        good = {"results": [], "errors": [], "paths": {"scanned": ["/snapshot/a.rs"]}}
        result = self.parse_report("sast", good, 0)
        self.assertEqual(result.returncode, 0, result.stderr)
        variants = [
            dict(good, errors=[{}]),
            dict(good, paths={"scanned": []}),
            dict(good, skipped_rules=[{"id": "unsupported"}]),
            dict(good, skipped_rules=""),
            dict(good, paths={"scanned": ["/snapshot/a.rs"], "skipped": ["x"]}),
            dict(good, paths={"scanned": ["/elsewhere/a.rs"]}),
            dict(good, paths={"scanned": ["../a.rs"]}),
            dict(good, results=[{"check_id": "x", "extra": {"is_ignored": True}}]),
        ]
        for data in variants:
            with self.subTest(data=data):
                self.assertEqual(self.parse_report("sast", data, 0).returncode, 2)

    def test_osv_requires_all_identified_lockfiles(self):
        expected = {name: "hash" for name in ("a", "b", "c")}
        good = {
            "results": [
                {
                    "source": {"type": "lockfile", "path": "/snapshot/" + name},
                    "packages": [
                        {"package": {"name": "fixture", "version": "1", "ecosystem": "npm"}}
                    ],
                }
                for name in expected
            ]
        }
        result = self.parse_report("dependencies", good, 0, expected)
        self.assertEqual(result.returncode, 0, result.stderr)
        for change in ("missing", "duplicate", "empty", "suppressed", "identity", "exit"):
            data = json.loads(json.dumps(good))
            if change == "missing":
                data["results"].pop()
            elif change == "duplicate":
                data["results"].append(data["results"][0])
            elif change == "empty":
                data["results"][0]["packages"] = []
            elif change == "suppressed":
                data["results"][0]["experimental_pes"] = [{}]
            elif change == "identity":
                del data["results"][0]["packages"][0]["package"]["name"]
            with self.subTest(change=change):
                self.assertEqual(
                    self.parse_report(
                        "dependencies", data, 1 if change == "exit" else 0, expected
                    ).returncode,
                    2,
                )

    def test_empty_rule_test_and_sarif_fail_closed(self):
        for kind, doc in [
            ("sast-rules", {"config_missing_tests": [], "config_with_errors": [], "results": {}}),
            ("sarif", {"version": "2.1.0", "runs": [{"tool": {}, "results": []}]}),
        ]:
            self.assertEqual(self.parse_report(kind, doc, 0).returncode, 2)

    def test_exception_metadata_and_expiry(self):
        today = dt.datetime.now(dt.UTC).date()
        row = dict(
            id="one",
            tool="osv",
            finding="x",
            component="server",
            owner="person",
            reason="reviewed",
            reviewed=str(today),
            expires=str(today + dt.timedelta(days=1)),
            control="test",
        )
        path = self.write_json("exceptions.json", [row])
        result = self.shell("stacks/ec/security/validate-exceptions.sh", path)
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertFalse(json.loads(result.stdout)["applied"])
        for rows in ([row, row], [dict(row, expires=str(today))], [dict(row, owner="")]):
            self.write_json("exceptions.json", rows)
            self.assertEqual(
                self.shell("stacks/ec/security/validate-exceptions.sh", path).returncode, 2
            )

    def test_budget_measures_actual_bytes_and_gzip(self):
        (self.root / "dist").mkdir()
        (self.root / "dist/a.js").write_bytes(b"x" * 100)
        path = self.write_json("budget.json", {"directory": "dist", "maximum_bytes": 100})
        result = self.shell("stacks/ec/metrics/check.sh", "budget", path)
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertEqual(json.loads(result.stdout)["total_bytes"], 100)
        self.write_json("budget.json", {"directory": "dist", "maximum_bytes": 99})
        self.assertEqual(self.shell("stacks/ec/metrics/check.sh", "budget", path).returncode, 1)
        (self.root / "dist/link").symlink_to("/etc/passwd")
        self.assertEqual(self.shell("stacks/ec/metrics/check.sh", "budget", path).returncode, 2)

    def test_performance_uses_nearest_rank_p95(self):
        self.write_json("samples.json", {"samples": list(range(1, 21))})
        path = self.write_json(
            "performance.json", {"report": "samples.json", "p95_limit": 19, "maximum": 20}
        )
        result = self.shell("stacks/ec/metrics/check.sh", "performance", path)
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertEqual(json.loads(result.stdout)["p95"], 19)
        self.write_json("samples.json", {"samples": []})
        self.assertNotEqual(
            self.shell("stacks/ec/metrics/check.sh", "performance", path).returncode, 0
        )

    def test_lcov_validates_records_and_threshold(self):
        report = self.root / "coverage.lcov"
        report.write_text("SF:a.rs\nLF:10\nLH:8\nFNF:2\nFNH:1\nend_of_record\n")
        path = self.write_json(
            "coverage.json", {"report": "coverage.lcov", "format": "lcov", "minimum": {"lines": 80}}
        )
        result = self.shell("stacks/ec/metrics/check.sh", "coverage", path)
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertEqual(json.loads(result.stdout)["metrics"]["lines"]["actual"], 80)
        report.write_text("SF:a.rs\nLF:10\nLH:8\n")
        self.assertEqual(self.shell("stacks/ec/metrics/check.sh", "coverage", path).returncode, 2)

    def test_eslint_ratchet_and_errors(self):
        baseline = self.write_json(
            "baseline.json", [{"file": "a.ts", "rule": "complexity", "maximum": 1}]
        )
        report = self.write_json(
            "lint.json",
            [{"filePath": "a.ts", "messages": [{"ruleId": "complexity", "severity": 1}]}],
        )
        result = self.shell("stacks/frontend/eslint-ratchet.sh", report, baseline)
        self.assertEqual(result.returncode, 0, result.stderr)
        self.write_json(
            "lint.json",
            [{"filePath": "a.ts", "messages": [{"ruleId": "complexity", "severity": 2}]}],
        )
        self.assertEqual(
            self.shell("stacks/frontend/eslint-ratchet.sh", report, baseline).returncode, 1
        )

    def test_summary_escapes_markdown_and_does_not_mutate(self):
        path = self.write_json(
            "results.json",
            {
                "schema": 1,
                "results": [
                    dict(
                        project="x|y",
                        task="a",
                        state="blocked",
                        returncode=None,
                        reason="<tag>\nnext",
                    )
                ],
            },
        )
        original = path.read_bytes()
        result = self.shell("stacks/ec/reports/summarize.sh", path)
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertIn(b"x\\|y", result.stdout)
        self.assertIn(b"&lt;tag&gt; next", result.stdout)
        self.assertEqual(path.read_bytes(), original)

    def test_summary_accepts_measurement_without_relabeling_it_as_passed(self):
        path = self.write_json(
            "results.json",
            {
                "schema": 1,
                "results": [
                    dict(
                        project="security",
                        task="syft",
                        state="measured",
                        returncode=0,
                        reason="inventário",
                    )
                ],
            },
        )
        result = self.shell("stacks/ec/reports/summarize.sh", path)
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertIn(b"| security | syft | measured |", result.stdout)

    def test_shell_syntax(self):
        self.assertEqual(self.shell("check.sh").returncode, 0)

    def fake_tool(self, name, body):
        directory = self.root / "bin"
        directory.mkdir(exist_ok=True)
        path = directory / name
        path.write_text("#!/usr/bin/env bash\nset -euo pipefail\n" + body)
        path.chmod(0o700)
        self.env["PATH"] = str(directory) + os.pathsep + os.environ["PATH"]
        return path

    def test_rust_invocation_flags_and_missing_policy(self):
        self.env["EC_RUST_TOOLCHAIN"] = "fixture-toolchain"
        self.env["EC_ROOT"] = str(self.root)
        (self.root / "Cargo.toml").write_text("[workspace]\n")
        (self.root / "Cargo.lock").write_text("# fixture")
        (self.root / "rust-toolchain.toml").write_text('[toolchain]\nchannel = "1.99.1"\n')
        self.fake_tool(
            "rustup",
            """
if [[ ${*: -1} == --version ]]; then echo 'fixture 1.0.0'; exit 0; fi
if [[ $4 == update ]]; then exit 0; fi
printf '%s\n' "$@" | jq -R . | jq -s .
""",
        )
        for name in ("fmt", "clippy", "test", "harness-build", "machete", "audit", "deny"):
            if name in ("machete", "audit", "deny"):
                self.fake_tool("cargo-" + name, "exit 0\n")
            if name == "audit":
                (self.root / "cargo/advisory-db/.git").mkdir(parents=True)
                self.env["EC_RUSTSEC_DB"] = str(self.root / "cargo/advisory-db")
                self.fake_tool("git", "date +%s\n")
                (self.root / "scripts/config/native/cargo-audit").mkdir(parents=True, exist_ok=True)
                (self.root / "scripts/config/native/cargo-audit/audit.toml").write_text(
                    "[database]\nfetch=false\n"
                )
                self.env["CARGO_HOME"] = str(self.root / "cargo")
                self.assertEqual(self.shell("stacks/rust/check.sh", name).returncode, 3)
                self.env["EC_TASK_DIR"] = str(self.root / "task-audit")
                (self.root / "task-audit").mkdir()
            if name == "deny":
                self.assertEqual(self.shell("stacks/rust/check.sh", name).returncode, 3)
                (self.root / "scripts/config/native/cargo-deny").mkdir(parents=True, exist_ok=True)
                (self.root / "scripts/config/native/cargo-deny/deny.toml").write_text(
                    "[advisories]\nignore = []\n"
                )
                self.env["EC_TASK_DIR"] = str(self.root / "task-deny")
                (self.root / "task-deny").mkdir()
            result = self.shell("stacks/rust/check.sh", name)
            self.assertEqual(result.returncode, 0, (name, result.stderr))
            args = json.loads(result.stdout)
            self.assertEqual(
                args[:4],
                ["run", "fixture-toolchain", "cargo", "build" if name == "harness-build" else name],
            )
            if name in ("test", "clippy", "harness-build"):
                self.assertIn("--offline", args)
                self.assertNotIn("--locked", args)
            if name == "audit":
                self.assertIn("--no-fetch", args)
            if name == "deny":
                stage = self.root / "task-deny/deny"
                self.assertEqual(
                    (stage / "advisory-dbs/advisory-db-3157b0e258782691").resolve(),
                    self.root / "cargo/advisory-db",
                )
                self.assertEqual(Path(args[args.index("--config") + 1]), stage / "deny.toml")
                self.assertIn("ignore = []", (stage / "deny.toml").read_text())
                self.assertIn('db-path = "$EC_DENY_DB_ROOT"', (stage / "deny.toml").read_text())
                self.assertNotIn(
                    "db-path",
                    (self.root / "scripts/config/native/cargo-deny/deny.toml").read_text(),
                )

    def test_frontend_actual_npm_scripts_and_missing_script(self):
        self.write_json(
            "package.json", {"engines": {"node": ">=22.12"}, "scripts": {"test": "vitest run"}}
        )
        self.write_json("package-lock.json", {})
        self.write_json("node_modules/.package-lock.json", {})
        self.fake_tool("npm", 'printf "%s\\n" "$@" | jq -R . | jq -s .\n')
        result = self.shell("stacks/frontend/check.sh", "test")
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertEqual(json.loads(result.stdout), ["run", "test"])
        self.assertEqual(self.shell("stacks/frontend/check.sh", "coverage").returncode, 3)
        self.assertEqual(self.shell("stacks/frontend/check.sh", "performance").returncode, 3)
        result = self.shell("stacks/frontend/check.sh", "setup")
        self.assertEqual(json.loads(result.stdout), ["ci", "--ignore-scripts"])

    def test_geiger_virtual_workspace_scans_each_member_and_preserves_failure(self):
        self.env["EC_RUST_TOOLCHAIN"] = "fixture-toolchain"
        self.env.update(EC_ROOT=str(self.root), EC_TASK_DIR=str(self.root / "task"))
        (self.root / "task").mkdir()
        (self.root / "Cargo.toml").write_text("[workspace]\n")
        (self.root / "Cargo.lock").write_text("# fixture\n")
        (self.root / "rust-toolchain.toml").write_text('[toolchain]\nchannel = "1.99.1"\n')
        # O segundo membro não precisa ser dependência do primeiro.
        manifests = [
            str(self.root / "crates" / name / "Cargo.toml") for name in ("app", "independent")
        ]
        metadata = {
            "workspace_members": ["app", "independent"],
            "packages": [
                {"id": name, "manifest_path": path}
                for name, path in zip(("app", "independent"), manifests, strict=False)
            ],
            "target_directory": str(self.root / "target"),
        }
        self.write_json("metadata.json", metadata)
        self.fake_tool("cargo-geiger", "exit 0\n")
        self.fake_tool(
            "rustup",
            """
if [[ ${*: -1} == --version ]]; then echo 'fixture 1.0.0'; exit 0; fi
# O gate deve usar exclusivamente o plugin instalado, sem build/test/update.
[[ $4 != build && $4 != test && $4 != update ]] || exit 98
if [[ $4 == metadata ]]; then cat "$EC_ROOT/metadata.json"; exit 0; fi
[[ $4 == geiger ]] || exit 9
[[ ${RUST_MIN_STACK:-} == 16777216 ]] || exit 29
printf '%s\\n' "$@" | jq -R . | jq -s . >> "$EC_ROOT/invocations.json"
if [[ ${*: -1} == *independent/Cargo.toml && -f $EC_ROOT/fail-second ]]; then
    echo "WARNING: Dependency file was never scanned: $EC_ROOT/target/debug/build/serde_core-fixture/out/private.rs" >&2
    exit 17
fi
[[ ! -f $EC_ROOT/empty-report ]] || exit 0
echo 'unsafe inventory fixture'
""",
        )
        result = self.shell("stacks/rust/check.sh", "geiger")
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertFalse((self.root / "task/geiger-tool").exists())
        decoder = json.JSONDecoder()
        calls = (self.root / "invocations.json").read_text()
        for manifest in manifests:
            args, end = decoder.raw_decode(calls.lstrip())
            calls = calls.lstrip()[end:]
            self.assertEqual(args[:4], ["run", "fixture-toolchain", "cargo", "geiger"])
            self.assertEqual(args[-2:], ["--manifest-path", manifest])
            self.assertNotIn("--locked", args)
            for flag in ("--offline", "--all-features"):
                self.assertIn(flag, args)
        self.assertFalse(calls.strip())
        (self.root / "fail-second").touch()
        result = self.shell("stacks/rust/check.sh", "geiger")
        self.assertEqual(result.returncode, 2, result.stderr)
        self.assertEqual((self.root / "task/geiger-0-exit.txt").read_text(), "0\n")
        self.assertEqual((self.root / "task/geiger-1-exit.txt").read_text(), "17\n")
        diagnostic = json.loads((self.root / "task/geiger-1-diagnostic.json").read_text())
        self.assertEqual(diagnostic["native_exit_code"], 17)
        self.assertEqual(diagnostic["manifest"], manifests[1])
        self.assertEqual(diagnostic["files"][0]["kind"], "generated_rust")
        self.assertFalse(diagnostic["files"][0]["removal_as_fix"])
        raw_stderr = (self.root / "task/geiger-1.stderr.log").read_bytes()
        self.assertEqual(raw_stderr.count(b"WARNING: Dependency file was never scanned:"), 1)
        self.assertEqual(result.stderr.count(raw_stderr), 1)
        self.assertIn(b"Pode remover para corrigir?", result.stderr)
        self.assertIn(b"nenhum arquivo foi removido", result.stderr)
        (self.root / "fail-second").unlink()
        (self.root / "empty-report").touch()
        result = self.shell("stacks/rust/check.sh", "geiger")
        self.assertEqual(result.returncode, 2, result.stderr)
        self.assertIn(b"invent\xc3\xa1rio Geiger vazio", result.stderr)
        self.write_json("metadata.json", {"workspace_members": [], "packages": []})
        result = self.shell("stacks/rust/check.sh", "geiger")
        self.assertEqual(result.returncode, 2, result.stderr)
        self.assertIn(b"metadata Geiger", result.stderr)

    def test_http_harness_rejects_nonlocal_paths_before_connection(self):
        from unittest.mock import patch

        spec = importlib.util.spec_from_file_location(
            "http_fixture", ROOT / "application/integration/tests/integration/publication_http.py"
        )
        module = importlib.util.module_from_spec(spec)
        spec.loader.exec_module(module)
        with patch.object(module.http.client, "HTTPConnection") as connection:
            for path in (
                "file:///etc/passwd",
                "https://example.invalid",
                "//example.invalid",
                "/a\r\nHost: elsewhere",
            ):
                with self.assertRaises(ValueError):
                    module.call("GET", path)
            connection.assert_not_called()

    def test_sql_harness_passes_values_separately_and_rejects_variable_names(self):
        from unittest.mock import patch

        spec = importlib.util.spec_from_file_location(
            "sql_fixture", ROOT / "application/integration/tests/integration/publication_http.py"
        )
        module = importlib.util.module_from_spec(spec)
        spec.loader.exec_module(module)
        with patch.object(module.subprocess, "run") as run:
            with self.assertRaises(ValueError):
                module.sql("SELECT 1", **{"bad;name": "x"})
            run.assert_not_called()
            value = "'; DROP TABLE revisions; --"
            module.sql("SELECT :'value'", value=value)
            self.assertEqual(run.call_args.kwargs["input"], "SELECT :'value'")
            self.assertIn("value=" + value, run.call_args.args[0])
            self.assertNotIn("shell", run.call_args.kwargs)

    def test_sast_rule_oracle_requires_every_positive_annotation(self):
        import re

        rules = {
            rule
            for path in (SCRIPTS / "rules/opengrep").iterdir()
            if path.is_file()
            for rule in re.findall(r"ruleid:\s*(ec\.[\w.-]+)", path.read_text())
        }
        checks = {
            rule: {
                "passed": True,
                "matches": {"fixture": {"expected_lines": [1], "reported_lines": [1]}},
            }
            for rule in rules
        }
        report = {
            "config_missing_tests": [],
            "config_with_errors": [],
            "results": {"fixture": {"checks": checks}},
        }
        result = self.parse_report("sast-rules", report, 0)
        self.assertEqual(result.returncode, 0, result.stderr)
        checks.pop(next(iter(checks)))
        self.assertEqual(self.parse_report("sast-rules", report, 0).returncode, 2)

    def test_trivy_and_codeql_reports(self):
        report, inputs = self.trivy_report()
        report["Results"][0]["Vulnerabilities"] = [{"VulnerabilityID": "X"}]
        result = self.parse_report("trivy", report, 1, inputs)
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertEqual(json.loads(result.stdout)["state"], "findings")
        self.assertEqual(self.parse_report("trivy", report, 0, inputs).returncode, 2)
        sarif = {
            "version": "2.1.0",
            "runs": [
                {
                    "tool": {"driver": {"name": "CodeQL", "rules": [{"id": "fixture"}]}},
                    "invocations": [{"executionSuccessful": True}],
                    "results": [{"ruleId": "fixture"}],
                }
            ],
        }
        result = self.parse_report("sarif", sarif, 0)
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertEqual(json.loads(result.stdout)["state"], "findings")

    def test_sources_and_final_layout(self):
        self.assertFalse((ROOT / "scripts_erase").exists())
        for path in SCRIPTS.rglob("*"):
            if path.suffix == ".py":
                self.assertTrue(
                    "tests" in path.relative_to(SCRIPTS).parts or path.name == "local-state.py"
                )
            if "rules" not in path.relative_to(SCRIPTS).parts:
                self.assertNotIn(path.suffix, (".ts", ".ps1"))

    def test_additional_toolbox_checks_are_selectable_without_changing_defaults(self):
        optional = {"grype", "bandit", "syft", "yara-x"}
        result = self.shell("automate.sh", "plan")
        self.assertEqual(result.returncode, 0, result.stderr)
        names = {task["name"] for task in json.loads(result.stdout)["tasks"]}
        self.assertTrue(optional.isdisjoint(names))
        for check in sorted(optional):
            result = self.shell("automate.sh", "plan", "--check", check)
            self.assertEqual(result.returncode, 0, result.stderr)
            tasks = [
                (task["project"], task["name"])
                for task in json.loads(result.stdout)["tasks"]
                if task["project"] != "docs"
            ]
            dependencies = [("prepare", "server-cargo"), ("prepare", "agent-cargo")]
            expected = dependencies if check in ("grype", "syft") else []
            self.assertEqual(tasks, [*expected, ("security", check)])

    def test_toolbox_override_survives_clean_environment_including_empty(self):
        for value in ("/trusted path/toolbox", ""):
            self.env["TOOLBOX_BIN"] = value
            code, directory, _, _, err = self.run_tasks(
                [self.task("override", 'test "${TOOLBOX_BIN+x}" = x && printf "%s" "$TOOLBOX_BIN"')]
            )
            self.assertEqual(code, 0, err)
            self.assertEqual(
                Path(str(directory) + "-00-scripts-override.stdout.log").read_text(), value
            )

    def test_selinux_policy_survives_clean_environment_including_empty(self):
        for value in ("shared", "private", ""):
            self.env["TOOLBOX_SELINUX_LABEL"] = value
            code, directory, _, _, err = self.run_tasks(
                [
                    self.task(
                        "selinux",
                        'test "${TOOLBOX_SELINUX_LABEL+x}" = x && printf "%s" "$TOOLBOX_SELINUX_LABEL"',
                    )
                ]
            )
            self.assertEqual(code, 0, err)
            self.assertEqual(
                Path(str(directory) + "-00-scripts-selinux.stdout.log").read_text(), value
            )

    def test_toolbox_state_and_docker_selection_survive_clean_environment(self):
        for key in (
            "TOOLBOX_STATE",
            "DOCKER_HOST",
            "DOCKER_CONTEXT",
            "DOCKER_CONFIG",
            "DOCKER_TLS_VERIFY",
            "DOCKER_CERT_PATH",
        ):
            with self.subTest(key=key):
                value = "selected value " + key
                self.env[key] = value
                code, directory, _, _, err = self.run_tasks(
                    [self.task("selection", 'printf "%s" "$' + key + '"')]
                )
                self.assertEqual(code, 0, err)
                self.assertEqual(
                    Path(str(directory) + "-00-scripts-selection.stdout.log").read_text(), value
                )

    def test_new_configuration_is_bound_to_input_manifest(self):
        self.write_json("scripts/config/automation.json", {"schema": 1})
        code, _, doc, _, err = self.run_tasks(
            [self.task("mutate-tool-policy", "printf '{}' > scripts/config/automation.json")]
        )
        self.assertEqual(code, 2, err)
        self.assertIn("scripts/config/automation.json", doc["changed_inputs"])

    def trivy_report(self):
        locks = [
            "application/agents/Cargo.lock",
            "application/server/backend/Cargo.lock",
            "application/server/frontend/package-lock.json",
        ]
        return (
            {
                "SchemaVersion": 2,
                "ArtifactName": "app",
                "Results": [
                    {"Target": "/snapshot/" + path, "Packages": [{"Name": "fixture"}]}
                    for path in locks
                ],
            },
            dict.fromkeys(locks, "hash"),
        )

    def test_trivy_relative_inventory_and_escaping_paths(self):
        report, inputs = self.trivy_report()
        for item in report["Results"]:
            item["Target"] = item["Target"].removeprefix("/snapshot/application/")
        self.assertEqual(self.parse_report("trivy", report, 0, inputs).returncode, 0)
        for invalid in ("../agents/Cargo.lock", "/elsewhere/agents/Cargo.lock"):
            report["Results"][0]["Target"] = invalid
            self.assertEqual(self.parse_report("trivy", report, 0, inputs).returncode, 2)

    def test_trivy_secrets_and_misconfigurations_cannot_pass_as_zero_vulns(self):
        report, inputs = self.trivy_report()
        report["Results"].append(
            {
                "Target": "config",
                "Secrets": [{"RuleID": "fixture"}],
                "Misconfigurations": [{"ID": "fixture", "Status": "FAIL"}],
            }
        )
        result = self.parse_report("trivy", report, 1, inputs)
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertEqual(json.loads(result.stdout)["finding_count"], 2)
        self.assertEqual(self.parse_report("trivy", report, 0, inputs).returncode, 2)
        report["Results"][0]["ExperimentalModifiedFindings"] = [{"Status": "ignored"}]
        self.assertEqual(self.parse_report("trivy", report, 1, inputs).returncode, 2)


if __name__ == "__main__":
    unittest.main()
