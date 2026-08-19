import api from './api';

const borrowService = {
  getAllBorrowings: () => api.get('/borrowings'),
  getActiveBorrowings: () => api.get('/borrowings/active'),
  getMemberBorrowingHistory: (memberId) => api.get(`/borrowings/member/${memberId}`),
  issueBook: (data) => api.post('/borrowings/issue', data),
  returnBook: (id) => api.put(`/borrowings/return/${id}`),
};

export default borrowService;
