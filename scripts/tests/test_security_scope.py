"""Oráculos de fontes omitidas e histórico insuficiente; sem execução implícita."""

import copy
import datetime as dt
import hashlib
import json
import tempfile
import unittest
from pathlib import Path
from types import SimpleNamespace
from unittest.mock import patch

from tools.quality import native_gates as native
from tools.quality.automation_config import load_config, select_files, source_files, verify_catalogs
from tools.quality.secret_history import history_identity, history_options
from tools.quality.syft_inventory import verify_lock_inventory
from tools.quality.yara_scan import file_context, parse_ndjson


class SourceCoverageTests(unittest.TestCase):
    def test_lockfile_presence_cannot_hide_a_missing_development_dependency(self):
        with tempfile.TemporaryDirectory() as temp:
            root = Path(temp)
            (root / "package-lock.json").write_text(
                json.dumps({
                    "lockfileVersion": 3,
                    "packages": {
                        "": {"name": "local-app", "version": "1"},
                        "node_modules/runtime": {"version": "1"},
                        "node_modules/dev": {"version": "2", "dev": True},
                    },
                })
            )
            components = {"frontend": {"lockfile": "package-lock.json"}}
            packages = [
                dict(
                    name=name,
                    version=version,
                    type="npm",
                    locations=[dict(path="/project/package-lock.json")],
                )
                for name, version in (("runtime", "1"), ("dev", "2"))
            ]
            report = dict(artifacts=packages)
            self.assertEqual(
                verify_lock_inventory(root, components, report), {"package-lock.json": 2}
            )
            for mutation in ("missing", "version", "provenance"):
                data = copy.deepcopy(report)
                if mutation == "missing":
                    data["artifacts"].pop()
                elif mutation == "version":
                    data["artifacts"][1]["version"] = "3"
                else:
                    data["artifacts"][1]["locations"][0]["path"] = "/project/other-lock.json"
                with self.subTest(mutation=mutation), self.assertRaises(ValueError):
                    verify_lock_inventory(root, components, data)

    def test_dotenv_is_not_lost_to_gitignore_or_generated_exclusions(self):
        config = load_config()
        with tempfile.TemporaryDirectory() as temp:
            root = Path(temp)
            for name in (
                ".env",
                ".env.production",
                "application/service/.env.local",
                "application/service/main.mjs",
                "application/service/node_modules/secret.env",
            ):
                path = root / name
                path.parent.mkdir(parents=True, exist_ok=True)
                path.write_text("fixture")
            (root / ".gitignore").write_text(".env*\napplication/\n")
            for scope in ("all", "trivy", "inventory"):
                with self.subTest(scope=scope):
                    names = source_files(config, root, scope)
                    self.assertLessEqual(
                        {".env", ".env.production", "application/service/.env.local"}, set(names)
                    )
                    self.assertNotIn("application/service/node_modules/secret.env", names)
                    self.assertEqual(names, source_files(config, root, "all"))

    def test_sast_includes_automation_and_javascript_without_scanning_its_own_positive_fixtures(
        self,
    ):
        config = load_config()
        names = [
            "application/server/frontend/src/main.mjs",
            "tools/tool.py",
            "scripts/check.sh",
            "application/integration/tests/contract.py",
            "scripts/rules/opengrep/positive.ts",
        ]
        self.assertEqual(select_files(config, names, "sast"), sorted(names[:-1]))


class GitHistoryCoverageTests(unittest.TestCase):
    def fake_git(self, root, *, shallow=False, commits=None):
        git_dir = root / ".git"
        git_dir.mkdir(exist_ok=True)
        commit = "a" * 40
        values = {
            ("rev-parse", "--show-toplevel"): str(root),
            ("rev-parse", "--is-shallow-repository"): "true" if shallow else "false",
            ("rev-parse", "HEAD"): commit,
            ("rev-list", "--all", "--reflog", "HEAD"): commit if commits is None else commits,
            ("rev-parse", "--absolute-git-dir"): str(git_dir),
            ("rev-parse", "--path-format=absolute", "--git-common-dir"): str(git_dir),
            ("for-each-ref", "--format=%(refname) %(objectname)"): "refs/heads/main " + commit,
        }

        def run(arguments, **kwargs):
            self.assertEqual(kwargs["timeout"], 15)
            self.assertEqual(kwargs["env"]["GIT_OPTIONAL_LOCKS"], "0")
            key = tuple(arguments[5:])
            return SimpleNamespace(returncode=0, stdout=(values[key] + "\n").encode())

        return run

    def test_shallow_missing_and_invalid_history_never_prove_full_coverage(self):
        with tempfile.TemporaryDirectory() as temp:
            root = Path(temp)
            for options, message in [
                ({"shallow": True}, "raso"),
                ({"commits": ""}, "vazio"),
                ({"commits": "invalid"}, "inválido"),
            ]:
                with (
                    self.subTest(options=options),
                    patch(
                        "tools.quality.secret_history.subprocess.run",
                        side_effect=self.fake_git(root, **options),
                    ),
                    self.assertRaisesRegex(ValueError, message),
                ):
                    history_identity(root, 15)
            with (
                patch(
                    "tools.quality.secret_history.subprocess.run",
                    return_value=SimpleNamespace(returncode=128, stdout=b""),
                ),
                self.assertRaisesRegex(ValueError, "indisponível"),
            ):
                history_identity(root, 15)

    def test_identity_changes_with_history_and_objects_are_mounted_read_only(self):
        with tempfile.TemporaryDirectory() as temp:
            root = Path(temp)
            with patch(
                "tools.quality.secret_history.subprocess.run", side_effect=self.fake_git(root)
            ):
                before = history_identity(root, 15)
            with patch(
                "tools.quality.secret_history.subprocess.run",
                side_effect=self.fake_git(root, commits="a" * 40 + "\n" + "b" * 40),
            ):
                after = history_identity(root, 15)
            self.assertNotEqual(before["commits_sha256"], after["commits_sha256"])
            self.assertEqual(after["commit_count"], 2)
            options = history_options(before)
            self.assertIn(str(root / ".git") + ":/ec-git:ro", options)
            self.assertNotIn(":/ec-git:rw", " ".join(options))
            self.assertIn("GIT_NO_REPLACE_OBJECTS=1", options)
            copied = copy.deepcopy(before)
            copied["common_dir"] = str(root / "shared")
            self.assertIn(str(root / "shared") + ":/ec-git-common:ro", history_options(copied))


class ReportCoverageTests(unittest.TestCase):
    def test_native_grype_dismissals_remain_pending_instead_of_approving(self):
        report = dict(
            artifacts=[dict(name="fixture", version="1", type="npm", locations=[dict(path="/project/" + path) for path in native.LOCKS])],
            matches=[],
            ignoredMatches=[
                dict(
                    vulnerability=dict(id="CVE-fixture"),
                    appliedIgnoreRules=[dict(package=dict(name="headers"))],
                )
            ],
            descriptor=dict(db=dict(valid=True, built=dt.datetime.now(dt.UTC).isoformat())),
        )
        result = native.basic_report("grype", report, 0, ())
        self.assertEqual(result["state"], "findings")
        self.assertEqual(result["finding_count"], 1)
        self.assertEqual(result["native_finding_count"], 0)
        self.assertEqual(result["native_suppressed_count"], 1)

    def test_trivy_export_removes_image_environment_and_secret_payloads(self):
        with tempfile.TemporaryDirectory() as temp:
            root = Path(temp)
            folder = root / "step/results"
            folder.mkdir(parents=True)
            report = folder / "native.json"
            report.write_text(
                json.dumps(
                    dict(
                        SchemaVersion=2,
                        Metadata=dict(
                            ImageID="immutable",
                            ImageConfig={"Env": ["TOKEN=private-fixture"]},
                            Layers=[{"CreatedBy": "RUN private-fixture"}],
                        ),
                        Results=[
                            dict(
                                Target="image",
                                Secrets=[
                                    dict(
                                        RuleID="rule",
                                        Match="private-fixture",
                                        Code={"Lines": ["private-fixture"]},
                                    )
                                ],
                                Misconfigurations=[
                                    dict(ID="config", CauseMetadata={"Code": "private-fixture"})
                                ],
                            )
                        ],
                    )
                )
            )
            native.Gate("trivy", root, root).redact_trivy(report)
            self.assertNotIn("private-fixture", report.read_text())
            self.assertEqual(json.loads(report.read_text())["Metadata"]["ImageID"], "immutable")
            self.assertNotIn("private-fixture", (folder.parent / "native.json.log").read_text())


class YaraCoverageTests(unittest.TestCase):
    def test_clean_files_require_explicit_results_and_authorship_is_retained(self):
        with tempfile.TemporaryDirectory() as temp:
            path = Path(temp) / "native.log"
            finding = dict(
                identifier="fixture",
                namespace="upstream",
                meta=[["author", "maintainer"], ["author", "coauthor"]],
            )
            rows = [
                dict(path="/project/clean", rules=[]),
                dict(path="/project/found", rules=[finding]),
            ]
            path.write_text("\n".join(json.dumps(row) for row in rows))
            result = parse_ndjson(path, ["/project/clean", "/project/found"])
            self.assertEqual(result[0]["meta"], [["author", "maintainer"], ["author", "coauthor"]])
            for bad in (
                [rows[1]],
                [*rows, rows[0]],
                [rows[0], dict(path="/project/found", rules=[finding, finding])],
            ):
                path.write_text("\n".join(json.dumps(row) for row in bad))
                with self.assertRaises(ValueError):
                    parse_ndjson(path, ["/project/clean", "/project/found"])

    def test_context_uses_file_bytes_instead_of_an_executable_extension(self):
        with tempfile.TemporaryDirectory() as temp:
            root = Path(temp)
            (root / "fake.exe").write_bytes(b"ordinary text")
            self.assertEqual(file_context(root, "fake.exe")["filetype"], "UNKNOWN")
            body = bytearray(128)
            body[:2] = b"MZ"
            body[60:64] = (100).to_bytes(4, "little")
            body[100:104] = b"PE\0\0"
            (root / "payload.bin").write_bytes(body)
            context = file_context(root, "payload.bin")
            self.assertEqual(context["filetype"], "EXE")
            self.assertEqual(context["extension"], ".bin")


class CatalogIntegrityTests(unittest.TestCase):
    def test_changed_added_and_missing_rules_cannot_reuse_import_receipt(self):
        with tempfile.TemporaryDirectory() as temp:
            root = Path(temp)
            folder = root / "rules"
            folder.mkdir()
            rule = folder / "sample.yar"
            original = b"rule Sample { condition: true }"
            rule.write_bytes(original)
            digest = hashlib.sha256(
                b"sample.yar\0" + hashlib.sha256(original).hexdigest().encode() + b"\n"
            ).hexdigest()
            config = {
                "tools": {
                    "yara-x": {"catalogs": [dict(root="rules", file_count=1, tree_sha256=digest)]}
                }
            }
            self.assertEqual(len(verify_catalogs(config, root, "yara-x")), 1)
            for mutation in ("changed", "added", "missing"):
                with self.subTest(mutation=mutation):
                    rule.write_bytes(original)
                    extra = folder / "extra.yar"
                    extra.unlink(missing_ok=True)
                    if mutation == "changed":
                        rule.write_bytes(b"rule Different { condition: true }")
                    elif mutation == "added":
                        extra.write_bytes(original)
                    else:
                        rule.unlink()
                    with self.assertRaisesRegex(ValueError, "alterado"):
                        verify_catalogs(config, root, "yara-x")
