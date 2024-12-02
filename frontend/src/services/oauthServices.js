import ApiService from "./ApiService";
import { useUserStore } from "@/stores/user";

class OauthServices extends ApiService {
  get entity() {
    return "user";
  }

  async login(credential) {
    const { email, password } = credential;
    var data = {
      email: email,
      password: password,
    };
    console.log(data);
    return this.request({
      method: "post",
      url: `/${this.entity}/login`,
      headers: {
        "Content-Type": "multipart/form-data",
      },
      data: data,
    });
  }
  async signup(credential) {
    const {
      firstName,
      lastName,
      email,
      password,
      confirmPassword,
      phone,
      role,
    } = credential;

    var data = {
      firstName: firstName,
      lastName: lastName,
      email: email,
      password: password,
      confirmPassword: confirmPassword,
      phone: phone,
      role: role,
    };
    console.log(data);
    return this.request({
      method: "post",
      url: `/${this.entity}/register`,
      headers: {
        "Content-Type": "multipart/form-data",
      },
      data: data,
    });
  }

  async logout(access, refresh) {
    console.log(access, refresh);
    
    const data = {
      access_token: access,
      refresh_token: refresh,
    };
    const option = {
      method: "post",
      url: `/${this.entity}/logout`,
      headers: {
        Authorization: `Bearer ${access}`,
      },
      data: data,
    };
    return this.request(option);
  }

  async gets(access, params) {
    const option = {
      method: "get",
      url: `/${this.entity}`,
      headers: {
        Authorization: `Bearer ${access}`,
      },
      params: { ...params },
    };
    return this.request(option);
  }

  async getme(access, id) {
    console.log(access);

    const option = {
      method: "get",
      url: `/${this.entity}/detail-user/${id}/`,
      headers: {
        Authorization: `Bearer ${access}`,
      },
    };
    return this.request(option);
  }

  async updateProfile(id, data) {
    const formData = new FormData();

    formData.append("name", data.name);
    formData.append("phone", data.phone);
    formData.append("email", data.email);
    if (data.avatar) {
      formData.append("avatar", data.avatar);
    }
    console.log("FormData for update", formData);

    return this.request({
      method: "put",
      url: `/${this.entity}/update-user/${id}/`,
      headers: {
        "Content-Type": "multipart/form-data",
      },
      data: formData,
    });
  }

  async delete(id, access) {
    return this.request({
      method: "delete",
      url: `/${this.entity}/delete-user/${id}/`,
      headers: {
        Authorization: `Bearer ${access}`,
      },
    });
  }
}

export default new OauthServices();
