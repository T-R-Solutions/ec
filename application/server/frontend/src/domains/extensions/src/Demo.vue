<script setup lang="ts">
import DemoWorkspace from '../../../shared/ui/DemoWorkspace.vue'
import type { DemoDraft } from '../../../shared/ui/demo'
import { demo } from './demo'
defineProps<{ openDraft: (key: string, values: Record<string, string>) => DemoDraft }>()
</script>
<template><DemoWorkspace :definition="demo" :open-draft="openDraft"><template #visual="{ record, values }"><h3>Revisão de compatibilidade</h3><ol class="timeline"><li>Origem declarada na fixture · não verificada</li><li>Versão proposta: <code>{{ values.version }}</code></li><li>Permissões solicitadas disponíveis no inspector</li><li>Isolamento e assinatura: sem evidência</li></ol></template></DemoWorkspace></template>
