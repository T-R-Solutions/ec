# Coverage-Guided Fuzzing, Parser Robustness and Safety

**Boundary:** property-based random generation and feedback-guided fuzzing have overlapping uses but different mechanisms. libFuzzer uses coverage feedback and a corpus; the included Python byte test is *not* coverage-guided.

## Real fuzz target checklist

- Select a narrow parser/data boundary with explicit accepted/rejected behavior.
- Provide pure/deterministic target whenever practical; no shell, network or persistent side effects.
- Bound input size, execution time, memory and nesting; sandbox untrusted inputs.
- Instrument the actual language implementation; optionally run ASan/UBSan where toolchain supports.
- Seed valid/invalid corpora; save/replay crash/timeout/sanitizer artifacts after triage.
- Separate a parser rejection that complies with contract from a crash/unsafe behavior.
- Minimize crashes and keep a deterministic regression before issue closure.

## Rust reference (NOT RUN HERE)

```rust
// fuzz/fuzz_targets/parse.rs — illustrative only
#![no_main]
use libfuzzer_sys::fuzz_target;
fuzz_target!(|bytes: &[u8]| {
    // Replace `parse` with an approved, safe parser under test.
    // Do not treat every parse rejection as a bug.
    let _ = my_parser::parse(bytes);
});
```

Do not invoke a fuzz target against production, secret-bearing artifacts, or privileged services. Limit corpora; a fuzzer exhausting resources is not evidence of a product defect unless that is within a declared threat model. Follow LLVM libFuzzer and Rust Fuzz Book for real toolchain-specific steps.
