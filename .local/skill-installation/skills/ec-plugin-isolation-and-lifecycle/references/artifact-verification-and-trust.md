# Artifact Verification, Provenance and Trust — On-Demand Method

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

## Trust is a conjunction, not a badge

For a candidate `A = (publisher_id, plugin_id, release, digest)`, distinguish independent predicates:

- `metadata_compatible(A)`: manifest, host contracts and dependency resolution passed in the preceding skill; does **not** establish safety or permission.
- `bytes_match(A)`: the byte stream matches a digest obtained from an authenticated expected source; a digest supplied by the adversary along with the bytes offers no authenticity.
- `authentic_publisher(A)`: trusted signature/key or accepted OIDC issuer + **exact** signer identity, with key rotation/compromise policy; do not infer this from `signed=true` or a display name.
- `expected_provenance(A)`: signed attestation covers this subject digest and matches approved source repository, builder and build type/parameters. SLSA level, if asserted, must be verified, not merely copied from metadata.
- `fresh_and_allowed(A)`: trusted metadata not expired, rollback/freeze/revocation checks passed; locally approved publisher/source/digest policy holds now.
- `deployable(A)`: artifact is compatible, verified, authorized, its sandbox can be enforced and dependencies are acceptable **at the actual activation boundary**.

If any required check is **unknown**, do not translate unknown into pass. Precisely define which checks are required by project workflow and disallow external execution until the verification/signing boundary is demonstrated.

## Verification order and evidence

1. Obtain an immutable content address (digest) from an authenticated metadata channel, not a mutable release tag alone. Pin manifest, dependency graph and artifact bytes to the same candidate.
2. Stream with byte ceilings and hash the bytes; verify content digest **before** loading as executable code. Immutable content store must prevent alternate bytes under a trusted digest. For OCI, verify descriptor sizes and digests; OCI JSON descriptor metadata belongs to the OCI transport protocol, **not** Ec desired configuration.
3. Verify the signature and signer/issuer/trust path; Sigstore keyless verification requires checking certificate identity and OIDC issuer explicitly. TUF-style update roles, expirations, threshold signatures and rollback/freeze checks are an option for artifact discovery/distribution, not evidence that Ec implemented TUF.
4. Verify provenance envelope, statement subject digest, trusted builder ID, expected source and external parameters; SLSA Build verification is not satisfied by attaching an unverified attestation. Third-party SBOM/signed metadata are *additional evidence*, not blanket safety proof.
5. Enforce separately administered publisher/source/digest allow/deny controls; key revocation overrides previous positive receipts and cache. Bind receipts to trust-policy revision, verification time and immutable artifact identity.
6. Safely preflight packaging, then stage outside live code paths. Never run an install-time script, dynamic loader or arbitrary parser that may execute commands before verification. For verified-but-incompatible or untrusted candidates, quarantine and preserve minimally sufficient incident data.
7. Revalidate any receipt whose trust policy, expiry, digest, dependencies or desired revision changed before the final start; the *same artifact bytes* must be activated (avoid TOCTOU replacement).

## Hostile archive preflight (reference only)

For an archive ingestion path, check: absolute/drive/UNC paths, `..`/`.` segments, NULs and ambiguous separators, symlinks/hardlinks/devices, duplicate/case-insensitive collision names, reserved components, member count/depth/length, declared compressed and decompressed sizes, compression ratio, malformed CRC, nested archive policy, executable and install-hook controls. Verify hashes over **actual bytes**, not only metadata. Do not unpack to a shared destination or follow pre-existing symlinks. Implement approved extraction using race-resistant OS primitives on each target platform; **the included ZIP preflight is not a secure extractor**.

## Decision matrix

| Result | Installation / UI handling |
|---|---|
| Compatible metadata only | `unverified`; no execution |
| Missing/invalid signature or unknown trust root | `blocked` or `quarantined`; log reason |
| Signed by a key outside allowed publisher identity | `blocked`; signature validity alone insufficient |
| Valid provenance but wrong source or subject digest | `blocked`; no implicit trust-on-first-use |
| Expired/revoked signing metadata or catalog rollback | `blocked` and re-evaluate active digests |
| Verified, compatible, approved, sandbox preflight passing | Eligible to stage/start; **not yet active** |
| Verifier offline | `verification-unavailable`; no fail-open activation |

**Authority references:** TUF 1.0.36, SLSA 1.2 artifact verification, Sigstore Cosign verification, OCI descriptor specification, OWASP CI/CD Security, and Samuel et al. CCS 2010 / Torres-Arias et al. USENIX Security 2019 (bibliography and access depth in `sources-and-decisions.md`).
