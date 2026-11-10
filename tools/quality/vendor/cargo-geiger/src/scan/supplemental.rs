//! Reconcile rustc dep-info with the source-tree scan. Never waive unknown inputs.
use super::{GeigerContext, RsFileMetricsWrapper};
use geiger::{find::find_unsafe_in_file, IncludeTests};
use std::collections::HashSet;
use std::path::{Path, PathBuf};
use syn::visit::Visit;

#[derive(Default)]
struct Includes {
    data: HashSet<PathBuf>,
    code: HashSet<PathBuf>,
    base: PathBuf,
}

impl<'ast> Visit<'ast> for Includes {
    fn visit_macro(&mut self, node: &'ast syn::Macro) {
        let destination =
            if node.path.is_ident("include_str") || node.path.is_ident("include_bytes") {
                &mut self.data
            } else if node.path.is_ident("include") {
                &mut self.code
            } else {
                return;
            };
        // Dynamic paths are deliberately unresolved and retain the native warning.
        if let Ok(literal) = syn::parse2::<syn::LitStr>(node.tokens.clone()) {
            if let Ok(path) = self.base.join(literal.value()).canonicalize() {
                destination.insert(path);
            }
        }
    }
}

fn includes(path: &Path) -> Result<Includes, String> {
    let text = std::fs::read_to_string(path).map_err(|e| format!("{}: {e}", path.display()))?;
    let syntax = syn::parse_file(&text).map_err(|e| format!("{}: {e}", path.display()))?;
    let mut visitor = Includes {
        base: path.parent().ok_or("source without parent")?.to_owned(),
        ..Includes::default()
    };
    visitor.visit_file(&syntax);
    Ok(visitor)
}

pub(super) fn complete(
    context: &mut GeigerContext,
    used: &mut HashSet<PathBuf>,
    dependency_groups: &[HashSet<PathBuf>],
    include_tests: IncludeTests,
) -> Result<(), String> {
    let mut data = HashSet::new();
    let mut code = HashSet::new();
    let mut scanned = HashSet::new();
    for metrics in context.package_id_to_metrics.values() {
        for path in metrics.rs_path_to_metrics.keys() {
            scanned.insert(path.clone());
            let found = includes(path)?;
            data.extend(found.data);
            code.extend(found.code);
        }
    }
    // Include a generated source in its real package's metrics, using shared
    // dep-info with that package's scanned sources. Ambiguous ownership is fatal.
    let mut remaining = used.difference(&scanned).cloned().collect::<Vec<_>>();
    remaining.sort();
    for path in remaining {
        if context.ignored_paths.contains(&path) {
            continue;
        }
        if data.contains(&path)
            && !code.contains(&path)
            && path.extension().is_none_or(|e| e != "rs")
        {
            // A proven data include is not Rust source. Keep an explicit record.
            eprintln!(
                "Build data (include_str!/include_bytes!, not Rust): {}",
                path.display()
            );
            context.ignored_paths.insert(path);
            continue;
        }
        if path.extension().is_none_or(|e| e != "rs") && !code.contains(&path) {
            continue; // Unknown input: the existing warning/error remains active.
        }
        let owners = context
            .package_id_to_metrics
            .iter()
            .filter(|(_, metrics)| {
                dependency_groups.iter().any(|group| {
                    group.contains(&path)
                        && metrics.rs_path_to_metrics.keys().any(|p| group.contains(p))
                })
            })
            .map(|(id, _)| id.clone())
            .collect::<Vec<_>>();
        if owners.len() != 1 {
            return Err(format!(
                "Cannot attribute build source {}: {} candidate packages",
                path.display(),
                owners.len()
            ));
        }
        let metrics = find_unsafe_in_file(&path, include_tests)
            .map_err(|e| format!("Cannot scan build source {}: {e:?}", path.display()))?;
        eprintln!(
            "Scanned build source: {} (package {})",
            path.display(),
            owners[0]
        );
        context
            .package_id_to_metrics
            .get_mut(&owners[0])
            .unwrap()
            .rs_path_to_metrics
            .insert(
                path,
                RsFileMetricsWrapper {
                    metrics,
                    is_crate_entry_point: false,
                },
            );
    }
    Ok(())
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::scan::PackageMetrics;

    #[test]
    fn build_data_and_generated_unsafe_are_accounted_separately() {
        let dir = tempfile::tempdir().unwrap();
        let root = dir.path().join("lib.rs");
        let doc = dir.path().join("README.md");
        let generated = dir.path().join("private.rs");
        let unknown = dir.path().join("unknown.txt");
        std::fs::write(
            &root,
            "#![doc = include_str!(\"README.md\")]\npub fn root() {}\n",
        )
        .unwrap();
        std::fs::write(&doc, "# Documentation, not Rust").unwrap();
        std::fs::write(&generated, "pub unsafe fn generated() {}\n").unwrap();
        std::fs::write(&unknown, "unknown build input").unwrap();
        let id = krates::cm::PackageId {
            repr: "path+file:///fixture#fixture@0.1.0".into(),
        };
        let mut context = GeigerContext::default();
        let mut package = PackageMetrics::default();
        package
            .rs_path_to_metrics
            .insert(root.clone(), RsFileMetricsWrapper::default());
        context.package_id_to_metrics.insert(id.clone(), package);
        let mut used = HashSet::from([
            root.clone(),
            doc.clone(),
            generated.clone(),
            unknown.clone(),
        ]);
        let groups = vec![used.clone()];
        complete(&mut context, &mut used, &groups, IncludeTests::Yes).unwrap();
        assert!(used.contains(&doc));
        assert!(context.ignored_paths.contains(&doc));
        assert!(used.contains(&unknown));
        let metrics = &context.package_id_to_metrics[&id].rs_path_to_metrics[&generated];
        assert_eq!(metrics.metrics.counters.functions.unsafe_, 1);
        assert!(!metrics.is_crate_entry_point);
        assert_eq!(
            super::super::list_files_used_but_not_scanned(&context, &used),
            vec![unknown]
        );
    }

    #[test]
    fn a_non_rust_extension_does_not_waive_code_or_dynamic_includes() {
        let dir = tempfile::tempdir().unwrap();
        let root = dir.path().join("lib.rs");
        let included = dir.path().join("code.inc");
        std::fs::write(&included, "pub unsafe fn included() {}\n").unwrap();
        std::fs::write(&root, "const DATA: &str = include_str!(\"code.inc\"); include!(\"code.inc\"); const OTHER: &str = include_str!(concat!(\"READ\", \"ME.md\"));").unwrap();
        let found = includes(&root).unwrap();
        assert!(found.data.contains(&included));
        assert!(found.code.contains(&included));
        assert_eq!(found.data.len(), 1);
        let mut used = HashSet::from([included]);
        assert!(complete(
            &mut GeigerContext::default(),
            &mut used,
            &[],
            IncludeTests::Yes
        )
        .is_ok());
        // Without a scanned consumer this remains unknown; it is never waived.
        assert_eq!(used.len(), 1);
    }

    #[test]
    fn included_code_is_scanned_even_when_also_read_as_data() {
        let dir = tempfile::tempdir().unwrap();
        let root = dir.path().join("lib.rs");
        let included = dir.path().join("code.inc");
        std::fs::write(&included, "pub unsafe fn included() {}\n").unwrap();
        std::fs::write(&root, "const DATA: &str = include_str!(\"code.inc\"); include!(\"code.inc\");").unwrap();
        let id = krates::cm::PackageId { repr: "fixture".into() };
        let mut context = GeigerContext::default();
        let mut package = PackageMetrics::default();
        package.rs_path_to_metrics.insert(root.clone(), RsFileMetricsWrapper::default());
        context.package_id_to_metrics.insert(id.clone(), package);
        let mut used = HashSet::from([root, included.clone()]);
        let groups = vec![used.clone()];
        complete(&mut context, &mut used, &groups, IncludeTests::Yes).unwrap();
        assert!(!context.ignored_paths.contains(&included));
        assert_eq!(context.package_id_to_metrics[&id].rs_path_to_metrics[&included].metrics.counters.functions.unsafe_, 1);
    }

    #[test]
    fn ambiguous_ownership_and_invalid_generated_rust_are_errors() {
        let dir = tempfile::tempdir().unwrap();
        let generated = dir.path().join("private.rs");
        std::fs::write(&generated, "this is not Rust").unwrap();
        let mut context = GeigerContext::default();
        let mut group = HashSet::from([generated.clone()]);
        for n in 0..2 {
            let root = dir.path().join(format!("root{n}.rs"));
            std::fs::write(&root, "pub fn f() {}\n").unwrap();
            let mut package = PackageMetrics::default();
            package.rs_path_to_metrics.insert(root.clone(), RsFileMetricsWrapper::default());
            context.package_id_to_metrics.insert(krates::cm::PackageId { repr: format!("fixture{n}") }, package);
            group.insert(root);
        }
        let mut used = group.clone();
        let groups = vec![group];
        let error = complete(&mut context, &mut used, &groups, IncludeTests::Yes).unwrap_err();
        assert!(error.contains("2 candidate packages"));
        context.package_id_to_metrics.remove(&krates::cm::PackageId { repr: "fixture1".into() });
        let error = complete(&mut context, &mut used, &groups, IncludeTests::Yes).unwrap_err();
        assert!(error.contains("Cannot scan build source"));
        assert!(!context.ignored_paths.contains(&generated));
    }

    #[test]
    fn generated_code_without_an_owner_is_an_error() {
        let mut used = HashSet::from([PathBuf::from("missing.rs")]);
        assert!(complete(
            &mut GeigerContext::default(),
            &mut used,
            &[],
            IncludeTests::Yes
        )
        .is_err());
    }
}
