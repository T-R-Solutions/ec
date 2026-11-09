# Revisão de obsolescência em scripts/ — 09/11/2026

Foram analisados os 45 arquivos existentes em scripts/. A remoção justificada
foi documental: trechos superados e duplicados do README operacional foram
apagados, reduzindo-o de 181 para 130 linhas. **Nenhum arquivo operacional
completo foi identificado como descartável e nenhum foi excluído.**

O README continua com descoberta da Toolbox, entrada da suíte, exemplo nativo,
gates, seleção de fontes, critérios, relatórios e limites de execução. Detalhes de
preparação ficam no guia canônico; relatos de sessões ficam em seus registros.

## O que foi apagado do guia operacional

O [inventário prévio](inventory.json) registra classificação, motivos,
evidências, consumidores e fontes mantidas por grupo. A
[cópia integral anterior](before/scripts-README.md) preserva os bytes e as
observações históricas, incluindo os trechos retirados. Seus links relativos
mantêm o contexto original de scripts/; a cópia é um registro, não o guia atual.

| Grupo | Conteúdo retirado de scripts/README.md | Justificativa e fonte mantida |
| --- | --- | --- |
| DOC-01 | Relato de configs STIC/schema 2/locks retirados e encaminhamento pelo antigo native-execution | O consumidor usa exec e configs nativas. Os guias principais consumer-integration/application já são indicados no README; histórico em toolbox-native-20261108 e toolbox-alignment-20261109. |
| DOC-01 | Autorização particular de migração e relato da limpeza anterior | Autorizações datadas não são instruções operacionais permanentes. A política canônica task-verification é a autoridade; os relatos continuam nos registros originais e na cópia anterior. |
| DOC-01 | Descrição de retirada do alias trivy-toolbox | O parser de automate e o catálogo mantêm trivy; o alias não é opção atual. A tabela Trivy preserva o gate completo e seus critérios. |
| DOC-01 | Seção de inventário/retenção centrada na área privada da primeira limpeza | O guia atual encaminha para inventário e índice de evidências; recuperação daquela sessão permanece no relatório cleanup-20261109, sem apagar seu backup. |
| DOC-02 | Apêndice datado de ambiente, mounts, bases e resultado da adequação | O preparo detalhado já está em verification-tools e o relato de execução em toolbox-alignment-20261109. Orientações vigentes foram consolidadas no README, sem manter uma segunda explicação completa. |

Foram conservados os elementos exclusivos necessários: a explicação de
TOOLBOX_ROOT/TOOLBOX_BIN, ligação com a seleção de ambiente, o comportamento de
cache por raiz de snapshot, o exemplo com diretório novo, a tabela de bases e
Checks Bundle, captura de streams e códigos/limpeza. Os documentos de destino
permanecem acessíveis pelas referências ao final do README.

## Artefatos operacionais preservados e consumidores

O [mapa vigente](../../development/scripts-inventory.md) descreve os consumidores
por grupo. A revisão conferiu source/imports, chamadas do catálogo e runtime,
paths de configuração e convenções de descoberta, além das referências textuais.

- automate/toolbox/check, common/catalog/inputs/runtime, preflight integration e
  scanner-gate continuam encadeados; os checks Rust/frontend têm chamadas reais.
- Onze configs nativas são selecionadas pelos gates/checks; coverage.json é a
  política usada pela biblioteca de cobertura. Não são cópias substituídas por
  configuração central: a plataforma deixa regras e aprovação com o consumidor.
- Regras e fixtures Opengrep são descobertas por diretório e anotações; suites
  CodeQL são referenciadas pelo JSON de projetos; a regra YARA-X pertence ao
  check opcional. São recursos ativos mesmo sem import literal de cada fixture.
- check-report.sh e redact-trivy.jq são chamados pelo módulo native_gates.
  Sua divisão de responsabilidades não constitui duplicação de scanners.
- metrics/check.sh e coverage.sh são usados pelos checks de cobertura e pelas
  opções budget/performance. As opções continuam presentes no parser/catálogo.
- eslint-ratchet, summarize e validate-exceptions têm interfaces explícitas e
  casos em test_shell. Ausência no catálogo padrão não comprova depreciação;
  esses contratos opcionais foram preservados.
- Os quatro módulos test_*.py são descobertos por unittest no catálogo. Casos
  com nomes legacy/retired verificam rejeições e impedem reintrodução de caminhos
  retirados; não são as implementações legadas que foram substituídas.
- LICENSE preserva o aviso MIT do componente importado, incluindo seu escopo e
  exclusões. A menção a scripts legados no aviso não autoriza retirar a licença.

Não foram encontrados caches, downloads, relatórios, logs, backups ou diretórios
legados dentro da árvore scripts/ observada. As áreas de recuperação e os runs
existentes fora dela não foram selecionados para exclusão. Não houve limpeza por
idade, nome depreciado isolado ou ausência de referência textual apenas.

## Método, escopo e limites

Rota local: alteração documental delimitada, sem mudar contratos, configuração
ou código executável. Autoridades e estado do projeto foram relidos, incluindo
AGENTS, workflow, task-verification, contexto, constituição e ADR-001.

Skills lidas/aplicadas: software-design para distinguir consumidores e papéis;
readme-lifecycle-engineering para eliminar deriva, separar operação/histórico e
concentrar detalhes no guia canônico. Pacotes reais:

- .codex/skills/software-design/SKILL.md, ponte 1, SHA-256
  0c9c262d893476999857858f12dba2b789dbd522294d5b19c1ed99831a7aaddc.
- .codex/skills/readme-lifecycle-engineering/SKILL.md, ponte 1, SHA-256
  f6376d52d5cd250f10a9c7fa94abecbd78cf968fcd438672b173be5d5a4f037b.

A inspeção foi estática/manual: arquivos, referências, caminhos e diferenças.
Os 44 arquivos de suporte/execução em scripts/ mantêm os mesmos hashes. Nenhum
teste, script de verificação, automate.sh, ferramenta, download ou commit foi
executado. Não houve renderização de Markdown ou comprovação de integração real.
AC-27 e os critérios operacionais não recebem aprovação desta limpeza.

T038, o inventário de desenvolvimento e a convergência foram atualizados para
registrar o resultado; registros anteriores não foram reescritos. O limite da
análise é a árvore local e seus consumidores observáveis; não certifica usos
externos nem correção integral de todos os scripts. Não há outro candidato com
evidência suficiente de exclusão dentro desse escopo.

## Inventário, diário e recuperação

A área privada `.local/scripts-cleanup-20261109/` está explicitamente ignorada e
excluída desta limpeza. Contém before.json, previous com os cinco arquivos antes
da edição, differences.diff e operations.jsonl incremental. O inventário e as
cópias foram escritos antes de retirar os trechos.

O [fechamento](inspection.json) registra hashes, os 44 arquivos operacionais
preservados e as diferenças observadas. O [diário](operations.jsonl) mantém cópia
dos eventos da sessão. A cópia pública do README anterior está em before/.

Para revisar uma recuperação sem sobrescrever mudanças posteriores, da raiz:

```bash
mkdir .local/scripts-cleanup-20261109/review
cp -p --no-clobber .local/scripts-cleanup-20261109/previous/scripts/README.md .local/scripts-cleanup-20261109/review/scripts-README.before.md
diff -u .local/scripts-cleanup-20261109/review/scripts-README.before.md scripts/README.md
```

Faça merge apenas dos trechos desejados. Os hashes posteriores em inspection.json
permitem reconhecer edições posteriores a esta sessão. Não substituir a árvore
inteira pelos backups; restaurar relatos de migração no guia atual não torna
válidas suas instruções antigas. Os logs, auditorias, registros de falhas e
backups das sessões anteriores permanecem nos caminhos originais.
