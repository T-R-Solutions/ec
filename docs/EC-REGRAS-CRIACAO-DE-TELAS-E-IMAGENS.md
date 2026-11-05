# EC — Regras para criação de imagens, telas e interfaces de uma plataforma CI/CD

## 1. Objetivo

As imagens, mockups, protótipos e interfaces do Ec devem servir à **operação, configuração e administração real de uma plataforma CI/CD**. Cada tela precisa comunicar claramente onde o usuário está, o que está sendo administrado, o estado da infraestrutura, a ação disponível e o resultado esperado. Não criar telas genéricas de SaaS, dashboards decorativos ou composições visuais desconectadas das funções do produto.

O Ec é uma plataforma completa, inspirada na abrangência funcional de ferramentas como TeamCity, porém **com identidade e arquitetura próprias**. Sua experiência compreende projetos, subprojetos, equipes, usuários, permissões, plugins, integrações, repositórios, pipelines, agentes, pools, builds, testes, logs, artefatos, ambientes, deployments, auditoria e administração do servidor — não apenas o designer de pipelines.

## 2. Direção de arte

A direção visual deve ser **criativa, contemporânea, elegante, profissional, precisa e altamente funcional**. Priorizar hierarquia, tipografia, ritmo, proporções, espaços, contraste e qualidade de interação. Uma interface bonita não pode sacrificar a legibilidade de dados técnicos nem a capacidade de operação.

Não copiar TeamCity, GitHub Actions, GitLab, Jenkins ou outro produto em layout, cores, marca ou padrões visuais exclusivos. Referências podem informar princípios de interação e cobertura funcional, mas o Ec deve desenvolver sua linguagem própria. Evitar estética de template administrativo genérico, saturação excessiva, cartões supérfluos, vidro translúcido indiscriminado, neon ornamental, sombras pesadas e elementos sem propósito.

## 3. Identidade visual e continuidade

Definir antes de qualquer coleção de imagens: logotipo e uso de marca (se aprovados), sistema tipográfico, tokens de cor, escala de espaçamento, densidade, grid, iconografia, bordas, elevação, estados semânticos, motion e componentes.

Todas as páginas devem compartilhar a mesma identidade. Sidebar, barra superior, navegação contextual, breadcrumbs, menus, botões, tabelas, cards, inputs, indicadores, editores e mensagens precisam preservar consistência. Não mudar o padrão da interface a cada tela gerada.

Prever temas **claro e escuro** com paridade funcional. Sempre verificar contraste, foco, divisórias, estados e tipografia nos dois temas.

## 4. Arquitetura de informação e navegação

A navegação deve apresentar uma arquitetura coerente e extensível, organizada por áreas de trabalho:

- **Visão geral:** saúde, atividade, falhas, filas, alertas.
- **Projetos:** projetos, subprojetos, parâmetros, políticas, configurações.
- **Pipelines:** definições, templates, editor visual, formulário e YAML.
- **Execuções:** builds, histórico, fila, testes, logs, artefatos.
- **Entregas:** ambientes, releases, aprovações, deployments.
- **Infraestrutura:** agentes, pools, capacidades, conectividade.
- **Pessoas e acesso:** usuários, equipes, grupos, papéis, permissões.
- **Extensões:** plugins, integrações, webhooks, provedores Git.
- **Administração:** configurações globais, auditoria, segurança, retenção, observabilidade.

Não colocar tudo indiscriminadamente na sidebar. Separar navegação global de navegação por projeto, com breadcrumbs, subtabs e páginas de detalhe. O usuário deve reconhecer imediatamente o escopo de cada ação: global, equipe, projeto ou pipeline.

## 5. Composição e leitura

Cada imagem deve possuir **ponto focal funcional**: tabela de usuários na gestão de usuários; árvore na gestão de projetos; catálogo na gestão de plugins; grafo no editor de pipeline; logs na execução; YAML no modo de código. Não usar dashboards como composição padrão de todas as páginas.

Organizar informação primária, secundária e ações de forma previsível. Utilizar alinhamento, proporções, contraste e espaços negativos para criar hierarquia. Reservar espaço para navegação e operações reais, não para gráficos ornamentais.

Dados densos são admissíveis quando a tarefa exige, mas devem continuar legíveis. Um bom design de CI/CD deve permitir investigação técnica rápida, sem excesso de cliques e sem ocultar informações essenciais.

## 6. Grid, layout e responsividade

Projetar para desktop como ambiente de trabalho avançado, com comportamento adaptativo em tablets e celulares. Empregar CSS Grid para organização bidimensional, Flexbox para componentes unidimensionais, Container Queries para adaptação local e Media Queries para mudanças baseadas no viewport.

Utilizar unidades relativas, `clamp()` e tokens quando adequados. Painéis laterais do designer, YAML e logs podem ser redimensionáveis ou recolhíveis. Em mobile, oferecer visualização alternativa de DAGs, tabelas e configurações complexas; não simplesmente reduzir o canvas até ficar ilegível.

Não cortar ações, nomes, status, timestamps, botões ou indicadores críticos. Testar tamanhos diferentes e possíveis nomes extensos de projetos, agentes e pipelines.

## 7. Tipografia, iconografia e componentes

Usar poucas fontes, uma escala tipográfica controlada e monoespaçada para logs, YAML, caminhos, commits, comandos e IDs. Timestamps, duração, contadores e valores devem ser fáceis de comparar.

Ícones devem complementar rótulos, não substituí-los quando houver ambiguidade. Usar componentes reutilizáveis para listas, tabelas, árvore, matriz de permissões, gráficos, timelines, formulários, YAML, diff, logs, editor DAG, prompts de aprovação, mensagens e feedback de sincronização.

Evitar interfaces de baixa densidade que gastem grandes áreas apenas com cabeçalhos, ilustrações ou espaços sem função.

## 8. Cor, estados e atmosfera visual

A paleta deve transmitir controle, confiança e precisão. Usar cores de status com semântica consistente e reforço por texto ou forma: aguardando, na fila, executando, sucesso, falha, cancelado, pausado, indisponível, bloqueado, validando, rascunho, publicado e fora de sincronização.

Não confundir falha de build com falha de infraestrutura. Não representar agente desconectado como build cancelado. Não ocultar estados críticos em variações quase indistinguíveis de uma mesma cor.

Evitar saturação uniforme. Reservar a cor de destaque para ações e informações que exigem atenção contextual.

## 9. Página de projetos

Criar telas para listar, pesquisar, favoritar, criar, duplicar, mover, arquivar, visualizar e editar projetos/subprojetos. Exibir árvore hierárquica, proprietário, equipes, repositórios, pipelines associados, estado e políticas relevantes.

Oferecer **três modos de configuração**:

- **Visual:** estrutura em árvore, seleção de projeto pai, associação visual, reorganização por arrastar e soltar quando segura e pré-visualização de impacto.
- **Formulário:** campos convencionais, validação contextual, seções avançadas e assistente de criação quando útil.
- **YAML:** especificação completa do recurso, com validação e diff.

Projetos não devem ser representados apenas como uma galeria de cards sem operações administrativas.

## 10. Equipes, usuários e permissões

Criar telas próprias para equipes, usuários, convites, grupos, papéis, permissões, associação a projetos, permissões efetivas, identidade federada e auditoria. O design visual deve permitir compreender **quem tem acesso a quê** e a diferença entre papel atribuído e permissão efetiva.

Os modos de edição são: matriz/diagrama e associação visual quando apropriado; formulários e tabelas para gestão tradicional; YAML para políticas, papéis e vínculos declarativos.

Não forçar um diagrama de drag-and-drop para todas as operações administrativas. Criação de convite, revogação de sessão ou autenticação são ações operacionais com fluxos próprios. **Senhas, sessões e tokens não devem ser escritos em YAML.** Identidades devem ser verificadas pelo mecanismo de autenticação apropriado; YAML pode declarar vínculos e políticas.

## 11. Plugins e integrações

Criar catálogo de plugins com pesquisa, categoria, compatibilidade, versão, origem, estado, permissões solicitadas e detalhes. Prever telas de instalação, configuração, atualização, desativação, remoção, dependências e diagnóstico.

Disponibilizar configuração visual (ex.: conexão entre eventos e destinos), tradicional (campos e opções) e YAML para a configuração declarativa. Não representar instalação como mero botão sem indicar permissões, riscos, versão e efeitos.

Os mockups não podem prometer execução de plugins arbitrários sem modelo de integridade, isolamento e autorização. A existência de páginas projetadas não significa que toda extensão já está implementada.

## 12. Designer de pipelines

O editor de pipeline deve ser visualmente excelente **e tecnicamente correto**. Usar canvas com nós e conexões legíveis, biblioteca de tarefas, inspector, minimapa em grafos grandes, zoom, pan, seleção múltipla, auto-layout, alinhamento, desfazer/refazer e sinais de validação.

Diferenciar estágios, jobs, steps, dependências, condições, gatilhos e aprovações. Um grafo de dependências é um DAG; ciclos devem ser impedidos ou sinalizados. A direção de leitura deve ser clara, evitando arestas cruzadas e conexões visualmente ambíguas.

O designer visual não deve esconder `needs`, requisitos de agentes, parâmetros, timeouts, retries, artefatos ou políticas importantes. Para cada nó, oferecer propriedades detalhadas no inspector/formulário e correspondência no YAML.

## 13. Os três modos de configuração

**Regra central do Ec: toda configuração desejada tem como fonte da verdade um arquivo YAML.** Visual, Formulário e YAML são diferentes maneiras de editar e visualizar a mesma definição. Não criar configurações independentes em cada modo.

- Alterar no canvas atualiza o modelo do rascunho que será serializado como YAML.
- Alterar em formulários produz a mesma definição semântica.
- Alterar o YAML válido atualiza as projeções visual e tradicional quando for possível representar o recurso sem perda.
- Se um recurso não puder ser projetado no canvas, manter edição em formulário/YAML e declarar a limitação claramente.
- Não apagar silenciosamente campos desconhecidos, comentários importantes, expressões ou configurações avançadas ao mudar de modo.
- Exibir diferenças, validação, estado de salvamento, publicação, sincronização e conflito.

**Nunca usar JSON, XML, TOML ou código de programação como formatos alternativos de arquivos de configuração.** JSON em tráfego de API, se utilizado, não é um arquivo de configuração e não substitui o YAML canônico.

## 14. Editor YAML, revisão e publicação

O editor YAML deve possuir realce, linhas, autocomplete, indicação de schemas, erros contextualizados, diff, histórico, comparação e preview de efeito. Validar sintaxe, tipos, referências, permissões, ciclos e compatibilidade com a versão da plataforma.

As imagens devem distinguir claramente **rascunho**, **validado**, **pendente de aprovação**, **publicado**, **sincronizando**, **sincronizado**, **divergente** e **falha**. O clique em salvar não pode se confundir com publicação ou aplicação efetiva.

Mostrar versões de configuração e, em execuções, a revisão imutável usada. Preferir repositório Git versionado como autoridade do YAML e tratar banco de consulta como projeção reconstruível. Em conflitos de edição, mostrar diff e exigir resolução explícita.

**Importante:** o YAML é fonte da verdade das **configurações**, não dos estados operacionais. Estado de execução, logs, fila, leases, auditoria, artefatos e sessões continuam no backend/armazenamento operacional; segredos devem ser referências seguras.

## 15. Agentes, execuções, logs e deployments

Projetar telas que permitam diagnosticar rapidamente builds e infraestrutura. Mostrar status de agentes, pools, compatibilidade, slots, capacidade, desconexão, manutenção e razões pelas quais um job não pode ser executado.

Em execuções, incluir revisão, agente, tempo, logs por etapa, testes, artefatos, cancelamento, retry explícito, erro e histórico. Diferenciar eventos ao vivo de dados históricos. Mostrar fila com posição e motivos de bloqueio somente quando existirem dados confiáveis.

Em entregas, apresentar ambientes, releases, aprovações, riscos, evidências, histórico e rollback quando suportado; operações críticas devem exibir destino, versão e impacto antes da confirmação.

## 16. Dados e narrativa operacional

Cada mockup deve contar uma **história operacional plausível**: o projeto teve uma mudança, o pipeline foi disparado, um job entrou na fila, foi alocado a um agente, gerou logs e artefatos e terminou com um resultado específico.

Usar nomes coerentes de projeto, repositório, branch, commit, agente, pool, job e etapa. Tabelas, timestamps, status e gráficos devem concordar entre si. Nunca usar números aleatórios para simular métricas sem marcar dados como demonstrativos.

Toda estatística precisa ter período, unidade e escopo. Cada alerta deve ter causa provável, contexto e caminho de investigação; não criar alertas impossíveis de agir.

## 17. Segurança, acessibilidade e operação

A UI deve indicar papéis, ações autorizadas, exigências de aprovação, segredos protegidos e operações destrutivas. Não exibir segredos em plaintext em campos, logs, preview de YAML ou screenshots. Não sugerir que um usuário sem privilégios pode instalar plugins, alterar políticas globais ou implantar em produção sem autorização.

Adotar WCAG 2.2 AA como meta de acessibilidade. Toda função relevante deve ter percurso por teclado e foco visível. Para o DAG, oferecer representação alternativa em lista/árvore/formulário, inclusive para usuários sem mouse. Usar rótulos, mensagens e ícones, nunca depender apenas de cores.

Não usar a aparência de uma execução bem-sucedida para mascarar desconexão de agentes, erro de sincronização ou falha na aplicação de políticas.

## 18. Realismo visual e precisão de imagens

Gerar interfaces em proporções plausíveis e alta legibilidade. Evitar pseudo-texto, barras deformadas, ícones aleatórios, botões ilegíveis, menu desalinhado, perspectivas artificiais e excesso de efeitos. Qualquer texto visível deve ter termos técnicos corretos e estar em idioma consistente.

Mockups podem usar exemplos fictícios, mas devem ser identificados como **referência conceitual**. Não inventar funcionalidades como implementadas ou resultados de testes inexistentes. Imagens não comprovam comportamento, acessibilidade, performance ou segurança; esses atributos exigem protótipos e testes reais.

## 19. Conjunto obrigatório de telas

Em uma coleção completa de design, gerar ao menos:

1. Visão geral operacional e alertas.
2. Lista de projetos, árvore de subprojetos, criação visual, formulário e YAML.
3. Gestão de equipes, membros e permissões.
4. Gestão de usuários e papéis.
5. Catálogo e configuração de plugins.
6. Integrações com repositórios e serviços externos.
7. Biblioteca de pipelines, editor DAG, formulário e editor YAML.
8. Pré-visualização de diff, validação, publicação e conflito de configuração.
9. Inventário de agentes, pools e compatibilidade.
10. Execuções, fila, logs, testes e artefatos.
11. Ambientes, releases, aprovações e deployments.
12. Administração, auditoria e políticas de segurança.
13. Estados de erro, vazio, carregamento, acesso negado, offline e fora de sincronização.

Não produzir uma coleção que mostre apenas gráficos de pipeline: a abrangência administrativa do Ec deve ser perceptível já nas primeiras telas.

## 20. Organização e revisão de qualidade

Padronizar nome de arquivo, área, tela, tema, viewport, estado e versão, por exemplo: `ec-projects-create-desktop-dark-v01.png`. Registrar objetivo da tela, persona, dados demonstrativos, decisões visuais, requisitos de permissão e comportamento esperado.

Revisar antes de aprovar: identidade visual, coerência com a navegação, conteúdo técnico, tipografia, contraste, densidade, estados, responsividade, consistência entre modos de edição, preservação do YAML, segurança e viabilidade de implementação.

Rejeitar telas com funções irreais, botões sem contexto, propriedades incoerentes, fluxos incompletos, perda potencial de configuração, gráficos decorativos, pseudo-texto, excessos estéticos ou cópia de concorrentes.

## 21. Briefing obrigatório para uma nova tela

Cada solicitação deve identificar: **área**, **tela**, **objetivo**, **tipo de usuário e permissões**, **viewport**, **tema**, **estado**, **modo (Visual/Formulário/YAML)**, **dados**, **ação principal**, **ações secundárias**, **complexidade esperada**, **elementos obrigatórios**, **elementos proibidos**, **navegação**, **comportamento responsivo** e **relação com o YAML canônico**.

Se houver opções a apresentar, preferir a que resolve melhor o fluxo e justificar compensações entre clareza, densidade e sofisticação. Não sacrificar precisão funcional para produzir um mockup mais chamativo.

## 22. Regra final

Cada imagem do Ec deve parecer parte da **mesma plataforma viva e operável**: moderna sem exibicionismo, completa sem desorganização, intuitiva sem sacrificar recursos avançados, tecnicamente correta e vinculada ao YAML como fonte da verdade das configurações.

A qualidade será medida por **clareza, elegância, coerência, utilidade operacional, consistência entre telas, acessibilidade, segurança e implementabilidade**.

---

### Base documental e decisões

Este documento adapta o modelo de regras visuais fornecido para o projeto OX ao domínio de CI/CD e considera os arquivos anexados `README(3).md`, `decision-matrix.md` e `implementation-plan.md`. Da pesquisa anterior, preserva Rust no servidor/agentes, PostgreSQL para fila e estado operacional, protocolo HTTP versionado, leases/fencing e implementação incremental. **Substitui a configuração declarativa em JSON por YAML** e antecipa o design da interface como requisito desde o início; não afirma que as funcionalidades estejam implementadas.

Referências técnicas: [YAML 1.2.2](https://yaml.org/spec/1.2/), [OWASP CI/CD Security](https://cheatsheetseries.owasp.org/cheatsheets/CI_CD_Security_Cheat_Sheet.html), [WCAG 2.2](https://www.w3.org/TR/WCAG22/), [ARIA Authoring Practices](https://www.w3.org/WAI/ARIA/apg/), [MDN Container Queries](https://developer.mozilla.org/en-US/docs/Web/CSS/Guides/Containment/Container_queries).
