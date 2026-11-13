#!/usr/bin/env bash
# Supervisor Linux: grupo de processos, deadline, logs brutos e lock no pai.
set -o pipefail
: "${CODE:?raiz do código necessária}"
source "${CODE}/scripts/stacks/ec/environment.sh"
execute_task() {
    local pid rc=0 cause='' size stdout_reader stderr_reader
    touch "${TASK_STDOUT}" "${TASK_STDERR}"
    mkfifo "${TASK_DIR}/stdout.pipe" "${TASK_DIR}/stderr.pipe"
    # Limitar apenas os logs; RLIMIT_FSIZE no workload alteraria testes legítimos de disco.
    head -c "$((LOG_BYTES+1))" 9>&- < "${TASK_DIR}/stdout.pipe" > "${TASK_STDOUT}" &
    stdout_reader=$!
    head -c "$((LOG_BYTES+1))" 9>&- < "${TASK_DIR}/stderr.pipe" > "${TASK_STDERR}" &
    stderr_reader=$!
    # Bash lança um grupo separado; fecha fd do lock no filho.
    setsid timeout --signal=TERM --kill-after="${CLEANUP_TIMEOUT}" "${TASK_TIMEOUT}" \
        "${TASK_ARGV[@]}" \
        9>&- > "${TASK_DIR}/stdout.pipe" 2> "${TASK_DIR}/stderr.pipe" &
    pid=$!
    ACTIVE_PID=${pid}
    while kill -0 "${pid}" 2>/dev/null; do
        size=$(( $(stat -c %s "${TASK_STDOUT}" 9>&-) + $(stat -c %s "${TASK_STDERR}" 9>&-) ))
        if (( size > LOG_BYTES )); then cause='log byte limit exceeded'; break; fi
        if (( CANCELLED )); then cause=cancelled; break; fi
        sleep 0.1 9>&-
    done
    if [[ -n ${cause} ]]; then
        kill -TERM -- "-${pid}" 2>/dev/null || :
        # O executor precisa inspecionar e remover seu container antes de SIGKILL.
        local cleanup_deadline=$((SECONDS+CLEANUP_TIMEOUT))
        while kill -0 "${pid}" 2>/dev/null && (( SECONDS < cleanup_deadline )); do sleep 0.1 9>&-; done
        kill -KILL -- "-${pid}" 2>/dev/null || :
    fi
    wait "${pid}" || rc=$?
    kill -TERM -- "-${pid}" 2>/dev/null || :
    sleep 0.1
    kill -KILL -- "-${pid}" 2>/dev/null || :
    wait "${stdout_reader}" || :
    wait "${stderr_reader}" || :
    rm -- "${TASK_DIR}/stdout.pipe" "${TASK_DIR}/stderr.pipe"
    ACTIVE_PID=''
    if (( rc == 124 )); then cause=timeout; fi
    size=$(( $(stat -c %s "${TASK_STDOUT}") + $(stat -c %s "${TASK_STDERR}") ))
    if (( size > LOG_BYTES )); then cause='log byte limit exceeded'; fi
    TASK_RC=${rc}; TASK_CAUSE=${cause}
}
cancel_run() {
    CANCELLED=1
    if [[ -n ${ACTIVE_PID:-} ]]; then kill -TERM -- "-${ACTIVE_PID}" 2>/dev/null || :; fi
}
run_plan() {
    umask 077
    : "${ROOT:?raiz necessária}" "${PLAN:?plano necessário}"
    local parent lock index=0 task project name cwd depends state reason raw row code
    : "${CODE:?raiz do código necessária}"
    RUN_ENV=()
    local maximum_json classification_size
    maximum_json=$(configuration runtime.json_bytes)
    LOG_BYTES=$(configuration runtime.log_bytes)
    CLEANUP_TIMEOUT=$(configuration runtime.cleanup_timeout)
    prepare_run_environment
    parent=$(python3 "${CODE}/scripts/local-state.py" runtime-path --root "${ROOT}")
    lock=${parent}/workspace.lock
    [[ ! -L ${lock} ]] || fail 'lock symlink'
    exec 9> "${lock}"
    flock -n 9 || blocked 'outra verificação usa este checkout'
    LOG_DIR=$(python3 "${CODE}/scripts/local-state.py" prepare --root "${ROOT}" --lock-fd 9)
    RUN_DIR=$(mktemp -d "${parent}/run-XXXXXXXX")
    chmod 700 "${RUN_DIR}"
    trap 'finish_run "$?"' EXIT
    observe_inputs all "${RUN_DIR}/inputs.json"
    printf '[]\n' > "${RUN_DIR}/rows.json"
    local input_digest server_target agent_target finished
    input_digest=$(hash "${RUN_DIR}/inputs.json")
    server_target=$(local_path server)
    agent_target=$(local_path agent)
    jq --arg digest "${input_digest}" '.tasks |= map(del(.argv)) | . + {schema:1,executed:true,results:[],inputs_sha256:$digest,complete:false}' <<< "${PLAN}" | atomic_json "${RUN_DIR}/results.json"
    printf 'Logs: %s/%s-*\n' "${LOG_DIR}" "${RUN_DIR##*/}"
    CANCELLED=0 ACTIVE_PID=''
    trap cancel_run INT TERM HUP
    jq -c '.tasks[]' <<< "${PLAN}" > "${RUN_DIR}/tasks.list"
    while IFS= read -r task; do
        project=$(jq -r .project <<< "${task}"); name=$(jq -r .name <<< "${task}"); cwd=$(jq -r .cwd <<< "${task}")
        TASK_DIR=$(printf '%s/%02d-%s-%s' "${RUN_DIR}" "${index}" "${project}" "${name}")
        mkdir -m 700 "${TASK_DIR}"
        TASK_STDOUT=${LOG_DIR}/${RUN_DIR##*/}-${TASK_DIR##*/}.stdout.log
        TASK_STDERR=${LOG_DIR}/${RUN_DIR##*/}-${TASK_DIR##*/}.stderr.log
        state=error reason='' raw=null
        depends=$(jq -c .depends <<< "${task}")
        if (( CANCELLED )); then state=blocked; reason='cancelado antes da tarefa'
        elif ! jq -e --argjson deps "${depends}" '([.[]|select(.state=="passed")|.project+":"+.task]) as $done | ($deps-$done|length)==0' "${RUN_DIR}/rows.json" >/dev/null; then
            state=blocked; reason='dependências não aprovadas'
        else
            TASK_ARGV=()
            jq -j '.argv[] | ., "\u0000"' <<< "${task}" > "${TASK_DIR}/argv.list"
            while IFS= read -r -d '' arg; do TASK_ARGV+=("${arg}"); done < "${TASK_DIR}/argv.list"
            rm -- "${TASK_DIR}/argv.list"
            TASK_TIMEOUT=$(jq -r .timeout <<< "${task}")
            local work
            work=$(inside "${ROOT}" "${cwd}")
            local -a clean_env=(env -i "${RUN_ENV[@]}" "TMPDIR=${TASK_DIR}" "EC_ROOT=${ROOT}" "EC_FRONTEND_BUILD_DIR=${RUN_DIR}/frontend-dist" "EC_COVERAGE_SERVER_BIN=${server_target}/debug/ec-server-app" "EC_COVERAGE_AGENT_BIN=${agent_target}/debug/ec-agent-app" "EC_TASK_DIR=${TASK_DIR}" "EC_E2E_ARTIFACT_DIR=${TASK_DIR}/artifacts" "npm_config_cache=${TASK_DIR}/npm-cache")
            clean_env+=("EC_TASK_TIMEOUT=${TASK_TIMEOUT}" "EC_RUN_DIR=${RUN_DIR}")
            case ${project} in server) clean_env+=("CARGO_TARGET_DIR=${server_target}");; agent) clean_env+=("CARGO_TARGET_DIR=${agent_target}");; *) :;; esac
            if [[ ! -d ${work} ]]; then state=blocked; reason="diretório ausente: ${cwd}"
            elif ! command -v "${TASK_ARGV[0]}" >/dev/null; then state=blocked; reason="ferramenta ausente: ${TASK_ARGV[0]}"
            else
                # preflight de integração no mesmo ambiente restrito, sem efeitos de setup.
                if [[ ${project} == integration || ( ${project} == e2e && ${name} != playwright ) ]]; then TASK_ARGV=(bash "${CODE}/scripts/stacks/ec/integration.sh" "${project}" "${TASK_ARGV[@]}"); fi
                TASK_ARGV=("${clean_env[@]}" "${TASK_ARGV[@]}")
                pushd "${work}" >/dev/null || fail 'diretório de trabalho indisponível'
                execute_task
                popd >/dev/null || fail 'não foi possível restaurar cwd'
                raw=${TASK_RC}
                if [[ -n ${TASK_CAUSE} ]]; then state=error; reason=${TASK_CAUSE}
                elif [[ -f ${TASK_DIR}/classification.json && ( ${project} == security || ${name} == ruff || ${name} == shellcheck || ${name} == playwright ) ]]; then
                    classification_size=$(stat -c %s "${TASK_DIR}/classification.json")
                    if [[ -L ${TASK_DIR}/classification.json ]] || (( classification_size > maximum_json )) || ! jq -e -s 'length == 1' "${TASK_DIR}/classification.json" >/dev/null; then
                        printf '{"state":"error","reason":"classificação inválida"}\n' > "${TASK_DIR}/classification.json"
                    fi
                    state=$(jq -r .state "${TASK_DIR}/classification.json")
                    reason=$(jq -r '.reason // ""' "${TASK_DIR}/classification.json")
                    case ${state}:${TASK_RC} in passed:0|measured:0|findings:1|error:2|blocked:3) ;; *) state=error; reason='classificação e exit inconsistentes';; esac
                elif (( TASK_RC == 0 )); then state=passed
                elif (( TASK_RC == 3 || TASK_RC == 127 )); then state=blocked; reason='pré-requisito ausente; consultar stderr.log'
                else state=error; reason='comando falhou; consultar stderr.log/stdout.log'; fi
            fi
        fi
        row=$(jq -nc --arg project "${project}" --arg task "${name}" --arg state "${state}" --arg reason "${reason}" --argjson rc "${raw}" --argjson plan "${task}" '{project:$project,task:$task,state:$state,reason:$reason,returncode:$rc,cwd:$plan.cwd}')
        if [[ -f ${TASK_DIR}/tool.json ]]; then
            json_file "${TASK_DIR}/tool.json"
            row=$(jq --slurpfile tool "${TASK_DIR}/tool.json" '.+{tool:$tool[0]}' <<< "${row}")
        fi
        if [[ ${name} == coverage && -s ${TASK_DIR}/coverage.json ]]; then
            json_file "${TASK_DIR}/coverage.json"
            row=$(jq --slurpfile coverage "${TASK_DIR}/coverage.json" '.+{coverage:$coverage[0]}' <<< "${row}")
        fi
        if [[ -s ${TASK_DIR}/classification.json && ( ${project} == security || ${name} == ruff || ${name} == shellcheck || ${name} == playwright ) ]] && jq -e 'type=="object"' "${TASK_DIR}/classification.json" >/dev/null 2>&1; then
            row=$(jq --slurpfile result "${TASK_DIR}/classification.json" '.+($result[0]|{finding_count,native_finding_count,native_suppressed_count,reviewed_count,artifact_count,measurement,tree_finding_count,history_finding_count,image_count,image_results,license_count,license_measurement,native_executions})' <<< "${row}")
        fi
        printf '%s\n' "${row}" | atomic_json "${TASK_DIR}/result.json"
        jq --slurpfile row "${TASK_DIR}/result.json" '.+[$row[0]]' "${RUN_DIR}/rows.json" | atomic_json "${RUN_DIR}/rows.json"
        code=$(aggregate < "${RUN_DIR}/rows.json")
        jq --slurpfile rows "${RUN_DIR}/rows.json" --argjson code "${code}" '.results=$rows[0] | .exit_code=$code' "${RUN_DIR}/results.json" | atomic_json "${RUN_DIR}/results.json"
        printf '%s:%s: %s (exit=%s) %s\n' "${project}" "${name}" "${state}" "${raw}" "${reason}"
        index=$((index+1))
    done < "${RUN_DIR}/tasks.list"
    observe_inputs all "${RUN_DIR}/inputs-after.json"
    local changes lock_updates unexpected cargo_locks
    cargo_locks=$(configuration components)
    cargo_locks=$(jq '[.[]|select(.language=="rust")|.lockfile]' <<< "${cargo_locks}")
    changes=$(jq -n --slurpfile before "${RUN_DIR}/inputs.json" --slurpfile after "${RUN_DIR}/inputs-after.json" '($before[0]+$after[0]|keys) | map(select($before[0][.] != $after[0][.]))')
    # Locks podem ser atualizados pelo Cargo em desenvolvimento. Conservar os
    # dois hashes, sem atribuir a origem da escrita nem aprovar versões mistas.
    lock_updates=$(jq -n --argjson changed "${changes}" --argjson locks "${cargo_locks}" --slurpfile before "${RUN_DIR}/inputs.json" --slurpfile after "${RUN_DIR}/inputs-after.json" '
        [$changed[] | select(. as $path | $locks | index($path)) |
         {path:.,before:$before[0][.],after:$after[0][.]} | select(.before != null and .after != null)]')
    unexpected=$(jq -n --argjson changed "${changes}" --argjson locks "${lock_updates}" '$changed - [$locks[].path]')
    if [[ ${unexpected} != '[]' ]]; then
        jq '.+[{project:"scripts",task:"input-stability",state:"error",returncode:null,reason:"inputs alterados durante execução"}]' "${RUN_DIR}/rows.json" | atomic_json "${RUN_DIR}/rows.json"
    fi
    if [[ ${lock_updates} != '[]' ]]; then
        reason='Cargo.lock atualizado; reexecutar a suíte para verificar a resolução final'
        jq --arg reason "${reason}" '.+[{project:"scripts",task:"dependency-resolution",state:"error",returncode:null,reason:$reason}]' "${RUN_DIR}/rows.json" | atomic_json "${RUN_DIR}/rows.json"
        printf 'scripts:dependency-resolution: error (exit=null) %s\n' "${reason}"
    fi
    if (( CANCELLED )); then
        jq '.+[{project:"scripts",task:"cancellation",state:"error",returncode:null,reason:"run cancelado"}]' "${RUN_DIR}/rows.json" | atomic_json "${RUN_DIR}/rows.json"
    fi
    code=$(aggregate < "${RUN_DIR}/rows.json")
    jq --slurpfile rows "${RUN_DIR}/rows.json" --argjson code "${code}" --argjson changed "${changes}" --argjson locks "${lock_updates}" --argjson cancelled "${CANCELLED}" '.results=$rows[0] | .exit_code=$code | .changed_inputs=$changed | .updated_lockfiles=$locks | .cancelled=($cancelled==1) | .complete=($code==0 and $cancelled==0)' "${RUN_DIR}/results.json" | atomic_json "${RUN_DIR}/results.json"
    # Também honrar sinal recebido durante a publicação do resultado final.
    if (( CANCELLED )); then
        code=2
        jq '.exit_code=2 | .complete=false | .cancelled=true' "${RUN_DIR}/results.json" | atomic_json "${RUN_DIR}/results.json"
    fi
    # Encerramento não significa aprovação: também registrar runs com achados/erros.
    finished=$(date +%s)
    jq --argjson finished "${finished}" '.finished_at=$finished' "${RUN_DIR}/results.json" | atomic_json "${RUN_DIR}/results.json"
    # Honrar também sinais recebidos durante a publicação dos metadados.
    if (( CANCELLED )); then
        code=2
        jq '.exit_code=2 | .complete=false | .cancelled=true' "${RUN_DIR}/results.json" | atomic_json "${RUN_DIR}/results.json"
    fi
    trap - INT TERM HUP
    return "${code}"
}

# EXIT também cobre erros de preparação/classificação; conservar o exit original.
finish_run() {
    local code=$1
    trap - EXIT
    if ! timeout --signal=TERM --kill-after="${CLEANUP_TIMEOUT}" "${CLEANUP_TIMEOUT}" \
        env -i "${RUN_ENV[@]}" EC_RUN_DIR="${RUN_DIR}" python3 "${CODE}/tools/quality/prepare_automation.py" cleanup; then
        printf 'error: limpeza de serviços de teste incompleta\n' >&2
        code=2
        if [[ -f ${RUN_DIR}/results.json ]]; then
            jq '.complete=false | .exit_code=2 | .service_cleanup="failed"' "${RUN_DIR}/results.json" | atomic_json "${RUN_DIR}/results.json"
        fi
    fi
    if ! python3 "${CODE}/scripts/local-state.py" finish --root "${ROOT}" --lock-fd 9 \
        --workspace "${RUN_DIR}" --log-dir "${LOG_DIR}" --exit-code "${code}"; then
        printf 'error: exportação/limpeza incompleta em %s\n' "${RUN_DIR}" >&2
        code=2
    fi
    exit "${code}"
}
