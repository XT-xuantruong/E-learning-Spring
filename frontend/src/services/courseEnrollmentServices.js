import ApiService from "./ApiService";
// import { useAdminStore } from "@/stores/admin";

class CourseEnrollmentServices extends ApiService {
  get entity() {
    return "courseEnrollment";
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

  async create(data) {
    return this.request({
      method: "post",
      url: `/${this.entity}`,
      data: data,
    });
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
