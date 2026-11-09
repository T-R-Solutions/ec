#!/usr/bin/env bash
set -euo pipefail
CODE=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/../../../.." && pwd -P)
source "$CODE/scripts/stacks/ec/common.sh"
[[ $# == 1 ]] || fail 'uso: validate-exceptions.sh REGISTRO.json'
need jq; json_file "$1"
jq -e 'type=="array" and all(.[];
    keys==["component","control","expires","finding","id","owner","reason","reviewed","tool"] and
    all(.[]; type=="string" and test("\\S"))) and
    (map([.tool,.finding,.component])|length== (unique|length)) and
    (map(.id)|length==(unique|length))' "$1" >/dev/null || fail 'metadados ausentes ou exceção duplicada'
while IFS=$'\t' read -r reviewed expires; do
    [[ $reviewed =~ ^[0-9]{4}-[0-9]{2}-[0-9]{2}$ && $expires =~ ^[0-9]{4}-[0-9]{2}-[0-9]{2}$ ]] || fail 'datas ISO necessárias'
    date -d "$reviewed" +%F >/dev/null; date -d "$expires" +%F >/dev/null
    [[ $reviewed < $expires && ! $reviewed > $(date -u +%F) && $expires > $(date -u +%F) ]] || fail 'exceção expirada, revisão futura ou intervalo inválido'
done < <(jq -r '.[]|[.reviewed,.expires]|@tsv' "$1")
jq '{valid:length,passed:true,applied:false}' "$1"
