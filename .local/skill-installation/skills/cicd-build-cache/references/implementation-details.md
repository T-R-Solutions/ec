# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Ec references below are conditional consumer examples. They impose no Ec documents, YAML format,
stack or product policy on a generic task.

## Method
1. Distinguish dependency caches (restore keys and mutable dependency directories), action-output
   caches (deterministic declared inputs -> declared outputs), and retained artifacts. Do not
   conflate the three.
2. Determine whether the action is safe to cache: reproducible outputs, no undeclared
   time/random/network/secret effects, all relevant inputs accounted for, and no side-effect-only
   task. Mark uncertain actions non-cacheable.
3. Derive a versioned canonical key from domain-separated, length-encoded inputs: source/content
   digests, dependency lockfiles, command/arguments, toolchain and platform, relevant environment
   and cache schema version. Do not serialize secrets or derive authorization solely from a key.
4. Define a read/write policy based on tenant, project, branch/PR trust, producer provenance and
   privilege. Untrusted/fork jobs must not poison privileged writable caches; any restored bytes are
   untrusted until verified.
5. Upload immutable blobs by digest, independently verify SHA-256 and size, then publish an
   action-key mapping only after complete validation. On a key conflict with different content,
   reject or quarantine; never silently overwrite trusted results.
6. On restore, authorize scope, verify full-object digest, verify manifest and output paths, prevent
   traversal/symlink escapes and unsafe archive extraction, and fall back to recomputation on
   miss/corruption. A hit is not proof of safe execution.
7. Make invalidation explicit: key/schema/toolchain changes, policy revocation, integrity failures,
   producer compromise, TTL and manual purge. Restore-key prefix fallbacks must not cross trust
   boundaries.
8. Bound size, retention and storage. Eviction may use LRU/TTL/quotas but must preserve live
   references while deletion is in progress. Never promise a specific hit rate or speedup without
   workload benchmarks.
9. Measure hit/miss reasons, transfer bytes/time, recomputation, poisoned/corrupt entries, storage
   cost and correctness under cold/warm/invalidated runs. Benchmark under representative projects
   and compare total time, not only cache lookup latency.
10. Test positive, negative, boundary and adversarial cases. Record synthetic vs actual
    backend/agent/CI evidence and map to acceptance criteria; do not claim Ec implementation from
    this skill.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Content-addressed storage](cas-and-integrity.md): cas and integrity.
- [Invalidation and eviction](invalidation-and-eviction.md): invalidation and eviction.
- [Keys and hermeticity](keys-and-hermeticity.md): keys and hermeticity.
- [Evidence ledger](sources-and-evidence.md): source provenance or a disputed recommendation.
- [Routing and test oracles](testing-and-routing.md): testing and routing.
- [Trust and cache poisoning](trust-and-poisoning.md): trust and poisoning.
