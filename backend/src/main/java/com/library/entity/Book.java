package com.library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

/**
 * Book Entity - Maps to the 'books' table in MySQL.
 *
 * KEY RELATIONSHIP:
 * @ManyToOne with Author → Many books can belong to one author.
 * @JoinColumn(name = "author_id") → The 'author_id' column in the books table
 * is the foreign key pointing to the authors table.
 *
 * Interview Question: "What is @ManyToOne in JPA?"
 * Answer: "It defines a many-to-one relationship. In my project, many books
 * can belong to one author. The @JoinColumn specifies which column in the
 * books table holds the foreign key."
 */
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, unique = true, length = 20)
    private String isbn;

    @Column(nullable = false, length = 100)
    private String category;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "available_quantity", nullable = false)
    private int availableQuantity;

    @Column(name = "published_date")
    private LocalDate publishedDate;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    // Default constructor
    public Book() {
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
