<!-- src/layouts/DefaultLayout.vue -->
<template>
  <div class="flex h-screen bg-gray-50">
    <!-- Sidebar (fixed) -->
    <Sidebar class="fixed inset-y-0 left-0 w-64 overflow-y-auto" />

    <!-- Main Content -->
    <div class="flex-1 flex flex-col ml-64">
      <!-- Header (fixed) -->
      <Header @toggle-sidebar="toggleSidebar" class="sticky top-0" />

      <!-- Page Content (scrollable) -->
      <main class="flex-1  bg-gray-50 p-6">
        <slot></slot>
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import Sidebar from '@/components/Sidebar/Sidebar.vue';
import Header from '@/components/Header/Header.vue';

const toggleSidebar = () => {
  console.log('Toggle sidebar');
};
</script>