# Numbers, Currency, Units and Elapsed Durations

Use `Intl.NumberFormat` with an explicit locale and options: grouping, decimal precision, currency and unit. Locale display does not imply currency conversion; keep original numeric amount and currency code. Do not round monetary or policy-critical values without a documented rule. Never parse human display back into authoritative business data without a separately specified locale-aware parser, input grammar and validation.

**Numbering system:** Arabic and other locales may display non-Latin digits. Honor locale/numbering system requirements but keep machine-readable job counts, timestamps, indexes and IDs in typed form for logic. `1,234.5` can mean different things in different locales; avoid `parseFloat(localizedString)`.

**Elapsed duration:** a duration of 90 seconds is not a wall-clock time “00:01:30”. Declare input unit (e.g., ms), rounding policy, precision and display granularity. `Intl.NumberFormat` unit formatting and `Intl.ListFormat` can render unit components; if using `Intl.DurationFormat`, check runtime support and browser matrix rather than assuming. Negative durations and unsafe integers must be handled explicitly.

**Oracles:** same typed amount across locale switching; no accidental unit/currency conversion; very large and fractional numbers; 0/1/2 durations, values below one second, empty/null, NaN/Infinity, precision differences and consistent units/labels. Currency display example with USD is illustrative, not a claim that the host product supports billing.

Sources: https://tc39.es/ecma402/2026/ ; https://www.unicode.org/reports/tr35/tr35-numbers.html .
