use super::*;

#[test]
fn chunks_are_lossless_and_bounded_before_persistence() {
    assert_eq!(
        decode(&json!("00ff61"), ec_contracts::MAX_LOG_CHUNK_BYTES),
        Ok(vec![0, 255, b'a'])
    );
    assert_eq!(hex(&[0, 255, b'a']), "00ff61");
    assert_eq!(
        decode(&json!(""), ec_contracts::MAX_LOG_CHUNK_BYTES),
        Ok(vec![])
    );
    assert_eq!(
        decode(
            &json!("00".repeat(65536)),
            ec_contracts::MAX_LOG_CHUNK_BYTES
        )
        .unwrap()
        .len(),
        65536
    );
    for value in [json!("0"), json!("00".repeat(65537))] {
        assert_eq!(
            decode(&value, ec_contracts::MAX_LOG_CHUNK_BYTES),
            Err((413, "chunk_limit"))
        );
    }
    for value in [json!(null), json!("gg"), json!("é")] {
        assert_eq!(
            decode(&value, ec_contracts::MAX_LOG_CHUNK_BYTES),
            Err((400, "hex"))
        );
    }
}

#[test]
fn sequence_and_persisted_json_are_validated() {
    assert_eq!(seq(&json!({"sequence":1})), Ok(1));
    assert_eq!(seq(&json!({"sequence":i64::MAX})), Ok(i64::MAX));
    for value in [
        json!({}),
        json!({"sequence":0}),
        json!({"sequence":-1}),
        json!({"sequence":"1"}),
    ] {
        assert_eq!(seq(&value), Err((400, "sequence")));
    }
    assert_eq!(parse("{\"receipt\":1}"), Ok(json!({"receipt":1})));
    assert_eq!(parse("corrupt"), Err((500, "stored_json")));
}
