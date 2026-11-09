use std::{
    io::Write,
    process::{Command, Stdio},
};
const YAML: &str = include_str!("../../../../../integration/tests/fixtures/project-pipeline.yaml");

fn invoke(args: &[&str], input: &[u8]) -> std::process::Output {
    let mut child = Command::new(env!("CARGO_BIN_EXE_ec-server-app"))
        .args(args)
        .stdin(Stdio::piped())
        .stdout(Stdio::piped())
        .stderr(Stdio::piped())
        .spawn()
        .unwrap();
    child.stdin.take().unwrap().write_all(input).unwrap();
    child.wait_with_output().unwrap()
}

#[test]
fn process_reports_only_local_validation() {
    let output = invoke(&["validate-yaml"], YAML.as_bytes());
    assert!(output.status.success(), "{:?}", output);
    assert!(
        String::from_utf8(output.stdout)
            .unwrap()
            .contains("VALIDATED_LOCAL")
    );
    assert!(output.stderr.is_empty());
}

#[test]
fn rejected_inputs_and_usage_have_failure_status() {
    for input in [
        YAML.replace("ec/v1alpha1", "ec/v999"),
        format!("{YAML}\nproject: duplicate"),
        format!("{YAML}\ntoken: never-print-this-value"),
    ] {
        let output = invoke(&["validate-yaml"], input.as_bytes());
        assert_eq!(output.status.code(), Some(1));
        assert!(output.stdout.is_empty());
        assert!(!String::from_utf8_lossy(&output.stderr).contains("never-print-this-value"));
    }
    assert_eq!(invoke(&[], b"").status.code(), Some(2));
    assert_eq!(
        invoke(&["validate-yaml"], &[0xff, 0xfe]).status.code(),
        Some(1)
    );
}

#[test]
fn supported_resource_round_trip_preserves_all_fields() {
    let fields = ec_server_configuration::parse(YAML).unwrap();
    let model = ec_server_projects::ProjectPipeline::validate(&fields).unwrap();
    let encoded = ec_server_configuration::serialize(&fields);
    let decoded = ec_server_configuration::parse(&encoded).unwrap();
    assert_eq!(decoded, fields);
    assert_eq!(
        ec_server_projects::ProjectPipeline::validate(&decoded).unwrap(),
        model
    );
}
