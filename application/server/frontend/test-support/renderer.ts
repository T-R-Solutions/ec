// Renderer de teste Vue: estado, eventos e ciclo de vida; não simula um browser.
import { createRenderer, h, nextTick, type Component } from 'vue'
import { createPinia } from 'pinia'
import { parse, NodeTypes, type TemplateChildNode } from '@vue/compiler-dom'

export class Node {
  children: Node[] = []
  parent: Node | null = null
  props: Record<string, any> = {}
  listeners: Record<string, ((event: any) => unknown)[]> = {}
  value: any = ''
  focused = false
  focus() { this.focused = true } // Registra intenção; não prova foco no navegador.
  selected = false
  multiple = false
  selectedIndex = -1
  constructor(public tag: string, public content = '') {}
  get text(): string { return this.content + this.children.map(n => n.text).join('') }
  get type() { return this.props.type ?? this.tag }
  get options() { return this.children.filter(n => n.tag === 'option') }
  getRootNode() { return this }
  addEventListener(name: string, callback: (event: any) => unknown) {
    (this.listeners[name] ??= []).push(callback)
  }
  removeEventListener(name: string, callback: (event: any) => unknown) {
    this.listeners[name] = this.listeners[name]?.filter(c => c !== callback) ?? []
  }
}

function remove(node: Node) {
  if (node.parent) node.parent.children.splice(node.parent.children.indexOf(node), 1)
  node.parent = null
}

function insert(node: Node, parent: Node, anchor: Node | null = null) {
  remove(node)
  const index = anchor ? parent.children.indexOf(anchor) : -1
  parent.children.splice(index < 0 ? parent.children.length : index, 0, node)
  node.parent = parent
}

function staticNode(ast: TemplateChildNode): Node {
  if (ast.type === NodeTypes.TEXT) return new Node('#text', ast.content)
  if (ast.type === NodeTypes.COMMENT) return new Node('#comment')
  if (ast.type !== NodeTypes.ELEMENT) throw new Error('Nó dinâmico em conteúdo estático')
  const node = new Node(ast.tag)
  for (const property of ast.props) {
    if (property.type !== NodeTypes.ATTRIBUTE) throw new Error('Diretiva em conteúdo estático')
    node.props[property.name] = property.value?.content ?? ''
    if (property.name === 'value') node.value = property.value?.content ?? ''
  }
  for (const child of ast.children) insert(staticNode(child), node)
  return node
}

export const renderer = createRenderer<Node, Node>({
  createElement: tag => new Node(tag),
  createText: text => new Node('#text', text),
  createComment: () => new Node('#comment'),
  setText: (node, text) => { node.content = text },
  setElementText: (node, text) => { node.children = []; node.content = text },
  parentNode: node => node.parent,
  nextSibling: node => node.parent?.children[node.parent.children.indexOf(node) + 1] ?? null,
  insert,
  insertStaticContent(content, parent, anchor) {
    const nodes = parse(content).children.map(staticNode)
    if (!nodes.length) nodes.push(new Node('#comment'))
    for (const node of nodes) insert(node, parent, anchor)
    return [nodes[0], nodes[nodes.length - 1]]
  },
  remove,
  patchProp(node, key, _old, value) {
    node.props[key] = value
    if (key === 'value') { node.value = value; (node as any)._value = value }
  },
})

export function mount(component: Component, props: Record<string, unknown> = {}) {
  const root = new Node('root')
  const app = renderer.createApp({ render: () => h(component, props) })
  app.use(createPinia()).mount(root)
  return { root, unmount: () => app.unmount() }
}

export function all(root: Node, tag: string): Node[] {
  return [...(root.tag === tag ? [root] : []), ...root.children.flatMap(n => all(n, tag))]
}

export function button(root: Node, text: string): Node {
  const found = all(root, 'button').find(n => n.text === text)
  if (!found) throw new Error(`Botão ausente: ${text}; conteúdo: ${root.text}`)
  return found
}

export async function event(node: Node, name: string, extra: Record<string, unknown> = {}) {
  if (node.props.disabled) throw new Error('Interação bloqueada por disabled')
  const payload = { target: node, preventDefault() {}, stopPropagation() {}, ...extra }
  for (const listener of node.listeners[name] ?? []) await listener(payload)
  const handler = node.props['on' + name[0].toUpperCase() + name.slice(1)]
  if (handler) await handler(payload)
  await settle()
}

export async function input(node: Node, value: string) {
  node.value = value
  if (node.tag === 'select') for (const option of node.options) option.selected = option.value === value
  await event(node, node.tag === 'select' ? 'change' : 'input')
}

export async function settle() {
  for (let n = 0; n < 10; n++) { await Promise.resolve(); await nextTick() }
}

export function deferred<T>() {
  let resolve!: (value: T) => void
  let reject!: (reason: unknown) => void
  const promise = new Promise<T>((yes, no) => { resolve = yes; reject = no })
  return { promise, resolve, reject }
}
