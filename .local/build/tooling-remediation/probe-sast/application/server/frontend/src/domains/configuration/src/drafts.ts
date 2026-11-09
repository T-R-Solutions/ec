import { defineStore } from 'pinia'
import { reactive } from 'vue'
import { seed } from './model'
export interface Pending {path:string;body:Record<string,unknown>}
export const useDrafts = defineStore('drafts',()=>{
 const entries=reactive<Record<string,{yaml:string;base:number;pending:Pending|undefined}>>({})
 function open(project:string){if(!entries[project])entries[project]={yaml:seed(project),base:0,pending:undefined};return entries[project]}
 function clear(){for(const k of Object.keys(entries)) delete entries[k]}
 return {open,clear}
})
