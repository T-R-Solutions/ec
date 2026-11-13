#!/usr/bin/env bash
# O catálogo declarativo e sua seleção pertencem a scripts/config/automation.json.
make_plan() {
    : "${CODE:?raiz do código necessária}" "${ROOT:?raiz necessária}" "${SCOPE:?escopo necessário}"
    local item
    local -a arguments=(--root "${ROOT}" --scope "${SCOPE}")
    # Arrays preenchidos pelo parser da entrada; não substituir a seleção.
    # shellcheck disable=SC2154
    for item in "${SURFACES[@]}"; do arguments+=(--surface "${item}"); done
    # shellcheck disable=SC2154
    for item in "${CHECKS[@]}"; do arguments+=(--check "${item}"); done
    python3 "${CODE}/tools/quality/automation_config.py" plan "${arguments[@]}"
}
