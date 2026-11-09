# Licenciamento do código próprio Ec

O usuário confirmou GPL3 para o projeto em 09/11/2026 e delegou a escolha da
declaração técnica. O identificador adotado é **GPL-3.0-only**, correspondente à
versão 3; não foi acrescentada permissão para versões posteriores. O texto
integral existente em [LICENSE](../../LICENSE) permanece intacto.

Os workspaces Agent e Server declaram `license = "GPL-3.0-only"` em
workspace.package. Os onze crates herdam esse campo com license.workspace=true.
O frontend declara o mesmo identificador em package.json e na entrada raiz do
package-lock. Publish=false/private=true permanecem configurados; esta mudança
não autoriza publicar pacotes ou o repositório.

O Cargo Deny aceita essa expressão explicitamente para os onze crates próprios,
pelos nomes dos pacotes em licenses.exceptions. Não foram acrescentadas
exclusões de advisories nem ignorados crates privados. A lista de licenças
permitidas para as demais dependências permanece a mesma.

Componentes importados, ferramentas e dependências conservam seus avisos e
licenças, incluindo scripts/LICENSE e os recursos das skills. Esta declaração
do código próprio não reescreve licenças de terceiros ou registros históricos.

Referências: [identificador SPDX GPL-3.0-only](https://spdx.org/licenses/GPL-3.0-only.html),
[configuração de licenças do Cargo Deny](https://embarkstudios.github.io/cargo-deny/checks/licenses/cfg.html).
Resultados de execução são registrados na
[verificação para commits](../validation/commit-verification-20261109/report.md).
