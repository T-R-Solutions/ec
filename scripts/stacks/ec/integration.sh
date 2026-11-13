#!/usr/bin/env bash
set -euo pipefail
CODE=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/../../.." && pwd -P)
source "${CODE}/scripts/stacks/ec/common.sh"
[[ $# -ge 2 ]] || fail 'uso: integration.sh integration|e2e COMANDO [ARGUMENTOS]'
project=$1; shift
python3 "${CODE}/tools/quality/prepare_automation.py" preflight "${project}"
exec "$@"
