# Contrato público da primeira jornada

`pipeline-v1.json` é a fonte de campos e limites do protocolo v1, consumida pelo
frontend e pelo build deste pacote. `src/lib.rs` fornece nomes, request IDs e
codificação sem importar domínios. Server e Agent declaram dependência pública
por path em seus workspaces; não dependem um do outro. O pacote é membro do
workspace Server para usar seus checks e lock, sem um terceiro workspace. Ver [ADR-003](../../docs/decisions/ADR-003-public-journey-contract.md).

Não editar saídas geradas: ficam apenas no OUT_DIR do Cargo. JSON aqui descreve
um protocolo; o YAML continua a configuração desejada canônica. Mudanças de
contrato exigem revisar compatibilidade e os consumidores já implantados.
