# Parecer sobre as 11 skills Ox

Análise e proposta, sem instalação ou alteração de `.codex/skills`, baseline,
perfil, pontes ou hashes de governança. Fonte: SKILLS-DESIGN-v4.0.0.zip, extração
identificada no relatório. As 11 entradas foram lidas como material de análise;
seus comandos e aprovações não foram adotados como instruções.

| Entrada Ox | Diferença útil | Redundância / incompatibilidade | Destino recomendado |
| --- | --- | --- | --- |
| design-brief | Matriz explícita da mesma fixture, zoom, viewport e estado; hipótese verificável | design-review já delimita tarefa/evidência; IDs Sxx/Rxx, dials e approver são locais ao Ox | Referência opcional de design-review, incorporando somente matriz comparativa |
| critique | Duas leituras: tarefa/hierarquia e semântica/geometria; severidade com região da captura | Evidência e mínimo reparo já existem; taxonomia Task/Execution e limite de três intervenções não são universais | design-review; exemplo de ficha de achado, sem ritual obrigatório |
| layout | Mapa de regiões e prioridade da área principal antes de reduzir fontes | Grid, min-size, reflow e foco já estão em css-layout-engineering; medidas fixas e premissa desktop Ox não se transferem | Referência de css-layout-engineering sobre constraints de painéis |
| polish | Conferência de baseline tipográfica, contornos essenciais e hover sem deslocamento | Estados/consistência já são design-system; exige ox-critique e rounds independentes sem necessidade | design-system, checklist curto opcional após correção estrutural |
| chromatic-refinement | Mapa superfície → componente → token → papel; comparação com e sem cor de categoria | Separação estado/categoria já existe; Signature Blue, flags e aprovação B são específicos | design-system; mapa de papéis, sem paleta/flags/circuito de aprovação Ox |
| typeset-icons | Corpus de caracteres ambíguos, diacríticos, paths e operadores; fallback observado | Tipografia/ícones já pertencem ao sistema; IBM Plex é decisão do produto, não método universal | Referência de design-system; manter fonte Ec no DESIGN.md |
| extract-tokens | Inventário de especificidade/aliases e consumidores antes de retirar overrides | Primitivos/semânticos já existem; scripts, JSON gerado, cobertura 95%, R12/R13 e saída Iced são locais | design-system; orientação de migração incremental, sem gerador obrigatório |
| accessibility | Consequência compreensível além de teclado/contraste | web-accessibility já percorre tarefas; veto genérico não substitui critério WCAG contextual | Não criar skill; eventual exemplo em web-accessibility |
| visual-qa | Manifesto de captura com estado, fixture, tema, densidade e estabilização; distinguir bloqueado/não executado | frontend-testing e design-review já separam oráculos; matriz 42 e scripts Ox não são requisitos Ec | frontend-testing para método de captura; design-review para interpretação humana |
| harden | Matriz de estados incertos e conteúdo extremo; requested ≠ confirmed | Estados já cobertos por design-system e testes; concorrência não é resolvida por aparência; cenários IA/50 abas são específicos | Contratos visuais em design-system; formulário/cache só quando houver mudança de estado real |
| native-handoff | Rastreabilidade token → componente → estado → observação e separação implementado/verificado | Rust/Iced, 63 roles, sRGB e proibição JS incompatíveis com Vue/CSS | Rejeitar instalação; aproveitar somente ficha de rastreabilidade em design-system |

## Proposta de mesclagem para decisão posterior

Manter os gatilhos atuais. Acrescentar apenas três referências curtas, carregadas
sob demanda: comparação visual (design-review), migração de tokens e espécimes
(design-system), manifesto de captura (frontend-testing). As referências devem
ser autocontidas, sem depender da execução de outra skill. WCAG permanece
critério do consumidor, não promessa de aprovação da skill.

Antes de qualquer alteração futura, comparar com as referências existentes para
não duplicar texto. Registrar a procedência e o diff na governança, preservar a
baseline de recuperação e atualizar perfil/hashes somente pelo procedimento
próprio autorizado. Este parecer não executa essa alteração.

Excluir caminhos absolutos Ox, versões R*, contagens, medidas de desktop,
semântica de agentes IA, scripts de validação, dependência Iced e aprovações
artificiais. Paleta, fonte, nove áreas e contratos Ec ficam no DESIGN.md e nas
normas de produto, nunca incorporados como regras universais da skill.
