import apiClient from "../apiClient.js";

export const registerUser = (userData) =>
  apiClient.post("users", userData).then((res) => res.data);

export const loginUser = (credentials) =>
  apiClient.post("auth/login", credentials).then((res) => res.data);
