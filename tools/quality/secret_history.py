"""Identidade do histórico Git local sem transportar configuração pessoal."""

import hashlib
import json
import os
import re
import subprocess
from pathlib import Path


def _git_environment():
    environment = {
        key: os.environ[key] for key in ("PATH", "HOME", "LANG", "LC_ALL") if key in os.environ
    }
    environment.update(
        GIT_CONFIG_NOSYSTEM="1",
        GIT_CONFIG_GLOBAL="/dev/null",
        GIT_OPTIONAL_LOCKS="0",
        GIT_TERMINAL_PROMPT="0",
        GIT_NO_REPLACE_OBJECTS="1",
        GIT_ALLOW_PROTOCOL="",
        GIT_NO_LAZY_FETCH="1",
    )
    return environment


def history_identity(root, timeout):
    root = Path(root).resolve()
    environment = _git_environment()

    def git(*arguments):
        result = subprocess.run(
            ["git", "-c", "core.fsmonitor=false", "-C", str(root), *arguments],
            capture_output=True,
            env=environment,
            check=False,
            timeout=timeout,
        )
        if result.returncode != 0:
            raise ValueError("histórico Git local indisponível; scan completo não demonstrado")
        return result.stdout

    if Path(git("rev-parse", "--show-toplevel").decode().strip()).resolve() != root:
        raise ValueError("histórico deve pertencer à raiz analisada")
    if git("rev-parse", "--is-shallow-repository").strip() != b"false":
        raise ValueError("histórico Git raso; preparar histórico completo antes do scan")
    head = git("rev-parse", "HEAD").decode().strip()
    commits = sorted(set(git("rev-list", "--all", "--reflog", "HEAD").decode().splitlines()))
    if not commits or any(
        not re.fullmatch(r"[0-9a-f]{40}|[0-9a-f]{64}", item) for item in [head, *commits]
    ):
        raise ValueError("inventário Git inválido ou vazio")
    git_dir = Path(git("rev-parse", "--absolute-git-dir").decode().strip()).resolve()
    common = Path(
        git("rev-parse", "--path-format=absolute", "--git-common-dir").decode().strip()
    ).resolve()
    if not git_dir.is_dir() or not common.is_dir():
        raise ValueError("objetos Git indisponíveis")
    alternates = common / "objects/info/alternates"
    if alternates.exists() and alternates.read_bytes().strip():
        raise ValueError("histórico com objetos externos; preparar objetos locais completos")
    if any((common / "objects/pack").glob("*.promisor")):
        raise ValueError("histórico parcial; preparar objetos locais completos antes do scan")
    references = git("for-each-ref", "--format=%(refname) %(objectname)")
    return dict(
        head=head,
        references_sha256=hashlib.sha256(references).hexdigest(),
        commits_sha256=hashlib.sha256("\n".join(commits).encode()).hexdigest(),
        commit_count=len(commits),
        git_dir=str(git_dir),
        common_dir=str(common),
    )


def history_options(identity):
    options = [
        "--mount",
        identity["git_dir"] + ":/ec-git:ro",
        "--env",
        "GIT_DIR=/ec-git",
        "--env",
        "GIT_WORK_TREE=/project",
    ]
    if identity["common_dir"] != identity["git_dir"]:
        options += [
            "--mount",
            identity["common_dir"] + ":/ec-git-common:ro",
            "--env",
            "GIT_COMMON_DIR=/ec-git-common",
        ]
    for key, value in dict(
        GIT_CONFIG_NOSYSTEM="1",
        GIT_CONFIG_GLOBAL="/dev/null",
        GIT_OPTIONAL_LOCKS="0",
        GIT_TERMINAL_PROMPT="0",
        GIT_NO_REPLACE_OBJECTS="1",
        GIT_ALLOW_PROTOCOL="",
        GIT_NO_LAZY_FETCH="1",
        GIT_CONFIG_COUNT="2",
        GIT_CONFIG_KEY_0="safe.directory",
        GIT_CONFIG_VALUE_0="/project",
        GIT_CONFIG_KEY_1="core.fsmonitor",
        GIT_CONFIG_VALUE_1="false",
    ).items():
        options += ["--env", key + "=" + value]
    return options


def history_blob_identities(root, report, timeout, maximum_bytes):
    """Vincula ocorrências aos bytes do commit e comprova hashes de evidências."""
    import tempfile
    from pathlib import PurePosixPath

    blobs = {}
    sources = {}

    def blob(commit, name):
        path = PurePosixPath(name)
        if (
            not re.fullmatch(r"[0-9a-f]{40}|[0-9a-f]{64}", commit)
            or not name or path.is_absolute() or ".." in path.parts
            or str(path) != name or "\0" in name
        ):
            raise ValueError("identidade de ocorrência histórica inválida")
        key = commit + ":" + name
        if key not in blobs:
            with tempfile.TemporaryFile() as output:
                result = subprocess.run(
                    ["git", "-c", "core.fsmonitor=false", "-C", str(Path(root).resolve()),
                     "show", "--no-ext-diff", "--no-textconv", key],
                    stdout=output, stderr=subprocess.PIPE, env=_git_environment(),
                    check=False, timeout=timeout,
                )
                if result.returncode != 0 or output.seek(0, os.SEEK_END) > maximum_bytes:
                    raise ValueError("arquivo histórico indisponível ou maior que limite")
                output.seek(0)
                blobs[key] = output.read()
        return blobs[key]

    for finding in report:
        commit, name = finding.get("Commit", ""), finding.get("File", "")
        content = blob(commit, name)
        key = commit + ":" + name
        source = sources.setdefault(key, {"sha256": hashlib.sha256(content).hexdigest(), "verified_digest_lines": []})
        line_number = finding.get("StartLine")
        if (
            not name.startswith("docs/validation/") or not name.endswith(".json")
            or type(line_number) is not int or line_number < 1
            or finding.get("EndLine", line_number) != line_number
        ):
            continue
        lines = content.decode("utf-8").splitlines()
        if line_number > len(lines):
            raise ValueError("localização histórica fora do arquivo")
        line = lines[line_number - 1].strip().rstrip(",")
        if not re.fullmatch(r'"[^"\n]+"\s*:\s*"[0-9a-f]{64}"', line):
            continue
        reference, expected = next(iter(json.loads("{" + line + "}").items()))
        try:
            referenced = blob(commit, reference)
        except ValueError:
            referenced = None
        if referenced is not None and hashlib.sha256(referenced).hexdigest() == expected:
            source["verified_digest_lines"].append(line_number)
    return sources
