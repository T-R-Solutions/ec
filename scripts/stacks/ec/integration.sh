#!/usr/bin/env bash
set -euo pipefail
CODE=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/../../.." && pwd -P)
source "$CODE/scripts/stacks/ec/common.sh"
project=$1; shift
need docker; need git; need jq
[[ $project != e2e || -x /usr/bin/firefox ]] || blocked 'Firefox ausente'
docker version --format '{{.Server.Version}}' >/dev/null || blocked 'daemon Docker indisponível'
data=$(docker inspect ec-publication-test) || blocked 'container ec-publication-test ausente'
jq -e '.[0] | .State.Running and any(.NetworkSettings.Ports["5432/tcp"][]; .HostIp=="127.0.0.1" and .HostPort=="55439")' <<< "$data" >/dev/null || blocked 'PostgreSQL deve estar ativo em 127.0.0.1:55439'
docker exec ec-publication-test pg_isready -U postgres >/dev/null || blocked 'PostgreSQL não pronto'
need ss
ports=(58139)
[[ $project != e2e ]] || ports+=(5173 58282)
for port in "${ports[@]}"; do
    [[ -z $(ss -H -ltn "sport = :$port") ]] || blocked "porta $port ocupada"
done
exec "$@"
