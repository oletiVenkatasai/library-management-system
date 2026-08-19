import api from './api';

const memberService = {
  getAllMembers: () => api.get('/members'),
  getMemberById: (id) => api.get(`/members/${id}`),
  addMember: (member) => api.post('/members', member),
  updateMember: (id, member) => api.put(`/members/${id}`, member),
  deleteMember: (id) => api.delete(`/members/${id}`),
  searchMembers: (keyword) => api.get(`/members/search?keyword=${keyword}`),
};

export default memberService;
