import { test, expect } from './fixtures.mjs';
test('headless Chromium with consumer fixture', async ({ page, consumerLabel }) => {
  await page.setContent(`<h1>${consumerLabel}</h1>`);
  await expect(page.getByRole('heading')).toHaveText('consumer-owned fixture');
});
