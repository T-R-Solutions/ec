<script setup lang="ts">
withDefaults(defineProps<{ title: string; tone?: 'neutral' | 'warning' | 'danger' | 'success' }>(), { tone: 'neutral' })
</script>
<template><div class="state-notice" :class="`tone-${tone}`" role="status"><strong>{{ title }}</strong><div><slot /></div><slot name="action" /></div></template>
