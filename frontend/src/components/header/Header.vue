<template>
  <header class="bg-white shadow-sm sticky top-0 z-50">
    <nav class="container mx-auto px-4 py-4">
      <div class="flex justify-between items-center">
        <!-- Logo -->
        <RouterLink to="/">
          <h1 class="text-2xl font-bold text-blue-600">E-Learning</h1>
        </RouterLink>

        <!-- Search and Navigation -->
        <div class="flex items-center flex-1 max-w-2xl mx-12">
          <!-- Search Container -->
          <div class="relative flex-1 max-w-xl">
            <form @submit.prevent="handleSearch" class="relative">
              <input v-model="searchQuery" @focus="isSearchFocused = true"
                @blur="setTimeout(() => (isSearchFocused = false), 200)" type="text"
                placeholder="Tìm kiếm khóa học, giảng viên..."
                class="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" />
              <Search class="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 w-5 h-5 cursor-pointer"
                @click="handleSearch" />
              <button v-if="searchQuery" type="button" @click="clearSearch"
                class="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400 hover:text-gray-600">
                <X class="w-4 h-4" />
              </button>
            </form>

            <!-- Search Suggestions Dropdown -->
            <div v-if="isSearchFocused && filteredSuggestions.length > 0"
              class="absolute top-full left-0 right-0 mt-1 bg-white border rounded-md shadow-lg z-50">
              <div class="p-2">
                <div v-for="(suggestion, index) in filteredSuggestions" :key="index"
                  @click="handleSuggestionClick(suggestion)"
                  class="flex items-center px-3 py-2 hover:bg-gray-50 rounded cursor-pointer">
                  <!-- Icon based on type -->
                  <div class="w-8 h-8 rounded-full bg-blue-100 flex items-center justify-center mr-3">
                    <span class="text-blue-600 text-sm">
                      {{
                        suggestion.type === "course"
                          ? "📚"
                          : suggestion.type === "instructor"
                            ? "👨‍🏫"
                            : "📝"
                      }}
                    </span>
                  </div>
                  <div>
                    <div class="text-sm font-medium">
                      {{ suggestion.title }}
                    </div>
                    <div class="text-xs text-gray-500">
                      {{ suggestion.category }}
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Navigation Links -->
        <div class="flex items-center ml-6 space-x-6">
          <a v-for="link in ['Khóa học', 'Giảng viên']" :key="link" href="#"
            class="text-gray-700 hover:text-blue-600 transition-colors duration-200">
            {{ link }}
          </a>
        </div>

        <!-- Auth Buttons -->
        <div v-if="!userStore.user.isAuthenticated" class="flex items-center space-x-4">
          <button @click="handleLogin" class="px-4 py-2 text-blue-600 hover:text-blue-700 transition-colors">
            Đăng nhập
          </button>
          <button @click="handleSignup"
            class="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition-colors">
            Đăng ký
          </button>
        </div>

        <!-- User Profile Dropdown -->
        <div v-else class="relative">
          <button @click="toggleDropdown" class="flex items-center space-x-2 focus:outline-none">
            <img :src="userStore.user.avatar
              ? 'http://127.0.0.1:8092' + userStore.user.avatar
              : 'https://i.pinimg.com/1200x/bc/43/98/bc439871417621836a0eeea768d60944.jpg'
              " alt="User avatar" class="w-8 h-8 rounded-full object-cover border-2 border-indigo-600" />
            <svg :class="{ 'rotate-180': isDropdownOpen }" class="w-4 h-4 transition-transform duration-200" fill="none"
              stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
            </svg>
          </button>

          <!-- Dropdown Menu -->
          <div v-if="isDropdownOpen" class="absolute right-0 mt-2 w-48 bg-white rounded-md shadow-lg py-1 z-50">
            <div class="px-4 py-2 border-b">
              <p class="text-sm font-medium text-gray-900">
                {{ userStore.user.name }}
              </p>
              <p class="text-sm text-gray-500">{{ userStore.user.email }}</p>
            </div>

            <RouterLink v-for="(item, index) in menuItems" :key="index" :to="item.to"
              class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100" @click="isDropdownOpen = false">
              {{ item.label }}
            </RouterLink>

            <button @click="handleLogout" class="w-full text-left px-4 py-2 text-sm text-red-600 hover:bg-gray-100">
              Đăng xuất
            </button>
          </div>
        </div>
      </div>
    </nav>
  </header>
</template>

<script setup>
import { ref, computed, onBeforeUnmount, onMounted } from "vue";
import { useRouter } from "vue-router";
import { Search, X } from "lucide-vue-next";
import { useUserStore } from "@/stores/user";
import oauthServices from "@/services/oauthServices";

const router = useRouter();
const searchQuery = ref("");
const isSearchFocused = ref(false);
const userStore = useUserStore();
const isDropdownOpen = ref(false);

// Mock data for search suggestions
const searchSuggestions = ref([
  {
    type: "course",
    title: "Lập trình Web Frontend",
    category: "Web Development",
  },
  {
    type: "course",
    title: "React.js Nâng cao",
    category: "JavaScript",
  },
  {
    type: "instructor",
    title: "TS. Nguyễn Văn A",
    category: "Giảng viên",
  },
  {
    type: "blog",
    title: "Học lập trình nên bắt đầu từ đâu?",
    category: "Blog",
  },
]);

const filteredSuggestions = computed(() => {
  if (!searchQuery.value) return [];
  return searchSuggestions.value.filter((item) =>
    item.title.toLowerCase().includes(searchQuery.value.toLowerCase())
  );
});

const clearSearch = () => {
  searchQuery.value = "";
};

const handleSearch = () => {
  if (searchQuery.value.trim()) {
    router.push({
      name: "search",
      query: {
        q: searchQuery.value,
      },
    });
    isSearchFocused.value = false;
  }
};

const handleSuggestionClick = (suggestion) => {
  searchQuery.value = suggestion.title;
  handleSearch();
};

const menuItems = [
  {
    label: "Thông tin cá nhân",
    to: { name: "myaccount", query: { tab: "profile" } },
  },
];

const toggleDropdown = () => {
  isDropdownOpen.value = !isDropdownOpen.value;
};

const closeDropdown = (e) => {
  if (!e.target.closest(".relative")) {
    isDropdownOpen.value = false;
  }
};

onMounted(() => {
  document.addEventListener("click", closeDropdown);
});

onBeforeUnmount(() => {
  document.removeEventListener("click", closeDropdown);
});

const handleLogout = async () => {
  await oauthServices.logout(userStore.user.access, userStore.user.refresh);
  userStore.removeToken();
  userStore.$reset();
  router.push("/signin");
};

const handleLogin = () => {
  router.push({ name: "signin" });
};

const handleSignup = () => {
  router.push({ name: "signup" });
};
</script>

<style scoped>
.search-transition-enter-active,
.search-transition-leave-active {
  transition: opacity 0.2s ease;
}

.search-transition-enter-from,
.search-transition-leave-to {
  opacity: 0;
}
</style>
