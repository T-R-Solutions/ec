import { defineConfig } from '@playwright/test';
export default defineConfig({
  testDir: './tests',
  outputDir: '/out/test-results',
  reporter: [['list'], ['json'], ['html', { open: 'never' }]],
  use: { headless: true, trace: 'retain-on-failure', screenshot: 'only-on-failure' },
});
