import { useAdminStore } from "@/stores/admin";
import ApiService from "./ApiService";

class CategoryServices extends ApiService {
  get entity() {
    return "category";
  }

  async getByslug(slug) {
    return this.request({
      method: "get",
      url: `/${this.entity}/get-slug/${slug}`,
    });
  }
  async create(data) {
    const adminStore = useAdminStore();
    return this.request({
      method: "post",
      url: `/${this.entity}`,
      data: data,
   
      headers: {
        Authorization: `Bearer ${adminStore.admin.access}`,
         "Content-Type": "multipart/form-data",
      },
    });
  }
  async update(data) {
    const adminStore = useAdminStore();
    const { id } = data;
    return this.request({
      method: "put",
      url: `/${this.entity}/${id}`,
      data: data,
      headers: {
        Authorization: `Bearer ${adminStore.admin.access}`,
        "Content-Type": "multipart/form-data",
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
        "Content-Type": "multipart/form-data",
      },
    });
  }
}

export default new CategoryServices();
