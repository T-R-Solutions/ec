import { computed, ref, watch } from 'vue'
import type { useSession } from '../domains/access/src'
import type { configurationWorkspace } from '../domains/configuration/src'
import type { Project } from '../domains/projects/src'
import type { Agent } from '../domains/infrastructure/src'
import { projectPath } from '../shared/api'

/** Contexto operacional; cada watcher encerra suas consultas ao trocar de contexto. */
export function useProjectContext(
  session: ReturnType<typeof useSession>,
  workspace: ReturnType<typeof configurationWorkspace>,
  clearMessage: () => void,
) {
  const projects = ref<Project[]>([]); const project = ref(''); const agents = ref<Agent[]>([])
  const query = ref(''); const projectsLoading = ref(false); const agentsLoading = ref(false)
  const projectError = ref(''); const agentError = ref('')
  const filteredProjects = computed(() => projects.value.filter(p => p.project.toLocaleLowerCase('pt-BR').includes(query.value.toLocaleLowerCase('pt-BR'))))
  const canWrite = computed(() => projects.value.find(p => p.project === project.value)?.role !== 'viewer')
watch(() => session.api, async (api, _, cleanup) => {
  workspace.clear(); projects.value = []; project.value = ''; agents.value = []; clearMessage(); query.value = ''; projectError.value = ''; agentError.value = ''
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

  return { projects, project, agents, query, projectsLoading, agentsLoading,
    projectError, agentError, filteredProjects, canWrite }
}
