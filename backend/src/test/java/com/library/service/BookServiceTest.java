package com.library.service;

import com.library.dto.BookRequest;
import com.library.dto.BookResponse;
import com.library.entity.Author;
import com.library.entity.Book;
import com.library.exception.BusinessException;
import com.library.exception.DuplicateResourceException;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import com.library.repository.BorrowTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for BookService using Mockito.
 */
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BorrowTransactionRepository borrowTransactionRepository;

    @InjectMocks
    private BookService bookService;

    private Author author;
    private Book book;
    private BookRequest bookRequest;

    @BeforeEach
    void setUp() {
        author = new Author("Robert C. Martin", "Clean code advocate");
        author.setId(1L);

        book = new Book();
        book.setId(10L);
        book.setTitle("Clean Code");
        book.setIsbn("978-0132350884");
        book.setCategory("Software Engineering");
        book.setQuantity(5);
        book.setAvailableQuantity(5);
        book.setAuthor(author);

        bookRequest = new BookRequest();
        bookRequest.setTitle("Clean Code");
        bookRequest.setIsbn("978-0132350884");
        bookRequest.setCategory("Software Engineering");
        bookRequest.setQuantity(5);
        bookRequest.setAvailableQuantity(5);
        bookRequest.setAuthorId(1L);
    }

    @Test
    void testAddBook_Success() {
        when(bookRepository.findByIsbn(bookRequest.getIsbn())).thenReturn(Optional.empty());
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookResponse response = bookService.addBook(bookRequest);

        assertNotNull(response);
        assertEquals("Clean Code", response.getTitle());
        assertEquals("978-0132350884", response.getIsbn());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testAddBook_DuplicateIsbn_ThrowsException() {
        when(bookRepository.findByIsbn(bookRequest.getIsbn())).thenReturn(Optional.of(book));

        assertThrows(DuplicateResourceException.class, () -> bookService.addBook(bookRequest));
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void testAddBook_InvalidAvailableQuantity_ThrowsException() {
        bookRequest.setAvailableQuantity(10); // Greater than total quantity 5

        when(bookRepository.findByIsbn(bookRequest.getIsbn())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> bookService.addBook(bookRequest));
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void testGetBookById_NotFound_ThrowsException() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.getBookById(99L));
    }
}
