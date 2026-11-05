# Translation Workflow and Security Boundaries

Work with localization owners and linguists: extract stable message keys, supply source-language intention and UI context, review localized content in running screens, use release gates for missing keys and native review, and preserve rollback/revision metadata. Machine translation and pseudolocalization are **unreviewed inputs**, not evidence of certified copy.

**Security:** translated or third-party resource files must not become executable code or trusted HTML. In Vue, use text interpolation or safe component slots; follow Vue I18n's explicit HTML message security warnings. Keep a strict remote-bundle origin/version contract and integrity policy. Untrusted `<img>` strings are shown literally in fixtures; never set `innerHTML` with user-provided text. Distinguish sensitive data access/permissions from locale preference.

**Privacy:** don't automatically send secrets, source logs, tokens or user identifiers to public translation APIs. Human review context must be scrubbed of credentials. `lang`/`dir` attributes communicate content semantics; they do not enforce access control.

**Approval evidence:** source and target locale, translated catalog revision, reviewer and date, screenshots of representative states, bug IDs, unresolved terminology/context, HTML/accessibility check results and product release owner decision.

Sources: https://vue-i18n.intlify.dev/guide/essentials/syntax ; https://www.w3.org/International/questions/qa-html-language-declarations ; Kabát (2023), DOI https://doi.org/10.3991/ijim.v17i01.36367 .
