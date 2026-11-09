#!/usr/bin/env bash
# Lista NUL de fontes ativas; histórico, segredos locais e outputs ficam fora.
source_files() {
    : "${ROOT:?raiz necessária}"
    local scope=${1:-all} base
    local -a bases=(application scripts tools sdd config docs/development)
    [[ $scope != sast ]] || bases=(application/agents application/server/backend application/server/frontend)
    [[ $scope != trivy && $scope != inventory ]] || bases=(application scripts/config scripts/rules)
    if [[ $scope == dependencies ]]; then
        printf '%s\0' application/agents/Cargo.lock application/server/backend/Cargo.lock application/server/frontend/package-lock.json
        return
    fi
    for base in "${bases[@]}"; do
        [[ ! -d $ROOT/$base ]] || find "$ROOT/$base" \( -name .git -o -name .local -o -name .codex -o -name .agents -o -name .aws -o -name node_modules -o -name target -o -name dist -o -name __pycache__ -o -name .env -o -name '.env.*' -o -path "$ROOT/config/governance/sources" \) -prune -o \( -type f -o -type l \) -print0
    done | while IFS= read -r -d '' file; do
        file=${file#"$ROOT/"}
        [[ $file != config/governance/install.lock && $file != config/governance/install-journal.json ]] || continue
        if [[ $scope == sast && $file != *.rs && $file != *.ts && $file != *.vue && ! -L $ROOT/$file ]]; then continue; fi
        printf '%s\0' "$file"
    done
    if [[ $scope == all ]]; then
        printf '%s\0' AGENTS.md README.md
    fi
}
observe_inputs() {
    local scope=$1 dest=$2 copy=${3:-} file path digest
    local list
    list=$(mktemp)
    source_files "$scope" | sort -z > "$list"
    : > "$dest.lines"
    while IFS= read -r -d '' file; do
        path=$(inside "$ROOT" "$file")
        [[ -f $path ]] || fail "fonte não regular: $file"
        digest=$(hash "$path")
        if [[ -n $copy ]]; then
            mkdir -p -- "$copy/$(dirname -- "$file")"
            cp -- "$path" "$copy/$file"
            chmod --reference="$path" "$copy/$file"
            [[ $(hash "$copy/$file") == "$digest" ]] || fail "input mudou durante snapshot: $file"
        fi
        jq -nc --arg path "$file" --arg hash "$digest" '{key:$path,value:$hash}' >> "$dest.lines"
    done < "$list"
    jq -s 'from_entries' "$dest.lines" | atomic_json "$dest"
    rm -- "$list" "$dest.lines"
}
