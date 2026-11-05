# Routing and Agent Selection Oracles

> Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

**Positive:**
- “Display timestamps in the viewer's chosen time zone without shifting date-only values.” → internationalization.
- “Our Arabic UI reverses an agent ID in the table; fix directionality.” → internationalization.
- “Implement a typed translation catalog and detect missing plural categories.” → internationalization.
- “German error messages overflow at 200% zoom; add pseudo-localization tests.” → internationalization as the primary concern, CSS engineering only for geometry.
- “Switch locales without old network responses overwriting the latest selection.” → internationalization, with optional state/cache collaboration.

**Negative:**
- “Translate the following paragraph from English to French.” → writing/translation, no engineering skill.
- “Design a new grid layout independent of language.” → css-layout-engineering.
- “Fix JWT issuer signature checks.” → identity/security expertise.
- “Change PostgreSQL queue semantics to exactly once.” → distributed systems/CI scheduler.
- “Create a new YAML resource schema.” → schema owner; locale may only affect UI labels.

**Borderline:**
- “A German long label clips a Vue modal.” → internationalization validates expansion and translation context; css-layout-engineering owns responsive structural fix.
- “Search filters display Arabic names in the wrong order.” → internationalization for display collation; information architecture/search owns relevance and canonical matching.
- “Internationalize the login page” → internationalization for presentation only, identity-federation for authentication contracts.

**Agent evaluation plan:** run at least the listed prompts with skill descriptions visible, record actual selected skills/nonselections and artifact-specific rationale. Static test inventories and false-positive checks do not prove an agent will route correctly. No actual agent selection test is executed as part of this package.
