#!/usr/bin/env bash
set -euo pipefail
CODE=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/../../../.." && pwd -P)
source "${CODE}/scripts/stacks/ec/common.sh"
[[ $# == 1 ]] || fail 'uso: summarize.sh DATA/RUN-automate.results.log'
need jq; json_file "$1"
jq -e '.schema==1 and (.results|type=="array" and length>0) and all(.results[]; (.state as $s|["passed","measured","findings","error","blocked","not_applicable"]|index($s))!=null and has("project") and has("task") and has("reason") and has("returncode"))' "$1" >/dev/null || fail 'resultado schema 1 inválido'
printf '# Resultado de automação\n\n| Projeto | Tarefa | Estado | Exit bruto | Diagnóstico |\n| --- | --- | --- | --- | --- |\n'
jq -r 'def cell: tostring|gsub("[\\r\\n\\t]";" ")|gsub("\\|";"\\|")|gsub("<";"&lt;")|gsub(">";"&gt;"); .results[] | "| " + ([.project,.task,.state,.returncode,.reason]|map(cell)|join(" | ")) + " |"' "$1"
printf '\nEstados desta execução; não constituem aprovação de produção ou de inputs alterados depois.\n'
