# Diagnósticos Geiger — 09/11/2026

O pedido é explicar os erros por arquivo, incluindo finalidade e possibilidade
de remoção. Rota local: nenhum critério, scanner, dependência ou comando Cargo
foi trocado. Skill primária tool-output-engineering para preservação de bytes e
falhas; auxiliar software-design para separar explicação do despacho shell.
Caminhos/hashes consultados constam em [observations.json](observations.json).

## Implementação e consumidores

O [check Rust](../../../scripts/stacks/rust/check.sh) chama o novo
[helper de diagnóstico](../../../tools/quality/geiger_diagnostics.py)
apenas após erro nativo. Mantém argv locked/offline/all-features, execução por
membro, interrupção na primeira falha e exit 2 do gate. O código nativo continua
separado, inclusive valores diferentes de 2. O catálogo continua selecionando
Geiger nos dois workspaces; não foi criado outro gate ou instalador.

Cada membro passa a guardar stdout em geiger-N.txt, stderr em
geiger-N.stderr.log e o código nativo em geiger-N-exit.txt. O stderr capturado é
reproduzido uma vez ao término do membro. O JSON complementar aponta os logs e
seus hashes, linha do aviso, finalidade provável, retenção e próximo passo.
Não abre os caminhos dos avisos, executa comandos ou remove arquivos. Controles
no texto e UTF-8 inválido geram classificação desconhecida; nomes no resumo
são escapados. Saídas desconhecidas continuam recuperáveis nos logs. Falha do
helper não transforma a falha Geiger em aprovação.

## Arquivos efetivamente observados

A base histórica é run-N6YzWLdj, Geiger 0.14.0. Esta inspeção não reexecutou
a ferramenta. Caminhos completos, linhas originais e hashes estão no JSON.
Os 14 READMEs abaixo partem de
`.local/build/ec-cargo-home/registry/src/index.crates.io-1949cf8c6b5b557f/`.
Todos têm consumidor estático confirmado, em atributo doc com include_str!:

| Arquivo relativo ao registro | Consumidor relativo ao registro | Pode remover para corrigir? |
| --- | --- | --- |
| `sha2-0.11.1/README.md` | `sha2-0.11.1/src/lib.rs`:2 | Não: consumido por `include_str!` no build. |
| `getrandom-0.4.3/README.md` | `getrandom-0.4.3/src/lib.rs`:10 | Não: consumido por `include_str!` no build. |
| `crypto-common-0.2.2/README.md` | `crypto-common-0.2.2/src/lib.rs`:3 | Não: consumido por `include_str!` no build. |
| `md-5-0.11.0/README.md` | `md-5-0.11.0/src/lib.rs`:2 | Não: consumido por `include_str!` no build. |
| `cmov-0.5.4/README.md` | `cmov-0.5.4/src/lib.rs`:3 | Não: consumido por `include_str!` no build. |
| `hmac-0.13.0/README.md` | `hmac-0.13.0/src/lib.rs`:13 | Não: consumido por `include_str!` no build. |
| `rand_core-0.10.1/README.md` | `rand_core-0.10.1/src/lib.rs`:5 | Não: consumido por `include_str!` no build. |
| `siphasher-1.0.4/README.md` | `siphasher-1.0.4/src/lib.rs`:1 | Não: consumido por `include_str!` no build. |
| `cpufeatures-0.3.1/README.md` | `cpufeatures-0.3.1/src/lib.rs`:2 | Não: consumido por `include_str!` no build. |
| `ctutils-0.4.8/README.md` | `ctutils-0.4.8/src/lib.rs`:2 | Não: consumido por `include_str!` no build. |
| `digest-0.11.3/README.md` | `digest-0.11.3/src/lib.rs`:3 | Não: consumido por `include_str!` no build. |
| `const-oid-0.10.2/README.md` | `const-oid-0.10.2/src/lib.rs`:3 | Não: consumido por `include_str!` no build. |
| `chacha20-0.10.2/README.md` | `chacha20-0.10.2/src/lib.rs`:2 | Não: consumido por `include_str!` no build. |
| `hybrid-array-0.4.15/README.md` | `hybrid-array-0.4.15/src/lib.rs`:3 | Não: consumido por `include_str!` no build. |

Outros arquivos apontados, com caminhos a partir da raiz Ec:

| Arquivo | Finalidade | Pode remover para corrigir? |
| --- | --- | --- |
| `.local/build/ec-server-clean/debug/build/serde_core-d6749a4dd20f1f45/out/private.rs` | Código gerado do Serde no target Server | Não. Saída regenerável, necessária ao build; apagá-la não corrige a lacuna Geiger. |
| `.local/build/ec-agent-target/debug/build/serde_core-d6749a4dd20f1f45/out/private.rs` | Código gerado do Serde no target Agent | Não. Mesmos bytes não anulam o papel independente no outro target. |

O serde_core 1.0.229 do cache grava private.rs em build.rs:24 e o inclui em
src/crate_root.rs:165. Ambos os arquivos gerados observados contêm o módulo
__private229. Uma limpeza autorizada de cache fora de builds pode reconstruir
as saídas, mas o aviso pode reaparecer. Nenhuma limpeza foi feita nesta tarefa.
Arquivos futuros desconhecidos devem permanecer indeterminados; a explicação
por caminho não comprova existência nem dispensa investigar consumidores.

O Server parou em ec-server-results e o Agent em ec-agent-control. Os membros
seguintes não foram analisados por esses gates. Os avisos não comprovam
vulnerabilidade ou necessidade de remover dependências. A classificação de
erro foi mantida e não impõe zero unsafe como nova política.

## Inspeção, regressões e limites

Foram inspecionados o despacho, a origem de target_directory no Cargo metadata,
a propagação do erro, os quatro logs brutos históricos e os consumidores acima.
Os hashes dos logs foram registrados sem reescrevê-los. Os guias README de
scripts, verification-tools e scripts-inventory apontam para os novos artefatos.
T039 foi registrada na feature existente; critérios permanecem UNVERIFIED.

O [novo módulo de regressões](../../../scripts/tests/test_geiger_diagnostics.py)
cobre retenção, fontes geradas, caminhos desconhecidos/traversal, erros sem
avisos reconhecidos, preservação de bytes, linhas, controles, UTF-8 inválido e
conteúdo tratado como dados. O contrato em test_shell.py foi ampliado para
conferir captura stderr, JSON, membro e código nativo 17 sem mascarar a falha.
Ambos são descobertos pelo unittest já configurado no catálogo.

**Nenhum teste, helper novo, script de verificação ou automate.sh foi executado
nesta tarefa.** Não há novo pedido de commit para esta alteração e a política
canônica restringe execuções. A inspeção estática não demonstra execução ou
correção do Geiger instalado. Nenhum commit novo foi criado nem os três commits
anteriores foram alterados. Nenhum arquivo de aplicação, dependência, cache ou
log foi removido. A cobertura abaixo de 80% continua fora desta alteração.

## Verificação após novo pedido de commit

As observações de implementação e a ausência de testes descritas acima são
anteriores ao novo pedido de commit. O usuário escolheu sim; foi executada a
suíte integral, sem filtros, em `run-8ca5QvB9`. [Resultado agregado](first-verification-results.json):
37 tarefas aprovadas, seis erros e exit 2, entradas estáveis, sem cancelamento.
Nenhum commit foi criado.

Os oito novos testes unitários do diagnóstico e o contrato shell com código
nativo 17 passaram. O conjunto de automação executou 95 testes e reprovou um:
test_sources_and_final_layout exige Python operacional fora de scripts/. O
helper havia sido colocado em scripts/stacks/rust/geiger_diagnostics.py. Após
terminar essa rodada, ele foi movido para tools/quality/geiger_diagnostics.py;
a chamada, o import dos testes e as referências atuais foram ajustados. A
regra de layout e suas asserções foram preservadas, sem criar exceção.
A próxima execução completa deve verificar o endereço corrigido.

Os diagnósticos reais [Server](first-server-geiger-diagnostic.json) e
[Agent](first-agent-geiger-diagnostic.json) mostram os arquivos, a finalidade,
a orientação de preservação, linhas e hashes dos logs. Ambos registraram código
nativo 1 e o gate manteve exit 2; não substituir esses valores pelos de rodadas
anteriores. As duas falhas Geiger e as três de cobertura continuaram reprovadas.
Os demais gates, incluindo segurança, integração e Firefox, passaram.

O diagnóstico OSV confirmou refreshed=false, reutilizando o snapshot existente;
não houve atualização OSV nesta rodada. RustSec continuou local, sem download.
Os logs e diagnósticos da rodada e das anteriores foram preservados.

A cópia recuperável dos seis arquivos anteriores à correção de localização
fica em `.local/cleanup-20261109/geiger-layout-806bg47m/`, com caminhos originais, bytes, metadados e
inventory.json com SHA-256. A área já está explicitamente ignorada e excluída
da limpeza. Para restauração, copiar o arquivo desejado primeiro para outra
pasta de revisão e comparar com a versão atual; não sobrescrever alterações
posteriores nem restaurar o endereço antigo como operação vigente.

## Resultado da segunda suíte após corrigir a localização

`run-BvlgAwX0` executou novamente a suíte integral, sem filtros. Foram
**38 tarefas aprovadas e cinco erros**, exit 2, sem cancelamento ou alteração
das entradas durante a execução. [Resultado agregado](verification-results.json).
Os [95 testes de automação passaram](scripts-tests.log), incluindo os oito
casos novos, o contrato de falha shell e a regra de layout. A
[falha anterior de localização](first-scripts-tests.log) permanece preservada.
Ruff, ShellCheck, sintaxe, governança, SDD e seus testes também passaram.

Os diagnósticos reais [Server](server-geiger-diagnostic.json) e
[Agent](agent-geiger-diagnostic.json) foram produzidos pelo helper em tools/quality.
Server: 14 READMEs de dependências e um Rust gerado; Agent: um Rust gerado.
Os caminhos, finalidades e orientação de preservação foram exibidos; os códigos
nativos foram 1 e os gates permaneceram em 2. Os membros seguintes continuam
não examinados após a primeira falha. Não houve supressão ou remoção.

Persistem os dois erros Geiger e os três de cobertura: Server 21,56%, Agent
16,18% e frontend 14,78%, com mínimo de 80% por componente. A melhoria do
diagnóstico não corrige essas lacunas nem permite aprovar a suíte. Os demais
gates passaram, incluindo Audit/Deny, segurança, CodeQL, três harnesses HTTP,
Playwright e Firefox. [OSV](osv-reuse.json) confirmou refreshed=false, reutilizando
f42aea416d5cacae7bd533f846068b462453b8c01276802fbb4ca41ac9af8b9c;
RustSec continuou local. Nenhum download foi solicitado ou observado nesta
rodada. Os logs originais históricos conferidos mantiveram seus SHA-256.

**Nenhum novo commit, staging ou push foi feito.** A escolha sim condiciona a
criação de commits ao sucesso integral, e essa condição não foi cumprida. Os
três commits anteriores permanecem intactos; não houve autorização para
reescrever o histórico. Os arquivos não versionados desta alteração estão
preservados para inclusão futura, após resolução dos bloqueios e nova suíte.

Este resultado foi acrescentado após a execução. As atualizações finais de
relato e convergência tiveram inspeção manual; não renovam hashes de observações
anteriores nem promovem AC-09 ou os demais critérios UNVERIFIED a PASS.
