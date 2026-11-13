import type { DemoDefinition } from '../../../shared/ui/demo'
export const demo: DemoDefinition = {
  "title": "Ambientes e promoção",
  "persona": "Operador de release / aprovador",
  "task": "Revisar destino, artefato e gate de aprovação",
  "permission": "Promover no ambiente de destino",
  "consequence": "Uma promoção real afetaria o destino e exigiria evidência do artefato e autorização. Não há deployment nem rollback.",
  "limitation": "Ambientes e linha do tempo são fixtures; aprovação não é concedida.",
  "fields": [
    {
      "key": "environment",
      "label": "Destino",
      "options": [
        "Homologação",
        "Produção"
      ]
    },
    {
      "key": "artifact",
      "label": "Artefato proposto"
    },
    {
      "key": "gate",
      "label": "Gate",
      "options": [
        "Revisão manual",
        "Aguardar evidência"
      ]
    }
  ],
  "records": [
    {
      "id": "staging",
      "name": "Homologação",
      "scope": "Checkout",
      "state": "Aguardando evidência",
      "values": {
        "environment": "Homologação",
        "artifact": "checkout-demo.tar",
        "gate": "Aguardar evidência"
      },
      "facts": [
        [
          "Integridade",
          "Não verificada · artefato ilustrativo"
        ],
        [
          "Solicitante",
          "Rui · pessoa fictícia"
        ]
      ]
    },
    {
      "id": "production",
      "name": "Produção",
      "scope": "Checkout",
      "state": "Bloqueado por gate",
      "values": {
        "environment": "Produção",
        "artifact": "checkout-demo.tar",
        "gate": "Revisão manual"
      },
      "facts": [
        [
          "Aprovação",
          "Pendente na fixture"
        ],
        [
          "Rollback",
          "Não demonstrado"
        ]
      ]
    }
  ],
  "id": "delivery"
}
