#!/usr/bin/env bash
# Biblioteca compartilhada; nunca executar conteúdo de configuração como shell.
fail() { printf 'error: %s\n' "$*" >&2; exit 2; }
blocked() { printf 'blocked: %s\n' "$*" >&2; exit 3; }
need() { command -v "$1" >/dev/null || blocked "ferramenta ausente: $1"; }
json_file() {
    [[ -f $1 && ! -L $1 ]] || fail "arquivo regular necessário: $1"
    [[ $(stat -c %s -- "$1") -le 8388608 ]] || fail "JSON acima de 8MiB: $1"
    jq -e -s 'length == 1' "$1" >/dev/null || fail "JSON inválido: $1"
}
# Aceita somente caminhos internos sem symlinks, inclusive em ancestrais.
inside() {
    local base=$1 rel=$2 part current=$1
    [[ -n $rel && $rel != /* && $rel != *$'\n'* ]] || fail "caminho relativo necessário: $rel"
    local -a parts
    IFS=/ read -r -a parts <<< "$rel"
    for part in "${parts[@]}"; do
        [[ $part != .. ]] || fail "travessia de caminho: $rel"
        current+=/$part
        [[ ! -L $current ]] || fail "symlink não permitido: $rel"
    done
    printf '%s/%s\n' "$base" "$rel"
}
hash() { sha256sum -- "$1" | cut -d ' ' -f 1; }
record_tool() {
    [[ -n ${EC_TASK_DIR:-} ]] || return 0
    local tool=$1 version=$2 launcher
    launcher=$(command -v "$tool")
    jq -n --arg launcher "$launcher" --arg hash "$(hash "$launcher")" --arg version "$version" \
        '{launcher:$launcher,launcher_sha256:$hash,version:$version}' | atomic_json "$EC_TASK_DIR/tool.json"
}
atomic_json() {
    local dest=$1
    cat > "$dest.tmp"
    chmod 600 "$dest.tmp"
    mv -- "$dest.tmp" "$dest"
}
aggregate() {
    jq '[.[].state] | if index("error") then 2 elif index("blocked") then 3 elif index("findings") then 1 else 0 end'
}
