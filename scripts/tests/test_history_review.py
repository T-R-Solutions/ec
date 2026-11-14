"""Histórico: provas de conteúdo não substituem detecção nem revisão da árvore."""

import hashlib
import json
import unittest
from types import SimpleNamespace
from unittest.mock import patch

from tools.quality.native_gates import gitleaks_history_review, gitleaks_review
from tools.quality.secret_history import history_blob_identities


class HistoricalReviewTests(unittest.TestCase):
    def setUp(self):
        self.commit = "a" * 40
        self.path = "rules/catalog/indicator.yar"
        self.key = self.commit + ":" + self.path
        self.digest = hashlib.sha256(b"public fixture\n").hexdigest()
        self.finding = {"Commit": self.commit, "File": self.path, "RuleID": "generic-api-key", "StartLine": 2}
        self.sources = {self.key: {"sha256": self.digest}}
        self.reviews = {self.path: {"sha256": self.digest, "rules": [{"id": "generic-api-key", "lines": [2], "reason": "Indicador público conferido."}]}}

    def review(self, finding=None, sources=None, roots=("rules/catalog",)):
        return gitleaks_history_review([finding or self.finding], sources or self.sources, self.reviews, roots)

    def test_historical_catalog_requires_exact_bytes_rule_line_and_declared_root(self):
        result = self.review()
        self.assertEqual((result["native_finding_count"], result["reviewed_count"], result["finding_count"]), (1, 1, 0))
        for change in ({"StartLine": 3}, {"RuleID": "private-key"}, {"Commit": "b" * 40}):
            self.assertEqual(self.review(dict(self.finding, **change))["finding_count"], 1)
        self.assertEqual(self.review(sources={self.key: {"sha256": "0" * 64}})["finding_count"], 1)
        self.assertEqual(self.review(roots=("another/catalog",))["finding_count"], 1)

    def test_current_tree_review_alone_never_approves_history(self):
        result = gitleaks_review([self.finding], {self.path: {"sha256": self.digest}}, self.reviews, history=True)
        self.assertEqual(result["finding_count"], 1)

    def test_file_digest_is_proven_against_bytes_in_the_same_commit(self):
        contents = b"public source\n"
        digest = hashlib.sha256(contents).hexdigest()
        report = {"Commit": self.commit, "File": "docs/validation/fixture/inputs.json", "RuleID": "generic-api-key", "StartLine": 2}
        mapping = json.dumps({"application/source.rs": digest}, indent=2).encode()
        calls = []
        def git(argv, **options):
            calls.append((argv, options))
            options["stdout"].write(mapping if argv[-1].endswith("inputs.json") else contents)
            return SimpleNamespace(returncode=0)
        with patch("tools.quality.secret_history.subprocess.run", side_effect=git):
            sources = history_blob_identities(".", [report], 5, 1000)
        result = gitleaks_history_review([report], sources, {}, ())
        self.assertEqual(result["finding_count"], 0)
        self.assertEqual(len(calls), 2)
        self.assertEqual(calls[1][0][-1], self.commit + ":application/source.rs")
        self.assertEqual(calls[0][1]["env"]["GIT_NO_LAZY_FETCH"], "1")

    def test_unproven_hex_value_remains_a_finding(self):
        report = dict(self.finding, File="docs/validation/fixture/inputs.json")
        source = {self.commit + ":" + report["File"]: {"sha256": self.digest, "verified_digest_lines": []}}
        self.assertEqual(gitleaks_history_review([report], source, {}, ())["finding_count"], 1)

    def test_missing_referenced_object_never_becomes_a_proven_digest(self):
        report = {"Commit": self.commit, "File": "docs/validation/fixture/inputs.json", "RuleID": "generic-api-key", "StartLine": 2}
        mapping = json.dumps({"application/missing.rs": "0" * 64}, indent=2).encode()
        def git(argv, **options):
            options["stdout"].write(mapping)
            return SimpleNamespace(returncode=0 if argv[-1].endswith("inputs.json") else 128)
        with patch("tools.quality.secret_history.subprocess.run", side_effect=git):
            sources = history_blob_identities(".", [report], 5, 1000)
        self.assertEqual(gitleaks_history_review([report], sources, {}, ())["finding_count"], 1)

    def test_invalid_commit_or_path_is_rejected_before_git(self):
        for change in ({"Commit": "--all"}, {"File": "../outside"}, {"File": "/etc/passwd"}):
            with patch("tools.quality.secret_history.subprocess.run") as git, self.assertRaises(ValueError):
                history_blob_identities(".", [dict(self.finding, **change)], 5, 100)
            git.assert_not_called()
