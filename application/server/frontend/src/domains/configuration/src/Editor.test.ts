import { afterEach, beforeEach, expect, test, vi } from 'vitest'
import Editor from './Editor.vue'
import { ApiError, type Api } from '../../../shared/api'
import { useDrafts } from './drafts'
import { type Model, seed } from './model'
import { all, button, deferred, event, input, mount, settle } from '../../../../test-support/renderer'

const model: Model = { apiVersion: 'ec/v1alpha1', kind: 'ProjectPipeline', project: 'ec', pipeline: 'build',
  repository: '/tmp/source', commit: '0'.repeat(40), script: 'printf ok > result.txt', timeoutSeconds: '30', artifact: 'result.txt' }
let cleanup: (() => void) | undefined
beforeEach(() => {
  vi.useFakeTimers()
  vi.stubGlobal('Document', class {})
  vi.stubGlobal('ShadowRoot', class {})
})
afterEach(() => { cleanup?.(); cleanup = undefined; vi.useRealTimers(); vi.unstubAllGlobals() })

function setup(overrides: Record<string, (body?: any) => unknown> = {}, canWrite = true) {
  const api = vi.fn(async (path: string, body?: any) => {
    if (overrides[path]) return overrides[path](body)
    if (path === '/projects/ec') return { published: 2, effective: 1 }
    if (path === '/projects/ec/revisions/2') return { yaml: seed('ec') }
    if (path.endsWith('/validation')) return { valid: true, projection: model }
    if (path.endsWith('/publications')) return { revision: 3 }
    if (path.endsWith('/runs')) return { runId: 9 }
    throw new Error('Rota inesperada: ' + path)
  })
  const wrapper = mount(Editor, { api: api as Api, project: 'ec', canWrite })
  cleanup = wrapper.unmount
  return { ...wrapper, api }
}

async function validate() { await vi.advanceTimersByTimeAsync(200); await settle() }

test('debounce, projeção, revisão textual e carregamento preservam a decisão do usuário', async () => {
  const { root, api } = setup()
  expect(root.text).toContain('Validando no parser')
  await validate()
  expect(root.text).toContain('Semântica válida')
  await event(button(root, 'Formulário'), 'click')
  const field = all(root, 'input').find(n => n.props.value === 'build')!
  field.value = 'release'; await event(field, 'change')
  expect(root.text).toContain('Confirmar substituição textual')
  expect(button(root, 'Publicar rascunho').props.disabled).toBe(true)
  await event(button(root, 'Preservar texto atual'), 'click')
  expect(useDrafts().open('ec').yaml).toBe(seed('ec'))
  field.value = 'release'; await event(field, 'change')
  await event(button(root, 'Aplicar alteração'), 'click')
  expect(useDrafts().open('ec').yaml).toContain('"pipeline": "release"')
  await validate()
  await event(button(root, 'YAML'), 'click')
  await input(all(root, 'textarea')[0], 'invalid: [')
  await input(all(root, 'textarea')[0], seed('ec'))
  await validate()
  expect(api.mock.calls.filter(([p]) => p.endsWith('/validation')).at(-1)?.[1]).toEqual({ yaml: seed('ec') })
  await event(button(root, 'Carregar publicada (substitui rascunho)'), 'click')
  expect(root.text).toContain('Base 2')
  expect(useDrafts().open('ec').yaml).toBe(seed('ec'))
})

test('publicação confirma recibo e submissão usa revisão escolhida', async () => {
  const { root, api } = setup()
  await validate()
  await event(button(root, 'Publicar rascunho'), 'click')
  const published = api.mock.calls.find(([p]) => p.endsWith('/publications'))!
  expect(published[1]).toMatchObject({ baseRevision: 0, yaml: seed('ec') })
  expect(published[1].requestId).toMatch(/^[a-f0-9-]{36}$/)
  expect(root.text).toContain('Recibo confirmado pelo Server')
  expect(root.text).toContain('Base 3')
  const revision = all(root, 'input').find(n => n.props.type === 'number')!
  await input(revision, '1')
  await event(button(root, 'Submeter revisão escolhida'), 'click')
  expect(api.mock.calls.find(([p]) => p.endsWith('/runs'))?.[1]).toMatchObject({ revisionId: 1 })
  expect(useDrafts().open('ec').pending).toBeUndefined()
})

test('resposta incerta mantém bytes e requestId durante replay', async () => {
  let attempts = 0
  const { root, api } = setup({ '/projects/ec/publications': () => {
    if (++attempts === 1) throw new Error('conexão perdida')
    return { revision: 3 }
  } })
  await validate()
  await event(button(root, 'Publicar rascunho'), 'click')
  expect(root.text).toContain('Resposta incerta')
  const pending = JSON.stringify(useDrafts().open('ec').pending)
  await event(button(root, 'Repetir mesmo pedido'), 'click')
  const requests = api.mock.calls.filter(([p]) => p.endsWith('/publications'))
  expect(requests[0][1]).toEqual(requests[1][1])
  expect(pending).toContain(requests[1][1].requestId)
  expect(useDrafts().open('ec').pending).toBeUndefined()
})

test('recuperação de recibo retém pedido recusado e depois confirma publicação', async () => {
  const { root } = setup({ '/projects/ec/publications': () => { throw new ApiError(500, 'database') } })
  await validate(); await event(button(root, 'Publicar rascunho'), 'click')
  const request = useDrafts().open('ec').pending!
  const path = '/projects/ec/receipts/' + request.body.requestId
  // O primeiro erro mantém o pedido; recuperação posterior usa o mesmo identificador.
  const wrapperCleanup = cleanup!
  wrapperCleanup(); cleanup = undefined
  const api = vi.fn(async (p: string) => {
    if (p === path) return { revision: 3 }
    if (p === '/projects/ec') return { published: 3, effective: 3 }
    if (p === '/projects/ec/revisions/3') return { yaml: seed('ec') }
    if (p.endsWith('/validation')) return { valid: true, projection: model }
    throw new Error('recibo ausente')
  })
  // Mesmo workspace Pinia restaura o pedido ao remontar o componente.
  const { renderer, Node } = await import('../../../../test-support/renderer')
  const { createPinia } = await import('pinia')
  const pinia = createPinia()
  const app = renderer.createApp(Editor, { api: api as Api, project: 'ec', canWrite: true })
  app.use(pinia)
  useDrafts(pinia).open('ec').pending = request
  const next = new Node('root'); app.mount(next); cleanup = () => app.unmount()
  await settle()
  await event(button(next, 'Consultar recibo'), 'click')
  expect(api.mock.calls.some(([p]) => p === path)).toBe(true)
  expect(next.text).toContain('Recibo recuperado')
  expect(next.text).toContain('Base 3')
  expect(root.text).not.toContain('Recibo recuperado')
})

test('conflito exige adoção explícita da base; viewer não pode publicar ou executar', async () => {
  const { root } = setup({ '/projects/ec/publications': () => { throw new ApiError(409, 'base_conflict') } })
  await validate(); await event(button(root, 'Publicar rascunho'), 'click')
  expect(useDrafts().open('ec').pending).toBeUndefined()
  expect(button(root, 'Publicar rascunho').props.disabled).toBe(true)
  await event(button(root, 'Comparar e adotar base atual'), 'click')
  expect(root.text).toContain('Base atual adotada explicitamente')
  expect(useDrafts().open('ec').base).toBe(2)
  cleanup!(); cleanup = undefined
  const viewer = setup({}, false)
  await validate()
  expect(button(viewer.root, 'Publicar rascunho').props.disabled).toBe(true)
  expect(button(viewer.root, 'Submeter revisão escolhida').props.disabled).toBe(true)
})

test('validação tardia não substitui o texto atual e desmontagem aborta consultas', async () => {
  const late = deferred<unknown>()
  let count = 0
  const { root } = setup({ '/projects/ec/validation': () => ++count === 1 ? late.promise : { valid: false, diagnostic: { code: 'yaml', message: 'inválido' } } })
  await vi.advanceTimersByTimeAsync(200)
  await event(button(root, 'YAML'), 'click')
  await input(all(root, 'textarea')[0], 'invalid: [')
  await validate()
  late.resolve({ valid: true, projection: model }); await settle()
  expect(root.text).toContain('inválido')
  expect(button(root, 'Publicar rascunho').props.disabled).toBe(true)
  cleanup!(); cleanup = undefined
  expect(vi.getTimerCount()).toBe(0)
})
