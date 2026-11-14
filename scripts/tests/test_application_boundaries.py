"""Rejeições independentes para deriva das fronteiras e do contrato da aplicação."""

import json
import tempfile
import unittest
from pathlib import Path

from tools.quality.application_boundaries import violations

ROOT = Path(__file__).resolve().parents[2]


class ApplicationBoundariesTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory(prefix="ec boundaries ")
        self.root = Path(self.temp.name)
        self.addCleanup(self.temp.cleanup)

    def write(self, name, text):
        target = self.root / name
        target.parent.mkdir(parents=True, exist_ok=True)
        target.write_text(text)

    def test_current_application_respects_ownership(self):
        self.assertEqual(violations(ROOT), [])

    def test_dependency_version_cannot_be_reintroduced_in_a_member(self):
        self.write("application/agents/Cargo.toml", '[workspace.dependencies]\nserde_json="1"\n')
        path = "application/agents/crates/example/Cargo.toml"
        self.write(path, '[dependencies]\nserde_json.workspace=true\n')
        self.assertEqual(violations(self.root), [])
        self.write(path, '[dependencies]\nserde_json="1"\n')
        self.assertTrue(any("autoridade do workspace" in item for item in violations(self.root)))

    def test_cross_component_private_dependency_is_rejected(self):
        self.write("application/agents/Cargo.toml", "[workspace.dependencies]\n")
        self.write("application/agents/crates/example/Cargo.toml",
                   '[dependencies]\nec-server-access={path="../../../server/backend/crates/ec-server-access"}\n')
        self.assertTrue(any("entre componentes" in item for item in violations(self.root)))

    def test_workspace_inheritance_cannot_hide_private_cross_component_dependency(self):
        self.write("application/agents/Cargo.toml",
                   '[workspace.dependencies]\nec-server-access={path="../server/backend/crates/ec-server-access"}\n')
        self.write("application/agents/crates/example/Cargo.toml",
                   '[dependencies]\nec-server-access.workspace=true\n')
        self.assertTrue(any("entre componentes" in item for item in violations(self.root)))

    def test_public_entry_and_local_module_are_allowed_but_private_import_is_rejected(self):
        path = "application/server/frontend/src/app/view.ts"
        self.write(path, "import { View } from '../domains/runs/src'\n")
        self.write("application/server/frontend/src/domains/runs/src/index.ts", "export { View } from './View.vue'\n")
        self.assertEqual(violations(self.root), [])
        self.write(path, "import View from '../domains/runs/src/View.vue'\n")
        self.assertTrue(any("implementação privada" in item for item in violations(self.root)))

    def test_domain_cannot_import_composition(self):
        self.write("application/server/frontend/src/domains/runs/src/bad.ts",
                   "import { state } from '../../../app/state'\n")
        self.assertTrue(any("depende da composição" in item for item in violations(self.root)))

    def test_contract_change_requires_database_compatibility_review(self):
        descriptor = json.loads((ROOT / "application/contracts/pipeline-v1.json").read_text())
        self.write("application/server/backend/migrations/003_results.sql",
                   (ROOT / "application/server/backend/migrations/003_results.sql").read_text())
        descriptor["constants"]["MAX_ARTIFACT_BYTES"]["value"] += 1
        self.write("application/contracts/pipeline-v1.json", json.dumps(descriptor))
        self.assertEqual(violations(self.root), ["contrato incompatível com migration v1: MAX_ARTIFACT_BYTES"])

    def test_field_addition_cannot_silently_bypass_semantic_validation(self):
        descriptor = json.loads((ROOT / "application/contracts/pipeline-v1.json").read_text())
        self.write("application/server/backend/crates/ec-server-projects/src/lib.rs",
                   (ROOT / "application/server/backend/crates/ec-server-projects/src/lib.rs").read_text())
        descriptor["fields"].append("unvalidated")
        self.write("application/contracts/pipeline-v1.json", json.dumps(descriptor))
        self.assertEqual(violations(self.root), ["campos públicos sem validação semântica correspondente"])

    def test_replay_decision_cannot_move_back_to_http_composition(self):
        self.write("application/server/backend/crates/ec-server-app/src/service.rs",
                   'fn publish() { return Err((409, "request_conflict")); }\n')
        self.assertTrue(any("decisão de replay duplicada" in item for item in violations(self.root)))

    def test_replay_documentation_and_test_oracles_are_not_business_decisions(self):
        self.write("application/server/backend/crates/ec-server-app/src/service.rs",
                   '// request_conflict pertence ao domínio\n')
        self.write("application/server/backend/crates/ec-server-app/src/service_tests.rs",
                   'assert_eq!(actual, Err((409, "request_conflict")));\n')
        self.assertEqual(violations(self.root), [])
