<template>
    <div
        class="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-all duration-300 h-[480px] flex flex-col">
        <RouterLink :to="{ name: 'course-detail', query: { course: course.id } }">
            <div class="h-48 w-full flex-shrink-0">
                <img :src="'http://localhost:8092/backend' + course.thumbnail" :alt="course.title"
                    class="w-full h-full object-cover" />
            </div>
        </RouterLink>

        <div class="p-6 flex flex-col flex-grow">
            <div class="flex-grow">
                <span class="text-sm text-blue-600 font-medium">{{ course.category_id.title }}</span>
                <h3 class="text-xl font-semibold mt-2 line-clamp-2">{{ course.title }}</h3>
                <p class="mt-4 text-gray-600 text-sm line-clamp-3">{{ course.description }}</p>
            </div>

            <div class="mt-4 flex items-center justify-between border-t pt-4">
                <p class="text-xl font-bold">{{ formatPrice(course.price) }}</p>
                <button v-if="check == false" @click="proceedToCheckout"
                    class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors">
                    Đăng ký
                </button>
                <button v-else @click="toStudy"
                    class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors">
                    Tiếp tục học
                </button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { defineProps, onBeforeMount, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useOrderStore } from '@/stores/order';
import { useUserStore } from '@/stores/user';

const router = useRouter();
const orderStore = useOrderStore();
const user = useUserStore()
const props = defineProps({
    course: {
        type: Object,
        required: true,
    },
    check: {
        type: Boolean,
        default: false,
    }
});

const proceedToCheckout = () => {
    console.log("Proceeding to checkout with items:", props.course);
    orderStore.addItem(props.course);  // Thêm khóa học vào giỏ hàng
    router.push("/checkout");  // Chuyển hướng đến trang thanh toán
};
const toStudy = () => {
    router.push({ name: "study", query: { course: props.course.id } });
};
const formatPrice = (price) => {
    return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(price);
};

</script>
