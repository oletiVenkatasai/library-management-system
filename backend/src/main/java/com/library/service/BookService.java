package com.library.service;

import com.library.dto.BookRequest;
import com.library.dto.BookResponse;
import com.library.entity.Author;
import com.library.entity.Book;
import com.library.entity.BorrowStatus;
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

/**
 * Book Service - Business logic for book operations.
 *
 * Key business rules:
 * 1. ISBN must be unique
 * 2. availableQuantity cannot be greater than quantity
 * 3. Cannot delete a book with active borrowings
 * 4. Author must exist when adding/updating a book
 */
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

    /**
     * Get all books.
     */
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get book by ID.
     */
    public BookResponse getBookById(Long id) {
        Book book = findBookOrThrow(id);
        return mapToResponse(book);
    }

    /**
     * Add a new book.
     */
    public BookResponse addBook(BookRequest request) {
        // Check for duplicate ISBN
        Optional<Book> existingBook = bookRepository.findByIsbn(request.getIsbn());
        if (existingBook.isPresent()) {
            throw new DuplicateResourceException("A book with ISBN '" + request.getIsbn() + "' already exists");
        }

        // Validate availableQuantity <= quantity
        if (request.getAvailableQuantity() > request.getQuantity()) {
            throw new BusinessException("Available quantity cannot be greater than total quantity");
        }

        // Find the author
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

    /**
     * Update an existing book.
     */
    public BookResponse updateBook(Long id, BookRequest request) {
        Book book = findBookOrThrow(id);

        // Check for duplicate ISBN (but allow the same book to keep its ISBN)
        Optional<Book> existingBook = bookRepository.findByIsbn(request.getIsbn());
        if (existingBook.isPresent() && !existingBook.get().getId().equals(id)) {
            throw new DuplicateResourceException("A book with ISBN '" + request.getIsbn() + "' already exists");
        }

        // Validate availableQuantity <= quantity
        if (request.getAvailableQuantity() > request.getQuantity()) {
            throw new BusinessException("Available quantity cannot be greater than total quantity");
        }

        // Find the author
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

    /**
     * Delete a book.
     * Cannot delete if the book has active (ISSUED) borrowings.
     */
    public void deleteBook(Long id) {
        Book book = findBookOrThrow(id);

        // Check for active borrowings
        if (!borrowTransactionRepository.findByBookIdAndStatus(id, BorrowStatus.ISSUED).isEmpty()) {
            throw new BusinessException("Cannot delete book. It has active borrowing transactions.");
        }

        bookRepository.delete(book);
    }

    /**
     * Search books by title, ISBN, or category.
     */
    public List<BookResponse> searchBooks(String keyword) {
        return bookRepository.searchBooks(keyword)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // Helper Methods
    // ============================================================

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
