/** Contrato de apresentação de fixtures; não é um schema de recurso Ec. */
export interface DemoRecord {
  id: string
  name: string
  scope: string
  state: string
  facts: [string, string][]
  values: Record<string, string>
}
export interface DemoDefinition {
  id: string
  title: string
  persona: string
  task: string
  permission: string
  consequence: string
  limitation: string
  fields: { key: string; label: string; options?: string[] }[]
  records: DemoRecord[]
}

export interface DemoDraft { values: Record<string, string>; stage: 'detail' | 'edit' | 'review'; mode: 'Visual' | 'Formulário' | 'YAML'; retained: boolean }
