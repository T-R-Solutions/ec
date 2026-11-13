import type { DemoDefinition } from '../../../shared/ui/demo'
export const demo: DemoDefinition = {
  "title": "Pools e elegibilidade",
  "persona": "Operador de plataforma",
  "task": "Inspecionar requisitos e propor agrupamento",
  "permission": "Administrar infraestrutura do projeto",
  "consequence": "Mudar requisitos poderia impedir alocação. Nenhuma capacidade, slot ou heartbeat é medido aqui.",
  "limitation": "Pools são demonstrativos; agentes reais permanecem no modo operacional.",
  "fields": [
    {
      "key": "pool",
      "label": "Pool"
    },
    {
      "key": "platform",
      "label": "Plataforma",
      "options": [
        "Linux",
        "Windows"
      ]
    },
    {
      "key": "requirement",
      "label": "Requisito"
    }
  ],
  "records": [
    {
      "id": "linux",
      "name": "Linux • compilação",
      "scope": "Comércio",
      "state": "Capacidade desconhecida",
      "values": {
        "pool": "linux-build",
        "platform": "Linux",
        "requirement": "git, shell"
      },
      "facts": [
        [
          "Agentes ilustrativos",
          "runner-demo-01"
        ],
        [
          "Capacidade disponível",
          "Não medida"
        ]
      ]
    },
    {
      "id": "windows",
      "name": "Windows • testes",
      "scope": "Comércio",
      "state": "Sem correspondência na fixture",
      "values": {
        "pool": "windows-test",
        "platform": "Windows",
        "requirement": "powershell"
      },
      "facts": [
        [
          "Elegibilidade",
          "A fixture contém apenas runner Linux"
        ],
        [
          "Slots",
          "Desconhecidos"
        ]
      ]
    }
  ],
  "id": "infrastructure"
}
