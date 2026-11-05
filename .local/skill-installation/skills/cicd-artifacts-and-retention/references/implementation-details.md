# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Ec references below are conditional consumer examples. They impose no Ec documents, YAML format,
stack or product policy on a generic task.

## Engineering method
1. Define immutable identity `{tenant, project, run, job, attempt, artifact_id, version}` and
   producer provenance (source commit/config revision/agent). Never trust a user-supplied path as
   storage authority.
2. Validate upload authorization, scope, filename/path normalization, object size, content type,
   digest algorithm, expected digest and quota. Reject traversal, symlinks/unsafe archive extraction
   and decompression bombs; do not infer safety from file extensions.
3. Stage bytes in bounded streaming chunks. Compute a full-object SHA-256 independently; multipart
   ETag is not generally the full-object SHA-256. Verify expected digest and size before atomic
   manifest publication.
4. Persist immutable metadata and publication state: `staging -> verified -> published`; failed
   uploads remain non-downloadable. Use transactions/outbox and reconcile orphaned blobs after
   crashes. Make repeated finalize idempotent for the same digest and reject conflicting reuse.
5. Authorize reads at every request (tenant, project, run, artifact, purpose). Prefer short-lived,
   scoped download URLs; handle revocation limitations explicitly. Record accesses where policy
   requires.
6. Specify retention from policy and timestamps, including legal hold, minimum retention,
   versioning, replication, backups and deduplicated references. Expired is not synonymous with
   physically erased. Never delete held/referenced blobs.
7. Garbage collection: select eligible metadata, mark tombstone, wait for safety/restore window if
   required, delete physical versions only when all references and holds are absent, reconcile
   errors, and record evidence. For content-addressed blobs, reference counting must be
   transactional.
8. Define cryptographic erasure/media sanitization separately from ordinary object deletion;
   evaluate NIST SP 800-88r2 when confidentiality requires it.
9. Test corruption, partial upload, concurrent finalization, cross-tenant access, reference races,
   holds, versioned delete markers, rollback/recovery, quotas and data-loss faults.
10. Report observed tests, limitations and pending real storage/DB integration. Never claim
    durability, secure deletion or throughput from an in-memory/SQLite fixture.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Upload and integrity](integrity-and-upload.md): integrity and upload.
- [Provenance and access](provenance-and-access.md): provenance and access.
- [Retention and GC](retention-and-gc.md): retention and gc.
- [Routing and test oracles](routing-and-oracles.md): routing and oracles.
- [Evidence ledger](sources-and-evidence.md): source provenance or a disputed recommendation.
- [Storage and deletion semantics](storage-and-deletion.md): storage and deletion.
