package com.library.controller;

import com.library.dto.AuthorRequest;
import com.library.dto.AuthorResponse;
import com.library.service.AuthorService;
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
 * Author Controller - Handles HTTP requests for author operations.
 *
 * WHAT: The entry point for all /api/authors requests.
 * WHY: Maps HTTP methods (GET, POST, PUT, DELETE) to service methods.
 * HOW: Spring automatically routes requests to the matching method.
 *
 * @RestController = @Controller + @ResponseBody
 *   - Tells Spring: "This class handles REST API requests"
 *   - All return values are automatically converted to JSON
 *
 * @RequestMapping("/api/authors") = All endpoints in this class start with /api/authors
 *
 * Interview Question: "What is @RestController?"
 * Answer: "It combines @Controller and @ResponseBody. It tells Spring that
 * this class handles REST requests and returns JSON responses directly."
 */
@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * GET /api/authors - Get all authors
     */
    @GetMapping
    public ResponseEntity<List<AuthorResponse>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    /**
     * GET /api/authors/{id} - Get author by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    /**
     * POST /api/authors - Add a new author
     * @Valid triggers Jakarta Validation on the request body
     */
    @PostMapping
    public ResponseEntity<AuthorResponse> addAuthor(@Valid @RequestBody AuthorRequest request) {
        AuthorResponse created = authorService.addAuthor(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    /**
     * PUT /api/authors/{id} - Update an existing author
     */
    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponse> updateAuthor(
            @PathVariable Long id,
            @Valid @RequestBody AuthorRequest request) {
        return ResponseEntity.ok(authorService.updateAuthor(id, request));
    }

    /**
     * DELETE /api/authors/{id} - Delete an author
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/authors/search?keyword=value - Search authors by name
     */
    @GetMapping("/search")
    public ResponseEntity<List<AuthorResponse>> searchAuthors(@RequestParam String keyword) {
        return ResponseEntity.ok(authorService.searchAuthors(keyword));
    }
}
