import { useState, useEffect } from 'react';
import dashboardService from '../services/dashboardService';

/**
 * Dashboard Page - Shows library statistics from the real database.
 */
function Dashboard() {
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    fetchStats();
  }, []);

  const fetchStats = async () => {
    try {
      setLoading(true);
      const response = await dashboardService.getStats();
      setStats(response.data);
      setError('');
    } catch (err) {
      setError('Failed to load dashboard statistics.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="text-center mt-5">
        <div className="spinner-border" role="status">
          <span className="visually-hidden">Loading...</span>
        </div>
      </div>
    );
  }

  if (error) {
    return <div className="alert alert-danger mt-3">{error}</div>;
  }

  return (
    <div>
      <h2 className="mb-4">Dashboard</h2>

      {/* Statistics Cards */}
      <div className="row g-3 mb-4">
        <div className="col-md-4 col-lg">
          <div className="card text-center border-primary">
            <div className="card-body">
              <h5 className="card-title text-primary">Total Books</h5>
              <p className="display-6 fw-bold">{stats.totalBooks}</p>
            </div>
          </div>
        </div>
        <div className="col-md-4 col-lg">
          <div className="card text-center border-success">
            <div className="card-body">
              <h5 className="card-title text-success">Total Members</h5>
              <p className="display-6 fw-bold">{stats.totalMembers}</p>
            </div>
          </div>
        </div>
        <div className="col-md-4 col-lg">
          <div className="card text-center border-info">
            <div className="card-body">
              <h5 className="card-title text-info">Authors</h5>
              <p className="display-6 fw-bold">{stats.totalAuthors}</p>
            </div>
          </div>
        </div>
        <div className="col-md-6 col-lg">
          <div className="card text-center border-warning">
            <div className="card-body">
              <h5 className="card-title text-warning">Available Books</h5>
              <p className="display-6 fw-bold">{stats.availableBooks}</p>
            </div>
          </div>
        </div>
        <div className="col-md-6 col-lg">
          <div className="card text-center border-danger">
            <div className="card-body">
              <h5 className="card-title text-danger">Issued Books</h5>
              <p className="display-6 fw-bold">{stats.issuedBooks}</p>
            </div>
          </div>
        </div>
      </div>

      {/* Recent Borrowings */}
      <div className="card">
        <div className="card-header">
          <h5 className="mb-0">Recent Borrowings</h5>
        </div>
        <div className="card-body">
          {stats.recentBorrowings && stats.recentBorrowings.length > 0 ? (
            <div className="table-responsive">
              <table className="table table-striped table-hover">
                <thead>
                  <tr>
                    <th>Book</th>
                    <th>Member</th>
                    <th>Issue Date</th>
                    <th>Due Date</th>
                    <th>Status</th>
                  </tr>
                </thead>
                <tbody>
                  {stats.recentBorrowings.map((b) => (
                    <tr key={b.id}>
                      <td>{b.bookTitle}</td>
                      <td>{b.memberName}</td>
                      <td>{b.issueDate}</td>
                      <td>{b.dueDate}</td>
                      <td>
                        <span className={`badge ${b.status === 'ISSUED' ? 'bg-warning text-dark' : 'bg-success'}`}>
                          {b.status}
                        </span>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          ) : (
            <p className="text-muted">No borrowing transactions yet.</p>
          )}
        </div>
      </div>
    </div>
  );
}

export default Dashboard;
