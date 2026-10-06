import { useState, useContext } from 'react';
import { AuthContext } from '../context/AuthContext';

function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError]       = useState('');
  const [loading, setLoading]   = useState(false);
  const { login } = useContext(AuthContext);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    const success = await login(username, password);
    setLoading(false);
    if (!success) {
      setError('Invalid username or password. Please try again.');
    }
  };

  return (
    <div className="auth-page d-flex justify-content-center align-items-center vh-100">
      <div className="auth-card">
        <div className="auth-header text-center mb-4">
          <div className="auth-logo mb-3">
            <i className="bi bi-book-half"></i>
          </div>
          <h2 className="auth-title">Welcome Back</h2>
          <p className="auth-subtitle">Sign in to Library Management System</p>
        </div>

        {error && (
          <div className="alert alert-danger d-flex align-items-center gap-2 mb-3">
            <i className="bi bi-exclamation-triangle-fill"></i>
            <span>{error}</span>
          </div>
        )}

        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label">Username</label>
            <div className="input-group">
              <span className="input-group-text bg-transparent border-end-0">
                <i className="bi bi-person text-muted"></i>
              </span>
              <input
                id="login-username"
                type="text"
                className="form-control border-start-0 ps-0"
                placeholder="Enter username"
                value={username}
                onChange={e => setUsername(e.target.value)}
                required
                autoFocus
              />
            </div>
          </div>

          <div className="mb-4">
            <label className="form-label">Password</label>
            <div className="input-group">
              <span className="input-group-text bg-transparent border-end-0">
                <i className="bi bi-lock text-muted"></i>
              </span>
              <input
                id="login-password"
                type="password"
                className="form-control border-start-0 ps-0"
                placeholder="Enter password"
                value={password}
                onChange={e => setPassword(e.target.value)}
                required
              />
            </div>
          </div>

          <button
            type="submit"
            className="btn btn-primary w-100 btn-lg mb-3"
            disabled={loading}
          >
            {loading
              ? <><span className="spinner-border spinner-border-sm me-2"></span>Signing in...</>
              : <><i className="bi bi-box-arrow-in-right me-2"></i>Sign In</>
            }
          </button>
        </form>

      </div>

      <style>{`
        .auth-page {
          background: linear-gradient(135deg, #1a1a2e 0%, #16213e 50%, #0f3460 100%);
          min-height: 100vh;
        }
        .auth-card {
          background: #fff;
          border-radius: 16px;
          padding: 2.5rem;
          width: 100%;
          max-width: 420px;
          box-shadow: 0 25px 50px rgba(0,0,0,0.4);
        }
        .auth-logo {
          width: 64px; height: 64px;
          background: linear-gradient(135deg, #4361ee, #4895ef);
          border-radius: 16px;
          display: flex; align-items: center; justify-content: center;
          margin: 0 auto;
          font-size: 2rem; color: #fff;
          box-shadow: 0 8px 20px rgba(67,97,238,0.4);
        }
        .auth-title { font-size: 1.5rem; font-weight: 700; color: #1a202c; }
        .auth-subtitle { color: #718096; font-size: 0.875rem; margin-bottom: 0; }
        .auth-hint {
          text-align: center;
          background: #f7fafc;
          border-radius: 8px;
          padding: 0.6rem 1rem;
          margin-top: 0.5rem;
        }
        .auth-hint code {
          background: #edf2ff;
          color: #4361ee;
          padding: 0.1em 0.4em;
          border-radius: 4px;
          font-size: 0.85em;
        }
        .input-group-text {
          border-radius: 8px 0 0 8px !important;
        }
      `}</style>
    </div>
  );
}

export default Login;
