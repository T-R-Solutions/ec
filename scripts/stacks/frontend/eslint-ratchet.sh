#!/usr/bin/env bash
set -euo pipefail
CODE=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/../../.." && pwd -P)
source "$CODE/scripts/stacks/ec/common.sh"
[[ $# == 2 ]] || fail 'uso: eslint-ratchet.sh RELATÓRIO.json BASELINE.json'
need jq; json_file "$1"; json_file "$2"
# Baseline explícita: [{file,rule,maximum}]; não executar ESLint ou instalar pacotes.
jq -e 'type=="array" and all(.[]; (.file|type=="string") and (.rule|type=="string") and (.maximum|type=="number" and .>=0 and .==floor)) and (map([.file,.rule])|length==(unique|length))' "$2" >/dev/null || fail 'baseline inválida'
jq -e 'type=="array" and all(.[]; (.filePath|type=="string") and (.messages|type=="array") and all(.messages[]; (.ruleId|type=="string") and (.severity==1 or .severity==2)))' "$1" >/dev/null || fail 'relatório ESLint inválido'
result=$(jq --slurpfile baseline "$2" '[.[]|.filePath as $file|.messages[]|{file:$file,rule:.ruleId,severity:.severity}] | group_by([.file,.rule]) | map(.[0] as $row | ($baseline[0]|map(select(.file==$row.file and .rule==$row.rule))|.[0].maximum//0) as $maximum | {file:$row.file,rule:$row.rule,count:length,maximum:$maximum,passed:(length<=$maximum and all(.[];.severity<2))}) | {results:.,passed:all(.[];.passed)}' "$1")
printf '%s\n' "$result"
jq -e .passed <<< "$result" >/dev/null || exit 1
