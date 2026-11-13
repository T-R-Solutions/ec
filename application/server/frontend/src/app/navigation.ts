export const areas = [
  { id: 'overview', icon: 'activity', label: 'Visão geral', scope: 'Global' },
  { id: 'projects', icon: 'folders', label: 'Projetos', scope: 'Global / projeto' },
  { id: 'pipelines', icon: 'schema', label: 'Pipelines', scope: 'Projeto / recurso' },
  { id: 'runs', icon: 'player-play', label: 'Execuções', scope: 'Projeto / execução' },
  { id: 'delivery', icon: 'package', label: 'Entregas', scope: 'Projeto / ambiente' },
  { id: 'infrastructure', icon: 'server', label: 'Infraestrutura', scope: 'Projeto / agente' },
  { id: 'access', icon: 'users', label: 'Pessoas e acesso', scope: 'Global / vínculo' },
  { id: 'extensions', icon: 'puzzle', label: 'Extensões e integrações', scope: 'Global / integração' },
  { id: 'administration', icon: 'adjustments-horizontal', label: 'Administração', scope: 'Global' },
] as const
export type Area = typeof areas[number]['id']
