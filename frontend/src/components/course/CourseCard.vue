<template>
    <RouterLink :to="{ name: 'course-detail', query: { course: course.id } }"
        class="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-all duration-300 h-[480px] flex flex-col">
        <div class="h-48 w-full flex-shrink-0">
            <img :src="'http://localhost:8092/backend' + course.thumbnail" :alt="course.title"
                class="w-full h-full object-cover" />
        </div>

        <div class="p-6 flex flex-col flex-grow">
            <div class="flex-grow">
                <span class="text-sm text-blue-600 font-medium">{{ course.category_id.title }}</span>
                <h3 class="text-xl font-semibold mt-2 line-clamp-2">{{ course.title }}</h3>
                <p class="mt-4 text-gray-600 text-sm line-clamp-3">{{ course.description }}</p>
            </div>

            <div class="mt-4 flex items-center justify-between border-t pt-4">
                <p class="text-xl font-bold">{{ formatPrice(course.price) }}</p>
                <button class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors">
                    Đăng ký
                </button>
            </div>
        </div>
    </RouterLink>
</template>

<script setup>
import { defineProps, onBeforeMount } from 'vue'
import { RouterLink } from 'vue-router';
import categories from '@/faker/categories';
import categoryServices from '@/services/categoryServices';

const props = defineProps({
    course: {
        type: Object,
        required: true,
    }
});

const formatPrice = (price) => {
    return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(price);
};
// console.log(props.course.category_id);

// const fetchCategory = async () => {
//     await categoryServices.get(props.course.category_id)
//         .then(response => {
//             category.value = response.data.data
//         })
//         .catch(error => {
//             console.error(error)
//         })
// }
// onBeforeMount(() => {
//     fetchCategory()
// })
</script>