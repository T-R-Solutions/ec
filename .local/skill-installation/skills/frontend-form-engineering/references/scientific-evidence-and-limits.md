# Peer-reviewed empirical evidence — what it supports and what it does not

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

The following peer-reviewed sources were identified through publisher pages or university research records. They inform hypotheses, not universal rules or performance claims about Ec.

## 1. Bargas-Avila et al. (2007): validation timing

**J. A. Bargas-Avila et al.**, “Usable error message presentation in the World Wide Web: Do not show errors right away,” *Interacting with Computers* 19(3), 330–341, 2007. DOI: **10.1016/j.intcom.2007.01.003**. Publisher: https://academic.oup.com/iwc/article-abstract/19/3/330/798898

Two experiments (77 and 90 participants) found worse performance with immediate error feedback than with errors presented after completing the form in tested online form conditions. **Supports:** treating validation timing as a choice requiring evidence; avoiding dogmatic immediate errors. **Does not support:** banning all inline feedback or asserting identical outcomes for specialized administration, modern screen readers or mobile.

## 2. Seckler et al. (2012): error placement

**M. Seckler, A. N. Tuch, K. Opwis, J. A. Bargas-Avila**, “User-friendly locations of error messages in web forms: Put them on the right side of the erroneous input field,” *Interacting with Computers* 24(3), 107–118, 2012. DOI: **10.1016/j.intcom.2012.03.002**. Publisher: https://academic.oup.com/iwc/article-abstract/24/3/107/688803

Controlled online study (303 participants) compared six locations; messages near erroneous fields performed better than at form top/bottom in tested conditions. Right-side placement was rated highly. **Supports:** field-proximal feedback. **Does not support:** always-right placement, especially in RTL, mobile, zoomed or assistive-technology contexts; field errors may also need a summary.

## 3. Seckler et al. (2014): combined form guidelines

**M. Seckler, S. Heinz, J. A. Bargas-Avila, K. Opwis, A. N. Tuch**, “Designing usable web forms: Empirical evaluation of web form improvement guidelines,” *CHI 2014*, pp. 1275–1284. DOI: **10.1145/2556288.2557265**. University record: https://researchprofiles.ku.dk/en/publications/designing-usable-web-forms-empirical-evaluation-of-web-form-impro/

A controlled eye-tracking study (65 participants) found that forms revised according to a bundle of 20 guidelines were associated with faster completion, fewer submission attempts and higher satisfaction in tested company websites. **Supports:** evaluating bundled design guidance rather than isolated aesthetics. **Does not support:** attributing each improvement to a single guideline, transferring measured effect sizes to a new product or claiming the fixture has been usability tested.

## 4. Inal (2016): attention vs task completion

**Y. Inal**, “User-Friendly Locations of Error Messages in Web Forms: An Eye Tracking Study,” *Journal of Eye Movement Research* 9(5), 2016. DOI: **10.16910/jemr.9.5.1**. Journal: https://bop.unibe.ch/JEMR/article/view/2821

Study with 32 participants reported quickest visual detection for right-side error messages but **no statistically significant group mean differences** for several task-level measures (including form completion and correction times). **Supports:** avoid conflating first fixation/attention with task success; place messages near fields as a candidate. **Does not support:** guaranteed time savings or universal optimal location.

## 5. Bargas-Avila et al. (2011): date format trade-offs

**J. A. Bargas-Avila et al.**, “Working towards Usable Forms on the World Wide Web: Optimizing Date Entry Input Fields,” *Advances in Human-Computer Interaction*, 2011. DOI: **10.1155/2011/202701**. Publisher: https://doi.org/10.1155/2011/202701

Empirical online study with 172 participants compared six date-input representations. Drop-down choice avoided format errors; single-field input with accessible format indication could be faster and more satisfying in the studied setting. **Supports:** choose based on error-cost vs speed, rather than assuming one date component is best. **Does not support:** universal datetime choices across locale and time zones.

## 6. Bargas-Avila et al. (2011): multiple choice control trade-offs

“Working towards Usable Forms on the Worldwide Web: Optimizing Multiple Selection Interface Elements,” *Advances in Human-Computer Interaction*, 2011. DOI: **10.1155/2011/347171**. Publisher: https://onlinelibrary.wiley.com/doi/10.1155/2011/347171

Study with 106 participants compared checkboxes and list boxes; checkboxes were faster and rated more favorably for smaller option sets, but differences declined with practice. **Supports:** consider familiarity and option count. **Does not support:** replacing very large searchable multiselects with hundreds of checkboxes.

## Synthesis: conflicts, context and quality

- **Timing vs placement are distinct variables.** A late error can still appear close to its field; an inline message can appear only after a submit.
- **W3C WCAG 2.2 defines required perceivable information and recovery criteria, not a universal exact timing/placement algorithm.** Research influences design alternatives; normative standards determine compliance criteria.
- **GOV.UK is a government design system, not a scientific trial nor a mandatory standard for Ec.** Its preference for submit-time validation is a useful default for some contexts, which must be evaluated rather than blindly copied.
- **Historical participants and tasks differ from Ec's technical administrative users.** Samples from 2007–2016 cannot establish an outcome in the 2026 Ec application.
- **No effect sizes or p-values are fabricated here.** When an abstract reports significant improvement without exact numeric values, record the direction and study limits only.

Evidence hierarchy for implementation claims: direct product tests and task observations > a context-specific controlled experiment > transferable guideline/hypothesis. Even product tests require cohort/conditions disclosure.

**Explicit study limitation:** No cited experimental intervention has been demonstrated in the Ec application.
