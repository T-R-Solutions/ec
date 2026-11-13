# Plano de mesclagem — 11/11/2026

## Escopo e decisão

O pedido atual autoriza implementar o valor identificado nas 11 skills Ox e
atualizar SDD, AGENTS.md e documentação. Substitui a limitação anterior de somente
propor a mesclagem. Rota local de manutenção da feature governance-adoption:
conteúdo de método e metadados, sem mudar contratos de produto, catálogo de
identidades ou ferramentas de instalação/auditoria.

Primária: skill-creator, `/home/rodrigo/.codex/skills/.system/skill-creator/SKILL.md`.
Os quatro pacotes de destino e suas referências são objetos da revisão; lê-los
não significa executar jornadas de UI ou seus testes. A procedência e os hashes
serão registrados junto ao resultado. Não há delegação ou nova instalação.

Alternativas: copiar 11 novas skills duplicaria gatilhos e importaria dependências
Ox; acrescentar o pacote inteiro a uma referência sobrecarregaria o contexto.
Escolha: duas referências novas em design-system, uma em frontend-testing e
extensões curtas em referências já existentes de design-review e layout. Os
entrypoints passam a encaminhar somente problemas pertinentes.

## Incrementos e critérios de inspeção

1. Comparar fontes e referências existentes; identificar acréscimo, redundância
   e rejeição para cada uma das 11 entradas. Preservar os bytes anteriores.
2. Preparar candidatos: rastreabilidade de tokens, espécimes, estados incertos,
   crítica visual controlada, restrições geométricas e manifesto de captura.
   Manter nomes, descrições, UI metadata e regras de autorização existentes.
3. Revisar diffs e links internos; aplicar somente com precondições de conteúdo.
   Sincronizar os quatro pacotes com baseline; preservar fontes primárias e
   acrescentar cópia exata do ZIP como fonte suplementar no perfil schema 2.
4. Atualizar adaptations/files somente dos quatro pacotes. Atualizar documentos
   de entrada e SDD, mantendo critérios não executados como UNVERIFIED.

Critérios: acréscimos mudam decisões concretas; nada exige fonte/cor/framework,
script, número de casos ou aprovação exclusiva do Ox; referências se resolvem
no pacote; os registros permitem distinguir origem, adaptação e resultado atual.
O hash do arquivo não comprova eficácia. Não haverá execução de validadores,
testes ou quick_validate sem o fluxo autorizado de commit.

## Riscos e tratamento

- Duplicação: acessibilidade, formulários e cache já têm métodos suficientes;
  registrar reaproveitamento sem acrescentar instruções redundantes.
- Perda de procedência: manter origin/source_files históricos; suplemento entra
  como source_archive e mapa explícito de membros e destinos.
- Divergência instalação/baseline: preparar conteúdo único e comparar os hashes
  anteriores antes da escrita; backup permite recuperação com revisão de conflito.
- Escrita na pasta protegida: aplicar o candidato revisado por comando específico
  com a permissão real do ambiente. Autorização funcional vem do pedido atual.
- Falsa aprovação: preservar evidence.json anterior e não promover gates sem
  execução. A inspeção editorial tem escopo próprio e documentado.
