# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Ec references below are conditional consumer examples. They impose no Ec documents, YAML format,
stack or product policy on a generic task.

## Workflow: inputs → decisions → outputs

### 1. Identify scope, inputs and invariants

Collect exact source and target locales (BCP 47), locale priority and fallback requirements, who
provides translations, formatting preferences, time-zone choice and trust source, whether values are
**instants**, **civil dates** or **elapsed durations**, browser/runtime ICU/tzdb/CLDR versions,
affected pages, release gates, and acceptance criteria. If unknown, mark `SPEC GAP`; do not choose
locales, currencies, translations, or time-zone defaults on behalf of the product.

Record required invariants: same IDs/values across locales; saved operation outcomes never change
when only locale changes; stable canonical identifiers; no dropped or reinterpreted form contents;
no old locale result overwriting a newer selection; user-provided strings cannot become executable
markup. See [locale negotiation bcp47](locale-negotiation-bcp47.md) and [resource contracts and
extraction](resource-contracts-and-extraction.md).

### 2. Design an explicit locale and message contract

Use syntactically valid BCP 47 tags and an explicit supported allowlist. Choose a well-defined
matching scheme (RFC 4647 lookup/filtering as applicable); do not equate `pt-PT` with `pt-BR` by
language alone without a documented fallback. Separate preferred locale from server-trusted language
for security decisions. Define translation key owner, fallback visibility, placeholder/typing
validation, plural/ordinal/gender/context handling, and missing-key behavior. Never concatenate
fragments that translators need to reorder. See [plural messageformat and
context](plural-messageformat-and-context.md).

### 3. Preserve data semantics across presentation

- **Instant**: store/transmit an absolute instant or explicit-offset timestamp, render using a named
  IANA zone and `Intl.DateTimeFormat` (or a reviewed equivalent). Treat tzdb updates as a real
  dependency. For time-zone conversion of *wall-clock input* across DST overlaps/gaps, require a
  disambiguation policy and tested library; do not silently guess.
- **Date-only** (`YYYY-MM-DD`): preserve a calendar date; do **not** parse it as an ordinary local
  instant and shift its day. Keep calendar semantics explicit.
- **Elapsed duration**: represent an elapsed numeric duration, not a wall-clock time of day; state
  rounding and unit policies.
- **Numbers/currency**: use `Intl.NumberFormat` and explicit ISO currency/unit; formatting does not
  perform currency conversion, nor does it supply a locale-specific inverse parser.
- **Plural**: use `Intl.PluralRules`/localization library with correct categories for each locale,
  including required `other` and placeholders.
- **Sorting**: `Intl.Collator` is for human-facing ordering; never use it to decide permission,
  hashes, canonical keys, or serialization order.

See [date time and time zones](date-time-and-time-zones.md), [numeric currency unit
duration](numeric-currency-unit-duration.md) and [sort search
segmentation](sort-search-segmentation.md).

### 4. Make script and UI context explicit

Update document `lang` and base `dir` together when language changes; annotate nested
foreign-language content. Use `<bdi>` or `dir="auto"` to isolate unknown-direction text. Use CSS
logical properties and verify direction-sensitive icons individually; **RTL is not a universal
mirror operation**. Test IME, Arabic/Hebrew, numerals, monospaced technical IDs, and grapheme
clusters. Avoid `v-html` with translations/untrusted parameters; interpolate as text/components. See
[bidi rtl and input](bidi-rtl-and-input.md), [translation workflow and
security](translation-workflow-and-security.md) and [vue integration](vue-integration.md).

### 5. Implement safe switching and fallback

Treat a locale switch as a concurrent request: load the complete permitted locale resource, validate
it, apply it atomically, and reject stale responses or logout-invalidated results. Preserve data,
focus and route scope where the host requirements demand it. Do not claim translation fallback is
complete localization. Changes to preferred locale and time-zone settings require confirmed
persistence if persisted. See [vue integration](vue-integration.md).

### 6. Verify behavior with differentiated evidence

Run static catalog checks; positive/negative locale resolution; wrong/missing placeholders; plural
`0/1/2/fractional` by sample locale; dates at midnight and DST transitions; valid/invalid zones;
numbers with distinct digits and grouping; pseudo-localization and long labels at narrow viewports;
mixed bidi with dynamic IDs; keyboard and name-language annotations; race and state-preservation
tests; fallback visibility and recovery. Use **actual browser and actual installed locale data** for
observed results and record versions. Then perform native-language review and product integration
tests. A synthetic reference suite cannot certify translations. See [testing matrix and
oracles](testing-matrix-and-oracles.md) and [integration and evidence](integration-and-evidence.md).

### 7. Deliver and hand off

Provide: locale/tag and matching decision; translation resource/schema map; data-vs-presentation
contract; i18n threat/RTL/fallback analysis; mapping from requirements to tests; commands and
observed outputs with environment versions; known limitations; and explicit project workflow
criterion statuses. Report a recommended fix separately from a measured result. A structural project
workflow bridge does not prove agent selection or product behavior.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Right-to-Left, Mixed-Script IDs and Input](bidi-rtl-and-input.md): bidi rtl and input.
- [Instants, Civil Dates, Time Zones and DST](date-time-and-time-zones.md): date time and time zones.
- [Product Integration Contract and Evidence Separation](integration-and-evidence.md): integration and evidence.
- [BCP 47 Negotiation and Fallback](locale-negotiation-bcp47.md): locale negotiation bcp47.
- [Numbers, Currency, Units and Elapsed Durations](numeric-currency-unit-duration.md): numeric currency unit duration.
- [Grammar, Plurals, Selects and Context](plural-messageformat-and-context.md): plural messageformat and context.
- [UI Catalog and Source-String Contract](resource-contracts-and-extraction.md): resource contracts and extraction.
- [Routing and Agent Selection Oracles](routing-and-test-oracles.md): routing and test oracles.
- [Peer-Reviewed Evidence and Limits](scientific-evidence-and-limits.md): source provenance or a disputed recommendation.
- [Collation, Search, Unicode Graphemes and Stable Keys](sort-search-segmentation.md): sort search segmentation.
- [Primary Sources, Status and Technical Decisions](sources-and-decisions.md): source provenance or a disputed recommendation.
- [I18n Verification Matrix and Executable Oracles](testing-matrix-and-oracles.md): testing matrix and oracles.
- [Pseudolocalization, Expansion, Wrapping and Non-Latin Text](text-length-pseudolocalization-and-layout.md): text length pseudolocalization and layout.
- [Translation Workflow and Security Boundaries](translation-workflow-and-security.md): translation workflow and security.
- [Vue 3 / TypeScript Integration — Optional Host Pattern](vue-integration.md): vue integration.
