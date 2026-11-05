# EC — Regras de design, criação de telas, experiência e consistência funcional

**Tipo:** diretrizes obrigatórias para concepção visual, imagens de interface, protótipos, frontend e especificação funcional.  
**Produto:** Ec, plataforma independente de integração contínua e entrega/implantação contínuas (CI/CD), inspirada na abrangência funcional de produtos como TeamCity, sem copiar sua identidade ou implementação.  
**Stack estabelecida:** frontend Vue.js + TypeScript; servidor/control plane e agentes em Rust.  
**Estado:** visão de produto e critérios de projeto; funcionalidades futuras não devem ser apresentadas como já implementadas.

## 1. Objetivo

O Ec deve ser uma plataforma de CI/CD **abrangente, moderna, elegante, eficiente, confiável e compreensível**. Não será apenas um editor de pipelines: deverá administrar projetos, subprojetos, equipes, usuários, identidades, permissões, integrações, repositórios, agentes, pools, pipelines, execuções, ambientes, deployments, artefatos, plugins, políticas, auditoria e operações da instância.

Cada tela, imagem, protótipo ou componente deve comunicar uma tarefa real e oferecer um caminho explícito para concluí-la. Não criar dashboards genéricos, telas decorativas, gráficos sem significado ou interfaces que aparentem funcionalidade inexistente. A qualidade visual é requisito de primeira classe, mas não substitui correção, segurança, legibilidade ou ergonomia.

**Princípio central:** complexidade funcional sem complexidade desnecessária de interação. Expor primeiro o essencial e oferecer profundidade progressiva para usuários avançados.

## 2. Regras inegociáveis de produto

1. A aplicação é **Ec**, com identidade própria e independente, sem reproduzir layout, ícones, marca ou componentes distintivos de TeamCity.
2. O Ec não se limita a pipelines: todas as entidades administrativas e operacionais devem ter interfaces completas de criação, consulta, edição e gestão, respeitadas as permissões.
3. As configurações declarativas são expressas **exclusivamente em arquivos YAML**. Não adotar JSON, TOML, XML, DSL própria ou arquivos de código como formatos alternativos de configuração do Ec.
4. O **YAML canônico é a fonte da verdade para configuração desejada**. Editor visual, formulários e editor YAML são representações e formas de edição do mesmo modelo; nenhuma delas mantém configuração paralela ou autoritativa.
5. O Ec deve oferecer três modos complementares: **Visual**, **Formulário** (tradicional) e **YAML**, com transição clara e sem perda semântica. A modalidade visual se adapta ao domínio; não obrigar arrastar e soltar onde uma tabela, árvore ou formulário é melhor.
6. Qualquer mudança persistente de configuração feita por interface ou API deve produzir/alterar o YAML, passar pelo mesmo pipeline de validação, versionamento e autorização e somente então tornar-se efetiva.
7. Estado operacional não é configuração declarativa: execuções, filas, leases, logs, sessões, métricas, artefatos e auditoria têm armazenamento apropriado; não devem ser persistidos como YAML de configuração.
8. Segredos, senhas, tokens, chaves privadas e credenciais não podem ficar em plaintext no YAML. Usar referências a um gerenciador de segredos e materialização controlada no momento da execução.
9. A UI não pode sugerir que uma alteração foi salva, publicada, sincronizada ou executada sem confirmação correspondente do servidor.
10. Segurança, acessibilidade, desempenho e feedback de estados são condições de aprovação, não melhorias opcionais.

## 3. Arquitetura de informação

Organizar o produto em áreas de navegação reconhecíveis, sem impor que todas apareçam no menu principal ao mesmo tempo:

- **Visão geral:** saúde da instância, atividade recente, builds críticos, filas, falhas e alertas.
- **Projetos:** organizações/escopos quando habilitados, projetos, subprojetos, repositórios, parâmetros, políticas, modelos e recursos associados.
- **Pipelines:** criação, biblioteca, designer visual, definições YAML, templates, variáveis, gatilhos, validação e dependências.
- **Execuções:** builds, histórico, filas, logs, testes, diagnósticos, artefatos, duração, retry e cancelamento.
- **Entregas:** ambientes, aprovações, releases, implantações, histórico e rollback conforme política.
- **Infraestrutura:** agentes, pools, capacidades, requisitos, capacidade de execução, compatibilidade, manutenção e conectividade.
- **Pessoas e acesso:** usuários, equipes/grupos, funções, permissões, convites, identidade federada e trilha de acesso.
- **Extensões e integrações:** plugins, provedores Git, notificações, webhooks, serviços externos, credenciais referenciadas e API.
- **Administração:** configuração global, políticas, observabilidade, retenção, auditoria, segurança, atualizações e saúde do servidor.

Navegação contextual por projeto e breadcrumbs devem distinguir recursos globais de recursos locais. A hierarquia de projetos nunca pode ser confundida com a hierarquia de pipelines.

## 4. Identidade visual e direção de arte

A identidade do Ec deve comunicar **precisão, controle, confiança e sofisticação técnica**. Priorizar composição arquitetada, hierarquia clara, tipografia excelente, proporções consistentes, espaços bem resolvidos e animações discretas.

Evitar estética de template genérico de SaaS, excesso de gradientes, vidro translúcido indiscriminado, neon decorativo, sombras pesadas, cartões infinitos, cores saturadas em todas as áreas e painéis sem propósito. A originalidade deve resultar da qualidade de interação e de um sistema visual coerente, não de efeitos gratuitos.

Criar design system próprio com tokens para cor, tipografia, espaçamento, raios, bordas, elevação, densidade, foco, animação e estados semânticos. Preferir aparência profissional contemporânea em temas claro e escuro com paridade funcional. Não copiar o design system de outro fabricante integralmente.

## 5. Composição, grid e responsividade

Usar **CSS Grid** para estruturas bidimensionais; **Flexbox** para alinhamento e distribuição unidimensional; **Container Queries** quando componentes precisarem reagir à área disponível; **Media Queries** quando a adaptação depender do viewport ou características do dispositivo. Usar `clamp()`, unidades relativas e tokens para dimensões fluidas quando apropriado.

Estabelecer hierarquia de três níveis: navegação persistente, contexto/localização e área de trabalho. O conteúdo de maior importância deve ser visualmente dominante. Sidebars, drawers, inspectors e painéis de detalhes devem ser redimensionáveis ou recolhíveis quando isso melhorar tarefas complexas.

Preferir desktop para autoria intensiva de pipelines e administração, mas garantir consultas, aprovações, triagem de falhas e monitoramento em tablets e celulares. Em telas pequenas, oferecer alternativa estruturada ao canvas e a tabelas largas; não simplesmente reduzir tudo proporcionalmente. Não esconder funções essenciais apenas por restrição de largura.

## 6. Tipografia, iconografia e legibilidade

Usar poucas famílias tipográficas, com escalas coerentes e pesos controlados. Dados técnicos — caminhos, hashes, identificadores, comandos, expressões e YAML — devem usar tipografia monoespaçada apropriada. Numerais tabulares ajudam em métricas, tempo e estatísticas.

Ícones devem possuir significado estável, rótulos acessíveis e consistência de traço. Não depender de ícone isolado para ações ambíguas. Evitar abreviações obscuras sem tooltip ou ajuda contextual.

Usar densidade de informação equilibrada: interfaces técnicas podem ser compactas, mas não apertadas. Permitir modos de densidade quando fizer sentido e manter áreas clicáveis adequadas.

## 7. Cores, status e semântica visual

Cor deve indicar significado, não ornamentação. Diferenciar, com redundância por texto e forma, estados como **rascunho, validando, aguardando, na fila, executando, sucesso, falha, cancelado, ignorado, bloqueado, indisponível e desconhecido**.

Não usar verde para qualquer ação positiva nem vermelho para qualquer elemento de destaque. Estados de atenção, risco, infraestrutura e autorização devem ter convenções consistentes. Estados desconhecidos e conexão degradada nunca podem aparecer como sucesso.

Gráficos, indicadores, logs e mapas de dependência devem possuir legendas, rótulos e contraste suficiente em ambos os temas.

## 8. Linguagem visual de dados e dashboards

Dashboards devem responder a perguntas concretas: **o que está falhando, onde, desde quando, por quê, quem é responsável e qual ação é possível**. Não preencher a tela com números de vaidade.

Priorizar saúde de pipelines, duração e regressão, tempo de fila, agentes disponíveis, taxas de falha, builds recentes, falhas recorrentes, deployments e capacidade de execução. Cada indicador deve declarar período, escopo, unidade, atualização e estado dos dados; métricas comparativas exigem base de comparação.

Diferenciar estatísticas históricas, dados em tempo real e estimativas. Não inventar percentuais, benchmarks, latências, custos ou tendências nas telas de referência; dados fictícios devem ser identificados como demonstrativos em protótipos e nunca apresentados como medição real.

## 9. Página e gestão de projetos

Projetos são uma entidade de primeira classe, com lista, busca, filtros, favoritos, histórico, dono, estado, hierarquia, ações em lote e páginas de detalhe. Suportar subprojetos, herança explícita de políticas e parâmetros, isolamento de permissões, templates quando habilitados e movimentação/cópia com pré-visualização de impacto.

A criação deve ter três apresentações:

- **Visual:** árvore hierárquica interativa, seleção de pais, associação por arrastar e soltar quando segura, mapas de dependência e preview da estrutura.
- **Formulário:** nome, chave, descrição, propriedade, repositórios, equipes, políticas, agentes, parâmetros e integrações com organização em seções.
- **YAML:** definição completa do recurso, referências e relações, com validação e diff.

Excluir, mover, arquivar ou alterar herança deve exibir impactos sobre pipelines, permissões, históricos e referências. Não permitir operações destrutivas implícitas ou ambíguas.

## 10. Equipes, usuários, identidade e permissões

Criar páginas completas para pessoas, equipes/grupos, funções e permissões com busca, filtros, convites, associação a projetos, associação a equipes, escopos e trilha de alteração. Preferir **RBAC** com políticas por escopo e menor privilégio; permitir capacidades granulares quando necessárias.

- **Visual:** matriz de permissões, relação hierárquica entre equipes e projetos, arrastar e soltar para associações simples com confirmação clara e visualização de permissões efetivas.
- **Formulário:** convites, vinculação a grupos, atribuição de papéis, políticas de acesso, identidade federada e revogação.
- **YAML:** declaração de equipes, papéis, políticas e vínculos de identidades por referências estáveis.

**Limite obrigatório:** configuração de autorização pode ser YAML, mas autenticação, validação de identidade, sessões, senhas e credenciais reais pertencem ao provedor de identidade e ao subsistema de segurança. Um arquivo YAML não deve criar, por si só, uma identidade autenticada ou conter credenciais. Proteger a própria alteração de permissões contra escalonamento de privilégios e perda de acesso administrativo.

## 11. Plugins e integrações

Ter uma área dedicada a descoberta, instalação, atualização, configuração, ativação, desativação, saúde, permissões, compatibilidade, versões, dependências, logs e remoção de plugins. Distinguir **plugin instalado**, **plugin ativo**, **plugin compatível**, **plugin confiável** e **plugin com erro**.

- **Visual:** catálogo pesquisável, fluxos de integração, diagramas de dependência quando úteis e interfaces de mapeamento de eventos/ações.
- **Formulário:** assistente de instalação, escopos, versões, opções, credenciais referenciadas e políticas.
- **YAML:** habilitação, escopo, versão pinada, configuração e permissões solicitadas.

Plugins devem ter manifesto, contratos versionados, verificação de origem/integridade, limites de privilégios, política de atualização e modelo de isolamento. Rejeitar plugins JVM de TeamCity como compatibilidade nativa e não permitir execução irrestrita de DSL no servidor. A gestão visual pode ser especificada desde cedo, mas execução de plugins externos só deve ser ativada após o mecanismo de isolamento e assinatura/verificação estar demonstrado.

## 12. Designer visual de pipelines

O designer deve tratar pipelines como um **grafo dirigido acíclico (DAG)** quando houver dependências e como sequência simples quando o fluxo for linear. Oferecer biblioteca de tarefas, sidebar de componentes, canvas central e inspector de propriedades.

Suportar criação por clique ou arrastar e soltar, posicionamento, conexão entre etapas, seleção múltipla, duplicação, exclusão, agrupamento, zoom, pan, minimapa para grafos grandes, ajuste automático de layout, alinhamento, desfazer/refazer e navegação por teclado. Estabelecer comportamento determinístico de conexões e pontos de ancoragem.

Cada nó deve comunicar nome, função, condições, dependências, requisitos de agente e estado na visualização de execuções; design de edição e design de monitoramento são modos distintos. Diferenciar passos, jobs, estágios, triggers, gates de aprovação e artefatos por convenções visuais e rótulos, não apenas por cor.

O canvas deve impedir ou sinalizar ciclos, dependências inexistentes, referências quebradas e combinações inválidas. Ele não pode esconder opções avançadas: sempre disponibilizar inspector, formulário e YAML.

## 13. Formulários avançados e assistência à configuração

A edição tradicional deve ser uma experiência de primeira classe, não uma versão simplificada e limitada do YAML. Usar agrupamento semântico, descrições curtas, valores padrão explícitos, campos condicionais, exemplos contextualizados e validação próxima ao campo.

Oferecer modos **Básico** e **Avançado** quando a quantidade de opções justificar, sem apagar ou sobrescrever configurações invisíveis. Configurações potencialmente perigosas exigem explicação de impacto; não esconder efeitos colaterais em tooltips.

Usar criação orientada por assistente quando o fluxo for complexo, mas permitir edição direta para especialistas. Não obrigar o usuário a passar por múltiplas etapas para pequenas alterações. Um formulário só está completo se houver representação inequívoca no YAML.

## 14. Editor YAML e fonte única da verdade

O YAML é a **definição canônica da configuração desejada**, para todos os recursos configuráveis do Ec, inclusive projetos, equipes, políticas, integrações, plugins, agentes/pools e pipelines.

Requisitos do editor:

1. Editor com numeração de linhas, realce, autocomplete, schema-aware suggestions, documentação contextual, busca, formatação, folding e diff.
2. Validação de sintaxe, esquema, tipos, valores, referências, dependências, permissões e regras de negócio antes da publicação.
3. Mensagens de erro com arquivo, linha, coluna, caminho lógico, causa e correção recomendada.
4. Identificador de API/schema versionado por recurso, migração compatível e mensagens claras de depreciação.
5. Rejeitar chaves duplicadas e limites perigosos de aliases, tamanho, profundidade e expansão; impedir tags arbitrárias que instanciem objetos/execução.
6. Preservar comentários, ordenação de apresentação e estilo sempre que tecnicamente possível. A preservação **exata** de formatação não é garantida por um parser sem suporte a edição com informação de origem; quando não puder ser preservada, apresentar o diff antes de regravar.
7. Usar listas YAML quando a ordem for semanticamente importante; não inferir ordem de execução a partir da ordem de chaves de um mapping.
8. Não introduzir silenciosamente defaults que alterem semântica, nem descartar campos desconhecidos ou específicos de extensões.
9. Exibir a **versão publicada/efetiva**, a **versão proposta** e a **revisão usada em cada execução**, sem confundir conceitos.
10. Proibir publicação de YAML inválido; rascunhos inválidos podem existir apenas como rascunhos não efetivos.

## 15. Paridade entre Visual, Formulário e YAML

Os três modos são interfaces sobre o **mesmo modelo semântico**, não três mecanismos diferentes.

- Alterar um nó no canvas atualiza o modelo do rascunho e sua representação YAML.
- Alterar YAML válido atualiza formulários e grafo de forma determinística quando o recurso puder ser projetado visualmente.
- Alterar um campo no formulário atualiza o mesmo modelo, sem criar estado de configuração autônomo.
- Uma alteração inválida no YAML não pode corromper os outros modos nem modificar a configuração efetiva.
- Campos que não têm representação visual adequada continuam editáveis por formulário/YAML; o canvas deve indicar que há configurações avançadas.
- Recursos que não podem ser representados sem perda no visual ou formulário permanecem acessíveis via YAML; nesses casos, desativar edição visual incompatível ou mostrar claramente a limitação. **Nunca descartar configuração silenciosamente.**

Testar round-trip semântico: **YAML → modelo → UI → modelo → YAML** deve preservar o significado, inclusive referências e recursos desconhecidos suportados pelo contrato. A equivalência semântica é obrigatória; equivalência textual byte a byte só quando explicitamente garantida.

## 16. Fluxo de edição, versionamento e sincronização

Toda modificação deve seguir um fluxo inteligível:

**Selecionar recurso → escolher modo de edição → editar rascunho → validar → inspecionar diff e impacto → autorizar/aprovar → gravar YAML versionado → reconciliar → indicar estado efetivo.**

Preferir um repositório Git de configuração canônica, com histórico, revisão imutável, revisão humana e integração a pull requests quando a política exigir. O produto também pode encapsular a experiência Git para quem só usa UI. A edição via interface não deve escapar ao controle de revisão. Para cada publicação, armazenar referência verificável de revisão e hash; jobs devem fixar a revisão efetiva no momento de submissão.

Prever conflitos por edição concorrente, diferenças entre rascunho e versão publicada, falhas de sincronização, recursos órfãos e **drift**. Exibir diff de três vias e orientar resolução quando necessário; nunca usar “última gravação vence” silenciosamente.

Se houver cache ou projeção em banco de dados para consulta rápida, tais estruturas devem ser **derivadas e reconstruíveis** a partir da configuração YAML, não fonte alternativa da configuração desejada. A UI deve distinguir `Draft`, `Validated`, `Pending approval`, `Published`, `Reconciling`, `Synced`, `Drifted` e `Failed`.

## 17. Modelo de recursos YAML

Definir convenção de recursos com esquema estável, por exemplo `apiVersion`, `kind`, `metadata` e `spec`, adequada à semântica do Ec. O modelo abaixo é **ilustrativo, não uma implementação validada nem um contrato definitivo**:

```yaml
apiVersion: ec.dev/v1alpha1
kind: Pipeline
metadata:
  name: backend-ci
  project: plataforma/api
spec:
  repository: api-main
  triggers:
    - type: push
      branches: [main]
  jobs:
    - id: test
      agent:
        pool: linux
      steps:
        - id: compile
          run:
            executable: cargo
            args: [test, --locked]
    - id: package
      needs: [test]
      steps:
        - id: build
          run:
            executable: cargo
            args: [build, --release, --locked]
```

Não usar esse exemplo como evidência de suporte a campos ou recursos. Especificar e testar o schema real antes de construir editores, templates e exemplos oficiais. Utilizar referências a segredos, nunca segredos embutidos.

## 18. Repositórios, gatilhos, parâmetros e templates

Oferecer gestão visual e tradicional de conexões Git, branches, revisões, webhooks, credenciais referenciadas, triggers, agendas, filtros, parâmetros e templates. Explicitar a diferença entre código-fonte do aplicativo e repositório que contém a configuração canônica do Ec, caso sejam distintos.

Exibir simulação de eventos/condições de trigger, impacto de alterações em templates, origem de parâmetros herdados e precedência de overrides. Builds sempre devem apontar para revisão imutável do código e da configuração aplicada.

## 19. Agentes, pools e capacidade

Criar telas para agente, pool e capacidade com status, conexão, sistema operacional, recursos relevantes, slots, tags/capabilities, compatibilidade com jobs, workloads ativos, histórico e diagnósticos. Mostrar o motivo específico pelo qual um job está ou não está elegível para um agente.

- **Visual:** distribuição por pools, associação de jobs, visualização de capacidade e de filas; drag-and-drop para associações seguras.
- **Formulário:** configuração de pools, requisitos, quotas, políticas de execução e manutenção.
- **YAML:** especificações declarativas de pools, políticas, limites e requisitos.

Distinguir configuração declarativa do pool do estado registrado pelo agente (heartbeat, disponibilidade, leases, consumo de recursos), que é operacional e não deve ser escrito como YAML de configuração.

## 20. Execuções, logs, testes e artefatos

Exibir pipeline em execução com status de cada etapa, duração, timestamps, agente, revisão de configuração, revisão de código, causa do disparo e ações autorizadas. Logs devem ser incrementais, pesquisáveis, filtráveis e correlacionados a job/step, preservando ordenação, índices de chunk e tratamento de reconexão.

Relatórios de testes devem permitir distinguir falha de teste, falha de build, erro de infraestrutura e cancelamento. Artefatos precisam de nome, tamanho, tipo, verificação de integridade, origem, retenção, permissões e links seguros.

A UI deve expor retry como **nova tentativa sob política explícita**, sem sugerir que operações externas são exatamente uma vez. Cancelar deve distinguir solicitação enviada, cancelamento em andamento e confirmação final.

## 21. Ambientes, aprovações, releases e deployments

Projetar área própria para ambientes (`development`, `staging`, `production` ou nomenclaturas personalizadas), promoções, releases, gates, janelas de mudança, aprovações, estados, evidências e rollback. A configuração desejada destes recursos segue YAML; eventos e histórico operacional permanecem no banco apropriado.

Aprovações críticas devem exibir artefato, revisão, destino, autor, riscos, políticas aplicadas e consequências. Nunca tratar aprovação visual como mera decoração. A interface não pode prometer rollback automático para serviços que não possuem estratégia concreta e comprovada.

## 22. Confiabilidade e consistência operacional

A experiência visual deve refletir a arquitetura de execução: monólito modular em Rust no control plane, PostgreSQL para transações, fila e estado operacional, agentes Rust isolados e protocolo HTTP versionado. Reservas transacionais, leases, fencing, idempotência e retries explícitos são requisitos de domínio, não elementos opcionais de interface.

Mostrar com clareza problemas de agente desconectado, lease expirado, item reencaminhado, falha transitória, quota, timeout, cancelamento e indisponibilidade de serviços. Não prometer execução distribuída `exactly once`; os efeitos externos de um job podem exigir idempotência própria.

Não propor microsserviços como padrão sem medição demonstrando a necessidade. A interface deve depender de contratos de domínio estáveis e não da fragmentação física do servidor.

## 23. Segurança e governança

Aplicar autenticação confiável, autorização por ação/recurso, segregação de funções, auditoria, MFA/SSO quando disponível, proteção de webhooks, prevenção de abuso e proteção contra escalonamento de privilégios.

Workloads de repositórios não confiáveis devem executar sob política de isolamento, recursos limitados e separação de pools/credenciais. Não instalar plugins arbitrários, montar segredos de produção ou conceder privilégios elevados sem controles proporcionais.

Pré-visualizar mudanças de alto risco, exigir aprovação quando configurada, validar permissões no backend, usar políticas de menor privilégio e mascarar segredos em logs e interfaces. Distinguir claramente uma ação permitida, negada e pendente de aprovação.

## 24. Pesquisa, navegação e produtividade

Implementar busca global com contexto e filtros; breadcrumbs; recentes; favoritos; comandos rápidos; links profundos; links copiáveis; ações em lote seguras; navegação por teclado e atalhos descobríveis.

Tabelas extensas devem oferecer ordenação, filtro, paginação ou virtualização, personalização de colunas e preservação de estado de navegação. Não transformar qualquer coleção em cards; escolher tabela, árvore, lista, timeline, grafo ou cards conforme densidade e relação dos dados.

Detalhes de uma entidade devem ser acessíveis sem perda do contexto anterior. Evitar modais gigantes para fluxos complexos; preferir páginas e painéis de edição adequados à tarefa.

## 25. Microinterações, movimento e feedback

Toda ação deve retornar sinal perceptível de início, progresso, sucesso, erro, cancelamento ou indisponibilidade. Estados devem ser informativos e acessíveis. Adotar animação curta e funcional para hierarquia, transição de painel, conexão de nós e reorganização; respeitar preferência por redução de movimento.

Não usar loaders intermináveis, optimistic UI para operações administrativas críticas sem estratégia de reversão, notificações que desapareçam antes da leitura nem animações que mascarem lentidão.

Proibir controles falsos ou superficiais em imagens que pretendem representar um produto implementável.

## 26. Acessibilidade e internacionalização

Estabelecer **WCAG 2.2 nível AA** como critério de referência para interfaces web. Aplicar contraste adequado, indicação de foco visível, navegação completa por teclado, semântica apropriada, nomes acessíveis, mensagens de erro identificáveis, ajuda contextual, feedback que não dependa apenas de cor e respeito a preferências de movimento.

O designer visual deve oferecer modo alternativo estruturado (árvore/lista de dependências, formulários e operações via teclado) equivalente em capacidade. Não depender exclusivamente do arrastar e soltar.

Preparar localização de textos, datas, números, fusos horários e duração. Evitar strings embutidas em imagens e layout que dependa de um único idioma.

## 27. Desempenho e eficiência

A elegância também depende de responsividade percebida. Estabelecer orçamentos e medir em máquinas e projetos representativos, separando metas de resultados verificados.

Priorizar carregamento progressivo, code splitting, virtualização de listas/logs, desacoplamento da renderização do canvas, controle de atualizações em tempo real, reconexão, caching seguro e atualização seletiva de dados. Evitar polling agressivo, re-renderização completa de grafos a cada evento e dependências pesadas sem benefício demonstrado.

No frontend, usar Vue.js + TypeScript com componentes tipados, APIs claras e design tokens. Não presumir que uma biblioteca visual é mais rápida ou leve sem benchmark reproduzível.

## 28. Componentes e estados mínimos

O design system deve contemplar navegação global e contextual; tabelas densas; árvores hierárquicas; listas; grids; cards informativos; gráficos; timeline; badges de estado; painéis de inspeção; breadcrumbs; search/command palette; filtros; formulários; validação; selects complexos; ações em lote; diálogos de confirmação; diff viewer; editor YAML; logs streaming; DAG/canvas; tooltips; ajuda; toasts; banners; skeletons; empty states; erros; permissão negada; conflitos; sincronização e falha parcial.

Cada componente deve ter estados `default`, `hover` quando pertinente, `focus`, `active`, `selected`, `disabled`, `loading`, `error`, `warning` e `success` quando semânticos. Não criar componentes específicos para telas isoladas quando houver padrão reutilizável adequado.

## 29. Regras para geração de imagens, mockups e protótipos

Toda imagem ou proposta de tela deve representar um **caso real do Ec**, com função, usuário, ação principal, informação central e resultado esperado. A interface retratada deve obedecer ao design system compartilhado e mostrar dados verossímeis e terminologia técnica coerente.

Regras de composição visual:

1. Definir antes da geração: tela, função, persona, viewport, tema, estado, densidade, hierarquia, componente principal e interações críticas.
2. Mostrar barras, menus, tabelas, diagramas, painéis e conteúdos em proporções plausíveis para o viewport.
3. A área dominante deve corresponder à tarefa central: grafo no designer, tabela na gestão de usuários, histórico na página de execuções, editor no modo YAML.
4. Não preencher toda tela com widgets, porcentagens ou gráficos sem relação com a tarefa.
5. Textos, nomes de campos, botões, códigos, labels e estados devem ser legíveis, coerentes e sem pseudoidioma; abreviações devem ser intencionais.
6. Evitar arestas cruzadas desnecessárias em grafos, tabelas incoerentes, estatísticas impossíveis e informações de um recurso exibidas como se fossem de outro.
7. Não inventar tela “bonita” que não possa ser implementada com a arquitetura e os contratos previstos.
8. Mostrar estados relevantes: vazio, carregando, sucesso, falha, validação YAML, conflito de revisão, permissões, offline e reconciliação.
9. Para coleções de imagens, conservar a mesma navegação, marca, tokens, componentes, nomenclatura e comportamento entre telas.
10. Tratar imagens estáticas como **referência de interface**, não como teste de usabilidade nem prova de funcionamento. Para validar interação, produzir protótipo navegável e testes.

## 30. Cobertura mínima de telas

Criar e manter especificações visuais para:

| Área | Telas e interações obrigatórias |
|---|---|
| Visão geral | Overview global, saúde, falhas recentes, atividade, alertas |
| Projetos | Lista, árvore, criação, detalhe, edição, permissões, políticas, revisão YAML |
| Equipes | Lista, criação, composição, projetos vinculados, permissões efetivas |
| Usuários | Lista, convite, detalhe, papéis, grupos, sessões/auditoria conforme política |
| Papéis e permissões | Matriz visual, formulários, configuração YAML, simulação de permissão |
| Plugins | Catálogo, detalhe, instalação, configuração, versões, permissões, saúde |
| Integrações | Git/VCS, webhooks, notificações, autenticação, credenciais referenciadas |
| Pipelines | Lista, modelos, criação, designer DAG, formulário, editor YAML e diff |
| Execuções | Fila, histórico, execução ativa, logs, testes, falhas, artefatos |
| Agentes | Inventário, detalhe, pools, capacidades, compatibilidade, manutenção |
| Deployments | Ambientes, releases, promoções, aprovações, histórico, rollback suportado |
| Administração | Configuração global, segurança, auditoria, retenção, observabilidade |
| Estados transversais | Carregamento, vazio, falha, acesso negado, desconectado, conflito, drift |

A primeira coleção visual não pode conter somente telas de pipeline. Deve evidenciar, ao menos, um fluxo de projeto, um fluxo de pessoas/acesso, um fluxo de plugins/integrações e um fluxo de pipeline/YAML.

## 31. Critérios de briefing obrigatório para novas telas

Toda solicitação de criação visual deve incluir, ou explicitar hipóteses para:

- **Área e tela:** qual recurso será mostrado e onde pertence na navegação.
- **Objetivo do usuário:** ação concreta e resultado verificável.
- **Persona e permissão:** administrador global, administrador de projeto, desenvolvedor, operador, auditor ou outra.
- **Modo:** Visual, Formulário, YAML, consulta ou monitoramento.
- **Viewport:** dimensões e plataforma-alvo, incluindo comportamento responsivo necessário.
- **Tema e densidade:** claro/escuro; normal/compacto.
- **Estado:** vazio, em edição, validando, publicado, executando, falha, conflito etc.
- **Dados e entidades:** exemplos realistas, unidades e relações consistentes.
- **Ação principal, secundárias e riscos:** salvar, publicar, reverter, aprovar, excluir, executar etc.
- **Fonte da verdade:** recurso YAML, revisão, status de sincronização e forma de publicação.
- **Interação:** o que ocorre ao clicar, arrastar, editar, cancelar, validar e confirmar.
- **Restrições:** acessibilidade, segurança, desempenho, conteúdo sensível e consistência visual.

Quando faltar contexto não essencial, documentar hipótese e criar a solução sem inventar comportamento de produção. Quando faltar uma restrição de segurança essencial, adotar opção conservadora.

## 32. Entregáveis de design e engenharia

Para cada recurso significativo, produzir conforme sua fase:

1. Mapa de navegação e modelo de entidades.
2. User flows, casos de uso, estados e critérios de permissão.
3. Wireframes e alternativas de layout com justificativa técnica.
4. Mockups nos temas claro/escuro para desktop e variação responsiva relevante.
5. Componentes reutilizáveis e seus tokens/estados.
6. Protótipo interativo para fluxos críticos.
7. Contrato YAML e validações correspondentes, com exemplos válidos/inválidos.
8. Contratos de API e eventos, incluindo autorização e erros.
9. Testes funcionais, de acessibilidade, de equivalência entre modos e de conflito.
10. Revisão conjunta de produto, design, frontend, backend e segurança.

Nenhuma imagem estática substitui o contrato funcional. Nenhum componente deve entrar em produção apenas por aprovação estética.

## 33. Ordem recomendada de implementação

**Fase A — fundamentos de produto e arquitetura:** modelo de entidades, mapa de navegação, design system, schemas YAML versionados, políticas, contratos de sincronização, protótipos dos três modos; tipagem de domínio Rust/TypeScript e validação unificada. A UI deve ser desenhada cedo, mesmo quando nem todas as telas estejam implementadas.

**Fase B — núcleo funcional seguro:** PostgreSQL, API, fila durável, scheduler transacional, leases/fencing, agente Rust, checkout em revisão fixa, steps, logs, artefatos, cancelamento e estados operacionais. Entregar interface mínima real para projetos, pipeline básico, YAML e execução, sem alegar completude administrativa.

**Fase C — paridade de edição e gestão:** formulários completos, editor YAML, designer visual semântico, diff/publicação/rollback de configuração, recursos de projetos, equipes, usuários, RBAC, agentes/pools, filtros e auditoria.

**Fase D — expansão de CI/CD:** DAGs complexos, webhooks, templates, testes estruturados, cache seguro, regras avançadas, múltiplos agentes, ambientes, gates, releases e deployments.

**Fase E — ecossistema e escala:** gestão e execução segura de plugins, integrações avançadas, observabilidade aprofundada, políticas corporativas, isolamento para workloads não confiáveis, HA quando justificado e otimização com benchmarks.

A ordem é uma **recomendação de engenharia**, sujeita a dependências e gates de aceitação. O catálogo de telas e o design system devem contemplar a visão completa desde o início, enquanto cada funcionalidade é marcada como `planejada`, `em desenvolvimento`, `validada` ou `disponível`.

## 34. Critérios objetivos de aceitação

Rejeitar uma tela/funcionalidade se qualquer dos casos abaixo ocorrer:

- Um ajuste persistente pode existir exclusivamente em estado local do frontend ou no banco sem YAML correspondente.
- As modalidades Visual, Formulário e YAML produzem resultados semanticamente divergentes.
- Salvar no formulário descarta um campo YAML não exposto no formulário.
- Uma edição publicada altera execuções anteriores ou muda a revisão que um job já fixou.
- Usuários podem publicar permissões que elevem seu próprio privilégio sem controle apropriado.
- YAML permite executar tags arbitrárias, chaves duplicadas aceitas silenciosamente ou segredos inline.
- O canvas é inutilizável sem mouse e não existe alternativa equivalente.
- A UI indica sucesso antes de o backend confirmar a operação relevante.
- Dados críticos dependem somente de cor ou ficam inacessíveis em viewport pequeno.
- Uma operação perigosa não informa seu impacto ou ocorre sem autorização.
- Métricas são exibidas sem escopo/período ou como números aparentemente reais sem base.
- Plugins recebem permissões/execução sem verificação e isolamento adequados.
- A interface mascara estados de agente, fila, sincronização, conflito ou perda de conexão.
- O fluxo inteiro de projetos, equipes, usuários, plugins ou pipelines só pode ser concluído por um único modo de edição sem justificativa técnica documentada.

**Ensaios mínimos:** criar projeto pelos três modos; editar equipe por formulário e conferir YAML; alterar DAG visual e validar YAML; editar YAML e inspecionar canvas; detectar conflito entre edições simultâneas; rejeitar schema inválido; confirmar que secrets são referências; verificar permissões; recarregar e reconstruir projeções; fixar revisão de execução; testar teclado, leitor de tela, tema escuro e layout responsivo; executar CI real com agente sob falhas de conexão.

## 35. Organização e controle de continuidade

Padronizar nomes de componentes, telas, arquivos de design, especificações e schemas. Registrar para cada alteração a área do produto, persona, versão, status, referência de requisito, decisão de UX, contrato YAML, estado coberto, responsável técnico e evidência de teste.

Manter registro de decisões de arquitetura e design. Alterações globais de navegação, semântica de estado, marca ou tokens devem ser aplicadas consistentemente, sem criar uma segunda linguagem visual.

## 36. Regra final

Cada tela do Ec deve responder com clareza **onde estou, o que estou vendo, o que posso fazer, quais serão os efeitos, qual configuração YAML define esse comportamento e qual é o estado real do sistema**.

A medida de qualidade será **clareza funcional, excelência visual, consistência declarativa, segurança, acessibilidade, desempenho, capacidade administrativa e confiança operacional**. Uma solução só é considerada concluída quando pode ser implementada, utilizada e validada — não apenas quando parece moderna.

---

## Compatibilização com os documentos anexados

As diretrizes acima usam os arquivos `README(3).md`, `decision-matrix.md` e `implementation-plan.md` fornecidos como base arquitetural. As novas decisões de produto alteram especificamente:

| Tema | Pesquisa inicial | Diretriz atual para Ec |
|---|---|---|
| Configuração declarativa | JSON em F02 e no início do plano | **YAML como único formato de arquivo de configuração** |
| Escopo de projetos | Projeto simples no núcleo; templates adiados | **Visão completa de projetos/subprojetos**, implementada incrementalmente |
| Interface | UI como capacidade posterior | **Design system e arquitetura de UX desde a fase inicial**; interface funcional mínima em conjunto com o núcleo |
| Edição | Sem compromisso de três modos equivalentes | **Visual, Formulário e YAML** sobre modelo único e validação compartilhada |
| Plugins | Extensões externas adiadas | **Gestão de plugins no escopo de produto**; execução continua condicionada à segurança |
| Persistência operacional | PostgreSQL como fonte da fila/estado | **Mantida**, sem conflitar com YAML como autoridade de configuração |
| Agentes/scheduler | Rust, HTTP versionado, leases/fencing | **Mantidos** como invariantes técnicos |

Os documentos anexados são resultados de pesquisa e planejamento; não comprovam implementação de servidor/agente ou validação dinâmica de TeamCity.

## Referências técnicas de apoio

- YAML 1.2.2 — especificação oficial: https://yaml.org/spec/1.2/
- OWASP — CI/CD Security Cheat Sheet: https://cheatsheetseries.owasp.org/cheatsheets/CI_CD_Security_Cheat_Sheet.html
- W3C — WCAG 2.2: https://www.w3.org/TR/WCAG22/
- W3C — ARIA Authoring Practices: https://www.w3.org/WAI/ARIA/apg/
- MDN — CSS Container Queries: https://developer.mozilla.org/en-US/docs/Web/CSS/Guides/Containment/Container_queries
- JetBrains TeamCity — Creating and Editing Projects: https://www.jetbrains.com/help/teamcity/creating-and-editing-projects.html
- JetBrains TeamCity — Creating and Editing Build Configurations: https://www.jetbrains.com/help/teamcity/creating-and-editing-build-configurations.html
- Argo CD — reconciliação declarativa e detecção de drift (referência conceitual, não dependência): https://argo-cd.readthedocs.io/en/latest/

**Nota:** as referências externas fundamentam conceitos e práticas. A definição do produto Ec, a nomenclatura dos recursos e a divisão em fases são decisões propostas neste documento, não especificações oficiais de outro produto.
