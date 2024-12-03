<!-- CategoryManagement.vue -->
<template>
  <DefaultLayout>
    <div class="category-management p-4">
      <!-- Header Section -->
      <div class="mb-8">
        <div class="flex justify-between items-center mb-4">
          <div>
            <h2 class="text-2xl font-bold text-gray-800">Category Management</h2>
            <p class="text-gray-600 mt-1">
              Manage all your categories here
            </p>
          </div>
          <button
            @click="openModal()"
            class="flex items-center gap-2 px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors duration-200"
          >
            <svg
              class="w-5 h-5"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M12 4v16m8-8H4"
              />
            </svg>
            Add New Category
          </button>
        </div>

        <!-- Search and Filter Section -->
        <div class="flex gap-4 items-center">
          <div class="flex-2 relative">
            <span
              class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none"
            >
              <svg
                class="w-5 h-5 text-gray-400"
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
            </span>
            <input
              type="text"
              v-model="searchQuery"
              placeholder="Search..."
              class="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
            />
          </div>
        </div>
      </div>

      <!-- Categories List -->
      <div class="bg-white rounded-lg shadow">
        <!-- Table Header -->
        <div
          class="grid grid-cols-12 gap-4 px-6 py-4 bg-gray-50 rounded-t-lg border-b text-sm font-medium text-gray-700"
        >
          <div class="col-span-1">#</div>
          <div class="col-span-4">Category Name</div>
          <div class="col-span-3">Slug</div>
          <div class="col-span-2">Created Date</div>
          <div class="col-span-2 text-right">Actions</div>
        </div>

        <!-- Categories List -->
        <div>
          <div
            v-for="(category, index) in filteredCategories"
            :key="category.id"
            class="grid grid-cols-12 gap-4 px-6 py-4 hover:bg-gray-50 border-b last:border-b-0 items-center transition-colors duration-150"
            :class="{ 'bg-gray-50': index % 2 === 0 }"
          >
            <div class="col-span-1 text-sm text-gray-600">
              {{ index + 1 }}
            </div>
            <div class="col-span-4">
              <div class="text-sm font-medium text-gray-900">
                {{ category.title }}
              </div>
            </div>
            <div class="col-span-3">
              <span
                class="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-gray-100 text-gray-800"
              >
                {{ category.slug }}
              </span>
            </div>
            <div class="col-span-2 text-sm text-gray-600">
              {{ formatDate(category.createdAt) }}
            </div>
            <div class="col-span-2 flex justify-end gap-2">
              <button
                @click="openModal(category)"
                class="text-indigo-600 hover:text-indigo-900 bg-indigo-50 px-3 py-1 rounded-md hover:bg-indigo-100 transition-colors duration-200"
                title="Edit"
              >
                <font-awesome-icon :icon="['fas', 'pen']" />
              </button>
              <button
                @click="deleteCategory(category.id)"
                class="text-red-600 hover:text-red-900 bg-red-50 px-3 py-1 rounded-md hover:bg-red-100 transition-colors duration-200"
                title="Delete"
              >
                <font-awesome-icon :icon="['fas', 'trash']" />
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- Modal -->
      <div
        v-if="isModalOpen"
        class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
        @click.self="closeModal"
      >
        <div class="bg-white rounded-lg w-full max-w-md p-6 relative">
          <button
            @click="closeModal"
            class="absolute top-4 right-4 text-gray-400 hover:text-gray-500 focus:outline-none focus:text-gray-500 transition-colors duration-200"
          >
            <svg
              class="w-6 h-6"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M6 18L18 6M6 6l12 12"
              />
            </svg>
          </button>

          <h3 class="text-lg font-medium leading-6 text-gray-900 mb-4">
            {{ isEditing ? "Edit Category" : "Add New Category" }}
          </h3>

          <form @submit.prevent="handleSubmit" class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">
                Category Name <span class="text-red-500">*</span>
              </label>
              <input
                v-model="categoryForm.title"
                type="text"
                class="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
                :class="{ 'border-red-300': formErrors.title }"
                placeholder="Enter category name"
                required
              />
              <p v-if="formErrors.title" class="mt-1 text-sm text-red-500">
                {{ formErrors.title }}
              </p>
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">
                Slug <span class="text-red-500">*</span>
              </label>
              <input
                v-model="categoryForm.slug"
                type="text"
                class="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
                :class="{ 'border-red-300': formErrors.slug }"
                placeholder="Enter category slug"
                required
              />
              <p class="mt-1 text-sm text-gray-500">
                Slug will be automatically generated from the category name
              </p>
              <p v-if="formErrors.slug" class="mt-1 text-sm text-red-500">
                {{ formErrors.slug }}
              </p>
            </div>

            <div class="flex justify-end gap-3 pt-4">
              <button
                type="button"
                @click="closeModal"
                class="px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md shadow-sm hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
              >
                Cancel
              </button>
              <button
                type="submit"
                class="inline-flex justify-center px-4 py-2 text-sm font-medium text-white bg-blue-600 border border-transparent rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
                :disabled="isSubmitting"
              >
                <svg
                  v-if="isSubmitting"
                  class="w-5 h-5 mr-2 -ml-1 animate-spin"
                  fill="none"
                  viewBox="0 0 24 24"
                >
                  <circle
                    class="opacity-25"
                    cx="12"
                    cy="12"
                    r="10"
                    stroke="currentColor"
                    stroke-width="4"
                  />
                  <path
                    class="opacity-75"
                    fill="currentColor"
                    d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
                  />
                </svg>
                {{ isEditing ? "Update" : "Add" }}
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </DefaultLayout>
</template>

<script setup>
import { ref, reactive, computed, watch, onBeforeMount } from "vue";
import DefaultLayout from "@/layouts/DefaultLayout.vue";
import categoryServices from "@/services/categoryServices";

// State
const categories = ref([]);
const isEditing = ref(false);
const editingId = ref(null);
const isModalOpen = ref(false);
const isSubmitting = ref(false);
const searchQuery = ref("");
const formErrors = reactive({
  title: "",
  slug: "",
});

// Form state
const categoryForm = reactive({
  title: "",
  slug: "",
});

// Computed
const filteredCategories = computed(() => {
  let result = categories.value;

  // Search
  if (searchQuery.value) {
    const query = searchQuery.value.toLowerCase();
    result = result.filter((category) =>
      category.title.toLowerCase().includes(query)
    );
  }

  return result;
});

// Watchers
watch(
  () => categoryForm.title,
  (newTitle) => {
    if (!isEditing.value) {
      categoryForm.slug = generateSlug(newTitle);
    }
  }
);

// Methods
const formatDate = (date) => {
  return new Date(date).toLocaleDateString("en-US", {
    year: "numeric",
    month: "short",
    day: "numeric",
  });
};

const generateSlug = (text) => {
  return text
    .toLowerCase()
    .normalize("NFD")
    .replace(/[\u0300-\u036f]/g, "")
    .replace(/[đĐ]/g, "d")
    .replace(/([^0-9a-z-\s])/g, "")
    .replace(/(\s+)/g, "-")
    .replace(/-+/g, "-")
    .replace(/^-+|-+$/g, "");
};

const resetForm = () => {
  categoryForm.title = "";
  categoryForm.slug = "";
  formErrors.title = "";
  formErrors.slug = "";
  isEditing.value = false;
  editingId.value = null;
};

const openModal = (category = null) => {
  resetForm();
  if (category) {
    isEditing.value = true;
    editingId.value = category.id;
    categoryForm.title = category.title;
    categoryForm.slug = category.slug;
  }
  isModalOpen.value = true;
};

const closeModal = () => {
  resetForm();
  isModalOpen.value = false;
};

const validateForm = () => {
  let isValid = true;
  formErrors.title = "";
  formErrors.slug = "";

  if (!categoryForm.title.trim()) {
    formErrors.title = "Category name cannot be empty";
    isValid = false;
  }

  if (!categoryForm.slug.trim()) {
    formErrors.slug = "Slug cannot be empty";
    isValid = false;
  }

  // const existingCategory = categories.value.find(
  //   (cat) => cat.slug === categoryForm.slug && cat.id !== editingId.value
  // );
  // if (existingCategory) {
  //   formErrors.slug = "Slug already exists";
  //   isValid = false;
  // }

  return isValid;
};

const handleSubmit = async () => {
  if (!validateForm()) return;

  try {
    isSubmitting.value = true;

    if (isEditing.value) {
      await categoryServices.update({
        "id": editingId.value, 
        'title': categoryForm.title, 
        'slug': categoryForm.slug
      });
      const index = categories.value.findIndex(
        (cat) => cat.id === editingId.value
      );
      if (index !== -1) {
        categories.value[index] = {
          ...categories.value[index],
          title: categoryForm.title,
          slug: categoryForm.slug,
        };
      }
    } else {
      try {
        const response = await categoryServices.create(categoryForm);
        
        if (response && response.data && response.data.data) {
          const data = response.data.data;
          const newCategory = {
            id: data.id,
            title: data.title,
            slug: data.slug,
            createdAt: data.createdAt || new Date().toISOString(),
          };
          categories.value.push(newCategory);
        } else {
          console.error("Unexpected response structure:", response);
          alert("Failed to create category. Unexpected server response.");
        }
      } catch (createError) {
        console.error("Error creating category:", createError);
        alert("Failed to create category. Please try again.");
      }
    }

    closeModal();
  } catch (error) {
    console.error("Error submitting form:", error);
    alert("An error occurred. Please try again.");
  } finally {
    isSubmitting.value = false;
  }
};

onBeforeMount(async () => {
  await categoryServices.gets().then((response) => {
    categories.value = response.data.data;
  });
});

const deleteCategory = async (id) => {
  if (!confirm("Bạn có chắc chắn muốn xóa danh mục này?")) return;

  try {
    await categoryServices.delete(id)
    categories.value = categories.value.filter((cat) => cat.id !== id);
  } catch (error) {
    console.error("Error deleting category:", error);
  }
};
</script>
