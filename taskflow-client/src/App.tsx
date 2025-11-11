import { Routes, Route } from 'react-router-dom';
import LoginPage from './pages/LoginPage.tsx';
import DashboardPage from './pages/DashboardPage.tsx';

function App() {
  return(
    // Dark background for all the app
    <div className='min-h-screen w-full bg-gray-900 p-4'>
      <Routes>
        {/* Public route */}
        <Route path='/login' element={<LoginPage />} />

        {/* Private Route */}
        <Route path='/dashboard' element={<DashboardPage />} />

        {/* TODO: Add register route
        TODO: Add root route "/" to redirect
        TODO: Add protected Routes */}
      </Routes>
    </div>
  )
}

export default App
