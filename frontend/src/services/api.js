import axios from 'axios';

/**
 * Axios Instance - Central HTTP client configuration.
 *
 * WHY: Instead of writing the base URL in every API call,
 * we configure it once here. All service files import this instance.
 *
 * VITE_API_URL comes from the .env file.
 */
const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

export default api;
