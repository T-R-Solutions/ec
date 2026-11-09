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
    geiger) need cargo-geiger; set -- geiger --locked --offline;;
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
        set -- llvm-cov --workspace --all-targets --all-features --locked --offline --lcov --output-path "$COVERAGE_DIR/coverage.lcov";;
    *) fail "check Rust desconhecido: $check";;
esac
case $check in
    machete|geiger|deny|audit|coverage)
        plugin=$check
        [[ $check != coverage ]] || plugin=llvm-cov
        plugin_version=$(rustup run "$channel" cargo "$plugin" --version) || blocked "não foi possível identificar cargo-$plugin"
        record_tool "cargo-$plugin" "$plugin_version";;
esac
if [[ $check == coverage ]]; then
    rc=0
    rustup run "$channel" cargo "$@" || rc=$?
    printf '%s\n' "$rc" > "$EC_TASK_DIR/producer-exit.txt"
    (( rc == 0 )) || fail 'geração de cobertura Rust falhou; relatório não aprovado'
    coverage_finish
    exit $?
fi
if [[ $check == geiger ]]; then
    # Geiger 0.14 exige pacote concreto mesmo quando o workspace é virtual.
    # Percorrer todos os membros também cobre crates fora da árvore do binário.
    need jq
    stage=${EC_TASK_DIR:-$(mktemp -d)}
    rustup run "$channel" cargo metadata --locked --offline --all-features --format-version 1 --no-deps > "$stage/workspace.json"
    members=$(jq -ce '. as $workspace | [.workspace_members[] as $id | .packages[] | select(.id==$id) | .manifest_path] |
        if length>0 and length==($workspace.workspace_members|length) and all(.[]; type=="string" and endswith("/Cargo.toml")) then . else error("membros inválidos") end' "$stage/workspace.json") || fail 'metadata Geiger sem membros completos'
    : > "$stage/geiger.txt"
    index=0
    while IFS= read -r -d '' manifest; do
        rc=0
        rustup run "$channel" cargo "$@" --all-features --manifest-path "$manifest" > "$stage/geiger-$index.txt" || rc=$?
        printf '%s\n' "$rc" > "$stage/geiger-$index-exit.txt"
        printf 'manifest: %s\n' "$manifest" >> "$stage/geiger.txt"
        cat "$stage/geiger-$index.txt" >> "$stage/geiger.txt"
        cat "$stage/geiger-$index.txt"
        printf '%s\n' "$rc" > "$stage/geiger-exit.txt"
        (( rc == 0 )) || fail "inventário Geiger falhou: $manifest"
        [[ -s $stage/geiger-$index.txt ]] || fail "inventário Geiger vazio: $manifest"
        index=$((index + 1))
    done < <(jq -j '.[]+"\u0000"' <<< "$members")
    exit 0
fi
exec rustup run "$channel" cargo "$@"
