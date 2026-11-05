# Refresh token lifecycle and replay response

Token issuance is policy-dependent; refresh tokens are not universally necessary. RFC 9700 says public clients issued refresh tokens need sender-constrained tokens or refresh token rotation and replay detection. Confidential clients must bind refresh tokens to the issued client. Avoid putting long-lived refresh tokens in localStorage or URLs.

**Rotation contract:** identify a token family; atomically mark the old token spent and issue a successor; detect reuse; revoke the whole family under approved policy and require fresh authentication. Specify grace/concurrency behavior explicitly and document any attacker-vs-legitimate-client ambiguity. A single-process in-memory test cannot demonstrate atomicity across multiple API instances or durability after crashes.

**Revocation and logout:** distinguish invalidating an RP session from revoking provider-issued tokens; RFC 7009 defines a revocation endpoint, while RFC 7662 covers token introspection. Resource-server cache freshness and revocation propagation may cause delayed effects. If sender constraints such as DPoP are selected, bind them using RFC 9449 and check replay proof semantics.

**Oracles:** one refresh succeeds, stale predecessor reuse triggers family handling, a valid successor fails after family revocation, logout disallows new refresh and session access, concurrent requests observe the documented winner/loser result, and data leaks never print refresh-token values.
