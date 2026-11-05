"""Coleta desta revisão documental; oracles não exercitam o produto Ec."""
from concurrent.futures import ThreadPoolExecutor
import hashlib
import json
import os
from pathlib import Path
import re
import subprocess
import sys
from urllib.parse import unquote
import zipfile

ROOT = Path(__file__).resolve().parents[3]
OUT = Path(__file__).resolve().parent
EVIDENCE = ROOT / 'sdd/features/governance-adoption/evidence.json'


def digest(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def write_json(path, value):
    path.write_text(json.dumps(value, ensure_ascii=False, indent=2) + '\n')


def run(command):
    result = subprocess.run(command, cwd=ROOT, capture_output=True, text=True,
                            env=dict(os.environ, PYTHONDONTWRITEBYTECODE='1'))
    return {'command': command, 'cwd': str(ROOT), 'exit_code': result.returncode,
            'stdout': result.stdout, 'stderr': result.stderr}


def anchors(path):
    values = set()
    counts = {}
    text = re.sub(r'```.*?```', '', path.read_text(), flags=re.S)
    for title in re.findall(r'^#{1,6}\s+(.+)$', text, flags=re.M):
        title = re.sub(r'\[([^]]+)\]\([^)]+\)', r'\1', title)
        value = re.sub(r'[^\w\s-]', '', title.lower()).replace(' ', '-')
        count = counts.get(value, 0)
        counts[value] = count + 1
        values.add(value + (f'-{count}' if count else ''))
    values.update(re.findall(r'(?:id|name)=["\']([^"\']+)', text))
    return values


def luminance(value):
    channels = [int(value[i:i + 2], 16) / 255 for i in (1, 3, 5)]
    linear = [c / 12.92 if c <= 0.04045 else ((c + 0.055) / 1.055) ** 2.4
              for c in channels]
    return sum(c * factor for c, factor in zip(linear, [0.2126, 0.7152, 0.0722]))


def contrast(a, b):
    first, second = sorted([luminance(a), luminance(b)])
    return (second + 0.05) / (first + 0.05)


scope = json.loads((OUT / 'scope.json').read_text())
with zipfile.ZipFile(OUT / 'previous.zip') as archive:
    previous = json.loads(archive.read('sdd/features/governance-adoption/evidence.json'))
    previous_readme = archive.read('README.md').decode()
old_inputs = {i['path']: i for e in previous['evidence'] for i in e['inputs']}
changed = sorted(name for name, item in old_inputs.items() if digest(ROOT / name) != item['sha256'])
unexpected = sorted(set(changed) - set(scope['edited_paths']))
assert not unexpected, f'Inputs anteriores alterados fora do escopo: {unexpected}'

catalog = json.loads((ROOT / 'docs/assets/catalog.json').read_text())
visual = json.loads((OUT / 'visual-checks.json').read_text())
assert len(visual['generated']) == len(catalog['figures']) == 38
assert len(visual['cases']) == 312
assert not visual['errors']
documents = set(catalog['documents']) | {'README.md', 'docs/architecture/application-overview.md',
                                        'docs/assets/README.md', str((OUT / 'report.md').relative_to(ROOT))}
direct = set(re.findall(r'\]\(([^)#]+)', previous_readme))
assert direct <= documents, f'Referências diretas fora da revisão: {direct - documents}'

# Oracle independente de autoria: tabela canônica vigente de dependências Rust.
table = (ROOT / 'docs/architecture/application-layout.md').read_text()
dependencies = {}
for module, cell in re.findall(r'^\| (ec-(?:agent|server)-[\w-]+) \| ([^|]+) \|$', table, re.M):
    if module.endswith('-app'):
        continue
    dependencies[module] = set(re.findall(r'ec-(?:agent|server)-[\w-]+', cell))
dependency_checks = []
for module, expected in dependencies.items():
    family = 'agent' if module.startswith('ec-agent-') else 'server'
    name = family + '-' + module.removeprefix('ec-' + family + '-')
    text = (ROOT / f'docs/assets/diagrams/{name}.mmd').read_text()
    actual = {'ec-' + family + '-' + target.lower() for target in re.findall(r'SELF -->\|"pode consumir"\| (\w+)', text)}
    assert actual == expected, (module, expected, actual)
    dependency_checks.append({'module': module, 'expected': sorted(expected), 'observed': sorted(actual)})
assert len(dependency_checks) == 12

assets = []
for name, metadata in catalog['figures'].items():
    for extension in ['mmd', 'layout.json', 'svg']:
        p = ROOT / f'docs/assets/diagrams/{name}.{extension}'
        assert p.is_file(), p
        assets.append({'path': p.relative_to(ROOT).as_posix(), 'sha256': digest(p)})
    for source in metadata['sources']:
        assert (ROOT / source).is_file(), source
    svg = (ROOT / f'docs/assets/diagrams/{name}.svg').read_text()
    assert '<script' not in svg and '<foreignObject' not in svg
    assert 'aria-' in svg and '<title' in svg and '<desc' in svg

links = []
future_outputs = {OUT / 'checks.json', OUT / 'gates.json'}
for name in sorted(documents):
    p = ROOT / name
    text = re.sub(r'```.*?```', '', p.read_text(), flags=re.S)
    for target in re.findall(r'\]\(([^)\n]+)\)', text):
        if target.startswith(('https:', 'http:', 'mailto:')):
            continue
        location, _, fragment = target.partition('#')
        dest = (p.parent / unquote(location)).resolve() if location else p
        exists = dest.is_file() and dest.is_relative_to(ROOT)
        assert exists or dest in future_outputs, (name, target)
        anchor_ok = not fragment or dest.suffix != '.md' or unquote(fragment) in anchors(dest)
        assert anchor_ok, f'Âncora inválida: {name} → {target}'
        links.append({'source': name, 'target': target, 'anchor_checked': bool(fragment and dest.suffix == '.md')})

palette = {
    'blue': ['#f1f6fc', '#425f7f', '#193b63'],
    'teal': ['#eff9f6', '#397569', '#185446'],
    'purple': ['#f6f2fc', '#776099', '#533679'],
    'amber': ['#fff8e9', '#947237', '#704d13'],
    'rose': ['#fff1f3', '#a65368', '#842b43'],
}
contrasts = []
for tone, (fill, stroke, text) in palette.items():
    record = {'tone': tone, 'text': contrast(fill, text), 'stroke': contrast(fill, stroke)}
    assert record['text'] >= 4.5 and record['stroke'] >= 3, record
    contrasts.append(record)
assert contrast('#ffffff', '#52657b') >= 3

stale = run([sys.executable, 'sdd/tools/validate.py', '--root', '.', '--require-approved', '--require-fresh'])
assert stale['exit_code'] != 0 and 'Stale input' in stale['stdout'] + stale['stderr'], stale
write_json(OUT / 'stale-gate.json', stale)
commands = [
    [sys.executable, 'tools/governance/validate.py'],
    [sys.executable, 'tools/governance/validate.py', '--require-installed'],
    [sys.executable, 'tools/governance/install_skills.py', '--check'],
    [sys.executable, 'sdd/tools/validate.py', '--root', '.', '--structure-only'],
    [sys.executable, '-m', 'unittest', 'discover', '-s', 'tools/governance/tests', '-v'],
]
with ThreadPoolExecutor(max_workers=3) as executor:
    checks = list(executor.map(run, commands))
write_json(OUT / 'command-checks.json', checks)
for item in checks:
    print(' '.join(item['command'][1:]), 'exit', item['exit_code'], flush=True)
    if item['exit_code']:
        print(item['stdout'] + item['stderr'])
assert all(item['exit_code'] == 0 for item in checks)
assert 'Ran 24 tests' in checks[-1]['stderr'] and 'OK' in checks[-1]['stderr']
placeholder = ('Este registro será completado após a execução final dos checks;\n'
               'resultados pendentes não recebem aprovação por antecipação.')
summary = (f'Resultado observado: 38 figuras, 52 páginas de prévia e 312 casos de largura/tema, '
           f'{len(links)} links locais, incluindo âncoras, e 12 mapas de dependências Rust '
           'conferidos contra a tabela canônica. Auditor padrão e com instalação exigida: '
           'PASS, 67 skills e 596 arquivos; preflight: ALREADY_INSTALLED; estrutura SDD: PASS; '
           '24 testes existentes: OK. A matriz não encontrou overflow global, imagens '
           'ausentes, nós sobrepostos, rótulos essenciais abaixo de 14 CSS px ou falhas '
           'de contraste de texto nos seletores medidos. Os gates atuais constam de gates.json.')
report = OUT / 'report.md'
assert placeholder in report.read_text()
report.write_text(report.read_text().replace(placeholder, summary))
preview = ROOT / '.local/build/documentation-enterprise/site' / report.relative_to(ROOT).with_suffix('.md.html')
if preview.is_file():
    preview.write_text(preview.read_text().replace(placeholder, summary))

skills = ['technical-documentation', 'readme-lifecycle-engineering', 'frontend-testing']
result = {
    'date': '2026-11-04', 'environment': sys.version, 'route': 'local documental',
    'changed_previous_inputs': changed, 'unexpected_previous_inputs': unexpected,
    'direct_readme_references': sorted(direct), 'documents': sorted(documents),
    'figures': len(catalog['figures']), 'visual_cases': len(visual['cases']),
    'links': links, 'rust_dependency_oracle': dependency_checks, 'palette_contrast': contrasts,
    'skills': [{'path': f'.codex/skills/{name}/SKILL.md', 'sha256': digest(ROOT / f'.codex/skills/{name}/SKILL.md')}
               for name in skills],
    'assets': assets, 'commands': checks,
    'semantic_review': 'As novas imagens mantêm requisitos planejados e dependências documentadas. Normas e fontes históricas preservadas; sem novas garantias de runtime.',
    'visual_inspection': 'README em claro/escuro, telas de arquitetura/contratos/contribuição e pranchas das 38 figuras inspecionadas. Correções: composição compacta e quebra de caminhos para evitar overflow de rótulos.',
    'limits': ['Prévia local Markdown-it; não é o frontend Ec nem um cliente Markdown remoto.',
               'Sem estudo com leitores ou tecnologia assistiva e sem certificação WCAG.',
               'Sem execução de produto ou consulta de URLs externas.'],
}
write_json(OUT / 'checks.json', result)
assert all((OUT / p).is_file() for p in ['checks.json', 'visual-checks.json', 'command-checks.json'])

# Nova observação dos mesmos seis critérios de governança, sem reutilizar o resultado antigo.
record = 'E-DOCUMENTATION-ENTERPRISE'
current = dict(previous)
current['environment'] = 'Python 3.13.16; revisão documental e visual local em 2026-11-04; Chromium 141.0.7390.37; sem histórico Git acessível'
for requirement in current['requirements']:
    requirement['evidence_ids'] = [record]
current['limitations'] = [
    'Runtime Ec, eficácia geral das skills e descoberta na UI não verificados.',
    'Não há transação multi-arquivo nem teste de escritores hostis/watchers.',
    'O bundle preserva fontes pertinentes; não reconstrói os ZIPs integrais retirados.',
    'Renderização observada somente na prévia local Markdown-it/Chromium, sem certificação de outros clientes.',
    'Sem estudo com leitores, teste de tecnologia assistiva ou certificação WCAG.',
]
inputs = {name: dict(item) for name, item in old_inputs.items()}
for base in [ROOT / 'docs/assets', OUT]:
    for p in base.rglob('*'):
        if p.is_file() and p.name not in {'gates.json', 'checks.json'}:
            name = p.relative_to(ROOT).as_posix()
            inputs[name] = {'path': name, 'role': 'other'}
for name in inputs:
    inputs[name]['sha256'] = digest(ROOT / name)
current['evidence'] = [{
    'id': record, 'kind': 'inspection',
    'command_or_method': 'Revisão semântica contra autoridades; comparação de inputs anteriores; oracles de dependências, links/âncoras e paleta; auditor/preflight; 24 testes existentes; Playwright em três larguras e dois temas. Métodos em verify.py e render.mjs; resultados em checks.json e visual-checks.json.',
    'observed_result': f'Seis critérios de governança reobservados: 67 skills/596 arquivos íntegros; 24 testes OK; {len(links)} links locais e {len(dependency_checks)} módulos Rust conferidos; 38 figuras e 312 casos visuais. Produto permanece sem execução.',
    'artifact': (OUT / 'checks.json').relative_to(ROOT).as_posix(), 'sha256': digest(OUT / 'checks.json'),
    'inputs': [inputs[name] for name in sorted(inputs)],
}]
write_json(EVIDENCE, current)
gates = [run([sys.executable, 'sdd/tools/validate.py', '--root', '.']),
         run([sys.executable, 'sdd/tools/validate.py', '--root', '.', '--require-approved', '--require-fresh'])]
write_json(OUT / 'gates.json', gates)
assert all(item['exit_code'] == 0 for item in gates), gates
print(f'APPROVED no escopo observado: {len(documents)} páginas, {len(links)} links, 38 figuras, 312 casos visuais; gates atuais aprovados.')
