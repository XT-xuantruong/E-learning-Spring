<script setup>
import { ref } from 'vue';
import { User, Book, CheckCircle } from 'lucide-vue-next';

const activeTab = ref('profile');

const courses = [
    {
        id: 1,
        title: 'Introduction to Vue.js',
        status: 'in-progress'
    },
    {
        id: 2,
        title: 'Advanced React Patterns',
        status: 'completed'
    },
    {
        id: 3,
        title: 'Data Structures and Algorithms',
        status: 'in-progress'
    },
    {
        id: 4,
        title: 'Machine Learning Fundamentals',
        status: 'completed'
    }
];

const user = {
    name: 'John Doe',
    email: 'john.doe@example.com',
    avatar: '/api/placeholder/80/80'
};
</script>

<template>
    <div class="flex h-screen bg-gray-50">
        <!-- Sidebar -->
        <div class="bg-blue-700 text-white p-4 w-64 flex-shrink-0">
            <h2 class="text-lg font-bold mb-6 text-center">Dashboard</h2>
            <div class="space-y-4">
                <button :class="{ 'bg-blue-800': activeTab === 'profile' }"
                    class="w-full flex items-center p-3 rounded-lg hover:bg-blue-600 transition"
                    @click="activeTab = 'profile'">
                    <User class="mr-2" />
                    Profile
                </button>
                <button :class="{ 'bg-blue-800': activeTab === 'courses' }"
                    class="w-full flex items-center p-3 rounded-lg hover:bg-blue-600 transition"
                    @click="activeTab = 'courses'">
                    <Book class="mr-2" />
                    Courses
                </button>
            </div>
        </div>

        <!-- Main Content -->
        <div class="flex-grow p-8">
            <!-- Profile Tab -->
            <div v-if="activeTab === 'profile'" class="bg-white shadow-lg rounded-md p-6">
                <div class="flex items-center mb-6">
                    <img :src="user.avatar" alt="Avatar" class="w-16 h-16 rounded-full mr-4" />
                    <div>
                        <h2 class="text-xl font-bold text-gray-800">{{ user.name }}</h2>
                        <p class="text-gray-500">{{ user.email }}</p>
                    </div>
                </div>
                <button class="bg-indigo-600 hover:bg-indigo-700 text-white py-2 px-4 rounded w-full mb-2">
                    Edit Profile
                </button>
                <button class="bg-indigo-600 hover:bg-indigo-700 text-white py-2 px-4 rounded w-full">
                    Change Password
                </button>
            </div>

            <!-- Courses Tab -->
            <div v-else-if="activeTab === 'courses'" class="bg-white shadow-lg rounded-md p-6">
                <h2 class="text-2xl font-bold text-gray-800 mb-4">Your Courses</h2>
                <div class="space-y-4">
                    <div v-for="course in courses" :key="course.id"
                        class="flex justify-between items-center border-b pb-4">
                        <div class="flex items-center">
                            <Book class="mr-2 text-indigo-500" />
                            <span class="text-lg font-medium">{{ course.title }}</span>
                        </div>
                        <span
                            :class="course.status === 'completed' ? 'bg-green-100 text-green-700' : 'bg-yellow-100 text-yellow-700'"
                            class="px-3 py-1 rounded-full text-sm font-semibold">
                            <CheckCircle v-if="course.status === 'completed'"
                                class="inline-block mr-1 text-green-500" />
                            {{ course.status === 'completed' ? 'Completed' : 'In Progress' }}
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
