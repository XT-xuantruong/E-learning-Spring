<script setup>
import { ref, computed } from 'vue';

const questions = ref([
    {
        id: 1,
        question: 'What is the capital of France?',
        options: ['London', 'Paris', 'Berlin', 'Madrid'],
        answer: 1,
        userAnswer: null
    },
    {
        id: 2,
        question: 'Which planet in our solar system is known as the "Red Planet"?',
        options: ['Jupiter', 'Saturn', 'Mars', 'Venus'],
        answer: 2,
        userAnswer: null
    },
    {
        id: 3,
        question: 'What is the largest ocean on Earth?',
        options: ['Atlantic Ocean', 'Indian Ocean', 'Arctic Ocean', 'Pacific Ocean'],
        answer: 3,
        userAnswer: null
    },
    {
        id: 4,
        question: 'Who is credited with inventing the telephone?',
        options: ['Thomas Edison', 'Alexander Graham Bell', 'Nikola Tesla', 'Benjamin Franklin'],
        answer: 1,
        userAnswer: null
    },
    {
        id: 5,
        question: 'In what year did World War II end?',
        options: ['1945', '1939', '1941', '1947'],
        answer: 0,
        userAnswer: null
    }
]);

const score = ref(0);
const isSubmitted = ref(false);

const handleAnswer = (questionId, selectedIndex) => {
    if (isSubmitted.value) return;

    // Find the question and update its userAnswer
    const question = questions.value.find(q => q.id === questionId);
    if (question) {
        question.userAnswer = selectedIndex;
    }
};


const submitQuiz = () => {
    if (isSubmitted.value) return;

    // Use `questions.value` to access the array for `reduce`
    score.value = questions.value.reduce((acc, question) => {
        return acc + (question.userAnswer === question.answer ? 1 : 0);
    }, 0);

    isSubmitted.value = true;
};


const resetQuiz = () => {
    questions.forEach(question => {
        question.userAnswer = null;
    });
    score.value = 0;
    isSubmitted.value = false;
};

const getOptionClass = (question, optionIndex) => {
    if (question.userAnswer === optionIndex && !isSubmitted.value) {
        return 'bg-blue-100 border-blue-500 scale-105 shadow-md'; // Show selected answer style immediately
    }

    // After submission, highlight correct and incorrect answers
    if (isSubmitted.value) {
        if (optionIndex === question.answer) {
            return 'bg-green-100 border-green-500';
        }
        if (question.userAnswer === optionIndex && question.userAnswer !== question.answer) {
            return 'bg-red-100 border-red-500';
        }
    }

    return 'hover:bg-blue-50 hover:border-blue-400';
};


</script>

<template>
    <div class="min-h-screen bg-gray-50 py-8 px-4 sm:px-6 lg:px-8">
        <div class="max-w-3xl mx-auto">
            <!-- Header -->
            <div class="text-center mb-8">
                <h1 class="text-3xl font-bold text-gray-900">General Knowledge Quiz</h1>
                <p class="mt-2 text-gray-600">Answer all questions and submit to see your score</p>
            </div>

            <!-- Score Display -->
            <div v-if="isSubmitted" class="mb-8 p-4 bg-white rounded-lg shadow-sm">
                <div class="text-center">
                    <h2 class="text-xl font-semibold text-gray-900">Your Score</h2>
                    <p class="mt-2 text-3xl font-bold text-indigo-600">
                        {{ score }} / {{ questions.length }}
                    </p>
                    <button @click="resetQuiz"
                        class="mt-4 px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                        Try Again
                    </button>
                </div>
            </div>

            <!-- Questions List -->
            <div class="space-y-6">
                <div v-for="(question, questionIndex) in questions" :key="question.id"
                    class="bg-white rounded-lg shadow-sm p-6">
                    <div class="mb-4">
                        <h3 class="text-lg font-medium text-gray-900">
                            {{ questionIndex + 1 }}. {{ question.question }}
                        </h3>
                    </div>

                    <div class="space-y-2">
                        <div v-for="(option, optionIndex) in question.options" :key="optionIndex"
                            @click="handleAnswer(question.id, optionIndex)" :class="[
                                'p-3 border-2 rounded-lg cursor-pointer transition-all duration-200 transform-gpu',
                                getOptionClass(question, optionIndex)
                            ]">
                            <div class="flex items-center">
                                <span class="ml-2">{{ option }}</span>
                            </div>
                        </div>
                    </div>


                </div>
            </div>

            <!-- Submit Button -->
            <div class="mt-8 flex justify-center">
                <button v-if="!isSubmitted" @click="submitQuiz"
                    class="px-6 py-3 border border-transparent rounded-md shadow-sm text-base font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                    Submit Quiz
                </button>
            </div>
        </div>
    </div>
</template>