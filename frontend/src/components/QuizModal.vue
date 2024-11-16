<template>
  <Teleport to="body">
    <div
      v-if="isModalOpen"
      class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50"
    >
      <div v-if="currentQuiz" class="bg-white p-6 rounded shadow-lg w-full max-w-md">
        <h2 class="text-xl font-bold mb-4">{{ currentQuiz.title }}</h2>
        <div v-for="question in currentQuiz.questions" :key="question.id" class="space-y-2">
          <h3 class="font-medium">{{ question.question }}</h3>
          <ul class="space-y-1">
            <li v-for="answer in question.answers" :key="answer.id" class="flex items-center">
              <input
                type="radio"
                :name="`question-${question.id}`"
                :value="answer.id"
                :checked="answer.correct"
                disabled
                class="mr-2"
              />
              <span>{{ answer.answer }}</span>
            </li>
          </ul>
        </div>
        <div class="flex justify-end mt-4">
          <button
            @click="closeModal"
            class="bg-gray-500 text-white px-4 py-2 rounded hover:bg-gray-600"
          >
            Đóng
          </button>
        </div>
      </div>
      <div v-else class="bg-white p-6 rounded shadow-lg w-full max-w-md">
        <h2 class="text-xl font-bold mb-4">
          {{ isEditing ? 'Sửa Quiz' : 'Thêm Quiz Mới' }}
        </h2>
        <form @submit.prevent="handleSubmit" class="space-y-4">
          <div>
            <label class="block mb-1">Tiêu đề:</label>
            <input
              v-model="quizForm.title"
              type="text"
              class="w-full p-2 border rounded"
              required
            />
          </div>
          <div>
            <label class="block mb-1">Khóa học:</label>
            <select
              v-model="quizForm.course_id"
              class="w-full p-2 border rounded"
              required
            >
              <option value="">Chọn khóa học</option>
              <option v-for="course in courses" :key="course.id" :value="course.id">
                {{ course.name }}
              </option>
            </select>
          </div>
          <div class="flex justify-end">
            <button
              type="submit"
              class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 mr-2"
            >
              {{ isEditing ? 'Cập nhật' : 'Thêm mới' }}
            </button>
            <button
              type="button"
              @click="closeModal"
              class="bg-gray-500 text-white px-4 py-2 rounded hover:bg-gray-600"
            >
              Hủy
            </button>
          </div>
        </form>
      </div>
    </div>
  </Teleport>
</template>

<script setup>
defineProps({
  isModalOpen: {
    type: Boolean,
    required: true
  },
  isEditing: {
    type: Boolean,
    required: true
  },
  quizForm: {
    type: Object,
    required: true
  },
  currentQuiz: {
    type: Object,
    required: false
  },
  courses: {
    type: Array,
    required: true
  },
  handleSubmit: {
    type: Function,
    required: true
  },
  closeModal: {
    type: Function,
    required: true
  }
})
</script>