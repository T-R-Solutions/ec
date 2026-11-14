#!/usr/bin/env bash
# Geração e avaliação usam diretório novo e inputs do mesmo run.
set -o pipefail
coverage_begin() {
    : "${CODE:?raiz do código necessária}" "${EC_TASK_DIR:?coverage requer diretório privado do runner}"
    local policy
    ROOT=${EC_ROOT:-${CODE}}
    source "${CODE}/scripts/stacks/ec/inputs.sh"
    policy=$(configuration coverage)
    COVERAGE_DIR=$(mktemp -d "${EC_TASK_DIR}/coverage-XXXXXXXX")
    COVERAGE_REPORT_DIR=${COVERAGE_DIR}/report
    mkdir "${COVERAGE_REPORT_DIR}"
    export COVERAGE_DIR COVERAGE_REPORT_DIR
    COVERAGE_WORKSPACE=${PWD}
    observe_inputs all "${COVERAGE_DIR}/inputs-before.json"
    # O Cargo é a autoridade dos membros, inclusive fora da pasta do workspace.
    if [[ -f Cargo.toml ]]; then
        rustup run "${EC_RUST_TOOLCHAIN:?toolchain da cobertura necessário}" cargo metadata --offline --no-deps --format-version 1 > "${COVERAGE_DIR}/metadata.json"
        python3 "${CODE}/tools/quality/coverage_inventory.py" "${COVERAGE_DIR}/metadata.json" > "${COVERAGE_DIR}/sources.json"
    else
        local file
        : > "${COVERAGE_DIR}/sources.txt"
        find . \( -name target -o -name node_modules -o -name dist -o -name .local \) -prune -o -type f -printf '%P\0' | sort -z > "${COVERAGE_DIR}/sources.list"
        while IFS= read -r -d '' file; do
            case ${file} in
                */src/*.rs)
                    case ${file} in */tests/*|*/tests.rs|*_tests.rs|*-tests.rs) continue;; *) :;; esac;;
                src/*.ts|src/*.vue)
                    case ${file} in *.test.ts|*.spec.ts|*.d.ts) continue;; *) :;; esac;;
                *) continue;;
            esac
            printf '%s\n' "${COVERAGE_WORKSPACE}/${file}" >> "${COVERAGE_DIR}/sources.txt"
        done < "${COVERAGE_DIR}/sources.list"
        jq -Rn '[inputs]' < "${COVERAGE_DIR}/sources.txt" > "${COVERAGE_DIR}/sources.json"
    fi
    jq -e 'length>0' "${COVERAGE_DIR}/sources.json" >/dev/null || fail 'inventário de cobertura vazio'
    jq --arg report "${COVERAGE_DIR}/coverage.lcov" --arg sources "${COVERAGE_DIR}/sources.json" --arg source_root "${COVERAGE_WORKSPACE}" \
        '.+{report:$report,sources:$sources,source_root:$source_root}' <<< "${policy}" > "${COVERAGE_DIR}/policy.json"
}
coverage_finish() {
    local rc=0
    observe_inputs all "${COVERAGE_DIR}/inputs-after.json"
    cmp -s "${COVERAGE_DIR}/inputs-before.json" "${COVERAGE_DIR}/inputs-after.json" || fail 'inputs alterados durante cobertura'
    # O avaliador resolve os caminhos em relação a este diretório privado.
    jq --arg report coverage.lcov --arg sources sources.json '.report=$report | .sources=$sources' \
        "${COVERAGE_DIR}/policy.json" > "${COVERAGE_DIR}/policy-local.json"
    (cd "${COVERAGE_DIR}" && bash "${CODE}/scripts/stacks/ec/metrics/check.sh" coverage policy-local.json) > "${EC_TASK_DIR}/coverage.json" || rc=$?
    cat "${EC_TASK_DIR}/coverage.json"
    return "${rc}"
}
