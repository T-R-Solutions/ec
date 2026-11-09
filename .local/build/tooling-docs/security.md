# Segurança das fontes Ec

O perfil security executa Gitleaks 8.24.3 pelo toolbox compartilhado, Opengrep nativo
1.33.1 (testes de regras + SAST) e OSV-Scanner nativo 2.7.0 (três lockfiles offline).
As configurações, versões, preparo, fontes oficiais e limites estão no
[guia de ferramentas](../../config/quality/README.md). O toolbox documentado em
`.local/usage.md` ainda não oferece adapters Opengrep/OSV/Trivy: essa ausência bloqueia
os respectivos checks, sem instalação nativa ou fallback online implícitos.

```bash
bash scripts/automate.sh run --surface security
bash scripts/automate.sh run --check secrets
bash scripts/automate.sh run --check sast
bash scripts/automate.sh run --check dependencies
bash scripts/prepare.sh tools gitleaks
bash scripts/prepare.sh rustsec
```

A seleção parcial mantém governança; SAST inclui testes das oito regras locais.
Gitleaks usa defaults, redação de segredos e snapshot sem arquivos de ignore; um
preflight rejeita marcadores inline que o adapter não consegue desabilitar por flag.
A análise de segredos não inclui histórico Git, diretórios pessoais, caches ou
relatórios históricos. Inputs ativos incluem aplicação, automação, ferramentas,
SDD, configuração e guias de desenvolvimento, além dos arquivos de configuração da raiz.

SAST exige inventário completo sem skips/supressões e testes positivos de cada regra.
As regras Vue são textuais, sem análise de taint completa do script do SFC.
OSV usa snapshots dos dois Cargo.lock e do package-lock.json com --all-packages;
sem rede, sem call analysis Rust e sem executar build scripts. TOML explícito sem
ignores. Qualquer achado bloqueia o gate, independentemente da severidade.

## Bases OSV

Preparar crates.io e npm conforme o [modo offline oficial por versão](https://github.com/google/osv-scanner/blob/v2.7.0/docs/offline-mode.md),
baixando inicialmente para temporários, verificando conteúdo/procedência e substituindo
os arquivos somente fora de scans ativos:

```text
.local/security/osv/osv-scanner/crates.io/all.zip
.local/security/osv/osv-scanner/npm/all.zip
.local/security/osv/manifest.json
```

`bash scripts/security/record-osv-db.sh` registra SHA-256 e data e preserva o manifesto
anterior. O scan exige hash íntegro antes/depois, data não futura e registro de até
24h. Não renovar somente o timestamp para aparentar atualização da base. O registro
não comprova autenticidade nem frescor upstream; informar data e procedência do download.
RustSec usa preparo separado em `.local/security/rustsec` ou `EC_RUSTSEC_DB` explícito.

## Evidências

O runner preserva logs, exit original, configurações, snapshots por hash e relatórios
nos runs privados. Toolbox registra também imagem/versão e caminho de seus volumes;
relatórios de Ruff/Gitleaks/ShellCheck/Playwright são copiados para o run Ec.
No scan não há instalação, update de bases ou regras buscadas em registry.
Relatório ausente/incompleto, erro ou exit inconsistente falha; ferramenta/adapter/base
indisponível bloqueia. Exceções não são aplicadas automaticamente pelo validador.

A segurança depende também dos testes negativos reais de autorização, execução,
publicação e recuperação. Um scan limpo não substitui essas jornadas nem autoriza
publicação, implantação ou aceite de conformidade.
