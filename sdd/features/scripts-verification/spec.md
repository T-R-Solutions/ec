# Especificação — scripts e encerramento Ec

- REQ-01: remover componentes importados sem consumidor, preservando origem, licenças e observações históricas.
- AC-01 → REQ-01: inventário por conteúdo cobre todos os arquivos originais de scripts, decisões/grupos justificam remoções e não há referências operacionais quebradas.
- REQ-02: entrada proporcional executa comandos reais, sem instalações implícitas, com seleção/cwd/saídas e logs honestos.
- AC-02 → REQ-02: regressões demonstram seleção Rust/npm/harnesses, falha bruta, ausência bloqueada, timeout, cancelamento, retenção, espaços, outro cwd e rejeição de alterações de inputs.
- REQ-03: encerramento referencia uma política única em AGENTS, README, SDD, templates, pontes e skills relevantes, com integridade rastreável.
- AC-03 → REQ-03: inspeção das entradas e auditores conferem os dois pacotes revisados/recuperação, fontes originais preservadas e estados de evidência atualizados sem renovar observações antigas.
- REQ-04: observar todos os checks aplicáveis e separar bloqueios da implementação e de outras features.
- AC-04 → REQ-04: governança, Rust, frontend, scripts, scanners de segredos/SAST/dependências (incluindo teste real das regras) e harnesses integration/E2E passam no ambiente autorizado, com registros novos; qualquer ferramenta obrigatória ausente mantém este critério UNVERIFIED.

Limites: Linux local confiável, sem instalação global, commit, publicação ou promessa
nova de produto; SAST/CVEs têm cobertura delimitada, sem avaliação de licenças de dependências, e sem dispensa de ACs da
primeira jornada. erase.md é consulta temporária, sem consumidor operacional.

## Reintegração de scanners

- REQ-05: reintegrar Opengrep e OSV para a stack Ec sem downloads implícitos, aceites por ausência, configuração herdada ou perda de diagnósticos.
- AC-05 → REQ-05: plano/consumidores incluem SAST Rust/TS/Vue com regras/fixtures locais e OSV offline nos três lockfiles; testes de contrato rejeitam erros, skips, relatório incompleto, ausência/corrupção de base, supressões e exit inconsistente, com pré-requisitos atuais documentados. Eficácia das regras e scan real são exigidos em AC-04, sem aprovação por executáveis fixture.

## Migração shell autorizada — 2026-11-06

REQ-06 substitui a manutenção de Python operacional por Bash em scripts, sem
instalações implícitas ou perda dos contratos de diagnóstico/seleção/supervisão.
AC-06 → REQ-06: scripts_erase ausente, backup recuperável, nenhuma implementação
operacional Python/TypeScript em scripts, divisão lógica sem runners duplicados,
capacidades adicionais acessíveis e regressões shell aprovadas; ferramentas/políticas
indisponíveis explicitamente bloqueadas, sem alegar eficácia real. AC-04 permanece
exigindo execução dos motores reais. Governança/harnesses externos não são convertidos.

## Ferramentas nativas e toolbox — 2026-11-06

REQ-07: usar plugins Cargo globais independentemente do cache Ec e o toolbox
descrito em `.local/usage.md` para ferramentas externas, com configurações nativas
versionadas.
- AC-07 → REQ-07: testes rejeitam ferramentas/imagens/adapters ausentes, relatos
inconsistentes, skips/flaky no Playwright e supressões de segredos; perfis selecionam
os novos checks e o plano não instala nada. Execução real permanece em AC-04.
REQ-08: ampliar os controles padrão de dependências/origens/licenças, qualidade shell
e Python; bases OSV registradas têm validade operacional de 24h e RustSec exige
commit de até sete dias.
- AC-08 → REQ-08: configurações explícitas sem ignores de dependências, versões e
fontes oficiais documentadas, deny/audit/machete padrão Rust e Ruff/ShellCheck
obrigatórios; resultado real e limitações identificados por ferramenta.


## Gates padrão — 2026-11-07

- REQ-09: Geiger nos dois workspaces, cobertura Rust/frontend e Trivy/CodeQL integram o padrão sem enfraquecer seleção, supervisão ou gates anteriores.
- AC-09 → REQ-09: catálogo padrão e seleções preservam governança, deduplicação e ordem anterior ao build dos harnesses; Geiger registra inventário/versionamento sem impor zero unsafe.
- REQ-10: cobertura de linhas >=80% por componente, sem arredondamento nem média entre Server/Agent/frontend; geração/avaliação no mesmo run e inventário de produção completo.
- AC-10 → REQ-10: regressões rejeitam 79,99%, relatório antigo/ausente/truncado, denominador vazio e fonte omitida; 80% e 80,01% aprovam somente o gate; ferramentas/provider ausentes bloqueiam.
- REQ-11: Trivy 0.61.1 e CodeQL 2.27.3 com configuração versionada, scan sem rede, preparo explícito, integridade e classificação conservadora; CodeQL cobre os três componentes.
- AC-11 → REQ-11: regressões rejeitam base expirada/alterada, bundle/pack/banco ausente, banco divergente, SARIF parcial/suprimido/erro e inventário incompleto dos lockfiles; exit zero CodeQL com resultados é findings; Trivy conta vuln/misconfig/secret sem persistir os valores dos segredos no relatório.

Os novos critérios exigem execução posterior autorizada. O pedido atual não inclui
commit nem execução de scripts. Resultados anteriores não aprovam estes inputs.

## Dados e ambientes Toolbox — 2026-11-07

- REQ-12: separar executáveis/ambientes e snapshots preparados pela Toolbox dos alvos, regras, configuração e validade definidos pelo Ec, nas dez famílias solicitadas. Preservar cobertura do catálogo vigente e não inventar suporte, regras ou snapshots.
- AC-12 → REQ-12: configurações e wrappers disponibilizam Trivy Docker, Grype, Bandit e Syft como checks adicionais, mantêm OSV offline e regras locais Opengrep, recusam histórico Gitleaks ausente e YARA-X indisponível; preparo de DB aceita somente Trivy/OSV/Grype, vincula snapshot real e scan não atualiza dados. Relatórios inválidos, inventário Trivy incompleto, exit inconsistente e SBOM vazio são rejeitados. A geração isolada de SBOM é medição; security:syft exige também análise Grype sem achados. Execução real permanece necessária para aceite.

## Conclusão restrita ao Ec — requisitos de destino em 2026-11-07

Os requisitos abaixo registram o destino solicitado; sua implementação permanece
pendente. REQ-12/AC-12 descrevem a integração intermediária com checks adicionais;
REQ-16/AC-16 exigem sua promoção ao padrão. REQ-17/AC-17 condicionam a futura
substituição do Trivy 0.61.1 de REQ-11/AC-11 à preservação das três categorias.
O código atual não muda por esta atualização documental. O
[plano de conclusão](toolbox-completion-plan.md) define dependências e limites.

- REQ-13 — Fronteira de escrita do consumidor: Restringir alterações e todo estado de filesystem da integração ao Ec; projeto Toolbox permanece somente leitura.
- AC-13 → REQ-13: Ponte local redireciona índices, volumes, logs, caches e locks; importação, preparo, run, erro e cancelamento não escrevem nem relabelam fora do Ec; sem fallback à CLI com volumes externos.

- REQ-14 — YARA-X local: Fornecer regras, configuração e integração operacional YARA-X locais ao Ec, sem mudar o catálogo compartilhado.
- AC-14 → REQ-14: Regras úteis com fonte/licença, positivos/negativos, imagem/versão verificável e relatório íntegro; regra vazia/inválida, include externo, timeout, inventário incompleto e execução parcial não aprovam; não confundir sentinelas de teste com cobertura real.

- REQ-15 — Imagens e bases reais: Adotar imagens e snapshots por identidade real, preservando versões e configurações anteriores recuperáveis.
- AC-15 → REQ-15: Imagem, versão/arquitetura e integridade/procedência são observadas; DB principal/Java/checks conforme Trivy, crates.io/npm OSV e DB Grype têm identidade e validade; preparo interrompido não publica ambiente completo; corrupção/expiração/divergência são rejeitadas.

- REQ-16 — Suíte padrão completa: Incluir Bandit, Grype, Syft, YARA-X e testes das regras no padrão; promover Trivy completo sem duplicação.
- AC-16 → REQ-16: Plano e run padrão têm cada check uma vez, regras precedem scans e seleção explícita conserva dependências/governança; Syft mantém contagem de artefatos e exige análise Grype do mesmo SBOM, com base íntegra/recente e zero achados para aprovação.

- REQ-17 — Migração Trivy com paridade: Convergir Trivy nativo/Docker para um gate completo, preservando vuln, misconfig e secret antes de retirar a dependência nativa.
- AC-17 → REQ-17: Versão exata, checks e flags observados; positivos/negativos das três categorias, três locks inventariados, segredo redigido antes de gravar e rede bloqueada; diferenças entre versões investigadas, sem fallback automático nem dupla contagem.

- REQ-18 — Aceite observado da conclusão: Demonstrar os novos contratos em execução real autorizada e manter documentação/evidência atuais.
- AC-18 → REQ-18: Suíte completa sem filtros observada com todos os checks novos efetivamente executados e sem erros/bloqueios; respeitar política de commit e autorização específica de downloads; inspeção ou testes fixture não promovem eficácia de motores a APPROVED.


## Adequação STIC e limpeza autorizada — 08/11/2026

Esta decisão substitui a localização de configuração, ponte/armazenamento e preparo
locais anteriores (inclusive REQ-13/REQ-15). A política estrita, cobertura de segurança
e exigência de execução permanecem; o pedido de exclusão de downloads prevalece
sobre a recomendação do contrato de manter instalações para rollback.

- REQ-19: adotar o wrapper comum STIC, config canônica em scripts/config e somente CLI pública; preservar critérios sem fallback nativo e limpar downloads de ferramentas no Ec com inventário.
- AC-19 → REQ-19: wrapper idêntico ao template, descoberta TOOLBOX_BIN/PATH, configurações/callers atuais, volumes exclusivos da plataforma, downloads locais removidos por recibo, evidências históricas preservadas e bloqueios explícitos; homologação exige testes autorizados e capacidades públicas efetivas.

Retomada: H03/H05/H06/H07 da plataforma, integração dos gates Ec ao staging/export,
paridade Trivy, adapter CodeQL e preparação externa autorizada. Instalação do
wrapper não é homologação. Regras/suites/políticas e checks obrigatórios permanecem;
versões antigas de backends não constituem autorização para restaurá-los.

## Integração nativa autorizada — 08/11/2026

O pedido atual substitui REQ-06 (proibição de Python operacional), REQ-07/11/12/15/19
(seleção/instalação independente e APIs antigas) e REQ-13 (estado central deslocado).
As partes correspondentes de AC-06/07/11/12/13/15/19 permanecem históricas e são
reavaliadas conforme o contrato nativo abaixo, sem renovar evidências antigas.
REQ-16/AC-16 eram proposta de promoção dos complementares; o pedido vigente
preserva sua classificação opcional. REQ-17/18 mantêm paridade e aceite real.
A autorização atual permite testes e automate.sh sem commit; downloads continuam
exigindo autorização específica. Nenhum commit/publicação/administração central.

- REQ-20: wrapper mínimo TOOLBOX_BIN/PATH, override inválido rejeitado, exec nativo e retirada das configurações obsoletas após migração dos chamadores.
- AC-20 → REQ-20: argv com espaços, token vazio e caracteres especiais chega intacto, código original preservado e nenhuma chamada consumer/config/lock necessária.
- REQ-21: alvos explícitos, snapshot Ec com hashes/modos, fontes ro, relatórios/streams/diagnóstico separados e severidades/ignores sem afrouxamento.
- AC-21 → REQ-21: testes rejeitam seleção vazia, lock obrigatório ausente, symlink, inventário parcial, supressão, JSON malformado, exit inconsistente e fonte alterada.
- REQ-22: Ruff, ShellCheck, Gitleaks, Opengrep, OSV e Trivy completo obrigatórios; Bandit, Grype, Syft e YARA-X opcionais; eficácia real de regras com positivos/negativos; SBOM isolado é medição; security:syft acopla Grype para aprovação.
- AC-22 → REQ-22: comandos nativos preservam regras/config, três locks e categorias Trivy; fixtures de contrato não são declaradas como homologação dos motores.
- REQ-23: Playwright central ESM, build estático próprio sem runner duplicado/ABI host, API simulada identificada e jornada Firefox preservada.
- AC-23 → REQ-23: dois testes completos sem skipped/flaky e browser Chromium real; Firefox executado separadamente quando serviços disponíveis.
- REQ-24: CodeQL create/resolve/analyze Server Rust, Agent Rust e frontend JS/TS; bases/resultados Ec, packs/extratores/runtimes centrais e Rust/dependências explícitos.
- AC-24 → REQ-24: os três bancos finalizados, inventário completo e SARIF válido com consultas, sem supressões/avisos/extração parcial; nenhum alvo ausente pode aprovar.
- REQ-25: preservar outcomes/códigos, integridade, segredos e encerramento/limpeza; nenhuma capacidade privilegiada implícita ou atualização de base em scan.
- AC-25 → REQ-25: testes distinguem exit nativo 124/125/130 de infraestrutura, timeout/cancelamento; segredo sintético ausente de metadados, limpeza falha reprova e supervisor deixa janela para remoção.
- REQ-26: validação real sem filtros, versões/imagens/bases observadas, plugins Cargo/RustSec separados, recuperação e bloqueios externos concretos.
- AC-26 → REQ-26: automate.sh run completo observado sem filtros e todos os requisitos obrigatórios comprovados; ferramentas inativas, base vencida ou execução parcial impedem conclusão integral.

## Adequação ao guia central — 09/11/2026

A documentação consolidada em consumer-integration/application mantém exec como
interface principal. Esta atualização substitui somente o uso de recibo externo
OSV/prepared_at e as descrições de checks Trivy embutidos; mantém critérios,
classificação opcional, três locks, controles e transporte nativo. A autorização
de execução da migração anterior não se estende a esta tarefa.

- REQ-27: alinhar ambiente selecionado, proveniência pública de bases e Checks Bundle Trivy ao guia central, sem imports internos, pins, downloads ou administração da plataforma.
- AC-27 → REQ-27: TOOLBOX_STATE e seleções Docker alcançam a CLI sem transporte integral do ambiente; OSV/Trivy exigem snapshot/fontes/hashes/menor timestamp upstream <=24h no diagnóstico, sem prepared_at/recibo externo; Trivy exige bundle externo compatível, mount ro e rede none, sem troca entre etapas. Testes negativos e suíte autorizada devem demonstrar rejeição das inconsistências; inspeção não equivale a aceite executado.

## Correção dos cinco bloqueios — 09/11/2026

- REQ-28: corrigir a descoberta Geiger do build sem remover entradas, editar caches, trocar o motor ou aceitar fontes Rust não examinadas; preservar offline/all-features, todos os membros, código nativo e interrupção na primeira falha. Atualizações dos locks seguem REQ-31, substituindo a exigência anterior de `--locked` por instrução de 11/11/2026.
- AC-28 → REQ-28: dados incorporados são demonstrados pelos consumidores; Rust gerado entra nos contadores do Geiger com proprietário único; entrada desconhecida, proprietário ambíguo e parsing inválido não aprovam. Os dois gates completos passam em execução autorizada, inclusive os membros antes não alcançados.
- REQ-29: cumprir REQ-10 por testes úteis e coleta atual das jornadas reais, preservando denominador, limiar e isolamento dos perfis; não alegar cobertura por inspeção.
- AC-29 → REQ-29: Server, Agent e frontend atingem >=80% de linhas individualmente na métrica canônica, com testes de comportamento/rejeição; falha de qualquer produtor impede aprovação e toda a suíte passa quando autorizada.

Este pedido não inclui commit nem execução de verificação. Os critérios novos e
os critérios anteriores afetados permanecem UNVERIFIED até evidência executada.

## Ambiente automático por execução — 11/11/2026

- REQ-30: `automate.sh run` configura automaticamente o ambiente necessário em qualquer escopo, usando recursos locais e preservando seleções explícitas. A configuração dura somente durante a execução, sem modificar perfis ou variáveis do shell chamador.
- AC-30 → REQ-30: descoberta determinística da Toolbox e padrões Cargo/Rustup/Geiger/RustSec/CodeQL chegam às tarefas através do ambiente selecionado; overrides vazios não recebem fallback; segredos alheios não são transportados; encerramento normal, erro ou sinal não deixam variáveis no chamador. `plan` não prepara ambiente e `source` é recusado sem efeitos. A descoberta consulta somente identidades locais (Git/Rustup), não faz downloads nem declara recursos ausentes como preparados.

## Resolução de dependências em desenvolvimento — 11/11/2026

- REQ-31: permitir atualizações dos locks da aplicação e do Geiger durante as verificações, sem `--locked` ou requisitos exatos nos manifests; preservar offline, proveniência e honestidade do aceite.
- AC-31 → REQ-31: comandos Rust e coletor LLVM não impõem `--locked`; `syn` do Geiger aceita versões compatíveis. Uma mudança em qualquer dos três locks conserva os bytes e registra caminho/hashes antes/depois em `updated_lockfiles`, exigindo nova suíte sobre a resolução final. Mudanças em outras entradas continuam reprovando estabilidade. Nenhum resultado nativo, política de downloads ou aprovação de cobertura é convertido em sucesso por essa permissão.


## Preparo automático solicitado — 11/11/2026

- REQ-32: configurar SELinux shared, RustSec local e toolchain ativo somente na execução; resolver dependências compatíveis a partir do cache atual e renovar DB/Java DB Trivy ausentes/vencidas antes do scan, pela CLI pública, com downloads autorizados. Este preparo complementa REQ-27 sem alterar seus checks de integridade.
- AC-32 → REQ-32: overrides explícitos prevalecem; nenhuma variável é gravada no chamador; Rust/CodeQL compartilham o toolchain observado. Trivy publica as duas bases em manutenção com raiz vazia, exige diagnóstico/cleanup/identidade, revalida datas upstream e executa scans offline. Falha de manutenção, base futura/adulterada ou expirada após o preparo nunca aprova.

## Consolidação da configuração — 11/11/2026

O pedido atual substitui a seleção do Geiger vendorizado de REQ-28: todos os
comandos Cargo usam os executáveis instalados e o toolchain ativo do sistema.
Falhas de análise continuam sendo falhas, sem omitir arquivos ou aceitar saída
parcial. Versões observadas são evidência, não requisitos fixados pelo runner.

- REQ-33: uma configuração canônica declara tarefas, executores, componentes, políticas, recursos, ambiente, limites e serviços. Cargo e plugins executam no host; scanners executam exclusivamente pela Toolbox/Docker. Configurações nativas têm uma referência responsável e não duplicam decisões da automação.
- AC-33 → REQ-33: plano, execução, inventário, cobertura, CodeQL e harnesses consomem a mesma fonte; configuração inválida é rejeitada antes de executar; seleção não perde dependências. Não há build privado ou pin de plugins Cargo, fallback de scanner para host ou caminho de outro projeto embutido no código.
- REQ-34: preparo explícito por recurso, reutilizado na execução; dependências Cargo compatíveis locais podem atualizar locks; bases são inspecionadas/renovadas pela ferramenta responsável antes de scans; serviços de teste têm ciclo de vida e limpeza definidos.
- AC-34 → REQ-34: preparo falho bloqueia somente consumidores dependentes; falta de ferramenta/componente/base identifica recurso e ação necessária. Locks alterados são registrados; downloads dependem da autorização vigente. PostgreSQL dedicado é validado antes de iniciar e volta ao estado anterior ao fim, inclusive em falhas normais; variáveis e temporários não escapam do run.
- REQ-35: preservar diagnósticos e achados suficientes para investigar cada tarefa após limpeza, e alinhar testes aos contratos atuais sem afrouxar políticas de segurança/cobertura.
- AC-35 → REQ-35: relatórios nativos redigidos quando necessário e resultados por alvo sobrevivem como logs privados; asserts de retenção de formulário e sessão verificam o estado pertinente; regressões cobrem seleção, backend, preparo, falha, isolamento e limpeza. Evidência executada e inspeção permanecem distintas.

Esclarecimento do mesmo pedido: Chromium e Firefox devem usar somente os browsers
da Toolbox/Docker. Isso substitui a exigência de Firefox/Marionette no host de
REQ-23, preservando os oráculos reais e a distinção da API simulada. A variante
Firefox será preparada pelo usuário; indisponibilidade bloqueia explicitamente,
sem fallback para o host, sem instalação ou troca global de versão pelo Ec.


## Correção autorizada do run-IR9jPgS9 — 12/11/2026

O usuário autorizou implementação, testes e reexecuções completas com complementares,
sem pedido de commit. A triagem abaixo substitui somente a rejeição indiscriminada
Bandit de REQ-21; os scanners mantêm inventário integral, níveis e relatórios brutos.

- REQ-36: CodeQL só aprova extração saudável; preservar diagnóstico completo do runtime Rust.
- AC-36 → REQ-36: severidade SARIF efetiva, inclusive omitida e herdada, é validada; extração parcial, métrica insuficiente, mera presença em artifacts e falha interna de build script/proc-macro mesmo com SARIF saudável reprovam; Server, Agent e frontend passam realmente.
- REQ-37: Geiger usa relações resolvidas pelo Cargo e distingue dependências inativas de perdas de associação, sem dispensar fontes ou unsafe.
- AC-37 → REQ-37: regressões cobrem dependências opcionais, renomeadas e versões simultâneas; dependência ativa ausente reprova; inventários dos dois workspaces passam sem falhas de associação.
- REQ-38: triagem Bandit explícita na configuração responsável, vinculada ao hash da fonte, regra, linha e justificativa; nenhum skip nativo ou exclusão de testes.
- AC-38 → REQ-38: scan integral preservado; achado novo, hash divergente e ocorrência sem justificativa reprovam; relatório distingue contagem bruta, revisada e pendente; a suíte com --scope complete passa nos inputs finais estáveis.


## Ampliação da cobertura solicitada — 12/11/2026

- REQ-39: ampliar regras, severidades, linguagens e fontes suportadas pelas versões instaladas, após consultar documentação oficial; configurações nativas seguem referenciadas somente pelo JSON canônico.
- AC-39 → REQ-39: Ruff usa seleção explícita de regras estáveis compatíveis com scripts stdlib/CLI, unittest e namespaces Python, conforme adequação autorizada em 13/11/2026; sem exigir migração de framework, documentação/tipagem universal ou copyright. Segurança Python mantém scan Bandit integral e revisão por conteúdo. A política Ruff canônica registra os grupos e exceções pontuais de bootstrap de imports; não há exclusão de fontes ou supressão por comentário. ShellCheck mantém opcionais/dataflow, Clippy os grupos cumulativos, CodeQL security-and-quality e catálogos Opengrep/YARA a origem/licença/revisão explícitas. Regras não suportadas, inventário omitido ou dependência ausente não aprovam; não habilitar grupos que se contradizem.
- REQ-40: Gitleaks inclui árvore atual, arquivos de ambiente e histórico local completo, com redação e identidade Git observada; decodificação/arquivos compactados têm limites explícitos.
- AC-40 → REQ-40: supressões pessoais não reduzem cobertura, histórico ausente/raso/mutável bloqueia e valores de segredos não são exportados.
- REQ-41: por instrução de 13/11/2026, os scanners verificam somente fontes, dependências e configurações do Ec. A Toolbox fornece os executores e não é alvo de auditoria do Ec; retirar seletores, exportação e scans das imagens das ferramentas.
- AC-41 → REQ-41: Trivy executa filesystem e Syft/Grype analisam o inventário Ec com os três locks; nenhum comando de scan de imagem ou exportação Docker é emitido. O campo obsoleto inputs.images é rejeitado. Achados Ec, inventário incompleto, falha operacional e relatório inválido continuam reprovando.
- REQ-42: remover configurações substituídas e registrar limites/pré-requisitos sem renovar aceite dos runs anteriores.
- AC-42 → REQ-42: uma fonte por responsabilidade, referências oficiais por versão, critérios afetados UNVERIFIED até execução autorizada; achados novos não são dispensados para obter verde.

O pedido vigente autoriza pesquisa e alteração de configurações, inclusive na
plataforma responsável quando necessário. A autorização específica para baixar
catálogos foi recebida. O operador indicou a instalação shared-toolbox em dev_audit:
o escopo de imagens usa suas seleções ativas e a variante Firefox, sem inferir
imagens Ec de produção. A execução anterior de apenas um comando não é
autorização para novos scans nesta ampliação.

Nesta retomada, o usuário autorizou explicitamente a qualificação nativa, os
testes Ec/Toolbox e a suíte completa com complementares, incluindo bases de
vulnerabilidades quando necessário. Correções e repetições desta ampliação
estão abrangidas, sem commit/publicação.
