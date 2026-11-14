import contract from '../../../../../../contracts/pipeline-v1.json'
export const apiVersion = contract.constants.API_VERSION.value
export const resourceKind = contract.constants.RESOURCE_KIND.value
export const fields = Object.freeze(contract.fields)

export type Model = Record<typeof fields[number], string>
export interface Validation { valid: boolean; projection?: Model; diagnostic?: {code:string;message:string;line?:number;column?:number;field?:string} }
export function serialize(model: Model): string {
  return fields.map(k => JSON.stringify(k) + ': ' + JSON.stringify(model[k]) + '\n').join('')
}
export const seed = (project: string) => serialize({apiVersion,kind:resourceKind,project,pipeline:'build',repository:'/tmp/source',commit:'0'.repeat(40),script:'printf ok > result.txt',timeoutSeconds:'30',artifact:'result.txt'})
