package com.library.repository;

import com.library.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Member Repository.
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * Find a member by email (for duplicate check).
     */
    Optional<Member> findByEmail(String email);

    /**
     * Search members by name or email (case-insensitive).
     */
    @Query("SELECT m FROM Member m WHERE " +
           "LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(m.email) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Member> searchMembers(@Param("keyword") String keyword);
}
