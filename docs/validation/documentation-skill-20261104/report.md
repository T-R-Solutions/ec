# Refatoração de technical-documentation

Data: 2026-11-04. Rota local de manutenção de método documental, sem novos contratos de produto.

## Pedido e decisão

O usuário solicitou Mermaid e documentação visualmente elaborada com gráficos, fluxos, imagens e iconografia de nível enterprise, traduzindo adjetivos em critérios verificáveis. A [entrada da skill](../../../.codex/skills/technical-documentation/SKILL.md) mantém as garantias textuais e passa a orientar narrativa visual. Duas referências separam [qualidade mensurável](../../../.codex/skills/technical-documentation/references/visual-quality.md) de [Mermaid e assets](../../../.codex/skills/technical-documentation/references/mermaid-and-assets.md). Metadados de invocação, ponte, perfil e baseline acompanham a alteração.

Beleza corresponde a hierarquia, alinhamento, espaçamento, legibilidade e zero cortes/sobreposições. Enterprise corresponde a semântica correta, iconografia vetorial especializada/coerente/licenciada, rastreabilidade, reprodução e acessibilidade. Moderno corresponde a fontes editáveis, vetores, adaptação ao destino/tema e tokens. Tamanhos, grade e limiar de complexidade são convenções ajustáveis da skill; contraste é fundamentado em W3C, sem alegar conformidade integral. O método exige fonte Mermaid e prevê exportação profissional quando o host não suporta seus ícones; fluxo genérico bruto não satisfaz esse briefing.

## Integridade e escopo

Os 1.426 inputs anteriores foram conferidos sem divergência antes da preparação. O [escopo](scope.json) identifica precondições e hashes dos arquivos revisados. [previous.zip](previous.zip) preserva os arquivos anteriores e a evidência vigente. O bundle de originais e todos os source_files permanecem intactos. Pacote instalado e baseline são atualizados para quatro arquivos cada; o total instalado passa de 594 para 596 arquivos em 67 skills. Não há mudança de seleção, contagem de skills ou permissão implícita de publicação.

O candidato é preparado em /tmp, validado em cópia isolada e aplicado somente com a permissão de filesystem exigida para .codex. Escritas verificam precondições; não há promessa de transação multi-arquivo ou proteção contra escritor hostil. Recuperação usa o backup após comparar hashes atuais; não sobrescrever trabalho posterior.

## Verificação e critérios

A revisão semântica examina casos contrastantes: arquitetura sem suporte de ícones exige fonte/editável e exportação apropriada; sequência preserva notação e caminhos de falha; gráfico sem dados não inventa medições; correção pontual não força infografia; ausência de renderizador impede declarar aprovação visual; critério editorial ajustável não se apresenta como norma WCAG. É inspeção do método, não teste independente com usuários ou simulação de execução por outro agente.

Os [checks](checks.json) registram validação da skill, integridade do catálogo, preflight, links/estrutura e 24 testes existentes no checkout real. A validação do candidato em cópia está em [candidate-checks.json](candidate-checks.json). Os [gates](gates.json) registram o vínculo atual de evidências. AC-01/05 são reobservados por auditor/preflight; AC-02 por revisão das normas e preservação dos contratos; AC-03 por links, metadados e estrutura; AC-04 por testes negativos existentes; AC-06 por preservação de fontes/limpeza e comparação dos inputs, sem repetir remoções.

## Fontes externas e limites

Consultadas em 2026-11-04: [Mermaid: ícones](https://mermaid.js.org/config/icons.html), [arquitetura](https://mermaid.js.org/syntax/architecture.html) e [acessibilidade](https://mermaid.js.org/config/accessibility.html); W3C [contraste de texto](https://www.w3.org/WAI/WCAG22/Understanding/contrast-minimum.html) e [não textual](https://www.w3.org/WAI/WCAG22/Understanding/non-text-contrast.html). São fontes dos critérios especificados, não resultados do produto Ec.

Skill primária aplicada: skill-creator, com caminho/hash em plan.md. Seu quick_validate confere estrutura; não demonstra eficácia. Não foram instalados Mermaid CLI, bibliotecas de ícones ou navegador. Nenhum novo diagrama/imagem foi produzido para a aplicação; o pedido altera a skill. Renderização do exemplo Mermaid, acessibilidade real, aderência de futuros outputs e descoberta pela UI permanecem UNVERIFIED. Não houve delegação, commit, deploy ou publicação.
