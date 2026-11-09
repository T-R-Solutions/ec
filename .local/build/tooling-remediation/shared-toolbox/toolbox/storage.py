"""File-based storage, immutable database snapshots, and scoped retention."""
import contextlib
import datetime as dt
import fcntl
import hashlib
import json
import os
import shutil
import uuid
from pathlib import Path
from .config import REPO, ID


def now():
    return dt.datetime.now(dt.timezone.utc).isoformat()


def atomic_json(path, value):
    path = Path(path)
    path.parent.mkdir(parents=True, exist_ok=True)
    tmp = path.with_name(path.name + '.' + uuid.uuid4().hex + '.tmp')
    try:
        with tmp.open('x') as f:
            json.dump(value, f, indent=2, sort_keys=True, default=str)
            f.write('\n')
            f.flush()
            os.fsync(f.fileno())
        os.replace(tmp, path)
    finally:
        tmp.unlink(missing_ok=True)


def digest(value):
    return hashlib.sha256(json.dumps(value, sort_keys=True, default=str).encode()).hexdigest()


def tree_manifest(root, exclude_dirs=()):
    """Hash regular files, links, modes; do not follow symlinks outside consumer."""
    root = Path(root)
    out = {}
    for folder, dirs, files in os.walk(root, followlinks=False):
        dirs[:] = [name for name in dirs if name not in exclude_dirs]
        for name in sorted(dirs + files):
            p = Path(folder) / name
            rel = str(p.relative_to(root))
            if p.is_symlink():
                out[rel] = {'link': os.readlink(p)}
            elif p.is_file():
                h = hashlib.sha256()
                with p.open('rb') as f:
                    for chunk in iter(lambda: f.read(1024 * 1024), b''):
                        h.update(chunk)
                out[rel] = {'sha256': h.hexdigest(), 'mode': p.stat().st_mode & 0o777}
    return out


class Store:
    def __init__(self, root, tools_root=None):
        self.root = Path(root).expanduser().resolve()
        self.tools_root = Path(tools_root or REPO / 'tools').expanduser().resolve()
        if any(c in str(self.root) + str(self.tools_root) for c in ',\n\r\x00'):
            raise ValueError('state path cannot contain commas or line breaks')
        self.root.mkdir(parents=True, exist_ok=True, mode=0o700)

    def volume(self, tool, create=True):
        if not ID.fullmatch(tool):
            raise ValueError('invalid tool identifier')
        path = self.tools_root / tool / 'volume'
        if create:
            path.mkdir(parents=True, exist_ok=True, mode=0o700)
        return path.resolve()

    def tool_run(self, tool, project, run_id, create=True):
        path = self.volume(tool, create=create) / 'projects' / project / 'runs' / run_id
        if create:
            path.mkdir(parents=True, exist_ok=False, mode=0o700)
        return path

    def preparation_dir(self, tool):
        path = self.volume(tool) / 'preparation' / uuid.uuid4().hex
        path.mkdir(parents=True, mode=0o700)
        return path

    @contextlib.contextmanager
    def lock(self, key):
        parent = self.volume(key[1]) if key[0] in ['image', 'database'] else self.root
        p = parent / 'locks' / (digest(key) + '.lock')
        p.parent.mkdir(parents=True, exist_ok=True)
        with p.open('a') as f:
            fcntl.flock(f, fcntl.LOCK_EX)
            yield

    def run_dir(self, project):
        run_id = dt.datetime.now(dt.timezone.utc).strftime('%Y%m%dT%H%M%S.%fZ-') + uuid.uuid4().hex[:12]
        p = self.root / 'projects' / project / 'runs' / run_id
        p.mkdir(parents=True, mode=0o700)
        return p

    def database(self, tool, version, snapshot):
        return self.volume(tool) / 'databases' / version / snapshot

    def prune(self, project, keep, apply=False):
        # Do not traverse links; never delete bases, images or another project.
        parent = self.root / 'projects' / project / 'runs'
        candidates = sorted((p for p in parent.glob('*') if p.is_dir() and not p.is_symlink()
                             and (p / 'summary.json').is_file()), reverse=True)[keep:]
        if apply:
            with self.lock(['prune', project]):
                for p in candidates:
                    # Derive paths from our layout, never trust a path in summary.json.
                    for tool in self.tools_root.iterdir():
                        if not tool.is_dir() or tool.is_symlink() or not ID.fullmatch(tool.name):
                            continue
                        volume = tool / 'volume'
                        run = volume / 'projects' / project / 'runs' / p.name
                        if volume.is_symlink() or any(parent.is_symlink() for parent in [run, *list(run.parents)[:3]]):
                            raise ValueError(f'refusing retention through symlink: {run}')
                        if run.is_dir():
                            shutil.rmtree(run)
                    shutil.rmtree(p)
        return [str(p) for p in candidates]
