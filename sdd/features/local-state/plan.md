# Plano e decisões — 11/11/2026

Rota completa: exclusão de dados locais, retenção e concorrência. O pedido atual
substitui a preservação anterior de cópias. Não criar novos backups nem executar
suíte sem seguir task-verification.md.

D-01 / R-01 / REQ-01: distinguir fontes originais, logs, caches/builds e backups.
Mover ZIP visual original para docs/design/sources; manter o ZIP canônico de
skills no catálogo e apagar duplicata/extratos. Remover archives/baseline e
backups explícitos em docs/validation. Não confundir hash `before.json` com
backup de conteúdo. Risco de perda aceito pelo pedido; não prometer recuperação
de arquivos sem commit. Registrar manutenção somente no log da data.

D-02 / R-02 / REQ-02: workspace temporário por run e targets compartilhados
somente durante esse run; passar caminhos de binários aos harnesses e build
frontend transitório ao gate Playwright. stdout e
stderr são gravados diretamente como logs privados por ferramenta. Exportar
metadados de execução e logs nativos, descartando relatórios/snapshots/artefatos.
Testes de governança derivam preparação do catálogo instalado em fixtures
transitórias. Alternativa rejeitada: mudar o nome de archive e continuar guardando
as cópias. Dependências offline passam a usar o cache já preparado do usuário.

D-03 / R-03 / REQ-03: data local de início, ordem ISO e duas datas distintas;
mesmo dia acumula logs com IDs distintos. Lock em /tmp separado da árvore
expirada, descritores sem symlinks e conteúdo restrito. Relógio regressivo falha.
EXIT conserva status e descarta temporários. Risco residual: SIGKILL/host ou
filesystem podem deixar temporários; não remover automaticamente um workspace
que possa conter processos ativos. Sem garantia contra escritor malicioso do
mesmo usuário. Retenção de datas não limita bytes por dia.

D-04 / R-04 / REQ-04: atualizar entradas operacionais e registrar que backups
citados em documentos datados foram retirados. Não renovar evidência de testes.

Primária: tool-output-engineering, ponte versão 1,
`.codex/skills/tool-output-engineering/SKILL.md`: preservar saídas brutas,
classificação, exit e limites de retenção. Auxiliar consultada:
workspace-integrity-engineering, ponte versão 1,
`.codex/skills/workspace-integrity-engineering/SKILL.md`: contenção de caminhos,
lock e identidade. Recomendações de backups/snapshots para recuperação dessas
skills não se aplicam diante da proibição expressa do usuário. Hashes consultados
estão no registro da mudança. Sem delegação ou instalação de skills.
