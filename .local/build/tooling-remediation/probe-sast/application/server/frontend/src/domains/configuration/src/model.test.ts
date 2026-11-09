import { test, expect } from 'vitest'
import { seed, serialize, fields, type Model } from './model'
test('serialização explícita inclui todos os nove campos sem coerção',()=>{
 const lines=seed('ec').trim().split('\n');expect(lines.length).toBe(9)
 const m=Object.fromEntries(lines.map(line=>{const at=line.indexOf(': ');return [JSON.parse(line.slice(0,at)),JSON.parse(line.slice(at+2))]})) as Model
 expect(Object.keys(m)).toEqual([...fields]);expect(m.timeoutSeconds).toBe('30')
 m.script='printf "não"\n\t# & *';expect(serialize(m)).toContain(JSON.stringify(m.script))
})
