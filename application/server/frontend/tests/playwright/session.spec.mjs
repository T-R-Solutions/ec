import { test, expect } from '/opt/pw/node_modules/@playwright/test/index.mjs';

test('sessão rejeitada não libera contexto privado nem persiste a credencial', async ({ page }) => {
  const requests = [];
  await page.route('**/api/v1/**', async route => {
    requests.push(new URL(route.request().url()).pathname);
    await route.fulfill({ status: 401, json: { code: 'invalid_session' } });
  });
  await page.goto('/?demo=projects&gallery=1');
  await expect(page.getByRole('heading', { name: 'Entrar no Ec' })).toBeVisible();
  await page.getByRole('button', { name: 'Verificar sessão' }).click();
  await expect(page.getByRole('alert')).toContainText('Informe o token');
  await expect(page.getByLabel('Token de sessão')).toBeFocused();
  expect(requests).toEqual([]);
  await page.getByLabel('Token de sessão').fill('fixture-invalid-session');
  await page.getByRole('button', { name: 'Verificar sessão' }).click();
  await expect(page.getByRole('alert')).toContainText('Sessão recusada ou expirada');
  await expect(page.getByLabel('Token de sessão')).toHaveValue('');
  await expect(page.getByRole('navigation', { name: 'Contexto do projeto' })).toHaveCount(0);
  await expect(page.getByLabel('Projeto acessível')).toHaveCount(0);
  await expect(page.getByRole('navigation', { name: 'Navegação principal' })).toHaveCount(0);
  expect(requests).toEqual(['/api/v1/session']);
  expect(await page.evaluate(() => [localStorage.length, sessionStorage.length])).toEqual([0, 0]);
});

test('identidade da API é texto; sair remove o acesso e recarregar exige sessão', async ({ page }) => {
  const identity = '<img src=x onerror="window.ecInjected=true">';
  let revoked = false;
  await page.route('**/api/v1/**', async route => {
    const path = new URL(route.request().url()).pathname;
    if (path === '/api/v1/session' && route.request().method() === 'DELETE') {
      revoked = true;
      await route.fulfill({ json: {} });
    } else if (path === '/api/v1/session') {
      await route.fulfill({ json: { identity, expires: '2099-01-01T00:00:00Z' } });
    } else if (path === '/api/v1/projects') {
      await route.fulfill({ json: { projects: [] } });
    } else {
      throw new Error('Requisição inesperada: ' + path);
    }
  });
  await page.goto('/');
  await page.getByLabel('Token de sessão').fill('fixture-session');
  await page.getByRole('button', { name: 'Verificar sessão' }).click();
  await expect(page.getByText(identity, { exact: false })).toBeVisible();
  await expect(page.locator('main img')).toHaveCount(0);
  await expect(page.getByRole('heading', { name: 'Sessão verificada' })).toBeFocused();
  expect(await page.evaluate(() => window.ecInjected)).toBeUndefined();
  expect(await page.evaluate(() => [localStorage.length, sessionStorage.length])).toEqual([0, 0]);
  await page.getByRole('button', { name: 'Revogar e sair' }).click();
  await expect(page.getByLabel('Token de sessão')).toBeVisible();
  expect(revoked).toBe(true);
  await page.reload();
  await expect(page.getByLabel('Token de sessão')).toBeVisible();
  await expect(page.getByRole('navigation', { name: 'Contexto do projeto' })).toHaveCount(0);
  await expect(page.getByLabel('Projeto acessível')).toHaveCount(0);
  await expect(page.getByRole('navigation', { name: 'Navegação principal' })).toHaveCount(0);
});

test('entrada pública preserva teclado, reflow e bloqueia submissão duplicada', async ({ page }) => {
  let release;
  const response = new Promise(resolve => { release = resolve; });
  let calls = 0;
  await page.route('**/api/v1/session', async route => {
    calls++;
    await response;
    await route.fulfill({ status: 401, json: { code: 'invalid_session' } });
  });
  await page.setViewportSize({ width: 320, height: 900 });
  await page.goto('/');
  await expect(page.getByLabel('Token de sessão')).toBeFocused();
  await page.keyboard.press('Shift+Tab');
  await expect(page.getByRole('link', { name: 'Ir ao conteúdo' })).toBeFocused();
  await expect(page.getByRole('link', { name: 'Ir ao conteúdo' })).toBeInViewport();
  await page.keyboard.press('Enter');
  await expect(page.getByRole('main')).toBeFocused();
  await page.keyboard.press('Tab');
  await expect(page.getByLabel('Token de sessão')).toBeFocused();
  await page.keyboard.press('Tab');
  await expect(page.getByRole('button', { name: 'Verificar sessão' })).toBeFocused();
  expect(await page.evaluate(() => document.documentElement.scrollWidth)).toBe(320);
  await page.getByLabel('Token de sessão').fill('fixture');
  await page.keyboard.press('Enter');
  await expect(page.getByRole('status')).toContainText('Verificando');
  await expect(page.getByRole('button', { name: 'Verificando' })).toBeDisabled();
  await page.locator('form').evaluate(form => { form.requestSubmit(); form.requestSubmit(); });
  expect(calls).toBe(1);
  release();
  await expect(page.getByRole('alert')).toContainText('Sessão recusada');
  await expect(page.getByLabel('Token de sessão')).toHaveValue('');
  await expect(page.getByLabel('Token de sessão')).toBeFocused();
  for (const width of [320, 390, 768, 1024, 1440, 1920]) {
    await page.setViewportSize({ width, height: 900 });
    expect(await page.evaluate(() => document.documentElement.scrollWidth)).toBe(width);
    await expect(page.getByRole('button', { name: 'Verificar sessão' })).toBeInViewport();
  }
});
