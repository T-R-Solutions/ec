export class ApiError extends Error {
  constructor(public status: number, public code: string) { super(`${status}: ${code}`) }
}
export type Api = <T>(path: string, body?: unknown, signal?: AbortSignal, method?: string) => Promise<T>
export function client(token: string, expired: () => void): Api {
  return async <T>(path: string, body?: unknown, signal?: AbortSignal, method?: string): Promise<T> => {
    const r = await fetch('/api/v1' + path, { method: method ?? (body === undefined ? 'GET' : 'POST'), signal,
      headers: { Authorization: 'Bearer ' + token, 'Content-Type': 'application/json' },
      body: body === undefined ? undefined : JSON.stringify(body), cache: 'no-store' })
    const value = await r.json()
    if (!r.ok) { if (r.status === 401) expired(); throw new ApiError(r.status, value.code) }
    return value as T
  }
}
export const projectPath = (project: string) => '/projects/' + encodeURIComponent(project)
