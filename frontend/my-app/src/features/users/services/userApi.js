import apiConst from '../../../services/apiConst';

export const userApi = {
  getAll: () => apiConst.get('/users'),
  getById: (id) => apiConst.get(`/users/${id}`),
  create: (userData) => apiConst.post('/users/register', userData),
  update: (id, userData) => apiConst.put(`/users/${id}`, userData),
  delete: (id) => apiConst.delete(`/users/${id}`),
};

export default userApi;