package com.library.dto;

/**
 * Author Response DTO - What the API sends back to the client.
 *
 * This controls exactly what fields the client sees.
 */
public class AuthorResponse {

    private Long id;
    private String name;
    private String biography;

    // Default constructor
    public AuthorResponse() {
    }

    // Constructor from all fields
    public AuthorResponse(Long id, String name, String biography) {
        this.id = id;
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
