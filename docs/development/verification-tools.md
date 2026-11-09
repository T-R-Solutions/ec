# Preparar e operar as verificações

O [contrato vigente](../../scripts/README.md) usa `toolbox exec`, sem configuração
de integração, locks ou versões de scanners do consumidor. A Toolbox controla
versões, imagens, browsers, bases e caches. Ec controla comandos/configs/regras,
dependências e serviços da aplicação, alvos, relatórios e critérios.

## Plataforma

Disponibilizar Python >=3.11, Docker local Linux/amd64 acessível ao processo
chamador e instalação central no PATH ou TOOLBOX_BIN. Para selecionar uma
instalação, exporte TOOLBOX_ROOT=/caminho/shared-toolbox e
TOOLBOX_BIN="$TOOLBOX_ROOT/bin/toolbox"; TOOLBOX_ROOT não é lido pelo wrapper.
Conferir doctor, central list e central status no próprio ambiente da automação.
Lista vazia bloqueia; não usar imagens
históricas diretamente nem registrar/ativar por inferência. O operador precisa
qualificar e ativar as 12 ferramentas, com Chromium para Playwright e bundle
completo CodeQL (packs Rust/JS, extratores, Node e runtimes).

Downloads exigem autorização específica com componente, origem, versão,
verificação e destino. A execução deve reutilizar o preparo existente e não
instalar ferramentas implicitamente. Os procedimentos principais estão em
docs/consumer-integration.md e
docs/application.md da instalação; docs/native-validation.md separa comprovação
e limites. Os antigos native-execution/operations são encaminhamentos.

Na instalação externa observada em 09/11/2026, o executor OSV passou a chamar
`refresh_osv_before_run`: após 12 horas desde a aquisição, baixa os archives
oficiais de crates.io/npm e publica um novo snapshot antes do scanner. A opção
de rede do container não impede esse download no processo host. Isso diverge
do preparo explícito ainda descrito nos guias centrais consultados. Antes de
nova execução, é necessário obter autorização específica para esse efeito ou
dispor de uma interface central que permita desativá-lo. Os gates Ec atuais
não impedem essa atualização. A sétima suíte já acionou esse comportamento;
o [registro da execução](../validation/commit-verification-20261109/report.md)
documenta o download sem autorização prévia, o snapshot e os recibos observados.

OSV/Trivy/Grype precisam de snapshots íntegros e atuais. O executor identifica e
verifica os dados centrais; o limite de 24h é política Ec, não uma opção de exec.
Grype valida a data nativa descriptor.db.built. OSV/Trivy fornecem
`database_metadata` no diagnóstico: schema 1, snapshot e duas fontes obrigatórias
com URLs, hashes, semântica e timestamps upstream. Ec confere a menor data entre
as fontes e rejeita futuro, vencimento ou inconsistência. OSV usa criação da
geração upstream de crates.io/npm; Trivy usa UpdatedAt de DB/Java DB e também
NextUpdate nativo. prepared_at não mede idade dos dados; não há arquivo externo
EC_OSV_SCANNER_DATABASE_RECORD exigido ou fallback para recibos antigos.

Trivy requer também Checks Bundle **externo**, separado de DB/Java DB, preparado
e selecionado centralmente para a imagem ativa. O operador usa o procedimento
`docs/application.md#trivy-checks` e central prepare-checks --tool trivy quando
a manutenção/download estiverem autorizados. O Ec não executa esse preparo.
Cada scan confere checks/checks_runtime do diagnóstico, identidade, origem,
mount bind ro em /cache/policy e rede none; troca entre etapas invalida o gate.
Não aceitar fallback embutido nem flexibilizar idade para obter aprovação.

## Componentes do Ec

Rust 1.99.1, rustfmt, Clippy, testes, Cargo/npm/build/cobertura continuam tarefas
da stack. Plugins cargo-audit, cargo-deny, cargo-machete, cargo-geiger e
cargo-llvm-cov não constam do catálogo central consultado. Os executáveis globais
já existentes são identificados pelo Ec, sem reinstalação. Centralizá-los exige
módulos externos com versões verificadas, CLI nativa, runtime Rust/LLVM compatível,
políticas offline e manutenção RustSec; não simular suporte central.
`EC_RUSTSEC_DB` aponta para base Git real preparada externamente, com commit
não futuro de até sete dias; --no-fetch e os limites existentes permanecem.
Ferramentas Cargo instaladas e base de advisories são recursos distintos. Antes
de propor qualquer download, localizar e conferir as bases locais existentes.
Uma cópia local íntegra pode ser reutilizada no cache Cargo do Ec sem rede;
configure EC_RUSTSEC_DB para Audit e mantenha o layout esperado por Deny.
Não baixar novamente uma base já disponível por falta de seleção no ambiente.
Os crates próprios declaram GPL-3.0-only por herança dos workspaces; a política
Deny permite essa expressão para os nomes dos onze pacotes Ec. As licenças de
terceiros mantêm sua política e avisos; [declaração vigente](licensing.md).

Frontend requer Node >=22.12, npm e dependências do package-lock preparadas
explicitamente; não baixar pacotes no gate. Playwright usa só build estático,
servidor Node stdlib e runner ESM absoluto central, sem node_modules do host.
Logo não transporta addons compilados em Node 26 para Node 22 da imagem.
O webServer executa no mesmo container; localhost está correto. API é simulada.
Firefox/driver Python e serviços reais continuam na jornada separada, sem
centralização implícita. Para serviços externos de novos testes, selecionar rede,
host e URL e manter credenciais do banco somente na aplicação.

CodeQL cria os bancos Server/Agent/frontend em pasta explícita por tarefa. Rust
usa build-mode none, que ainda executa build scripts/macros via rust-analyzer.
Forneça diretórios preparados Linux/amd64 em `EC_CODEQL_RUSTUP_HOME`,
`EC_CODEQL_CARGO_HOME` e `EC_CODEQL_CARGO_BIN`, com Rust 1.99.1/rust-src, rustup,
cargo, rustc, bibliotecas e dependências offline. Rustup/bin são montados ro;
somente registry/git do Cargo são copiados para cache privado gravável, sem
config/credentials do host. Targets ficam junto aos bancos em /ec-codeql.
A imagem CodeQL candidata não comprova linker/bibliotecas/build scripts do Ec;
falta ou incompatibilidade bloqueia cada alvo Rust, mas frontend é tentado.
SARIF precisa inventariar todos os arquivos e rejeita consultas ausentes, supressões,
extração parcial e notificações de aviso/erro. Não executar pack install.

## Validação e recuperação

Executar conforme [política](task-verification.md) e autorização da tarefa. A
autorização específica da migração de 08/11/2026 pertence ao registro histórico;
não autoriza execução em tarefas posteriores.
Resultados reais e limitações ficam no [registro da migração](../validation/toolbox-native-20261108/report.md).
Testes de contrato têm executor simulado e não homologam scanner/browser.

Antes de repetir, resolver ativação/bases/runtime externamente, conservar o run
anterior e iniciar outro. Nunca reutilizar relatório velho como aprovação.
O backup `.local/native-migration-20261108/before.zip` e o manifesto inputs-before
preservam os arquivos anteriores, incluindo as três configs removidas. Restaurar
somente arquivos da integração, após comparar SHA-256 atual com o manifesto
pós-migração e preservar edições posteriores. Não extrair tudo sobre o checkout
nem restaurar instalações/caches. A integração antiga bloqueava scanners e depende
de APIs antigas; recuperar seus bytes não a torna funcional.

## Consulta temporária retirada — 09/11/2026

O antigo erase.md da raiz descrevia scanners no PATH e automate.py, substituídos
pelo fluxo nativo acima. Seus bytes foram [arquivados como histórico](../validation/cleanup-20261109/retired/erase.md).
Não usar seus downloads/paths como preparo vigente. O [inventário atual](scripts-inventory.md)
e o [relatório de limpeza](../validation/cleanup-20261109/report.md) registram
consumidores e recuperação; nenhum preparo ou teste foi realizado nesta limpeza.

## Ambiente do operador, dados e mounts

O ambiente selecionado do Ec conserva TOOLBOX_STATE e DOCKER_HOST/DOCKER_CONTEXT/
DOCKER_CONFIG/DOCKER_TLS_VERIFY/DOCKER_CERT_PATH até a CLI pública, sem levar o
ambiente inteiro para scanners. Contextos, certificados e arquivos apontados
precisam existir no host com acesso autorizado. TOOLBOX_STATE altera índices e
locks; não desloca tools/NOME/volume. Preferir bind mounts no mesmo host Linux.

Em SELinux enforcing, o guia central descreve TOOLBOX_SELINUX_LABEL=shared para
árvores dedicadas compartilhadas. A variável não é habilitada automaticamente;
relabel exige revisão dos caminhos pelo operador. Não aplicar private sobre
árvores simultaneamente compartilhadas nem relaxar DAC/ACL para ocultar falhas.

Fontes Ec são /project ro. /out é rw quando --results está presente; /cache é rw;
/db e /data são ro na análise. Trivy monta /cache/db, /cache/java-db e o bundle
em /cache/policy. Bancos CodeQL não pertencem ao snapshot central de vulnerabilidades:
ficam no mount rw /ec-codeql por tarefa. Nenhum gate pede --maintenance.

O README central de 09/11 registra 12 ferramentas ativas. Essa observação da
plataforma não foi repetida no Ec e não qualifica CodeQL Rust, todos os modos
IaC ou os gates deste produto. [Relatório desta adequação](../validation/toolbox-alignment-20261109/report.md).
