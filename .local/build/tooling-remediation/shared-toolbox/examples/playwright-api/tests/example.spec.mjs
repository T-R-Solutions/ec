import http from 'node:http';
import { test, expect } from './fixtures.mjs';
let server;
let url;
test.beforeAll(async () => {
  server = http.createServer((_req, res) => {
    res.setHeader('content-type', 'application/json');
    res.end(JSON.stringify({ ok: true }));
  });
  await new Promise(resolve => server.listen(0, '127.0.0.1', resolve));
  url = `http://127.0.0.1:${server.address().port}`;
});
test.afterAll(async () => { await new Promise(resolve => server.close(resolve)); });
test('APIRequestContext without browser', async ({ request, consumerLabel }) => {
  expect(consumerLabel).toBe('consumer-owned fixture');
  const response = await request.get(url);
  expect(response.ok()).toBeTruthy();
  expect(await response.json()).toEqual({ ok: true });
});
