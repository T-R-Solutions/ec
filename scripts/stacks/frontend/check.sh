#!/usr/bin/env bash
set -euo pipefail
CODE=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/../../.." && pwd -P)
source "$CODE/scripts/stacks/ec/common.sh"
[[ $# == 1 ]] || fail 'uso: check.sh typecheck|test|build|dev|setup|lint|coverage|build-budget|performance (no frontend)'
check=$1
[[ -f package.json && -f package-lock.json ]] || blocked 'package.json/package-lock.json necessários'
need node; need npm; need jq
node -e 'let [a,b]=process.versions.node.split(".").map(Number);process.exit(a>22||(a===22&&b>=12)?0:1)' || blocked 'Node >=22.12 necessário'
record_tool npm "$(npm --version); Node $(node --version)"
export npm_config_offline=true npm_config_update_notifier=false
case $check in
    setup) exec npm ci --ignore-scripts;;
    build-budget)
        [[ -f quality/build-budget.json ]] || blocked 'política quality/build-budget.json necessária'
        npm run build
        exec bash "$CODE/scripts/stacks/ec/metrics/check.sh" budget quality/build-budget.json;;
    performance)
        jq -e '.scripts.performance | type == "string"' package.json >/dev/null || blocked 'script npm performance necessário'
        [[ -f quality/performance.json ]] || blocked 'política quality/performance.json necessária'
        npm run performance
        exec bash "$CODE/scripts/stacks/ec/metrics/check.sh" performance quality/performance.json;;
    lint|coverage|typecheck|test|build|dev) ;;
    *) fail "check frontend desconhecido: $check";;
esac
[[ -f node_modules/.package-lock.json ]] || blocked 'preparar npm ci --ignore-scripts explicitamente'
jq -e --arg check "$check" '.scripts[$check] | type == "string" and length > 0' package.json >/dev/null || blocked "script npm ausente: $check"
if [[ $check == coverage ]]; then
    [[ -n ${EC_TASK_DIR:-} ]] || blocked 'executar coverage pela entrada automate.sh'
    [[ -f node_modules/@vitest/coverage-v8/package.json ]] || blocked 'provider @vitest/coverage-v8 ausente; preparar dependências explicitamente'
    [[ $(jq -r .version node_modules/@vitest/coverage-v8/package.json) == "$(jq -r .version node_modules/vitest/package.json)" ]] || blocked 'Vitest e provider de cobertura devem ter a mesma versão'
    source "$CODE/scripts/stacks/ec/metrics/coverage.sh"
    coverage_begin
    rc=0
    npm run coverage -- --coverage.reportsDirectory="$COVERAGE_REPORT_DIR" || rc=$?
    printf '%s\n' "$rc" > "$EC_TASK_DIR/producer-exit.txt"
    (( rc == 0 )) || fail 'geração de cobertura frontend falhou; relatório não aprovado'
    [[ -s $COVERAGE_REPORT_DIR/lcov.info ]] || fail 'Vitest não produziu LCOV atual'
    mv -- "$COVERAGE_REPORT_DIR/lcov.info" "$COVERAGE_DIR/coverage.lcov"
    coverage_finish
    exit $?
fi
exec npm run "$check"
