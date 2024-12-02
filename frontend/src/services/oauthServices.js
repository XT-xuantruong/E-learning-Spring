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
      url: `/${this.entity}/getall/`,
      headers: {
        Authorization: `Bearer ${access}`,
      },
      params: { ...params },
    };
    return this.request(option);
  }

  async getme(id) {
    const option = {
      method: "get",
      url: `/${this.entity}/${id}`,
    };
    return this.request(option);
  }

  async updateProfile(id, data) {
    const formData = new FormData();

    formData.append("firstName", data.firstName);
    formData.append("lastName", data.lastName);
    formData.append("email", data.email);
    if (data.avatar) {
      // Convert base64 to file
      const base64ToFile = async (base64String) => {
        const res = await fetch(base64String);
        const blob = await res.blob();
        return new File([blob], "avatar.png", { type: "image/png" });
      };

      // Add file to form data
      const avatar = await base64ToFile(data.avatar);
      formData.append("avatar", avatar);
    }
    console.log("FormData for update", formData);

    return this.request({
      method: "put",
      url: `/${this.entity}/${id}`,
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
