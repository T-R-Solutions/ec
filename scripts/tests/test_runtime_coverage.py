"""Contratos do coletor LLVM; fixtures não comprovam execução dos harnesses."""
import importlib.util
import os
from pathlib import Path
import shlex
import subprocess
import tempfile
from types import SimpleNamespace
import unittest
from unittest.mock import patch

ROOT = Path(__file__).resolve().parents[2]
SPEC = importlib.util.spec_from_file_location('runtime_coverage', ROOT / 'tools/quality/collect_runtime_coverage.py')
MODULE = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(MODULE)


class CoverageEnvironmentTests(unittest.TestCase):
    def test_assignments_preserve_paths_and_never_execute_shell_text(self):
        output = "RUSTFLAGS='-C instrument-coverage'\nLLVM_PROFILE_FILE='/fixture/space path/p-%p.profraw'\nCARGO_TARGET_DIR='/fixture/$(not-a-command)'\n"
        result = MODULE.coverage_environment(output, {'PATH': '/trusted'})
        self.assertEqual(result['LLVM_PROFILE_FILE'], '/fixture/space path/p-%p.profraw')
        self.assertEqual(result['CARGO_TARGET_DIR'], '/fixture/$(not-a-command)')
        self.assertEqual(result['PATH'], '/trusted')

    def test_unknown_keys_multiple_commands_and_missing_instrumentation_fail(self):
        for output in [
            'PATH=/untrusted',
            'RUSTFLAGS=one two',
            'RUSTFLAGS=flags; touch /tmp/not-allowed',
            "RUSTFLAGS='-C instrument-coverage'",
            "LLVM_PROFILE_FILE='/tmp/profile'\nRUSTFLAGS='-C opt-level=0'",
        ]:
            with self.subTest(output=output), self.assertRaises(ValueError):
                MODULE.coverage_environment(output, {})

    def test_encoded_flags_remain_data(self):
        output = "CARGO_ENCODED_RUSTFLAGS='-C\x1finstrument-coverage'\nLLVM_PROFILE_FILE=/tmp/p-%p.profraw\n"
        result = MODULE.coverage_environment(output, {})
        self.assertEqual(result['CARGO_ENCODED_RUSTFLAGS'], '-C\x1finstrument-coverage')

    def test_wrapper_instrumentation_from_installed_llvm_cov_is_preserved(self):
        output = "\n".join([
            'LLVM_PROFILE_FILE=/fixture/p-%p.profraw',
            '__CARGO_LLVM_COV_RUSTC_WRAPPER=1',
            "__CARGO_LLVM_COV_RUSTC_WRAPPER_RUSTFLAGS='-C\x1finstrument-coverage\x1f--cfg=coverage'",
            '__CARGO_LLVM_COV_RUSTC_WRAPPER_CRATE_NAMES=ec_server_app',
            'RUSTC_WRAPPER=/trusted/cargo-llvm-cov',
        ])
        result = MODULE.coverage_environment(output, {})
        self.assertEqual(result['RUSTC_WRAPPER'], '/trusted/cargo-llvm-cov')
        self.assertEqual(result['__CARGO_LLVM_COV_RUSTC_WRAPPER_CRATE_NAMES'], 'ec_server_app')
        self.assertIn('instrument-coverage', result['__CARGO_LLVM_COV_RUSTC_WRAPPER_RUSTFLAGS'])

    def collect(self, directory, failed_harness=None):
        target = Path(directory) / 'target'
        environment = dict(COVERAGE_DIR=directory, CARGO_TARGET_DIR=str(target))
        assignments = '\n'.join(f'{key}={shlex.quote(value)}' for key, value in {
            'RUSTFLAGS': '-C instrument-coverage',
            'LLVM_PROFILE_FILE': str(target / 'profile-%p.profraw'),
            'CARGO_TARGET_DIR': str(target),
        }.items())
        calls = []

        def run(argv, **options):
            calls.append((argv, options))
            if 'show-env' in argv:
                return SimpleNamespace(stdout=assignments)
            if 'build' in argv:
                component = 'server' if str(options['cwd']).endswith('backend') else 'agent'
                binary = Path(options['env']['CARGO_TARGET_DIR']) / 'debug' / f'ec-{component}-app'
                binary.parent.mkdir(parents=True, exist_ok=True)
                binary.write_bytes(b'fixture, never executed')
            if failed_harness and argv[-1].endswith(failed_harness + '.py'):
                raise subprocess.CalledProcessError(7, argv)
            return SimpleNamespace(returncode=0)

        with patch.dict(os.environ, environment, clear=True), \
                patch('sys.argv', ['collector', '--channel', '1.99.1', '--component', 'server']), \
                patch.object(MODULE.subprocess, 'run', side_effect=run):
            MODULE.main()
        return calls

    def test_current_instrumented_binary_is_used_by_all_real_server_harnesses(self):
        with tempfile.TemporaryDirectory(prefix='ec coverage with spaces ') as directory:
            calls = self.collect(directory)
            builds = [(argv, options) for argv, options in calls if 'build' in argv]
            self.assertEqual(len(builds), 2)
            for argv, _ in builds:
                for flag in ('--workspace', '--locked', '--offline', '--all-features'):
                    self.assertIn(flag, argv)
            self.assertIn('instrument-coverage', builds[0][1]['env']['RUSTFLAGS'])
            self.assertNotIn('LLVM_PROFILE_FILE', builds[1][1]['env'])
            harnesses = [(argv, options) for argv, options in calls if argv[0] == 'bash']
            self.assertEqual([Path(argv[-1]).stem for argv, _ in harnesses],
                             ['publication_http', 'orchestration_http', 'agent_http'])
            for argv, options in harnesses:
                self.assertEqual(Path(argv[1]).name, 'integration.sh')
                self.assertEqual(options['env']['EC_COVERAGE_SERVER_BIN'],
                                 str(Path(directory) / 'target/debug/ec-server-app'))
                self.assertEqual(options['env']['EC_TEST_GRACEFUL_STOP'], '1')
                self.assertTrue(options['check'])

    def test_harness_failure_is_not_converted_to_success(self):
        with tempfile.TemporaryDirectory(prefix='ec coverage failure ') as directory:
            with self.assertRaises(subprocess.CalledProcessError) as result:
                self.collect(directory, failed_harness='orchestration_http')
            self.assertEqual(result.exception.returncode, 7)


if __name__ == '__main__':
    unittest.main()
