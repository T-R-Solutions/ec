import { expect, test } from 'vitest'
import { createPinia, setActivePinia } from 'pinia'
import { isReactive, nextTick, watch } from 'vue'
import { useDemoWorkspace } from './store'

test('primeira abertura é reativa e reabrir conserva a proposta', async () => {
  setActivePinia(createPinia())
  const store = useDemoWorkspace()
  const fixture = { team: 'Revisores' }
  const draft = store.open('access/reviewers', fixture)
  expect(isReactive(draft)).toBe(true)
  let observed = ''
  const stop = watch(() => `${draft.mode}/${draft.stage}/${draft.values.team}`, value => { observed = value })
  try {
    draft.mode = 'Formulário'
    draft.stage = 'edit'
    draft.values.team = 'Revisores locais'
    await nextTick()
    expect(observed).toBe('Formulário/edit/Revisores locais')
    expect(store.open('access/reviewers', fixture)).toBe(draft)
    expect(store.open('access/builders', { team: 'Engenharia' }).values.team).toBe('Engenharia')
    expect(fixture.team).toBe('Revisores')
  } finally {
    stop()
  }
})
