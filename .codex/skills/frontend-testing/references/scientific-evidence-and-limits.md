# Peer-reviewed scientific evidence — claims and limits

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

| Study | Verified venue / DOI | Supports | Does **not** establish |
|---|---|---|---|
| Barr, Harman, McMinn, Shahbaz & Yoo (2015), *The Oracle Problem in Software Testing: A Survey* | IEEE TSE 41(5); 10.1109/TSE.2014.2372785 | Test-oracle specification and automation limits | An oracle for a particular frontend feature or Ec acceptance |
| Inozemtseva & Holmes (2014), *Coverage Is Not Strongly Correlated with Test Suite Effectiveness* | ICSE 2014; 10.1145/2568225.2568271 | Code coverage is not a reliable standalone surrogate for fault detection in studied Java programs | A precise correlation for TS/Vue or proof all coverage measures are useless |
| Luo, Hariri, Eloussi & Marinov (2014), *An Empirical Analysis of Flaky Tests* | FSE 2014; 10.1145/2635868.2635920 | Classification and observational evidence for causes of flaky tests | Prevalence or expected pass rates of this project's suite |
| Jia & Harman (2011), *An Analysis and Survey of the Development of Mutation Testing* | IEEE TSE 37(5); 10.1109/TSE.2010.62 | Principles, history, costs and known limits of mutation tests | Guarantee that killing synthetic mutants means all real defects will be detected |

## Transfer discipline

A scientific study is evidence for the question, subjects and method it investigated. Design choices here are **engineering recommendations**, not independently proven laws of frontend performance. Treat mock/test-harness success as a local observation; do not claim a scientific publication verified code in this package. Include scientific DOI and a verified host in `sources-and-decisions.md`.
