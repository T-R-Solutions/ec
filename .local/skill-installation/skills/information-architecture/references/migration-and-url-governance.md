# Canonical destinations, URLs and IA migration

## Stable identity vs changing presentation

A canonical destination must remain resolvable under the documented link lifecycle. Record `canonical_id`, `route`, `display_label`, `scope`, `parent`, `version/revision` where relevant, `redirect_aliases` and owner. Names and labels can be localized and edited independently of IDs. A link may require a durable resource ID instead of a user-friendly slug; choose based on product-specific URL policy.

## Migration procedure

1. Snapshot the existing IA and all known inbound links: menus, emails, notifications, browser bookmarks, docs, APIs, audit records.
2. Describe the proposed parent/label/section movements and identity preservation rules. Assess permissions and search impact before publishing changes.
3. Define explicit redirect/migration mapping for renamed/moved routes. Check one-to-one mapping, loops, collisions, deleted resources, access rights, and whether historical links should remain valid.
4. Test direct loading and back/forward for representative routes, including permission changes and deleted resources. The correct user-visible state for a forbidden resource depends on security policy and must not leak existence unintentionally.
5. Observe real post-change routing and task results, with a rollback path. A fixture demonstrating successful redirects does not prove production history compatibility.

## Test matrix

| Scenario | Expected contract (to be specified by product) |
|---|---|
| Label changes, ID remains | New label; stable canonical identity |
| Project/workspace move | New hierarchy path; redirects or explicit break with notification |
| Old direct link | Deterministic resolution or stable unavailable state |
| Alias collision | Build/review rejects ambiguous mapping |
| Deleted parent | Children reparented explicitly or made unavailable safely |
| Scope/ACL changed | No protected metadata exposed through old URL/redirect |
| Back/forward | Reconstructs intended current item and search/filter state |
| Search suggestion | Points to canonical authorized destination, not obsolete alias |

**Boundary:** this skill defines the *UX and IA contract*. Router implementation belongs to `frontend-routing`, API aliases to `http-api-contracts`, access control to the backend/security domain, and YAML persistence to the consuming product's authority.

Sources: [USWDS Breadcrumb](https://designsystem.digital.gov/components/breadcrumb/), [WAI navigation design](https://www.w3.org/WAI/curricula/designer-modules/navigation-design/).
