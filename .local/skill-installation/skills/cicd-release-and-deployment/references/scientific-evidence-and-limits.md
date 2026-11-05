# Peer-reviewed research: findings, transfer limits and citations

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

The papers below were found with publisher/university metadata and DOI. They inform method design, not claims of measured Ec outcomes.

| ID | Work and venue | DOI | Useful finding / boundary |
|---|---|---|---|
| S01 | Schermann, Schöni, Leitner, Gall (2016), *Bifrost: Supporting Continuous Deployment with Automated Enactment of Multi-Phase Live Testing Strategies*, ACM Middleware '16 | [10.1145/2988336.2988348](https://doi.org/10.1145/2988336.2988348) | Formal model and prototype for staged live experiments; prototype-specific performance is **not** a benchmark for Ec. |
| S02 | Schermann, Cito, Leitner, Zdun, Gall (2018), *We're Doing It Live: A Multi-Method Empirical Study on Continuous Experimentation*, Information and Software Technology 99, 41-57 | [10.1016/j.infsof.2018.02.010](https://doi.org/10.1016/j.infsof.2018.02.010) | Interviews and survey show monitoring and principled evaluation gaps; exploratory empirical results, not a proof that a canary eliminates incidents. |
| S03 | Shahin, Babar, Zhu (2017), *Continuous Integration, Delivery and Deployment: A Systematic Review on Approaches, Tools, Challenges and Practices*, IEEE Access 5, 3909-3943 | [10.1109/ACCESS.2017.2685629](https://doi.org/10.1109/ACCESS.2017.2685629) | Maps reported practices and gaps across a 2004-2016 corpus; cannot set target-specific release policies or SLOs. |
| S04 | Shahin, Babar, Zahedi, Zhu (2017), *Beyond Continuous Delivery: An Empirical Investigation of Continuous Deployment Challenges*, ACM/IEEE ESEM '17 | [10.1109/ESEM.2017.18](https://doi.org/10.1109/ESEM.2017.18) | Observes barriers including release policy, manual checks and insufficient automation; respondents/context constrain generalization. |
| S05 | Laukkanen, Itkonen, Lassenius (2017), *Problems, causes and solutions when adopting continuous delivery—A systematic literature review*, Information and Software Technology 82, 55-79 | [10.1016/j.infsof.2016.10.001](https://doi.org/10.1016/j.infsof.2016.10.001) | Reports recurring adoption barriers and solutions; not a controlled experiment of these example gate algorithms. |

**Research-to-method derivation:** explicit staged phases, monitoring signal provenance and uncertainty, secure automation, human/organizational review where demanded, and rejection of unsupported rollback claims. The choice of sample sizes, thresholds, phase weights, deadlines, reviewer policy and target adapter is a *project decision* requiring accepted spec and evidence.

**Not proven:** reduction in outage probability, zero downtime, availability percentile, speed-up, security compliance, statistical effectiveness of arbitrary canary gates, or behavior of any Ec release controller. No invented benchmarks.
