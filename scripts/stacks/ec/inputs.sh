#!/usr/bin/env bash
# Lista NUL de fontes ativas; histórico, segredos locais e outputs ficam fora.
set -o pipefail
source_files() {
    : "${ROOT:?raiz necessária}"
    local implementation
    implementation=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/../../.." && pwd -P) || return "$?"
    if [[ ${1:-all} == acceptance ]]; then
        python3 "${implementation}/tools/quality/automation_config.py" acceptance-inputs --root "${ROOT}" || return "$?"
    else
        python3 "${implementation}/tools/quality/automation_config.py" inputs "${1:-all}" --root "${ROOT}" || return "$?"
    fi
}
observe_inputs() {
    local scope=$1 dest=$2 copy=${3:-} file path digest copied_digest
    local list
    list=$(mktemp)
    source_files "${scope}" | sort -z > "${list}"
    : > "${dest}.lines"
    while IFS= read -r -d '' file; do
        path=$(inside "${ROOT}" "${file}")
        [[ -f ${path} ]] || fail "fonte não regular: ${file}"
        digest=$(hash "${path}")
        if [[ -n ${copy} ]]; then
            mkdir -p -- "${copy}/$(dirname -- "${file}")"
            cp -- "${path}" "${copy}/${file}"
            chmod --reference="${path}" "${copy}/${file}"
            copied_digest=$(hash "${copy}/${file}")
            [[ ${copied_digest} == "${digest}" ]] || fail "input mudou durante snapshot: ${file}"
        fi
        jq -nc --arg path "${file}" --arg hash "${digest}" '{key:$path,value:$hash}' >> "${dest}.lines"
    done < "${list}"
    jq -s 'from_entries' "${dest}.lines" | atomic_json "${dest}"
    rm -- "${list}" "${dest}.lines"
}
