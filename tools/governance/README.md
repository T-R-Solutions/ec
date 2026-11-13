# Ferramentas de governança

Utilitários Python executáveis para catálogo, integridade, instalação de skills e testes de rejeição. Não implementam Ec Server ou Ec Agent.

## Catálogo vigente

Localização, proveniência e recuperação estão no
[guia de instalação](../../docs/development/skill-installation.md).
A instalação ativa é única; preflight e auditoria não exigem baseline permanente
nem demonstram eficácia da skill ou descoberta pela interface.

## Interfaces

Os comandos abaixo documentam ferramentas; seu acionamento segue a
[política de verificação](../../docs/development/task-verification.md).

Pré-requisitos: Python 3.11+, biblioteca padrão e, para testes, diretório temporário gravável. Os caminhos dos comandos abaixo partem da raiz do repositório.

| Comando | Efeito e resultado esperado |
| --- | --- |
| `python3 tools/governance/validate.py` | Auditoria de leitura: catálogo, fontes, hashes, pontes e links cobertos; `PASS` e exit 0 quando íntegro |
| `python3 tools/governance/validate.py --require-installed` | Exige também instalação efetiva íntegra no destino declarado |
| `python3 tools/governance/install_skills.py --check` | Preflight sem escrita; instalação atual íntegra resulta em `ALREADY_INSTALLED` |
| `python3 -m unittest discover -s tools/governance/tests -v` | Suíte em cópias temporárias; `OK` e exit 0 quando os cenários passam |

O instalador também possui modo de aplicação com escrita. Consulte o [procedimento canônico](../../docs/development/skill-installation.md) antes de usá-lo: o catálogo já está instalado, e repetir uma instalação não é etapa de onboarding.

## Arquivos e falhas

[validate.py](validate.py) lê o [perfil](../../config/governance/profile.json), confere os pacotes e a preservação das fontes. [install_skills.py](install_skills.py) rejeita conflitos e destinos alheios. A [suíte](tests/test_governance.py) exercita controles de rejeição e instalação idempotente.

Uma divergência exige examinar o arquivo e sua origem; não renovar hashes para encobri-la. Não manter baseline duplicada; usar a revisão correspondente no Git/GitHub. Os testes derivam fixtures temporárias da instalação vigente. Falha por diretório temporário indisponível deve ser tratada no ambiente antes de interpretar o resultado da suíte.

O validador [SDD](../../sdd/README.md) verifica outro contrato: estrutura, rastreabilidade e evidências. Nenhuma dessas ferramentas certifica eficácia das skills, comportamento ou conformidade do produto.

[Contribuição](../../docs/development/contributing.md) · [Política de testes](../../docs/development/testing.md) · [Projeto](../../README.md)
