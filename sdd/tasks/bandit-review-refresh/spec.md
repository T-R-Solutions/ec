# Correção da triagem Bandit — 15/11/2026

REQ-01: resolver os 16 achados pendentes do run-xmE7fgB1 após revisar os fluxos atuais, mantendo scan integral e rejeição de ocorrências não revisadas.

- AC-01 → REQ-01: execução nativa integral Bandit tem zero pendências, preservando contagens brutas e justificativas por hash/regra/linha nos três arquivos afetados.
- AC-02 → REQ-01: regressões comprovam rejeição de hash, linha, regra, ocorrência nova e razão ausente; superfícies docs/scripts passam e escopo é revisado independentemente.

Relação parcial com AC-38 de scripts-verification; não aceita a suíte completa nem as demais pendências da feature. Não altera fontes Python ou autoriza publicação/downloads.
