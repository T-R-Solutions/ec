# Interface web — T006

Interface Vue 3/TypeScript/Pinia executável em `application/server/frontend/`. Escopo: projetos concedidos, ProjectPipeline ec/v1alpha1 de uma etapa, publicação, execuções, saída e agentes do projeto. Apenas Linux confiável, Git local admitido e nenhuma credencial externa. Não implementa DAG, IdP, senha, pools ou segredos por referência.

Da raiz, depois de preparar Server/PostgreSQL, sessões e repositório conforme [publicação](publication.md), [orquestração](orchestration.md) e [Agent](agent.md):

```bash
npm --prefix application/server/frontend ci --ignore-scripts
npm --prefix application/server/frontend run dev -- --port 5173 --strictPort
```

Abra http://127.0.0.1:5173. Vite encaminha `/api` ao Server loopback em 58139. Para usar essa configuração, execute o Server com `EC_PORT=58139`; a CLI operacional mantém `EC_DATABASE_URL` somente no processo Server. Agent usa sua configuração e credencial separadas. Não coloque essas credenciais no navegador. O build gera `dist/`; implantação remota, TLS e proxy de produção não são demonstrados por este incremento.

A entrada recebe o bearer opaco provisionado pelo administrador via CLI atual, sem identidade ou papel selecionável. O token fica capturado somente em memória na função cliente, nunca em URL, storage web ou YAML. O campo é limpo ao enviar. Recarregar a aba exige nova entrada. A sessão expira em oito horas pelo relógio PostgreSQL; `revoke` administrativo e “Revogar e sair” a invalidam no Server. Uma resposta 401 descarta o contexto privado e volta à entrada. 403 não confirma operação; projetos invisíveis retornam 404. Não há logs de headers ou captura de tráfego com tokens nos ensaios.

Pinia mantém sessão e rascunhos em memória por projeto. Navegação entre páginas/projetos preserva rascunho, base e pedido incerto na mesma sessão. Troca/expiração de sessão descarta o contexto privado, inclusive os rascunhos em memória. Fechar/recarregar a aba não oferece recuperação durável; copie o YAML antes de sair se precisar conservá-lo. Viewer pode editar um rascunho e validar, mas controles de mutação ficam desabilitados e o Server rejeita requisições diretas.

Visual é uma etapa real com campos editáveis; Formulário e YAML representam o mesmo modelo. A validação sem efeitos usa o parser/schema Rust, preservando sua interpretação textual de escalares e seus limites. O último modelo válido permanece visível quando o texto é inválido. Edição estruturada propõe a serialização completa: mostra antes/depois e exige escolha explícita para perder comentários/apresentação. Nenhum modo altera bytes apenas por ser aberto. SHA-256 identifica bytes YAML, não equivalência semântica. Publicação continua responsável por referências e autorização.

A base do rascunho é explícita. “Carregar publicada” substitui o rascunho por ação expressa; conflitos oferecem comparação e adoção explícita da base sem descartar o texto. Não há merge automático. Pedido incerto conserva requestId e payload: consultar recibo ou repetir o mesmo pedido. Um novo requestId não contorna resposta incerta. A revisão escolhida para submissão é independente do rascunho; recibo e execução mostram revisão/hash/commit fixados pelo Server.

Consultas de execuções são polling sequencial a cada dois segundos após a consulta anterior. Cada ciclo tem geração e AbortController. Navegação, troca de projeto e unmount cancelam consultas/timers; geração descarta respostas tardias mesmo se o transporte ignorar abort. Validação usa debounce de 200 ms e a mesma defesa. Dados anteriores ficam identificados como possivelmente desatualizados em falha de conexão; reconexão consulta o Server sem inventar transições. Catálogo de agentes é um snapshot da consulta e não declara heartbeat/capacidade. Nenhuma rota Agent é chamada pela interface.

Logs são chunks hex por tentativa, decodificados com substituição de UTF-8 inválido e controles escapados, interpolação textual Vue sem HTML ou terminal. Paginação de 50 chunks limita a renderização sem descartar os chunks recebidos. A ordem é coleta entre streams; não há transmissão durante o processo no Agent atual. Recibo terminal, lastSequence e truncated delimitam completude conhecida. artifactError distingue sucesso do processo de saída completa. Download reconsulta saída autorizada, exige recibo publicado, ordenação de partes, tamanho e SHA-256 iguais, e entrega Blob dos bytes originais. Divergência bloqueia entrega.

## Verificações reproduzíveis

```bash
npm --prefix application/server/frontend run typecheck
npm --prefix application/server/frontend test
npm --prefix application/server/frontend run build
python3 application/integration/tests/e2e/frontend_firefox.py
```

O último comando usa **`/usr/bin/firefox` do sistema**, Marionette em perfil temporário, sem baixar navegador. O perfil temporário habilita MOZ_REMOTE_ALLOW_SYSTEM_ACCESS apenas para ajustar zoom de reflow via Marionette; não usa nem modifica o perfil pessoal. Exige container dedicado `ec-publication-test` em 55439, binários em `.local/build/ec-server-clean/debug/` e `.local/build/ec-agent-target/debug/`, compilados conforme os guias. Cria banco UUID, Git, sessões e raízes Agent temporários; remove banco em finally. Usa portas 5173, 58139 e 58282. Nunca execute em paralelo com publication_http.py, orchestration_http.py ou agent_http.py. Os comandos usam Docker e criação de processos/browser, sujeitos às permissões reais do ambiente.

O cliente Marionette fica em `frontend/tests/e2e/`; a jornada entre componentes fica em `application/integration/tests/e2e/`. Campos são preenchidos por eventos DOM, cliques e Tab usam WebDriver nativo. Falhas de transporte deliberadas no fetch são identificadas; respostas de domínio e persistência são reais. Testes unitários verificam integridade/corrupção de artefato, bytes de logs, serialização e reatividade/isolação dos rascunhos. Ver [evidências](../validation/frontend-20261105/report.md).

T007 deve reutilizar a fixture, sem substituir seu próprio harness completo/rejeições. T001 deve especificar e implementar referência/resolução segura de segredos antes de jobs com credenciais; não liberar esses jobs por este frontend. Aceite agregado permanece pendente enquanto AC-01 ou AC-07 não forem aprovados.

## Retenção de novas observações

O harness agora usa EC_E2E_ARTIFACT_DIR quando informado ou cria diretório temporário
exclusivo; não grava novamente em docs/validation/frontend-20261105. Pelo runner,
imagens e browser-results.json ficam no run privado. Copiar somente resultados
revisados para um novo registro oficial. Seguir [verificação de tarefas](task-verification.md).
