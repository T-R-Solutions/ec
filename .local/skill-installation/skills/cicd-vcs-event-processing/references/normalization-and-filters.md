# Event normalization and filtering
Use typed event variants, not stringly-typed fallback. Explicitly distinguish push, tag, PR/MR, delete, merge queue and manual reconciliation.
Ref filters operate on canonical `refs/heads/*` / `refs/tags/*`; define exact match vs glob, case sensitivity, path matching and negative precedence. Do not assume providers share ref conventions.
Evaluate against registered repository identity, not arbitrary payload text. Unknown event and missing revision must fail closed or be quarantined with diagnostic reason.
Store both normalized fields and a safe hash/reference to original delivery for forensic replay; avoid sensitive payload dumps.
