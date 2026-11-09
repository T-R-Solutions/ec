# Alinhamento com a documentação central — 09/11/2026

Escopo: atualizar o consumidor Ec conforme README, consumer-integration e
application da Shared Toolbox. A instalação externa é somente fonte de leitura;
não alterar volumes, seleção central, dependências ou serviços. Preservar os
históricos e mudanças preexistentes. Não executar testes, scripts de verificação,
preparos ou downloads neste pedido, conforme a política do Ec.

Rota completa por mudança na validação dos diagnósticos e seleção de ambiente.
Primária lida/aplicada: software-delivery, ponte 1 em
sdd/chain/software-delivery.md, pacote .codex/skills/software-delivery/SKILL.md,
SHA-256 b0166f50c4532b55cae5ac7e4cde24c35767d56e7db142ea7001402fdd2b94a0.
Contribuição: conferir entradas, contrato nativo, identidade dos dados, fronteira
do consumidor e limites de validação. Não executar avaliações automatizadas.

## Requisitos e decisões

REQ-27/AC-27 da feature scripts-verification delimitam os novos critérios.
D-21: continuar com exec, TOOLBOX_BIN/PATH e configs nativas, mantendo a rejeição
Ec de override vazio. Encaminhar seleções explícitas de Docker e TOOLBOX_STATE
pelas duas camadas de ambiente, sem copiar o ambiente inteiro para ferramentas.

D-22: validar database_metadata do diagnóstico nativo OSV/Trivy: snapshot,
fontes obrigatórias, hashes, semântica e menor timestamp upstream, com limite Ec
de 24h. Não usar prepared_at como frescor nem recibo externo para autorizar scan.
Para Trivy, observar Checks Bundle externo, imagem/versão, mount ro, rede none e
estabilidade de identidade entre scans; manter UpdatedAt/NextUpdate e controles
vuln/misconfig/secret já existentes. Grype conserva sua data nativa da base.

R-21: mudar estado/endpoint explicitamente é seleção do operador, não autorização
para acesso externo ou cópia de credenciais. R-22: diagnóstico com dados ausentes,
antigos, futuros ou inconsistentes deve reprovar; evidência da plataforma não
homologa gates Ec. Schema 2/STIC/export continuam fora do caminho escolhido.

## Tarefas

- T035: inventariar fontes centrais, scripts, consumidores e diferenças; cópias
  anteriores em .local/toolbox-alignment-20261109/previous/ e hashes em before.json.
- T036: ajustar ambiente, validação de dados/Checks Bundle e casos de regressão.
  Inspeção manual; testes serão apenas escritos neste pedido.
- T037: atualizar guias e SDD, inspecionar diferenças e registrar limites, fontes
  e recuperação no relatório. Critério operacional permanece UNVERIFIED sem suíte.
