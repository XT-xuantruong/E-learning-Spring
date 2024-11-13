<template>
    <div class="mt-8 flex justify-center">
        <nav class="inline-flex">
            <a href="#" class="px-4 py-2 border border-gray-300 bg-white hover:bg-gray-200 text-gray-700"
                @click.prevent="previousPage" :class="{ 'opacity-50 cursor-not-allowed': props.currentPage === 1 }"
                :disabled="props.currentPage === 1">
                <font-awesome-icon icon="chevron-left" />
            </a>

            <a v-for="page in totalPages" :key="page" href="#" @click.prevent="goToPage(page)"
                class="px-4 py-2 border border-gray-300" :class="{
                    'bg-blue-500 text-white': props.currentPage === page,
                    'bg-white hover:bg-gray-200 text-gray-700':
                        props.currentPage !== page,
                }">{{ page }}</a>

            <a href="#" class="px-4 py-2 border border-gray-300 bg-white hover:bg-gray-200 text-gray-700"
                @click.prevent="nextPage" :class="{
                    'opacity-50 cursor-not-allowed': props.currentPage === totalPages,
                }" :disabled="props.currentPage === totalPages">
                <font-awesome-icon icon="chevron-right" />
            </a>
        </nav>
    </div>
</template>

<script setup>
import { ref, defineProps, computed, defineEmits } from "vue";

const emit = defineEmits(["page-changed"]);
const props = defineProps({
    totalItems: {
        type: Number,
        required: true,
    },
    itemsPerPage: {
        type: Number,
        default: 10,
    },
    currentPage: {
        type: Number,
        required: true,
    },
});

const totalPages = computed(() => {
    return Math.ceil(props.totalItems / props.itemsPerPage);
});

const goToPage = (page) => {
    if (page >= 1 && page <= totalPages.value) {
        emit("page-changed", page); // Emit sự kiện để thông báo cho component cha
    }
};

const nextPage = () => {
    console.log("next " + props.currentPage);

    if (props.currentPage < totalPages.value) {
        goToPage(props.currentPage + 1);
    }
};

const previousPage = () => {
    console.log("Previous page " + props.currentPage);

    if (props.currentPage > 1) {
        goToPage(props.currentPage - 1);
    }
};
</script>