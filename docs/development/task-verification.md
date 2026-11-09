# Verificação opcional após pedido de commit

Esta é a política canônica do Ec para SDD e skills. Scripts de verificação não são
acionados automaticamente durante implementação, revisão ou encerramento de tarefas.
Inspecione as alterações e registre resultados e lacunas; a execução depende do
fluxo abaixo, somente após pedido explícito de commit.

## Pergunta obrigatória

Após o pedido de commit, apresente exatamente:

> Deseja executar o script automate.sh (que roda todos os outros scripts)?

Ofereça somente duas respostas: `sim` e `não`. Aguarde a escolha; silêncio ou opção
pré-selecionada não constituem resposta. Faça a pergunta para o pedido de commit
atual, sem repetir a escolha para cada commit do mesmo pedido.

- `não`: crie normalmente os commits solicitados, sem executar scripts de verificação.
  Registre que a suíte não foi executada por escolha do usuário; isso não é erro nem
  impedimento para o commit e não equivale a aceite técnico das features.
- `sim`: execute, da raiz, a suíte completa pela única entrada abaixo. Só crie os
  commits se todos os scripts da suíte terminarem sem erros. Qualquer erro, ferramenta
  ausente, interrupção ou execução incompleta bloqueia os commits. Corrija problemas
  dentro do escopo autorizado e execute novamente a suíte completa antes de criar
  commits; se não puder resolvê-los, relate o bloqueio sem criar commits.

```bash
bash scripts/automate.sh run
```

Não usar `--surface` ou `--check` nesse fluxo, nem substituir o runner por scripts
avulsos, validadores SDD ou comandos de teste indicados em skills, templates ou guias.
Exemplos de comandos nessas fontes descrevem interfaces; não autorizam acionamento
fora deste fluxo. A regra trata de scripts de verificação, sem impedir leitura,
inspeção manual, edição de arquivos ou operações Git solicitadas.

A execução cobre todas as superfícies padrão configuradas no runner. Opções extras
não incluídas no catálogo padrão não passam a ser executadas por esta instrução.
O runner resolve a raiz pela própria localização; para caminhos com espaços, use
`bash "/caminho com espaços/ec/scripts/automate.sh" run`. Não depende de hooks e não
instala hooks. Esta política orienta o agente; não altera a CLI do runner.

## Suíte e critérios vigentes — execução nativa

O [guia de scripts](../../scripts/README.md) registra seleção, comandos e parsers.
A integração nativa substitui o bloqueio por capacidades STIC/layout/staging/export.
Indisponibilidade operacional continua bloqueando e não aprova critérios.

- Governança: auditores, instalação, SDD e seus testes.
- Automação: sintaxe, testes de contratos, Ruff e ShellCheck centrais obrigatórios.
- Server/Agent: Cargo fmt, Clippy, testes, machete, deny, audit, geiger e cobertura >=80%, Rust 1.99.1/locked/offline e targets separados.
- Frontend: npm typecheck, test, build e cobertura >=80%, com dependências do lockfile preparadas.
- Segurança: Gitleaks/Opengrep e eficácia de regras, OSV nos três locks, Trivy vuln/misconfig/secret e CodeQL nos três componentes.
- Complementares opcionais: Grype, Bandit, Syft e YARA-X. Syft é medição. O antigo trivy-toolbox foi retirado após unificação do gate completo.
- Integração/E2E: builds com failpoints, harnesses reais, Playwright central com API simulada e jornada Firefox separada.

Linux, Bash, jq, coreutils, util-linux e Python stdlib são pré-requisitos de
supervisão. Ferramentas centrais precisam estar qualificadas/ativas e suas bases
atuais. Cargo/npm e dependências de aplicação pertencem ao Ec. Os detalhes de
RustSec, database_metadata OSV/Trivy, Checks Bundle externo Trivy, seleção de
ambiente/estado, runtime CodeQL Rust, PostgreSQL e Firefox
estão no [guia de preparação](verification-tools.md). Executar um gate não concede
autorização para instalar scanner/dependência, atualizar base ou ampliar capacidade
do host. O guia registra a atualização automática OSV observada na Toolbox externa;
esse efeito exige autorização específica ou uma interface que permita impedi-lo.

## Evidências e limites

Runs privados ficam em `.local/automation/runs/run-*/`, com logs separados,
hashes de entradas, resultados e classificação Ec. Cada chamada nativa tem
diagnóstico próprio, código original, versão, imagem, base e limpeza observados.
O supervisor não persiste argv ou valores de credenciais. Relatórios inválidos,
parciais, suprimidos, cancelados e limpeza falha nunca aprovam. A agregação mantém
2 (erro) > 3 (bloqueio) > 1 (achados) > 0 (gates aprovados/medições concluídas).
Medição Syft não constitui parecer de vulnerabilidade.

Inspeções/mocks não aprovam eficácia real. Nenhum resultado histórico foi renovado
para aparentar uma execução atual. O [registro vigente](../validation/toolbox-native-20261108/report.md)
identifica comandos executados, resultados e lacunas; a feature SDD mantém seus
critérios abertos até evidência suficiente.

## Autorização específica da migração — 08/11/2026

O pedido atual autoriza implementação e testes necessários, inclusive
`bash scripts/automate.sh run` sem filtros, sem vincular a execução a um pedido de
commit. Substitui exclusivamente essa condição para esta tarefa. A autorização
não inclui downloads implícitos, commits, tags, publicação, mudanças de permissões
do host, alterações no Ox ou implementação/configuração/dados operacionais
centrais. As instruções anteriores de pergunta de commit continuam válidas fora
desta autorização específica.
