"""Browser Playwright da Toolbox; nenhum executável de navegador no host."""

import os
import signal
import sys
import time
from concurrent.futures import ThreadPoolExecutor
from concurrent.futures import TimeoutError as FutureTimeout
from pathlib import Path

ROOT = Path(__file__).resolve().parents[4]
sys.path.insert(0, str(ROOT))
from tools.quality.automation_config import load_config
from tools.quality.native_gates import (
    Blocked,
    Gate,
    Rejected,
    identity,
    inventory,
    read_json,
    require,
    write_json,
)


class Browser:
    def __init__(self, engine):
        self.config = load_config()
        self.settings = self.config["browser"]["journey"]
        if engine not in self.settings["engines"]:
            raise ValueError("navegador não declarado")
        self.engine = engine
        self.task = Path(os.environ["EC_TASK_DIR"])
        self.bridge = self.task / "browser-bridge"
        self.bridge.mkdir(mode=0o700)
        self.downloads = self.bridge / "downloads"
        self.downloads.mkdir()
        self.sequence = 0
        self.closed = False
        self.gate = Gate("journey", ROOT, self.task)
        self.gate.prepare()
        self.gate.copy_build()
        self.pool = ThreadPoolExecutor(max_workers=1)
        self.handlers = {}
        for signum in (signal.SIGTERM, signal.SIGINT, signal.SIGHUP):
            self.handlers[signum] = signal.signal(signum, self.interrupt)
        options = [
            "--browser",
            engine,
            "--env",
            "EC_BROWSER=" + engine,
            "--mount",
            str(self.bridge) + ":/bridge:rw",
        ]
        self.future = self.pool.submit(
            self.gate.run,
            "playwright",
            ["test", "--config=/project/" + self.settings["config"]],
            options=options,
            network=self.settings["network"],
            timeout=self.config["runtime"]["task_timeout"],
        )
        try:
            self.observed = self.wait_file(
                self.bridge / "ready.json", self.settings["startup_timeout"]
            )
            require(
                self.observed.get("browser") == engine and bool(self.observed.get("version")),
                "navegador iniciado diverge da seleção",
            )
        except BaseException:
            self.close(failed=True)
            raise

    def interrupt(self, signum, frame):
        self.gate.receive(signum, frame)
        raise InterruptedError("jornada interrompida")

    def wait_file(self, path, timeout):
        deadline = time.monotonic() + timeout
        while not path.exists():
            if self.future.done():
                step, code = self.future.result()
                report = step / "results/native.json"
                if report.is_file() and "Executable doesn't exist" in report.read_text():
                    raise Blocked(
                        "navegador "
                        + self.engine
                        + " ausente na imagem Docker; preparar a variante na Toolbox"
                    )
                raise Rejected(
                    "Playwright encerrou antes de completar a jornada; saída nativa=" + str(code)
                )
            if time.monotonic() >= deadline:
                raise Rejected("prazo de resposta do navegador esgotado")
            time.sleep(0.025)
        return read_json(path)

    def request(self, name, args=None):
        self.sequence += 1
        request = self.bridge / f"request-{self.sequence}.json"
        response = self.bridge / f"response-{self.sequence}.json"
        write_json(request, {"name": name, "args": args or {}})
        result = self.wait_file(response, self.settings["command_timeout"])
        response.unlink()
        if result.get("error"):
            raise Rejected("operação Playwright falhou: " + name)
        return result["value"]

    def js(self, script, *args):
        return self.request("evaluate", {"script": script, "args": list(args)})

    def wait(self, script, timeout=20):
        deadline = time.monotonic() + timeout
        while time.monotonic() < deadline:
            value = self.js(script)
            if value:
                return value
            time.sleep(0.05)
        raise AssertionError("Condição no browser não observada: " + script)

    def click(self, text):
        return self.request("click", {"text": text})

    def input(self, selector, value):
        return self.request("input", {"selector": selector, "value": value})

    def text(self):
        return self.request("text")

    def viewport(self, width, height):
        return self.request("viewport", {"width": width, "height": height})

    def navigate(self, url):
        return self.request("navigate", {"url": url})

    def screenshot(self):
        return self.request("screenshot")

    def key(self, key):
        return self.request("key", {"key": key})

    def close(self, failed=False):
        if self.closed:
            return
        self.closed = True
        try:
            if not self.future.done() and (self.bridge / "ready.json").exists():
                self.request("close", {"failed": failed})
            elif not self.future.done():
                self.gate.receive(signal.SIGTERM, None)
            step, code = self.future.result(timeout=self.config["runtime"]["cleanup_timeout"])
            if not failed:
                report = read_json(step / "results/native.json")
                stats = report["stats"]
                require(
                    code == 0
                    and report.get("errors") == []
                    and stats["expected"] == 1
                    and all(stats[k] == 0 for k in ("unexpected", "skipped", "flaky")),
                    "jornada Playwright incompleta ou falhou",
                )
                require(inventory(ROOT) == self.gate.before, "fontes alteradas durante a jornada")
                for path, observed in self.gate.build_before.items():
                    require(
                        identity(self.gate.build_root / path) == observed,
                        "build mudou durante a jornada",
                    )
        except FutureTimeout:
            self.gate.receive(signal.SIGTERM, None)
            self.future.result(timeout=self.config["runtime"]["cleanup_timeout"])
            raise Rejected("Playwright não encerrou no prazo; jornada reprovada") from None
        except BaseException:
            if not self.future.done():
                self.gate.receive(signal.SIGTERM, None)
                try:
                    self.future.result(timeout=self.config["runtime"]["cleanup_timeout"])
                except (Blocked, Rejected) as error:
                    write_json(
                        self.task / "browser-cleanup.json",
                        {"state": "error", "type": type(error).__name__},
                    )
            raise
        finally:
            for signum, handler in self.handlers.items():
                signal.signal(signum, handler)
            self.pool.shutdown(wait=False, cancel_futures=True)

    def __enter__(self):
        return self

    def __exit__(self, kind, _value, _traceback):
        self.close(failed=kind is not None)
