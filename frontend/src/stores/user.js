import { defineStore } from "pinia";
import axios from "axios";

export const useUserStore = defineStore("user", {
  persist: {
    enabled: true,
    strategies: [
      {
        key: "user",
        storage: localStorage,
      },
    ],
  },
  state: () => ({
    user: {
      isAuthenticated: false,
      id: null,
      name: null,
      email: null,
      access: null,
      refresh: null,
      avatar: null,
    },
  }),

  actions: {
    initStore() {
      console.log("initStore", localStorage.getItem("user.access"));

      if (localStorage.getItem("user.access")) {
        console.log("User has access!");

        this.user.access = localStorage.getItem("user.access");
        this.user.refresh = localStorage.getItem("user.refresh");
        this.user.id = localStorage.getItem("user.id");
        this.user.name = localStorage.getItem("user.name");
        this.user.email = localStorage.getItem("user.email");
        this.user.avatar = localStorage.getItem("user.avatar");
        // this.user.isAuthenticated = true;

        // this.refreshToken();

        console.log("Initialized user:", this.user);
      }
    },

    setToken(data) {
      this.user.access = data.access_token;
      this.user.refresh = data.refresh_token;
      this.user.isAuthenticated = true;

      localStorage.setItem("user.access", data.access_token);
      localStorage.setItem("user.refresh", data.refresh_token);
      localStorage.setItem("user.isAuthenticated", true);

    },

    removeToken() {

      this.user.refresh = null;
      this.user.access = null;
      this.user.isAuthenticated = false;
      this.user.id = null;
      this.user.name = null;
      this.user.email = null;
      this.user.avatar = null;

      localStorage.setItem("user.isAuthenticated", false);
      localStorage.setItem("user.access", "");
      localStorage.setItem("user.refresh", "");
      localStorage.setItem("user.id", "");
      localStorage.setItem("user.name", "");
      localStorage.setItem("user.email", "");
      localStorage.setItem("user.avatar", "");
    },

    setUserInfo(user) {

      this.user.id = user.id;
      this.user.name = user.name;
      this.user.email = user.email;
      this.user.avatar = user.avatar;

      localStorage.setItem("user.id", this.user.id);
      localStorage.setItem("user.name", this.user.name);
      localStorage.setItem("user.email", this.user.email);
      localStorage.setItem("user.avatar", this.user.avatar);

    },
    updateAvatar(avatar) {
      console.log(avatar);

      this.user.avatar = avatar;
      localStorage.setItem("user.avatar", this.user.avatar);
    },

    refreshToken() {
      axios
        .post("/api/account/refresh/", {
          refresh: this.user.refresh,
        })
        .then((response) => {
          this.user.access = response.data.access;

          localStorage.setItem("user.access", response.data.access);

          axios.defaults.headers.common["Authorization"] =
            "Bearer " + response.data.access;
        })
        .catch((error) => {
          console.log(error);

          this.removeToken();
        });
    },
  },
});
