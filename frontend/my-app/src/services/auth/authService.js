import apiConst from "../apiConst.js";

export const registerUser = (userData) =>
    apiConst.post("users", userData).then((res) => res.data);

export const loginUser = (credentials) =>
    apiConst.post("auth/login", credentials).then((res) => res.data);
