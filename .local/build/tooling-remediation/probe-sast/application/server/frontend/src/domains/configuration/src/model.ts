export const fields = ['apiVersion','kind','project','pipeline','repository','commit','script','timeoutSeconds','artifact'] as const
export type Model = Record<typeof fields[number], string>
export interface Validation { valid: boolean; projection?: Model; diagnostic?: {code:string;message:string;line?:number;column?:number;field?:string} }
export function serialize(model: Model): string {
  return fields.map(k => JSON.stringify(k) + ': ' + JSON.stringify(model[k]) + '\n').join('')
}
export const seed = (project: string) => serialize({apiVersion:'ec/v1alpha1',kind:'ProjectPipeline',project,pipeline:'build',repository:'/tmp/source',commit:'0'.repeat(40),script:'printf ok > result.txt',timeoutSeconds:'30',artifact:'result.txt'})
