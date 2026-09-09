package com.library.repository;

import com.library.entity.BorrowStatus;
import com.library.entity.BorrowTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowTransactionRepository extends JpaRepository<BorrowTransaction, Long> {

    List<BorrowTransaction> findByStatus(BorrowStatus status);

    List<BorrowTransaction> findByMemberId(Long memberId);

    List<BorrowTransaction> findByBookId(Long bookId);

    List<BorrowTransaction> findByBookIdAndStatus(Long bookId, BorrowStatus status);

    List<BorrowTransaction> findByMemberIdAndStatus(Long memberId, BorrowStatus status);

    long countByStatus(BorrowStatus status);

    @Query("SELECT bt FROM BorrowTransaction bt ORDER BY bt.issueDate DESC")
    List<BorrowTransaction> findRecentBorrowings();

    @Query("SELECT bt FROM BorrowTransaction bt WHERE bt.status = :status AND bt.dueDate < :today ORDER BY bt.dueDate ASC")
    List<BorrowTransaction> findOverdueBorrowings(
            @org.springframework.data.repository.query.Param("status") BorrowStatus status,
            @org.springframework.data.repository.query.Param("today") java.time.LocalDate today);
}
