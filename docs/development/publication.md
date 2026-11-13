# Publicação persistente local

Implementado e demonstrado em 05/11/2026: PostgreSQL como autoridade única de configuração, HTTP v1, sessões reais e autorização por projeto. Parser/CLI anterior preservado. A fatia T004 adicionou [fila durável e tentativas](orchestration.md); Agent, scripts e frontend continuam pendentes. [Contrato](../../sdd/features/first-functional-journey/spec.md) e [resultados](../validation/publication-20261105/http-postgres.log).

Pré-requisitos: Rust 1.99.1, Git, PostgreSQL 18 (observado 18.4), Python 3.13 para harness e Docker para o banco descartável. Dependências diretas exatas nos manifests, transitivas/checksums em Cargo.lock. postgres 0.19.12, tiny_http 0.12.0, serde_json 1.0.145, sha2 0.10.9. Cache/target ficam no checkout.

```bash
export CARGO_HOME="${CARGO_HOME:-$HOME/.cargo}"
export CARGO_TARGET_DIR="$PWD/application/server/backend/target"
cargo +1.99.1 fetch --manifest-path application/server/backend/Cargo.toml --locked
cargo +1.99.1 build --manifest-path application/server/backend/Cargo.toml --locked --offline
```

Para banco PostgreSQL vazio de desenvolvimento já criado, defina EC_DATABASE_URL com conexão local autorizada. O exemplo abaixo é exclusivo de banco descartável em loopback sem credenciais; não é configuração de implantação. Para outro banco, use autenticação PostgreSQL apropriada e mantenha connection string fora de logs. O Server não seleciona IdP nem oferece login por senha.

```bash
export EC_DATABASE_URL='host=127.0.0.1 port=55439 user=postgres dbname=ec_publication'
application/server/backend/target/debug/ec-server-app migrate
application/server/backend/target/debug/ec-server-app provision alice ec admin /caminho/privado/ec/alice.session local
application/server/backend/target/debug/ec-server-app admit-repository ec /caminho/canonico/git SHA_COMPLETO_REAL
application/server/backend/target/debug/ec-server-app serve
```

Migration usa transação e lock advisory; reaplicação verifica versões 1 e 2 (fila T004 aditiva). Rollback do código não remove dados; para resetar teste crie outro banco descartável. `provision` aceita viewer/operator/admin e exige arquivo novo: gera 32 bytes aleatórios do SO, grava token hex uma vez em modo 0600 e hash no banco com expiração em 8h. Acesso ao comando/banco é privilégio administrativo local. `revoke alice` revoga todas as sessões da identidade. DELETE /api/v1/session revoga só a sessão usada. Não existe concessão de papel por header HTTP ou por YAML. Em caso de falha de provisionamento, use um novo arquivo ao tentar novamente; arquivo escrito antes de commit não prova sessão válida.

Rotas:

| Método/caminho | Permissão | Resultado |
| --- | --- | --- |
| GET /api/v1/projects/ec | viewer/operator/admin | published e effective separados |
| POST /api/v1/projects/ec/publications | operator/admin | recibo com requestId, baseRevision, revision, sha256 |
| GET /api/v1/projects/ec/revisions/1 | viewer/operator/admin | bytes YAML via string JSON, hash e projeção |
| GET /api/v1/projects/ec/receipts/pedido-1 | operator/admin | recibo da identidade autenticada |
| DELETE /api/v1/session | sessão válida | revogação |

Server escuta apenas 127.0.0.1:8080 (EC_PORT altera porta). Credencial bearer exige transporte protegido antes de exposição remota; esta fatia não implementa terminação TLS, cookies ou federação. Quatro workers, SQL com lock timeout 5s/statement timeout 10s; não foi demonstrada resistência a clientes lentos ou overload. Sem retenção automática de revisões/recibos. Banco e operador local são fronteira confiável; triggers append-only não impedem superusuário de alterar schema. Scripts podem conter texto arbitrário: não há detector genérico de segredos.

Exemplo de cliente que mantém token fora de argv/stdout, após adaptar fixture para projeto ec, caminho e commit admitidos:

```python
import json
from pathlib import Path
from urllib.request import Request, urlopen
token = Path('/caminho/privado/ec/alice.session').read_text()
headers = {'Authorization': 'Bearer ' + token, 'Content-Type': 'application/json'}
body = json.dumps({'requestId': 'pedido-1', 'baseRevision': 0,
                   'yaml': Path('/caminho/privado/ec/project.yaml').read_text()}).encode()
with urlopen(Request('http://127.0.0.1:8080/api/v1/projects/ec/publications',
                     data=body, headers=headers, method='POST')) as response:
    receipt = json.load(response)
with urlopen(Request('http://127.0.0.1:8080/api/v1/projects/ec/revisions/' + str(receipt['revision']),
                     headers=headers)) as response:
    revision = json.load(response)
assert revision['yaml'].encode() == Path('/caminho/privado/ec/project.yaml').read_bytes()
```

## Reproduzir a evidência

O harness cria banco com UUID, Git real e sessões em arquivos temporários, termina/reinicia processos Server, inspeciona SQL e remove banco/arquivos ao final. Não utiliza bancos existentes. Usa container exclusivo `ec-publication-test` na porta 55439. Preparação portátil com imagem oficial PostgreSQL 18.4 (alternativa à imagem PostGIS local usada no ensaio):

```bash
docker run -d --name ec-publication-test -e POSTGRES_HOST_AUTH_METHOD=trust -p 127.0.0.1:55439:5432 postgres:18.4
docker exec ec-publication-test pg_isready -U postgres
cargo +1.99.1 build --manifest-path application/server/backend/Cargo.toml --locked --offline --features test-failpoints
python3 application/integration/tests/integration/publication_http.py
cargo +1.99.1 test --manifest-path application/server/backend/Cargo.toml --locked --offline
cargo +1.99.1 clippy --manifest-path application/server/backend/Cargo.toml --locked --offline --all-targets --all-features -- -D warnings
```

Pontos de falha existem somente com feature test-failpoints: X-Ec-Test-Failure before-commit retorna 500 após inserts sem commit; crash-before-commit encerra processo com 86; after-commit fecha writer HTTP sem resposta após commit. Build operacional deve omitir a feature. O teste não prova recuperação de disco nem failover PostgreSQL.

No ambiente observado foi usada imagem local itapiraco/postgis:18-3.6-alpine: primeiro bootstrap parou por variável de script opcional ausente; reinício do container já inicializado iniciou PostgreSQL 18.4. Nenhum recurso PostGIS é usado. A imagem oficial acima é instrução alternativa, não ensaio executado. Identidade/digest observados constam no relatório.

Fontes primárias consultadas: [locks PostgreSQL 18](https://www.postgresql.org/docs/18/explicit-locking.html), [sessões OWASP](https://cheatsheetseries.owasp.org/cheatsheets/Session_Management_Cheat_Sheet.html), [workers tiny_http](https://tiny-http.github.io/tiny-http/tiny_http/index.html). Documentação web versionada do driver não ficou acessível; comportamento de rollback Drop/commit e fechamento do writer foi conferido diretamente nas fontes oficiais baixadas postgres-0.19.12/src/transaction.rs e tiny_http-0.12.0/src/request.rs, identificadas pelo Cargo.lock. Sem extrapolar a documentação latest 0.19.14 para novas APIs.

Preparação efetivamente executada com a imagem local (container novo exclusivo; aguardar término do primeiro bootstrap antes de start):

```bash
docker run -d --name ec-publication-test -e POSTGRES_HOST_AUTH_METHOD=trust -p 127.0.0.1:55439:5432 itapiraco/postgis:18-3.6-alpine
docker wait ec-publication-test
docker start ec-publication-test
docker exec ec-publication-test pg_isready -U postgres
```

O harness cria seus próprios bancos; o exemplo manual exige `docker exec ec-publication-test createdb -U postgres ec_publication` antes da migration. Depois do ensaio o container de teste pode ser parado com `docker stop ec-publication-test`.

## Incremento T005

[Agent real, logs, artefatos, migration 003 e recuperação](agent.md). Os contratos e evidências anteriores permanecem históricos; o endpoint result legado não certifica entrega de saída. Harnesses devem rodar em sequência por compartilharem a porta HTTP.
