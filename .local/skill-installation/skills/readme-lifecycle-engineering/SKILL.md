---
name: readme-lifecycle-engineering
description: Create, audit, restructure, and keep repository or package READMEs accurate as software evolves. Use for README entry points, runnable onboarding, documentation splitting, navigation, and drift checks; skip API reference writing or general technical-documentation tasks without a README lifecycle issue.
---

## Acionamento de scripts no Ec

Neste checkout, esta regra prevalece sobre recomendações de execução desta skill e
suas referências. Scripts de verificação só podem ser acionados após pedido explícito
de commit. Pergunte exatamente: “Deseja executar o script automate.sh (que roda todos os outros scripts)?”
Ofereça somente `sim` e `não`, sem resposta automática; aguarde a escolha.
Com `não`, prossiga normalmente com os commits solicitados, sem executar scripts.
Com `sim`, execute da raiz `bash scripts/automate.sh run`, sem filtros, e só crie
os commits após todos os scripts da suíte terminarem sem erros. Falha, interrupção,
ferramenta ausente ou execução incompleta bloqueiam os commits nessa opção.
Não execute scripts avulsos nem os acione automaticamente durante implementação,
revisão ou encerramento. Inspeção manual continua permitida; checks não executados
permanecem explicitamente não verificados. Detalhes: `docs/development/task-verification.md`
(caminho relativo à raiz Ec). Em outros projetos, siga a política do consumidor.

# README Lifecycle Engineering — Accurate Entry Points, Sustainable Documentation

## Scope and inputs

Inspect the actual repository tree, root and package READMEs, audience, release/status,
source/installation commands, supported environments, existing documentation and decisions.
Distinguish **observed behavior**, **documented contract**, **planned feature** and **unknown**.
A README introduces and routes readers; it does not replace every manual, API reference or
architecture decision. Preserve the consumer's conventions and authority hierarchy.

## Procedure

1. **Define readers and outcomes.** Identify first-time users, operators, contributors and
   maintainers; determine what each must find or do without repository archaeology. State the
   supported product version or revision and audience-specific limitations.
2. **Audit the current entry path.** Check name/purpose, project maturity, prerequisites,
   minimal verified setup and usage, tests, help/contact, support boundaries, license and links.
   Compare the README's claims with real files/contracts before drafting.
3. **Choose the smallest maintainable layout.** Keep one discoverable root `README.md` for
   project identity, quick start, status and navigation. Add package/subproject READMEs only
   when they have independent entry points; move detailed procedures, references, concepts,
   policies and changelogs to canonical files linked from the README. No universal line-count
   threshold or mandatory multi-file hierarchy.
4. **Write for a reproducible first run.** Provide the working directory, prerequisites,
   precise commands, inputs, expected observations, safe demo values and failure recovery.
   Do not invent runnable commands, supported features, downloads, tests or results.
5. **Maintain alongside change.** Map changes in build, CLI/API, configuration, prerequisites,
   permissions, deployment, status and support to affected README sections and linked docs.
   Avoid duplicate normative instructions; update the canonical source and inbound links
   together. Preserve historical context and versioned links when needed.
6. **Verify at the right evidence level.** Inspect/render Markdown and links; execute selected
   onboarding steps only in an authorized environment; confirm exit status and effects. Review
   failed, missing, denied and unsupported paths. Static checks and successful commands have
   different evidentiary meanings.
7. **Report outcomes and maintenance triggers.** Identify changed files, audience paths,
   checked revision, exact verification results, unresolved gaps, ownership and the next
   source-change trigger. Do not label unexecuted instructions as validated.

## Load detail when needed

- [README scope and audience](references/readme-scope-and-content.md): first-page content,
  readers, maturity/status and minimum first-run information.
- [One README or many?](references/documentation-topology-and-diataxis.md): placement,
  directory READMEs, `docs/`, canonical ownership, separation criteria and navigation.
- [Keep documentation current](references/maintenance-and-change-triggers.md): change impact,
  lifecycle events, deprecation, drift prevention and migration.
- [Validation and reader evidence](references/verification-and-acceptance.md): runnable commands,
  broken links, checks, oracles and honest acceptance statements.
- [Detailed decisions and failure cases](references/implementation-details.md): difficult
  layouts, disagreements, real project constraints and additional thematic references.

## Completion and limits

Deliver an accurate, navigable README change (or bounded diagnosis) and verified checks
that the available environment supports. Separate editorial review, link checks,
command execution, user research and published release evidence. Sources in references
inform methods; they do not prove the current repository works or authorize changes.

The skill is self-contained and works without SDD or other skills. Use an existing
repository workflow; select another specialist only for a specific unresolved domain
question. Creating or editing documentation does not authorize publishing, releases,
network messages, access escalation, credential handling or destructive commands.
