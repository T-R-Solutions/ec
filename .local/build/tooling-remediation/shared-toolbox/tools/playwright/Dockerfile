ARG BASE
FROM ${BASE} AS node
ARG SNAPSHOT
RUN test -n "$SNAPSHOT" \
    && rm -f /etc/apt/sources.list.d/debian.sources \
    && printf 'deb [check-valid-until=no] http://snapshot.debian.org/archive/debian/%s/ trixie main\n' "$SNAPSHOT" > /etc/apt/sources.list \
    && apt-get update && apt-get install -y --no-install-recommends xz-utils \
    && rm -rf /var/lib/apt/lists/*
COPY payload/node.tar.xz /tmp/node.tar.xz
RUN mkdir /node && tar -xJf /tmp/node.tar.xz -C /node --strip-components=1
FROM ${BASE}
ARG SNAPSHOT
ARG BROWSERS=none
RUN test -n "$SNAPSHOT" \
    && rm -f /etc/apt/sources.list.d/debian.sources \
    && printf 'deb [check-valid-until=no] http://snapshot.debian.org/archive/debian/%s/ trixie main\n' "$SNAPSHOT" > /etc/apt/sources.list \
    && apt-get update && apt-get install -y --no-install-recommends ca-certificates libstdc++6 \
    && rm -rf /var/lib/apt/lists/*
# npm is used during preparation only, and removed from the final filesystem.
COPY --from=node /node/bin/node /usr/local/bin/node
COPY --from=node /node/lib/node_modules/npm /opt/npm
COPY payload/package.json payload/package-lock.json /opt/pw/
ENV PLAYWRIGHT_BROWSERS_PATH=/opt/browsers PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD=1
RUN mkdir -p /opt/browsers && cd /opt/pw && node /opt/npm/bin/npm-cli.js ci --ignore-scripts --no-audit --no-fund \
    && if [ "$BROWSERS" != none ]; then node node_modules/playwright/cli.js install --with-deps "$BROWSERS"; fi \
    && rm -rf /opt/npm /root/.npm /var/lib/apt/lists/* \
    && chmod -R a+rX /opt/pw /opt/browsers
COPY tools/playwright/entrypoint.mjs /opt/entrypoint.mjs
RUN mkdir /toolbox && dpkg-query -W > /toolbox/packages.tsv
WORKDIR /out/work
ENTRYPOINT ["node", "/opt/entrypoint.mjs"]
