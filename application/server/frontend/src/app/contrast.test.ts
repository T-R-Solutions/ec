// @vitest-environment node
import { test, expect } from 'vitest'
import { readFileSync } from 'node:fs'
const css = readFileSync(new URL('../shared/ui/tokens.css', import.meta.url), 'utf8')
function roles(selector: string, base: Record<string,string> = {}) {
  const block = css.split(selector)[1].split('}')[0]
  return { ...base, ...Object.fromEntries([...block.matchAll(/(--[\w-]+):\s*([^;]+);/g)].map(m => [m[1],m[2].trim()])) }
}
function resolve(tokens: Record<string,string>, key: string): string {
  const value = tokens[key]
  if (!value) throw new Error('Token ausente: ' + key)
  const ref = value.match(/^var\((--[\w-]+)\)$/)
  return ref ? resolve(tokens, ref[1]) : value
}
function luminance(hex: string) {
  const c = hex.slice(1).match(/../g)!.map(v=>parseInt(v,16)/255).map(v=>v<=.04045?v/12.92:((v+.055)/1.055)**2.4)
  return c[0]*.2126+c[1]*.7152+c[2]*.0722
}
function ratio(a: string,b: string) { const x=luminance(a),y=luminance(b);return (Math.max(x,y)+.05)/(Math.min(x,y)+.05) }
const light = roles(':root {')
for (const [name,tokens] of [['claro',light],['escuro',roles(':root[data-theme=dark] {',light)]] as const) {
  test(`pares semânticos ${name}; não substitui contraste renderizado`, () => {
    const contrast = (a: string,b: string) => ratio(resolve(tokens,a),resolve(tokens,b))
    for (const surface of ['--ec-surface','--ec-canvas','--ec-nav']) {
      expect(contrast('--ec-text',surface)).toBeGreaterThanOrEqual(4.5)
      expect(contrast('--ec-muted',surface)).toBeGreaterThanOrEqual(4.5)
      expect(contrast('--ec-action',surface)).toBeGreaterThanOrEqual(4.5)
      expect(contrast('--ec-outline',surface)).toBeGreaterThanOrEqual(3)
      expect(contrast('--ec-focus',surface)).toBeGreaterThanOrEqual(3)
    }
    expect(contrast('--ec-on-action','--ec-action')).toBeGreaterThanOrEqual(4.5)
    for (const state of ['success','warning','danger']) expect(contrast(`--ec-${state}`,`--ec-${state}-bg`)).toBeGreaterThanOrEqual(4.5)
  })
}
