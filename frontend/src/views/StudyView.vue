<template>
    <DefaultLayout>
        <!-- Progress Section -->
        <CourseProgress :course="currentCourse" :completedLessons="completedLessons"
            :totalLessons="lessons?.length || 0" />

        <div class="grid grid-cols-3">
            <!-- PDF Viewer Section -->
            <div class="m-5 p-5 bg-white border rounded col-span-2">
                <PdfViewer :pdfUrl="pdfUrl" :currentIndex="currentIndex" :lessons="lessons || []" @prev="prevPdf"
                    @next="nextPdf" />
            </div>

            <!-- Lessons List Section -->
            <LessonsList :quizs="quizInCourse || []" :lessons="lessons || []" :currentIndex="currentIndex"
                :completedLessons="completedLessons" @select-lesson="selectPdf" />
        </div>
    </DefaultLayout>
</template>

<script setup>
import { ref, computed, onMounted, watch, onBeforeMount } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import DefaultLayout from '@/layouts/user/DefaultLayout.vue';
import CourseProgress from '@/components/course/CourseProgress.vue';
import PdfViewer from '@/components/pdf/PdfViewer.vue';
import LessonsList from '@/components/course/LessonsList.vue';
import courseServices from '@/services/courseServices';
import courseEnrollmentServices from '@/services/courseEnrollmentServices';
import { useUserStore } from '@/stores/user';

// Router setup
const route = useRoute();
const router = useRouter();
const user = useUserStore()
const courseId = route.query.course;
const currentCourse = ref({
    lectures: [],
    quizs: []
});
const course = ref({})

// Computed properties with null checks
const lessons = computed(() => {
    if (!currentCourse.value?.lectures) return [];
    return [...currentCourse.value.lectures].sort((a, b) => a.title.localeCompare(b.title));
});

const quizInCourse = computed(() => {
    if (!currentCourse.value?.quizs) return [];
    return [...currentCourse.value.quizs].sort((a, b) => a.title.localeCompare(b.title));
});

// State
const currentIndex = ref(-1);
const pdfUrl = ref('');
const completedLessons = ref(2);

// Fetch course data
const fetchCourse = async () => {
    try {
        const response = await courseServices.getMoreDetail(courseId);
        currentCourse.value = response.data.data;

        // Initialize first lesson if available
        if (lessons.value.length > 0 && currentIndex.value === -1) {
            const lessonId = route.query.lesson;
            if (lessonId) {
                const index = findLessonIndex(lessonId);
                if (index !== -1) {
                    selectPdf(index);
                }
            } else {
                // If no lesson ID, start from the last completed lesson + 1
                const nextLessonIndex = Math.min(completedLessons.value, lessons.value.length - 1);
                selectPdf(nextLessonIndex);
                updateRoute(nextLessonIndex);
            }
        }
    } catch (error) {
        console.error('Error fetching course:', error);
        // You might want to add error handling UI here
    }
};
const fetchCourseByUser = async () => {
    await courseEnrollmentServices.getbyuser({ userId: user.user.id })
        .then(response => {
            course.value = response.data.data.find(c => c.course.id === route.query.course)

            console.log(course.value);

        })
        .catch(error => {
            console.error(error)
        })
}

// Find lesson index by ID
const findLessonIndex = (lessonId) => {
    return lessons.value.findIndex(lesson => lesson.id === lessonId);
};

// Methods
const selectPdf = (index) => {
    if (lessons.value[index]) {
        currentIndex.value = index;
        pdfUrl.value = `http://localhost:8092/backend${lessons.value[index].content}`;
    }
};

const nextPdf = () => {
    if (currentIndex.value < lessons.value.length - 1) {
        selectPdf(currentIndex.value + 1);
        if (currentIndex.value > completedLessons.value) {
            completedLessons.value = currentIndex.value;
        }
        if (currentIndex.value === lessons.value.length - 1) {
            completedLessons.value = lessons.value.length;
        }
        updateRoute();
    }
};

const prevPdf = () => {
    if (currentIndex.value > 0) {
        selectPdf(currentIndex.value - 1);
        updateRoute();
    }
};

const updateRoute = () => {
    if (lessons.value[currentIndex.value]) {
        router.push({
            name: 'study',
            query: {
                ...route.query,
                lesson: lessons.value[currentIndex.value].id
            }
        });
    }
};

// Lifecycle hooks
onBeforeMount(() => {
    fetchCourse()
    fetchCourseByUser()
});

// Watch for route changes
watch(() => route.query.lesson, (newLessonId) => {
    if (newLessonId && lessons.value.length > 0) {
        const index = findLessonIndex(newLessonId);
        if (index !== -1 && index !== currentIndex.value) {
            selectPdf(index);
        }
    }
});
</script>