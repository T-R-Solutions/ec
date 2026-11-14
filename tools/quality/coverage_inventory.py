"""Inventário de produção Rust derivado dos membros efetivos do Cargo."""

import json
import sys
from pathlib import Path


def production_sources(metadata):
    """Inclui membros fora da pasta do workspace e rejeita metadata incompleta."""
    members = metadata.get("workspace_members", [])
    packages = {package["id"]: package for package in metadata.get("packages", [])}
    if not members or len(members) != len(set(members)) or not set(members) <= packages.keys():
        raise ValueError("membros Cargo ausentes ou inconsistentes")
    sources = set()
    for member in members:
        manifest = Path(packages[member]["manifest_path"]).resolve(strict=True)
        for path in (manifest.parent / "src").rglob("*.rs"):
            if (
                "tests" in path.relative_to(manifest.parent).parts
                or path.name == "tests.rs"
                or path.name.endswith(("_tests.rs", "-tests.rs"))
            ):
                continue
            sources.add(str(path.resolve(strict=True)))
    if not sources:
        raise ValueError("inventário de produção Cargo vazio")
    return sorted(sources)


if __name__ == "__main__":
    print(json.dumps(production_sources(json.loads(Path(sys.argv[1]).read_text()))))
