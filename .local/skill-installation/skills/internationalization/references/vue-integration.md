# Vue 3 / TypeScript Integration — Optional Host Pattern

Vue I18n supports `createI18n`, the Composition API `useI18n`, fallback and plural messages. Choose the installed major version and explicit project mode; do not mix Legacy and Composition APIs without a migration plan. This portable skill does **not** require Vue when used elsewhere.

**Flow:** determine supported locale and initial user preference → load approved catalog (bounded, authenticated if needed) → validate catalog/key contract → atomically update i18n locale and `<html lang/dir>` → preserve form state and route → verify focus and screen-reader status → persist only after server confirmation if the setting is persisted.

**Concurrency:** tag every locale load with a generation ID or abort policy. On a newer selection, ignore an earlier result; on logout/session change, clear pending loads and sensitive caches. A loader failure should preserve the last committed language rather than show partially translated mixed components, except where approved fallback explicitly permits that state.

**Example (not a compiled app):**
```ts
import { useI18n } from 'vue-i18n'
const { locale, t, n, d } = useI18n({ useScope: 'global' })
// After validated resource load and stale-result guard:
locale.value = requestedSupportedLocale
// The host's UI orchestration updates root lang/dir in the same commit.
```

**Do not:** run user text through `v-html`, localize YAML contract keys, treat UI language as authorization, or override a user's time-zone preference because the UI locale changed. Consult `vue3-engineering` for lifecycle/composable details, `frontend-state-and-cache` for general cache behavior and `frontend-testing` for full application E2E.

Docs: https://vue-i18n.intlify.dev/guide/essentials/started ; https://vue-i18n.intlify.dev/guide/essentials/fallback ; https://vue-i18n.intlify.dev/guide/essentials/syntax .
