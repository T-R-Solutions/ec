# Scientific Evidence and Applicability Limits

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

Scientific sources S-01 to S-06 are fully identified in `sources-and-decisions.md` with DOI and peer-review venue. Their evidence level is observational rather than an interoperability standard.

- **Versioning**: Serbout and Pautasso (2024, JWE) analyzed 603,293 API descriptions from 2015–2023 across four sources. The corpus demonstrates real-world diversity of version schemes. It does not show path versioning or header versioning is always best. [DOI](https://doi.org/10.13052/jwe1540-9589.2341).
- **Semantic versions are imperfect indicators**: Serbout and Pautasso (2024, ICWE) identified discrepancies between versions and breaking-change classification in their corpus. Treat SemVer as a signal and require actual consumer compatibility evidence. [DOI](https://doi.org/10.1007/978-3-031-62362-2_25).
- **API evolution**: Di Lauro et al. (2022, JWE) observed changing API description size over time. There is no general proof that all API evolution is additive. [DOI](https://doi.org/10.13052/jwe1540-9589.2167).
- **Specification vs live behavior**: APSEC (2018) compares documents with API responses and reports discrepancies in the sampled APIs, supporting a separate provider-runtime test. Do not project its percentage onto another service. [DOI](https://doi.org/10.1109/APSEC.2018.00103).
- **Client impact**: JSS (2015) presents case studies and interviews on web API evolvability; dependence on client behavior means a purely syntactic comparison may miss breakage. [DOI](https://doi.org/10.1016/j.jss.2014.10.014).
- **Static schema compatibility**: ICWS (2022) studied OpenAPI request/response composability. Matching schema shapes is not equivalent to matching authorization, runtime behavior or effects. [DOI](https://doi.org/10.1109/ICWS55610.2022.00068).

**Non-extrapolation rule:** No paper measures the Ec API, demonstrates precisely-once remote effects, or compares the performance of the bundled fixture against Rust HTTP implementations. Document study limitations (corpus, selection, metrics, period) before adapting conclusions. Prefer a targeted consumer test over inventing numerical benefit from these publications.
