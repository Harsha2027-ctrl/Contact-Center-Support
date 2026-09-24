import api from './axios';

export const search = (query) =>
  api.get('/api/search', { params: { query } });