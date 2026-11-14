<script setup lang="ts">
import { ref, watch, computed, toRefs, onUnmounted } from 'vue'
import InspectorPanel from '../../../shared/ui/InspectorPanel.vue'
import StateNotice from '../../../shared/ui/StateNotice.vue'
import type { Api } from '../../../shared/api'
import { projectPath, ApiError } from '../../../shared/api'
import { useDrafts } from './drafts'
import { fields, seed, serialize, apiVersion, resourceKind, type Model, type Validation } from './model'
const props = defineProps<{api:Api;project:string;canWrite:boolean}>()
const drafts=useDrafts();const {yaml,base,pending}=toRefs(drafts.open(props.project))
const mode = ref('Visual'); const model = ref<Model>(); const validation = ref<Validation>(); const validating = ref(false)
const published = ref(0); const effective = ref(0); const publishedYaml = ref(''); const proposed = ref(''); const message = ref(''); const conflict = ref(false)
const selectedRevision = ref(0); const receipt = ref<unknown>(); const busy = ref(false)
let generation = 0; let refreshGeneration=0; const lifecycle=new AbortController()
onUnmounted(()=>{generation++;refreshGeneration++;lifecycle.abort()})
watch(yaml, (text, _, cleanup) => {
  const n = ++generation; validation.value = undefined; validating.value = true
  const controller = new AbortController(); const timer = setTimeout(async () => {
    try { const v = await props.api<Validation>(projectPath(props.project)+'/validation', {yaml:text},controller.signal); if(n===generation) {validation.value=v;if(v.valid) model.value=v.projection} }
    catch(e) {if(!controller.signal.aborted) message.value=String(e)}
    finally {if(n===generation) validating.value=false}
  }, 200)
  cleanup(()=>{clearTimeout(timer);controller.abort()})
}, {immediate:true})
async function refresh() {
  const n=++refreshGeneration
  const p = await props.api<{published:number;effective:number}>(projectPath(props.project),undefined,lifecycle.signal)
  if(n!==refreshGeneration)return
  published.value=p.published; effective.value=p.effective
  if(p.published) {const r=await props.api<{yaml:string}>(projectPath(props.project)+'/revisions/'+p.published,undefined,lifecycle.signal);if(n===refreshGeneration)publishedYaml.value=r.yaml}
}
async function loadPublished() { try {await refresh(); yaml.value=publishedYaml.value || seed(props.project);base.value=published.value;selectedRevision.value=published.value;conflict.value=false} catch(e){message.value=String(e)} }
async function resolveBase() {try {await refresh();base.value=published.value;conflict.value=false;message.value='Base atual adotada explicitamente; revise o diff antes de publicar.'}catch(e){message.value=String(e)}}
function edit(field: keyof Model, value: string) {if(model.value && model.value[field]!==value) proposed.value=serialize({...model.value,[field]:value})}
const valid = computed(()=>validation.value?.valid===true && !validating.value)
async function send() {
  if(!pending.value)return
  const request=pending.value
  busy.value=true;message.value='Pedido enviado; aguardando recibo.'
  try {receipt.value=await props.api(request.path,request.body);if(request.path.endsWith('/publications'))base.value=(receipt.value as {revision:number}).revision;message.value='Recibo confirmado pelo Server';pending.value=undefined;await refresh()}
  catch(e) {
    if(e instanceof ApiError && e.status<500) {message.value=String(e);conflict.value=e.status===409;pending.value=undefined}
    else message.value='Resposta incerta. Recupere o recibo ou repita o mesmo pedido e payload.'
  } finally {busy.value=false}
}
async function recover() {
 if(!pending.value)return
 const p=pending.value;const kind=p.path.endsWith('publications')?'receipts':'run-receipts'
 try {receipt.value=await props.api(projectPath(props.project)+'/'+kind+'/'+p.body.requestId);if(kind==='receipts')base.value=(receipt.value as {revision:number}).revision;pending.value=undefined;message.value='Recibo recuperado';await refresh()}
 catch(e) {message.value=String(e)+' — conserve o pedido; pode repetir os mesmos bytes.'}
}
function publish() {pending.value={path:projectPath(props.project)+'/publications',body:{requestId:crypto.randomUUID(),baseRevision:base.value,yaml:yaml.value}};void send()}
function submit() {pending.value={path:projectPath(props.project)+'/runs',body:{requestId:crypto.randomUUID(),revisionId:selectedRevision.value}};void send()}
void refresh().then(()=>{selectedRevision.value=published.value}).catch(e=>message.value=String(e))
</script>
<template>
<section aria-labelledby="config-title"><h2 id="config-title">Configuração do pipeline</h2>
<p class="technical">Proposta: rascunho · Base {{base}} · Publicada {{published}} · Efetiva {{effective}}</p><StateNotice v-if="!canWrite" title="Acesso de leitura">Você pode preparar um rascunho e validar. Publicar e executar exigem permissão de escrita no projeto.</StateNotice>
<button @click="loadPublished" :disabled="!!pending">Carregar publicada (substitui rascunho)</button>
<nav aria-label="Apresentações"><button v-for="tab in ['Visual','Formulário','YAML']" :key="tab" :aria-pressed="mode===tab" @click="mode=tab">{{tab}}</button></nav>
<p role="status">{{validating?'Validando no parser do Server…':valid?'Semântica válida; referências verificadas na publicação.':'Projeções mostram o último modelo válido, não o texto YAML atual.'}}</p>
<pre v-if="validation?.diagnostic" role="alert">{{validation.diagnostic}}</pre>
<label v-if="mode==='YAML'">YAML canônico<textarea v-model="yaml" rows="18" spellcheck="false" :readonly="!!pending" /></label>
<div v-else :class="mode==='Visual'?'workspace editor-workspace':'form-workspace'">
<div v-if="mode==='Visual'" class="pipeline"><p class="eyebrow">{{resourceKind}} · {{apiVersion}}</p><strong>{{model?.project || project}} / {{model?.pipeline || 'pipeline'}}</strong><span aria-hidden="true">↓</span><strong>1 · Executar script Linux → coletar artefato</strong><p>Uma etapa · {{model?.timeoutSeconds}} segundos · {{model?.artifact}}</p></div>
<component :is="mode==='Visual'?InspectorPanel:'div'" title="Propriedades da etapa"><div class="fields" v-if="model"><label v-for="field in fields" :key="field">{{field}}<textarea v-if="field==='script'" :value="model[field]" @change="edit(field,($event.target as HTMLTextAreaElement).value)" :disabled="!valid || !!pending" /><input v-else :value="model[field]" @change="edit(field,($event.target as HTMLInputElement).value)" :disabled="!valid || !!pending" /></label></div><p v-else>Propriedades disponíveis após validação do YAML no Server.</p></component>
</div>
<section v-if="proposed" aria-label="Revisão da alteração textual"><h3>Confirmar substituição textual</h3><p>Comentários e apresentação podem ser perdidos. A equivalência semântica não preserva os bytes SHA-256.</p><div class="diff"><pre>Antes
{{yaml}}</pre><pre>Depois
{{proposed}}</pre></div><button @click="yaml=proposed;proposed=''">Aplicar alteração</button><button @click="proposed=''">Preservar texto atual</button></section>
<details><summary>Diferenças: publicada / rascunho</summary><div class="diff"><pre>{{publishedYaml || 'Nenhuma revisão publicada'}}</pre><pre>{{yaml}}</pre></div></details>
<button class="primary" :disabled="!canWrite || !valid || !!pending || conflict || !!proposed" @click="publish">Publicar rascunho</button>
<button v-if="conflict" @click="resolveBase">Comparar e adotar base atual</button>
<label>Revisão para executar<input type="number" min="1" v-model.number="selectedRevision" /></label><button :disabled="!canWrite || selectedRevision<1 || !!pending" @click="submit">Submeter revisão escolhida</button>
<div v-if="pending"><p>Pedido preservado: {{pending.body.requestId}}</p><button :disabled="busy" @click="recover">Consultar recibo</button><button :disabled="busy" @click="send">Repetir mesmo pedido</button></div>
<p role="status">{{message}}</p><pre v-if="receipt" aria-label="Recibo">{{receipt}}</pre>
</section>
</template>
