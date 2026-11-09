#!/usr/bin/env bash
# Critérios do Ec ainda sem equivalência no contrato público STIC.
set -euo pipefail
CODE=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/../../.." && pwd -P)
source "$CODE/scripts/stacks/ec/common.sh"
[[ $# == 1 ]] || fail 'uso: scanner-gate.sh CHECK'
case $1 in
    trivy) reason='Trivy: requer vuln/misconfig/secret; adapter compartilhado cobre somente vuln';;
    codeql) reason='CodeQL: não existe adapter na Toolbox; suites Rust e JS/TS continuam exigidas';;
    yara-x) reason='YARA-X: adapter e regras locais ainda indisponíveis';;
    secrets-rules|sast-rules) reason='testes reais de regras exigem staging e transporte público de fixtures';;
    secrets|sast|dependencies|ruff|shellcheck|playwright|trivy-toolbox|grype|bandit|syft)
        reason='integração de gates depende de suppression-policy-v2, layout-v2, export e staging-manifest-v1; concluir integração e homologar após suporte público';;
    *) fail "check desconhecido: $1";;
esac
# Não executar scanners contra a árvore completa nem interpretar medição Syft como aceite.
# O wrapper público continua disponível para negociação e manutenção explícita.
blocked "$reason (scripts/README.md)"
