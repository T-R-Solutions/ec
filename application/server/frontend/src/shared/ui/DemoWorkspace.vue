<script setup lang="ts">
import { computed, ref, nextTick } from 'vue'
import type { DemoDefinition } from './demo'
import type { DemoDraft } from './demo'
import StateNotice from './StateNotice.vue'
import StatusBadge from './StatusBadge.vue'
import InspectorPanel from './InspectorPanel.vue'
const props = defineProps<{ definition: DemoDefinition; openDraft: (key: string, values: Record<string, string>) => DemoDraft }>()
const query = ref(''); const selected = ref(props.definition.records[0].id)
const scenario = ref('ready'); const heading = ref<HTMLElement>(); const listHeading = ref<HTMLElement>()
const current = computed(() => props.definition.records.find(r => r.id === selected.value) ?? props.definition.records[0])
const drafts = Object.fromEntries(props.definition.records.map(record => [record.id, props.openDraft(`${props.definition.id}/${record.id}`, record.values)]))
const draft = computed(() => drafts[current.value.id])
const records = computed(() => props.definition.records.filter(r => `${r.name} ${r.scope} ${r.state}`.toLocaleLowerCase('pt-BR').includes(query.value.toLocaleLowerCase('pt-BR'))))
const modes = ['Visual', 'Formulário', 'YAML'] as const
const states: Record<string, [string, string]> = {
  loading: ['Carregamento demonstrativo', 'A consulta está pendente. Nenhum dado confirmado.'],
  empty: ['Nenhum recurso neste cenário', 'Escolha o cenário disponível para voltar à fixture.'],
  denied: ['Acesso negado · exemplo', 'A operação exigiria autorização do servidor; sua sessão real não foi alterada.'],
  unavailable: ['Serviço indisponível · exemplo', 'Não há integração para esta demonstração. Nenhuma solicitação foi enviada.'],
  stale: ['Dados desatualizados · exemplo', 'A lista abaixo é uma fixture, não uma observação recente do servidor.'],
  conflict: ['Conflito de revisão · exemplo', 'Compare a origem e a proposta. Não existe merge nem publicação nesta demonstração.'],
  pending: ['Submissão pendente · exemplo', 'Nenhuma confirmação foi recebida. A proposta permanece apenas nesta aba.'],
  partial: ['Falha parcial · exemplo', 'O estado de parte dos recursos é desconhecido; não há confirmação global.'],
}
const visible = computed(() => !['loading','empty','denied','unavailable'].includes(scenario.value))
function yaml(values: Record<string, string>) {
  return '# Exemplo visual; sem schema de publicação\n# Não representa estado efetivo\n' + Object.entries(values).map(([key,value]) => `${key}: ${JSON.stringify(value)}`).join('\n')
}
async function inspect(id: string) { selected.value = id; await nextTick(); heading.value?.focus() }
async function stage(value: DemoDraft['stage']) { draft.value.stage = value; await nextTick(); heading.value?.focus() }
function discard() { draft.value.values = { ...current.value.values }; draft.value.retained = false; void stage('detail') }
</script>
<template>
  <StateNotice title="Demonstração local · dados fictícios" class="demo-banner">Propostas ficam nesta aba. Nenhuma ação publica, executa ou concede acesso.</StateNotice>
  <div class="toolbar">
    <label class="search">Buscar na fixture<input v-model="query" type="search" placeholder="Nome, escopo ou estado" /></label>
    <label>Cenário visual<select v-model="scenario"><option value="ready">Disponível</option><option v-for="(value,key) in states" :key="key" :value="key">{{ value[0] }}</option></select></label>
  </div>
  <StateNotice v-if="states[scenario]" :title="states[scenario][0]" :tone="scenario === 'conflict' || scenario === 'partial' ? 'warning' : 'neutral'">
    {{ states[scenario][1] }} <template #action><button @click="scenario='ready'">Voltar ao cenário disponível</button></template>
  </StateNotice>
  <div v-if="visible" class="workspace">
    <div class="workspace-main">
      <h2 ref="listHeading" tabindex="-1">{{ definition.title }}</h2>
      <div class="table-scroll" tabindex="0" aria-label="Recursos demonstrativos">
        <table><caption>Fixture local · {{ records.length }} resultados</caption><thead><tr><th scope="col">Recurso</th><th scope="col">Escopo</th><th scope="col">Estado ilustrativo</th></tr></thead>
          <tbody><tr v-for="record in records" :key="record.id" :class="{selected: selected===record.id}"><th scope="row"><button :aria-pressed="selected===record.id" @click="inspect(record.id)">{{ record.name }}</button></th><td>{{ record.scope }}</td><td>{{ record.state }}</td></tr></tbody>
        </table>
      </div>
      <StateNotice v-if="!records.length" title="Nenhum resultado"><button @click="query=''">Limpar busca</button></StateNotice>
      <section aria-label="Recurso selecionado">
        <div class="page-heading" style="margin-top: 1.5rem"><div><p class="eyebrow">{{ current.scope }} / {{ current.id }}</p><h2 ref="heading" tabindex="-1">{{ current.name }}</h2></div><StatusBadge :label="draft.stage==='detail' ? 'Fixture' : 'Proposta local'" /></div>
        <p v-if="draft.retained" role="status">Proposta retida nesta aba para avaliação. Não publicada nem aplicada.</p>
        <nav class="mode-nav" aria-label="Apresentações demonstrativas"><button v-for="mode in modes" :key="mode" :aria-pressed="draft.mode===mode" @click="draft.mode=mode">{{ mode }}</button></nav>
        <template v-if="draft.stage==='review'">
          <h3>Revisar proposta local</h3><p>{{ definition.consequence }}</p>
          <div class="diff"><div><h4>Origem · fixture</h4><pre>{{ yaml(current.values) }}</pre></div><div><h4>Proposta · não efetiva</h4><pre>{{ yaml(draft.values) }}</pre></div></div>
          <div class="toolbar"><button class="primary" @click="draft.retained=true;stage('detail')">Reter proposta nesta aba</button><button @click="stage('edit')">Voltar à edição</button><button @click="discard">Descartar proposta</button></div>
        </template>
        <template v-else>
          <div v-if="draft.mode==='Visual'"><slot name="visual" :record="current" :values="draft.values" /><dl><template v-for="field in definition.fields" :key="field.key"><dt>{{ field.label }}</dt><dd>{{ draft.values[field.key] }}</dd></template></dl></div>
          <form v-else-if="draft.mode==='Formulário'" @input="draft.retained=false" @submit.prevent="stage('review')">
            <fieldset><legend>{{ draft.stage==='edit' ? 'Editar proposta' : 'Valores ilustrativos' }}</legend><label v-for="field in definition.fields" :key="field.key">{{ field.label }}<select v-if="field.options" v-model="draft.values[field.key]" :disabled="draft.stage!=='edit'" required><option v-for="option in field.options" :key="option">{{ option }}</option></select><input v-else v-model="draft.values[field.key]" :readonly="draft.stage!=='edit'" required maxlength="160" /></label></fieldset>
            <button v-if="draft.stage==='edit'" class="primary">Revisar proposta local</button>
          </form>
          <div v-else><StateNotice title="YAML ilustrativo · somente leitura">Projeção da proposta local. Parser, schema e edição YAML arbitrária não estão disponíveis para este recurso.</StateNotice><pre tabindex="0" aria-label="YAML ilustrativo">{{ yaml(draft.values) }}</pre></div>
          <div class="toolbar"><button v-if="draft.stage==='detail'" @click="draft.mode='Formulário';stage('edit')">Editar proposta local</button><button v-if="draft.stage==='edit'" @click="discard">Descartar proposta</button><button @click="listHeading?.focus()">Voltar à lista</button></div>
        </template>
      </section>
    </div>
    <InspectorPanel title="Contexto e consequências"><dl><dt>Persona</dt><dd>{{ definition.persona }}</dd><dt>Tarefa</dt><dd>{{ definition.task }}</dd><dt>Permissão necessária na operação real</dt><dd>{{ definition.permission }}</dd><template v-for="[label,value] in current.facts" :key="label"><dt>{{ label }}</dt><dd>{{ value }}</dd></template><dt>Integração e limites</dt><dd>{{ definition.limitation }}</dd><dt>Consequência proposta</dt><dd>{{ definition.consequence }}</dd></dl><StatusBadge label="Sem integração operacional" /></InspectorPanel>
  </div>
</template>
