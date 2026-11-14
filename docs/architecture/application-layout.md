# Organização da aplicação

O [estado do projeto](../../sdd/project.md) é a entrada para implementação e
pendências. Este mapa mantém somente localização e responsabilidade. Decisões:
[ADR-002](../decisions/ADR-002-application-layout.md) e
[ADR-003](../decisions/ADR-003-public-journey-contract.md).

## Mapa físico

```text
application/
  contracts/                    # contrato público v1, sem estado
    pipeline-v1.json             # fonte de campos, identificadores e limites
    build.rs                     # geração Rust apenas em OUT_DIR
    src/lib.rs                   # nomes, request IDs, hash e hexadecimal
  agents/
    Cargo.toml                   # workspace e versões de dependências
    crates/ec-agent-{control,workspace,execution,reporting,app}/
  server/
    backend/
      Cargo.toml                 # workspace, dependências e membro contracts
      migrations/                # evolução persistente versionada
      crates/ec-server-{access,configuration,projects,orchestration,results,app}/
      crates/ec-server-{delivery,extensions,operations}/  # estrutura futura
    frontend/
      package.json
      src/app/                   # shell, contexto e composição
      src/domains/               # nove áreas, com entradas src/index.ts
      src/shared/                # API e apresentação compartilhada
  integration/tests/
    integration/                 # HTTP/PostgreSQL/Agent reais
    e2e/                         # jornadas reais de navegador
```

Os dois workspaces e locks permanecem independentes. O pacote público contracts
é membro do workspace Server para testar/formatar com o fluxo existente; o Agent
consome somente esse pacote por path. Não há import de domínio privado entre os
executáveis. Frontend importa o mesmo descritor de protocolo; não mantém outra
lista canônica de campos. JSON de protocolo não substitui YAML de configuração Ec.

## Responsabilidade e encapsulamento

| Local | Responsabilidade |
| --- | --- |
| ec-server-access | Sessões e grants por projeto |
| ec-server-configuration | Parsing YAML com limites do contrato |
| ec-server-projects | Schema ProjectPipeline, revisão e publicação idempotente |
| ec-server-orchestration | Fila, sessão Agent, reservas, tentativas e submissão idempotente |
| ec-server-results | Logs e artefatos, vinculados à autoridade de tentativa |
| ec-server-app/src/service | Composição privada: admin, http, projects, agents e protocol |
| ec-agent-control | Transporte e heartbeat |
| ec-agent-workspace | Checkout e coleta de arquivo admitidos |
| ec-agent-execution | Processo, drenagem e término |
| ec-agent-reporting | Spool, persistência e entrega |
| ec-agent-app | Bootstrap, sessão e coordenação da tentativa |

Replay é decidido no domínio. A composição fornece verificação de sessão após
lock e preparação de referências sob demanda; repetição não revalida referências
externas de uma publicação já confirmada. Transação e autorização não são
fragmentadas por arquivo. APIs de persistência usam Transaction explícita no
incremento atual; isso é acoplamento deliberado ao PostgreSQL, sem alegar portas
abstratas já implementadas. Falhas HTTP v1 compartilham vocabulário público.

No frontend, app importa somente entradas públicas de domínios. Domínios não
importam app; shared não importa domínios. O contexto de projetos e agentes fica
no composable de app; o catálogo de navegação identifica áreas demonstrativas.
Fixtures permanecem isoladas dos clientes operacionais. Diretórios não são
serviços, bancos, isolamento nem evidência de conclusão funcional.

## Prevenção de deriva

Dependências externas dos membros são herdadas do respectivo workspace. Os
checks de arquitetura em scripts/tests/test_application_boundaries.py rejeitam
versões locais, imports privados, dependência de composição e incompatibilidade
entre limites públicos e constraints da migration histórica. Os testes possuem
mutações negativas; análise textual não prova toda a arquitetura.

Constraints SQL históricas não são geradas novamente por mudar um limite: essa
mudança exige revisão de compatibilidade e, quando necessária, nova migration.
Validação independente no Server e no Agent permanece obrigatória. Mudanças
incompatíveis de protocolo precisam tratar binários de versões diferentes.

[Verificação](../development/task-verification.md) ·
[Estado local](../development/local-state.md) ·
[Contratos](contracts.md) · [Testes](../development/testing.md)
