#!/usr/bin/env bash
set -euo pipefail
CODE=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/.." && pwd -P)
listing=$(mktemp)
trap 'rm -f -- "${listing}"' EXIT
find "${CODE}/scripts" -name '*.sh' -type f -print0 > "${listing}"
while IFS= read -r -d '' file; do bash -n "${file}"; done < "${listing}"
# ShellCheck tem tarefa obrigatória própria via toolbox, sem omissão silenciosa.
