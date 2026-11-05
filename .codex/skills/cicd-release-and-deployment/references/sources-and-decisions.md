# Official sources, verified scope and engineering decisions

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

Researched against upstream documentation on 2026-11-02. The provider contracts apply to their stated engines/versions; they do not establish features in Ec.

| ID | Primary official documentation | Supported claim / restriction |
|---|---|---|
| O01 | [Kubernetes Deployment concepts](https://kubernetes.io/docs/concepts/workloads/controllers/deployment/) | Rolling update, surge/unavailable constraints, rollout revision scope and controller status; rollback does not revert all state. |
| O02 | [Kubernetes update without downtime](https://kubernetes.io/docs/tasks/run-application/update-deployment-rolling/) | Progress deadline and rollout history/undo; a stalled condition is not successful recovery. |
| O03 | [Argo Rollouts canary](https://argoproj.github.io/argo-rollouts/features/canary/) | Canary phases, traffic routing and analysis options; actual traffic may differ without router. |
| O04 | [Argo Rollouts analysis](https://argoproj.github.io/argo-rollouts/features/analysis/) | AnalysisRun, success/failure/abort behavior and measurement thresholds. |
| O05 | [Argo Rollouts blue-green](https://argoproj.github.io/argo-rollouts/features/bluegreen/) | Preview/active Services, promotion pause and analysis. |
| O06 | [Argo Rollouts rollback window](https://argoproj.github.io/argo-rollouts/features/rollback/) | Fast rollback conditions; available history/retention matters. |
| O07 | [Flux Flagger strategies](https://fluxcd.io/flagger/usage/deployment-strategies/) | Canary, blue-green, A/B with provider capability differences. |
| O08 | [GitHub deployment environments](https://docs.github.com/en/actions/reference/workflows-and-actions/deployments-and-environments) | Required reviewers, protected environment gates, branch and secret controls; availability depends on plan/repo. |
| O09 | [GitHub review deployments](https://docs.github.com/en/actions/how-tos/deploy/configure-and-manage-deployments/review-deployments) | Approval/rejection of waiting deployments; actual job starts after approved gates. |
| O10 | [Google Cloud Deploy rollback](https://docs.cloud.google.com/deploy/docs/roll-back) | Rollback creates a new rollout of an earlier release; not a generic undo of all side effects. |
| O11 | [SLSA build provenance](https://slsa.dev/spec/v1.0/provenance) | Subject/builder/buildDefinition model; requires independent trust policy and identity verification. |
| O12 | [Google SRE: Canarying Releases](https://sre.google/workbook/canarying-releases/) | Canary selection/representativeness, comparison and rollback trade-offs. |
| O13 | [Google SRE: Release Engineering](https://sre.google/sre-book/release-engineering/) | Hermetic builds, immutable release handling, gated deployments, traceability. |

**Engineering choices (not universal source mandates):** scoped approval bound to digest/config revision/policy; minimum canary samples, freshness and target generation; a CAS-based one-active-operation-per-target policy; explicit provider confirmation before terminal success; failure/unknown -> pause or abort path; rollback gated on migration compatibility. The concrete values and decisions belong to accepted project specifications.
