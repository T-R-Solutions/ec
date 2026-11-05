//! Minimal resource semantics, independent of parser and storage technology.
use std::collections::BTreeMap;

#[derive(Debug, Clone, PartialEq, Eq)]
pub struct ProjectPipeline {
    pub project: String,
    pub pipeline: String,
    pub repository: String,
    pub commit: String,
    pub script: String,
    pub timeout_seconds: u32,
    pub artifact: String,
}

#[derive(Debug, Clone, PartialEq, Eq)]
pub struct InvalidField {
    pub field: String,
    pub message: &'static str,
}

impl ProjectPipeline {
    pub fn validate(fields: &BTreeMap<String, String>) -> Result<Self, InvalidField> {
        let allowed = [
            "apiVersion",
            "kind",
            "project",
            "pipeline",
            "repository",
            "commit",
            "script",
            "timeoutSeconds",
            "artifact",
        ];
        let error = |field: &str, message| InvalidField {
            field: field.into(),
            message,
        };
        for key in fields.keys() {
            if !allowed.contains(&key.as_str()) {
                return Err(error(
                    key,
                    "Campo desconhecido ou segredo inline não suportado",
                ));
            }
        }
        let get = |key: &str| {
            fields
                .get(key)
                .filter(|v| !v.is_empty())
                .cloned()
                .ok_or_else(|| error(key, "Campo obrigatório"))
        };
        if get("apiVersion")? != "ec/v1alpha1" {
            return Err(error("apiVersion", "Versão não suportada"));
        }
        if get("kind")? != "ProjectPipeline" {
            return Err(error("kind", "Kind não suportado"));
        }
        let name = |key: &str| -> Result<String, InvalidField> {
            let value = get(key)?;
            if value.len() > 64
                || !value.as_bytes()[0].is_ascii_lowercase()
                || value.ends_with('-')
                || !value
                    .bytes()
                    .all(|b| b.is_ascii_lowercase() || b.is_ascii_digit() || b == b'-')
            {
                return Err(error(key, "Nome inválido"));
            }
            Ok(value)
        };
        let project = name("project")?;
        let pipeline = name("pipeline")?;
        let repository = get("repository")?;
        if !repository.starts_with('/')
            || repository.contains('\0')
            || repository.split('/').any(|v| v == "..")
        {
            return Err(error(
                "repository",
                "Somente caminho local absoluto controlado",
            ));
        }
        let commit = get("commit")?;
        if commit.len() != 40
            || !commit
                .bytes()
                .all(|b| b.is_ascii_digit() || (b'a'..=b'f').contains(&b))
        {
            return Err(error("commit", "Exige SHA Git completo minúsculo"));
        }
        let script = get("script")?;
        if script.len() > 8192 || script.contains('\0') {
            return Err(error("script", "Script inválido ou maior que limite"));
        }
        let timeout = get("timeoutSeconds")?;
        if !timeout.bytes().all(|b| b.is_ascii_digit()) {
            return Err(error("timeoutSeconds", "Exige decimal entre 1 e 3600"));
        }
        let timeout_seconds = timeout
            .parse::<u32>()
            .ok()
            .filter(|v| (1..=3600).contains(v))
            .ok_or_else(|| error("timeoutSeconds", "Exige decimal entre 1 e 3600"))?;
        let artifact = get("artifact")?;
        if artifact.starts_with('/')
            || artifact.contains(['\\', '\0'])
            || artifact
                .split('/')
                .any(|p| p.is_empty() || p == "." || p == "..")
        {
            return Err(error("artifact", "Exige caminho relativo sem traversal"));
        }
        Ok(Self {
            project,
            pipeline,
            repository,
            commit,
            script,
            timeout_seconds,
            artifact,
        })
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    fn fields() -> BTreeMap<String, String> {
        [
            ("apiVersion", "ec/v1alpha1"),
            ("kind", "ProjectPipeline"),
            ("project", "ec"),
            ("pipeline", "build"),
            ("repository", "/tmp/source"),
            ("commit", "0123456789abcdef0123456789abcdef01234567"),
            ("script", "printf ok > result.txt"),
            ("timeoutSeconds", "30"),
            ("artifact", "result.txt"),
        ]
        .into_iter()
        .map(|(k, v)| (k.into(), v.into()))
        .collect()
    }
    #[test]
    fn accepts_complete_resource() {
        let value = ProjectPipeline::validate(&fields()).unwrap();
        assert_eq!(value.timeout_seconds, 30);
        assert_eq!(value.artifact, "result.txt");
    }
    #[test]
    fn rejects_unknown_missing_secrets_and_invalid_references() {
        for (key, value) in [
            ("password", "secret"),
            ("token", "secret"),
            ("apiVersion", "ec/v9"),
            ("kind", "Other"),
            ("project", "Invalid"),
            ("pipeline", ""),
            ("commit", "main"),
            ("repository", "../source"),
            ("artifact", "../outside"),
            ("artifact", "/etc/passwd"),
            ("artifact", "a//b"),
            ("timeoutSeconds", "0"),
            ("timeoutSeconds", "3601"),
            ("timeoutSeconds", "null"),
            ("timeoutSeconds", "999999999999999"),
            ("script", ""),
        ] {
            let mut map = fields();
            map.insert(key.into(), value.into());
            assert!(
                ProjectPipeline::validate(&map).is_err(),
                "accepted {key}={value}"
            );
        }
        for key in fields().keys() {
            let mut map = fields();
            map.remove(key);
            assert_eq!(ProjectPipeline::validate(&map).unwrap_err().field, *key);
        }
    }
}
