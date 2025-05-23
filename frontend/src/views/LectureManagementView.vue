<script setup>
import { onBeforeMount, ref } from "vue";
import DefaultLayout from "@/layouts/DefaultLayout.vue";
import lectureServices from "@/services/lectureServices";
import courseServices from "@/services/courseServices";

// Add courses data
const courses = ref([]);

const lectures = ref([]);

const isModalOpen = ref(false);
const isPdfViewerOpen = ref(false);
const modalMode = ref("add");
const selectedLecture = ref(null);
const selectedFile = ref(null);
const currentPdfUrl = ref("");

const formData = ref({
  title: "",
  course_id: "",
  content: "",
  pdfUrl: "",
  date: "",
  size: "",
});

// Function to generate a unique ID
const generateUniqueId = () => {
  return lectures.value.length > 0
    ? Math.max(...lectures.value.map((l) => l.id)) + 1
    : 1;
};

const handleFileSelect = (event) => {
  const file = event.target.files[0];
  if (file && file.type === "application/pdf") {
    selectedFile.value = file;
    formData.value.file = file;
    formData.value.content = file.name;
    formData.value.pdfUrl = URL.createObjectURL(file);
    formData.value.size = (file.size / (1024 * 1024)).toFixed(2) + " MB";
    formData.value.date = new Date().toISOString().split("T")[0];
  } else {
    alert("Please select a PDF file");
    event.target.value = "";
  }
};

const openPdfViewer = (lecture) => {
  currentPdfUrl.value = lecture.pdfUrl;
  isPdfViewerOpen.value = true;
};

const closePdfViewer = () => {
  currentPdfUrl.value = "";
  isPdfViewerOpen.value = false;
};


const updateLecture = async () => {
  await lectureServices.update(formData.value).then((Response) => {
    console.log(Response);
  });

  const index = lectures.value.findIndex(
    (l) => l.id === selectedLecture.value.id
  );
  if (index !== -1) {
    lectures.value[index] = {
      ...lectures.value[index],
      ...formData.value,
    };
  }
  closeModal();
};

const addLecture = async () => {
  if (
    !formData.value.title ||
    !formData.value.course_id ||
    !selectedFile.value
  ) {
    alert("Please fill in all required fields and select a PDF file");
    return;
  }

  await lectureServices.create(formData.value).then((Response) => {
    console.log(Response);
  });
  const newLecture = {
    id: generateUniqueId(),
    ...formData.value,
  };

  lectures.value.push(newLecture);

  closeModal();
};

const deleteLecture = async (lectureId) => {
  if (confirm("Are you sure you want to delete this lecture?")) {
    await lectureServices.delete(lectureId).then((response) => {
      console.log(response);
    });
    lectures.value = lectures.value.filter((l) => l.id !== lectureId);
  }
};

const openEditModal = (lecture) => {
  modalMode.value = "edit";
  selectedLecture.value = lecture;
  formData.value = { ...lecture };
  selectedFile.value = null;
  isModalOpen.value = true;
};

const openAddModal = () => {
  modalMode.value = "add";
  isModalOpen.value = true;
};

const closeModal = () => {
  isModalOpen.value = false;
  selectedLecture.value = null;
  formData.value = {
    title: "",
    course_id: "",
    content: "",
    pdfUrl: "",
    date: "",
  };
  selectedFile.value = null;
};

const handleSubmit = async () => {
  if (modalMode.value === "add") {
    await addLecture();
  } else {
    updateLecture();
  }
};

const getCourseName = (courseId) => {
  const course = courses.value.find((c) => c.id === courseId);
  return course ? course.title : courseId;
};

onBeforeMount(async () => {
  // await courseServices.gets().then((res)=>{
  //   console.log(res.data);
    
  // })
  await courseServices.gets().then((response) => {
    const data = response.data.data;

    data.forEach((element) => {
      element.category_id = element.category_id.id;
    });

    courses.value = data;
    
  });
  await lectureServices.gets().then((response) => {
    const data = response.data.data;
    data.forEach((element) => {
      element.course_id = element.course.id;
      element.date = element.createdAt;
      element.pdfUrl = "http://localhost:8092/backend" + element.content;
      element.content = element.content.substring(
        element.content.lastIndexOf("_") + 1
      );
    });
    lectures.value = data;
    console.log(lectures.value);
  });

 
});

const formatDate = (date) => {
  return new Date(date).toLocaleDateString("vi-VN", {
    year: "numeric",
    month: "short",
    day: "numeric",
  });
};
</script>

<template>
  <DefaultLayout>
    <div class="min-h-screen bg-gray-50 py-8">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <!-- Header Section -->
        <div
          class="bg-white rounded-t-lg shadow-sm px-6 py-4 mb-6 flex justify-between items-center"
        >
          <div>
            <h1 class="text-2xl font-bold text-gray-900">Lecture Management</h1>
            <p class="text-sm text-gray-500 mt-1">
              Manage your course lectures and materials
            </p>
          </div>
          <!-- Add New Lecture Button -->
          <button
            @click="openAddModal"
            class="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 transition-colors duration-200"
          >
            Add New Lecture
          </button>
        </div>

        <!-- Content Section -->
        <div class="bg-white rounded-lg shadow-sm overflow-hidden">
          <div class="min-w-full">
            <div
              class="grid grid-cols-12 bg-gray-50 border-b border-gray-200 text-xs uppercase font-medium text-gray-500 divide-x divide-gray-200"
            >
              <div class="col-span-4 px-6 py-3">Lecture Details</div>
              <div class="col-span-2 px-6 py-3">Course</div>
              <div class="col-span-2 px-6 py-3">Date</div>
              <div class="col-span-2 px-6 py-3">Actions</div>
            </div>

            <div class="divide-y divide-gray-200">
              <div
                v-for="lecture in lectures"
                :key="lecture.id"
                class="grid grid-cols-12 hover:bg-gray-50 transition-colors duration-150 divide-x divide-gray-200"
              >
                <div class="col-span-4 px-6 py-4">
                  <div class="font-medium text-gray-900">
                    {{ lecture.title }}
                  </div>
                  <div class="mt-1 flex items-center text-sm text-gray-500">
                    <span
                      class="cursor-pointer hover:text-indigo-600"
                      @click="openPdfViewer(lecture)"
                    >
                      {{ lecture.content }}
                    </span>
                  </div>
                </div>
                <div class="col-span-2 px-6 py-4 flex items-center">
                  <span
                    class="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-blue-100 text-blue-800"
                  >
                    {{ getCourseName(lecture.course_id) }}
                  </span>
                </div>
                <div
                  class="col-span-2 px-6 py-4 flex items-center text-sm text-gray-500"
                >
                  {{ formatDate(lecture.date) }}
                </div>

                <div class="col-span-2 px-6 py-4 flex items-center space-x-3">
                  <button
                    @click="openEditModal(lecture)"
                    class="text-indigo-600 hover:text-indigo-900 bg-indigo-50 px-3 py-1 rounded-md hover:bg-indigo-100 transition-colors duration-200"
                  >
                    <font-awesome-icon :icon="['fas', 'pen']" />
                  </button>
                  <button
                    @click="deleteLecture(lecture.id)"
                    class="text-red-600 hover:text-red-900 bg-red-50 px-3 py-1 rounded-md hover:bg-red-100 transition-colors duration-200"
                  >
                    <font-awesome-icon :icon="['fas', 'trash']" />
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Add/Edit Modal -->
      <div
        v-if="isModalOpen"
        class="fixed inset-0 bg-gray-500 bg-opacity-75 flex items-center justify-center p-4"
      >
        <div class="bg-white rounded-lg shadow-xl max-w-md w-full mx-4">
          <div class="px-6 py-4 border-b border-gray-200">
            <h3 class="text-lg font-medium text-gray-900">
              {{ modalMode === "add" ? "Add New Lecture" : "Edit Lecture" }}
            </h3>
          </div>

          <form @submit.prevent="handleSubmit" class="p-6">
            <div class="space-y-4">
              <div>
                <label class="block text-sm font-medium text-gray-700"
                  >Title</label
                >
                <input
                  v-model="formData.title"
                  type="text"
                  required
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
                />
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700"
                  >Course</label
                >
                <select
                  v-model="formData.course_id"
                  required
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
                >
                  <option value="" disabled>Select a course</option>
                  <option
                    v-for="course in courses"
                    :key="course.id"
                    :value="course.id"
                  >
                    {{ course.title }} 
                  </option>
                </select>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700"
                  >PDF File</label
                >
                <div class="mt-1 flex items-center">
                  <input
                    type="file"
                    accept="application/pdf"
                    @change="handleFileSelect"
                    class="block w-full text-sm text-gray-500 file:mr-4 file:py-2 file:px-4 file:rounded-md file:border-0 file:text-sm file:font-medium file:bg-indigo-50 file:text-indigo-700 hover:file:bg-indigo-100"
                  />
                </div>
              </div>
            </div>

            <div class="mt-6 flex justify-end space-x-3">
              <button
                type="button"
                @click="closeModal"
                class="px-4 py-2 border border-gray-300 rounded-md text-sm font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
              >
                Cancel
              </button>
              <button
                type="submit"
                class="px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
              >
                {{ modalMode === "add" ? "Add Lecture" : "Update" }}
              </button>
            </div>
          </form>
        </div>
      </div>

      <!-- PDF Viewer Modal -->
      <div
        v-if="isPdfViewerOpen"
        class="fixed inset-0 bg-gray-500 bg-opacity-75 flex items-center justify-center p-4"
      >
        <div class="bg-white rounded-lg shadow-xl w-full max-w-5xl">
          <div
            class="flex items-center justify-between px-6 py-4 border-b border-gray-200"
          >
            <h3 class="text-lg font-medium text-gray-900">PDF Viewer</h3>
            <button
              @click="closePdfViewer"
              class="text-gray-400 hover:text-gray-500 focus:outline-none"
            >
              <span class="text-2xl">&times;</span>
            </button>
          </div>
          <div class="h-[calc(80vh-8rem)] w-full bg-gray-100 p-4">
            <iframe
              :src="currentPdfUrl"
              class="w-full h-full rounded-lg shadow-inner"
              type="application/pdf"
            >
            </iframe>
            <div
              v-if="!currentPdfUrl"
              class="flex items-center justify-center h-full"
            >
              <p class="text-gray-500 text-center">
                Your browser doesn't support PDF viewing.<br />
                <a
                  :href="currentPdfUrl"
                  target="_blank"
                  class="text-indigo-600 hover:text-indigo-800"
                >
                  Download the PDF
                </a>
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </DefaultLayout>
</template>
