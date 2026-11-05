# Activation routing, negative/boundary selection and integration cases

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

**Skill identity:** `vue3-engineering`. An indexing agent should load the frontmatter/short description first and read the body only when selected. This skill is not invoked just because an application uses Vue; the task must require Vue-specific implementation/review.

## Selection corpus (not measured LLM agent results)

| ID | User task | Select primary? | Owner / reason |
|---|---|---|---|
| S01 | Correct a Vue 3 watcher that displays stale API responses after prop change | **YES** | watcher invalidation, component lifecycle |
| S02 | Implement typed `defineProps`/`defineEmits` on an SFC | **YES** | Vue compiler macros and public component contract |
| S03 | Convert UI behavior to a composable with unmount cleanup | **YES** | composition and effect ownership |
| S04 | Investigate why computed value never refreshes | **YES** | reactive dependency graph |
| S05 | Fix parent/child `defineModel` default de-synchronization | **YES** | Vue v-model contract |
| S06 | Integrate async component and trace SSR setup leakage | **YES** | Vue lifecycle / SSR behavior; specialist architecture may assist |
| S07 | Configure TypeScript conditional types in a library with no `.vue` | NO | `typescript-engineering` |
| S08 | Create Ec design tokens for dark theme | NO | `design-system` |
| S09 | Decide CSS container breakpoints for a two-column layout | NO | `css-layout-engineering` |
| S10 | Define global HTTP cache eviction and invalidation policy | NO | `frontend-state-and-cache` |
| S11 | Add login OIDC authorization and refresh tokens | NO | `identity-federation` |
| S12 | Implement Vue Router nested routes and guards only | NO | `frontend-routing`; backend authorizes |
| S13 | Specify the YAML canonical schema for an Ec pipeline | NO | `ec-declarative-resource-schemas` |
| S14 | Build the E2E test framework/test runner configuration for all pages | NO | `frontend-testing` |
| S15 | Review keyboard and screen-reader conformance only | NO | `web-accessibility` |
| S16 | Define CI/CD DAG execution ordering | NO | `cicd-pipeline-graph` |
| S17 | Review template reactivity bug in the Ec DAG editor | **YES** (bounded) | Vue-specific behavior; `ec-pipeline-authoring-workspace` owns business model |
| S18 | Implement a complete Vue form and business validations | **CONDITIONAL** | Vue SFC effects are secondary; `frontend-form-engineering` owns form semantics |
| S19 | Improve massive Vue log table performance by measuring renders | **YES** (Vue method) | `performance-engineering` if independent profiling methodology needed |
| S20 | Render backend-authoritative job status in existing Vue component | **YES** (UI) | product status meaning belongs to feature contract / Ec run skill |

**Corpus is a routing specification, not an agent evaluation result.** To evaluate actual selection, replay paraphrases including confusing references to Vue, log chosen skills and measure false positives/negatives; retain full real transcripts in a separate authorized evaluation.

## Acceptance scenarios for a consuming Vue application (plan, not executed)

| ID | Precondition | Action | Required observation |
|---|---|---|---|
| A01 | Vue component with typed props/events | Typecheck with installed `vue-tsc` | Correct payloads accepted, mismatches rejected |
| A02 | Parent-controlled field | Change child model | Parent/child updated, no silent default split |
| A03 | Computed/props update | Mutate source | Exactly expected visible state after `nextTick` |
| A04 | Watcher A pending | Change source to B; resolve B then A | Only B commits, no stale error |
| A05 | Component mounted with listener | Unmount | Listener removed, pending result ignored |
| A06 | Async operation fails | Resolve/reject latest promise | Visible error and retry affordance from actual state |
| A07 | Deferred SSR request for user X | Render user Y concurrently | No per-user state leakage |
| A08 | Invalid external HTML/URL | Render user-supplied data | No executable template/unsafe DOM URL accepted |
| A09 | Long list representative | Profile before and after optimization | Measured improvement without semantics/focus regression |
| A10 | Same component keyboard-driven | Navigate/activate/submit | Focus order and accessible status checked in real browser |
| A11 | User not authorized at backend | Click action (where permitted) | Backend denies; component shows confirmed denial |
| A12 | Vue app with dev/build setup | Run project typecheck/build/test | Distinct outputs and errors retained |

**No Ec acceptance status:** All above are *future integration tests*. The package's local Node/TS tests cannot satisfy these ACs; current application status is **NÃO VERIFICADO**.

## Rejection conditions

Reject output that silently converts untrusted YAML/transport fields into component state of authority; relies on a no-longer-mounted component; uses a compiler macro in ordinary JavaScript; claims `vite build` typechecked all SFCs; treats an emit as successful backend confirmation; claims WCAG compliance from a screenshot; recommends unmeasured improvements; or reports reference-model tests as Ec application acceptance.
