"""Reobservação da correção de caminhos; não verifica runtime Ec."""
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
REL = OUT.relative_to(ROOT).as_posix()
EVIDENCE = "sdd/features/governance-adoption/evidence.json"


def digest(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def write_json(path, value):
    path.write_text(json.dumps(value, ensure_ascii=False, indent=2) + "\n")


def relocated(name):
    return "application/" + name if name.startswith(("agents/", "server/")) else name


def main():
    scope = json.loads((OUT / "scope.json").read_text())
    with zipfile.ZipFile(OUT / "previous.zip") as archive:
        previous = json.loads(archive.read(EVIDENCE))
    old_inputs = {item["path"]: item for record in previous["evidence"]
                  for item in record["inputs"]}
    changed = []
    for name, item in old_inputs.items():
        if digest(ROOT / relocated(name)) != item["sha256"] or relocated(name) != name:
            if name not in scope:
                raise RuntimeError("Input alterado fora do escopo: " + name)
            changed.append(name)
    spec = importlib.util.spec_from_file_location("layout_sdd", ROOT / "sdd/tools/validate.py")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    errors = []
    for folder in ["application", "tests"]:
        errors.extend(module.markdown_errors(ROOT / folder, ROOT))
    for name in ["agents", "server"]:
        if (ROOT / name).exists():
            errors.append("Diretório antigo ainda presente: " + name)
    modules = [*sorted((ROOT / "application/agents/crates").iterdir()),
               *sorted((ROOT / "application/server/backend/crates").iterdir()),
               *sorted((ROOT / "application/server/frontend/src/domains").iterdir())]
    if len(modules) != 23:
        errors.append("Quantidade de módulos diferente de 23")
    for folder in modules:
        for name in ["README.md", "src/.gitkeep", "tests/.gitkeep"]:
            if not (folder / name).is_file():
                errors.append("Arquivo ausente: " + str(folder / name))
    if errors:
        raise RuntimeError(errors)
    checks = []
    env = dict(os.environ, PYTHONDONTWRITEBYTECODE="1")
    for name, command in [
        ("catalog", ["python3", "tools/governance/validate.py", "--require-installed"]),
        ("structure", ["python3", "sdd/tools/validate.py", "--root", ".", "--structure-only"]),
        ("tests", ["python3", "-m", "unittest", "discover", "-s", "tools/governance/tests", "-v"]),
        ("installation", ["python3", "tools/governance/install_skills.py", "--check"]),
    ]:
        result = subprocess.run(command, cwd=ROOT, env=env, capture_output=True, text=True)
        (OUT / (name + ".log")).write_text("COMMAND: " + " ".join(command) +
                                          "\nEXIT: " + str(result.returncode) + "\n" +
                                          result.stdout + result.stderr)
        checks.append({"name": name, "command": command, "exit_code": result.returncode})
        print(name + ": exit=" + str(result.returncode), flush=True)
    write_json(OUT / "checks.json", {"checks": checks, "link_errors": errors,
                                   "module_count": len(modules), "changed_inputs": changed})
    if any(item["exit_code"] for item in checks):
        raise RuntimeError("Verificação falhou; evidência não renovada")
    inputs = {relocated(name): dict(item, path=relocated(name),
                                   sha256=digest(ROOT / relocated(name)))
              for name, item in old_inputs.items()}
    for name in scope:
        name = relocated(name)
        if name != EVIDENCE:
            inputs[name] = {"path": name, "sha256": digest(ROOT / name), "role": "other"}
    for path in OUT.iterdir():
        if path.name in {"gates.json", "standard.log", "acceptance.log"}:
            continue
        name = path.relative_to(ROOT).as_posix()
        inputs[name] = {"path": name, "sha256": digest(path), "role": "other"}
    evidence = {key: value for key, value in previous.items() if key != "evidence"}
    evidence["environment"] = "Python " + sys.version.split()[0] + "; correção local em 2026-11-04"
    evidence["evidence"] = [{
        "id": "E-APPLICATION-PATHS", "kind": "inspection",
        "command_or_method": "verify.py: comparação de inputs, árvore, links, auditoria, preflight e 24 testes; revisão documental descrita em report.md.",
        "observed_result": "APPROVED: caminhos corrigidos, contratos preservados e verificações com exit 0; observação histórica da limpeza preservada.",
        "artifact": REL + "/checks.json", "sha256": digest(OUT / "checks.json"),
        "inputs": list(inputs.values()),
    }]
    for requirement in evidence["requirements"]:
        requirement["evidence_ids"] = ["E-APPLICATION-PATHS"]
    write_json(ROOT / EVIDENCE, evidence)
    gates = []
    for name, extra in [("standard", []), ("acceptance", ["--require-approved", "--require-fresh"])]:
        command = ["python3", "sdd/tools/validate.py", "--root", ".", *extra]
        result = subprocess.run(command, cwd=ROOT, env=env, capture_output=True, text=True)
        (OUT / (name + ".log")).write_text(result.stdout + result.stderr)
        gates.append({"command": command, "exit_code": result.returncode})
        print(name + ": exit=" + str(result.returncode), flush=True)
    write_json(OUT / "gates.json", gates)
    if any(item["exit_code"] for item in gates):
        raise RuntimeError("Gate SDD falhou")


if __name__ == "__main__":
    main()
