import { defineConfig } from 'vitest/config'
import vue from '@vitejs/plugin-vue'
import { readFileSync } from 'node:fs'
const automation = JSON.parse(readFileSync(new URL('../../../scripts/config/automation.json', import.meta.url), 'utf8'))
const apiAddress = `http://${automation.services.http.host}:${automation.services.http.port}`
export default defineConfig({
  plugins: [vue()],
  // O runner descarta o build/cache ao encerrar; desenvolvimento mantém o padrão Vite.
  build: { outDir: process.env.EC_FRONTEND_BUILD_DIR ?? 'dist' },
  cacheDir: process.env.EC_TASK_DIR ? `${process.env.EC_TASK_DIR}/vite-cache` : 'node_modules/.vite',
  server: { proxy: { '/api': apiAddress } },
  test: {
    environment: './test-support/environment.ts',
    include: ['src/**/*.test.ts'],
    coverage: {
      provider: 'v8',
      // include também coleta fontes sem testes; não medir somente módulos importados.
      include: ['src/**/*.ts', 'src/**/*.vue'],
      exclude: ['src/**/*.test.ts', 'src/**/*.spec.ts', 'src/**/*.d.ts'],
      reporter: ['lcov'],
      reportsDirectory: process.env.COVERAGE_DIR ?? 'coverage',
    },
  },
})
