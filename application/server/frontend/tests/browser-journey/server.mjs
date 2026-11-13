// Build real no Docker; proxy somente para o Server de teste declarado.
import { createServer, request } from 'node:http';
import { readFile } from 'node:fs/promises';
import { fileURLToPath } from 'node:url';
import { resolve, sep, extname } from 'node:path';

const config = JSON.parse(await readFile(new URL('../../../../../scripts/config/automation.json', import.meta.url), 'utf8'));
// A configuração fornece as portas; a fronteira do proxy permanece loopback.
// Este processo também valida o contrato quando iniciado fora do runner Python.
if (config.services.http.host !== '127.0.0.1') throw new Error('Proxy requer loopback');
for (const port of [config.services.http.port, config.services.frontend.port]) {
  if (!Number.isInteger(port) || port < 1 || port > 65535) throw new Error('Porta inválida');
}
const root = fileURLToPath(new URL('../../dist/', import.meta.url));
const types = { '.html': 'text/html', '.js': 'text/javascript', '.css': 'text/css', '.svg': 'image/svg+xml' };
createServer(async (incoming, response) => {
  if (incoming.url.startsWith('/api/')) {
    const upstream = request({ hostname: '127.0.0.1', port: Number(config.services.http.port),
      path: incoming.url, method: incoming.method, headers: incoming.headers }, result => {
      response.writeHead(result.statusCode, result.headers);
      result.pipe(response);
    });
    upstream.on('error', () => response.writeHead(502).end());
    incoming.pipe(upstream);
    return;
  }
  try {
    const pathname = decodeURIComponent(new URL(incoming.url, 'http://localhost').pathname);
    const target = resolve(root, '.' + (pathname === '/' ? '/index.html' : pathname));
    if (!target.startsWith(resolve(root) + sep) || !['GET', 'HEAD'].includes(incoming.method)) {
      response.writeHead(403).end();
      return;
    }
    const body = await readFile(target);
    response.writeHead(200, { 'Content-Type': types[extname(target)] || 'application/octet-stream',
      'Cache-Control': 'no-store', 'X-Content-Type-Options': 'nosniff' });
    response.end(incoming.method === 'HEAD' ? undefined : body);
  } catch {
    response.writeHead(404).end();
  }
}).listen(config.services.frontend.port, '127.0.0.1');
