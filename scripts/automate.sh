#!/usr/bin/env bash
set -euo pipefail
umask 077
CODE=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/.." && pwd -P)
source "$CODE/scripts/stacks/ec/common.sh"
usage() {
    cat <<'HELP'
Uso: bash scripts/automate.sh plan|run [--root DIRETÓRIO] [--surface NOME] [--check NOME]
Superfícies: docs scripts server agent frontend security integration e2e (padrão: todas).
Checks padrão selecionáveis: machete deny geiger audit coverage trivy codeql ruff shellcheck playwright.
Checks adicionais: lint build-budget performance dev setup grype bandit syft yara-x.
Checks de segurança: secrets sast dependencies (secrets/sast incluem testes de regras).
--check é repetível e limita tarefas das superfícies selecionadas; governança permanece obrigatória.
Sem --surface, --check seleciona sua superfície; coverage exige server/agent/frontend explícito.
plan produz JSON sem executar tarefas. run preserva logs, exit e resultados em .local/automation/runs.
HELP
}
[[ $# -gt 0 ]] || { usage >&2; exit 2; }
operation=$1; shift
case $operation in -h|--help) usage; exit 0;; plan|run) ;; *) fail 'operação deve ser plan ou run';; esac
ROOT=$CODE SURFACES=() CHECKS=()
while (( $# )); do
    case $1 in
        --root|--surface|--check)
            [[ $# -ge 2 && -n $2 && $2 != --* ]] || fail "valor ausente: $1"
            case $1 in --root) ROOT=$2;; --surface) SURFACES+=("$2");; --check) CHECKS+=("$2");; esac
            shift 2;;
        -h|--help) usage; exit 0;;
        *) fail "argumento desconhecido: $1";;
    esac
done
need jq
ROOT=$(cd -- "$ROOT" && pwd -P) || fail 'raiz inexistente'
for check in "${CHECKS[@]}"; do
    case $check in secrets|secrets-rules|sast|sast-rules|dependencies|machete|deny|geiger|audit|coverage|lint|build-budget|performance|dev|setup|trivy|grype|bandit|syft|yara-x|codeql|ruff|shellcheck|playwright) ;;
        *) fail "check desconhecido: $check";; esac
done
if (( ${#SURFACES[@]} == 0 )); then
    if (( ${#CHECKS[@]} == 0 )); then SURFACES=(docs scripts server agent frontend security integration e2e)
    else
        for check in "${CHECKS[@]}"; do
            case $check in secrets|secrets-rules|sast|sast-rules|dependencies|trivy|grype|bandit|syft|yara-x|codeql) SURFACES+=(security);;
                machete|deny|geiger|audit) SURFACES+=(server agent);;
                ruff|shellcheck) SURFACES+=(scripts);;
                playwright) SURFACES+=(e2e);;
                coverage) fail 'coverage requer --surface server, agent ou frontend';;
                *) SURFACES+=(frontend);; esac
        done
    fi
fi
for surface in "${SURFACES[@]}"; do
    case $surface in docs|scripts|server|agent|frontend|security|integration|e2e) ;; *) fail "superfície desconhecida: $surface";; esac
done
source "$CODE/scripts/stacks/ec/catalog.sh"
source "$CODE/scripts/stacks/ec/inputs.sh"
source "$CODE/scripts/stacks/ec/runtime.sh"
# Deduplicar sem ordenar: o build de failpoints deve suceder cargo test.
tasks=$(make_catalog | jq -s 'reduce .[] as $task ([]; if any(.[]; .project==$task.project and .name==$task.name) then . else .+[$task] end)')
if (( ${#CHECKS[@]} )); then
    checks_json=$(printf '%s\n' "${CHECKS[@]}" | jq -R . | jq -s .)
    tasks=$(jq --argjson checks "$checks_json" '[.[] | select(.project=="docs" or (.name as $name | $checks|index($name)) or (.project=="frontend" and .name=="build" and ($checks|index("playwright"))) or (.name=="secrets-rules" and ($checks|index("secrets"))) or (.name=="sast-rules" and ($checks|index("sast"))))]' <<< "$tasks")
    for check in "${CHECKS[@]}"; do
        jq -e --arg check "$check" 'any(.[]; .project!="docs" and .name==$check)' <<< "$tasks" >/dev/null || fail "check $check incompatível com superfícies selecionadas"
    done
fi
surfaces_json=$(printf '%s\n' "${SURFACES[@]}" | jq -R . | jq -s 'unique')
PLAN=$(jq -n --arg root "$ROOT" --argjson surfaces "$surfaces_json" --argjson tasks "$tasks" '{root:$root,surfaces:$surfaces,tasks:$tasks,executed:false}')
if [[ $operation == plan ]]; then printf '%s\n' "$PLAN"; exit 0; fi
[[ $(uname -s) == Linux ]] || fail 'supervisão requer Linux'
need flock; need setsid; need timeout; need sha256sum
run_plan
