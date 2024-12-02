<script setup>
import { computed, ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import SidebarLayout from '@/layouts/user/SidebarLayout.vue';
import Pagination from '@/components/pagination/Pagination.vue';
import CourseList from '@/components/course/CourseList.vue';
import { onBeforeMount } from "vue";
import courseServices from "@/services/courseServices";

const courseList = ref([])
const route = useRoute();
const router = useRouter();
const sortOrder = ref("");
const itemsPerPage = ref(2);
const currentPage = ref(parseInt(route.query.page) || 1);
const openFilterModal = ref(false);
const minPrice = ref(""); // Minimum price
const maxPrice = ref(""); // Maximum price
const starRating = ref(""); // Default rating filter
const filterOptions = ref({
    minPrice: null,
    maxPrice: null,
    starRating: null,
});
function closeModal() {
    openFilterModal.value = false;
}
const fetchCourse = async () => {
    await courseServices.gets()
        .then(response => {
            courseList.value = response.data.data
        })
        .catch(error => {
            console.error(error)
        })
}
// const fetchCategory = async () => {
//     await categoryServices.getByslug(route.query.c)
//         .then(response => {
//             cate.value = response.data.data
//         })
//         .catch(error => {
//             console.error(error)
//         })
// }
onBeforeMount(() => {
    fetchCourse()
    // fetchCategory()
})

let searchResult = computed(() => {
    if (!route.query.q) return courses;
    const query = route.query.q.toLowerCase();
    return courseList.value.filter((course) =>
        course.title.toLowerCase().includes(query)
    );
});
const applyFilter = () => {
    filterOptions.value = {
        minPrice: minPrice.value ? minPrice.value : null,
        maxPrice: maxPrice.value ? maxPrice.value : null,
        starRating: starRating.value ? starRating.value : null,
    };
    closeModal();
};
const totalItems = computed(() => searchResult.value.length);

const paginatedItems = computed(() => {
    const start = (currentPage.value - 1) * itemsPerPage.value;
    return searchResult.value.slice(start, start + itemsPerPage.value);
});

const handlePageChange = (page) => {
    currentPage.value = page;
    router.push({ name: "search", query: { ...route.query, page: page } });
};
watch(
    () => route.query.page,
    (newPage) => {
        currentPage.value = parseInt(newPage) || 1;
    }
);
</script>

<template>
    <SidebarLayout>
        <div class="flex">
            <!-- Search Results Heading -->
            <h1 class="text-2xl font-bold mb-4 flex-1">
                Search Results for "{{ route.query.q }}"
            </h1>
            <div class="flex justify-end items-center mb-4 space-x-4">
                <button @click="openFilterModal = true" class="px-4 py-2 bg-gray-300 rounded-lg hover:bg-gray-400">
                    <font-awesome-icon icon="filter" />
                </button>

                <!-- Dropdown Sort -->
                <div>
                    <select v-model="sortOrder" @change="sortProducts" class="px-4 py-2 rounded-lg bg-slate-100">
                        <option value="" disabled selected>Sort</option>
                        <option value="asc">Giá thấp đến cao</option>
                        <option value="desc">Giá cao đến thấp</option>
                    </select>
                </div>
            </div>
        </div>
        <div v-if="openFilterModal" class="fixed inset-0 bg-black bg-opacity-50 flex justify-end"
            @click.self="closeModal">
            <div class="bg-white w-80 h-full p-6 shadow-lg transform translate-x-0 transition-transform">
                <h2 class="text-xl font-semibold mb-4">Filter Options</h2>

                <!-- Price Range Filter -->
                <div class="mb-4">
                    <label class="block text-gray-700 font-medium">Price Range</label>
                    <div class="flex space-x-2">
                        <input type="number" v-model="minPrice" placeholder="Min" class="w-1/2 p-2 border rounded"
                            min="0" />
                        <input type="number" v-model="maxPrice" placeholder="Max" class="w-1/2 p-2 border rounded"
                            min="0" />
                    </div>
                </div>

                <!-- Star Rating Filter -->
                <div class="mb-4">
                    <label class="block text-gray-700 font-medium">Star Rating</label>
                    <div class="flex flex-col space-y-2">
                        <label>
                            <input type="radio" v-model="starRating" value="" class="mr-2" />
                            Any
                        </label>
                        <label>
                            <input type="radio" v-model="starRating" value="1" class="mr-2" />
                            1 Star & Up
                        </label>
                        <label>
                            <input type="radio" v-model="starRating" value="2" class="mr-2" />
                            2 Stars & Up
                        </label>
                        <label>
                            <input type="radio" v-model="starRating" value="3" class="mr-2" />
                            3 Stars & Up
                        </label>
                        <label>
                            <input type="radio" v-model="starRating" value="4" class="mr-2" />
                            4 Stars & Up
                        </label>
                        <label>
                            <input type="radio" v-model="starRating" value="5" class="mr-2" />
                            5 Stars Only
                        </label>
                    </div>
                </div>

                <button @click="applyFilter" class="bg-blue-500 text-white w-full py-2 rounded-lg hover:bg-blue-600">
                    Apply Filters
                </button>
            </div>
        </div>

        <!-- Results Grid -->
        <CourseList :sort="sortOrder" :filterOptions="filterOptions" :courses="paginatedItems" />
        <!-- Pagination -->
        <Pagination :totalItems="totalItems" :itemsPerPage="itemsPerPage" @page-changed="handlePageChange"
            :currentPage="currentPage" />
    </SidebarLayout>
</template>