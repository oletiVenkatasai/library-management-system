package com.library.service;

import com.library.dto.AuthorRequest;
import com.library.dto.AuthorResponse;
import com.library.entity.Author;
import com.library.exception.BusinessException;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author Service - Contains all business logic for author operations.
 *
 * WHAT: The middle layer between Controller and Repository.
 * WHY: Business rules belong here, NOT in the controller.
 *      Controllers should only handle HTTP requests/responses.
 * WHERE: Called by AuthorController, calls AuthorRepository.
 *
 * Interview Question: "Why did you separate Service and Controller?"
 * Answer: "The Controller handles HTTP (request/response). The Service handles
 * business logic (validation, rules). This follows the Single Responsibility Principle."
 */
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    // Constructor injection (recommended over @Autowired on fields)
    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Get all authors.
     */
    public List<AuthorResponse> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get author by ID.
     */
    public AuthorResponse getAuthorById(Long id) {
        Author author = findAuthorOrThrow(id);
        return mapToResponse(author);
    }

    /**
     * Add a new author.
     */
    public AuthorResponse addAuthor(AuthorRequest request) {
        Author author = new Author();
        author.setName(request.getName());
        author.setBiography(request.getBiography());

        Author saved = authorRepository.save(author);
        return mapToResponse(saved);
    }

    /**
     * Update an existing author.
     */
    public AuthorResponse updateAuthor(Long id, AuthorRequest request) {
        Author author = findAuthorOrThrow(id);

        author.setName(request.getName());
        author.setBiography(request.getBiography());

        Author updated = authorRepository.save(author);
        return mapToResponse(updated);
    }

    /**
     * Delete an author.
     * Cannot delete if books are associated with this author.
     */
    public void deleteAuthor(Long id) {
        Author author = findAuthorOrThrow(id);

        // Check if author has any books
        if (!bookRepository.findByAuthorId(id).isEmpty()) {
            throw new BusinessException("Cannot delete author. Books are associated with this author.");
        }

        authorRepository.delete(author);
    }

    /**
     * Search authors by name.
     */
    public List<AuthorResponse> searchAuthors(String keyword) {
        return authorRepository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // Helper Methods
    // ============================================================

    /**
     * Find author by ID or throw ResourceNotFoundException.
     * Used internally by multiple methods.
     */
    private Author findAuthorOrThrow(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
    }

    /**
     * Convert Author entity to AuthorResponse DTO.
     */
    private AuthorResponse mapToResponse(Author author) {
        return new AuthorResponse(
                author.getId(),
                author.getName(),
                author.getBiography()
        );
    }
}
