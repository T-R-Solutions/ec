# Security Boundary and Plugin Catalog Presentation

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

## Identity, permission and trust separation

A plugin manifest can describe a `publisher`, a `pluginId`, a release version, requested permissions and capability contributions; those are *untrusted assertions* until verified by the appropriate systems. Do not let the manifest self-register privileged namespaces, grant itself authorization, override core resource kinds or call internal host APIs by declaring a field.

| Signal | What it can prove | What it cannot prove |
|---|---|---|
| YAML structural validation | The metadata satisfies a known syntax/schema | Safety, authenticity, version conformance in actual code |
| Version/constraint matching | The stated release versions satisfy agreed constraints | Binary/API behavior and runtime compatibility |
| Catalog capability claim | A manifest declares a supported contribution | The host registered it or will execute it |
| Permission request | The plugin wants a named permission | Permission was approved or granted |
| Reported checksum/signature field | Only a claimed value | Actual verified integrity, trust or provenance |
| Conformance test | Particular observed contract behavior under test conditions | Universal correctness or isolated execution |
| Backend installation/activation receipt | A corresponding operation was observed | That every workflow is healthy, unless separately observed |

**Handoff:** signing, origin verification, artifact trust roots, runtime isolation, privileges, installation/update/disable, lifecycle and emergency quarantine belong to `ec-plugin-isolation-and-lifecycle`. For security review, use `application-security`. This skill only identifies the metadata and test surfaces they must consume.

## Catalog UX method

Use a searchable table/catalog with filterable publisher, plugin identity, version, compatibility diagnostics, permission requests, available extension points, last verification time and observed operational state. Show metadata compatibility and trust/activation as separate columns or fields. A missing observation is `unknown`, not success.

A suitable static diagnosis includes `code`, affected axis, plugin/host revision, expected and observed values, project workflow link and a user-action suggestion. Never display internal policy details to unauthorized users. Report `unresolved` or `unknown` separately from `incompatible`, and do not change an installed or active badge based solely on a newly parsed manifest.

If a plugin contributes a UI panel or configuration screen, it must remain within the approved Ec design tokens, keyboard and responsive interaction patterns, with labels and error explanations. A static mockup does not prove that such a UI or the isolation mechanism exists.

## Misleading examples to reject

- `pluginVersion: 2.0.0` therefore “compatible with Ec 2.0.0”. The axes are unrelated.
- A valid YAML manifest therefore “verified plugin”. YAML validation is not a signature check.
- A permission is listed therefore “granted”. A request is only metadata.
- The manifest declares a host namespace therefore the host must expose it. Host points must be registered independently.
- A previous success with an older binary proves the latest release safe. Revalidate exact bytes and contracts.
- The user changed the plugin in a form therefore “installed”. Desired YAML, reconciled configuration, installation and activation are distinct states.

**Sources:** Ec norms, OWASP CI/CD security cheat sheet, SLSA 1.2, Kubernetes extension contracts and W3C WCAG 2.2. The Ec-specific UI obligations come from the Ec norms rather than being invented by these generic sources.
