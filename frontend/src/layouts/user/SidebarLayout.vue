<template>
    <div class="min-h-screen bg-gray-50">
        <Header />
        <div class="flex my-10">
            <!-- Left Sidebar -->
            <aside class="w-1/4 bg-white p-4 shadow-lg rounded-lg border-2 m-2">
                <h2 class="text-xl font-bold mb-4">Categories</h2>
                <ul class="space-y-2">
                    <li v-for="category in visibleCategories" :key="category.id"
                        class="w-full hover:bg-gray-300 p-2 flex items-center space-x-3 rounded-lg">
                        <RouterLink :to="{
                            name: 'category',
                            query: { c: category.slug },
                        }" class="text-black w-full flex items-center">
                            <span>{{ category.name }} ({{ getTotalItems(category.id) }})</span>
                        </RouterLink>
                    </li>
                </ul>
                <button v-if="hasMoreCategories" @click="loadMoreCategories"
                    class="mt-4 p-2 text-black hover:underline">
                    Xem Thêm
                </button>
            </aside>

            <!-- Main Content - Product List -->
            <div class="w-3/4 ml-8">
                <slot></slot>
            </div>
        </div>
        <Footer />
    </div>
</template>
<script setup>
import Header from '@/components/header/Header.vue';
import Footer from '@/components/footer/Footer.vue';
import categories from '@/faker/categories';
import courses from '@/faker/course';
import { RouterLink } from "vue-router";
import { computed, ref } from "vue";

const displayedCount = ref(2);
const visibleCategories = computed(() => {
    return categories.slice(0, displayedCount.value);
});

// Tính số danh mục còn lại
const hasMoreCategories = computed(
    () => displayedCount.value < categories.length
);

const loadMoreCategories = () => {
    displayedCount.value += 10;
};
const getTotalItems = (categoryId) => {
    return courses.filter((p) => p.category_id === categoryId).length;
};

</script>