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

export const publicRoutes = [
<<<<<<< HEAD
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
    component: StudyView,
  },
  {
    path: "/quiz",
    name: "quiz",
    component: QuizView,
  },
  {
    path: "/myaccount",
    name: "myaccount",
    component: MyAccountView,
  },
  {
    path: "/checkout",
    name: "checkout",
    component: CheckoutView,
  },
];
=======

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
      component: StudyView,
    },
    {
      path: "/quiz",
      name: "quiz",
      component: QuizView,
    },
    {
      path: "/myaccount",
      name: "myaccount",
      component: MyAccountView,
    },
    {
      path: "/checkout",
      name: "checkout",
      component: CheckoutView,
    },
    {
      path: "/signup",
      name: "signup",
      component: SignupView,
    },

]
>>>>>>> 15976a4a38b3c10363d345868b6c1a818a0628ef
