<template>
    <DefaultLayout>
        <!-- Progress Section -->
        <CourseProgress :course="currentCourse" :completedLessons="completedLessons"
            :totalLessons="lessons?.length || 0" :completedQuizzes="completedQuizzes"
            :totalQuizzes="quizInCourse?.length || 0" />

        <div class="grid grid-cols-3">
            <!-- PDF Viewer Section -->
            <div class="m-5 p-5 bg-white border rounded col-span-2">
                <PdfViewer :pdfUrl="pdfUrl" :currentIndex="currentIndex" :lessons="lessons || []" @prev="prevPdf"
                    @next="nextPdf" />
            </div>

            <!-- Lessons List Section -->
            <LessonsList :quizResultList="quizResultList" :quizs="quizInCourse || []" :lessons="lessons || []"
                :currentIndex="currentIndex" :completedLessons="completedLessons" @select-lesson="selectPdf" />
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
import quizResultServices from '@/services/quizResultServices';

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
const completedLessons = ref(0);
const quizResultList = ref([]);
const completedQuizzes = computed(() => {
    return quizResultList.value.filter(
        result => result.quiz.course.id === courseId && result.score !== null
    ).length;
});
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
const fetchQuizResult = async () => {
    try {
        const response = await quizResultServices.getByUser(user.user.id);
        quizResultList.value = response.data.data;
        console.log(quizResultList.value);

    } catch (error) {
        console.error('Error fetching course:', error);
        // You might want to add error handling UI here
    }
};
const fetchCourseByUser = async () => {
    try {
        const response = await courseEnrollmentServices.getbyuser({ userId: user.user.id });
        course.value = response.data.data.find(c => c.course.id === courseId);
        console.log("r" + response.data.data);

        if (course.value?.lastLecture) {
            const lastLectureIndex = lessons.value.findIndex(
                lesson => lesson.id === course.value.lastLecture
            );
            console.log("last" + lastLectureIndex);

            completedLessons.value = lastLectureIndex !== -1 ? lastLectureIndex + 1 : 0;
            console.log("get" + completedLessons.value);

        }
    } catch (error) {
        console.error('Error fetching course enrollment data:', error);
    }
};

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
const updateProgress = async (id, lectureId) => {
    await courseEnrollmentServices.update({ id, lastLecture: lectureId })
    // Kiểm tra nếu tất cả bài học và bài kiểm tra đã hoàn thành
    if (completedLessons.value === lessons.value.length &&
        completedQuizzes.value === quizInCourse.value.length) {
        // Cập nhật trạng thái isfinish
        await courseEnrollmentServices.update({ id, isFinish: true });
        console.log("Course completed!");
    }
}

const nextPdf = async () => {
    if (currentIndex.value < lessons.value.length - 1) {
        selectPdf(currentIndex.value + 1);

        // Cập nhật tiến độ
        if (currentIndex.value >= completedLessons.value) {
            completedLessons.value = currentIndex.value + 1;
            await updateProgress(course.value.id, lessons.value[currentIndex.value].id);
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
    fetchQuizResult()
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