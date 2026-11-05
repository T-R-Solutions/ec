# Evidence ledger

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

## Official documentation consulted
- AWS S3, Checking object integrity: https://docs.aws.amazon.com/AmazonS3/latest/userguide/checking-object-integrity.html — checksum mechanisms and full-object integrity.
- AWS S3, Multipart upload overview: https://docs.aws.amazon.com/AmazonS3/latest/userguide/mpuoverview.html — multipart and composite/full-object checksums.
- AWS S3, Expiring objects: https://docs.aws.amazon.com/AmazonS3/latest/userguide/lifecycle-expire-general-considerations.html — delete markers and versioning.
- Google Cloud Storage, Object holds: https://docs.cloud.google.com/storage/docs/object-holds — event-based and temporary holds.
- NIST SP 800-88 Rev. 2 (September 2025): https://csrc.nist.gov/pubs/sp/800/88/r2/final — sanitization program; not equivalent to deleting a blob.

## Peer-reviewed research: context, not product certification
- Quinlan and Dorward (2002), *Venti: A New Approach to Archival Storage*, USENIX FAST 2002, https://www.usenix.org/conference/fast-02/venti-new-approach-archival-storage — content-addressed archival storage; does not establish Ec retention correctness.
- Douceur et al. (2002), *Reclaiming Space from Duplicate Files in a Serverless Distributed File System*, ICDCS 2002, DOI: 10.1109/ICDCS.2002.1022302 — duplicate storage management and reference considerations; verify paper-specific applicability before performance claims.
- Baker et al. (2006), *A Fresh Look at the Reliability of Long-term Digital Storage*, EuroSys 2006, DOI: 10.1145/1217935.1217947 — long-term storage reliability modeling, not a guarantee for a specific object store.

## Evidence classification
Official provider docs describe supported semantics, not automatically the Ec deployment. Scientific results are context-bound. The local SQLite/FS fixture tests application-level invariants only; it cannot prove cross-region durability, provider compliance, production GC, encryption or sanitization.
