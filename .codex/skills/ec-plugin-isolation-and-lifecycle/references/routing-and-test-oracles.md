# Skill Routing and Acceptance Evaluation Oracles

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

**Status:** scenarios are **PLANNED**, not evaluated by an AI agent, and real Ec acceptance tests are **NOT EXECUTED**. Positive activation tests require `ec-plugin-isolation-and-lifecycle`; negative cases should route elsewhere or decline without inventing runtime support.

## Routing scenarios (20 proposed)

| ID | Input task | Expected route / test oracle |
|---|---|---|
| R01 | Verify a signed plugin before activation | Activate; independently check signer identity, subject and trust root |
| R02 | Install a version-pin plugin safely | Activate; stage and verify before runtime start |
| R03 | Provide a Wasmtime sandbox for untrusted extension | Activate; map imports and host resource budgets |
| R04 | Stop compromised plugin across workers | Activate; block hostcalls and fence old generation immediately |
| R05 | Upgrade a plugin while jobs are running | Activate; explain drain/stop-first vs blue-green project workflow choice |
| R06 | Roll back a plugin after broken update | Activate; new YAML revision and reverify old bytes/current trust |
| R07 | Explain why an installed plugin is not active | Activate; distinguish install verification and readiness |
| R08 | Uninstall a plugin with dependents | Activate; impact and safe cleanup/retention |
| R09 | Show plugin permissions in administration UI | Activate alongside access/projection as needed; requests are not grants |
| R10 | Investigate host resource exhaustion by WASI | Activate; host-wide controls, no sandbox claim from linear memory |
| R11 | Reconcile plugin state after control-plane restart | Activate; fresh observation and generation fencing |
| R12 | Secure plugin update catalog against rollback | Activate; freshness, key rotation and revocation |
| R13 | Parse plugin manifest semver | **Do not activate**; use `ec-plugin-manifests-and-compatibility` |
| R14 | Solve transitive extension-point dependency constraints | **Do not activate**; use manifest/compatibility skill |
| R15 | Serialize project YAML | **Do not activate**; use declarative schema skill |
| R16 | Style unrelated dashboard | **Do not activate**; relevant design skill |
| R17 | Renew an agent lease | **Do not activate**; agent-control protocol |
| R18 | Retry a failed pipeline job | **Do not activate**; run execution operations |
| R19 | Explain inherited user RBAC permissions | **Do not activate alone**; effective-permissions skill |
| R20 | Create generic Rust FFI binding unrelated to plugins | **Do not activate**; Rust / software design skills |

## Real Ec acceptance tests (48 **not executed**)

| ID | System-level assertion / negative oracle |
|---|---|
| E01 | Publisher key is independently trusted by policy, not claimed in plugin metadata |
| E02 | Artifact bytes differ from authenticated digest → reject before code load |
| E03 | Same mutable tag but new digest → no silent update |
| E04 | Correct signature but unapproved certificate identity/issuer → reject |
| E05 | Correct signature but wrong provenance subject digest → reject |
| E06 | Correct digest but unexpected source repository → reject |
| E07 | Correct source but untrusted builder/parameters → reject |
| E08 | Expired update metadata → block, not fail open |
| E09 | Stale catalog rollback metadata → detect/decline |
| E10 | Revoked signer/compromised key invalidates cached verification |
| E11 | Dependency substitution with unpinned digest → reject |
| E12 | Artifact archive `../` or absolute member → reject pre-extraction |
| E13 | Archive symlink/hardlink/device entry → reject |
| E14 | Archive duplicate case-insensitive path → reject |
| E15 | Archive decompression bomb / count/depth budget → bounded reject |
| E16 | Archive decode error/CRC corruption → fail closed |
| E17 | Compatibility alone does not permit installation |
| E18 | Requested capability without grant → deny hostcall |
| E19 | Grant outside project/tenant scope → deny hostcall |
| E20 | Grant to different digest/generation → deny hostcall |
| E21 | Revoked grant blocks new hostcalls before process termination |
| E22 | Secret reference resolves only for authorized runtime purpose |
| E23 | Secret contents never appear in status, YAML, audit or logs |
| E24 | Network egress denied without explicit policy |
| E25 | Filesystem traversal via broker hostcall is denied |
| E26 | Plugin ANSI/HTML/control output is sanitized and bounded |
| E27 | Wasm guest infinite loop terminated/yielded under measured budget |
| E28 | Hostcall I/O/resource exhaustion bounded at host level |
| E29 | Native helper has no elevated identity, host mounts or unsafe namespace |
| E30 | Container restricted profile and cgroup quotas actually enforced |
| E31 | Sandbox escape adversarial attempt cannot access host credentials |
| E32 | Plugin start receives generation unique to slot and immutable digest |
| E33 | Readiness from old generation cannot mark new plugin active |
| E34 | Concurrent update and disable obey revision CAS, not last-writer-wins |
| E35 | Duplicate request ID has identical result, cannot re-run side effects |
| E36 | Crash after verification but before start recovers to safe staged/unknown |
| E37 | Crash after start but before acknowledgment does not duplicate activation |
| E38 | Unreachable runtime is reported unknown, not active/healthy |
| E39 | Disable denies hostcalls immediately, even if stop is pending |
| E40 | Cleanup timeout reports stopping/unknown, never stopped |
| E41 | Uninstall with dependent consumers requires approved impact handling |
| E42 | Rollback to revoked artifact is blocked |
| E43 | Rollback rechecks grants under current trust policy |
| E44 | Actual YAML desired state round-trips across Visual/Form/YAML |
| E45 | Runtime operational observations never persist as desired YAML |
| E46 | UI conveys verifying, staged, running, blocked, failed, unknown without color-only indication |
| E47 | Keyboard and screen-reader validation on real plugin management flows |
| E48 | Benchmarks and adversarial scenarios recorded with reproducible host/runtime environment and logs |

## Suggested execution sequence

1. Run actual parser/manifest contract tests and trusted verifier against tampered fixture artifacts with a real configured policy.
2. Start the chosen sandbox in the actual deployment environment with untrusted guest fixtures. Test OS/network/FS/memory/CPU and hostcall denials.
3. Execute lifecycle update/disable/recovery tests with durable PostgreSQL or actual chosen backend and concurrent requests.
4. Exercise frontend with Vue components, assistive technology and network failures. Record observed server evidence/revision/generation.
5. Carry out controlled red-team exercises; remediate exceptions; report residual risks rather than saying sandboxing is proved by synthetic tests.
