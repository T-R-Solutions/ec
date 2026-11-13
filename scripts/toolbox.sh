#!/usr/bin/env bash
set -euo pipefail
# Template toolbox-native.sh; override presente, inclusive vazio, deve ser válido.
if [[ -n ${TOOLBOX_BIN+x} ]]; then
    [[ -f "${TOOLBOX_BIN}" && -x "${TOOLBOX_BIN}" ]] || { echo 'TOOLBOX_BIN must name an executable' >&2; exit 125; }
    exec "${TOOLBOX_BIN}" "$@"
fi
command -v toolbox >/dev/null || { echo 'Set TOOLBOX_BIN or add toolbox to PATH' >&2; exit 125; }
exec toolbox "$@"
