# Reparação da automação — 12/11/2026

O run final `run-3BXITHOD` aprovou **54/54 tarefas**, com exit final **0**,
entradas e lockfiles estáveis e limpeza concluída. Inclui os complementares
Grype, Bandit, Syft e YARA-X e as jornadas Playwright, Chromium e Firefox.
[Resultados, identidades e hashes observados](run-3BXITHOD.json).

## Diagnóstico e correções

| Problema observado | Causa comprovada | Correção e evidência |
| --- | --- | --- |
| CodeQL aprovado no run original com extração Rust falha | Server: 11 arquivos com erro e zero saudáveis; Agent: nove com erro e zero saudáveis. Severidade omitida escapava da avaliação; presença em artifacts não demonstrava extração. | Validador SARIF único resolve defaults e overrides por ID/índice, rejeita diagnósticos degradados e exige extração saudável por fonte e métricas suficientes. Regressões rejeitam relatórios aparentemente completos e extração insuficiente. |
| Workspaces Rust não carregados corretamente pelo extrator | O runtime do extrator acionava proxies Rustup com 1.99.0 ausente. Depois, stderr completo revelou linker cc ausente e falhas de build scripts apesar das métricas saudáveis. O lançador ainda precisava preservar os FDs do jobserver. | Binários da instalação selecionada são usados diretamente, sem pin de versão; stdout, stderr original, status e descritores do jobserver são preservados. Compilações críticas com erro bloqueiam antes da análise. A receita atual da Toolbox recebeu build-essential e foi qualificada antes da ativação. |
| Geiger emitia 593 mensagens sem associação no run original | Busca por faixa de versão de declarações opcionais/inativas podia escolher versões ausentes do grafo. Metadata multiplataforma e features fracas não representam, por si, dependência ativa. | Plugin 0.14.0+ec.2 usa identidades de resolve.nodes.deps e filtra target/ativação antes da associação. Perda de nó ativo reprova; casos inativos legítimos são cobertos por regressões. Os seis membros Server e cinco Agent foram aprovados sem mensagens de associação perdida ou dependência não examinada. |
| Bandit reprovava com 373 achados sem triagem formal | Assertivas dos harnesses, subprocessos locais e outras operações precisavam de decisões vinculadas ao código. | Inventário bruto integral preservado; configuração canônica contém SHA-256, regra, linhas e justificativa de cada decisão. O código novo levou o total a 375: 375 revisados e zero pendentes. Ocorrência nova, movida, sem justificativa ou em fonte alterada reprova. |
| Syft inferia nome/versão pelo diretório | A origem não tinha identidade explícita. | Nome Ec e versão derivada dos hashes das entradas. O aviso não reapareceu. |

O tratamento Bandit não usa baseline nativa, skips globais nem exclusão de testes.
O scan mantém todas as severidades e ignora comentários nosec. A aprovação indica
zero pendências após revisão, preservando os 375 achados nativos no relatório.

No Geiger, as mensagens `Build data (include_str!/include_bytes!, not Rust)`
identificam dados incorporados cujo consumidor foi comprovado pela AST, como
README e SQL. `Scanned build source` informa Rust gerado que foi analisado e
incluído nos contadores com proprietário definido. Dependência opcional inativa
ou de outro target pode estar em metadata sem pertencer ao grafo ativo;
serde?/std não ativa serde. Esses casos são testados. Perdas de associações
ativas e fontes Rust não examinadas continuam bloqueando. Contadores de unsafe
não representam, isoladamente, uma vulnerabilidade demonstrada.

## Avisos e limites mantidos

- Cargo Deny: duplicidades de block-buffer, cpufeatures, crypto-common, digest,
  sha2 e wasi continuam sob multiple-versions = warn; não houve atualização
  incompatível para silenciar o aviso.
- Licenças: a política compartilhada preserva exceções necessárias ao workspace
  companheiro. Ausência de uso no Agent não autoriza apagar uma exceção do Server.
- Fixtures positivas Gitleaks e Trivy: códigos não zero são esperados; os critérios
  de eficácia confirmaram detecção. Não foram convertidos em falhas adicionais.
- YARA-X: aprovação restrita à sentinela EICAR; não constitui análise geral de malware.
- Os scanners cobrem as regras, bases e fontes observadas. Critérios históricos de
  outras tarefas não foram promovidos automaticamente.

## Validação e integridade

| Verificação | Resultado observado |
| --- | --- |
| Escopo completo, com complementares | 54 passed; zero findings, error ou blocked no resultado agregado |
| Testes de scripts | 159 aprovados |
| Biblioteca Geiger | 192 aprovados |
| Toolbox compartilhada | 85 aprovados; validate e doctor saudáveis |
| CodeQL | Server 11/11 e Agent 9/9 com extração saudável; frontend sem achados; compilações críticas Rust sem falhas |
| Bandit | 375 achados nativos, 375 revisados, zero pendentes |
| Integração e navegadores | publication_http, orchestration_http, agent_http, Playwright, Chromium e Firefox aprovados |
| Entradas e limpeza | Sem drift, sem lockfiles alterados, sem cancelamento; artefatos intermediários descartados |

A imagem CodeQL ativa é
`sha256:0ee3284ac49c0a2ab676e90c6e9f7ead5985d021d1ee3c47fd9b6f80ae1bc494`. O bundle 2.27.3 foi reutilizado após
conferência contra checksum oficial. Os downloads Debian e a correção externa
foram autorizados explicitamente. A qualificação incluiu Cargo check offline
real de Server e Agent na imagem candidata, seguido de criação/análise CodeQL
com recibos internos aprovados.

As iterações intermediárias foram preservadas: CP24bjsM, ozM6Y3nN, ywYuxSGi e
vs4eAX3o. Falhas Geiger anteriores e rejeições por alteração das entradas durante
a execução não foram apagadas nem reclassificadas. O run original IR9jPgS9 teve
53 passed e um findings (Bandit), mas seu CodeQL estava aprovado indevidamente.

A configuração de automação permanece em scripts/config/automation.json; a
triagem não foi espalhada por configurações paralelas. A plataforma externa
mantém toolbox.json e a receita vigente tools/codeql/Dockerfile. Revisões
operacionais anteriores são histórico, não configurações concorrentes.

Logs brutos privados: .local/logs/2026-11-12/run-3BXITHOD-*, sujeitos à retenção de duas
datas. O JSON deste relatório preserva as identidades e os resultados necessários
após a expiração desses logs. O fechamento documental foi auditado separadamente no run `run-3ZfLm5vp`: cinco
checks aprovados, exit zero, entradas estáveis e limpeza concluída. Este registro
posterior descreve o resultado observado, sem alterar a implementação testada.
[Resultado da auditoria documental](closure.json). Nenhum commit ou publicação foi feito.
