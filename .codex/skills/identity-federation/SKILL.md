---
name: identity-federation
description: Implement or review federated authentication, OIDC/OAuth flows, session lifecycle and identity-provider interoperability. Skip application RBAC policy and generic security reviews without federation.
---

## Acionamento de scripts no Ec

Neste checkout, esta regra prevalece sobre recomendações de execução desta skill e
suas referências. Scripts de verificação só podem ser acionados após pedido explícito
de commit. Pergunte exatamente: “Deseja executar o script automate.sh (que roda todos os outros scripts)?”
Ofereça somente `sim` e `não`, sem resposta automática; aguarde a escolha.
Com `não`, prossiga normalmente com os commits solicitados, sem executar scripts.
Com `sim`, execute da raiz `bash scripts/automate.sh run`, sem filtros, e só crie
os commits após todos os scripts da suíte terminarem sem erros. Falha, interrupção,
ferramenta ausente ou execução incompleta bloqueiam os commits nessa opção.
Não execute scripts avulsos nem os acione automaticamente durante implementação,
revisão ou encerramento. Inspeção manual continua permitida; checks não executados
permanecem explicitamente não verificados. Detalhes: `docs/development/task-verification.md`
(caminho relativo à raiz Ec). Em outros projetos, siga a política do consumidor.

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

Complete the requested change or diagnosis and the checks that support its claims. Report the
affected contract, observed results, actual versions/commands and material gaps. Keep structural
review, simulated cases, real integration and production evidence distinct. Imported research and
execution records are historical context, not results of the current task.

This folder works without SDD or other skills. Consult an available specialist only for a concrete
unresolved question; its absence is a gap only when essential to acceptance. Use the project's
existing record for a bounded change. This method grants no external access, publication or
destructive-action authority.
