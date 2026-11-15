# Especificação — adoção de governança Ec

## Contexto

O pedido inicial abrangeu os pacotes fornecidos e documentos docs, seleção/migração
para .codex/skills e adaptação de SDD/AGENTS, seguida de validação. A captura de Ox
é referência de organização, sem impor sua stack ou estado. A manutenção atual
inclui coesão entre essas configurações, sem alterar a seleção ou aprovar eficácia.

## Requisitos e aceitação

- REQ-01: selecionar pacotes completos e rastreáveis de acordo com os documentos Ec e o adicional README.
- AC-01 → REQ-01: inventário reconcilia 71 candidatas, 67 selecionadas, seis condicionais e quatro excluídas, sem recursos internos ausentes ou hashes divergentes.
- REQ-02: adaptar autoridades e contexto ao Ec sem importar estado do Ox nem promover propostas.
- AC-02 → REQ-02: revisão documental preserva Rust/Vue/TS, YAML, PostgreSQL/HTTP e distingue gRPC/SQLite propostos; análise individual cobre candidatas e os cinco documentos.
- REQ-03: manter SDD utilizável e referências de seleção resolvíveis.
- AC-03 → REQ-03: pontes, templates, links, metadados, traceability e estados passam validação estrutural; preparação não conta como instalação.
- REQ-04: demonstrar que verificadores rejeitam falhas relevantes.
- AC-04 → REQ-04: testes em cópias isoladas rejeitam alteração, ausência, identidade/links inválidos, symlinks, escape e conflito; instalação idempotente é verificada em fixture.
- REQ-05: instalar skills no destino solicitado sem sobrescrever conteúdo alheio.
- AC-05 → REQ-05: 67 pacotes completos presentes em .codex/skills, pontes/perfil reconciliados e auditor --require-installed aprovado no repositório real.

## Limites

Sem execução de produto, alteração das cinco fontes documentais originais, publicação
ou mudança de permissão. A exclusão dos ZIPs integrais foi autorizada no pedido
posterior de limpeza e é limitada por REQ-06. Uma restrição de escrita não reduz o
critério AC-05. Acesso pela UI é distinto da instalação filesystem. Retenção e recuperação
seguem a revisão de 11/11/2026 da [ADR-001](../../../docs/decisions/ADR-001-skills-sdd-adoption.md);
menções históricas à baseline não exigem uma cópia permanente.

## Limpeza posterior à instalação — 2026-11-03

- REQ-06: retirar apenas entradas dispensáveis e cache, mantendo fontes pertinentes, instalação, recuperação, consumidores e evidências históricas.
- AC-06 → REQ-06: inventário completo classifica os arquivos; recursos originais das 71 candidatas são preservados e conferidos; skills_to_use e o cache aprovado são removidos; testes e gates correntes passam sem os pacotes antigos; arquivos mantidos fora do conjunto de edição conservam seus hashes.

## Recuperação do instalador — 2026-11-05

- REQ-07: retomar instalação interrompida, preservando conteúdo alheio, fonte de preparação autorizada e auditoria estrita.
- AC-07 → REQ-07: interrupções na cópia, entre pontes e antes/depois do perfil convergem por retry; conflitos de metadados, pacotes, preparação e journal são rejeitados; instaladores concorrentes não escrevem simultaneamente e morte do processo libera o lock. Preparação de testes usa fixtures temporárias, conforme a política de retenção.


## Aceite obrigatório — 14/11/2026

Manutenção de AGENTS, workflow e política de verificação encaminhada à
[feature de aceite obrigatório](../workflow-enforcement/spec.md). Os critérios
históricos desta feature não recebem aprovação automática pela mudança.
