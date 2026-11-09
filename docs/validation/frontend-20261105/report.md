# T006 — interface web integrada, 2026-11-05

REQ-06 → D-06/R-06 → T006 → `application/server/frontend/` e extensões HTTP em service.rs → Firefox do sistema com Server/PostgreSQL/Agent/Git reais → AC-06. A feature conserva AC-01 e AC-07 pendentes. Evidências históricas da feature anterior estão em previous.zip; resultados anteriores em seus diretórios originais não foram reescritos.

## Implementação e oráculos

Vue 3.5.45, Pinia 4.1.0, TypeScript 5.9.3, Vite 7.3.1 e lockfile npm. Versões efetivas de Node/Python/Firefox e ambiente em environment.json e browser-results.json. Rust 1.99.1, edition 2024, PostgreSQL no container dedicado `ec-publication-test`. Binários/workloads Linux locais confiáveis, sem credenciais externas. As extensões foram especificadas na spec antes de implementar: consulta de sessão, projetos acessíveis, agentes por projeto e validação sem persistência. Sem migrations novas.

O ensaio `application/integration/tests/e2e/frontend_firefox.py` usa Firefox `/usr/bin/firefox` com Marionette e perfil temporário, Server Rust real, banco UUID descartável, repositório Git e Agent Rust real. Preenchimento de campos usa eventos DOM, cliques e Tab usam WebDriver nativo. Comparações independentes: projeção do parser/schema Rust versus campos esperados; SHA-256 dos bytes YAML consultados; revisão/hash/commit do run após nova publicação; bytes baixados versus arquivo real do Git; consultas SQL e contagem de publicações sem efeitos nas negações. Nenhum token é escrito em URL, YAML, screenshots ou logs de evidência. O teste apaga banco, processos e dados temporários.

A validação por viewer aceita apenas a semântica e não publica nem admite repositórios; snapshot SQL permanece igual. Cobertura inclui duplicatas, campos desconhecidos, null/coleções/anchors/tags, limite de nome e documento e interpretação decimal textual. Os testes parser/schema existentes ampliam rejeições para aliases, merge, documentos múltiplos, traversal, scripts e escalares excessivos.

O navegador demonstra Visual/Formulário/YAML, preservação de comentário ao rejeitar a reserialização, escolha explícita antes de perda textual, YAML inválido e bloqueio de publicação. Um transporte deliberadamente ignora AbortSignal e atrasa uma validação anterior: a projeção atual não volta à resposta obsoleta. Conflito 409 conserva o YAML; nova base é adotada por ação explícita. Perda de resposta é injetada no fetch somente depois da resposta HTTP real e do commit; consulta de recibo recupera a mesma identidade sem nova revisão. Um segundo teste usa failpoint real do Server e replay do mesmo payload.

O Agent executa checkout e script reais. UI consulta stdout/stderr com UTF-8 inválido, ESC e texto `<script>` sem criar elementos HTML. Download reconsulta e verifica metadados, e arquivo salvo pelo Firefox conserva os bytes nulos/inválidos originais. Processo running cancelado distingue intenção persistida de estado terminal; execução já terminal recebe resposta sem efeito. Outra execução produz mais de 16 MiB de saída real: UI mostra truncamento, artifactError e impede download do artefato ausente, mesmo com sucesso do processo. Corrupção de tamanho/hash e sequência é rejeitada por testes unitários do consumidor de bytes.

Polling desconectado mantém estado identificado como possivelmente desatualizado e reconecta. Viewer tem mutações desabilitadas e requisições HTTP diretas retornam 403 sem publicar. Expiração/revogação reais removem o contexto privado; sessão de outro projeto não recebe opções/dados do projeto anterior. Viewports efetivos: claro 1440×964 CSS px e escuro 292×442 CSS px, foco do skip link e Tab nativo exercitados. Firefox limita a janela externa estreita a 500 px; zoom real 1,7× via API do navegador no perfil temporário permite verificar reflow abaixo de 320 CSS px. Dimensões e DPR estão no browser-results.json. Não há certificação WCAG, leitor de tela ou matriz de todos os navegadores demonstrados.

## Comandos e resultados

`checks.json` registra comandos e exit codes; logs individuais preservam saída completa. Inclui Firefox, vue-tsc, build Vite, Vitest (seis testes, incluindo contraste WCAG de texto e limites/foco nos dois temas), cargo test/clippy/fmt e rebuild test-failpoints antes de publication_http.py, orchestration_http.py e agent_http.py em sequência. `governance-checks.json` registra os auditores e o gate agregado. Consulte esses arquivos para resultados efetivos; estrutura não substitui produto.

As fontes observadas, inclusive implementações, testes, manifests e lockfiles, estão em observed-source-inputs.json. Os inputs de evidência SDD são hash dos bytes realmente observados, após reexecução; o registro de revisão semântica cobre os documentos finais da feature. A seleção e contribuição de cada skill/ponte está em skills.json. Fontes oficiais usadas: [watchers Vue](https://vuejs.org/guide/essentials/watchers.html), [proxy Vite](https://vite.dev/config/server-options) e [Marionette](https://firefox-source-docs.mozilla.org/testing/marionette/Protocol.html).

## Inspeção visual

[Visual claro](visual-light.png), [YAML claro](configuration-light.png), [execução real](run-real-firefox.png) e [escuro móvel](configuration-dark-mobile.png). Screenshots demonstram composição e estados visuais; o script, logs e oráculos acima demonstram interação e integração. Não são substitutos.

## Falhas encontradas e corrigidas

A revisão de contraste reforçou a borda clara dos controles; seis testes frontend passaram após o ajuste. O primeiro build encontrou um import CSS inválido, removido. O primeiro round-trip no navegador detectou retorno de objeto bruto na criação do rascunho; corrigido para devolver proxy reativo e coberto por teste Pinia. Harness encontrou corridas ao clicar antes da validação, ao mudar sessões com a página anterior ativa e ao revogar enquanto um controle era desmontado: espera por estado observável e polling posterior à revogação substituem essas premissas. A limpeza do perfil passou a aguardar o processo Firefox terminar antes de remover o diretório. Os ensaios finais são posteriores às correções; tentativas intermediárias não são declaradas PASS.

## Limites e continuidade

Somente loopback, jobs Linux cooperantes, Git admitido, sem segredos externos/isolamento hostil/exactly-once. Rascunhos e pedido incerto recuperáveis dentro da sessão em memória; reload/fechamento/expiração descartam dados privados. Inventário Agent é snapshot, não heartbeat nem prova de elegibilidade. Logs pós-processo, consulta integral autorizada com renderização paginada; sem SSE. Listagens de Server e retenção continuam com os limites T004/T005. Implantação remota/TLS e conformance completa WCAG exigem trabalho próprio; este ensaio usa Firefox efetivo em Linux.

T001: especificar schema de referências de segredos, resolução/materialização segura no Agent, negações de referência ausente/negada, redaction/limpeza e testes sem token literal antes de aprovar AC-01. T007: reutilizar esta fixture de PostgreSQL/Git/Server/Agent/Firefox, acrescentar o conjunto completo de rejeições da spec e os segredos da T001, com oráculos independentes e aceite integrado próprio. AC-07 não é aprovado por este ensaio T006. Gate agregado deve falhar enquanto AC-01/AC-07 permanecerem pendentes. Sem commit/tag/publicação externa.
