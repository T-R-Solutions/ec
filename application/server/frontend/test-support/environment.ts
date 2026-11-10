import { builtinEnvironments, type Environment } from 'vitest/runtime'

// Executa em Node com o renderer Vue de teste, compilando SFCs para o cliente.
// Não fornece DOM nem transforma estes testes em evidência de navegador.
export default {
  ...builtinEnvironments.node,
  name: 'ec-vue-renderer',
  viteEnvironment: 'client',
} satisfies Environment
