# Field types, parsing and formatting

## Separate display, transport and semantic values

A user-entered string, the form field's DOM value, the API payload value and the persisted domain value are not always identical. Declare a parser, normalized representation and reversible formatter from the authoritative field contract. Do not guess what should be trimmed, lowercased, coerced or reordered.

- **Numbers:** `input[type=number]` still has DOM string values; distinguish `''`, `NaN`, zero, fractional inputs and locale decimal separators. Vue `.number` uses `parseFloat`, but returns the original string when parsing fails and empty string when empty. Do not assume it guarantees `number` type.
- **Dates/times:** specify absolute date vs instant, timezone and daylight-saving behavior; prefer explicit data models rather than ambiguous locale parsing. A single universal date entry UI is not academically established (Bargas-Avila et al. 2011 found speed/error trade-offs for six tested alternatives).
- **Identifiers:** user-facing labels and URL-safe codes are distinct; never reinterpret a field's identity on a label change.
- **Multi-select/order:** a set of selected values differs semantically from a user-ordered sequence. Keep stable keys for repeatable rows and do not silently reorder.
- **Unicode:** don't reject legitimate international text by restricting all names to ASCII or a simplistic regex; use field-specific rules. Do not normalize Unicode without explicit domain justification.
- **IME composition:** do not apply destructive trimming/parsing or intensive validation during composition events. Test typing with actual IME/assistive tech if it matters.
- **Secrets:** password/token forms require dedicated identity/credential flows and protections; do not persist plaintext into draft configuration, URLs, browser logs or test artifacts.
- **Autofill:** HTML `autocomplete` tokens are meaningful; don't disable autofill indiscriminately. Verify accessibility and privacy implications of the field type.

## Parsing contract example

```ts
type ParseResult<T> =
  | { ok: true; value: T }
  | { ok: false; code: string; message: string };
// Demonstrative only; choose the actual number grammar and ranges from the spec.
function parseWholeDays(input: string): ParseResult<number> {
  if (!/^(?:[1-9]|[12][0-9]|30)$/.test(input))
    return { ok: false, code: 'range', message: 'Enter a whole number from 1 to 30' };
  return { ok: true, value: Number(input) };
}
```

If a requirement permits `0`, leading zeros, decimals or spaces, this example is not appropriate. Frontend validation should mirror server expectations without claiming authority. A form that hides an advanced field should either preserve it or explicitly document a contract-defined deletion.

## Test oracles

Test typed empty values, out-of-range, Unicode, whitespace differences, numeric locale input, date ambiguity, multiple selections, ordering and IME. Include a negative case where a coercion silently changes a value and assert that the adapter preserves meaning or blocks submission with clear feedback.

**Boundary:** Dedicated `internationalization` and `typescript-engineering` skills own cross-cutting locale and type system methods; this file describes how forms consume those contracts.
