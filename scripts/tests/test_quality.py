"""Oráculos dos gates padrão; fixtures não certificam os motores reais."""

import copy
import json
import os
import subprocess
import tempfile
import unittest
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]


class QualityTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory(prefix="ec quality with spaces ")
        self.root = Path(self.temp.name)
        self.env = dict(os.environ, EC_ROOT=str(self.root), EC_TASK_DIR=str(self.root / "task"))
        (self.root / "task").mkdir()
        self.write("AGENTS.md", "fixture\n")
        self.write("README.md", "fixture\n")

    def tearDown(self):
        self.temp.cleanup()

    def write(self, path, content):
        target = self.root / path
        target.parent.mkdir(parents=True, exist_ok=True)
        target.write_text(content)
        return target

    def document(self, path, value):
        return self.write(path, json.dumps(value))

    def run_shell(self, script, *args):
        return subprocess.run(  # Script versionado e argumentos separados, sem shell=True.
            ["bash", str(ROOT / "scripts" / script), *map(str, args)],  # Bash do PATH confiável do operador.
            cwd=self.root,
            env=self.env,
            capture_output=True,
            timeout=30,
            check=False,
        )

    def library(self, program, *args):
        # O programa é literal de cada teste; paths/valores não viram código shell.
        harness = (
            """set -euo pipefail
CODE=$1 ROOT=$2 TASK_DIR=$2/task
shift 2
source "$CODE/scripts/stacks/ec/common.sh"
source "$CODE/scripts/stacks/ec/inputs.sh"
"""
            + program
        )
        return subprocess.run(  # Programa fixture literal; valores passam em parâmetros posicionais.
            ["bash", "-c", harness, "fixture", str(ROOT), str(self.root), *map(str, args)],  # Bash do PATH confiável do operador.
            cwd=self.root,
            env=self.env,
            capture_output=True,
            timeout=30,
            check=False,
        )

    def test_atomic_result_preserves_previous_document_on_invalid_or_failed_write(self):
        destination = self.root / "result.json"
        destination.write_text('{"state":"previous"}\n')
        for content in ("", "{", "{} {}"):
            result = self.library("""printf '%s' "$1" | atomic_json "$ROOT/result.json" """, content)
            self.assertNotEqual(result.returncode, 0)
            self.assertEqual(json.loads(destination.read_text()), {"state": "previous"})
        result = self.library("""printf '{"state":"current"}\\n' | atomic_json "$ROOT/result.json" """)
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertEqual(json.loads(destination.read_text()), {"state": "current"})

    def lcov(self, hit=80, total=100, source="a.rs"):
        return f"SF:{source}\nLF:{total}\nLH:{hit}\nFNF:0\nFNH:0\nend_of_record\n"

    def test_default_and_selected_catalog_keep_new_checks_once(self):
        expected = {
            ("server", "geiger"),
            ("agent", "geiger"),
            ("server", "coverage"),
            ("agent", "coverage"),
            ("frontend", "coverage"),
            ("security", "trivy"),
            ("security", "codeql"),
        }
        result = self.run_shell("automate.sh", "plan")
        self.assertEqual(result.returncode, 0, result.stderr)
        tasks = json.loads(result.stdout)["tasks"]
        keys = [(task["project"], task["name"]) for task in tasks]
        self.assertLessEqual(expected, set(keys))
        self.assertEqual(len(keys), len(set(keys)))
        for project in ("server", "agent"):
            self.assertLess(
                keys.index((project, "coverage")), keys.index((project, "harness-build"))
            )
        for project, check in sorted(expected):
            result = self.run_shell(
                "automate.sh", "plan", "--surface", project, "--check", check, "--check", check
            )
            self.assertEqual(result.returncode, 0, result.stderr)
            selected = json.loads(result.stdout)["tasks"]
            self.assertEqual(
                [
                    (t["project"], t["name"])
                    for t in selected
                    if t["project"] not in ("docs", "prepare")
                ],
                [(project, check)],
            )
        self.assertFalse((self.root / ".local").exists())

    def test_coverage_threshold_has_no_rounding_or_cross_component_average(self):
        policy = self.document(
            "coverage.json",
            {
                "format": "lcov",
                "report": "coverage.lcov",
                "minimum": {"lines": 80},
            },
        )
        for hit, expected in ((7999, 1), (8000, 0), (8001, 0)):
            with self.subTest(hit=hit):
                self.write("coverage.lcov", self.lcov(hit, 10000))
                result = self.run_shell("stacks/ec/metrics/check.sh", "coverage", policy)
                self.assertEqual(result.returncode, expected, result.stderr)
                metric = json.loads(result.stdout)["metrics"]["lines"]
                self.assertEqual((metric["covered"], metric["total"]), (hit, 10000))

    def test_coverage_invalid_duplicate_empty_and_missing_reports_fail(self):
        policy = self.document(
            "coverage.json",
            {
                "format": "lcov",
                "report": "coverage.lcov",
                "minimum": {"lines": 80},
            },
        )
        self.assertEqual(
            self.run_shell("stacks/ec/metrics/check.sh", "coverage", policy).returncode, 3
        )
        for report in (
            "",
            "SF:a.rs\nLF:100\n",
            self.lcov(101),
            self.lcov(0, 0),
            self.lcov() + self.lcov(),
        ):
            with self.subTest(report=report):
                self.write("coverage.lcov", report)
                self.assertEqual(
                    self.run_shell("stacks/ec/metrics/check.sh", "coverage", policy).returncode, 2
                )

    def test_coverage_inventory_rejects_omitted_unexercised_file(self):
        first = str(self.root / "a.rs")
        second = str(self.root / "never-exercised.rs")
        self.document("sources.json", [first, second])
        self.write("coverage.lcov", self.lcov(100, source=first))
        policy = self.document(
            "coverage.json",
            {
                "format": "lcov",
                "report": "coverage.lcov",
                "sources": "sources.json",
                "minimum": {"lines": 80},
            },
        )
        self.assertEqual(
            self.run_shell("stacks/ec/metrics/check.sh", "coverage", policy).returncode, 2
        )
        self.write("coverage.lcov", self.lcov(100, source=first) + self.lcov(0, source=second))
        self.assertEqual(
            self.run_shell("stacks/ec/metrics/check.sh", "coverage", policy).returncode, 1
        )

    def test_coverage_never_reuses_previous_report(self):
        self.write("src/a.ts", "export const a = 1\n")
        self.write("task/coverage-old/coverage.lcov", self.lcov(100))
        result = self.library(
            'source "$CODE/scripts/stacks/ec/metrics/coverage.sh"\ncoverage_begin\ncoverage_finish\n'
        )
        self.assertEqual(result.returncode, 3, result.stderr)
        self.assertTrue((self.root / "task/coverage-old/coverage.lcov").exists())

    def test_frontend_producer_cleanup_cannot_erase_acceptance_evidence(self):
        self.write("application/server/frontend/src/a.ts", "export const a = 1\n")
        for name in ("package.json", "package-lock.json", "node_modules/.package-lock.json"):
            self.document(
                "application/server/frontend/" + name, {"scripts": {"coverage": "fixture"}}
            )
        for package in ("vitest", "@vitest/coverage-v8"):
            self.document(
                "application/server/frontend/node_modules/" + package + "/package.json",
                {"version": "4.1.12"},
            )
        directory = self.root / "bin"
        directory.mkdir()
        node = directory / "node"
        node.write_text("#!/bin/bash\nexit 0\n")
        node.chmod(0o700)
        npm = directory / "npm"
        npm.write_text("""#!/usr/bin/python3
import os
from pathlib import Path
import shutil
import sys
if '--version' in sys.argv:
    print('fixture')
    sys.exit(0)
output = Path(next(a.split('=', 1)[1] for a in sys.argv if a.startswith('--coverage.reportsDirectory=')))
shutil.rmtree(output)
output.mkdir()
source = Path(os.environ['EC_ROOT']) / 'application/server/frontend/src/a.ts'
(output / 'lcov.info').write_text(f'SF:{source}\\nLF:1\\nLH:1\\nFNF:0\\nFNH:0\\nend_of_record\\n')
""")
        npm.chmod(0o700)
        self.env["PATH"] = str(directory) + os.pathsep + os.environ["PATH"]
        result = subprocess.run(  # Script versionado, runtime fixture e argv separado.
            ["/bin/bash", str(ROOT / "scripts/stacks/frontend/check.sh"), "coverage"],
            cwd=self.root / "application/server/frontend",
            env=self.env,
            capture_output=True,
            timeout=30,
            check=False,
        )
        self.assertEqual(result.returncode, 0, result.stderr + result.stdout)
        self.assertEqual(len(list((self.root / "task").glob("coverage-*/inputs-before.json"))), 1)

    def test_coverage_rejects_input_mutation(self):
        self.write("application/server/frontend/src/a.ts", "export const a = 1\n")
        prefix = 'source "$CODE/scripts/stacks/ec/metrics/coverage.sh"\ncd "$ROOT/application/server/frontend"\ncoverage_begin\n'
        result = self.library(prefix + "echo changed >> src/a.ts\ncoverage_finish\n")
        self.assertEqual(result.returncode, 2, result.stderr)

    def sarif(self):
        return {
            "version": "2.1.0",
            "runs": [
                {
                    "tool": {"driver": {"name": "CodeQL", "rules": [{"id": "fixture"}]}},
                    "results": [],
                    "invocations": [{"executionSuccessful": True}],
                }
            ],
        }

    def parse(self, kind, report, code=0, inputs=None):
        path = self.document("report.json", report)
        args = [kind, path, code]
        if inputs is not None:
            args.extend([self.document("inputs.json", inputs), "/snapshot"])
        return self.run_shell("stacks/ec/security/check-report.sh", *args)

    def test_codeql_zero_exit_with_results_is_findings(self):
        report = self.sarif()
        report["runs"][0]["results"] = [{"ruleId": "fixture"}]
        result = self.parse("sarif", report)
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertEqual(json.loads(result.stdout), {"state": "findings", "finding_count": 1})
        self.assertEqual(self.parse("sarif", report, 1).returncode, 2)

    def test_codeql_rejects_partial_diagnostics_suppressions_and_empty_suite(self):
        self.assertEqual(self.parse("sarif", self.sarif()).returncode, 0)
        for change in ("execution", "diagnostic", "configuration", "suppression", "rules"):
            report = self.sarif()
            run = report["runs"][0]
            if change == "execution":
                run["invocations"][0]["executionSuccessful"] = False
            elif change in ("diagnostic", "configuration"):
                key = (
                    "toolExecutionNotifications"
                    if change == "diagnostic"
                    else "toolConfigurationNotifications"
                )
                run["invocations"][0][key] = [{"level": "warning"}]
            elif change == "suppression":
                run["results"] = [{"ruleId": "fixture", "suppressions": [{"status": "accepted"}]}]
            else:
                run["tool"]["driver"]["rules"] = []
            with self.subTest(change=change):
                self.assertEqual(self.parse("sarif", report).returncode, 2)

    def test_trivy_requires_all_lockfiles_and_counts_all_finding_types(self):
        locks = [
            "application/agents/Cargo.lock",
            "application/server/backend/Cargo.lock",
            "application/server/frontend/package-lock.json",
        ]
        report = {
            "SchemaVersion": 2,
            "ArtifactName": "snapshot",
            "Results": [
                {"Target": "/snapshot/" + path, "Packages": [{"Name": "fixture"}]} for path in locks
            ],
        }
        inputs = dict.fromkeys(locks, "hash")
        self.assertEqual(self.parse("trivy", report, inputs=inputs).returncode, 0)
        incomplete = copy.deepcopy(report)
        incomplete["Results"].pop()
        self.assertEqual(self.parse("trivy", incomplete, inputs=inputs).returncode, 2)
        report["Results"][0].update(
            Vulnerabilities=[{"VulnerabilityID": "fixture"}],
            Secrets=[{"RuleID": "fixture"}],
            Misconfigurations=[{"ID": "fixture", "Status": "FAIL"}],
        )
        result = self.parse("trivy", report, 1, inputs)
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertEqual(json.loads(result.stdout)["finding_count"], 3)
        self.assertEqual(self.parse("trivy", report, 0, inputs).returncode, 2)
        self.assertEqual(self.parse("trivy", report, 7, inputs).returncode, 2)

    def test_trivy_redaction_preserves_count_without_secret_values(self):
        report = {
            "SchemaVersion": 2,
            "ArtifactName": "fixture",
            "Results": [
                {
                    "Target": "fixture",
                    "Secrets": [
                        {
                            "RuleID": "fixture",
                            "Match": "sensitive-fixture-value",
                            "Code": {"Lines": [{"Content": "sensitive-fixture-value"}]},
                            "Severity": "HIGH",
                            "StartLine": 1,
                            "EndLine": 1,
                        }
                    ],
                }
            ],
        }
        result = subprocess.run(  # Filtro versionado; JSON fixture em stdin.
            ["jq", "-f", str(ROOT / "scripts/stacks/ec/security/redact-trivy.jq")],  # jq do PATH confiável do operador.
            input=json.dumps(report).encode(),
            capture_output=True,
            timeout=10,
            check=False,
        )
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertNotIn(b"sensitive-fixture-value", result.stdout)
        redacted = json.loads(result.stdout)
        parsed = self.parse("trivy", redacted, 1)
        self.assertEqual(parsed.returncode, 0, parsed.stderr)
        self.assertEqual(json.loads(parsed.stdout)["finding_count"], 1)


if __name__ == "__main__":
    unittest.main()
