# Formal Notes — Bidirectional Ec Draft Projections

This is an **engineering adaptation** of published research on lenses, not a theorem that the Ec implementation has been proved correct. The approved Ec resource contract, not this note, defines admissible fields and values.

## Models and boundaries

- `Y`: a valid, version-identified, fully typed desired-configuration resource parsed from YAML. This is a **transient semantic draft**; the saved authoritative representation remains a versioned YAML document.
- `P`: source/presentation metadata (comments, style, source spans, explicit quoting, anchors if supported). `P` may be absent in parsers that discard it. YAML itself distinguishes representation, serialization, and presentation.
- `V_m`: a partial view for mode `m ∈ {form, visual}` with only the supported capabilities of that resource/version.
- `R`: a versioned snapshot identity or immutable base revision of the draft as last known; not a deployment revision.
- `B`: uncommitted source-text buffer, potentially syntactically invalid, not authoritative and not automatically projected into the valid semantic draft.

`get_m : Y -> V_m` is projection. `put_m : (Y, V_m, R) -> Result<Y, Rejection>` applies an accepted edit relative to the original *full* resource, not by serializing the view back as a complete resource. A narrower API using `Operation` rather than a complete `V_m` is often safer for preserving complements. A method using typed edit operations is an engineering recommendation, not a mandatory feature of the YAML specification.

Possible editorial operation shape (illustrative TypeScript **interface design**, not an Ec contract):

```ts
interface DraftOperation {
  resourceId: string;
  baseRevision: string;
  mode: 'form' | 'visual';
  target: { stableNodeId: string; fieldPath: readonly string[] };
  kind: 'set-field' | 'remove-field' | 'add-edge' | 'remove-edge';
  value?: unknown;
}

type ApplyResult =
  | { status: 'applied'; newDraftRevision: string }
  | { status: 'rejected'; code: string; details: string }
  | { status: 'conflict'; expectedBase: string; actualBase: string };
```

Never copy this shape into the Ec API without approved project contract and schema evidence; the generic payload `unknown` requires validation, not blind casting.

## Testable lens laws

Let `≈` denote **approved semantic equivalence** on valid resources; if the schema does not specify how to normalize a property, do not guess. For an accepted/representable view `v`:

1. **GetPut / stability:** `put(s, get(s)) ≈ s`. Re-rendering or applying a no-op view must not delete hidden, unsupported, or extension information. If a proposed `put` changes a hidden timeout, it violates GetPut.
2. **PutGet / reflection:** `get(put(s, v)) ≈_view v`. A supported, accepted form/canvas change is observable when projecting the updated source. Reject invalid changes rather than claiming PutGet for out-of-domain inputs.
3. **PutPut / overwrite (conditional):** `put(put(s, v1), v2) ≈ put(s, v2)` is useful **only** for a designated class of successive, whole-view overwrite operations when both sides are defined and relative revision handling does not change their meaning. Foster et al. distinguish GetPut/PutGet as fundamental and note that PutPut fails for some legitimate combinators; **do not impose it globally on patches or noncommuting operations**.

If a projection is read-only for a field, define an explicit smaller update domain rather than invent a putback. If the view intentionally omits information, `put` needs the original source to retain it; a `toYaml(view)` conversion cannot generally recover it.

**Illustrative failure:** a YAML pipeline contains a job with `timeout`, `retry`, `agent`, and an extension field. The canvas shows only the job name and its edges. Deleting and recreating the job from the canvas would lose these fields. A compliant `set-job-name` operation targets that job's stable ID inside the original full resource, preserving other fields and references.

## Additional invariants and caveats

- **Textual vs semantic:** comments and whitespace are presentation details, but Ec documentation additionally requires avoiding silent loss of *important* comments/presentation. Test both independently: semantic equivalence alone is not enough to approve a destructive rewrite; a displayed diff must include presentation changes when support is incomplete.
- **Omission/`null`/defaults:** an absent field and an explicitly empty/`null` field may differ under the resource contract. Define normalization with the schema owner, not the UI.
- **Mappings vs sequences:** YAML mapping-key order is not semantically ordered, whereas sequences are ordered; DAG dependency edges carry directed semantics independently of presentation order.
- **Unknown fields:** preserve where the approved extension contract permits them; reject unsupported additions where it does not. Never silently drop either class.
- **Identity:** use stable entity IDs rather than array offsets or DOM positions, including across reordering, undo and branch switches.
- **Validation:** client-side projection checks provide feedback, not authorization or publication; server-side rules remain authoritative.
- **Invalid edits:** a dirty YAML text buffer can be invalid without corrupting the valid semantic draft or the published revision; do not claim it was saved or effective.
- **Conflict:** when `baseRevision` no longer matches, do not use last-write-wins silently; report a conflict or use a separately approved merge strategy.
- **Three modes do not imply three masters:** Visual/Form/YAML are entry points over the same semantic resource with different expressiveness. Each view can fail closed for edits it cannot represent; its available capabilities must be disclosed.

## Interpretation of source evidence

Foster et al., *Combinators for Bidirectional Tree Transformations* (POPL 2005; journal extension TOPLAS 2007), establish the abstract view/update problem, the GetPut and PutGet laws, and the conditional nature of PutPut. The **application** to Ec's resource editors is our engineering inference.

YAML 1.2.2 specifies the distinction between representation, serialization and presentation. Vue documentation recommends pure computed getters and one-way state flow. These do not *prove* Ec's architecture; they motivate design constraints that still require tests against its implementation.
