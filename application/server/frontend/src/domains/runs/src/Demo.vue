<script setup lang="ts">
import DemoWorkspace from '../../../shared/ui/DemoWorkspace.vue'
import type { DemoDraft } from '../../../shared/ui/demo'
import { demo } from './demo'
defineProps<{ openDraft: (key: string, values: Record<string, string>) => DemoDraft }>()
</script>
<template><DemoWorkspace :definition="demo" :open-draft="openDraft"><template #visual="{ record, values }"><h3>Evidências da execução · exemplo</h3><ol class="timeline"><li>Submissão ilustrativa recebida</li><li>Resultado: {{ record.state }}</li><li>Consulte completude e revisão no inspector</li></ol><p>Sem logs brutos nesta fixture. Ausência de logs não comprova sucesso nem falha.</p></template></DemoWorkspace></template>
