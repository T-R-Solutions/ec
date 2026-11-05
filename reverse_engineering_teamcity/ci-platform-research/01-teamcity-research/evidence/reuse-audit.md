# Auditoria de reaproveitamento

Base preservada: `rea/teamcity-reverse-engineering/`. Não foi reexecutado inventário, ASM, CFR ou catálogo.
`reuse-validation.json` revalida os artefatos de todas as 19 afirmações, inclusive o JAR aninhado de DSL,
e a presença das desassemblagens. Conteúdo estático idêntico permite reaproveitar a análise; não prova execução.
O catálogo antigo registra 412.168 conteúdos distintos de classe, 124 desassemblados seletivamente,
19 afirmações curadas, 546 rotas REST declaradas e 0/14 cenários dinâmicos executados.
1.239 verificações anteriores são resultado histórico, não testes reexecutados nesta entrega.

REA CLI instalado anuncia 6.8.0 (`rea --help` consultado antes de qualquer nova operação).
Não há ferramentas REA MCP expostas nesta sessão. Reaproveitado o bundle real
`../../../teamcity-reverse-engineering/25-evidencias/rea-desktop-bundle.json`.
Evidence de origem: `ev_f3ef0c08f2f33316db8a132d97e0760cdadd12c84153087152f2b37b908dc745`.
É reconhecimento parcial de diretório, com orçamento de metadados atingido; seu digest não é hash integral de cada arquivo.
Nenhuma nova operação de reconhecimento REA é necessária para as perguntas desta fatia.
Os hashes integrais dos JARs são verificados por Python, identificado como ferramenta externa.

Reutilizáveis: claims, desassemblagens, descriptors, fontes SQL catalogadas, índice SQLite de símbolos,
ferramentas de consulta, limitações e evidência REA. Não reutilizar como fatos: nomes de candidatos,
arestas simbólicas como despacho resolvido, Java inválido do CFR, índices sintáticos como entendimento semântico.

Classificações: OBSERVED exige execução de TeamCity; não há nenhuma nesta entrega.
STATIC_CONFIRMED aplica-se somente aos trechos dos artefatos; DOCUMENTED às fontes oficiais;
INFERRED é hipótese explícita; UNKNOWN permanece sem comprovação. Propostas Rust não são afirmações TeamCity.

Perguntas técnicas resolvidas sem nova descompilação: persistência da fila (TC-004),
compatibilidade (D-MATCH), despacho (TC-005/017), ordem de steps (TC-010), conclusão e artefatos (TC-012).
O vínculo transacional completo REST→fila→resultado segue UNKNOWN; a plataforma nova define seu próprio contrato.
