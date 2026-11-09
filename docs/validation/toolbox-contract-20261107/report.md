# Configuração do contrato Toolbox/Ec — 2026-11-07

Objetivo: aplicar a divisão solicitada de ambientes/dados da Toolbox e entradas,
regras e políticas do Ec às dez famílias de ferramentas.

## Inputs e método

Leitura das autoridades Ec, workflow, política de verificação, feature
scripts-verification e skill software-delivery; leitura direta dos manifests,
config loader, adapters e docs/offline-security.md em
`/home/rodrigo/Projetos/dev_audit/shared-toolbox`. Nenhuma escrita nesse projeto.
Consulta às fontes oficiais OSV/Trivy vinculadas no contrato operacional.
Backup dos arquivos existentes em `previous.zip`; originais históricos preservados.
Diff textual por conteúdo e leitura manual dos callers/configs, sem depender de Git.

## Resultado implementado

`toolbox.json` configura as dez famílias e seleciona Trivy Docker 0.76.2,
Grype 0.119.0, Bandit 1.8.3 e Syft 1.23.1. Configs YAML próprias mantêm política no
Ec. Validade de bases de 24h acompanha OSV já vigente, com diferentes relógios
explicitados no guia. Nenhum snapshot novo foi inventado ou preparado.

`prepare.sh database` delega update-db para Trivy/OSV/Grype e adota o hash retornado;
`osv-db` permanece compatível. Ferramentas sem base externa são recusadas nesse modo.
Wrappers preservam relatórios, identidade da DB, achados e erros. Syft preserva
SBOM e measurement; sucesso confirma geração de inventário, sem parecer de segurança.

`trivy-toolbox`, `grype`, `bandit`, `syft` e `yara-x` ficam selecionáveis como checks
adicionais security. Catálogo padrão não foi substituído: Trivy Docker disponível
cobre somente vuln, enquanto Trivy nativo padrão continua vuln/misconfig/secret.
Gitleaks history e YARA-X indisponível são bloqueados explicitamente. Regras locais
Opengrep, configuração Ruff/ShellCheck e variante/testes Playwright foram preservados.

Testes de contrato preparados em `scripts/tests/test_shell.py`: seleção adicional,
rejeição de erros/exit Bandit, formato Grype, SBOM vazio/status Syft, inventário dos
três lockfiles Trivy e bloqueio de YARA/histórico ausente. Não executados.

## Limites e retomada

Nenhum teste/script de verificação, Docker, build, preparo, download, commit ou
publicação executado. Inspeção não aprova AC-12 nem os ACs anteriores. A policy do
Ec exige pedido explícito de commit e escolha de executar automate.sh para a suíte.
Downloads requerem autorização específica; não houve pergunta de commit neste pedido.

Faltam imagens/locks/bases reais das novas seleções e observação dos motores com as
configurações nativas. YARA-X exige adapter compartilhado e regras fornecidas pelo
consumidor, incluindo compatibilidade de compilados se usados. Migração completa
do gate Trivy exige preservar e validar misconfig/secret. Checks adicionais não são
executados no catálogo padrão; sua eficácia permanece sem evidência nesta tarefa.

[Contrato e interfaces](../../../scripts/security/toolbox-contract.md).
