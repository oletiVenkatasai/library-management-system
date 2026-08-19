package com.library.dto;

import java.util.List;

/**
 * Dashboard Response DTO - Contains all statistics for the dashboard page.
 */
public class DashboardResponse {

    private long totalBooks;
    private long totalMembers;
    private long totalAuthors;
    private long availableBooks;
    private long issuedBooks;
    private List<BorrowResponse> recentBorrowings;

    // Default constructor
    public DashboardResponse() {
    }

    // Getters and Setters
    public long getTotalBooks() {
        return totalBooks;
    }

    public void setTotalBooks(long totalBooks) {
        this.totalBooks = totalBooks;
    }

    public long getTotalMembers() {
        return totalMembers;
    }

    public void setTotalMembers(long totalMembers) {
        this.totalMembers = totalMembers;
    }

    public long getTotalAuthors() {
        return totalAuthors;
    }

    public void setTotalAuthors(long totalAuthors) {
        this.totalAuthors = totalAuthors;
    }

    public long getAvailableBooks() {
        return availableBooks;
    }

    public void setAvailableBooks(long availableBooks) {
        this.availableBooks = availableBooks;
    }

    public long getIssuedBooks() {
        return issuedBooks;
    }

    public void setIssuedBooks(long issuedBooks) {
        this.issuedBooks = issuedBooks;
    }

    public List<BorrowResponse> getRecentBorrowings() {
        return recentBorrowings;
    }

    public void setRecentBorrowings(List<BorrowResponse> recentBorrowings) {
        this.recentBorrowings = recentBorrowings;
    }
}
