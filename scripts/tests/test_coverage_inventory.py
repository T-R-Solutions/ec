"""Inventário de fontes completo, independente do relatório que será avaliado."""

import tempfile
import unittest
from pathlib import Path

from tools.quality.coverage_inventory import production_sources


class CoverageInventoryTests(unittest.TestCase):
    def test_workspace_member_outside_component_is_included_and_dependencies_are_not_members(self):
        with tempfile.TemporaryDirectory(prefix="ec inventory ") as directory:
            root = Path(directory)
            packages = []
            for name in ("server/app", "contracts", "dependency"):
                path = root / name
                (path / "src").mkdir(parents=True)
                (path / "Cargo.toml").write_text("fixture\n")
                (path / "src/lib.rs").write_text("pub fn production() {}\n")
                (path / "src/contests.rs").write_text("pub fn domain() {}\n")
                (path / "src/contracts_tests.rs").write_text("fixture de teste\n")
                packages.append({"id": name, "manifest_path": str(path / "Cargo.toml")})
            metadata = {"workspace_members": ["server/app", "contracts"], "packages": packages}
            expected = sorted(str(root / name / "src" / file) for name in ("server/app", "contracts") for file in ("lib.rs", "contests.rs"))
            self.assertEqual(production_sources(metadata), expected)

    def test_missing_member_and_empty_production_never_approve_a_denominator(self):
        for metadata in ({}, {"workspace_members": ["missing"], "packages": []}):
            with self.assertRaises(ValueError):
                production_sources(metadata)
