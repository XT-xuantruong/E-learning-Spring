<script setup>
import { ref, reactive, computed, onBeforeMount } from "vue";
import QuizModal from "@/components/QuizModal.vue";
import DefaultLayout from "@/layouts/DefaultLayout.vue";
import courseServices from "@/services/courseServices";
import quizServices from "@/services/quizServices";

// State
const quizzes = ref([]);

const courses = ref([]);

const isEditing = ref(false);
const quizForm = reactive({
  id: null,
  title: "",
  course_id: null,
});
const isModalOpen = ref(false);
const currentQuiz = ref(null);
const searchQuery = ref("");

// Computed properties
const quizzesByCourseId = computed(() => {
  return quizzes.value.reduce((acc, quiz) => {
    if (!acc[quiz.course_id]) {
      acc[quiz.course_id] = [];
    }
    acc[quiz.course_id].push(quiz);
    return acc;
  }, {});
});

const filteredQuizzes = computed(() => {
  const query = searchQuery.value.toLowerCase().trim();
  if (!query) return quizzesByCourseId.value;

  return Object.fromEntries(
    Object.entries(quizzesByCourseId.value)
      .map(([courseId, quizList]) => [
        courseId,
        quizList.filter(
          (quiz) =>
            quiz.title.toLowerCase().includes(query) ||
            courses.value
              .find((c) => c.id === quiz.course_id)
              ?.title.toLowerCase()
              .includes(query) ||
            quiz.id.toString().includes(query)
        ),
      ])
      .filter(([, quizzes]) => quizzes.length > 0)
  );
});

// CRUD operations
const handleSubmit = async () => {
  if (isEditing.value) {
    updateQuiz();
  } else {
    await createQuiz();
  }
  resetForm();
  isModalOpen.value = false;
};

const createQuiz = async () => {
  await quizServices.create(quizForm);
  const newQuiz = {
    id: quizzes.value.length + 1,
    title: quizForm.title,
    course_id: quizForm.course_id,
    created_at: new Date().toISOString().slice(0, 10),
  };
  quizzes.value.push(newQuiz);
};

const updateQuiz = async () => {
  await quizServices.update(quizForm).then((res) => {
    const data = res.data.data;
    console.log(data);

    const updatedQuiz = {
      id: data.id,
      title: data.title,
      course_id: data.course.id,
      createdAt: formatDate(data.createdAt),
    };
    const index = quizzes.value.findIndex((q) => q.id === updatedQuiz.id);
    quizzes.value.splice(index, 1, updatedQuiz);
  });
};

const editQuiz = (quiz) => {
  quizForm.id = quiz.id;
  quizForm.title = quiz.title;
  quizForm.course_id = quiz.course_id;
  isEditing.value = true;
  isModalOpen.value = true;
};

const deleteQuiz = async (id) => {
  if (!confirm("Are you sure you want to delete this quiz?")) return;
  await quizServices.delete(id)
  const index = quizzes.value.findIndex((q) => q.id === id);
  quizzes.value.splice(index, 1);
};

const cancelEdit = () => {
  resetForm();
  isModalOpen.value = false;
};

const resetForm = () => {
  quizForm.id = null;
  quizForm.title = "";
  quizForm.course_id = null;
  isEditing.value = false;
};

// Utilities
const formatDate = (date) => {
  return new Date(date).toLocaleDateString("en-US");
};

// Lifecycle hooks
onBeforeMount(async () => {
  try {
    await quizServices.gets().then((res) => {
      const data = res.data.data;
      data.forEach((element) => {
        element.course_id = element.course.id;
      });
      quizzes.value = data;
    });

    const response = await courseServices.gets();
    const data = response.data.data;

    data.forEach((element) => {
      element.category_id = element.category_id.id;
      element.thumbnail = "http://localhost:8092/backend" + element.thumbnail;
    });

    courses.value = data;
    console.log(courses.value);
  } catch (error) {
    console.error("Error fetching courses:", error);
  }
});

const closeModal = () => {
  if (currentQuiz.value) {
    currentQuiz.value = null;
  }
  isModalOpen.value = false;
};
</script>

<template>
  <DefaultLayout>
    <div class="container mx-auto p-4">
      <h1 class="text-2xl font-bold mb-4">Quiz Management</h1>

      <!-- Quiz form -->
      <div class="flex justify-end mb-4">
        <button
          @click="
            resetForm();
            isModalOpen = true;
          "
          class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
        >
          Add New Quiz
        </button>
      </div>
      <QuizModal
        :isModalOpen="isModalOpen"
        :isEditing="isEditing"
        :quizForm="quizForm"
        :currentQuiz="currentQuiz"
        :courses="courses"
        :handleSubmit="handleSubmit"
        :closeModal="closeModal"
      />

      <!-- Quiz list -->
      <div class="bg-white p-4 rounded shadow">
        <div class="flex justify-between">
          <h2 class="text-xl mb-3">Quiz List</h2>
          <input
            type="text"
            v-model="searchQuery"
            placeholder="Search..."
            class="pl-10 pr-4 py-2 rounded-lg border border-gray-200"
          />
        </div>

        <div v-for="(quizList, courseId) in filteredQuizzes" :key="courseId">
          <h3 class="text-lg mb-2">
            {{ courses.find((c) => c.id === courseId)?.title }}
          </h3>
          <table class="w-full">
            <thead>
              <tr class="bg-gray-100">
                <th class="p-2 text-left">Title</th>
                <th class="p-2 text-left">Created Date</th>
                <th class="p-2 text-left">Actions</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="quiz in quizList" :key="quiz.id" class="border-b">
                <td class="p-2">{{ quiz.title }}</td>
                <td class="p-2">{{ formatDate(quiz.createdAt) }}</td>
                <td class="p-2">
                  <button
                    @click="editQuiz(quiz)"
                    class="mr-2 text-indigo-600 hover:text-indigo-900 bg-indigo-50 px-3 py-1 rounded-md hover:bg-indigo-100 transition-colors duration-200"
                  >
                    <font-awesome-icon :icon="['fas', 'pen']" />
                  </button>

                  <button
                    @click="deleteQuiz(quiz.id)"
                    class="text-red-600 hover:text-red-900 bg-red-50 px-3 py-1 rounded-md hover:bg-red-100 transition-colors duration-200"
                  >
                    <font-awesome-icon :icon="['fas', 'trash']" />
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </DefaultLayout>
</template>
