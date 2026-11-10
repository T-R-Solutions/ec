#!/usr/bin/env bash
set -euo pipefail
CODE=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/../../.." && pwd -P)
source "$CODE/scripts/stacks/ec/common.sh"
[[ $# -ge 1 ]] || fail 'uso: check.sh fmt|clippy|test|harness-build|machete|deny|geiger|audit|coverage (no workspace)'
check=$1; shift
[[ $# == 0 ]] || fail 'argumentos inesperados'
[[ -f Cargo.toml && -f Cargo.lock && -f rust-toolchain.toml ]] || blocked 'executar no workspace Rust Ec'
# O formato versionado Ec contém um único channel literal; não avaliar TOML como código.
channel=$(sed -n 's/^channel = "\([a-zA-Z0-9.-]*\)"$/\1/p' rust-toolchain.toml)
[[ -n $channel && $channel != *$'\n'* ]] || fail 'toolchain sem channel literal único'
# Descoberta dos plugins globais independente do cache de dependências do Ec.
export PATH="$PATH:${EC_CARGO_BIN:-${CARGO_HOME:-$HOME/.cargo}/bin}:$HOME/.cargo/bin"
ROOT=${EC_ROOT:-$CODE}
need rustup
export RUSTUP_AUTO_INSTALL=0 CARGO_NET_OFFLINE=true
version=$(rustup run "$channel" cargo --version) || blocked "toolchain indisponível: $channel"
record_tool rustup "$version"
case $check in
    fmt) rustup run "$channel" rustfmt --version >/dev/null || blocked 'rustfmt indisponível'; set -- fmt --all -- --check;;
    clippy) rustup run "$channel" clippy-driver --version >/dev/null || blocked 'clippy indisponível'; set -- clippy --workspace --locked --offline --all-targets --all-features -- -D warnings;;
    test) set -- test --workspace --locked --offline;;
    harness-build) set -- build --workspace --locked --offline --features test-failpoints;;
    machete) need cargo-machete; set -- machete;;
    geiger) set -- geiger --locked --offline;;
    deny)
        need cargo-deny
        policy=$ROOT/scripts/config/native/cargo-deny/deny.toml
        [[ -f $policy ]] || blocked 'política scripts/config/native/cargo-deny/deny.toml necessária'
        set -- deny --locked --offline --all-features --config "$policy" check;;
    audit)
        need cargo-audit
        db=${EC_RUSTSEC_DB:-}
        [[ -n $db ]] || blocked 'configure EC_RUSTSEC_DB para uma base RustSec preparada externamente'
        [[ -d $db/.git ]] || blocked 'base RustSec local necessária; configurar EC_RUSTSEC_DB; não há downloader local'
        need git
        stamp=$(git -C "$db" log -1 --format=%ct) || fail 'base RustSec inválida'
        [[ $stamp =~ ^[0-9]+$ ]] || fail 'data RustSec inválida'
        now=$(date +%s)
        (( stamp <= now && now - stamp <= 604800 )) || blocked 'base RustSec futura ou com commit há mais de sete dias'
        stage=${EC_TASK_DIR:-$(mktemp -d)}/audit
        mkdir -p "$stage/.cargo"
        cp "$ROOT/scripts/config/native/cargo-audit/audit.toml" "$stage/.cargo/audit.toml"
        lockfile=$PWD/Cargo.lock
        cd "$stage"
        set -- audit --no-fetch --db "$db" --file "$lockfile" --deny warnings --json;;
    coverage)
        need cargo-llvm-cov
        rustup component list --installed --toolchain "$channel" | grep '^llvm-tools' >/dev/null || blocked 'componente llvm-tools-preview necessário'
        [[ -n ${EC_TASK_DIR:-} ]] || blocked 'executar coverage pela entrada automate.sh'
        source "$CODE/scripts/stacks/ec/metrics/coverage.sh"
        coverage_begin
        # Target exclusivo: nunca reutilizar instrumentação nos builds dos harnesses.
        export CARGO_TARGET_DIR="$COVERAGE_DIR/target"
        export CARGO_LLVM_COV_TARGET_DIR="$CARGO_TARGET_DIR"
        set -- llvm-cov --workspace --all-targets --all-features --locked --offline --no-report;;
    *) fail "check Rust desconhecido: $check";;
esac
if [[ $check == geiger ]]; then
    # Mesma versão/motor Geiger; correção local auditável da descoberta dep-info.
    # Build isolado, sem instalação global, atualização ou escrita no registro.
    geiger_stage=${EC_TASK_DIR:-$(mktemp -d)}
    geiger_target=$geiger_stage/geiger-tool
    build_rc=0
    CARGO_HOME=${EC_GEIGER_BUILD_CARGO_HOME:-$HOME/.cargo} \
        rustup run "$channel" cargo build --locked --offline \
        --manifest-path "$CODE/tools/quality/vendor/cargo-geiger/Cargo.toml" \
        --target-dir "$geiger_target" --bin cargo-geiger || build_rc=$?
    printf '%s\n' "$build_rc" > "$geiger_stage/geiger-build-exit.txt"
    (( build_rc == 0 )) || blocked 'build offline do Geiger corrigido indisponível'
    [[ -x $geiger_target/debug/cargo-geiger ]] || blocked 'build Geiger não produziu executável'
    test_rc=0
    CARGO_HOME=${EC_GEIGER_BUILD_CARGO_HOME:-$HOME/.cargo} \
        rustup run "$channel" cargo test --locked --offline --lib \
        --manifest-path "$CODE/tools/quality/vendor/cargo-geiger/Cargo.toml" \
        --target-dir "$geiger_target" scan::supplemental::tests || test_rc=$?
    printf '%s\n' "$test_rc" > "$geiger_stage/geiger-tests-exit.txt"
    (( test_rc == 0 )) || fail 'regressões da descoberta Geiger falharam'
    # Cargo só respeita a prioridade PATH se cargo-home/bin já estiver na lista.
    export PATH="$geiger_target/debug:${CARGO_HOME:-$HOME/.cargo}/bin:$PATH"
fi
case $check in
    machete|geiger|deny|audit|coverage)
        plugin=$check
        [[ $check != coverage ]] || plugin=llvm-cov
        plugin_version=$(rustup run "$channel" cargo "$plugin" --version) || blocked "não foi possível identificar cargo-$plugin"
        record_tool "cargo-$plugin" "$plugin_version";;
esac
if [[ $check == geiger && -n ${EC_TASK_DIR:-} ]]; then
    jq --arg patch 'Ec dep-info 1' --arg source "$CODE/tools/quality/vendor/cargo-geiger/Cargo.toml" \
        '.+{local_patch:$patch,source_manifest:$source,worker_stack_bytes:16777216}' "$EC_TASK_DIR/tool.json" | \
        atomic_json "$EC_TASK_DIR/tool.json"
fi
if [[ $check == coverage ]]; then
    rc=0
    rustup run "$channel" cargo "$@" || rc=$?
    printf '%s\n' "$rc" > "$EC_TASK_DIR/producer-exit.txt"
    (( rc == 0 )) || fail 'geração de cobertura Rust falhou; relatório não aprovado'
    case $PWD in
        */application/server/backend) component=server;;
        */application/agents) component=agent;;
        *) fail 'workspace de cobertura desconhecido';;
    esac
    runtime_rc=0
    python3 "$CODE/tools/quality/collect_runtime_coverage.py" --channel "$channel" --component "$component" || runtime_rc=$?
    printf '%s\n' "$runtime_rc" > "$EC_TASK_DIR/runtime-producer-exit.txt"
    (( runtime_rc == 0 )) || fail 'jornada real de cobertura falhou; relatório não aprovado'
    rustup run "$channel" cargo llvm-cov report --locked --offline --lcov --output-path "$COVERAGE_DIR/coverage.lcov" || fail 'relatório LLVM agregado falhou'
    coverage_finish
    exit $?
fi
if [[ $check == geiger ]]; then
    # Geiger 0.14 exige pacote concreto mesmo quando o workspace é virtual.
    # Percorrer todos os membros também cobre crates fora da árvore do binário.
    need jq
    need python3
    stage=$geiger_stage
    rustup run "$channel" cargo metadata --locked --offline --all-features --format-version 1 --no-deps > "$stage/workspace.json"
    members=$(jq -ce '. as $workspace | [.workspace_members[] as $id | .packages[] | select(.id==$id) | .manifest_path] |
        if length>0 and length==($workspace.workspace_members|length) and all(.[]; type=="string" and endswith("/Cargo.toml")) then . else error("membros inválidos") end' "$stage/workspace.json") || fail 'metadata Geiger sem membros completos'
    : > "$stage/geiger.txt"
    index=0
    while IFS= read -r -d '' manifest; do
        rc=0
        # Parsing recursivo em build debug excedeu a pilha padrão das threads Rayon.
        # Ajuste restrito ao scanner; nenhum ulimit ou configuração do host muda.
        RUST_MIN_STACK=16777216 rustup run "$channel" cargo "$@" --all-features --manifest-path "$manifest" \
            > "$stage/geiger-$index.txt" 2> "$stage/geiger-$index.stderr.log" || rc=$?
        printf '%s\n' "$rc" > "$stage/geiger-$index-exit.txt"
        cat "$stage/geiger-$index.stderr.log" >&2
        printf 'manifest: %s\n' "$manifest" >> "$stage/geiger.txt"
        cat "$stage/geiger-$index.txt" >> "$stage/geiger.txt"
        cat "$stage/geiger-$index.txt"
        printf '%s\n' "$rc" > "$stage/geiger-exit.txt"
        if (( rc != 0 )); then
            target_directory=$(jq -r '.target_directory | if type=="string" then . else "" end' "$stage/workspace.json") || target_directory=''
            python3 "$CODE/tools/quality/geiger_diagnostics.py" \
                --manifest "$manifest" --native-exit-code "$rc" \
                --stdout "$stage/geiger-$index.txt" --stderr "$stage/geiger-$index.stderr.log" \
                --cargo-home "${CARGO_HOME:-$HOME/.cargo}" --target-directory "$target_directory" \
                --workspace-root "$PWD" --output "$stage/geiger-$index-diagnostic.json" || \
                printf 'aviso: diagnóstico complementar indisponível; consulte os logs brutos em %s\n' "$stage" >&2
            fail "inventário Geiger falhou: $manifest; saída nativa=$rc; logs e diagnóstico em $stage/geiger-$index*; nenhum arquivo será removido automaticamente"
        fi
        [[ -s $stage/geiger-$index.txt ]] || fail "inventário Geiger vazio: $manifest"
        index=$((index + 1))
    done < <(jq -j '.[]+"\u0000"' <<< "$members")
    exit 0
fi
exec rustup run "$channel" cargo "$@"
