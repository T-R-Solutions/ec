import { defineConfig } from '/opt/pw/node_modules/@playwright/test/index.mjs';
import { fileURLToPath } from 'node:url';
import { readFileSync } from 'node:fs';
const config = JSON.parse(readFileSync(new URL('../../automation.json', import.meta.url), 'utf8')).browser.playwright;
const baseURL = `http://127.0.0.1:${config.port}`;

// Browser checks autocontidos com API simulada; jornadas reais usam ambos os motores.
export default defineConfig({
  testDir: fileURLToPath(new URL('../../../../' + config.test_directory, import.meta.url)),
  outputDir: '/out/test-results',
  forbidOnly: true,
  fullyParallel: false,
  workers: 1,
  retries: 0,
  timeout: config.test_timeout_ms,
  expect: { timeout: config.expect_timeout_ms },
  reporter: [['list'], ['json', { outputFile: '/out/native.json' }], ['html', { open: 'never', outputFolder: '/out/html' }]],
  use: {
    baseURL,
    browserName: config.browser,
    headless: true,
    serviceWorkers: 'block',
    // Sessões não devem vazar para traces, screenshots ou estado persistido.
    trace: 'off',
    screenshot: 'off',
    video: 'off',
  },
  webServer: {
    cwd: fileURLToPath(new URL('../../../../', import.meta.url)),
    command: 'node application/server/frontend/tests/playwright/server.mjs',
    url: baseURL,
    reuseExistingServer: false,
    timeout: config.server_timeout_ms,
  },
});
