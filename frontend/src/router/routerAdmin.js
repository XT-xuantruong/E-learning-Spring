// router/admin/routes.js
import Dashboard from "@/views/DashboardView.vue";
import CourseManagement from "@/views/CourseManagementView.vue";
import CategoryCourse from "@/views/CategoryCourseView.vue";
import QuizList from "@/views/QuizListView.vue";
import QAManagement from "@/views/QAManagementView.vue";
import ScopeManagement from "@/views/ScopeManagementView.vue";
import LectureManagement from "@/views/LectureManagementView.vue";
import StudentManagement from "@/views/StudentManagementView.vue";
import TeacherManagement from "@/views/TeacherManagementView.vue";
import Login from "@/views/LoginView.vue";
import DashboardView from "@/views/DashboardView.vue";

export const adminRoutes = [
  {
    path: "/admin",
    name: "admin",
    meta: { requiresAuth: false },
    children: [
      {
        path: "login",
        name: "Login",
        component: Login,
        meta: {
          title: "Login",
        },
      },
     
      {
        path: "courses/course-management",
        name: "CourseManagement",
        component: CourseManagement,
        meta: {
          title: "CourseManagement",
        },
      },
      {
        path: "courses/category-course",
        name: "CategoryCourse",
        component: CategoryCourse,
        meta: {
          title: "CategoryCourse",
        },
      },
      {
        path: "courses/lecture-management",
        name: "LectureManagement",
        component: LectureManagement,
        meta: {
          title: "LectureManagement",
        },
      },
      {
        path: "quiz/quiz-list",
        name: "QuizList",
        component: QuizList,
        meta: {
          title: "QuizList",
        },
      },
      {
        path: "quiz/scope-management",
        name: "ScopeManagement",
        component: ScopeManagement,
        meta: {
          title: "ScopeManagement",
        },
      },
      {
        path: "quiz/question-answer-management",
        name: "QAManagement",
        component: QAManagement,
        meta: {
          title: "QAManagement",
        },
      },
     
      {
        path: "users/student-management",
        name: "StudentManagement",
        component: StudentManagement,
        meta: {
          title: "StudentManagement",
        },
      },
      {
        path: "users/teacher-management",
        name: "TeacherManagement",
        component: TeacherManagement,
        meta: {
          title: "TeacherManagement",
        },
      },
    ],
  },
];
