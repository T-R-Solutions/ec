"""Rejeições do gate com recibos sintéticos; não são runs de produto."""

import copy
import importlib.util
import json
import tempfile
import unittest
from pathlib import Path

CODE = Path(__file__).resolve().parents[3]
SPEC = importlib.util.spec_from_file_location("task_gate", CODE / "tools/governance/task_gate.py")
gate = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(gate)


class TaskGateTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory(prefix="ec-task-gate-")
        self.addCleanup(self.temp.cleanup)
        self.root = Path(self.temp.name)
        self.feature = "sdd/features/example"
        self.base = self.root / self.feature
        self.base.mkdir(parents=True)
        for name in ("AGENTS.md", "README.md", "implementation.py", "oracle.py"):
            (self.root / name).write_text("original\n")
        (self.base / "spec.md").write_text("REQ-01\n- AC-01 → REQ-01: comportamento esperado\n")
        (self.base / "plan.md").write_text("D-01 R-01 REQ-01\n")
        (self.base / "tasks.md").write_text("T001 AC-01\n")
        self.write(
            self.feature + "/traceability.json",
            [
                {
                    "acceptance": "AC-01",
                    "requirement": "REQ-01",
                    "decision": "D-01",
                    "risk": "R-01",
                    "tasks": ["T001"],
                    "implementation": "implementation.py",
                    "oracle": "oracle.py",
                }
            ],
        )
        self.workflow = {
            "schema_version": 1,
            "surfaces": ["docs"],
            "stages": {
                stage: ["E-01"]
                for stage in ("analysis", "planning", "implementation", "review", "validation")
            },
        }
        self.write(self.feature + "/workflow.json", self.workflow)
        (self.root / "observation.log").write_text("fixture de teste\n")
        paths = ["implementation.py", "oracle.py"] + [
            self.feature + "/" + name
            for name in ("spec.md", "plan.md", "tasks.md", "traceability.json", "workflow.json")
        ]
        self.evidence = {
            "schema_version": 2,
            "environment": "fixture",
            "limitations": [],
            "requirements": [
                {
                    "id": "AC-01",
                    "criterion": "comportamento",
                    "status": "APPROVED",
                    "evidence_ids": ["E-01"],
                }
            ],
            "evidence": [
                {
                    "id": "E-01",
                    "kind": "test",
                    "command_or_method": "fixture",
                    "observed_result": "fixture íntegra",
                    "artifact": "observation.log",
                    "sha256": gate.sdd.digest(self.root / "observation.log"),
                    "inputs": [
                        {
                            "path": p,
                            "sha256": gate.sdd.digest(self.root / p),
                            "role": "implementation",
                        }
                        for p in paths
                    ],
                }
            ],
        }
        self.write(self.feature + "/evidence.json", self.evidence)
        self.config = gate.automation.load_config()
        self.write("scripts/config/automation.json", self.config)
        self.receipt = ".local/logs/2026-11-14/run-fixture-automate.results.log"
        self.prefix = self.receipt.removesuffix("results.log")
        self.result = gate.automation.plan(self.config, self.root, ["docs"])
        self.result.update(
            executed=True,
            complete=True,
            exit_code=0,
            results=[
                {"project": t["project"], "task": t["name"], "returncode": 0, "state": "passed"}
                for t in self.result["tasks"]
            ],
        )
        inputs = {
            name: gate.sdd.digest(self.root / name)
            for name in gate.automation.acceptance_files(self.config, self.root)
        }
        self.write(self.prefix + "inputs.log", inputs)
        self.write(self.prefix + "inputs-after.log", inputs)
        self.result["inputs_sha256"] = gate.sdd.digest(self.root / (self.prefix + "inputs.log"))
        self.write(self.receipt, self.result)
        self.write(
            self.prefix + "exit.log",
            {"exit_code": 0, "cleanup_error": None, "intermediate_artifacts": "discarded"},
        )

    def write(self, path, value):
        target = self.root / path
        target.parent.mkdir(parents=True, exist_ok=True)
        target.write_text(json.dumps(value, indent=2) + "\n")

    def refresh_evidence(self, scope, evidence):
        for item in evidence["evidence"]:
            for binding in item["inputs"]:
                binding["sha256"] = gate.sdd.digest(self.root / binding["path"])
        self.write(scope + "/evidence.json", evidence)

    def prepare_task(self):
        task = "sdd/tasks/correction"
        base = self.root / task
        base.mkdir(parents=True)
        for name in ("spec.md", "plan.md", "tasks.md", "traceability.json"):
            (base / name).write_bytes((self.base / name).read_bytes())
        workflow = copy.deepcopy(self.workflow)
        workflow.update(
            schema_version=2,
            scope={
                "kind": "task",
                "related_features": [
                    {"path": self.feature, "criteria": ["AC-01"], "coverage": "complete"}
                ],
                "rationale": "Correção delimitada",
                "review": "Relações revisadas",
            },
        )
        self.write(task + "/workflow.json", workflow)
        evidence = copy.deepcopy(self.evidence)
        for item in evidence["evidence"]:
            for binding in item["inputs"]:
                binding["path"] = binding["path"].replace(self.feature + "/", task + "/")
            item["inputs"].append(
                {
                    "path": self.feature + "/spec.md",
                    "role": "contract",
                    "sha256": gate.sdd.digest(self.base / "spec.md"),
                }
            )
        self.refresh_evidence(task, evidence)
        return task, workflow, evidence

    def test_task_acceptance_preserves_pending_feature(self):
        task, _, _ = self.prepare_task()
        self.evidence["requirements"][0]["status"] = "UNVERIFIED"
        self.write(self.feature + "/evidence.json", self.evidence)
        unchanged = (self.base / "evidence.json").read_bytes()
        gate.check_task(self.root, task)
        self.assertEqual((self.base / "evidence.json").read_bytes(), unchanged)
        with self.assertRaisesRegex(ValueError, "critério pendente"):
            gate.check_feature(self.root, self.feature)
        with self.assertRaises(ValueError):
            gate.check_feature(self.root, task)

    def test_task_scope_rejects_invalid_relations_and_justifications(self):
        task, workflow, evidence = self.prepare_task()
        mutations = [
            lambda w: w.update(schema_version=1),
            lambda w: w["scope"].update(kind="feature"),
            lambda w: w["scope"].update(related_features=[]),
            lambda w: w["scope"].update(rationale=" "),
            lambda w: w["scope"].update(review=""),
            lambda w: w["scope"]["related_features"][0].update(path="sdd/features/missing"),
            lambda w: w["scope"]["related_features"][0].update(path="../escape"),
            lambda w: w["scope"]["related_features"][0].update(criteria=[]),
            lambda w: w["scope"]["related_features"][0].update(criteria=["AC-99"]),
            lambda w: w["scope"]["related_features"][0].update(criteria=["AC-01", "AC-01"]),
            lambda w: w["scope"]["related_features"][0].update(coverage="partial"),
            lambda w: w["scope"]["related_features"].append(w["scope"]["related_features"][0]),
        ]
        for mutate in mutations:
            value = copy.deepcopy(workflow)
            mutate(value)
            self.write(task + "/workflow.json", value)
            self.refresh_evidence(task, evidence)
            with self.subTest(mutation=mutate), self.assertRaises(ValueError):
                gate.check_task(self.root, task)

    def test_partial_task_cannot_approve_incomplete_feature(self):
        task, workflow, evidence = self.prepare_task()
        with (self.base / "spec.md").open("a") as source:
            source.write("AC-02: segundo comportamento pendente\n")
        workflow["scope"]["related_features"][0]["coverage"] = "partial"
        self.write(task + "/workflow.json", workflow)
        self.refresh_evidence(task, evidence)
        gate.check_task(self.root, task)
        with self.assertRaises(ValueError):
            gate.check_feature(self.root, self.feature)
        workflow["scope"]["related_features"][0]["coverage"] = "complete"
        self.write(task + "/workflow.json", workflow)
        self.refresh_evidence(task, evidence)
        with self.assertRaises(ValueError):
            gate.check_task(self.root, task)

    def test_task_requires_current_related_spec_review(self):
        task, _, evidence = self.prepare_task()
        evidence["evidence"][0]["inputs"] = [
            binding
            for binding in evidence["evidence"][0]["inputs"]
            if binding["path"] != self.feature + "/spec.md"
        ]
        self.refresh_evidence(task, evidence)
        with self.assertRaisesRegex(ValueError, "revisão"):
            gate.check_task(self.root, task)

    def test_feature_v2_preserves_all_criteria(self):
        workflow = copy.deepcopy(self.workflow)
        workflow.update(
            schema_version=2,
            scope={
                "kind": "feature",
                "related_features": [],
                "rationale": "Aceite integral",
                "review": "Todos os critérios revisados",
            },
        )
        self.write(self.feature + "/workflow.json", workflow)
        self.refresh_evidence(self.feature, self.evidence)
        gate.check_feature(self.root, self.feature)
        self.evidence["requirements"][0]["status"] = "UNVERIFIED"
        self.write(self.feature + "/evidence.json", self.evidence)
        with self.assertRaises(ValueError):
            gate.check_feature(self.root, self.feature)

    def test_old_inventory_receipt_never_approves(self):
        for version in (None, 0, True, 2):
            value = copy.deepcopy(self.result)
            value["acceptance_inventory_version"] = version
            self.write(self.receipt, value)
            with self.subTest(version=version), self.assertRaisesRegex(ValueError, "contrato"):
                gate.check_run(self.root, self.receipt, ["docs"])

    def test_agent_config_membership_and_content_changes_after_run(self):
        files = (
            ".codex/config.toml",
            ".codex/agents/nested/reviewer.toml",
            ".codex/skills/method/SKILL.md",
            "docs/constitution.md",
        )
        for name in files:
            path = self.root / name
            path.parent.mkdir(parents=True, exist_ok=True)
            path.write_text("original")
        inputs = {
            name: gate.sdd.digest(self.root / name)
            for name in gate.automation.acceptance_files(self.config, self.root)
        }
        self.write(self.prefix + "inputs.log", inputs)
        self.write(self.prefix + "inputs-after.log", inputs)
        self.result["inputs_sha256"] = gate.sdd.digest(self.root / (self.prefix + "inputs.log"))
        self.write(self.receipt, self.result)
        gate.check_run(self.root, self.receipt, ["docs"])
        for name in files:
            path = self.root / name
            operations = (
                ("change", "delete", "rename")
                if name == ".codex/config.toml"
                else ("change", "delete", "rename", "add")
            )
            for operation in operations:
                extra = path.with_name("added" + path.name)
                if operation == "change":
                    path.write_text("changed")
                elif operation == "delete":
                    path.unlink()
                elif operation == "rename":
                    path.rename(extra)
                else:
                    extra.write_text("new")
                with self.subTest(name=name, operation=operation), self.assertRaises(ValueError):
                    gate.check_run(self.root, self.receipt, ["docs"])
                if extra.exists():
                    extra.unlink()
                path.write_text("original")

    def test_adding_configuration_after_run_rejects(self):
        path = self.root / ".codex/config.toml"
        path.parent.mkdir(parents=True)
        path.write_text("model = 'changed'\n")
        with self.assertRaises(ValueError):
            gate.check_run(self.root, self.receipt, ["docs"])

    def test_valid_fixture(self):
        gate.check_feature(self.root, self.feature)
        gate.check_run(self.root, self.receipt, ["docs"])

    def test_each_missing_stage(self):
        for stage in self.workflow["stages"]:
            with self.subTest(stage=stage):
                value = copy.deepcopy(self.workflow)
                del value["stages"][stage]
                self.write(self.feature + "/workflow.json", value)
                with self.assertRaises(ValueError):
                    gate.check_feature(self.root, self.feature)

    def test_pending_and_stale_evidence(self):
        for state in ("REJECTED", "UNVERIFIED", "INCONCLUSIVE"):
            with self.subTest(state=state):
                self.evidence["requirements"][0]["status"] = state
                self.write(self.feature + "/evidence.json", self.evidence)
                with self.assertRaises(ValueError):
                    gate.check_feature(self.root, self.feature)
        self.evidence["requirements"][0]["status"] = "APPROVED"
        self.write(self.feature + "/evidence.json", self.evidence)
        (self.root / "implementation.py").write_text("changed\n")
        with self.assertRaisesRegex(ValueError, "Stale input"):
            gate.check_feature(self.root, self.feature)

    def test_bad_results(self):
        for state in ("error", "blocked", "skipped", "findings"):
            with self.subTest(state=state):
                value = copy.deepcopy(self.result)
                value["results"][0]["state"] = state
                self.write(self.receipt, value)
                with self.assertRaises(ValueError):
                    gate.check_run(self.root, self.receipt, ["docs"])

    def test_incomplete_and_missing_task(self):
        for key, value in (
            ("complete", False),
            ("executed", False),
            ("exit_code", 2),
            ("exit_code", False),
            ("results", []),
        ):
            with self.subTest(key=key, value=value):
                record = copy.deepcopy(self.result)
                record[key] = value
                self.write(self.receipt, record)
                with self.assertRaises(ValueError):
                    gate.check_run(self.root, self.receipt, ["docs"])

    def test_cleanup_and_missing_receipt(self):
        self.write(self.prefix + "exit.log", {"exit_code": 2, "cleanup_error": "failed"})
        with self.assertRaises(ValueError):
            gate.check_run(self.root, self.receipt, ["docs"])
        (self.root / (self.prefix + "exit.log")).unlink()
        with self.assertRaises(ValueError):
            gate.check_run(self.root, self.receipt, ["docs"])

    def test_wrong_scope_and_duplicate_tasks(self):
        with self.assertRaises(ValueError):
            gate.check_run(self.root, self.receipt, ["docs", "scripts"])
        self.result["tasks"].append(self.result["tasks"][0])
        self.write(self.receipt, self.result)
        with self.assertRaises(ValueError):
            gate.check_run(self.root, self.receipt, ["docs"])

    def test_changed_added_deleted_inputs(self):
        original = (self.root / "README.md").read_bytes()
        (self.root / "README.md").write_text("mutation")
        with self.assertRaises(ValueError):
            gate.check_run(self.root, self.receipt, ["docs"])
        (self.root / "README.md").write_bytes(original)
        new = self.base / "new.md"
        new.write_text("new")
        with self.assertRaises(ValueError):
            gate.check_run(self.root, self.receipt, ["docs"])
        new.unlink()
        (self.base / "plan.md").unlink()
        with self.assertRaises(ValueError):
            gate.check_run(self.root, self.receipt, ["docs"])

    def test_escape_symlink_duplicate_json(self):
        for path in ("../outside", "/etc/passwd"):
            with self.assertRaises(ValueError):
                gate.safe_file(self.root, path)
        (self.root / "alias").symlink_to(self.base, target_is_directory=True)
        with self.assertRaises(ValueError):
            gate.check_feature(self.root, "alias")
        (self.base / "workflow.json").write_text('{"schema_version":1,"schema_version":1}')
        with self.assertRaises(ValueError):
            gate.check_feature(self.root, self.feature)


if __name__ == "__main__":
    unittest.main()
