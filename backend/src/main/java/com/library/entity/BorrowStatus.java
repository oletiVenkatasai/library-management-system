package com.library.entity;

/**
 * Borrow Status Enum - Represents the state of a borrowing transaction.
 *
 * ISSUED   → The book has been borrowed and not yet returned.
 * RETURNED → The book has been returned.
 */
public enum BorrowStatus {
    ISSUED,
    RETURNED
}
