<template>
  <DefaultLayout>
    <div class="container mx-auto p-6 max-w-7xl">
      <!-- Header -->
      <div class="mb-8">
        <h1 class="text-2xl font-bold text-gray-800">Quản lý điểm số</h1>
        <p class="text-gray-600 mt-2">
          Theo dõi và cập nhật điểm số của học viên
        </p>
      </div>

      <!-- Modal cập nhật điểm -->
      <div
        v-if="showModal"
        class="fixed inset-0 bg-gray-900/50 backdrop-blur-sm flex items-center justify-center z-50"
      >
        <div
          class="bg-white rounded-xl shadow-2xl w-full max-w-md transform transition-all"
        >
          <!-- Modal Header -->
          <div class="px-6 py-4 border-b border-gray-100">
            <h2 class="text-xl font-semibold text-gray-800">Cập nhật điểm</h2>
          </div>

          <!-- Modal Body -->
          <form @submit.prevent="handleUpdate" class="p-6">
            <div class="space-y-5">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1"
                  >Học viên</label
                >
                <input
                  v-model="formData.student"
                  type="text"
                  class="w-full px-4 py-2.5 rounded-lg border border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-colors"
                  required
                  placeholder="Nhập tên học viên"
                />
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1"
                  >Điểm số</label
                >
                <input
                  v-model.number="formData.score"
                  type="number"
                  class="w-full px-4 py-2.5 rounded-lg border border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-colors"
                  required
                  min="0"
                  max="100"
                  placeholder="Nhập điểm (0-100)"
                />
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1"
                  >Bài kiểm tra</label
                >
                <select
                  v-model="formData.quiz_id"
                  class="w-full px-4 py-2.5 rounded-lg border border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-colors"
                  required
                >
                  <option value="" disabled>Chọn bài kiểm tra</option>
                  <option
                    v-for="quiz in quizzes"
                    :key="quiz.id"
                    :value="quiz.id"
                  >
                    {{ quiz.name }}
                  </option>
                </select>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1"
                  >Khóa học</label
                >
                <select
                  v-model="formData.course_id"
                  class="w-full px-4 py-2.5 rounded-lg border border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-colors"
                  required
                >
                  <option value="" disabled>Chọn khóa học</option>
                  <option
                    v-for="course in courses"
                    :key="course.id"
                    :value="course.id"
                  >
                    {{ course.name }}
                  </option>
                </select>
              </div>
            </div>

            <!-- Modal Footer -->
            <div class="flex justify-end gap-3 mt-8">
              <button
                type="button"
                @click="closeModal"
                class="px-4 py-2 text-sm font-medium text-gray-700 bg-gray-100 rounded-lg hover:bg-gray-200 focus:outline-none focus:ring-2 focus:ring-gray-400 transition-colors"
              >
                Hủy
              </button>
              <button
                type="submit"
                class="px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-lg hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 transition-colors"
              >
                Cập nhật
              </button>
            </div>
          </form>
        </div>
      </div>

      <!-- Bảng điểm -->
      <div class="bg-white rounded-xl shadow-md overflow-hidden">
        <div class="overflow-x-auto">
          <table class="min-w-full divide-y divide-gray-200">
            <thead class="bg-gray-50">
              <tr>
                <th
                  class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                >
                  ID
                </th>
                <th
                  class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                >
                  Học viên
                </th>
                <th
                  class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                >
                  Điểm
                </th>
                <th
                  class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                >
                  Bài kiểm tra
                </th>
                <th
                  class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                >
                  Khóa học
                </th>
                <th
                  class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                >
                  Thao tác
                </th>
              </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
              <tr
                v-for="score in scores"
                :key="score.uuid"
                class="hover:bg-gray-50"
              >
                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                  {{ score.uuid }}
                </td>
                <td
                  class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900"
                >
                  {{ score.student }}
                </td>
                <td class="px-6 py-4 whitespace-nowrap">
                  <span
                    class="px-2.5 py-1 rounded-full text-sm font-medium"
                    :class="{
                      'bg-red-100 text-red-800': score.score < 50,
                      'bg-yellow-100 text-yellow-800':
                        score.score >= 50 && score.score < 80,
                      'bg-green-100 text-green-800': score.score >= 80,
                    }"
                  >
                    {{ score.score }}
                  </span>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                  {{ getQuizName(score.quiz_id) }}
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                  {{ getCourseName(score.course_id) }}
                </td>
                <td
                  class="px-6 py-4 whitespace-nowrap text-sm font-medium space-x-3"
                >
                  <button
                    @click="editScore(score)"
                    class=" text-indigo-600 hover:text-indigo-900 bg-indigo-50 px-3 py-1 rounded-md hover:bg-indigo-100 transition-colors duration-200"
                  >
                  <font-awesome-icon :icon="['fas', 'pen']" />

                  </button>
                  <button
                    @click="deleteScore(score.uuid)"
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

<script setup>
// Script remains the same as before
import { ref, reactive } from "vue";
import DefaultLayout from "@/layouts/DefaultLayout.vue";

const quizzes = ref([
  { id: "Q1", name: "Kiểm tra Toán" },
  { id: "Q2", name: "Kiểm tra Lý" },
  { id: "Q3", name: "Kiểm tra Hóa" },
]);

const courses = ref([
  { id: "C1", name: "Khóa học Toán" },
  { id: "C2", name: "Khóa học Lý" },
  { id: "C3", name: "Khóa học Hóa" },
]);

// State
const scores = ref([
  {
    uuid: 1,
    student: "Long",
    score: 10,
    quiz_id: "Q1",
    course_id: "C1",
  },
]);
const showModal = ref(false);
const editingId = ref(null);

const formData = reactive({
  student: "",
  score: 0,
  quiz_id: "",
  course_id: "",
});

const getQuizName = (quizId) => {
  const quiz = quizzes.value.find((q) => q.id === quizId);
  return quiz ? quiz.name : quizId;
};

const getCourseName = (courseId) => {
  const course = courses.value.find((c) => c.id === courseId);
  return course ? course.name : courseId;
};

const resetForm = () => {
  formData.student = "";
  formData.score = 0;
  formData.quiz_id = "";
  formData.course_id = "";
  editingId.value = null;
};

const closeModal = () => {
  showModal.value = false;
  resetForm();
};

const editScore = (score) => {
  editingId.value = score.uuid;
  formData.student = score.student;
  formData.score = score.score;
  formData.quiz_id = score.quiz_id;
  formData.course_id = score.course_id;
  showModal.value = true;
};

const handleUpdate = () => {
  const index = scores.value.findIndex((s) => s.uuid === editingId.value);
  if (index !== -1) {
    scores.value[index] = {
      ...scores.value[index],
      student: formData.student,
      score: formData.score,
      quiz_id: formData.quiz_id,
      course_id: formData.course_id,
    };
  }
  closeModal();
};

const deleteScore = (uuid) => {
  if (confirm("Bạn có chắc chắn muốn xóa?")) {
    scores.value = scores.value.filter((s) => s.uuid !== uuid);
  }
};
</script>
