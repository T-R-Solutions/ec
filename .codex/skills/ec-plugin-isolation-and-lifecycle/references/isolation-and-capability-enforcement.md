# Isolation and Runtime Capability Enforcement — On-Demand Method

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

## Choose runtime on evidence, not preference

| Runtime candidate | Actual boundary | Must additionally validate | Limitations / project workflow gate |
|---|---|---|---|
| Wasm guest (e.g. Wasmtime/WASI) | Wasm instance linear-memory/control-flow protection; explicit host imports | Every linked host function, granted directory/FD/network access, memory/table limits, fuel/epoch interruption, host-side I/O, CPU/thread/fd/process quotas, resource exhaustion through imported APIs | Wasm isolation is not complete availability isolation; runtime vulnerabilities and host-call confused deputy remain. Supported WASI API/preview must be selected in project workflow. |
| Native helper process | OS process address-space isolation and selected OS sandbox policies | Dedicated identity, no-new-privileges, seccomp+LSM, namespaces, filesystem read-only, network egress, cgroup v2 CPU/memory/PIDs/I/O, IPC protocol limits | Process separation alone is **not** sufficient without concrete privilege boundaries. Linux mechanisms are platform-specific. |
| Container | OS isolation from namespaces/cgroups plus runtime controls | Baseline/restricted policy, no privileged mode or host namespaces, drop caps, non-root, readonly root filesystem, seccomp, network policy, secrets, resource limits | Shared kernel and host/runtime vulnerabilities; Kubernetes is an example **not an Ec deployment requirement**. |
| MicroVM | Hypervisor-based boundary | Guest kernel and VMM patching, virtual device surface, network/storage segmentation, key delivery, warmup/latency/cost, lifecycle monitoring | More moving parts, uncertain overhead until measured; Firecracker research is comparative evidence, not Ec benchmark. |
| In-process native dynamic library | No security isolation from Rust host | Applicable only to already trusted host components, or after stronger external proof | FFI `unsafe` and ordinary ABI compatibility do not constrain plugin authority. **Never a default for untrusted third-party code.** |

Seccomp filters **are not a sandbox** by themselves (Linux kernel documentation). Wasmtime `ResourceLimiter` does **not** account for every allocation and does not replace execution/fuel limits (Wasmtime API documentation). The 2025 USENIX Security resource-isolation study empirically demonstrates host-side resource exhaustion routes through WASI/WASIX. Threat-model-specific tests are mandatory.

## Hostcall / privilege decision contract

For every plugin-to-host request require a consistent identity tuple: `(artifact_digest, plugin_id, instance_generation, scope, operation, target, credential_epoch)`. Reject a request unless:

1. The generation and digest match a confirmed **currently serving** instance and published desired revision.
2. The host's authorization decision is fresh enough for the action; granted scope/capability covers exact operation and target. A requested permission or signed plugin does not grant access.
3. Revocation epoch and short-lived credential (when applicable) remain valid. A closed/gracefully stopping instance must not obtain new grants.
4. Inputs, response sizes, rate limits, timeouts and output encoding are bounded; network/FS paths are canonicalized *within the broker*, preventing scope traversal and confused-deputy behavior.
5. Actions and denials produce scoped audit events without embedding secret values; sensitive authorization explanations are redacted to the viewer's own scope.

The host must never expose its entire process environment, filesystem handles, instance-wide credentials or full HTTP client by default. Secret **references** can be desired YAML configuration, but secret *material* is obtained just-in-time and bound to purpose, instance and lifetime. Deny external network access unless granted with an independently enforced egress policy.

## Concrete adversarial integration cases (not yet executed)

- Untrusted module attempts undeclared filesystem/network/secret operation; host broker denies it, including after permissions change.
- Wasm guest allocates memory, loops, spawns hostcalls or requests large stream; bounded CPU, memory, I/O, thread, FD, process count and cancellation are observable from *host* resource limits.
- Native process tries ptrace, access to host PID/socket, container escape primitives, symlink traversal and writable host mounts; denied by appropriate platform controls.
- Plugin emits ANSI control sequences, malicious HTML, structured logs with credential values and oversized metrics; output sanitized, bounded and redacted.
- Broker and runtime are restarted separately; stale generation and old credentials stay rejected.
- Timing, resource exhaustion, and sandbox isolation are measured with reproducible workloads and environment details; do not claim performance superiority without real data.

**Research:** Wasmtime security + runtime resource docs, Linux seccomp and cgroup v2, Kubernetes Pod Security Standards, NIST SP 800-190, Rust FFI documentation, USENIX Security 2025 Yu et al., NSDI 2020 Agache et al., USENIX Security 2023 Kim & Lee. See `sources-and-decisions.md`.
