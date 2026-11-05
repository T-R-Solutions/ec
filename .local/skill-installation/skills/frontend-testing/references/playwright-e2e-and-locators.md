# Playwright E2E and user-visible locators

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

Prefer `page.getByRole('button', {name:'Save'})`, `getByLabel('Title')`, locators scoped by region and `await expect(locator).toBeVisible()/toHaveText()`. Locators have actionability checks and retryable assertions. Avoid brittle CSS structure selectors, network-idle as a universal completion oracle, fixed sleeps and `force: true` to bypass actionability without justification. Test keyboard/URL/reload, context-bound session and view-level failure states.

```ts
import { test, expect } from '@playwright/test'
test('server error preserves typed content', async ({ page }) => {
  await page.goto('/feature') // target route must exist in the real app
  await page.getByRole('textbox', {name:'Title'}).fill('Draft')
  await page.getByRole('button', {name:'Save'}).click()
  await expect(page.getByRole('status')).toContainText('Save failed')
  await expect(page.getByRole('textbox', {name:'Title'})).toHaveValue('Draft')
})
```

This is an **unexecuted template**, not an Ec route or installed Playwright Test proof. Distinguish `browser mock`, `browser live`, and `deployed`. Use per-test browser contexts, and diagnose with trace-on-failure while redacting sensitive content.

Official: https://playwright.dev/docs/best-practices ; https://playwright.dev/docs/locators ; https://playwright.dev/docs/actionability ; https://playwright.dev/docs/trace-viewer-intro
