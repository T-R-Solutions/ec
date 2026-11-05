# Data migration and compatibility

Deployment strategies cannot guarantee safe rollback when data contracts change incompatibly. For services with persistent state, plan **expand/migrate/contract**, backward/forward compatibility windows, dual-read/write only when needed, cutover and cleanup after support windows. A previously deployed binary may not understand new schema or data representation.

Classify each migration: reversible, conditionally reversible, irreversible. Record why rollback is feasible, what backups exist, data loss risk and whether restoring data would discard legitimate post-deployment writes. Do not use `rollback_supported=True` as a replacement for a tested recovery procedure.

Test mixed-version reads/writes, transient concurrent versions, schema incompatibility, post-release writes, rollback inability, partial migrations and recovery from incomplete deploy. Separate code rollback from data restoration.
