# Evidence Register — Ec Environments and Approvals

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

**Investigated:** 2026-10-31. **Scope:** focused relevant sections of official documentation and primary scientific publications, not every page of each publisher's complete documentation corpus. Search results were checked against original publication or vendor pages wherever accessible. No forums, social posts or unverified opinions were used.

**Evidence classes:** `official-section` = relevant text directly examined on official site; `peer-full-section` = relevant original journal/conference PDF sections opened; `abstract-only` = official abstract or publisher catalog only (not falsely claimed full text); `book-chapter-section` = freely available primary technical book chapter. Every source below is a reference, not evidence that Ec implements the described feature.

## Official standards, implementations and vendor documentation

| ID | Original publisher and precise URL | Access | What was actually supported / limits |
|---|---|---|---|
| O01 | GitHub, [Managing environments for deployment](https://docs.github.com/en/actions/how-tos/deploy/configure-and-manage-deployments/manage-environments) | official-section | Environment protection, reviewers, optional self-review prohibition and plan-dependent availability. No Ec equivalence asserted. |
| O02 | GitHub, [Reviewing deployments](https://docs.github.com/en/actions/how-tos/deploy/configure-and-manage-deployments/review-deployments) | official-section | Review outcomes, access to secrets after permitted start, and conditional bypass. |
| O03 | GitHub, [Deployments and environments](https://docs.github.com/en/actions/reference/workflows-and-actions/deployments-and-environments) | official-section | Gate rules, reviewer grouping, wait timers and environment-scoped secrets. |
| O04 | GitLab, [Deployment approvals](https://docs.gitlab.com/ci/environments/deployment_approvals/) | official-section | Protected environment approvals, one human vote per deployment even across groups; approval does not by itself start the job. |
| O05 | GitLab, [Protected environments](https://docs.gitlab.com/ci/environments/protected_environments/) | official-section | Allowed-to-deploy and configuration privileges, group inheritance, project/tier scopes, version/tier caveats. |
| O06 | GitLab, [Deployment safety](https://docs.gitlab.com/ci/environments/deployment_safety/) | official-section | Deployment serialization, avoid out-of-order older deployments, freeze windows and protected variables. |
| O07 | GitLab, [Deployments](https://docs.gitlab.com/ci/environments/deployments/) | official-section | Rollback as a new deployment with a new job ID and older revision; target-specific job mechanics necessary. |
| O08 | Microsoft Azure Pipelines, [Define approvals and checks](https://learn.microsoft.com/en-us/azure/devops/pipelines/process/approvals?view=azure-devops) | official-section | Resource-owner checks, independent approval categories, timeout and locks. Azure stores its checks outside pipeline YAML: **this differs from Ec's single-YAML-configuration rule and must not be copied.** |
| O09 | Argo CD, [Sync Windows](https://argo-cd.readthedocs.io/en/stable/user-guide/sync_windows/) | official-section | Allow/deny windows, deny precedence and optional running sync-overrun; Argo examples are not Ec defaults. |
| O10 | Argo CD, [Resource Health](https://argo-cd.readthedocs.io/en/stable/operator-manual/health/) | official-section | Resource health is separate from desired synchronization status; actual health depends on target-specific resource status. |
| O11 | Kubernetes, [Deployments](https://kubernetes.io/docs/concepts/workloads/controllers/deployment/) | official-section | Progressing/complete/failed-to-progress conditions; exceeding deadline marks a condition, **not an automatic rollback**. |
| O12 | Kubernetes, [kubectl rollout status](https://kubernetes.io/docs/reference/kubectl/generated/kubectl_rollout/kubectl_rollout_status/) | official-section | `--revision` guards against tracking a different rollout; unpinned watchers follow latest revision. |
| O13 | NIST, [SP 800-53 Rev. 5 (with updates)](https://csrc.nist.gov/Pubs/sp/800/53/r5/upd1/Final) | official-section | AC-5 separation of duties; CM-3 change approval/control; CM-4 impact analysis; AU-family audit practices. A control catalog is not a prescriptive Ec RBAC model. |
| O14 | NIST, [SP 800-218 SSDF](https://csrc.nist.gov/pubs/sp/800/218/final) | official-section | Security practices and release integrity as process recommendations, not feature proof. |
| O15 | OWASP, [CI/CD Security Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/CI_CD_Security_Cheat_Sheet.html) | official-section | Review before production, least privilege, isolation, secrets protection and monitoring. |
| O16 | PostgreSQL, [Transaction Isolation](https://www.postgresql.org/docs/current/transaction-iso.html) | official-section | Read Committed versus Serializable consistency, need to retry serialization failures. No cross-Git/target atomicity. |
| O17 | PostgreSQL, [INSERT and ON CONFLICT](https://www.postgresql.org/docs/current/sql-insert.html) | official-section | Conflict handling and `RETURNING`; a suitable deduplication technique only under a designed uniqueness key. |
| O18 | SLSA, [Specification v1.2](https://slsa.dev/spec/v1.2/) | official-section | **Current approved** version in October 2026; v1.1 is retired. Security levels and attestation concepts. |
| O19 | SLSA, [Build: Verifying artifacts v1.2](https://slsa.dev/spec/v1.2/verifying-artifacts) | official-section | Match artifact digest, provenance subject, trusted builder identity, signatures and expectations; signatures alone do not imply authorization. |
| O20 | W3C, [WCAG 2.2 Focus Visible](https://www.w3.org/WAI/WCAG22/Understanding/focus-visible) | official-section | Keyboard focus must be visible. |
| O21 | W3C, [WCAG 2.2 Status Messages](https://www.w3.org/WAI/WCAG22/Understanding/status-messages.html) | official-section | Dynamic operation status must be programmatically available without forcing focus changes. |
| O22 | W3C, [WAI-ARIA Alert Dialog Pattern](https://www.w3.org/WAI/ARIA/apg/patterns/alertdialog/) | official-section | Accessible high-stakes confirmation dialogs when an interruptive alert dialog is appropriate. |
| O23 | Vue, [TypeScript with Composition API](https://vuejs.org/guide/typescript/composition-api) | official-section | Type-safe props/events/state for safe view projections; not evidence of UI accessibility. |
| O24 | GitLab, [Environment management](https://docs.gitlab.com/ci/environments/) | official-section | Environment details, roles and observations for deployment history; vendor behavior only. |
| O25 | Google, [Site Reliability Engineering: Reliable Product Launches at Scale](https://sre.google/sre-book/reliable-product-launches/) | book-chapter-section | Checklists, risk assessment, gradual rollout and contingency preparations; editorial engineering book, not peer-reviewed controlled trial. |

## Peer-reviewed scientific literature

| ID | Publication | Access | Insight and transfer limit |
|---|---|---|---|
| P01 | Sandhu, Coyne, Feinstein, Youman, [Role-Based Access Control Models](https://csrc.nist.gov/csrc/media/projects/role-based-access-control/documents/sandhu96.pdf), *IEEE Computer* 29(2), 1996 | peer-full-section (model, constraints, separation of duty) | RBAC separates actor-role-permission relationships; separation of duties is configurable, not automatic. The paper does **not** select an Ec approval quorum. |
| P02 | Pang et al., [Zanzibar: Google's Consistent, Global Authorization System](https://www.usenix.org/system/files/atc19-pang.pdf), *USENIX ATC*, 2019 | peer-full-section (causal order, consistency tokens, optimistic concurrency) | Authorizing based on stale rights is unsafe when objects/policy changed; Zanzibar architecture and numerical claims are not Ec requirements. |
| P03 | Torres-Arias et al., [in-toto: Providing farm-to-table guarantees for bits and bytes](https://www.usenix.org/system/files/sec19-torres-arias.pdf), *USENIX Security*, 2019 | peer-full-section (layout, step thresholds, artifact flows and metadata) | Verify integrity of released artifacts across build steps; threshold metadata is not a substitute for human deployment approval. |
| P04 | Garcia-Molina and Salem, [Sagas](https://www.sigmod.org/publications/sigmodRecord/8706/), *ACM SIGMOD*, 1987 | abstract/official index only; full PDF link failed access | Compensation for long-lived, partly completed distributed operations; not evidence that arbitrary external deployment effects are reversible. |
| P05 | Clark and Wilson, [A Comparison of Commercial and Military Computer Security Policies](https://ieeexplore.ieee.org/abstract/document/6234890), *IEEE Symposium on Security and Privacy*, 1987 | abstract-only (publisher blocked full text) | Integrity controls and organizational separation; details beyond abstract must not be attributed to a full read. |
| P06 | Bertino, Ferrari and Atluri, [The Specification and Enforcement of Authorization Constraints in Workflow Management Systems](https://doi.org/10.1145/300830.300837), *ACM TISSEC* 2(1), 1999 | bibliographic record and abstract-level metadata; full ACM text unavailable | Independent authorization constraints are a specialized workflow problem; no unverified algorithm attributed to this source. |

## Research synthesis and conflict handling

- **No single vendor supplies the Ec contract.** GitHub can enforce one-of-many reviewers; GitLab supports multiple approvals and explicitly separates approval from job start; Azure may store checks outside pipeline YAML. The Ec normative documents require YAML as the *single* desired declarative configuration. Therefore inherit conceptual gates, **not** vendor wire schemas, defaults, approval counts or configuration locations.
- **Approved does not imply dispatched or healthy** (O02–O04, O07, O10–O12). A trustworthy UI must name which authority observed each state.
- **Release evidence is immutable and policy/version-bound** (P02–P03, O18–O19). Design recommendation: recheck approval against exact digest, target, revision and current authorization at the point of effect; the detailed approval carry-over policy remains SDD-owned.
- **Concurrency needs explicit policy** (O06, O08, O16–O17); no UI lock, optimistic client field, or user-visible timer can replace server-side exclusion and reconciliation.
- **Policy choices are not scientific facts.** Distinct-approver thresholds, no-self-review, freeze precedence and emergency bypass depend on product decisions; security literature motivates guarding them but does not impose one universal implementation.

## Normative mapping (files read in full)

- `EC-REGRAS-DE-DESIGN-E-PRODUTO.md`: §§2, 11, 14–16, 21–23, 26–28, 33–35; canonical YAML desired state, safe approval, environment/releases and real feedback.
- `EC-REGRAS-CRIACAO-DE-TELAS-E-IMAGENS.md`: §§1–4, 8, 13–15, 17–21; explicit scope, approval/delivery UI, secret redaction, coherent states and keyboard alternatives.
- Scope-conformance finding: **no identified contradiction** in the proposed method. The O08 Azure model (checks not in YAML) is explicitly non-transferable to Ec's canonical desired configuration.

## Research integrity and limitations

Original publisher pages and three complete-paper PDFs were directly examined for relevant sections; two papers were reviewed at abstract level and one at bibliographic/abstract level. Search snippets were supplementary discovery, not evidence of full-text reading. No forums were used. No copyrighted article is reproduced in full. Real Ec source, implemented schema, human studies, Rust/PostgreSQL/target/browser integration and language-model skill-selection experiments are absent; no claims are made for them.
