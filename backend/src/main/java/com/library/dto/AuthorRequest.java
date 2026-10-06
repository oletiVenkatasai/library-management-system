package com.library.dto;

import jakarta.validation.constraints.NotBlank;

public class AuthorRequest {

    @NotBlank(message = "Author name is required")
    private String name;

    private String biography;

    public AuthorRequest() {
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
