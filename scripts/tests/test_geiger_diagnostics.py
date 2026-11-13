"""Oráculos de retenção, bytes brutos e limites do diagnóstico complementar."""

import hashlib
import importlib.util
import json
import tempfile
import unittest
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
SPEC = importlib.util.spec_from_file_location(
    "geiger_diagnostics", ROOT / "tools/quality/geiger_diagnostics.py"
)
DIAGNOSTICS = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(DIAGNOSTICS)


class GeigerDiagnosticsTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory(prefix="ec geiger with spaces ")
        self.addCleanup(self.temp.cleanup)
        self.root = Path(self.temp.name)
        self.cargo_home = self.root / "cargo home"
        self.target = self.root / "build target"
        self.workspace = self.root / "workspace"
        self.stdout = self.root / "stdout.bin"
        self.stderr = self.root / "stderr.bin"

    def diagnostic(self, stderr, stdout=b"inventario nativo\n", code=2):
        self.stdout.write_bytes(stdout)
        self.stderr.write_bytes(stderr)
        return DIAGNOSTICS.build_diagnostic(
            self.workspace / "crates/app/Cargo.toml",
            code,
            self.stdout,
            self.stderr,
            str(self.cargo_home),
            str(self.target),
            str(self.workspace),
        )

    def warning(self, path):
        return b"WARNING: Dependency file was never scanned: " + str(path).encode() + b"\n"

    def test_dependency_readme_is_a_build_input_not_a_deletion_candidate(self):
        path = self.cargo_home / "registry/src/registry-id/sha2-0.11.1/README.md"
        result = self.diagnostic(self.warning(path))
        entry = result["files"][0]
        self.assertEqual(entry["path"], str(path))
        self.assertEqual(entry["kind"], "dependency_documentation")
        self.assertEqual(entry["owner"], "sha2-0.11.1")
        self.assertFalse(entry["removal_as_fix"])
        self.assertIn("include_str!", entry["purpose"])
        self.assertFalse(path.exists(), "A classificação não deve criar/abrir o arquivo mencionado")

    def test_generated_rust_is_regenerable_but_deletion_does_not_fix_analysis(self):
        path = self.target / "debug/build/serde_core-hash/out/private.rs"
        result = self.diagnostic(self.warning(path))
        entry = result["files"][0]
        self.assertEqual(entry["kind"], "generated_rust")
        self.assertEqual(entry["owner"], "serde_core-hash")
        self.assertFalse(entry["removal_as_fix"])
        self.assertIn("recompilação", entry["removal"])
        self.assertIn("não resolve", entry["removal"])

    def test_workspace_source_is_preserved(self):
        path = self.workspace / "crates/app/src/lib.rs"
        entry = self.diagnostic(self.warning(path))["files"][0]
        self.assertEqual(entry["kind"], "workspace_rust")
        self.assertFalse(entry["removal_as_fix"])

    def test_unknown_traversal_and_misleading_names_do_not_get_removal_advice(self):
        for path in (
            self.root / "outside/README.md",
            self.cargo_home / "registry/src/registry-id/pkg/README.rs",
            self.target / "debug/build/pkg/out/../../../../outside.rs",
            self.workspace / "../outside.rs",
            "relative/path.rs",
        ):
            with self.subTest(path=path):
                entry = self.diagnostic(self.warning(path))["files"][0]
                self.assertEqual(entry["kind"], "unknown")
                self.assertIsNone(entry["removal_as_fix"])
                self.assertIn("preservar", entry["removal"])

    def test_native_failure_without_known_warning_keeps_error_and_raw_locations(self):
        result = self.diagnostic(b"error: compiler failed with an unknown format\n", code=17)
        self.assertEqual(result["native_exit_code"], 17)
        self.assertEqual(result["files"], [])
        text = DIAGNOSTICS.describe(result)
        self.assertIn("Nenhum caminho reconhecido", text)
        self.assertIn("saída nativa 17", text)
        self.assertIn("Log bruto stderr:", text)

    def test_raw_bytes_and_line_references_are_preserved(self):
        path = self.target / "release/build/serde_core-hash/out/private.rs"
        stderr = b"unknown diagnostic\r\n" + self.warning(path) + self.warning(path)
        stdout = b"\xff\x00native stdout\r\n"
        result = self.diagnostic(stderr, stdout)
        self.assertEqual([entry["stderr_line"] for entry in result["files"]], [2, 3])
        for stream, expected in [("stdout", stdout), ("stderr", stderr)]:
            self.assertEqual(result["raw"][stream]["sha256"], hashlib.sha256(expected).hexdigest())
            self.assertEqual(result["raw"][stream]["bytes"], len(expected))
            self.assertEqual(Path(result["raw"][stream]["path"]).read_bytes(), expected)

    def test_controls_and_invalid_utf8_are_explicitly_unclassified(self):
        prefix = str(self.root).encode("utf-8") + b"/"
        for name in (prefix + b"\xff.rs", prefix + b"\x1b[31m.rs", b"", prefix + b"\t.rs"):
            with self.subTest(name=name):
                result = self.diagnostic(
                    b"WARNING: Dependency file was never scanned: " + name + b"\n"
                )
                self.assertEqual(result["files"][0]["kind"], "unknown")
                self.assertIsNone(result["files"][0]["removal_as_fix"])
                self.assertNotIn("\x1b", DIAGNOSTICS.describe(result))
                json.loads(json.dumps(result, ensure_ascii=True))

    def test_warning_content_is_data_and_does_not_execute_instructions(self):
        marker = self.root / "must not exist"
        path = f"/unclassified/$(touch {marker}).rs"
        result = self.diagnostic(self.warning(path))
        self.assertEqual(result["files"][0]["path"], path)
        self.assertEqual(result["files"][0]["kind"], "unknown")
        self.assertFalse(marker.exists())


if __name__ == "__main__":
    unittest.main()
