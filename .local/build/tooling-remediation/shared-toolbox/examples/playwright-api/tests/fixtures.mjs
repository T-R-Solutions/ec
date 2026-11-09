import { test as base, expect } from '@playwright/test';
export const test = base.extend({
  consumerLabel: async ({}, use) => { await use('consumer-owned fixture'); },
});
export { expect };
