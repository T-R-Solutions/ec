# Especificação — adoção de governança Ec

## Contexto

O usuário solicita análise exaustiva dos pacotes em skills_to_use e documentos docs, seleção/migração para .codex/skills e adaptação de SDD/AGENTS, seguida de validação. A captura de Ox é referência de organização, sem impor sua stack ou estado.

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

Sem execução de produto, alteração das cinco fontes documentais originais, publicação ou mudança de permissão. A exclusão dos ZIPs integrais, fora do escopo inicial, foi autorizada no pedido posterior de limpeza e é limitada por REQ-06. EROFS mantém AC-05 UNVERIFIED, não reduz o requisito. Acesso pela UI é uma observação distinta da instalação filesystem.

## Limpeza posterior à instalação — 2026-11-03

- REQ-06: retirar apenas entradas dispensáveis e cache, mantendo fontes pertinentes, instalação, recuperação, consumidores e evidências históricas.
- AC-06 → REQ-06: inventário completo classifica os arquivos; recursos originais das 71 candidatas são preservados e conferidos; skills_to_use e o cache aprovado são removidos; testes e gates correntes passam sem os pacotes antigos; arquivos mantidos fora do conjunto de edição conservam seus hashes.

## Recuperação do instalador — 2026-11-05

- REQ-07: retomar instalação interrompida, preservando conteúdo alheio, baseline e auditoria estrita.
- AC-07 → REQ-07: interrupções na cópia, entre pontes e antes/depois do perfil convergem por retry; conflitos de metadados, pacotes, baseline e journal são rejeitados; instaladores concorrentes não escrevem simultaneamente e morte do processo libera o lock.
