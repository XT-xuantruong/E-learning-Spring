// src/faker/reviews.js
const reviews = [
  {
    id: 1,
    course_id: 9,
    user_id: 1,
    rating: 5,
    comment:
      "Khóa học rất chi tiết và dễ hiểu. Giảng viên nhiệt tình, tài liệu đầy đủ. Tôi đã học được rất nhiều từ khóa học này.",
    created_at: "2024-03-15",
    user: {
      name: "Trần Văn An",
      avatar: "/api/placeholder/40/40",
      title: "Frontend Developer",
    },
  },
  {
    id: 2,
    course_id: 9,
    user_id: 2,
    rating: 4,
    comment:
      "Nội dung khóa học rất thực tế, giúp tôi hiểu rõ hơn về Vue 3 và Tailwind CSS. Tuy nhiên, phần exercises có thể được bổ sung thêm.",
    created_at: "2024-03-10",
    user: {
      name: "Nguyễn Thị Bình",
      avatar: "/api/placeholder/40/40",
      title: "Web Developer",
    },
  },
  {
    id: 3,
    course_id: 9,
    user_id: 3,
    rating: 5,
    comment:
      "Tuyệt vời! Đã học được nhiều kiến thức mới và áp dụng được vào dự án thực tế ngay lập tức.",
    created_at: "2024-03-05",
    user: {
      name: "Lê Hoàng Nam",
      avatar: "/api/placeholder/40/40",
      title: "Full-stack Developer",
    },
  },
  {
    id: 4,
    course_id: 9,
    user_id: 4,
    rating: 5,
    comment:
      "Giảng viên rất tâm huyết, giải thích chi tiết và dễ hiểu. Source code và tài liệu đầy đủ.",
    created_at: "2024-02-28",
    user: {
      name: "Phạm Thị Dung",
      avatar: "/api/placeholder/40/40",
      title: "UX/UI Designer",
    },
  },
  {
    id: 5,
    course_id: 9,
    user_id: 5,
    rating: 4,
    comment:
      "Khóa học rất hay, đặc biệt là phần Composition API và Tailwind CSS. Mong có thêm các khóa nâng cao.",
    created_at: "2024-02-20",
    user: {
      name: "Hoàng Văn Em",
      avatar: "/api/placeholder/40/40",
      title: "Software Engineer",
    },
  },
];

export default reviews;
