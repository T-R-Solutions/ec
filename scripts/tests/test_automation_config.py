"""Contratos da configuração e do preparo; nenhum recurso real é executado."""

import copy
import json
import os
import subprocess
import sys
import tempfile
import unittest
from pathlib import Path
from types import SimpleNamespace
from unittest.mock import patch

from tools.quality import automation_config as config
from tools.quality import prepare_automation as prepare


class ConfigurationTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory(prefix="ec config fixture ")
        self.addCleanup(self.temp.cleanup)
        self.root = Path(self.temp.name)
        self.value = config.load_config()

    def load(self, value):
        path = self.root / "automation.json"
        path.write_text(json.dumps(value))
        return config.load_config(path)

    def test_optimized_harness_cannot_drop_assertions_and_report_success(self):
        result = subprocess.run(  # Interpretador corrente; import literal de módulo versionado, sem serviços.
            [sys.executable, "-O", "-c", "import application.integration.tests.settings"],
            cwd=config.CODE,
            capture_output=True,
            text=True,
            env=dict(os.environ, PYTHONDONTWRITEBYTECODE="1"),
            timeout=10,
            check=False,
        )
        self.assertNotEqual(result.returncode, 0)
        self.assertIn("Harnesses Ec exigem asserts ativos", result.stderr)

    def test_rejects_unknown_fields_cycles_missing_dependencies_and_weakened_coverage(self):
        mutations = [
            lambda v: v.update(unknown=True),
            lambda v: v["inputs"].update(scanner_roots=["application"]),
            lambda v: v["inputs"].update(images=["toolbox:active"]),
            lambda v: v["tasks"][0].update(unknown=True),
            lambda v: v["tasks"][0]["depends"].append("unknown:task"),
            lambda v: v["tasks"][0]["depends"].append("docs:governance"),
            lambda v: v["coverage"]["minimum"].update(lines=79),
            lambda v: v["components"]["server"].update(root="../outside"),
            lambda v: v["tools"]["geiger"].update(executor="docker"),
            lambda v: v["environment"].update(forward="HOME"),
            lambda v: v["tasks"][0].update(cwd=False),
            lambda v: v["tasks"][0].update(surfaces=["unknown"]),
            lambda v: v["services"]["frontend"].update(port=v["services"]["http"]["port"]),
            lambda v: v["tasks"][-1].update(argv=["firefox"]),
            lambda v: v["tasks"][-1].update(timeout=0),
        ]
        for mutate in mutations:
            value = copy.deepcopy(self.value)
            mutate(value)
            with self.subTest(mutate=mutate), self.assertRaises((ValueError, KeyError)):
                self.load(value)

    def test_presentation_configuration_is_typed_and_consumable(self):
        value = copy.deepcopy(self.value)
        value["presentation"] = {"enabled": False, "refresh_seconds": 2, "heartbeat_seconds": 15}
        self.assertEqual(self.load(value)["presentation"], value["presentation"])
        for field, invalid in (("enabled", "false"), ("refresh_seconds", 0),
                               ("refresh_seconds", True), ("heartbeat_seconds", 1),
                               ("unknown", 1)):
            candidate = copy.deepcopy(value)
            candidate["presentation"][field] = invalid
            with self.subTest(field=field), self.assertRaises(ValueError):
                self.load(candidate)

    def test_public_secret_reviews_allow_only_supported_tools_and_native_rule_ids(self):
        review = {
            "rules/public.yar": {
                "sha256": "a" * 64,
                "rules": [
                    {"id": "generic-api-key", "lines": [7], "reason": "Indicador público revisado."}
                ],
            }
        }
        value = copy.deepcopy(self.value)
        value["tools"]["gitleaks"]["triage"] = review
        self.load(value)
        for mutation in ("tool", "rule", "hash", "reason", "line"):
            value = copy.deepcopy(self.value)
            value["tools"]["gitleaks"]["triage"] = copy.deepcopy(review)
            target = value["tools"]["gitleaks"]["triage"]["rules/public.yar"]
            if mutation == "tool":
                value["tools"]["ruff"]["triage"] = review
            elif mutation == "rule":
                target["rules"][0]["id"] = "B603"
            elif mutation == "hash":
                target["sha256"] = "invalid"
            elif mutation == "reason":
                target["rules"][0]["reason"] = ""
            else:
                target["rules"][0]["lines"] = [0]
            with self.subTest(mutation=mutation), self.assertRaises(ValueError):
                self.load(value)

    def test_plan_keeps_transitive_dependencies_once_and_before_consumers(self):
        plan = config.plan(self.value, self.root, checks=["firefox"])
        tasks = plan["tasks"]
        ids = [t["project"] + ":" + t["name"] for t in tasks]
        self.assertEqual(len(ids), len(set(ids)))
        self.assertIn("prepare:postgres", ids)
        self.assertIn("prepare:server-cargo", ids)
        self.assertIn("frontend:build", ids)
        self.assertNotIn("e2e:chromium", ids)
        for task in tasks:
            for dependency in task["depends"]:
                self.assertLess(
                    ids.index(dependency), ids.index(task["project"] + ":" + task["name"])
                )

    def test_preparation_is_internal_and_selected_only_by_dependencies(self):
        self.assertNotIn("prepare", self.value["selection"]["surfaces"])
        resources = [t for t in self.value["tasks"] if t["modes"] == ["dependency"]]
        self.assertTrue(resources)
        self.assertTrue(all(t["surfaces"] == [] for t in resources))
        docs = config.plan(self.value, self.root, surfaces=["docs"])["tasks"]
        self.assertTrue(all(t["project"] == "docs" for t in docs))
        full = config.plan(self.value, self.root, scope="complete")["tasks"]
        self.assertEqual(
            {t["name"] for t in full if t["project"] == "prepare"},
            {"server-cargo", "agent-cargo", "rustsec", "postgres"},
        )
        value = copy.deepcopy(self.value)
        resource = next(t for t in value["tasks"] if t["modes"] == ["dependency"])
        resource["surfaces"] = ["server"]
        with self.assertRaisesRegex(ValueError, "preparo por dependência"):
            self.load(value)

    def test_unknown_surface_diagnostic_identifies_task_and_value(self):
        value = copy.deepcopy(self.value)
        value["tasks"][0]["surfaces"] = ["unknown"]
        with self.assertRaisesRegex(ValueError, "docs:governance: unknown"):
            self.load(value)

    def test_cargo_and_browser_backends_do_not_fall_back(self):
        plan = config.plan(self.value, self.root, scope="complete")
        for task in plan["tasks"]:
            if task["project"] in ("server", "agent"):
                self.assertEqual(task["executor"], "cargo")
                self.assertNotIn("docker", task["argv"])
            if task["project"] == "security":
                self.assertEqual(task["executor"], "toolbox")
        browsers = [t for t in plan["tasks"] if t["executor"] == "browser"]
        self.assertEqual({t["name"] for t in browsers}, {"chromium", "firefox"})
        self.assertTrue(all(t["argv"][-1] == t["name"] for t in browsers))

    def test_paths_with_spaces_are_arguments_not_shell_programs(self):
        with patch.object(config, "CODE", self.root / "space $(literal)"):
            plan = config.plan(self.value, self.root, checks=["ruff"])
        command = next(t for t in plan["tasks"] if t["name"] == "ruff")["argv"]
        self.assertEqual(
            command,
            ["bash", str(self.root / "space $(literal)/scripts/stacks/ec/scanner-gate.sh"), "ruff"],
        )

    def test_duplicate_properties_are_rejected(self):
        path = self.root / "automation.json"
        path.write_text('{"schema":1,"schema":1}')
        with self.assertRaises(ValueError):
            config.load_config(path)

    def test_rustup_probe_failure_does_not_abort_independent_surfaces(self):
        inherited = {"HOME": str(self.root), "PATH": os.defpath}

        def binary(name, **_):
            return "/fixture/rustup" if name == "rustup" else None

        for failure in (subprocess.TimeoutExpired(["rustup"], 15), OSError("fixture")):
            with (
                self.subTest(failure=type(failure).__name__),
                patch.object(config.shutil, "which", side_effect=binary),
                patch.object(config.subprocess, "run", side_effect=failure),
            ):
                environment = config.environment(self.value, self.root, inherited)
                self.assertEqual(environment["EC_RUST_TOOLCHAIN"], "")
                self.assertNotIn("RUSTUP_TOOLCHAIN", environment)
                self.assertEqual(environment["HOME"], str(self.root))

    def test_inventory_propagates_access_failure_instead_of_omitting_sources(self):
        (self.root / "application").mkdir()

        def unreadable(*_args, **kwargs):
            kwargs["onerror"](PermissionError("fixture access denied"))

        with (
            patch.object(config.os, "walk", side_effect=unreadable),
            self.assertRaises(PermissionError),
        ):
            config.source_files(self.value, self.root, "all")

    def test_acceptance_inventory_adds_governance_without_expanding_scanners(self):
        extra = (
            ".codex/config.toml",
            ".codex/agents/nested/reviewer.toml",
            ".codex/skills/method/SKILL.md",
            ".codex/skills/method/references/oracle.md",
            "docs/constitution.md",
            "docs/architecture/context.md",
        )
        for name in ("AGENTS.md", "README.md", *extra):
            path = self.root / name
            path.parent.mkdir(parents=True, exist_ok=True)
            path.write_text("fixture")
        scanner = set(config.source_files(self.value, self.root, "all"))
        acceptance = set(config.acceptance_files(self.value, self.root))
        self.assertTrue(scanner.isdisjoint(extra))
        self.assertEqual(acceptance, scanner | set(extra))
        self.assertEqual(
            config.plan(self.value, self.root)["acceptance_inventory_version"],
            config.ACCEPTANCE_INVENTORY_VERSION,
        )
        self.assertIn(
            "--catalog-only",
            next(t for t in self.value["tasks"] if t["project"] == "docs" and t["name"] == "sdd")[
                "argv"
            ],
        )

    def test_acceptance_inventory_rejects_symlinks_files_and_parents(self):
        for name in ("AGENTS.md", "README.md"):
            (self.root / name).write_text("fixture")
        outside = self.root / "outside"
        outside.mkdir()
        for name in (
            ".codex",
            ".codex/agents",
            ".codex/agents/nested",
            ".codex/skills",
            ".codex/skills/method",
            "docs/architecture",
        ):
            path = self.root / name
            path.parent.mkdir(parents=True, exist_ok=True)
            path.symlink_to(outside, target_is_directory=True)
            with self.subTest(name=name), self.assertRaisesRegex(ValueError, "symlink"):
                config.acceptance_files(self.value, self.root)
            path.unlink()
        for name in (
            ".codex/config.toml",
            ".codex/agents/alias.toml",
            ".codex/skills/alias.md",
            "docs/alias.md",
        ):
            path = self.root / name
            path.parent.mkdir(parents=True, exist_ok=True)
            path.symlink_to(self.root / "README.md")
            with self.subTest(name=name), self.assertRaisesRegex(ValueError, "symlink"):
                config.acceptance_files(self.value, self.root)
            path.unlink()
        value = copy.deepcopy(self.value)
        value["inputs"]["files"].append("../escape")
        with self.assertRaises(ValueError):
            config.acceptance_files(value, self.root)

    def test_inventory_excludes_generated_files_before_selecting_scanner_sources(self):
        frontend = "application/server/frontend"
        generated = [
            f"{frontend}/node_modules/dependency/index.ts",
            "application/agents/target/generated.rs",
            f"{frontend}/dist/index.ts",
            f"{frontend}/dist-ssr/index.ts",
            f"{frontend}/.cache/generated.ts",
            f"{frontend}/.eslintcache",
            f"{frontend}/.stylelintcache",
            f"{frontend}/.yarn/cache/dependency.zip",
            f"{frontend}/.yarn/unplugged/dependency/index.ts",
            f"{frontend}/.yarn/build-state.yml",
            f"{frontend}/.yarn/install-state.gz",
            f"{frontend}/.pnp.cjs",
            f"{frontend}/.pnp.loader.mjs",
            "application/.ec/local/state.json",
            f"{frontend}/src/view.ts.bak",
            f"{frontend}/src/view.ts.orig",
            f"{frontend}/src/view.ts.rej",
            f"{frontend}/tsconfig.tsbuildinfo",
            f"{frontend}/vite.config.ts.timestamp-123.mjs",
            f"{frontend}/npm-debug.log.1",
            "application/agents/default.profraw",
            "application/agents/default.profdata",
            "application/agents/source.rs.bk",
            "application/agents/source.gcda",
            "application/agents/source.gcno",
            "application/agents/program.pdb",
            "application/integration/tests/helper.pyc",
            "application/integration/tests/helper.pyo",
            "application/integration/tests/helper.pyd",
            "application/integration/tests/result.tmp",
            "application/integration/tests/result.temp",
            "application/integration/tests/.helper.py.swp",
            "application/integration/tests/.helper.py.swo",
            "application/integration/tests/helper.py~",
        ]
        for directory in (
            ".venv",
            "venv",
            "__pycache__",
            ".pytest_cache",
            ".mypy_cache",
            ".ruff_cache",
            "coverage",
            "htmlcov",
            ".vitest",
            ".nyc_output",
            "playwright-report",
            "test-results",
            "blob-report",
            ".pnpm-store",
            ".npm",
            ".vite",
            ".vite-temp",
            ".vite-inspect",
            ".idea",
            ".history",
        ):
            generated.append(f"{frontend}/{directory}/generated.ts")
        for name in (
            ".coverage",
            ".coverage.host.123",
            ".DS_Store",
            "Thumbs.db",
            "Desktop.ini",
            "yarn-debug.log",
            "yarn-error.log",
            "pnpm-debug.log",
            "lerna-debug.log",
        ):
            generated.append(f"{frontend}/{name}")
        retained = [
            f"{frontend}/src/view.ts",
            f"{frontend}/src/View.vue",
            f"{frontend}/tests/view.test.ts",
            f"{frontend}/public/logo.svg",
            f"{frontend}/package-lock.json",
            f"{frontend}/.yarn/plugins/plugin.cjs",
            f"{frontend}/.yarn/releases/yarn.cjs",
            "application/agents/crates/example/src/lib.rs",
            "application/agents/Cargo.lock",
            "application/server/backend/Cargo.lock",
            "application/integration/tests/test_example.py",
            "application/integration/tests/fixtures/example.sqlite",
            "application/integration/tests/fixtures/evidence.log",
            "application/integration/tests/fixtures/pipeline.yaml",
        ]
        for name in generated + retained:
            path = self.root / name
            path.parent.mkdir(parents=True, exist_ok=True)
            path.write_text("fixture")
        # Um ignore local não pode retirar fontes do inventário canônico.
        (self.root / ".gitignore").write_text("application/\n")
        for scope in ("all", "sast", "trivy", "inventory"):
            with self.subTest(scope=scope):
                sources = set(config.source_files(self.value, self.root, scope))
                self.assertTrue(sources.isdisjoint(generated))
                expected = (
                    retained
                    if scope != "sast"
                    else [
                        name
                        for name in retained
                        if Path(name).suffix in self.value["inputs"]["sast_extensions"]
                    ]
                )
                self.assertLessEqual(set(expected), sources)
        self.assertEqual(
            set(config.source_files(self.value, self.root, "dependencies")),
            {name for name in retained if name.endswith(("Cargo.lock", "package-lock.json"))},
        )


class PreparationTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory(prefix="ec preparation ")
        self.addCleanup(self.temp.cleanup)
        self.root = Path(self.temp.name)
        self.config = config.load_config()

    def test_cargo_updates_and_fetches_only_offline_in_declared_workspace(self):
        workspace = self.root / self.config["components"]["server"]["root"]
        workspace.mkdir(parents=True)
        (workspace / "Cargo.toml").write_text("[workspace]")
        (workspace / "Cargo.lock").write_text("fixture")
        with (
            patch.dict(os.environ, EC_RUST_TOOLCHAIN="active-fixture"),
            patch.object(prepare, "command") as command,
        ):
            prepare.prepare_cargo(self.config, self.root, "server")
        self.assertEqual(
            [c.args[0] for c in command.call_args_list],
            [
                ["rustup", "run", "active-fixture", "cargo", "--version"],
                ["rustup", "run", "active-fixture", "cargo", "update", "--offline"],
                ["rustup", "run", "active-fixture", "cargo", "fetch", "--offline"],
            ],
        )
        self.assertEqual(command.call_args.kwargs["cwd"], workspace)

    def test_postgres_restores_only_container_started_by_run(self):
        for was_running in (True, False):
            with self.subTest(was_running=was_running):
                receipt = self.root / "services.json"
                if receipt.exists():
                    receipt.unlink()
                identity = "a" * 64
                with (
                    patch.object(
                        prepare,
                        "inspect_postgres",
                        return_value={"Id": identity, "State": {"Running": was_running}},
                    ),
                    patch.object(prepare, "command") as command,
                ):
                    prepare.postgres(self.config, self.root)
                    prepare.cleanup(self.root)
                calls = [c.args[0][:2] for c in command.call_args_list]
                self.assertEqual(["docker", "start"] in calls, not was_running)
                self.assertEqual(["docker", "stop"] in calls, not was_running)
                self.assertEqual(json.loads(receipt.read_text())["cleanup"], "restored")

    def test_wrong_postgres_binding_is_rejected_before_start(self):
        # Fixture rejeitada antes de iniciar o serviço; nenhum socket é aberto.
        data = [
            {
                "Id": "a" * 64,
                "HostConfig": {
                    "PortBindings": {"5432/tcp": [{"HostIp": "0.0.0.0", "HostPort": "55439"}]}
                },
            }
        ]  # Bind inseguro simulado para comprovar a rejeição.
        with patch.object(
            prepare, "command", return_value=SimpleNamespace(stdout=json.dumps(data))
        ):
            with self.assertRaises(prepare.Unavailable):
                prepare.inspect_postgres(self.config)

    def test_llvm_requires_identical_compiler_metadata(self):
        active = self.root / "toolchains/active-fixture"
        compatible = self.root / "toolchains/other-name"
        for root in (active, compatible):
            (root / "bin").mkdir(parents=True)
            (root / "bin/rustc").touch()
        broken = self.root / "toolchains/000-broken/bin/rustc"
        broken.parent.mkdir(parents=True)
        broken.touch()
        tools = compatible / "lib/rustlib/test-host/bin"
        tools.mkdir(parents=True)
        for tool in ("llvm-cov", "llvm-profdata"):
            (tools / tool).touch(mode=0o700)
        metadata = "release: current\ncommit-hash: same\nhost: test-host\nLLVM version: same\n"

        def run(argv, **_):
            if argv[0] == str(broken):
                raise subprocess.CalledProcessError(1, argv)
            return SimpleNamespace(stdout=str(active) if "sysroot" in argv else metadata)

        with (
            patch.dict(os.environ, EC_RUST_TOOLCHAIN="active-fixture", RUSTUP_HOME=str(self.root)),
            patch.object(prepare, "command", side_effect=run),
        ):
            values = prepare.llvm_environment()
            self.assertEqual(values["LLVM_COV"], str(tools / "llvm-cov"))

        def incompatible(argv, **_):
            if argv[0] == str(compatible / "bin/rustc"):
                return SimpleNamespace(
                    stdout=metadata.replace("commit-hash: same", "commit-hash: different")
                )
            return run(argv)

        with (
            patch.dict(os.environ, EC_RUST_TOOLCHAIN="active-fixture", RUSTUP_HOME=str(self.root)),
            patch.object(prepare, "command", side_effect=incompatible),
        ):
            with self.assertRaises(prepare.Unavailable):
                prepare.llvm_environment()

    def test_cleanup_does_not_require_readable_configuration(self):
        with (
            patch.dict(os.environ, EC_RUN_DIR=str(self.root)),
            patch.object(prepare.sys, "argv", ["prepare", "cleanup"]),
            patch.object(prepare, "load_config", side_effect=ValueError("broken config")) as loader,
            patch.object(prepare, "cleanup") as cleanup,
        ):
            self.assertEqual(prepare.main(), 0)
            loader.assert_not_called()
            cleanup.assert_called_once_with(self.root)
