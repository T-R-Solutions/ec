#!/usr/bin/env bash
# Biblioteca compartilhada; nunca executar conteúdo de configuração como shell.
set -o pipefail
fail() { printf 'error: %s\n' "$*" >&2; exit 2; }
blocked() { printf 'blocked: %s\n' "$*" >&2; exit 3; }
need() { command -v "$1" >/dev/null || blocked "ferramenta ausente: $1"; }
json_file() {
    [[ -f $1 && ! -L $1 ]] || fail "arquivo regular necessário: $1"
    local bytes maximum
    bytes=$(stat -c %s -- "$1") || fail "não foi possível medir JSON: $1"
    maximum=$(configuration runtime.json_bytes)
    [[ ${bytes} -le ${maximum} ]] || fail "JSON acima do limite configurado: $1"
    jq -e -s 'length == 1' "$1" >/dev/null || fail "JSON inválido: $1"
}
# Aceita somente caminhos internos sem symlinks, inclusive em ancestrais.
inside() {
    local base=$1 rel=$2 part current=$1
    [[ -n ${rel} && ${rel} != /* && ${rel} != *$'\n'* ]] || fail "caminho relativo necessário: ${rel}"
    local -a parts
    IFS=/ read -r -a parts <<< "${rel}"
    for part in "${parts[@]}"; do
        [[ ${part} != .. ]] || fail "travessia de caminho: ${rel}"
        current+=/${part}
        [[ ! -L ${current} ]] || fail "symlink não permitido: ${rel}"
    done
    printf '%s/%s\n' "${base}" "${rel}"
}
hash() {
    local digest
    digest=$(sha256sum -- "$1") || return "$?"
    printf '%s\n' "${digest%% *}" || return "$?"
}
record_tool() {
    [[ -n ${EC_TASK_DIR:-} ]] || return 0
    local tool=$1 version=$2 launcher digest
    launcher=$(command -v "${tool}")
    digest=$(hash "${launcher}")
    jq -n --arg launcher "${launcher}" --arg hash "${digest}" --arg version "${version}" \
        '{launcher:$launcher,launcher_sha256:$hash,version:$version}' | atomic_json "${EC_TASK_DIR}/tool.json"
}
atomic_json() {
    local dest=$1
    cat > "${dest}.tmp" || return "$?"
    # Nunca substituir o último resultado válido por saída vazia/truncada.
    if ! jq -e -s 'length == 1' "${dest}.tmp" >/dev/null; then
        rm -f -- "${dest}.tmp" || return "$?"
        return 2
    fi
    chmod 600 "${dest}.tmp" || return "$?"
    mv -- "${dest}.tmp" "${dest}" || return "$?"
}
aggregate() {
    jq '[.[].state] | if index("error") then 2 elif index("blocked") then 3 elif index("findings") then 1 else 0 end'
}

# Builds são temporários por execução; dependências Cargo pertencem ao usuário.
local_path() {
    case $1 in
        cargo) printf '%s\n' "${CARGO_HOME:-${HOME}/.cargo}";;
        server|agent) printf '%s/%s-target\n' "${RUN_DIR:?workspace necessário}" "$1";;
        *) fail "categoria local desconhecida: $1";;
    esac
}

# Só dados JSON; não usar eval/source em configuração.
configuration() {
    local implementation
    implementation=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/../../.." && pwd -P) || return "$?"
    python3 "${implementation}/tools/quality/automation_config.py" get "$1" || return "$?"
}
