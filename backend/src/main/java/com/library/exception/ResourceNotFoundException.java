package com.library.exception;

/**
 * Thrown when a requested resource (book, author, member, etc.) is not found.
 *
 * Example: When someone tries to get a book with ID 999, but it doesn't exist.
 *
 * Interview Question: "How do you handle 'not found' errors in your project?"
 * Answer: "I created a custom ResourceNotFoundException. When thrown, the
 * GlobalExceptionHandler catches it and returns a 404 JSON response."
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
