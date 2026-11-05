# Skill Routing and Verification Oracles

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

The identifiers in this appendix are **planned test cases and synthetic examples**, not approved Ec project acceptance criteria. Activation should be selective, to avoid long prompt contexts and responsibility duplication.

## A. Activation / non-activation scenarios

| ID | User/task prompt | Select this skill? | Reason |
|---|---|---|---|
| SEL-01 | "Why may a project contributor deploy to production?" | Yes | Effective access explanation |
| SEL-02 | "Implement a backend authorization `Check` contract for pipeline edit." | Yes | Backend policy decision |
| SEL-03 | "Prevent a team leader from assigning a role they cannot confer." | Yes | Privilege delegation safety |
| SEL-04 | "Show direct, inherited and group-based rights separately." | Yes | Access provenance |
| SEL-05 | "A project moved; which effective permissions changed?" | Yes | Re-evaluate scope and impact; topology skill secondary |
| SEL-06 | "Detect stale permissions immediately after an admin revocation." | Yes | Authorization freshness |
| SEL-07 | "Implement role hierarchy constraints and detect cycles." | Yes | Role graph correctness |
| SEL-08 | "Generate permission decision table and property tests." | Yes | Policy verification |
| SEL-09 | "Create the users list and send invites." | No | People/access administration |
| SEL-10 | "Implement OIDC ID token verification and log in." | No | Identity federation |
| SEL-11 | "Move a project under a different parent." | No | Project hierarchy management |
| SEL-12 | "Publish a modified YAML revision with diff." | No | Configuration publication |
| SEL-13 | "Implement parsing errors for YAML keys." | No | Declarative resource schemas |
| SEL-14 | "Design canvas and form synchronization." | No | Semantic configuration projections |
| SEL-15 | "Conduct an organization-wide generic threat model." | No | Application security |
| SEL-16 | "Create a generic Vue navigation component." | No | Vue frontend engineering |
| SEL-17 | "Audit permissions for plugin execution and secret access." | Yes | Policy decision; plugin skill supplies protected operations |
| SEL-18 | "Visualize a permission matrix and stale provenance warnings." | Yes | Permission truth states; accessibility skill complementary |

## B. Synthetic executable reference-model checks

Run the exact model as follows:

```sh
PYTHONPATH=validation python3 -m unittest discover -s validation -p 'test_reference_model.py' -v
```

These cases exercise deliberately invented `reader`, `builder`, `operator`, `reviewer` roles, `root/build/deploy` project scopes, and synthetic `allow` / `deny` rules; **they are not Ec policy decisions**. The class has 100 individually named methods. The list is generated from the tested code, not made up as evidence.

| Test ID | Executable reference-model oracle |
|---|---|
| SYN-001 | `direct reader` |
| SYN-002 | `direct build` |
| SYN-003 | `direct deploy` |
| SYN-004 | `direct role bind` |
| SYN-005 | `unknown action denies` |
| SYN-006 | `bob nested group reader` |
| SYN-007 | `bob nested group builder` |
| SYN-008 | `bob not allowed root` |
| SYN-009 | `bob not allowed sibling` |
| SYN-010 | `bob no role bind` |
| SYN-011 | `direct grant to child` |
| SYN-012 | `direct grant does not inherit upward` |
| SYN-013 | `direct grant inherits downward` |
| SYN-014 | `role hierarchy includes read` |
| SYN-015 | `role hierarchy includes build` |
| SYN-016 | `group binding provenance` |
| SYN-017 | `role inheritance provenance` |
| SYN-018 | `scoped bindings have stable ids` |
| SYN-019 | `duplicate paths do not duplicate authority` |
| SYN-020 | `scope source explicit` |
| SYN-021 | `fixture explicit deny overrides` |
| SYN-022 | `fixture deny keeps evidence for privileged review` |
| SYN-023 | `fixture deny is scope limited` |
| SYN-024 | `fixture wrong context does not grant` |
| SYN-025 | `fixture matching context grants` |
| SYN-026 | `fixture missing context indeterminate` |
| SYN-027 | `fixture missing context rejects execution` |
| SYN-028 | `fixture unrelated context ignored` |
| SYN-029 | `fixture unrelated context may be extra` |
| SYN-030 | `fixture incomplete attribute does not evaluate false` |
| SYN-031 | `unknown principal indeterminate` |
| SYN-032 | `disabled principal denies` |
| SYN-033 | `disabled principal cannot execute` |
| SYN-034 | `unknown scope indeterminate` |
| SYN-035 | `empty action indeterminate` |
| SYN-036 | `scope cycle indeterminate` |
| SYN-037 | `scope missing parent indeterminate` |
| SYN-038 | `scope cross tenant parent indeterminate` |
| SYN-039 | `role cycle indeterminate` |
| SYN-040 | `role missing parent indeterminate` |
| SYN-041 | `group cycle indeterminate` |
| SYN-042 | `group missing member indeterminate` |
| SYN-043 | `group cross tenant nested indeterminate` |
| SYN-044 | `missing group tenant indeterminate` |
| SYN-045 | `unknown binding role indeterminate` |
| SYN-046 | `unknown binding scope indeterminate` |
| SYN-047 | `duplicate binding id indeterminate` |
| SYN-048 | `unknown group binding indeterminate` |
| SYN-049 | `cross tenant group binding indeterminate` |
| SYN-050 | `incoherent snapshot indeterminate` |
| SYN-051 | `not effective policy indeterminate` |
| SYN-052 | `missing snapshot id indeterminate` |
| SYN-053 | `equal minimum allows` |
| SYN-054 | `newer minimum policy indeterminate` |
| SYN-055 | `newer minimum topology indeterminate` |
| SYN-056 | `newer minimum membership indeterminate` |
| SYN-057 | `newer minimum identity indeterminate` |
| SYN-058 | `newer minimum attrs indeterminate` |
| SYN-059 | `malformed minimum indeterminate` |
| SYN-060 | `revocation changes effective access` |
| SYN-061 | `revocation old snapshot cannot satisfy newer minimum` |
| SYN-062 | `group removal requires revision and changes result` |
| SYN-063 | `identity deactivation changes result` |
| SYN-064 | `project move invalidates inherited scope` |
| SYN-065 | `cache uses version as part of key` |
| SYN-066 | `cache uses context as part of key` |
| SYN-067 | `cache rejects too old minimum` |
| SYN-068 | `delegator may grant reader on root` |
| SYN-069 | `delegator may grant builder on root` |
| SYN-070 | `delegator may grant operator on root` |
| SYN-071 | `no self delegation` |
| SYN-072 | `non admin cannot bind role` |
| SYN-073 | `cannot delegate action actor lacks` |
| SYN-074 | `cannot delegate deny overridden action` |
| SYN-075 | `unknown target produces indeterminate` |
| SYN-076 | `inactive target produces indeterminate` |
| SYN-077 | `unknown role produces indeterminate` |
| SYN-078 | `delegation after revocation fails` |
| SYN-079 | `delegation rejects stale policy minimum` |
| SYN-080 | `self explanation hides private paths` |
| SYN-081 | `privileged inspection provenance` |
| SYN-082 | `unprivileged third party redaction` |
| SYN-083 | `disabled third party redaction` |
| SYN-084 | `self explanation contains decision` |
| SYN-085 | `self explanation not expose group` |
| SYN-086 | `no privileged explanation for unknown` |
| SYN-087 | `backend decision has version tuple` |
| SYN-088 | `backend decision has snapshot id` |
| SYN-089 | `denied no grants has no sensitive evidence` |
| SYN-090 | `preview reports new grant` |
| SYN-091 | `preview is read only` |
| SYN-092 | `preview can show revoked access` |
| SYN-093 | `check matches list scopes` |
| SYN-094 | `check matches list after revoke` |
| SYN-095 | `check list with minimum staleness safe` |
| SYN-096 | `unknown object not listed` |
| SYN-097 | `cache repeat is idempotent` |
| SYN-098 | `property allow not cross tenant without binding` |
| SYN-099 | `property all unknown actions denied` |
| SYN-100 | `property deterministic under binding permutation` |

## C. Planned real Ec acceptance/integration scenarios (NOT EXECUTED)

| ID | Required future evidence when the Ec implementation exists |
|---|---|
| EC-01 | Real authoritative YAML permissions match the approved versioned schema and project workflow role vocabulary. |
| EC-02 | Backend authenticates a principal and never trusts a posted role label. |
| EC-03 | Backend middleware checks exact action and object before file/log access. |
| EC-04 | Backend denies an operation not covered by approved policies. |
| EC-05 | Project ancestor policy is applied only where project workflow inheritance permits it. |
| EC-06 | Resource move invalidates old-ancestor grants at required freshness. |
| EC-07 | Team membership change updates effective rights at required freshness. |
| EC-08 | Role hierarchy cases match approved policy without cycles. |
| EC-09 | Unsupported role hierarchy returns an explicit policy validation error. |
| EC-10 | Explicit exception/deny behavior follows approved policy, not framework defaults. |
| EC-11 | Conflict between multiple roles is adjudicated according to project workflow precedence. |
| EC-12 | Separation-of-duty restrictions reject prohibited grants and activations. |
| EC-13 | Delegation cannot grant a role beyond the actor's approved authority. |
| EC-14 | Actor revocation between preview and commit blocks stale grant action. |
| EC-15 | Self-grant and same-actor approval constraints match approved project contract policy. |
| EC-16 | Scope and resource tenant boundaries resist horizontal access attempts. |
| EC-17 | Revocation cannot leave an old cached allow on a sensitive action. |
| EC-18 | Access for new content cannot be checked at a causally older snapshot. |
| EC-19 | Policy projection lag returns a truthful stale/unknown status. |
| EC-20 | Git config publication failure cannot mark an uncommitted candidate effective. |
| EC-21 | Missing critical resource attributes produce safe non-execution and diagnosable error. |
| EC-22 | IdP deprovision blocks authorization according to its trusted lifecycle contract. |
| EC-23 | API check and list/search visible objects use semantically equivalent revisions. |
| EC-24 | Pagination, global search and artifact listing do not leak resource identifiers. |
| EC-25 | Permission matrix distinguishes assigned from computed effective rights. |
| EC-26 | Admin-only provenance includes safe scope/role/group path and input revision. |
| EC-27 | Ordinary users cannot inspect private group memberships via explanation API. |
| EC-28 | Admin UI remains keyboard-operable and screen-reader states are announced. |
| EC-29 | Logs correlate authorization decisions without identity tokens or secrets. |
| EC-30 | Publication/reconciliation and projected authorization state agree after restart. |
| EC-31 | Unknown role/reference errors are not transformed into permissive fallbacks. |
| EC-32 | Relevant Postgres snapshot/lock/serialization failures recheck authorization. |
| EC-33 | Agent/worker and API actions cannot bypass the same protected-resource enforcement. |
| EC-34 | Permission-result cache has a measured correctness/freshness envelope. |
| EC-35 | Load tests include deep groups/roles/projects and bounded traversal work. |
| EC-36 | Automated project acceptance tests distinguish real Ec results from synthetic oracles. |

## D. Property oracles

- **No allow without a verified authorization path** at an acceptable policy snapshot.
- **Scope isolation:** an inherited grant cannot jump to another tenant or unrelated branch.
- **Freshness bound:** check at revision older than the caller-required revocation watermark is `indeterminate`, never an allow.
- **Determinism:** reordering independent bindings cannot alter a decision under the same specified combination policy.
- **Safety on invalid data:** cycles, missing trusted identifiers, unknown schema references and incomplete authorization state do not authorize execution.
- **Equivalence:** `Check(P,A,R,snapshot)` equals membership in `ListAccessible(P,A,snapshot)`; the same holds for the defined pagination/filters.
- **Privacy:** normal self-access explanation does not reveal private group member lists; privileged explanation includes bounded provenance and versions.
- **No privilege conferral by edit access alone:** grant authority is checked separately on the actor and proposed target role/capability, using approved project contract semantics.

The reference model includes a restrictive synthetic delegation policy and deny-overrides semantics **for teaching/testing only**. Do not prescribe these as Ec product rules or replace approved project contract decisions with this file.
