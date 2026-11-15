"""Catálogo histórico e aceite atual têm oráculos distintos, em fixtures temporárias."""

import copy
import os
import subprocess
import sys
import unittest

from tools.governance.tests import test_task_gate as fixtures

sdd = fixtures.gate.sdd


class CatalogTests(unittest.TestCase):
    def setUp(self):
        self.fixture = fixtures.TaskGateTests("test_valid_fixture")
        self.fixture.setUp()
        self.addCleanup(self.fixture.doCleanups)
        self.root = self.fixture.root
        self.base = self.fixture.base
        for name in (
            "README.md",
            "project.md",
            "constitution.md",
            "workflow.md",
            "evidence.md",
            "templates/spec.md",
            "templates/plan.md",
            "templates/tasks.md",
            "templates/converge.md",
        ):
            path = self.root / "sdd" / name
            path.parent.mkdir(parents=True, exist_ok=True)
            path.write_text("fixture\n")

    def audit(self, **kwargs):
        return sdd.audit(self.root, **kwargs)

    def cli(self, *args):
        # Programa versionado, sem subprocessos de produto, rede ou downloads.
        return subprocess.run(
            [
                sys.executable,
                str(fixtures.CODE / "sdd/tools/validate.py"),
                "--root",
                str(self.root),
                *args,
            ],
            env=dict(os.environ, PYTHONDONTWRITEBYTECODE="1"),
            capture_output=True,
            text=True,
            check=False,
            timeout=10,
        )

    def test_expired_history_passes_catalog_but_cannot_be_reaccepted(self):
        task, _, _ = self.fixture.prepare_task()
        (self.root / "observation.log").unlink()
        # Tarefa nova usa outra observação atual; não renova o recibo antigo.
        current = self.root / "current.log"
        current.write_text("nova execução de fixture\n")
        document = sdd.strict_json(self.root / task / "evidence.json")
        document["evidence"][0].update(artifact="current.log", sha256=sdd.digest(current))
        self.fixture.write(task + "/evidence.json", document)
        self.assertEqual(self.audit(catalog_only=True), [])
        self.assertEqual(self.cli("--catalog-only").returncode, 0)
        self.assertTrue(self.audit())
        fixtures.gate.check_task(self.root, task)
        with self.assertRaises(ValueError):
            fixtures.gate.check_feature(self.root, self.fixture.feature)

    def test_catalog_skips_historical_input_hash_and_existence(self):
        document = copy.deepcopy(self.fixture.evidence)
        document["evidence"][0]["sha256"] = "0" * 64
        document["evidence"][0]["inputs"].append(
            {"path": "expired/source.py", "sha256": "1" * 64, "role": "other"}
        )
        self.fixture.write(self.fixture.feature + "/evidence.json", document)
        self.assertEqual(self.audit(catalog_only=True), [])
        self.assertTrue(self.audit())
        self.assertEqual(self.audit(check_evidence=False), [])
        self.assertEqual(self.cli("--structure-only").returncode, 0)
        self.assertNotEqual(self.cli("--require-approved", "--require-fresh").returncode, 0)

    def test_invalid_json_status_references_and_hash_shapes_always_fail_catalog(self):
        for mutation in (
            "json",
            "status",
            "ref",
            "input-ref",
            "hash",
            "input-hash",
            "input-role",
            "duplicate-id",
            "missing-id",
            "trace",
        ):
            evidence = copy.deepcopy(self.fixture.evidence)
            if mutation == "status":
                evidence["requirements"][0]["status"] = "EXPIRED"
            elif mutation == "ref":
                evidence["requirements"][0]["evidence_ids"] = ["E-missing"]
            elif mutation == "input-ref":
                evidence["evidence"][0]["inputs"][0]["path"] = "../escape.py"
            elif mutation == "hash":
                evidence["evidence"][0]["sha256"] = "invalid"
            elif mutation == "input-hash":
                evidence["evidence"][0]["inputs"][0]["sha256"] = "invalid"
            elif mutation == "input-role":
                evidence["evidence"][0]["inputs"][0]["role"] = "unknown"
            elif mutation == "duplicate-id":
                evidence["evidence"].append(evidence["evidence"][0])
            elif mutation == "missing-id":
                evidence["requirements"] = []
            self.fixture.write(self.fixture.feature + "/evidence.json", evidence)
            if mutation == "json":
                (self.base / "evidence.json").write_text('{"schema_version":2,"schema_version":2}')
            if mutation == "trace":
                self.fixture.write(self.fixture.feature + "/traceability.json", [])
            with self.subTest(mutation=mutation):
                self.assertTrue(self.audit(catalog_only=True))
                self.assertNotEqual(self.cli("--catalog-only").returncode, 0)

    def test_catalog_enumerates_tasks_and_explicit_features_stay_scoped(self):
        task, workflow, _ = self.fixture.prepare_task()
        workflow["stages"]["review"] = ["missing"]
        self.fixture.write(task + "/workflow.json", workflow)
        self.assertTrue(self.audit(catalog_only=True))
        self.assertEqual(self.audit(catalog_only=True, features="sdd/features"), [])

    def test_incomplete_stage_is_valid_catalog_but_not_acceptance(self):
        task, workflow, evidence = self.fixture.prepare_task()
        workflow["stages"]["review"] = []
        self.fixture.write(task + "/workflow.json", workflow)
        self.fixture.refresh_evidence(task, evidence)
        self.assertEqual(self.audit(catalog_only=True), [])
        with self.assertRaises(ValueError):
            fixtures.gate.check_task(self.root, task)

    def test_malformed_nested_workflow_rejects_without_traceback(self):
        task, workflow, _ = self.fixture.prepare_task()
        malformed = [
            [],
            None,
            {"schema_version": 2},
            copy.deepcopy(workflow),
            copy.deepcopy(workflow),
            copy.deepcopy(workflow),
        ]
        malformed[3]["stages"]["review"] = [["E-01"]]
        malformed[4]["scope"]["related_features"][0]["criteria"] = [["AC-01"]]
        malformed[5]["surfaces"] = [{"docs": True}]
        for value in malformed:
            self.fixture.write(task + "/workflow.json", value)
            with self.subTest(workflow=value):
                result = self.cli("--catalog-only")
                self.assertNotEqual(result.returncode, 0)
                self.assertNotIn("Traceback", result.stderr)

    def test_task_requires_workflow_but_legacy_feature_does_not(self):
        task, _, _ = self.fixture.prepare_task()
        (self.root / task / "workflow.json").unlink()
        self.assertTrue(self.audit(catalog_only=True))
        (self.base / "workflow.json").unlink()
        self.assertEqual(self.audit(catalog_only=True, features="sdd/features"), [])

    def test_modes_are_explicit_and_mutually_exclusive(self):
        for args in (
            ("--catalog-only", "--require-fresh"),
            ("--catalog-only", "--structure-only"),
            ("--catalog-only", "--require-approved"),
        ):
            with self.subTest(args=args):
                self.assertNotEqual(self.cli(*args).returncode, 0)

    def test_nonfinite_json_is_never_valid(self):
        path = self.base / "evidence.json"
        path.write_text('{"schema_version": NaN}')
        with self.assertRaises(ValueError):
            sdd.strict_json(path)
        self.assertTrue(self.audit(catalog_only=True))


if __name__ == "__main__":
    unittest.main()
