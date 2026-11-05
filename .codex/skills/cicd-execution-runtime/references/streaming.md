# Output, secrets and resource lifecycle
Read stdout and stderr concurrently; preserving order *within each stream* is possible, but exact cross-stream ordering is generally not guaranteed without an explicit merge mechanism.
Bound queues, bytes and disk. Record truncation and gaps; never silently claim complete logs.
Redact secrets at ingestion and avoid command-line arguments for sensitive values where they may be exposed by process inspection.
Cleanup temporary workspace after confirmed termination according to retention policy; failed cleanup must be observable and retriable.
Distinguish operational logs from configuration YAML.
