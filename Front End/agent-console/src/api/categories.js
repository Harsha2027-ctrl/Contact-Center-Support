import api from './axios';

export const getAllCategories = () => api.get('/api/categories');
export const createCategory = (name, description) =>
  api.post('/api/categories', { name, description });
export const deleteCategory = (id) => api.delete(`/api/categories/${id}`);