# Instalações pendentes — consulta temporária

Gitleaks detecta segredos nas fontes; dependência da superfície `security` em
`scripts/automate.py`. Obrigatório quando essa superfície for aplicável ou no padrão
conservador; ausente no PATH observado. Versão revisada: **8.30.1**, licença MIT.
Nenhuma instalação foi feita. Pré-requisitos permanentes também estão em
`docs/development/task-verification.md`; nenhum gate depende deste documento.

Instalação Linux x86_64 em PATH global (ação futura do operador, requer rede e sudo
somente no install): baixe o asset e checksums da [release oficial](https://github.com/gitleaks/gitleaks/releases/tag/v8.30.1),
confira SHA-256 antes de extrair e instale o executável:

```bash
curl -fLO https://github.com/gitleaks/gitleaks/releases/download/v8.30.1/gitleaks_8.30.1_linux_x64.tar.gz
curl -fLO https://github.com/gitleaks/gitleaks/releases/download/v8.30.1/gitleaks_8.30.1_checksums.txt
sha256sum --ignore-missing -c gitleaks_8.30.1_checksums.txt
tar -xzf gitleaks_8.30.1_linux_x64.tar.gz gitleaks
sudo install -m 0755 gitleaks /usr/local/bin/gitleaks
gitleaks version
```

Use pasta vazia de download e confira sucesso do checksum antes dos próximos passos.
Asset observado: SHA-256 `551f6fc83ea457d62a0d98237cbad105af8d557003051f41f3e7ca7b3f2470eb`.
Outra arquitetura exige o asset correspondente, nunca este binário.

Alternativa Docker oficial, linux/amd64, referência reproduzível do
[pacote oficial](https://github.com/gitleaks/gitleaks/pkgs/container/gitleaks/):

```bash
docker run --rm --pull=never ghcr.io/gitleaks/gitleaks:v8.30.1@sha256:b109bc5f8f76a38196a3e413704fc5b9e3c32360bce4e4b603bd6f45b3721dbb version
```

A imagem precisa ser obtida explicitamente antes. Docker requer daemon/privilégios;
scan com bind mount deve montar somente fontes necessárias e guardar relatório
separado. O runner atual consome CLI no PATH: Docker isoladamente não satisfaz seu
preflight, e não há fallback automático. Sem credenciais ou rede para scan nativo;
regras versionadas em scripts/automation/rules/gitleaks.toml. Não habilitar upload.
Após preparar a CLI, executar `python3 scripts/automate.py run --surface security`.

Rust/Node/npm/Docker/Firefox já estavam disponíveis; dependências de aplicação
continuam nos manifests/lockfiles. Opengrep e OSV foram reintegrados; suas instalações e bases também são necessárias
para o perfil security, conforme abaixo. Instruções verificadas nas fontes oficiais acima e no [manual](https://github.com/gitleaks/gitleaks).

## Opengrep — SAST

Dependência de `scripts/security/commands/opengrep.sh`, teste das regras e check
`sast` do runner. Estado observado: **ausente**. Versão revisada **1.33.1**,
licença LGPL-2.1, regras locais Ec sem registry, conta ou token. Necessário no perfil
security/padrão; rede somente para instalação. Binário Linux x86_64/glibc, conforme
[assets oficiais](https://github.com/opengrep/opengrep/releases/expanded_assets/v1.33.1):

```bash
curl -fL -o opengrep_manylinux_x86 https://github.com/opengrep/opengrep/releases/download/v1.33.1/opengrep_manylinux_x86
printf '%s  %s\n' 46e8133cc490f9f50518061590ce2f6be5a08afaeda034471c0ceda4b3899808 opengrep_manylinux_x86 | sha256sum -c -
sudo install -m 0755 opengrep_manylinux_x86 /usr/local/bin/opengrep
opengrep --disable-version-check --version
```

Conferir checksum com sucesso antes do install; outra arquitetura/libc exige asset
correspondente. Não usar somente opengrep-core: o adapter consome a CLI completa.
Configuração em scripts/security/rules/opengrep. Ver [projeto oficial](https://github.com/opengrep/opengrep/tree/v1.33.1).

## OSV-Scanner — dependências

Dependência de `scripts/security/commands/osv-scanner.sh` e check `dependencies`.
Estado observado: **ausente**, bases crates.io/npm **ausentes**. Versão **2.7.0**,
licença Apache-2.0 do scanner. Necessário no perfil security/padrão. Binário Linux
amd64 dos [assets oficiais](https://github.com/google/osv-scanner/releases/expanded_assets/v2.7.0):

```bash
curl -fL -o osv-scanner_linux_amd64 https://github.com/google/osv-scanner/releases/download/v2.7.0/osv-scanner_linux_amd64
printf '%s  %s\n' 160c6253e2922e9e0a6a9771c5c4c40a4b3c6090b34a3cdd5e80c96cc2e4218b osv-scanner_linux_amd64 | sha256sum -c -
sudo install -m 0755 osv-scanner_linux_amd64 /usr/local/bin/osv-scanner
osv-scanner --version
```

Conferir checksum antes de instalar; outra arquitetura exige asset correspondente.
Não requer Go para executar o binário distribuído. Verificação adicional de
procedência conforme [instalação oficial](https://github.com/google/osv-scanner/blob/v2.7.0/docs/installation.md).
Preparar separadamente os dois ZIPs oficiais e registrar seu manifesto conforme
[scripts/security/README.md](scripts/security/README.md). O scan é offline, sem
credenciais, chamadas à API, call analysis Rust ou execução de builds. A base não
faz parte do binário e deve ser atualizada explicitamente antes do aceite.

Os dois adapters consomem executáveis no PATH; Docker não satisfaz automaticamente
esse contrato. As opções nativas acima são as documentadas nesta reintegração;
nenhuma imagem Docker foi presumida ou baixada. Sudo é necessário somente no install
global apresentado; downloads usam HTTPS. Nada foi instalado nesta tarefa.

Depois da preparação: `python3 scripts/automate.py run --surface security`.
A ausência de cada CLI/base mantém o check bloqueado. Este arquivo pode ser apagado:
pré-requisitos e procedimento permanente estão no guia de segurança, sem dependência
operacional de erase.md.
