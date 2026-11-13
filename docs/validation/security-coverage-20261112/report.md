# Ampliação da cobertura das verificações — 12/11/2026

O pedido autoriza ampliar as configurações após consultar fontes oficiais,
manter uma fonte por responsabilidade e retirar configurações substituídas.
Catálogos foram baixados com autorização específica; o operador indicou a
instalação shared-toolbox em dev_audit. A execução de testes, qualificação,
escopo completo e atualização necessária de bases foi autorizada nesta sessão.
Não houve commit/publicação nem atualização inferida de versões das ferramentas.

## Configuração e alcance

O catálogo permanece em scripts/config/automation.json, com referências às
políticas nativas existentes. Toolbox mantém a seleção de versões/imagens no
seu toolbox.json e estado operacional; Ec consulta central resolve, sem copiar
os ponteiros ativos. Imagens selecionadas: 12 ativas e a variante Firefox,
sem inferir imagens Ec de produção. Export por ID, sem socket nos scanners.

- Ruff ALL/preview e ignore-noqa; ShellCheck opcionais/dataflow/style/sourced.
- Clippy all/pedantic/nursery/cargo, todos os targets/features e keep-going;
  restriction em bloco é contraditório e não foi habilitado.
- Machete com metadata e Geiger all-deps em todos os membros/features.
- CodeQL security-and-quality: Server, Agent, frontend e automação Python.
- Gitleaks: árvore, dotenv, refs/reflogs/HEAD; redação e rejeição de histórico
  insuficiente/mutável. Não há download do histórico nem flag de archive
  inexistente na versão instalada.
- Opengrep: 231 IDs declarados, incluindo oito locais; catálogos elttam,
  Trail of Bits e njsscan, com revisões, licenças e hashes. Schema/padrões
  qualificados offline nas linguagens do catálogo; positivos/negativos locais
  testados separadamente. Não se alega teste positivo para cada regra importada.
- YARA-X: 754 arquivos externos signature-base, além da sentinela Ec; compilação
  por grupo, contexto real por arquivo e NDJSON também para arquivos limpos.
- Trivy comprehensive nas fontes/imagens, inventário de licenças separado;
  Syft all-layers/archives e Grype sobre o mesmo inventário, sem filtros pessoais.
  OSV mantém os três locks declarados e all-packages, sem ignores.
- Deny preserva as exceções necessárias aos workspaces e duplicidades como
  aviso; não foi forçada atualização incompatível para eliminar duplicidades.

Referências oficiais por versão e limites: [guia de preparação e cobertura](../../development/verification-tools.md).

## Qualificação e reparos observados

Toolbox validate e doctor passaram; 87 testes passaram. O novo resolvedor é
somente leitura e usa o mesmo seletor da execução, sem preparar ou ativar imagens.

O primeiro run completo ampliado, run-6MwleBfs, terminou com exit 2: 41 passed,
cinco findings, sete errors e um blocked. Integrações e navegadores passaram;
os 173 testes passaram. Os quatro alvos CodeQL concluíram extração saudável,
com 17 ocorrências na nova suíte. Esse run não aprova os bytes posteriores.

As rodadas focais corrigiram incompatibilidades concretas:

1. Opengrep --validate baixa metachecks remotos obrigatoriamente. Foi trocado por
   qualificação estrita sem rede; o teste local usa uma configuração contendo
   cópia efêmera dos bytes locais, sem catálogos externos sem fixtures associadas.
   Parsers respeitam extensões; regras genéricas não forçam parsing incorreto.
2. Docker/containerd identifica o índice OCI, diferente do hash da configuração.
   A prova percorre índice/manifesto/configuração/camadas, verifica hashes e
   liga os IDs nativos Trivy/Syft à configuração correta. Um export local real
   foi conferido e descartado; regressões rejeitam substituição de camadas.
3. Trivy image não aceita include-dev-deps nessa versão. A flag exclusiva de
   filesystem não é enviada à imagem, que usa o catálogo instalado suportado.
4. Descartes automáticos Grype permanecem no inventário como pendências,
   separados das associações nativas. Incluem associações de CVEs do kernel
   aos headers: não são automaticamente vulnerabilidades aplicáveis à imagem.
5. YARA-X 1.0 emite metadados como pares ordenados, preservados sem perder
   autoria/chaves repetidas. A única ocorrência inicial era o literal da
   sentinela no teste; foi composto em runtime, mantendo o controle positivo
   real e todos os arquivos/regras examinados, sem exclusão ou baseline.
6. A agregação excedeu ARG_MAX com centenas de recibos. Ela agora lê o arquivo
   persistido, com regressão de 2.000 recibos. O run interrompido conservou
   exit 126 e exportou os logs, com intermediários descartados e cleanup sem erro.

run-lsz5eH6D completou Trivy nas fontes e 13 imagens: 8.519 ocorrências nativas.
Grype e Syft/Grype completaram 13 imagens: 9.290 associações e 3.009 descartes
preservados como pendências. São observações por imagem, com repetições; não
somar ferramentas como se fossem vulnerabilidades únicas confirmadas.

A rodada focal run-y4MBZCOs aprovou os 176 testes e os testes locais Opengrep.
O SAST ampliado concluiu com achados, sem erro operacional/omissão de arquivos.
O resultado funcional das imagens está observado; a repetição completa sobre
os arquivos estabilizados ainda está em execução. Nenhum resultado histórico
foi promovido a aprovação de segurança atual.

## Limites e pendências

Achados Ruff/ShellCheck/Clippy/CodeQL/Gitleaks/Opengrep, revisões Bandit invalidadas
por conteúdo alterado e ocorrências das imagens exigem análise própria. Valores
de segredos não são reproduzidos. Nenhum finding foi dispensado para obter verde;
revisões Bandit das fontes alteradas foram retiradas, sem renovar hashes.

Não se alega cobertura de todas as plataformas/features possíveis, eficácia
positiva de todas as regras ou ausência geral de malware. O alcance é o das
versões, fontes, regras e bases observadas. Não há certificação jurídica de
licenças nem aprovação implícita das imagens. Não houve alteração de aplicação
para resolver automaticamente o novo estoque de qualidade.

Logs privados em .local/logs/2026-11-12 seguem retenção de duas datas; este
relatório é síntese das observações, não cópia integral nem retenção dos streams.


## Conferência adicional da configuração efetiva Syft

O run-4f8byi1k concluiu 54 resultados: 43 passed, nove findings e dois erros
Clippy, com entradas/locks estáveis e todas as jornadas de navegador aprovadas.
YARA-X ficou sem ocorrências após a composição runtime do literal de teste.

A leitura do descriptor Syft demonstrou uma limitação adicional: dev dependencies
JavaScript estavam desativadas por padrão. Não se usou o resultado anterior
para afirmar máxima abrangência. A política foi ampliada na mesma fonte nativa:
união dos catalogadores image/directory/file, dev deps e pacotes .NET declarados
sem exigência de DLL. Buscas remotas permanecem desligadas; versões não são
adivinhadas. O inventário mistura pacotes declarados e instalados, não prova
alcançabilidade/exploração de cada associação.

A qualificação real confirmou as opções efetivas e 283 pacotes, cobrindo as
249 dependências externas distintas dos três locks. Por lock: 102 Server,
21 Agent e 147 frontend; repetições entre workspaces não são pacotes distintos.
Foi acrescentado oráculo por pacote/versão/tipo/localização do lock, com regressão
de perda de dev dependency, versão incorreta e atribuição a outro lock.
Uma nova repetição completa validará esses bytes; o run anterior não os aprova.


## Resultado completo observado — run-cHRcsFAw

A repetição completa terminou com **exit 2**: **43 passed, nove findings e dois
errors Clippy**. As 54 tarefas produziram resultados; não houve cancelamento,
drift de entradas ou alteração de locks. A limpeza terminou sem erro e os
intermediários foram descartados. Passaram 177 testes Ec, as duas coberturas Rust,
a cobertura frontend, Geiger nos dois workspaces, OSV, testes locais Opengrep,
YARA-X, três integrações e os três checks de navegador. A Toolbox manteve seus
87 testes/validate/doctor aprovados sobre código inalterado.

| Check reprovado | Resultado observado |
| --- | --- |
| Ruff | 13.023 ocorrências de qualidade/segurança, sem fixes automáticos |
| ShellCheck | 4.234 ocorrências, incluindo sourced/opcionais |
| Clippy Server e Agent | Exit 101 com grupos ampliados; inclui documentação/metadata/estilo, sem forçar atualização incompatível |
| Gitleaks | 49: dez em quatro arquivos do catálogo público YARA e 39 no histórico, vários em manifestos de hashes; não são credenciais Ec confirmadas |
| Opengrep | Dez achados; catálogo e testes locais qualificados, sem omissão de alvos |
| CodeQL | 17 achados na suíte ampliada, quatro alvos com extração saudável |
| Trivy | 8.607 ocorrências nas fontes/13 imagens, com licenças inventariadas separadamente |
| Grype e Syft/Grype | Cada check: 9.291 associações nativas e 3.009 descartes mantidos como pendências nas 13 imagens |
| Bandit | 392 achados brutos, 342 revisados e 50 pendentes; nenhum hash renovado para herdar aceite |

O SBOM real apresentou 283 pacotes e comprovou por lock todas as 102 dependências
externas Server, 21 Agent e 147 frontend. A configuração efetiva confirmou dev
deps, all-layers e a união de catalogadores. Os três scanners usaram o mesmo
conjunto de 13 IDs de imagem. YARA-X ficou sem ocorrências após o reparo do literal
de teste. Não houve redução de regras ou exclusão de código para recuperar verde.

[Resumo dos resultados, identidades e limites](run-cHRcsFAw.json). Os totais são
ocorrências nativas por check/imagem; não constituem contagem de vulnerabilidades
únicas nem confirmação de exploração. Grype e Syft/Grype analisam o mesmo escopo:
não somar os dois totais. CVEs repetidas na base compartilhada das imagens também
se repetem entre alvos. Descartes de headers do kernel exigem contexto próprio.

## Fechamento posterior e próximo trabalho

A auditoria de coesão encontrou scanner_roots, seletor antigo sem consumidor no
fluxo nativo atual. Foi removido; as consultas auxiliares trivy/inventory usam
inputs.roots, incluindo dotenv, com regressão de equivalência à seleção all.
Esse refactor posterior não mudou alvos/comandos dos scanners da execução
completa. Sua validação focal e a auditoria documental têm registro separado;
não se atribui o hash novo à execução antiga.

O escopo de configuração/qualificação foi executado. **A aprovação de segurança
continua reprovada** e não se declara o projeto livre de problemas. Próxima etapa:
triagem contextual de Gitleaks/SAST/CodeQL e dos pacotes/imagens, revisão Bandit
por código e correção gradual do estoque Ruff/ShellCheck/Clippy. Priorizar
ocorrências confirmadas e correções compatíveis; não atualizar dependências só
para eliminar duplicidades permitidas. Imagens novas/updates de binários e
publicação não foram realizados por inferência.

Fechamento focal **run-Ur88v3HT**: seis tarefas aprovadas, **177 testes Ec
passaram**, exit zero, entradas/locks estáveis e limpeza concluída. O seletor
inativo foi retirado; aliases trivy/inventory equivalem à seleção all, conforme
regressão. [Recibo focal](closure.json). O fechamento não reclassifica os
resultados nem renova os hashes da suíte completa. A auditoria documental
posterior tem apenas esse alcance.

Auditoria documental **run-KHaz42J3**: cinco checks aprovados, exit zero,
sem drift e com limpeza concluída. [Recibo documental](documentation.json).
Este registro de observação foi acrescentado depois da auditoria e não renova
seus inputs nem os da execução completa.
