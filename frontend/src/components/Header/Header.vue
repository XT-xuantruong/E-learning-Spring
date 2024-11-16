<!-- src/components/Header.vue -->
<template>
  <header class="bg-white shadow-sm border-b border-gray-200">
    <div class="flex justify-between items-center px-6 py-4">
      <!-- Left side -->
      <div class="flex items-center gap-4">
        <button 
          @click="$emit('toggle-sidebar')"
          class="text-slate-500 hover:text-slate-600 transition-colors"
        >
          <i class="fas fa-bars text-lg"></i>
        </button>
        
        <!-- Search Bar -->
        <div class="hidden md:flex items-center">
          <div class="relative">
            <span class="absolute inset-y-0 left-0 pl-3 flex items-center">
              <i class="fas fa-search text-gray-400"></i>
            </span>
            <input 
              type="text" 
              placeholder="Tìm kiếm..." 
              class="pl-10 pr-4 py-2 rounded-lg border border-gray-200 focus:outline-none focus:ring-2 focus:ring-emerald-500 focus:border-transparent w-64"
            >
          </div>
        </div>
      </div>
      
      <!-- Right side -->
      <div class="flex items-center gap-4">
        <!-- Notifications -->
        <button class="relative p-2 text-slate-500 hover:text-slate-600 transition-colors">
          <i class="fas fa-bell"></i>
          <span class="absolute top-0 right-0 h-2 w-2 rounded-full bg-red-500"></span>
        </button>
        
        <!-- Profile Dropdown -->
        <div class="relative" @click="isProfileOpen = !isProfileOpen">
          <button class="flex items-center gap-3 hover:bg-gray-50 rounded-lg p-2 transition-colors">
            <img 
              src="https://via.placeholder.com/40" 
              alt="Profile" 
              class="w-8 h-8 rounded-full object-cover border-2 border-emerald-500"
            >
            <div class="hidden md:block text-left">
              <p class="text-sm font-medium text-slate-700">Admin User</p>
              <p class="text-xs text-slate-500">admin@example.com</p>
            </div>
            <i class="fas fa-chevron-down text-slate-400 text-sm"></i>
          </button>
          
          <!-- Dropdown Menu -->
          <div 
            v-if="isProfileOpen" 
            class="absolute right-0 mt-2 w-48 bg-white rounded-lg shadow-lg border border-gray-100 py-2"
          >
            <a href="#" class="flex items-center gap-3 px-4 py-2 text-sm text-slate-600 hover:bg-gray-50">
              <i class="fas fa-user w-4"></i>
              <span>Hồ sơ</span>
            </a>
            <a href="#" class="flex items-center gap-3 px-4 py-2 text-sm text-slate-600 hover:bg-gray-50">
              <i class="fas fa-cog w-4"></i>
              <span>Cài đặt</span>
            </a>
            <hr class="my-2 border-gray-100">
            <a href="#" class="flex items-center gap-3 px-4 py-2 text-sm text-red-600 hover:bg-gray-50">
              <i class="fas fa-sign-out-alt w-4"></i>
              <span>Đăng xuất</span>
            </a>
          </div>
        </div>
      </div>
    </div>
  </header>
</template>

<script setup>
import { ref } from 'vue';

const isProfileOpen = ref(false);
</script>