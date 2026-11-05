# Resource Identity and HTTP Method Semantics

**RFC 9110** describes resource identifiers, methods, safe and idempotent properties and response status meanings. Distinguish target resource (`/resources/{id}`), related collection, and server-defined operation/status resource. Do not encode an unsafe action in a safe `GET` query argument; crawlers and caches may perform GET automatically. Verify `HEAD` and `OPTIONS` only if the API actually exposes/documented support for them.

| Method | Contract review | Caveat |
|---|---|---|
| GET | Safe read; representation, cache and validator behavior | May produce operational logging; must not mean delete or execute |
| POST | Create/command/process | Repetition can create duplicates unless application contract establishes replay safety |
| PUT | Replace designated resource | Defined as idempotent in HTTP, not proof of DB atomicity |
| PATCH | Document patch media and semantics | Not automatically idempotent; no hidden default semantics |
| DELETE | Target deletion request | Method is idempotent, but response/status can change on repeat |

Define status matrix per operation: 200/201/202/204; 304 where conditional; 400, 401, 403, 404, 405 + `Allow`, 406, 409, 412, 413, 415, 422, 428, 429 and 5xx as applicable. Do not return `200 OK` for an operation that is only queued, or `201 Created` without an actually created resource. For resource absence and access-denied, choose an intentional policy rather than leaking cross-scope identifiers. Review method/method override handling at proxies. [RFC 9110](https://www.rfc-editor.org/rfc/rfc9110.html).
