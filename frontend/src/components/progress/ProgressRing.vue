<template>
    <div class="relative w-8 h-8">
        <!-- Background circle -->
        <svg class="w-full h-full" viewBox="0 0 36 36">
            <circle class="text-gray-300" stroke-width="4" stroke="currentColor" fill="transparent" r="16" cx="18"
                cy="18" />
            <!-- Progress circle -->
            <circle class="text-orange-500" stroke-width="4" :stroke-dasharray="`${circumference} ${circumference}`"
                :stroke-dashoffset="progressOffset" stroke-linecap="round" stroke="currentColor" fill="transparent"
                r="16" cx="18" cy="18" />
        </svg>
        <!-- Percentage text -->
        <div class="absolute inset-0 flex items-center justify-center text-white text-xs font-semibold">
            {{ progress }}%
        </div>
    </div>
</template>

<script setup>
import { computed, defineProps } from 'vue'

const props = defineProps({
    progress: {
        type: Number,
        required: true
    }
})

const radius = 16
const circumference = 2 * Math.PI * radius
const progressOffset = computed(() => circumference - (props.progress / 100) * circumference)
</script>

<style scoped>
/* Additional styling if needed */
</style>