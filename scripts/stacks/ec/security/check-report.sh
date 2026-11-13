#!/usr/bin/env bash
set -euo pipefail
CODE=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/../../../.." && pwd -P)
source "${CODE}/scripts/stacks/ec/common.sh"
[[ $# -ge 3 && $# -le 5 ]] || fail 'uso: check-report.sh secrets|sast|sast-rules|dependencies|sarif|trivy RELATÓRIO EXIT [INPUTS SNAPSHOT]'
kind=$1 report=$2 rc=$3 inputs=${4:-/dev/null} snapshot=${5:-}
need jq; json_file "${report}"
case ${kind} in
    secrets)
        [[ ${rc} == 0 || ${rc} == 10 ]] || fail 'erro operacional Gitleaks'
        jq -e 'type=="array" and all(.[]; type=="object" and
            all(.RuleID,.File; type=="string" and length>0) and
            (.StartLine|type=="number" and .>0) and
            ((.Secret//"")|.=="REDACTED" or test("^[*]*$")))' "${report}" >/dev/null || fail 'relatório Gitleaks inválido ou sem redação'
        count=$(jq length "${report}")
        (( (rc==10) == (count>0) )) || fail 'exit Gitleaks inconsistente';;
    sast-rules)
        [[ ${rc} == 0 ]] || fail 'testes de regras falharam'
        rules=$(configuration policies.opengrep | jq -r .)
        expected=$(for fixture in "${CODE}/${rules}"/*; do
            [[ ! -f ${fixture} ]] || sed -n 's/.*ruleid: *\(ec\.[a-zA-Z0-9_.-]*\).*/\1/p' "${fixture}"
        done | sort -u | jq -R . | jq -s .)
        jq -e --argjson ids "${expected}" '
            .config_missing_tests==[] and .config_with_errors==[] and (.results|type=="object" and length>0) and
            ([.results[]|.checks|to_entries[]] as $checks |
            ($checks|map(.key)|sort)==($ids|sort) and
            all($checks[]; .value.passed==true and ((.value.errors//[])|length==0) and
                (.value.matches|type=="object" and length>0) and
                all(.value.matches[]; (.expected_lines|type=="array") and .expected_lines==.reported_lines and all(.expected_lines[]; type=="number" and .>0 and .==floor)) and
                ([.value.matches[].expected_lines|length]|add)>0))' "${report}" >/dev/null || fail 'teste de regras vazio, incompleto ou inválido'
        count=0;;
    sast|dependencies)
        [[ ${rc} == 0 || ${rc} == 1 ]] || fail 'erro operacional scanner'
        json_file "${inputs}"
        jq -e 'type=="object" and length>0' "${inputs}" >/dev/null || fail 'snapshot vazio'
        jq -e --arg kind "${kind}" --arg prefix "${snapshot}/" --slurpfile expected "${inputs}" '
            def rel: if startswith($prefix) then ltrimstr($prefix) elif startswith("/") then error("fora do snapshot") else . end | if (split("/")|index("..")) then error("travessia") else . end;
            if $kind=="sast" then
                (.results|type=="array") and .errors==[] and ((.skipped_rules//[])|type=="array" and length==0) and (.paths.scanned|type=="array") and ((.paths.skipped//[])|type=="array" and length==0) and
                ((.paths.scanned|map(rel)|sort)==($expected[0]|keys|sort)) and
                all(.results[]; (.check_id|type=="string") and (.extra|type=="object") and (.extra.is_ignored//false)==false)
            else
                ((.errors//[])|length==0) and (.results|type=="array" and length>0) and
                ((.experimental_generic_findings//[])|length==0) and
                (([.results[].source.path|rel]|sort)==($expected[0]|keys|sort)) and
                all(.results[]; .source.type=="lockfile" and ((.experimental_pes//[])|length==0) and
                    (.packages|type=="array" and length>0) and all(.packages[];
                        all(.package.name,.package.version,.package.ecosystem; type=="string" and length>0) and
                        ((.vulnerabilities//[])|type=="array") and all(.vulnerabilities[]?; (.id|type=="string" and length>0))))
            end' "${report}" >/dev/null || fail 'relatório scanner incompleto, suprimido ou inválido'
        if [[ ${kind} == sast ]]; then count=$(jq '.results|length' "${report}"); else count=$(jq '[.results[].packages[]|(.vulnerabilities//[])|length]|add' "${report}"); fi
        (( (rc==1) == (count>0) )) || fail 'exit e contagem inconsistentes';;
    sarif)
        # CodeQL database analyze retorna 0 também quando há achados; qualquer não zero é erro.
        [[ ${rc} == 0 ]] || fail 'erro operacional CodeQL/SARIF'
        exec python3 "${CODE}/tools/quality/sarif_report.py" "${report}";;
    trivy)
        [[ ${rc} == 0 || ${rc} == 1 ]] || fail 'erro operacional Trivy'
        jq -e '.SchemaVersion==2 and (.ArtifactName|type=="string" and length>0) and (.Results|type=="array" and length>0) and all(.Results[]; (.Target|type=="string") and (.ExperimentalModifiedFindings//[])==[] and ((.Vulnerabilities//[])|type=="array") and ((.Secrets//[])|type=="array") and ((.Misconfigurations//[])|type=="array") and all(.Secrets[]?; (.RuleID|type=="string" and length>0)) and all(.Misconfigurations[]?; (.ID|type=="string" and length>0) and (.Status=="PASS" or .Status=="FAIL")) and all(.Vulnerabilities[]?; (.VulnerabilityID|type=="string" and length>0)))' "${report}" >/dev/null || fail 'relatório Trivy incompleto'
        count=$(jq '[.Results[] | ((.Vulnerabilities//[])|length) + ((.Secrets//[])|length) + ([.Misconfigurations[]? | select(.Status=="FAIL")]|length)]|add' "${report}")
        if [[ ${inputs} != /dev/null ]]; then
            json_file "${inputs}"
            jq -e --arg prefix "${snapshot}/" --slurpfile expected "${inputs}" '
                def rel: (if startswith($prefix) then ltrimstr($prefix) elif startswith("/project/") then ltrimstr("/project/") elif startswith("/") then error("fora do snapshot") else . end) | if (split("/")|index("..")) then error("travessia") elif startswith("application/") then . else "application/" + . end;
                . as $report |
                ([$expected[0]|keys[]|select(endswith("Cargo.lock") or endswith("package-lock.json"))] | sort) as $locks |
                ([$report.Results[]|select((.Packages//[])|length>0)|.Target|rel]|unique) as $targets |
                $report.SchemaVersion==2 and ($locks|length)==3 and ($locks-$targets|length)==0' "${report}" >/dev/null || fail 'Trivy omitiu inventário dos três lockfiles obrigatórios'
        fi
        (( (rc==1) == (count>0) )) || fail 'exit Trivy inconsistente';;
    *) fail "parser desconhecido: ${kind}";;
esac
jq -n --argjson count "${count}" '{state:(if $count>0 then "findings" else "passed" end),finding_count:$count}'
