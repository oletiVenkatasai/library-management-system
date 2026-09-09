package com.library.service;

import com.library.dto.AuthorRequest;
import com.library.dto.AuthorResponse;
import com.library.entity.Author;
import com.library.exception.BusinessException;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public List<AuthorResponse> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public AuthorResponse getAuthorById(Long id) {
        Author author = findAuthorOrThrow(id);
        return mapToResponse(author);
    }

    public AuthorResponse addAuthor(AuthorRequest request) {
        Author author = new Author();
        author.setName(request.getName());
        author.setBiography(request.getBiography());

        Author saved = authorRepository.save(author);
        return mapToResponse(saved);
    }

    public AuthorResponse updateAuthor(Long id, AuthorRequest request) {
        Author author = findAuthorOrThrow(id);

        author.setName(request.getName());
        author.setBiography(request.getBiography());

        Author updated = authorRepository.save(author);
        return mapToResponse(updated);
    }

    public void deleteAuthor(Long id) {
        Author author = findAuthorOrThrow(id);

        if (!bookRepository.findByAuthorId(id).isEmpty()) {
            throw new BusinessException("Cannot delete author. Books are associated with this author.");
        }

        authorRepository.delete(author);
    }

    public List<AuthorResponse> searchAuthors(String keyword) {
        return authorRepository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private Author findAuthorOrThrow(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
    }

    private AuthorResponse mapToResponse(Author author) {
        return new AuthorResponse(
                author.getId(),
                author.getName(),
                author.getBiography()
        );
    }
}
