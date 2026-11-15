# Especificação — proposta documental de subagentes

- REQ-01: entregar análise direta em Markdown na raiz, com tabelas de agentes, atuação, esforço, orquestração e configurações, baseada no Ec e na documentação oficial.
- AC-01 → REQ-01: ANALISE-SUBAGENTES.md contém os sete papéis, dependências, modelo/esforço e configuração de cada subagente e do principal; distingue proposta de instalação e limitações de enforcement; links internos e exemplos TOML são válidos.

Escopo documental: não instalar agentes, alterar permissões ou delegar a tarefa.

## Revisão de modelos e controle — 14/11/2026

- REQ-02: incorporar Astra e perfis de complexidade, explicitando a autoridade que define esforço e os limites da sessão.
- AC-02 → REQ-02: proposta usa Astra low no principal, Sol nos perfis padrão e variantes explícitas; não promete mudança do próprio esforço durante execução; exemplos e tabelas são coerentes e não ativam configurações.

## Adoção autorizada — triagem e dois perfis, 14/11/2026

O pedido posterior autoriza criar a configuração efetiva conforme a análise
aprovada na conversa. A restrição documental acima descreve a etapa anterior;
os exemplos históricos de ANALISE-SUBAGENTES.md permanecem preservados.

- REQ-03: configurar entrada Sol médio, dois orquestradores Astra médio/alto e especialistas conforme a matriz aprovada; extra alto somente por necessidade.
- AC-03 → REQ-03: .codex/config.toml e os treze agentes têm TOML válido, nomes únicos, modelos/esforços da matriz de docs/development/codex-agents.md e configuração reconhecida pelo cliente local, sem alterar permissões ou configuração global.
- REQ-04: definir triagem, entrega de contexto, seleção proporcional, continuidade, capacidade e responsabilidades distintas de revisão/teste/validação.
- AC-04 → REQ-04: instruções e guia cobrem classificação por risco, escalonamento justificado, raiz acompanhando o orquestrador, no máximo quatro agentes contando a raiz, um escritor por arquivo e aceite conforme a política canônica; distinguem configuração de execução comportamental não demonstrada.

Escopo de aceite: configuração local e contratos das instruções. Não inclui
benchmark, avaliação comportamental por modelos, scheduler determinístico nem
troca do modelo da conversa em execução.
