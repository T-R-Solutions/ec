# Conclusão operacional STIC — trabalho em andamento

Pedido atual autoriza completar a integração e realizar downloads necessários.
A solicitação anterior de limpeza não proíbe preparar ferramentas na plataforma
externa; o Ec permanece consumidor. Não declarar conformidade pelo bloqueio de gates.

- Primária: software-delivery, já lida nesta conversa; manter requisitos de identidade,
  preparo explícito, execução offline, resultados e critérios não enfraquecidos.
- Plataforma candidata: `/tmp/ec-stic-platform-work`, cópia sem volumes ou Git.
  Escrita externa depende da permissão real e ocorrerá somente após revisão/testes.
- Docker: diagnóstico fora do sandbox confirmou Engine 29.7.2-ce linux/amd64.
- STIC: schema 2, políticas tipadas, stage/manifesto, eventos e export implementados
  na candidata; 59 testes da plataforma passaram. Isso ainda não prova scans reais.
- YARA-X: release 1.0.0 oficial tem asset Linux `.gz` contendo tar, hash conferido;
  ajuda e saída JSON observadas com fixture fora do Ec. Adapter candidato criado.
- Testes Ec: pergunta pendente sobre exceção ao fluxo de commit do AGENTS.md;
  nenhum script de verificação Ec executado nesta etapa.

T028: completar contratos públicos e rejeições da plataforma, sem políticas Ec no núcleo.
T029: trocar gate bloqueante por fluxo público stage/run/export; migrar políticas e oráculos.
T030: preparar imagens/plugins e adotar snapshots atuais por identidade, com downloads autorizados.
T031: executar verificações autorizadas, corrigir falhas e registrar limites concretos.

Pendências abertas: Trivy completo com redação antes de persistir, CodeQL, regras
YARA do consumidor, equivalência de inventário, lifecycle de preparo, testes reais,
configuração/lock/bases atuais e validação completa. Não há aprovação operacional.
