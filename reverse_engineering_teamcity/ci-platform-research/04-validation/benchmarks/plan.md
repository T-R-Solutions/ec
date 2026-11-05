# Benchmark reproduzível proposto, ainda não executado

Antes de medir: registrar CPU/modelo/cores, RAM, kernel, filesystem/disco, rede/RTT,
versões Rust/crates/PostgreSQL, imagem de executor, limites cgroup e commit/lockfile.
Baseline proposto: controle 4 vCPU/8GiB, DB 4 vCPU/8GiB SSD, 4 agentes 2 vCPU/4GiB,
rede 1Gbps. É ambiente alvo, não hardware desta sessão. Registrar máquinas físicas compartilhadas e ruído.

Workloads: 10k jobs no-op; comandos fixos 1s/30s; DAG fanout 10; incompatibilidade de 50%;
logs 1MiB/s por agente durante 60s; artefatos de 1MiB/100MiB; desconexão de 25% dos agentes.
Warmup 60s, janela 10min, cinco repetições e seeds fixas; publicar raw samples e intervalos de confiança.
Carga aumenta 1/4/16/64 polls e 1/4/16 agentes; não comparar configurações com recursos diferentes.

Métricas: builds/s, pedido→commit, elegível→lease, lease→start, p50/p95/p99;
RSS/CPU por componente, bytes/s de logs/artefatos, spool máximo, idade por projeto,
tempo de recuperação após restart e leases vencidas. Separar tempo do comando de overhead de orquestração.
Metas propostas do piloto: p95 enqueue <200ms a 10 req/s; p95 elegível→lease <2s com slot livre;
zero resultado stale aceito; reconciliação <60s com TTL30s; RSS controle <1GiB no workload de 10k jobs.
São critérios para experimento, não garantias já obtidas. Falha de meta exige profile, não troca especulativa de arquitetura.
Comparação TeamCity somente com versão, hardware, jobs, outputs e política de durabilidade equivalentes; não realizada.
