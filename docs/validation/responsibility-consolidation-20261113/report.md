# Consolidação de responsabilidades — 13/11/2026

Estado observado: critérios da consolidação aprovados por execução completa
run-hOzphSex, seguida de revisão documental. [Resumo revisado](checks.json).
A validação do fechamento é a próxima etapa da tarefa; não houve commit ou publicação.

## Escopo e proprietários

| Conhecimento | Autoridade | Proteção |
| --- | --- | --- |
| Campos, limites e identificadores v1 | application/contracts/pipeline-v1.json e src/lib.rs | Geração Rust/import TS e regressões |
| Replay de publicação | projects/publication | HTTP/PostgreSQL e referência retirada |
| Replay de submissão | orchestration/submit | Recibos e snapshot fixado |
| Dependências externas | Cada workspace.dependencies | Guard rejeita versão local e import privado |
| HTTP/CLI | Módulos privados de service | Mesmas transações, rotas, sessão e failpoints |
| Frontend | Contexto, catálogo e entradas públicas | Typecheck, sessão/rascunhos e browsers |
| Produção medida | Cargo metadata | Denominador independente do LCOV |
| Histórico de segredos | Prova de bytes/contexto | Sem exclusões nem tabela paralela por commit |
| Estado vigente | sdd/project.md | Mapas e guias apontam para essa entrada |

Os dois executáveis/workspaces permanecem. O pacote público é membro do workspace
Server, sem importar domínios; o Agent o consome por path. JSON descreve protocolo,
sem substituir YAML desejado. SQL histórico não é gerado novamente por mudar um
limite; o guard exige compatibilidade ou evolução explícita. Validações em
fronteiras independentes são preservadas.

## Iterações reais

| Rodada | Resultado e consequência |
| --- | --- |
| run-NZqxTG9w | Interrompida por escrita da Toolbox fora do sandbox; limpeza concluída; sem aprovação |
| run-An85OKZ4 | Integrações/browsers passaram; SDD antigo, inventário Server e histórico impediram aprovação |
| run-DI8Sr4ZJ | 190 testes da automação e Gitleaks com provas passaram; Bandit exigiu revisão por conteúdo |
| run-2wGcTwt4 | Inventário/cobertura Server, linters, Gitleaks, Bandit e governança aprovados no foco |
| run-hOzphSex | Todas as 54 tarefas aprovadas, exit 0, sem drift/lock alterado/cancelamento/erro de limpeza |

Na rodada completa, os 1316 inputs registrados coincidiam antes do
fechamento documental. Coberturas, oráculos SQL/processos, Chromium/Firefox,
Playwright, CodeQL e scanners são observações reais, não resultados de mocks.
Ferramentas e métricas individuais estão no resumo e nos logs privados do run.

## Revisão de conteúdo e limites

As 35 detecções históricas foram conferidas: 25 hashes correspondem aos bytes dos
arquivos referenciados no mesmo commit; dez são indicadores públicos com bytes,
regra e linha idênticos ao catálogo importado/revisado. Todas as detecções nativas
permanecem. Sem prova, a ocorrência continua pendente. Não há allowlist genérica
de hashes, remoção de assinatura, exclusão de caminhos ou reescrita do histórico.
Os testes negativos exercitam ausência de objeto, digest sem prova, outro blob,
linha/regra divergentes e revisão da árvore sem contexto histórico.

Revisão manual: sessão reavaliada após lock; replay único e sem revalidar referência
externa já confirmada; YAML validado antes de conflito de base em pedido novo;
callbacks/transação mantêm efeitos atômicos. Versões herdadas e entradas públicas
não alteram autorização, efeitos de processos, recuperação ou bytes dos recibos.

T008/T009 e T071/T072 concluídas nos critérios da consolidação. AC-08 e AC-43
aprovados; AC-41/42 reobservados no escopo Ec. Demais critérios de produto mantêm
seus estados. Não é prova universal de ausência de defeitos nem aceite de
segredos por referência, isolamento, escala ou funcionalidades demonstrativas.
