import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080',
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  
  if (token && token !== 'undefined' && token !== 'null' && !config.url.startsWith('/api/news')) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
}, (error) => {
  return Promise.reject(error);
});

api.interceptors.response.use((response) => {
  return response;
}, (error) => {
  if (error.response && error.response.status === 401) {
    console.error("🚨 401 Unauthorized: O Java recusou o Token!");
    localStorage.removeItem('token');
  
  }
  return Promise.reject(error);
});

export default api;