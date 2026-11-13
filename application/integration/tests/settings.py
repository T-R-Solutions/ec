"""Serviços dos harnesses: projeção da configuração canônica da automação."""

import sys
from pathlib import Path

if not __debug__:
    raise RuntimeError("Harnesses Ec exigem asserts ativos; remova -O/PYTHONOPTIMIZE")

ROOT = Path(__file__).resolve().parents[3]
sys.path.insert(0, str(ROOT))
from tools.quality.automation_config import load_config

CONFIG = load_config()
SERVICES = CONFIG["services"]
HTTP_ADDRESS = SERVICES["http"]["host"] + ":" + str(SERVICES["http"]["port"])
