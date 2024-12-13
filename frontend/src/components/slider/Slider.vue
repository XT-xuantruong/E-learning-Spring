<template>
    <!-- Hero Slider -->
    <section class="relative h-[400px] overflow-hidden">
        <div class="absolute inset-0 flex transition-transform duration-500 ease-in-out"
            :style="{ transform: `translateX(-${currentSlide * 100}%)` }">
            <div v-for="slide in slides" :key="slide.id" class="min-w-full h-full relative">
                <img :src="slide.image" :alt="slide.title" class="w-full h-full object-cover" />
                <div class="absolute inset-0 bg-black bg-opacity-40 flex items-center justify-center text-center">
                    <div class="text-white">
                        <h2 class="text-4xl font-bold mb-4">{{ slide.title }}</h2>
                        <p class="text-xl">{{ slide.description }}</p>
                    </div>
                </div>
            </div>
        </div>
        <!-- Slider Controls -->
        <button @click="prevSlide"
            class="absolute left-4 top-1/2 -translate-y-1/2 bg-white/80 p-2 rounded-full hover:bg-white">
            <ChevronLeft class="w-6 h-6" />
        </button>
        <button @click="nextSlide"
            class="absolute right-4 top-1/2 -translate-y-1/2 bg-white/80 p-2 rounded-full hover:bg-white">
            <ChevronRight class="w-6 h-6" />
        </button>
    </section>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { ChevronLeft, ChevronRight, Book, Users, Video, Award } from 'lucide-vue-next'

// Data for slider
const slides = ref([
    {
        id: 1,
        image: 'https://png.pngtree.com/background/20220725/original/pngtree-webinar-banner-e-learning-internet-lesson-picture-image_1760342.jpg',
        title: 'Học lập trình trực tuyến',
        description: 'Khám phá hơn 1000+ khóa học chất lượng cao'
    },
    {
        id: 2,
        image: 'https://png.pngtree.com/thumb_back/fh260/back_our/20190617/ourmid/pngtree-blue-technology-e-commerce-learning-education-business-poster-banner-image_123753.jpg',
        title: 'Giảng viên hàng đầu',
        description: 'Học từ các chuyên gia trong ngành'
    },
    {
        id: 3,
        image: 'https://png.pngtree.com/thumb_back/fh260/background/20211021/pngtree-e-learning-table-information-blurs-urban-background-image_912834.png',
        title: 'Học mọi lúc mọi nơi',
        description: 'Truy cập không giới hạn trên mọi thiết bị'
    }
])

// Slider controls
const currentSlide = ref(0)
const slideInterval = ref(null)

const nextSlide = () => {
    currentSlide.value = (currentSlide.value + 1) % slides.value.length
}

const prevSlide = () => {
    currentSlide.value = (currentSlide.value - 1 + slides.value.length) % slides.value.length
}

onMounted(() => {
    // Auto slide every 5 seconds
    slideInterval.value = setInterval(nextSlide, 5000)
})

onBeforeUnmount(() => {
    if (slideInterval.value) {
        clearInterval(slideInterval.value)
    }
})
</script>