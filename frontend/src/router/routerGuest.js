import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import CouseListView from "@/views/CouseListView.vue";
import SearchResultView from "@/views/SearchResultView.vue";
import CourseDetailView from "@/views/CourseDetailView.vue";
import StudyView from "@/views/StudyView.vue";
import QuizView from "@/views/QuizView.vue";
import MyAccountView from "@/views/MyAccountView.vue";
import CheckoutView from "@/views/CheckoutView.vue";
import SignupView from "@/views/SignupView.vue";
import SigninView from "@/views/SigninView.vue";

export const publicRoutes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/category",
    name: "category",
    component: CouseListView,
  },
  {
    path: "/search",
    name: "search",
    component: SearchResultView,
  },
  {
    path: "/course",
    name: "course-detail",
    component: CourseDetailView,
  },
  {
    path: "/study",
    name: "study",
    meta: { requiresAuth: true },
    component: StudyView,
  },
  {
    path: "/quiz",
    name: "quiz",
    meta: { requiresAuth: true },
    component: QuizView,
  },
  {
    path: "/myaccount",
    name: "myaccount",
    meta: { requiresAuth: true },
    component: MyAccountView,
  },
  {
    path: "/checkout",
    name: "checkout",
    meta: { requiresAuth: true },
    component: CheckoutView,
  },
  {
    path: "/signup",
    name: "signup",
    component: SignupView,
  },
  {
    path: "/signin",
    name: "signin",
    component: SigninView,
  },
];
