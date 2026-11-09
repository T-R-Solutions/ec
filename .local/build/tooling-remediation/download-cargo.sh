#!/usr/bin/env bash
set -euo pipefail
umask 077
mkdir -p .local/tools/bin .local/build/tooling-remediation/downloads
curl -fsSL https://github.com/EmbarkStudios/cargo-deny/releases/download/0.20.2/cargo-deny-0.20.2-x86_64-unknown-linux-musl.tar.gz -o .local/build/tooling-remediation/downloads/cargo-deny-0.20.2-x86_64-unknown-linux-musl.tar.gz
printf '%s  %s\n' 9f12ed4c49936e09b48bf862b595cde2fe64fcbd9d74dfacac6131ca824c8d5f .local/build/tooling-remediation/downloads/cargo-deny-0.20.2-x86_64-unknown-linux-musl.tar.gz | sha256sum -c -
tar -xzf .local/build/tooling-remediation/downloads/cargo-deny-0.20.2-x86_64-unknown-linux-musl.tar.gz -C .local/build/tooling-remediation/downloads
install -m 755 .local/build/tooling-remediation/downloads/cargo-deny-0.20.2-x86_64-unknown-linux-musl/cargo-deny .local/tools/bin/cargo-deny
curl -fsSL https://github.com/bnjbvr/cargo-machete/releases/download/v0.9.2/cargo-machete-v0.9.2-x86_64-unknown-linux-musl.tar.gz -o .local/build/tooling-remediation/downloads/cargo-machete-v0.9.2-x86_64-unknown-linux-musl.tar.gz
printf '%s  %s\n' 48200087f54c55aabcd4db4af1e25742b49846c02a1b1bfa134711945b35b2e9 .local/build/tooling-remediation/downloads/cargo-machete-v0.9.2-x86_64-unknown-linux-musl.tar.gz | sha256sum -c -
tar -xzf .local/build/tooling-remediation/downloads/cargo-machete-v0.9.2-x86_64-unknown-linux-musl.tar.gz -C .local/build/tooling-remediation/downloads
install -m 755 .local/build/tooling-remediation/downloads/cargo-machete-v0.9.2-x86_64-unknown-linux-musl/cargo-machete .local/tools/bin/cargo-machete
