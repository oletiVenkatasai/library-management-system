import { useState, useEffect } from 'react';
import borrowService from '../services/borrowService';

/**
 * Overdue Books Page — lists all active borrowings where the due date has passed.
 * Helps librarians quickly identify and follow up on late returns.
 */
function Overdue() {
  const [overdue, setOverdue] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  useEffect(() => {
    fetchOverdue();
  }, []);

  const fetchOverdue = async () => {
    try {
      setLoading(true);
      const response = await borrowService.getOverdueBorrowings();
      setOverdue(response.data);
      setError('');
    } catch (err) {
      setError('Failed to load overdue borrowings.');
    } finally {
      setLoading(false);
    }
  };

  const handleReturn = async (id) => {
    if (!window.confirm('Mark this book as returned?')) return;
    try {
      await borrowService.returnBook(id);
      setSuccess('Book returned successfully!');
      fetchOverdue();
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to return book.');
    }
  };

  /**
   * Calculate how many days overdue a borrowing is.
   */
  const daysOverdue = (dueDate) => {
    const due = new Date(dueDate);
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    return Math.floor((today - due) / (1000 * 60 * 60 * 24));
  };

  return (
    <div>
      <div className="d-flex align-items-center mb-3 gap-3">
        <h2 className="mb-0">
          <span className="text-danger me-2">&#9888;</span>Overdue Books
        </h2>
        {!loading && (
          <span className="badge bg-danger fs-6">{overdue.length} overdue</span>
        )}
      </div>

      <p className="text-muted mb-4">
        Books that have not been returned past their due date. Please follow up with the respective members.
      </p>

      {error && (
        <div className="alert alert-danger alert-dismissible">
          {error}
          <button type="button" className="btn-close" onClick={() => setError('')} />
        </div>
      )}
      {success && (
        <div className="alert alert-success alert-dismissible">
          {success}
          <button type="button" className="btn-close" onClick={() => setSuccess('')} />
        </div>
      )}

      {loading ? (
        <div className="text-center mt-5">
          <div className="spinner-border text-danger" role="status">
            <span className="visually-hidden">Loading...</span>
          </div>
        </div>
      ) : overdue.length === 0 ? (
        <div className="alert alert-success d-flex align-items-center gap-2">
          <span style={{ fontSize: '1.5rem' }}>&#10003;</span>
          <div>
            <strong>All clear!</strong> No overdue borrowings at the moment.
          </div>
        </div>
      ) : (
        <div className="table-responsive">
          <table className="table table-hover border">
            <thead className="table-danger">
              <tr>
                <th>ID</th>
                <th>Book Title</th>
                <th>Member</th>
                <th>Issue Date</th>
                <th>Due Date</th>
                <th>Days Overdue</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {overdue.map((b) => {
                const days = daysOverdue(b.dueDate);
                return (
                  <tr key={b.id} className="align-middle">
                    <td>{b.id}</td>
                    <td><strong>{b.bookTitle}</strong></td>
                    <td>{b.memberName}</td>
                    <td>{b.issueDate}</td>
                    <td className="text-danger fw-semibold">{b.dueDate}</td>
                    <td>
                      <span className={`badge ${
                        days > 14 ? 'bg-danger' : days > 7 ? 'bg-warning text-dark' : 'bg-secondary'
                      }`}>
                        {days} day{days !== 1 ? 's' : ''}
                      </span>
                    </td>
                    <td>
                      <button
                        id={`return-overdue-${b.id}`}
                        className="btn btn-sm btn-outline-success"
                        onClick={() => handleReturn(b.id)}
                      >
                        Mark Returned
                      </button>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}

export default Overdue;
