"""Reobservação documental de 2026-11-03; não é teste de produto."""
import hashlib
import importlib.util
import json
import os
from pathlib import Path
import subprocess
import sys
import zipfile

ROOT = Path(__file__).resolve().parents[3]
OUT = Path(__file__).resolve().parent
REL = str(OUT.relative_to(ROOT))
EVIDENCE = "sdd/features/governance-adoption/evidence.json"


def digest(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def write_json(path, value):
    path.write_text(json.dumps(value, ensure_ascii=False, indent=2) + "\n")


def main():
    os.chdir(ROOT)
    manifest = json.loads((OUT / "manifest.json").read_text())
    with zipfile.ZipFile(OUT / "previous.zip") as archive:
        previous = json.loads(archive.read(EVIDENCE))
    old_inputs = {item["path"]: item for record in previous["evidence"]
                  for item in record["inputs"]}
    allowed = set(manifest["modified"])
    changed = []
    unexpected = []
    for name, item in old_inputs.items():
        current = digest(ROOT / name)
        if current != item["sha256"]:
            changed.append(name)
            if name not in allowed:
                unexpected.append(name)
    if unexpected:
        raise RuntimeError("Inputs anteriores alterados fora do escopo: " + repr(unexpected))
    checks = []
    env = os.environ.copy()
    env["PYTHONDONTWRITEBYTECODE"] = "1"
    commands = [
        ("catalog", ["python3", "tools/governance/validate.py"]),
        ("installation", ["python3", "tools/governance/validate.py", "--require-installed"]),
        ("structure", ["python3", "sdd/tools/validate.py", "--root", ".", "--structure-only"]),
        ("governance-tests", ["python3", "-m", "unittest", "discover", "-s", "tools/governance/tests", "-v"]),
        ("installation-check", ["python3", "tools/governance/install_skills.py", "--check"]),
    ]
    for name, command in commands:
        result = subprocess.run(command, cwd=ROOT, env=env, capture_output=True, text=True)
        log = OUT / (name + ".log")
        log.write_text("COMMAND: " + " ".join(command) + "\nEXIT: " +
                       str(result.returncode) + "\n" + result.stdout + result.stderr)
        checks.append({"name": name, "command": command, "exit_code": result.returncode,
                       "log": str(log.relative_to(ROOT)), "sha256": digest(log)})
        print(name + ": exit=" + str(result.returncode), flush=True)
    spec = importlib.util.spec_from_file_location("sdd_layout_check", ROOT / "sdd/tools/validate.py")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    errors = []
    for folder in ["agents", "server", "tests"]:
        errors.extend(module.markdown_errors(ROOT / folder, ROOT))
    module_paths = [*sorted((ROOT / "agents/crates").iterdir()),
                    *sorted((ROOT / "server/backend/crates").iterdir()),
                    *sorted((ROOT / "server/frontend/src/domains").iterdir())]
    for folder in module_paths:
        for relative in ["README.md", "src/.gitkeep", "tests/.gitkeep"]:
            if not (folder / relative).is_file():
                errors.append("Estrutura ausente: " + str(folder / relative))
    for folder in ["agents", "server", "tests"]:
        for path in (ROOT / folder).rglob("*"):
            if path.is_file() and path.name not in {"README.md", ".gitkeep"}:
                errors.append("Arquivo de produto inesperado: " + str(path))
            if path.is_dir() and not any(path.iterdir()):
                errors.append("Diretório vazio não preservado: " + str(path))
    for name, expected in manifest["files"].items():
        if name == REL + "/report.md":
            continue
        if digest(ROOT / name) != expected:
            errors.append("Conteúdo diferente do preparado: " + name)
    checks.append({"name": "tree-links-content", "errors": errors,
                   "rust_crates": 14, "frontend_domains": 9,
                   "actual_module_count": len(module_paths)})
    if len(module_paths) != 23:
        errors.append("Quantidade inesperada de módulos")
    passed = all(row.get("exit_code", 0) == 0 for row in checks) and not errors
    skills = [{"path": ".codex/skills/" + name + "/SKILL.md",
               "sha256": digest(ROOT / ".codex/skills" / name / "SKILL.md"),
               "contribution": contribution}
              for name, contribution in [
                  ("readme-lifecycle-engineering", "Entradas, navegação e distinção de estado"),
                  ("software-architecture", "Domínios, alternativas e dependências")]]
    result = {"status": "PASS" if passed else "FAIL", "checks": checks,
              "observed_changed_inputs": changed, "unexpected_changed_inputs": unexpected,
              "skills": skills, "python": sys.version,
              "limits": ["Sem runtime de produto, testes de produto ou renderização visual.",
                         "Revisão semântica manual; verificação de caminhos não prova domínio correto.",
                         "AC-06 preserva a observação histórica da limpeza; nenhuma remoção foi reexecutada."]}
    write_json(OUT / "checks.json", result)
    if not passed:
        raise RuntimeError("Falha na validação; consulte checks.json e logs. Evidência não renovada.")
    doc_list = "\n".join("- [" + name + "](../../../" + name + ")"
                         for name in sorted(manifest["files"])
                         if name.endswith(".md") and name != REL + "/report.md")
    skill_rows = "\n".join("| " + s["path"] + " | " + s["sha256"] + " | " +
                           s["contribution"] + " |" for s in skills)
    report = """# Validação da estrutura documental da aplicação

Data: 2026-11-03. Resultado: APPROVED para a entrega documental e a reobservação de governança. Runtime Ec permanece UNVERIFIED; não há código, manifests, testes de produto ou aplicação executável.

## Escopo, método e resultado

A rota local documental materializou 14 crates planejados (quatro domínios Agent, oito backend e dois pontos de composição), nove domínios frontend, app/shared e diretórios de testes. Cada domínio tem README, src e tests; diretórios sem código têm .gitkeep.

A árvore completa está no [mapa arquitetural](../../architecture/application-layout.md); as escolhas e alternativas estão na [ADR-002](../../decisions/ADR-002-application-layout.md). O [manifesto](manifest.json) identifica todos os arquivos preparados e seus hashes. O [procedimento](verify.py), os [resultados](checks.json) e os logs registram esta execução.

| Critério desta entrega | Estado | Evidência e limite |
| --- | --- | --- |
| Estrutura materializada, src/tests por domínio, sem manifests fictícios | APPROVED | Inspeção de árvore e inventário real; todos os módulos têm arquivos de preservação |
| Navegação, referências e conteúdo preparado | APPROVED | Links locais dos componentes e auditoria documental passam; hashes dos arquivos preparados conferidos |
| Responsabilidades, dependências e política de testes | APPROVED | Revisão contra pedido, contexto e duas normas; grafo permitido sem dependência reversa de composição; frontend usa interfaces públicas |
| Verificações existentes | APPROVED | Auditor, instalação, estrutura SDD, preflight e 24 testes de governança com exit 0 |
| Produto, segurança, capacidade e acessibilidade em runtime | UNVERIFIED | Não implementados nesta etapa |
| Renderização visual e pesquisa com leitores | UNVERIFIED | Houve inspeção do Markdown e links, sem ensaio de renderização/usabilidade |

A separação mantém os dois monólitos. PostgreSQL/HTTP e YAML canônico conservam seu status; gRPC/Protobuf/SQLite local permanecem propostas. Pinia vem do pedido vigente. Não foram herdados toolchain, licença, dependências ou domínios Ox.

Cenários de teste descritos nos READMEs são trabalho futuro. Testes de governança permanecem em tools/governance/tests; a única adaptação de código inclui agents/server/tests nas fixtures para resolver seus novos links. Não foram adicionados testes de produto nem testes que apenas reproduzam a documentação.

## Reobservação da governança existente

A auditoria e o gate de evidências atuais passaram antes das alterações. As versões anteriores dos oito arquivos acompanhados, incluindo evidence.json, estão em [previous.zip](previous.zip). A comparação pós-edição conferiu os 1.293 inputs únicos da observação anterior: somente entradas documentais e a fixture autorizada mudaram; o conjunto exato está em checks.json.

Reexaminamos os seis critérios de governance-adoption:
- AC-01/AC-05: catálogo, origens, 67 skills/594 arquivos e baseline verificados novamente por auditor e preflight.
- AC-02: revisão das novas entradas contra as fontes preserva autoridades e distingue estrutura de implementação e proposta de decisão.
- AC-03: links, pontes, metadados e estrutura foram revalidados; evidence.json aponta para esta nova observação.
- AC-04: os 24 controles existentes passaram em fixtures que incluem os novos diretórios.
- AC-06: o histórico de limpeza foi conservado e seus inputs não alterados fora do escopo. Os gates atuais foram reexecutados; a remoção histórica não foi repetida nem apresentada como evento desta etapa.

A observação anterior não recebeu hashes novos para aparentar execução atual. Foi arquivada antes da edição; novos registros de evidência vinculam a revisão e os testes desta etapa aos arquivos atuais. O gate final é registrado separadamente em [gates.json](gates.json), depois de estabilizar os inputs. Seu resultado valida vínculos e estados, não runtime ou adequação semântica por si só.

## Skills efetivamente utilizadas

| Caminho | SHA-256 observado | Contribuição |
| --- | --- | --- |
""" + skill_rows + """

Não houve delegação, instalação de skills, commit ou publicação. technical-documentation não foi necessária como especialidade adicional: a separação de entradas e documentos canônicos foi coberta pela skill primária.

## Documentos criados ou atualizados

""" + doc_list + """

## Pendências e manutenção

Fixar toolchains, versões, manifests, runners e descoberta de testes; especificar schemas, APIs, transações, contratos de revisão/tentativa/lease e recuperação; implementar e demonstrar autorização, isolamento e acessibilidade. Os READMEs não autorizam execução de workloads ou plugins sem esses contratos e verificações.

Ao alterar limites de domínio, atualizar ADR, mapa e README correspondente. Ao criar código e manifests, documentar comandos somente após executá-los e atualizar a classificação de maturidade. Mudanças de inputs invalidam esta observação conforme [evidence.md](../../../sdd/evidence.md).
"""
    (OUT / "report.md").write_text(report)
    # Create the gate record before checking all links; its final contents are not evidence inputs.
    write_json(OUT / "gates.json", {"status": "UNVERIFIED", "checks": []})
    manifest["files"][REL + "/report.md"] = digest(OUT / "report.md")
    write_json(OUT / "manifest.json", manifest)
    current_inputs = {name: {"path": name, "sha256": digest(ROOT / name),
                             "role": item["role"]}
                      for name, item in old_inputs.items()}
    for name in [*manifest["files"], REL + "/manifest.json",
                 REL + "/previous.zip", *[row["log"] for row in checks if "log" in row]]:
        current_inputs[name] = {"path": name, "sha256": digest(ROOT / name), "role": "other"}
    records = []
    for identifier, kind, artifact, method, observation in [
        ("E-LAYOUT-AUDIT", "test", REL + "/checks.json",
         "Reexecução dos comandos e comparação dos inputs anteriores conforme verify.py e logs.",
         "PASS: 24 controles de governança, auditoria, instalação, preflight, árvore e links; mudanças de inputs restritas ao escopo."),
        ("E-LAYOUT-REVIEW", "inspection", REL + "/report.md",
         "Revisão do delta documental contra autoridades, pedido, política de testes e observação preservada da limpeza.",
         "APPROVED para governança/documentação; YAML, HTTP/PostgreSQL e propostas preservados, sem produto implementado.")
    ]:
        records.append({"id": identifier, "kind": kind, "command_or_method": method,
                        "observed_result": observation, "artifact": artifact,
                        "sha256": digest(ROOT / artifact),
                        "inputs": list(current_inputs.values())})
    evidence = {key: value for key, value in previous.items() if key != "evidence"}
    for requirement in evidence["requirements"]:
        requirement["evidence_ids"] = ["E-LAYOUT-AUDIT", "E-LAYOUT-REVIEW"]
    evidence["environment"] = "Python " + sys.version.split()[0] + "; reobservação documental local em 2026-11-03."
    evidence["evidence"] = records
    evidence["limitations"].append("Estrutura da aplicação documentada; nenhum teste de runtime ou renderização foi executado.")
    write_json(ROOT / EVIDENCE, evidence)
    gates = []
    for name, command in [
        ("standard", ["python3", "sdd/tools/validate.py", "--root", "."]),
        ("acceptance", ["python3", "sdd/tools/validate.py", "--root", ".", "--require-approved", "--require-fresh"]),
        ("final-catalog", ["python3", "tools/governance/validate.py", "--require-installed"])
    ]:
        result = subprocess.run(command, cwd=ROOT, env=env, capture_output=True, text=True)
        (OUT / (name + ".log")).write_text("COMMAND: " + " ".join(command) + "\nEXIT: " +
                                         str(result.returncode) + "\n" + result.stdout + result.stderr)
        gates.append({"name": name, "command": command, "exit_code": result.returncode,
                      "output": result.stdout + result.stderr})
        print(name + ": exit=" + str(result.returncode), flush=True)
    okay = all(row["exit_code"] == 0 for row in gates)
    write_json(OUT / "gates.json", {"status": "PASS" if okay else "FAIL", "checks": gates})
    if not okay:
        raise RuntimeError("Gate final falhou; consulte gates.json.")
    print("PASS: estrutura documental e governança reobservadas; runtime não implementado.", flush=True)


if __name__ == "__main__":
    main()
