import { afterEach, expect, test, vi } from 'vitest'
import { client, ApiError, projectPath } from './api'

afterEach(() => vi.unstubAllGlobals())

test('cliente preserva método, bytes JSON, credencial, sinal e destino de projeto', async () => {
  const fetch = vi.fn().mockResolvedValue({ ok: true, json: async () => ({ revision: 7 }) })
  vi.stubGlobal('fetch', fetch)
  const expired = vi.fn()
  const api = client('session', expired)
  const signal = new AbortController().signal
  expect(await api(projectPath('a/b ?'), { yaml: 'é\n# texto' }, signal)).toEqual({ revision: 7 })
  expect(fetch).toHaveBeenLastCalledWith('/api/v1/projects/a%2Fb%20%3F', {
    method: 'POST', signal, cache: 'no-store', headers: { Authorization: 'Bearer session', 'Content-Type': 'application/json' },
    body: '{"yaml":"é\\n# texto"}',
  })
  await api('/session')
  expect(fetch.mock.calls[1][1].method).toBe('GET')
  expect(fetch.mock.calls[1][1].body).toBeUndefined()
  await api('/session', undefined, undefined, 'DELETE')
  expect(fetch.mock.calls[2][1].method).toBe('DELETE')
  expect(expired).not.toHaveBeenCalled()
})

test('401 expira sessão; rejeições e falhas de transporte continuam erros', async () => {
  const fetch = vi.fn()
  vi.stubGlobal('fetch', fetch)
  const expired = vi.fn()
  const api = client('token', expired)
  for (const status of [403, 409, 500, 401]) {
    fetch.mockResolvedValueOnce({ ok: false, status, json: async () => ({ code: 'denied' }) })
    await expect(api('/session')).rejects.toEqual(new ApiError(status, 'denied'))
  }
  expect(expired).toHaveBeenCalledTimes(1)
  fetch.mockRejectedValueOnce(new Error('offline'))
  await expect(api('/session')).rejects.toThrow('offline')
})
