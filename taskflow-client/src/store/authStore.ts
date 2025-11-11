import { create } from 'zustand';
import { persist } from 'zustand/middleware'; // to save in localStorage

// Define the "shape" of our state
interface AuthState {
  token: String | null;
  setToken: (token: string) => void; // Action for saving the token
  logout: () => void; // Action for deleting the token
}

// Create the store
export const useAuthStore = create<AuthState>()(
  // persist wrap our store
  persist(
    (set) => ({
      token: null, // inital state
      setToken: (token: String) => set({ token }),
      logout: () => set({ token: null }),
    }),
    {
      // name of the localstorage
      name: 'auth-storage',
    }
  )
);