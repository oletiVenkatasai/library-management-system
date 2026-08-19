import { Link, useLocation } from 'react-router-dom';

/**
 * Navbar Component - Top navigation bar.
 * Shows the app name and is always visible.
 */
function Navbar() {
  return (
    <nav className="navbar navbar-dark bg-dark px-3">
      <Link className="navbar-brand fw-bold" to="/dashboard">
        <i className="bi bi-book me-2"></i>
        Library Management System
      </Link>
    </nav>
  );
}

export default Navbar;
