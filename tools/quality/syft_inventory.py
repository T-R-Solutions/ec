"""Comprova dependências dos locks, sem usar presença do arquivo como inventário."""

import json
import tomllib
from pathlib import PurePosixPath

from tools.quality.automation_config import unique


def verify_lock_inventory(root, components, report):
    observed = {}
    for package in report["artifacts"]:
        identifier = (package["type"], package["name"], package["version"])
        for location in package.get("locations", []):
            path = location["path"].removeprefix("/project/").lstrip("/")
            if ".." in PurePosixPath(path).parts:
                raise ValueError("localização do inventário fora das fontes")
            observed.setdefault(path, set()).add(identifier)
    counts = {}
    for component in components.values():
        name = component["lockfile"]
        path = root / name
        if path.name == "Cargo.lock":
            lock = tomllib.loads(path.read_text())
            expected = {
                ("rust-crate", package["name"], package["version"])
                for package in lock["package"]
                if package.get("source")
            }
        else:
            lock = json.loads(path.read_text(), object_pairs_hook=unique)
            expected = {
                ("npm", package.get("name", key.rsplit("node_modules/", 1)[-1]), package["version"])
                for key, package in lock["packages"].items()
                if "node_modules/" in key and not package.get("link")
            }
        if not expected or expected - observed.get(name, set()):
            raise ValueError("inventário omitiu dependências declaradas em " + name)
        counts[name] = len(expected)
    return counts
