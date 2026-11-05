# Peer-Reviewed Evidence and Limits

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

Each item below is a published research result; none certifies this skill's example runtime or the Ec application.

1. **Mahajan, Alameer, McMinn & Halfond (2018).** “Automated repair of internationalization presentation failures in web pages using style similarity clustering and search-based techniques.” *IEEE ICST*, pp. 215–226. DOI: https://doi.org/10.1109/ICST.2018.00030. A web study investigating layout distortions caused by translation and techniques for repair. **Implication:** include layout-expansion regression testing. **Limit:** measured repair success in their studied pages does not transfer to another UI.
2. **Mahajan, Alameer, McMinn & Halfond (2021).** “Effective automated repair of internationalization presentation failures in web applications using style similarity clustering and search-based techniques.” *Software Testing, Verification and Reliability* 31, e1746. DOI: https://doi.org/10.1002/stvr.1746. Follow-up with more pages and reported user evaluation. **Implication:** layout fixing needs readability/oracle checks as well as geometric checks. **Limit:** repair tooling and curated pages do not prove enterprise app usability.
3. **Leiva & Alabau (2015).** “Automatic Internationalization for Just In Time Localization of Web-Based User Interfaces.” *ACM Transactions on Computer-Human Interaction* 22(3), Article 13. DOI: https://doi.org/10.1145/2701422. Evaluated a method for adding post-hoc translation support to websites. **Implication:** source string extraction and late localization have engineering costs/constraints. **Limit:** not a license to use automated translation as approved production copy.
4. **Rau & Liang (2003).** “Internationalization and localization: evaluating and testing a Website for Asian users.” *Ergonomics* 46(1–3), 255–270. DOI: https://doi.org/10.1080/00140130303527. User-centered localization and usability evaluation in a specific website context. **Implication:** native-user task testing is distinct from syntactic correctness. **Limit:** old context, users and technology; avoid universal claims.
5. **Awwad & Slany (2016).** “Automated Bidirectional Languages Localization Testing for Android Apps with Rich GUI.” *Mobile Information Systems*, Article 2872067. DOI: https://doi.org/10.1155/2016/2872067. Investigates bidirectional localization testing in mobile GUI. **Implication:** include mixed-direction and direction-sensitive icon checks. **Limit:** Android mobile results cannot establish browser conformance.
6. **Kabát (2023).** “Possible Translation Problems, Their Causes, and Solutions in Agile Localization of Software.” *International Journal of Interactive Mobile Technologies* 17(1), 129–140. DOI: https://doi.org/10.3991/ijim.v17i01.36367. A qualitative study of translation problems arising from UI string context. **Implication:** provide screenshot/context/meaning metadata to translators. **Limit:** a single-product corpus is not a universal defect rate.

### Evidence claim protocol

- **Normative fact**: quote exact standard/spec version and requirement, not empirical impact.
- **Research observation**: report studied context, method, authors and DOI; no invented numeric effectiveness.
- **Executable evidence**: report command, tested versions, sample/mutation count and passed/failed outcome.
- **Hypothesis**: clearly mark expected benefit with a planned test, not a claimed result.
- **Pending**: native linguistic review, screen reader/keyboard coverage, installed Vue runtime and real-backend tests must remain open until executed.
