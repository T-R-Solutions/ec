# Plano incremental e gates

1. Núcleo testável: tipos, validação JSON, um job e steps, transições e chaves idempotentes. Gate A01/A02 e domínio puro.
2. Persistência: migrações PostgreSQL, API de submissão/consulta, fila SQL, reserva e leases.
   Gate A03–A07 com duas instâncias de scheduler e falhas de commit. Não usar mocks como prova de atomicidade.
3. Agente confiável: handshake, poll/start, checkout fixo, processo limitado, logs numerados e artefatos.
   Gate A08–A10 e build de ponta a ponta em host descartável. Este é o primeiro núcleo funcional do produto.
4. Confiabilidade: cancelamento, deadlines, perda/retry explícito, quotas, backup e retenção.
   Gate A11–A14; primeiro piloto somente após todos gates CORE. Sem credenciais reais nos fixtures.
5. Ampliação: DAG, múltiplos agentes, fairness, triggers Git e relatórios de testes. Gate A15–A17.
6. Workloads não confiáveis: isolamento validado no host e pools por confiança antes de habilitar uso hostil (A13/R18).
7. Capacidades opcionais: cache confiável, extensões externas, UI, matriz, condições e tuning após benchmarks.
   Gate A18/A19 e metas medidas. Multisserviços/HA apenas quando limites do monólito forem demonstrados.

Nenhuma etapa exige recuperar todos os métodos TeamCity. Reabrir investigação somente para dúvida que altere requisito/ADR.
Entregas deste prompt são pesquisa, especificação, arquitetura, contratos, plano e validação de modelo;
o servidor/agente de produção ainda precisa das etapas acima.
