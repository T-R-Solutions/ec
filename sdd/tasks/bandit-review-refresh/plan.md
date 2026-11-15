# Plano

D-01: reexaminar os 16 pontos em scripts/tests/test_automation_config.py, scripts/tests/test_shell.py e tools/quality/automation_config.py; atualizar somente os três registros tools.bandit.triage em scripts/config/automation.json.
R-01: atualizar hashes sem revisão poderia ocultar mudança perigosa. Inspeção de dados/argv, contexto de fixtures, exceções específicas e revisão independente são obrigatórias; não adicionar exclusões ou nosec.

Skill primária application-security, lida em .codex/skills/application-security/SKILL.md: rastrear entradas até subprocess/limpeza/inventário e distinguir fixtures de controles reais. Sem nova decisão de produto; rota local delimitada com coordenação alta conservadora pela classificação de segurança.

O orquestrador é único escritor da configuração e deste registro. Revisor independente somente leitura. Validador independente recebe recibo final. Capacidades observadas: perfis, contexto novo, mensagens e limite quatro; nenhum close exposto.

Verificação: testes existentes de triagem e execução nativa por `bash scripts/automate.sh run --check bandit`; depois estabilizar documentos e executar `bash scripts/automate.sh run --surface docs --surface scripts`. As superfícies cobrem configuração e contratos da automação; o check nativo adicional cobre o scanner afetado. Segurança integral usa ferramentas/bases alheias a esta mudança, portanto não é superfície exigida. Nenhum teste é removido e a seleção Bandit conserva todos os alvos.
