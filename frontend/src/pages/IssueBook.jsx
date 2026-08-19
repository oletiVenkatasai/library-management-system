import { useState, useEffect } from 'react';
import bookService from '../services/bookService';
import memberService from '../services/memberService';
import borrowService from '../services/borrowService';

/**
 * Issue Book Page - Form to issue a book to a member.
 */
function IssueBook() {
  const [members, setMembers] = useState([]);
  const [books, setBooks] = useState([]);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [form, setForm] = useState({ memberId: '', bookId: '', dueDate: '' });

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const [membersRes, booksRes] = await Promise.all([
        memberService.getAllMembers(),
        bookService.getAllBooks(),
      ]);
      setMembers(membersRes.data);
      // Only show books that have available copies
      setBooks(booksRes.data.filter((b) => b.availableQuantity > 0));
    } catch (err) {
      setError('Failed to load data.');
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');

    try {
      const data = {
        memberId: parseInt(form.memberId),
        bookId: parseInt(form.bookId),
        dueDate: form.dueDate,
      };
      await borrowService.issueBook(data);
      setSuccess('Book issued successfully!');
      setForm({ memberId: '', bookId: '', dueDate: '' });
      fetchData(); // Refresh available books
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to issue book.');
    }
  };

  return (
    <div>
      <h2 className="mb-4">Issue Book</h2>

      {error && <div className="alert alert-danger alert-dismissible">{error}<button type="button" className="btn-close" onClick={() => setError('')}></button></div>}
      {success && <div className="alert alert-success alert-dismissible">{success}<button type="button" className="btn-close" onClick={() => setSuccess('')}></button></div>}

      <div className="card" style={{ maxWidth: '600px' }}>
        <div className="card-header">Issue a Book</div>
        <div className="card-body">
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label className="form-label">Select Member *</label>
              <select className="form-select" value={form.memberId}
                onChange={(e) => setForm({ ...form, memberId: e.target.value })} required>
                <option value="">-- Choose Member --</option>
                {members.map((m) => (
                  <option key={m.id} value={m.id}>{m.name} ({m.email})</option>
                ))}
              </select>
            </div>
            <div className="mb-3">
              <label className="form-label">Select Book *</label>
              <select className="form-select" value={form.bookId}
                onChange={(e) => setForm({ ...form, bookId: e.target.value })} required>
                <option value="">-- Choose Book --</option>
                {books.map((b) => (
                  <option key={b.id} value={b.id}>{b.title} (Available: {b.availableQuantity})</option>
                ))}
              </select>
            </div>
            <div className="mb-3">
              <label className="form-label">Due Date *</label>
              <input type="date" className="form-control" value={form.dueDate}
                onChange={(e) => setForm({ ...form, dueDate: e.target.value })} required />
            </div>
            <button type="submit" className="btn btn-primary">Issue Book</button>
          </form>
        </div>
      </div>
    </div>
  );
}

export default IssueBook;
