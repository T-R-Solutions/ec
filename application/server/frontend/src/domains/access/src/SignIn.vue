<script setup lang="ts">
import { nextTick, onMounted, ref } from 'vue'
import { ApiError } from '../../../shared/api'
import { useSession } from './index'

defineProps<{ ended: boolean; warning?: string }>()
const session = useSession()
const token = ref('')
const pending = ref(false)
const error = ref('')
const field = ref<HTMLInputElement>()
onMounted(() => field.value?.focus())

async function enter() {
  if (pending.value) return
  if (!token.value.trim()) {
    error.value = 'Informe o token de sessão para continuar.'
    field.value?.focus()
    return
  }
  const credential = token.value
  token.value = ''
  error.value = ''
  pending.value = true
  try { await session.enter(credential) }
  catch (cause) {
    error.value = cause instanceof ApiError && [401, 403].includes(cause.status)
      ? 'Sessão recusada ou expirada. Solicite um token válido ao administrador e tente novamente.'
      : 'Não foi possível verificar a sessão. Confira a conexão e tente novamente.'
  } finally {
    pending.value = false
    await nextTick()
    field.value?.focus()
  }
}
</script>

<template>
  <section class="access-card" aria-labelledby="signin-title">
    <span class="brand" aria-label="Ec">Ec<span class="brand-dot" aria-hidden="true">.</span></span>
    <div class="access-heading">
      <p class="access-eyebrow">Seu ambiente de trabalho</p>
      <h1 id="signin-title">Entrar no Ec</h1>
      <p>Conecte-se para continuar.</p>
    </div>

    <form novalidate :aria-busy="pending" @submit.prevent="enter">
      <label for="session-token">Token de sessão</label>
      <input
        id="session-token"
        ref="field"
        v-model="token"
        name="session-token"
        type="password"
        autocomplete="off"
        autocapitalize="none"
        :spellcheck="false"
        required
        :disabled="pending"
        :aria-invalid="error ? 'true' : undefined"
        :aria-describedby="'session-help' + (error ? ' signin-error' : '')"
        @input="error = ''"
      />
      <p id="session-help" class="field-help">Use o token fornecido pelo administrador do seu Ec Server.</p>

      <div class="signin-feedback" aria-live="polite" aria-atomic="true">
        <p v-if="error" id="signin-error" class="feedback-error" role="alert">{{ error }}</p>
        <p v-else-if="pending" role="status">Verificando sessão…</p>
        <p v-else-if="warning" class="feedback-warning" role="status">{{ warning }}</p>
        <p v-else-if="ended" role="status">Sessão encerrada. Entre novamente para continuar.</p>
      </div>

      <button class="primary" type="submit" :disabled="pending">
        <span>{{ pending ? 'Verificando…' : 'Verificar sessão' }}</span>
        <span v-if="pending" class="loading-indicator" aria-hidden="true"></span>
        <span v-else class="button-arrow" aria-hidden="true">→</span>
      </button>
    </form>

    <p class="session-note"><svg viewBox="0 0 20 20" fill="none" aria-hidden="true"><path d="M6.5 8V5.5a3.5 3.5 0 0 1 7 0V8M5 8h10v9H5z" stroke="currentColor" stroke-width="1.5" stroke-linejoin="round"/></svg>A sessão fica somente na memória desta aba.</p>
  </section>
</template>
