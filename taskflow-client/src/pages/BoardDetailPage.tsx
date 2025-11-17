import { useParams } from "react-router-dom";

export default function BoardDetailPage() {
  // Get the param from the URL
  const { boardId } = useParams<{ boardId: string }>();

  return(
    <div className="text-white">
      <h1 className="text-3xl font-bold">
        Cargando Tablero...
      </h1>
      <p className="text-gray-400">
        Id del Tablero: {boardId}
      </p>
    </div>
  )
}