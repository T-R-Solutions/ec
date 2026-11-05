# ID tokens, access tokens and API authorization

**Three different things:** ID Token proves authentication to a specific OIDC relying party; access token authorizes access to a resource server with its own expected audience/scope; local session grants a browser a previously authenticated RP context under local policy. None should be substituted for another.

API access tokens may be JWTs, opaque handles, proof-of-possession or other formats. When JWT access tokens are used, apply their selected profile (e.g. RFC 9068 where adopted) and resource server audience/scope checks; otherwise query trusted token introspection (RFC 7662) if that mode is supported. Do not accept an ID Token as a resource-server access token simply because its signature verifies.

Consider `aud`, `scope`, `client_id` and tenant scoping as independent gates. Never authorize resource administration based solely on browser route guards or on a token claim without validated policy. Include 401 vs 403 behavior according to the resource contract; keep error diagnostics free of credentials. Exercise wrong API audience, expired/revoked opaque token, missing scope and replayed proof as distinct failures.
