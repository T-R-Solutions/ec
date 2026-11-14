//! Vocabulário público v1. Não contém estado nem depende dos domínios Ec.
use sha2::{Digest, Sha256};
include!(concat!(env!("OUT_DIR"), "/v1.rs"));

const _: () = {
    assert!(AGENT_CHUNK_BYTES <= MAX_LOG_CHUNK_BYTES);
    assert!(AGENT_CHUNK_BYTES <= MAX_ARTIFACT_CHUNK_BYTES);
    assert!(HEARTBEAT_INTERVAL_MILLIS >= HEARTBEAT_TICK_MILLIS);
    assert!(HEARTBEAT_INTERVAL_MILLIS.is_multiple_of(HEARTBEAT_TICK_MILLIS));
    assert!(MAX_SCRIPT_BYTES <= MAX_SCALAR_BYTES);
    assert!(MAX_SCALAR_BYTES <= MAX_INPUT_BYTES);
};

#[must_use]
pub fn valid_name(value: &str) -> bool {
    !value.is_empty()
        && value.len() <= MAX_NAME_BYTES
        && value.as_bytes()[0].is_ascii_lowercase()
        && !value.ends_with('-')
        && value
            .bytes()
            .all(|c| c.is_ascii_lowercase() || c.is_ascii_digit() || c == b'-')
}

#[must_use]
pub fn valid_request_id(value: &str) -> bool {
    !value.is_empty()
        && value.len() <= MAX_REQUEST_ID_BYTES
        && value
            .bytes()
            .all(|c| c.is_ascii_alphanumeric() || c == b'-')
}

fn lower_hex(value: &str, length: usize) -> bool {
    value.len() == length
        && value
            .bytes()
            .all(|byte| byte.is_ascii_digit() || (b'a'..=b'f').contains(&byte))
}

#[must_use]
pub fn valid_commit(value: &str) -> bool {
    lower_hex(value, GIT_COMMIT_HEX_BYTES)
}

#[must_use]
pub fn valid_sha256(value: &str) -> bool {
    lower_hex(value, SHA256_HEX_BYTES)
}

#[must_use]
pub fn hash(bytes: &[u8]) -> String {
    format!("{:x}", Sha256::digest(bytes))
}

#[must_use]
pub fn hex(bytes: &[u8]) -> String {
    use std::fmt::Write;
    let mut output = String::with_capacity(bytes.len() * 2);
    for byte in bytes {
        let _ = write!(output, "{byte:02x}");
    }
    output
}

/// Falha do contrato HTTP v1; detalhes internos não são enviados ao consumidor.
pub type Failure = (u16, &'static str);

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn names_and_request_ids_have_distinct_contracts() {
        assert!(valid_name("ec-build"));
        assert!(!valid_name("Ec"));
        assert!(!valid_name("ec-"));
        assert!(valid_request_id("Ec-1"));
        assert!(!valid_request_id("../ec"));
        assert!(!valid_request_id(&"a".repeat(MAX_REQUEST_ID_BYTES + 1)));
    }
    #[test]
    fn hashes_reject_short_uppercase_and_non_hex_identifiers() {
        assert!(valid_commit(&"a".repeat(40)));
        assert!(valid_sha256(&"0".repeat(64)));
        for value in ["a".repeat(39), "A".repeat(40), "g".repeat(40)] {
            assert!(!valid_commit(&value));
        }
        assert!(!valid_sha256(&"0".repeat(63)));
        assert!(!valid_sha256(&"G".repeat(64)));
    }
    #[test]
    fn public_bytes_and_fields_remain_wire_compatible() {
        assert_eq!(hex(&[0, 128, 255]), "0080ff");
        assert_eq!(
            hash(b""),
            "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
        );
        assert_eq!(PIPELINE_FIELDS.len(), 9);
        assert!(PIPELINE_FIELDS.contains(&"timeoutSeconds"));
        assert!(HEARTBEAT_AUTHORITY_SECONDS < u64::from(LEASE_SECONDS));
    }
}
