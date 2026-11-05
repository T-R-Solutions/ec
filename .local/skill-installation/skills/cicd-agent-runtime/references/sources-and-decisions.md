# Evidence ledger

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

## Official and maintainer documentation — consulted
- Kubernetes Node Status: https://kubernetes.io/docs/reference/node/node-status/ — node conditions, capacity and heartbeat mechanisms.
- Kubernetes Leases: https://kubernetes.io/docs/concepts/architecture/leases/ — lightweight coordination and heartbeat Lease semantics.
- HashiCorp Nomad agent lifecycle: https://developer.hashicorp.com/nomad/docs/deploy/nomad-agent — client fingerprint, registration, heartbeat, disconnection.
- HashiCorp Nomad client configuration: https://developer.hashicorp.com/nomad/docs/configuration/client — host capability and allocation context.
- HashiCorp Nomad node identity: https://docs.hashicorp.com/nomad/docs/architecture/cluster/node-identity — identity renewal and storage.
These are reference designs, not mandates to implement Kubernetes/Nomad behavior.

## Peer-reviewed research — contextual, not product evidence
- Hindman et al., *Mesos: A Platform for Fine-Grained Resource Sharing in the Data Center*, NSDI 2011, https://www.usenix.org/conference/nsdi11/mesos-platform-fine-grained-resource-sharing-data-center . Empirical resource sharing in Mesos; not a benchmark of Ec.
- Verma et al., *Large-scale cluster management at Google with Borg*, EuroSys 2015, https://research.google/pubs/large-scale-cluster-management-at-google-with-borg/ . Operational cluster management experience; not a proof of Ec behavior.
- Schwarzkopf et al., *Omega: Flexible, Scalable Schedulers for Large Compute Clusters*, EuroSys 2013, https://research.google/pubs/omega-flexible-scalable-schedulers-for-large-compute-clusters/ . Scheduling architecture, not agent wire compatibility.

## Engineering synthesis
Keep local agent lifecycle independent of scheduling policy and wire protocol. Heartbeat loss is an imperfect failure detector. Resource and slot accounting require atomicity. Epoch/fencing and attempt identity prevent stale updates but cannot reverse external side effects.
No runtime benchmarks or production integration are claimed.
