import api from '../lib/axios';
import type { Board } from '../models';

// US-106: Bring all of my boards
export const getMyBoards = async (): Promise<Board[]> => {
  try {
    const response = await api.get<Board[]>('/boards');
    return response.data;
  } catch (error) {
    console.error("Error fetching boards: ", error);
    throw new Error("No se pudieron cargar los tableros.")
  }
};

// US-105: Create new board
export const createBoard = async (title: string): Promise<Board> => {
  try {
    const response = await api.post<Board>('/boards', { title });
    return response.data;
  } catch (error) {
    console.log("Error creating board: ", error);
    throw new Error("Fallo al crear el tablero.")
  }
}