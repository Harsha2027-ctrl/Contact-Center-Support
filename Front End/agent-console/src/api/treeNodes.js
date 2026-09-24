import api from './axios';

export const getRootNode = (treeId) => api.get(`/api/tree-nodes/tree/${treeId}/root`);
export const getNodeWithOptions = (nodeId) => api.get(`/api/tree-nodes/${nodeId}/with-options`);
export const getNodesByTree = (treeId) => api.get(`/api/tree-nodes/tree/${treeId}`);
export const createNode = (data) => api.post('/api/tree-nodes', data);
export const deleteNode = (id) => api.delete(`/api/tree-nodes/${id}`);