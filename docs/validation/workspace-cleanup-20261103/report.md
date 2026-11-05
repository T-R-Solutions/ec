# Auditoria de limpeza do workspace — 2026-11-03

## Escopo e decisão

Pedido: apagar `skills_to_use/` e outros arquivos apenas quando dispensáveis. Foram inventariados todos os 2.183 arquivos, inclusive ocultos, tamanhos, SHA-256 e identidades; não havia symlinks nem diretórios vazios. Os critérios estão em REQ-06/AC-06 da adoção de governança. Estado final: APPROVED; remoção concluída e aceite SDD com evidência atual aprovado.

| Grupo | Decisão e motivo |
|---|---|
| `skills_to_use/` | Retirar os 15 arquivos depois de preservar fontes pertinentes e atualizar consumidores. Os dois ZIPs passaram em CRC e hashes; o pacote Ox inclui estado, ativos e evidências de outro produto, sem uso no Ec. |
| Cache Python da pesquisa | Retirar o `.pyc` de `build_research_v2.py`; fonte preservada, cache regenerável. |
| `.codex/skills/` | Manter: 67 skills, 594 arquivos conferidos contra o perfil. |
| `.local/skill-installation/skills/` | Manter: baseline explícito de recuperação e fixture dos testes; seus 594 arquivos correspondem ao perfil e à instalação. |
| `config/`, `tools/`, `sdd/`, `AGENTS.md`, `README.md` | Manter governança e consumidores; adaptar referências vigentes e verificar novamente a feature afetada. |
| Cinco documentos de entrada em `docs/` | Manter intactos: autoridades e evidências da análise. A entrada README duplicada é um input histórico registrado, não lixo. |
| Evidências de adoção e cópias anteriores | Manter, inclusive duplicatas: caminhos e bytes participam da proveniência de sessões distintas. |
| Pesquisa TeamCity | Manter corpus, SQLite, JSONL comprimido, classes extraídas, Java descompilado, SQL/XML, índices, relatórios, prompts, protótipo e testes. Ausência de referências não provaria inutilidade. |
| Utilitários Java/Python e JARs | Manter: scripts de pesquisa usam `IndexClasses`, `RestIndex`, ASM e CFR. Os `.class` extraídos são evidência; os do diretório tools são executáveis dos utilitários. |
| Logs CFR vazios | Manter: representam saídas de execuções registradas e fazem parte do corpus, mesmo com tamanho zero. |

## Fontes e consumidores

O bundle `config/governance/sources/skills-inputs.zip` guarda bytes originais das 71 candidatas (incluindo quatro excluídas), arquivos Ec de entrada, pontes/métodos SDD de Ox, AGENTS/README e os contextos de autoridade/arquitetura de Ox. O inventário de membros em [pacotes](packages.json) registra o que foi retido ou descartado. O conteúdo descartado não é necessário aos consumidores Ec identificados; não se promete reconstruir os ZIPs completos.

O auditor exige o hash do bundle e confere cada `source_files` original. Testes passam a copiar o bundle pelo perfil de governança, sem depender de `skills_to_use/`. Scripts nas sessões históricas de `skills-adoption/` permanecem como evidência, com seus pré-requisitos originais; o auditor em `tools/governance/` e a nova sessão são os consumidores correntes.

## Método e recuperação

Inventário por varredura de todos os caminhos e SHA-256; busca textual de dependências; leitura dos produtores/consumidores, autoridades, ADR e feature; CRC dos dois pacotes; comparação integral de instalação e baseline. Duplicatas fora das skills foram examinadas: são cópias históricas, a entrada README ou logs vazios. SQLite aberto em modo somente leitura: `PRAGMA quick_check` retornou `ok`; a consulta de `BuildQueueImpl` retornou a classe esperada. Nenhum gerador histórico foi executado.

[Inventário anterior](before.json), [versões editadas anteriores](previous.zip) e [diário](journal.json) permitem comparar a operação. Restaurar somente um arquivo cujo conteúdo ainda corresponda ao hash escrito registrado; preservar alterações posteriores. Não há Git nem transação global. O procedimento recusa caminhos simbólicos e alterações observadas antes de escrever/remover, mas não demonstra proteção contra um processo hostil trocando caminhos no intervalo entre verificação e operação. Não houve ensaio de watchers ou múltiplos agentes.

A análise cobre todos os arquivos do checkout e todos os membros dos ZIPs pela classificação, referências e comparação; não representa revisão semântica linha a linha de milhões de símbolos, nem certeza sobre uso futuro não registrado. Esses dados foram conservados.

## Verificações

A instalação e o SDD com `--require-approved --require-fresh` passaram antes da mudança. Resultados posteriores estão em [checks](checks.json), [24 testes](negative-controls.log), [aceite SDD](acceptance.log) e [revisão](review.md). Limites herdados: eficácia das skills, descoberta na UI e runtime do Ec não são certificados; links ausentes na entrada README original são históricos e foram preservados.

## Resultado observado

Removidos 16 arquivos. somando 115.771.351 bytes. O bundle de fontes retido tem 1.295.948 bytes. A redução líquida do checkout. incluindo novos inventários e evidências. está em [armazenamento](storage.json).

Conferidos 2154 arquivos sem alteração antes da substituição dos dois registros correntes de evidência/convergência; ambos foram preservados em previous.zip. Todos os comandos de checks.json e o aceite --require-approved --require-fresh passaram.
