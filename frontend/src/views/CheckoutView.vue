<script setup>
import { useOrderStore } from '@/stores/order';
import { computed, onMounted, ref } from 'vue';
import { loadScript } from "@paypal/paypal-js";
import { useUserStore } from '@/stores/user';
import courseEnrollmentServices from '@/services/courseEnrollmentServices';
import { useRoute, useRouter } from 'vue-router';
import DefaultLayout from '@/layouts/user/DefaultLayout.vue';

const orderStore = useOrderStore();
const user = useUserStore();
const router = useRouter();
const loading = ref(false);
const error = ref(null);

// Computed properties for better reactivity
const total = computed(() => orderStore.item.price);
const usdAmount = computed(() => (total.value / 23000).toFixed(2));

// Price formatter with proper typing
const formatPrice = (price) => {
    return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(price);
};

// Handler for successful enrollment
const handleEnrollmentSuccess = async (response) => {
    if (response.data.data.paid) {
        orderStore.removeItems();
        await router.push({
            name: "myaccount",
            query: { tab: "orders" }
        });
    }
};

// PayPal integration with error handling
const initializePayPal = async () => {
    try {
        loading.value = true;
        const paypal = await loadScript({
            clientId: "Aem6xwjgD1G7OqUTRqYpigsFQs4r5B6jBDB_l4WGZKjtaacDq7CvY_p5yJFD3cV5xRsN6iOqvy03c4Ag",
        });

        if (!paypal) {
            throw new Error("Failed to load PayPal SDK");
        }

        await paypal.Buttons({
            createOrder: (data, actions) => {
                return actions.order.create({
                    purchase_units: [{
                        amount: {
                            value: usdAmount.value,
                        },
                    }],
                });
            },
            onApprove: async (data, actions) => {
                try {
                    const state = await actions.order.capture();

                    const orderData = {
                        price: total.value,
                        course: orderStore.item.id,
                        user: user.user.id,
                        isPaid: state.status === "COMPLETED",
                    };

                    await courseEnrollmentServices.create(orderData)
                        .then(handleEnrollmentSuccess)
                        .catch(error => {
                            console.error('Enrollment failed:', error);
                            throw new Error('Failed to enroll in course');
                        });

                } catch (error) {
                    console.error('Payment failed:', error);
                    error.value = error.name === 'INSTRUMENT_DECLINED'
                        ? 'Your payment was declined. Please try another payment method.'
                        : 'Something went wrong. Please try again.';
                }
            },
            onError: (err) => {
                console.error("PayPal Error:", err);
                error.value = "Payment processing failed. Please try again.";
            },
        }).render("#payPal");

    } catch (error) {
        console.error("PayPal initialization failed:", error);
        error.value = "Failed to initialize payment system. Please refresh the page.";
    } finally {
        loading.value = false;
    }
};

onMounted(initializePayPal);
</script>

<template>
    <DefaultLayout>
        <div class="flex justify-center items-start p-4 bg-gray-100">
            <div class="w-full max-w-2xl bg-white rounded-lg shadow-lg p-6">
                <h1 class="text-2xl font-semibold mb-4">Course Checkout</h1>

                <!-- Course Selection -->
                <div class="mb-6">
                    <h2 class="text-lg font-medium">Course Details</h2>
                    <div class="flex justify-between items-center py-2">
                        <div class="flex items-center gap-4">
                            <img class="w-16 h-16 object-cover rounded-md"
                                :src="`http://localhost:8092/backend${orderStore.item.thumbnail}`"
                                :alt="orderStore.item.title" />
                            <span class="font-medium">{{ orderStore.item.title }}</span>
                        </div>
                        <span class="text-lg">{{ formatPrice(orderStore.item.price) }}</span>
                    </div>
                </div>

                <!-- Order Summary -->
                <div class="border-t pt-4">
                    <h2 class="text-lg font-medium mb-2">Order Summary</h2>
                    <div class="flex justify-between font-semibold py-2 text-lg">
                        <span>Total</span>
                        <span>{{ formatPrice(total) }}</span>
                    </div>
                    <div class="text-sm text-gray-500 mt-1">
                        Approximately ${{ usdAmount }} USD
                    </div>
                </div>

                <!-- Error Display -->
                <div v-if="error" class="mt-4 p-3 bg-red-100 text-red-700 rounded">
                    {{ error }}
                </div>

                <!-- Payment Options -->
                <div class="mt-6">
                    <h2 class="text-lg font-medium mb-4">Payment Method</h2>
                    <div v-if="loading" class="flex justify-center py-4">
                        <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-gray-900"></div>
                    </div>
                    <div id="payPal" :class="{ 'opacity-50 pointer-events-none': loading }"></div>
                </div>
            </div>
        </div>
    </DefaultLayout>
</template>