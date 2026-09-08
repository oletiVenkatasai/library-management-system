import { useState, useEffect } from 'react';
import memberService from '../services/memberService';

/**
 * Members Page - Full CRUD for members with search.
 */
function Members() {
  const [members, setMembers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [searchKeyword, setSearchKeyword] = useState('');
  const [showForm, setShowForm] = useState(false);
  const [editingMember, setEditingMember] = useState(null);
  const [form, setForm] = useState({
    name: '', email: '', phone: '', address: '', membershipDate: ''
  });

  useEffect(() => {
    fetchMembers();
  }, []);

  const fetchMembers = async () => {
    try {
      setLoading(true);
      const response = await memberService.getAllMembers();
      setMembers(response.data);
      setError('');
    } catch (err) {
      setError('Failed to load members.');
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async () => {
    if (!searchKeyword.trim()) {
      fetchMembers();
      return;
    }
    try {
      const response = await memberService.searchMembers(searchKeyword);
      setMembers(response.data);
    } catch (err) {
      setError('Search failed.');
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');

    try {
      if (editingMember) {
        await memberService.updateMember(editingMember.id, form);
        setSuccess('Member updated successfully!');
      } else {
        await memberService.addMember(form);
        setSuccess('Member added successfully!');
      }
      resetForm();
      fetchMembers();
    } catch (err) {
      const data = err.response?.data;
      const message = data?.message || (data?.errors ? Object.values(data.errors).join(', ') : 'Operation failed.');
      setError(message);
    }
  };

  const handleEdit = (member) => {
    setEditingMember(member);
    setForm({
      name: member.name,
      email: member.email,
      phone: member.phone,
      address: member.address || '',
      membershipDate: member.membershipDate || '',
    });
    setShowForm(true);
    setError('');
    setSuccess('');
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Are you sure you want to delete this member?')) return;
    setError('');
    setSuccess('');
    try {
      await memberService.deleteMember(id);
      setSuccess('Member deleted successfully!');
      fetchMembers();
    } catch (err) {
      const data = err.response?.data;
      setError(data?.message || 'Failed to delete member.');
    }
  };

  const resetForm = () => {
    setForm({ name: '', email: '', phone: '', address: '', membershipDate: '' });
    setEditingMember(null);
    setShowForm(false);
  };

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h2>Members</h2>
        <button className="btn btn-primary" onClick={() => { resetForm(); setShowForm(!showForm); }}>
          {showForm ? 'Cancel' : '+ Add Member'}
        </button>
      </div>

      {error && <div className="alert alert-danger alert-dismissible">{error}<button type="button" className="btn-close" onClick={() => setError('')}></button></div>}
      {success && <div className="alert alert-success alert-dismissible">{success}<button type="button" className="btn-close" onClick={() => setSuccess('')}></button></div>}

      {/* Form */}
      {showForm && (
        <div className="card mb-3">
          <div className="card-header">{editingMember ? 'Edit Member' : 'Add New Member'}</div>
          <div className="card-body">
            <form onSubmit={handleSubmit}>
              <div className="row g-3">
                <div className="col-md-6">
                  <label className="form-label">Name *</label>
                  <input type="text" className="form-control" value={form.name}
                    onChange={(e) => setForm({ ...form, name: e.target.value })} required />
                </div>
                <div className="col-md-6">
                  <label className="form-label">Email *</label>
                  <input type="email" className="form-control" value={form.email}
                    onChange={(e) => setForm({ ...form, email: e.target.value })} required />
                </div>
                <div className="col-md-4">
                  <label className="form-label">Phone *</label>
                  <input type="text" className="form-control" value={form.phone}
                    onChange={(e) => setForm({ ...form, phone: e.target.value })} required />
                </div>
                <div className="col-md-4">
                  <label className="form-label">Address</label>
                  <input type="text" className="form-control" value={form.address}
                    onChange={(e) => setForm({ ...form, address: e.target.value })} />
                </div>
                <div className="col-md-4">
                  <label className="form-label">Membership Date *</label>
                  <input type="date" className="form-control" value={form.membershipDate}
                    onChange={(e) => setForm({ ...form, membershipDate: e.target.value })} required />
                </div>
              </div>
              <div className="mt-3">
                <button type="submit" className="btn btn-primary me-2">
                  {editingMember ? 'Update Member' : 'Add Member'}
                </button>
                <button type="button" className="btn btn-secondary" onClick={resetForm}>Cancel</button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* Search */}
      <div className="input-group mb-3" style={{ maxWidth: '400px' }}>
        <input type="text" className="form-control" placeholder="Search by name or email..."
          value={searchKeyword} onChange={(e) => setSearchKeyword(e.target.value)}
          onKeyDown={(e) => e.key === 'Enter' && handleSearch()} />
        <button className="btn btn-outline-secondary" onClick={handleSearch}>Search</button>
        {searchKeyword && (
          <button className="btn btn-outline-danger" onClick={() => { setSearchKeyword(''); fetchMembers(); }}>Clear</button>
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
                <th>Email</th>
                <th>Phone</th>
                <th>Address</th>
                <th>Membership Date</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {members.length === 0 ? (
                <tr><td colSpan="7" className="text-center text-muted">No members found.</td></tr>
              ) : (
                members.map((member) => (
                  <tr key={member.id}>
                    <td>{member.id}</td>
                    <td>{member.name}</td>
                    <td>{member.email}</td>
                    <td>{member.phone}</td>
                    <td>{member.address || <span className="text-muted">—</span>}</td>
                    <td>{member.membershipDate}</td>
                    <td>
                      <button className="btn btn-sm btn-warning me-1" onClick={() => handleEdit(member)}>Edit</button>
                      <button className="btn btn-sm btn-danger" onClick={() => handleDelete(member.id)}>Delete</button>
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

export default Members;
