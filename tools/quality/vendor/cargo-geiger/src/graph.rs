pub mod extra_deps;

use extra_deps::ExtraDeps;

use crate::args::{Args, DepsArgs, TargetArgs};
use crate::cli::get_cfgs;
use crate::mapping::{CargoMetadataParameters, DepsNotReplaced};

use cargo::util::CargoResult;
use cargo_platform::Cfg;
use krates::cm::{Dependency, DependencyKind, Package, PackageId};
use krates::{Kid, Node};
use petgraph::graph::NodeIndex;
use std::collections::hash_map::Entry;
use std::collections::HashMap;
use std::path::PathBuf;

/// Representation of the package dependency graph
pub struct Graph {
    pub graph: petgraph::Graph<PackageId, DependencyKind>,
    pub nodes: HashMap<PackageId, NodeIndex>,
}

// Almost unmodified compared to the original in cargo-tree, should be fairly
// simple to move this and the dependency graph structure out to a library.
/// Function to build a graph of packages dependencies
pub fn build_graph<'a>(
    args: &Args,
    cargo_metadata_parameters: &'a CargoMetadataParameters,
    config_host: &'a str,
    global_rustc_path: &'a PathBuf,
    root_package_id: PackageId,
) -> CargoResult<Graph> {
    let (extra_deps, target) =
        build_graph_prerequisites(config_host, &args.deps_args, &args.target_args);
    let cfgs = get_cfgs(global_rustc_path, &args.target_args.target)?;

    let mut graph = Graph {
        graph: petgraph::Graph::new(),
        nodes: HashMap::new(),
    };
    graph.nodes.insert(
        root_package_id.clone(),
        graph.graph.add_node(root_package_id.clone()),
    );

    let mut pending_packages = vec![root_package_id.clone()];

    let graph_configuration = GraphConfiguration {
        target,
        cfgs: cfgs.as_deref(),
        extra_deps,
    };

    while let Some(package_id) = pending_packages.pop() {
        let is_root_package = package_id == root_package_id;
        add_package_dependencies_to_graph(
            cargo_metadata_parameters,
            package_id,
            &graph_configuration,
            &mut graph,
            &mut pending_packages,
            is_root_package,
        )?;
    }

    Ok(graph)
}

struct GraphConfiguration<'a> {
    target: Option<&'a str>,
    cfgs: Option<&'a [Cfg]>,
    extra_deps: ExtraDeps,
}

fn add_graph_node_if_not_present_and_edge(
    dependency: &Dependency,
    dependency_package_id: PackageId,
    graph: &mut Graph,
    index: NodeIndex,
    pending_packages: &mut Vec<PackageId>,
) {
    let dependency_index = match graph.nodes.entry(dependency_package_id.clone()) {
        Entry::Occupied(e) => *e.get(),
        Entry::Vacant(e) => {
            pending_packages.push(dependency_package_id.clone());
            *e.insert(graph.graph.add_node(dependency_package_id))
        }
    };
    graph
        .graph
        .add_edge(index, dependency_index, dependency.kind);
}

fn add_package_dependencies_to_graph(
    cargo_metadata_parameters: &CargoMetadataParameters,
    package_id: PackageId,
    graph_configuration: &GraphConfiguration,
    graph: &mut Graph,
    pending_packages: &mut Vec<PackageId>,
    is_root_package: bool,
) -> CargoResult<()> {
    let index = graph.nodes[&package_id];
    let kid: Kid = package_id.clone().into();

    let krates_node_option = cargo_metadata_parameters.krates.node_for_kid(&kid);

    let dep_not_replaced_option = cargo_metadata_parameters
        .metadata
        .deps_not_replaced(&package_id, is_root_package);

    match (krates_node_option, dep_not_replaced_option) {
        (Some(Node::Krate { krate, .. }), Some(dependencies)) => {
            for (dependency_package_id, _) in dependencies {
                let dependency_iterator = filter_dependencies(
                    cargo_metadata_parameters,
                    &dependency_package_id,
                    graph_configuration,
                    krate,
                )?;

                for dependency in dependency_iterator {
                    add_graph_node_if_not_present_and_edge(
                        dependency,
                        dependency_package_id.clone(),
                        graph,
                        index,
                        pending_packages,
                    );
                }
            }
        }
        _ => {
            anyhow::bail!("Dependência ativa ausente do grafo: {}", package_id)
        }
    }
    Ok(())
}

fn build_graph_prerequisites<'a>(
    config_host: &'a str,
    deps_args: &'a DepsArgs,
    target_args: &'a TargetArgs,
) -> (ExtraDeps, Option<&'a str>) {
    let extra_deps = if deps_args.all_deps {
        ExtraDeps::All
    } else if deps_args.build_deps {
        ExtraDeps::Build
    } else if deps_args.dev_deps {
        ExtraDeps::Dev
    } else {
        ExtraDeps::NoMore
    };

    let target = if target_args.all_targets {
        None
    } else {
        Some(target_args.target.as_deref().unwrap_or(config_host))
    };

    (extra_deps, target)
}

fn optional_dependency_enabled(
    package: &Package,
    dependency: &Dependency,
    features: &[String],
) -> bool {
    if !dependency.optional {
        return true;
    }
    let alias = dependency.rename.as_deref().unwrap_or(&dependency.name);
    features.iter().any(|feature| {
        package.features.get(feature).is_some_and(|values| {
            values.iter().any(|value| {
                value == &format!("dep:{alias}") || value.starts_with(&format!("{alias}/"))
            })
        })
    })
}

fn filter_dependencies<'a>(
    cargo_metadata_parameters: &'a CargoMetadataParameters,
    dependency_package_id: &'a PackageId,
    graph_configuration: &'a GraphConfiguration,
    package: &'a Package,
) -> CargoResult<Vec<&'a Dependency>> {
    let resolved = cargo_metadata_parameters
        .metadata
        .packages
        .iter()
        .find(|package| package.id == *dependency_package_id)
        .ok_or_else(|| {
            anyhow::anyhow!("Dependência resolvida ausente: {}", dependency_package_id)
        })?;
    let features = &cargo_metadata_parameters
        .metadata
        .resolve
        .as_ref()
        .and_then(|resolve| resolve.nodes.iter().find(|node| node.id == package.id))
        .ok_or_else(|| anyhow::anyhow!("Features resolvidas ausentes: {}", package.id))?
        .features;
    let mut selected = Vec::new();
    for dependency in &package.dependencies {
        // Filtrar plataforma/tipo antes da busca no Krates: cfg(any()) é
        // deliberadamente inativo, embora exista no metadata multiplataforma.
        let matches = dependency.name == resolved.name && dependency.req.matches(&resolved.version);
        if matches
            && optional_dependency_enabled(package, dependency, features)
            && graph_configuration.extra_deps.allows(dependency.kind)
            && dependency
                .target
                .as_ref()
                .and_then(|platform| {
                    graph_configuration
                        .target
                        .map(|target| match graph_configuration.cfgs {
                            None => false,
                            Some(cfgs) => platform.matches(target, cfgs),
                        })
                })
                .unwrap_or(true)
        {
            let kid: Kid = dependency_package_id.clone().into();
            anyhow::ensure!(
                cargo_metadata_parameters
                    .krates
                    .node_for_kid(&kid)
                    .is_some(),
                "Dependência ativa não associada: {}",
                dependency_package_id
            );
            selected.push(dependency);
        }
    }
    Ok(selected)
}

#[cfg(test)]
mod graph_tests {
    use super::*;
    use rstest::*;

    #[test]
    fn optional_activation_honors_renames_explicit_dep_and_weak_features() {
        let (_, metadata) = crate::lib_tests::construct_krates_and_metadata();
        let mut package = metadata.root_package().unwrap().clone();
        let mut dependency = package.dependencies[0].clone();
        dependency.optional = true;
        dependency.rename = Some("serialization".into());
        package
            .features
            .insert("serialization".into(), vec!["dep:serialization".into()]);
        package
            .features
            .insert("std".into(), vec!["serialization?/std".into()]);
        package
            .features
            .insert("explicit".into(), vec!["dep:serialization".into()]);
        package
            .features
            .insert("strong".into(), vec!["serialization/std".into()]);
        assert!(!optional_dependency_enabled(
            &package,
            &dependency,
            &["std".into()]
        ));
        assert!(!optional_dependency_enabled(&package, &dependency, &[]));
        for feature in ["serialization", "explicit", "strong"] {
            assert!(optional_dependency_enabled(
                &package,
                &dependency,
                &[feature.into()]
            ));
        }
    }

    #[test]
    fn same_named_feature_without_dep_reference_does_not_activate_optional_dependency() {
        let (_, metadata) = crate::lib_tests::construct_krates_and_metadata();
        let mut package = metadata.root_package().unwrap().clone();
        let mut dependency = package.dependencies[0].clone();
        dependency.optional = true;
        dependency.rename = Some("serialization".into());
        package.features.insert("serialization".into(), vec![]);
        assert!(!optional_dependency_enabled(
            &package,
            &dependency,
            &["serialization".into()]
        ));
    }

    #[test]
    fn inactive_platform_edge_is_skipped_but_active_missing_node_fails() {
        let (krates, mut metadata) = crate::lib_tests::construct_krates_and_metadata();
        let mut parent = metadata.root_package().unwrap().clone();
        let mut package = metadata
            .packages
            .iter()
            .find(|p| p.id != parent.id)
            .unwrap()
            .clone();
        package.id = PackageId {
            repr: "registry+https://example.invalid#inactive@1.0.0".into(),
        };
        package.name = "inactive".into();
        let mut dependency = parent.dependencies[0].clone();
        dependency.name = package.name.clone();
        dependency.req = "*".parse().unwrap();
        dependency.target = Some("cfg(any())".parse().unwrap());
        parent.dependencies = vec![dependency];
        let id = package.id.clone();
        metadata.packages.push(package);
        let parameters = CargoMetadataParameters {
            krates: &krates,
            metadata: &metadata,
        };
        let configuration = GraphConfiguration {
            extra_deps: ExtraDeps::All,
            target: Some("x86_64-unknown-linux-gnu"),
            cfgs: Some(&[]),
        };
        assert!(
            filter_dependencies(&parameters, &id, &configuration, &parent)
                .unwrap()
                .is_empty()
        );
        parent.dependencies[0].target = None;
        assert!(filter_dependencies(&parameters, &id, &configuration, &parent).is_err());
    }

    #[test]
    fn missing_active_package_is_an_error_instead_of_a_dropped_edge() {
        let (krates, metadata) = crate::lib_tests::construct_krates_and_metadata();
        let parameters = CargoMetadataParameters {
            krates: &krates,
            metadata: &metadata,
        };
        let configuration = GraphConfiguration {
            extra_deps: ExtraDeps::All,
            target: None,
            cfgs: None,
        };
        let missing = PackageId {
            repr: "registry+https://example.invalid#active@1.0.0".into(),
        };
        assert!(filter_dependencies(
            &parameters,
            &missing,
            &configuration,
            metadata.root_package().unwrap()
        )
        .is_err());
    }

    #[rstest(
        input_deps_args,
        expected_extra_deps,
        case(
            DepsArgs {
                all_deps: true,
                build_deps: false,
                dev_deps: false
            },
            ExtraDeps::All
        ),
        case(
            DepsArgs {
                all_deps: false,
                build_deps: true,
                dev_deps: false
            },
            ExtraDeps::Build
        ),
        case(
            DepsArgs {
                all_deps: false,
                build_deps: false,
                dev_deps: true
            },
            ExtraDeps::Dev
        ),
        case(
            DepsArgs {
                all_deps: false,
                build_deps: false,
                dev_deps: false
            },
            ExtraDeps::NoMore
        )
    )]
    fn build_graph_prerequisites_extra_deps_test(
        input_deps_args: DepsArgs,
        expected_extra_deps: ExtraDeps,
    ) {
        let config_host = "config_host";
        let target_args = TargetArgs::default();

        let (extra_deps, _) =
            build_graph_prerequisites(config_host, &input_deps_args, &target_args);

        assert_eq!(extra_deps, expected_extra_deps);
    }

    #[rstest(
        input_target_args,
        expected_target,
        case(
            TargetArgs {
                all_targets: true,
                target: None
            },
            None
        ),
        case(
            TargetArgs {
                all_targets: false,
                target: None
            },
            Some("default_config_host")),
        case(
            TargetArgs {
                all_targets: false,
                target: Some(String::from("provided_config_host")),
            },
            Some("provided_config_host")
        )
    )]
    fn build_graph_prerequisites_all_targets_test(
        input_target_args: TargetArgs,
        expected_target: Option<&str>,
    ) {
        let config_host = "default_config_host";
        let deps_args = DepsArgs::default();

        let (_, target) = build_graph_prerequisites(config_host, &deps_args, &input_target_args);

        assert_eq!(target, expected_target);
    }
}
