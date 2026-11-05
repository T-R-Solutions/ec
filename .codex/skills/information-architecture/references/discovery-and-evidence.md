# Discovery, user tasks, and evidence discipline

## Define the real question

Document: problem statement; target users and task frequency/criticality; known current information architecture; feature state; the affected navigation region and its ownership; user vocabulary; product permissions and sources of truth. The UI structure is not a proxy for a domain model. Ask where people start and what **final destination or result** proves task completion.

## Build a task sample

| Field | Example (illustrative only) |
|---|---|
| Role | Operator allowed to view a project |
| Starting point | Deep link to project overview |
| User task | Find the most recent failed execution |
| Goal / oracle | Arrive at a relevant execution detail, not merely open “Runs” |
| Constraints | Read permission, selected project, keyboard alternate path |
| Risk | Global results could be mistaken for project-scoped ones |
| Evidence | Instrumented observation in real application required |

Consider entry via search engine, saved link, notification, overview, menu and project detail. Include experts, novices, people using keyboard/assistive technology, and multi-language users where relevant. Don't presume a fictitious role or a supported feature.

## Data sources and uncertainty

- **Observed**: real task sessions, anonymized search terms, consented usability observations, support incidents, navigation logs with disclosed instrumentation.
- **Reported**: interviews, surveys, domain-expert notes; vulnerable to recall and selection bias.
- **Exploratory**: card sort, competitor inspection and internal workshops; useful hypotheses, **not** observed task performance.
- **Synthetic**: model fixtures and scripted paths; prove the fixture and assertions only.

Segment by user tasks and authorization context where safe. If no real sessions exist, record `NOT EXECUTED` and propose a task-based research protocol. Never invent participant results, click rates, abandonment percentages, time-to-find or confidence intervals.

## Output

Deliver an explicit task inventory with IDs, roles, destinations, confidence/provenance, evidence gaps and research questions. For a fundamental redesign, evaluate an alternative grouping before recommending a taxonomy. GOV.UK's research guidance emphasizes recruiting representative participants and realistic, non-leading task prompts.

Sources: [GOV.UK moderated usability testing](https://www.gov.uk/service-manual/user-research/using-moderated-usability-testing), [GOV.UK research planning](https://www.gov.uk/service-manual/user-research/plan-round-of-user-research), [W3C navigation design](https://www.w3.org/WAI/curricula/designer-modules/navigation-design/).
