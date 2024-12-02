<template>
  <DefaultLayout>
    <div class="p-6 bg-gray-50 min-h-screen">
      <div class="max-w-7xl mx-auto">
        <!-- Header -->
        <div class="flex justify-between items-center mb-8">
          <h2 class="text-2xl font-bold text-gray-800">Course Management</h2>
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
            Add New Course
          </button>
        </div>

        <!-- Course List -->
        <div class="bg-white rounded-lg shadow overflow-hidden">
          <div class="overflow-x-auto m-4">
            <!-- Search Bar -->
            <div class="hidden md:flex items-center mb-3">
              <div class="relative">
                <span class="absolute inset-y-0 left-0 pl-3 flex items-center">
                  <i class="fas fa-search text-gray-400"></i>
                </span>
                <input
                  type="text"
                  v-model="searchQuery"
                  placeholder="Search..."
                  class="pl-10 pr-4 py-2 rounded-lg border border-gray-200"
                />
              </div>
            </div>
            <table class="min-w-full divide-y divide-gray-200">
              <thead class="bg-gray-50">
                <tr>
                  <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Image
                  </th>
                  <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Title
                  </th>
                  <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Description
                  </th>
                  <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Category
                  </th>
                  <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Price
                  </th>
                  <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Actions
                  </th>
                </tr>
              </thead>
              <tbody class="bg-white divide-y divide-gray-200">
                <tr
                  v-for="course in filteredCourses"
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
                  <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                    {{ formatPrice(course.price) }}
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap text-sm font-medium space-x-2">
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
              {{ isEditing ? "Edit Course" : "Add New Course" }}
            </h3>

            <form @submit.prevent="handleSubmit" class="space-y-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">
                  Title
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
                  Description
                </label>
                <textarea
                  v-model="formData.description"
                  rows="3"
                  required
                  class="w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                ></textarea>
              </div>

              <!-- Price Field -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">
                  Course Price
                </label>
                <input
                  v-model="formData.price"
                  type="number"
                  step="0.01"
                  min="0"
                  required
                  class="w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                />
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">
                  Image
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
                      No image
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
                    Remove Image
                  </button>
                </div>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">
                  Category
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
                    {{ category.title }}
                  </option>
                </select>
              </div>

              <div class="flex justify-end space-x-3 pt-4">
                <button
                  type="button"
                  @click="closeModal"
                  class="px-4 py-2 border border-gray-300 rounded-md text-sm font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  class="px-4 py-2 bg-indigo-600 text-white rounded-md text-sm font-medium hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                >
                  {{ isEditing ? "Update" : "Add" }}
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
import { ref, reactive, computed, onBeforeMount } from "vue";
import DefaultLayout from "@/layouts/DefaultLayout.vue";
import categoryServices from "@/services/categoryServices";
import courseServices from "@/services/courseServices";

// Categories data
const categories = ref([]);

// Course data
const courses = ref([]);

const showModal = ref(false);
const isEditing = ref(false);
const imagePreview = ref(null);
const selectedFile = ref(null);
const searchQuery = ref("");

const formData = reactive({
  price: 30000.00, // Default price as float
});

const filteredCourses = computed(() => {
  const query = searchQuery.value.toLowerCase().trim();
  if (!query) return courses.value;

  return courses.value.filter((course) =>
    course.title.toLowerCase().includes(query)
  );
});


const formatPrice = (amount) => {
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(amount);
};
// Methods remain the same as in the previous version
const handleImageChange = (event) => {
  const file = event.target.files[0];
  if (file) {
    selectedFile.value = file;
    const reader = new FileReader();
    reader.onload = (e) => {
      imagePreview.value = e.target.result;
      formData.thumbnail = e.target.result;
      formData.file = file;
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
  return category ? category.title : "No Category";
};

const openAddModal = () => {
  isEditing.value = false;
  showModal.value = true;
};

const openEditModal = (course) => {
  isEditing.value = true;
  formData.id = course.id;
  formData.title = course.title;
  formData.description = course.description;
  formData.thumbnail = course.thumbnail;
  formData.category_id = course.category_id;
  formData.price = course.price;
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
  formData.price = 30000.00; // Reset to default price
  imagePreview.value = null;
  selectedFile.value = null;
};

const handleSubmit = async () => {
  if (isEditing.value) {
    await courseServices.update(formData).then((res) => {
      const data = res.data.data;
      console.log(data);

      formData.category_id = data.category_id.id;
      const index = courses.value.findIndex((c) => c.id === formData.id);

      if (index !== -1) {
        courses.value[index] = { ...formData };
      }
    });
  } else {
    await courseServices.create(formData);
    const newCourse = {
      ...formData,
      id: Math.max(0, ...courses.value.map((c) => c.id)) + 1,
    };
    courses.value.push(newCourse);
  }
  closeModal();
};

const deleteCourse = async (id) => {
  if (confirm("Are you sure you want to delete this course?")) {
    await courseServices.delete(formData);
    courses.value = courses.value.filter((course) => course.id !== id);
  }
};

onBeforeMount(async () => {
  await categoryServices.gets().then((response) => {
    const data = response.data.data;

    categories.value = data;
  });

  await courseServices.gets().then((response) => {
    const data = response.data.data;

    data.forEach((element) => {
      element.category_id = element.category_id.id;
      element.thumbnail = "http://localhost:8092/backend" + element.thumbnail;
    });

    courses.value = data;
  });
});
</script>