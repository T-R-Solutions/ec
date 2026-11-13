<script setup lang="ts">
import DemoWorkspace from '../../../shared/ui/DemoWorkspace.vue'
import type { DemoDraft } from '../../../shared/ui/demo'
import { demo } from './demo'
defineProps<{ openDraft: (key: string, values: Record<string, string>) => DemoDraft }>()
</script>
<template><DemoWorkspace :definition="demo" :open-draft="openDraft"><template #visual="{ record, values }"><h3>Hierarquia de projetos</h3><ul class="tree"><li>Comércio<ul><li><strong>{{ values.name }}</strong> · seleção</li><li>Recursos associados: pipeline e repositório ilustrativos</li></ul></li></ul></template></DemoWorkspace></template>
