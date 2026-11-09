# Base identity is required; no mutable default is permitted.
ARG BASE
FROM ${BASE} AS unpack
COPY payload /payload/
RUN mkdir /unpacked && if [ -f /payload/tool.tar ]; then tar -xf /payload/tool.tar -C /unpacked; else cp /payload/tool /unpacked/gitleaks; fi \
    && mkdir /output && find /unpacked -type f -name gitleaks -exec cp {} /output/gitleaks \; \
    && test -s /output/gitleaks && chmod 755 /output/gitleaks
FROM ${BASE}
ARG SNAPSHOT
RUN test -n "$SNAPSHOT" \
    && rm -f /etc/apt/sources.list.d/debian.sources \
    && printf 'deb [check-valid-until=no] http://snapshot.debian.org/archive/debian/%s/ trixie main\n' "$SNAPSHOT" > /etc/apt/sources.list \
    && apt-get update && apt-get install -y --no-install-recommends ca-certificates libgcc-s1 libc6 git \
    && rm -rf /var/lib/apt/lists/*
COPY --from=unpack /output/gitleaks /usr/local/bin/gitleaks
RUN mkdir /toolbox && dpkg-query -W > /toolbox/packages.tsv
WORKDIR /src
ENTRYPOINT ["gitleaks"]
