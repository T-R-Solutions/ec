---
name: identity-federation
description: Implement or review federated authentication, OIDC/OAuth flows, session lifecycle and identity-provider interoperability. Skip application RBAC policy and generic security reviews without federation.
---

No Ec, siga `docs/development/task-verification.md` para acionar checks e
`docs/development/local-state.md` para arquivos de trabalho (caminhos desde a
raiz). Em outros consumidores, siga as políticas locais.

# Identity Federation — Security and Interoperability Engineering

## Scope and inputs

Discover supported issuers, client type, redirect registrations, protocol/library versions, session
architecture and account-linking policy. Do not assume an identity provider or token format.

Preserve the consumer's stack and policies. Product-specific examples in references do not establish
requirements for this task.

## Procedure

1. Trace authorization request, callback, token exchange and application session as distinct trust
   boundaries; bind state, nonce and PKCE to the initiating flow where required.
2. Verify issuer, audience, signature, allowed algorithms, lifetime and flow-specific claims with
   maintained libraries; distinguish ID tokens from access tokens.
3. Constrain discovery and key retrieval, including redirects and rotation. Do not trust arbitrary
   issuer URLs or key identifiers from untrusted payloads.
4. Define refresh, revocation, logout and account linking using explicit identity and proof
   requirements. Provider logout and local session invalidation are distinct outcomes.
5. Test callback replay, wrong issuer/audience, stale keys, concurrent refresh, expired sessions and
   unavailable providers. Record real-provider and simulated evidence separately.

## Load detail when needed

- [Authorization code, PKCE and callback
  contract](references/authorization-code-pkce-and-callback.md): consult for authorization code pkce
  and callback.
- [ID Token verification and JWT/JWK pitfalls](references/id-token-validation-and-jose.md): consult
  for id token validation and jose.
- [Multi-issuer, tenant mapping and identity
  linking](references/multi-issuer-and-account-linkage.md): consult for multi issuer and account
  linkage.
- [Detailed decisions and failure cases](references/implementation-details.md): consult for a
  complex case, additional constraints or the remaining thematic references; apply only relevant
  steps.

## Completion and limits

Registre contrato afetado, inputs, método, resultado e lacunas; distinga inspeção,
simulação e integração real. Registros importados não são resultados atuais.

Aplique apenas os passos pertinentes, usando o registro existente do projeto.
A skill funciona sem SDD; consulte auxiliares disponíveis somente para questões
distintas ainda não resolvidas. O método não amplia a autorização da tarefa.
