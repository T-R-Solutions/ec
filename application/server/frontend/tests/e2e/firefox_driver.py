"""Cliente Marionette mínimo para Firefox do sistema; nenhum navegador baixado.

Protocolo oficial: https://firefox-source-docs.mozilla.org/testing/marionette/Protocol.html
"""
import json
import socket
import time


class Firefox:
    def __init__(self, port=58282):
        deadline = time.monotonic() + 20
        while True:
            try:
                self.socket = socket.create_connection(('127.0.0.1', port), timeout=15)
                break
            except OSError:
                if time.monotonic() > deadline:
                    raise
                time.sleep(.1)
        self.read()
        self.sequence = 0
        self.command('WebDriver:NewSession', {'capabilities': {'alwaysMatch': {'acceptInsecureCerts': False}}})

    def read(self):
        length = b''
        while not length.endswith(b':'):
            length += self.socket.recv(1)
        remaining = int(length[:-1])
        data = b''
        while len(data) < remaining:
            data += self.socket.recv(remaining - len(data))
        return json.loads(data)

    def command(self, name, args=None):
        self.sequence += 1
        data = json.dumps([0, self.sequence, name, args or {}]).encode()
        self.socket.sendall(str(len(data)).encode() + b':' + data)
        response = self.read()
        if response[2]:
            raise AssertionError(response[2])
        result = response[3]
        return result.get('value') if isinstance(result, dict) and set(result) == {'value'} else result

    def js(self, script, *args):
        return self.command('WebDriver:ExecuteScript', {'script': script, 'args': list(args), 'newSandbox': True})

    def wait(self, script, timeout=20):
        deadline = time.monotonic() + timeout
        while time.monotonic() < deadline:
            value = self.js(script)
            if value:
                return value
            time.sleep(.05)
        raise AssertionError('Condição no browser não observada: ' + script)

    def click(self, text):
        element = self.js("return Array.from(document.querySelectorAll('button')).find(b=>b.textContent.trim()===arguments[0])", text)
        assert element, text
        return self.command('WebDriver:ElementClick', {'id': element['element-6066-11e4-a52e-4f735466cecf']})

    def input(self, selector, value):
        self.js("const e=document.querySelector(arguments[0]);e.value=arguments[1];e.dispatchEvent(new Event('input',{bubbles:true}));e.dispatchEvent(new Event('change',{bubbles:true}));", selector, value)

    def text(self):
        return self.js('return document.body.innerText')
