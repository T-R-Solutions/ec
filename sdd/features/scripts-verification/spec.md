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
- AC-12 → REQ-12: configurações e wrappers disponibilizam Trivy Docker, Grype, Bandit e Syft como checks adicionais, mantêm OSV offline e regras locais Opengrep, recusam histórico Gitleaks ausente e YARA-X indisponível; preparo de DB aceita somente Trivy/OSV/Grype, vincula snapshot real e scan não atualiza dados. Relatórios inválidos, inventário Trivy incompleto, exit inconsistente e SBOM vazio são rejeitados. A geração de SBOM fica identificada como medição. Execução real permanece necessária para aceite.

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
- AC-16 → REQ-16: Plano e run padrão têm cada check uma vez, regras precedem scans e seleção explícita conserva dependências/governança; Syft mantém medição e contagem de artefatos no resultado agregado, sem parecer de vulnerabilidades.

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
- REQ-22: Ruff, ShellCheck, Gitleaks, Opengrep, OSV e Trivy completo obrigatórios; Bandit, Grype, Syft e YARA-X opcionais; eficácia real de regras com positivos/negativos; SBOM apenas medição.
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
