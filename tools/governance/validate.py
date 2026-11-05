"""Read-only audit of the Ec skill catalog. Python 3.11+, standard library."""
import argparse
import hashlib
import importlib.util
import json
from pathlib import Path
import re
import sys
import zipfile


def digest(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def strict_json(path):
    def unique(pairs):
        result = {}
        for key, value in pairs:
            if key in result:
                raise ValueError(f'Duplicate JSON key: {key}')
            result[key] = value
        return result
    return json.loads(path.read_text(), object_pairs_hook=unique)


def safe(root, value, must_exist=True):
    if not isinstance(value, str) or not value or Path(value).is_absolute() or '..' in Path(value).parts:
        raise ValueError(f'Invalid relative path: {value!r}')
    root = root.resolve()
    path = root / value
    if not path.resolve().is_relative_to(root):
        raise ValueError(f'Escaping path: {value}')
    for part in [path, *path.parents]:
        if part == root:
            break
        if part.is_symlink():
            raise ValueError(f'Symlink path: {value}')
    if must_exist and not path.exists():
        raise ValueError(f'Missing path: {value}')
    return path


def portable(root):
    spec = importlib.util.spec_from_file_location('portable_sdd', root / 'sdd/tools/validate.py')
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


def audit_source_bundle(root, profile):
    """Check retained original resources independently of installed adaptations."""
    relative = profile['source_bundle']
    if relative not in profile['source_archives']:
        raise ValueError('Source bundle is not bound by source_archives')
    errors = []
    with zipfile.ZipFile(safe(root, relative)) as archive:
        names = archive.namelist()
        if len(names) != len(set(names)):
            raise ValueError('Duplicate source bundle member')
        for name in names:
            path = Path(name)
            if path.is_absolute() or '..' in path.parts or '\\' in name:
                raise ValueError(f'Unsafe source bundle member: {name}')
        prefixes = {
            'ec': 'ec/new_skills/skills',
            'ox': 'ox/.codex/skills',
            'readme': 'readme/new_skills/skills',
        }
        for row in profile['skills']:
            for name, expected in row['source_files'].items():
                member = f"{prefixes[row['origin']]}/{row['name']}/{name}"
                try:
                    data = archive.read(member)
                except KeyError as exc:
                    raise ValueError(f'Missing original resource: {member}') from exc
                if hashlib.sha256(data).hexdigest() != expected:
                    errors.append(f'Original resource changed: {member}')
    return errors


def audit(root, require_installed=False):
    root = root.resolve()
    profile = strict_json(root / 'config/governance/profile.json')
    errors = []
    if profile['schema_version'] not in (1, 2):
        raise ValueError('Unsupported governance profile schema')
    selected = [r for r in profile['skills'] if r['decision'] != 'excluded']
    names = [r['name'] for r in profile['skills']]
    if len(names) != len(set(names)):
        errors.append('Duplicate catalog identity')
    counts = (len(selected), sum(r['decision'] == 'conditional' for r in selected), len(names)-len(selected))
    if counts != (profile['selected_count'], profile['conditional_count'], profile['excluded_count']):
        errors.append('Profile counts disagree with records')
    if counts != (67, 6, 4):
        errors.append('Catalog differs from ADR-001 selection counts')
    exclusions = {'iced-engineering', 'code-context-engineering', 'code-knowledge-graph-engineering', 'agent-usage-accounting'}
    if {r['name'] for r in profile['skills'] if r['decision']=='excluded'} != exclusions:
        errors.append('Exclusions differ from the reviewed selection')
    if any(r['decision'] not in ('adopt','conditional','excluded') for r in profile['skills']):
        errors.append('Unknown selection decision')
    expected = {'ec': 40, 'ox': 30, 'readme': 1}
    if {k:sum(r['origin'] == k for r in profile['skills']) for k in expected} != expected:
        errors.append('Source inventory differs from the 71 reviewed candidates')
    if profile['installation_status'] not in ('prepared', 'installed'):
        errors.append('Unknown installation status')
    if profile['target_root'] != '.codex/skills':
        errors.append('Unexpected installation target')
    active = '.codex/skills' if profile['installation_status'] == 'installed' else '.local/skill-installation/skills'
    if profile['skill_root'] != active:
        errors.append('Active root contradicts installation status')
    if require_installed and profile['installation_status'] != 'installed':
        errors.append('Skills are prepared, not installed in .codex/skills')
    mod = portable(root)
    actual = {p.name for p in safe(root, active).iterdir() if p.is_dir()}
    if actual != {r['name'] for r in selected}:
        errors.append('Active skill directories do not match the selected catalog')
    bridges = {p.stem for p in (root/'sdd/chain').glob('*.md') if p.name != 'README.md'}
    if bridges != {r['name'] for r in selected}:
        errors.append('Bridge set differs from selected skills')
    for row in selected:
        name = row['name']
        if not re.fullmatch(r'[a-z0-9]+(?:-[a-z0-9]+)*', name) or len(name)>64:
            errors.append(f'Invalid skill name: {name}')
            continue
        expected_path = f'{active}/{name}/SKILL.md'
        if row['path'] != expected_path or row['target_path'] != f'.codex/skills/{name}/SKILL.md':
            errors.append(f'Incorrect profile path: {name}')
        folder = safe(root, f'{active}/{name}')
        current = {str(p.relative_to(folder)) for p in folder.rglob('*') if p.is_file()}
        if current != set(row['files']):
            errors.append(f'File inventory mismatch: {name}')
        for relative, expected_hash in row['files'].items():
            path = safe(folder, relative)
            if not re.fullmatch(r'[0-9a-f]{64}',expected_hash) or digest(path) != expected_hash:
                errors.append(f'Hash mismatch: {name}/{relative}')
        errors.extend(mod.skill_errors(folder, name))
        bridge = safe(root, f'sdd/chain/{name}.md').read_text()
        if f'\nskill_path: {expected_path}\n' not in bridge:
            errors.append(f'Bridge points to wrong active location: {name}')
        ui = safe(folder, 'agents/openai.yaml').read_text()
        for key in ('display_name', 'short_description', 'default_prompt'):
            if not re.search(rf'^  {key}: .+', ui, re.M):
                errors.append(f'Missing UI metadata {key}: {name}')
        if '$'+name not in ui:
            errors.append(f'Default prompt does not name skill: {name}')
        if re.search(r'\b(?:TODO|TBD|UNCONFIGURED)\b',(folder/'SKILL.md').read_text()):
            errors.append(f'Unfinished entrypoint: {name}')
    errors.extend(mod.bridge_errors(root, root/'sdd/chain'))
    for file in ['AGENTS.md','README.md']:
        # Markdown checker scans directories; isolate the files through its regex contract below.
        text = (root/file).read_text()
        for target in re.findall(r'\]\(([^)]+)\)', text):
            if not target.startswith(('https:', 'http:', '#')):
                try: safe(root, target.split('#')[0])
                except ValueError as exc: errors.append(str(exc))
    for folder in ['docs/architecture','docs/development','docs/decisions']:
        errors.extend(mod.markdown_errors(root/folder, root))
    for key in ('source_documents','source_archives'):
        for relative, expected_hash in profile[key].items():
            if digest(safe(root, relative)) != expected_hash:
                errors.append(f'Original input changed: {relative}')
    if profile['schema_version'] == 2:
        errors.extend(audit_source_bundle(root, profile))
    return {'status':'FAIL' if errors else 'PASS','installation_status':profile['installation_status'],
            'selected':len(selected),'conditional':counts[1],'excluded':counts[2],
            'skill_files':sum(len(r['files']) for r in selected),'errors':errors,
            'limits':['Local structure, integrity and routing only; no product or skill effectiveness certification.',
                      'External URLs and upstream technical claims are not revalidated.']}


def main():
    parser=argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--root',type=Path,default=Path(__file__).resolve().parents[2])
    parser.add_argument('--require-installed',action='store_true')
    args=parser.parse_args()
    try: result=audit(args.root,args.require_installed)
    except (OSError,ValueError,KeyError,TypeError,zipfile.BadZipFile) as exc:
        print(json.dumps({'status':'ERROR','error':str(exc)},ensure_ascii=False));return 2
    print(json.dumps(result,ensure_ascii=False,indent=2))
    return int(result['status']!='PASS')


if __name__=='__main__':
    sys.exit(main())
