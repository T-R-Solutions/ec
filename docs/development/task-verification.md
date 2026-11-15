# Verificação obrigatória antes do aceite

Esta política substitui em 14/11/2026 o fluxo opcional após pedido de commit,
por instrução explícita de tornar obrigatório o ciclo de trabalho. O pedido de
uma alteração autoriza inspeção, testes pertinentes, correções e reexecuções
locais necessárias. Downloads, publicação e permissões reais do ambiente
continuam exigindo a autorização correspondente. Não perguntar se o usuário
quer testar uma mudança: verificação faz parte da tarefa.

## Ciclo e condição de conclusão

Siga análise → planejamento → implementação → revisão → teste → validação.
Falha exige diagnóstico, correção e nova verificação dos inputs afetados.
Não repetir indefinidamente a mesma execução diante de uma dependência externa
inalterada. Falta de ferramenta, permissão, execução ou evidência mantém o aceite
bloqueado e deve indicar a condição exata de retomada. Não reduzir critérios,
remover testes ou atualizar hashes de resultados antigos para obter aprovação.

Use `bash scripts/automate.sh run` para a suíte padrão. Durante a tarefa,
`--surface` permite executar as superfícies pertinentes, justificadas no plano;
`--check` permite diagnóstico, mas não substitui as superfícies exigidas no aceite.
Testes focados locais, sem downloads, podem ser executados diretamente durante
implementação e correção; registre resultados e limitações. A suíte integral
continua obrigatória antes de commits solicitados. Não há mais opção de pular
verificação para criar commit. Este fluxo não autoriza commit ou publicação.

## Entrada de aceite

Para uma alteração, preparar `workflow.json` junto aos artefatos da tarefa,
conforme o [contrato de evidência](../../sdd/evidence.md), e executar:

```bash
bash scripts/accept-task.sh --task sdd/tasks/NOME --run .local/logs/AAAA-MM-DD/run-ID-automate.results.log
```

Substituir NOME/data/ID pelos valores reais. Para aceite agregado, usar
`--feature sdd/features/NOME`, que exige todos os ACs da feature. `--task` exige
os ACs próprios e relações revisadas, sem aprovar critérios da feature por
consequência. Escopo não pode omitir trabalho necessário ao pedido.
O comando exige etapas e critérios
com evidências atuais, resultados completos das superfícies declaradas,
encerramento e limpeza bem-sucedidos, e inventário atual idêntico ao observado.
Finalize os artefatos antes do último run: editá-los depois invalida o recibo.
Logs expirados pela retenção exigem nova execução para reaceite do registro.
O check global `docs:sdd` usa catálogo: valida schemas/referências sem exigir
freshness de observações históricas. Um PASS de catálogo/estrutura não é aceite.
O inventário de aceite é versionado e inclui governança ativa além das fontes;
recibos anteriores sem essa cobertura não aprovam a garantia atual. Scanners
conservam seus escopos próprios.

O agente deve chamar esta entrada antes de declarar uma alteração concluída.
Se ela rejeitar, continuar as correções autorizadas ou entregar estado bloqueado,
sem declarar conclusão. Perguntas sem alteração exigem fontes e resposta revisada,
mas não criar feature, rodar suíte ou fabricar evidência de implementação.

## Fronteira de proteção

A entrada bloqueia o aceite emitido pela automação local. Não intercepta respostas
do chat, comandos Git diretos ou escritores que possam alterar código e recibos.
Não existe hook de término do agente configurado neste repositório. Para tornar
a regra incontornável nesses canais, o host/CI deve exigir este gate e proteger
validador, critérios e recibos fora da escrita do executor. Não declarar essa
integração externa implantada. Instruções superiores e permissões reais prevalecem.

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
estão no [guia de preparação](verification-tools.md). Aplicam-se o escopo da tarefa
e a autorização específica de downloads definidos acima. O guia registra a atualização automática
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
