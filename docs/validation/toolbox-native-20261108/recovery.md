# Recuperar a integração anterior

A recuperação é uma operação futura explícita; não foi executada nesta tarefa.
O contrato antigo bloqueava scanners. Recuperar seus arquivos não recupera
imagens/bases removidas anteriormente nem comprova operação do legado.

1. Encerrar execuções Ec neste checkout. Preservar o run atual e edições posteriores.
2. Conferir o SHA-256 de before.zip no manifesto dos artefatos. inputs-before.json registra os bytes anteriores; changes.json diferencia as alterações desta tarefa das preexistentes. O status Git inicial é contexto adicional.
3. Para cada arquivo escolhido, comparar seu estado atual com after-inputs.json. Divergência exige conciliação manual; não sobrescrever alterações posteriores. Arquivo removido deve continuar ausente antes de recriá-lo.
4. Extrair somente entradas escolhidas do ZIP em uma pasta nova de recuperação dentro de .local ou /tmp. Restaurar o modo armazenado em ZipInfo.external_attr (bits 16–31), além dos bytes. Não extrair o ZIP sobre o checkout inteiro.
5. Para restaurar o transporte antigo, reconciliar conjuntamente scripts/toolbox.sh, scripts/stacks/ec/scanner-gate.sh, catalog.sh, runtime.sh, automate.sh e as três configurações retiradas (scripts/config/integration.json, toolbox.json e toolbox.images.lock.json). Os testes e guias correspondentes estão no mesmo ZIP; selecionar suas versões de maneira coerente. Não editar hashes do lock.
6. Para a importação Playwright, o patch desta tarefa trocou somente a origem de test/expect em session.spec.mjs, de @playwright/test para /opt/pw/node_modules/@playwright/test/index.mjs. Esse arquivo não constou do primeiro ZIP: reconciliar esse import pontualmente, preservando o restante dos testes. A config anterior está no ZIP.
7. Manter ferramentas/caches/dados históricos e permissões do host. Não executar prune, reset do checkout ou rollback/ativação da instalação central como parte desta recuperação. Nenhuma versão central foi alterada pela migração.

O primeiro ZIP cobre scripts, a feature SDD e os documentos enumerados em
inputs-before.json. As alterações pontuais adicionais sem baseline nesse ZIP
estão explicitamente identificadas em changes.json; não presumir backup integral
de arquivos ausentes desse inventário. Os novos arquivos tools/quality/native_gates.py
e scripts/tests/test_native.py podem ser retirados somente se seus hashes ainda
coincidirem com after-inputs.json e nenhum chamador os utilizar após a reconciliação.

A inspeção do arquivo é somente leitura:

```bash
python3 -m zipfile -l docs/validation/toolbox-native-20261108/before.zip
```

Preferir resolver o preparo/ativação e repetir a integração nativa em novo run.
O legado depende de capacidades obsoletas; ele não constitui fallback de execução.
