"""Diagnóstico Geiger sem executar comandos ou abrir os caminhos dos avisos."""

import argparse
import hashlib
import json
import sys
from pathlib import Path, PurePosixPath

PREFIX = b"WARNING: Dependency file was never scanned: "


def relative_parts(path, root):
    if not root:
        return ()
    parent = PurePosixPath(root)
    if not path.is_absolute() or not parent.is_absolute():
        return ()
    if ".." in path.parts or ".." in parent.parts:
        return ()
    try:
        return path.relative_to(parent).parts
    except ValueError:
        return ()


def classify_file(name, cargo_home, target_directory, workspace_root):
    """Classificação lexical conservadora; não comprova existência ou desuso."""
    path = PurePosixPath(name)
    registry = relative_parts(path, str(PurePosixPath(cargo_home) / "registry/src"))
    target = relative_parts(path, target_directory)
    workspace = relative_parts(path, workspace_root)
    if len(registry) == 3 and path.name.casefold() in {
        "readme",
        "readme.md",
        "readme.rst",
        "readme.txt",
        "readme.markdown",
    }:
        return {
            "kind": "dependency_documentation",
            "owner": registry[1],
            "purpose": "Documentação de dependência registrada pelo Geiger como entrada do build; "
            "pode ser incluída por include_str! em atributos doc.",
            "removal_as_fix": False,
            "removal": "Não remover do cache Cargo. O aviso indica consumo pelo build, não desuso; "
            "a remoção pode quebrar a compilação ou a documentação da dependência.",
            "next_step": "Investigar a classificação de entradas não Rust pelo Geiger; "
            "manter o arquivo e conferir seu consumidor antes de alterar a integração.",
        }
    if len(target) >= 5 and target[1] == "build" and target[3] == "out" and path.suffix == ".rs":
        return {
            "kind": "generated_rust",
            "owner": target[2],
            "purpose": "Código Rust na saída OUT_DIR de um build script, usado pelo build "
            "mas não examinado pelo Geiger.",
            "removal_as_fix": False,
            "removal": "Não remover durante a execução nem para corrigir o gate. É saída "
            "regenerável: uma limpeza de build exige recompilação e pode recriar "
            "o mesmo arquivo e o mesmo aviso; não resolve a lacuna da análise.",
            "next_step": "Investigar o gerador e incluir sua saída na análise; "
            "não editar o arquivo gerado nem ocultar o aviso.",
        }
    if workspace and path.suffix == ".rs":
        return {
            "kind": "workspace_rust",
            "purpose": "Fonte Rust do workspace registrada como entrada do build.",
            "removal_as_fix": False,
            "removal": "Preservar: este aviso não comprova obsolescência da fonte.",
            "next_step": "Investigar por que a fonte usada pelo build não foi examinada.",
        }
    return {
        "kind": "unknown",
        "purpose": "Finalidade não determinada por um caminho reconhecido.",
        "removal_as_fix": None,
        "removal": "Indeterminado: preservar até investigar conteúdo, origem e consumidores. "
        "O diagnóstico não autoriza exclusão.",
        "next_step": "Consultar o aviso original e investigar o arquivo sem executar seu conteúdo.",
    }


def build_diagnostic(
    manifest,
    native_exit_code,
    stdout_path,
    stderr_path,
    cargo_home,
    target_directory,
    workspace_root,
):
    raw = {}
    contents = {}
    for stream, path in [("stdout", stdout_path), ("stderr", stderr_path)]:
        content = path.read_bytes()
        contents[stream] = content
        raw[stream] = {
            "path": str(path),
            "bytes": len(content),
            "sha256": hashlib.sha256(content).hexdigest(),
        }
    files = []
    for line_number, line in enumerate(contents["stderr"].split(b"\n"), 1):
        line = line.removesuffix(b"\r")
        if not line.startswith(PREFIX):
            continue
        encoded_path = line[len(PREFIX) :]
        try:
            name = encoded_path.decode("utf-8")
            valid_name = bool(name) and not any(ord(char) < 32 or ord(char) == 127 for char in name)
        except UnicodeDecodeError:
            name = encoded_path.decode("utf-8", errors="backslashreplace")
            valid_name = False
        entry = (
            classify_file(name, cargo_home, target_directory, workspace_root)
            if valid_name
            else {
                "kind": "unknown",
                "purpose": "Caminho vazio, com controles ou UTF-8 inválido.",
                "removal_as_fix": None,
                "removal": "Preservar; consultar os bytes do log original.",
                "next_step": "Investigar a saída inválida sem interpretá-la como comando.",
            }
        )
        files.append({"path": name, "stderr_line": line_number, **entry})
    return {
        "schema": 1,
        "manifest": str(manifest),
        "native_exit_code": native_exit_code,
        "raw": raw,
        "files": files,
        "scope": "Avisos reconhecidos deste membro; não é inventário completo do workspace.",
        "basis": "Caminhos e avisos nativos; o helper não abre os arquivos mencionados "
        "nem prova que existam ou possam ser excluídos.",
        "fallback": "Mensagens não reconhecidas, inclusive erros de compilação, permanecem "
        "nos logs brutos; ausência de caminhos extraídos não significa sucesso.",
    }


def describe(diagnostic):
    def quote(value):
        return json.dumps(value, ensure_ascii=True)

    lines = [
        f"Geiger: falha no membro {quote(diagnostic['manifest'])} "
        f"(saída nativa {diagnostic['native_exit_code']}).",
        f"Arquivos com aviso reconhecido: {len(diagnostic['files'])}.",
    ]
    for entry in diagnostic["files"]:
        lines.extend([
            f"- Arquivo: {quote(entry['path'])} (stderr, linha {entry['stderr_line']}).",
            *([f"  Pacote/unidade: {quote(entry['owner'])}."] if "owner" in entry else []),
            f"  Finalidade: {entry['purpose']}",
            f"  Pode remover para corrigir? {entry['removal']}",
            f"  Próximo passo: {entry['next_step']}",
        ])
    if not diagnostic["files"]:
        lines.append("Nenhum caminho reconhecido: consultar os logs; não há conclusão de desuso.")
    lines.append("A reprovação permanece; nenhum arquivo foi removido ou aviso suprimido.")
    lines.append(
        "O gate interrompe neste membro; os membros seguintes, se houver, não são analisados."
    )
    for stream, record in diagnostic["raw"].items():
        lines.append(f"Log bruto {stream}: {quote(record['path'])}; SHA-256 {record['sha256']}.")
    return "\n".join(lines) + "\n"


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--manifest", required=True)
    parser.add_argument("--native-exit-code", type=int, required=True)
    parser.add_argument("--stdout", type=Path, required=True)
    parser.add_argument("--stderr", type=Path, required=True)
    parser.add_argument("--cargo-home", required=True)
    parser.add_argument("--target-directory", default="")
    parser.add_argument("--workspace-root", required=True)
    parser.add_argument("--output", type=Path, required=True)
    args = parser.parse_args()
    diagnostic = build_diagnostic(
        args.manifest,
        args.native_exit_code,
        args.stdout,
        args.stderr,
        args.cargo_home,
        args.target_directory,
        args.workspace_root,
    )
    args.output.write_text(
        json.dumps(diagnostic, ensure_ascii=True, indent=2) + "\n", encoding="utf-8"
    )
    print(describe(diagnostic), end="", file=sys.stderr)


if __name__ == "__main__":
    main()
