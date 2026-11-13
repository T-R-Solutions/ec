"""Executa o binário Rust selecionado e preserva stderr que o extrator pode consumir."""

import json
import os
import re
import shutil
import subprocess
import sys
import tempfile
from pathlib import Path, PurePosixPath


def validate_status(directory, component, maximum_bytes):
    critical = []
    for tool in ("cargo", "rustc"):
        path = Path(directory) / f"{component}-{tool}.stderr.status.jsonl"
        if not path.is_file() or path.is_symlink() or path.stat().st_size > maximum_bytes:
            raise ValueError("diagnóstico do runtime Rust ausente ou inválido")
        records = [json.loads(line) for line in path.read_text().splitlines()]
        if not records:
            raise ValueError("diagnóstico do runtime Rust vazio")
        for record in records:
            if (
                record.get("tool") != tool
                or type(record.get("critical")) is not bool
                or type(record.get("returncode")) is not int
            ):
                raise ValueError("diagnóstico do runtime Rust malformado")
            if record["critical"]:
                critical.append(record)
                if record["returncode"] != 0:
                    raise ValueError(
                        "CodeQL Rust: falha interna de "
                        + tool
                        + "/"
                        + record["operation"]
                        + "; consultar stderr preservado"
                    )
    if not critical:
        raise ValueError("CodeQL Rust não demonstrou compilação de build scripts/macros")


def execute(binary, log, arguments):
    path = PurePosixPath(binary)
    os.umask(0o077)
    with tempfile.TemporaryFile() as errors:
        descriptors = set()
        for match in re.finditer(
            r"--jobserver-(?:auth|fds)=(\d+),(\d+)", os.environ.get("CARGO_MAKEFLAGS", "")
        ):
            for value in match.groups():
                descriptor = int(value)
                if descriptor > 2:
                    os.fstat(descriptor)
                    descriptors.add(descriptor)
        result = subprocess.run(
            [binary, *arguments], stderr=errors, check=False, pass_fds=tuple(sorted(descriptors))
        )  # Binário do toolchain selecionado e validado; argv do extrator, sem shell.
        operation = (
            "compile"
            if path.name == "rustc" and "--crate-name" in arguments
            else next((arg for arg in arguments if arg in ("check", "build")), "probe")
        )
        record = dict(
            tool=path.name,
            operation=operation,
            critical=operation != "probe",
            returncode=result.returncode,
        )
        status = Path(log).with_suffix(".status.jsonl")
        descriptor = os.open(status, os.O_WRONLY | os.O_CREAT | os.O_APPEND, 0o600)
        try:
            os.write(descriptor, (json.dumps(record) + "\n").encode())
        finally:
            os.close(descriptor)
        errors.seek(0)
        with Path(log).open("ab") as output:
            output.write(f"{path.name}: exit={result.returncode}\n".encode())
            shutil.copyfileobj(errors, output)
        errors.seek(0)
        shutil.copyfileobj(errors, sys.stderr.buffer)
    return result.returncode if result.returncode >= 0 else 128 - result.returncode


def main():
    binary, log, *arguments = sys.argv[1:]
    path, destination = PurePosixPath(binary), PurePosixPath(log)
    if (
        not binary.startswith("/ec-rustup/toolchains/")
        or path.name not in ("cargo", "rustc")
        or ".." in path.parts
        or not log.startswith("/ec-codeql/")
        or ".." in destination.parts
    ):
        raise ValueError("runtime Rust fora das montagens declaradas")
    return execute(binary, log, arguments)


if __name__ == "__main__":
    sys.exit(main())
