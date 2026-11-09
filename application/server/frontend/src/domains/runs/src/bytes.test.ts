import { test, expect } from 'vitest'
import { logText, unhex, verifiedArtifact } from './bytes'
test('controles e UTF-8 inválido são dados, HTML permanece texto',()=>{
 expect(logText('1b5b33316d00ff3c7363726970743e')).toBe('\\u001b[31m\\u0000�<script>')
 expect(()=>unhex('a')).toThrow();expect(()=>unhex('zz')).toThrow()
})
test('download preserva bytes somente se tamanho, sequência e hash conferirem',async()=>{
 const sha='e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855'
 expect(await verifiedArtifact([],0,sha)).toEqual(new Uint8Array())
 await expect(verifiedArtifact([],1,sha)).rejects.toThrow('Integridade')
 await expect(verifiedArtifact([{sequence:2,data:'00'}],1,sha)).rejects.toThrow('Sequência')
 await expect(verifiedArtifact([{sequence:1,data:'ff'}],1,sha)).rejects.toThrow('Integridade')
})
