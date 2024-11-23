import ApiService from "./ApiService";
import { useAdminStore } from "@/stores/admin";

class ProductServices extends ApiService {
  get entity() {
    return "product";
  }
  async update(data) {
    const adminStore = useAdminStore();
    console.log(adminStore.admin.isAuthenticated, adminStore.admin.access);
    console.log(data);

    const { _id } = data;
    const option = {
      method: "put",
      url: `/${this.entity}/${_id}/`,
      data: data,
      headers: {
        Authorization: `Bearer ${adminStore.admin.access}`,
      },
    };
    return this.request(option);
  }

  async create(data) {
    const adminStore = useAdminStore();
    const formData = new FormData();

    formData.append("name", data.name);
    formData.append("thumbnail", data.thumbnail);
    formData.append("images", data.images);
    formData.append("price", data.price);
    formData.append("description", data.description);
    formData.append("category", data.category);
    formData.append("countInStock", data.countInStock);
    const option = {
      method: "post",
      url: `/${this.entity}/`,
      data: data,
      headers: {
        "Content-Type": "multipart/form-data",
        Authorization: `Bearer ${adminStore.admin.access}`,
      },
    };
    return this.request(option);
  }

  async delete(id) {
    const adminStore = useAdminStore();
    return this.request({
      method: "delete",
      url: `/${this.entity}/${id}/`,
      headers: {
        Authorization: `Bearer ${adminStore.admin.access}`,
      },
    });
  }
}

export default new ProductServices();
