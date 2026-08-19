package com.library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Author Entity - Maps to the 'authors' table in MySQL.
 *
 * WHAT: A Java class that represents one row in the 'authors' table.
 * WHY: JPA/Hibernate uses this to automatically generate SQL queries.
 * HOW: Each field maps to a column. @Entity tells JPA "this is a database table."
 *
 * Interview Question: "What is an Entity in JPA?"
 * Answer: "An Entity is a Java class annotated with @Entity that maps to a database table.
 * Each instance of the class represents one row in that table."
 */
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String biography;

    // Default constructor (required by JPA)
    public Author() {
    }

    // Constructor with fields
    public Author(String name, String biography) {
        this.name = name;
        this.biography = biography;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
}
