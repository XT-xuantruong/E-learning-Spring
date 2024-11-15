<script setup>
import { ref, computed } from "vue";
import DefaultLayout from "@/layouts/DefaultLayout.vue";

const teachers = ref([
  {
    id: 1,
    first_name: "John",
    last_name: "Doe",
    avatar: "/api/placeholder/100/100",
    role: "teacher",
    email: "john.doe@school.com",
  },
  {
    id: 2,
    first_name: "Jane",
    last_name: "Smith",
    avatar: "/api/placeholder/100/100",
    role: "teacher",
    email: "jane.smith@school.com",
  },
]);

const formData = ref({
  first_name: "",
  last_name: "",
  avatar: "/api/placeholder/100/100",
  role: "teacher",
  email: "",
});

const isEditing = ref(false);
const selectedTeacherId = ref(null);
const showModal = ref(false);
const searchQuery = ref("");

const filteredTeachers = computed(() => {
  return teachers.value.filter(
    (teacher) =>
      teacher.first_name
        .toLowerCase()
        .includes(searchQuery.value.toLowerCase()) ||
      teacher.last_name
        .toLowerCase()
        .includes(searchQuery.value.toLowerCase()) ||
      teacher.email.toLowerCase().includes(searchQuery.value.toLowerCase())
  );
});

const updateTeacher = () => {
  const index = teachers.value.findIndex(
    (t) => t.id === selectedTeacherId.value
  );
  if (index !== -1) {
    teachers.value[index] = {
      id: selectedTeacherId.value,
      ...formData.value,
    };
  }
  resetForm();
};

const deleteTeacher = (id) => {
  if (confirm("Bạn có chắc muốn xóa giáo viên này?")) {
    teachers.value = teachers.value.filter((t) => t.id !== id);
  }
};

const editTeacher = (teacher) => {
  selectedTeacherId.value = teacher.id;
  formData.value = { ...teacher };
  isEditing.value = true;
  showModal.value = true;
};

const resetForm = () => {
  formData.value = {
    first_name: "",
    last_name: "",
    avatar: "/api/placeholder/100/100",
    role: "teacher",
    email: "",
  };
  isEditing.value = false;
  selectedTeacherId.value = null;
  showModal.value = false;
};

const handleAvatarUpload = (event) => {
  const file = event.target.files[0];
  if (file) {
    formData.value.avatar = "/api/placeholder/100/100";
  }
};

const handleClickOutside = (event) => {
  if (event.target.classList.contains("modal-overlay")) {
    resetForm();
  }
};
</script>

<template>
  <DefaultLayout>
    <div class="min-h-screen bg-gray-50 p-6">
      <!-- Main Container -->
      <div class="max-w-7xl mx-auto">
        <!-- Header Section -->
        <div class="mb-8">
          <div class="flex justify-between items-center mb-6">
            <h1 class="text-3xl font-bold text-gray-900">Quản lý giáo viên</h1>
          </div>

          <!-- Search and Filter Section -->
          <div class="relative mb-6">
            <input
              type="text"
              v-model="searchQuery"
              placeholder="Tìm kiếm giáo viên..."
              class="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
            />
            <div
              class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none"
            >
              <svg
                class="h-5 w-5 text-gray-400"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
                />
              </svg>
            </div>
          </div>
        </div>

        <!-- Teachers Grid -->
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <div
            v-for="teacher in filteredTeachers"
            :key="teacher.id"
            class="bg-white rounded-lg shadow-sm hover:shadow-md transition-shadow duration-200"
          >
            <div class="p-6">
              <div class="flex items-center space-x-4">
                <img
                  :src="teacher.avatar"
                  alt="avatar"
                  class="w-16 h-16 rounded-full object-cover border-2 border-gray-200"
                />
                <div class="flex-1 min-w-0">
                  <p class="text-lg font-semibold text-gray-900 truncate">
                    {{ teacher.first_name }} {{ teacher.last_name }}
                  </p>
                  <p class="text-sm text-gray-500 truncate">
                    {{ teacher.email }}
                  </p>
                  <p class="text-xs text-gray-400 mt-1">Giáo viên</p>
                </div>
              </div>

              <div class="mt-4 flex justify-end space-x-2">
                <button
                  @click="editTeacher(teacher)"
                  class="inline-flex items-center px-3 py-1.5 border border-gray-300 text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                >
                  <svg
                    class="h-4 w-4 mr-1.5"
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M15.232 5.232l3.536 3.536m-2.036-5.036a2.5 2.5 0 113.536 3.536L6.5 21.036H3v-3.572L16.732 3.732z"
                    />
                  </svg>
                  Sửa
                </button>
                <button
                  @click="deleteTeacher(teacher.id)"
                  class="inline-flex items-center px-3 py-1.5 border border-red-300 text-sm font-medium rounded-md text-red-700 bg-white hover:bg-red-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500"
                >
                  <svg
                    class="h-4 w-4 mr-1.5"
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"
                    />
                  </svg>
                  Xóa
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Modal -->
      <div
        v-if="showModal"
        class="modal-overlay fixed inset-0 bg-gray-500 bg-opacity-75 backdrop-blur-sm flex items-center justify-center z-50"
        @click="handleClickOutside"
      >
        <div
          class="bg-white rounded-xl shadow-xl w-full max-w-2xl mx-4 transform transition-all"
          @click.stop
        >
          <!-- Modal Header -->
          <div class="px-6 py-4 border-b border-gray-200">
            <h2 class="text-xl font-semibold text-gray-900">
              {{ isEditing ? "Sửa thông tin giáo viên" : "Thêm giáo viên mới" }}
            </h2>
          </div>

          <!-- Modal Body -->
          <div class="px-6 py-4">
            <form @submit.prevent="updateTeacher()">
              <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2"
                    >Họ</label
                  >
                  <input
                    v-model="formData.first_name"
                    type="text"
                    class="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring-indigo-500 focus:border-indigo-500"
                    required
                  />
                </div>
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2"
                    >Tên</label
                  >
                  <input
                    v-model="formData.last_name"
                    type="text"
                    class="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring-indigo-500 focus:border-indigo-500"
                    required
                  />
                </div>
                <div class="md:col-span-2">
                  <label class="block text-sm font-medium text-gray-700 mb-2"
                    >Email</label
                  >
                  <input
                    v-model="formData.email"
                    type="email"
                    class="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring-indigo-500 focus:border-indigo-500"
                    required
                  />
                </div>
                <div class="md:col-span-2">
                  <label class="block text-sm font-medium text-gray-700 mb-2"
                    >Ảnh đại diện</label
                  >
                  <div class="flex items-center space-x-4">
                    <img
                      :src="formData.avatar"
                      alt="Preview"
                      class="h-12 w-12 rounded-full object-cover"
                    />
                    <input
                      type="file"
                      @change="handleAvatarUpload"
                      accept="image/*"
                      class="flex-1 px-3 py-2 border border-gray-300 rounded-md focus:ring-indigo-500 focus:border-indigo-500"
                    />
                  </div>
                </div>
              </div>

              <!-- Modal Footer -->
              <div class="mt-6 flex justify-end space-x-3">
                <button
                  type="button"
                  @click="resetForm"
                  class="px-4 py-2 border border-gray-300 rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                >
                  Hủy
                </button>
                <button
                  type="submit"
                  class="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                >
                  {{ isEditing ? "Cập nhật" : "Lưu" }}
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </DefaultLayout>
</template>

<style scoped>
.modal-overlay {
  backdrop-filter: blur(4px);
}
</style>
