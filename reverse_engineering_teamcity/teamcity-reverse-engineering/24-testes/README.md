# Validação e cenários pendentes

A validação executada verifica integridade e consistência das evidências estáticas; não é teste funcional de TeamCity. Os scripts de análise carregam bytes com ASM/javap/CFR e não inicializam classes da aplicação.

Cenários dinâmicos previstos, todos pendentes (0/14): inicialização de servidor; inicialização de agente; registro/autorização; criação de projeto sintético; configuração de build; enqueue; compatibilidade de agente; sucesso; falha; cancelamento; publicação de artefatos; dependências; aplicação Kotlin DSL; plugin de teste.

A execução exige ambiente isolado com dados sintéticos. Nenhuma instância operacional, banco ou pipeline foi usado. A disponibilidade de uma instalação local não estabelece isolamento para iniciá-la.

Resultado: **1.239 verificações passaram**, incluindo descriptors de todas as 124 classes selecionadas e contagens de chamadas dos corpos elegíveis comparadas ao javap. Cinco classes sem corpos elegíveis não entram na comparação de instruções. O rehash de 16 artefatos originais selecionados não encontrou divergências. Ver `static-validation.json` e `original-artifacts-validation.json`.
