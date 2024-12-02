<template>
    <div class="w-full p-5 bg-white border rounded-xl">
        <h2>{{ course.title }}</h2>
        <h2 class="text-lg font-bold text-blue-400">Progress</h2>

        <!-- Lessons Progress -->
        <div class="my-4">
            <div class="flex justify-between">
                <span>Completed Lessons</span>
                <span>{{ completedLessons }} / {{ totalLessons }}</span>
            </div>
            <div class="w-full bg-gray-300 rounded-full h-2 mt-1">
                <div :style="{ width: (completedLessons / totalLessons) * 100 + '%' }"
                    class="bg-blue-500 h-2 rounded-full"></div>
            </div>
            <p class="text-sm mt-1">{{ ((completedLessons / totalLessons) * 100) }}% completed</p>
        </div>

        <!-- Quizzes Progress -->
        <div class="my-4">
            <div class="flex justify-between">
                <span>Completed Quizzes</span>
                <span>{{ completedQuizzes }} / {{ totalQuizzes }}</span>
            </div>
            <div class="w-full bg-gray-300 rounded-full h-2 mt-1">
                <div :style="{ width: (completedQuizzes / totalQuizzes) * 100 + '%' }"
                    class="bg-green-500 h-2 rounded-full"></div>
            </div>
            <p class="text-sm mt-1">{{ ((completedQuizzes / totalQuizzes) * 100).toFixed(1) }}% completed</p>
        </div>
    </div>
</template>

<script setup>
import { defineProps } from 'vue'
defineProps({
    course: {
        type: Object,
        required: true
    },
    completedLessons: {
        type: Number,
        required: true
    },
    totalLessons: {
        type: Number,
        required: true
    },
    completedQuizzes: {
        type: Number,
        required: true
    },
    totalQuizzes: {
        type: Number,
        required: true
    }
});
</script>