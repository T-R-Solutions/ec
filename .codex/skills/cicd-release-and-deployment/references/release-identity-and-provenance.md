# Release identity, provenance and immutability

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill. Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

A release identifier must resolve to a stable tuple: artifact digest, artifact type, source revision, canonical configuration revision/hash, build run and provenance identity/verification policy. A human-friendly `v1.2.3`/container tag is a *locator*, not sufficient cryptographic identity. Bind deployment attempt to a digest or immutable artifact version; prevent a later tag update from changing the deployed bytes silently.

Supply-chain provenance is evidence that a particular builder produced a subject, contingent on **signature/identity and trusted build policy verification**. Merely storing a SLSA predicate or boolean field does not authenticate it. Require checks on subject digest, expected signer/issuer, predicate/builder identity and allowed source in the consuming product. SLSA build provenance is not itself proof of production security; record what was actually verified.

Never promote an artifact to another environment by silently rebuilding it from a mutable branch. If fresh environment-specific configuration is permitted, bind its own immutable revision and policy to that attempt. The product's accepted project workflow decides which differences are legal. For Ec, YAML remains authority for desired config; observed attempts and provider receipts remain operational.

Test: renamed artifact tag, mismatched digest, wrong source revision, wrong config revision, stale attestation, missing attestation, cross-tenant release references. An unchanged digest across a new promotion should preserve release identity without claiming identical deployed configuration if the target allows overrides.
