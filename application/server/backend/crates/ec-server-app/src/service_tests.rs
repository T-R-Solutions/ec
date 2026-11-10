use super::*;

#[test]
fn project_names_and_positive_ids_reject_route_ambiguity_and_overflow() {
    for value in ["a", "ec", "a-1", &"a".repeat(64)] {
        assert!(name(value), "rejected {value:?}");
    }
    for value in ["", "A", "0ec", "ec-", "../ec", "ec/a", "é", &"a".repeat(65)] {
        assert!(!name(value), "accepted {value:?}");
    }
    assert_eq!(positive("1"), Ok(1));
    assert_eq!(positive("9223372036854775807"), Ok(i64::MAX));
    for value in ["0", "-1", "1.0", "1/2", "9223372036854775808", ""] {
        assert_eq!(positive(value), Err((400, "id")));
    }
}

#[test]
fn envelopes_require_the_exact_declared_keys() {
    assert_eq!(envelope(&json!({"yaml":"x"}), &["yaml"]), Ok(()));
    for value in [
        json!(null),
        json!([]),
        json!({}),
        json!({"yaml":"x","token":"extra"}),
    ] {
        assert_eq!(envelope(&value, &["yaml"]), Err((400, "envelope")));
    }
    assert_eq!(
        envelope(
            &json!({"requestId":"x","revisionId":1}),
            &["requestId", "revisionId"]
        ),
        Ok(())
    );
}
