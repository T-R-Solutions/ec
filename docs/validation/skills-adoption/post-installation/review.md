# Revisão após instalação — 2026-11-03

A instalação retornou INSTALLED, count=67. A nova auditoria exige --require-installed no checkout real e passou. Os 594 arquivos instalados correspondem aos hashes do perfil; fontes e ZIPs continuam íntegros. As 67 pontes resolvem o destino instalado. A nova execução revalidou metadados, recursos, fontes e os testes negativos.

Reexaminados AGENTS, contexto, constituição, ADR-001 e análise de seleção: permanecem Rust Server/Agent, Vue/TypeScript, YAML canônico e PostgreSQL/HTTP. gRPC/Protobuf/SQLite continuam propostas. As 71 decisões e seis skills condicionais permanecem iguais. A mudança documental registra apenas a instalação concluída; nenhuma norma original foi alterada. A fixture dos testes agora reconstrói a preparação dentro do diretório temporário, preservando os mesmos cenários de rejeição, recuperação e idempotência.

Os registros anteriores foram preservados em previous/ como bytes, inclusive evidence.json, relatório e convergência. As novas evidências usam nova execução e revisão do delta; hashes históricos não foram renovados.

Limites: não observada descoberta pela interface, eficácia das skills, URLs externas ou runtime Ec. Sem certificação de concorrência, watchers ou transação atômica entre todos os metadados. Instalação é cópia verificada com baseline preservado, conforme o procedimento do repositório.
