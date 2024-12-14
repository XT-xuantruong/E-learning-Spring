<template>
  <DefaultLayout>
    <div class="min-h-screen bg-gray-50 py-8">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <!-- Header -->
        <div class="mb-8">
          <h1 class="text-3xl font-bold text-gray-900">Student Management</h1>
          <p class="mt-2 text-sm text-gray-600">
            Manage student information and track learning progress
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
                placeholder="Search students..."
                class="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
              />
            </div>
            <div class="relative">
              <select
                v-model="filterStatus"
                class="appearance-none pl-4 pr-10 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 bg-white"
              >
                <option value="">All Statuses</option>
                <option value="active">Active</option>
                <option value="inactive">Paused</option>
                <option value="graduated">Graduated</option>
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
                    Student
                  </th>
                  <th
                    class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                  >
                    Courses & Status
                  </th>
                  <th
                    class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                  >
                    Payment
                  </th>

                  <th
                    class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                  >
                    Actions
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
                      <div>
                        <template
                          v-if="student.courses && student.courses.length > 0"
                        >
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
                              role="status"
                              :aria-label="`Course ${course.name} status`"
                            >
                              <template v-if="course.status === 'completed'"
                                >✓</template
                              >
                            </span>
                            <span class="text-sm font-medium text-gray-900">
                              {{ course.name }}
                            </span>
                          </div>
                        </template>
                        <div v-else class="text-gray-500">
                          No courses enrolled
                        </div>
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
                                  ? "Paid"
                                  : "Partial Payment"
                              }}
                            </span>
                          </span>
                        </div>
                      </div>
                    </div>
                  </td>

                  <td class="px-6 py-4 whitespace-nowrap">
                    <div class="flex items-center space-x-3">
                      <button
                        class="text-red-600 hover:text-red-900 bg-red-50 px-3 py-1 rounded-md hover:bg-red-100 transition-colors duration-200"
                        title="Delete"
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
import { ref, computed, onBeforeMount } from "vue";
import DefaultLayout from "@/layouts/DefaultLayout.vue";
import oauthServices from "@/services/oauthServices";
import courseEnrollmentServices from "@/services/courseEnrollmentServices";
import { useAdminStore } from "@/stores/admin";

// State
const students = ref([]);

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
  return new Intl.NumberFormat("en-US", {
    style: "currency",
    currency: "USD",
  }).format(amount);
};

const deleteStudent = async (id) => {
  if (confirm("Are you sure you want to delete this student?")) {
    const adminStore = useAdminStore();

    await oauthServices.delete(id, adminStore.admin.access);
    students.value = students.value.filter((s) => s.id !== id);
  }
};

onBeforeMount(async () => {
  await oauthServices.gets().then((res) => {
    const data = res.data.data;
    const dataStudents = data.filter((student) => student.role === "STUDENT");
    dataStudents.forEach((student) => {
      student.name = student.firstName + student.lastName;
      students.value = [...students.value, { ...student, courses: [] }];
    });

    data.forEach(async (value) => {
      await courseEnrollmentServices
        .getbyuser({ userId: value.id })
        .then((res) => {
          if (res.status === 200) {
            const courseData = res.data.data;

            courseData.forEach(async (course) => {
              const student = students.value.find(
                (s) => s.id === course.user.id
              );
              if (student) {
                student.courses.push({
                  id: course.id,
                  name:
                    course.course.title !== null
                      ? course.course.title
                      : "No Course",
                  status: course.finish ? "completed" : "in_progress",
                  payment: course.course.price,
                  paymentStatus: course.paid ? "paid" : "partial",
                });
              }
            });
          }
        });
    });
    console.log(students.value);
  });
});
</script>