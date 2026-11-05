# Tests, negative compiler probes, mutation sensitivity and evidence tiers

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill. Ec-specific rules below apply only to an Ec consumer whose current contract establishes them. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

Scientific-method context: [Barr et al., IEEE TSE 2015, test oracle problem](https://doi.org/10.1109/TSE.2014.2372785) and [Inozemtseva & Holmes, ICSE 2014, coverage vs effectiveness](https://doi.org/10.1145/2568225.2568271); these papers do not validate this package.

## Repeatable local commands (when Node/tsc installed)

```bash
python3 validation/run_compiler_tests.py
python3 validation/run_runtime_tests.py
python3 validation/run_module_and_declaration_tests.py
python3 validation/run_mutation_probes.py
python3 validation/run_version_gate.py
python3 validation/run_checks.py
python3 validation/run_project workflow_fixture.py --source-zip ../skills_zip.zip
python3 validation/build_package.py
python3 validation/check_zip.py
python3 validation/run_zip_negative_tests.py
```

Before copying the portable skill to another repository, replace fixtures with the *actual application's tests*. The scripts above are package-development validation, not commands the generic skill must always run for users.

## Independent oracles

| Layer | Input | Valid observable result | Invalid observable result |
|---|---|---|---|
| Compiler positive | Valid discriminated union, generic key | `tsc` exit 0, no diagnostic | Fails; investigate type/API or config |
| Compiler negative | Disallowed assignment/unknown access/missing variant | `tsc` nonzero + expected `TSxxxx` diagnostic | An unsafe operation compiles, or a different unrelated error appears |
| Module/emit | NodeNext `.mts` | Generated `.mjs` imports an existing extension | Invalid import specifier, runtime cannot load |
| Library consumer | Public emitted `.d.ts` | Correctly typed call compiles, bad call rejected | Missing exports, incorrect declaration or unsound consumer behavior |
| Runtime boundary | `unknown` payloads | Valid accepted; invalid rejected with intended reason | Any malformed payload is accepted or error is swallowed |
| Mutation | Known intentional defect | At least one relevant test fails | Mutation survives, identify weak oracle |
| project workflow bridge | Original model + bridge | Valid path accepted; missing skill path rejected | A missing path passes structural validation |
| Real product | Revisioned Ec checkout | All relevant acceptance criterias with evidence | Only synthetic/structural evidence exists |

## Evidence classifications

- **STATIC REVIEW**: files/links/checker specification present.
- **TYPECHECK EXECUTED**: actual compiler version and diagnostic output captured.
- **RUNTIME FIXTURE EXECUTED**: emitted JS tested in actual Node process; the fixture itself is synthetic.
- **INTEGRATION EXECUTED**: real consumer/application and dependencies tested in authorized workspace.
- **RESEARCH**: source-supported API semantics and scientific study limits, not a code test.
- **NOT EXECUTED**: unavailable compiler versions, browser, Vue tooling, Ec application, real agent behavior or other missing infrastructure.

Always include exact commands, exit codes, compiler version, input revision/hash and the range of the oracles. Never promote `tsc` to a security/functional acceptance test or infer test effectiveness from number of tests/coverage alone.
