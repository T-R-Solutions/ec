<script setup lang="ts">
import { ref } from 'vue'
defineProps<{ title: string }>()
const open = ref(true)
</script>
<template><section class="inspector"><button class="inspector-toggle" :aria-expanded="open" @click="open = !open">{{ open ? '−' : '+' }} {{ title }}</button><div v-if="open" class="inspector-body"><slot /></div></section></template>
