import { test, expect } from 'vitest'
import { createPinia, setActivePinia } from 'pinia'
import { watch, nextTick, toRefs } from 'vue'
import { useDrafts } from './drafts'
test('primeiro rascunho é reativo, preservado por projeto e descartado ao trocar sessão',async()=>{
 setActivePinia(createPinia());const store=useDrafts();const {yaml}=toRefs(store.open('ec'))
 let observed='';const stop=watch(yaml,v=>observed=v)
 yaml.value='invalid: [';await nextTick();expect(observed).toBe('invalid: [')
 expect(store.open('ec').yaml).toBe('invalid: [');expect(store.open('other').yaml).not.toBe('invalid: [')
 store.clear();expect(store.open('ec').yaml).not.toBe('invalid: [');stop()
})
