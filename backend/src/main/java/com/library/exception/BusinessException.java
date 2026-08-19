package com.library.exception;

/**
 * Thrown when a business rule is violated.
 *
 * Examples:
 * - Trying to issue a book that has no available copies
 * - Trying to return a book that was already returned
 * - Trying to delete an author who still has books
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
