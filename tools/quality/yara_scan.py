"""Contexto por arquivo e oráculo NDJSON da CLI YARA-X 1.0."""

import json
import pwd

EXTERNALS = ("filename", "filepath", "extension", "filetype", "owner")


def file_context(root, name):
    path = root / name
    with path.open("rb") as stream:
        header = stream.read(64)
        kind = "UNKNOWN"
        if header.startswith(b"MZ") and len(header) == 64:
            offset = int.from_bytes(header[60:64], "little")
            if offset <= path.stat().st_size - 4:
                stream.seek(offset)
                if stream.read(4) == b"PE\0\0":
                    kind = "EXE"
    uid = path.stat().st_uid
    try:
        owner = pwd.getpwuid(uid).pw_name
    except KeyError:
        owner = str(uid)
    return dict(
        filename=path.name,
        filepath=str(path),
        extension=path.suffix.lower(),
        filetype=kind,
        owner=owner,
    )


def external_arguments(values):
    arguments = []
    for name in EXTERNALS:
        arguments += ["--define", name + "=" + json.dumps(values.get(name, ""), ensure_ascii=False)]
    return arguments


def parse_ndjson(path, expected):
    rows = [json.loads(line) for line in path.read_text().splitlines() if line.strip()]
    if any(not isinstance(row, dict) or not isinstance(row.get("path"), str) for row in rows):
        raise ValueError("YARA-X não identificou os arquivos analisados")
    names = [row.get("path") for row in rows]
    if len(names) != len(set(names)) or sorted(names) != sorted(expected):
        raise ValueError("YARA-X omitiu, duplicou ou acrescentou arquivos ao inventário")
    findings = []
    for row in rows:
        if not isinstance(row.get("rules"), list):
            raise ValueError("YARA-X não informou resultados por arquivo")
        identities = set()
        for rule in row["rules"]:
            if (
                not isinstance(rule, dict)
                or not isinstance(rule.get("identifier"), str)
                or not rule["identifier"]
                or not isinstance(rule.get("meta"), list)
                or any(
                    not isinstance(item, list) or len(item) != 2 or not isinstance(item[0], str)
                    for item in rule["meta"]
                )
                or not isinstance(rule.get("namespace"), str)
            ):
                raise ValueError("YARA-X omitiu regra, namespace ou metadados de autoria")
            identity = (rule["namespace"], rule["identifier"])
            if identity in identities:
                raise ValueError("YARA-X repetiu regra no mesmo arquivo")
            identities.add(identity)
            findings.append(dict(path=row["path"], **rule))
    return findings
