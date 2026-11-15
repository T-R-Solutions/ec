import { afterEach, beforeEach, expect, test, vi } from 'vitest'
import { createPinia, setActivePinia } from 'pinia'
import { useSession } from './index'
import { deferred } from '../../../../test-support/renderer'

beforeEach(() => setActivePinia(createPinia()))
afterEach(() => vi.unstubAllGlobals())
const response = (identity: string) => ({ ok: true, json: async () => ({ identity, expires: '2030-01-01' }) })

test('resposta de login anterior não restaura identidade após troca ou limpeza', async () => {
  const old = deferred<ReturnType<typeof response>>()
  const fetch = vi.fn().mockReturnValueOnce(old.promise).mockResolvedValueOnce(response('atual'))
  vi.stubGlobal('fetch', fetch)
  const session = useSession()
  const previous = session.enter('old')
  await session.enter('new')
  old.resolve(response('antiga')); await previous
  expect(session.identity).toBe('atual')
  expect(session.expires).toBe('2030-01-01')
  expect(session.api).toBeTypeOf('function')
  session.clear()
  expect(session.identity).toBe('')
  expect(session.expires).toBe('')
  expect(session.api).toBeUndefined()
})

test('401 de cliente antigo não limpa sessão nova; logout limpa mesmo com erro', async () => {
  const fetch = vi.fn().mockResolvedValue(response('primeira'))
  vi.stubGlobal('fetch', fetch)
  const session = useSession()
  await session.enter('first')
  const previous = session.api!
  const pending = deferred<any>()
  fetch.mockReturnValueOnce(pending.promise)
  const oldCall = previous('/projects').catch(e => e)
  await session.enter('second')
  pending.resolve({ ok: false, status: 401, json: async () => ({ code: 'session' }) })
  await oldCall
  expect(session.api).toBeDefined()
  fetch.mockRejectedValueOnce(new Error('offline'))
  await expect(session.leave()).rejects.toThrow('offline')
  expect(session.api).toBeUndefined()
  await session.leave()
})

test('revalidação tardia não substitui uma sessão nova nem restaura sessão limpa', async () => {
  const late = deferred<ReturnType<typeof response>>()
  const fetch = vi.fn().mockResolvedValueOnce(response('primeira')).mockReturnValueOnce(late.promise).mockResolvedValueOnce(response('atual'))
  vi.stubGlobal('fetch', fetch)
  const session = useSession()
  await session.enter('first')
  const checking = session.refresh()
  await session.enter('new')
  late.resolve(response('antiga')); await checking
  expect(session.identity).toBe('atual')
  const cleared = deferred<ReturnType<typeof response>>()
  fetch.mockReturnValueOnce(cleared.promise)
  const pending = session.refresh()
  session.clear()
  cleared.resolve(response('revogada')); await pending
  expect(session.identity).toBe('')
  expect(session.api).toBeUndefined()
  await session.refresh()
})

test('revalidação abortada ignora resposta mesmo quando transporte não respeita abort', async () => {
  const late = deferred<ReturnType<typeof response>>()
  const fetch = vi.fn().mockResolvedValueOnce(response('atual')).mockReturnValueOnce(late.promise)
  vi.stubGlobal('fetch', fetch)
  const session = useSession()
  await session.enter('current')
  const controller = new AbortController()
  const pending = session.refresh(controller.signal)
  controller.abort()
  late.resolve(response('tardia')); await pending
  expect(session.identity).toBe('atual')
})
