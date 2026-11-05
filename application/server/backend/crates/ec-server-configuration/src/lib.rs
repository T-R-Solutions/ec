//! Bounded YAML boundary. No persistence, execution or authorization occurs here.
use std::collections::BTreeMap;
use yaml_rust2::{
    parser::{Event, Parser},
    scanner::TScalarStyle,
};

pub const MAX_INPUT_BYTES: usize = 65536;
pub const MAX_SCALAR_BYTES: usize = 8192;
pub const MAX_EVENTS: usize = 4096;

#[derive(Debug, Clone, PartialEq, Eq)]
pub struct Diagnostic {
    pub code: &'static str,
    pub message: String,
    pub line: Option<usize>,
    pub column: Option<usize>,
}

impl Diagnostic {
    fn plain(code: &'static str, message: &str) -> Self {
        Self {
            code,
            message: message.into(),
            line: None,
            column: None,
        }
    }
}

/// Parse exactly one flat mapping. Detect duplicates before map insertion.
/// Fields remain text; the resource validator owns schema interpretation.
pub fn parse(input: &str) -> Result<BTreeMap<String, String>, Diagnostic> {
    if input.len() > MAX_INPUT_BYTES {
        return Err(Diagnostic::plain("input_limit", "YAML excede 64 KiB"));
    }
    let mut parser = Parser::new_from_str(input);
    let mut fields = BTreeMap::new();
    let mut documents = 0;
    let mut opened = false;
    let mut closed = false;
    let mut key: Option<String> = None;
    for _ in 0..MAX_EVENTS {
        let (event, marker) = parser.next_token().map_err(|e| Diagnostic {
            code: "syntax",
            message: e.to_string(),
            line: Some(e.marker().line()),
            column: Some(e.marker().col() + 1),
        })?;
        let reject = |code, message: &str| Diagnostic {
            code,
            message: message.into(),
            line: Some(marker.line()),
            column: Some(marker.col() + 1),
        };
        match event {
            Event::StreamStart | Event::DocumentEnd => {}
            Event::DocumentStart => {
                documents += 1;
                if documents > 1 {
                    return Err(reject("documents", "Apenas um documento é permitido"));
                }
            }
            Event::MappingStart(anchor, tag) => {
                if anchor != 0 || tag.is_some() {
                    return Err(reject("construction", "Tags e anchors não são permitidos"));
                }
                if opened {
                    return Err(reject(
                        "shape",
                        "Schema exige campos escalares sem coleções aninhadas",
                    ));
                }
                opened = true;
            }
            Event::MappingEnd => {
                closed = true;
            }
            Event::Scalar(value, style, anchor, tag) => {
                if style == TScalarStyle::Plain
                    && matches!(value.as_str(), "" | "~" | "null" | "Null" | "NULL")
                {
                    return Err(reject("null", "Null não pertence a este schema"));
                }
                if anchor != 0 || tag.is_some() {
                    return Err(reject("construction", "Tags e anchors não são permitidos"));
                }
                if !opened || closed {
                    return Err(reject("shape", "Recurso deve ser um mapa"));
                }
                if value.len() > MAX_SCALAR_BYTES {
                    return Err(reject("scalar_limit", "Campo excede 8192 bytes"));
                }
                if let Some(name) = key.take() {
                    fields.insert(name, value);
                } else {
                    if value == "<<" {
                        return Err(reject("merge", "Merge keys não são permitidas"));
                    }
                    if fields.contains_key(&value) {
                        return Err(reject("duplicate", "Chave duplicada"));
                    }
                    key = Some(value);
                }
            }
            Event::Alias(_) => return Err(reject("construction", "Aliases não são permitidos")),
            Event::SequenceStart(_, _) | Event::SequenceEnd => {
                return Err(reject("shape", "Sequências não pertencem a este schema"));
            }
            Event::StreamEnd => {
                if documents == 1 && opened && closed && key.is_none() {
                    return Ok(fields);
                }
                return Err(reject("shape", "Documento vazio ou incompleto"));
            }
            Event::Nothing => return Err(reject("syntax", "Evento inesperado")),
        }
    }
    Err(Diagnostic::plain(
        "event_limit",
        "Limite de eventos excedido",
    ))
}

/// Semantic serialization; callers retain original source for comments/diffs.
pub fn serialize(fields: &BTreeMap<String, String>) -> String {
    fn quote(value: &str) -> String {
        let mut out = String::from("\"");
        for ch in value.chars() {
            match ch {
                '"' => out.push_str("\\\""),
                '\\' => out.push_str("\\\\"),
                '\n' => out.push_str("\\n"),
                '\r' => out.push_str("\\r"),
                '\t' => out.push_str("\\t"),
                ch if ch.is_control() => out.push_str(&format!("\\u{:04x}", ch as u32)),
                ch => out.push(ch),
            }
        }
        out.push('"');
        out
    }
    fields
        .iter()
        .map(|(k, v)| format!("{}: {}\n", quote(k), quote(v)))
        .collect()
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn duplicate_resolved_keys_rejected() {
        assert_eq!(
            parse("name: first\n\"name\": second").unwrap_err().code,
            "duplicate"
        );
    }
    #[test]
    fn dangerous_or_lossy_constructions_rejected() {
        for input in [
            "x: &a value",
            "x: !Custom value",
            "x: !!str value",
            "x: *missing",
            "x: {nested: value}",
            "x: [one]",
            "? [one, two]\n: value",
            "<<: value",
            "x: one\n---\nx: two",
            "",
            "hello",
            "x: \0",
            "x: null",
            "x: ~",
            "x:",
        ] {
            assert!(parse(input).is_err(), "accepted {input:?}");
        }
    }
    #[test]
    fn bounds_and_diagnostics() {
        assert_eq!(
            parse(&"x".repeat(MAX_INPUT_BYTES + 1)).unwrap_err().code,
            "input_limit"
        );
        assert_eq!(
            parse(&format!("x: {}", "a".repeat(MAX_SCALAR_BYTES + 1)))
                .unwrap_err()
                .code,
            "scalar_limit"
        );
        let events = (0..2100).map(|i| format!("k{i}: v\n")).collect::<String>();
        assert_eq!(parse(&events).unwrap_err().code, "event_limit");
        let error = parse("x: [").unwrap_err();
        assert!(error.line.is_some());
    }
    #[test]
    fn round_trip_preserves_semantics_and_script_punctuation() {
        let input =
            "project: 'yes'\nscript: |\n  echo 'não' && echo \"ok\"\n  printf '! & * # : {}'\n";
        let fields = parse(input).unwrap();
        assert_eq!(parse(&serialize(&fields)).unwrap(), fields);
        assert_eq!(fields["project"], "yes");
        assert!(fields["script"].contains("&&"));
    }
}
