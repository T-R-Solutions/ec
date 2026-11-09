from pathlib import Path
import hashlib
import json
import zipfile
root = Path('/home/rodrigo/Projetos/ec')
report = 'docs/validation/tooling-20261106/report.md'
archive = root / 'docs/validation/tooling-20261106/previous-evidence.zip'
extra = ['toolbox.json', 'toolbox.images.lock.json', '.shellcheckrc', 'ruff.toml', 'playwright.config.mjs',
         'config/quality/deny.toml', 'config/quality/audit.toml', 'config/quality/trivy.yaml',
         'config/quality/codeql.qls', 'docs/development/verification-tools.md',
         'application/integration/tests/e2e/frontend_firefox.py',
         'application/server/frontend/tests/playwright/session.spec.mjs',
         'application/server/frontend/tests/playwright/server.mjs',
         'docs/validation/tooling-20261106/shared-toolbox.patch']
extra += [str(p.relative_to(root)) for p in (root / 'scripts').rglob('*') if p.is_file() and '__pycache__' not in p.parts]
def digest(path):
    return hashlib.sha256((root / path).read_bytes()).hexdigest()
with zipfile.ZipFile(archive) as previous:
    for feature in ['governance-adoption', 'first-functional-journey', 'scripts-verification']:
        path = f'sdd/features/{feature}/evidence.json'
        old = json.loads(previous.read(path))
        inputs = {row['path']: row.get('role', 'other') for record in old['evidence'] for row in record['inputs']}
        for name in extra:
            inputs.setdefault(name, 'configuration' if name.endswith(('.toml', '.json', '.yaml', '.qls')) else 'implementation')
        for name in ['spec.md', 'plan.md', 'tasks.md', 'traceability.json']:
            inputs[f'sdd/features/{feature}/{name}'] = 'contract'
        identifier = 'E-TOOLING-20261106-' + feature.upper()
        requirements = old['requirements']
        for criterion in requirements:
            if criterion['status'] in ['APPROVED', 'REJECTED']:
                criterion['evidence_ids'] = [identifier]
        if feature == 'scripts-verification':
            for number in [7, 8]:
                requirements.append({'id': f'AC-0{number}', 'criterion': f'AC-0{number} conforme spec.md; integração/configuração e falhas fechadas observadas; execução completa permanece em AC-04.', 'status': 'APPROVED', 'evidence_ids': [identifier]})
        current = {
            'schema_version': 2,
            'environment': 'Linux openSUSE/SELinux; Bash/jq; Rust 1.99.1; cargo-audit 0.22.2; Docker local; Ruff 0.11.13, Gitleaks 8.24.3, Playwright 1.56.1; PostgreSQL dedicado e Firefox reais. Ferramentas ausentes explicitamente bloqueadas.',
            'requirements': requirements,
            'evidence': [{
                'id': identifier, 'kind': 'test',
                'command_or_method': 'Nova inspeção dos consumidores e configurações; 41 testes shell, 34 de governança e 18 do toolbox; runner completo run-erlhfC5q, reexecução E2E e verificação documental conforme report.md. Preservação dos registros anteriores em previous-evidence.zip.',
                'observed_result': 'Resultados e correções observados em report.md. Gates indisponíveis e achados Ruff continuam sem aprovação; AC-04 de scripts e ACs 01/07 da primeira jornada permanecem UNVERIFIED. Nenhum resultado antigo teve hashes renovados para aparentar execução nova.',
                'artifact': report, 'sha256': digest(report),
                'inputs': [{'path': name, 'sha256': digest(name), 'role': role} for name, role in sorted(inputs.items()) if name != path]
            }],
            'limitations': list(old.get('limitations', [])) + [
                'Registros anteriores preservados em docs/validation/tooling-20261106/previous-evidence.zip; esta é uma nova observação dos inputs atuais.',
                'Ruff tem achados; ShellCheck sem checksum oficial reconhecido; cargo-deny/machete e motores nativos Opengrep/OSV não localizados. Configuração não comprova eficácia dessas ferramentas.',
                'Playwright usa API simulada; o relatório separa isso dos harnesses e Firefox reais. Não há certificação enterprise ou aceite novo de critérios anteriormente UNVERIFIED.'
            ]
        }
        (root / path).write_text(json.dumps(current, ensure_ascii=False, indent=2) + '\n')
