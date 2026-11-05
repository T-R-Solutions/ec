# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Ec references below are conditional consumer examples. They impose no Ec documents, YAML format,
stack or product policy on a generic task.

## Contract and invariants

1. Represent an edge **prerequisite → dependent**; a node's `needs` set lists its prerequisites.
   Define whether an empty graph and disconnected components are allowed **in the feature spec**,
   not by accident.
2. Validate stable IDs, duplicates, missing references, invalid/self references, repeated dependency
   edges, structural limits and directed cycles *before* using DAG algorithms. Never silently invent
   a missing node, normalize two distinct IDs into one, or drop unknown edges.
3. For any accepted DAG, a topological order contains each node exactly once, and for each edge
   `(u,v)`, `index(u) < index(v)`. No valid order exists for a directed cycle.
4. Choose **explicit deterministic tie-breaking** for reproducible output; a valid topological order
   is not necessarily unique. State the comparison/collation rule, including Unicode handling across
   languages. An ordered ready **heap** and Kahn levels are different products.
5. At a frontier, a node is dependency-eligible only if **every** prerequisite satisfies the
   contract-specific predicate. An exhausted graph frontier does not imply available agents,
   permissions, quotas, leases or permission to run.
6. Distinguish a graph's theoretical dependency depth/critical path from a wall-clock build
   forecast: actual duration depends on resources, retries, cache and external execution.
7. Evaluate a proposed edit against an immutable graph snapshot. Reject invalid cycles and reference
   breaks without partially committing changes; report downstream impact using reachability.
8. Make limits explicit for nodes, edges, fan-in, fan-out, label length, expansion and processing
   time. Keep diagnostics stable and actionable; protect validators against adversarial graph sizes.


## Working method

### Step 1 — Establish graph authority and edge semantics

Read the approved feature spec, domain types and actual source. Identify the scope of IDs,
dependency direction, whether prerequisites refer to jobs/steps/stages, resolution of references,
conditional edges, approval gates and the validity of an empty graph. An example from another CI/CD
vendor is **not** the Ec contract. Record unknown semantics as `SPEC GAP`. See
[graph-contract-and-trust-boundaries](graph-contract-and-trust-boundaries.md).

### Step 2 — Normalize input only when safe, then validate

Parse the **owner's** input format with its schema/parsing authority; this skill consumes typed
nodes and edges. Detect duplicates and dangling references before constructing adjacency maps.
Reject self-loops; detect multi-node cycles and preserve a **real directed cycle witness** for
users. Guard expansion limits before allocating large graphs. Record problem location and cause. See
[validation-and-diagnostics](validation-and-diagnostics.md) and
[cycle-diagnostics](cycle-diagnostics.md).

### Step 3 — Select order and frontier algorithms deliberately

Use Kahn indegree processing or equivalent DFS for static topological sort. A lexicographically
**global ready queue** gives reproducible tie-breaking with `O((V+E) log V)` using a priority queue;
simple Kahn with a FIFO queue provides `O(V+E)` but does not promise lexicographic output. Compute
**generations/layers separately**, where all predecessors of layer `k` occur in earlier layers; do
not mistake layers for serialized job execution. See [topological-ordering](topological-ordering.md)
and [fan-in-fan-out](fan-in-fan-out.md).

### Step 4 — Derive dependency relations and bounded fan-out

Construct both predecessor and successor adjacency; determine direct fan-in/fan-out, transitive
ancestors and descendants with visited sets; avoid exponential enumeration of paths unless an
explicit bounded requirement exists. Expanding a job matrix is **not** equivalent to graph fan-out
and needs a separately approved contract for generated IDs, cardinality, edge mapping, and failure
policy. See [fan-in-fan-out](fan-in-fan-out.md).

### Step 5 — Evaluate readiness without deciding scheduling policy

Only consider pending nodes. Compute missing/unacceptable prerequisites under an **explicit**
satisfaction predicate from the spec, and distinguish waiting for running work from a terminal
result that does not satisfy the predicate. Never silently skip jobs or claim a job started.
Selecting an agent, reserving a slot, fairness, quotas, leases/fencing, transitions and retries
belong to `cicd-scheduler` and runtime specifications. See
[readiness-and-status](readiness-and-status.md).

### Step 6 — Handle edits, revisions and graph evolution

For small/medium authoring graphs, favor full revalidation after graph edits until measured costs
justify incremental algorithms. Incremental topological-order algorithms have formal and empirical
literature, but complexity in implementation and concurrency warrants benchmarks and a stable
revision model. Compare *old* and *proposed* edge sets; report affected descendants and fix the
accepted graph to a configuration revision. See
[graph-edits-and-revisions](graph-edits-and-revisions.md),
[scientific-evidence-and-limits](scientific-evidence-and-limits.md).

### Step 7 — Verify with independent oracles

Test named examples, self/mutual/deep cycles, disconnected nodes, large fan-out/fan-in,
Unicode/duplicate IDs, input permutations, and non-mutation on failed edits. Differential-check
independent implementations (`graphlib`, `petgraph`, `NetworkX` when available); exhaustively
enumerate **small** graphs and generate bounded DAGs with replayable seeds. Verify each edge
precedes its dependent, rather than comparing only one expected order. Include mutation sensitivity
and resource limits. A fixture test cannot prove the Ec scheduler behaves correctly. See
[testing-and-oracles](testing-and-oracles.md), [performance-and-limits](performance-and-limits.md).

### Step 8 — Report outputs and evidence

Deliver a graph contract and ID policy; diagnostic and cycle witness; ordered nodes and/or
explicitly labeled generations; prerequisite reachability and impact; concrete bounds;
invariant-oracle mapping; reproducible tests (commands, versions, seeds, hashes) and explicit
pending real integration. When project workflow full route applies, map `spec.md` AC → `plan.md`
algorithm choice → `tasks.md` implementation → evidence and convergence. See [evidence-and-project
workflow](evidence-and-reporting.md).


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Directed cycle detection and witness construction](cycle-diagnostics.md): cycle diagnostics.
- [Traceability, project workflow and scope of evidence](evidence-and-reporting.md): evidence and reporting.
- [Dependency reachability, fan-in/fan-out and expansion](fan-in-fan-out.md): fan in fan out.
- [Graph contract and trust boundaries](graph-contract-and-trust-boundaries.md): graph contract and trust boundaries.
- [Graph edits, revision fixation and incremental algorithms](graph-edits-and-revisions.md): graph edits and revisions.
- [Bounds, resource safety and performance evidence](performance-and-limits.md): performance and limits.
- [Prerequisite readiness is not execution scheduling](readiness-and-status.md): readiness and status.
- [Activation controls and agent evaluation scenarios](routing-and-test-oracles.md): routing and test oracles.
- [Peer-reviewed research: evidence and limits](scientific-evidence-and-limits.md): source provenance or a disputed recommendation.
- [Source register and engineering decisions](sources-and-decisions.md): source provenance or a disputed recommendation.
- [Independent DAG testing oracles and fault models](testing-and-oracles.md): testing and oracles.
- [Topological ordering versus generations](topological-ordering.md): topological ordering.
- [Validation order, bounded resources and precise diagnostics](validation-and-diagnostics.md): validation and diagnostics.
