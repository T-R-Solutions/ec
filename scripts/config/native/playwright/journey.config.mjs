import { defineConfig } from '/opt/pw/node_modules/@playwright/test/index.mjs';
import { readFileSync } from 'node:fs';
import { fileURLToPath } from 'node:url';

const config = JSON.parse(readFileSync(new URL('../../automation.json', import.meta.url), 'utf8'));
const browser = process.env.EC_BROWSER;
if (!config.browser.journey.engines.includes(browser)) throw new Error('Browser da jornada não declarado');
export default defineConfig({
  testDir: '../../../../application/server/frontend/tests/browser-journey',
  testMatch: 'bridge.spec.mjs',
  outputDir: '/out/test-results',
  forbidOnly: true,
  workers: 1,
  retries: 0,
  timeout: (config.runtime.task_timeout - config.runtime.cleanup_timeout) * 1000,
  reporter: [['list'], ['json', { outputFile: '/out/native.json' }]],
  use: { browserName: browser, headless: true, acceptDownloads: true, serviceWorkers: 'block',
         trace: 'off', screenshot: 'off', video: 'off' },
  webServer: {
    cwd: fileURLToPath(new URL('../../../../', import.meta.url)),
    command: 'node application/server/frontend/tests/browser-journey/server.mjs',
    url: `http://${config.services.http.host}:${config.services.frontend.port}`,
    reuseExistingServer: false,
    timeout: config.browser.journey.startup_timeout * 1000,
  },
});
