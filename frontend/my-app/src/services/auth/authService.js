import apiConst from "../apiConst.js";

export const registerUser = (userData) =>
    apiConst.post("users/register", userData).then((res) => res.data);

export const loginUser = (credentials) =>
    apiConst.post("auth/login", credentials).then((res) => res.data);

export const logout = () =>
    apiConst.post("auth/logout").then((res) => res.data);

export const getInforMe = () =>
    apiConst.get("auth/me").then((res) => res.data);
