<script setup lang="ts">
import DemoWorkspace from '../../../shared/ui/DemoWorkspace.vue'
import type { DemoDraft } from '../../../shared/ui/demo'
import { demo } from './demo'
defineProps<{ openDraft: (key: string, values: Record<string, string>) => DemoDraft }>()
</script>
<template><DemoWorkspace :definition="demo" :open-draft="openDraft"><template #visual="{ record, values }"><h3>Sequência estruturada</h3><ol class="timeline"><li>Repositório: {{ values.repository }}</li><li><div class="pipeline"><strong>1 · Executar script</strong><p>Limite proposto: {{ values.timeout }} segundos</p></div></li><li>Coleta de saída · não executada</li></ol></template></DemoWorkspace></template>
