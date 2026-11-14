# ADR-003 — Contrato público compartilhado da jornada v1

Data: 13/11/2026. Estado: adotada para a consolidação autorizada nesta sessão;
validação executada será registrada na convergência da primeira jornada.

Server e Agent mantêm workspaces e releases próprios. Campos e limites do
protocolo, antes repetidos, pertencem a `application/contracts/`. Esse pacote
não contém SQL, autenticação, processos, estado ou imports dos domínios. Ambos
consomem a mesma fonte durante o desenvolvimento, sem importar um ao outro.
O pacote integra o workspace Server para receber seus testes, formatação e lock;
o Agent o consome como biblioteca pública. Não há terceiro workspace.

O descritor JSON versionado é entrada de build do protocolo HTTP, não YAML de
configuração desejada. O build Rust gera declarações em OUT_DIR; Vite importa o
mesmo descritor. Arquivos gerados não são versionados. Validação continua em cada
fronteira. Alterações incompatíveis exigem nova versão e estratégia explícita;
compartilhar fonte não demonstra compatibilidade de binários já implantados.

Manter cópias com checagem de igualdade detectaria divergência, mas ainda exigiria
editar várias fontes. Unificar os workspaces mudaria a supervisão e a resolução
sem necessidade. A biblioteca pública pequena atende aos consumidores atuais.

Publicação e submissão mantêm recibos, transações e decisões de replay no domínio.
A composição traduz HTTP, verifica sessão e conecta validação sem repetir a
comparação. PostgreSQL continua a única autoridade operacional. Nenhuma migration,
novo transporte, serviço ou privilégio é introduzido.

Reavaliar a fronteira ao suportar agentes de outra versão, novos schemas ou
consumidores externos. O pacote não autoriza abstrações genéricas nem mover
implementações privadas para uma biblioteca comum.
