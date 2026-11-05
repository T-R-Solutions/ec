# Collation, Search, Unicode Graphemes and Stable Keys

Use `Intl.Collator` for **display** sorting under an explicitly chosen locale and sensitivity; test case/diacritic behavior and numeric labels. Collation of names may differ by locale, ICU version and user expectation. A sorted UI list is not a canonical ordering of IDs and must not redefine security policy or YAML serialized order.

Use `Intl.Segmenter` for grapheme/word/sentence boundaries when supported; JavaScript string `.length` counts UTF-16 code units, **not user-perceived characters**. Tests should include combining marks, emoji ZWJ, surrogate pairs and scripts without spaces. Avoid truncating paths/commits or splitting a multi-code-point grapheme for display.

Localized search may need a separate indexing/tokenization contract; do not assume `toLowerCase` or simple substring search equals natural-language searching for all locales. Search ranking, accent folding and prefix matching are product decisions to be tested with users.

**Oracles:** stable job ID mapping across locales, no lost resources after sorting, no duplicated records, exact count of search results based on the agreed matching contract, and no hidden Unicode changes in canonical resource names.

Source: https://tc39.es/ecma402/2026/ .
