# Plano

D-01: manter procedimento comum canônico e acrescentar guia de replicação autocontido;
R-01: duplicação da matriz produziria divergência. Guia aponta à matriz/config real.
D-02: duas adoções delimitadas e mapa por papel;
R-02: copiar TOMLs sem políticas/gate produziria dependências quebradas e falso aceite.
D-03: verificação estática e fixtures proporcionais, com revisão independente;
R-03: parsing não comprova carregamento ou eficácia; nenhum destino real fornecido.
D-04: links nas entradas existentes;
R-04: detalhes espalhados ou mudanças concorrentes perderiam a fonte única.

Primária technical-documentation (.codex/skills/technical-documentation/SKILL.md),
auxiliar readme-lifecycle-engineering para navegação; openai-docs para claims de cliente.
Entradas efetivas lidas; manual oficial atual consultado, CLI --version/--help observados.
Tabelas comunicam inventários e mapeamentos sem novos assets/renderizador.

Somente superfície docs: mudança em guias, índices e registro SDD; sem código,
configuração ou scripts alterados nesta tarefa. Testes focados de TOML, links e
snippet em fixture mais plan do runner; depois runner docs e gate --task atuais.
Revisor independente confronta escopo e contratos; testador executa cenários;
validador independente confronta critérios/evidências e executa gate após recibo final.

Coordenador único escreve README.md, docs/README.md,
docs/development/{contributing,codex-agents,codex-agents-replication}.md e este
registro; especialistas somente observam e testador escreve seu log/temporários.
Preservar demais mudanças existentes. Limite4 nós inclui raiz/controlador,
coordenador e dois especialistas; liberar término antes de novo contexto.
