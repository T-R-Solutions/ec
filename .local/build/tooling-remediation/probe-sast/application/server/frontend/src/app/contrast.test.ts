import { test, expect } from 'vitest'
import { readFileSync } from 'node:fs'
const css=readFileSync(new URL('./style.css',import.meta.url),'utf8')
function luminance(hex:string) {
 if(hex.length===4)hex='#'+hex.slice(1).split('').map(v=>v+v).join('')
 const channels=hex.slice(1).match(/../g)!.map(v=>parseInt(v,16)/255).map(v=>v<=0.04045?v/12.92:((v+0.055)/1.055)**2.4)
 return channels[0]*0.2126+channels[1]*0.7152+channels[2]*0.0722
}
function ratio(a:string,b:string){const x=luminance(a),y=luminance(b);return (Math.max(x,y)+0.05)/(Math.min(x,y)+0.05)}
for(const [name,selector] of [['claro',':root{'],['escuro',':root[data-theme=dark]{']]) {
 test(`contraste WCAG dos tokens ativos ${name}: texto 4.5 e limite/foco 3`,()=>{
  const block=css.split(selector)[1].split('}')[0]
  const get=(key:string)=>block.match(new RegExp(`(?:^|;)${key}:(#[a-f0-9]{3,6})`))![1]
  for(const background of [get('background'),get('--panel')]){
   expect(ratio(get('color'),background)).toBeGreaterThanOrEqual(4.5)
   expect(ratio(get('--accent'),background)).toBeGreaterThanOrEqual(4.5)
   expect(ratio(get('--border'),background)).toBeGreaterThanOrEqual(3)
  }
 })
}
