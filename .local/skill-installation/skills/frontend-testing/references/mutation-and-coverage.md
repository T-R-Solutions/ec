# Mutation testing and coverage interpretation

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

Use small plausible behavioral defects to check whether tests detect relevant failures, such as changing a 409 conflict into success, removing a validation guard or allowing an old response to overwrite the current view. Each mutation must compile/run, baseline must pass, and the targeted test must fail for an oracle-specific reason. An invalid syntax error, missing dependency or harness crash does not count as a killed behavioral mutation.

Classify surviving mutants as gaps to review, not automatically as actual product defects. Some mutations are semantically equivalent; triage before reporting a ratio. Keep the mutation runner bounded and reproducible. Code/branch coverage is useful to find untested code, but a large percentage does not establish fault-detection effectiveness.

Research: Jia & Harman (2011), *An Analysis and Survey of the Development of Mutation Testing*, IEEE TSE, DOI 10.1109/TSE.2010.62; Inozemtseva & Holmes (2014), *Coverage Is Not Strongly Correlated with Test Suite Effectiveness*, ICSE, DOI 10.1145/2568225.2568271. Findings from Java/other programs cannot be transferred as numerical predictions to Vue or Ec.

Official: https://vitest.dev/guide/coverage ; https://xplorestaging.ieee.org/document/5487526 ; https://dblp.dagstuhl.de/rec/conf/icse/InozemtsevaH14.html
