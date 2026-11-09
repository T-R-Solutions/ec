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
