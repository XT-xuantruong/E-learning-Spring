import { createRouter, createWebHistory } from 'vue-router'


import Dashboard from '@/views/DashboardView.vue'
import CourseManagement from '@/views/CourseManagementView.vue'
import CategoryCourse from '@/views/CategoryCourseView.vue'
import QuizList from '@/views/QuizListView.vue'
import QAManagement from '@/views/QAManagementView.vue'
import ReviewManagement from '@/views/ReviewManagementView.vue'
import ScopeManagement from '@/views/ScopeManagementView.vue'
import LectureManagement from '@/views/LectureManagementView.vue'
import StudentManagement from '@/views/StudentManagementView.vue'
import TeacherManagement from '@/views/TeacherManagementView.vue'
import Login from '@/views/LoginView.vue'

const routes = [
  {
    path: '/',
    name: 'dashboard',
    component: Dashboard,
    meta: {
      title: 'dashboard',
    },
  },
  {
    path: '/courses/course-management',
    name: 'CourseManagement',
    component: CourseManagement,
    meta: {
      title: 'CourseManagement',
    },
  },
  {
    path: '/courses/category-course',
    name: 'CategoryCourse',
    component: CategoryCourse,
    meta: {
      title: 'CategoryCourse',
    },
  },
  {
    path: '/courses/lecture-management',
    name: 'LectureManagement',
    component: LectureManagement,
    meta: {
      title: 'LectureManagement',
    },
  },
  {
    path: '/quiz/quiz-list',
    name: 'QuizList',
    component: QuizList,
    meta: {
      title: 'QuizList',
    },
  },
  {
    path: '/quiz/scope-management',
    name: 'ScopeManagement',
    component: ScopeManagement,
    meta: {
      title: 'ScopeManagement',
    },
  },
  {
    path: '/quiz/question-answer-management',
    name: 'QAManagement',
    component: QAManagement,
    meta: {
      title: 'QAManagement',
    },
  },
  {
    path: '/review',
    name: 'ReviewManagement',
    component: ReviewManagement,
    meta: {
      title: 'ReviewManagement',
    },
  },
  {
    path: '/users/student-management',
    name: 'StudentManagement',
    component: StudentManagement,
    meta: {
      title: 'StudentManagement',
    },
  },
  {
    path: '/users/teacher-management',
    name: 'TeacherManagement',
    component: TeacherManagement,
    meta: {
      title: 'TeacherManagement',
    },
  },
  {
    path: '/login',
    name: 'Login',
    component: Login,
    meta: {
      title: 'Login',
    },
  },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

// router.beforeEach((to, from, next) => {
//   document.title = `Vue.js ${to.meta.title} | TailAdmin - Vue.js Tailwind CSS Dashboard Template`
//   next()
// })

export default router
