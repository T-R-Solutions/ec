<script setup lang="ts">
import DemoWorkspace from '../../../shared/ui/DemoWorkspace.vue'
import type { DemoDraft } from '../../../shared/ui/demo'
import { demo } from './demo'
defineProps<{ openDraft: (key: string, values: Record<string, string>) => DemoDraft }>()
</script>
<template><DemoWorkspace :definition="demo" :open-draft="openDraft"><template #visual="{ record, values }"><h3>Matriz ilustrativa · {{ values.team }}</h3><div class="table-scroll" tabindex="0" aria-label="Matriz ilustrativa"><table><caption>Papel proposto: {{ values.role }} · não é autorização efetiva</caption><thead><tr><th>Ação</th><th>Escopo</th><th>Origem</th></tr></thead><tbody><tr><td>Consultar</td><td>{{ values.scope }}</td><td>{{ record.state }}</td></tr><tr><td>Publicar</td><td>{{ values.scope }}</td><td>Exige avaliação do Server</td></tr><tr><td>Administrar acesso</td><td>{{ values.scope }}</td><td>Não concedido nesta interface</td></tr></tbody></table></div></template></DemoWorkspace></template>
