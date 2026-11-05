# Peer-reviewed empirical and conceptual evidence — precise limits

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

Literature was inspected via publisher/university/researcher records on 2026-11-01. The study claims below are restricted to each sample, design and period. No study is an official CLI standard, and none tests Ec.

| ID | Verified study, publisher and DOI | Supported observation | Boundary |
|---|---|---|---|
| S01 | *Bash in the Wild: Language Usage, Code Smells, and Bugs*. ACM Transactions on Software Engineering and Methodology 32(1), article 8 (2023), DOI <https://doi.org/10.1145/3517193> | Large-scale analysis of >1 million Bash scripts found common issues involving quoting, permissions, resource management and error handling | GitHub script corpora are not a controlled experiment evaluating a CLI library or a specific CLI design |
| S02 | Schröder & Cito, *An empirical investigation of command-line customization*. Empirical Software Engineering 27, article 30 (2022), DOI <https://doi.org/10.1007/s10664-021-10036-y> | Analysis of >2.2 million shell aliases identifies shortcuts, default changes and command chains; suggests discoverability and customization opportunities | Aliases expose user practices, not measured improvement from a particular command tree |
| S03 | Perez De Rosso & Jackson, *What's Wrong with Git?: A Conceptual Design Analysis*. ACM Onward! 2013, DOI <https://doi.org/10.1145/2509578.2509584> | Conceptual analysis finds design misfits in Git's model and motivates simpler conceptual structure | A specific Git case study; not universal benchmark of all CLIs |
| S04 | Perez De Rosso & Jackson, *Purposes, Concepts, Misfits, and a Redesign of Git*. ACM OOPSLA 2016, DOI <https://doi.org/10.1145/2983990.2984018> | Analysis of Git/Stack Overflow reports and a small user study supporting evaluation of conceptual misfits | Small study of Git/Gitless; do not project effect sizes to Ec |
| S05 | Miller et al., *Inky: A Sloppy Command Line for the Web with Rich Visual Feedback*. ACM UIST 2008, DOI <https://doi.org/10.1145/1449715.1449737> | Prototype and preliminary field study of context assistance and live feedback for typed commands | Hybrid web UI, not a terminal-only admin CLI; no mandate to add visual hints |

## Translation to a responsible engineering method

- S01 supports **testing error handling, quoting and status inspection**, not claiming a specific parser is secure.
- S02 suggests **testing discoverability, common workflows and composition**, not assuming every CLI should offer aliases or fuzzy autocorrection.
- S03/S04 justify **conceptual task reviews and study of confusing subcommands**, not asserting a redesigned grammar yields a fixed improvement.
- S05 supports **evaluating contextual assistance when useful**, but also highlights the cost of transferring a hybrid interface study to a terminal context.

## Independent evidence still necessary

Actual end users should perform representative tasks on the chosen OS and shell; collect completion, wrong-target errors, help discovery, cognitive load and accessibility evidence with a preregistered protocol when appropriate. Real CLI binaries should be assessed for platform compatibility, permissions, stderr/stdout, recovery, latency and error safety. No performance gains, security guarantees or usability improvements are asserted from these papers alone.
