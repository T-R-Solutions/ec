<script setup lang="ts">
const input = 'fixture';
// ruleid: ec.vue.dynamic-eval
eval(input);
// ok: ec.vue.dynamic-eval
JSON.parse(input);
</script>
<template>
  <!-- ruleid: ec.vue.raw-html -->
  <div v-html="input"></div>
  <!-- ok: ec.vue.raw-html -->
  <div>{{ input }}</div>
</template>
