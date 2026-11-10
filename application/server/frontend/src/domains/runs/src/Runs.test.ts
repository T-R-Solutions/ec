import { afterEach, beforeEach, expect, test, vi } from 'vitest'
import Runs from './Runs.vue'
import type { Api } from '../../../shared/api'
import { all, button, deferred, event, mount, settle } from '../../../../test-support/renderer'

let cleanup: (() => void) | undefined
beforeEach(() => vi.useFakeTimers())
afterEach(() => { cleanup?.(); cleanup = undefined; vi.useRealTimers(); vi.unstubAllGlobals(); vi.restoreAllMocks() })
const run = { runId: 4, revisionId: 2, configHash: 'config', commit: 'commit', state: 'running',
  cancelRequested: false, result: null, attempts: [{ attemptId: 8, ordinal: 1, agentId: 'linux', state: 'running',
    resultReceipt: { output: { lastSequence: 51, truncated: true, artifactError: 'absent', signal: null } } }] }

function setup(overrides: Record<string, () => unknown> = {}, canWrite = true) {
  const api = vi.fn(async (path: string) => {
    if (overrides[path]) return overrides[path]()
    if (path.endsWith('/runs')) return { runs: [run] }
    if (path.endsWith('/cancel')) return { effective: true, state: 'running' }
    if (path.endsWith('/output')) return { logs: Array.from({ length: 51 }, (_, n) => ({ sequence: n + 1, stream: 'stdout', data: '6f6b' })), upload: null, parts: [] }
    throw new Error('rota inesperada: ' + path)
  })
  const wrapper = mount(Runs, { api: api as Api, project: 'ec', canWrite })
  cleanup = wrapper.unmount
  return { ...wrapper, api }
}

test('consulta, seleção, paginação e cancelamento mostram estado confirmado', async () => {
  const { root, api } = setup()
  await settle()
  expect(root.text).toContain('Atualizado pelo Server')
  expect(root.text).toContain('Revisão usada 2')
  await event(button(root, 'Execução #4 · running'), 'click')
  expect(root.text).toContain('Logs 1–50 de 51')
  expect(root.text).toContain('Truncado: sim')
  expect(root.text).toContain('artifactError: absent')
  expect(button(root, 'Logs anteriores').props.disabled).toBe(true)
  await event(button(root, 'Próximos logs'), 'click')
  expect(root.text).toContain('Logs 51–51 de 51')
  expect(button(root, 'Próximos logs').props.disabled).toBe(true)
  await event(button(root, 'Logs anteriores'), 'click')
  await event(button(root, 'Cancelar #4'), 'click')
  expect(root.text).toContain('Intenção persistida; aguardando confirmação terminal')
  expect(api.mock.calls.some(([p]) => p.endsWith('/4/cancel'))).toBe(true)
  await vi.advanceTimersByTimeAsync(2000)
  expect(api.mock.calls.filter(([p]) => p.endsWith('/runs')).length).toBeGreaterThan(1)
  cleanup!(); cleanup = undefined
  expect(vi.getTimerCount()).toBe(0)
})

test('dados vazios, consulta recusada e viewer têm estados honestos', async () => {
  const { root } = setup({ '/projects/ec/runs': () => ({ runs: [] }) })
  await settle(); expect(root.text).toContain('Nenhuma execução observada')
  cleanup!(); cleanup = undefined
  const viewer = setup({}, false)
  await settle()
  expect(button(viewer.root, 'Cancelar #4').props.disabled).toBe(true)
  cleanup!(); cleanup = undefined
  const rejected = setup({ '/projects/ec/runs': () => { throw new Error('403') } })
  await settle()
  expect(rejected.root.text).toContain('dados possivelmente desatualizados')
  expect(rejected.root.text).toContain('403')
})

test.each([
  [{ effective: true, state: 'cancelled' }, 'Cancelamento confirmado'],
  [{ effective: false, state: 'succeeded' }, 'Sem efeito: execução já terminal'],
])('cancelamento distingue confirmação e execução terminal', async (result, message) => {
  const { root } = setup({ '/projects/ec/runs/4/cancel': () => result })
  await settle(); await event(button(root, 'Cancelar #4'), 'click')
  expect(root.text).toContain(message)
})

test('falha de cancelamento mantém resultado incerto', async () => {
  const { root } = setup({ '/projects/ec/runs/4/cancel': () => { throw new Error('offline') } })
  await settle(); await event(button(root, 'Cancelar #4'), 'click')
  expect(root.text).toContain('Resultado incerto')
  expect(root.text).toContain('offline')
})

test('download só é liberado com recibo e integridade verificada', async () => {
  const clicked = vi.fn()
  vi.stubGlobal('document', { createElement: () => ({ click: clicked }) })
  const create = vi.spyOn(URL, 'createObjectURL').mockReturnValue('blob:test')
  const revoke = vi.spyOn(URL, 'revokeObjectURL').mockImplementation(() => {})
  // SHA-256 de "a", referência conhecida independente do verificador.
  const output = { logs: [], upload: { receipt: { attemptId: 8 }, size: 1,
    sha256: 'ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb' }, parts: [{ sequence: 1, data: '61' }] }
  const { root } = setup({ '/projects/ec/runs/4/attempts/8/output': () => output })
  await settle(); await event(button(root, 'Execução #4 · running'), 'click')
  expect(root.text).toContain('publicado íntegro no Server')
  await event(button(root, 'Baixar artefato verificado'), 'click')
  expect(root.text).toContain('Download verificado: tamanho e SHA-256')
  expect(clicked).toHaveBeenCalledTimes(1)
  expect(create).toHaveBeenCalledTimes(1)
  await vi.advanceTimersByTimeAsync(1000)
  expect(revoke).toHaveBeenCalledWith('blob:test')
  output.upload.sha256 = '0'.repeat(64)
  await event(button(root, 'Baixar artefato verificado'), 'click')
  expect(clicked).toHaveBeenCalledTimes(1)
  expect(root.text).not.toContain('Download verificado: tamanho e SHA-256')
})

test('desmontar aborta consulta pendente sem reagendar polling', async () => {
  const pending = deferred<unknown>()
  const api = vi.fn((_path: string, _body?: unknown, _signal?: AbortSignal) => pending.promise)
  const wrapper = mount(Runs, { api: api as Api, project: 'ec', canWrite: true })
  const signal = api.mock.calls[0][2]!
  wrapper.unmount()
  expect(signal.aborted).toBe(true)
  pending.resolve({ runs: [run] }); await settle()
  expect(vi.getTimerCount()).toBe(0)
  expect(all(wrapper.root, 'article')).toEqual([])
})
