# Tests, evidence and acceptance oracles

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill. Ec-specific rules below apply only to an Ec consumer whose current contract establishes them. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

## Oracle schema

For each scenario record:

```text
REQ/AC ID | persona/permissions | initial revision | input/sequence
  expected field/status/focus/DOM/server call | actual observation
  environment/version | fixture/commit | evidence URI/hash | verdict
```

Don't create the feature's requirements in a test harness. Work from `spec.md` and the product's established contracts. A local method test is useful but insufficient for full product acceptance.

## Layer 1 — static and model logic

Positive cases: typed normal values; empty/null/zero distinction; optional/conditional/repeater behavior; input field identity; controlled status transitions; deep-copy snapshots; preserved extra/advanced values. Negative cases: invalid ranges; unknown fields; coercion loss; duplicate request; stale response; error key mapping; fake ACK. Use deterministic enumerated traces or property-based testing; record seeds and shrinking if randomized.

## Layer 2 — browser and accessibility mechanics

Real-browser DOM tests should check associated labels, name/role/value, fieldsets, keyboard navigation, `aria-describedby`, `aria-invalid`, inline error and summary, focus restoration, native constraint checks, conditional visibility, 320px reflow, and untrusted server message escaping. A passing browser test does not prove a screen reader will announce everything correctly, nor that users will successfully correct their input. Test those separately.

## Layer 3 — real API/product integration

Requires actual application code, a supported environment, known schema, authorized accounts and backend fixture. Test client/server validation agreement, stale async validation, optimistic concurrency, malformed external payloads, unauthorized action, revision conflict, partial effect, canceled request, 422 field errors vs 403 denied vs 409 conflict (codes only if specified), network outage, recovery after reload, YAML semantic equivalence when relevant and no secret leakage.

Never fabricate a completed end-to-end test from an HTML-only fixture. project workflow convergence evidence must identify tested revision and distinguish product acceptance from model validation.

## Layer 4 — user and assistive technology evaluation

Verify realistic tasks with keyboard and assistive technology, ideally representative users; document platform, browser, screen reader, input method and sample limitations. Scientific literature indicates guideline combinations and error placement can affect performance in certain studies; it does not establish universal usability. Use task completion, correction success, perceived effort and error recurrence as measured dependent variables, not invented thresholds.

## Artifact fixtures included

- `validation/fixtures/form.html`, `form.css`, `form.js`: **synthetic** client with fake server requests and explicit responses; no network.
- `validation/ts/form-state.ts`, `form-state-tests.js`, `tsconfig.json`: TypeScript model checks for pending/dirty/conflict/snapshot behavior.
- `validation/run_browser_tests.py`: Chromium DOM, keyboard, focus, escaping and concurrency assertions.
- `validation/run_typescript_tests.py`: compile and execute model tests plus deterministic state traces.
- `validation/run_mutations.py`: targeted faults, with each baseline oracle first required to pass.
- `validation/run_project workflow_fixture.py`: original project workflow structural validator on an ephemeral fixture, not an Ec project acceptance test.

## Reporting discipline

`PASS` requires an observed result under a stated tool and target. `NOT EXECUTED` must remain distinct from `PASS`. Synthetic fixtures are examples and oracles, not product features. Pending use of real Vue, mobile Safari/Firefox, server API, user studies and assistive technology must be listed explicitly.
