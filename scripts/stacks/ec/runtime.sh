#!/usr/bin/env bash
# Supervisor Linux: grupo de processos, deadline, logs brutos e lock no pai.
execute_task() {
    local pid rc=0 cause='' size stdout_reader stderr_reader
    touch "$TASK_DIR/stdout.log" "$TASK_DIR/stderr.log"
    mkfifo "$TASK_DIR/stdout.pipe" "$TASK_DIR/stderr.pipe"
    # Limitar apenas os logs; RLIMIT_FSIZE no workload alteraria testes legítimos de disco.
    head -c 8388609 9>&- < "$TASK_DIR/stdout.pipe" > "$TASK_DIR/stdout.log" &
    stdout_reader=$!
    head -c 8388609 9>&- < "$TASK_DIR/stderr.pipe" > "$TASK_DIR/stderr.log" &
    stderr_reader=$!
    # Bash lança um grupo separado; fecha fd do lock no filho.
    setsid timeout --signal=TERM --kill-after=120 "$TASK_TIMEOUT" \
        "${TASK_ARGV[@]}" \
        9>&- > "$TASK_DIR/stdout.pipe" 2> "$TASK_DIR/stderr.pipe" &
    pid=$!
    ACTIVE_PID=$pid
    while kill -0 "$pid" 2>/dev/null; do
        size=$(( $(stat -c %s "$TASK_DIR/stdout.log" 9>&-) + $(stat -c %s "$TASK_DIR/stderr.log" 9>&-) ))
        if (( size > 8388608 )); then cause='log byte limit exceeded'; break; fi
        if (( CANCELLED )); then cause=cancelled; break; fi
        sleep 0.1 9>&-
    done
    if [[ -n $cause ]]; then
        kill -TERM -- "-$pid" 2>/dev/null || :
        # O executor precisa inspecionar e remover seu container antes de SIGKILL.
        local cleanup_deadline=$((SECONDS+120))
        while kill -0 "$pid" 2>/dev/null && (( SECONDS < cleanup_deadline )); do sleep 0.1 9>&-; done
        kill -KILL -- "-$pid" 2>/dev/null || :
    fi
    wait "$pid" || rc=$?
    kill -TERM -- "-$pid" 2>/dev/null || :
    sleep 0.1
    kill -KILL -- "-$pid" 2>/dev/null || :
    wait "$stdout_reader" || :
    wait "$stderr_reader" || :
    rm -- "$TASK_DIR/stdout.pipe" "$TASK_DIR/stderr.pipe"
    ACTIVE_PID=''
    if (( rc == 124 )); then cause=timeout; fi
    size=$(( $(stat -c %s "$TASK_DIR/stdout.log") + $(stat -c %s "$TASK_DIR/stderr.log") ))
    if (( size > 8388608 )); then cause='log byte limit exceeded'; fi
    TASK_RC=$rc; TASK_CAUSE=$cause
}
cancel_run() {
    CANCELLED=1
    if [[ -n ${ACTIVE_PID:-} ]]; then kill -TERM -- "-$ACTIVE_PID" 2>/dev/null || :; fi
}
run_plan() {
    : "${ROOT:?raiz necessária}" "${PLAN:?plano necessário}"
    local parent lock index=0 task project name cwd depends state reason raw row code
    : "${CODE:?raiz do código necessária}"
    parent=$(inside "$ROOT" .local/automation/runs)
    mkdir -p "$parent"
    lock=$(inside "$ROOT" .local/automation/workspace.lock)
    [[ ! -L $lock ]] || fail 'lock symlink'
    exec 9> "$lock"
    flock -n 9 || blocked 'outra verificação usa este checkout'
    RUN_DIR=$(mktemp -d "$parent/run-XXXXXXXX")
    chmod 700 "$RUN_DIR"
    observe_inputs all "$RUN_DIR/inputs.json"
    printf '[]\n' > "$RUN_DIR/rows.json"
    jq --arg digest "$(hash "$RUN_DIR/inputs.json")" '.tasks |= map(del(.argv)) | . + {schema:1,executed:true,results:[],inputs_sha256:$digest,complete:false}' <<< "$PLAN" | atomic_json "$RUN_DIR/results.json"
    printf 'Resultados: %s\n' "$RUN_DIR"
    CANCELLED=0 ACTIVE_PID=''
    trap cancel_run INT TERM HUP
    while IFS= read -r task; do
        project=$(jq -r .project <<< "$task"); name=$(jq -r .name <<< "$task"); cwd=$(jq -r .cwd <<< "$task")
        TASK_DIR=$(printf '%s/%02d-%s-%s' "$RUN_DIR" "$index" "$project" "$name")
        mkdir -m 700 "$TASK_DIR"
        state=error reason='' raw=null
        depends=$(jq -c .depends <<< "$task")
        if (( CANCELLED )); then state=blocked; reason='cancelado antes da tarefa'
        elif ! jq -e --argjson deps "$depends" '([.[]|select(.state=="passed")|.project+":"+.task]) as $done | ($deps-$done|length)==0' "$RUN_DIR/rows.json" >/dev/null; then
            state=blocked; reason='dependências não aprovadas'
        else
            TASK_ARGV=()
            while IFS= read -r -d '' arg; do TASK_ARGV+=("$arg"); done < <(jq -j '.argv[] | ., "\u0000"' <<< "$task")
            TASK_TIMEOUT=$(jq -r .timeout <<< "$task")
            local work
            work=$(inside "$ROOT" "$cwd")
            local -a clean_env=(env -i "PATH=$PATH:${EC_CARGO_BIN:-${CARGO_HOME:-$HOME/.cargo}/bin}:$HOME/.cargo/bin" "HOME=$HOME" "LANG=C.UTF-8" "LC_ALL=C.UTF-8" "NO_COLOR=1" "PYTHONDONTWRITEBYTECODE=1" "RUSTUP_AUTO_INSTALL=0" "CARGO_NET_OFFLINE=true" "CARGO_HOME=${CARGO_HOME:-$ROOT/.local/build/ec-cargo-home}" "TMPDIR=$TASK_DIR" "EC_ROOT=$ROOT" "EC_TASK_DIR=$TASK_DIR" "EC_E2E_ARTIFACT_DIR=$TASK_DIR/artifacts" "npm_config_cache=$TASK_DIR/npm-cache" "npm_config_offline=true" "npm_config_update_notifier=false")
            [[ ! ${TOOLBOX_BIN+x} ]] || clean_env+=("TOOLBOX_BIN=$TOOLBOX_BIN")
            clean_env+=("EC_TASK_TIMEOUT=$TASK_TIMEOUT")
            local selected
            # Seleções do operador alcançam a CLI; não passam implicitamente ao container.
            for selected in TOOLBOX_STATE TOOLBOX_SELINUX_LABEL DOCKER_HOST DOCKER_CONTEXT DOCKER_CONFIG DOCKER_TLS_VERIFY DOCKER_CERT_PATH EC_CODEQL_RUSTUP_HOME EC_CODEQL_CARGO_HOME EC_CODEQL_CARGO_BIN EC_GEIGER_BUILD_CARGO_HOME; do
                [[ ! ${!selected+x} ]] || clean_env+=("$selected=${!selected}")
            done
            [[ -z ${EC_RUSTSEC_DB:-} ]] || clean_env+=("EC_RUSTSEC_DB=$EC_RUSTSEC_DB")
            [[ -z ${RUSTUP_HOME:-} ]] || clean_env+=("RUSTUP_HOME=$RUSTUP_HOME")
            case $project in server) clean_env+=("CARGO_TARGET_DIR=$ROOT/.local/build/ec-server-clean");; agent) clean_env+=("CARGO_TARGET_DIR=$ROOT/.local/build/ec-agent-target");; esac
            if [[ ! -d $work ]]; then state=blocked; reason="diretório ausente: $cwd"
            elif ! command -v "${TASK_ARGV[0]}" >/dev/null; then state=blocked; reason="ferramenta ausente: ${TASK_ARGV[0]}"
            else
                # preflight de integração no mesmo ambiente restrito, sem efeitos de setup.
                if [[ $project == integration || ( $project == e2e && $name != playwright ) ]]; then TASK_ARGV=(bash "$CODE/scripts/stacks/ec/integration.sh" "$project" "${TASK_ARGV[@]}"); fi
                TASK_ARGV=("${clean_env[@]}" "${TASK_ARGV[@]}")
                pushd "$work" >/dev/null || fail 'diretório de trabalho indisponível'
                execute_task
                popd >/dev/null || fail 'não foi possível restaurar cwd'
                raw=$TASK_RC
                if [[ -n $TASK_CAUSE ]]; then state=error; reason=$TASK_CAUSE
                elif [[ -f $TASK_DIR/classification.json && ( $project == security || $name == ruff || $name == shellcheck || $name == playwright ) ]]; then
                    if ! (json_file "$TASK_DIR/classification.json"); then
                        printf '{"state":"error","reason":"classificação inválida"}\n' > "$TASK_DIR/classification.json"
                    fi
                    state=$(jq -r .state "$TASK_DIR/classification.json")
                    reason=$(jq -r '.reason // ""' "$TASK_DIR/classification.json")
                    case $state:$TASK_RC in passed:0|measured:0|findings:1|error:2|blocked:3) ;; *) state=error; reason='classificação e exit inconsistentes';; esac
                elif (( TASK_RC == 0 )); then state=passed
                elif (( TASK_RC == 3 || TASK_RC == 127 )); then state=blocked; reason='pré-requisito ausente; consultar stderr.log'
                else state=error; reason='comando falhou; consultar stderr.log/stdout.log'; fi
            fi
        fi
        row=$(jq -nc --arg project "$project" --arg task "$name" --arg state "$state" --arg reason "$reason" --argjson rc "$raw" --argjson plan "$task" '{project:$project,task:$task,state:$state,reason:$reason,returncode:$rc,cwd:$plan.cwd}')
        if [[ -f $TASK_DIR/tool.json ]]; then
            json_file "$TASK_DIR/tool.json"
            row=$(jq --slurpfile tool "$TASK_DIR/tool.json" '.+{tool:$tool[0]}' <<< "$row")
        fi
        if [[ $name == coverage && -s $TASK_DIR/coverage.json ]]; then
            json_file "$TASK_DIR/coverage.json"
            row=$(jq --slurpfile coverage "$TASK_DIR/coverage.json" '.+{coverage:$coverage[0]}' <<< "$row")
        fi
        if [[ -s $TASK_DIR/classification.json && ( $project == security || $name == ruff || $name == shellcheck || $name == playwright ) ]] && jq -e 'type=="object"' "$TASK_DIR/classification.json" >/dev/null 2>&1; then
            row=$(jq --slurpfile result "$TASK_DIR/classification.json" '.+($result[0]|{finding_count,artifact_count,measurement,native_executions})' <<< "$row")
        fi
        printf '%s\n' "$row" | atomic_json "$TASK_DIR/result.json"
        jq --argjson row "$row" '.+[$row]' "$RUN_DIR/rows.json" | atomic_json "$RUN_DIR/rows.json"
        code=$(aggregate < "$RUN_DIR/rows.json")
        jq --slurpfile rows "$RUN_DIR/rows.json" --argjson code "$code" '.results=$rows[0] | .exit_code=$code' "$RUN_DIR/results.json" | atomic_json "$RUN_DIR/results.json"
        printf '%s:%s: %s (exit=%s) %s\n' "$project" "$name" "$state" "$raw" "$reason"
        index=$((index+1))
    done < <(jq -c '.tasks[]' <<< "$PLAN")
    observe_inputs all "$RUN_DIR/inputs-after.json"
    local changes
    changes=$(jq -n --slurpfile before "$RUN_DIR/inputs.json" --slurpfile after "$RUN_DIR/inputs-after.json" '($before[0]+$after[0]|keys) | map(select($before[0][.] != $after[0][.]))')
    if [[ $changes != '[]' ]]; then
        jq '.+[{project:"scripts",task:"input-stability",state:"error",returncode:null,reason:"inputs alterados durante execução"}]' "$RUN_DIR/rows.json" | atomic_json "$RUN_DIR/rows.json"
    fi
    if (( CANCELLED )); then
        jq '.+[{project:"scripts",task:"cancellation",state:"error",returncode:null,reason:"run cancelado"}]' "$RUN_DIR/rows.json" | atomic_json "$RUN_DIR/rows.json"
    fi
    code=$(aggregate < "$RUN_DIR/rows.json")
    jq --slurpfile rows "$RUN_DIR/rows.json" --argjson code "$code" --argjson changed "$changes" --argjson cancelled "$CANCELLED" '.results=$rows[0] | .exit_code=$code | .changed_inputs=$changed | .cancelled=($cancelled==1) | .complete=($code==0 and $cancelled==0)' "$RUN_DIR/results.json" | atomic_json "$RUN_DIR/results.json"
    # Também honrar sinal recebido durante a publicação do resultado final.
    if (( CANCELLED )); then
        code=2
        jq '.exit_code=2 | .complete=false | .cancelled=true' "$RUN_DIR/results.json" | atomic_json "$RUN_DIR/results.json"
    fi
    trap - INT TERM HUP
    return "$code"
}
