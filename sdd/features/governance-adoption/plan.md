# Plano — governança Ec

## Fontes e decisões

Normas originais em docs, três pacotes de skills, AGENTS e SDD Ox. Pedido atual autoriza adaptar o AGENTS local previamente protegido contra substituição na tarefa anterior.

- D-01 / R-01 / REQ-01: 40 Ec + 26 Ox + README; excluir quatro, preservar arquivos e hashes. Risco: catálogo incompleto ou aplicação por palavra-chave.
- D-02 / R-02 / REQ-02: ADR-001 documental e contexto real; propostas gRPC/SQLite não adotadas. Risco: importar permissões/aceite/stack Ox.
- D-03 / R-03 / REQ-03: SDD portátil com links entre autoridades dentro da raiz; paths reais na preparação. Risco: links quebrados ou falso aceite.
- D-04 / R-04 / REQ-04: auditor stdlib, testes negativos e validador oficial de skills disponível no ambiente. Risco: testes que só repetem implementação.
- D-05 / R-05 / REQ-05: instalador sem overwrite e com verificação; respeitar EROFS. Risco: interromper migração multi-arquivo; manter baseline e reexaminar evidência após instalação.

## Métodos efetivamente consultados

Skill-creator em `/home/rodrigo/.codex/skills/.system/skill-creator/SKILL.md` orientou escopo, metadados e validação. Não faz parte das 67 migradas; seu hash e do quick_validate serão registrados na evidência de ferramentas. Entradas dos pacotes foram objeto da análise, não declaração de execução das 67 especialidades. A entrada README e fronteiras Ec foram consultadas para estrutura honesta de onboarding; não se alega estudo de usuários.
Pontes formato 1, skill identity por name, versões locais por hashes no perfil. Fontes técnicas externas não foram atualizadas; esta tarefa adapta contratos documentais fornecidos, não recomenda bibliotecas/versionamento atuais.

## Entregas e recuperação

AGENTS, README, docs de autoridades/arquitetura/análise/ADR, perfil, 67 pacotes preparados, pontes, templates, auditor, instalador, testes e evidência schema 2. Não copiar features Ox nem construir aplicação. Instalar quando destino gravável; depois atualizar prose de status e coletar observações novas. Não modificar hashes antigos para mascarar drift.

## Retomada da instalação — 2026-11-03

Rota local para concluir T005 conforme D-05, sem mudança de contrato ou seleção. Skill primária aplicada: `.codex/skills/workspace-integrity-engineering/SKILL.md`; ponte formato 1; SHA-256 `6811f2e83a4bdc376eb37df172fe3112f8ac02d2eb25f65821dabdf41dfa6bc8`. Contribuição: preservar baseline e registros anteriores, verificar integridade e conflitos, distinguir instalação de descoberta e emitir evidência nova. Os ensaios multiagente/watchers da skill não foram executados; não fazem parte desta migração delimitada. O instalador foi autorizado fora do sandbox e retornou INSTALLED para 67 pacotes. A suíte prepara seus próprios metadados de preparação em cópias isoladas, independentemente do estado instalado do checkout.

## Limpeza posterior — rota completa

- D-06 / R-06 / REQ-06: aposentar ZIPs integrais somente depois de preservar recursos pertinentes num bundle verificável. Risco: eliminar fonte única, quebrar consumidor ou disfarçar evidência antiga. Manter a pesquisa, baseline e registros históricos; atualizar consumidores, critérios afetados e produzir observações novas.
- Comparação: excluir tudo perderia proveniência; manter todos os pacotes conservaria cerca de 110 MiB de entradas já integradas e material de outro produto. O bundle mantém todas as 71 fontes de skills e os métodos/contextos de governança consultados, sem importar estado de produto Ox.
- Skill primária: `.codex/skills/workspace-integrity-engineering/SKILL.md`, ponte 1, SHA-256 `6811f2e83a4bdc376eb37df172fe3112f8ac02d2eb25f65821dabdf41dfa6bc8`. Contribuição: inventário de caminhos, conferência de hashes/identidades antes das mutações, cópia das versões editadas, preservação e comparação final. Não há Git. Não se alega isolamento contra escritor hostil, atomicidade de múltiplos arquivos ou execução dos ensaios de watchers/multiagente da skill.
- Recuperação: versões prévias dos arquivos editados em `docs/validation/workspace-cleanup-20261103/previous.zip`; diário identifica cada escrita/remoção. Comparar hashes atuais antes de restaurar, nunca sobrescrever trabalho novo. As fontes pertinentes são recuperáveis pelo bundle; conteúdo de produto Ox descartado não é recuperável por seus hashes.
- Ordem: inventariar e preservar fontes; adaptar perfil, auditor, testes e documentação; validar; conferir precondições e remover somente a lista aprovada; verificar preservação e renovar a evidência com novas execuções.

## Manutenção da skill documental — 2026-11-04

Rota local para refatoração autorizada de método/documentação, sem alterar contratos de produto ou a seleção das 67 skills. D-01/D-03/D-05 e R-01/R-03/R-05 continuam pertinentes: preservar recursos originais, referências resolvíveis e instalação/baseline consistentes. Primária aplicada: `/home/rodrigo/.codex/skills/.system/skill-creator/SKILL.md`, SHA-256 `6656e54755638e8efcf275a472b9672eaa8a9a1b9e59dc210e275b03b59e1e66`, para escopo, divulgação progressiva, metadados e validação. `technical-documentation` é o objeto da edição, não uma alegação de execução de todas as suas modalidades.

Duas referências concentram qualidade visual mensurável e Mermaid/assets. As medidas editoriais são convenções locais; contraste remete a W3C e suporte de ícones ao Mermaid oficial consultados. Preparar e validar candidato antes de solicitar escrita na pasta protegida `.codex`; atualizar perfil/baseline sem alterar fontes preservadas. Preservar versões anteriores e reexaminar evidência de governança. Não introduzir instalação de renderizador, produção de imagens, publicação ou implementação de produto por esta manutenção.

## Recuperação — rota completa, 2026-11-05

- D-07 / R-07 / REQ-07: journal write-ahead com conteúdo anterior e esperado, substituições individuais e flock não bloqueante no Linux. Rollback automático foi descartado pois restaurar cegamente apagaria edições externas; aceitar qualquer divergência de ponte enfraqueceria a auditoria. O journal tem schema 1, conjunto fechado de caminhos e estado final recalculado a partir do anterior. Só conteúdos anterior/esperado são retomáveis. O perfil é o último commit; o journal só é retirado após auditoria final.
- Primária: `.codex/skills/workspace-integrity-engineering/SKILL.md`, ponte 1, SHA-256 `6811f2e83a4bdc376eb37df172fe3112f8ac02d2eb25f65821dabdf41dfa6bc8`. Contribuição: precondições de conteúdo, journal, conflitos e exclusão entre processos. Não houve delegação.
- O auditor público não aceita estados intermediários. Na retomada, uma cópia temporária dos inputs restaura somente a visão anterior de metadados para auditoria estrita; os arquivos reais permanecem intactos. Pacotes do destino são conferidos separadamente antes de continuar. --check não recupera nem altera metadados.
- Escopo: processos cooperantes, Linux e filesystem local com rename/flock/fsync. Escritor externo simultâneo que ignora flock pode correr entre comparação e replace; não se promete proteção contra adversário com escrita no checkout. Journal não é assinatura contra adulteração coordenada de todos os inputs. Uma ponte alterada para bytes exatamente iguais aos esperados é indistinguível de progresso legítimo e segura para convergência.
- Arquivos e diretórios são sincronizados, mas os testes exercitam interrupção de processo/I/O, não corte físico de energia. Temporários órfãos fora do destino ativo são preservados; nunca promovidos pela mera existência. Não há transação atômica multi-arquivo.
