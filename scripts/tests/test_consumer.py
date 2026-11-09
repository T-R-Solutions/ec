"""Contrato público e rejeições; mocks não homologam scanners nem capacidades."""
import os
from pathlib import Path
import subprocess
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[2]


class ConsumerTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory(prefix='ec consumer with spaces ')
        self.directory = Path(self.temp.name)
        self.env = dict(os.environ)
        self.env.pop('TOOLBOX_BIN', None)
        self.env.pop('EC_TOOLBOX_BIN', None)
        for key in ('EC_ROOT', 'EC_TASK_DIR', 'EC_E2E_ARTIFACT_DIR'):
            self.env.pop(key, None)

    def tearDown(self):
        self.temp.cleanup()

    def invoke(self, *arguments):
        return subprocess.run(  # noqa: S603 — Wrapper versionado, argv separado.
            ['bash', str(ROOT / 'scripts/toolbox.sh'), *arguments],  # noqa: S607 — Bash do ambiente de testes.
            cwd=self.directory, env=self.env, capture_output=True, timeout=10,
        )

    def test_invalid_explicit_binary_never_falls_back(self):
        fallback = self.directory / 'toolbox'
        fallback.write_text('#!/bin/bash\nexit 0\n')
        fallback.chmod(0o700)
        self.env['PATH'] = str(self.directory) + os.pathsep + os.environ['PATH']
        self.env['TOOLBOX_BIN'] = str(self.directory / 'missing')
        self.assertEqual(self.invoke('exec').returncode, 125)
        self.env['TOOLBOX_BIN'] = ''
        self.assertEqual(self.invoke('help').returncode, 125)
        self.env['TOOLBOX_BIN'] = str(self.directory)
        self.assertEqual(self.invoke('exec').returncode, 125)
        fallback.chmod(0o600)
        self.env['TOOLBOX_BIN'] = str(fallback)
        self.assertEqual(self.invoke('exec').returncode, 125)

    def test_argv_and_raw_exits_from_unrelated_cwd(self):
        binary = self.directory / 'toolbox fixture'
        capture = self.directory / 'argv'
        binary.write_text('#!/bin/bash\nprintf "%s\\0" "$@" > "$CAPTURE"\nexit "$CODE"\n')
        binary.chmod(0o700)
        self.env.update(TOOLBOX_BIN=str(binary), CAPTURE=str(capture))
        for code in (0, 1, 2, 3, 17):
            self.env['CODE'] = str(code)
            argument = 'tool with spaces;$(touch forbidden)'
            result = self.invoke('exec', '--tool', 'ruff', '--project-root', str(self.directory), '--', '', argument)
            self.assertEqual(result.returncode, code, result.stderr)
            args = capture.read_bytes().split(b'\0')[:-1]
            self.assertEqual(args, [b'exec', b'--tool', b'ruff', b'--project-root',
                                   str(self.directory).encode(), b'--', b'', argument.encode()])
            self.assertFalse((self.directory / 'forbidden').exists())

    def test_path_discovery_and_missing_installation(self):
        binary = self.directory / 'toolbox'
        binary.write_text('#!/bin/bash\nprintf native-out\nprintf native-err >&2\nexit 17\n')
        binary.chmod(0o700)
        self.env['PATH'] = str(self.directory)
        # Bash é fornecido por caminho absoluto neste teste de PATH mínimo.
        result = subprocess.run(  # noqa: S603 — Wrapper versionado e PATH fixture.
            ['/bin/bash', str(ROOT / 'scripts/toolbox.sh'), 'exec'],
            cwd=self.directory, env=self.env, capture_output=True, timeout=10)
        self.assertEqual((result.returncode, result.stdout, result.stderr),
                         (17, b'native-out', b'native-err'))
        binary.unlink()
        result = subprocess.run(  # noqa: S603 — Wrapper versionado e PATH fixture.
            ['/bin/bash', str(ROOT / 'scripts/toolbox.sh'), 'exec'],
            cwd=self.directory, env=self.env, capture_output=True, timeout=10)
        self.assertEqual(result.returncode, 125)

    def test_scanner_gates_require_evidence_directory(self):
        for check in ('ruff', 'shellcheck', 'playwright', 'secrets', 'secrets-rules',
                      'sast', 'sast-rules', 'dependencies', 'trivy', 'codeql',
                      'grype', 'bandit', 'syft', 'yara-x'):
            result = subprocess.run(  # noqa: S603 — Gate versionado e enumeração fixa.
                ['bash', str(ROOT / 'scripts/stacks/ec/scanner-gate.sh'), check],  # noqa: S607 — Bash do ambiente de testes.
                cwd=self.directory, env=self.env, capture_output=True, timeout=10,
            )
            self.assertEqual(result.returncode, 3, (check, result.stderr))
            self.assertIn(b'blocked:', result.stderr)
        self.assertFalse((self.directory / '.local').exists())

    def test_legacy_configurations_retired_after_callers_migrate(self):
        for name in ('toolbox.json', 'integration.json', 'toolbox.images.lock.json'):
            self.assertFalse((ROOT / 'scripts/config' / name).exists())
        self.assertFalse((ROOT / 'tools/toolbox_consumer').exists())
        self.assertFalse((ROOT / 'toolbox.json').exists())
        self.assertFalse((ROOT / 'scripts/toolbox-local.sh').exists())


if __name__ == '__main__':
    unittest.main()
