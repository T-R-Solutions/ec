"""Negative controls run in disposable copies; never mutate the working catalog."""
import importlib.util
import json
from pathlib import Path
import shutil
import sys
import tempfile
import unittest
import zipfile
from unittest.mock import patch

ROOT=Path(__file__).resolve().parents[3]
sys.path.insert(0,str(ROOT/'tools/governance'))
import validate
import install_skills


class GovernanceTests(unittest.TestCase):
    def setUp(self):
        self.temp=tempfile.TemporaryDirectory(prefix='ec-governance-test-')
        self.addCleanup(self.temp.cleanup)
        self.root=Path(self.temp.name)
        for path in ['.local/skill-installation/skills','config/governance','sdd','docs','tools/governance',
                     'application']:
            shutil.copytree(ROOT/path,self.root/path,ignore=shutil.ignore_patterns('__pycache__'))
        # Source bundles are copied with config/governance; retired inputs are unnecessary.
        for name in ['AGENTS.md','README.md']:
            shutil.copyfile(ROOT/name,self.root/name)
        research_index=Path('reverse_engineering_teamcity/README.md')
        (self.root/research_index).parent.mkdir(parents=True)
        shutil.copyfile(ROOT/research_index,self.root/research_index)
        research=Path('reverse_engineering_teamcity/ci-platform-research/README.md')
        (self.root/research).parent.mkdir(parents=True)
        shutil.copyfile(ROOT/research,self.root/research)
        self.profile=self.root/'config/governance/profile.json'
        # Installation scenarios always start from an isolated prepared catalog,
        # including when the source checkout has already been installed.
        profile = json.loads(self.profile.read_text())
        prepared_root = '.local/skill-installation/skills'
        for row in profile['skills']:
            if row['decision'] == 'excluded':
                continue
            prepared_path = f"{prepared_root}/{row['name']}/SKILL.md"
            bridge = self.root / 'sdd/chain' / f"{row['name']}.md"
            bridge.write_text(bridge.read_text().replace(
                'skill_path: ' + row['path'], 'skill_path: ' + prepared_path))
            row['path'] = prepared_path
        profile['skill_root'] = prepared_root
        profile['installation_status'] = 'prepared'
        self.profile.write_text(json.dumps(profile, ensure_ascii=False, indent=2) + '\n')

    def audit(self, installed=False):
        return validate.audit(self.root,installed)

    def test_preparation_passes_but_is_not_installation(self):
        self.assertEqual(self.audit()['status'],'PASS')
        self.assertEqual(self.audit(True)['status'],'FAIL')

    def test_modified_skill_is_rejected(self):
        path=self.root/'.local/skill-installation/skills/cicd-scheduler/SKILL.md'
        path.write_text(path.read_text()+'\nTampered contract\n')
        self.assertTrue(any('Hash mismatch' in e for e in self.audit()['errors']))

    def test_missing_resource_is_rejected(self):
        path=next((self.root/'.local/skill-installation/skills/rust/references').glob('*.md'))
        path.unlink()
        with self.assertRaises(ValueError):self.audit()

    def test_duplicate_json_key_is_rejected(self):
        self.profile.write_text('{"skills":[],"skills":[]}')
        with self.assertRaisesRegex(ValueError,'Duplicate'):self.audit()

    def test_missing_bridge_is_rejected(self):
        (self.root/'sdd/chain/cicd-scheduler.md').unlink()
        with self.assertRaises(ValueError):self.audit()

    def test_excluded_skill_bridge_is_rejected(self):
        shutil.copyfile(self.root/'sdd/chain/rust.md',self.root/'sdd/chain/iced-engineering.md')
        self.assertEqual(self.audit()['status'],'FAIL')

    def test_escaping_and_symlink_paths_are_rejected(self):
        with self.assertRaises(ValueError):validate.safe(self.root,'../outside')
        alias=self.root/'alias';alias.symlink_to(self.root/'docs',target_is_directory=True)
        with self.assertRaises(ValueError):validate.safe(self.root,'alias/constitution.md')

    def test_install_success_and_idempotent_repeat(self):
        result=install_skills.install(self.root,True)
        self.assertEqual(result['status'],'INSTALLED')
        self.assertEqual(self.audit(True)['status'],'PASS')
        self.assertEqual(install_skills.install(self.root,True)['status'],'ALREADY_INSTALLED')
        self.assertTrue((self.root/'.local/skill-installation/skills/rust/SKILL.md').exists())

    def test_conflict_preserves_destination_and_profile(self):
        dest=self.root/'.codex/skills/rust';dest.mkdir(parents=True)
        (dest/'SKILL.md').write_text('User work\n')
        before=self.profile.read_bytes()
        with self.assertRaisesRegex(ValueError,'Conflicting destination'):
            install_skills.install(self.root,True)
        self.assertEqual((dest/'SKILL.md').read_text(),'User work\n')
        self.assertEqual(self.profile.read_bytes(),before)
        self.assertEqual(list(dest.parent.iterdir()),[dest])

    def test_readonly_error_preserves_metadata(self):
        before=self.profile.read_bytes()
        mkdir=Path.mkdir
        def denied(path,*args,**kwargs):
            if path==self.root/'.codex/skills':raise OSError(30,'Read-only file system')
            return mkdir(path,*args,**kwargs)
        with patch.object(Path,'mkdir',denied):
            with self.assertRaises(OSError):install_skills.install(self.root,True)
        self.assertEqual(self.profile.read_bytes(),before)
        self.assertEqual(self.audit()['status'],'PASS')

    def test_destination_symlink_is_rejected(self):
        (self.root/'.codex').mkdir()
        (self.root/'.codex/skills').symlink_to(self.root/'.local/skill-installation/skills',target_is_directory=True)
        with self.assertRaisesRegex(ValueError,'Symlink'):install_skills.install(self.root,True)

    def test_sdd_links_may_reach_authority_but_not_escape_root(self):
        module=validate.portable(self.root)
        file=self.root/'sdd/link-test.md'
        file.write_text('[Authority](../docs/constitution.md)\n')
        self.assertFalse(module.markdown_errors(self.root/'sdd',self.root))
        file.write_text('[Escape](../../outside.md)\n')
        self.assertTrue(module.markdown_errors(self.root/'sdd',self.root))

    def test_acceptance_parser_does_not_promote_mentions(self):
        module=validate.portable(self.root)
        self.assertEqual(module.acceptance_ids('Mention AC-99\n```\nAC-02: example\n```\n- AC-01 → REQ-01: actual\n'),{'AC-01'})
        with self.assertRaises(ValueError):module.acceptance_ids('AC-01: one\nAC-01: two\n')

    def test_local_link_drift_is_detected_even_with_rehashed_skill(self):
        path=self.root/'.local/skill-installation/skills/rust/SKILL.md'
        path.write_text(path.read_text()+'\n[Missing](references/missing.md)\n')
        profile=json.loads(self.profile.read_text())
        next(r for r in profile['skills'] if r['name']=='rust')['files']['SKILL.md']=validate.digest(path)
        self.profile.write_text(json.dumps(profile))
        self.assertTrue(any('missing link' in e for e in self.audit()['errors']))

    def test_unrelated_destination_is_preserved(self):
        dest=self.root/'.codex/skills/custom';dest.mkdir(parents=True)
        (dest/'notes.txt').write_text('Keep this')
        with self.assertRaisesRegex(ValueError,'unrelated'):
            install_skills.install(self.root,True)
        self.assertEqual((dest/'notes.txt').read_text(),'Keep this')

    def test_interrupted_package_copy_can_resume(self):
        original=shutil.copytree
        calls=[]
        def interrupted(src,dst,*args,**kwargs):
            calls.append(str(src))
            if len(calls)==2:
                raise OSError('Simulated interruption before next package')
            return original(src,dst,*args,**kwargs)
        before=self.profile.read_bytes()
        with patch.object(install_skills.shutil,'copytree',interrupted):
            with self.assertRaises(OSError):install_skills.install(self.root,True)
        self.assertEqual(self.profile.read_bytes(),before)
        self.assertEqual(install_skills.install(self.root,True)['status'],'INSTALLED')
        self.assertEqual(self.audit(True)['status'],'PASS')

    def interrupt_metadata(self, index=1, after=False):
        original = install_skills.atomic_write
        calls = []

        def interrupted(path, content):
            if path.name != 'install-journal.json':
                calls.append(path)
                if len(calls) == index + 1:
                    if after:
                        original(path, content)
                    raise OSError('Injected metadata interruption')
            return original(path, content)

        with patch.object(install_skills, 'atomic_write', interrupted):
            with self.assertRaisesRegex(OSError, 'Injected'):
                install_skills.install(self.root, True)
        return calls[-1]

    def test_resume_between_bridges_and_around_profile_commit(self):
        # Separate disposable fixtures for both sides of each representative commit.
        for index in [0, 1, 33, 66, 67]:
            for after in [False, True]:
                with self.subTest(index=index, after=after):
                    case = GovernanceTests()
                    case.setUp()
                    try:
                        baseline = case.root/'.local/skill-installation/skills/rust/SKILL.md'
                        before = baseline.read_bytes()
                        case.interrupt_metadata(index, after)
                        profile = case.profile.read_bytes()
                        check = install_skills.install(case.root, False)
                        self.assertEqual(check['status'], 'READY_TO_ATTEMPT')
                        self.assertEqual(case.profile.read_bytes(), profile)
                        self.assertEqual(install_skills.install(case.root, True)['status'], 'INSTALLED')
                        self.assertEqual(case.audit(True)['status'], 'PASS')
                        self.assertEqual(baseline.read_bytes(), before)
                        self.assertFalse((case.root/'config/governance/install-journal.json').exists())
                        self.assertEqual(install_skills.install(case.root, True)['status'], 'ALREADY_INSTALLED')
                    finally:
                        case.doCleanups()

    def test_recovery_rejects_external_metadata_without_overwrite(self):
        path = self.interrupt_metadata()
        path.write_text(path.read_text() + '\nExternal edit\n')
        before = path.read_bytes()
        with self.assertRaisesRegex(ValueError, 'Conflicting metadata'):
            install_skills.install(self.root, True)
        self.assertEqual(path.read_bytes(), before)

    def test_recovery_rejects_changed_package(self):
        self.interrupt_metadata()
        path = self.root/'.codex/skills/rust/SKILL.md'
        path.write_text('External package')
        with self.assertRaisesRegex(ValueError, 'Conflicting destination'):
            install_skills.install(self.root, True)
        self.assertEqual(path.read_text(), 'External package')

    def test_recovery_reaudits_baseline(self):
        self.interrupt_metadata()
        path = self.root/'.local/skill-installation/skills/rust/SKILL.md'
        path.write_text('Changed baseline')
        with self.assertRaisesRegex(ValueError, 'Catalog audit failed'):
            install_skills.install(self.root, True)
        self.assertEqual(path.read_text(), 'Changed baseline')

    def test_journal_tampering_is_rejected(self):
        self.interrupt_metadata()
        path = self.root/'config/governance/install-journal.json'
        journal = json.loads(path.read_text())
        journal['after']['sdd/chain/rust.md'] = 'Unexpected'
        path.write_text(json.dumps(journal))
        with self.assertRaisesRegex(ValueError, 'Invalid installation journal'):
            install_skills.install(self.root, True)

    def test_concurrent_installer_is_rejected_and_lock_released(self):
        import subprocess
        script = (
            "import sys; from pathlib import Path; "
            "sys.path.insert(0, sys.argv[1]); import install_skills; "
            "install_skills.install(Path(sys.argv[2]), True)"
        )
        with install_skills.installation_lock(self.root, True):
            result = subprocess.run(
                [sys.executable, '-c', script, str(ROOT/'tools/governance'), str(self.root)],
                capture_output=True, text=True)
            self.assertNotEqual(result.returncode, 0)
            self.assertIn('Installer already running', result.stderr)
            self.assertEqual(self.audit()['status'], 'PASS')
        self.assertEqual(install_skills.install(self.root, True)['status'], 'INSTALLED')

    def test_process_death_after_bridge_commit_can_resume(self):
        import subprocess
        script = """
import os, sys
from pathlib import Path
sys.path.insert(0, sys.argv[1])
import install_skills
original = install_skills.atomic_write
def killed(path, content):
    original(path, content)
    if path.parent.name == 'chain':
        os._exit(71)
install_skills.atomic_write = killed
install_skills.install(Path(sys.argv[2]), True)
"""
        result = subprocess.run([sys.executable, '-c', script,
                                 str(ROOT/'tools/governance'), str(self.root)])
        self.assertEqual(result.returncode, 71)
        self.assertEqual(self.audit()['status'], 'FAIL')
        self.assertEqual(install_skills.install(self.root, True)['status'], 'INSTALLED')
        self.assertEqual(self.audit(True)['status'], 'PASS')

    def test_journal_commit_interruption_and_missing_journal(self):
        original = install_skills.atomic_write
        def interrupted(path, content):
            original(path, content)
            if path.name == 'install-journal.json':
                raise OSError('Journal committed')
        with patch.object(install_skills, 'atomic_write', interrupted):
            with self.assertRaises(OSError):
                install_skills.install(self.root, True)
        self.assertEqual(self.audit()['status'], 'PASS')
        self.interrupt_metadata()
        (self.root/'config/governance/install-journal.json').unlink()
        with self.assertRaisesRegex(ValueError, 'Catalog audit failed'):
            install_skills.install(self.root, True)

    def test_profile_conflict_preserves_external_content(self):
        self.interrupt_metadata(67)
        self.profile.write_text(self.profile.read_text() + ' ')
        before = self.profile.read_bytes()
        with self.assertRaisesRegex(ValueError, 'Conflicting metadata'):
            install_skills.install(self.root, True)
        self.assertEqual(self.profile.read_bytes(), before)

    def test_process_death_during_copy_preserves_orphan_and_resumes(self):
        import subprocess
        script = """
import os, sys
from pathlib import Path
sys.path.insert(0, sys.argv[1])
import install_skills
def killed(source, destination):
    destination.mkdir()
    (destination/'partial.txt').write_text('unconfirmed copy')
    os._exit(72)
install_skills.shutil.copytree = killed
install_skills.install(Path(sys.argv[2]), True)
"""
        result = subprocess.run([sys.executable, '-c', script,
                                 str(ROOT/'tools/governance'), str(self.root)])
        self.assertEqual(result.returncode, 72)
        orphans = list((self.root/'.codex').glob('.ec-install-*/*/partial.txt'))
        self.assertEqual(len(orphans), 1)
        self.assertEqual(self.audit()['status'], 'PASS')
        self.assertEqual(install_skills.install(self.root, True)['status'], 'INSTALLED')
        self.assertEqual(self.audit(True)['status'], 'PASS')
        self.assertEqual(orphans[0].read_text(), 'unconfirmed copy')

    def test_evidence_hash_change_is_not_silently_accepted(self):
        module=validate.portable(self.root)
        target=self.root/'sample.txt';target.write_text('observed')
        record={'schema_version':2,'evidence':[{'id':'E1','inputs':[
            {'path':'sample.txt','sha256':validate.digest(target),'role':'implementation'}]}]}
        self.assertFalse(module.input_errors(record,self.root,[],self.root/'sdd/features/governance-adoption',True))
        target.write_text('changed')
        self.assertTrue(module.input_errors(record,self.root,[],self.root/'sdd/features/governance-adoption',True))

    def test_schema_one_cannot_claim_freshness(self):
        module=validate.portable(self.root)
        self.assertTrue(module.input_errors({'schema_version':1},self.root,[],self.root/'sdd/features/governance-adoption',True))

    def rewrite_source_bundle(self, transform):
        profile = json.loads(self.profile.read_text())
        bundle = self.root / profile['source_bundle']
        with zipfile.ZipFile(bundle) as archive:
            members = {name: archive.read(name) for name in archive.namelist()}
        transform(members)
        with zipfile.ZipFile(bundle, 'w', zipfile.ZIP_DEFLATED) as archive:
            for name, data in members.items():
                archive.writestr(name, data)
        # The resource-level oracle must reject damage even with a new container hash.
        profile['source_archives'][profile['source_bundle']] = validate.digest(bundle)
        self.profile.write_text(json.dumps(profile))

    def test_curated_sources_work_without_retired_packages(self):
        self.assertFalse((self.root / 'skills_to_use').exists())
        self.assertEqual(self.audit()['status'], 'PASS')

    def test_missing_source_bundle_declaration_is_rejected(self):
        profile = json.loads(self.profile.read_text())
        del profile['source_bundle']
        self.profile.write_text(json.dumps(profile))
        with self.assertRaises(KeyError):
            self.audit()

    def test_missing_source_archive_is_rejected(self):
        profile = json.loads(self.profile.read_text())
        (self.root / profile['source_bundle']).unlink()
        with self.assertRaisesRegex(ValueError, 'Missing path'):
            self.audit()

    def test_changed_original_resource_is_rejected(self):
        name = 'ec/new_skills/skills/cicd-scheduler/SKILL.md'
        self.rewrite_source_bundle(lambda members: members.update({name: b'Tampered'}))
        self.assertTrue(any('Original resource changed' in e for e in self.audit()['errors']))

    def test_missing_original_resource_is_rejected(self):
        name = 'ec/new_skills/skills/cicd-scheduler/SKILL.md'
        self.rewrite_source_bundle(lambda members: members.pop(name))
        with self.assertRaisesRegex(ValueError, 'Missing original resource'):
            self.audit()

    def test_source_bundle_path_escape_is_rejected(self):
        self.rewrite_source_bundle(lambda members: members.update({'../outside': b'escape'}))
        with self.assertRaisesRegex(ValueError, 'Unsafe source bundle'):
            self.audit()



if __name__=='__main__':unittest.main()
