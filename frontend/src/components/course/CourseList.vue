<template>
    <section class="">
        <div class="container mx-auto px-4">
            <div class="grid grid-cols-1">
                <div class="col-span-1">
                    <div class="tabs product-tabs">
                        <div class="tabs-header flex justify-between border-b my-2">
                            <h3 class="text-lg font-semibold">{{ title }}</h3>
                        </div>

                        <div class="tab-content" id="nav-tabContent">
                            <div class="sm:block">
                                <div
                                    class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-3 xl:grid-cols-3 gap-4">
                                    <!-- Course -->

                                    <div v-if="sortedCourses.length !== 0" class="col"
                                        v-for="(item, index) in sortedCourses" :key="index">
                                        <CourseCard :course="item" />
                                    </div>
                                    <h3 v-else>No Course</h3>
                                </div>
                                <!--product-->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</template>

<script setup>
import { defineProps, computed } from "vue";
import CourseCard from "./CourseCard.vue";

const props = defineProps({
    title: {
        type: String,
    },
    sort: {
        type: String,
    },
    filterOptions: {
        type: Object,
        default: () => ({
            minPrice: null,
            maxPrice: null,
            starRating: null,
        }),
    },
    courses: {
        type: Array,
        required: true,
    },
});

// Sorting and filter logic
const sortedCourses = computed(() => {
    const { minPrice, maxPrice, starRating } = props.filterOptions || {
        minPrice: null,
        maxPrice: null,
        starRating: null,
    };

    // Step 1: Filter products by both price range and star rating
    let filteredCourses = props.courses.filter((course) => {
        const inPriceRange =
            (minPrice === null || course.price >= minPrice) &&
            (maxPrice === null || course.price <= maxPrice);

        const meetsStarRating = starRating === null || course.rating >= starRating;

        return inPriceRange && meetsStarRating;
    });
    console.log(filteredCourses);

    // Step 2: Sort the filtered products
    if (props.sort === "asc") {
        filteredCourses = filteredCourses.sort((a, b) => a.price - b.price);
    } else if (props.sort === "desc") {
        filteredCourses = filteredCourses.sort((a, b) => b.price - a.price);
    }

    return filteredCourses;
});
</script>