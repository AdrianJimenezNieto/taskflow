import axios from "axios";

// Create the axios instance
export const api = axios.create({
  // We define the base URL of our SpringBoot API
  baseURL: 'http://localhost:8080/api/v1'
});

// TODO: add interceptor for the jwt validation

export default api;