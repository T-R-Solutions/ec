#!/usr/bin/env bash
# Projeção humana adaptada de Ox/scripts/lib/progress.sh. Nunca lê saída bruta.
# shellcheck disable=SC2034,SC2154 # Estado compartilhado com runtime.sh.
progress_safe() { jq -aRn --arg value "$1" '$value'; }
progress_duration() { printf '%02d:%02d' "$(($1/60))" "$(($1%60))"; }
progress_label() {
    local label=$1 color=''
    case ${label} in SUCESSO) color=32;; FALHA) color=31;; BLOQUEADA|CANCELADA) color=33;; *) :;; esac
    if ${PROGRESS_COLOR} && [[ -n ${color} ]]; then printf '\033[%sm%s\033[0m' "${color}" "${label}"
    else printf '%s' "${label}"; fi
}
progress_name() {
    case $1 in
        preparation) printf 'Preparação e inventário de entradas';;
        finalization) printf 'Finalização e publicação dos logs';;
        *:fmt) printf 'Formatação';; *:check) printf 'Verificação de compilação';;
        *:test) printf 'Testes';; *:build) printf 'Compilação';;
        *:clippy) printf 'Clippy';; *:coverage) printf 'Cobertura';;
        docs:sdd) printf 'Estrutura SDD';; docs:governance) printf 'Governança Ec';;
        scripts:syntax) printf 'Sintaxe dos scripts';; scripts:tests) printf 'Testes dos scripts';;
        *) progress_safe "$1";;
    esac
}
progress_init() {
    local settings
    settings=$(configuration presentation)
    PROGRESS_ENABLED=$(jq -r .enabled <<<"${settings}")
    PROGRESS_REFRESH=$(jq -r .refresh_seconds <<<"${settings}")
    PROGRESS_HEARTBEAT=$(jq -r .heartbeat_seconds <<<"${settings}")
    PROGRESS_TOTAL=$(($1+2)) PROGRESS_DONE=0 PROGRESS_ACTIVE=false PROGRESS_RECORDED=false
    PROGRESS_TTY=false PROGRESS_CONTROL=false PROGRESS_COLOR=false
    [[ ! -t 2 ]] || PROGRESS_TTY=true
    if ${PROGRESS_TTY} && [[ ${TERM:-} != dumb ]]; then PROGRESS_CONTROL=true; fi
    if ${PROGRESS_CONTROL} && [[ -z ${NO_COLOR:-} ]]; then PROGRESS_COLOR=true; fi
    if ${PROGRESS_ENABLED}; then
        printf 'EC · AUTOMAÇÃO\n  Progresso: fases com peso igual; percentual da fase indisponível. Conclusão não significa aprovação.\n' >&2
    fi
}
progress_begin() {
    PROGRESS_ID=$1 PROGRESS_STARTED=${SECONDS} PROGRESS_UPDATED=${SECONDS} PROGRESS_ACTIVE=true
    ${PROGRESS_ENABLED} || return 0
    local name activity
    name=$(progress_name "$1")
    activity=$(progress_safe "$1")
    printf '\n[%d/%d] %s\n' "$((PROGRESS_DONE+1))" "${PROGRESS_TOTAL}" "${name}" >&2
    printf '  Atividade: %s.\n' "${activity}" >&2
    progress_line
}
progress_clear() {
    if ${PROGRESS_ENABLED} && ${PROGRESS_CONTROL}; then printf '\r\033[2K' >&2; fi
}
progress_line() {
    ${PROGRESS_ENABLED} || return 0
    progress_clear
    local duration
    duration=$(progress_duration "$((SECONDS-PROGRESS_STARTED))")
    printf '[%d/%d] total: %d%% | fase: indisponível | decorrido: %s' \
        "$((PROGRESS_DONE+1))" "${PROGRESS_TOTAL}" "$((100*PROGRESS_DONE/PROGRESS_TOTAL))" \
        "${duration}" >&2
    if ! ${PROGRESS_CONTROL}; then printf '\n' >&2; fi
}
progress_tick() {
    ${PROGRESS_ENABLED} || return 0
    local interval=${PROGRESS_HEARTBEAT}
    if ${PROGRESS_TTY}; then interval=${PROGRESS_REFRESH}; fi
    if ((SECONDS-PROGRESS_UPDATED>=interval)); then
        progress_line
        PROGRESS_UPDATED=${SECONDS}
    fi
}
progress_end() {
    local state=$1 raw=$2 reference=${3:-} label styled_label duration quoted_reference
    case ${state} in
        passed) label=SUCESSO;; measured) label=REGISTRADO;; blocked) label=BLOQUEADA;;
        cancelled) label=CANCELADA;; *) label=FALHA;;
    esac
    if ${PROGRESS_ENABLED}; then
        progress_clear
        styled_label=$(progress_label "${label}")
        duration=$(progress_duration "$((SECONDS-PROGRESS_STARTED))")
        printf '  [%s] | duração: %s | código: %s\n' "${styled_label}" \
            "${duration}" "${raw}" >&2
        case ${state} in
            measured) printf '  Resultado: medição registrada; consulte o relatório.\n' >&2;;
            findings) printf '  Resultado: achados reportados; consulte o relatório.\n' >&2;;
            blocked) printf '  Resultado: pré-requisito ou dependência impediu a execução.\n' >&2;;
            cancelled) printf '  Resultado: execução cancelada; resultados parciais.\n' >&2;;
            passed) :;;
            *) printf '  Resultado: erro de execução ou relatório inválido; consulte os logs.\n' >&2;;
        esac
        if [[ -n ${reference} ]]; then
            quoted_reference=$(progress_safe "${reference}")
            printf '  Log/relatório: %s\n' "${quoted_reference}" >&2
        fi
    fi
    PROGRESS_DONE=$((PROGRESS_DONE+1)) PROGRESS_ACTIVE=false
}
progress_finish() {
    local code=$1 published=$2 cancelled=$3
    ${PROGRESS_ENABLED} || return 0
    printf '\nRESUMO DA EXECUÇÃO\n' >&2
    if ((cancelled)); then printf 'Execução CANCELADA; fases restantes não executadas.\n' >&2
    elif ((published)); then printf 'Execução incompleta: exportação ou limpeza dos logs falhou.\n' >&2
    elif [[ ${PROGRESS_RECORDED} != true ]] || (( PROGRESS_DONE != PROGRESS_TOTAL )); then printf 'Execução incompleta: fases sem resultado.\n' >&2
    else printf 'total: 100%% | Resultados registrados; código: %s.\n' "${code}" >&2; fi
    printf 'Saídas brutas, avisos, motivos e detalhes permanecem nos logs; aceitação exige o gate da tarefa.\n' >&2
}
