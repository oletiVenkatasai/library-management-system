package com.library.repository;

import com.library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author Repository - Provides database operations for the Author entity.
 *
 * WHAT: An interface that extends JpaRepository to get CRUD methods for free.
 * WHY: Spring Data JPA auto-generates the implementation at runtime.
 *      We don't need to write SQL for basic operations.
 * HOW: JpaRepository<Author, Long> means:
 *      - Author = the entity type
 *      - Long = the type of the primary key (id)
 *
 * Built-in methods we get for free:
 * - save(author)         → INSERT or UPDATE
 * - findById(id)         → SELECT WHERE id = ?
 * - findAll()            → SELECT *
 * - deleteById(id)       → DELETE WHERE id = ?
 * - count()              → SELECT COUNT(*)
 *
 * Interview Question: "What is Spring Data JPA?"
 * Answer: "Spring Data JPA is a module that reduces boilerplate code.
 * We just define an interface extending JpaRepository, and Spring
 * automatically generates the implementation with all CRUD methods."
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    /**
     * Search authors by name (case-insensitive, partial match).
     *
     * Spring Data JPA reads the method name and generates:
     * SELECT * FROM authors WHERE LOWER(name) LIKE LOWER('%keyword%')
     */
    List<Author> findByNameContainingIgnoreCase(String name);
}
