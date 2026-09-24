import api from './axios';

export const startSession = (treeId, ticketRef) =>
  api.post('/api/sessions/start', { treeId, ticketRef });

export const resolveSession = (id) => api.put(`/api/sessions/${id}/resolve`);
export const escalateSession = (id) => api.put(`/api/sessions/${id}/escalate`);