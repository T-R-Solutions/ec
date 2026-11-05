# BCP 47 Negotiation and Fallback

Use RFC 5646 for canonical language identifiers and explicitly select a matching algorithm from RFC 4647. A browser language priority is preference data, not an authorization policy. Do not derive country, currency or UTC time zone solely from language.

**Inputs:** requested locale(s), host supported-locales list, explicit default/fallback, browser/library supported locales, optional persisted user selection, optional regional or writing-system variant and acceptance criteria.

**Decision tree:** validate tag (`Intl.getCanonicalLocales`, `Intl.Locale`); check supported exact match; apply explicitly documented lookup/region/parent fallback; if no match, use the product's approved default and show missing-translation status when appropriate. Do not convert `pt-PT` to `pt-BR` automatically unless supported-region rules permit it. Never confuse UI copy locale with user's selected time zone or domain locale.

**Negative oracles:** reject `pt_BR`; reject unsupported default; do not silently choose an unauthorized data scope from a locale; do not accept attacker-supplied language strings as filesystem paths or resource selectors. Do not interpret a Unicode `-u-` extension as proof that its requested numbering system/calendar is implemented.

**Output:** negotiation table with exact/parent/default routes, fallback rationale, UI scope and persistence boundary. Record unsupported preferences without inventing language support.

Standards: https://www.rfc-editor.org/rfc/rfc5646 ; https://www.rfc-editor.org/rfc/rfc4647 ; https://tc39.es/ecma402/2026/ .
