import api from './axios';

export const login = (email, password) =>
  api.post('/api/auth/login', { email, password });

export const register = (name, email, password, role) =>
  api.post('/api/auth/register', { name, email, password, role });