use std::{env, error::Error, fmt::Write, fs, path::PathBuf};

fn main() -> Result<(), Box<dyn Error>> {
    println!("cargo::rerun-if-changed=pipeline-v1.json");
    let descriptor: serde_json::Value =
        serde_json::from_str(&fs::read_to_string("pipeline-v1.json")?)?;
    let mut output = String::from("// Gerado a partir do descritor público v1; não editar.\n");
    let fields = descriptor["fields"].as_array().ok_or("campos ausentes")?;
    let mut names = std::collections::BTreeSet::new();
    writeln!(output, "pub const PIPELINE_FIELDS: &[&str] = &[")?;
    for field in fields {
        let name = field.as_str().ok_or("campo inválido")?;
        if !names.insert(name) {
            return Err("campo duplicado".into());
        }
        writeln!(output, "{name:?},")?;
    }
    writeln!(output, "];")?;
    for (name, item) in descriptor["constants"]
        .as_object()
        .ok_or("constantes ausentes")?
    {
        if name.is_empty()
            || name.as_bytes()[0].is_ascii_digit()
            || !name
                .bytes()
                .all(|c| c.is_ascii_uppercase() || c.is_ascii_digit() || c == b'_')
        {
            return Err("identificador inválido".into());
        }
        let kind = item["rust_type"].as_str().ok_or("tipo ausente")?;
        if kind == "str" {
            let value = item["value"].as_str().ok_or("texto inválido")?;
            writeln!(output, "pub const {name}: &str = {value:?};")?;
        } else if matches!(kind, "usize" | "u64" | "u32" | "i64" | "i32") {
            let value = item["value"].as_u64().ok_or("limite inválido")?;
            if value == 0 {
                return Err("limite não positivo".into());
            }
            let mut literal = String::new();
            for (index, character) in value.to_string().chars().rev().enumerate() {
                if index > 0 && index.is_multiple_of(3) {
                    literal.push('_');
                }
                literal.push(character);
            }
            let literal: String = literal.chars().rev().collect();
            writeln!(output, "pub const {name}: {kind} = {literal};")?;
        } else {
            return Err("tipo não suportado".into());
        }
    }
    let destination = PathBuf::from(env::var_os("OUT_DIR").ok_or("OUT_DIR ausente")?);
    fs::write(destination.join("v1.rs"), output)?;
    Ok(())
}
