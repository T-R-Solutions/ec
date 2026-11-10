# Dependências Geiger no sistema — inspeção de 09/11/2026

Busca somente de leitura desde `/`, incluindo mounts acessíveis. Foram percorridos **477.007 diretórios**, **3.235.324 arquivos** e **9.870 manifestos Cargo.toml**. Não houve download, Cargo, build, teste ou script de verificação.

O executável `/home/rodrigo/.cargo/bin/cargo-geiger` está registrado como **0.14.0**, perfil release, compilado com Rust 1.99.1 para x86_64-unknown-linux-gnu. A identificação foi lida em `.crates2.json`, sem executar o scanner.

O lock da cópia preparada contém **453 pacotes de registro**. Foram encontrados **356 arquivos de pacote com versão e SHA-256 correspondentes ao lock** (contagem por pacote, sem duplicar cópias). Há mais uma fonte candidata, backtrace 0.3.76 no rust-src, cuja identidade não foi conferida com o pacote publicado. Para **96 versões** não foi encontrado nem manifesto correspondente nem arquivo `.crate` na parte acessível do sistema.

Isso não demonstra 96 downloads necessários no Linux. A lista inclui dependências de outras plataformas e recursos opcionais. A necessidade efetiva ainda depende do alvo, das features e da resolução; o build não foi executado.

| Dependência do lock | Versões encontradas nos registros locais consultados |
| --- | --- |
| `cc 1.8.0` | 1.10.0, 1.11.0, 1.11.1 |
| `annotate-snippets 0.12.16` | 0.11.5, 0.12.17 |
| `arc-swap 1.9.2` | 1.9.3 |
| `assert_cmd 2.2.2` | Não encontrada |
| `rstest 0.18.2` | Não encontrada |
| `better-panic 0.3.0` | Não encontrada |

A existência de versões diferentes não satisfaz automaticamente o lock atual. Essa exigência vem do lock copiado para recompilar a variante corrigida; não significa que o Geiger instalado precise desses downloads para funcionar.

Dois achados corrigem a inspeção limitada ao Cargo home global: `wasi 0.11.1+wasi-snapshot-preview1` existe no cache Ec e em runs CodeQL, com SHA-256 correspondente; `backtrace 0.3.76` existe entre os fontes da toolchain, ainda sem prova de equivalência ao `.crate` publicado.

Foram registradas **116 ocorrências de acesso/leitura negados**, incluindo `/root`, `/var/lib/docker`, `/opt/containerd` e `/.snapshots`. Esses diretórios não permitem leitura pelo usuário atual no ambiente; as permissões não foram contornadas. `/proc`, `/sys`, `/dev` e `/run/credentials` foram excluídos. Symlinks não foram seguidos; arquivos compactados genéricos e imagens de containers não foram abertos. Portanto, ausência significa **não encontrado no sistema acessível**, não prova de inexistência absoluta no SO.

[Resumo e lista das 96 versões](system-cache-summary.json) · [Inventário de caminhos e hashes](system-cache-inspection.json) · [Versões alternativas nos registros](alternative-cached-versions.json)

## Versões não encontradas

- `addr2line-0.25.1`
- `android_system_properties-0.1.6`
- `annotate-snippets-0.12.16`
- `anstyle-wincon-3.0.11`
- `arc-swap-1.9.2`
- `assert_cmd-2.2.2`
- `better-panic-0.3.0`
- `block2-0.6.2`
- `cargo-credential-macos-keychain-0.4.25`
- `cargo-credential-wincred-0.4.25`
- `cc-1.8.0`
- `console-0.16.6`
- `core-foundation-0.10.1`
- `crc32fast-1.5.2`
- `defmt-0.3.100`
- `defmt-1.1.1`
- `defmt-macros-1.1.1`
- `defmt-parser-1.0.0`
- `deranged-0.5.8`
- `difflib-0.4.0`
- `encode_unicode-1.0.0`
- `encoding_rs-0.8.42`
- `equivalent-1.0.2`
- `erased-serde-0.4.10`
- `find-msvc-tools-0.1.14`
- `flate2-1.1.10`
- `foreign-types-0.3.2`
- `foreign-types-shared-0.1.1`
- `futures-timer-3.0.4`
- `gimli-0.32.3`
- `globset-0.4.20`
- `icu_collections-2.3.0`
- `icu_locale_core-2.3.0`
- `icu_normalizer-2.3.0`
- `icu_normalizer_data-2.3.0`
- `icu_properties-2.3.0`
- `icu_properties_data-2.3.0`
- `icu_provider-2.3.1`
- `ignore-0.4.33`
- `insta-1.49.0`
- `jiff-0.2.38`
- `jiff-core-0.1.1`
- `jiff-static-0.2.38`
- `jiff-tzdb-0.1.9`
- `jiff-tzdb-platform-0.1.3`
- `libc-0.2.190`
- `libssh2-sys-0.3.3`
- `libz-sys-1.1.29`
- `memmap2-0.9.11`
- `miniz_oxide-0.8.9`
- `miow-0.6.1`
- `normpath-1.5.2`
- `objc2-cloud-kit-0.3.2`
- `objc2-core-data-0.3.2`
- `objc2-core-image-0.3.2`
- `objc2-core-location-0.3.2`
- `objc2-core-text-0.3.2`
- `objc2-ui-kit-0.3.2`
- `objc2-user-notifications-0.3.2`
- `object-0.37.3`
- `once_cell_polyfill-1.70.2`
- `openssl-0.10.81`
- `openssl-macros-0.1.1`
- `openssl-src-300.6.1+3.6.3`
- `openssl-sys-0.9.117`
- `orion-0.17.15`
- `portable-atomic-util-0.2.8`
- `predicates-3.1.4`
- `predicates-core-1.0.10`
- `predicates-tree-1.0.13`
- `relative-path-1.9.3`
- `rsqlite-vfs-0.1.1`
- `rstest-0.18.2`
- `rstest_macros-0.18.2`
- `rustix-1.1.5`
- `schannel-0.1.29`
- `security-framework-3.7.0`
- `security-framework-sys-2.17.0`
- `serde_spanned-1.1.2`
- `sha2-0.11.0`
- `similar-2.7.0`
- `smallvec-1.16.3`
- `sqlite-wasm-rs-0.5.5`
- `syn-3.0.8`
- `termtree-0.5.1`
- `tinystr-0.8.4`
- `toml_writer-1.1.3+spec-1.1.0`
- `unicode-normalization-0.1.25`
- `unicode-width-0.2.2`
- `utf8_iter-1.0.4`
- `valuable-0.1.1`
- `yoke-0.8.3`
- `zerocopy-0.8.62`
- `zerocopy-derive-0.8.62`
- `zerovec-0.11.8`
- `zerovec-derive-0.11.6`
