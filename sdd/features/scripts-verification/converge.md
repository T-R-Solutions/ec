# Convergência nativa — 08/11/2026

REQ-20–26 → D-20/R-20 → T028–T033. Implementação independente no Ec concluída;
aceite integral REJECTED. Run final run-13bf8eQO, sem filtros: exit2, 24 passed,
12 blocked, 7 error, sem alteração de inputs e sem cancelamento. 80 testes de
contrato e 34 de governança passaram; HTTP/PostgreSQL e Firefox reais passaram.

| Critério atual | Estado | Evidência / limite |
| --- | --- | --- |
| AC-20 | APPROVED | Wrapper/argv/override/exits em contrato; E-NATIVE-CONTRACT |
| AC-21 | APPROVED | Seleção, relatórios, supressões, modos/symlinks e integridade em contrato; E-NATIVE-CONTRACT |
| AC-22 | UNVERIFIED | Comandos/regras implementados, motores sem versões centrais ativas |
| AC-23 | UNVERIFIED | Import/isolamento de dependências implementados; Chromium central não executado. Firefox separado passou |
| AC-24 | UNVERIFIED | Create/resolve/analyze implementados, nenhum banco/SARIF real; runtime Rust/Cargo fornecido; falta ativação/qualificação central |
| AC-25 | APPROVED | Contrato de outcomes/códigos/segredos/sinais/cancelamento tardio; limpeza real Docker ainda pendente em AC-26 |
| AC-26 | REJECTED | E-NATIVE-FULL: requisitos obrigatórios bloqueados e sete erros |

T028–T031: implementação/contratos exercitados. T032: suíte executada, aceite
rejeitado. T033: documentação, evidência e recuperação entregues com limites.
Reabrir T030–T032 para homologar imagens/bases, runtime CodeQL e operações reais
após preparo externo; não instalar fallback ou reduzir limiares. Os critérios
históricos AC-01–19 não foram promovidos por estes testes; seu estado anterior
permanece preservado e as partes substituídas estão identificadas na spec.

[Relatório e retomada](../../../docs/validation/toolbox-native-20261108/report.md),
[recuperação](../../../docs/validation/toolbox-native-20261108/recovery.md).
O binding de conteúdo corresponde às fontes observadas; nenhuma evidência antiga
foi renovada para parecer teste atual. Sem commits, downloads ou manutenção central.

## Histórico preservado

# Convergência — documentação e fronteira Ec em 7 de novembro de 2026

Documentação vigente e `.gitignore` sincronizados com o plano. REQ-13 a REQ-18,
AC-13 a AC-18 e T019–T024 estão registrados; critérios permanecem **UNVERIFIED**.
A implementação ainda usa volumes externos, checks adicionais e Trivy nativo no
padrão, com YARA-X bloqueado. O ignore local não corrige esse comportamento.
Nenhum scanner, teste, preparo ou script de verificação foi executado.

T018 foi substituída como roteiro de retomada por T019–T024; não iniciar preparo
pela CLI compartilhada sob a restrição de escrita apenas no Ec. Retomar por T019,
com redirecionamento de estado via ponte local e testes de fronteira; seguir
[dependências e critérios](toolbox-completion-plan.md). Downloads e verificações
continuam sujeitos às autorizações e à política canônica. A escolha de executar
uma suíte não autoriza mudanças externas.

[Relatório desta revisão](../../../docs/validation/toolbox-docs-20261107/report.md).
Registros e estados anteriores abaixo são históricos, sem renovação de hashes.

# Convergência — contrato Toolbox/Ec em 7 de novembro de 2026

T017 implementada e inspecionada; AC-12 **UNVERIFIED**. Nenhuma imagem/base foi
preparada e nenhum script/teste executado. T018 permanece pendente. AC-01 a AC-11
continuam UNVERIFIED nos inputs atuais; observações históricas abaixo são preservadas.
[Relatório atual](../../../docs/validation/toolbox-contract-20261107/report.md).

# Convergência — gates padrão em 7 de novembro de 2026

## Análise e correções do run-oUebC3O4

O run histórico contém 31 passed, seis error, um findings e cinco blocked;
exit agregado 2, sem drift registrado. Foram corrigidos os defeitos demonstrados
nos testes/Geiger/LCOV e as justificativas Ruff. T015 concluída por implementação
e inspeção; T016 e a execução de T014 permanecem pendentes. Nenhum script foi
executado nesta tarefa. AC-01 a AC-11 continuam UNVERIFIED nos inputs editados.

Licença própria, preparo ShellCheck, ferramentas/provider de cobertura e
Trivy/CodeQL continuam pendentes conforme o
[relatório atual](../../../docs/validation/automation-run-oUebC3O4-20261107/report.md).
As observações abaixo são anteriores às correções e permanecem históricas;
seus arquivos/evidências originais foram preservados em previous.zip.

AC-01 a AC-11: **UNVERIFIED nos inputs atuais**. A implementação e os testes foram
preparados e inspecionados manualmente; não houve execução de scripts, preparos
nativos ou motores neste pedido. O lockfile foi atualizado por npm com
--package-lock-only --ignore-scripts; isso não comprova build nem cobertura.

T011/T012/T013 registram implementação, não aceite executado. T014 permanece
pendente do fluxo de commit e resposta explícita sim. A observação histórica de
Deny reprovado por licenças é preservada; não é uma execução nos inputs atuais.
A cobertura dos três componentes permanece desconhecida.

[Relatório, inputs e backup](../../../docs/validation/default-quality-20261107/report.md).
Retomada: preparar dependências/ferramentas/bases/bancos conforme o contrato nativo;
quando houver autorização no fluxo vigente, executar a suíte completa e analisar
os resultados sem reduzir os gates. Ferramenta/pack/base ausente bloqueia.

## Convergência anterior, preservada como histórico

# Convergência — 7 de novembro de 2026

AC-01/02/03/05/06/07/08 permanecem APPROVED pela inspeção dos contratos,
43 testes de scripts, 34 de governança, 23 da plataforma e execução real dos
scanners conforme o [relatório](../../../docs/validation/tooling-remediation-20261106/report.md).

AC-04 está REJECTED: todas as ferramentas executaram, mas Cargo Deny reprova os
11 crates privados Ec sem licença definida. Advisories/bans/sources passaram;
nenhuma exceção de licença foi aplicada. T010 conclui a correção das integrações,
a triagem e a coleta; T004 continua dependente da decisão sobre licença/escopo.
AC-01/AC-07 da primeira jornada permanecem UNVERIFIED por suas lacunas próprias.

A observação anterior está preservada em previous-evidence.zip do relatório.
Retomada: resolver a política de licença dos membros privados, executar Deny nos
dois workspaces e atualizar apenas as evidências afetadas. Não confundir sucesso
dos scanners ou integração com aceite agregado do produto.

## Convergência de 08/11/2026 — estrutura migrada, operação bloqueada

REQ-19 → D-19/R-19 → T025/T026 → scripts/config, wrapper comum, stacks e recibos.
T027 depende da plataforma e execução autorizada. AC-19: UNVERIFIED para aceite
operacional; implementação local e exclusões foram inspecionadas. AC-01–AC-18
continuam UNVERIFIED nos inputs atuais. Não foram executados automate.sh, testes,
preparos, downloads ou scanners. Histórico anterior preservado em
[relatório](../../../docs/validation/scripts-stic-20261108/report.md) e previous-*.

## Limpeza de 09/11/2026 — inspeção, sem execução da suíte

T034 e critérios L01–L05 estão registrados no
[relatório da limpeza](../../../docs/validation/cleanup-20261109/report.md).
Os consumidores vigentes e a integração nativa foram inspecionados. Foram
preservados código de automação, contratos opcionais, regras/fixtures, testes,
configs, logs e históricos. Exclusões têm inventário, cópias, precondições e diário.
O guia temporário erase.md saiu da raiz e foi arquivado integralmente como histórico.
Os estados anteriores permanecem datados; esta limpeza não reexecuta nem aprova
AC-01–AC-26, cuja validação nos inputs alterados permanece UNVERIFIED.

## Adequação ao contrato central — 09/11/2026

REQ-27 → D-21/D-22, R-21/R-22 → T035–T037. Ambiente de Docker/estado,
database_metadata upstream e Checks Bundle externo foram implementados e
inspecionados conforme os guias centrais. Casos de regressão escritos, não
executados. AC-27 permanece UNVERIFIED; os critérios operacionais anteriores
não são renovados. [Relatório e recuperação](../../../docs/validation/toolbox-alignment-20261109/report.md).

## Revisão de obsolescência em scripts/ — 09/11/2026

T038 conclui a retirada documental justificada no
[relatório](../../../docs/validation/scripts-cleanup-20261109/report.md).
Os arquivos operacionais mantêm os mesmos bytes; conteúdo histórico foi
preservado fora do guia corrente. Nenhum critério operacional foi renovado;
AC-27 continua UNVERIFIED, sem execução de testes ou scripts de verificação.
