# Sources and evidence ledger

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

## Official provider documentation (verified)
- GitHub, Validating webhook deliveries: https://docs.github.com/en/webhooks/using-webhooks/validating-webhook-deliveries — raw body HMAC-SHA256 and constant-time comparison.
- GitHub, Troubleshooting webhooks: https://docs.github.com/en/webhooks/testing-and-troubleshooting-webhooks/troubleshooting-webhooks — retries, throttling, signature failures.
- GitLab, Webhooks: https://docs.gitlab.com/user/project/integrations/webhooks/ — modern signed webhook headers, timestamp, `webhook-id`, and legacy compatibility.
- GitLab, Project webhooks API: https://docs.gitlab.com/api/project_webhooks/ — delivery event diagnostics and resend context.
- Git, gitrevisions: https://git-scm.com/docs/gitrevisions — revisions, refs, object identifiers.
- Git, git-rev-parse: https://git-scm.com/docs/git-rev-parse — resolving names to object IDs.

## Peer-reviewed research — contextual, not Ec measurements
- Kreps, Narkhede and Rao (2011), *Kafka: a Distributed Messaging System for Log Processing*, NetDB 2011. https://notes.stephenholiday.com/Kafka.pdf . Durable log/event concepts; not proof of exactly-once for webhooks.
- Akidau et al. (2015), *The Dataflow Model: A Practical Approach to Balancing Correctness, Latency, and Cost in Massive-Scale, Unbounded, Out-of-Order Data Processing*, VLDB 2015, DOI 10.14778/2824032.2824076. Context for out-of-order streams; not a CI webhook benchmark.
- Helland (2007), *Life Beyond Distributed Transactions: An Apostate's Opinion*, CIDR 2007. https://www.cidrdb.org/cidr2007/papers/cidr07p15.pdf . Context for idempotent message processing and independent side effects.

Research caveat: official docs specify provider behavior; academic papers support distributed-event reasoning but do not establish empirical performance, security or correctness of this implementation.
