# Integração de Geiger, Coverage, Trivy e CodeQL ao padrão

Data: 2026-11-07. Pedido: implementar os quatro checks no run padrão, cobertura
de linhas >=80% por componente e configurações de segurança baseadas nas fontes
oficiais. Não houve pedido de commit nem autorização para executar verificações.

## Alterações e decisões

O catálogo inclui sete tarefas adicionais: Geiger/coverage no Server e Agent,
coverage frontend, Trivy e CodeQL. Checks anteriores, governança, filtros e
deduplicação permanecem. Coverage Rust usa target privado e precede os builds dos
harnesses; o runtime acrescenta as métricas ao resultado sem mudar a prioridade
dos códigos de saída ou o contrato de cancelamento/timeout.

A política de cobertura compara linhas cobertas/total com 80%, por componente,
sem arredondamento ou média. Os produtores usam diretórios novos e inventário
independente de fontes; ausência de relatório, fonte omitida ou inputs alterados
nunca aprova. Vitest/provider V8 foram fixados em 4.1.12. O npm acrescentou 15
entradas ao lockfile; a inspeção dos manifests não encontrou mudança de versão
ou remoção de pacote já existente.

Geiger registra árvore nativa, metadata dos membros próprios, versão/hash do plugin
e exit, sem criar proibição de unsafe. A disponibilidade/eficácia real não foi medida.

Trivy mantém 0.61.1, por compatibilidade com o contrato existente. Base schema 2,
hashes e UpdatedAt/NextUpdate upstream são verificados; validade máxima de 24h é
decisão operacional Ec. Checks de configuração são os embutidos na CLI. Há
inventário dos três lockfiles e contagem dos três tipos de achados. Redação em pipe
remove valores/trechos de segredos do relatório persistido.

CodeQL 2.27.3 usa packs Rust 0.1.45 e JavaScript 2.4.8 do bundle correspondente,
security-extended e bancos separados para Server, Agent e frontend. Cada banco
é vinculado às fontes/manifests/configurações e ao bundle por hashes. O scan usa
cópias, sem download; exit zero com resultados é findings, e SARIF parcial,
suprimido ou com diagnósticos de erro/aviso reprova. Preparo explícito usa o lock
existente; a extração Rust sem build pode executar build.rs/proc-macros.

Fronteiras consideradas: configurações pessoais/supressões poderiam ocultar
achados; bases/bancos antigos poderiam aparentar análise atual; rede poderia
expor fontes/credenciais ou alterar os inputs. Configuração explícita, env vazio,
snapshots, manifests, gates conservadores e bwrap sem rede tratam esses riscos.
Isso não certifica segurança completa nem isolamento para código hostil.

Fontes oficiais, versões, justificativas, preparo e limitações estão no
[contrato nativo](../../../scripts/security/native-scanners.md). A referência
genérica de database create omite Rust em uma lista; o extractor Rust da tag
selecionada declara build-mode none e o guia oficial de extração confirma suporte.

## Inspeção realizada

Foram lidos os arquivos de autoridades, skills e pontes selecionadas, contratos do
runner/adapters, configurações e manifests. A revisão manual comparou os arquivos
alterados com o backup anterior, seguiu os fluxos de sucesso/bloqueio/erro e revisou
os novos oráculos. A comparação é por conteúdo; não depende do índice Git, que
contém muitas mudanças prévias. Nenhum arquivo de aplicação Rust ou skill foi editado.

Operação de dependências efetivamente executada, no frontend:

```bash
npm install --package-lock-only --ignore-scripts --save-dev --save-exact @vitest/coverage-v8@4.1.12 --no-audit --no-fund --cache /tmp/ec-quality-npm-cache
```

Resultado observado: exit 0, lockfile atualizado. Não instala o provider em
node_modules nem valida execução do Vitest. Não é evidência de cobertura/build.

Regressões foram escritas/atualizadas em `scripts/tests/test_shell.py` e
`scripts/tests/test_quality.py`: catálogo/seleção/ordem, fronteiras de 80%,
relatórios incompletos/obsoletos, fontes omitidas, política enfraquecida,
inputs alterados, bloqueio de rede sem fallback, base Trivy expirada/alterada,
bundle/pack/banco ausentes ou divergentes, SARIF parcial/suprimido e redação.
São testes preparados, **não executados**. Fixtures não comprovam motores reais.

## Evidências e retomada

AC-01 a AC-11 da feature scripts-verification estão UNVERIFIED nos inputs atuais.
As evidências agregadas das features governance-adoption e first-functional-journey
também vinculavam scripts/configurações alterados; seus critérios atuais foram
marcados UNVERIFIED. Resultados históricos permanecem nos backups, sem renovação
dos hashes antigos para aparentar aprovação atual.

Não foram executados automate.sh, plan, testes, validadores, lints, scanners,
preparos de bases/bancos ou instalação de ferramentas. Cobertura atingida,
compatibilidade real dos adapters, namespace bwrap e duração/RAM/disco continuam
não verificados. Os pré-requisitos devem ser preparados explicitamente conforme
o guia. A reprovação histórica de Cargo Deny por licenças privadas não foi corrigida
ou reexecutada nesta tarefa.

Retomada de verificações: somente após pedido de commit, formular a pergunta
canônica e aguardar sim/não. Com sim, usar a suíte completa sem filtros; com não,
seguir com o commit solicitado sem scripts, preservando os critérios não verificados.

Preservação: `previous.zip` contém os arquivos originais de scripts, qualidade,
feature e manifests/guias afetados; `previous-related-evidence.zip` contém as
evidências/convergências anteriores das outras duas features. `inputs-before.json`
registra hashes anteriores; `changed-files.json` lista os arquivos de trabalho
alterados e seus hashes atuais. Nenhum commit ou publicação foi criado.
