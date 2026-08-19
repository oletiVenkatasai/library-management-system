import { useState, useEffect } from 'react';
import borrowService from '../services/borrowService';

/**
 * Borrowings Page - View all borrowing transactions with Return action.
 */
function Borrowings() {
  const [borrowings, setBorrowings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [filter, setFilter] = useState('all'); // 'all', 'active', 'returned'

  useEffect(() => {
    fetchBorrowings();
  }, [filter]);

  const fetchBorrowings = async () => {
    try {
      setLoading(true);
      let response;
      if (filter === 'active') {
        response = await borrowService.getActiveBorrowings();
      } else {
        response = await borrowService.getAllBorrowings();
      }

      let data = response.data;
      if (filter === 'returned') {
        data = data.filter((b) => b.status === 'RETURNED');
      }

      setBorrowings(data);
      setError('');
    } catch (err) {
      setError('Failed to load borrowings.');
    } finally {
      setLoading(false);
    }
  };

  const handleReturn = async (id) => {
    if (!window.confirm('Are you sure you want to return this book?')) return;
    try {
      await borrowService.returnBook(id);
      setSuccess('Book returned successfully!');
      fetchBorrowings();
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to return book.');
    }
  };

  return (
    <div>
      <h2 className="mb-4">Borrowing Transactions</h2>

      {error && <div className="alert alert-danger alert-dismissible">{error}<button type="button" className="btn-close" onClick={() => setError('')}></button></div>}
      {success && <div className="alert alert-success alert-dismissible">{success}<button type="button" className="btn-close" onClick={() => setSuccess('')}></button></div>}

      {/* Filter Buttons */}
      <div className="btn-group mb-3">
        <button className={`btn ${filter === 'all' ? 'btn-primary' : 'btn-outline-primary'}`}
          onClick={() => setFilter('all')}>All</button>
        <button className={`btn ${filter === 'active' ? 'btn-warning' : 'btn-outline-warning'}`}
          onClick={() => setFilter('active')}>Active (Issued)</button>
        <button className={`btn ${filter === 'returned' ? 'btn-success' : 'btn-outline-success'}`}
          onClick={() => setFilter('returned')}>Returned</button>
      </div>

      {/* Table */}
      {loading ? (
        <div className="text-center"><div className="spinner-border" role="status"></div></div>
      ) : (
        <div className="table-responsive">
          <table className="table table-striped table-hover">
            <thead className="table-dark">
              <tr>
                <th>ID</th>
                <th>Book</th>
                <th>Member</th>
                <th>Issue Date</th>
                <th>Due Date</th>
                <th>Return Date</th>
                <th>Status</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {borrowings.length === 0 ? (
                <tr><td colSpan="8" className="text-center text-muted">No borrowing transactions found.</td></tr>
              ) : (
                borrowings.map((b) => (
                  <tr key={b.id}>
                    <td>{b.id}</td>
                    <td>{b.bookTitle}</td>
                    <td>{b.memberName}</td>
                    <td>{b.issueDate}</td>
                    <td>{b.dueDate}</td>
                    <td>{b.returnDate || <span className="text-muted">—</span>}</td>
                    <td>
                      <span className={`badge ${b.status === 'ISSUED' ? 'bg-warning text-dark' : 'bg-success'}`}>
                        {b.status}
                      </span>
                    </td>
                    <td>
                      {b.status === 'ISSUED' && (
                        <button className="btn btn-sm btn-success" onClick={() => handleReturn(b.id)}>
                          Return
                        </button>
                      )}
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}

export default Borrowings;
