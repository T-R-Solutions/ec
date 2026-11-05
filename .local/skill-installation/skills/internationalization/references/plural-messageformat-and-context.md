# Grammar, Plurals, Selects and Context

Plural systems are locale-specific and cannot be replaced with `count === 1 ? singular : plural`. `Intl.PluralRules` yields locale-dependent categories; `other` is always necessary. The exact translation strings are an editorial asset that still need native review. Distinguish cardinal (“2 jobs”) from ordinal (“2nd”) and decimal from integer cases.

**Input contract:** message key, locale, count and parameter types, grammatical context, gender/select when needed, literal format, and fallback behavior. Avoid concatenating partial phrases, sentence templates or isolated word fragments that translators cannot reorder. Use named placeholders and rich component interpolation rather than HTML strings where possible.

**Engineering:** choose maintained ICU MessageFormat/Vue I18n syntax when needed; don't invent a parser that fails on nested selects, apostrophes or plural offsets. Validate fallback rules and missing placeholders by branch, and test plural boundary samples for the actual supported locale data. Avoid relying on English grammar for Arabic, Russian or Japanese. The fixture uses `Intl.PluralRules` with a deliberately limited data object to verify the core invariant, **not full MessageFormat compatibility**.

**Test cases:** `en-US`: 0/1/2 and fractions; `ar-EG`: 0/1/2/3/11/100; `ru-RU`: 1/2/5/21 and decimals; `ja-JP`: other; missing `other` fails; named `{count}` missing fails; a translation replacing `{user}` with `{name}` fails static checks.

Sources: https://www.unicode.org/reports/tr35/tr35-numbers.html ; https://vue-i18n.intlify.dev/guide/essentials/pluralization ; https://tc39.es/ecma402/2026/ .
