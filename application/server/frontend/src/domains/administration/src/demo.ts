import type { DemoDefinition } from '../../../shared/ui/demo'
export const demo: DemoDefinition = {
  "title": "Políticas da instância",
  "persona": "Administrador de plataforma",
  "task": "Revisar escopo e consequência da retenção",
  "permission": "Administração da instância",
  "consequence": "Retenção real poderia remover evidências. A proposta não agenda nem remove dados.",
  "limitation": "Políticas e eventos de auditoria são exemplos locais; nenhuma configuração global é alterada.",
  "fields": [
    {
      "key": "policy",
      "label": "Política"
    },
    {
      "key": "days",
      "label": "Retenção proposta (dias)"
    },
    {
      "key": "scope",
      "label": "Escopo"
    }
  ],
  "records": [
    {
      "id": "artifacts",
      "name": "Retenção de artefatos",
      "scope": "Instância",
      "state": "Proposta",
      "values": {
        "policy": "Artefatos",
        "days": "30",
        "scope": "Projetos de exemplo"
      },
      "facts": [
        [
          "Origem",
          "Fixture visual"
        ],
        [
          "Aplicação",
          "Não implementada nesta interface"
        ]
      ]
    },
    {
      "id": "audit",
      "name": "Retenção de auditoria",
      "scope": "Instância",
      "state": "Revisão necessária",
      "values": {
        "policy": "Auditoria",
        "days": "90",
        "scope": "Instância de exemplo"
      },
      "facts": [
        [
          "Origem",
          "Fixture visual"
        ],
        [
          "Exclusão",
          "Nenhuma exclusão executada"
        ]
      ]
    }
  ],
  "id": "administration"
}
