# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Ec references below are conditional consumer examples. They impose no Ec documents, YAML format,
stack or product policy on a generic task.

## Procedure
1. Separate operational logs/test outcomes from desired YAML configuration. Bind every record to
   tenant, project, run, job, step and **attempt**, and pin source/config revision references.
2. Define an append contract: immutable per-stream integer sequence, byte payload and digest,
   timestamps (`event` versus `observed`), encoding and stream identity (`stdout`, `stderr`,
   structured). Do not infer a global stdout/stderr order from wall-clock timestamps.
3. Authenticate the producer and authorize per-attempt writes. For a repeated sequence with
   identical digest return deduplicated acknowledgment; conflicting reuse is an error. Reject stale
   attempts/epochs.
4. Persist accepted chunk and sequence acknowledgment atomically. Detect gaps and expose missing
   ranges; never silently treat an incomplete stream as complete. Resume from last committed
   sequence, not the last client-rendered sequence.
5. Bound memory and storage, define backpressure and truncation markers, sanitize ANSI/control
   characters at display, and redact secrets before persistence where possible. Regex-only redaction
   cannot guarantee removal of arbitrary transformed secrets.
6. Preserve raw bytes safely, decode UTF-8 incrementally across chunk boundaries, and specify
   replacement/error behavior for invalid encodings.
7. Parse test reports with entity expansion and external resolution disabled,
   input-size/depth/test-count limits, and a documented dialect. “JUnit XML” is an ecosystem family,
   not one universally enforced schema.
8. Normalize test identities and statuses (`passed`, `failed`, `error`, `skipped`, `unknown`) and
   distinguish test failure, build/process failure, infrastructure failure and cancellation. Handle
   retries and flaky tests by attempt identity; never overwrite history silently.
9. Reconcile declared counts against observed cases, incomplete reports and duplicate suites. Reject
   conflicting duplicates; expose report-quality diagnostics.
10. Test positive, negative, boundary, mutation and fault cases: reorder, replay, gaps,
    crash-after-commit, invalid UTF-8, malicious XML, secret leaks, high volume and missing reports.
    Map evidence to acceptance criteria and explicitly mark non-executed integration.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Chunk ingestion](chunks-and-replay.md): chunks and replay.
- [Failure taxonomy](failure-taxonomy.md): failure taxonomy.
- [Test oracles](routing-and-oracles.md): routing and oracles.
- [Security and redaction](security-and-redaction.md): security and redaction.
- [Official documentation and research evidence](sources-and-evidence.md): source provenance or a disputed recommendation.
- [Test report ingestion](test-report-ingestion.md): test report ingestion.
