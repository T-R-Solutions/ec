# Convergência — correções de governança

Observações de 14/11/2026 no checkout local Ec. Esta tarefa preserva os ACs e
as pendências das features relacionadas. Revisão/catálogo não renovam evidências
históricas nem demonstram eficácia comportamental de modelos.

## Observações coletadas

- Implementação 1–3: 104 testes focados aprovados em 35,065 s, exit 0;
  `.local/logs/2026-11-14/governance-corrections-focused-da256f74.log`,
  com inputs observados no arquivo adjacente `.inputs.log`.
- Templates/links SDD: nenhuma falha, exemplos permanecem UNVERIFIED;
  `.local/logs/2026-11-14/governance-corrections-template-e94c68fb.log`.
- Revisão independente 1–4/11: sem achados no código/docs; 104 testes próprios e
  adversos de recibo/inventário confirmados. Relato em
  `.local/logs/2026-11-14/governance-corrections-review-code.log`.
- Capacidade real: raiz + coordenador + dois especialistas; após término do
  implementador, criação de revisor independente por perfil foi bem-sucedida.
  Liberação automática de vaga observada; nenhum comando close disponível.

- Auditor/pacotes após aplicação: PASS, 67 selecionadas e 599 arquivos; 40 testes
  aprovados em 54,301 s, inputs inalterados; logs `run-7768777d40db-skills-final.*.log`.
- Revisão consolidada dos onze itens: sem achados pendentes, 77 inputs atuais;
  `.local/logs/2026-11-14/governance-corrections-final-review.log`.
- Conferência final de templates/links contra o validador estabilizado: nenhuma
  falha em `.local/logs/2026-11-14/governance-corrections-final-template.log`.

## Correções e limites observados

A revisão encontrou dois defeitos corrigidos antes do aceite: perfis de validador
com --feature fixo (passaram a remeter à escolha canônica --task/--feature) e
parser que aceitava descrição divergente com delimitador inline. A cobertura
inclui também rejeição de continuação YAML fora do contrato de identidade de
uma linha. O resultado reprovado foi preservado separadamente no log
`governance-corrections-review-metadata-rejected.log`; não teve hashes renovados.

A aplicação protegida foi encaminhada pela raiz após duas chamadas do especialista
sem retorno, interrompidas e verificadas sem efeitos. A execução autorizada na
raiz retornou exit 0; os 28 arquivos ativos coincidiram com o candidato revisado.
O registro atual está em
`.local/logs/2026-11-14/governance-corrections-protected-application.log`.
O profile atualiza hashes de catálogo dos bytes instalados, sem alterar fontes
originais ou evidências de features anteriores.

Os critérios e vínculos atuais ficam em evidence.json. O recibo do runner e o
gate ficam fora dos inputs deste documento para evitar ciclo de hashes. Nenhuma
conclusão de alteração é emitida sem o gate atual aprovado.

Limites: revisão e fixtures não comprovam eficácia de modelos, custo/latência,
comportamento de toda equipe, nem proteção externa de chat/Git/CI. Aceitar esta
tarefa não aceita integralmente as features relacionadas. Logs seguem retenção
de duas datas; reaceite após expiração exige novas observações.
