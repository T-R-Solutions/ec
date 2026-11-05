# Isolation and resource limits
Linux cgroup v2 provides resource controls (`memory.max`, `pids.max`, `cpu.max`) but does not itself isolate filesystem or network.
Namespaces, seccomp and LSM restrictions must be assessed together; `no_new_privs` only prevents certain privilege gains on exec and is not a sandbox.
Require independent verification of enforcement, including a hostile fixture unable to read host secrets, escape workspace or use forbidden network routes.
Do not execute hostile workloads on a shared production host to test isolation. Use a disposable isolated environment with explicit authorization.
Resource ceilings and cleanup policies depend on OS and kernel capabilities; never silently downgrade a required containment profile.
