<template>
    <!-- Categories Section -->
    <section class="py-16 container mx-auto px-4">
        <div class="flex justify-between items-center border-b pb-2 mb-12">
            <h2 class="text-3xl font-bold">Danh mục khóa học</h2>
            <a href="/categories" class="text-blue-600 hover:text-blue-800 font-medium transition-colors">
                Xem tất cả
                <span class="ml-1">→</span>
            </a>
        </div>
        <div class="grid grid-cols-1 md:grid-cols-3 lg:grid-cols-4 gap-6">
            <div v-for="category in ['Web Development', 'Mobile Development', 'UI/UX Design', 'Data Science',
                'Machine Learning', 'DevOps', 'Digital Marketing', '...']" :key="category" class="bg-white p-6 rounded-lg shadow-sm hover:shadow-md transition-shadow cursor-pointer
                    border-2 border-transparent hover:border-blue-600">
                <h3 class="text-lg font-semibold text-center">{{ category }}</h3>
            </div>
        </div>
    </section>
</template>