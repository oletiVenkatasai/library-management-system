import api from './api';

const authorService = {
  getAllAuthors: () => api.get('/authors'),
  getAuthorById: (id) => api.get(`/authors/${id}`),
  addAuthor: (author) => api.post('/authors', author),
  updateAuthor: (id, author) => api.put(`/authors/${id}`, author),
  deleteAuthor: (id) => api.delete(`/authors/${id}`),
  searchAuthors: (keyword) => api.get(`/authors/search?keyword=${keyword}`),
};

export default authorService;
