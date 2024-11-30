<script setup>
import { ref, onMounted, onBeforeMount, onBeforeUnmount } from 'vue'
import { ChevronLeft, ChevronRight, Book, Users, Video, Award } from 'lucide-vue-next'
import CourseCard from '@/components/course/CourseCard.vue';
import DefaultLayout from '@/layouts/user/DefaultLayout.vue';
import Slider from '@/components/slider/Slider.vue';
import CategorySection from '@/components/category/CategorySection.vue';
import categoryServices from '@/services/categoryServices';
import courseServices from '@/services/courseServices';

const categories = ref([])
const courseList = ref([])
const fetchCourse = async () => {
  await courseServices.gets()
    .then(response => {
      courseList.value = response.data.data
    })
    .catch(error => {
      console.error(error)
    })
}
const fetchCategory = async () => {
  await categoryServices.gets()
    .then(response => {
      categories.value = response.data.data
    })
    .catch(error => {
      console.error(error)
    })
}
onBeforeMount(() => {
  fetchCourse()
  fetchCategory()
})
// Instructors data
const instructors = ref([
  {
    id: 1,
    name: 'TS. Nguyễn Văn A',
    role: 'Senior Frontend Developer',
    image: '/api/placeholder/300/300',
    description: '10 năm kinh nghiệm phát triển web'
  },
  {
    id: 2,
    name: 'ThS. Trần Thị B',
    role: 'UX/UI Designer',
    image: '/api/placeholder/300/300',
    description: 'Chuyên gia thiết kế với 8 năm kinh nghiệm'
  },
  {
    id: 3,
    name: 'KS. Lê Văn C',
    role: 'Backend Developer',
    image: '/api/placeholder/300/300',
    description: 'Phát triển hệ thống cho các tập đoàn lớn'
  }
])

// Reviews data
const reviews = ref([
  {
    id: 1,
    name: 'Nguyễn Văn X',
    avatar: '/api/placeholder/100/100',
    comment: 'Khóa học rất hay và chi tiết, giảng viên nhiệt tình',
    rating: 5
  },
  {
    id: 2,
    name: 'Trần Thị Y',
    avatar: '/api/placeholder/100/100',
    comment: 'Tài liệu đầy đủ, dễ hiểu, support nhanh chóng',
    rating: 5
  },
  {
    id: 3,
    name: 'Phạm Văn Z',
    avatar: '/api/placeholder/100/100',
    comment: 'Học xong đã có thể áp dụng vào công việc thực tế',
    rating: 4
  }
])


// // Previous data remains the same
// const slides = ref([/* ... previous slides data ... */])
// const courses = ref([/* ... previous courses data ... */])
// const instructors = ref([/* ... previous instructors data ... */])
// const reviews = ref([/* ... previous reviews data ... */])

// Stats data
const stats = ref([
  {
    icon: Book,
    count: '1000+',
    label: 'Khóa học',
    color: 'text-blue-600'
  },
  {
    icon: Users,
    count: '50,000+',
    label: 'Học viên',
    color: 'text-green-600'
  },
  {
    icon: Video,
    count: '5,000+',
    label: 'Video bài giảng',
    color: 'text-yellow-600'
  },
  {
    icon: Award,
    count: '200+',
    label: 'Giảng viên',
    color: 'text-purple-600'
  }
])

// FAQs data
const faqs = ref([
  {
    question: 'Làm sao để bắt đầu một khóa học?',
    answer: 'Bạn cần đăng ký tài khoản, chọn khóa học mong muốn và thanh toán. Sau đó bạn có thể truy cập khóa học ngay lập tức.',
    isOpen: false
  },
  {
    question: 'Thời hạn truy cập khóa học là bao lâu?',
    answer: 'Bạn có quyền truy cập vĩnh viễn vào các khóa học đã mua. Có thể học bất cứ lúc nào và xem lại không giới hạn.',
    isOpen: false
  },
  {
    question: 'Làm sao để được hỗ trợ khi gặp khó khăn?',
    answer: 'Chúng tôi có đội ngũ support 24/7 qua chat, email và hotline. Giảng viên cũng sẽ trả lời các câu hỏi trong phần thảo luận của khóa học.',
    isOpen: false
  },
  {
    question: 'Có được cấp chứng chỉ sau khi hoàn thành không?',
    answer: 'Có, bạn sẽ nhận được chứng chỉ hoàn thành có giá trị sau khi hoàn thành tất cả các bài học và bài kiểm tra.',
    isOpen: false
  }
])

// Login/Signup modal controls
const showLoginModal = ref(false)
const showSignupModal = ref(false)
const loginForm = ref({
  email: '',
  password: ''
})
const signupForm = ref({
  name: '',
  email: '',
  password: '',
  confirmPassword: ''
})

// Newsletter form
const newsletterEmail = ref('')

const handleNewsletterSubmit = () => {
  // Handle newsletter signup logic
  alert('Cảm ơn bạn đã đăng ký nhận tin!')
  newsletterEmail.value = ''
}

// Previous methods remain the same
</script>

<template>
  <DefaultLayout>
    <Slider />
    <CategorySection :categories="categories" />
    <!-- Courses Grid -->
    <section class="py-16 container mx-auto px-4">
      <div class="flex justify-between items-center mb-12">
        <h2 class="text-3xl font-bold">Khóa học nổi bật</h2>
        <a href="/courses" class="text-blue-600 hover:text-blue-800 font-medium transition-colors">
          Xem tất cả
          <span class="ml-1">→</span>
        </a>
      </div>
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        <CourseCard v-for="course in courseList" :key="course.id" :course="course" />
      </div>
    </section>
    <!-- FAQ Section -->
    <section class="py-16 bg-gray-50">
      <div class="container mx-auto px-4">
        <h2 class="text-3xl font-bold text-center mb-12">Câu hỏi thường gặp</h2>
        <div class="max-w-3xl mx-auto space-y-4">
          <div v-for="(faq, index) in faqs" :key="index" class="bg-white rounded-lg shadow-sm">
            <button @click="faq.isOpen = !faq.isOpen"
              class="w-full px-6 py-4 text-left font-semibold flex justify-between items-center">
              {{ faq.question }}
              <span class="transform transition-transform" :class="faq.isOpen ? 'rotate-180' : ''">▼</span>
            </button>
            <div v-show="faq.isOpen" class="px-6 py-4 text-gray-600 border-t">
              {{ faq.answer }}
            </div>
          </div>
        </div>
      </div>
    </section>
  </DefaultLayout>
</template>

<style scoped>
/* Previous styles remain the same */
</style>