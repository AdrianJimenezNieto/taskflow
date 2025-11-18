import React, { useState } from 'react';
import type { TaskList, Card } from '../../models';
import { createCard } from '../../services/boardService';

interface TaskColumnProps {
  list: TaskList;
  // this property will advice the father when we create a new card
  onCardAdded: (listId: number, newCard: Card) => void;
}

export default function TaskColumn({ list, onCardAdded}: TaskColumnProps) {
  const [newCardTitle, setNewCardTitle] = useState('');

  const handleCreateCard = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!newCardTitle.trim()) return;

    try{
      // Call the api
      const newCard = await createCard(list.id, newCardTitle);
      // Advice the father to update the global state
      onCardAdded(list.id, newCard);
      // Clean the input
      setNewCardTitle('');
    } catch (error) {
      console.error(error);
      // TODO: manage local errors
    }
  };

  return (
    <div className='w-72 flex-shrink-0 rounded-lg bg-gray-800 p-4 flex flex-col max-h-full'>
      <h2 className='mb-3 font-semibold text-white'>{list.title}</h2>

      {/* List of Cards (with scroll if they are many) */}
      <div className='flex-1 flex flex-col gap-3 overflow-y-auto min-h-0 mb-3'>
        {list.cards.length > 0 ? (
          list.cards.map((card) => (
            <div key={card.id} className='rounded-md bg-gray-700 p-3 shadow-md text-white text-sm'>
              {card.title}
            </div>
          ))
        ) : (
          <p className='text-sm text-gray-400'>No hay tarjetas.</p>
        )}
      </div>

      {/* Form to add new card */}
      <form onSubmit={handleCreateCard} className='mt-auto'>
        <input
          type="text"
          value={newCardTitle}
          placeholder='Añadir nueva tarjeta...'
          onChange={(e) => setNewCardTitle(e.target.value)}
          className='w-full rounded bg-gray-900 px-2 py-1 text-sm text-white border border-gray-600 focus:border-blue-500 focus:outline-none'
        />
        <button
          type='submit'
          className='mt-2 w-full rounded bg-blue-600 px-2 py-1 text-xs font-bold text-white transition duration-200 ease-in-out hover:bg-blue-700'
        >
          Añadir
        </button>
      </form>
    </div>
  )
}