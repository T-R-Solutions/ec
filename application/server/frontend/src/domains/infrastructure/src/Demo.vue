<script setup lang="ts">
import DemoWorkspace from '../../../shared/ui/DemoWorkspace.vue'
import type { DemoDraft } from '../../../shared/ui/demo'
import { demo } from './demo'
defineProps<{ openDraft: (key: string, values: Record<string, string>) => DemoDraft }>()
</script>
<template><DemoWorkspace :definition="demo" :open-draft="openDraft"><template #visual="{ record, values }"><h3>Compatibilidade ilustrativa</h3><div class="pipeline"><strong>{{ values.pool }}</strong><p>Requer {{ values.platform }} · {{ values.requirement }}</p><p>Runner da fixture: Linux. {{ values.platform === "Linux" ? "Plataforma corresponde; capacidade não medida." : "Plataforma incompatível; não há alocação." }}</p></div></template></DemoWorkspace></template>
