# Evidências preservadas

`rea-desktop-bundle.json` contém a Evidence completa do reconhecimento REA; `rea-desktop-summary.json` é somente sua projeção. O SHA do diretório emitido pelo REA vincula observações de inventário limitado, não os bytes integrais de todos os arquivos. `../artifacts.json` contém SHA-256 integral dos arquivos inventariados.

`symbols.jsonl.gz` é o índice ASM completo. `classes/`, `javap/` e `decompiled/` contêm representações seletivas vinculadas pelos índices focus. Arquivos Java são saída de decompilador, não código-fonte original. `resources/` preserva recursos embarcados selecionados e scripts de inicialização; não contém Data Directory operacional.

O índice externo de recibos está em `../evidence-index.json`. Receipts externos não são IDs Evidence emitidos pelo REA. A validação está em `../24-testes/static-validation.json`. Os scripts de produção e consulta estão em `rea/tools/`, fora desta pasta.
