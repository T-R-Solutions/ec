# Fontes e reprodução dos visuais

As imagens técnicas deste diretório pertencem à documentação do Ec. Descrevem
requisitos e limites planejados; não são capturas de um produto implementado.

## Arquivos e autoridade

| Arquivo | Responsabilidade |
| --- | --- |
| `diagrams/*.mmd` | Fonte Mermaid dos participantes, rótulos e relações |
| `diagrams/*.layout.json` | Título, pergunta, estado, fontes, cores, ícones e pistas de composição |
| `diagrams/*.svg` | Exportação derivada, incorporada nos documentos |
| [catalog.json](catalog.json) | Inventário de figuras e documentos consumidores |
| [render.mjs](render.mjs) | Renderização, composição e verificação da prévia local |
| [vendor/lucide/](vendor/lucide/provenance.json) | Subset de ícones, origem e licença preservada |

O Mermaid mantém o significado do diagrama. A composição adiciona cabeçalho,
ícones vetoriais e agrupamentos; mapas independentes e sequências longas recebem
arranjo compacto. Os SVGs não mantêm um modelo semântico editado separadamente.
Alterações de relações e rótulos devem começar na fonte `.mmd`.

## Direção visual

Fundo branco, texto escuro, tipografia Arial/sans-serif e família Lucide outline.
Azul identifica autoria, verde identifica controle/estado, violeta identifica
execução, âmbar identifica condição/pendência e rosa identifica rejeição. Rótulos
e descrições mantêm o significado sem depender das cores. O selo tipográfico Ec
identifica esta documentação; não constitui aprovação de uma marca gráfica.

Cada figura possui uma pergunta, fonte e descrição adjacente. Diagramas amplos
têm link para ampliação e fonte editável. A prévia usa regiões com rolagem e foco
por teclado para manter os rótulos essenciais com pelo menos 14 CSS px; o texto
da página reflui em larguras menores. SVGs preservam fundo claro também na prévia
escura para manter a paleta interna observada.

## Reproduzir no ambiente observado

Execute a partir da raiz. Os caminhos abaixo identificam ferramentas já existentes
neste ambiente; não são dependências do produto nem instruções de instalação:

```bash
EC_DOC_TOOLS=/tmp/ox-doc-visual-tools \
EC_DOC_CHROMIUM=/tmp/ox-doc-chromium-extracted/chrome-linux/chrome \
node docs/assets/render.mjs
```

O diretório de ferramentas precisa conter `node_modules` com Mermaid 11.12.2,
Markdown-it 14.1.0 e Playwright 1.56.1. O Chromium observado é 141.0.7390.37.
Em outro ambiente, informe caminhos reais e reveja compatibilidade. A execução
requer permissão para iniciar processos de navegador e escrever os derivados.

O comando regenera SVGs, monta a prévia em
`.local/build/documentation-enterprise/site/README.md.html` e executa a matriz
visual. As saídas de observação ficam em `.local/build/documentation-enterprise/observations/`.
Para uma coleta datada, use `--evidence docs/validation/<nova-revisão>` e preserve
registros anteriores. Não reutilize um destino histórico para uma nova observação.

## Verificação e manutenção

Confira fontes, alternativas textuais, nós, relações, cortes, sobreposições,
contraste e tamanho final. Abra a página e a figura ampliada; sintaxe aceita não
garante qualidade visual. A matriz automática e a inspeção humana têm escopos
distintos, registrados no [relatório](../validation/documentation-enterprise-20261104/report.md).

Ao mudar um contrato, domínio, rótulo, ícone, tema ou renderizador, atualize os
consumidores, regenere as imagens e observe os checks afetados. Não altere fontes
importadas ou evidências históricas para aparentar resultado atual. Origem e
licença dos ícones estão em [provenance.json](vendor/lucide/provenance.json) e
[LICENSE](vendor/lucide/LICENSE). A documentação permanece sob a política do
repositório; a licença dos ícones não licencia o produto Ec.
