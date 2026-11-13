"""Inclui jornadas reais nos perfis LLVM do componente, dentro do run atual."""

import argparse
import os
import shlex
import subprocess
import sys
from pathlib import Path

if __package__ in (None, ""):
    sys.path.insert(0, str(Path(__file__).resolve().parents[2]))
from tools.quality.automation_config import CODE, load_config


def coverage_environment(output, parent):
    """Interpreta atribuições do show-env; nunca executa texto como shell."""
    result = dict(parent)
    allowed = {
        "RUSTFLAGS",
        "RUSTDOCFLAGS",
        "LLVM_PROFILE_FILE",
        "CARGO_TARGET_DIR",
        "CARGO_LLVM_COV_TARGET_DIR",
        "CARGO_LLVM_COV_BUILD_DIR",
        "CARGO_LLVM_COV",
        "CARGO_LLVM_COV_SHOW_ENV",
        "CARGO_ENCODED_RUSTFLAGS",
        "CARGO_ENCODED_RUSTDOCFLAGS",
        "__CARGO_LLVM_COV_RUSTC_WRAPPER",
        "__CARGO_LLVM_COV_RUSTC_WRAPPER_RUSTFLAGS",
        "__CARGO_LLVM_COV_RUSTC_WRAPPER_CRATE_NAMES",
        "RUSTC_WRAPPER",
    }
    for line in output.splitlines():
        if not line.strip():
            continue
        tokens = shlex.split(line)
        if len(tokens) != 1 or "=" not in tokens[0]:
            raise ValueError("show-env não contém atribuição literal única")
        key, value = tokens[0].split("=", 1)
        if key not in allowed:
            raise ValueError("variável show-env desconhecida: " + key)
        result[key] = value
    flags = (
        result.get("RUSTFLAGS", "")
        + result.get("CARGO_ENCODED_RUSTFLAGS", "")
        + result.get("__CARGO_LLVM_COV_RUSTC_WRAPPER_RUSTFLAGS", "")
    )
    if not result.get("LLVM_PROFILE_FILE") or "instrument-coverage" not in flags:
        raise ValueError("instrumentação LLVM ausente")
    return result


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--channel", required=True)
    parser.add_argument("--component", choices=("server", "agent"), required=True)
    args = parser.parse_args()
    root = Path(os.environ.get("EC_ROOT", str(CODE)))
    coverage_dir = Path(os.environ["COVERAGE_DIR"]).resolve(strict=True)
    config = load_config()
    workspaces = {
        name: root / item["root"]
        for name, item in config["components"].items()
        if item["language"] == "rust"
    }
    workspace = workspaces[args.component]
    cargo = ["rustup", "run", args.channel, "cargo"]
    # Executável local conhecido; stdout é um formato de dados validado acima.
    output = subprocess.run(  # Cargo local selecionado, argv separado, sem shell; stderr preservado no run.
        cargo + ["llvm-cov", "show-env", "--offline"],
        cwd=workspace,
        check=True,
        stdout=subprocess.PIPE,
        text=True,
    ).stdout
    (coverage_dir / "llvm-show-env.txt").write_text(output)
    environment = coverage_environment(output, os.environ)
    target = Path(environment["CARGO_TARGET_DIR"]).resolve()
    if target != coverage_dir / "target":
        raise ValueError("target LLVM fora da área privada desta medição")
    subprocess.run(  # Cargo fixo, workspace conhecido e sem rede.
        cargo + ["build", "--workspace", "--offline", "--all-features"],
        cwd=workspace,
        env=environment,
        check=True,
    )
    companion = "agent" if args.component == "server" else "server"
    companion_env = dict(os.environ, CARGO_TARGET_DIR=str(coverage_dir / "companion"))
    for key in (
        "RUSTFLAGS",
        "RUSTDOCFLAGS",
        "CARGO_ENCODED_RUSTFLAGS",
        "CARGO_ENCODED_RUSTDOCFLAGS",
        "LLVM_PROFILE_FILE",
        "CARGO_LLVM_COV_TARGET_DIR",
        "CARGO_LLVM_COV_BUILD_DIR",
        "CARGO_LLVM_COV",
        "CARGO_LLVM_COV_SHOW_ENV",
        "RUSTC_WRAPPER",
        "__CARGO_LLVM_COV_RUSTC_WRAPPER",
        "__CARGO_LLVM_COV_RUSTC_WRAPPER_RUSTFLAGS",
        "__CARGO_LLVM_COV_RUSTC_WRAPPER_CRATE_NAMES",
    ):
        companion_env.pop(key, None)
    subprocess.run(  # Companheiro real em target exclusivo, sem instalação.
        cargo + ["build", "--workspace", "--offline", "--all-features"],
        cwd=workspaces[companion],
        env=companion_env,
        check=True,
    )
    binaries = {
        args.component: target / "debug" / f"ec-{args.component}-app",
        companion: coverage_dir / "companion/debug" / f"ec-{companion}-app",
    }
    for binary in binaries.values():
        if not binary.is_file():
            raise ValueError("binário da jornada ausente: " + str(binary))
    harnesses = (
        ("publication_http", "orchestration_http", "agent_http")
        if args.component == "server"
        else ("agent_http",)
    )
    runtime_env = dict(
        os.environ,
        LLVM_PROFILE_FILE=environment["LLVM_PROFILE_FILE"],
        EC_COVERAGE_SERVER_BIN=str(binaries["server"]),
        EC_COVERAGE_AGENT_BIN=str(binaries["agent"]),
        EC_TEST_GRACEFUL_STOP="1",
    )
    for name in harnesses:
        subprocess.run(  # Bash do PATH confiável; harness versionado e perfis exclusivos da tarefa atual.
            [
                "bash",
                str(root / "scripts/stacks/ec/integration.sh"),
                "integration",  # Bash do PATH controlado pelo runner, sem shell=True.
                sys.executable,
                str(root / f"application/integration/tests/integration/{name}.py"),
            ],
            cwd=root,
            env=runtime_env,
            check=True,
        )


if __name__ == "__main__":
    main()
