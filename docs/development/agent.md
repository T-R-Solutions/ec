# Agent local — T005

O Agent Rust executa somente jobs Linux confiáveis, de repositórios Git locais explicitamente admitidos, sem credenciais externas. Server e PostgreSQL continuam autoridade de sessão, fence, lease e retry. Não há isolamento de workloads nem garantia exactly-once. Segredos por referência continuam pendentes na T001; UI permanece T006.

## Compilar e executar

Da raiz do checkout, Rust 1.99.1, edição 2024, dependências diretas com faixas
compatíveis e lockfiles atualizáveis durante o desenvolvimento. Consulte a
[política de atualização](verification-tools.md#dependências-rust-em-desenvolvimento).

```bash
export CARGO_HOME="${CARGO_HOME:-$HOME/.cargo}"
export CARGO_TARGET_DIR="$PWD/application/agents/target"
cargo +1.99.1 build --manifest-path application/agents/Cargo.toml --locked --offline
application/agents/target/debug/ec-agent-app /caminho/privado/agent.json
```

Crie uma raiz privada 0700 e configuração local JSON 0600; use `address` (127.0.0.1:porta), `root` (raiz canônica), `credentialFile` (arquivo 0600 produzido por `ec-server-app provision-agent NOME PROJETO ARQUIVO`), `repositories` (lista de caminhos canônicos admitidos) e `once` (true para um trabalho). Não passe token em argv, YAML ou ambiente do script. Configuração de bootstrap não é configuração declarativa de pipeline. O Server precisa de `migrate` até v3 e `serve`, conforme publication.md e orchestration.md. Fonte Git indisponível no Agent produz preparation_failed, mesmo cadastrada no Server.

O checkout é novo por tentativa, detached no SHA do snapshot. `/bin/sh -c SCRIPT` executa no checkout com stdin fechado, PATH=/usr/bin:/bin e HOME no workspace; demais variáveis são removidas. Git não herda configuração global/sistema, não usa templates/hooks nem faz inicialização de submódulos. Clone admite exclusivamente protocolo file/local; não há fetch remoto, credential helper ou retry local do script.

## Supervisão e recuperação

Heartbeat independente a cada 2s usa confirmação conservadora pelo instante monotônico de envio; após mais de 10s sem confirmação o Agent perde autoridade local. Requisições têm connect timeout 2s e read/write timeout 3s. PostgreSQL decide a validade real do lease de 30s e da sessão absoluta de 8h. SIGTERM/INT do Agent interrompe intake e termina o grupo do trabalho; timeout/cancelamento/perda de autoridade também terminam o grupo. Graça de 2s, depois SIGKILL e wait/reap; stdout/stderr têm workers separados. Jobs admitidos não podem escapar do grupo, fechar intencionalmente o lock de exclusão ou permanecer como escritores depois da coleta. Não se promete término universal de processos hostis.

O lock de instância flock é herdado pelos subprocessos cooperantes. Queda abrupta do Agent não mata universalmente descendentes: um reinício na mesma raiz falha enquanto eles mantêm o lock, impedindo execução concorrente sem depender de PID reutilizável. Aguarde a saída ou intervenha de forma operacionalmente verificada; não remova o lock para contornar a exclusão. Depois de adquirir o lock, o reinício cria sessão nova, consulta histórico e registra observação do journal anterior. Não publica spool antigo sob uma tentativa nova nem reexecuta para recuperar somente ACK. O Server pode criar ordinal 2 por perda de sessão/lease; efeitos externos já produzidos podem repetir.

`journal.json` distingue reserved, starting, produced, pending e confirmed. Conteúdo anterior e recibo observado ficam em previous-journal.json após restart. Spool por tentativa e ACKs usam arquivo temporário exclusivo, sync_all, rename e sync do diretório. Falha de escrita/read/sync interrompe a operação sem alegar confirmação; temporário sobrevivente exige inspeção operacional antes de remoção. Não existe SQLite. Arquivos não concedem autoridade. A sequência de confirmação é: logs → artefato íntegro ou artifactError explícito → finish/recibo terminal. Queda entre running e spawn pode consumir uma tentativa sem execução; queda após efeito externo pode repetir efeito no retry do Server.

## Logs, artefatos e limites

Duas drenagens escrevem spool de até 16MiB de bytes por tentativa; após o limite continuam drenando e output.truncated=true. Chunks Agent de até 32KiB, Server aceita até 64KiB. Bytes brutos são hex, sem conversão UTF-8; futuras telas devem escapar controles e apresentar substituição para UTF-8 inválido. Uma etapa implícita (1), sequência global desde 1, stream stdout/stderr; a ordem representa coleta, sem causalidade entre streams. Não há envio durante o processo nesta fatia: o spool é enviado após término, com heartbeat independente durante entrega. Memória de coleta é limitada; requisições/artefato carregam no máximo os limites declarados. Não há redaction arbitrária de segredos embutidos em scripts confiáveis.

Disco: spool JSON hex até aproximadamente 32MiB, cópia do artefato até 32MiB, mais checkout admitido. Até 16 diretórios de tentativa por raiz, depois intake falha explicitamente até manutenção do operador. Tamanho dos repositórios admitidos é responsabilidade do operador; espaço insuficiente produz erro e journal pendente. Sem limpeza automática de origem, checkout ou spool; retenção PostgreSQL permanece indefinida nesta capacidade inicial. Retries de entrega duram no máximo 12s por mensagem e param ao perder autoridade; não há retry local de execução.

Artefato deve ser arquivo regular, caminho relativo com componentes normais, sem symlinks intermediários/finais, até 16MiB. Coleta verifica tamanho e mtime antes/depois e usa O_NOFOLLOW/O_NONBLOCK. Modificação, ausência, erro de leitura, tamanho/tipo ou caminho inválidos produzem artifactError explícito. Processo pode ter sucesso com artifactError: isto significa sucesso do processo e saída incompleta, nunca artefato íntegro. A coleta pressupõe término de todos os escritores cooperantes; não protege contra escritor hostil concorrente no mesmo UID.

Server guarda staging, bytes e metadados no mesmo PostgreSQL. Não há janela entre autoridades blob/banco. artifact-finish verifica bytes/tamanho/SHA-256 antes de publicar recibo. Incompleto/corrompido não tem recibo. GET output distingue upload.receipt=null (staging) de artefato publicado; parts contém bytes em hex para consulta/download autorizado. Logs e ACK estão na mesma transação; replay idêntico retorna ACK, divergência ou lacuna dá 409. Inclusão nova exige autoridade ativa, inclusive após terminal; replay exige sessão vigente e ownership original, mas dispensa lease ativo para conteúdo já aceito.

`finish` estende resultado com output {lastSequence,truncated,artifactError,signal}; exige última sequência persistida e artefato publicado se artifactError=null. failed com sinal conserva exitCode=null e signal positivo, sem código inventado. Cancelamento persistido prevalece conforme T004. Endpoint result legado permanece e não declara completude de saída. Contratos completos em spec.md da feature. Consultas de usuário GET /api/v1/projects/{project}/runs/{run}/attempts/{attempt}/output exigem sessão e papel no projeto; operações Agent não aceitam bearer de usuário.

## Verificação reproduzível

Use somente ec-publication-test em 127.0.0.1:55439, PostgreSQL 18.4. Cada harness cria banco UUID e o remove em finally. Não execute harnesses simultaneamente: usam a mesma porta HTTP 58139. Imagem/digest efetivos e resultados em docs/validation/agent-20261105/.

```bash
CARGO_TARGET_DIR="$PWD/application/server/backend/target" cargo +1.99.1 build --manifest-path application/server/backend/Cargo.toml --locked --offline --features test-failpoints
CARGO_TARGET_DIR="$PWD/application/agents/target" cargo +1.99.1 build --manifest-path application/agents/Cargo.toml --locked --offline --features test-failpoints
python3 application/integration/tests/integration/agent_http.py
python3 application/integration/tests/integration/orchestration_http.py
python3 application/integration/tests/integration/publication_http.py
```

Recompile com test-failpoints imediatamente antes dos harnesses após cargo test/build. Build operacional não contém EC_AGENT_TEST_DROP, EC_AGENT_TEST_PREPARE_GATE ou hooks Server. O ensaio usa processos, filhos, arquivos, Git e SQL reais, incluindo reinícios e respostas perdidas; não é cliente simulado do Agent. Fontes de implementação: [CommandExt](https://doc.rust-lang.org/std/os/unix/process/trait.CommandExt.html) e [locks PostgreSQL](https://www.postgresql.org/docs/18/functions-admin.html).

Limite adicional de spool/ingestão: o Agent agrega leituras parciais em chunks de 32KiB por stream (mais cauda no EOF), limitando arquivos a aproximadamente 514 chunks para 16MiB. O Server rejeita chunks vazios e sequence acima de 2048 por coleção (logs ou partes do artefato), evitando amplificação ilimitada de metadados por mensagens vazias. Rejeição é explícita; nenhum marcador de completude é emitido para entrega rejeitada.
