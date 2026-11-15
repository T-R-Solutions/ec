# Especificação — aceite obrigatório de tarefas

Pedido de 14/11/2026: tornar obrigatório o ciclo de trabalho e impedir aceite
local quando etapas ou evidências faltarem. A análise encontrou instruções
opcionais após commit, validador estrutural e runner com recibos, sem gate por tarefa.

- REQ-01: exigir análise, planejamento, implementação, revisão, testes e validação.
- AC-01 → REQ-01: o gate rejeita etapa ausente, critério pendente e evidência alterada.
- REQ-02: exigir execução atual, completa e bem-sucedida das superfícies declaradas.
- AC-02 → REQ-02: o gate rejeita falha, skip, limpeza incompleta, tarefa ausente e arquivo alterado, adicionado ou removido após o run; aceita fixture íntegra.
- REQ-03: integrar a entrada e eliminar o fluxo opcional contraditório.
- AC-03 → REQ-03: accept-task.sh está acessível pela entrada Bash e a política exige testes antes do aceite; limites de chat, Git e adulteração local são explícitos.

Não há acesso a um supervisor do chat ou configuração de CI protegida. A garantia
implementável é rejeição no gate local; não prometer impossibilidade absoluta de
bypass por quem possui escrita no próprio verificador.
