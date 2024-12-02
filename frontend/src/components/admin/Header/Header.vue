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
      </div>

      <!-- Right side -->
      <div class="flex items-center gap-4">
        <!-- Notifications -->
        <button
          class="relative p-2 text-slate-500 hover:text-slate-600 transition-colors"
        >
          <i class="fas fa-bell"></i>
          <span
            class="absolute top-0 right-0 h-2 w-2 rounded-full bg-red-500"
          ></span>
        </button>

        <!-- Profile Dropdown -->
        <div class="relative" @click="isProfileOpen = !isProfileOpen">
          <button
            class="flex items-center gap-3 hover:bg-gray-50 rounded-lg p-2 transition-colors"
          >
            <div class="hidden md:block text-left">
              <p class="text-sm font-medium text-slate-700">
                {{ adminStore.admin.name }}
              </p>
              <p class="text-xs text-slate-500">{{ adminStore.admin.email }}</p>
            </div>
            <i class="fas fa-chevron-down text-slate-400 text-sm"></i>
          </button>

          <!-- Dropdown Menu -->
          <div
            v-if="isProfileOpen"
            class="absolute right-0 mt-2 w-48 bg-white rounded-lg shadow-lg border border-gray-100 py-2"
          >
            <button
              @click="logOut"
              class="flex items-center gap-3 px-4 py-2 text-sm text-red-600 hover:bg-gray-50"
            >
              <i class="fas fa-sign-out-alt w-4"></i>
              <span>Đăng xuất</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  </header>
</template>

<script setup>
import { ref } from "vue";
import { useAdminStore } from "@/stores/admin";
import oauthServices from "@/services/oauthServices";
import { useRouter } from "vue-router";

const adminStore = useAdminStore();
const router = useRouter();

const isProfileOpen = ref(false);

const logOut = async () => {
  try {
    await oauthServices.logout(
      adminStore.admin.access,
      adminStore.admin.refresh
    );

    adminStore.removeToken();
    router.push("/admin/login");
  } catch (error) {
    formError.value = "Invalid email or password";
    console.error("Login error:", error);
  }
};
</script>
