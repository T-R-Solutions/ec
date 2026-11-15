# Documentação de replicação dos agentes

Pedido: atualizar documentação, principalmente agentes, para replicar configuração em outra aplicação.
Rota local, complexidade normal: escopo documental recuperável; contratos existentes.
Não instalar no destino desconhecido, alterar perfis ativos, publicar ou criar commit.

REQ-01: inventário e responsabilidades reais, separados dos Agents do produto.
REQ-02: procedimento de adaptação reproduzível para workflow próprio e SDD completo.
REQ-03: validação em camadas e retomada honestas, sem alegar eficácia/instalação externa.
REQ-04: entrada navegável e fontes únicas em pt-BR.

- AC-01 → REQ-01: guia identifica config, treze perfis, matriz canônica, raiz/triagem/coordenação, limite da árvore e contextos independentes de revisão/validação.
- AC-02 → REQ-02: leitor encontra origem/destino/adaptação por papel, dependências diretas/transitivas, adoção mínima versus completa e preservação de configuração/permissões do destino.
- AC-03 → REQ-03: exemplos verificáveis, preflight e falhas/retomada distinguem estrutura, carregamento e execução; não prometem implantação externa ou eficácia; comandos sem destino real são identificados.
- AC-04 → REQ-04: README, índice, contribuição e procedimento comum encaminham ao guia sem duplicar a matriz; links locais e TOMLs relacionados conferidos.

Aceite próprio não aprova features relacionadas nem renova evidências anteriores.
