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
                @blur="setTimeout(() => isSearchFocused = false, 200)" type="text"
                placeholder="Tìm kiếm khóa học, giảng viên..."
                class="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" />
              <Search class="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 w-5 h-5 cursor-pointer"
                @click="handleSearch" />
              <button v-if="searchQuery" type="button" @click="clearSearch"
                class="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400 hover:text-gray-600">
                <X class="w-4 h-4" />
              </button>
            </form>

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
        <div class="flex items-center space-x-4">
          <button class="px-4 py-2 text-blue-600 hover:text-blue-700 transition-colors">
            Đăng nhập
          </button>
          <button class="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition-colors">
            Đăng ký
          </button>
        </div>
      </div>
    </nav>
  </header>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter, RouterLink } from 'vue-router'
import { Search, X } from 'lucide-vue-next'

const router = useRouter()
const searchQuery = ref('')
const isSearchFocused = ref(false)

// Mock data for search suggestions
const searchSuggestions = ref([
  {
    type: 'course',
    title: 'Lập trình Web Frontend',
    category: 'Web Development'
  },
  {
    type: 'course',
    title: 'React.js Nâng cao',
    category: 'JavaScript'
  },
  {
    type: 'instructor',
    title: 'TS. Nguyễn Văn A',
    category: 'Giảng viên'
  },
  {
    type: 'blog',
    title: 'Học lập trình nên bắt đầu từ đâu?',
    category: 'Blog'
  }
])

const filteredSuggestions = computed(() => {
  if (!searchQuery.value) return []
  return searchSuggestions.value.filter(item =>
    item.title.toLowerCase().includes(searchQuery.value.toLowerCase())
  )
})

const clearSearch = () => {
  searchQuery.value = ''
}

const handleSearch = () => {
  if (searchQuery.value.trim()) {
    router.push({
      name: 'search',
      query: {
        q: searchQuery.value
      }
    })
    isSearchFocused.value = false
  }
}

const handleSuggestionClick = (suggestion) => {
  searchQuery.value = suggestion.title
  handleSearch()
}
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