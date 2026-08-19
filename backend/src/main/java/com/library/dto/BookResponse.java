package com.library.dto;

import java.time.LocalDate;

/**
 * Book Response DTO - What the API sends back to the client.
 * Includes author name instead of the full Author object.
 */
public class BookResponse {

    private Long id;
    private String title;
    private String isbn;
    private String category;
    private int quantity;
    private int availableQuantity;
    private LocalDate publishedDate;
    private Long authorId;
    private String authorName;

    // Default constructor
    public BookResponse() {
    }

    // Constructor with all fields
    public BookResponse(Long id, String title, String isbn, String category,
                        int quantity, int availableQuantity, LocalDate publishedDate,
                        Long authorId, String authorName) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.category = category;
        this.quantity = quantity;
        this.availableQuantity = availableQuantity;
        this.publishedDate = publishedDate;
        this.authorId = authorId;
        this.authorName = authorName;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
