<template>
  <DefaultLayout>
    <div class="min-h-screen bg-gray-50 py-8">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <!-- Header -->
        <div class="mb-8">
          <h1 class="text-3xl font-bold text-gray-900">Quản Lý Học Viên</h1>
          <p class="mt-2 text-sm text-gray-600">
            Quản lý thông tin và theo dõi tiến độ học tập của học viên
          </p>
        </div>

        <!-- Actions Bar -->
        <div
          class="mb-6 flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4"
        >
          <div class="flex flex-1 w-full sm:w-auto items-center space-x-4">
            <div class="relative flex-1 sm:flex-none sm:w-96">
              <input
                v-model="searchQuery"
                type="text"
                placeholder="Tìm kiếm học viên..."
                class="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
              />
              <span
                class="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"
              >
                🔍
              </span>
            </div>
            <div class="relative">
              <select
                v-model="filterStatus"
                class="appearance-none pl-4 pr-10 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 bg-white"
              >
                <option value="">Tất cả trạng thái</option>
                <option value="active">Đang học</option>
                <option value="inactive">Tạm dừng</option>
                <option value="graduated">Đã tốt nghiệp</option>
              </select>
            </div>
          </div>
        </div>

        <!-- Students List -->
        <div class="bg-white rounded-xl shadow">
          <div class="overflow-x-auto">
            <table class="w-full">
              <thead>
                <tr class="bg-gray-50 border-b border-gray-200">
                  <th
                    class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                  >
                    Học viên
                  </th>
                  <th
                    class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                  >
                    Khóa học & Trạng thái
                  </th>
                  <th
                    class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                  >
                    Thanh toán
                  </th>
                  <th
                    class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                  >
                    Trạng thái
                  </th>
                  <th
                    class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                  >
                    Thao tác
                  </th>
                </tr>
              </thead>
              <tbody class="divide-y divide-gray-200">
                <tr
                  v-for="student in filteredStudents"
                  :key="student.id"
                  class="hover:bg-gray-50"
                >
                  <td class="px-6 py-4 whitespace-nowrap">
                    <div class="flex items-center">
                      <div class="flex-shrink-0 h-10 w-10">
                        <img
                          class="h-10 w-10 rounded-full object-cover"
                          src=""
                          :alt="student.name"
                        />
                      </div>
                      <div class="ml-4">
                        <div class="text-sm font-medium text-gray-900">
                          {{ student.name }}
                        </div>
                        <div class="text-sm text-gray-500">
                          {{ student.email }}
                        </div>
                      </div>
                    </div>
                  </td>
                  <td class="px-6 py-4">
                    <div class="space-y-3">
                      <div
                        v-for="course in student.courses"
                        :key="course.id"
                        class="flex items-center space-x-2"
                      >
                        <span
                          :class="[
                            'w-5 h-5 flex items-center justify-center rounded-full border',
                            course.status === 'completed'
                              ? 'bg-green-500 border-green-500 text-white'
                              : 'border-gray-300',
                          ]"
                        >
                          <template v-if="course.status === 'completed'"
                            >✓</template
                          >
                        </span>
                        <span class="text-sm font-medium text-gray-900">
                          {{ course.name }}
                        </span>
                      </div>
                    </div>
                  </td>
                  <td class="px-6 py-4">
                    <div class="space-y-3">
                      <div
                        v-for="course in student.courses"
                        :key="course.id"
                        class="flex flex-col"
                      >
                        <div class="text-sm font-medium text-gray-900">
                          {{ formatCurrency(course.payment) }}
                        </div>
                        <div class="mt-1">
                          <span
                            :class="[
                              'inline-flex items-center',
                              course.paymentStatus === 'paid'
                                ? 'text-green-600'
                                : 'text-yellow-600',
                            ]"
                          >
                            {{ course.paymentStatus === "paid" ? "✓" : "•" }}
                            <span class="ml-1 text-sm">
                              {{
                                course.paymentStatus === "paid"
                                  ? "Đã thanh toán"
                                  : "Thanh toán một phần"
                              }}
                            </span>
                          </span>
                        </div>
                      </div>
                    </div>
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap">
                    <span :class="getStatusClass(student.status)">
                      {{ getStatusText(student.status) }}
                    </span>
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap">
                    <div class="flex items-center space-x-3">
                      <button
                        class="text-red-600 hover:text-red-900 bg-red-50 px-3 py-1 rounded-md hover:bg-red-100 transition-colors duration-200"
                        title="Xóa"
                        @click="deleteStudent(student.id)"
                      >
                        <font-awesome-icon :icon="['fas', 'trash']" />
                      </button>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </DefaultLayout>
</template>

<script setup>
import { ref, computed } from "vue";
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
        status: "completed",
        payment: 5000000,
        paymentStatus: "paid",
      },
      {
        id: 2,
        name: "React Fundamentals",
        status: "completed",
        payment: 4500000,
        paymentStatus: "paid",
      },
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
        status: "in_progress",
        payment: 6000000,
        paymentStatus: "partial",
      },
    ],
    status: "active",
  },
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

// Methods
const formatCurrency = (amount) => {
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(amount);
};

const getStatusClass = (status) => {
  const classes = {
    active: "bg-green-100 text-green-800",
    inactive: "bg-yellow-100 text-yellow-800",
    graduated: "bg-blue-100 text-blue-800",
  };
  return `px-3 py-1 rounded-full text-sm font-medium ${classes[status]}`;
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
