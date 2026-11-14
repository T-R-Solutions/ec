# Ec Agent

Workspace Rust com cinco crates; edição e toolchain pertencem aos manifests. T005 executa jobs locais Linux confiáveis, admitidos sem credenciais externas. [Operação, build, contratos e recuperação](../../docs/development/agent.md); [evidências](../../docs/validation/agent-20261105/report.md). Não há isolamento de jobs hostis nem exatamente uma vez de efeitos externos.

A [política de verificação](../../docs/development/task-verification.md) controla
os checks do workspace. Configuração e limites dos scanners estão no
[contrato Toolbox/Ec](../../scripts/README.md); a integração nativa substituiu STIC
e depende de ferramentas centrais, bases válidas e pré-requisitos da aplicação. As ferramentas baixadas no Ec
foram removidas; o Agent e a plataforma Toolbox não foram alterados.

A [limpeza de 09/11/2026](../../docs/validation/cleanup-20261109/report.md) retirou
somente src/.gitkeep dos cinco crates já preenchidos; fontes, tests/ ainda vazios,
manifests e lock permanecem. Nenhuma verificação foi executada nessa limpeza.

Campos e limites de protocolo vêm do [contrato público v1](../contracts/README.md). Dependências externas são herdadas do workspace; não há import de domínio Server.
