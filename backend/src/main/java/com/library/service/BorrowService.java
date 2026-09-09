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

    @Transactional
    public BorrowResponse issueBook(BorrowRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + request.getMemberId()));

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + request.getBookId()));

        if (book.getAvailableQuantity() <= 0) {
            throw new BusinessException("Book '" + book.getTitle() + "' is currently unavailable.");
        }

        BorrowTransaction transaction = new BorrowTransaction();
        transaction.setBook(book);
        transaction.setMember(member);
        transaction.setIssueDate(LocalDate.now());
        transaction.setDueDate(request.getDueDate());
        transaction.setStatus(BorrowStatus.ISSUED);

        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        bookRepository.save(book);

        BorrowTransaction saved = borrowTransactionRepository.save(transaction);
        return mapToResponse(saved);
    }

    @Transactional
    public BorrowResponse returnBook(Long transactionId) {
        BorrowTransaction transaction = borrowTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrow transaction not found with id: " + transactionId));

        if (transaction.getStatus() == BorrowStatus.RETURNED) {
            throw new BusinessException("This book has already been returned.");
        }

        transaction.setReturnDate(LocalDate.now());
        transaction.setStatus(BorrowStatus.RETURNED);

        Book book = transaction.getBook();
        book.setAvailableQuantity(book.getAvailableQuantity() + 1);
        bookRepository.save(book);

        BorrowTransaction updated = borrowTransactionRepository.save(transaction);
        return mapToResponse(updated);
    }

    public List<BorrowResponse> getAllBorrowings() {
        return borrowTransactionRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<BorrowResponse> getActiveBorrowings() {
        return borrowTransactionRepository.findByStatus(BorrowStatus.ISSUED)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<BorrowResponse> getOverdueBorrowings() {
        return borrowTransactionRepository.findOverdueBorrowings(BorrowStatus.ISSUED, LocalDate.now())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<BorrowResponse> getMemberBorrowingHistory(Long memberId) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + memberId));

        return borrowTransactionRepository.findByMemberId(memberId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

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
