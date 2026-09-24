import api from './axios';

export const logStep = (sessionId, nodeId, optionSelected) =>
  api.post('/api/session-paths/log', { sessionId, nodeId, optionSelected });