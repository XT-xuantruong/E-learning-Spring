import ApiService from "./ApiService";
import { useAdminStore } from "@/stores/admin";

class CourseServices extends ApiService {
  get entity() {
    return "courses";
  }
  async update(data) {
    const adminStore = useAdminStore();
    data.createBy = adminStore.admin.id;
    const { id } = data;
    const option = {
      method: "put",
      url: `/${this.entity}/${id}`,
      data: data,
      headers: {
        Authorization: `Bearer ${adminStore.admin.access}`,
        "Content-Type": "multipart/form-data",
      },
    };
    return this.request(option);
  }


  async create(data) {
    const adminStore = useAdminStore();
    data.createBy = adminStore.admin.id;

    console.log(data);

    return this.request({
      method: "post",
      url: `/${this.entity}`,
      headers: {
        Authorization: `Bearer ${adminStore.admin.access}`,
        "Content-Type": "multipart/form-data",
      },
      data: data,
    });
  }

  async gets() {
    const adminStore = useAdminStore();
    return this.request({
      method: "get",
      url: `/${this.entity}`,
      headers: {
        Authorization: `Bearer ${adminStore.admin.access}`,
      },
    });
  }

  async delete(id) {
    const adminStore = useAdminStore();
    return this.request({
      method: "delete",
      url: `/${this.entity}/${id}`,
      headers: {
        Authorization: `Bearer ${adminStore.admin.access}`,
      },
    });
  }
}

export default new CourseServices();
