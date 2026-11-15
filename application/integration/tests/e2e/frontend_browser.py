"""Login real via Playwright, Server e PostgreSQL descartável.

A reconstrução do frontend retirou editor, execução e demonstrações da UI.
Esses contratos de domínio continuam nos harnesses HTTP de integração; esta
jornada observa somente os controles de sessão realmente expostos pela tela.
Sem tokens em traces, URLs, resultados ou screenshots.
"""

import base64
import json
import os
import subprocess
import sys
import tempfile
from contextlib import ExitStack
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parents[1] / "integration"))
import publication_http as h
from browser_driver import Blocked, Browser
from settings import CONFIG

ART = (
    Path(os.environ["EC_E2E_ARTIFACT_DIR"])
    if "EC_E2E_ARTIFACT_DIR" in os.environ
    else Path(tempfile.mkdtemp(prefix="ec-login-evidence-"))
)
FRONTEND_URL = "http://" + h.SERVICES["http"]["host"] + ":" + str(h.SERVICES["frontend"]["port"])


def main(engine):
    ART.mkdir(exist_ok=True, parents=True)
    subprocess.run(
        ["docker", "exec", h.CONTAINER, "createdb", "-U", h.POSTGRES["user"], h.DATABASE],
        check=True,
    )  # Docker confiável; container dedicado e banco UUID, argv sem shell.
    results = []
    viewports = {}

    def passed(message):
        results.append(message)
        print("PASS " + message, flush=True)

    try:
        with (
            tempfile.TemporaryDirectory(prefix="ec-login-", dir=h.temporary_parent()) as directory,
            ExitStack() as cleanup,
        ):
            tmp = Path(directory)
            h.cli("migrate")
            tokens = {}
            for actor, project, role in [
                ("admin", "ec", "admin"),
                ("viewer", "ec", "viewer"),
                ("foreign", "other", "admin"),
                ("expired", "ec", "viewer"),
                ("revoked", "ec", "viewer"),
            ]:
                path = tmp / actor
                h.cli("provision", actor, project, role, str(path), "local")
                tokens[actor] = path.read_text()
            h.sql("UPDATE sessions SET expires=clock_timestamp()-interval '1 second' WHERE identity='expired'")
            h.cli("revoke", "revoked")
            server = h.start()
            cleanup.callback(h.stop, server)

            # Oráculos HTTP independentes da apresentação e sem efeitos de publicação.
            before = h.snapshot()
            for credential in (None, "invalid-session", tokens["expired"], tokens["revoked"]):
                assert h.call("GET", "/session", credential)[0] == 401
            session_status, session = h.call("GET", "/session", tokens["admin"])
            assert session_status == 200 and session["identity"] == "admin" and session["expires"]
            assert h.call("GET", "/projects", tokens["viewer"])[1]["projects"][0]["role"] == "viewer"
            assert h.call("GET", "/projects/ec/agents", tokens["foreign"])[0] == 404
            assert h.snapshot() == before
            passed("HTTP real rejeita sessão ausente/inválida/expirada/revogada e mantém escopo de projeto sem publicação")

            browser = cleanup.enter_context(Browser(engine))
            browser.viewport(1440, 1000)
            browser.navigate(FRONTEND_URL)
            browser.wait("return !!document.querySelector('#session-token')")

            def screenshot(name):
                # O campo é esvaziado antes do request; nunca capturar credencial digitada.
                assert browser.js("return !document.querySelector('#session-token')?.value")
                viewports[name] = browser.js(
                    "return {width:innerWidth,height:innerHeight,dpr:devicePixelRatio}"
                )
                (ART / name).write_bytes(base64.b64decode(browser.screenshot()))

            def private_state_absent():
                return browser.js(
                    """return localStorage.length===0 && sessionStorage.length===0 &&
                    !location.search && !location.hash &&
                    !document.body.innerText.includes(arguments[0]) &&
                    !document.documentElement.outerHTML.includes(arguments[0])""",
                    tokens["admin"],
                )

            def login(actor):
                browser.input("#session-token", tokens[actor])
                browser.key("Enter")
                browser.wait("return document.querySelector('h1')?.textContent==='Sessão verificada'")
                assert browser.js(
                    "return Array.from(document.querySelectorAll('dd')).some(e=>e.textContent===arguments[0])",
                    actor,
                )
                browser.wait("return document.activeElement.id==='session-title'")
                assert not browser.js("return !!document.querySelector('#session-token')")

            assert browser.js("return document.activeElement.id==='session-token'")
            assert not browser.js("return !!document.querySelector('nav, aside, .shell')")
            screenshot("login-desktop.png")
            browser.click("Verificar sessão")
            browser.wait("return document.body.innerText.includes('Informe o token de sessão')")
            assert browser.js("return document.activeElement.id==='session-token'")
            passed("Formulário vazio mantém foco e rejeita envio antes de autenticar")

            for actor in (None, "expired", "revoked"):
                browser.input("#session-token", tokens[actor] if actor else "invalid-session")
                browser.key("Enter")
                browser.wait("return document.body.innerText.includes('Sessão recusada ou expirada')")
                assert browser.js("return document.activeElement.id==='session-token'")
                assert browser.js("return document.querySelector('#session-token').value===''")
                assert not browser.js("return !!document.querySelector('.session-confirmation')")
            screenshot("login-refused.png")
            assert h.snapshot() == before
            passed("Navegador recebe 401 real para token inválido/expirado/revogado, limpa campo e permite tentar novamente")

            login("admin")
            assert session["expires"] in browser.text()
            assert private_state_absent()
            screenshot("login-confirmed.png")
            passed("Token provisionado confirma identidade e validade reais; credencial ausente de DOM, URL e storages")

            # Recarregar perde somente a sessão em memória; não revoga no servidor.
            browser.navigate(FRONTEND_URL)
            browser.wait("return !!document.querySelector('#session-token')")
            assert not browser.js("return !!document.querySelector('.session-confirmation')")
            assert private_state_absent()
            assert h.call("GET", "/session", tokens["admin"])[0] == 200
            passed("Recarregar remove sessão da aba sem inventar revogação remota")

            login("admin")
            browser.click("Revogar e sair")
            browser.wait("return !!document.querySelector('#session-token')")
            assert h.call("GET", "/session", tokens["admin"])[0] == 401
            assert browser.js("return document.activeElement.id==='session-token'")
            assert private_state_absent()
            screenshot("login-ended.png")
            passed("Sair revoga sessão no Server e retorna formulário vazio com foco")

            # Outra identidade não herda a confirmação da sessão anterior.
            login("viewer")
            assert "admin" not in browser.text()
            browser.click("Revogar e sair")
            browser.wait("return !!document.querySelector('#session-token')")
            assert h.call("GET", "/session", tokens["viewer"])[0] == 401
            passed("Troca de identidade usa nova sessão e revoga somente seu token")

            # O foco da janela revalida a sessão no Server, sem simular seu relógio.
            fresh = tmp / "viewer-fresh"
            h.cli("provision", "viewer", "ec", "viewer", str(fresh), "local")
            tokens["viewer"] = fresh.read_text()
            login("viewer")
            h.sql("UPDATE sessions SET expires=clock_timestamp()-interval '1 second' WHERE identity='viewer'")
            assert h.call("GET", "/session", tokens["viewer"])[0] == 401
            browser.js("window.dispatchEvent(new Event('focus'))")
            browser.wait("return !!document.querySelector('#session-token')")
            assert not browser.js("return !!document.querySelector('.session-confirmation, .session-details')")
            assert browser.js("return document.querySelector('#session-token').value===''")
            browser.wait("return document.activeElement.id==='session-token'")
            screenshot("login-expired.png")
            passed("Expiração real no PostgreSQL é detectada ao retornar à janela e remove identidade e confirmação")

            fresh = tmp / "viewer-revalidation"
            h.cli("provision", "viewer", "ec", "viewer", str(fresh), "local")
            tokens["viewer"] = fresh.read_text()
            login("viewer")
            h.cli("revoke", "viewer")
            assert h.call("GET", "/session", tokens["viewer"])[0] == 401
            browser.js("window.dispatchEvent(new Event('focus'))")
            browser.wait("return !!document.querySelector('#session-token')")
            assert not browser.js("return !!document.querySelector('.session-confirmation, .session-details')")
            assert browser.js("return document.querySelector('#session-token').value===''")
            browser.wait("return document.activeElement.id==='session-token'")
            screenshot("login-revoked.png")
            passed("Revogação administrativa real é detectada ao retornar à janela e remove identidade e confirmação")

            for width, height, name in [
                (768, 1024, "login-tablet.png"),
                (390, 844, "login-mobile.png"),
                (320, 900, "login-reflow.png"),
            ]:
                browser.viewport(width, height)
                assert browser.js("return document.documentElement.scrollWidth<=innerWidth")
                assert browser.js("return document.querySelector('#session-token').getBoundingClientRect().width>0")
                screenshot(name)
            browser.js("document.querySelector('.skip').focus()")
            browser.key("Enter")
            assert browser.js("return document.activeElement.id==='access-content'")
            browser.key("Tab")
            assert browser.js("return document.activeElement.id==='session-token'")
            assert h.snapshot() == before
            passed("Desktop/tablet/mobile e reflow 320 CSS px sem overflow; skip link e Tab alcançam o campo")

            browser.close()
            (ART / "browser-results.json").write_text(
                json.dumps(
                    {
                        "browser": browser.observed,
                        "results": results,
                        "viewports": viewports,
                        "oracles": {
                            "sessionEndpoint": "/api/v1/session",
                            "logoutStatus": 401,
                            "publishedStateUnchanged": h.snapshot() == before,
                        },
                        "limitations": [
                            "Campos usam eventos DOM; envio por Enter, cliques e teclado via Playwright. Sem leitor de tela.",
                            "Reflow observado em 320 CSS px; não demonstra zoom nativo 400% nem certificação WCAG completa.",
                            "Editor e operação retirados da UI por reconstrução; contratos de domínio permanecem nos harnesses HTTP.",
                            "Revalidação exercitada pelo evento de foco da janela; intervalo periódico não é acelerado por este harness.",
                        ],
                    },
                    ensure_ascii=False,
                    indent=2,
                ) + "\n"
            )
    finally:
        subprocess.run(
            ["docker", "exec", h.CONTAINER, "dropdb", "-U", h.POSTGRES["user"], "--force", h.DATABASE],
            check=True,
        )  # Docker confiável; banco UUID dedicado, argv sem shell.


if __name__ == "__main__":
    if len(sys.argv) != 2 or sys.argv[1] not in CONFIG["browser"]["journey"]["engines"]:
        raise SystemExit("uso: frontend_browser.py chromium|firefox")
    try:
        main(sys.argv[1])
    except Blocked as error:
        print("blocked: " + str(error), file=sys.stderr)
        raise SystemExit(3) from error
