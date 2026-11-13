#!/usr/bin/env bash
set -euo pipefail
CODE=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/../../.." && pwd -P)
source "${CODE}/scripts/stacks/ec/common.sh"
[[ $# -ge 1 ]] || fail 'uso: check.sh fmt|check|clippy|test|harness-build|machete|deny|geiger|audit|coverage (no workspace)'
check=$1; shift
[[ $# == 0 ]] || fail 'argumentos inesperados'
[[ -f Cargo.toml && -f Cargo.lock && -f rust-toolchain.toml ]] || blocked 'executar no workspace Rust Ec'
# O runner observa o toolchain ativo; não impor uma versão histórica da suíte.
if [[ -n ${EC_RUST_TOOLCHAIN+x} ]]; then channel=${EC_RUST_TOOLCHAIN}
elif [[ -n ${RUSTUP_TOOLCHAIN+x} ]]; then channel=${RUSTUP_TOOLCHAIN}
else
    channel=$(RUSTUP_AUTO_INSTALL=0 rustup show active-toolchain) || blocked 'toolchain ativo indisponível'
    channel=${channel%% *}
fi
[[ ${channel} =~ ^[a-zA-Z0-9_.-]+$ ]] || fail 'toolchain ativo inválido'
# Descoberta dos plugins globais independente do cache de dependências do Ec.
[[ -z ${EC_CARGO_BIN+x} || -n ${EC_CARGO_BIN} ]] || blocked 'EC_CARGO_BIN vazio; selecione os plugins instalados'
export PATH="${PATH}:${EC_CARGO_BIN-${CARGO_HOME:-${HOME}/.cargo}/bin}"
ROOT=${EC_ROOT:-${CODE}}
need rustup
export RUSTUP_AUTO_INSTALL=0 CARGO_NET_OFFLINE=true EC_RUST_TOOLCHAIN="${channel}"
version=$(rustup run "${channel}" cargo --version) || blocked "toolchain indisponível: ${channel}"
record_tool rustup "${version}"
case ${check} in
    fmt) rustup run "${channel}" rustfmt --version >/dev/null || blocked 'rustfmt indisponível'; set -- fmt --all -- --check;;
    check) set -- check --workspace --offline --all-targets --all-features;;
    clippy)
        clippy_policy=$(configuration policies.clippy | jq -er .)
        export CLIPPY_CONF_DIR="${ROOT}/${clippy_policy%/*}"
        rustup run "${channel}" clippy-driver --version >/dev/null || blocked 'clippy indisponível'
        # restriction contém regras contraditórias; os demais grupos são cumulativos.
        set -- clippy --workspace --offline --all-targets --all-features --keep-going -- \
            -W clippy::all -W clippy::pedantic -W clippy::nursery -W clippy::cargo -D warnings;;
    test) set -- test --workspace --offline;;
    harness-build) set -- build --workspace --offline --features test-failpoints;;
    machete) need cargo-machete; set -- machete --with-metadata;;
    geiger) need cargo-geiger; set -- geiger --offline --all-deps;;
    deny)
        need cargo-deny
        policy=$(configuration policies.cargo-deny)
        policy=${ROOT}/$(jq -er . <<< "${policy}")
        [[ -f ${policy} ]] || blocked "política Cargo Deny ausente: ${policy}"
        python3 "${CODE}/tools/quality/prepare_automation.py" rustsec || exit $?
        db=${EC_RUSTSEC_DB:-}
        [[ -n ${db} && -d ${db}/.git ]] || blocked 'base RustSec local necessária para Deny; configure EC_RUSTSEC_DB'
        [[ -n ${EC_TASK_DIR:-} ]] || blocked 'executar Deny pela entrada automate.sh para limpar o preparo temporário'
        stage=${EC_TASK_DIR}/deny
        mkdir -p "${stage}/advisory-dbs"
        # Nome derivado da URL oficial pelo cargo-deny (XXH64), não da versão.
        # Reutilizar o checkout: nenhum clone, cópia ou fetch de RustSec.
        db=$(realpath -- "${db}")
        db_directory=$(configuration databases.rustsec.deny_directory)
        db_directory=$(jq -er . <<< "${db_directory}")
        ln -s -- "${db}" "${stage}/advisory-dbs/${db_directory}"
        export EC_DENY_DB_ROOT=${stage}/advisory-dbs
        # Cargo-deny expande a variável ao ler TOML; preservar o nome literal.
        sed "/^\\[advisories\\]$/a db-path = \"\$EC_DENY_DB_ROOT\"" "${policy}" > "${stage}/deny.toml"
        maximum=$(configuration databases.rustsec.maximum_age_seconds)
        url=$(configuration databases.rustsec.url)
        sed -i "/^\[advisories\]$/a maximum-db-staleness = \"PT${maximum}S\"\\ndb-urls = [${url}]" "${stage}/deny.toml"
        policy=${stage}/deny.toml
        set -- deny --offline --all-features --config "${policy}" check;;
    audit)
        need cargo-audit
        python3 "${CODE}/tools/quality/prepare_automation.py" rustsec || exit $?
        db=${EC_RUSTSEC_DB:-}
        [[ -n ${db} ]] || blocked 'configure EC_RUSTSEC_DB para uma base RustSec preparada externamente'
        [[ -d ${db}/.git ]] || blocked "base RustSec não encontrada no caminho selecionado: ${db}; configure EC_RUSTSEC_DB para uma base Git local existente"
        [[ -n ${EC_TASK_DIR:-} ]] || blocked 'executar Audit pela entrada automate.sh'
        stage=${EC_TASK_DIR}/audit
        mkdir -p "${stage}/.cargo"
        policy=$(configuration policies.cargo-audit)
        policy=$(jq -er . <<< "${policy}")
        cp "${ROOT}/${policy}" "${stage}/.cargo/audit.toml"
        lockfile=${PWD}/Cargo.lock
        cd "${stage}"
        set -- audit --no-fetch --db "${db}" --file "${lockfile}" --deny warnings --json;;
    coverage)
        need cargo-llvm-cov
        llvm=$(python3 "${CODE}/tools/quality/prepare_automation.py" llvm) || exit $?
        LLVM_COV=$(jq -er .LLVM_COV <<< "${llvm}")
        LLVM_PROFDATA=$(jq -er .LLVM_PROFDATA <<< "${llvm}")
        export LLVM_COV LLVM_PROFDATA
        [[ -n ${EC_TASK_DIR:-} ]] || blocked 'executar coverage pela entrada automate.sh'
        source "${CODE}/scripts/stacks/ec/metrics/coverage.sh"
        coverage_begin
        # Target exclusivo: nunca reutilizar instrumentação nos builds dos harnesses.
        export CARGO_TARGET_DIR="${COVERAGE_DIR}/target"
        export CARGO_LLVM_COV_TARGET_DIR="${CARGO_TARGET_DIR}"
        set -- llvm-cov --workspace --all-targets --all-features --offline --no-report;;
    *) fail "check Rust desconhecido: ${check}";;
esac
case ${check} in
    machete|geiger|deny|audit|coverage)
        plugin=${check}
        [[ ${check} != coverage ]] || plugin='llvm-cov'
        executor=$(configuration "tools.${plugin}.executor")
        executor=$(jq -er . <<< "${executor}")
        [[ ${executor} == cargo ]] || fail "plugin fora do executor Cargo: ${plugin}"
        plugin_version=$(rustup run "${channel}" cargo "${plugin}" --version) || blocked "não foi possível identificar cargo-${plugin}"
        record_tool "cargo-${plugin}" "${plugin_version}";;
    *) :;;
esac
if [[ ${check} == coverage ]]; then
    rc=0
    rustup run "${channel}" cargo "$@" || rc=$?
    printf '%s\n' "${rc}" > "${EC_TASK_DIR}/producer-exit.txt"
    (( rc == 0 )) || fail 'geração de cobertura Rust falhou; relatório não aprovado'
    component=$(configuration components)
    component=$(jq -er --arg workspace "${PWD#"${ROOT}/"}" 'to_entries[]|select(.value.root==$workspace)|.key' <<< "${component}") || fail 'workspace de cobertura desconhecido'
    runtime_rc=0
    python3 "${CODE}/tools/quality/collect_runtime_coverage.py" --channel "${channel}" --component "${component}" || runtime_rc=$?
    printf '%s\n' "${runtime_rc}" > "${EC_TASK_DIR}/runtime-producer-exit.txt"
    (( runtime_rc == 0 )) || fail 'jornada real de cobertura falhou; relatório não aprovado'
    rustup run "${channel}" cargo llvm-cov report --offline --lcov --output-path "${COVERAGE_DIR}/coverage.lcov" || fail 'relatório LLVM agregado falhou'
    coverage_finish
    exit $?
fi
if [[ ${check} == geiger ]]; then
    # Analisar pacotes concretos também quando o workspace é virtual.
    # Percorrer todos os membros também cobre crates fora da árvore do binário.
    need jq
    need python3
    stage=${EC_TASK_DIR:?Geiger requer diretório privado do runner}
    stack=$(configuration runtime.geiger_stack_bytes)
    rustup run "${channel}" cargo metadata --offline --all-features --format-version 1 --no-deps > "${stage}/workspace.json"
    members=$(jq -ce '. as $workspace | [.workspace_members[] as $id | .packages[] | select(.id==$id) | .manifest_path] |
        if length>0 and length==($workspace.workspace_members|length) and all(.[]; type=="string" and endswith("/Cargo.toml")) then . else error("membros inválidos") end' "${stage}/workspace.json") || fail 'metadata Geiger sem membros completos'
    : > "${stage}/geiger.txt"
    index=0
    jq -j '.[]+"\u0000"' <<< "${members}" > "${stage}/members.list"
    while IFS= read -r -d '' manifest; do
        rc=0
        # Parsing recursivo em build debug excedeu a pilha padrão das threads Rayon.
        # Ajuste restrito ao scanner; nenhum ulimit ou configuração do host muda.
        RUST_MIN_STACK=${stack} rustup run "${channel}" cargo "$@" --all-features --manifest-path "${manifest}" \
            > "${stage}/geiger-${index}.txt" 2> "${stage}/geiger-${index}.stderr.log" || rc=$?
        printf '%s\n' "${rc}" > "${stage}/geiger-${index}-exit.txt"
        cat "${stage}/geiger-${index}.stderr.log" >&2
        printf 'manifest: %s\n' "${manifest}" >> "${stage}/geiger.txt"
        cat "${stage}/geiger-${index}.txt" >> "${stage}/geiger.txt"
        cat "${stage}/geiger-${index}.txt"
        printf '%s\n' "${rc}" > "${stage}/geiger-exit.txt"
        if (( rc != 0 )); then
            target_directory=$(jq -r '.target_directory | if type=="string" then . else "" end' "${stage}/workspace.json") || target_directory=''
            python3 "${CODE}/tools/quality/geiger_diagnostics.py" \
                --manifest "${manifest}" --native-exit-code "${rc}" \
                --stdout "${stage}/geiger-${index}.txt" --stderr "${stage}/geiger-${index}.stderr.log" \
                --cargo-home "${CARGO_HOME:-${HOME}/.cargo}" --target-directory "${target_directory}" \
                --workspace-root "${PWD}" --output "${stage}/geiger-${index}-diagnostic.json" || \
                printf 'aviso: diagnóstico complementar indisponível; consulte os logs brutos em %s\n' "${stage}" >&2
            fail "inventário Geiger falhou: ${manifest}; saída nativa=${rc}; logs e diagnóstico em ${stage}/geiger-${index}*; logs preservados; builds descartáveis podem ser removidos após o encerramento do run"
        fi
        [[ -s ${stage}/geiger-${index}.txt ]] || fail "inventário Geiger vazio: ${manifest}"
        index=$((index + 1))
    done < "${stage}/members.list"
    exit 0
fi
exec rustup run "${channel}" cargo "$@"
