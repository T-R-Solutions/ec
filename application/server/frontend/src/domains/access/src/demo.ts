import type { DemoDefinition } from '../../../shared/ui/demo'
export const demo: DemoDefinition = {
  "title": "Vínculos de equipes",
  "persona": "Administrador / auditor",
  "task": "Inspecionar origem do acesso e propor um vínculo",
  "permission": "Administração de acesso no escopo",
  "consequence": "Alterar um papel poderia conceder ou retirar capacidades. Nenhuma identidade, sessão ou permissão é criada.",
  "limitation": "Matriz e vínculos são fictícios, sem cálculo de autorização efetiva.",
  "fields": [
    {
      "key": "team",
      "label": "Equipe"
    },
    {
      "key": "scope",
      "label": "Escopo"
    },
    {
      "key": "role",
      "label": "Papel",
      "options": [
        "viewer",
        "editor",
        "admin"
      ]
    }
  ],
  "records": [
    {
      "id": "reviewers",
      "name": "Revisores",
      "scope": "Comércio / Checkout",
      "state": "Herdado",
      "values": {
        "team": "Revisores",
        "scope": "Comércio",
        "role": "viewer"
      },
      "facts": [
        [
          "Origem",
          "Vínculo no pai Comércio"
        ],
        [
          "Identidade federada",
          "Não integrada"
        ]
      ]
    },
    {
      "id": "builders",
      "name": "Engenharia",
      "scope": "Comércio / Checkout",
      "state": "Direto",
      "values": {
        "team": "Engenharia",
        "scope": "Comércio / Checkout",
        "role": "editor"
      },
      "facts": [
        [
          "Origem",
          "Vínculo direto no projeto"
        ],
        [
          "Membros",
          "Ana e Rui · pessoas fictícias"
        ]
      ]
    }
  ],
  "id": "access"
}
