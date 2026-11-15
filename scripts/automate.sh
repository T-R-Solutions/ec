#!/usr/bin/env bash
if [[ ${BASH_SOURCE[0]} != "$0" ]]; then
    printf 'Execute automate.sh com bash ou ./scripts/automate.sh; não use source.\n' >&2
    return 2
fi
set -euo pipefail
umask 077
CODE=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/.." && pwd -P)
source "${CODE}/scripts/stacks/ec/common.sh"
usage() {
    cat <<'HELP'
Uso: bash scripts/automate.sh plan|run [--root DIRETÓRIO] [--scope complete] [--surface NOME] [--check NOME]
--scope complete inclui todas as superfícies e Grype, Bandit, Syft e YARA-X.
Não combinar --scope complete com --surface ou --check.
Inclui os pré-requisitos declarados; não inclui dev/setup nem checks frontend opcionais.
Superfícies: docs scripts server agent frontend security integration e2e (padrão: todas).
Checks selecionáveis são os declarados em scripts/config/automation.json.
Chromium e Firefox usam Playwright na Toolbox/Docker, sem navegador no host.
Checks de segurança: secrets sast dependencies (secrets/sast incluem testes de regras).
--check é repetível e limita tarefas das superfícies selecionadas; governança permanece obrigatória.
Sem --surface, --check seleciona sua superfície; coverage exige server/agent/frontend explícito.
plan produz JSON sem executar tarefas. run preserva logs, exit e resultados em .local/logs/AAAA-MM-DD (somente as duas datas mais recentes).
run configura o ambiente temporário automaticamente, em qualquer escopo, usando instalações locais.
Fases e duração em stderr; presentation em scripts/config/automation.json controla atualização e atividade.
Cores apenas em TTY compatível, respeitando NO_COLOR; saídas brutas permanecem nos logs privados.
HELP
}
[[ $# -gt 0 ]] || { usage >&2; exit 2; }
operation=$1; shift
case ${operation} in -h|--help) usage; exit 0;; plan|run) ;; *) fail 'operação deve ser plan ou run';; esac
ROOT=${CODE} SURFACES=() CHECKS=() SCOPE=default
while (( $# )); do
    case $1 in
        --root|--surface|--check|--scope)
            [[ $# -ge 2 && -n $2 && $2 != --* ]] || fail "valor ausente: $1"
            case $1 in --scope) [[ ${SCOPE} == default ]] || fail "--scope repetido"; [[ $2 == complete ]] || fail "scope desconhecido: $2"; SCOPE=$2;; --root) ROOT=$2;; --surface) SURFACES+=("$2");; --check) CHECKS+=("$2");; *) fail 'opção interna inválida';; esac
            shift 2;;
        -h|--help) usage; exit 0;;
        *) fail "argumento desconhecido: $1";;
    esac
done
if [[ ${SCOPE} == complete ]] && (( ${#SURFACES[@]} || ${#CHECKS[@]} )); then
    fail "--scope complete não aceita --surface ou --check"
fi
need jq
ROOT=$(cd -- "${ROOT}" && pwd -P) || fail 'raiz inexistente'
source "${CODE}/scripts/stacks/ec/catalog.sh"
source "${CODE}/scripts/stacks/ec/inputs.sh"
source "${CODE}/scripts/stacks/ec/runtime.sh"
PLAN=$(make_plan)
if [[ ${operation} == plan ]]; then printf '%s\n' "${PLAN}"; exit 0; fi
platform=$(uname -s)
[[ ${platform} == Linux ]] || fail 'supervisão requer Linux'
need flock; need setsid; need timeout; need sha256sum
run_plan
