# Navigation Guards, Asynchronous Races and Failure Taxonomy

Vue Router 4 guards may return `false` to abort, a route location to redirect, or throw an error. `router.push()` is asynchronous. A navigation may result in `aborted`, `cancelled` or `duplicated`, and a redirection can succeed as a new navigation. Observe the actual resolved result with `isNavigationFailure` and `NavigationFailureType`; do not treat every resolved promise as a successful route change.

```ts
import { isNavigationFailure, NavigationFailureType } from 'vue-router'
const failure = await router.push({ name: 'workspace', params: { workspaceId } })
if (isNavigationFailure(failure, NavigationFailureType.aborted)) {
  // Preserve current context and explain why leaving was prevented.
}
```

Guard declarations must define navigation-in-progress feedback, redirect chain limits, error paths, canceled requests, stale identity/session changes, `beforeRouteUpdate` for reused components and whether unsaved edits block navigation. An async permission check can resolve after a newer navigation or identity switch: avoid leaking the protected component and re-evaluate permission with current identity. Route guards are a user-experience layer, **not server-side authorization**.

Be wary of using `beforeEnter` only on a parent route: it is not necessarily re-run when changing between the same parent's children. Reuse appropriate global/per-component guards and backend checks where needed.

Test paths: allow, abort, redirect, external/invalid redirect, redirect cycle, duplicate, late async answer, identity switch, back/forward under revoked privileges, route load failure, and form dirty guard.

Sources: [Vue Router Navigation Guards](https://router.vuejs.org/guide/advanced/navigation-guards.html), [Navigation Failures](https://router.vuejs.org/guide/advanced/navigation-failures.html), [Route Meta Fields](https://router.vuejs.org/guide/advanced/meta.html).
