from pathlib import Path
import hashlib
import json
base = Path('/home/rodrigo/Projetos/dev_audit/shared-toolbox')
stage = Path('/home/rodrigo/Projetos/ec/.local/build/toolbox-selinux')
changes = {}
def save(relative, content):
    original = (base / relative).read_bytes()
    name = relative.replace('/', '__')
    (stage / (name + '.before')).write_bytes(original)
    (stage / (name + '.after')).write_text(content)
    changes[relative] = {'before': hashlib.sha256(original).hexdigest(), 'file': name + '.after'}
p = base / 'toolbox/docker.py'
s = p.read_text().replace('import uuid\n', 'import uuid\nfrom pathlib import Path\n')
s = s.replace("def command(image,", '''def bind_mount(source, target, readonly=False):
    label = os.environ.get('TOOLBOX_SELINUX_LABEL', '')
    if not label:
        return ['--mount', f'type=bind,src={source},dst={target}' + (',readonly' if readonly else '')]
    if label != 'private':
        raise ValueError('TOOLBOX_SELINUX_LABEL must be empty or private')
    source = Path(source)
    if not source.is_absolute() or not source.is_dir() or any(c in str(source) for c in ':,\\\\n\\\\r'):
        raise ValueError('SELinux bind source must be an existing absolute directory without delimiters')
    return ['--volume', f'{source}:{target}:' + ('ro,Z' if readonly else 'rw,Z')]


def command(image,''')
s=s.replace("'--mount', f'type=bind,src={source},dst=/src,readonly',", "*bind_mount(source, '/src', readonly=True),")
s=s.replace("'--mount', f'type=bind,src={output},dst=/out',", "*bind_mount(output, '/out'),")
s=s.replace("args += ['--mount', f'type=bind,src={database},dst=/db,readonly']", "args += bind_mount(database, '/db', readonly=True)")
# Literal escaping in generated source must reject control characters, not letters.
s=s.replace("for c in ':,\\\\n\\\\r'", "for c in ':,\\n\\r'")
save('toolbox/docker.py',s)
p=base/'tests/test_core.py';s=p.read_text();s=s.replace('    def test_mount_contract_and_paths_with_spaces(self):', '''    def test_private_selinux_mount_preserves_isolation(self):
        output = self.root / 'results'
        output.mkdir()
        with patch.dict(os.environ, {'TOOLBOX_SELINUX_LABEL': 'private'}):
            _, cmd = command('sha256:' + '0'*64, [], self.consumer, output)
            self.assertIn(f'{self.consumer}:/src:ro,Z', cmd)
            self.assertIn(f'{output}:/out:rw,Z', cmd)
            self.assertIn('--cap-drop=ALL', cmd)
            self.assertNotIn('label=disable', cmd)
            with self.assertRaises(ValueError):
                command('sha256:' + '0'*64, [], self.root / 'missing', output)
        with patch.dict(os.environ, {'TOOLBOX_SELINUX_LABEL': 'disable'}):
            with self.assertRaises(ValueError):
                command('sha256:' + '0'*64, [], self.consumer, output)

    def test_mount_contract_and_paths_with_spaces(self):''');save('tests/test_core.py',s)
p=base/'docs/references.json';data=json.loads(p.read_text());data.append({'tool':'Docker','version':'29.7.2 observed; official current docs','url':'https://docs.docker.com/engine/storage/bind-mounts/','section':'Configure the SELinux label','consulted_at':'2026-11-06','status':'read','supports':'Opt-in TOOLBOX_SELINUX_LABEL=private uses ro,Z source/database and rw,Z output, existing absolute directories, without disabling SELinux. Ec uses disposable snapshots; run checks immutable images.'});save('docs/references.json',json.dumps(data,indent=2)+'\n')
p=base/'docs/references.md';s=p.read_text()+'''\n## SELinux — 2026-11-06\n\n`TOOLBOX_SELINUX_LABEL=private` habilita volumes `ro,Z` para fonte/base e `rw,Z`\npara saída. O padrão permanece sem relabel. Usar somente diretórios dedicados:\n`Z` altera o rótulo no host; o consumidor Ec fornece snapshot descartável.\n[Docker: Configure the SELinux label](https://docs.docker.com/engine/storage/bind-mounts/),\nconsultado em 2026-11-06. Não usa privileged nem desabilita SELinux.\n''';save('docs/references.md',s)
(stage/'changes.json').write_text(json.dumps(changes,indent=2)+'\n')
