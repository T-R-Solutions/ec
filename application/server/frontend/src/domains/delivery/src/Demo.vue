<script setup lang="ts">
import DemoWorkspace from '../../../shared/ui/DemoWorkspace.vue'
import type { DemoDraft } from '../../../shared/ui/demo'
import { demo } from './demo'
defineProps<{ openDraft: (key: string, values: Record<string, string>) => DemoDraft }>()
</script>
<template><DemoWorkspace :definition="demo" :open-draft="openDraft"><template #visual="{ record, values }"><h3>Caminho de promoção · exemplo</h3><ol class="timeline"><li>Artefato proposto: <code>{{ values.artifact }}</code></li><li>Verificação de integridade pendente</li><li>Gate: {{ values.gate }}</li><li>Destino: {{ values.environment }} · não implantado</li></ol></template></DemoWorkspace></template>
