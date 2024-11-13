<script setup>
import { ref } from 'vue';

const courses = [
    { id: 1, title: 'Introduction to Vue.js', price: 29.99 },
    { id: 2, title: 'Advanced React Patterns', price: 39.99 },
    { id: 3, title: 'Data Structures and Algorithms', price: 49.99 }
];

const selectedCourses = ref([]);
const subtotal = ref(0);
const total = ref(0);
const shippingFee = 0; // No shipping fee for digital courses

const selectCourse = (course) => {
    if (!selectedCourses.value.some(selected => selected.id === course.id)) {
        selectedCourses.value.push(course);
        updateTotal();
    }
};

const removeCourse = (course) => {
    selectedCourses.value = selectedCourses.value.filter(selected => selected.id !== course.id);
    updateTotal();
};

const updateTotal = () => {
    subtotal.value = selectedCourses.value.reduce((acc, course) => acc + course.price, 0);
    total.value = subtotal.value + shippingFee;
};

const placeOrder = () => {
    alert('Order placed successfully!');
};

const handlePayWithPayPal = () => {
    alert('Redirecting to PayPal...');
};
</script>

<template>
    <div class="flex justify-center items-start h-screen p-4 bg-gray-100">
        <div class="w-full max-w-2xl bg-white rounded-lg shadow-lg p-6">
            <h1 class="text-2xl font-semibold mb-4">Course Checkout</h1>

            <!-- Course Selection -->
            <div class="mb-6">
                <h2 class="text-lg font-medium">Available Courses</h2>
                <div v-for="course in courses" :key="course.id" class="flex justify-between items-center py-2">
                    <span>{{ course.title }} - ${{ course.price.toFixed(2) }}</span>
                    <button @click="selectCourse(course)" class="text-blue-600 hover:underline">Add to Cart</button>
                </div>
            </div>

            <!-- Selected Courses -->
            <div class="mb-6">
                <h2 class="text-lg font-medium">Selected Courses</h2>
                <div v-for="course in selectedCourses" :key="course.id"
                    class="flex justify-between items-center py-2 border-b">
                    <span>{{ course.title }}</span>
                    <button @click="removeCourse(course)" class="text-red-600 hover:underline">Remove</button>
                </div>
            </div>

            <!-- Order Summary -->
            <div class="border-t pt-4">
                <h2 class="text-lg font-medium mb-2">Order Summary</h2>
                <div class="flex justify-between py-2">
                    <span>Subtotal</span>
                    <span>${{ subtotal.toFixed(2) }}</span>
                </div>
                <div class="flex justify-between py-2">
                    <span>Shipping</span>
                    <span>${{ shippingFee.toFixed(2) }}</span>
                </div>
                <div class="flex justify-between font-semibold py-2 text-lg">
                    <span>Total</span>
                    <span>${{ total.toFixed(2) }}</span>
                </div>
            </div>

            <!-- Payment Options -->
            <div class="mt-6">
                <h2 class="text-lg font-medium mb-4">Payment</h2>
                <div class="flex space-x-4">
                    <button @click="placeOrder"
                        class="w-full bg-blue-500 text-white py-2 px-4 rounded-lg hover:bg-blue-600">Place
                        Order</button>
                    <button @click="handlePayWithPayPal"
                        class="w-full bg-yellow-500 text-white py-2 px-4 rounded-lg hover:bg-yellow-600">Pay with
                        PayPal</button>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
body {
    font-family: 'Arial', sans-serif;
}
</style>
