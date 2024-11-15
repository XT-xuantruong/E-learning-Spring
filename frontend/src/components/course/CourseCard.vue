<template>
    <RouterLink :to="{ name: 'course-detail', query: { course: course.id } }"
        class="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-all duration-300 h-[480px] flex flex-col">
        <div class="h-48 w-full flex-shrink-0">
            <img :src="course.image" :alt="course.title" class="w-full h-full object-cover" />
        </div>

        <div class="p-6 flex flex-col flex-grow">
            <div class="flex-grow">
                <span class="text-sm text-blue-600 font-medium">{{ category }}</span>
                <h3 class="text-xl font-semibold mt-2 line-clamp-2">{{ course.title }}</h3>

                <div class="flex items-center mt-2">
                    <div class="flex">
                        <svg v-for="i in 5" :key="i" :class="[
                            'h-5 w-5',
                            i <= course.rating ? 'text-yellow-400' : 'text-gray-300'
                        ]" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                            <path
                                d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
                        </svg>
                    </div>
                    <span class="ml-2 text-gray-600">{{ course.rating }}/5</span>
                </div>

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
import { defineProps } from 'vue'
import { RouterLink } from 'vue-router';
import categories from '@/faker/categories';

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

const category = categories.find(c => c.id == props.course.category_id).name;
</script>