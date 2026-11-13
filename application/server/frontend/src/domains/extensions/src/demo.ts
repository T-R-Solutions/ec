import type { DemoDefinition } from '../../../shared/ui/demo'
export const demo: DemoDefinition = {
  "title": "Catálogo e conexões",
  "persona": "Responsável por integração",
  "task": "Avaliar origem, versão e permissões antes de propor ativação",
  "permission": "Gestão de integrações no escopo",
  "consequence": "Ativação exigiria compatibilidade, verificação de origem e isolamento. Nenhum plugin é instalado ou executado.",
  "limitation": "Catálogo fictício; não há runtime de plugins ou conector externo conectado.",
  "fields": [
    {
      "key": "name",
      "label": "Integração"
    },
    {
      "key": "version",
      "label": "Versão fixada"
    },
    {
      "key": "scope",
      "label": "Escopo"
    },
    {
      "key": "desired",
      "label": "Estado desejado",
      "options": [
        "Desativado",
        "Propor ativação"
      ]
    }
  ],
  "records": [
    {
      "id": "git",
      "name": "Conector Git",
      "scope": "Comércio",
      "state": "Não conectado",
      "values": {
        "name": "Git de exemplo",
        "version": "1.0.0-demo",
        "scope": "Comércio",
        "desired": "Desativado"
      },
      "facts": [
        [
          "Tipo",
          "Integração Git"
        ],
        [
          "Origem",
          "Fornecedor demonstrativo"
        ],
        [
          "Confiança",
          "Não verificada"
        ],
        [
          "Permissão solicitada",
          "Ler revisões do repositório"
        ]
      ]
    },
    {
      "id": "notifier",
      "name": "Notificador",
      "scope": "Instância",
      "state": "Não instalado",
      "values": {
        "name": "Notificador de exemplo",
        "version": "0.2.0-demo",
        "scope": "Comércio",
        "desired": "Desativado"
      },
      "facts": [
        [
          "Tipo",
          "Plugin"
        ],
        [
          "Origem",
          "Catálogo demonstrativo"
        ],
        [
          "Compatibilidade",
          "Não avaliada"
        ],
        [
          "Permissão solicitada",
          "Enviar notificações; sem credencial configurada"
        ]
      ]
    }
  ],
  "id": "extensions"
}
