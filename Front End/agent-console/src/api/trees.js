import api from './axios';

export const getPublishedTrees = () => api.get('/api/trees/published');
export const getAllTrees = () => api.get('/api/trees');
export const getTreeById = (id) => api.get(`/api/trees/${id}`);
export const createTree = (categoryId, name) =>
  api.post('/api/trees', { categoryId, name });
export const publishTree = (id) => api.put(`/api/trees/${id}/publish`);
export const unpublishTree = (id) => api.put(`/api/trees/${id}/unpublish`);
export const deleteTree = (id) => api.delete(`/api/trees/${id}`);