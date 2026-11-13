<script setup lang="ts">
import DemoWorkspace from '../../../shared/ui/DemoWorkspace.vue'
import type { DemoDraft } from '../../../shared/ui/demo'
import { demo } from './demo'
defineProps<{ openDraft: (key: string, values: Record<string, string>) => DemoDraft }>()
</script>
<template><DemoWorkspace :definition="demo" :open-draft="openDraft"><template #visual="{ record, values }"><h3>Trilha de revisão · fixture</h3><ol class="timeline"><li>11/11/2026, 10:00 UTC · proposta registrada no exemplo</li><li>11/11/2026, 10:05 UTC · revisão de impacto solicitada</li><li>Aplicação e expurgo não executados</li></ol></template></DemoWorkspace></template>
