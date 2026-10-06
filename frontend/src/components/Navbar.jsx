import { Link } from 'react-router-dom';
import { useContext } from 'react';
import { AuthContext } from '../context/AuthContext';

function Navbar() {
  const { user, logout } = useContext(AuthContext);

  return (
    <nav className="navbar navbar-dark bg-dark px-3 shadow-sm d-flex justify-content-between">
      <Link className="navbar-brand fw-bold fs-5" to="/dashboard">
        <i className="bi bi-book-half me-2 text-info"></i>
        Library Management System
      </Link>
      <div className="d-flex align-items-center gap-3">
        {user && (
          <>
            <span className="text-light small">
              <i className="bi bi-person-circle me-1"></i>
              {user.username}
            </span>
            <button onClick={logout} className="btn btn-sm btn-outline-light">
              <i className="bi bi-box-arrow-right me-1"></i> Logout
            </button>
          </>
        )}
      </div>
    </nav>
  );
}

export default Navbar;
