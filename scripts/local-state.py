"""Estado transitório da automação e retenção de somente duas datas de logs."""

import argparse
import contextlib
import datetime as dt
import fcntl
import hashlib
import json
import os
import re
import shutil
import stat
import sys
from pathlib import Path


@contextlib.contextmanager
def directory_fd(path):
    """Abrir cada ancestral sem seguir links; descritor ancora as operações."""
    path = Path(path).absolute()
    fd = os.open("/", os.O_RDONLY | os.O_DIRECTORY)
    try:
        for part in path.parts[1:]:
            child = os.open(part, os.O_RDONLY | os.O_DIRECTORY | os.O_NOFOLLOW, dir_fd=fd)
            os.close(fd)
            fd = child
        yield fd
    finally:
        os.close(fd)


def private_directory(path):
    with directory_fd(path.parent) as parent:
        try:
            os.mkdir(path.name, 0o700, dir_fd=parent)
        except FileExistsError:
            # Outro escritor pode ter criado o diretório; validar dono/modo abaixo.
            pass
    with directory_fd(path) as fd:
        info = os.fstat(fd)
        if info.st_uid != os.getuid() or stat.S_IMODE(info.st_mode) != 0o700:
            raise ValueError(f"Diretório deve ser privado e pertencer ao usuário: {path}")
    return path


def runtime_directory(root):
    identity = hashlib.sha256(os.fsencode(root.resolve())).hexdigest()[:24]
    return private_directory(Path("/tmp") / f"ec-automation-{os.getuid()}-{identity}")  # UID/hash, 0700 e abertura sem symlink.


def require_lock(root, inherited):
    path = runtime_directory(root) / "workspace.lock"
    with directory_fd(path.parent) as parent:
        fd = os.open(path.name, os.O_CREAT | os.O_RDWR | os.O_NOFOLLOW, 0o600, dir_fd=parent)
    try:
        info = os.fstat(fd)
        if not stat.S_ISREG(info.st_mode) or info.st_uid != os.getuid():
            raise ValueError("Lock inválido")
        if inherited is None or (info.st_dev, info.st_ino) != (
            os.fstat(inherited).st_dev,
            os.fstat(inherited).st_ino,
        ):
            raise ValueError("Operação requer o lock herdado do runner")
        fcntl.flock(inherited, fcntl.LOCK_EX | fcntl.LOCK_NB)
    finally:
        os.close(fd)


def prepare_logs(root, today=None):
    today = today or dt.datetime.now().astimezone().date().isoformat()
    if dt.date.fromisoformat(today).isoformat() != today:
        raise ValueError("Data inválida")
    local = private_directory(root / ".local")
    logs = private_directory(local / "logs")
    with directory_fd(logs) as parent:
        dates = sorted(os.listdir(parent))
        for name in dates:
            if not re.fullmatch(r"\d{4}-\d{2}-\d{2}", name):
                raise ValueError(f"Entrada inesperada em logs: {name}")
            dt.date.fromisoformat(name)
            # Não excluir links, diretórios arbitrários ou árvores montadas.
            with directory_fd(logs / name) as day_fd:
                if os.fstat(day_fd).st_dev != os.fstat(parent).st_dev:
                    raise ValueError("Retenção não atravessa mounts")
                for entry in os.listdir(day_fd):
                    info = os.stat(entry, dir_fd=day_fd, follow_symlinks=False)
                    if not stat.S_ISREG(info.st_mode) or not entry.endswith(".log"):
                        raise ValueError(f"Conteúdo não permitido: {name}/{entry}")
        if dates and today < dates[-1]:
            raise ValueError("Relógio anterior à última data de logs; retenção recusada")
        keep = sorted(set(dates + [today]))[-2:]
        # Expirar antes de criar a terceira data.
        for name in dates:
            if name not in keep:
                with directory_fd(logs / name) as day_fd:
                    for entry in os.listdir(day_fd):
                        os.unlink(entry, dir_fd=day_fd)
                os.rmdir(name, dir_fd=parent)
        if today not in dates:
            os.mkdir(today, 0o700, dir_fd=parent)
    return logs / today


def write_log(parent_fd, name, data):
    fd = os.open(
        name, os.O_WRONLY | os.O_CREAT | os.O_EXCL | os.O_NOFOLLOW, 0o600, dir_fd=parent_fd
    )
    with os.fdopen(fd, "wb") as stream:
        stream.write(data)


def export_log(output, name, path):
    with directory_fd(path.parent) as parent:
        source = os.open(path.name, os.O_RDONLY | os.O_NOFOLLOW, dir_fd=parent)
    with os.fdopen(source, "rb") as stream:
        if not stat.S_ISREG(os.fstat(stream.fileno()).st_mode):
            raise ValueError("Log não regular")
        dest = os.open(
            name, os.O_WRONLY | os.O_CREAT | os.O_EXCL | os.O_NOFOLLOW, 0o600, dir_fd=output
        )
        with os.fdopen(dest, "wb") as target:
            shutil.copyfileobj(stream, target)


def finish(root, workspace, log_dir, exit_code):
    parent = runtime_directory(root)
    if workspace.parent != parent or not re.fullmatch(r"run-[a-zA-Z0-9]+", workspace.name):
        raise ValueError("Workspace fora do escopo")
    if log_dir.parent != root / ".local/logs" or not re.fullmatch(
        r"\d{4}-\d{2}-\d{2}", log_dir.name
    ):
        raise ValueError("Destino fora de logs por data")
    with directory_fd(workspace), directory_fd(log_dir) as output:
        # Somente metadados de execução; nunca exportar snapshots/builds/artefatos.
        for name in ("results", "inputs", "inputs-after", "services"):
            path = workspace / f"{name}.json"
            if path.is_file() and not path.is_symlink():
                write_log(output, f"{workspace.name}-automate.{name}.log", path.read_bytes())
        for task in sorted(workspace.iterdir()):
            if (
                not re.fullmatch(r"\d+-[a-z0-9_-]+", task.name)
                or task.is_symlink()
                or not task.is_dir()
            ):
                continue
            for name in (
                "result",
                "classification",
                "tool",
                "coverage",
                "native-executions",
                "codeql-targets",
                "bandit-review",
                "gitleaks-history",
                "images",
                "rule-catalogs",
                "opengrep-qualification",
                "grype-inventory",
                "grype-native-database",
                "trivy-native-databases",
                "trivy-database",
                "osv-scanner-database",
                "targets",
                "yara-targets",
                "sources",
                "build",
                "failure",
                "browser-cleanup",
            ):
                path = task / f"{name}.json"
                if path.is_file() and not path.is_symlink():
                    write_log(output, f"{workspace.name}-{task.name}.{name}.log", path.read_bytes())
            browser = task / "artifacts/browser-results.json"
            if browser.is_file() and not browser.is_symlink() and not browser.parent.is_symlink():
                export_log(output, f"{workspace.name}-{task.name}.browser-results.log", browser)
            for path in sorted([
                *(task / "codeql").glob("*.stderr.log"),
                *(task / "codeql").glob("*.status.jsonl"),
            ]):
                if path.is_file() and not path.is_symlink():
                    suffix = path.name if path.name.endswith(".log") else f"{path.name}.log"
                    export_log(output, f"{workspace.name}-{task.name}.{suffix}", path)
            # Logs brutos dos membros Geiger e das chamadas nativas, sem fontes.
            for path in sorted(task.glob("*.log")):
                if path.is_file() and not path.is_symlink():
                    export_log(output, f"{workspace.name}-{task.name}.{path.name}", path)
            for path in sorted(task.glob("geiger-*-diagnostic.json")):
                if path.is_file() and not path.is_symlink():
                    export_log(output, f"{workspace.name}-{task.name}.{path.stem}.log", path)
            for path in sorted(task.glob("codeql-*-dependencies.json")):
                if path.is_file() and not path.is_symlink():
                    export_log(output, f"{workspace.name}-{task.name}.{path.stem}.log", path)
            for step in sorted(task.iterdir()):
                if (
                    not re.fullmatch(r"\d+-[a-z0-9-]+", step.name)
                    or step.is_symlink()
                    or not step.is_dir()
                ):
                    continue
                for name in (
                    "stdout.log",
                    "stderr.log",
                    "diagnostic.json",
                    "exit.json",
                    "redaction.json",
                    "native.json.log",
                    "native.sarif.log",
                ):
                    path = step / name
                    if path.is_file() and not path.is_symlink():
                        suffix = name[:-5] + ".log" if name.endswith(".json") else name
                        export_log(
                            output, f"{workspace.name}-{task.name}-{step.name}.{suffix}", path
                        )
        # rmtree não segue links; falha nunca publica encerramento aprovado.
        cleanup_error = None
        try:
            shutil.rmtree(workspace)
        except OSError as exc:
            cleanup_error = str(exc)
        write_log(
            output,
            f"{workspace.name}-automate.exit.log",
            (
                json.dumps({
                    "exit_code": 2 if cleanup_error else exit_code,
                    "finished_at": dt.datetime.now().astimezone().isoformat(),
                    "cleanup_error": cleanup_error,
                    "intermediate_artifacts": "remaining" if cleanup_error else "discarded",
                })
                + "\n"
            ).encode(),
        )
        if cleanup_error:
            raise ValueError(cleanup_error)


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("operation", choices=("runtime-path", "prepare", "finish"))
    parser.add_argument("--root", type=Path, required=True)
    parser.add_argument("--lock-fd", type=int)
    parser.add_argument("--workspace", type=Path)
    parser.add_argument("--log-dir", type=Path)
    parser.add_argument("--exit-code", type=int, default=2)
    args = parser.parse_args()
    root = args.root.resolve(strict=True)
    try:
        if args.operation == "runtime-path":
            print(runtime_directory(root))
        else:
            require_lock(root, args.lock_fd)
            if args.operation == "prepare":
                print(prepare_logs(root))
            elif args.workspace is None or args.log_dir is None:
                raise ValueError("finish requer workspace e log-dir")
            else:
                finish(root, args.workspace, args.log_dir, args.exit_code)
    except (OSError, ValueError) as exc:
        print(f"error: {exc}", file=sys.stderr)
        return 2
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
