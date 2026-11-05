# Refatoração visual da documentação — 2026-11-04

## Objetivo e escopo

Refatorar o README e a documentação de entrada por ele referenciada com imagens
técnicas, fluxogramas, mapas de componentes e fontes editáveis. Rota local
documental: não há alteração de contrato de runtime, persistência, autorização ou
implementação do produto. As 12 referências Markdown diretas anteriores, os guias
de arquitetura/testes e os 35 READMEs da aplicação fazem parte da revisão.

O [README](../../../README.md) apresenta identidade, estado, arquitetura, jornada,
contratos e caminhos de leitura. A [visão](../../architecture/application-overview.md)
detalha o produto; o [catálogo](../../architecture/contracts.md) mantém invariantes,
rejeições e lacunas. Guias e entradas locais recebem figuras pertinentes às suas
responsabilidades, sem reproduzir normas em cada módulo.

## Fontes, preservação e método

Fontes: estado do projeto, autoridades, normas de produto/interface, ADR-001/002,
mapa físico, política de testes, workflow, contrato de evidência e arquivos reais.
Os cinco documentos importados, o corpus da pesquisa, os pacotes de skills,
perfil/baseline e relatórios anteriores conservam seu conteúdo. A ADR-002 recebe
um mapa explicativo, preservando decisões e correções datadas.

[previous.zip](previous.zip) preserva os 52 arquivos existentes candidatos à
edição, incluindo a evidência de governança. O [escopo](scope.json) registra os
hashes anteriores; os 1.447 inputs anteriores estavam íntegros antes da mudança.
Novos assets, ferramentas de reprodução e evidências são inventariados na coleta
final. Registros anteriores não foram reescritos.

Skill primária: `technical-documentation`; auxiliar de entradas:
`readme-lifecycle-engineering`; auxiliar da matriz de navegador: `frontend-testing`.
Os caminhos efetivos e hashes observados constam da coleta. Não houve delegação,
instalação de dependências, commit, publicação ou execução de produto.

## Entrega visual e reprodução

As figuras possuem fonte Mermaid, metadados de composição, SVG, descrição
adjacente e acesso à ampliação. [Fontes e reprodução](../../assets/README.md)
documentam paleta, iconografia Lucide/ISC, ferramentas e gatilhos de manutenção.
O [catálogo](../../assets/catalog.json) identifica consumidores e proveniência.

O pipeline usa Mermaid para renderizar a fonte. A composição acrescenta ícones,
cabeçalho e agrupamentos; fluxos extensos e painéis independentes recebem arranjo
compacto. A prévia Markdown-it tem navegação por teclado, tabelas/figuras com
rolagem local, texto responsivo e temas claro/escuro. É uma prévia da documentação,
não uma interface Ec nem prova de suporte em outro cliente Markdown.

## Verificação e aceite

A coleta final registra resultados, contagens e comandos em
[checks.json](checks.json), [visual-checks.json](visual-checks.json) e
[gates.json](gates.json). As capturas ficam em [screenshots/README-1280-light.png](screenshots/README-1280-light.png).

Critérios desta revisão: referências diretas contempladas; relações fiéis às fontes;
fontes editáveis e exportações presentes; imagens carregadas e descritas; sem
overflow global nas páginas; sem texto fora dos SVGs ou nós sobrepostos; rótulos
essenciais legíveis no tamanho observado; links locais resolvíveis; comandos de
governança e suíte existentes aprovados; evidência vinculada aos inputs atuais.

Os resultados automáticos devem ser complementados por inspeção visual das
capturas e figuras. Resultado observado: 38 figuras, 52 páginas de prévia e 312 casos de largura/tema, 676 links locais, incluindo âncoras, e 12 mapas de dependências Rust conferidos contra a tabela canônica. Auditor padrão e com instalação exigida: PASS, 67 skills e 596 arquivos; preflight: ALREADY_INSTALLED; estrutura SDD: PASS; 24 testes existentes: OK. A matriz não encontrou overflow global, imagens ausentes, nós sobrepostos, rótulos essenciais abaixo de 14 CSS px ou falhas de contraste de texto nos seletores medidos. Os gates atuais constam de gates.json.

## Limites e manutenção

Não foram executados Ec Server, Agent, frontend de produto, banco, workloads,
benchmarks ou testes de produto. Não houve estudo com leitores nem teste de
tecnologia assistiva; a inspeção não certifica conformidade WCAG. URLs externas e
renderizadores de serviços remotos não fazem parte da verificação local.

Alterações de conteúdo, contrato, ícone ou renderizador exigem regenerar derivados
e observar os checks afetados em novo registro. Resultados de governança aceitam
somente os seis critérios dessa feature; não aceitam o produto nem a eficácia geral
das skills. O ambiente anterior sem navegador continua documentado nos registros
históricos, separado da execução atual com Playwright/Chromium.
