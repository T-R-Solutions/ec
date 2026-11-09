# Evidência T005 — Agent Rust real

Escopo REQ-05/D-05/R-05/T005/AC-05. Implementação em cinco crates Agent e ec-server-results, conectada por HTTP v1, PostgreSQL e migration 003. [Reprodução e decisões de recuperação](../../development/agent.md). Fontes anteriores preservadas em previous.zip; evidências T004 intactas. Skills efetivamente lidas, ponte versão 1, caminho e SHA-256 em skills.json.

## Ambiente

Linux x86_64, Rust/Cargo 1.99.1, edição 2024. Cache ec-cargo-home e targets ec-agent-target/ec-server-clean em .local/build. PostgreSQL 18.4 READ COMMITTED synchronous_commit=on no container exclusivo ec-publication-test, porta 127.0.0.1:55439. Imagem observada itapiraco/postgis:18-3.6-alpine, ID sha256:b456ff8dec1f076e170d900bd83bade3add0e1db6f55962487513b162094a9fb; sem recursos PostGIS. Nenhum uso de docker-postgis-1 ou banco de trabalho. Cada ensaio cria/remove banco UUID.

## Verificações

http-postgres-final.log: Agent executável real, Server e Git reais; nova publicação não altera snapshot; SQL confirma integridade/hash/tamanho/recibo; viewer consulta e cross-project nega. Nonzero, sinal, timeout, preparação falha, cancelamento preparação/running e descendentes reais; saída simultânea >32MiB limitada a 16MiB com truncamento. Heartbeat continua em preparação e entrega. Reinício Server conserva sessão; perda de lease termina execução e retry Server limita a dois ordinais. Queda Agent conserva lock herdado, bloqueia novo intake enquanto descendente vive, depois sessão nova e histórico sem autoridade anterior. Respostas perdidas após commit de reserva/logs/artefato/resultado são recuperadas pelo próprio Agent sem repetir script. Probes adicionais demonstram lacunas, payload divergente, UTF-8 inválido, upload incompleto/corrompido/excedido e autenticação separada. Finalização repetida após restart preserva recibo.

Regressões T002/T003 em publication-regression.log e T004 em orchestration-regression-final.log. Rust checks de ambos os workspaces, fmt, Clippy -D warnings, build normal e ausência de identificadores de hooks em rust-checks.json e logs. Rebuild test-failpoints precede ensaios, evitando sobrescrita silenciosa por cargo test/build. Checks governança/SDD em checks.json. Gate agregado deve falhar para AC-01/06/07.

## Falhas observadas e corrigidas

O container estava parado no primeiro ensaio; foi iniciado antes da execução funcional. Um ensaio de regressão foi disparado simultaneamente por engano contra a porta compartilhada e falhou por encontrar o Server do outro banco; foi reexecutado isoladamente, sem aceitar o resultado concorrente. http-postgres-expanded.log preserva falha real: conexão sem headers era classificada como envelope inválido. Corrigido para falha transitória com replay idempotente; ensaio final reexecutado.

## Limites e janelas de falha

Somente jobs cooperantes locais confiáveis sem credenciais externas; sem isolamento, execução remota, DAG, plugins, broker ou SQLite. Queda abrupta não garante matar todo descendente: lock herdado bloqueia reinício na mesma raiz enquanto processos cooperantes vivem. Operador não deve contornar o lock. Grupos que escapem, escritores hostis no mesmo UID, falha de energia/filesystem, failover PostgreSQL e esgotamento real de disco não foram demonstrados. Falha local de persistência interrompe sem declarar completude.

Spool limita bytes e comunica truncamento; entrega é posterior ao processo, com heartbeat independente. Arquivo ausente/ilegível/alterado/excedido/inválido é erro explícito, não artefato publicado; coleta pressupõe escritores terminados. Leitura interrompida e alteração concorrente são verificadas por erros/metadados, mas não há garantia contra alteração adversarial que restaure metadados. Consultas/downloads em hex, sem frontend. Armazenamento Server sem retenção automática; raiz Agent aceita até 16 diretórios antes de exigir manutenção e tamanho do Git admitido cabe ao operador. Processos podem produzir efeitos antes de queda e o retry de lease repetir efeitos: não exactly-once.

T001/AC-01 segue pendente por referências de segredos. T006/AC-06 e T007/AC-07 não concluídos por inferência; contribuição parcial a AC-07 agora inclui Agent/saída reais. Não houve commit, tag ou publicação.

Testes locais adicionais em Rust usam arquivos reais: bytes UTF-8 inválidos, ausência, traversal, symlink final/intermediário, diretório e arquivo de 16MiB+1 rejeitados; conflito real no arquivo temporário força falha de spool sem substituir chunk confirmado. Não substituem o ensaio de integração. A revisão de dependências removeu projects → access (herdada de T002 só para hash), usando sha2 já adotado; grafo atual conferido e regressões reexecutadas. A regressão também detectou ampliação acidental do limite de publicação ao admitir chunks hex; corrigida mantendo o novo limite somente nas duas rotas de chunks.

Checks finais: governança padrão/require-installed, preflight, 34 unitários, SDD padrão e freshness com --features sdd/features passaram. Aceite agregado com --features sdd/features --require-approved --require-fresh falhou somente para AC-01/06/07 UNVERIFIED, conforme esperado. A invocação sem --features não é usada como aceite. Identidade dos binários de teste/imagem, porta e consulta final sem bancos temporários restantes em environment.json.
