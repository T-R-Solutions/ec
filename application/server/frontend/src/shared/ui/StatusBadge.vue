<script setup lang="ts">
withDefaults(defineProps<{ tone?: 'neutral' | 'success' | 'warning' | 'danger'; label: string }>(), { tone: 'neutral' })
const symbols = { neutral: '○', success: '✓', warning: '!', danger: '×' }
</script>
<template><span class="status-badge" :class="`tone-${tone}`"><span aria-hidden="true">{{ symbols[tone] }}</span> {{ label }}</span></template>
