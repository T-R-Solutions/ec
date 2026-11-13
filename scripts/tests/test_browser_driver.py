"""Fronteira do browser Docker; fixtures não comprovam interação no navegador."""

import json
import os
import shutil
import subprocess
import tempfile
import unittest
from concurrent.futures import Future
from pathlib import Path
from unittest.mock import patch

from application.integration.tests.e2e import browser_driver as driver


class BrowserDriverTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory(prefix="ec browser contract ")
        self.addCleanup(self.temp.cleanup)
        self.task = Path(self.temp.name)

    def test_each_browser_uses_toolbox_and_private_file_bridge(self):
        for engine in ("chromium", "firefox"):
            task = self.task / engine
            task.mkdir()
            with (
                patch.dict(os.environ, EC_TASK_DIR=str(task)),
                patch.object(driver, "Gate") as gate_type,
                patch.object(driver, "ThreadPoolExecutor") as pool,
                patch.object(driver.signal, "signal"),
            ):
                future = Future()
                pool.return_value.submit.return_value = future
                gate = gate_type.return_value

                def start(path, _timeout, task_dir=task, browser_engine=engine):
                    self.assertEqual(path, task_dir / "browser-bridge/ready.json")
                    return {"browser": browser_engine, "version": "observed-fixture"}

                with patch.object(driver.Browser, "wait_file", side_effect=start):
                    browser = driver.Browser(engine)
                call = pool.return_value.submit.call_args
                self.assertEqual(call.args[0], gate.run)
                self.assertEqual(call.args[1], "playwright")
                self.assertIn("--browser", call.kwargs["options"])
                self.assertEqual(call.kwargs["options"][1], engine)
                self.assertIn(str(task / "browser-bridge") + ":/bridge:rw", call.kwargs["options"])
                self.assertEqual(call.kwargs["network"], "host")
                gate.prepare.assert_called_once()
                gate.copy_build.assert_called_once()
                future.set_exception(driver.Blocked("imagem fixture ausente"))
                with self.assertRaises(driver.Blocked):
                    browser.close(failed=True)
                pool.return_value.shutdown.assert_called_once()

    def test_reply_is_consumed_and_errors_are_not_success(self):
        browser = object.__new__(driver.Browser)
        browser.bridge = self.task
        browser.sequence = 0
        browser.settings = {"command_timeout": 1}

        def reply(path, _timeout):
            path.write_text(json.dumps({"value": None, "error": {"message": "fixture failure"}}))
            return json.loads(path.read_text())

        with patch.object(browser, "wait_file", side_effect=reply):
            with self.assertRaises(driver.Rejected):
                browser.input("input", "fixture-" + self.task.name)
        self.assertFalse((self.task / "response-1.json").exists())
        request = json.loads((self.task / "request-1.json").read_text())
        self.assertEqual(request["name"], "input")
        self.assertEqual(request["args"]["selector"], "input")

    def test_unknown_browser_cannot_select_a_host_program(self):
        with self.assertRaises(ValueError), patch.object(driver, "Gate") as gate:
            driver.Browser("/usr/bin/firefox")
        gate.assert_not_called()


class BrowserProxyTests(unittest.TestCase):
    """Executa o proxy com HTTP simulado; não abre sockets nem inicia browsers."""

    PROGRAM = """
import http from 'node:http';
import { syncBuiltinESMExports } from 'node:module';
import { pathToFileURL } from 'node:url';
const observed = {};
http.request = options => {
  observed.upstream = options;
  return { on() {} };
};
http.createServer = handler => ({ listen(port, host) {
  observed.listen = { port, host };
  handler({ url: '/api/session', method: 'GET', headers: {}, pipe() {} }, {});
} });
syncBuiltinESMExports();
await import(pathToFileURL(process.argv[1]));
process.stdout.write(JSON.stringify(observed));
"""

    def test_proxy_limits_destination_and_rejects_invalid_configuration_before_listening(self):
        node = shutil.which("node")
        self.assertIsNotNone(node, "Node local necessário para o contrato do proxy")
        with tempfile.TemporaryDirectory(prefix="ec proxy contract ") as temporary:
            root = Path(temporary)
            relative = Path("application/server/frontend/tests/browser-journey/server.mjs")
            server = root / relative
            server.parent.mkdir(parents=True)
            server.write_bytes((driver.ROOT / relative).read_bytes())
            config = root / "scripts/config/automation.json"
            config.parent.mkdir(parents=True)
            for host, port, frontend, accepted in (
                ("127.0.0.1", 58139, 5173, True),
                ("example.invalid", 58139, 5173, False),
                ("127.0.0.1", "58139", 5173, False),
                ("127.0.0.1", 0, 5173, False),
                ("127.0.0.1", 65536, 5173, False),
                ("127.0.0.1", 58139, False, False),
            ):
                with self.subTest(host=host, port=port, frontend=frontend):
                    config.write_text(
                        json.dumps({
                            "services": {
                                "http": {"host": host, "port": port},
                                "frontend": {"port": frontend},
                            }
                        })
                    )
                    result = subprocess.run(  # Node resolvido; programa literal e caminho separado, HTTP simulado.
                        [node, "--input-type=module", "-e", self.PROGRAM, str(server)],
                        capture_output=True,
                        text=True,
                        timeout=10,
                        check=False,
                    )
                    if accepted:
                        self.assertEqual(result.returncode, 0, result.stderr)
                        observed = json.loads(result.stdout)
                        self.assertEqual(
                            observed["listen"], {"port": frontend, "host": "127.0.0.1"}
                        )
                        self.assertEqual(
                            observed["upstream"],
                            {
                                "hostname": "127.0.0.1",
                                "port": port,
                                "path": "/api/session",
                                "method": "GET",
                                "headers": {},
                            },
                        )
                    else:
                        self.assertNotEqual(result.returncode, 0)
                        self.assertEqual(result.stdout, "")
