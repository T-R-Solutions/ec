# Modelo de ameaças e gates

Ativos: credenciais de agente, segredos de projetos, integridade de resultado, código, artefatos e disponibilidade.
Adversários: usuário sem permissão, build com código hostil, agente comprometido, rede intermediária e consumidor de artefato.
Fronteiras: usuário/API; agente/controle; supervisor/job; projetos entre si; metadados/armazenamento.

| Ameaça | Política inicial | Gate de ampliação |
|---|---|---|
| Escape de processo | piloto com repositórios e código confiáveis, usuário dedicado | OCI endurecido com namespaces, cgroups, seccomp, drop capabilities e filesystem restrito |
| Kernel compartilhado | pools separados por confiança | VM/microVM efêmera para workloads hostis conforme modelo de implantação |
| Roubo de segredo | referências autorizadas, credenciais efêmeras, token do agente fora do job | rede/egress controlados; teste de exfiltração; nunca confiar só em masking |
| Agente mente sobre execução | autorização e trilha de identidade | não há atestação de execução no CORE; agente é parte confiável |
| DoS | quotas CPU/RAM/pids/disco/log/artefatos, HTTP limitado | medir saturação e impedir starvation de heartbeat |
| Traversal/symlink | resolver com operações relativas a diretório e negar symlinks | testes TOCTOU no coletor; canonicalize isoladamente é insuficiente |
| Cross-project | autorização por objeto/projeto e pool | testes negativos em cada endpoint, inclusive download por digest |
| Rede hostil | TLS com validação de nome e rotação | revogação efetiva e nenhuma opção insegura automática |

OCI é especificação de recursos e isolamento, não garantia de contenção absoluta. Processo dedicado sozinho não
isola código não confiável. Nunca montar socket Docker do host nos jobs. Código hostil só habilitado depois de A13.
Sem prova jurídica de independência: código do protótipo usa contratos próprios, sem copiar pseudocódigo/recursos TeamCity.
