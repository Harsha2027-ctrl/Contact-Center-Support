import api from './axios';

export const getTreeAnalytics = (treeId) =>
  api.get(`/api/analytics/tree/${treeId}`);