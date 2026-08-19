package com.library.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Author Request DTO - What the client sends when creating/updating an author.
 *
 * WHY use a DTO instead of the Entity directly?
 * - The Entity has an 'id' field that we don't want the client to set.
 * - DTOs let us control exactly what data comes in and goes out.
 * - We can add validation annotations here without cluttering the Entity.
 */
public class AuthorRequest {

    @NotBlank(message = "Author name is required")
    private String name;

    private String biography;  // Optional

    // Default constructor
    public AuthorRequest() {
    }

    // Getters and Setters
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
