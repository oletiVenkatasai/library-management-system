package com.library.exception;

/**
 * Thrown when trying to create a resource that already exists.
 *
 * Examples:
 * - Adding a book with an ISBN that already exists
 * - Registering a member with an email that already exists
 */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}
