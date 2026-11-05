# Evidence reporting and gate semantics

> Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

For every test, capture: requirement, case ID, code revision, file/fixture hash, test layer, browser/dependency versions, baseline, exact command, expected/observed response, retries, stable log or trace and verdict (`PASS`, `FAIL`, `BLOCKED`, `NOT RUN`, `INCONCLUSIVE`, `FLAKY`). Do not infer a pass from the existence of a test file or assertion.

**Fail closed:** `missing Vue runtime` => `NOT EXECUTED`; `browser fixture passes` => evidence for synthetic browser target only; `API mocked` => no backend auth or persistence evidence; `test selector found` => not a full UX/accessibility audit. A pending deployment gate must remain pending even if unit and browser-mock checks pass.

Preserve immutable evidence inputs and attach source revision; redact credentials, private payloads, tokens and HAR secrets before distribution. Prefer machine-readable summaries with human-readable limitations, independent from the target product's configuration. The skill package's `MANIFEST.json` is *archive metadata*, not a project configuration file.

Research basis: Barr et al., IEEE TSE 2015 DOI 10.1109/TSE.2014.2372785. No algorithm can infer an unknown intended behavior reliably without an oracle.
