"""Identidade do histórico Git local sem transportar configuração pessoal."""

import hashlib
import os
import re
import subprocess
from pathlib import Path


def history_identity(root, timeout):
    root = Path(root).resolve()
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
