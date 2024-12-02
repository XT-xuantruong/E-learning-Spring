import ApiService from "./ApiService";
import { useAdminStore } from "@/stores/admin";

class LectureServices extends ApiService {
  get entity() {
    return "lecture";
  }
  async update(data) {
    const adminStore = useAdminStore();
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
    console.log(data);
    
    const adminStore = useAdminStore();
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

export default new LectureServices();
