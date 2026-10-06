import { useState, useEffect } from 'react';
import dashboardService from '../services/dashboardService';

function Dashboard() {
  const [stats, setStats]     = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError]     = useState('');

  useEffect(() => { fetchStats(); }, []);

  const fetchStats = async () => {
    try {
      setLoading(true);
      const response = await dashboardService.getStats();
      setStats(response.data);
      setError('');
    } catch (err) {
      setError('Failed to load dashboard. Please ensure the backend is running.');
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="d-flex justify-content-center align-items-center" style={{ height: '50vh' }}>
        <div className="text-center">
          <div className="spinner-border" style={{ width: '3rem', height: '3rem' }} role="status"></div>
          <p className="mt-3 text-muted fw-medium">Loading dashboard...</p>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="alert alert-danger d-flex align-items-center gap-2">
        <i className="bi bi-exclamation-triangle-fill fs-5"></i>
        <div>{error}</div>
      </div>
    );
  }

  const statCards = [
    { label: 'Total Books',       value: stats.totalBooks,    icon: 'bi-journal-bookmark-fill', gradient: 'linear-gradient(135deg,#4361ee,#4895ef)', shadow: 'rgba(67,97,238,.4)' },
    { label: 'Total Members',     value: stats.totalMembers,  icon: 'bi-people-fill',            gradient: 'linear-gradient(135deg,#06d6a0,#05b882)', shadow: 'rgba(6,214,160,.4)' },
    { label: 'Total Authors',     value: stats.totalAuthors,  icon: 'bi-person-lines-fill',      gradient: 'linear-gradient(135deg,#7209b7,#b5179e)', shadow: 'rgba(114,9,183,.4)' },
    { label: 'Available Books',   value: stats.availableBooks,icon: 'bi-check-circle-fill',      gradient: 'linear-gradient(135deg,#f72585,#b5179e)', shadow: 'rgba(247,37,133,.4)' },
    { label: 'Books Issued',      value: stats.issuedBooks,   icon: 'bi-box-arrow-right',        gradient: 'linear-gradient(135deg,#f4a261,#e76f51)', shadow: 'rgba(244,162,97,.4)' },
  ];

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <div>
          <h2 className="mb-1"><i className="bi bi-speedometer2 me-2 text-primary"></i>Dashboard</h2>
          <p className="text-muted mb-0 small">Full library overview &amp; management</p>
        </div>
        <button className="btn btn-outline-secondary btn-sm" onClick={fetchStats}>
          <i className="bi bi-arrow-clockwise me-1"></i> Refresh
        </button>
      </div>

      <div className="row g-3 mb-4">
        {statCards.map((card) => (
          <div className="col-6 col-lg" key={card.label}>
            <div className="stat-card" style={{ background: card.gradient, boxShadow: `0 8px 25px ${card.shadow}` }}>
              <div className="stat-icon"><i className={`bi ${card.icon}`}></i></div>
              <div className="stat-value">{card.value}</div>
              <div className="stat-label">{card.label}</div>
            </div>
          </div>
        ))}
      </div>

      <div className="row g-3 mb-4">
        <div className="col-md-6">
          <div className="card h-100">
            <div className="card-header d-flex align-items-center gap-2">
              <i className="bi bi-pie-chart-fill text-primary"></i>Book Availability
            </div>
            <div className="card-body">
              {stats.totalBooks > 0 ? (
                <>
                  <div className="d-flex justify-content-between mb-1 small fw-medium">
                    <span>Available</span>
                    <span className="text-success">{stats.availableBooks} books</span>
                  </div>
                  <div className="progress mb-3" style={{ height: '8px', borderRadius: '4px' }}>
                    <div className="progress-bar bg-success"
                      style={{ width: `${(stats.availableBooks / stats.totalBooks) * 100}%`, borderRadius: '4px' }}>
                    </div>
                  </div>
                  <div className="d-flex justify-content-between mb-1 small fw-medium">
                    <span>Issued</span>
                    <span className="text-warning">{stats.issuedBooks} books</span>
                  </div>
                  <div className="progress" style={{ height: '8px', borderRadius: '4px' }}>
                    <div className="progress-bar bg-warning"
                      style={{ width: `${(stats.issuedBooks / stats.totalBooks) * 100}%`, borderRadius: '4px' }}>
                    </div>
                  </div>
                </>
              ) : <p className="text-muted small mb-0">No books in the library yet.</p>}
            </div>
          </div>
        </div>
        <div className="col-md-6">
          <div className="card h-100">
            <div className="card-header d-flex align-items-center gap-2">
              <i className="bi bi-info-circle-fill text-info"></i>Quick Summary
            </div>
            <div className="card-body">
              <ul className="list-unstyled mb-0 small">
                {[
                  { label: 'Catalogue size',       value: `${stats.totalBooks} books`,   icon: 'bi-journal-text',  color: 'text-primary' },
                  { label: 'Registered members',   value: `${stats.totalMembers} members`,icon: 'bi-people',       color: 'text-success' },
                  { label: 'Books on loan',         value: `${stats.issuedBooks} issued`, icon: 'bi-clock',        color: 'text-warning' },
                  { label: 'Registered authors',   value: `${stats.totalAuthors} authors`,icon: 'bi-person',      color: 'text-primary' },
                ].map((item) => (
                  <li key={item.label} className="d-flex justify-content-between align-items-center py-2 border-bottom">
                    <span className={`${item.color} fw-medium`}>
                      <i className={`bi ${item.icon} me-2`}></i>{item.label}
                    </span>
                    <span className="text-muted">{item.value}</span>
                  </li>
                ))}
              </ul>
            </div>
          </div>
        </div>
      </div>

      <div className="card">
        <div className="card-header d-flex align-items-center gap-2">
          <i className="bi bi-clock-history text-primary"></i>
          Recent Borrowings
          <span className="badge bg-primary ms-auto">
            {stats.recentBorrowings?.length || 0}
          </span>
        </div>
        <div className="card-body p-0">
          {stats.recentBorrowings?.length > 0 ? (
            <div className="table-responsive">
              <table className="table mb-0">
                <thead className="table-dark">
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
                      <td><strong>{b.bookTitle}</strong></td>
                      <td>{b.memberName}</td>
                      <td><span className="text-muted">{b.issueDate}</span></td>
                      <td><span className="text-muted">{b.dueDate}</span></td>
                      <td>
                        <span className={`badge ${b.status === 'ISSUED' ? 'bg-warning text-dark' : 'bg-success'}`}>
                          <i className={`bi ${b.status === 'ISSUED' ? 'bi-hourglass-split' : 'bi-check-circle'} me-1`}></i>
                          {b.status}
                        </span>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          ) : (
            <div className="text-center py-5 text-muted">
              <i className="bi bi-inbox fs-1 d-block mb-2 opacity-25"></i>
              No borrowing transactions yet.
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default Dashboard;
