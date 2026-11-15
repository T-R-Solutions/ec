# Execução e retomada

Comece pelo [AGENTS.md](../AGENTS.md) e pelo [contexto](project.md).
Prossiga com ações locais reversíveis autorizadas; esclareça apenas ambiguidades
materiais, conflitos ou efeitos que dependam de autorização ainda ausente.

## Escolher a rota

Use a **rota local** quando objetivo, critérios e consumidores forem conhecidos
e a mudança for delimitada e recuperável, sem decisão material sobre contratos,
permissões, integridade, concorrência ou operação. Documentação extensa pode
seguir esta rota; tamanho da alteração não decide seu risco.

1. Inspecione instruções, alterações existentes, contratos, consumidores e checks pertinentes.
2. Implemente a mudança e examine o resultado conforme a
   [política de verificação](../docs/development/task-verification.md).
3. Registre objetivo, arquivos/versões observados, método, resultado e lacunas.
   Para alterações, mantenha os artefatos mínimos exigidos pelo gate de aceite,
   mesmo na rota local; use um registro delimitado em `sdd/tasks/<id>/` quando
   o objetivo não for aceitar todos os critérios da feature. O conteúdo pode
   ser curto, mas a rota local não dispensa etapas ou evidências.

Use a **rota completa** quando houver decisão material em aberto ou mudanças de
contratos, autorização, persistência, concorrência, coexistência de versões ou
recuperação que exijam coordenação. Ao surgir esse risco, amplie os critérios e
artefatos necessários preservando o trabalho já realizado.

Em qualquer rota, registre as relações com as features afetadas e atualize seus
artefatos quando o contrato mudar. Uma tarefa tem critérios próprios e mantém
explícita sua cobertura parcial; seu aceite não aprova as features relacionadas.
Não reduza ou mova critérios para escapar da verificação. O escopo e as pendências
exigem revisão independente. Consulte o [contrato de escopo](evidence.md).

## Especificar

Identifique autoridades, estado observado e contratos existentes. Escreva ou
ajuste `spec.md` com atores, invariantes, cenários normais e rejeitados, critérios
identificáveis e limites. Separe requisitos, hipóteses e exemplos; esclareça
lacunas impeditivas enquanto avança no trabalho independente.

## Planejar

Compare alternativas proporcionais e relacione decisões/riscos aos requisitos
em `plan.md`. Selecione métodos conforme o [contrato das pontes](chain/README.md),
sem importar o catálogo. ADRs, pesquisas e modelos adicionais são necessários
somente quando resolvem uma questão real.

## Implementar

Em `tasks.md`, decomponha mudanças e verificações em IDs, dependências, arquivos
e condições de conclusão. Confira coerência com spec e plan antes de editar.
Use os [templates](templates/README.md) para spec, plan, tasks, traceability,
evidence schema 2 e workflow; não representam aceite prévio.
Comandos precisam existir no projeto; referências a testes não autorizam execução.

Aplique o método selecionado e consulte apenas referências pertinentes. Mantenha
políticas nas fontes canônicas. Registre falhas e observações sem alterar requisitos
silenciosamente para fazer um resultado passar.

## Convergir e retomar

Relacione requisito → decisão/risco → tarefa → mudança → evidência → aceite.
O [contrato de evidência](evidence.md) define os cinco estados, rastreabilidade,
vínculos por conteúdo e limites da automação para ambas as rotas.

Ao retomar, leia spec, plan, tasks e a convergência mais recente; compare os
arquivos atuais com os inputs observados. Reexamine somente observações afetadas
e preserve os registros anteriores conforme a política de retenção, sem criar
backups ou renovar hashes para reutilizar um aceite antigo.

Se faltar execução, registre a tarefa e a condição exata para retomá-la. Se faltar
capacidade indispensável, mantenha o critério não verificado e indique o bloqueio.
Concluir depende dos critérios aplicáveis e de lacunas explícitas, não da existência
de documentos, do fim do contexto ou de uma ferramenta retornar sem erro.


## Encerramento obrigatório — 14/11/2026

Análise, planejamento, implementação, revisão, teste e validação são obrigatórios
para alterações. Registre as cinco etapas do gate (`validation` inclui teste e
comparação com critérios), execute a entrada de aceite da política canônica e
só declare conclusão com aprovação atual. Revisão examina contratos, consumidores
e critérios; teste executa cenários; validação decide com base nos resultados.
Finalize documentos e observações, encerre escritores e execute o runner das
superfícies justificadas. Passe o recibo real ao validador para o gate. Qualquer
entrada alterada exige nova observação/verificação afetada e novo recibo.
Falhas voltam à análise/correção pertinente, sem reiniciar trabalho válido.
Pedido de status não cancela a tarefa. Bloqueio não equivale a conclusão.
