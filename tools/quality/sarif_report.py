"""Validação SARIF compartilhada: execução, severidade efetiva e extração real."""

import json
import sys
from pathlib import Path, PurePosixPath
from urllib.parse import unquote, urlsplit


def require(condition, message):
    if not condition:
        raise ValueError(message)


def notification_descriptor(notification, driver):
    reference = notification.get("descriptor", {})
    descriptors = driver.get("notifications", [])
    descriptor = None
    if "index" in reference:
        index = reference["index"]
        require(
            type(index) is int and 0 <= index < len(descriptors), "índice de notificação inválido"
        )
        descriptor = descriptors[index]
        require(
            "id" not in reference or reference["id"] == descriptor["id"], "descritor divergente"
        )
    elif "id" in reference:
        matches = [item for item in descriptors if item.get("id") == reference["id"]]
        require(len(matches) == 1, "descritor de notificação ausente ou ambíguo")
        descriptor = matches[0]
    return descriptor


def notification_level(notification, driver, invocation):
    descriptors = driver.get("notifications", [])
    descriptor = notification_descriptor(notification, driver)
    level = notification.get("level")
    if level is None:
        level = (descriptor or {}).get("defaultConfiguration", {}).get("level", "warning")
        for override in invocation.get("notificationConfigurationOverrides", []):
            ref = override.get("descriptor", {})
            if descriptor and (
                ref.get("id") == descriptor["id"]
                or ("index" in ref and ref["index"] == descriptors.index(descriptor))
            ):
                level = override.get("configuration", {}).get("level", level)
    require(level in ("none", "note", "warning", "error"), "severidade SARIF inválida")
    return level


def source_path(value, source):
    uri = urlsplit(value)
    require(not uri.netloc and uri.scheme in ("", "file"), "URI SARIF externa")
    path = unquote(uri.path).removeprefix("/project/")
    require(
        path and not PurePosixPath(path).is_absolute() and ".." not in PurePosixPath(path).parts,
        "caminho SARIF fora das fontes",
    )
    path = PurePosixPath(path).as_posix()
    return path if source == "." or path.startswith(source + "/") else source + "/" + path


def evaluate(report, targets=None, source=None, language=None):
    require(report.get("version") == "2.1.0" and report.get("runs"), "SARIF vazio ou inválido")
    count = 0
    healthy = set()
    metrics = {}
    for run in report["runs"]:
        driver = run["tool"]["driver"]
        require(driver.get("name") and driver.get("rules"), "consultas SARIF ausentes")
        require(isinstance(run.get("results"), list) and run.get("invocations"), "SARIF parcial")
        for result in run["results"]:
            require(
                result.get("ruleId") and not result.get("suppressions"),
                "achado SARIF suprimido ou inválido",
            )
        count += len(run["results"])
        for invocation in run["invocations"]:
            require(invocation.get("executionSuccessful") is True, "execução SARIF incompleta")
            for key in ("toolExecutionNotifications", "toolConfigurationNotifications"):
                for notification in invocation.get(key, []):
                    level = notification_level(notification, driver, invocation)
                    identifier = (notification_descriptor(notification, driver) or {}).get("id", "")
                    require(
                        not identifier.endswith((
                            "/extraction-errors",
                            "/extraction-warnings",
                            "/database-quality",
                        )),
                        "extração CodeQL degradada: " + identifier,
                    )
                    require(
                        level not in ("warning", "error"),
                        "diagnóstico SARIF: " + notification.get("descriptor", {}).get("id", level),
                    )
                    if targets is not None and identifier.endswith("/successfully-extracted-files"):
                        for location in notification.get("locations", []):
                            healthy.add(
                                source_path(
                                    location["physicalLocation"]["artifactLocation"]["uri"], source
                                )
                            )
        for metric in run.get("properties", {}).get("metricResults", []):
            key = metric.get("ruleId", metric.get("rule", {}).get("id"))
            if key in (
                "rust/summary/number-of-files-extracted-with-errors",
                "rust/summary/number-of-successfully-extracted-files",
            ):
                require(
                    key not in metrics and type(metric.get("value")) is int, "métrica Rust inválida"
                )
                metrics[key] = metric["value"]
    if targets is not None:
        require(set(targets) <= healthy, "CodeQL omitiu extração saudável de fontes")
        if language == "rust":
            require(
                metrics.get("rust/summary/number-of-files-extracted-with-errors") == 0,
                "CodeQL Rust contém falhas de extração ou métrica ausente",
            )
            require(
                metrics.get("rust/summary/number-of-successfully-extracted-files", 0)
                >= len(targets),
                "CodeQL Rust com extração insuficiente",
            )
    return {"state": "findings" if count else "passed", "finding_count": count}


def main():
    try:
        print(json.dumps(evaluate(json.loads(Path(sys.argv[1]).read_text()))))
        return 0
    except (ValueError, KeyError, TypeError, IndexError, OSError):
        print(
            "SARIF inválido, parcial, suprimido ou com diagnósticos de erro/aviso", file=sys.stderr
        )
        return 2


if __name__ == "__main__":
    sys.exit(main())
