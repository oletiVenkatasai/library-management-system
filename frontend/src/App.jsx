import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Navbar from './components/Navbar';
import Sidebar from './components/Sidebar';
import Dashboard from './pages/Dashboard';
import Books from './pages/Books';
import Authors from './pages/Authors';
import Members from './pages/Members';
import IssueBook from './pages/IssueBook';
import Borrowings from './pages/Borrowings';
import Overdue from './pages/Overdue';

import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

/**
 * App Component - Main application with routing and layout.
 *
 * Layout:
 * ┌──────────────────────────────────────┐
 * │             Navbar                    │
 * ├──────────┬───────────────────────────┤
 * │          │                           │
 * │ Sidebar  │       Page Content        │
 * │          │                           │
 * └──────────┴───────────────────────────┘
 */
function App() {
  return (
    <Router>
      <div className="d-flex flex-column vh-100">
        <Navbar />
        <div className="d-flex flex-grow-1">
          <Sidebar />
          <main className="flex-grow-1 p-4 bg-white" style={{ overflowY: 'auto' }}>
            <Routes>
              <Route path="/" element={<Navigate to="/dashboard" />} />
              <Route path="/dashboard" element={<Dashboard />} />
              <Route path="/books" element={<Books />} />
              <Route path="/authors" element={<Authors />} />
              <Route path="/members" element={<Members />} />
              <Route path="/borrow/issue" element={<IssueBook />} />
              <Route path="/borrowings" element={<Borrowings />} />
              <Route path="/overdue" element={<Overdue />} />
            </Routes>
          </main>
        </div>
      </div>
    </Router>
  );
}

export default App;
