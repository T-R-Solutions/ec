# Correct Skill Selection Oracles

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

Positive: define a GET/PUT API with ETags (`http-api-contracts`); choose 409/412/428 behavior (`http-api-contracts`); compare OpenAPI versions for existing client risk (`http-api-contracts`); specify `202` operation monitor (`http-api-contracts`); design cursor pagination boundaries (`http-api-contracts`). Each yields an observable wire contract or explicit spec gap.

Negative: execute already-written HTTP test suite only (`api-testing`); implement a SQL lock (`database-engineering`); decide RBAC roles (`domain-modeling` plus security and product owner); style a frontend form (`frontend-form-engineering`); design server-agent lease protocol specifically for Ec (`ec-agent-control-protocol`); implement YAML declarative schema (`ec-declarative-resource-schemas`). These require another primary specialization.

Borderline: “A 200 indicates a build is done while execution is pending” activates to correct HTTP status/contract but does not own build execution. “A consumer loses filter choices on navigation” is primarily frontend routing unless the API query/pagination contract is being changed. “A plugin requests an extra scope” needs product-specific plugin/authorization owners; use HTTP contract skill only if revising permission/error representation. Assess agent selection with observed actions; a written routing table alone does not verify agent behavior.
