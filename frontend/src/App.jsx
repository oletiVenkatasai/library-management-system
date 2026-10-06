import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, AuthContext } from './context/AuthContext';
import { useContext } from 'react';
import Navbar   from './components/Navbar';
import Sidebar  from './components/Sidebar';
import Dashboard from './pages/Dashboard';
import Books     from './pages/Books';
import Authors   from './pages/Authors';
import Members   from './pages/Members';
import IssueBook from './pages/IssueBook';
import Borrowings from './pages/Borrowings';
import Overdue   from './pages/Overdue';
import Login     from './pages/Login';

import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

const ProtectedRoute = ({ children }) => {
  const { user, loading } = useContext(AuthContext);
  if (loading) {
    return (
      <div className="d-flex justify-content-center align-items-center vh-100">
        <div className="spinner-border text-primary" role="status"></div>
      </div>
    );
  }
  return user ? children : <Navigate to="/login" replace />;
};

const Layout = ({ children }) => (
  <div className="d-flex flex-column vh-100">
    <Navbar />
    <div className="d-flex flex-grow-1" style={{ overflow: 'hidden' }}>
      <Sidebar />
      <main className="flex-grow-1 p-4" style={{ overflowY: 'auto', background: '#f0f2f8' }}>
        {children}
      </main>
    </div>
  </div>
);

function App() {
  return (
    <Router>
      <AuthProvider>
        <Routes>
          <Route path="/login" element={<Login />} />

          <Route path="/" element={<Navigate to="/dashboard" replace />} />

          <Route path="/dashboard" element={<ProtectedRoute><Layout><Dashboard /></Layout></ProtectedRoute>} />
          <Route path="/books" element={<ProtectedRoute><Layout><Books /></Layout></ProtectedRoute>} />
          <Route path="/authors" element={<ProtectedRoute><Layout><Authors /></Layout></ProtectedRoute>} />
          <Route path="/members" element={<ProtectedRoute><Layout><Members /></Layout></ProtectedRoute>} />
          <Route path="/borrow/issue" element={<ProtectedRoute><Layout><IssueBook /></Layout></ProtectedRoute>} />
          <Route path="/borrowings" element={<ProtectedRoute><Layout><Borrowings /></Layout></ProtectedRoute>} />
          <Route path="/overdue" element={<ProtectedRoute><Layout><Overdue /></Layout></ProtectedRoute>} />

          <Route path="*" element={<Navigate to="/dashboard" replace />} />
        </Routes>
      </AuthProvider>
    </Router>
  );
}

export default App;
