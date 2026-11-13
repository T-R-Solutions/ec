"""Descoberta do ambiente e isolamento do shell chamador, sem ferramentas reais."""

import os
import subprocess
import tempfile
import unittest
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
PREPARE = """set -euo pipefail
ROOT=$1
source "$2/scripts/stacks/ec/environment.sh"
prepare_run_environment
env -i "${RUN_ENV[@]}" /usr/bin/env -0
"""


class EnvironmentTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory(prefix="ec environment with spaces ")
        self.directory = Path(self.temp.name)
        self.project = self.directory / "projects/ec"
        self.home = self.directory / "home"
        self.project.mkdir(parents=True)
        self.home.mkdir()
        self.env = {"PATH": "/usr/bin:/bin", "HOME": str(self.home)}

    def tearDown(self):
        self.temp.cleanup()

    def binary(self, path):
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_text("#!/bin/sh\nexit 99\n")
        path.chmod(0o700)
        return path

    def prepare(self):
        result = subprocess.run(  # Programa literal, fontes versionadas e argumentos separados.
            ["/bin/bash", "-c", PREPARE, "fixture", str(self.project), str(ROOT)],
            env=self.env,
            cwd=self.directory,
            capture_output=True,
            timeout=10,
            check=False,
        )
        self.assertEqual(result.returncode, 0, result.stderr)
        return dict(item.decode().split("=", 1) for item in result.stdout.split(b"\0") if item)

    def test_discovers_sibling_installation_without_executing_it(self):
        installation = self.project.parent / "dev_audit/shared-toolbox"
        binary = self.binary(installation / "bin/toolbox")
        (installation / "toolbox").mkdir()
        values = self.prepare()
        self.assertEqual(values["TOOLBOX_BIN"], str(binary))
        self.assertEqual(values["TOOLBOX_STATE"], str(installation / ".local/state"))
        self.assertFalse((installation / ".local").exists())

    def test_path_and_explicit_selection_take_precedence(self):
        binary = self.binary(self.directory / "bin/toolbox")
        self.env["PATH"] = str(binary.parent) + os.pathsep + self.env["PATH"]
        self.binary(self.project.parent / "shared-toolbox/bin/toolbox")
        self.assertEqual(self.prepare()["TOOLBOX_BIN"], str(binary))
        for value in ("", "/missing toolbox"):
            self.env["TOOLBOX_BIN"] = value
            self.assertEqual(self.prepare()["TOOLBOX_BIN"], value)

    def test_toolbox_root_selects_installation_without_fallback(self):
        self.env["TOOLBOX_ROOT"] = str(self.directory / "explicit")
        self.assertEqual(
            self.prepare()["TOOLBOX_BIN"], str(self.directory / "explicit/bin/toolbox")
        )
        self.env["TOOLBOX_ROOT"] = ""
        self.assertEqual(self.prepare()["TOOLBOX_BIN"], "")

    def test_rust_defaults_and_existing_advisory_database(self):
        cargo = self.home / ".cargo"
        database = cargo / "advisory-dbs/prepared"
        (database / ".git").mkdir(parents=True)
        values = self.prepare()
        self.assertEqual(values["EC_RUSTSEC_DB"], str(database))
        self.assertEqual(values["EC_CODEQL_CARGO_HOME"], str(cargo))
        self.assertNotIn("EC_GEIGER_BUILD_CARGO_HOME", values)
        self.assertEqual(values["EC_CODEQL_CARGO_BIN"], str(cargo / "bin"))
        self.assertEqual(values["EC_CODEQL_RUSTUP_HOME"], str(self.home / ".rustup"))
        (cargo / "advisory-dbs/other/.git").mkdir(parents=True)
        self.assertEqual(self.prepare()["EC_RUSTSEC_DB"], str(cargo / "advisory-db"))

    def test_overrides_and_secrets_boundary(self):
        self.env.update(
            CARGO_HOME=str(self.directory / "cargo"),
            RUSTUP_HOME=str(self.directory / "rustup"),
            EC_RUSTSEC_DB="/prepared database",
            EC_CODEQL_CARGO_BIN="",
            DOCKER_CONTEXT="selected context",
            EC_TEST_SECRET=self.directory.name,
        )
        values = self.prepare()
        self.assertEqual(values["EC_RUSTSEC_DB"], "/prepared database")
        self.assertEqual(values["EC_CODEQL_CARGO_BIN"], "")
        self.assertNotIn("EC_GEIGER_BUILD_CARGO_HOME", values)
        self.assertEqual(values["EC_CODEQL_RUSTUP_HOME"], self.env["RUSTUP_HOME"])
        self.assertEqual(values["DOCKER_CONTEXT"], "selected context")
        self.assertNotIn("EC_TEST_SECRET", values)
        self.assertNotIn("DOCKER_HOST", values)

    def test_automatic_selinux_and_refresh_are_local_and_overridable(self):
        values = self.prepare()
        self.assertEqual(values["TOOLBOX_SELINUX_LABEL"], "shared")
        self.assertEqual(values["EC_TRIVY_REFRESH"], "1")
        self.env.update(
            TOOLBOX_SELINUX_LABEL="", EC_TRIVY_REFRESH="0", EC_RUST_TOOLCHAIN="current-fixture"
        )
        values = self.prepare()
        self.assertEqual(values["TOOLBOX_SELINUX_LABEL"], "")
        self.assertEqual(values["EC_TRIVY_REFRESH"], "0")
        self.assertEqual(values["RUSTUP_TOOLCHAIN"], "current-fixture")
        self.assertNotIn("RUSTUP_TOOLCHAIN", self.env)

    def test_discovers_relocated_rustsec_without_fetch(self):
        base = self.project.parent / "ox/.cache/ox/security/generations/prepared/rustsec"
        (base / ".git").mkdir(parents=True)
        git = self.binary(self.directory / "bin/git")
        git.write_text('#!/bin/sh\n[ "$3" = log ] || exit 99\necho 100\n')
        self.env["PATH"] = str(git.parent) + os.pathsep + self.env["PATH"]
        self.assertEqual(self.prepare()["EC_RUSTSEC_DB"], str(base))
        self.env["EC_RUSTSEC_DB"] = ""
        self.assertEqual(self.prepare()["EC_RUSTSEC_DB"], "")

    def test_active_toolchain_is_discovered_without_installation(self):
        rustup = self.binary(self.home / ".cargo/bin/rustup")
        rustup.write_text(
            '#!/bin/sh\n[ "$RUSTUP_AUTO_INSTALL" = 0 ] || exit 99\n'
            '[ "$*" = "show active-toolchain" ] || exit 99\n'
            'echo "active-fixture (default)"\n'
        )
        values = self.prepare()
        self.assertEqual(values["EC_RUST_TOOLCHAIN"], "active-fixture")
        self.assertEqual(values["RUSTUP_TOOLCHAIN"], "active-fixture")

    def test_child_exit_does_not_set_or_erase_parent_variables(self):
        program = """set -eu
ROOT=$1
export EC_RUSTSEC_DB=original
for code in 0 2 143; do
    (source "$2/scripts/stacks/ec/environment.sh"
     prepare_run_environment
     env -i "${RUN_ENV[@]}" /bin/bash -c 'exit "$1"' fixture "$code") || :
    test "$EC_RUSTSEC_DB" = original
    test "${TOOLBOX_BIN+x}" != x
    test "${RUN_ENV+x}" != x
    test "${TOOLBOX_SELINUX_LABEL+x}" != x
    test "${EC_TRIVY_REFRESH+x}" != x
done
"""
        result = subprocess.run(  # Shell fixture literal, sem interpolação de dados em código.
            ["/bin/bash", "-c", program, "fixture", str(self.project), str(ROOT)],
            env=self.env,
            capture_output=True,
            timeout=10,
            check=False,
        )
        self.assertEqual(result.returncode, 0, result.stderr)

    def test_source_is_rejected_before_changing_shell_state(self):
        program = """before=$(set +o)
mask=$(umask)
source "$1/scripts/automate.sh" run && exit 1
test "$?" = 2 || exit 1
test "$(set +o)" = "$before" || exit 1
test "$(umask)" = "$mask"
"""
        result = subprocess.run(  # Source da entrada versionada para testar rejeição sem executar tarefas.
            ["/bin/bash", "-c", program, "fixture", str(ROOT)],
            env=self.env,
            capture_output=True,
            timeout=10,
            check=False,
        )
        self.assertEqual(result.returncode, 0, result.stderr)
