---
name: ec-product-contract-test-harness
description: Build or review an Ec cross-layer test harness for configuration, authorization, revision and execution contracts. Skip generic test strategy and product-policy invention.
---

# Ec Product Contract Test Harness

## Aplicação no Ec

Testar parser YAML real, autorização, revisão imutável e paridade Visual/Formulário/YAML quando implementados. Contratos gRPC/SQLite somente após ADR; não substituir integração real por um segundo produto simulado.

## Scope and inputs

Locate real product contracts, implementation adapters, environment permissions and independent
expected outcomes. Record whether each subject is implemented, simulated or unavailable.

Use the Ec consumer's current product requirements and implementation as authority. Locate them
rather than assuming filenames, schemas, endpoints or deployed features. Examples in this folder are
technical proposals until confirmed there.

## Procedure

1. Map each acceptance criterion to actor, scope, initial state, action, expected outcome,
   prohibited effects and an independent oracle.
2. Use thin adapters to actual parsers, APIs, stores and browser interfaces; do not build a shadow
   authorization or configuration system and call it product integration.
3. Exercise semantic editor round trips, hidden-field preservation, allowed/denied operations, stale
   approvals and revision-bound reconciliation where relevant.
4. Inject bounded, reproducible lifecycle faults, including duplicate/out-of-order events and
   cancellation races; retain cleanup and negative-control evidence.
5. Bind observed results to input versions and actual adapters. Missing adapters or synthetic
   fixtures leave real integration unverified; never certify a product merely from collected logs.

## Load detail when needed

- [Harness Architecture, Independent Oracles, and Adapter
  Contracts](references/harness-architecture-and-adapters.md): consult for harness architecture and
  adapters.
- [Semantic Configuration Vectors and Three-Mode
  Oracles](references/semantic-config-contract-vectors.md): consult for semantic config contract
  vectors.
- [Authorization, Revision and Reconciliation Contract
  Oracles](references/authorization-version-and-reconciliation-oracles.md): consult for
  authorization version and reconciliation oracles.
- [Detailed decisions and failure cases](references/implementation-details.md): consult for a
  complex case, additional constraints or the remaining thematic references; apply only relevant
  steps.

## Completion and limits

Complete the requested change or diagnosis and the checks that support its claims. Report the
affected contract, observed results, actual versions/commands and material gaps. Keep structural
review, simulated cases, real integration and production evidence distinct. Imported research and
execution records are historical context, not results of the current task.

This folder works without SDD or other skills. Consult an available specialist only for a concrete
unresolved question; its absence is a gap only when essential to acceptance. Use the project's
existing record for a bounded change. This method grants no external access, publication or
destructive-action authority.
