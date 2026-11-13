import { afterEach, beforeEach, expect, test, vi } from 'vitest'
import App from './App.vue'
import { all, button, event, input, mount, settle } from '../../test-support/renderer'

let cleanup: (() => void) | undefined
beforeEach(() => {
  vi.useFakeTimers()
  vi.stubGlobal('Document', class {})
  vi.stubGlobal('ShadowRoot', class {})
  vi.stubGlobal('document', { documentElement: { dataset: {} } })
})
afterEach(() => { cleanup?.(); cleanup = undefined; vi.useRealTimers(); vi.unstubAllGlobals() })

function setup(failed = false) {
  const fetch = vi.fn(async (url: string, request: RequestInit) => {
    if (failed) throw new Error('sem conexão')
    let value: unknown
    if (url === '/api/v1/session' && request.method === 'DELETE') value = { revoked: true }
    else if (url === '/api/v1/session') value = { identity: 'viewer', expires: '2030-01-01' }
    else if (url === '/api/v1/projects') value = { projects: [{ project: 'ec', role: 'viewer' }] }
    else if (url.endsWith('/agents')) value = { agents: [{ name: 'linux', revoked: false, sessionValid: true }] }
    else if (url.endsWith('/runs')) value = { runs: [] }
    else if (url === '/api/v1/projects/ec') value = { published: 0, effective: 0 }
    else if (url.endsWith('/validation')) value = { valid: false }
    else throw new Error('rota inesperada: ' + url)
    return { ok: true, json: async () => value }
  })
  vi.stubGlobal('fetch', fetch)
  const wrapper = mount(App)
  cleanup = wrapper.unmount
  return { ...wrapper, fetch }
}

async function enter(root: ReturnType<typeof mount>['root']) {
  const password = all(root, 'input').find(n => n.props.type === 'password')!
  await input(password, 'private-token')
  await event(all(root, 'form')[0], 'submit')
  expect(password.value).toBe('')
}

test('login, contexto do projeto, navegação, tema e logout refletem sessão real do store', async () => {
  const { root, fetch } = setup()
  expect(root.text).toContain('Entrar com sessão provisionada')
  await enter(root)
  expect(root.text).toContain('Sessão verificada')
  expect(root.text).toContain('viewer')
  const selects = all(root, 'select')
  await input(selects[0], 'dark')
  expect(document.documentElement.dataset.theme).toBe('dark')
  await input(selects[1], 'ec')
  expect(root.text).toContain('Configuração do pipeline')
  await event(button(root, 'Agentes'), 'click')
  expect(root.text).toContain('Agentes do projeto')
  expect(root.text).toContain('Sessão válida')
  await event(button(root, 'Execuções'), 'click')
  expect(root.text).toContain('Nenhuma execução observada')
  await event(button(root, 'Revogar e sair'), 'click')
  expect(root.text).toContain('Entrar com sessão provisionada')
  expect(root.text).not.toContain('2030-01-01')
  expect(fetch.mock.calls.some(([p, r]) => p === '/api/v1/session' && r.method === 'DELETE')).toBe(true)
})

test('login recusado não apresenta uma sessão nem conserva o token no campo', async () => {
  const { root } = setup(true)
  await enter(root); await settle()
  expect(root.text).toContain('sem conexão')
  expect(root.text).toContain('Entrar com sessão provisionada')
  expect(all(root, 'input').find(n => n.props.type === 'password')?.value).toBe('')
})

test('demonstração sem sessão preserva proposta entre áreas e não chama API', async () => {
  const { root, fetch } = setup()
  await event(button(root, 'Explorar demonstração'), 'click')
  await event(button(root, 'Pessoas e acesso'), 'click')
  await event(button(root, 'Editar proposta local'), 'click')
  const team = all(root, 'input').find(n => n.value === 'Revisores')!
  await input(team, 'Revisores locais')
  await event(all(root, 'form')[0], 'submit')
  expect(root.text).toContain('Revisores locais')
  expect(root.text).toContain('Proposta · não efetiva')
  await event(button(root, 'Reter proposta nesta aba'), 'click')
  await event(button(root, 'Projetos'), 'click')
  await event(button(root, 'Pessoas e acesso'), 'click')
  const retained = all(root, 'input').filter(n => n.value === 'Revisores locais')
  expect(retained).toHaveLength(1)
  expect(retained[0].props.readonly).toBe(true)
  expect(root.text).toContain('Não publicada nem aplicada')
  expect(fetch).not.toHaveBeenCalled()
})
