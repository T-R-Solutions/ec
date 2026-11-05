# Catálogo e seleção de skills

O [perfil](../../config/governance/profile.json) registra 71 candidatas, 67 selecionadas e quatro excluídas; seis selecionadas são condicionais.
Consulte a [análise individual](../../docs/development/skills-analysis.md) para motivos, limites e fontes.
Cada ponte contém o caminho efetivamente disponível, identidade e versão do formato. Confira esse caminho antes de usar a skill.

Escolha a primária pelo problema: scheduler ≠ protocolo ≠ runtime do Agent ≠ processos; captura de saída ≠ ingestão de logs; schemas ≠ projeções ≠ publicação; autenticação ≠ autorização ≠ administração.
Auxiliares precisam resolver questão distinta. Não carregar todo o catálogo nem criar agentes automaticamente.
Registre na feature caminho, hash observado, contribuição e se a skill foi indicada, selecionada, lida ou efetivamente aplicada.

O instalador em `tools/governance/install_skills.py` confere os arquivos antes de migrar as pontes para `.codex/skills/`. As 67 skills estão instaladas e as pontes migradas; isso não comprova descoberta automática pela interface.
