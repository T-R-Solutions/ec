# Catálogo de skills e recuperação

As 67 skills estão instaladas em `.codex/skills/` (599 arquivos na revisão de
11/11/2026). O perfil `config/governance/profile.json` registra os hashes vigentes;
as pontes SDD apontam para a instalação real. Não existe mais baseline duplicada
em `.local/skill-installation/skills/`, nem ZIP de versões anteriores.

Recuperação deve usar uma revisão disponível no Git/GitHub, preservando mudanças
alheias e conferindo perfil, pontes e arquivos correspondentes. Git não comprova
que conteúdo sem commit esteja publicado. Não renovar hashes para ocultar
alterações inesperadas. Fontes originais permanecem nos bundles declarados em
`source_archives`/`source_bundle`; não substituem a revisão adaptada da instalação.

O auditor verifica `.codex/skills/` quando o perfil está `installed`; não depende
da antiga baseline. O instalador reconhece uma instalação íntegra como
`ALREADY_INSTALLED`. Seu modo legado `prepared` e recuperação por journal existem
para cenários isolados; não são instrução para recriar preparação neste checkout.
Os testes constroem suas fixtures temporárias a partir do catálogo instalado,
removendo-as ao encerrar. Journal transacional de uma instalação interrompida
não deve ser confundido com backup periódico ou apagado para contornar conflito.

A auditoria e os testes integram a suíte, acionada somente conforme
[task-verification.md](task-verification.md). Nenhuma alteração de documentação
comprova eficácia da skill, descoberta pela interface ou auditoria aprovada.
Novas instalações continuam sujeitas às permissões reais do ambiente.

A mesclagem seletiva de SKILLS-DESIGN-v4.0.0 atualizou `design-system`,
`design-review`, `css-layout-engineering` e `frontend-testing`, sem criar novas
identidades. O ZIP canônico está em
`config/governance/sources/SKILLS-DESIGN-v4.0.0.zip`; a cópia em `.local` foi
removida. `source_files` conserva a origem, `files` descreve o conteúdo atual e
`adaptations` aponta a análise. Consulte o
[guia de métodos](frontend-design-skills.md) e a
[política de retenção](local-state.md).
