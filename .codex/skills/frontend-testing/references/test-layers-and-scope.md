# Test layers, scope, and ownership

Use the least expensive layer that can actually observe the contract. **Unit tests** verify pure transformations and state machines without DOM. **Mounted SFC** tests verify props, emits, rendering and local integration with actual Vue and a documented environment. **Browser mock** checks user interaction, layout/roles and client transitions but cannot prove real HTTP authorization. **Browser live** checks network and cross-layer behavior against an isolated real backend. **Deployed E2E** checks server fallback, CDN, cookies, headers and real deployment paths.

Do not impose a universal unit-to-E2E proportion. Each requirement gets the layer needed for its risk and oracle. If Vue libraries are absent, do not call a pure TypeScript test a Vue component test. For configuration editors with multiple projections, use the product contract harness to define domain-specific equivalence tests; this skill implements frontend execution of the tests rather than claiming YAML equivalence.

Recommended review: list which layers can *not* establish a fact. A component mock cannot certify an API guarantee, a browser cannot establish correctness of all backend paths, and coverage cannot certify effectiveness. Official Vue guide: https://vuejs.org/guide/scaling-up/testing.html
