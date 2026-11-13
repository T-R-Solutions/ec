import type { DemoDefinition } from '../../../shared/ui/demo'
export const demo: DemoDefinition = {
  "title": "Investigação de execuções",
  "persona": "Desenvolvedor / operador",
  "task": "Inspecionar resultado e completude da saída",
  "permission": "Leitura no projeto; escrita para cancelar",
  "consequence": "Anotações locais não cancelam, repetem ou alteram uma execução. Logs e artefatos reais estão no modo operacional.",
  "limitation": "Resultados sintéticos sem bytes de logs ou artefatos inventados.",
  "fields": [
    {
      "key": "run",
      "label": "Execução ilustrativa"
    },
    {
      "key": "note",
      "label": "Anotação de investigação"
    }
  ],
  "records": [
    {
      "id": "run-42",
      "name": "#42 · Checkout",
      "scope": "Comércio / Checkout",
      "state": "Falha do processo",
      "values": {
        "run": "demo-42",
        "note": "Investigar saída parcial"
      },
      "facts": [
        [
          "Revisão",
          "demo-r7 · sem hash real"
        ],
        [
          "Saída",
          "Parcial · artefato ausente"
        ],
        [
          "Causa",
          "Não determinada"
        ]
      ]
    },
    {
      "id": "run-43",
      "name": "#43 · Catálogo",
      "scope": "Comércio / Catálogo",
      "state": "Cancelamento solicitado",
      "values": {
        "run": "demo-43",
        "note": "Aguardar confirmação terminal"
      },
      "facts": [
        [
          "Revisão",
          "demo-r3 · sem hash real"
        ],
        [
          "Estado terminal",
          "Desconhecido"
        ],
        [
          "Rollback",
          "Não comprovado"
        ]
      ]
    }
  ],
  "id": "runs"
}
