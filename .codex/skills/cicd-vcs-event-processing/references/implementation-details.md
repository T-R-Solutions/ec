# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Ec references below are conditional consumer examples. They impose no Ec documents, YAML format,
stack or product policy on a generic task.

## Method
1. Establish trust boundary and tenant/repository binding. Limit body size and accepted content
   types before parsing. Authenticate **raw bytes** with the provider's documented method; do not
   reuse GitHub's signature algorithm for GitLab.
2. For GitHub use `X-Hub-Signature-256` HMAC-SHA256 over raw body with constant-time comparison. For
   modern GitLab signing tokens follow `webhook-id`, `webhook-timestamp`, `webhook-signature`
   (`v1,<base64>`) and the provider's signed-message construction. Legacy GitLab `X-Gitlab-Token` is
   not equivalent to signed integrity. Enforce version-appropriate rules.
3. Normalize authenticated provider event into explicit `{provider, installation, repository,
   event_type, delivery_id, ref, revision, actor_trust}`. Reject ambiguous repository/installation
   claims and unsupported event types.
4. Define deduplication scope `(provider, tenant, installation, repository, delivery_id)` and
   payload conflict behavior. A delivery ID is not globally unique across all providers/tenants.
   Treat redelivery as the same intent; retain audit evidence.
5. Apply configured filters to canonical refs and event types. Specify glob semantics and PR/fork
   trust separately. Never let an untrusted PR automatically receive privileged secrets.
6. Resolve and pin immutable commit OIDs. Never use a moving branch head as the source revision of
   an accepted run. Validate provider event SHA/ref semantics; deleted refs, force pushes and
   merge-queue events require explicit policy.
7. Atomically persist accepted delivery, dedup key and trigger intent (e.g. transaction/outbox);
   acknowledge only after durable commit. Retries after ambiguous acknowledgment must not create
   duplicate trigger intents.
8. Separate accepted event, queued trigger and executed build. A `2xx` webhook acknowledgment does
   not imply successful build.
9. Reconcile dropped, delayed, duplicated and out-of-order deliveries with provider delivery APIs or
   polling, under rate limits and permissions. No global ordering or exactly-once external effects
   guarantee.
10. Produce testable contracts, negative/mutation tests, fault injection and project workflow
    evidence. Distinguish local SQLite fixtures from PostgreSQL concurrency and live-provider
    interoperability.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Authentication](authentication.md): authentication.
- [Durable ingestion](durability-and-idempotency.md): durability and idempotency.
- [Selection and verification](failure-oracles.md): failure oracles.
- [Event normalization and filtering](normalization-and-filters.md): normalization and filters.
- [Immutable revisions and trust](revisions-and-trust.md): revisions and trust.
- [Sources and evidence ledger](sources-and-evidence.md): source provenance or a disputed recommendation.
