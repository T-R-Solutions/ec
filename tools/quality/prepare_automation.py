"""Preparo dos recursos locais declarados e restituição dos serviços possuídos."""

import argparse
import json
import os
import re
import shutil
import socket
import subprocess
import sys
import time
from pathlib import Path

if __package__ in (None, ""):
    sys.path.insert(0, str(Path(__file__).resolve().parents[2]))
from tools.quality.automation_config import CODE, load_config


class Unavailable(Exception):
    """Recurso local ausente: nenhum download implícito."""


def command(argv, *, capture=False, **kwargs):
    executable = shutil.which(argv[0])
    if not executable:
        raise Unavailable("ferramenta ausente: " + argv[0])
    return subprocess.run(  # argv conhecido, sem shell; caminhos declarados e validados.
        [executable, *argv[1:]],
        check=True,
        text=True,
        stdout=subprocess.PIPE if capture else None,
        **kwargs,
    )


def write(path, value):
    temporary = path.with_suffix(".tmp")
    temporary.write_text(json.dumps(value, indent=2) + "\n")
    temporary.chmod(0o600)
    temporary.replace(path)


def cargo_prefix():
    channel = os.environ.get("EC_RUST_TOOLCHAIN", "")
    if not re.fullmatch(r"[a-zA-Z0-9_.-]+", channel):
        raise Unavailable("toolchain ativo ausente; selecione uma instalação Rust existente")
    return ["rustup", "run", channel, "cargo"]


def prepare_cargo(config, root, component):
    workspace = root / config["components"][component]["root"]
    if not (workspace / "Cargo.toml").is_file() or not (workspace / "Cargo.lock").is_file():
        raise Unavailable("manifest/lock Cargo ausente: " + str(workspace))
    prefix = cargo_prefix()
    command(prefix + ["--version"])
    # Uma resolução por workspace/run; os gates seguintes usam esta resolução.
    command(prefix + ["update", "--offline"], cwd=workspace)
    command(prefix + ["fetch", "--offline"], cwd=workspace)


def rustsec(config):
    path = Path(os.environ.get("EC_RUSTSEC_DB", ""))
    if not os.environ.get("EC_RUSTSEC_DB") or not (path / ".git").exists():
        raise Unavailable(
            "base RustSec local ausente; configure EC_RUSTSEC_DB ou prepare a base do Cargo"
        )
    stamp = int(
        command(["git", "-C", str(path), "log", "-1", "--format=%ct"], capture=True).stdout.strip()
    )
    age = time.time() - stamp
    maximum = config["databases"]["rustsec"]["maximum_age_seconds"]
    if not 0 <= age <= maximum:
        raise Unavailable(
            "base RustSec futura ou expirada; renovar a base selecionada antes de Audit/Deny"
        )


def llvm_environment():
    """Reutiliza LLVM instalado para o mesmo compilador, sem trocar toolchain."""
    timeout = load_config()["runtime"]["probe_timeout"]
    prefix = cargo_prefix()[:-1]
    current = command(prefix + ["rustc", "-vV"], capture=True, timeout=timeout).stdout
    metadata = dict(line.split(": ", 1) for line in current.splitlines() if ": " in line)
    host = metadata["host"]
    root = Path(
        command(
            prefix + ["rustc", "--print", "sysroot"], capture=True, timeout=timeout
        ).stdout.strip()
    )

    def installed_tools(candidate):
        binaries = {
            key: candidate / "lib/rustlib" / host / "bin" / tool
            for key, tool in (("LLVM_COV", "llvm-cov"), ("LLVM_PROFDATA", "llvm-profdata"))
        }
        if all(path.is_file() and os.access(path, os.X_OK) for path in binaries.values()):
            return {key: str(path) for key, path in binaries.items()}
        return None

    active_tools = installed_tools(root)
    if active_tools:
        return active_tools
    rustup = Path(os.environ.get("RUSTUP_HOME", str(Path.home() / ".rustup")))
    for candidate in sorted((rustup / "toolchains").glob("*")):
        if candidate != root and (candidate / "bin/rustc").is_file():
            try:
                compiler = command(
                    [str(candidate / "bin/rustc"), "-vV"], capture=True, timeout=timeout
                ).stdout
            except (Unavailable, OSError, subprocess.SubprocessError):
                # Instalação auxiliar quebrada não invalida outra instalação compatível.
                continue
            other = dict(line.split(": ", 1) for line in compiler.splitlines() if ": " in line)
            if all(
                metadata.get(k) and metadata[k] == other.get(k)
                for k in ("release", "commit-hash", "host", "LLVM version")
            ):
                binaries = installed_tools(candidate)
                if binaries:
                    return binaries
    raise Unavailable(
        "LLVM de cobertura ausente para o compilador ativo; instalar llvm-tools-preview nesse toolchain"
    )


def inspect_postgres(config):
    postgres = config["services"]["postgres"]
    data = json.loads(command(["docker", "inspect", postgres["container"]], capture=True).stdout)[0]
    bindings = (
        data.get("HostConfig", {})
        .get("PortBindings", {})
        .get(str(postgres["container_port"]) + "/tcp")
        or []
    )
    if not bindings or not all(
        p.get("HostIp") == postgres["host"] and p.get("HostPort") == str(postgres["port"])
        for p in bindings
    ):
        raise Unavailable("container PostgreSQL dedicado não corresponde ao endpoint declarado")
    if not re.fullmatch(r"[a-f0-9]{64}", data.get("Id", "")):
        raise ValueError("identidade do container PostgreSQL inválida")
    return data


def postgres_ready(config, identity):
    service = config["services"]["postgres"]
    command(
        [
            "docker",
            "exec",
            identity,
            "pg_isready",
            "-h",
            service["host"],
            "-p",
            str(service["container_port"]),
            "-U",
            service["user"],
        ],
        capture=True,
    )


def postgres(config, run):
    service = config["services"]["postgres"]
    data = inspect_postgres(config)
    record = {
        "container": service["container"],
        "id": data["Id"],
        "was_running": data["State"]["Running"],
        "stop_required": False,
    }
    receipt = run / "services.json"
    if receipt.exists():
        raise ValueError("preparo PostgreSQL repetido no mesmo run")
    write(receipt, record)
    if not record["was_running"]:
        if not service["start_existing"]:
            raise Unavailable("PostgreSQL parado e início automático desabilitado")
        # Publicar propriedade antes do start; falha parcial ainda exige limpeza.
        record["stop_required"] = True
        write(receipt, record)
        command(["docker", "start", record["id"]])
    deadline = time.monotonic() + service["ready_timeout"]
    while True:
        try:
            postgres_ready(config, record["id"])
            return
        except subprocess.CalledProcessError:
            if time.monotonic() >= deadline:
                raise Unavailable("PostgreSQL iniciou, mas não ficou pronto no prazo") from None
            time.sleep(0.2)


def cleanup(run):
    receipt = run / "services.json"
    if not receipt.exists():
        return
    if receipt.is_symlink():
        raise ValueError("recibo de serviço não pode ser symlink")
    record = json.loads(receipt.read_text())
    if record["stop_required"]:
        if record["was_running"] or not re.fullmatch(r"[a-f0-9]{64}", record["id"]):
            raise ValueError("recibo de serviço inconsistente")
        # ID exato impede parar um container substituído com o mesmo nome.
        command(["docker", "stop", record["id"]])
        record["stop_required"] = False
    record["cleanup"] = "restored"
    write(receipt, record)


def preflight(config, project):
    if project not in ("integration", "e2e"):
        raise ValueError("projeto de integração desconhecido")
    data = inspect_postgres(config)
    if not data["State"]["Running"]:
        raise Unavailable("PostgreSQL parado; recurso prepare:postgres não concluído")
    postgres_ready(config, data["Id"])
    ports = [config["services"]["http"]["port"]]
    if project == "e2e":
        ports.append(config["services"]["frontend"]["port"])
    for port in ports:
        with socket.socket() as probe:
            probe.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
            try:
                probe.bind((config["services"]["http"]["host"], port))
            except OSError:
                raise Unavailable(f"porta de teste ocupada: {port}") from None


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument(
        "operation", choices=("cargo", "rustsec", "llvm", "postgres", "cleanup", "preflight")
    )
    parser.add_argument("target", nargs="?")
    args = parser.parse_args()
    os.environ["RUSTUP_AUTO_INSTALL"] = "0"
    os.environ["CARGO_NET_OFFLINE"] = "true"
    try:
        # Limpeza usa o recibo já publicado, mesmo se a configuração mudou ou quebrou.
        if args.operation == "cleanup":
            cleanup(Path(os.environ["EC_RUN_DIR"]).resolve(strict=True))
            return 0
        config = load_config()
        root = Path(os.environ.get("EC_ROOT", str(CODE)))
        if args.operation == "cargo":
            if (
                args.target not in config["components"]
                or config["components"][args.target]["language"] != "rust"
            ):
                raise ValueError("componente Cargo necessário")
            prepare_cargo(config, root, args.target)
        elif args.operation == "rustsec":
            rustsec(config)
        elif args.operation == "llvm":
            print(json.dumps(llvm_environment()))
        elif args.operation == "preflight":
            preflight(config, args.target)
        else:
            run = Path(os.environ["EC_RUN_DIR"]).resolve(strict=True)
            postgres(config, run)
    except (
        Unavailable,
        FileNotFoundError,
        subprocess.CalledProcessError,
        subprocess.TimeoutExpired,
    ) as error:
        print("blocked: " + str(error), file=sys.stderr)
        return 3
    except (OSError, ValueError, KeyError, TypeError) as error:
        print("error: preparo: " + str(error), file=sys.stderr)
        return 2
    return 0


if __name__ == "__main__":
    sys.exit(main())
