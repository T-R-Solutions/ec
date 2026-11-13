"""Retenção por datas; casos escritos, executados somente pela suíte autorizada."""

import importlib.util
import os
import shutil
import tempfile
import unittest
from pathlib import Path

SPEC = importlib.util.spec_from_file_location(
    "local_state", Path(__file__).parents[1] / "local-state.py"
)
STATE = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(STATE)


class RetentionTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory()
        self.addCleanup(self.temp.cleanup)
        self.root = Path(self.temp.name)

    def day(self, date):
        day = STATE.prepare_logs(self.root, date)
        (day / "tool.log").write_bytes(b"raw\x00\xff")
        return day

    def test_third_date_expires_only_oldest_and_same_day_preserves_bytes(self):
        first = self.day("2026-11-09")
        second = self.day("2026-11-10")
        STATE.prepare_logs(self.root, "2026-11-10")
        self.assertEqual((second / "tool.log").read_bytes(), b"raw\x00\xff")
        self.day("2026-11-11")
        self.assertFalse(first.exists())
        self.assertEqual(
            sorted(p.name for p in second.parent.iterdir()), ["2026-11-10", "2026-11-11"]
        )
        self.assertEqual((second / "tool.log").read_bytes(), b"raw\x00\xff")

    def test_clock_rollback_does_not_delete(self):
        day = self.day("2026-11-11")
        with self.assertRaises(ValueError):
            STATE.prepare_logs(self.root, "2026-11-10")
        self.assertTrue((day / "tool.log").exists())

    def test_symlink_date_and_unexpected_content_fail_before_deletion(self):
        old = self.day("2026-11-09")
        outside = self.root / "outside"
        outside.mkdir()
        (old.parent / "2026-11-10").symlink_to(outside, target_is_directory=True)
        with self.assertRaises(OSError):
            STATE.prepare_logs(self.root, "2026-11-11")
        self.assertTrue((old / "tool.log").exists())
        (old.parent / "2026-11-10").unlink()
        (old / "backup.zip").write_bytes(b"not a log")
        with self.assertRaises(ValueError):
            STATE.prepare_logs(self.root, "2026-11-11")
        self.assertTrue((old / "backup.zip").exists())

    def test_finish_discards_artifacts_and_preserves_tool_logs(self):
        parent = STATE.runtime_directory(self.root)
        self.addCleanup(shutil.rmtree, parent)
        workspace = parent / "run-example"
        task = workspace / "00-scripts-ruff"
        step = task / "00-ruff"
        step.mkdir(parents=True)
        (step / "stdout.log").write_bytes(b"raw\x00\xff")
        (step / "native.json.log").write_text('{"results":[{"test_id":"B101","line_number":8}]}')
        (step / "artifact.zip").write_bytes(b"discard")
        (task / "codeql-server-dependencies.json").write_text(
            '{"registry/source":{"sha256":"fixture"}}'
        )
        codeql = task / "codeql"
        codeql.mkdir()
        (codeql / "server-cargo.stderr.log").write_bytes(b"cargo stderr\x00\xff")
        status = b'{"returncode":101,"critical":true}\n'
        (codeql / "server-cargo.stderr.status.jsonl").write_bytes(status)
        (workspace / "results.json").write_text('{"complete":false}')
        integration = workspace / "01-integration-publication_http"
        integration.mkdir()
        (integration / "result.json").write_text('{"state":"error","returncode":17}')
        log_dir = STATE.prepare_logs(self.root)
        STATE.finish(self.root, workspace, log_dir, 2)
        self.assertFalse(workspace.exists())
        self.assertEqual(
            (log_dir / "run-example-00-scripts-ruff-00-ruff.stdout.log").read_bytes(),
            b"raw\x00\xff",
        )
        self.assertIn(
            "B101", (log_dir / "run-example-00-scripts-ruff-00-ruff.native.json.log").read_text()
        )
        self.assertTrue(
            (log_dir / "run-example-00-scripts-ruff.codeql-server-dependencies.log").is_file()
        )
        self.assertEqual(
            (log_dir / "run-example-00-scripts-ruff.server-cargo.stderr.log").read_bytes(),
            b"cargo stderr\x00\xff",
        )
        self.assertEqual(
            (
                log_dir / "run-example-00-scripts-ruff.server-cargo.stderr.status.jsonl.log"
            ).read_bytes(),
            status,
        )
        STATE.prepare_logs(self.root)
        self.assertEqual(
            (log_dir / "run-example-01-integration-publication_http.result.log").read_text(),
            '{"state":"error","returncode":17}',
        )
        self.assertTrue(all(p.suffix == ".log" and p.is_file() for p in log_dir.iterdir()))
        self.assertEqual((log_dir / "run-example-automate.exit.log").stat().st_mode & 0o777, 0o600)

    def test_finish_rejects_external_workspace(self):
        parent = STATE.runtime_directory(self.root)
        self.addCleanup(shutil.rmtree, parent)
        external = self.root / "run-example"
        external.mkdir()
        with self.assertRaises(ValueError):
            STATE.finish(self.root, external, STATE.prepare_logs(self.root), 0)
        self.assertTrue(external.exists())

    def test_lock_descriptor_required(self):
        parent = STATE.runtime_directory(self.root)
        self.addCleanup(shutil.rmtree, parent)
        with self.assertRaises(ValueError):
            STATE.require_lock(self.root, None)
        with (parent / "workspace.lock").open("r+") as stream:
            STATE.require_lock(self.root, stream.fileno())
        self.assertEqual(os.stat(parent).st_mode & 0o777, 0o700)
