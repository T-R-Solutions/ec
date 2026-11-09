#!/usr/bin/env bash
set -euo pipefail
CODE=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/.." && pwd -P)
while IFS= read -r -d '' file; do bash -n "$file"; done < <(find "$CODE/scripts" -name '*.sh' -type f -print0)
# ShellCheck tem tarefa obrigatória própria via toolbox, sem omissão silenciosa.
