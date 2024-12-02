import ApiService from "./ApiService";
// import { useAdminStore } from "@/stores/admin";

class QuizResultServices extends ApiService {
  get entity() {
    return "quiz-result";
  }
  async getByUser(id) {
    return this.request({
      method: "get",
      url: `/${this.entity}/get-by-user/${id}`,
    });
  }
  async create(data) {
    // const adminStore = useAdminStore();
    // console.log(adminStore.admin.isAuthenticated, adminStore.admin.access);
    // console.log(data);

    const formData = new FormData();

    // Thêm dữ liệu vào FormData
    for (const key in data) {
      if (data[key] !== undefined) {
        formData.append(key, data[key]);
      }
    }
    const option = {
      method: "post",
      url: `/${this.entity}`,
      data: formData,
      headers: {
        "Content-Type": "multipart/form-data",
        // Authorization: `Bearer ${adminStore.admin.access}`,
      },
    };
    return this.request(option);
  }
  // async update(data) {
  //   // const adminStore = useAdminStore();
  //   // console.log(adminStore.admin.isAuthenticated, adminStore.admin.access);
  //   // console.log(data);

  //   const { _id } = data;
  //   const option = {
  //     method: "put",
  //     url: `/${this.entity}/${_id}/`,
  //     data: data,
  //     // headers: {
  //     //   Authorization: `Bearer ${adminStore.admin.access}`,
  //     // },
  //   };
  //   return this.request(option);
  // }

  // async create(data) {
  //   const adminStore = useAdminStore();
  //   const formData = new FormData();

  //   formData.append("name", data.name);
  //   formData.append("thumbnail", data.thumbnail);
  //   formData.append("images", data.images);
  //   formData.append("price", data.price);
  //   formData.append("description", data.description);
  //   formData.append("category", data.category);
  //   formData.append("countInStock", data.countInStock);
  //   const option = {
  //     method: "post",
  //     url: `/${this.entity}/`,
  //     data: data,
  //     // headers: {
  //     //   "Content-Type": "multipart/form-data",
  //     //   Authorization: `Bearer ${adminStore.admin.access}`,
  //     // },
  //   };
  //   return this.request(option);
  // }

  // async delete(id) {
  //   const adminStore = useAdminStore();
  //   return this.request({
  //     method: "delete",
  //     url: `/${this.entity}/${id}/`,
  //     headers: {
  //       Authorization: `Bearer ${adminStore.admin.access}`,
  //     },
  //   });
  // }
}

export default new QuizResultServices();
