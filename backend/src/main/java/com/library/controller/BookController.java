package com.library.controller;

import com.library.dto.BookRequest;
import com.library.dto.BookResponse;
import com.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Book Controller - Handles HTTP requests for book operations.
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /** GET /api/books - Get all books */
    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    /** GET /api/books/{id} - Get book by ID */
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    /** POST /api/books - Add a new book */
    @PostMapping
    public ResponseEntity<BookResponse> addBook(@Valid @RequestBody BookRequest request) {
        BookResponse created = bookService.addBook(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    /** PUT /api/books/{id} - Update a book */
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookRequest request) {
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    /** DELETE /api/books/{id} - Delete a book */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    /** GET /api/books/search?keyword=value - Search books */
    @GetMapping("/search")
    public ResponseEntity<List<BookResponse>> searchBooks(@RequestParam String keyword) {
        return ResponseEntity.ok(bookService.searchBooks(keyword));
    }
}
