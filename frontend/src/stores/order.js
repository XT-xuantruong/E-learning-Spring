import { defineStore } from "pinia";

export const useOrderStore = defineStore("order", {
  persist: {
    enabled: true,
    strategies: [
      {
        key: "order",
        storage: localStorage,
      },
    ],
  },
  state: () => ({
    item: {},
  }),

  actions: {
    addItem(order) {
      this.item = { ...order };
    },
    removeItems() {
      this.item = {};
    },
  },
});
