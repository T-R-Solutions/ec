# Pesquisa para uma plataforma CI/CD independente em Rust

Execução de `rea/prompt_v2.md` em 2026-10-30. Entrega de pesquisa, especificação funcional,
arquitetura recomendada e plano incremental; inclui protótipo executável de contratos de lease.
Não há servidor/agente de produção implementados nem validação dinâmica de TeamCity.

Ordem de leitura:

1. [Auditoria de reaproveitamento](01-teamcity-research/evidence/reuse-audit.md) e [funcionalidades](01-teamcity-research/features/catalog.md).
2. [Requisitos independentes](02-independent-specification/requirements/catalog.md), [fluxo mínimo](02-independent-specification/workflows/minimum-ci.md) e [invariantes](02-independent-specification/invariants/invariants.md).
3. [Arquitetura](03-rust-architecture/components/architecture.md), [tecnologias](03-rust-architecture/components/technology-evaluation.md) e ADRs em `03-rust-architecture/decisions/`.
4. [Scheduler](03-rust-architecture/scheduling/scheduler.md), [persistência](03-rust-architecture/storage/consistency.md), [protocolo](03-rust-architecture/protocols/agent-v1.md) e [ameaças](03-rust-architecture/security/threat-model.md).
5. [Matriz de decisões](decision-matrix.md), [aceitação](02-independent-specification/acceptance-criteria/scenarios.md), [plano](implementation-plan.md) e [validação](04-validation/findings/validation.json).

Decisão recomendada: control plane em monólito modular Rust, PostgreSQL como autoridade de estado/fila,
agentes separados com protocolo HTTP versionado e isolamento por política. Reserva transacional com lease/fence;
entrega pelo menos uma vez, sem promessa de efeitos externos exatamente uma vez.

Reprodução a partir da raiz do repositório:

```bash
python3 rea/build_research_v2.py
python3 rea/ci-platform-research/04-validation/tests/validate.py
```

O primeiro comando regenera documentos desta entrega e revalida hashes selecionados; preserva o corpus legado.
O segundo compila/testa o modelo Rust e verifica rastreabilidade. Consulte os limites do teste antes de interpretar aprovação.
Fontes oficiais estão no [registro de evidências](01-teamcity-research/evidence/sources.md).
