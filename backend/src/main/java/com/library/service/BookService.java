package com.library.service;

import com.library.dto.BookRequest;
import com.library.dto.BookResponse;
import com.library.entity.Author;
import com.library.entity.Book;
import com.library.exception.BusinessException;
import com.library.exception.DuplicateResourceException;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.BookRepository;
import com.library.repository.AuthorRepository;
import com.library.repository.BorrowTransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BorrowTransactionRepository borrowTransactionRepository;

    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository,
                       BorrowTransactionRepository borrowTransactionRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.borrowTransactionRepository = borrowTransactionRepository;
    }

    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public BookResponse getBookById(Long id) {
        Book book = findBookOrThrow(id);
        return mapToResponse(book);
    }

    public BookResponse addBook(BookRequest request) {
        Optional<Book> existingBook = bookRepository.findByIsbn(request.getIsbn());
        if (existingBook.isPresent()) {
            throw new DuplicateResourceException("A book with ISBN '" + request.getIsbn() + "' already exists");
        }

        if (request.getAvailableQuantity() > request.getQuantity()) {
            throw new BusinessException("Available quantity cannot be greater than total quantity");
        }

        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + request.getAuthorId()));

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setCategory(request.getCategory());
        book.setQuantity(request.getQuantity());
        book.setAvailableQuantity(request.getAvailableQuantity());
        book.setPublishedDate(request.getPublishedDate());
        book.setAuthor(author);

        Book saved = bookRepository.save(book);
        return mapToResponse(saved);
    }

    public BookResponse updateBook(Long id, BookRequest request) {
        Book book = findBookOrThrow(id);

        Optional<Book> existingBook = bookRepository.findByIsbn(request.getIsbn());
        if (existingBook.isPresent() && !existingBook.get().getId().equals(id)) {
            throw new DuplicateResourceException("A book with ISBN '" + request.getIsbn() + "' already exists");
        }

        if (request.getAvailableQuantity() > request.getQuantity()) {
            throw new BusinessException("Available quantity cannot be greater than total quantity");
        }

        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + request.getAuthorId()));

        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setCategory(request.getCategory());
        book.setQuantity(request.getQuantity());
        book.setAvailableQuantity(request.getAvailableQuantity());
        book.setPublishedDate(request.getPublishedDate());
        book.setAuthor(author);

        Book updated = bookRepository.save(book);
        return mapToResponse(updated);
    }

    public void deleteBook(Long id) {
        Book book = findBookOrThrow(id);

        if (!borrowTransactionRepository.findByBookId(id).isEmpty()) {
            throw new BusinessException("Cannot delete book. Borrowing records are associated with this book.");
        }

        bookRepository.delete(book);
    }

    public List<BookResponse> searchBooks(String keyword) {
        return bookRepository.searchBooks(keyword)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private Book findBookOrThrow(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    private BookResponse mapToResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getCategory(),
                book.getQuantity(),
                book.getAvailableQuantity(),
                book.getPublishedDate(),
                book.getAuthor().getId(),
                book.getAuthor().getName()
        );
    }
}
