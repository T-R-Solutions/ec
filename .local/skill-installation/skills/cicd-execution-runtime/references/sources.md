# Evidence ledger: official specifications and scientific literature

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

## Official primary documentation (directly consulted)
1. Rust std::process::Command: https://doc.rust-lang.org/std/process/struct.Command.html — argv, spawning, exit behavior.
2. Rust std::process::Child: https://doc.rust-lang.org/std/process/struct.Child.html — wait, kill and zombie caveat.
3. Linux kernel no_new_privs: https://docs.kernel.org/userspace-api/no_new_privs.html — explicitly limited privilege guarantee.
4. Linux kernel cgroup v2: https://docs.kernel.org/admin-guide/cgroup-v2.html — memory, CPU and process limits.
5. POSIX kill: https://pubs.opengroup.org/onlinepubs/9799919799/functions/kill.html — signal semantics (reference for future integration).
6. POSIX waitpid: https://pubs.opengroup.org/onlinepubs/9799919799/functions/waitpid.html — child reaping (reference for future integration).

## Peer-reviewed research to consult during threat modeling
- Felter, Ferreira, Rajamony, Rubio (2015), "An Updated Performance Comparison of Virtual Machines and Linux Containers", IEEE ISPASS, DOI: 10.1109/ISPASS.2015.7095802. Evidence about specific performance measurements under the paper's workloads, **not** proof of isolation or performance for this runtime.
- Reshetova, Karhunen, Nyman, Asokan (2014), "Security of OS-Level Virtualization Technologies", Nordic Conference on Secure IT Systems (NordSec), DOI: 10.1007/978-3-319-11599-3_6. Threat-model framing for OS-level virtualization; does not certify a container deployment.
- Sultan, Ahmad, Dimitriou (2019), "Container Security: Issues, Challenges, and the Road Ahead", IEEE Access, DOI: 10.1109/ACCESS.2019.2911732. Survey; not a benchmark or a security certification.

Evidence boundary: peer-reviewed references are included for technical framing; DOI metadata and full texts require independent publisher verification before relying on specific experimental findings. No paper is claimed to have tested Ec.
