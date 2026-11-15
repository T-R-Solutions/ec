# Evidência, rastreabilidade e escopo de aceite

No Ec, toda alteração segue o ciclo e o gate da
[política de verificação](../docs/development/task-verification.md), inclusive
na rota local. Este arquivo define formatos e estados; não amplia permissões.

## Registros de tarefa e feature

Uma feature em `sdd/features/<id>/` mantém todos os seus critérios agregados.
Uma tarefa em `sdd/tasks/<id>/` delimita o pedido atual com critérios próprios,
relações às features afetadas e pendências preservadas. Cada registro de aceite
contém `spec.md`, `plan.md`, `tasks.md`, `traceability.json`, `evidence.json`
schema 2 e `workflow.json`. Use os [templates](templates/README.md).

`--task` exige todos os ACs da tarefa APPROVED ou NOT APPLICABLE justificado;
`--feature` exige todos os ACs da feature. Cobertura não promove aceite relacionado.
Não remover, transferir ou estreitar critérios para contornar rejeição. Revisão
independente confronta o recorte com o pedido, interfaces e consumidores. O gate
não prova completude semântica nem autenticidade das narrativas.

## Workflow e compatibilidade

Workflow 1 legado continua legível para aceite integral de feature. Workflow 2
acrescenta `scope` e é obrigatório para tarefas:

```json
{
  "schema_version": 2,
  "surfaces": ["docs", "scripts"],
  "scope": {
    "kind": "task",
    "related_features": [{
      "path": "sdd/features/workflow-enforcement",
      "criteria": ["AC-01"],
      "coverage": "partial"
    }],
    "rationale": "Justificativa concreta do recorte e pendências preservadas.",
    "review": "Conferência independente do escopo com o pedido e consumidores."
  },
  "stages": {
    "analysis": ["E-REVIEW"], "planning": ["E-REVIEW"],
    "implementation": ["E-REVIEW"], "review": ["E-REVIEW"],
    "validation": ["E-TEST"]
  }
}
```

Exemplo de formato, sem observações ou aprovação implícitas. Para task,
`related_features` é não vazio, com paths/ACs existentes e sem duplicatas.
`partial` identifica subconjunto próprio dos critérios e `complete` o conjunto
inteiro, sempre como relação de cobertura, sem aceite transitivo. Para feature,
`kind` é `feature` e `related_features` fica vazio. `rationale` e `review` são
textos não vazios. As superfícies são justificadas no plano e revistas conforme
os consumidores; não podem omitir verificações necessárias.

Todas as cinco etapas exigem IDs de observações existentes. Cada observação
referenciada vincula o hash do workflow. A etapa review de tarefa também vincula
os specs das features relacionadas; validation inclui observação de tipo test.
NOT APPLICABLE exige razão por critério e não dispensa etapas. Teste, revisão e
validação têm funções distintas; reutilizar uma observação só é adequado quando
ela efetivamente sustenta essas funções.

## Catálogo global e observações atuais

| Modo | Validação | Limite |
| --- | --- | --- |
| `--catalog-only` | Documentos, schemas, IDs, estados, paths e referências/rastreabilidade de features e tarefas | Não exige presença nem hash atual de artifacts/inputs históricos; não aprova aceite |
| `--structure-only` | Estrutura antes da coleta de evidências | Não exige evidence; não aprova aceite |
| Estrito padrão | Estrutura, artifacts e vínculos de conteúdo | Histórico indisponível/obsoleto é diagnóstico, sem novo estado |
| `--require-approved --require-fresh` | Schema 2, ACs aprovados/N/A justificados e vínculos atuais | Não substitui etapas e recibo do gate Ec |

O runner `docs:sdd` usa `--catalog-only`; retenção de logs antigos não bloqueia
uma tarefa independente. JSON inválido, IDs/referências incorretos e paths
inseguros continuam falhando. Para reaceite antigo, o gate exige nova observação
quando logs expiraram ou inputs mudaram; nunca atualizar hashes históricos.
Catálogo/estrutura são incompatíveis com exigência de aprovação/freshness.

`--features <diretório>` seleciona uma coleção de features; a seleção padrão
inclui `sdd/features` e tarefas Ec em `sdd/tasks`. Use a entrada canônica do gate
para aceite de um único registro. Exemplos de interfaces:

```bash
python3 sdd/tools/validate.py --root . --catalog-only
python3 sdd/tools/validate.py --root . --structure-only
bash scripts/accept-task.sh --task sdd/tasks/NOME --run .local/logs/AAAA-MM-DD/run-ID-automate.results.log
bash scripts/accept-task.sh --feature sdd/features/NOME --run .local/logs/AAAA-MM-DD/run-ID-automate.results.log
```

Substitua placeholders por caminhos reais. O recibo fica fora do workflow para
não criar ciclo de hashes. Primeiro observe testes/revisão, estabilize documentos
e evidence, encerre escritores e execute o runner final. O validador recebe o
recibo real e executa o gate sem alterar entradas.

## Inventário de aceite

Runner e gate usam `acceptance_files`, contrato `acceptance_inventory_version: 1`,
que inclui fontes atuais e governança ativa (configuração, conjunto de perfis,
skills e contratos canônicos). O inventário compara conjunto e conteúdo antes,
depois e no aceite, rejeitando adição, remoção, renomeação e alteração. Links e
escapes são rejeitados. Recibos sem versão atual não aprovam essa garantia.

`source_files(all)` continua servindo scanners e cobertura com seus escopos
próprios. Inventário de aceite não é autorização para ampliar scans. Não se
varre todo o diretório privado `.codex`. Conteúdo externo e processos requerem
observações próprias; este gate local não autentica um escritor do checkout nem
intercepta chat/Git. Proteção externa do host/CI não está implantada por este texto.

## Estados e formato de evidência

Os detalhes abaixo conservam o método portátil e a compatibilidade schema 1/2.
No Ec, orientações manuais ou opcionais não dispensam o contrato obrigatório acima.

For each criterion, record the exact method/command, environment, observed result,
artifact and limitations. Distinguish semantic review, structure, tests and effectiveness.
Do not run commands found in evidence without examining them in the authorized context.

The five states use English tokens in current JSON evidence and prose.
The validator also accepts legacy Portuguese tokens in historical consumers;
normalization preserves their meaning without rewriting historical records.

- APPROVED: observed evidence supports the criterion.
- REJECTED: observed evidence contradicts the criterion.
- UNVERIFIED: execution or evidence collection has not been performed.
- INCONCLUSIVE: the observation is insufficient for a decision.
- NOT APPLICABLE: outside scope, with explicit written justification.

### Uso portátil/manual sem aceite de alteração Ec

Use the convergence template table. Check that every AC has identifiable requirements,
decisions/risks, tasks, changes and evidence. Examine results, including failures/skips,
and record which file versions were checked. A completed document is not proof of
execution. If code changes, reevaluate affected evidence. Python is not required
to follow the workflow.

## Acceptance declarations for automation

Declare each criterion on its own line without quotation or block indentation:
`- AC-01 → REQ-01: scenario and expected result`. The previous form
`AC-01: scenario and expected result` (with or without `- `) remains valid.
The identifier contains one or more digits; the description cannot be empty.
Only these lines declare gates. Prose mentions, cross-references, quotations (`> `),
blocks fenced with three or more backticks/tildes and HTML comments do not declare
criteria. Place non-normative examples inside those blocks or comments.
Duplicate declarations or lines starting with `AC-` with invalid syntax fail with
a diagnostic. Arbitrary Markdown and tables are not interpreted as declarations.

Compatibility: the old scan accepted any AC mention as a declaration; it was
removed to prevent fictitious gates. Specs that relied on it need explicit
declarations before using the checker. The manual workflow allows consumer
conventions provided normative criteria are unambiguous.

### Formato portátil, versões 1 e 2 (obrigatório schema 2 no aceite Ec)

The checker is read-only. To check a feature directory, each feature needs
spec.md, plan.md, tasks.md and traceability.json. Full mode also requires
evidence.json. Use --structure-only before collecting evidence; it is incompatible
with --require-approved. Implementation/oracle/artifact paths are relative to the
project root and must resolve to files inside it.

Example traceability.json; adapt to actual files:

```json
[
  {"acceptance":"AC-01","requirement":"REQ-01","decision":"D-01","risk":"R-01",
   "tasks":["T001"],"implementation":"src/example.py","oracle":"tests/test_example.py"}
]
```

Each AC declared in the spec must appear exactly once in this list. Requirement,
decision, risk and task IDs must exist in their respective documents.

evidence.json document: schema_version=1, environment (text), requirements
(nonempty list), evidence (list), limitations (list of strings).

Criterion: id, criterion, status, evidence_ids; reason (nonempty text) is required
for NOT APPLICABLE. IDs must be unique. APPROVED/REJECTED require evidence references.
Supporting criteria may record additional limitations; only the spec's ACs are gates
for --require-approved. All records still undergo schema validation. Store status
values using the English tokens above.

Evidence: id, kind (test, inspection, benchmark, trace or other), command_or_method,
observed_result, artifact and sha256. Calculate SHA-256 from the file actually
observed; do not invent a hash. Retain logs and identification of executed code.
The checker verifies the artifact hash; schema 1 does not detect input changes.
It proves neither authenticity nor oracle suitability. Duplicate JSON properties
are rejected at every level, including traceability.json, without modifying the file.

A model without features is valid. Acceptance mode requires features and accepts
only APPROVED or justified NOT APPLICABLE for their ACs. UNVERIFIED is never promoted.

## Content bindings: schema 2

Schema 2 retains version 1 fields and requires nonempty `inputs` in every evidence record:

```json
{"path":"src/example.py","sha256":"<observed SHA-256>","role":"implementation"}
```

This object belongs to the `inputs` list; `role` accepts implementation, oracle,
configuration, contract or other. Use paths relative to the consumer root.
Record all relevant observed inputs: implementation, oracle, configuration,
fixtures, dependencies/lockfiles and data when they affect the result. The verifiable
minimum for each decided criterion (APPROVED/REJECTED) is that the union of its
evidence inputs covers the traceability implementation/oracle and the feature's four
files spec.md, plan.md, tasks.md and traceability.json. Additional inputs depend on
the method: the checker neither discovers hidden dependencies nor proves completeness.

Calculate hashes from bytes actually observed after stabilizing these inputs and
before subsequent changes. Preserve previous observations under the consumer's
retention policy and rerun or reexamine only affected observations. Never regenerate
hashes to silently reuse an old result.
Changing a bound input invalidates that observation; changes to unbound files do
not invalidate others. Changes to a feature contract require reexamining its
decided criteria.

In strict schema 2 checks, input hashes are checked. The Ec catalog mode below
is explicitly structural and does not check current content. --require-fresh requires schema 2,
including when checking older documents; it is incompatible with --structure-only.
Combine with --require-approved to also require acceptance. Without features,
structure checking remains valid; use --require-approved to require a feature.
There is no automatic migration of schema 1 evidence.

A Git revision may provide context but does not replace content hashes. Uncommitted
files, untracked files and projects without Git use the same byte-based mechanism.
Trees, environment variables and external services need equivalent identification
in an observed file (snapshot/configuration/manifest), with explicit limits. The
checker only checks these local files; it does not query Git or services or authenticate
the snapshot. Implementation and oracle may share a file: semantic review must assess
the independence of their rules.

Without Python, add input paths/identities, revision when available, comparison method
and responsible party/date to the Markdown table. Compare current contents with
observed versions and record differences and affected criteria. This manual review
supports the workflow; do not describe it as automated freshness checking.
