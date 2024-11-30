<template>
    <ul class="col m-5 bg-white border rounded-xl sticky top-20 max-h-[500px] overflow-y-auto">
        <h2 class="text-lg px-5 bg-white z-20 font-bold text-blue-400 my-3 sticky top-0">
            Lesson
        </h2>

        <li v-for="(lesson, index) in lessons" :key="lesson.id" :class="[
            'flex items-center py-2 px-5 border-b hover:bg-blue-400 hover:text-white',
            { 'text-gray-400 cursor-not-allowed': isLocked(index) }
        ]">
            <RouterLink :to="{
                name: 'study',
                query: { ...route.query, lesson: lesson.id }
            }" :class="{ 'cursor-not-allowed': isLocked(index) }" @click.prevent="handleLessonClick(index)">
                {{ index + 1 }}. {{ lesson.title }}
            </RouterLink>
            <span v-if="isLessonCompleted(index)" class="ml-auto text-green-500">✔</span>
        </li>

        <h2 class="text-lg px-5 bg-white z-20 font-bold text-blue-400 my-3 sticky top-0">
            Quiz
        </h2>
        <li v-for="(quiz, index) in quizs" :key="quiz.id" :class="[
            'flex items-center py-2 px-5 border-b hover:bg-blue-400 hover:text-white',
            { 'text-gray-400 cursor-not-allowed': isLocked(index) }
        ]">
            <RouterLink :to="{
                name: 'quiz',
                query: { q: quiz.id }
            }" :class="{ 'cursor-not-allowed': isLocked(index) }" @click.prevent="handleLessonClick(index)">
                {{ index + 1 }}. {{ quiz.title }}
            </RouterLink>
            <span v-if="isLessonCompleted(index)" class="ml-auto text-green-500">✔</span>
        </li>
    </ul>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue'
import { useRoute, RouterLink } from 'vue-router';

const route = useRoute();

const props = defineProps({
    lessons: {
        type: Array,
        required: true
    },
    quizs: {
        type: Array,
        required: true
    },
    currentIndex: {
        type: Number,
        required: true
    },
    completedLessons: {
        type: Number,
        required: true
    }
});

const emit = defineEmits(['select-lesson']);

const isLocked = (index) => {
    return index > props.completedLessons && index !== props.currentIndex;
};

const isLessonCompleted = (index) => {
    return index <= props.completedLessons;
};

const handleLessonClick = (index) => {
    if (!isLocked(index)) {
        emit('select-lesson', index);
    }
};
</script>