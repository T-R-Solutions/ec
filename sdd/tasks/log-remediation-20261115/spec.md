# Remediação dos diagnósticos de 15/11/2026

Pedido vigente: executar o plano derivado dos runs 6Q9frQYX e Tfjg1Tyv.
Escopo: revisão semântica dos 74 achados Bandit pendentes, triagem canônica
estrita, import Rust condicionado e investigação dos avisos CodeQL/YARA.
Preservar mudanças preexistentes e logs; sem commit ou publicação.

- REQ-01: Revisar as ocorrências atuais sem excluir fontes, regras ou diagnósticos.
- AC-01 → REQ-01: As 74 ocorrências têm análise de contexto e precondições; triagem aceita somente hash, regra, linha e motivo atuais, preservando scan bruto e rejeição de mutações.
- REQ-02: Eliminar import incondicional sem quebrar variante de teste.
- AC-02 → REQ-02: Import Read segue test-failpoints; builds e testes das variantes padrão e harness permanecem válidos.
- REQ-03: Investigar avisos sem inventar defeito ou silenciar diagnóstico.
- AC-03 → REQ-03: CodeQL/dynload e YARA têm fatos, hipóteses, limites e decisão revisados; alterações só quando deficiência comprovada, mantendo extração e eficácia.

Esta tarefa não aceita integralmente scripts-verification ou a primeira jornada.
A aprovação exige recibo atual e gate externo à evidência, conforme política.
