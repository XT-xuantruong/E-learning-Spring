<template>
  <aside
    class="fixed top-0 left-0 w-64 h-full bg-slate-800 text-emerald-400 shadow-lg"
  >
    <!-- Logo -->
    <div class="flex items-center gap-2 px-6 py-5 border-b border-slate-700">
      <i class="fas fa-graduation-cap text-2xl text-emerald-500"></i>
      <h2
        class="text-xl font-bold bg-gradient-to-r from-emerald-400 to-cyan-400 bg-clip-text text-transparent"
      >
        Course Admin
      </h2>
    </div>

    <!-- Navigation Menu -->
    <nav class="mt-6 px-4">
      <div class="space-y-3">
        <!-- Dashboard -->
        <router-link
          to="/admin/"
          class="flex items-center px-4 py-3 rounded-lg transition-colors duration-200"
          :class="[
            $route.path === 'admin/dashboard'
              ? 'bg-slate-700 text-emerald-400'
              : 'hover:bg-slate-700/50',
          ]"
        >
          <i class="fas fa-tachometer-alt w-5"></i>
          <span class="ml-3 text-sm font-medium">Dashboard</span>
        </router-link>

        <!-- Courses with Submenu -->
        <div class="space-y-2">
          <button
            @click="toggleSubmenu('courses')"
            class="w-full flex items-center justify-between px-4 py-3 rounded-lg transition-colors duration-200"
            :class="[
              openedSubmenus.includes('courses') ||
              $route.path.includes('/courses')
                ? 'bg-slate-700 text-emerald-400'
                : 'hover:bg-slate-700/50',
            ]"
          >
            <div class="flex items-center">
              <i class="fas fa-book w-5"></i>
              <span class="ml-3 text-sm font-medium">Course</span>
            </div>
            <i
              class="fas fa-chevron-down text-xs transition-transform duration-200"
              :class="{
                'transform rotate-180': openedSubmenus.includes('courses'),
              }"
            ></i>
          </button>
          <!-- Submenu -->
          <div
            v-show="
              openedSubmenus.includes('courses') ||
              $route.path.includes('/courses')
            "
            class="pl-4 space-y-2"
          >
            <router-link
              to="/admin/courses/course-management"
              class="flex items-center px-4 py-2 rounded-lg transition-colors duration-200"
              :class="[
                $route.path === '/courses/course-management'
                  ? 'bg-slate-700 text-emerald-400'
                  : 'hover:bg-slate-700/50',
              ]"
            >
              <i class="fas fa-list-ul w-5"></i>
              <span class="ml-3 text-sm font-medium">Course Management</span>
            </router-link>

            <router-link
              to="/admin/courses/category-course"
              class="flex items-center px-4 py-2 rounded-lg transition-colors duration-200"
              :class="[
                $route.path === '/courses/category-course'
                  ? 'bg-slate-700 text-emerald-400'
                  : 'hover:bg-slate-700/50',
              ]"
            >
              <i class="fas fa-tags w-5"></i>
              <span class="ml-3 text-sm font-medium">Category Course Management</span>
            </router-link>
            <router-link
              to="/admin/courses/lecture-management"
              class="flex items-center px-4 py-2 rounded-lg transition-colors duration-200"
              :class="[
                $route.path === '/courses/lecture-management'
                  ? 'bg-slate-700 text-emerald-400'
                  : 'hover:bg-slate-700/50',
              ]"
            >
              <i class="fas fa-tags w-5"></i>
              <span class="ml-3 text-sm font-medium">Lecture Management</span>
            </router-link>
          </div>
        </div>

        <!-- Quiz -->
        <div class="space-y-2">
          <button
            @click="toggleSubmenu('quiz')"
            class="w-full flex items-center justify-between px-4 py-3 rounded-lg transition-colors duration-200"
            :class="[
              openedSubmenus.includes('quiz') || $route.path.includes('/quiz')
                ? 'bg-slate-700 text-emerald-400'
                : 'hover:bg-slate-700/50',
            ]"
          >
            <div class="flex items-center">
              <i class="fas fa-book w-5"></i>
              <span class="ml-3 text-sm font-medium">Quiz</span>
            </div>
            <i
              class="fas fa-chevron-down text-xs transition-transform duration-200"
              :class="{
                'transform rotate-180': openedSubmenus.includes('quiz'),
              }"
            ></i>
          </button>

          <!-- Submenu -->
          <div
            v-show="
              openedSubmenus.includes('quiz') || $route.path.includes('/quiz')
            "
            class="pl-4 space-y-2"
          >
            <router-link
              to="/admin/quiz/quiz-list"
              class="flex items-center px-4 py-3 rounded-lg transition-colors duration-200"
              :class="[
                $route.path === '/quiz-list'
                  ? 'bg-slate-700 text-emerald-400'
                  : 'hover:bg-slate-700/50',
              ]"
            >
              <i class="fas fa-users w-5"></i>
              <span class="ml-3 text-sm font-medium">Quiz Management</span>
            </router-link>

            <router-link
              to="/admin/quiz/question-answer-management"
              class="flex items-center px-4 py-2 rounded-lg transition-colors duration-200"
              :class="[
                $route.path === 'quiz/question-answer-management'
                  ? 'bg-slate-700 text-emerald-400'
                  : 'hover:bg-slate-700/50',
              ]"
            >
              <i class="fas fa-tags w-5"></i>
              <span class="ml-3 text-sm font-medium">Question/Answer Management</span>
            </router-link>

           
          </div>
        </div>

       

        <!-- User -->
        <div class="space-y-2">
          <button
            @click="toggleSubmenu('users')"
            class="w-full flex items-center justify-between px-4 py-3 rounded-lg transition-colors duration-200"
            :class="[
              openedSubmenus.includes('users') ||
              $route.path.includes('/users')
                ? 'bg-slate-700 text-emerald-400'
                : 'hover:bg-slate-700/50',
            ]"
          >
            <div class="flex items-center">
              <i class="fas fa-book w-5"></i>
              <span class="ml-3 text-sm font-medium">User</span>
            </div>
            <i
              class="fas fa-chevron-down text-xs transition-transform duration-200"
              :class="{
                'transform rotate-180': openedSubmenus.includes('users'),
              }"
            ></i>
          </button>

          <!-- Submenu -->
          <div
            v-show="
              openedSubmenus.includes('users') ||
              $route.path.includes('/users')
            "
            class="pl-4 space-y-2"
          >
            <router-link
              to="/admin/users/student-management"
              class="flex items-center px-4 py-2 rounded-lg transition-colors duration-200"
              :class="[
                $route.path === '/courses/course-management'
                  ? 'bg-slate-700 text-emerald-400'
                  : 'hover:bg-slate-700/50',
              ]"
            >
              <i class="fas fa-list-ul w-5"></i>
              <span class="ml-3 text-sm font-medium">Student Management</span>
            </router-link>
            <router-link
              to="/admin/users/teacher-management"
              class="flex items-center px-4 py-2 rounded-lg transition-colors duration-200"
              :class="[
                $route.path === '/users/teacher-management'
                  ? 'bg-slate-700 text-emerald-400'
                  : 'hover:bg-slate-700/50',
              ]"
            >
              <i class="fas fa-tags w-5"></i>
              <span class="ml-3 text-sm font-medium">Teacher Management</span>
            </router-link>
          </div>
        </div>
      </div>

      <!-- Settings Section -->
      <!-- <div class="mt-12 pt-4 border-t border-slate-700">
        <router-link
          to="/settings"
          class="flex items-center px-4 py-3 rounded-lg transition-colors duration-200"
          :class="[
            $route.path === '/settings'
              ? 'bg-slate-700 text-emerald-400'
              : 'hover:bg-slate-700/50',
          ]"
        >
          <i class="fas fa-cog w-5"></i>
          <span class="ml-3 text-sm font-medium">Cài đặt</span>
        </router-link>
      </div> -->
    </nav>
  </aside>
</template>

<script setup>
import { ref, watch } from "vue";
import { useRoute } from "vue-router";
const route = useRoute();
const openedSubmenus = ref([]);

const toggleSubmenu = (submenuId) => {
  if (openedSubmenus.value.includes(submenuId)) {
    openedSubmenus.value = openedSubmenus.value.filter(
      (id) => id !== submenuId
    );
  } else {
    openedSubmenus.value.push(submenuId);
  }
};

// Auto-expand relevant submenus based on current route
watch(
  () => route.path,
  (newPath) => {
    if (newPath.includes("/courses")) {
      openedSubmenus.value.push("courses");
    } else {
      openedSubmenus.value = openedSubmenus.value.filter(
        (id) => id !== "courses"
      );
    }

    if (newPath.includes("/quiz")) {
      openedSubmenus.value.push("quiz");
    } else {
      openedSubmenus.value = openedSubmenus.value.filter((id) => id !== "quiz");
    }

    // Add more checks for other submenus as needed
  },
  { immediate: true }
);

//     setup() {
//       const route = useRoute();
//       const openedSubmenus = ref([]);

//       const toggleSubmenu = (submenuId) => {
//         if (openedSubmenus.value.includes(submenuId)) {
//           openedSubmenus.value = openedSubmenus.value.filter(id => id !== submenuId);
//         } else {
//           openedSubmenus.value.push(submenuId);
//         }
//       };

//       // Auto-expand relevant submenus based on current route
//       watch(
//         () => route.path,
//         (newPath) => {
//           if (newPath.includes("/courses")) {
//             openedSubmenus.value.push("courses");
//           } else {
//             openedSubmenus.value = openedSubmenus.value.filter(id => id !== "courses");
//           }

//           if (newPath.includes("/quiz")) {
//             openedSubmenus.value.push("quiz");
//           } else {
//             openedSubmenus.value = openedSubmenus.value.filter(id => id !== "quiz");
//           }

//           // Add more checks for other submenus as needed
//         },
//         { immediate: true }
//       );

//       return {
//         openedSubmenus,
//         toggleSubmenu
//       };
//     }
//   };
</script>
