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

Ao escolher `sim`, o usuário autoriza as ações necessárias à execução e à resolução
dos problemas encontrados nessa mesma tarefa, exceto downloads. Não repetir
confirmações para correções e reexecuções já abrangidas; downloads exigem autorização
específica e as permissões reais do ambiente continuam aplicáveis. Essa autorização
não implica publicação ou trabalho externo à tarefa de verificação.

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
- Server/Agent: Cargo check/fmt, Clippy, testes, machete, deny, audit, geiger instalados no host e cobertura >=80%, toolchain Rust ativo/offline e targets separados. Por instrução de 11/11/2026, Cargo.lock pode ser atualizado durante as verificações, sem `--locked`; alterações ficam registradas e exigem reexecução para comprovar a resolução final.
- Frontend: npm typecheck, test, build e cobertura >=80%, com dependências do lockfile preparadas.
- Segurança: Gitleaks na árvore e no histórico Git, Opengrep e eficácia de regras locais, OSV nos três locks, Trivy vuln/misconfig/secret nas fontes e configurações do Ec e CodeQL nos três componentes e na automação Python. Licenças Trivy têm inventário próprio, sem parecer jurídico implícito.
- Complementares opcionais: Grype, Bandit, Syft e YARA-X. O check Syft exige inventário válido e análise Grype do inventário Ec sem achados. YARA-X usa o catálogo versionado com contexto por arquivo e sentinelas locais. O antigo trivy-toolbox foi retirado após unificação do gate completo.
- Integração/E2E: builds com failpoints, harnesses reais, Playwright central com API simulada e jornadas reais Chromium e Firefox também pelo Playwright no Docker.

Linux, Bash, jq, coreutils, util-linux e Python stdlib são pré-requisitos de
supervisão. Ferramentas centrais precisam estar qualificadas/ativas e suas bases
atuais. Cargo/npm e dependências de aplicação pertencem ao Ec. Os detalhes de
RustSec, database_metadata OSV/Trivy, Checks Bundle externo Trivy, seleção de
ambiente/estado, runtime CodeQL Rust, PostgreSQL e Firefox
estão no [guia de preparação](verification-tools.md). Aplicam-se o alcance da escolha
e a exceção de downloads definidos acima. O guia registra a atualização automática
OSV observada na Toolbox externa e o preparo Trivy/Grype anterior ao scan;
esse efeito exige autorização específica ou uma interface que permita impedi-lo.

## Evidências e limites

Logs privados ficam em `.local/logs/AAAA-MM-DD/`, somente nas duas datas mais
recentes. Saídas brutas e registros estruturados de hashes, resultados, relatórios nativos redigidos quando necessário e
classificação Ec são logs por execução/ferramenta; artefatos intermediários
são descartados. Consulte [retenção](local-state.md). Cada chamada nativa tem
diagnóstico próprio, código original, versão, imagem, base e limpeza observados.
O supervisor não persiste argv ou valores de credenciais. Relatórios inválidos,
parciais, suprimidos, cancelados e limpeza falha nunca aprovam. A agregação mantém
2 (erro) > 3 (bloqueio) > 1 (achados) > 0 (gates aprovados/medições concluídas).
A geração Syft isolada não constitui parecer de vulnerabilidade; `security:syft`
acopla a análise Grype do mesmo SBOM para emitir aprovação de vulnerabilidades.

Inspeções/mocks não aprovam eficácia real. Nenhum resultado histórico foi renovado
para aparentar uma execução atual. A [evidência da feature](../../sdd/features/scripts-verification/evidence.json)
identifica observações e lacunas vigentes; os relatórios datados preservam os
resultados de cada execução. Critérios permanecem abertos até evidência suficiente.

## Autorizações históricas

A exceção de execução concedida à migração de 08/11/2026 está registrada na
[feature de scripts](../../sdd/features/scripts-verification/spec.md).
Ela não autoriza novas tarefas. Instruções explícitas da sessão vigente prevalecem
sobre este fluxo, dentro do escopo concedido e das permissões reais do ambiente.
