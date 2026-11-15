"""Aceite local obrigatório: etapas, critérios e recibo atual do runner.

Não autentica um escritor com acesso ao checkout nem intercepta o chat/Git.
"""

import argparse
import importlib.util
import sys
from pathlib import Path

CODE = Path(__file__).resolve().parents[2]


def load_module(name, path):
    spec = importlib.util.spec_from_file_location(name, path)
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


sdd = load_module("task_sdd", CODE / "sdd/tools/validate.py")
automation = load_module("task_automation", CODE / "tools/quality/automation_config.py")


def safe_file(root, value):
    automation.internal(value)
    path = root / value
    if any(p.is_symlink() for p in [path, *path.parents] if p.is_relative_to(root)):
        raise ValueError(f"link não permitido: {value}")
    return sdd.local_file(root, value)


def require(condition, message):
    if not condition:
        raise ValueError(message)


def check_scope(root, feature, kind):
    automation.internal(feature)
    require(Path(feature).as_posix() == feature, "escopo deve ter caminho canônico")
    require(
        Path(feature).parent == Path("sdd/tasks" if kind == "task" else "sdd/features"),
        "escopo deve ser diretório direto de sdd/tasks ou sdd/features",
    )
    base = safe_file(root, feature + "/workflow.json").parent
    documents = {
        name: safe_file(root, feature + "/" + name)
        for name in ("spec.md", "plan.md", "tasks.md", "traceability.json", "evidence.json")
    }
    workflow = sdd.strict_json(base / "workflow.json")
    errors = sdd.workflow_errors(workflow, root, base, kind, require_complete=True)
    require(not errors, "; ".join(errors))
    stages = workflow["stages"]
    evidence = sdd.strict_json(documents["evidence.json"])
    rows = sdd.strict_json(documents["traceability.json"])
    errors = sdd.evidence_errors(evidence, root)
    if errors:
        raise ValueError("; ".join(errors))
    errors = sdd.trace_errors(rows, root, base, evidence)
    errors += sdd.input_errors(evidence, root, rows, base, True)
    require(not errors, "; ".join(errors))
    require(evidence["schema_version"] == 2, "aceite exige evidence schema_version 2")
    observations = {item["id"]: item for item in evidence["evidence"]}
    for stage, refs in stages.items():
        automation.strings(refs)
        require(bool(refs) and set(refs) <= observations.keys(), f"etapa sem evidência: {stage}")
        for ref in refs:
            bound = {item["path"] for item in observations[ref].get("inputs", [])}
            require(
                feature + "/workflow.json" in bound, f"workflow não vinculado à evidência: {ref}"
            )
    if kind == "task":
        reviewed = {
            item["path"] for ref in stages["review"] for item in observations[ref]["inputs"]
        }
        for relation in workflow["scope"]["related_features"]:
            require(
                relation["path"] + "/spec.md" in reviewed,
                "revisão sem vínculo à spec da feature relacionada",
            )
    require(
        any(observations[ref]["kind"] == "test" for ref in stages["validation"]),
        "validação sem evidência de teste",
    )
    expected = sdd.acceptance_ids(documents["spec.md"].read_text())
    for row in evidence["requirements"]:
        if row["id"] in expected:
            require(
                sdd.state(row["status"]) in {"APPROVED", "NOT APPLICABLE"},
                f"critério pendente: {row['id']}",
            )
    return workflow


def check_feature(root, feature):
    return check_scope(root, feature, "feature")


def check_task(root, task):
    return check_scope(root, task, "task")


def check_run(root, receipt, surfaces):
    result_path = safe_file(root, receipt)
    require(result_path.name.endswith("-automate.results.log"), "recibo de runner inválido")
    require(result_path.parent.parent == root / ".local/logs", "recibo fora dos logs locais")
    prefix = receipt.removesuffix("results.log")
    result = sdd.strict_json(result_path)
    final = sdd.strict_json(safe_file(root, prefix + "exit.log"))
    before_path = safe_file(root, prefix + "inputs.log")
    before = sdd.strict_json(before_path)
    after = sdd.strict_json(safe_file(root, prefix + "inputs-after.log"))
    require(
        result.get("executed") is True
        and result.get("complete") is True
        and type(result.get("exit_code")) is int
        and result["exit_code"] == 0
        and not result.get("cancelled"),
        "execução incompleta ou reprovada",
    )
    require(
        type(final.get("exit_code")) is int
        and final["exit_code"] == 0
        and final.get("cleanup_error") is None
        and final.get("intermediate_artifacts") == "discarded",
        "encerramento não aprovado",
    )
    config = automation.load_config(root / "scripts/config/automation.json")
    expected = automation.plan(config, root, surfaces)
    require(
        result.get("root") == str(root)
        and result.get("configuration_sha256") == expected["configuration_sha256"],
        "raiz ou configuração divergente",
    )
    require(result.get("inputs_sha256") == sdd.digest(before_path), "hash do inventário divergente")
    require(
        type(result.get("acceptance_inventory_version")) is int
        and result["acceptance_inventory_version"] == automation.ACCEPTANCE_INVENTORY_VERSION,
        "contrato de inventário de aceite ausente ou antigo: execute novamente",
    )
    current = {
        name: sdd.digest(safe_file(root, name))
        for name in automation.acceptance_files(config, root)
    }
    require(bool(before) and before == after == current, "entradas alteradas: execute novamente")
    required = {(t["project"], t["name"]) for t in expected["tasks"]}
    planned = {(t["project"], t["name"]) for t in result["tasks"]}
    observed = [(t["project"], t["task"]) for t in result["results"]]
    require(
        required <= planned
        and planned == set(observed)
        and len(planned) == len(result["tasks"])
        and len(observed) == len(set(observed)),
        "tarefas ausentes ou duplicadas",
    )
    require(
        all(
            t.get("state") in {"passed", "measured"}
            and type(t.get("returncode")) is int
            and t["returncode"] == 0
            for t in result["results"]
        ),
        "resultado com falha, skip ou bloqueio",
    )


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--root", type=Path, default=CODE)
    scope = parser.add_mutually_exclusive_group(required=True)
    scope.add_argument("--feature", help="aceitar todos os critérios da feature")
    scope.add_argument("--task", help="aceitar somente sdd/tasks/ID e suas relações explícitas")
    parser.add_argument("--run", required=True, help="caminho relativo do automate.results.log")
    args = parser.parse_args()
    try:
        root = args.root.resolve(strict=True)
        workflow = check_task(root, args.task) if args.task else check_feature(root, args.feature)
        check_run(root, args.run, workflow["surfaces"])
    except (OSError, ValueError, KeyError, TypeError, AttributeError) as exc:
        print(f"REJECTED: {exc}", file=sys.stderr)
        return 1
    print("APPROVED: etapas e critérios vinculados; runner completo e entradas atuais.")
    print(
        "Limite: controle local, sem autenticação contra escritor do checkout ou bloqueio do chat."
    )
    return 0


if __name__ == "__main__":
    sys.exit(main())
