<script setup>
import { onBeforeMount, ref } from 'vue';
import { RouterLink } from 'vue-router';
import { User, Book, CheckCircle } from 'lucide-vue-next';
import DefaultLayout from '@/layouts/user/DefaultLayout.vue';
import courseEnrollmentServices from '@/services/courseEnrollmentServices';
import { useUserStore } from '@/stores/user';
import axios from 'axios';
import oauthServices from '@/services/oauthServices';

const user = useUserStore();
const activeTab = ref('profile');
const isEditing = ref(false);

// Profile Edit Form Fields
const editedProfile = ref({
    firstName: "",
    lastName: "",
    email: "",
    avatar: ""
});
const avatarInput = ref(null);
const fetchUser = async () => {
    try {
        const response = await oauthServices.getme(user.user.id);
        editedProfile.value = response.data.data;
    } catch (error) {
        console.error('Error fetching course enrollment data:', error);
    }
};
// Toggle editing state
const toggleEdit = () => {
    isEditing.value = !isEditing.value;
};
const previewUrl = ref("http://localhost:8092/backend" + user.user.avatar);
const handleFileSelect = (event) => {
    const file = event.target.files[0];
    if (file) {
        if (file.type.startsWith("image/")) {
            const reader = new FileReader();
            reader.onload = (e) => {
                previewUrl.value = e.target.result;
                editedProfile.value.avatar = e.target.result;
            };
            reader.readAsDataURL(file);
        } else {
            alert("Please select an image file");
        }
    }
};

// Save edited profile
const saveProfile = async () => {
    try {
        const response = await oauthServices.updateProfile(user.user.id, editedProfile.value)
        if (response) {
            console.log(response.data);

            user.user.name = response.data.data.firstName + " " + response.data.data.lastName;
            user.user.email = response.data.data.email;
            user.user.avatar = response.data.data.avatar;
            isEditing.value = false;
            alert('Profile updated successfully!');
        }
    } catch (error) {
        console.error('Error updating profile:', error);
    }
};

// Fetch courses
const courses = ref([]);
const fetchCourseByUser = async () => {
    try {
        const response = await courseEnrollmentServices.getbyuser({ userId: user.user.id });
        courses.value = response.data.data;
    } catch (error) {
        console.error('Error fetching course enrollment data:', error);
    }
};

onBeforeMount(() => {
    fetchCourseByUser();
    fetchUser()
});
</script>


<template>
    <DefaultLayout>
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
                        <img :src="'http://localhost:8092/backend' + user.user.avatar" alt="Avatar"
                            class="w-16 h-16 rounded-full mr-4" />
                        <div>
                            <h2 class="text-xl font-bold text-gray-800">{{ user.user.name }}</h2>
                            <p class="text-gray-500">{{ user.user.email }}</p>
                        </div>
                    </div>

                    <button class="bg-indigo-600 hover:bg-indigo-700 text-white py-2 px-4 rounded w-full mb-2"
                        @click="toggleEdit">
                        Edit Profile
                    </button>

                    <!-- Profile Edit Form -->
                    <!-- Profile Edit Form -->
                    <div v-if="isEditing">
                        <form @submit.prevent="saveProfile" class="space-y-4">
                            <div>
                                <label for="firstName" class="block text-sm font-medium text-gray-700">First
                                    Name</label>
                                <input id="firstName" v-model="editedProfile.firstName" type="text"
                                    class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm" />
                            </div>
                            <div>
                                <label for="lastName" class="block text-sm font-medium text-gray-700">Last Name</label>
                                <input id="lastName" v-model="editedProfile.lastName" type="text"
                                    class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm" />
                            </div>
                            <div>
                                <label for="email" class="block text-sm font-medium text-gray-700">Email</label>
                                <input id="email" v-model="editedProfile.email" type="email"
                                    class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm" />
                            </div>
                            <div>
                                <label for="avatar" class="block text-sm font-medium text-gray-700">Avatar</label>
                                <input id="avatar" ref="avatarInput" type="file" @change="handleFileSelect"
                                    class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm" />
                                <div v-if="previewUrl" class="mt-4">
                                    <p class="text-sm text-gray-500 mb-2">Preview:</p>
                                    <img :src="previewUrl" alt="Avatar Preview"
                                        class="w-24 h-24 rounded-full object-cover border border-gray-300 shadow-md" />
                                </div>
                            </div>

                            <div class="flex justify-end">
                                <button type="submit"
                                    class="bg-indigo-600 hover:bg-indigo-700 text-white py-2 px-4 rounded w-full sm:w-auto mt-4">
                                    <span>Save Changes</span>
                                </button>
                            </div>
                        </form>
                    </div>

                </div>

                <!-- Courses Tab -->
                <div v-else-if="activeTab === 'courses'" class="bg-white shadow-lg rounded-md p-6">
                    <h2 class="text-2xl font-bold text-gray-800 mb-4">Your Courses</h2>
                    <div class="space-y-4">
                        <div v-for="course in courses" :key="course.id"
                            class="flex justify-between items-center border-b pb-4">
                            <RouterLink :to="{ name: 'study', query: { course: course.course.id } }"
                                class="flex items-center">
                                <Book class="mr-2 text-indigo-500" />
                                <span class="text-lg font-medium">{{ course.course.title }}</span>
                            </RouterLink>
                            <span
                                :class="course.finish ? 'bg-green-100 text-green-700' : 'bg-yellow-100 text-yellow-700'"
                                class="px-3 py-1 rounded-full text-sm font-semibold">
                                <CheckCircle v-if="course.finish" class="inline-block mr-1 text-green-500" />
                                {{ course.finish ? 'Completed' : 'In Progress' }}
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </DefaultLayout>
</template>
