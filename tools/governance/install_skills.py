"""Install the prepared catalog without replacing existing user content."""
import argparse
import contextlib
import fcntl
import os
import json
from pathlib import Path
import shutil
import sys
import tempfile

from validate import audit, digest, safe, strict_json


def package_files(folder):
    for path in folder.rglob('*'):
        if path.is_symlink():
            raise ValueError('Symlink in package: ' + str(path))
    return {str(path.relative_to(folder)): digest(path)
            for path in folder.rglob('*') if path.is_file()}


def sync_directory(path):
    fd = os.open(path, os.O_RDONLY | os.O_DIRECTORY)
    try:
        os.fsync(fd)
    finally:
        os.close(fd)


def atomic_write(path, text):
    # A leftover temporary file is never treated as a committed write.
    fd, name = tempfile.mkstemp(prefix='.ec-metadata-', dir=path.parent)
    try:
        with os.fdopen(fd, 'w') as stream:
            stream.write(text)
            stream.flush()
            os.fsync(stream.fileno())
        os.replace(name, path)
        sync_directory(path.parent)
    finally:
        if os.path.exists(name):
            os.unlink(name)


def expected_metadata(before):
    profile_name = 'config/governance/profile.json'
    profile = json.loads(before[profile_name])
    if profile['installation_status'] != 'prepared':
        raise ValueError('Journal must start from preparation')
    after = {}
    for row in profile['skills']:
        if row['decision'] == 'excluded':
            continue
        name = f"sdd/chain/{row['name']}.md"
        after[name] = before[name].replace('skill_path: ' + row['path'],
                                           'skill_path: ' + row['target_path'])
        row['path'] = row['target_path']
    if set(before) != set(after) | {profile_name}:
        raise ValueError('Unexpected journal write set')
    profile['skill_root'] = profile['target_root']
    profile['installation_status'] = 'installed'
    # Profile commits last, but recovery also handles a completed profile write.
    after[profile_name] = json.dumps(profile, ensure_ascii=False, indent=2) + '\n'
    return after


def validate_journal(root, journal):
    if journal['schema_version'] != 1 or journal['after'] != expected_metadata(journal['before']):
        raise ValueError('Invalid installation journal')
    for name, before in journal['before'].items():
        current = safe(root, name).read_text()
        if current not in (before, journal['after'][name]):
            raise ValueError('Conflicting metadata: ' + name)


@contextlib.contextmanager
def installation_lock(root, apply):
    path = safe(root, 'config/governance/install.lock', must_exist=False)
    # Check remains read-only. Once created, the lock inode is never removed.
    if not apply and not path.exists():
        yield
        return
    with path.open('a' if apply else 'r') as stream:
        try:
            fcntl.flock(stream, fcntl.LOCK_EX | fcntl.LOCK_NB)
        except BlockingIOError as exc:
            raise ValueError('Installer already running') from exc
        try:
            yield
        finally:
            fcntl.flock(stream, fcntl.LOCK_UN)


def install(root, apply=False):
    root = root.resolve()
    with installation_lock(root, apply):
        return _install(root, apply)


def _install(root, apply=False):
    root=root.resolve()
    journal_path = safe(root, 'config/governance/install-journal.json', must_exist=False)
    journal = strict_json(journal_path) if journal_path.exists() else None
    profile_path = safe(root, 'config/governance/profile.json')
    if journal is not None:
        validate_journal(root, journal)
        profile = json.loads(journal['before']['config/governance/profile.json'])
        # Audit the recorded pre-install view without mutating live metadata.
        with tempfile.TemporaryDirectory(prefix='ec-install-audit-') as temp:
            view = Path(temp)
            for name in ['config/governance', 'sdd', 'docs', 'tools/governance', 'application',
                         '.local/skill-installation/skills', 'reverse_engineering_teamcity']:
                # Research is only needed for README link targets; avoid copying its index.
                if name == 'reverse_engineering_teamcity':
                    for file in ['README.md', 'ci-platform-research/README.md']:
                        dest = view/name/file
                        dest.parent.mkdir(parents=True, exist_ok=True)
                        shutil.copyfile(root/name/file, dest)
                else:
                    shutil.copytree(root/name, view/name, symlinks=True)
            for name in ['README.md', 'AGENTS.md']:
                shutil.copyfile(root/name, view/name)
            for name, content in journal['before'].items():
                (view/name).write_text(content)
            result = audit(view)
    else:
        result = audit(root)
        profile = strict_json(profile_path)
    if result['status'] != 'PASS':
        raise ValueError('Catalog audit failed: ' + '; '.join(result['errors']))
    if profile['installation_status'] == 'installed':
        return {'status': 'ALREADY_INSTALLED', 'count': profile['selected_count']}
    target=safe(root,profile['target_root'],must_exist=False)
    rows=[r for r in profile['skills'] if r['decision']!='excluded']
    if target.exists():
        allowed={r['name'] for r in rows}
        if set(p.name for p in target.iterdir())-allowed:
            raise ValueError('Destination contains unrelated entries; no writes performed')
    for row in rows:
        dest=target/row['name']
        if dest.exists():
            safe(root,str(dest.relative_to(root)))
            current=package_files(dest)
            if current!=row['files']:
                raise ValueError(f'Conflicting destination: {dest}; no writes performed')
    if not apply:
        return {'status':'READY_TO_ATTEMPT','count':len(rows),'target':str(target),
                'note':'Read-only preflight does not prove write permission. --apply performs installation.'}
    if journal is None:
        before = {'config/governance/profile.json': profile_path.read_text()}
        for row in rows:
            name = f"sdd/chain/{row['name']}.md"
            before[name] = safe(root, name).read_text()
        journal = {'schema_version': 1, 'before': before}
        journal['after'] = expected_metadata(before)
        atomic_write(journal_path, json.dumps(journal, ensure_ascii=False, indent=2) + '\n')
    # No elevation, chmod, symlink workaround, or overwrite of a conflicting destination.
    target.mkdir(parents=True,exist_ok=True)
    for row in rows:
        dest=target/row['name']
        if dest.exists():
            continue
        source=safe(root,row['path']).parent
        with tempfile.TemporaryDirectory(prefix='.ec-install-',dir=target.parent) as temp:
            candidate=Path(temp)/row['name']
            shutil.copytree(source,candidate)
            current=package_files(candidate)
            if current!=row['files']:
                raise ValueError(f'Copy verification failed: {row["name"]}')
            if dest.exists():
                raise ValueError(f'Destination appeared concurrently: {dest}')
            for file in candidate.rglob('*'):
                if file.is_file():
                    with file.open('rb') as stream:
                        os.fsync(stream.fileno())
            for directory in sorted((p for p in candidate.rglob('*') if p.is_dir()),
                                    key=lambda p: len(p.parts), reverse=True):
                sync_directory(directory)
            sync_directory(candidate)
            candidate.rename(dest)
            sync_directory(target)
    for row in rows:
        dest=target/row['name']
        if package_files(dest)!=row['files']:
            raise ValueError(f'Installed content changed: {row["name"]}')
    # Each file is atomic; the journal reconciles the multi-file transition.
    validate_journal(root, journal)
    for name, content in journal['after'].items():
        path = safe(root, name)
        if path.read_text() != content:
            atomic_write(path, content)
    final=audit(root,True)
    if final['status']!='PASS':
        raise ValueError('Post-install audit failed: '+ '; '.join(final['errors']))
    journal_path.unlink()
    sync_directory(journal_path.parent)
    return {'status':'INSTALLED','count':len(rows),'next':'Update project status prose and reexamine affected SDD evidence; old hashes must not be renewed without review.'}


def main():
    parser=argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--root',type=Path,default=Path(__file__).resolve().parents[2])
    mode=parser.add_mutually_exclusive_group(required=True)
    mode.add_argument('--check',action='store_true');mode.add_argument('--apply',action='store_true')
    args=parser.parse_args()
    try: result=install(args.root,args.apply)
    except (OSError,ValueError,KeyError) as exc:
        print(json.dumps({'status':'BLOCKED','error':str(exc)},ensure_ascii=False));return 1
    print(json.dumps(result,ensure_ascii=False,indent=2));return 0


if __name__=='__main__':
    sys.exit(main())
