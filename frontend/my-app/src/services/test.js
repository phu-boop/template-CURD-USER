import apiClient from "./apiClient.js";

export const getAdmin = () =>
    apiClient.get("test/admin");

export const getUser = () =>
    apiClient.get("test/user");
