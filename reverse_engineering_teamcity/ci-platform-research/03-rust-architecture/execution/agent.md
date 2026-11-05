# Agente e formato de pipeline

Supervisor: registrar → autenticar → anunciar capacidades → poll → reservar recursos locais → start ack → preparar
workspace → checkout revisão fixa → executar → drenar logs → subir outputs → complete → limpar.
Manifesto de workspaces local permite detectar órfãos no restart; nunca confiar em arquivo de PID sem identidade da execução.
Use processo/grupo/cgroup identificado, evitando matar PID reutilizado. Diretórios por AttemptId fora do diretório do agente.
Limites: deadline por step/job; CPU/memória/pids/disco do pool; rede conforme confiança.
stdout/stderr são drenados paralelamente; kill inclui descendentes; wait recolhe processo e valida cleanup.
Segredos só na etapa que precisa; arquivos temporários restritos e removidos. Não prometer redaction contra código malicioso.

Configuração independente, exemplo de contrato (não é parser implementado):

```json
{"schema_version":1,"source":{"url":"https://example.invalid/repo.git","revision":"FULL_COMMIT_ID"},
 "jobs":[{"id":"test","needs":[],"requirements":{"os":"linux","arch":"x86_64"},
 "timeout_seconds":600,"retry_safe":false,"steps":[{"argv":["cargo","test","--locked"],"cwd":"."}],
 "artifacts":[{"path":"reports/result.txt","required":true}]}]}
```

JSON é transporte inicial e IR tipado é separado. Validar limites antes de construir DAG;
revisão deve resolver identificador completo validado no checkout. CORE um job; IMPORTANT DAG estático.
Paralelismo existe entre jobs; steps sequenciais. Condições iniciais apenas all_success/always no domínio validado;
expressões arbitrárias rejeitadas. Matriz expande antes do scheduling com teto de jobs; adiada.
YAML tem familiaridade mas mais regras de parse; TOML é legível porém verboso em DAGs;
DSL Rust exige executar compilador fora do controle; UI pode gerar JSON, sem mudar semântica.
Artefatos intermediários são dependências explícitas por manifesto; cache e matriz não bloqueiam primeiro fluxo.
