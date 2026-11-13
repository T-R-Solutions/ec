"""Regressões de falso verde e triagem vinculada ao conteúdo das fontes."""

import copy
import hashlib
import io
import os
import sys
import tempfile
import unittest
from pathlib import Path
from types import SimpleNamespace
from unittest.mock import patch

from tools.quality.native_gates import bandit_review, gitleaks_review
from tools.quality.sarif_report import evaluate, source_path


def sarif_fixture():
    return {
        "version": "2.1.0",
        "runs": [
            {
                "tool": {
                    "driver": {
                        "name": "CodeQL",
                        "rules": [{"id": "security"}],
                        "notifications": [{"id": "rust/diagnostics/successfully-extracted-files"}],
                    }
                },
                "results": [],
                "artifacts": [{"location": {"uri": "src/lib.rs"}}],
                "invocations": [
                    {
                        "executionSuccessful": True,
                        "toolExecutionNotifications": [
                            {
                                "descriptor": {
                                    "id": "rust/diagnostics/successfully-extracted-files",
                                    "index": 0,
                                },
                                "level": "none",
                                "locations": [
                                    {
                                        "physicalLocation": {
                                            "artifactLocation": {"uri": "src/lib.rs"}
                                        }
                                    }
                                ],
                            }
                        ],
                    }
                ],
                "properties": {
                    "metricResults": [
                        {
                            "ruleId": "rust/summary/number-of-files-extracted-with-errors",
                            "value": 0,
                        },
                        {
                            "ruleId": "rust/summary/number-of-successfully-extracted-files",
                            "value": 1,
                        },
                    ]
                },
            }
        ],
    }


class SarifEvidenceTests(unittest.TestCase):
    def test_root_project_preserves_paths_and_rejects_external_locations(self):
        self.assertEqual(source_path("file:///project/tools/task.py", "."), "tools/task.py")
        self.assertEqual(source_path("tools/task.py", "."), "tools/task.py")
        for location in (
            "file:///etc/passwd",
            "https://example.org/task.py",
            "/project/tools/%2e%2e/task.py",
            "../task.py",
        ):
            with self.subTest(location=location), self.assertRaises(ValueError):
                source_path(location, ".")

    def test_effective_notification_level_and_overrides(self):
        for change, passes in [
            ("explicit-note", True),
            ("omitted", False),
            ("inherited-warning", False),
            ("inherited-note", True),
            ("override-warning", False),
            ("override-index", False),
            ("invalid", False),
        ]:
            report = sarif_fixture()
            run = report["runs"][0]
            invocation = run["invocations"][0]
            notification = invocation["toolExecutionNotifications"][0]
            if change == "explicit-note":
                notification["level"] = "note"
            else:
                notification.pop("level")
            if change.startswith("inherited") or change in ("override-warning", "override-index"):
                run["tool"]["driver"]["notifications"][0]["defaultConfiguration"] = {
                    "level": "note" if change != "inherited-warning" else "warning"
                }
            if change in ("override-warning", "override-index"):
                invocation["notificationConfigurationOverrides"] = [
                    {
                        "descriptor": {"id": notification["descriptor"]["id"]},
                        "configuration": {"level": "warning"},
                    }
                ]
            if change == "override-index":
                notification["descriptor"].pop("index")
                invocation["notificationConfigurationOverrides"][0]["descriptor"] = {"index": 0}
            if change == "invalid":
                notification["level"] = "unexpected"
            with self.subTest(change=change):
                if passes:
                    self.assertEqual(evaluate(report)["state"], "passed")
                else:
                    with self.assertRaises(ValueError):
                        evaluate(report)

    def test_artifacts_cannot_replace_healthy_extraction_or_metrics(self):
        good = sarif_fixture()
        args = (["application/agents/src/lib.rs"], "application/agents", "rust")
        self.assertEqual(evaluate(good, *args)["state"], "passed")
        for mutation in (
            "artifacts-only",
            "extraction-errors",
            "insufficient",
            "missing-metrics",
            "low-quality",
            "low-quality-note",
            "low-quality-index",
        ):
            report = copy.deepcopy(good)
            run = report["runs"][0]
            if mutation == "artifacts-only":
                run["invocations"][0]["toolExecutionNotifications"] = []
            elif mutation == "extraction-errors":
                run["properties"]["metricResults"][0]["value"] = 1
            elif mutation == "insufficient":
                run["properties"]["metricResults"][1]["value"] = 0
            elif mutation == "missing-metrics":
                run.pop("properties")
            else:
                run["tool"]["driver"]["notifications"].append({
                    "id": "rust/diagnostic/database-quality"
                })
                run["invocations"][0]["toolExecutionNotifications"].append({
                    "descriptor": {"index": 1}
                    if mutation == "low-quality-index"
                    else {"id": "rust/diagnostic/database-quality"},
                    **(
                        {"level": "note"}
                        if mutation in ("low-quality-note", "low-quality-index")
                        else {}
                    ),
                })
            with self.subTest(mutation=mutation), self.assertRaises(ValueError):
                evaluate(report, *args)


class BanditReviewTests(unittest.TestCase):
    def test_public_indicator_review_is_bound_to_content_and_never_applies_to_history(self):
        digest = hashlib.sha256(b"public indicator fixture").hexdigest()
        path = "rules/indicator.yar"
        finding = {"File": "/project/" + path, "RuleID": "generic-api-key", "StartLine": 7}
        sources = {path: {"sha256": digest}}
        reviews = {
            path: {
                "sha256": digest,
                "rules": [
                    {"id": "generic-api-key", "lines": [7], "reason": "Indicador público revisado."}
                ],
            }
        }
        report = [finding]
        self.assertEqual(gitleaks_review(report, sources, reviews)["reviewed_count"], 1)
        historical = gitleaks_review(report, sources, reviews, history=True)
        self.assertEqual(historical["reviewed_count"], 0)
        self.assertEqual(historical["finding_count"], 1)
        absent_identity = copy.deepcopy(reviews)
        absent_identity[path].pop("sha256")
        self.assertEqual(gitleaks_review(report, {}, absent_identity)["finding_count"], 1)
        for mutation in ("hash", "line", "rule", "new", "reason"):
            changed_report, changed_policy = copy.deepcopy(report), copy.deepcopy(reviews)
            if mutation == "hash":
                changed_policy[path]["sha256"] = "0" * 64
            elif mutation == "line":
                changed_report[0]["StartLine"] = 8
            elif mutation == "rule":
                changed_report[0]["RuleID"] = "github-pat"
            elif mutation == "new":
                changed_report.append(dict(finding, StartLine=8))
            else:
                changed_policy[path]["rules"][0]["reason"] = ""
            with self.subTest(mutation=mutation):
                self.assertEqual(
                    gitleaks_review(changed_report, sources, changed_policy)["finding_count"], 1
                )
        self.assertEqual(report, [finding])

    def test_review_is_bound_to_file_hash_rule_and_line(self):
        digest = hashlib.sha256(b"fixture source").hexdigest()
        finding = dict(filename="/project/tools/example.py", test_id="B603", line_number=7)
        sources = {"tools/example.py": {"sha256": digest}}
        reviews = {
            "tools/example.py": dict(
                sha256=digest,
                rules=[dict(id="B603", lines=[7], reason="Comando literal sobre fixture privada.")],
            )
        }
        report = {"results": [finding]}
        self.assertEqual(bandit_review(report, sources, reviews)["reviewed_count"], 1)
        self.assertEqual(report["results"], [finding])
        for mutation in ("hash", "line", "rule", "new", "reason"):
            data, policy = copy.deepcopy(report), copy.deepcopy(reviews)
            if mutation == "hash":
                policy["tools/example.py"]["sha256"] = "0" * 64
            elif mutation == "line":
                data["results"][0]["line_number"] = 8
            elif mutation == "rule":
                data["results"][0]["test_id"] = "B602"
            elif mutation == "new":
                data["results"].append(dict(finding, line_number=9))
            else:
                policy["tools/example.py"]["rules"][0]["reason"] = ""
            with self.subTest(mutation=mutation):
                result = bandit_review(data, sources, policy)
                self.assertEqual(result["state"], "findings")
                self.assertGreater(result["finding_count"], 0)


class RustExtractorDiagnosticsTests(unittest.TestCase):
    def test_child_failure_preserves_original_stderr_and_exit(self):
        from tools.quality.codeql_rust import execute

        with tempfile.TemporaryDirectory(prefix="ec rust diagnostic ") as temp:
            log = Path(temp) / "cargo.stderr.log"
            captured = io.BytesIO()
            with patch("sys.stderr", SimpleNamespace(buffer=captured)):
                code = execute(
                    sys.executable,
                    str(log),
                    [
                        "-c",
                        "import sys; sys.stderr.buffer.write(bytes([99,97,117,115,97,255,10])); sys.exit(7)",
                    ],
                )
            self.assertEqual(code, 7)
            self.assertEqual(captured.getvalue(), b"causa\xff\n")
            self.assertTrue(log.read_bytes().endswith(captured.getvalue()))
            self.assertIn(b"exit=7", log.read_bytes())
            self.assertEqual(log.stat().st_mode & 0o777, 0o600)

    def test_jobserver_descriptors_reach_the_real_child(self):
        from tools.quality.codeql_rust import execute

        reader, writer = os.pipe()
        try:
            os.write(writer, b"job")
            with tempfile.TemporaryDirectory(prefix="ec jobserver ") as temp:
                captured = io.BytesIO()
                with (
                    patch.dict(
                        os.environ,
                        CARGO_MAKEFLAGS=f"-j --jobserver-fds={reader},{writer} --jobserver-auth={reader},{writer}",
                    ),
                    patch("sys.stderr", SimpleNamespace(buffer=captured)),
                ):
                    result = execute(
                        sys.executable,
                        str(Path(temp) / "rustc.stderr.log"),
                        ["-c", f"import os; os.write(2, os.read({reader}, 3))"],
                    )
                self.assertEqual(result, 0)
                self.assertEqual(captured.getvalue(), b"job")
        finally:
            os.close(reader)
            os.close(writer)

    def test_failed_build_and_missing_runtime_evidence_are_rejected(self):
        import json

        from tools.quality.codeql_rust import validate_status

        with tempfile.TemporaryDirectory(prefix="ec rust status ") as temp:
            directory = Path(temp)
            for tool in ("cargo", "rustc"):
                (directory / f"agent-{tool}.stderr.status.jsonl").write_text(
                    json.dumps(
                        dict(
                            tool=tool,
                            operation="check" if tool == "cargo" else "compile",
                            critical=True,
                            returncode=0,
                        )
                    )
                    + "\n"
                )
            validate_status(directory, "agent", 1048576)
            record = dict(tool="cargo", operation="check", critical=True, returncode=101)
            (directory / "agent-cargo.stderr.status.jsonl").write_text(json.dumps(record) + "\n")
            with self.assertRaisesRegex(ValueError, "falha interna"):
                validate_status(directory, "agent", 1048576)
            (directory / "agent-cargo.stderr.status.jsonl").unlink()
            with self.assertRaisesRegex(ValueError, "ausente"):
                validate_status(directory, "agent", 1048576)
