# Immutable revisions and trust
Builds must pin immutable commit OID and configuration revision independently. Validate full expected hash algorithm/length for provider repository and verify checkout resolves to that object.
Push deletion often has an all-zero after SHA; do not schedule a deleted revision. A force push must not silently retarget an already accepted run.
PR/fork events may contain untrusted code; separate token and secret access from trusted push. A provider-generated merge commit and head commit are distinct choices, requiring explicit specification.
Avoid interpolating branch names into shell commands. Source checkout belongs to execution runtime.
