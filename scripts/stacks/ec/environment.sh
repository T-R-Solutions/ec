#!/usr/bin/env bash
# A projeção não modifica perfis nem o ambiente do processo chamador.
prepare_run_environment() {
    : "${ROOT:?raiz necessária}"
    local implementation encoded item
    implementation=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/../../.." && pwd -P)
    # Arquivo privado permite observar falha do produtor, sem esconder exit em < <(...).
    encoded=$(mktemp)
    if ! python3 "${implementation}/tools/quality/automation_config.py" environment --root "${ROOT}" > "${encoded}"; then
        rm -- "${encoded}"
        return 2
    fi
    RUN_ENV=()
    while IFS= read -r -d '' item; do RUN_ENV+=("${item}"); done < "${encoded}"
    rm -- "${encoded}"
}
