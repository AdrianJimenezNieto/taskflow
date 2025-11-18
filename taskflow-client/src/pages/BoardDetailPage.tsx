import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import { getBoardDetails, createTaskList } from "../services/boardService";
import type { BoardDetails, TaskList, Card } from "../models";
import TaskColumn from "../components/board/TaskColumn";

export default function BoardDetailPage() {
  // Get the param from the URL
  const { boardId } = useParams<{ boardId: string }>();

  // Local states to handle the data, load and errors
  const [board, setBoard] = useState<BoardDetails | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [newListTitle, setNewListTitle] = useState('');

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

  // New List handler
  const handleCreateList = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (newListTitle.trim() === '' || !boardId) return;

    try {
      // Call the API
      const newList = await createTaskList(boardId, newListTitle);

      // Update the local state
      if (board) {
        setBoard({
          ...board,
          lists: [...board.lists, newList]
        });
      }

      // Clean the input
      setNewListTitle('');
    } catch (err) {
      if (err instanceof Error) setError(err.message);
    }
  };

  // Function to handle the columns
  const handleCardAdded = (listId: number, newCard: Card) => {
    if(!board) return;

    // Update the inmutable state
    const updateLists = board.lists.map((list) => {
      if (list.id === listId) {
        // If it is the correct list, add the list
        return {...list, cards:[...list.cards, newCard]}
      }
      return list;
    });

    setBoard({ ...board, lists: updateLists})
  };

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
    <div className="text-white h-screen flex flex-col p-4">
      <h1 className="mb-4 text-3xl font-bold"> {board?.title} </h1>

      {/* Horizontal container for the lists */}
      <div className="flex h-full gap-4 overflow-x-auto overflow-y-hidden pb-4">
        {/* Map the lists */}
        {board.lists.length > 0 ? (
          board.lists.map((list: TaskList) => (
            <TaskColumn
              key={list.id}
              list={list}
              onCardAdded={handleCardAdded}
            />
          ))
        ) : (
          <p className="text-sm text-gray-400">Este tablero aún no tiene listas.</p>
        )}

        {/* Form to add new list (US-202) */}
        <div className="w-72 flex-shrink-0">
          <form onSubmit={handleCreateList} className="rounded-lg bg-gray-800 p-4">
            <input
              type="text"
              value={newListTitle}
              onChange={(e) => setNewListTitle(e.target.value)}
              placeholder="Añadir nueva lista..."
              className="w-full rounded-md border border-gray-600 bg-gray-700 px-3 py-2 text-white placeholder-gray-400 focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <button
              type="submit"
              className="mt-2 w-full rounded-md bg-blue-600 px-4 py-2 text-sm font-semibold text-white transition duration-200 ease-in-out hover:bg-blue-700"
            >
              Crear Lista
            </button>
          </form>
        </div>
      </div>
    </div>
  )
}