# Activation controls and agent evaluation scenarios

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

## Positives

- “Validate these `needs` references, show one real directed cycle and return stable problem codes.” → select `cicd-pipeline-graph`.
- “Give a deterministic topological order, dependency generations and all downstream jobs.” → select.
- “Evaluate whether this edge can be added without a cycle; show affected successors.” → select.
- “Verify graph algorithms using random DAGs, exhaustive small graphs and independent libraries.” → select, possibly `property-based-testing` auxiliary.

## Negatives

- “Implement an editor palette with drag handles and inspector.” → `ec-pipeline-authoring-workspace`.
- “Reserve the next eligible agent with transactional fairness.” → `cicd-scheduler`.
- “Define how Ec serializes unknown fields in YAML.” → `ec-declarative-resource-schemas`.
- “Cancel a process and collect step logs.” → `cicd-execution-runtime`.

## Boundaries

- “After dependency failure, shall downstream jobs run?” → `SPEC GAP` until the host's failure policy defines satisfaction and skip behavior.
- “Expand a matrix job into 20,000 variants and inherit edges.” → needs explicit expansion contract and limits before construction.
- “Is the critical path of this DAG the expected build duration?” → no; theoretical precedence measure only.

An agent selection evaluation must compare primary skill, excluded skills and exact reasoning against these examples. No real agent-selection benchmark is claimed as executed in this package.
