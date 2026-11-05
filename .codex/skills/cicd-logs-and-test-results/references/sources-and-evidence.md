# Official documentation and research evidence
Official:
> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

- OpenTelemetry Logs Data Model: https://opentelemetry.io/docs/specs/otel/logs/data-model/ — timestamp versus observed timestamp, trace context and log records.
- OpenTelemetry Logging specification: https://opentelemetry.io/docs/specs/otel/logs/ — correlation and pipeline concepts.
- Jenkins JUnit plugin: https://www.jenkins.io/doc/pipeline/steps/junit/ — report ingestion, missing reports and test result status.
- Jenkins JUnit plugin details: https://plugins.jenkins.io/junit/ — test report dialect support and streaming.
- Python ElementTree security notes: https://docs.python.org/3/library/xml.html#xml-vulnerabilities — XML parser risks.
- OWASP Logging Cheat Sheet: https://cheatsheetseries.owasp.org/cheatsheets/Logging_Cheat_Sheet.html — sensitive information and log injection.

Peer-reviewed research, contextual (not Ec evidence):
- He et al. (2017), *Drain: An Online Log Parsing Approach with Fixed Depth Tree*, IEEE ICWS, DOI 10.1109/ICWS.2017.13 — structured log parsing evaluation, not correctness of this ingestion design.
- Zhu et al. (2019), *Tools and Benchmarks for Automated Log Parsing*, ICSE, DOI 10.1109/ICSE.2019.00081 — comparative log parsing methodology; results are workload-dependent.
- Le and Zhang (2022), *Log-based Anomaly Detection Without Log Parsing*, ASE, DOI 10.1145/3551349.3556947 — anomaly detection research; not a substitute for deterministic CI test outcome parsing.

Source limits: scientific papers do not prove exactly-once ingestion, redaction completeness, performance or test outcome correctness for Ec. Verify publisher metadata before relying on individual empirical claims.
