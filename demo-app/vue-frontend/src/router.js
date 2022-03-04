import { createWebHistory, createRouter } from "vue-router";
const routes =  [
  {
    path: "/",
    alias: "/accounts",
    name: "accounts",
    component: () => import("./components/AccountsList")
  },
  {
    path: "/accounts/:id",
    name: "accounts-details",
    component: () => import("./components/Account")
  },
  {
    path: "/add",
    name: "add",
    component: () => import("./components/AddAccount")
  }
];
const router = createRouter({
  history: createWebHistory(),
  routes,
});
export default router;