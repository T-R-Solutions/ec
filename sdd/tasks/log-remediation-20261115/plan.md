# Plano de remediação

Rota completa por revisão de segurança e integridade de aceite. Autoridades:
AGENTS.md, constituição/contexto, ADR-001/002/003, workflow, evidence,
task-verification e local-state. Diagnóstico inicial: 54 checks, 53 passed,
Bandit findings com 375/301/74; 2772 hashes conferidos contra estado atual.

- D-01 / R-01 / REQ-01: Rever 74 contextos antes de registrar triagem; risco de aprovar novo conteúdo só pela renovação de hash. Preservar todos os scanners e negativos existentes.
- D-02 / R-02 / REQ-02: Condicionar apenas import Read à feature; risco de quebrar fechamento do harness e coleta de cobertura.
- D-03 / R-03 / REQ-03: Investigar sinais com outputs nativos e fontes; risco de confundir probes/fixtures com falha de execução ou aceitar extração parcial.

## Verificação

Teste focado unittest discover scripts/tests/test_security_evidence.py, depois
diagnóstico Bandit via runner. Após revisão independente, validar variantes
Server e harnesses e estabilizar todos os artefatos antes do runner final.
Runner final: bash scripts/automate.sh run --scope complete. Todas as superfícies
(docs, scripts, server, agent, frontend, security, integration, e2e) reproduzem
o alcance integral do diagnóstico e verificam efeitos da configuração comum.
Gate: scripts/accept-task.sh --task desta pasta, com recibo real do último run.
Downloads/refresh de bases exigem autorização específica; ausência não aprova.
Não atualizar evidências históricas nem alterar documentos após runner final.

## Coordenação

Implementador possui automation.json (triagem), service/http.rs e
reviewed-findings.md. Coordenador possui demais artefatos desta pasta.
Revisor independente somente lê; validador em contexto separado confere critérios
e executa gate. Quatro slots totais incluindo raiz; término não é presumido
como liberação sem observação. Todos preservam trabalho de terceiros.

Método application-security: ponte sdd/chain/application-security.md, bridge_version 1; entrada .codex/skills/application-security/SKILL.md, SHA-256 3cc0f13461e849cf31e43db95ba3cbcf004881f0b7ade8e7f3dd7cd03d5f884f; contribuição: revisão contextual dos achados e precondições.

Método software-delivery: ponte sdd/chain/software-delivery.md, bridge_version 1; entrada .codex/skills/software-delivery/SKILL.md, SHA-256 c7b5a1b1dc347bf6fe7b6a0d4b40fdf5f64dedbbe75e58543fe670445645fb98; contribuição: variantes de build e identidade de entradas.

Método autonomous-verification-engineering: ponte sdd/chain/autonomous-verification-engineering.md, bridge_version 1; entrada .codex/skills/autonomous-verification-engineering/SKILL.md, SHA-256 684fa71dd9261e7cf97b554bd542b0f1bb9d4a5c3e47b47ee561403f29b69a0e; contribuição: oráculos, evidências atuais e independência.

Método rust: ponte sdd/chain/rust.md, bridge_version 1; entrada .codex/skills/rust/SKILL.md, SHA-256 d027c1be9dbc5164168075427aa90a15a351202eb5021d2433ac0a016addf50a; contribuição: import condicionado e compatibilidade entre features.
