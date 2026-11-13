// IPC por diretório privado, sem serviço de controle do navegador exposto na rede.
import { test, expect } from '/opt/pw/node_modules/@playwright/test/index.mjs';
import { readFile, writeFile, rename, unlink, mkdir } from 'node:fs/promises';
import { basename } from 'node:path';

async function publish(name, value) {
  await writeFile(`/bridge/${name}.tmp`, JSON.stringify(value), { mode: 0o600 });
  await rename(`/bridge/${name}.tmp`, `/bridge/${name}.json`);
}

test('jornada real controlada pelo harness Server/Agent/PostgreSQL', async ({ page, browser }) => {
  const downloads = [];
  await mkdir('/bridge/downloads', { recursive: true });
  page.on('download', download => {
    const name = download.suggestedFilename();
    downloads.push((async () => {
      if (basename(name) !== name) throw new Error('Nome de download fora do diretório privado');
      await download.saveAs(`/bridge/downloads/${name}.partial`);
      await rename(`/bridge/downloads/${name}.partial`, `/bridge/downloads/${name}`);
      return true;
    })().catch(() => false));
  });
  await publish('ready', { browser: browser.browserType().name(), version: browser.version() });
  let finished = false;
  let failed = false;
  let sequence = 0;
  while (!finished) {
    sequence += 1;
    const name = `request-${sequence}`;
    let operation;
    while (!operation) {
      try { operation = JSON.parse(await readFile(`/bridge/${name}.json`, 'utf8')); }
      catch (error) {
        if (error.code !== 'ENOENT') throw error;
        await new Promise(resolve => setTimeout(resolve, 25));
      }
    }
    await unlink(`/bridge/${name}.json`);
    let value = null;
    let error = null;
    try {
      switch (operation.name) {
        case 'evaluate':
          value = await page.evaluate(({ script, args }) => Function(script).apply(null, args), operation.args);
          break;
        case 'navigate': await page.goto(operation.args.url); break;
        case 'viewport': await page.setViewportSize(operation.args); break;
        case 'click': await page.getByRole('button', { name: operation.args.text, exact: true }).click(); break;
        case 'input':
          await page.locator(operation.args.selector).evaluate((element, value) => {
            element.value = value;
            element.dispatchEvent(new Event('input', { bubbles: true }));
            element.dispatchEvent(new Event('change', { bubbles: true }));
          }, operation.args.value);
          break;
        case 'text': value = await page.locator('body').innerText(); break;
        case 'screenshot': value = (await page.screenshot()).toString('base64'); break;
        case 'key': await page.keyboard.press(operation.args.key); break;
        case 'close':
          // Sempre aguardar downloads, inclusive depois de uma operação falhar.
          const completed = await Promise.all(downloads);
          failed = failed || completed.some(success => !success);
          failed = failed || operation.args.failed;
          finished = true;
          break;
        default: throw new Error('Operação de browser desconhecida');
      }
    } catch (cause) {
      failed = true;
      // Mensagem do Playwright pode conter campos/credenciais: não persistir no relatório.
      error = { name: cause.name, message: 'Operação de navegador falhou; consultar a etapa do harness' };
    }
    await publish(`response-${sequence}`, { value: value ?? null, error });
  }
  expect(failed, 'todos os oráculos do harness e operações do browser devem passar').toBe(false);
});
