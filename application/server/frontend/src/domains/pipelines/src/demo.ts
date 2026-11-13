import type { DemoDefinition } from '../../../shared/ui/demo'
export const demo: DemoDefinition = {
  "title": "Biblioteca de pipelines",
  "persona": "Desenvolvedor",
  "task": "Explorar a apresentação de uma etapa",
  "permission": "Escrita no projeto para publicação real",
  "consequence": "A proposta visual não é ProjectPipeline validado. Use a sessão operacional para validar e publicar o YAML real.",
  "limitation": "Editor de demonstração sem parser; DAG com múltiplas etapas permanece planejado.",
  "fields": [
    {
      "key": "name",
      "label": "Nome"
    },
    {
      "key": "repository",
      "label": "Repositório ilustrativo"
    },
    {
      "key": "timeout",
      "label": "Limite (segundos)"
    }
  ],
  "records": [
    {
      "id": "build",
      "name": "Compilar checkout",
      "scope": "Comércio / Checkout",
      "state": "Rascunho",
      "values": {
        "name": "checkout-ci",
        "repository": "checkout-service",
        "timeout": "120"
      },
      "facts": [
        [
          "Etapas",
          "1 · executar script"
        ],
        [
          "Publicação",
          "Indisponível nesta demonstração"
        ]
      ]
    },
    {
      "id": "lint",
      "name": "Analisar catálogo",
      "scope": "Comércio / Catálogo",
      "state": "Rascunho",
      "values": {
        "name": "catalog-lint",
        "repository": "catalog-service",
        "timeout": "60"
      },
      "facts": [
        [
          "Etapas",
          "1 · executar script"
        ],
        [
          "Referências",
          "Não resolvidas"
        ]
      ]
    }
  ],
  "id": "pipelines"
}
