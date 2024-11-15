<template>
    <DefaultLayout>
        <!-- Progress Section -->
        <CourseProgress :course="course" :completedLessons="completedLessons" :totalLessons="lessons.length" />

        <div class="grid grid-cols-3">
            <!-- PDF Viewer Section -->
            <div class="m-5 p-5 bg-white border rounded col-span-2">
                <PdfViewer :pdfUrl="pdfUrl" :currentIndex="currentIndex" :lessons="lessons" @prev="prevPdf"
                    @next="nextPdf" />
            </div>

            <!-- Lessons List Section -->
            <LessonsList :lessons="lessons" :currentIndex="currentIndex" :completedLessons="completedLessons"
                @select-lesson="selectPdf" />
        </div>
    </DefaultLayout>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import DefaultLayout from '@/layouts/user/DefaultLayout.vue';
import lectures from '@/faker/lectures';
import courses from '@/faker/course';
import CourseProgress from '@/components/course/CourseProgress.vue';
import PdfViewer from '@/components/pdf/PdfViewer.vue';
import LessonsList from '@/components/course/LessonsList.vue';

// Router setup
const route = useRoute();
const router = useRouter();

// State
const currentIndex = ref(-1);
const pdfUrl = ref('');
const completedLessons = ref(2);

// Computed properties
const course = computed(() => courses.find(c => c.id == route.query.course));
const lessons = computed(() => lectures.filter(l => l.course_id == course.value.id));

// Find lesson index by ID
const findLessonIndex = (lessonId) => {
    return lessons.value.findIndex(lesson => lesson.id == lessonId);
};

// Lifecycle hooks
onMounted(() => {
    // If there's a lesson ID in the query, select that lesson
    const lessonId = route.query.lesson;
    if (lessonId) {
        const index = findLessonIndex(lessonId);
        if (index !== -1) {
            selectPdf(index);
        }
    } else if (lessons.value?.length > 0) {
        // If no lesson ID, start from the last completed lesson + 1
        const nextLessonIndex = Math.min(completedLessons.value, lessons.value.length - 1);
        selectPdf(nextLessonIndex);
        updateRoute(nextLessonIndex);
    }
});

// Watch for route changes
watch(
    () => route.query.lesson,
    (newLessonId) => {
        if (newLessonId) {
            const index = findLessonIndex(newLessonId);
            if (index !== -1 && index !== currentIndex.value) {
                selectPdf(index);
            }
        }
    }
);

// Methods
const selectPdf = (index) => {
    currentIndex.value = index;
    pdfUrl.value = lessons.value[index]?.content;
};

const nextPdf = () => {
    if (currentIndex.value < lessons.value.length - 1) {
        // Move to next lesson
        selectPdf(currentIndex.value + 1);

        // Update completed lessons
        if (currentIndex.value > completedLessons.value) {
            completedLessons.value = currentIndex.value;
        }

        // If this is the last lesson, mark it as completed when reached
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
    router.push({
        name: 'study',
        query: {
            ...route.query,
            lesson: lessons.value[currentIndex.value].id
        }
    });
};
</script>