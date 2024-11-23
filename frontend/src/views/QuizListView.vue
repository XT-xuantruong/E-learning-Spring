<script setup>
import { ref, reactive, computed, onMounted } from "vue";
import QuizModal from "@/components/QuizModal.vue";
import DefaultLayout from "@/layouts/DefaultLayout.vue";

// State
const quizzes = ref([
  {
    id: 1,
    title: "Quiz về Lịch sử Việt Nam",
    course_id: 1,
    course_name: "Lịch sử Việt Nam",
    created_at: "2023-04-01",
    questions: [
      {
        id: 1,
        question: "Năm nào Việt Nam giành được độc lập?",
        answers: [
          { id: 1, answer: "1954", correct: true },
          { id: 2, answer: "1945", correct: false },
          { id: 3, answer: "1975", correct: false },
          { id: 4, answer: "1946", correct: false },
        ],
      },
      {
        id: 2,
        question:
          "Ai là người đứng đầu Chính phủ Lâm thời Việt Nam Dân chủ Cộng hòa?",
        answers: [
          { id: 1, answer: "Hồ Chí Minh", correct: true },
          { id: 2, answer: "Võ Nguyên Giáp", correct: false },
          { id: 3, answer: "Nguyễn Tất Thành", correct: false },
          { id: 4, answer: "Trần Phú", correct: false },
        ],
      },
    ],
  },
  {
    id: 2,
    title: "Quiz về Toán học cơ bản",
    course_id: 2,
    course_name: "Toán học cơ bản",
    created_at: "2023-05-15",
    questions: [
      {
        id: 1,
        question: "Phương trình 2x + 3 = 11 có nghiệm là:",
        answers: [
          { id: 1, answer: "4", correct: true },
          { id: 2, answer: "3", correct: false },
          { id: 3, answer: "5", correct: false },
          { id: 4, answer: "2", correct: false },
        ],
      },
      {
        id: 2,
        question: "Tích của hai số 3 và 4 là:",
        answers: [
          { id: 1, answer: "12", correct: true },
          { id: 2, answer: "7", correct: false },
          { id: 3, answer: "10", correct: false },
          { id: 4, answer: "16", correct: false },
        ],
      },
    ],
  },
  {
    id: 3,
    title: "Quiz về Lập trình Python",
    course_id: 3,
    course_name: "Lập trình Python",
    created_at: "2023-06-30",
    questions: [
      {
        id: 1,
        question: 'Câu lệnh print("Hello, World!") in Python in ra:',
        answers: [
          { id: 1, answer: "Hello, World!", correct: true },
          { id: 2, answer: 'print("Hello, World!")', correct: false },
          { id: 3, answer: "Không in ra gì", correct: false },
          { id: 4, answer: "Lỗi cú pháp", correct: false },
        ],
      },
      {
        id: 2,
        question:
          "Để khai báo một biến x có giá trị 5 trong Python, ta dùng câu lệnh:",
        answers: [
          { id: 1, answer: "x = 5", correct: true },
          { id: 2, answer: "var x = 5", correct: false },
          { id: 3, answer: "let x = 5", correct: false },
          { id: 4, answer: "const x = 5", correct: false },
        ],
      },
    ],
  },
  {
    id: 4,
    title: "Quiz về Lập trình Python",
    course_id: 3,
    course_name: "Lập trình Python",
    created_at: "2023-06-30",
    questions: [
      {
        id: 1,
        question: "Phương thức append() trong Python được dùng để:",
        answers: [
          {
            id: 1,
            answer: "Thêm một phần tử vào cuối danh sách",
            correct: true,
          },
          {
            id: 2,
            answer: "Loại bỏ một phần tử khỏi danh sách",
            correct: false,
          },
          {
            id: 3,
            answer: "Sắp xếp các phần tử trong danh sách",
            correct: false,
          },
          {
            id: 4,
            answer: "Tìm kiếm một phần tử trong danh sách",
            correct: false,
          },
        ],
      },
      {
        id: 2,
        question: "Toán tử ** trong Python dùng để:",
        answers: [
          { id: 1, answer: "Lũy thừa", correct: true },
          { id: 2, answer: "Nhân", correct: false },
          { id: 3, answer: "Chia", correct: false },
          { id: 4, answer: "Trừ", correct: false },
        ],
      },
    ],
  },
]);
const courses = ref([
  { id: 1, name: "Lịch sử Việt Nam" },
  { id: 2, name: "Toán học cơ bản" },
  { id: 3, name: "Lập trình Python" },
]);
const isEditing = ref(false);
const quizForm = reactive({
  id: null,
  title: "",
  course_id: "",
});
const isModalOpen = ref(false);
const currentQuiz = ref(null);
const searchQuery = ref("");

// Computed properties
const quizzesByCourseName = computed(() => {
  return quizzes.value.reduce((acc, quiz) => {
    if (!acc[quiz.course_name]) {
      acc[quiz.course_name] = [];
    }
    acc[quiz.course_name].push(quiz);
    return acc;
  }, {});
});

const filteredQuizzes = computed(() => {
  const query = searchQuery.value.toLowerCase().trim();
  if (!query) return quizzesByCourseName.value;

  return Object.fromEntries(
    Object.entries(quizzesByCourseName.value)
      .map(([courseName, quizList]) => [
        courseName,
        quizList.filter(
          (quiz) =>
            quiz.title.toLowerCase().includes(query) ||
            courseName.toLowerCase().includes(query) ||
            quiz.id.toString().includes(query)
        ),
      ])
      .filter(([, quizzes]) => quizzes.length > 0)
  );
});

// CRUD operations
const handleSubmit = () => {
  if (isEditing.value) {
    updateQuiz();
  } else {
    createQuiz();
  }
  resetForm();
  isModalOpen.value = false;
};

const createQuiz = () => {
  const newQuiz = {
    id: quizzes.value.length + 1,
    title: quizForm.title,
    course_id: quizForm.course_id,
    course_name: courses.value.find((c) => c.id === quizForm.course_id).name,
    created_at: new Date().toISOString().slice(0, 10),
    questions: [],
  };
  quizzes.value.push(newQuiz);
  alert("Thêm quiz mới thành công!");
};

const updateQuiz = () => {
  const updatedQuiz = {
    id: quizForm.id,
    title: quizForm.title,
    course_id: quizForm.course_id,
    course_name: courses.value.find((c) => c.id === quizForm.course_id).name,
    created_at: quizzes.value.find((q) => q.id === quizForm.id).created_at,
    questions: quizzes.value.find((q) => q.id === quizForm.id).questions,
  };
  const index = quizzes.value.findIndex((q) => q.id === updatedQuiz.id);
  quizzes.value.splice(index, 1, updatedQuiz);
  alert("Cập nhật quiz thành công!");
};

const editQuiz = (quiz) => {
  quizForm.id = quiz.id;
  quizForm.title = quiz.title;
  quizForm.course_id = quiz.course_id;
  isEditing.value = true;
  isModalOpen.value = true;
};

const deleteQuiz = (id) => {
  if (!confirm("Bạn có chắc muốn xóa quiz này?")) return;
  const index = quizzes.value.findIndex((q) => q.id === id);
  quizzes.value.splice(index, 1);
  alert("Xóa quiz thành công!");
};

const viewQuestionAnswers = (quiz) => {
  currentQuiz.value = quiz;
  isModalOpen.value = true;
};

const cancelEdit = () => {
  resetForm();
  isModalOpen.value = false;
};

const resetForm = () => {
  quizForm.id = null;
  quizForm.title = "";
  quizForm.course_id = "";
  isEditing.value = false;
};

// Utilities
const formatDate = (date) => {
  return new Date(date).toLocaleDateString("vi-VN");
};

// Lifecycle hooks
onMounted(() => {
  // Fetch data from API or use demo data
});

const closeModal = () => {
  if (currentQuiz.value) {
    currentQuiz.value = null;
    isModalOpen.value = false;
  }
  isModalOpen.value = false;
};
</script>

<template>
  <DefaultLayout>
    <div class="container mx-auto p-4">
      <h1 class="text-2xl font-bold mb-4">Quản Lý Quiz</h1>

      <!-- Quiz form -->
      <div class="flex justify-end mb-4">
        <button
          @click="
            resetForm();
            isModalOpen = true;
          "
          class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
        >
          Thêm Quiz Mới
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
          <h2 class="text-xl mb-3">Danh sách Quiz</h2>
          <input
            type="text"
            v-model="searchQuery"
            placeholder="Search..."
            class="pl-10 pr-4 py-2 rounded-lg border border-gray-200"
          />
        </div>

        <div
          v-for="(quizList, courseName) in filteredQuizzes"
          :key="courseName"
        >
          <h3 class="text-lg mb-2">{{ courseName }}</h3>
          <table class="w-full">
            <thead>
              <tr class="bg-gray-100">
                <th class="p-2 text-left">ID</th>
                <th class="p-2 text-left">Tiêu đề</th>
                <th class="p-2 text-left">Ngày tạo</th>
                <th class="p-2 text-left">Thao tác</th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="quiz in quizList"
                :key="quiz.id"
                class="border-b"
              >
                <td class="p-2">{{ quiz.id }}</td>
                <td class="p-2">{{ quiz.title }}</td>
                <td class="p-2">{{ formatDate(quiz.created_at) }}</td>
                <td class="p-2">
                  <button
                    @click="editQuiz(quiz)"
                    class="mr-2 text-indigo-600 hover:text-indigo-900 bg-indigo-50 px-3 py-1 rounded-md hover:bg-indigo-100 transition-colors duration-200"
                  >
                    <font-awesome-icon :icon="['fas', 'pen']" />
                  </button>
                  <button
                    @click="viewQuestionAnswers(quiz)"
                    class="mr-2 text-yellow-600 hover:text--yellow-900 bg-indigo-50 px-3 py-1 rounded-md hover:bg-indigo-100 transition-colors duration-200"
                  >
                    <font-awesome-icon :icon="['fas', 'eye']" />
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
