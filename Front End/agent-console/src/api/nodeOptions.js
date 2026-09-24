import api from './axios';

export const getOptionsByNode = (nodeId) => api.get(`/api/node-options/node/${nodeId}`);
export const createOption = (nodeId, optionLabel, nextNodeId) =>
  api.post('/api/node-options', { nodeId, optionLabel, nextNodeId });
export const deleteOption = (id) => api.delete(`/api/node-options/${id}`);