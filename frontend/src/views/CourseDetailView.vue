<template>

    <DefaultLayout>
        <div class="min-h-screen bg-gray-100">
            <!-- Hero Section -->
            <div class="relative h-96 bg-gradient-to-r from-blue-600 to-indigo-700">
                <div class="container mx-auto px-4 py-16">
                    <div class="text-white">
                        <h1 class="text-4xl font-bold mb-4">{{ currentCourse?.title }}</h1>
                        <p class="text-xl mb-6">{{ currentCourse?.description }}</p>
                        <div class="flex items-center space-x-4" v-if="courseInstructor">
                            <img :src="courseInstructor.avatar" :alt="courseInstructor.name"
                                class="w-12 h-12 rounded-full">
                            <div>
                                <p class="font-medium">{{ courseInstructor.name }}</p>
                                <p class="text-sm opacity-80">{{ courseInstructor.title }}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Main Content -->
            <div class="container mx-auto px-4 py-8">
                <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
                    <!-- Course Content -->
                    <div class="lg:col-span-2">
                        <!-- Course Overview -->
                        <div class="bg-white rounded-lg shadow-md p-6 mb-8">
                            <h2 class="text-2xl font-bold mb-4">Tổng quan khóa học</h2>
                            <div class="grid grid-cols-2 gap-4 mb-6">

                                <div class="flex items-center space-x-2">
                                    <svg class="w-5 h-5 text-gray-500" fill="none" stroke="currentColor"
                                        viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                            d="M3 7v10a2 2 0 002 2h14a2 2 0 002-2V9a2 2 0 00-2-2h-6l-2-2H5a2 2 0 00-2 2z" />
                                    </svg>
                                    <span>{{ courseLectures.length }} bài học</span>
                                </div>
                                <div class="flex items-center space-x-2">
                                    <svg class="w-5 h-5 text-gray-500" fill="none" stroke="currentColor"
                                        viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                            d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                                    </svg>
                                    <span>Chứng chỉ hoàn thành</span>
                                </div>
                                <div class="flex items-center space-x-2">
                                    <svg class="w-5 h-5 text-gray-500" fill="none" stroke="currentColor"
                                        viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                            d="M3 15a4 4 0 004 4h9a5 5 0 10-.1-9.999 5.002 5.002 0 10-9.78 2.096A4.001 4.001 0 003 15z" />
                                    </svg>
                                    <span>Truy cập trọn đời</span>
                                </div>
                            </div>
                            <p class="text-gray-600">{{ currentCourse?.fullDescription }}</p>
                        </div>

                        <!-- Course Content -->
                        <div class="bg-white rounded-lg shadow-md p-6">
                            <h2 class="text-2xl font-bold mb-4">Nội dung khóa học</h2>
                            <div class="space-y-4">
                                <div v-for="(lecture, index) in courseLectures" :key="lecture.id"
                                    class="border-b last:border-b-0 pb-4">
                                    <div class="flex items-center justify-between">
                                        <div class="flex items-center space-x-4">
                                            <span
                                                class="w-8 h-8 flex items-center justify-center bg-blue-100 text-blue-600 rounded-full">
                                                {{ index + 1 }}
                                            </span>
                                            <div>
                                                <h3 class="font-medium">{{ lecture.title }}</h3>
                                                <p class="text-sm text-gray-500">45 phút</p>
                                            </div>
                                        </div>
                                        <button class="text-blue-600 hover:text-blue-700">
                                            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                    d="M14.752 11.168l-3.197-2.132A1 1 0 0010 9.87v4.263a1 1 0 001.555.832l3.197-2.132a1 1 0 000-1.664z" />
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                    d="M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                                            </svg>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Sidebar -->
                    <div class="lg:col-span-1">
                        <div class="bg-white rounded-lg shadow-md p-6 sticky top-20">
                            <div class="flex items-center justify-center space-x-2 mb-6">
                                <span class="text-3xl font-bold text-blue-600">{{ formatPrice(currentCourse?.price)
                                    }}</span>
                                <div class="flex items-center ml-4">
                                    <span class="text-yellow-400 mr-1">★</span>
                                    <span class="font-medium">{{ currentCourse?.rating }}</span>
                                </div>
                            </div>
                            <button
                                class="w-full bg-blue-600 text-white py-3 rounded-lg font-medium hover:bg-blue-700 transition-colors mb-4">
                                Đăng ký ngay
                            </button>
                            <div class="mt-6 space-y-4">
                                <div class="flex items-center space-x-3">
                                    <svg class="w-5 h-5 text-green-500" fill="none" stroke="currentColor"
                                        viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                            d="M5 13l4 4L19 7" />
                                    </svg>
                                    <span>30 ngày hoàn tiền</span>
                                </div>
                                <div class="flex items-center space-x-3">
                                    <svg class="w-5 h-5 text-green-500" fill="none" stroke="currentColor"
                                        viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                            d="M5 13l4 4L19 7" />
                                    </svg>
                                    <span>Truy cập trọn đời</span>
                                </div>
                                <div class="flex items-center space-x-3">
                                    <svg class="w-5 h-5 text-green-500" fill="none" stroke="currentColor"
                                        viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                            d="M5 13l4 4L19 7" />
                                    </svg>
                                    <span>Chứng chỉ hoàn thành</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </DefaultLayout>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import instructors from '@/faker/instructors'
import lectures from '@/faker/lectures'
import courses from '@/faker/course'
import DefaultLayout from '@/layouts/user/DefaultLayout.vue'

const route = useRoute()
// Giả sử chúng ta đang xem khóa học có id = 9
const courseId = route.query.course

// Lấy thông tin khóa học hiện tại
const currentCourse = computed(() => {
    return courses.find(course => course.id == courseId)
})

// Lấy thông tin giảng viên của khóa học
const courseInstructor = computed(() => {
    if (currentCourse.value) {
        return instructors.find(instructor => instructor.id == currentCourse.value.create_by)
    }
    return null
})

// Lấy danh sách bài giảng của khóa học
const courseLectures = computed(() => {
    return lectures.filter(lecture => lecture.course_id == courseId)
})

// Tính tổng thời lượng khóa học (giả định mỗi bài 45 phút)
const courseDuration = computed(() => {
    const totalMinutes = courseLectures.value.length * 45
    return Math.round(totalMinutes / 60)
})

const formatPrice = (price) => {
    return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(price)
}
</script>