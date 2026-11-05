# Source Register, Versions and Engineering Decisions

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

## Primary normative material (verified 2026-11-01)

| ID | Source | Status and specific use |
|---|---|---|
| H-01 | [RFC 9110 — HTTP Semantics](https://www.rfc-editor.org/rfc/rfc9110.html) | IETF Standards Track; methods, status, validators, conditional requests, content negotiation, Retry-After |
| H-02 | [RFC 9111 — HTTP Caching](https://www.rfc-editor.org/rfc/rfc9111.html) | IETF Standards Track; private/shared caching, no-store, validation |
| H-03 | [RFC 9457 — Problem Details](https://www.rfc-editor.org/rfc/rfc9457.html) | IETF Standards Track; supersedes RFC 7807; `application/problem+json` |
| H-04 | [RFC 6585 — Additional HTTP Status Codes](https://www.rfc-editor.org/rfc/rfc6585.html) | Standards Track; optional 428 precondition and 429 rate limiting |
| H-05 | [RFC 8288 — Web Linking](https://www.rfc-editor.org/rfc/rfc8288.html) | Standards Track; `Link` relations, not a mandated pagination syntax |
| H-06 | [RFC 9745 — Deprecation Header](https://www.rfc-editor.org/rfc/rfc9745.html) | Standards Track 2025; `Deprecation` structured date; deprecation is not shutdown |
| H-07 | [RFC 8594 — Sunset Header](https://www.rfc-editor.org/rfc/rfc8594.html) | Informational; service retirement hint, not a guarantee |
| H-08 | [RFC 3986 — URI](https://www.rfc-editor.org/rfc/rfc3986.html) | Standards Track; URI structure and reference resolution |
| H-09 | [RFC 3339 — Date and Time](https://www.rfc-editor.org/rfc/rfc3339.html) | Standards Track; timestamps with offsets; date contract must distinguish date-only |
| H-10 | [RFC 9651 — Structured Field Values](https://www.rfc-editor.org/rfc/rfc9651.html) | Standards Track; supersedes RFC 8941; relevant to Deprecation and I-D Idempotency-Key syntax |
| H-11 | [OpenAPI 3.2.1](https://spec.openapis.org/oas/v3.2.1.html) | OpenAPI Initiative spec; published 2026-09-10; description, not tested implementation |
| H-12 | [OpenAPI versions](https://spec.openapis.org/oas/) | Official index; tooling/version selection, especially 3.1 vs 3.2 |
| H-13 | [JSON Schema draft 2020-12](https://json-schema.org/draft/2020-12) | JSON Schema specification; applicable to JSON-compatible payload schemas, not a configuration mandate |
| H-14 | [OWASP REST Security Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/REST_Security_Cheat_Sheet.html) | Practice guidance; server-side authorization, content type and error safety |
| H-15 | [OWASP API Security Top 10 (2023)](https://owasp.org/API-Security/editions/2023/en/0x11-t10/) | Practice guidance; object-level and function-level access checks |
| H-16 | [HTTPAPI Idempotency-Key Internet-Draft -08](https://datatracker.ietf.org/doc/html/draft-ietf-httpapi-idempotency-key-header-08) | WORK IN PROGRESS, published 2026-10-22; NOT an RFC or finalized interoperability guarantee |

## Scientific research (peer-reviewed; scope is descriptive/empirical)

| ID | Peer-reviewed publication | DOI | What it supports; does **not** prove |
|---|---|---|---|
| S-01 | Serbout & Pautasso, *How Are Web APIs Versioned in Practice? A Large-Scale Empirical Study*, Journal of Web Engineering (2024) | [10.13052/jwe1540-9589.2341](https://doi.org/10.13052/jwe1540-9589.2341) | Diversity of versioning conventions in a large specification corpus; no universal winner or Ec policy |
| S-02 | Serbout & Pautasso, *How Many Web APIs Evolve Following Semantic Versioning?*, ICWE (2024) | [10.1007/978-3-031-62362-2_25](https://doi.org/10.1007/978-3-031-62362-2_25) | SemVer labels often disagree with detected breaking changes; does not establish a foolproof static diff |
| S-03 | Di Lauro, Serbout & Pautasso, *A Large-scale Empirical Assessment of Web API Size Evolution*, Journal of Web Engineering (2022) | [10.13052/jwe1540-9589.2167](https://doi.org/10.13052/jwe1540-9589.2167) | Growth and removal trends in studied descriptions; not a contract guarantee |
| S-04 | *An Empirical Study on the Reliability of the Web API Document*, APSEC (2018) | [10.1109/APSEC.2018.00103](https://doi.org/10.1109/APSEC.2018.00103) | Documentation and actual response mismatches in its sample; not a measured rate for Ec |
| S-05 | *Web API growing pains: Loosely coupled yet strongly tied*, Journal of Systems and Software (2015) | [10.1016/j.jss.2014.10.014](https://doi.org/10.1016/j.jss.2014.10.014) | Client migration burdens observed in its interviews/case studies; not universal prediction |
| S-06 | *How Composable is the Web? An Empirical Study on OpenAPI Data model Compatibility*, ICWS (2022) | [10.1109/ICWS55610.2022.00068](https://doi.org/10.1109/ICWS55610.2022.00068) | Static data-model matches are narrower with stricter matching; not behavioral equivalence |

## Authority boundaries and version caveats

Standards are evidence for protocol semantics; practitioner guidance proposes controls; papers provide observations under their sample/method; synthetic fixtures demonstrate only fixture behavior. Do not claim version-specific runtime capability without running the selected tool. RFC 9457 supersedes RFC 7807; RFC 9110 supersedes older semantics split across RFC 7231/7232. OpenAPI 3.2.1 is available, but the bundled sample uses **3.1.0** to avoid assuming every local tool understands 3.2. The `Idempotency-Key` draft is explicitly unfinished. RFC 9745's `Deprecation` date is an HTTP structured field, whereas RFC 8594's `Sunset` date is HTTP-date; they must not be conflated.
