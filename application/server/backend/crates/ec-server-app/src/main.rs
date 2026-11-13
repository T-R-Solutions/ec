mod service;
use std::{io::Read, process::ExitCode};
fn main() -> ExitCode {
    if !matches!(
        std::env::args().nth(1).as_deref(),
        Some(
            "validate-yaml"
                | "migrate"
                | "provision"
                | "revoke"
                | "admit-repository"
                | "provision-agent"
                | "revoke-agent"
                | "serve"
        )
    ) {
        eprintln!(
            "Uso: validate-yaml | migrate | provision | revoke | admit-repository | provision-agent | revoke-agent | serve"
        );
        return ExitCode::from(2);
    }
    if std::env::args().nth(1).as_deref() != Some("validate-yaml") {
        return if matches!(service::run(), Ok(())) {
            ExitCode::SUCCESS
        } else {
            eprintln!("Falha de operação; consulte configuração e contrato local");
            ExitCode::from(1)
        };
    }
    let args: Vec<_> = std::env::args_os().skip(1).collect();
    if args.len() != 1 || args[0] != "validate-yaml" {
        eprintln!("Uso: ec-server-app validate-yaml < recurso.yaml");
        return ExitCode::from(2);
    }
    let mut input = String::new();
    if let Err(error) = std::io::stdin().take(65537).read_to_string(&mut input) {
        eprintln!("Leitura: {error}");
        return ExitCode::from(1);
    }
    let fields = match ec_server_configuration::parse(&input) {
        Ok(fields) => fields,
        Err(error) => {
            eprintln!(
                "{}: {} (linha {:?}, coluna {:?})",
                error.code, error.message, error.line, error.column
            );
            return ExitCode::from(1);
        }
    };
    match ec_server_projects::ProjectPipeline::validate(&fields) {
        Ok(_) => {
            println!("VALIDATED_LOCAL: não publicado, não autorizado para executar");
            ExitCode::SUCCESS
        }
        Err(error) => {
            eprintln!("{}: {}", error.field, error.message);
            ExitCode::from(1)
        }
    }
}
