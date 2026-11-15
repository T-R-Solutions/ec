import { afterEach, beforeEach, expect, test, vi } from 'vitest'
import App from './App.vue'
import { all, button, deferred, event, input, mount, settle } from '../../test-support/renderer'

let cleanup: (() => void) | undefined
beforeEach(() => {
  vi.useFakeTimers()
  vi.stubGlobal('Document', class {})
  vi.stubGlobal('ShadowRoot', class {})
  vi.stubGlobal('window', new EventTarget())
})
afterEach(() => { cleanup?.(); cleanup = undefined; vi.useRealTimers(); vi.unstubAllGlobals() })

const response = (value: unknown, status = 200) => ({ ok: status < 400, status, json: async () => value })
function setup(fetch = vi.fn().mockResolvedValue(response({ identity: 'viewer', expires: '2030-01-01' }))) {
  vi.stubGlobal('fetch', fetch)
  const wrapper = mount(App)
  cleanup = wrapper.unmount
  return { ...wrapper, fetch }
}
const field = (root: ReturnType<typeof mount>['root']) => all(root, 'input')[0]
async function enter(root: ReturnType<typeof mount>['root'], credential = 'private-token') {
  await input(field(root), credential)
  await event(all(root, 'form')[0], 'submit')
}

test('entrada expõe apenas login e confirma sessão retornada pela API, com revogação', async () => {
  const { root, fetch } = setup()
  expect(root.text).toContain('Entrar no Ec')
  expect(all(root, 'nav')).toHaveLength(0)
  expect(root.text).not.toContain('demonstração')
  expect(root.text).not.toContain('Galeria')
  expect(fetch).not.toHaveBeenCalled()
  await enter(root)
  expect(root.text).toContain('Sessão verificada')
  expect(root.text).toContain('viewer')
  expect(root.text).toContain('2030-01-01')
  expect(all(root, 'input')).toHaveLength(0)
  expect(fetch).toHaveBeenCalledWith('/api/v1/session', expect.objectContaining({ method: 'GET', headers: expect.objectContaining({ Authorization: 'Bearer private-token' }) }))
  await event(button(root, 'Revogar e sair'), 'click')
  expect(root.text).toContain('Entrar no Ec')
  expect(root.text).toContain('Sessão encerrada')
  expect(root.text).not.toContain('viewer')
  expect(fetch).toHaveBeenCalledWith('/api/v1/session', expect.objectContaining({ method: 'DELETE' }))
})

test('token vazio ou apenas espaços não consulta API e orienta preenchimento', async () => {
  const { root, fetch } = setup()
  await enter(root, '   ')
  expect(root.text).toContain('Informe o token')
  expect(field(root).props['aria-invalid']).toBe('true')
  expect(field(root).focused).toBe(true)
  expect(fetch).not.toHaveBeenCalled()
  await input(field(root), 'nova-sessao')
  expect(root.text).not.toContain('Informe o token')
})

test.each([401, 403])('sessão recusada (%i) conserva tela pública e limpa credencial', async status => {
  const { root } = setup(vi.fn().mockResolvedValue(response({ code: 'invalid_session' }, status)))
  await enter(root)
  expect(root.text).toContain('Sessão recusada ou expirada')
  expect(root.text).not.toContain('Sessão verificada')
  expect(field(root).value).toBe('')
  expect(field(root).props['aria-describedby']).toContain('signin-error')
})

test('erro de conexão permite nova tentativa sem guardar token', async () => {
  const fetch = vi.fn().mockRejectedValueOnce(new Error('offline')).mockResolvedValue(response({ identity: 'viewer', expires: '2030-01-01' }))
  const { root } = setup(fetch)
  await enter(root)
  expect(root.text).toContain('Confira a conexão')
  expect(field(root).value).toBe('')
  await enter(root, 'outro-token')
  expect(root.text).toContain('Sessão verificada')
})

test('envio pendente bloqueia duplicação até a confirmação', async () => {
  const pending = deferred<ReturnType<typeof response>>()
  const fetch = vi.fn().mockReturnValue(pending.promise)
  const { root } = setup(fetch)
  await input(field(root), 'private-token')
  const form = all(root, 'form')[0]
  const first = event(form, 'submit')
  await settle()
  expect(field(root).value).toBe('')
  expect(field(root).props.disabled).toBe(true)
  expect(root.text).toContain('Verificando')
  await event(form, 'submit')
  expect(fetch).toHaveBeenCalledTimes(1)
  pending.resolve(response({ identity: 'viewer', expires: '2030-01-01' }))
  await first
  expect(root.text).toContain('Sessão verificada')
})

test('falha de revogação encerra acesso local e comunica confirmação remota ausente', async () => {
  const fetch = vi.fn().mockResolvedValueOnce(response({ identity: 'viewer', expires: '2030-01-01' })).mockRejectedValue(new Error('offline'))
  const { root } = setup(fetch)
  await enter(root)
  await event(button(root, 'Revogar e sair'), 'click')
  expect(root.text).toContain('não foi possível confirmar a revogação')
  expect(root.text).toContain('Entrar no Ec')
  expect(root.text).not.toContain('viewer')
})

test('revalida a cada 30s sem sobreposição; 401 retorna à entrada e encerra os timers', async () => {
  const pending = deferred<ReturnType<typeof response>>()
  const fetch = vi.fn().mockResolvedValueOnce(response({ identity: 'viewer', expires: '2030-01-01' })).mockReturnValue(pending.promise)
  const { root } = setup(fetch)
  await enter(root)
  await vi.advanceTimersByTimeAsync(30_000)
  expect(root.text).toContain('Verificando sessão')
  expect(root.text).not.toContain('Você está conectado')
  window.dispatchEvent(new Event('focus'))
  await vi.advanceTimersByTimeAsync(30_000)
  expect(fetch).toHaveBeenCalledTimes(2)
  pending.resolve(response({ code: 'invalid_session' }, 401))
  await settle()
  expect(root.text).toContain('Entrar no Ec')
  expect(root.text).not.toContain('viewer')
  expect(vi.getTimerCount()).toBe(0)
  window.dispatchEvent(new Event('focus'))
  await settle()
  expect(fetch).toHaveBeenCalledTimes(2)
})

test('erro de transporte informa confirmação desatualizada e retomar foco tenta novamente', async () => {
  const fetch = vi.fn()
    .mockResolvedValueOnce(response({ identity: 'viewer', expires: '2030-01-01' }))
    .mockRejectedValueOnce(new Error('offline'))
    .mockResolvedValue(response({ identity: 'identidade confirmada novamente', expires: '2031-01-01' }))
  const { root } = setup(fetch)
  await enter(root)
  await vi.advanceTimersByTimeAsync(30_000)
  expect(root.text).toContain('Sessão sem confirmação')
  expect(root.text).toContain('Última identidade confirmada')
  expect(root.text).not.toContain('Você está conectado')
  expect(root.text).not.toContain('Acesso confirmado')
  window.dispatchEvent(new Event('focus'))
  await settle()
  expect(root.text).toContain('Sessão verificada')
  expect(root.text).toContain('identidade confirmada novamente')
  expect(root.text).toContain('2031-01-01')
})

test('expiração informada pelo servidor encerra sessão mesmo com verificação pendente', async () => {
  const fetch = vi.fn().mockResolvedValueOnce(response({ identity: 'viewer', expires: new Date(Date.now() + 1_000).toISOString() }))
    .mockReturnValue(deferred().promise)
  const { root } = setup(fetch)
  await enter(root)
  window.dispatchEvent(new Event('focus'))
  await settle()
  expect(root.text).toContain('Verificando sessão')
  await vi.advanceTimersByTimeAsync(1_000)
  expect(root.text).toContain('Entrar no Ec')
  expect(root.text).not.toContain('viewer')
  expect(vi.getTimerCount()).toBe(0)
})

test('desmontagem remove listener e timers e aborta consulta em andamento', async () => {
  const remove = vi.spyOn(window, 'removeEventListener')
  const pending = deferred<ReturnType<typeof response>>()
  const fetch = vi.fn().mockResolvedValueOnce(response({ identity: 'viewer', expires: '2030-01-01' })).mockReturnValue(pending.promise)
  const { root } = setup(fetch)
  await enter(root)
  window.dispatchEvent(new Event('focus'))
  await settle()
  const signal = fetch.mock.calls[1][1].signal as AbortSignal
  expect(signal.aborted).toBe(false)
  cleanup!(); cleanup = undefined
  expect(signal.aborted).toBe(true)
  expect(remove).toHaveBeenCalledWith('focus', expect.any(Function))
  expect(vi.getTimerCount()).toBe(0)
  window.dispatchEvent(new Event('focus'))
  pending.resolve(response({ identity: 'resposta tardia', expires: '2031-01-01' }))
  await settle()
  expect(fetch).toHaveBeenCalledTimes(2)
})
