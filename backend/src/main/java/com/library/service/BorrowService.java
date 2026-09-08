package com.library.service;

import com.library.dto.BorrowRequest;
import com.library.dto.BorrowResponse;
import com.library.entity.Book;
import com.library.entity.BorrowStatus;
import com.library.entity.BorrowTransaction;
import com.library.entity.Member;
import com.library.exception.BusinessException;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.BookRepository;
import com.library.repository.BorrowTransactionRepository;
import com.library.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Borrow Service - Handles book issuing and returning.
 *
 * KEY CONCEPT: @Transactional
 * WHY? When we issue a book, TWO things must happen:
 *   1. Create the borrow transaction
 *   2. Decrease the book's availableQuantity
 * If step 2 fails, step 1 should also be rolled back.
 * @Transactional ensures both steps succeed or both fail.
 *
 * Interview Question: "What is @Transactional?"
 * Answer: "It ensures that all database operations in a method either
 * all succeed or all fail together. If one operation fails, everything
 * is rolled back to prevent inconsistent data."
 */
@Service
public class BorrowService {

    private final BorrowTransactionRepository borrowTransactionRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public BorrowService(BorrowTransactionRepository borrowTransactionRepository,
                         BookRepository bookRepository,
                         MemberRepository memberRepository) {
        this.borrowTransactionRepository = borrowTransactionRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * Issue a book to a member.
     *
     * Steps:
     * 1. Check that the member exists
     * 2. Check that the book exists
     * 3. Check that the book is available (availableQuantity > 0)
     * 4. Create borrow transaction with status ISSUED
     * 5. Decrease availableQuantity by 1
     */
    @Transactional
    public BorrowResponse issueBook(BorrowRequest request) {
        // 1. Find member
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + request.getMemberId()));

        // 2. Find book
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + request.getBookId()));

        // 3. Check availability
        if (book.getAvailableQuantity() <= 0) {
            throw new BusinessException("Book '" + book.getTitle() + "' is currently unavailable.");
        }

        // 4. Create transaction
        BorrowTransaction transaction = new BorrowTransaction();
        transaction.setBook(book);
        transaction.setMember(member);
        transaction.setIssueDate(LocalDate.now());
        transaction.setDueDate(request.getDueDate());
        transaction.setStatus(BorrowStatus.ISSUED);

        // 5. Decrease available quantity
        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        bookRepository.save(book);

        BorrowTransaction saved = borrowTransactionRepository.save(transaction);
        return mapToResponse(saved);
    }

    /**
     * Return a book.
     *
     * Steps:
     * 1. Find the borrow transaction
     * 2. Check that it hasn't already been returned
     * 3. Set return date and status to RETURNED
     * 4. Increase availableQuantity by 1
     */
    @Transactional
    public BorrowResponse returnBook(Long transactionId) {
        // 1. Find transaction
        BorrowTransaction transaction = borrowTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrow transaction not found with id: " + transactionId));

        // 2. Check if already returned
        if (transaction.getStatus() == BorrowStatus.RETURNED) {
            throw new BusinessException("This book has already been returned.");
        }

        // 3. Update transaction
        transaction.setReturnDate(LocalDate.now());
        transaction.setStatus(BorrowStatus.RETURNED);

        // 4. Increase available quantity
        Book book = transaction.getBook();
        book.setAvailableQuantity(book.getAvailableQuantity() + 1);
        bookRepository.save(book);

        BorrowTransaction updated = borrowTransactionRepository.save(transaction);
        return mapToResponse(updated);
    }

    /**
     * Get all borrowing transactions.
     */
    public List<BorrowResponse> getAllBorrowings() {
        return borrowTransactionRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get only active (ISSUED) borrowings.
     */
    public List<BorrowResponse> getActiveBorrowings() {
        return borrowTransactionRepository.findByStatus(BorrowStatus.ISSUED)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get overdue borrowings — issued books whose due date has passed.
     */
    public List<BorrowResponse> getOverdueBorrowings() {
        return borrowTransactionRepository.findOverdueBorrowings(BorrowStatus.ISSUED, LocalDate.now())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get borrowing history for a specific member.
     */
    public List<BorrowResponse> getMemberBorrowingHistory(Long memberId) {
        // Verify member exists
        memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + memberId));

        return borrowTransactionRepository.findByMemberId(memberId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // Helper Method
    // ============================================================

    private BorrowResponse mapToResponse(BorrowTransaction transaction) {
        return new BorrowResponse(
                transaction.getId(),
                transaction.getBook().getId(),
                transaction.getBook().getTitle(),
                transaction.getMember().getId(),
                transaction.getMember().getName(),
                transaction.getIssueDate(),
                transaction.getDueDate(),
                transaction.getReturnDate(),
                transaction.getStatus().name()
        );
    }
}
