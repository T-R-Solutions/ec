# Decisões sobre os avisos investigados

Análise readonly do run-Tfjg1Tyv, corroborada pelo revisor independente.

## CodeQL Python

O stream 43-security-codeql-09-codeql.stdout.log registra fallback Python3
(linhas 1 e 9), dynload indisponível (16) e 104 módulos processados (147).
Os quatro SARIFs são saudáveis, sem resultados ou notificações warning/error;
o Python identifica 41 fontes .py saudáveis e 218 notificações locais totais.
A inspeção do Dockerfile externo local indica instalação de python3 sem pacotes
recomendados; isso não identifica por si só os bytes da imagem histórica.
Não foi demonstrada perda de fonte Ec ou deficiência funcional por dynload.
Manter runtime e diagnóstico; não instalar Python2 nem suprimir warning.
Para confirmar/refutar perda de resolução nativa: identificar módulos necessários
aos alvos e comparar sua resolução/extrator na imagem de digest registrada.
Esta verificação adicional não foi executada e não é alegada como aprovada.

## YARA-X

O stream 47-security-yara-x-00-yara-x.stderr.log:458 registra loop potencialmente
lento em apt_winnti_br.yar:45, limitado por #jpeg. Outros avisos são text_as_hex,
non_bool_expr e duplicate_import dos catálogos importados. Nenhum timeout ou
compilação inválida foi observado. native_gates.py:1550 rejeita compilação
inválida/regras descartadas; :1600 exige scan sem stderr e inventário NDJSON.
Não alterar catálogo, intervalo ou duração apenas para apagar avisos. Otimização
futura depende de workload representativo e equivalência em fixtures positivas
e negativas; ausência de timeout histórico não prova custo limitado universal.

## Outros sinais

E0554 em 43-security-codeql.server-rustc.stderr.log:61 é probe proc-macro2,
registrado não crítico em status JSONL:35. Não trocar toolchain por esse sinal.
Cargo deny usa política compartilhada; Vite escreve em temporário exclusivo.
Não há defeito adicional comprovado que justifique alterações nesses componentes.

Todos os caminhos de logs acima ficam em .local/logs/2026-11-15/ e usam prefixo
run-Tfjg1Tyv-. São observações históricas, não recibo novo de aceite. A nova
execução deverá preservar saúde da extração e eficácia dos scanners.
