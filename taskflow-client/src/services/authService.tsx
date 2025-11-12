import api from "../lib/axios";
import { useAuthStore } from "../store/authStore";

// Define the shape of the token (matches the backend DTO)
interface LoginResponse {
  token: string;
  tokenType: string;
}

// Define the shape of the login data (matches the backend DTO)
interface LoginData {
  email: string;
  password: string;
}

// Async login function
export const login = async (loginData: LoginData): Promise<string> => {
  try {
    // Call the endpoint /auth/login
    const response = await api.post<LoginResponse>('/auth/login', loginData);

    // Extract the token
    const token = response.data.token;

    // Store the token with Zustand globaly
    useAuthStore.getState().setToken(token);

    return token;
  } catch (error) {
    // Exception management
    console.error("Error en el login: ", error);
    throw new Error("Email o contraseña incorrectos.")
  }
}

// Logout service
export const logout = () => {
  // Delete the token from the storage
  useAuthStore.getState().logout();
}

// TODO: create the register service (register)