import type { DemoDefinition } from '../../../shared/ui/demo'
export const demo: DemoDefinition = {
  "title": "Explorador de projetos",
  "persona": "Desenvolvedor / administrador de projeto",
  "task": "Inspecionar hierarquia e revisar propriedade",
  "permission": "Gestão do projeto e do pai",
  "consequence": "Uma mudança de pai poderia alterar herança e recursos associados. Esta proposta não move projetos.",
  "limitation": "Hierarquia e propriedade são exemplos; o cadastro real continua pela ferramenta atual.",
  "fields": [
    {
      "key": "name",
      "label": "Nome"
    },
    {
      "key": "parent",
      "label": "Projeto pai"
    },
    {
      "key": "owner",
      "label": "Equipe responsável"
    }
  ],
  "records": [
    {
      "id": "checkout",
      "name": "Checkout",
      "scope": "Comércio / Checkout",
      "state": "Rascunho",
      "values": {
        "name": "Checkout",
        "parent": "Comércio",
        "owner": "Equipe checkout"
      },
      "facts": [
        [
          "Herança",
          "Política de retenção do projeto Comércio"
        ],
        [
          "Repositório",
          "checkout-service · exemplo"
        ]
      ]
    },
    {
      "id": "catalog",
      "name": "Catálogo",
      "scope": "Comércio / Catálogo",
      "state": "Referência",
      "values": {
        "name": "Catálogo",
        "parent": "Comércio",
        "owner": "Equipe catálogo"
      },
      "facts": [
        [
          "Herança",
          "Política do projeto Comércio"
        ],
        [
          "Repositório",
          "catalog-service · exemplo"
        ]
      ]
    }
  ],
  "id": "projects"
}
