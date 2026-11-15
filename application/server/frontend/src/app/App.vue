<script setup lang="ts">
import { nextTick, onBeforeUnmount, onMounted, ref, watch } from 'vue'
import { SignIn, useSession } from '../domains/access/src'

const session = useSession()
const ended = ref(false)
const leaving = ref(false)
const warning = ref('')
const confirmation = ref<HTMLElement>()
const verification = ref<'current' | 'checking' | 'stale'>('current')
let requestCheck: (() => Promise<void>) | undefined
const onFocus = () => { void requestCheck?.() }
onMounted(() => { if (typeof window !== 'undefined') window.addEventListener('focus', onFocus) })
onBeforeUnmount(() => { if (typeof window !== 'undefined') window.removeEventListener('focus', onFocus) })

watch(() => session.api, async (api, previous, cleanup) => {
  if (api) {
    warning.value = ''
    verification.value = 'current'
    const generation = session.generation
    let current = true
    let check: AbortController | undefined
    let expiry: ReturnType<typeof setTimeout> | undefined
    function armExpiry() {
      clearTimeout(expiry)
      const remaining = Date.parse(session.expires) - Date.now()
      if (!Number.isFinite(remaining)) return
      if (remaining <= 0) { session.clear(); return }
      // Timers JavaScript aceitam no máximo um inteiro assinado de 32 bits.
      expiry = setTimeout(armExpiry, Math.min(remaining, 2_147_483_647))
    }
    async function refresh() {
      if (!current || check || leaving.value) return
      const controller = new AbortController()
      check = controller
      verification.value = 'checking'
      try {
        await session.refresh(controller.signal)
        if (current && session.generation === generation) {
          verification.value = 'current'
          armExpiry()
        }
      } catch {
        if (current && session.generation === generation) verification.value = 'stale'
      } finally { if (check === controller) check = undefined }
    }
    requestCheck = refresh
    const poll = setInterval(() => { void refresh() }, 30_000)
    cleanup(() => {
      current = false
      clearInterval(poll)
      clearTimeout(expiry)
      check?.abort()
      if (requestCheck === refresh) requestCheck = undefined
    })
    armExpiry()
    await nextTick()
    if (current) confirmation.value?.focus()
  } else if (previous) ended.value = true
}, { flush: 'post' })

async function leave() {
  if (leaving.value) return
  leaving.value = true
  try { await session.leave() }
  catch {
    warning.value = 'O acesso nesta aba foi encerrado, mas não foi possível confirmar a revogação no servidor.'
  } finally { leaving.value = false }
}
</script>

<template>
  <div class="access-page">
    <a class="skip" href="#access-content">Ir ao conteúdo</a>
    <main id="access-content" class="access-main" tabindex="-1">
      <SignIn v-if="!session.api" :ended="ended" :warning="warning" />
      <section v-else class="access-card session-confirmation" aria-labelledby="session-title">
        <span class="brand" aria-label="Ec">Ec<span class="brand-dot" aria-hidden="true">.</span></span>
        <div class="access-heading">
          <span v-if="verification === 'current'" class="session-badge"><span aria-hidden="true">✓</span> Acesso confirmado</span>
          <span v-else class="session-badge session-unconfirmed">{{ verification === 'checking' ? 'Verificando acesso' : 'Confirmação desatualizada' }}</span>
          <h1 id="session-title" ref="confirmation" tabindex="-1">{{ verification === 'current' ? 'Sessão verificada' : verification === 'checking' ? 'Verificando sessão' : 'Sessão sem confirmação' }}</h1>
          <p v-if="verification === 'current'">Você está conectado ao Ec Server.</p>
          <p v-else role="status">{{ verification === 'checking' ? 'Confirmando a validade da sessão com o Ec Server.' : 'Não foi possível confirmar a sessão. Uma nova verificação será feita automaticamente.' }}</p>
        </div>
        <dl class="session-details">
          <div><dt>{{ verification === 'current' ? 'Identidade' : 'Última identidade confirmada' }}</dt><dd>{{ session.identity }}</dd></div>
          <div><dt>{{ verification === 'current' ? 'Sessão válida até' : 'Validade informada pelo servidor' }}</dt><dd>{{ session.expires }}</dd></div>
        </dl>
        <p class="session-next">As demais telas da aplicação serão disponibilizadas nas próximas etapas.</p>
        <button type="button" class="primary" :disabled="leaving" @click="leave">{{ leaving ? 'Encerrando…' : 'Revogar e sair' }}</button>
      </section>
    </main>

    <footer class="access-footer"><span>Ec Server</span><span aria-hidden="true">·</span><span>Precisão em cada entrega.</span></footer>
  </div>
</template>
