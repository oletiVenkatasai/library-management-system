package com.library.repository;

import com.library.entity.BorrowStatus;
import com.library.entity.BorrowTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BorrowTransaction Repository.
 */
@Repository
public interface BorrowTransactionRepository extends JpaRepository<BorrowTransaction, Long> {

    /**
     * Find all active (ISSUED) borrowings.
     */
    List<BorrowTransaction> findByStatus(BorrowStatus status);

    /**
     * Find all borrowings for a specific member.
     */
    List<BorrowTransaction> findByMemberId(Long memberId);

    /**
     * Find active borrowings for a specific book.
     * Used to check if a book can be deleted.
     */
    List<BorrowTransaction> findByBookIdAndStatus(Long bookId, BorrowStatus status);

    /**
     * Find active borrowings for a specific member.
     * Used to check if a member can be deleted.
     */
    List<BorrowTransaction> findByMemberIdAndStatus(Long memberId, BorrowStatus status);

    /**
     * Count all active (ISSUED) borrowings.
     */
    long countByStatus(BorrowStatus status);

    /**
     * Get recent borrowings (ordered by issue date descending).
     */
    @Query("SELECT bt FROM BorrowTransaction bt ORDER BY bt.issueDate DESC")
    List<BorrowTransaction> findRecentBorrowings();

    /**
     * Find overdue borrowings — ISSUED status where dueDate is before today.
     */
    @Query("SELECT bt FROM BorrowTransaction bt WHERE bt.status = :status AND bt.dueDate < :today ORDER BY bt.dueDate ASC")
    List<BorrowTransaction> findOverdueBorrowings(
            @org.springframework.data.repository.query.Param("status") BorrowStatus status,
            @org.springframework.data.repository.query.Param("today") java.time.LocalDate today);
}
