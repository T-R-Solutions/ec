# Vue component testing with Vue Test Utils v2

Vue Test Utils v2 targets Vue 3. Confirm versions and `@vue/compiler-sfc` setup. Prefer mounting real components to assert visible text, inputs, events and state; stub only expensive or explicitly out-of-scope integrations and document the lost coverage. Use `mount` or `shallowMount` deliberately; a shallow mount hides child integration faults.

```ts
import { mount, flushPromises } from '@vue/test-utils'
import { expect, it } from 'vitest'
import SubmitPanel from './SubmitPanel.vue'

it('shows confirmation only after the service resolves', async () => {
  let resolve!: (value: { status: number }) => void
  const save = () => new Promise<{ status:number }>(r => { resolve=r })
  const view = mount(SubmitPanel, { props: { save } }) // illustrative API: adapt to actual SFC
  await view.get('input').setValue('Example')
  await view.get('form').trigger('submit')
  expect(view.text()).not.toContain('Saved')
  resolve({status:200})
  await flushPromises()
  expect(view.text()).toContain('Saved')
  view.unmount()
})
```

This is an **unexecuted adaptation template**; `SubmitPanel.vue` and its props do not exist in the package. The concrete component API must be obtained from the target repo. For async setup use `<Suspense>` in the test mount when required; `nextTick` and promise flushing solve different queues.

Official: https://test-utils.vuejs.org/guide/ ; https://test-utils.vuejs.org/guide/advanced/async-suspense ; https://test-utils.vuejs.org/api/
