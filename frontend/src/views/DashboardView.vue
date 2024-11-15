<template>
  <DefaultLayout>
    <div class="container mx-auto p-4">
      <header class="mb-6">
        <h1 class="text-2xl font-bold">Quản Lý Học Viên</h1>
        <div class="flex justify-between items-center mt-4">
          <div class="flex gap-1">
            <input
              v-model="searchQuery"
              type="text"
              placeholder="Tìm kiếm học viên..."
              class="px-4 py-2 border rounded-lg"
            />
            <select v-model="filterStatus" class="px-4 py-2 border rounded-lg">
              <option value="">Tất cả trạng thái</option>
              <option value="active">Đang học</option>
              <option value="inactive">Tạm dừng</option>
              <option value="graduated">Đã tốt nghiệp</option>
            </select>
          </div>
        </div>
      </header>

      <!-- Thống kê -->
      <div class="grid grid-cols-1 md:grid-cols-4 gap-4 mb-6">
        <div class="bg-white p-4 rounded-lg shadow">
          <h3 class="text-gray-500">Tổng số học viên</h3>
          <p class="text-2xl font-bold">{{ totalStudents }}</p>
        </div>
        <div class="bg-white p-4 rounded-lg shadow">
          <h3 class="text-gray-500">Đang học</h3>
          <p class="text-2xl font-bold text-green-500">{{ activeStudents }}</p>
        </div>
        <div class="bg-white p-4 rounded-lg shadow">
          <h3 class="text-gray-500">Tạm dừng</h3>
          <p class="text-2xl font-bold text-yellow-500">{{ inactiveStudents }}</p>
        </div>
        <div class="bg-white p-4 rounded-lg shadow">
          <h3 class="text-gray-500">Đã tốt nghiệp</h3>
          <p class="text-2xl font-bold text-blue-500">{{ graduatedStudents }}</p>
        </div>
      </div>

      <!-- Danh sách học viên -->
      <div class="bg-white rounded-lg shadow overflow-hidden">
        <table class="w-full">
          <thead class="bg-gray-50">
            <tr>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Học viên
              </th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Email
              </th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Khóa học
              </th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Trạng thái
              </th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Thao tác
              </th>
            </tr>
          </thead>
          <tbody class="divide-y divide-gray-200">
            <tr v-for="student in filteredStudents" :key="student.id">
              <td class="px-6 py-4">
                <div class="flex items-center">
                  <div class="h-10 w-10 rounded-full bg-gray-200 flex items-center justify-center">
                    {{ getInitials(student.name) }}
                  </div>
                  <div class="ml-4">
                    <div class="text-sm font-medium text-gray-900">
                      {{ student.name }}
                    </div>
                    <div class="text-sm text-gray-500">
                      ID: {{ student.id }}
                    </div>
                  </div>
                </div>
              </td>
              <td class="px-6 py-4 text-sm text-gray-500">
                {{ student.email }}
              </td>
              <td class="px-6 py-4">
                <div class="space-y-1">
                  <div v-for="course in student.courses" :key="course.id" 
                       class="flex items-center justify-between text-sm">
                    <div class="flex items-center">
                      <span class="w-4 h-4 mr-2 inline-flex items-center justify-center">
                        <font-awesome-icon
                          v-if="course.status === 'graduated'"
                          :icon="['fas', 'check']"
                          class="text-green-500"
                        />
                        <span v-else class="w-4 h-4 border border-gray-300 rounded-sm"></span>
                      </span>
                      {{ course.name }}
                    </div>
                    
                  </div>
                </div>
              </td>
              <td class="px-6 py-4">
                <span :class="getStatusClass(student.status)">
                  {{ getStatusText(student.status) }}
                </span>
              </td>
              <td class="px-6 py-4">
                <button
                  @click="deleteStudent(student.id)"
                  class="text-red-500 hover:text-red-700"
                >
                  <font-awesome-icon :icon="['fas', 'trash']" />
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </DefaultLayout>
</template>

<script setup>
import { ref, computed } from 'vue';
import DefaultLayout from "@/layouts/DefaultLayout.vue";

// State
const students = ref([
  {
    id: 1,
    name: "Nguyễn Văn A",
    email: "nguyenvana@example.com",
    courses: [
      {
        id: 1,
        name: "Vue.js Advanced",
        status: "active"
      },
      {
        id: 2,
        name: "React Fundamentals",
        status: "graduated"
      }
    ],
    status: "active",
  },
  {
    id: 2,
    name: "Trần Thị B",
    email: "tranthib@example.com",
    courses: [
      {
        id: 3,
        name: "Node.js Backend",
        status: "active"
      }
    ],
    status: "active",
  }
]);

const searchQuery = ref("");
const filterStatus = ref("");

// Computed
const filteredStudents = computed(() => {
  return students.value
    .filter(
      (student) =>
        student.name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
        student.email.toLowerCase().includes(searchQuery.value.toLowerCase())
    )
    .filter(
      (student) => !filterStatus.value || student.status === filterStatus.value
    );
});

const totalStudents = computed(() => students.value.length);
const activeStudents = computed(() => 
  students.value.filter(s => s.status === 'active').length
);
const inactiveStudents = computed(() => 
  students.value.filter(s => s.status === 'inactive').length
);
const graduatedStudents = computed(() => 
  students.value.filter(s => s.status === 'graduated').length
);

// Methods
const getInitials = (name) => {
  return name
    .split(" ")
    .map((word) => word[0])
    .join("")
    .toUpperCase();
};

const getStatusClass = (status) => {
  const classes = {
    active: "bg-green-100 text-green-800",
    inactive: "bg-yellow-100 text-yellow-800",
    graduated: "bg-blue-100 text-blue-800",
  };
  return `px-2 py-1 text-xs font-medium rounded-full ${classes[status]}`;
};

const getStatusText = (status) => {
  const texts = {
    active: "Đang học",
    inactive: "Tạm dừng",
    graduated: "Đã tốt nghiệp",
  };
  return texts[status];
};

const deleteStudent = (id) => {
  if (confirm("Bạn có chắc chắn muốn xóa học viên này?")) {
    students.value = students.value.filter((s) => s.id !== id);
  }
};
</script>