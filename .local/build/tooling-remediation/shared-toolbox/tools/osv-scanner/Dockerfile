# Base identity is required; no mutable default is permitted.
ARG BASE
FROM ${BASE} AS unpack
COPY payload /payload/
RUN mkdir /unpacked && if [ -f /payload/tool.tar ]; then tar -xf /payload/tool.tar -C /unpacked; else cp /payload/tool /unpacked/osv-scanner; fi \
    && mkdir /output && find /unpacked -type f -name osv-scanner -exec cp {} /output/osv-scanner \; \
    && test -s /output/osv-scanner && chmod 755 /output/osv-scanner
FROM ${BASE}
ARG SNAPSHOT
RUN test -n "$SNAPSHOT" \
    && rm -f /etc/apt/sources.list.d/debian.sources \
    && printf 'deb [check-valid-until=no] http://snapshot.debian.org/archive/debian/%s/ trixie main\n' "$SNAPSHOT" > /etc/apt/sources.list \
    && apt-get update && apt-get install -y --no-install-recommends ca-certificates libgcc-s1 libc6 \
    && rm -rf /var/lib/apt/lists/*
COPY --from=unpack /output/osv-scanner /usr/local/bin/osv-scanner
RUN mkdir /toolbox && dpkg-query -W > /toolbox/packages.tsv
WORKDIR /src
ENTRYPOINT ["osv-scanner"]
