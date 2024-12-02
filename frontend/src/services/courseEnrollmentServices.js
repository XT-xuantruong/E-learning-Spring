import ApiService from "./ApiService";
// import { useAdminStore } from "@/stores/admin";

class CourseEnrollmentServices extends ApiService {
  get entity() {
    return "courseEnrollment";
  }
  async update(data) {
    // const adminStore = useAdminStore();
    // console.log(adminStore.admin.isAuthenticated, adminStore.admin.access);
    // console.log(data);

    const { id, ...fields } = data;
    const formData = new FormData();

    // Thêm dữ liệu vào FormData
    for (const key in fields) {
      if (fields[key] !== undefined) {
        formData.append(key, fields[key]);
      }
    }
    const option = {
      method: "put",
      url: `/${this.entity}/${id}`,
      data: formData,
      headers: {
        "Content-Type": "multipart/form-data",
        // Authorization: `Bearer ${adminStore.admin.access}`,
      },
    };
    return this.request(option);
  }

  async create(data) {
    return this.request({
      method: "post",
      url: `/${this.entity}`,
      data: data,
    });
  }
  async getbyuser(params) {
    console.log(params);

    const option = {
      method: "get",
      url: `/${this.entity}/get-by-user`,
      params: { ...params },
    };
    return this.request(option);
  }

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

export default new CourseEnrollmentServices();
