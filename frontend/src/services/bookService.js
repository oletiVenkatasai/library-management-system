import api from './api';

const bookService = {
  getAllBooks: () => api.get('/books'),
  getBookById: (id) => api.get(`/books/${id}`),
  addBook: (book) => api.post('/books', book),
  updateBook: (id, book) => api.put(`/books/${id}`, book),
  deleteBook: (id) => api.delete(`/books/${id}`),
  searchBooks: (keyword) => api.get(`/books/search?keyword=${keyword}`),
};

export default bookService;
