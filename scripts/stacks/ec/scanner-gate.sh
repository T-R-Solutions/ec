#!/usr/bin/env bash
set -euo pipefail
CODE=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/../../.." && pwd -P)
exec python3 "$CODE/tools/quality/native_gates.py" "$@"
