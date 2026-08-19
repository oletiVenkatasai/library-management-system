package com.library.repository;

import com.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Book Repository.
 *
 * Custom query methods use Spring Data JPA's derived query feature
 * and simple JPQL where needed.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Find a book by ISBN.
     */
    Optional<Book> findByIsbn(String isbn);

    /**
     * Search books by title, ISBN, or category (case-insensitive).
     * Uses JPQL (Java Persistence Query Language) for a multi-field search.
     */
    @Query("SELECT b FROM Book b WHERE " +
           "LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(b.isbn) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(b.category) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Book> searchBooks(@Param("keyword") String keyword);

    /**
     * Find all books by author ID.
     * Used to check if an author has books before deleting.
     */
    List<Book> findByAuthorId(Long authorId);

    /**
     * Count books that have available copies > 0.
     */
    @Query("SELECT COUNT(b) FROM Book b WHERE b.availableQuantity > 0")
    long countAvailableBooks();
}
