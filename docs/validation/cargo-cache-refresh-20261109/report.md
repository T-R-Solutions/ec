# Limpeza e preparo do cache Cargo — 09/11/2026

Pedido vigente: limpar o cache Cargo e baixar as versões mais novas das crates identificadas como ausentes. A autorização inclui esses downloads. Não inclui build, testes, scripts de verificação ou alteração dos lockfiles de aplicação.

Resultado: **95/95 crates preparadas**, com cargo fetch concluído, fonte extraída e SHA-256 do arquivo .crate correspondente ao informado pelo registro. As 96 versões da lista anterior representam 95 crates distintas; defmt aparecia em duas versões.

## Limpeza realizada

Foram removidos apenas os dois caches globais indicados abaixo, após inspeção de ausência de processos Cargo/Rustc ativos. Não houve limpeza dos caches Ec/CodeQL, targets de builds, índices, credenciais, registros de instalação ou executáveis.

| Caminho | Arquivos removidos | Bytes de conteúdo |
| --- | --- | --- |
| `/home/rodrigo/.cargo/registry/cache` | 915 | 129672714 |
| `/home/rodrigo/.cargo/registry/src` | 48465 | 706558402 |

Total: 836231116 bytes de conteúdo removidos antes do novo preparo. O cache foi repovoado pelos downloads autorizados; ao terminar havia 200 arquivos .crate, incluindo dependências transitivas.

## Seleção e preparo

As versões estáveis mais recentes, não retiradas do registro, foram consultadas diretamente na API crates.io. versões exatas e checksums foram registrados antes da limpeza em versions.json. Nenhum lock de aplicação foi atualizado.

Cada crate teve um manifesto temporário independente em `.local/build/cargo-cache-refresh-20261109/requests/`, fixando a versão selecionada com `=versão` e default-features=false. Isso evita impor compatibilidade artificial entre 95 bibliotecas sem relação direta. Cargo fetch resolveu e baixou também suas dependências; outras versões podem coexistir quando exigidas por dependências transitivas.

O comando de preparo, com CARGO_HOME global e rede autorizada, foi:

```bash
rustup run 1.99.1 cargo fetch --manifest-path <manifesto temporário da crate>
```

Não foi usado cargo install: essas crates são bibliotecas, preparadas no cache para consumo por builds. Não houve compilação nem execução de build.rs, testes, automate.sh ou scripts de verificação. Logs individuais e manifests/locks temporários estão no diretório de preparo; execution.json registra os códigos de saída e arquivos resultantes.

## Versões solicitadas preparadas

| Crate | Versão estável selecionada |
| --- | --- |
| `addr2line` | `0.27.1` |
| `android_system_properties` | `0.1.6` |
| `annotate-snippets` | `0.12.17` |
| `anstyle-wincon` | `3.0.11` |
| `arc-swap` | `1.9.5` |
| `assert_cmd` | `2.2.2` |
| `better-panic` | `0.3.0` |
| `block2` | `0.6.2` |
| `cargo-credential-macos-keychain` | `0.4.25` |
| `cargo-credential-wincred` | `0.4.25` |
| `cc` | `1.11.1` |
| `console` | `0.16.6` |
| `core-foundation` | `0.10.1` |
| `crc32fast` | `1.5.4` |
| `defmt` | `1.2.0` |
| `defmt-macros` | `1.2.0` |
| `defmt-parser` | `1.1.0` |
| `deranged` | `0.5.9` |
| `difflib` | `0.4.0` |
| `encode_unicode` | `1.0.0` |
| `encoding_rs` | `0.8.44` |
| `equivalent` | `1.0.3` |
| `erased-serde` | `0.4.11` |
| `find-msvc-tools` | `0.1.15` |
| `flate2` | `1.1.11` |
| `foreign-types` | `0.5.0` |
| `foreign-types-shared` | `0.3.1` |
| `futures-timer` | `3.0.4` |
| `gimli` | `0.34.0` |
| `globset` | `0.4.21` |
| `icu_collections` | `2.4.0` |
| `icu_locale_core` | `2.4.0` |
| `icu_normalizer` | `2.4.0` |
| `icu_normalizer_data` | `2.4.0` |
| `icu_properties` | `2.4.0` |
| `icu_properties_data` | `2.4.0` |
| `icu_provider` | `2.4.0` |
| `ignore` | `0.4.34` |
| `insta` | `1.49.0` |
| `jiff` | `0.2.41` |
| `jiff-core` | `0.1.4` |
| `jiff-static` | `0.2.41` |
| `jiff-tzdb` | `0.1.9` |
| `jiff-tzdb-platform` | `0.1.3` |
| `libc` | `0.2.191` |
| `libssh2-sys` | `0.3.4` |
| `libz-sys` | `1.1.32` |
| `memmap2` | `0.9.12` |
| `miniz_oxide` | `0.9.1` |
| `miow` | `0.6.1` |
| `normpath` | `1.5.2` |
| `objc2-cloud-kit` | `0.3.2` |
| `objc2-core-data` | `0.3.2` |
| `objc2-core-image` | `0.3.2` |
| `objc2-core-location` | `0.3.2` |
| `objc2-core-text` | `0.3.2` |
| `objc2-ui-kit` | `0.3.2` |
| `objc2-user-notifications` | `0.3.2` |
| `object` | `0.40.0` |
| `once_cell_polyfill` | `1.70.2` |
| `openssl` | `0.10.82` |
| `openssl-macros` | `0.1.1` |
| `openssl-src` | `400.0.2+4.0.3` |
| `openssl-sys` | `0.9.118` |
| `orion` | `0.18.1` |
| `portable-atomic-util` | `0.2.8` |
| `predicates` | `3.1.4` |
| `predicates-core` | `1.0.10` |
| `predicates-tree` | `1.0.13` |
| `relative-path` | `2.0.3` |
| `rsqlite-vfs` | `0.2.0` |
| `rstest` | `0.28.0` |
| `rstest_macros` | `0.28.0` |
| `rustix` | `1.1.6` |
| `schannel` | `0.1.30` |
| `security-framework` | `3.7.0` |
| `security-framework-sys` | `2.17.0` |
| `serde_spanned` | `1.1.3` |
| `sha2` | `0.11.1` |
| `similar` | `3.2.1` |
| `smallvec` | `1.16.5` |
| `sqlite-wasm-rs` | `0.6.2` |
| `syn` | `3.0.10` |
| `termtree` | `1.0.0` |
| `tinystr` | `0.8.5` |
| `toml_writer` | `1.2.0+spec-1.1.0` |
| `unicode-normalization` | `0.1.26` |
| `unicode-width` | `0.2.3` |
| `utf8_iter` | `1.1.0` |
| `valuable` | `0.1.1` |
| `yoke` | `0.8.4` |
| `zerocopy` | `0.8.63` |
| `zerocopy-derive` | `0.8.63` |
| `zerovec` | `0.11.9` |
| `zerovec-derive` | `0.11.7` |

## Limites e relação com a correção anterior

O SHA-256 do cargo-geiger instalado antes/depois é igual: `e6e3d04bffcbf99f92abb13506d418fd4581704a94990fa4a0c7cfb2387a5d20`. Sua instalação não foi refeita.

O preparo de versões novas não satisfaz automaticamente os lockfiles antigos que exigem versões diferentes. A correção Geiger e os cinco gates continuam UNVERIFIED: não houve execução autorizada da suíte nesta tarefa. Compatibilidade de compilação, MSRV e todas as combinações de features dessas bibliotecas não foram testadas; os defaults foram desativados nos manifests temporários apenas para o preparo do cache.

[Versões e metadados do registro](versions.json) · [Recibo das operações e checksums](execution.json) · [Inspeção anterior do SO](../geiger-coverage-remediation-20261109/system-cache-report.md)

Nenhum commit, staging ou push foi realizado. Os registros anteriores permanecem históricos; caminhos para o cache global antigo podem agora não existir por causa da limpeza explicitamente solicitada.
