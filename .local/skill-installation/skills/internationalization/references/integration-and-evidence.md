# Product Integration Contract and Evidence Separation

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

**Inputs required from a real product:** supported locale list, translation catalog source, route/form and persisted preference requirements, approved UI states, timestamp types, timezone preference, compiler/browser versions, authorization and data scoping, product project workflow `REQ`/`AC`, CI command and test environment. A fictitious locale file cannot substitute for any of these.

**Outputs:** explicit locale negotiation table; catalog validation report; language/dir maps; temporal formatting contract; native review checklist; visual regression and E2E run logs; per-AC project workflow verdict; audit trail including source revision and environment versions.

**Provenance levels:**

1. **Source review**: evidence from specification or maintainer documentation; says nothing about host behavior.
2. **Synthetic TypeScript model**: parsing/logic contracts exercised locally; not the installed Vue i18n runtime.
3. **Synthetic Chromium UI**: real engine observations on a fixture; not real navigation/permissions/layout or certified translation.
4. **Mutation detection**: local oracles catch selected injected faults, not all plausible defects.
5. **project workflow structural validation**: checks bridge and file path; not feature acceptance.
6. **Real integration and native review**: only this can support host-product acceptance for the specified release scope.

In project workflow evidence use `NÃO VERIFICADO` for unexecuted product scenarios; do not convert a fixture pass into `APROVADO` for the actual application. Skip only when justified by the approved criterion; do not mask missing dependencies as green.

**No duplicate authority:** the application owns the language support and release policy. This skill only prescribes methods for implementing and testing the decided behavior.
