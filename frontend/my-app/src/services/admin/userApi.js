import apiConst from "../apiConst"; // import axios đã cấu hình sẵn

const userApi = {
    getAll: () => apiConst.get("/users"),
    getById: (id) => apiConst.get(`/users/${id}`),
    create: (data) => apiConst.post("/users/register", data),
    update: (id, data) => apiConst.put(`/users/${id}`, data),
    delete: (id) => apiConst.delete(`/users/${id}`),
};

export default userApi;
