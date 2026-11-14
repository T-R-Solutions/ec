export const areas = [
  { id: 'overview', icon: 'activity', label: 'Visão geral', scope: 'Global' },
  { id: 'projects', icon: 'folders', label: 'Projetos', scope: 'Global / projeto' },
  { id: 'pipelines', icon: 'schema', label: 'Pipelines', scope: 'Projeto / recurso' },
  { id: 'runs', icon: 'player-play', label: 'Execuções', scope: 'Projeto / execução' },
  { id: 'delivery', demonstrationOnly: true, icon: 'package', label: 'Entregas', scope: 'Projeto / ambiente' },
  { id: 'infrastructure', icon: 'server', label: 'Infraestrutura', scope: 'Projeto / agente' },
  { id: 'access', demonstrationOnly: true, icon: 'users', label: 'Pessoas e acesso', scope: 'Global / vínculo' },
  { id: 'extensions', demonstrationOnly: true, icon: 'puzzle', label: 'Extensões e integrações', scope: 'Global / integração' },
  { id: 'administration', demonstrationOnly: true, icon: 'adjustments-horizontal', label: 'Administração', scope: 'Global' },
] as const
export type Area = typeof areas[number]['id']

export function isDemonstrationOnly(id: Area): boolean {
  const area = areas.find(item => item.id === id)
  return !!area && 'demonstrationOnly' in area && area.demonstrationOnly
}
