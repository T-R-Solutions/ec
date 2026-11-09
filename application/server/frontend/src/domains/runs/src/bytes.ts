export function unhex(hex: string): Uint8Array<ArrayBuffer> {
 if(!/^(?:[0-9a-fA-F]{2})*$/.test(hex))throw new Error('Hex inválido')
 return Uint8Array.from(hex.match(/../g) ?? [], v=>parseInt(v,16))
}
export function logText(hex:string):string {
 return new TextDecoder('utf-8',{fatal:false}).decode(unhex(hex)).replace(/[\x00-\x08\x0b-\x1f\x7f-\x9f\u202a-\u202e\u2066-\u2069]/g,c=>'\\u'+c.charCodeAt(0).toString(16).padStart(4,'0'))
}
export async function verifiedArtifact(parts:{sequence:number;data:string}[],size:number,sha256:string):Promise<Uint8Array<ArrayBuffer>> {
 const chunks=parts.map((p,i)=>{if(p.sequence!==i+1)throw new Error('Sequência de artefato inválida');return unhex(p.data)})
 const bytes=new Uint8Array(chunks.reduce((n,c)=>n+c.length,0));let at=0
 for(const c of chunks){bytes.set(c,at);at+=c.length}
 const hash=Array.from(new Uint8Array(await crypto.subtle.digest('SHA-256',bytes)),v=>v.toString(16).padStart(2,'0')).join('')
 if(bytes.length!==size || hash!==sha256)throw new Error('Integridade divergente: download bloqueado')
 return bytes
}
