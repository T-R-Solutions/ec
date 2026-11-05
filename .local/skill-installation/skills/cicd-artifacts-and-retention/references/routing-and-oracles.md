# Routing and test oracles
Positive: verified upload, immutable manifest, authorized download, correct digest, valid hold and eventual GC.
Negative: digest mismatch, over-quota, unverified download, cross-tenant read, conflicting finalize, deletion under hold, deletion with live references.
Boundary: empty artifact, max size, duplicate finalize, retention at exact deadline, versioned object, concurrent GC and new reference.
Fault: crash between blob write and manifest, lost finalize response, partial upload, storage timeout, delete failure, replica lag.
Tests must assert real state, not only return codes. A model fixture is not proof of object store durability, security or deletion.
