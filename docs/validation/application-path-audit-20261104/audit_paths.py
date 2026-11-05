"""Auditoria estática dos caminhos vigentes; preserva fontes históricas."""
import hashlib
import json
import re
from pathlib import Path
from urllib.parse import unquote, urlsplit

ROOT = Path(__file__).resolve().parents[3]
OUT = Path(__file__).resolve().parent
IMPORTED = "docs/readme-lifecycle-engineering-SKILL.md"
PLANNED = {
    "application/agents/Cargo.toml",
    "application/server/backend/Cargo.toml",
    "application/server/frontend/package.json",
}


def main():
    files = [ROOT / "README.md", ROOT / "AGENTS.md"]
    for folder in ["application", "docs", "sdd", "tools/governance", "config/governance"]:
        files.extend(p for p in (ROOT / folder).rglob("*") if p.is_file()
                     and "__pycache__" not in p.parts
                     and not p.is_relative_to(ROOT / "docs/validation"))
    files = sorted(set(files))
    links, paths, errors, inventories = [], [], [], []
    for p in files:
        name = p.relative_to(ROOT).as_posix()
        inventories.append({"path": name, "sha256": hashlib.sha256(p.read_bytes()).hexdigest()})
        if p.suffix not in {".md", ".py", ".json"}:
            continue
        text = p.read_text()
        if p.suffix == ".md":
            for match in re.finditer(r'\[[^\]]*\]\(([^)]+)\)', text):
                target = match[1]
                parts = urlsplit(target.strip("<>"))
                if parts.scheme or parts.netloc or not parts.path:
                    continue
                resolved = (p.parent / unquote(parts.path)).resolve()
                exists = resolved.is_relative_to(ROOT) and resolved.is_file()
                status = "EXISTS" if exists else "MISSING"
                if not exists and name == IMPORTED:
                    resource = ROOT / ".codex/skills/readme-lifecycle-engineering" / parts.path
                    if resource.is_file():
                        status = "IMPORTED_REFERENCE"
                links.append({"file": name, "line": text.count("\n", 0, match.start()) + 1,
                              "target": target, "status": status})
                if status == "MISSING":
                    errors.append(name + ": link ausente: " + target)
        for match in re.finditer(r'`([^`\n]+)`', text):
            value = match[1]
            if not value.startswith(("application/", "agents/", "server/", "tests/")):
                continue
            if value.startswith("application/"):
                if (ROOT / value).exists():
                    status = "EXISTS"
                elif value in PLANNED:
                    status = "PLANNED"
                elif "<dominio>" in value:
                    status = "DOMAIN_PATTERN"
                    for domain in (ROOT / "application/server/frontend/src/domains").iterdir():
                        if not (ROOT / value.replace("<dominio>", domain.name)).is_dir():
                            errors.append(name + ": padrão sem destino: " + value)
                else:
                    status = "MISSING"
                    errors.append(name + ": caminho ausente: " + value)
            elif value.startswith("agents/") and name in {
                "docs/development/skills-analysis.md",
                "docs/reavaliacao-skills-ec-dois-monolitos-atualizada.md",
            }:
                status = "SKILL_METADATA"
            elif value.startswith("tests/"):
                status = "LOCAL_OR_EXAMPLE"
            else:
                status = "OLD_ROOT"
                errors.append(name + ": raiz antiga: " + value)
            paths.append({"file": name, "value": value, "status": status})
    for name in ["agents", "server", "tests"]:
        if (ROOT / name).exists():
            errors.append("Diretório antigo na raiz: " + name)
    # Os arquivos da evidência vigente são verificados pelo gate SDD; seu próprio
    # hash fica fora deste inventário para evitar autorreferência na nova observação.
    inventories = [item for item in inventories
                   if item["path"] != "sdd/features/governance-adoption/evidence.json"]
    result = {
        "status": "PASS" if not errors else "FAIL",
        "files_scanned": len(files),
        "application_files": sum(p.is_relative_to(ROOT / "application") for p in files),
        "markdown_links_checked": len(links),
        "errors": errors, "links": links, "literal_paths": paths,
        "inventory": inventories,
        "preserved": ["docs/validation: observações históricas e caminhos então existentes",
                      "reverse_engineering_teamcity: pesquisa histórica externa ao produto",
                      ".codex/skills e .local/skill-installation: metadados e baseline",
                      "docs/readme-lifecycle-engineering-SKILL.md: cópia importada; cinco links relativos pertencem ao pacote instalado"],
        "limits": ["Inspeção estática e revisão manual; sem runtime nem renderização.",
                   "Arquivos futuros e padrões são classificados, não criados para satisfazer links."],
    }
    (OUT / "path-audit.json").write_text(json.dumps(result, ensure_ascii=False, indent=2) + "\n")
    print(f"Caminhos: {result['status']}; {len(files)} arquivos; {len(links)} links; {len(errors)} erros", flush=True)
    if errors:
        raise RuntimeError(errors)


if __name__ == "__main__":
    main()
