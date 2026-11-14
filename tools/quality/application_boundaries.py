"""Fronteiras verificáveis da aplicação; não substitui revisão de semântica."""

import json
import re
import tomllib
from pathlib import Path

from tools.quality.automation_config import unique


def violations(root):
    """Retorna deriva de dependências, imports e limites SQL do contrato vigente."""
    root = Path(root)
    problems = []
    for workspace in ("application/server/backend", "application/agents"):
        manifest = root / workspace / "Cargo.toml"
        if not manifest.is_file():
            continue
        shared = tomllib.loads(manifest.read_text()).get("workspace", {}).get("dependencies", {})
        for path in (root / workspace / "crates").glob("*/Cargo.toml"):
            dependencies = tomllib.loads(path.read_text()).get("dependencies", {})
            for name, definition in dependencies.items():
                inherited = definition == {"workspace": True}
                effective = shared.get(name) if inherited else definition
                owner = root / workspace if inherited else path.parent
                if isinstance(effective, dict) and "path" in effective:
                    target = (owner / effective["path"]).resolve()
                    public_contract = name == "ec-contracts" and target == root / "application/contracts"
                    if not target.is_relative_to(root / workspace) and not public_contract:
                        problems.append(f"dependência privada entre componentes: {path}: {name}")
                elif not inherited or name not in shared:
                    problems.append(f"dependência fora da autoridade do workspace: {path}: {name}")

    frontend = root / "application/server/frontend/src"
    for path in frontend.rglob("*"):
        if path.suffix not in (".ts", ".vue") or path.name.endswith(".test.ts"):
            continue
        for specifier in re.findall(r"\bfrom\s+['\"]([^'\"]+)['\"]", path.read_text()):
            if not specifier.startswith("."):
                continue
            target = (path.parent / specifier).resolve()
            relative = target.relative_to(frontend) if target.is_relative_to(frontend) else None
            if relative and relative.parts[0] == "domains":
                owner = path.relative_to(frontend).parts
                same_domain = owner[0] == "domains" and owner[1] == relative.parts[1]
                if not same_domain and (relative.parts[2:] != ("src",)):
                    problems.append(f"import de implementação privada: {path}: {specifier}")
                if owner[0] == "shared":
                    problems.append(f"shared depende de domínio: {path}: {specifier}")
            if relative and relative.parts[0] == "app" and path.is_relative_to(frontend / "domains"):
                problems.append(f"domínio depende da composição: {path}: {specifier}")

    descriptor = root / "application/contracts/pipeline-v1.json"
    document = json.loads(descriptor.read_text(), object_pairs_hook=unique) if descriptor.is_file() else None
    schema = root / "application/server/backend/crates/ec-server-projects/src/lib.rs"
    if descriptor.is_file() and schema.is_file():
        fields = set(document["fields"])
        validated = set(re.findall(r'(?:get|name)\("([^"]+)"\)', schema.read_text()))
        if fields != validated:
            problems.append("campos públicos sem validação semântica correspondente")
    composition = root / "application/server/backend/crates/ec-server-app/src"
    for path in composition.rglob("*.rs"):
        if path.name == "tests.rs" or path.name.endswith(("_tests.rs", "-tests.rs")):
            continue
        if re.search(r'Err\s*\(\s*\(\s*409\s*,\s*"request_conflict"', path.read_text()):
            problems.append(f"decisão de replay duplicada na composição: {path}")
    migration = root / "application/server/backend/migrations/003_results.sql"
    if descriptor.is_file() and migration.is_file():
        constants = document["constants"]
        sql = migration.read_text()
        # Migration histórica é imutável: ampliar v1 exige decidir evolução do banco.
        for table, key, pattern in (
            ("output_logs", "MAX_LOG_CHUNK_BYTES", r"octet_length\(data\)<=(\d+)"),
            ("output_parts", "MAX_ARTIFACT_CHUNK_BYTES", r"octet_length\(data\)<=(\d+)"),
            ("output_uploads", "MAX_ARTIFACT_BYTES", r"size BETWEEN 0 AND (\d+)"),
        ):
            statement = next((item for item in sql.split(";") if f"CREATE TABLE {table}(" in item), "")
            match = re.search(pattern, statement)
            if not match or int(match[1]) != constants[key]["value"]:
                problems.append(f"contrato incompatível com migration v1: {key}")
    orchestration = root / "application/server/backend/migrations/002_orchestration.sql"
    if document and orchestration.is_file():
        sql = orchestration.read_text()
        match = re.search(r"ordinal BETWEEN 1 AND (\d+)", sql)
        if not match or int(match[1]) != document["constants"]["MAX_RUN_ATTEMPTS"]["value"]:
            problems.append("contrato incompatível com migration v1: MAX_RUN_ATTEMPTS")
        if "GIT_COMMIT_HEX_BYTES" in document["constants"]:
            length = document["constants"]["GIT_COMMIT_HEX_BYTES"]["value"]
            if "commit_sha ~ '^[0-9a-f]{" + str(length) + "}$'" not in sql:
                problems.append("contrato incompatível com migration v1: GIT_COMMIT_HEX_BYTES")
    if document and migration.is_file() and "SHA256_HEX_BYTES" in document["constants"]:
        length = document["constants"]["SHA256_HEX_BYTES"]["value"]
        if "sha256 ~ '^[0-9a-f]{" + str(length) + "}$'" not in migration.read_text():
            problems.append("contrato incompatível com migration v1: SHA256_HEX_BYTES")
    return problems
