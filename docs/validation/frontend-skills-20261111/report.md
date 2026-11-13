# Mesclagem de métodos de design — 11/11/2026

## Análise e escopo

O pedido posterior à entrega visual autoriza a implementação da mesclagem e a
atualização do SDD, AGENTS.md e documentação. A análise anterior em
[skills-review.md](../frontend-signal-20261111/skills-review.md) permanece histórica:
naquela entrega só houve proposta. Este registro identifica a alteração posterior.

Foram comparados os 11 SKILL.md do ZIP original com os entrypoints e referências
atuais de design-system, design-review, css-layout-engineering, frontend-testing
e referências de acessibilidade. A proposta inicial de três referências foi
refinada: duas referências em design-system, uma em frontend-testing e pequenas
extensões em referências existentes de review/layout. O [plano](plan.md) antecede
a aplicação. Rota local sobre governance-adoption; métodos e metadados de quatro
pacotes, sem nova seleção de skills ou alteração dos validadores.

## Destino de cada entrada

| Entrada analisada | Decisão implementada | Valor adicional / motivo |
| --- | --- | --- |
| ox-design-brief | design-review/references/review-method.md | Brief proporcional com hipótese e condições de comparação; sem reunião ou aprovação artificial |
| ox-critique | mesma referência | Duas leituras e achado associado a região, fixture, impacto e risco; sem quota de intervenções |
| ox-layout | css-layout-engineering/references/responsive-shells-and-overflow.md | Prioridade da área principal, restrições pelo conteúdo e reabertura do painel antes de compactar |
| ox-polish | design-system/references/typography-and-finish.md | Baseline, contorno funcional e hover sem deslocamento, depois dos problemas estruturais |
| ox-chromatic-refinement | design-system/references/visual-token-migration.md | Mapa de papel/consumidor e comparação neutra/cromática com a mesma fixture |
| ox-typeset-icons | typography-and-finish.md | Fonte solicitada/disponível/observada, pesos, fallback, corpus técnico e origem dos ícones |
| ox-extract-tokens | visual-token-migration.md | Cascata, aliases e consumidores; migração de nomes distinta de mudança visual; denominadores explícitos |
| ox-harden | visual-token-migration.md | Matriz estado/origem/apresentação/ação/conteúdo/evidência; pedido, confirmação e falha parcial distintos |
| ox-native-handoff | visual-token-migration.md, somente rastreabilidade | Papel → componente → estado → consumidor → observação; Rust/Iced e pipeline nativo rejeitados |
| ox-visual-qa | frontend-testing/references/visual-capture-evidence.md | Manifesto, prontidão observada, pares equivalentes, casos não executados e imagem capturada versus inspecionada |
| ox-accessibility | Reaproveitar web-accessibility, sem alteração | Contraste composto, foco/seleção, teclado, zoom, efeitos e ausência de sucesso indevido já estão nas referências existentes |

Não foi necessário alterar vue3-engineering, formulários, cache ou projeções:
a mesclagem não acrescenta conhecimento sobre seus mecanismos. O conteúdo de
harden aproveitado pertence ao contrato visual; a própria referência delimita
quando uma correção passa a exigir mudança comportamental.

## Arquivos e governança

- Oito arquivos de skills alterados/adicionados, em quatro pacotes; três referências
  novas. Nomes, descrições, metadados de descoberta e quantidade de skills preservados.
- Baseline e instalação recebem os mesmos bytes revisados. `before.json` e
  `previous.zip` preservam as versões anteriores e o perfil/documentos então lidos.
- O perfil schema 2 recebe uma fonte suplementar com hash do ZIP, mais `files` e
  `adaptations` dos quatro destinos. `origin`, `source_files`, bundle original,
  demais pacotes e ponteiros das pontes continuam com suas funções anteriores.
- O mapa de procedência identifica os membros do pacote recebido; não transforma
  essas 11 fontes em novas identidades instaladas.
- AGENTS.md aponta seleção sob demanda e autoridade visual. O guia de frontend,
  o guia de manutenção, o índice documental e sdd/project apontam esta revisão.
  Plan/tasks/converge/evidence de governance-adoption registram o novo estado.

## Inspeção e limites

Inspeção de conteúdo/diffs, correspondência de fontes, referências locais e
precondições de escrita. A coleta de hashes serve à rastreabilidade e evita
sobrescrever uma divergência conhecida; não equivale ao auditor canônico.

A revisão editorial confirmou que os novos métodos são autocontidos, não exigem
executar outras skills e não importam caminhos, scripts, versões, números de
casos, medidas ou aprovação exclusiva do Ox. Fonte, paleta e regras do Ec
permanecem no contrato visual e normas do produto.

Não executados: quick_validate, auditor de governança, preflight, validador SDD,
testes, automate.sh e ensaios de comportamento das skills. Não houve pedido de
commit. AC-01 a AC-07 de governance-adoption permanecem UNVERIFIED; não há
certificação de eficácia, descoberta atualizada na interface ou qualidade da UI.
Os exemplos do guia são cenários para uso futuro, não resultados de execução.

Recuperação: consultar previous.zip e o manifesto de aplicação; comparar o arquivo
atual com os bytes aplicados antes de restaurar. Não extrair o backup cegamente
sobre mudanças posteriores. O procedimento realiza escritas individuais com
precondições; não promete transação atômica entre vários arquivos.

## Resultado da aplicação

A operação de escrita `python3 .local/build/frontend-skills-20261111/apply.py`
terminou com código 0 e 28 destinos aplicados. A permissão específica do ambiente
foi concedida para alcançar `.codex`; não houve mudança de permissões do repositório.
[Registro de escrita e bytes lidos](applied.json), [procedência e conjunto preparado](provenance.json),
[ajustes editoriais finais](editorial-followup.json). Estes registros identificam
a aplicação de conteúdo, não resultados de verificadores.

O perfil passa de 596 para 599 arquivos nas mesmas 67 skills. A inspeção final dos
diffs confirmou alterações de files/adaptations em somente quatro registros do
perfil e inclusão da fonte suplementar. Os recursos originais e os resultados
históricos foram preservados. A abertura do guia de instalação foi atualizada
para a contagem corrente; a passagem histórica de 594 para 596 foi mantida.
