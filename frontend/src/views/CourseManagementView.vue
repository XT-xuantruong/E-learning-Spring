<template>
  <DefaultLayout>
    <div class="p-6 bg-gray-50 min-h-screen">
      <div class="max-w-7xl mx-auto">
        <!-- Header -->
        <div class="flex justify-between items-center mb-8">
          <h2 class="text-2xl font-bold text-gray-800">Quản lý khóa học</h2>
          <button
            @click="openAddModal"
            class="px-4 py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700 transition-colors duration-200 flex items-center gap-2"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              class="h-5 w-5"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M12 6v6m0 0v6m0-6h6m-6 0H6"
              />
            </svg>
            Thêm khóa học mới
          </button>
        </div>

        <!-- Course List -->
        <div class="bg-white rounded-lg shadow overflow-hidden">
          <div class="overflow-x-auto">
            <table class="min-w-full divide-y divide-gray-200">
              <thead class="bg-gray-50">
                <tr>
                  <th
                    class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                  >
                    Hình ảnh
                  </th>
                  <th
                    class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                  >
                    Tiêu đề
                  </th>
                  <th
                    class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                  >
                    Mô tả
                  </th>
                  <th
                    class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                  >
                    Danh mục
                  </th>
                  <th
                    class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                  >
                    Thao tác
                  </th>
                </tr>
              </thead>
              <tbody class="bg-white divide-y divide-gray-200">
                <tr
                  v-for="course in courses"
                  :key="course.id"
                  class="hover:bg-gray-50"
                >
                  <td class="px-6 py-4 whitespace-nowrap">
                    <img
                      :src="course.thumbnail"
                      alt="thumbnail"
                      class="h-12 w-12 rounded-lg object-cover"
                    />
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                    {{ course.title }}
                  </td>
                  <td class="px-6 py-4 text-sm text-gray-500 max-w-xs truncate">
                    {{ course.description }}
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                    {{ getCategoryName(course.category_id) }}
                  </td>
                  <td
                    class="px-6 py-4 whitespace-nowrap text-sm font-medium space-x-2"
                  >
                    <button
                      @click="openEditModal(course)"
                      class="text-indigo-600 hover:text-indigo-900 bg-indigo-50 px-3 py-1 rounded-md hover:bg-indigo-100 transition-colors duration-200"
                    >
                    <font-awesome-icon :icon="['fas', 'pen']" />
                    </button>
                    <button
                      @click="deleteCourse(course.id)"
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

        <!-- Modal -->
        <div
          v-if="showModal"
          class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50"
        >
          <div
            class="bg-white rounded-lg max-w-md w-full p-6 transform transition-all"
            @click.stop
          >
            <h3 class="text-lg font-medium leading-6 text-gray-900 mb-4">
              {{ isEditing ? "Sửa khóa học" : "Thêm khóa học mới" }}
            </h3>

            <form @submit.prevent="handleSubmit" class="space-y-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">
                  Tiêu đề
                </label>
                <input
                  v-model="formData.title"
                  type="text"
                  required
                  class="w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                />
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">
                  Mô tả
                </label>
                <textarea
                  v-model="formData.description"
                  rows="3"
                  required
                  class="w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                ></textarea>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">
                  Hình ảnh
                </label>
                <div class="mt-1 flex items-center space-x-4">
                  <div
                    class="w-24 h-24 border-2 border-gray-300 border-dashed rounded-lg overflow-hidden flex items-center justify-center relative"
                  >
                    <img
                      v-if="imagePreview"
                      :src="imagePreview"
                      alt="Preview"
                      class="w-full h-full object-cover"
                    />
                    <div v-else class="text-gray-400 text-sm text-center p-2">
                      Chưa có ảnh
                    </div>
                    <input
                      type="file"
                      @change="handleImageChange"
                      accept="image/*"
                      class="absolute inset-0 w-full h-full opacity-0 cursor-pointer"
                    />
                  </div>
                  <button
                    type="button"
                    @click="removeImage"
                    v-if="imagePreview"
                    class="text-sm text-red-600 hover:text-red-800"
                  >
                    Xóa ảnh
                  </button>
                </div>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">
                  Danh mục
                </label>
                <select
                  v-model="formData.category_id"
                  required
                  class="w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                >
                  <option
                    v-for="category in categories"
                    :key="category.id"
                    :value="category.id"
                  >
                    {{ category.name }}
                  </option>
                </select>
              </div>

              <div class="flex justify-end space-x-3 pt-4">
                <button
                  type="button"
                  @click="closeModal"
                  class="px-4 py-2 border border-gray-300 rounded-md text-sm font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                >
                  Hủy
                </button>
                <button
                  type="submit"
                  class="px-4 py-2 bg-indigo-600 text-white rounded-md text-sm font-medium hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                >
                  {{ isEditing ? "Cập nhật" : "Thêm mới" }}
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </DefaultLayout>
</template>

<script setup>
import { ref, reactive } from "vue";
import DefaultLayout from "@/layouts/DefaultLayout.vue";

// Categories data
const categories = ref([
  { id: 1, name: "Lập trình Web" },
  { id: 2, name: "Lập trình Mobile" },
  { id: 3, name: "Cơ sở dữ liệu" },
  { id: 4, name: "DevOps" },
]);

// Course data
const courses = ref([
  {
    id: 1,
    title: "Vue.js Cơ bản",
    description:
      "Khóa học Vue.js cho người mới bắt đầu với các khái niệm cơ bản và thực hành",
    thumbnail: "https://picsum.photos/200",
    category_id: 1,
  },
  {
    id: 2,
    title: "React Native Master",
    description:
      "Học phát triển ứng dụng di động với React Native từ cơ bản đến nâng cao",
    thumbnail: "https://picsum.photos/200",
    category_id: 2,
  },
]);

const showModal = ref(false);
const isEditing = ref(false);
const imagePreview = ref(null);
const selectedFile = ref(null);

const formData = reactive({
  id: null,
  title: "",
  description: "",
  thumbnail: "",
  category_id: "",
});

// Methods
const handleImageChange = (event) => {
  const file = event.target.files[0];
  if (file) {
    selectedFile.value = file;
    const reader = new FileReader();
    reader.onload = (e) => {
      imagePreview.value = e.target.result;
      formData.thumbnail = e.target.result; // Store base64 image
    };
    reader.readAsDataURL(file);
  }
};

const removeImage = () => {
  imagePreview.value = null;
  selectedFile.value = null;
  formData.thumbnail = "";
};

const getCategoryName = (categoryId) => {
  const category = categories.value.find((c) => c.id === categoryId);
  return category ? category.name : "Không có danh mục";
};

const openAddModal = () => {
  isEditing.value = false;
  resetForm();
  showModal.value = true;
};

const openEditModal = (course) => {
  isEditing.value = true;
  formData.id = course.id;
  formData.title = course.title;
  formData.description = course.description;
  formData.thumbnail = course.thumbnail;
  formData.category_id = course.category_id;
  imagePreview.value = course.thumbnail;
  showModal.value = true;
};

const closeModal = () => {
  showModal.value = false;
  resetForm();
};

const resetForm = () => {
  formData.id = null;
  formData.title = "";
  formData.description = "";
  formData.thumbnail = "";
  formData.category_id = "";
  imagePreview.value = null;
  selectedFile.value = null;
};

const handleSubmit = () => {
  if (isEditing.value) {
    const index = courses.value.findIndex((c) => c.id === formData.id);
    if (index !== -1) {
      courses.value[index] = { ...formData };
    }
  } else {
    const newCourse = {
      ...formData,
      id: Math.max(0, ...courses.value.map((c) => c.id)) + 1,
    };
    courses.value.push(newCourse);
  }
  closeModal();
};

const deleteCourse = (id) => {
  if (confirm("Bạn có chắc chắn muốn xóa khóa học này?")) {
    courses.value = courses.value.filter((course) => course.id !== id);
  }
};
</script>
