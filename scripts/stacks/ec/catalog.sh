#!/usr/bin/env bash
# Única definição de tarefas, diretórios, argumentos e dependências do runner.
add_task() {
    local project=$1 name=$2 cwd=$3 depends=$4 task_timeout=900; shift 4
    [[ $name != codeql ]] || task_timeout=14400
    jq -nc --arg project "$project" --arg name "$name" --arg cwd "$cwd" --arg depends "$depends" \
        --argjson timeout "$task_timeout" --args '{project:$project,name:$name,cwd:$cwd,depends:($depends|split(",")|map(select(length>0))),argv:$ARGS.positional,timeout:$timeout}' -- "$@"
}
# SURFACES é array preenchido por automate.sh; não inicializar aqui e apagar seleção.
# shellcheck disable=SC2154
has_surface() { [[ " ${SURFACES[*]} " == *" $1 "* ]]; }
make_catalog() {
    : "${CODE:?raiz do código necessária}"
    local project cwd check
    for check in governance installed skills sdd governance-tests; do
        case $check in
            governance) set -- tools/governance/validate.py;;
            installed) set -- tools/governance/validate.py --require-installed;;
            skills) set -- tools/governance/install_skills.py --check;;
            sdd) set -- sdd/tools/validate.py --root .;;
            governance-tests) set -- -m unittest discover -s tools/governance/tests -v;;
        esac
        add_task docs "$check" . '' python3 "$@"
    done
    if has_surface scripts; then
        add_task scripts syntax . '' bash "$CODE/scripts/check.sh"
        for check in shellcheck ruff; do
            add_task scripts "$check" . '' bash "$CODE/scripts/stacks/ec/scanner-gate.sh" "$check"
        done
        add_task scripts tests . '' python3 -m unittest discover -s scripts/tests -v
    fi
    for project in server agent; do
        if ! has_surface "$project" && ! has_surface integration && ! has_surface e2e; then continue; fi
        cwd=application/agents
        [[ $project != server ]] || cwd=application/server/backend
        for check in fmt clippy test machete deny audit geiger coverage; do add_task "$project" "$check" "$cwd" '' bash "$CODE/scripts/stacks/rust/check.sh" "$check"; done
        if has_surface integration || has_surface e2e; then
            add_task "$project" harness-build "$cwd" '' bash "$CODE/scripts/stacks/rust/check.sh" harness-build
        fi
    done
    if has_surface frontend || has_surface e2e; then
        for check in typecheck test build coverage; do add_task frontend "$check" application/server/frontend '' bash "$CODE/scripts/stacks/frontend/check.sh" "$check"; done
        # CHECKS é array preenchido pelo parser da entrada automate.sh.
        # shellcheck disable=SC2154
        for check in "${CHECKS[@]}"; do
            case $check in lint|build-budget|performance|dev|setup) add_task frontend "$check" application/server/frontend '' bash "$CODE/scripts/stacks/frontend/check.sh" "$check";; esac
        done
    fi
    if has_surface security; then
        add_task security secrets-rules . '' bash "$CODE/scripts/stacks/ec/scanner-gate.sh" secrets-rules
        for check in secrets sast-rules sast dependencies trivy codeql; do
            local dependencies=''
            [[ $check != secrets ]] || dependencies=security:secrets-rules
            [[ $check != sast ]] || dependencies=security:sast-rules
            add_task security "$check" . "$dependencies" bash "$CODE/scripts/stacks/ec/scanner-gate.sh" "$check"
        done
    fi
    if has_surface security; then
        for check in "${CHECKS[@]}"; do
            case $check in
                grype|bandit|syft|yara-x) add_task security "$check" . '' bash "$CODE/scripts/stacks/ec/scanner-gate.sh" "$check";;
            esac
        done
    fi
    if has_surface integration; then
        for check in publication_http orchestration_http agent_http; do
            add_task integration "$check" . server:harness-build,agent:harness-build python3 "application/integration/tests/integration/$check.py"
        done
    fi
    if has_surface e2e; then
        add_task e2e playwright . frontend:build bash "$CODE/scripts/stacks/ec/scanner-gate.sh" playwright
        add_task e2e firefox . server:harness-build,agent:harness-build,frontend:build python3 application/integration/tests/e2e/frontend_firefox.py
    fi
}
