import { useState, useEffect } from 'react';
import bookService from '../services/bookService';
import authorService from '../services/authorService';

/**
 * Books Page - Full CRUD for books with search functionality.
 */
function Books() {
  const [books, setBooks] = useState([]);
  const [authors, setAuthors] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [searchKeyword, setSearchKeyword] = useState('');
  const [showForm, setShowForm] = useState(false);
  const [editingBook, setEditingBook] = useState(null);
  const [form, setForm] = useState({
    title: '', isbn: '', category: '', quantity: '',
    availableQuantity: '', publishedDate: '', authorId: ''
  });

  useEffect(() => {
    fetchBooks();
    fetchAuthors();
  }, []);

  const fetchBooks = async () => {
    try {
      setLoading(true);
      const response = await bookService.getAllBooks();
      setBooks(response.data);
      setError('');
    } catch (err) {
      setError('Failed to load books.');
    } finally {
      setLoading(false);
    }
  };

  const fetchAuthors = async () => {
    try {
      const response = await authorService.getAllAuthors();
      setAuthors(response.data);
    } catch (err) {
      console.error('Failed to load authors', err);
    }
  };

  const handleSearch = async () => {
    if (!searchKeyword.trim()) {
      fetchBooks();
      return;
    }
    try {
      const response = await bookService.searchBooks(searchKeyword);
      setBooks(response.data);
    } catch (err) {
      setError('Search failed.');
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');

    // Basic frontend validation
    if (parseInt(form.availableQuantity) > parseInt(form.quantity)) {
      setError('Available quantity cannot be greater than total quantity.');
      return;
    }

    try {
      const bookData = {
        ...form,
        quantity: parseInt(form.quantity),
        availableQuantity: parseInt(form.availableQuantity),
        authorId: parseInt(form.authorId),
      };

      if (editingBook) {
        await bookService.updateBook(editingBook.id, bookData);
        setSuccess('Book updated successfully!');
      } else {
        await bookService.addBook(bookData);
        setSuccess('Book added successfully!');
      }

      resetForm();
      fetchBooks();
    } catch (err) {
      const data = err.response?.data;
      const message = data?.message || (data?.errors ? Object.values(data.errors).join(', ') : 'Operation failed.');
      setError(message);
    }
  };

  const handleEdit = (book) => {
    setEditingBook(book);
    setForm({
      title: book.title,
      isbn: book.isbn,
      category: book.category,
      quantity: book.quantity.toString(),
      availableQuantity: book.availableQuantity.toString(),
      publishedDate: book.publishedDate || '',
      authorId: book.authorId.toString(),
    });
    setShowForm(true);
    setError('');
    setSuccess('');
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Are you sure you want to delete this book?')) return;
    setError('');
    setSuccess('');
    try {
      await bookService.deleteBook(id);
      setSuccess('Book deleted successfully!');
      fetchBooks();
    } catch (err) {
      const data = err.response?.data;
      setError(data?.message || 'Failed to delete book.');
    }
  };

  const resetForm = () => {
    setForm({ title: '', isbn: '', category: '', quantity: '', availableQuantity: '', publishedDate: '', authorId: '' });
    setEditingBook(null);
    setShowForm(false);
  };

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h2>Books</h2>
        <button className="btn btn-primary" onClick={() => { resetForm(); setShowForm(!showForm); }}>
          {showForm ? 'Cancel' : '+ Add Book'}
        </button>
      </div>

      {error && <div className="alert alert-danger alert-dismissible">{error}<button type="button" className="btn-close" onClick={() => setError('')}></button></div>}
      {success && <div className="alert alert-success alert-dismissible">{success}<button type="button" className="btn-close" onClick={() => setSuccess('')}></button></div>}

      {/* Add/Edit Form */}
      {showForm && (
        <div className="card mb-3">
          <div className="card-header">{editingBook ? 'Edit Book' : 'Add New Book'}</div>
          <div className="card-body">
            <form onSubmit={handleSubmit}>
              <div className="row g-3">
                <div className="col-md-6">
                  <label className="form-label">Title *</label>
                  <input type="text" className="form-control" value={form.title}
                    onChange={(e) => setForm({ ...form, title: e.target.value })} required />
                </div>
                <div className="col-md-6">
                  <label className="form-label">ISBN *</label>
                  <input type="text" className="form-control" value={form.isbn}
                    onChange={(e) => setForm({ ...form, isbn: e.target.value })} required />
                </div>
                <div className="col-md-4">
                  <label className="form-label">Category *</label>
                  <input type="text" className="form-control" value={form.category}
                    onChange={(e) => setForm({ ...form, category: e.target.value })} required />
                </div>
                <div className="col-md-4">
                  <label className="form-label">Quantity *</label>
                  <input type="number" className="form-control" min="0" value={form.quantity}
                    onChange={(e) => setForm({ ...form, quantity: e.target.value })} required />
                </div>
                <div className="col-md-4">
                  <label className="form-label">Available Quantity *</label>
                  <input type="number" className="form-control" min="0" value={form.availableQuantity}
                    onChange={(e) => setForm({ ...form, availableQuantity: e.target.value })} required />
                </div>
                <div className="col-md-6">
                  <label className="form-label">Published Date</label>
                  <input type="date" className="form-control" value={form.publishedDate}
                    onChange={(e) => setForm({ ...form, publishedDate: e.target.value })} />
                </div>
                <div className="col-md-6">
                  <label className="form-label">Author *</label>
                  <select className="form-select" value={form.authorId}
                    onChange={(e) => setForm({ ...form, authorId: e.target.value })} required>
                    <option value="">Select Author</option>
                    {authors.map((a) => (
                      <option key={a.id} value={a.id}>{a.name}</option>
                    ))}
                  </select>
                </div>
              </div>
              <div className="mt-3">
                <button type="submit" className="btn btn-primary me-2">
                  {editingBook ? 'Update Book' : 'Add Book'}
                </button>
                <button type="button" className="btn btn-secondary" onClick={resetForm}>Cancel</button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* Search */}
      <div className="input-group mb-3" style={{ maxWidth: '400px' }}>
        <input type="text" className="form-control" placeholder="Search by title, ISBN, or category..."
          value={searchKeyword} onChange={(e) => setSearchKeyword(e.target.value)}
          onKeyDown={(e) => e.key === 'Enter' && handleSearch()} />
        <button className="btn btn-outline-secondary" onClick={handleSearch}>Search</button>
        {searchKeyword && (
          <button className="btn btn-outline-danger" onClick={() => { setSearchKeyword(''); fetchBooks(); }}>Clear</button>
        )}
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
                <th>Title</th>
                <th>Author</th>
                <th>Category</th>
                <th>ISBN</th>
                <th>Quantity</th>
                <th>Available</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {books.length === 0 ? (
                <tr><td colSpan="8" className="text-center text-muted">No books found.</td></tr>
              ) : (
                books.map((book) => (
                  <tr key={book.id}>
                    <td>{book.id}</td>
                    <td>{book.title}</td>
                    <td>{book.authorName}</td>
                    <td>{book.category}</td>
                    <td>{book.isbn}</td>
                    <td>{book.quantity}</td>
                    <td>
                      <span className={`badge ${book.availableQuantity > 0 ? 'bg-success' : 'bg-danger'}`}>
                        {book.availableQuantity}
                      </span>
                    </td>
                    <td>
                      <button className="btn btn-sm btn-warning me-1" onClick={() => handleEdit(book)}>Edit</button>
                      <button className="btn btn-sm btn-danger" onClick={() => handleDelete(book.id)}>Delete</button>
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

export default Books;
