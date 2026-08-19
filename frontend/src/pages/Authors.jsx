import { useState, useEffect } from 'react';
import authorService from '../services/authorService';

/**
 * Authors Page - Full CRUD for authors with search.
 */
function Authors() {
  const [authors, setAuthors] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [searchKeyword, setSearchKeyword] = useState('');
  const [showForm, setShowForm] = useState(false);
  const [editingAuthor, setEditingAuthor] = useState(null);
  const [form, setForm] = useState({ name: '', biography: '' });

  useEffect(() => {
    fetchAuthors();
  }, []);

  const fetchAuthors = async () => {
    try {
      setLoading(true);
      const response = await authorService.getAllAuthors();
      setAuthors(response.data);
      setError('');
    } catch (err) {
      setError('Failed to load authors.');
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async () => {
    if (!searchKeyword.trim()) {
      fetchAuthors();
      return;
    }
    try {
      const response = await authorService.searchAuthors(searchKeyword);
      setAuthors(response.data);
    } catch (err) {
      setError('Search failed.');
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');

    try {
      if (editingAuthor) {
        await authorService.updateAuthor(editingAuthor.id, form);
        setSuccess('Author updated successfully!');
      } else {
        await authorService.addAuthor(form);
        setSuccess('Author added successfully!');
      }
      resetForm();
      fetchAuthors();
    } catch (err) {
      const message = err.response?.data?.message || 'Operation failed.';
      setError(message);
    }
  };

  const handleEdit = (author) => {
    setEditingAuthor(author);
    setForm({ name: author.name, biography: author.biography || '' });
    setShowForm(true);
    setError('');
    setSuccess('');
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Are you sure you want to delete this author?')) return;
    try {
      await authorService.deleteAuthor(id);
      setSuccess('Author deleted successfully!');
      fetchAuthors();
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to delete author.');
    }
  };

  const resetForm = () => {
    setForm({ name: '', biography: '' });
    setEditingAuthor(null);
    setShowForm(false);
  };

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h2>Authors</h2>
        <button className="btn btn-primary" onClick={() => { resetForm(); setShowForm(!showForm); }}>
          {showForm ? 'Cancel' : '+ Add Author'}
        </button>
      </div>

      {error && <div className="alert alert-danger alert-dismissible">{error}<button type="button" className="btn-close" onClick={() => setError('')}></button></div>}
      {success && <div className="alert alert-success alert-dismissible">{success}<button type="button" className="btn-close" onClick={() => setSuccess('')}></button></div>}

      {/* Form */}
      {showForm && (
        <div className="card mb-3">
          <div className="card-header">{editingAuthor ? 'Edit Author' : 'Add New Author'}</div>
          <div className="card-body">
            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label className="form-label">Name *</label>
                <input type="text" className="form-control" value={form.name}
                  onChange={(e) => setForm({ ...form, name: e.target.value })} required />
              </div>
              <div className="mb-3">
                <label className="form-label">Biography</label>
                <textarea className="form-control" rows="3" value={form.biography}
                  onChange={(e) => setForm({ ...form, biography: e.target.value })} />
              </div>
              <button type="submit" className="btn btn-primary me-2">
                {editingAuthor ? 'Update Author' : 'Add Author'}
              </button>
              <button type="button" className="btn btn-secondary" onClick={resetForm}>Cancel</button>
            </form>
          </div>
        </div>
      )}

      {/* Search */}
      <div className="input-group mb-3" style={{ maxWidth: '400px' }}>
        <input type="text" className="form-control" placeholder="Search by name..."
          value={searchKeyword} onChange={(e) => setSearchKeyword(e.target.value)}
          onKeyDown={(e) => e.key === 'Enter' && handleSearch()} />
        <button className="btn btn-outline-secondary" onClick={handleSearch}>Search</button>
        {searchKeyword && (
          <button className="btn btn-outline-danger" onClick={() => { setSearchKeyword(''); fetchAuthors(); }}>Clear</button>
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
                <th>Name</th>
                <th>Biography</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {authors.length === 0 ? (
                <tr><td colSpan="4" className="text-center text-muted">No authors found.</td></tr>
              ) : (
                authors.map((author) => (
                  <tr key={author.id}>
                    <td>{author.id}</td>
                    <td>{author.name}</td>
                    <td>{author.biography || <span className="text-muted">—</span>}</td>
                    <td>
                      <button className="btn btn-sm btn-warning me-1" onClick={() => handleEdit(author)}>Edit</button>
                      <button className="btn btn-sm btn-danger" onClick={() => handleDelete(author.id)}>Delete</button>
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

export default Authors;
