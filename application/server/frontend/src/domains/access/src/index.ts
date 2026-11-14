import { defineStore } from 'pinia'
import { ref, shallowRef } from 'vue'
import { client, type Api } from '../../../shared/api'
export const useSession = defineStore('session', () => {
  const api = shallowRef<Api>(); const identity = ref(''); const expires = ref(''); const generation = ref(0)
  function clear() { api.value = undefined; identity.value = ''; expires.value = ''; generation.value++ }
  async function enter(token: string) {
    clear()
    const n=generation.value
    const candidate = client(token, ()=>{if(generation.value===n)clear()})
    const s = await candidate<{identity:string;expires:string}>('/session')
    if(generation.value!==n)return
    identity.value = s.identity; expires.value = s.expires; api.value = candidate
  }
  async function leave() { try { await api.value?.('/session', undefined, undefined, 'DELETE') } finally { clear() } }
  return { api, identity, expires, generation, enter, leave, clear }
})
export { default as AccessDemo } from './Demo.vue'
