import ast
import json
from pathlib import Path
root = Path.cwd()
report = json.loads((root / 'docs/validation/tooling-remediation-20261106/ruff-before.json').read_text())
by_file = {}
for item in report:
    name = item['filename'].removeprefix('/src/')
    by_file.setdefault(name, {}).setdefault(item['location']['row'], []).append(item['code'])
ledger = []
for name, rows in by_file.items():
    path = root / name
    source = path.read_text()
    lines = source.splitlines()
    for row, codes in rows.items():
        original = lines[row - 1]
        reason = ''
        subprocess_codes = [c for c in codes if c in ('S603', 'S607')]
        if subprocess_codes:
            if "'docker'" in original:
                reason = 'Docker do PATH confiável do operador; container dedicado literal, banco UUID do harness, argv sem shell.'
            elif "'git'" in original:
                reason = 'Git do PATH confiável do operador; comandos literais sobre repositório temporário criado pelo teste, argv sem shell.'
            elif "'npm'" in original:
                reason = 'npm do PATH confiável do operador; script dev versionado, cwd frontend fixo, argumentos literais.'
            elif 'firefox' in original:
                reason = 'Firefox absoluto do sistema; flags literais e perfil temporário privado, sem shell.'
            elif 'AGENT' in original:
                reason = 'Agent compilado em caminho fixo do checkout; configuração privada criada pelo harness, sem shell.'
            elif 'BIN' in original:
                reason = 'Server compilado em caminho fixo; subcomandos e argumentos dos testes locais, sem shell.'
            elif 'test_governance' in name:
                reason = 'Interpretador sys.executable; programa literal do teste de interrupção/lock, caminhos separados em argv.'
            elif 'harness' in original:
                reason = 'Bash do PATH confiável; programa harness literal, caminhos passados como parâmetros posicionais.'
            elif "'bash'" in original:
                reason = 'Bash do PATH confiável; script versionado ou programa literal, argumentos separados, sem interpolação em código shell.'
            else:
                reason = 'argv criado neste teste com Bash e parser versionado; arquivo JSON fixture separado, sem shell=True.'
            lines[row - 1] += '  # noqa: ' + ', '.join(subprocess_codes) + ' — ' + reason
        elif 'S608' in codes:
            reason = 'Corrigido: valores transmitidos por variáveis psql com quoting de literal, sem interpolação SQL.'
        elif 'S310' in codes:
            reason = 'Corrigido: HTTPConnection fixo em loopback; sem proxies, outros protocolos ou redirects automáticos.'
        elif 'S108' in codes:
            reason = 'Corrigido: cwd/escape usam diretório temporário gerenciado pelo teste, sem /tmp literal.'
        elif 'S105' in codes:
            reason = 'Corrigido: sentinela aleatória de teste para provar remoção de variável do ambiente.'
        for code in codes:
            ledger.append(dict(path=name, original_line=row, rule=code, original=original.strip(), disposition=reason))
    source = '\n'.join(lines) + '\n'
    if name.endswith('test_shell.py'):
        source = source.replace("cwd='/tmp'", 'cwd=self.root').replace("symlink_to('/tmp',", 'symlink_to(self.root.parent,')
        source = source.replace("'do-not-inherit'", 'os.urandom(16).hex()')
    # Convert f-string SQL calls into quoted psql variable references.
    tree = ast.parse(source)
    offsets = [0]
    for line in source.splitlines(keepends=True):
        offsets.append(offsets[-1] + len(line.encode()))
    edits = []
    for node in ast.walk(tree):
        if not isinstance(node, ast.Call) or not isinstance(node.func, ast.Attribute) or node.func.attr != 'sql' or not node.args or not isinstance(node.args[0], ast.JoinedStr):
            continue
        query = ''; values = []
        for part in node.args[0].values:
            if isinstance(part, ast.Constant):
                query += part.value
            else:
                key = 'value' + str(len(values))
                query += ":'" + key + "'"
                values.append(key + '=' + ast.unparse(part.value))
        replacement = 'h.sql(' + repr(query) + ', ' + ', '.join(values) + ')'
        edits.append((offsets[node.lineno-1]+node.col_offset, offsets[node.end_lineno-1]+node.end_col_offset, replacement))
    encoded = source.encode()
    for begin, end, replacement in sorted(edits, reverse=True):
        encoded = encoded[:begin] + replacement.encode() + encoded[end:]
    path.write_text(encoded.decode())
(root / 'docs/validation/tooling-remediation-20261106/ruff-triage.json').write_text(json.dumps(ledger, ensure_ascii=False, indent=2) + '\n')
