#!/usr/bin/env bash
set -euo pipefail
CODE=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/../../../.." && pwd -P)
source "${CODE}/scripts/stacks/ec/common.sh"
[[ $# == 2 ]] || fail 'uso: metrics/check.sh coverage|budget|performance POLÍTICA.json (caminhos relativos ao cwd)'
kind=$1 policy=$2
need jq; json_file "${policy}"
case ${kind} in
    coverage)
        jq -e '.format=="lcov" and (.minimum|type=="object" and length>0) and (.minimum|keys-["lines","functions"]|length==0) and all(.minimum[]; type=="number" and .>=0 and .<=100)' "${policy}" >/dev/null || fail 'política LCOV inválida'
        report=$(jq -er .report "${policy}")
        report=$(inside "${PWD}" "${report}")
        [[ -f ${report} ]] || blocked 'relatório LCOV ausente'
        # Verifica cada registro, não somente a soma; rejeita truncamento e contadores ausentes.
        totals=$(awk -F: '
            /^SF:/ { if(open || seen[$0]++) bad=1; open=1; records++; lf=lh=fnf=fnh=-1 }
            /^(LF|LH|FNF|FNH):/ {
                if(!open || $2 !~ /^[0-9]+$/) bad=1
                if($1=="LF") {if(lf!=-1)bad=1;lf=$2}
                if($1=="LH") {if(lh!=-1)bad=1;lh=$2}
                if($1=="FNF") {if(fnf!=-1)bad=1;fnf=$2}
                if($1=="FNH") {if(fnh!=-1)bad=1;fnh=$2}
            }
            /^end_of_record$/ {if(!open || lf<0 || lh<0 || lh>lf || fnf<0 || fnh<0 || fnh>fnf)bad=1; a+=lf;b+=lh;c+=fnf;d+=fnh;open=0}
            END {if(bad || open || !records)exit 2; printf "{\"lines\":[%d,%d],\"functions\":[%d,%d]}\n",b,a,d,c}' "${report}") || fail 'LCOV inválido ou incompleto'
        if jq -e 'has("sources")' "${policy}" >/dev/null; then
            sources=$(jq -er .sources "${policy}")
        sources=$(inside "${PWD}" "${sources}")
            json_file "${sources}"
            # Normalizar os caminhos LCOV antes de comparar com o inventário próprio.
            source_root=$(jq -r '.source_root // "."' "${policy}")
            sed -n 's/^SF://p' "${report}" | while IFS= read -r file; do
                [[ ${file} == /* ]] || file=${source_root}/${file}
                realpath -m -- "${file}"
            done | jq -Rn '[inputs]|sort' > "${report}.sources.json"
            jq -e --slurpfile actual "${report}.sources.json" 'type=="array" and length>0 and (sort==$actual[0])' "${sources}" >/dev/null || fail 'LCOV não corresponde ao inventário de fontes de produção'
        fi
        result=$(jq --argjson totals "${totals}" '.minimum|to_entries|map(.key as $key | $totals[$key] as $counts | if $counts[1]<=0 then error("denominador vazio") else {key:$key,value:{covered:$counts[0],total:$counts[1],actual:($counts[0]*100/$counts[1]),minimum:.value,passed:($counts[0]*100 >= .value*$counts[1])}} end)|from_entries | {metrics:.,passed:all(.[];.passed)}' "${policy}") || fail 'LCOV com denominador vazio ou métricas inválidas';;
    budget)
        jq -e '.maximum_bytes|type=="number" and .>0 and .==floor' "${policy}" >/dev/null || fail 'budget inválido'
        directory=$(jq -er .directory "${policy}")
        directory=$(inside "${PWD}" "${directory}")
        [[ -d ${directory} ]] || blocked 'diretório de build ausente'
        files='[]'
        listing=$(mktemp)
        trap 'rm -f -- "${listing}"' EXIT
        find "${directory}" \( -type f -o -type l \) -print0 | sort -z > "${listing}" || fail 'inventário do build falhou'
        while IFS= read -r -d '' path; do
            [[ ! -L ${path} && -f ${path} ]] || fail 'build contém symlink ou arquivo não regular'
            digest=$(hash "${path}")
            bytes=$(stat -c %s "${path}")
            compressed=$(gzip -nc -- "${path}" | wc -c)
            files=$(jq --arg path "${path#"${directory}/"}" --arg hash "${digest}" --argjson bytes "${bytes}" --argjson gzip "${compressed}" '.+[{path:$path,bytes:$bytes,gzip_bytes:$gzip,sha256:$hash}]' <<< "${files}")
        done < "${listing}"
        result=$(jq --argjson files "${files}" 'if ($files|length)==0 then error("build vazio") else {artifacts:$files,total_bytes:([$files[].bytes]|add),maximum_bytes:.maximum_bytes} | .+{passed:(.total_bytes<=.maximum_bytes)} end' "${policy}");;
    performance)
        jq -e 'all(.p95_limit,.maximum; type=="number" and .>0)' "${policy}" >/dev/null || fail 'limites de performance inválidos'
        report=$(jq -er .report "${policy}")
        report=$(inside "${PWD}" "${report}")
        json_file "${report}"
        result=$(jq --slurpfile policy "${policy}" 'if (.samples|type!="array" or length==0) or any(.samples[]; type!="number" or .<0) then error("amostras inválidas") else .samples|sort | {samples:length,p95:.[((length*0.95|ceil)-1)],maximum:max} | .+{passed:(.p95<=$policy[0].p95_limit and .maximum<=$policy[0].maximum)} end' "${report}");;
    *) fail "métrica desconhecida: ${kind}";;
esac
printf '%s\n' "${result}"
jq -e .passed <<< "${result}" >/dev/null || exit 1
