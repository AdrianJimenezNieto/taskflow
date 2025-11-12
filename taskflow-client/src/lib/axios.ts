import axios from "axios";
import { useAuthStore } from "../store/authStore";

// Create the axios instance
export const api = axios.create({
  // We define the base URL of our SpringBoot API
  baseURL: 'http://localhost:8080/api/v1'
});

// Axios Interceptor
api.interceptors.request.use(
  (config) => {
    // Obtain the token from Zustand
    const token = useAuthStore.getState().token;

    // If the token exists, we add it to the header 'Authorization'
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    // return the config
    return config;
  },
  (error) => {
    // Error handling 
    return Promise.reject(error);
  }
)

export default api;