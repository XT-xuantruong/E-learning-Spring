<template>
    <div>
        <VuePdfEmbed v-if="pdfUrl" :source="pdfUrl" annotation-layer text-layer class="h-auto" />
        <div v-else>
            Vui lòng chọn một file PDF để xem.
        </div>

        <div class="flex justify-between mt-4">
            <button @click="$emit('prev')" :disabled="currentIndex <= 0"
                class="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 disabled:opacity-50">
                Prev
            </button>

            <button @click="$emit('next')" :disabled="currentIndex >= lessons.length - 1"
                class="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 disabled:opacity-50">
                Next
            </button>
        </div>
    </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue'
import VuePdfEmbed from 'vue-pdf-embed';

import 'vue-pdf-embed/dist/styles/annotationLayer.css';
import 'vue-pdf-embed/dist/styles/textLayer.css';

defineProps({
    pdfUrl: String,
    currentIndex: Number,
    lessons: {
        type: Array,
        required: true
    }
});

defineEmits(['prev', 'next']);
</script>