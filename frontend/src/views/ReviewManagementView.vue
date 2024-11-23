<template>
  <DefaultLayout>
    <div class="container mx-auto p-4">
      <div class="flex justify-between items-center mb-4">
        <h1 class="text-2xl font-bold">Review Management</h1>
      </div>

      <!-- Edit Modal remains the same -->
      <div
        v-if="showModal"
        class="fixed inset-0 bg-gray-600 bg-opacity-50 overflow-y-auto h-full w-full z-50"
      >
        <div
          class="relative top-20 mx-auto p-5 border w-full max-w-2xl shadow-lg rounded-md bg-white dark:border-strokedark dark:bg-boxdark"
        >
          <!-- Modal Header -->
          <div class="flex justify-between items-center mb-4">
            <h3 class="text-lg font-medium dark:text-white">Edit Comment</h3>
            <button
              @click="closeModal"
              class="text-gray-400 hover:text-gray-500"
            >
              <span class="text-2xl">&times;</span>
            </button>
          </div>

          <!-- Modal Body -->
          <div class="mt-2">
            <form @submit.prevent="handleSubmit" class="space-y-4">
              <!-- User Field (Read-only) -->
              <div class="form-control">
                <label class="block text-sm font-medium mb-1 dark:text-white">
                  User
                </label>
                <input
                  v-model="formData.user"
                  class="w-full p-2 rounded-md border-gray-300 bg-gray-100 shadow-sm dark:border-strokedark dark:bg-gray-700 dark:text-white"
                  readonly
                />
              </div>

              <!-- Comment Field -->
              <div class="form-control">
                <label class="block text-sm font-medium mb-1 dark:text-white">
                  Review <span class="text-red-500">*</span>
                </label>
                <textarea
                  v-model="formData.comment"
                  rows="4"
                  class="w-full p-2 rounded-md border-gray-300 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50 dark:border-strokedark dark:bg-boxdark dark:text-white"
                  :class="{ 'border-red-500': errors.comment }"
                  placeholder="Enter comment"
                ></textarea>
                <span v-if="errors.comment" class="text-red-500 text-sm">
                  {{ errors.comment }}
                </span>
              </div>

              <!-- Rating Field -->
              <div class="form-control">
                <label class="block text-sm font-medium mb-1 dark:text-white">
                  Rating <span class="text-red-500">*</span>
                </label>
                <div class="flex items-center space-x-2">
                  <button
                    v-for="star in 5"
                    :key="star"
                    type="button"
                    @click="formData.rating = star"
                    class="focus:outline-none"
                  >
                    <span
                      class="text-2xl"
                      :class="
                        star <= formData.rating
                          ? 'text-yellow-500'
                          : 'text-gray-300'
                      "
                    >
                      ★
                    </span>
                  </button>
                  <span class="ml-2 text-sm text-gray-600 dark:text-gray-400">
                    ({{ formData.rating }}/5)
                  </span>
                </div>
                <span v-if="errors.rating" class="text-red-500 text-sm">
                  {{ errors.rating }}
                </span>
              </div>

              <!-- Modal Footer -->
              <div class="items-center px-4 py-3">
                <button
                  type="button"
                  @click="closeModal"
                  class="px-4 py-2 bg-gray-500 text-white text-base font-medium rounded-md shadow-sm hover:bg-gray-600 focus:outline-none focus:ring-2 focus:ring-gray-300 mr-2"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  :disabled="isSubmitting"
                  class="px-4 py-2 bg-primary bg-gray-200 hover:bg-gray-300 text-black text-base font-medium rounded-md shadow-sm hover:bg-opacity-90 focus:outline-none focus:ring-2 focus:ring-primary focus:ring-opacity-50"
                >
                  <span>Update</span>
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>

      <!-- Modified Comment List Table with Reply To column -->
      <div
        class="mt-6 rounded-sm border border-stroke bg-white px-5 pt-6 pb-2.5 shadow-default dark:border-strokedark dark:bg-boxdark sm:px-7.5 xl:pb-1"
      >
        <input
          type="text"
          v-model="searchQuery"
          placeholder="Search..."
          class="pl-10 pr-4 py-2 rounded-lg border border-gray-200"
        />
        <div class="max-w-full overflow-x-auto mt-3">
          <table class="w-full table-auto">
            <thead>
              <tr class="bg-gray-2 text-left dark:bg-meta-4">
                <th
                  class="min-w-[50px] py-4 px-4 font-medium text-black dark:text-white"
                >
                  ID
                </th>
                <th
                  class="min-w-[150px] py-4 px-4 font-medium text-black dark:text-white"
                >
                  User
                </th>

                <th
                  class="min-w-[300px] py-4 px-4 font-medium text-black dark:text-white"
                >
                  Review
                </th>
                <th
                  class="min-w-[100px] py-4 px-4 font-medium text-black dark:text-white"
                >
                  Rating
                </th>
                <th class="py-4 px-4 font-medium text-black dark:text-white">
                  Actions
                </th>
              </tr>
            </thead>
            <tbody>
              <template v-for="comment in filteredComments" :key="comment.id">
                <tr class="border-b border-[#eee] dark:border-strokedark">
                  <td class="py-5 px-4">
                    <p class="text-black dark:text-white">{{ comment.id }}</p>
                  </td>
                  <td class="py-5 px-4">
                    <p class="text-black dark:text-white">
                      {{ comment.user }}
                    </p>
                  </td>

                  <td class="py-5 px-4">
                    <p class="text-gray-600 dark:text-gray-400">
                      {{ comment.comment }}
                    </p>
                  </td>
                  <td class="py-5 px-4">
                    <div class="flex items-center">
                      <span class="text-yellow-500">{{
                        "★".repeat(comment.rating)
                      }}</span>
                      <span class="text-gray-300">{{
                        "★".repeat(5 - comment.rating)
                      }}</span>
                      <span class="ml-2 text-gray-600 dark:text-gray-400"
                        >({{ comment.rating }}/5)</span
                      >
                    </div>
                  </td>
                  <td class="py-5 px-4">
                    <div class="flex items-center space-x-3.5">
                      <button
                        @click="editComment(comment)"
                        class="text-yellow-600 hover:text--yellow-900 bg-indigo-50 px-3 py-1 rounded-md hover:bg-indigo-100 transition-colors duration-200"
                      >
                        <font-awesome-icon :icon="['fas', 'eye']" />
                      </button>
                      <button
                        @click="deleteComment(comment.id)"
                        class="text-red-600 hover:text-red-900 bg-red-50 px-3 py-1 rounded-md hover:bg-red-100 transition-colors duration-200"
                      >
                        <font-awesome-icon :icon="['fas', 'trash']" />
                      </button>
                    </div>
                  </td>
                </tr>
              </template>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </DefaultLayout>
</template>

<script setup>
import { ref, reactive, computed } from "vue";
import DefaultLayout from "@/layouts/DefaultLayout.vue";

// State
const showModal = ref(false);
const isSubmitting = ref(false);
const selectedComment = ref(null);
const searchQuery = ref("");

const formData = reactive({
  id: null,
  user: "",
  comment: "",
  rating: 0,
  parentId: null,
  replyToUser: "",
});

const errors = reactive({
  comment: "",
  rating: "",
});

// Sample data with reply information
const comments = ref([
  {
    id: 1,
    user: "John Doe",
    comment: "Great product! Really enjoyed using it.",
    rating: 5,
    parentId: null,
    replyToUser: null,
  },
  {
    id: 2,
    user: "Jane Smith",
    comment: "I agree with John, its amazing!",
    rating: 5,
    parentId: 1,
    replyToUser: "John Doe",
  },
  {
    id: 3,
    user: "Mike Johnson",
    comment: "The customer support is excellent.",
    rating: 4,
    parentId: null,
    replyToUser: null,
  },
  {
    id: 4,
    user: "Sarah Wilson",
    comment: "They helped me quickly.",
    rating: 5,
    parentId: 3,
    replyToUser: "Mike Johnson",
  },
  {
    id: 5,
    user: "Tom Brown",
    comment: "Same experience here.",
    rating: 4,
    parentId: 4,
    replyToUser: "Sarah Wilson",
  },
]);

// Methods
const validateForm = () => {
  let isValid = true;
  errors.comment = "";
  errors.rating = "";

  if (!formData.comment.trim()) {
    errors.comment = "Comment is required";
    isValid = false;
  }

  if (!formData.rating || formData.rating < 1) {
    errors.rating = "Rating is required";
    isValid = false;
  }

  return isValid;
};

const filteredComments = computed(() => {
  const query = searchQuery.value.toLowerCase().trim();
  if (!query) return comments.value;

  return comments.value.filter(
    (comment) =>
      comment.user.toLowerCase().includes(query) ||
      comment.comment.toLowerCase().includes(query)
  );
});

const editComment = (comment) => {
  selectedComment.value = comment;
  formData.id = comment.id;
  formData.user = comment.user;
  formData.comment = comment.comment;
  formData.rating = comment.rating;

  showModal.value = true;
};

const closeModal = () => {
  showModal.value = false;
  selectedComment.value = null;
  formData.id = null;
  formData.user = "";
  formData.comment = "";
  formData.rating = 0;

  errors.comment = "";
  errors.rating = "";
};

const handleSubmit = async () => {
  if (!validateForm()) return;

  try {
    isSubmitting.value = true;
    await new Promise((resolve) => setTimeout(resolve, 1000));

    const index = comments.value.findIndex((c) => c.id === formData.id);
    if (index !== -1) {
      comments.value[index] = {
        ...comments.value[index],
        comment: formData.comment,
        rating: formData.rating,
        replyToUser: formData.replyToUser,
      };
    }

    alert("Comment updated successfully");
    closeModal();
  } catch (error) {
    console.error("Error updating comment:", error);
    alert("Failed to update comment");
  } finally {
    isSubmitting.value = false;
  }
};

const deleteComment = async (id) => {
  if (!confirm("Are you sure you want to delete this comment?")) return;

  try {
    const deleteRecursive = (commentId) => {
      comments.value = comments.value.filter((comment) => {
        if (comment.id === commentId) {
          return false;
        }
        return true;
      });
    };

    await new Promise((resolve) => setTimeout(resolve, 1000));
    deleteRecursive(id);
    alert("Comment deleted successfully");
  } catch (error) {
    console.error("Error deleting comment:", error);
    alert("Failed to delete comment");
  }
};
</script>
