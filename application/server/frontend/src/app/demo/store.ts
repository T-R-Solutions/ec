import { defineStore } from 'pinia'
import { reactive } from 'vue'
import type { DemoDraft } from '../../shared/ui/demo'
export const useDemoWorkspace = defineStore('visual-demo', () => {
  const drafts = reactive<Record<string, DemoDraft>>({})
  function open(key: string, values: Record<string, string>) {
    if (!drafts[key]) {
      drafts[key] = { values: { ...values }, stage: 'detail', mode: 'Visual', retained: false }
    }
    return drafts[key]
  }
  return { drafts, open }
})
