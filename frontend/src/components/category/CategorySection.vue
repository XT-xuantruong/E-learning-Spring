<template>
    <!-- Categories Section -->
    <section class="py-16 container mx-auto px-4">
        <div class="flex justify-between items-center border-b pb-2 mb-12">
            <h2 class="text-3xl font-bold">Danh mục khóa học</h2>
            <a href="/categories" class="text-blue-600 hover:text-blue-800 font-medium transition-colors">
                Xem tất cả
                <span class="ml-1">→</span>
            </a>
        </div>
        <div class="grid grid-cols-1 md:grid-cols-3 lg:grid-cols-4 gap-6">
            <RouterLink :to="{ name: 'category', query: { ...route.query, c: category.slug } }"
                v-for="category in categories" :key="category" class="bg-white p-6 rounded-lg shadow-sm hover:shadow-md transition-shadow cursor-pointer
                    border-2 border-transparent hover:border-blue-600">
                <h3 class="text-lg font-semibold text-center">{{ category.title }}</h3>
            </RouterLink>
        </div>
    </section>
</template>

<script setup>
import { defineProps } from 'vue'
import { RouterLink, useRoute } from 'vue-router'
const route = useRoute()
const props = defineProps({
    categories: {
        type: Array,
        required: true
    }
})
</script>