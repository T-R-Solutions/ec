import { defineConfig } from 'vitest/config'
import vue from '@vitejs/plugin-vue'
export default defineConfig({
  plugins: [vue()],
  server: { proxy: { '/api': 'http://127.0.0.1:58139' } },
  test: {
    include: ['src/**/*.test.ts'],
    coverage: {
      provider: 'v8',
      // include também coleta fontes sem testes; não medir somente módulos importados.
      include: ['src/**/*.ts', 'src/**/*.vue'],
      exclude: ['src/**/*.test.ts', 'src/**/*.spec.ts', 'src/**/*.d.ts'],
      reporter: ['lcov'],
      reportsDirectory: process.env.COVERAGE_DIR ?? '.local/coverage',
    },
  },
})
