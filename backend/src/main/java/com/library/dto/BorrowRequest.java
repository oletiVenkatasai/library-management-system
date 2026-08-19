package com.library.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * Borrow Request DTO - Used when issuing a book.
 */
public class BorrowRequest {

    @NotNull(message = "Member ID is required")
    private Long memberId;

    @NotNull(message = "Book ID is required")
    private Long bookId;

    @NotNull(message = "Due date is required")
    private LocalDate dueDate;

    // Default constructor
    public BorrowRequest() {
    }

    // Getters and Setters
    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
