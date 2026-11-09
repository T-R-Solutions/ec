import { defineConfig } from '/opt/pw/node_modules/@playwright/test/index.mjs';
import { fileURLToPath } from 'node:url';

// Browser checks autocontidos: API simulada; a jornada real continua em Firefox.
export default defineConfig({
  testDir: '../../../../application/server/frontend/tests/playwright',
  outputDir: '/out/test-results',
  forbidOnly: true,
  fullyParallel: false,
  workers: 1,
  retries: 0,
  timeout: 30_000,
  expect: { timeout: 5_000 },
  reporter: [['list'], ['json', { outputFile: '/out/native.json' }], ['html', { open: 'never', outputFolder: '/out/html' }]],
  use: {
    baseURL: 'http://127.0.0.1:4173',
    browserName: 'chromium',
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
    url: 'http://127.0.0.1:4173',
    reuseExistingServer: false,
    timeout: 10_000,
  },
});
