# Seleção de skills e contrato das pontes

O [perfil](../../config/governance/profile.json) é o catálogo canônico: seleção,
origem, caminhos e hashes dos pacotes. A [análise individual](../../docs/development/skills-analysis.md)
registra os motivos de adoção e exclusão. As pontes mapeiam contribuições ao SDD;
os métodos e gatilhos pertencem aos respectivos `SKILL.md`.

## Selecionar e carregar

Escolha uma primária pelo problema e auxiliares apenas para questões distintas
ainda não resolvidas. Fronteiras importantes: scheduler/protocolo/runtime/processos;
captura de saída/ingestão de logs; schemas/projeções/publicação;
autenticação/autorização/administração. Para interfaces, use o
[guia de seleção de frontend](../../docs/development/frontend-design-skills.md).

Confira `skill_path` e a identidade `name`, leia a entrada efetiva e carregue
referências sob demanda. Uma menção não instala, ativa, delega nem executa outra
skill. A rota local pode usar um método sem criar feature ou ponte adicional.
Ausência de capacidade indispensável deixa o critério não verificado; continue
o trabalho independente.

Na rota completa, registre no plano: ponte e `bridge_version`, caminho e SHA-256
da entrada lida, motivo, contribuição e questão específica de cada auxiliar.
Distinga indicação, seleção, leitura e aplicação. Instalação não demonstra
descoberta pela interface, execução nem eficácia.

## Mapeamento comum

Forneça escopo autorizado, requisito/critério, decisão/risco, tarefa e os inputs
específicos da ponte. Cada artefato tem uma responsabilidade:

| Artefato | Conteúdo |
| --- | --- |
| Constituição | Autoridades e restrições existentes; a skill não cria política de produto |
| Spec | Comportamento, rejeições e critérios com cenário e oráculo justificável |
| Plan / ADR | Alternativas, escolha, custos, compatibilidade e riscos ligados aos requisitos |
| Tasks | Mudanças delimitadas, arquivos/ferramentas reais, dependências e condição de conclusão |
| Evidência / convergência | Inputs observados, método, resultado, critério e limitações conforme [evidence.md](../evidence.md) |

Auxiliares recebem a questão não resolvida, IDs pertinentes, fatos, hipóteses,
restrições e evidência necessária, dentro da autorização existente. Esta consulta
não cria uma cadeia obrigatória nem determina criação de subagentes.

## Manutenção

Cada ponte conserva apenas metadados de localização e contribuições específicas.
Não copie o workflow, os estados de evidência, o procedimento da skill ou a
[política de scripts](../../docs/development/task-verification.md) para as pontes.
Mudanças nos pacotes exigem atualizar `files` no perfil; `source_files` e bundles
identificam a origem preservada. Hash de catálogo não renova evidência de aceite.
