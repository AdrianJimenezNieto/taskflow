import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import { getBoardDetails } from "../services/boardService";
import type { BoardDetails, TaskList, Card } from "../models";

export default function BoardDetailPage() {
  // Get the param from the URL
  const { boardId } = useParams<{ boardId: string }>();

  // Local states to handle the data, load and errors
  const [board, setBoard] = useState<BoardDetails | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  // useEffect hook: runs when the component is mounted
  useEffect(() => {
    // runs only if we have a boardId
    if (!boardId) {
      setError('No se ha proposrcionado un ID de tablero');
      setIsLoading(false);
      return;
    }

    const fetchBoard = async() => {
      try {
        setIsLoading(true);
        // Call the API service
        const data = await getBoardDetails(boardId);
        setBoard(data);
        setError(null);
      } catch (err) {
        if (err instanceof Error) setError(err.message);
      } finally {
        setIsLoading(false);
      }
    };

    fetchBoard();
  }, [boardId]); // It reruns if the URL changes

  // Condicional rendering logic
  if (isLoading) {
    // TODO: build a loader
    return <div className="text-center text-white">Cargando tablero...</div>
  }

  if (error) {
    return <div className="text-center text-red-500">Error: {error}</div>
  }

  if(!board) {
    return <div className="text-center text-white">Tablero no encontrado.</div>
  }

  // Board render
  return(
    <div className="text-white">
      <h1 className="mb-4 text-3xl font-bold"> {board.title} </h1>

      {/* Horizontal container for the lists */}
      <div className="flex h-full gap-4 overflow-x-auto">
        {/* Map the lists */}
        {board.lists.length > 0 ? (
          board.lists.map((list: TaskList) => (
            <div key={list.id} className="w-72 flex-shrink-0 rounded-lg bg-gray-800 p-4">
              <h2 className="mb-3 font-semibold">{list.title}</h2>

              {/* Vertical container for the cards */}
              <div className="flex flex-col gap-3">
                {list.cards.length > 0 ? (
                  list.cards.map((card: Card) => (
                    <div key={card.id} className="rounded-md bg-gray-700 p-3 shadow-md">
                      {card.title}
                    </div>
                  ))
                ) : (
                  <p className="text-sm text-gray-400">No hay tarjetas en esta lista.</p>
                )}
                {/* TODO: Add form to add new card (US-203) */}
              </div>
            </div>
          ))
        ) : (
          <p className="text-sm text-gray-400">Este tablero aún no tiene listas.</p>
        )}
        {/* TODO: Form to add new card (US-202)*/}
      </div>
    </div>
  )
}