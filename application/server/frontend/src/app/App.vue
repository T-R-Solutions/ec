<script setup lang="ts">
import { computed, ref, watch, nextTick } from 'vue'
import { useSession } from '../domains/access/src'
import { ConfigurationEditor, configurationWorkspace } from '../domains/configuration/src'
import { RunsView } from '../domains/runs/src'
import type { Project } from '../domains/projects/src'
import type { Agent } from '../domains/infrastructure/src'
import { projectPath } from '../shared/api'
import { areas, type Area } from './navigation'
import { useDemoWorkspace } from './demo/store'
import SignalGlyph from '../shared/ui/SignalGlyph.vue'
import StateNotice from '../shared/ui/StateNotice.vue'
import StatusBadge from '../shared/ui/StatusBadge.vue'
import InspectorPanel from '../shared/ui/InspectorPanel.vue'
import ComponentGallery from '../shared/ui/ComponentGallery.vue'
import ProjectsDemo from '../domains/projects/src/Demo.vue'
import PipelinesDemo from '../domains/pipelines/src/Demo.vue'
import RunsDemo from '../domains/runs/src/Demo.vue'
import DeliveryDemo from '../domains/delivery/src/Demo.vue'
import InfrastructureDemo from '../domains/infrastructure/src/Demo.vue'
import AccessDemo from '../domains/access/src/Demo.vue'
import ExtensionsDemo from '../domains/extensions/src/Demo.vue'
import AdministrationDemo from '../domains/administration/src/Demo.vue'
const demos = { projects: ProjectsDemo, pipelines: PipelinesDemo, runs: RunsDemo, delivery: DeliveryDemo, infrastructure: InfrastructureDemo, access: AccessDemo, extensions: ExtensionsDemo, administration: AdministrationDemo }
const workspace = configurationWorkspace(); const session = useSession(); const demoWorkspace = useDemoWorkspace()
const token = ref(''); const message = ref(''); const projects = ref<Project[]>([]); const project = ref(''); const agents = ref<Agent[]>([])
const page = ref<Area>('pipelines'); const theme = ref('light'); const density = ref('normal'); const demo = ref(false); const gallery = ref(false); const navOpen = ref(false)
const query = ref(''); const projectsLoading = ref(false); const agentsLoading = ref(false); const projectError = ref(''); const agentError = ref(''); const entering = ref(false)
const content = ref<HTMLElement>()
const area = computed(() => areas.find(a => a.id === page.value)!)
const filteredProjects = computed(() => projects.value.filter(p => p.project.toLocaleLowerCase('pt-BR').includes(query.value.toLocaleLowerCase('pt-BR'))))
const canWrite = computed(() => projects.value.find(p => p.project === project.value)?.role !== 'viewer')
const demonstration = computed(() => demo.value || ['delivery','access','extensions','administration'].includes(page.value))
watch(theme, v => document.documentElement.dataset.theme = v)
watch(density, v => document.documentElement.dataset.density = v)
// Somente parâmetros de apresentação públicos. Nunca contém token, identidade ou projeto privado.
if (typeof window !== 'undefined') {
  const params = new URLSearchParams(window.location.search)
  const target = params.get('demo')
  if (areas.some(a => a.id === target)) { page.value = target as Area; demo.value = true }
  if (params.get('gallery') === '1') gallery.value = true
  if (params.get('theme') === 'dark') theme.value = 'dark'
  if (params.get('density') === 'compact') density.value = 'compact'
}
watch(() => session.api, async (api, _, cleanup) => {
  workspace.clear(); projects.value = []; project.value = ''; agents.value = []; message.value = ''; query.value = ''; projectError.value = ''; agentError.value = ''
  projectsLoading.value = false; agentsLoading.value = false
  if (!api) return
  let current = true; const c = new AbortController(); cleanup(() => { current = false; c.abort() })
  projectsLoading.value = true
  try { const p = await api<{projects:Project[]}>('/projects', undefined, c.signal); if (current) projects.value = p.projects }
  catch(e) { if (current) projectError.value = String(e) }
  finally { if (current) projectsLoading.value = false }
})
watch(project, async (p, _, cleanup) => {
  agents.value = []; agentError.value = ''; agentsLoading.value = false
  if (!p || !session.api) return
  let current = true; const c = new AbortController(); cleanup(() => { current = false; c.abort() }); agentsLoading.value = true
  try { const a = await session.api<{agents:Agent[]}>(projectPath(p) + '/agents', undefined, c.signal); if (current) agents.value = a.agents }
  catch(e) { if (current) agentError.value = String(e) }
  finally { if (current) agentsLoading.value = false }
})
async function enter() { const value = token.value; token.value = ''; entering.value = true; try { await session.enter(value); message.value = 'Sessão verificada'; demo.value = false } catch(e) { message.value = String(e) } finally { entering.value = false } }
async function navigate(id: Area) { page.value = id; gallery.value = false; navOpen.value = false; await nextTick(); content.value?.focus() }
function toggleDemo() {
  demo.value = !demo.value; gallery.value = false
  if (!demo.value && ['delivery','access','extensions','administration'].includes(page.value)) void navigate('projects')
}
function selectProject(id: string) { project.value = id; void navigate('pipelines') }
</script>
<template>
  <a class="skip" href="#content">Ir ao conteúdo</a>
  <header class="topbar"><strong class="brand">Ec <span>/ SIGNAL<br>PRECISION WORKBENCH</span></strong><button class="mobile-nav" :aria-expanded="navOpen" aria-controls="navigation" @click="navOpen=!navOpen">Menu</button><div class="toolbar"><label>Tema<select v-model="theme"><option value="light">Claro</option><option value="dark">Escuro · candidato</option></select></label><button :aria-pressed="density==='compact'" @click="density=density==='normal'?'compact':'normal'">{{ density==='normal' ? 'Densidade normal' : 'Densidade compacta' }}</button><button v-if="session.api" @click="session.leave().catch(e=>message=String(e))">Revogar e sair</button></div></header>
  <div class="shell">
    <aside id="navigation" class="navigator" :class="{'is-open':navOpen}"><p class="eyebrow">Áreas de trabalho</p><nav aria-label="Navegação principal"><button v-for="item in areas" :key="item.id" :aria-current="page===item.id&&!gallery?'page':undefined" @click="navigate(item.id)"><SignalGlyph :name="item.icon" /><span>{{ item.label }}</span></button></nav>
      <div class="context" v-if="session.api"><label>Projeto acessível<select v-model="project"><option value="">Selecione</option><option v-for="p in projects" :key="p.project" :value="p.project">{{ p.project }} · {{ p.role }}</option></select></label><nav aria-label="Contexto do projeto"><button @click="navigate('pipelines')">Configuração</button><button @click="navigate('infrastructure')">Agentes</button></nav><p class="meta">{{ session.identity }}<br>Expira: {{ session.expires }}</p></div>
      <div class="context"><button :aria-pressed="demo" @click="toggleDemo">{{ demo ? 'Voltar à operação' : 'Explorar demonstração' }}</button><p class="meta">Fixtures isoladas. Não publicam nem executam.</p><button :aria-current="gallery?'page':undefined" @click="gallery=true;navOpen=false">Galeria de componentes</button></div>
    </aside>
    <main id="content" ref="content" tabindex="-1"><p v-if="message" role="status">{{ message }}</p>
      <ComponentGallery v-if="gallery"/>
      <template v-else>
        <p class="breadcrumb">Ec / {{ demonstration ? 'Demonstração local' : 'Operação' }} / {{ !demonstration && project ? project : area.scope }} / {{ area.label }}</p>
        <div class="page-heading"><div><h1>{{ area.label }}</h1></div><StatusBadge :label="demonstration ? 'Demonstrativo' : session.api ? 'Sessão verificada' : 'Sessão necessária'" /></div>
        <template v-if="demonstration">
          <section v-if="page==='overview'"><StateNotice title="Visão geral demonstrativa">Escolha uma tarefa. As fixtures não representam saúde, capacidade ou métricas reais.</StateNotice><table><caption>Jornadas de avaliação</caption><thead><tr><th>Área</th><th>Próximo passo</th></tr></thead><tbody><tr v-for="item in areas.filter(a=>a.id!=='overview')" :key="item.id"><th scope="row"><button @click="navigate(item.id)">{{ item.label }}</button></th><td>Inspecionar recurso → editar proposta → revisar consequências</td></tr></tbody></table></section>
          <KeepAlive v-else><component :is="demos[page as keyof typeof demos]" :key="page" :open-draft="demoWorkspace.open" /></KeepAlive>
        </template>
        <form v-else-if="!session.api" class="login" @submit.prevent="enter"><p class="eyebrow">Acesso à operação</p><h2>Entrar com sessão provisionada</h2><p>Use a sessão entregue pelo administrador local. Ela fica somente na memória desta aba.</p><label>Token de sessão<input v-model="token" type="password" autocomplete="off" required /></label><button class="primary" :disabled="entering">{{ entering?'Verificando…':'Verificar sessão' }}</button><p class="footnote">Ao sair, o contexto privado e os rascunhos em memória são descartados.</p></form>
        <template v-else>
          <StateNotice v-if="projectsLoading" title="Carregando projetos acessíveis">Aguardando consulta do Server.</StateNotice>
          <StateNotice v-else-if="projectError" title="Consulta de projetos indisponível" tone="warning">{{ projectError }} Nenhum projeto confirmado nesta consulta.</StateNotice>
          <StateNotice v-else-if="!projects.length" title="Nenhum projeto acessível">O Server não retornou projetos para esta sessão.</StateNotice>
          <section v-if="page==='overview'"><h2>Continuar uma tarefa</h2><p>Projetos e papéis confirmados na consulta da sessão. Não há telemetria global disponível.</p><div class="toolbar"><button @click="navigate('projects')">Escolher projeto</button><button :disabled="!project" @click="navigate('runs')">Investigar execuções</button><button :disabled="!project" @click="navigate('infrastructure')">Consultar agentes</button></div><StateNotice title="Métricas globais indisponíveis">Capacidade, tendências e saúde da instância não são fornecidas por esta jornada.</StateNotice></section>
          <section v-else-if="page==='projects'"><div class="toolbar"><label class="search">Buscar projeto acessível<input v-model="query" type="search"/></label><button @click="demo=true">Explorar hierarquia demonstrativa</button></div><div class="workspace"><div class="workspace-main"><h2>Projetos acessíveis</h2><table><caption>Fonte: Server · consulta da sessão</caption><thead><tr><th>Projeto</th><th>Papel</th><th>Ação</th></tr></thead><tbody><tr v-for="p in filteredProjects" :key="p.project"><th scope="row">{{ p.project }}</th><td>{{ p.role }}</td><td><button @click="selectProject(p.project)">Abrir configuração</button></td></tr></tbody></table><p v-if="!filteredProjects.length&&!projectsLoading">Nenhum resultado nesta lista.</p></div><InspectorPanel title="Contexto de acesso"><p>O Server determina os projetos visíveis. O papel viewer permite consulta e rascunho local; publicação e execução exigem escrita.</p><p>Hierarquia, propriedade e cadastro visual são demonstrativos.</p></InspectorPanel></div></section>
          <StateNotice v-else-if="!project" title="Selecione um projeto">Use o seletor no contexto ou abra a lista.<template #action><button @click="navigate('projects')">Ver projetos</button></template></StateNotice>
          <template v-else>
            <ConfigurationEditor v-if="page==='pipelines'" :key="session.generation+project" :api="session.api" :project="project" :can-write="canWrite" />
            <RunsView v-if="page==='runs'" :key="session.generation+project" :api="session.api" :project="project" :can-write="canWrite" />
            <section v-if="page==='infrastructure'"><div class="toolbar"><h2>Agentes do projeto</h2><button @click="demo=true">Explorar pools demonstrativos</button></div><p>Validade da sessão observada na consulta; não indica heartbeat ou capacidade garantida.</p><StateNotice v-if="agentsLoading" title="Carregando agentes"/><StateNotice v-else-if="agentError" title="Consulta de agentes recusada ou indisponível" tone="warning">{{ agentError }}</StateNotice><p v-else-if="!agents.length">Nenhum agente cadastrado.</p><table v-else><caption>Snapshot da consulta · projeto {{ project }}</caption><thead><tr><th>Agente</th><th>Sessão</th><th>Capacidade</th></tr></thead><tbody><tr v-for="a in agents" :key="a.name"><th scope="row">{{ a.name }}</th><td><StatusBadge :label="a.revoked?'Revogado':a.sessionValid?'Sessão válida':'Sem sessão válida'"/></td><td>Não informada</td></tr></tbody></table></section>
          </template>
        </template>
      </template>
      <p class="footnote">Ec / Signal · YAML define configuração desejada. Confirmações operacionais vêm do Server.</p>
    </main>
  </div>
</template>
