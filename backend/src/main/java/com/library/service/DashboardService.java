package com.library.service;

import com.library.dto.BorrowResponse;
import com.library.dto.DashboardResponse;
import com.library.entity.BorrowStatus;
import com.library.entity.BorrowTransaction;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import com.library.repository.BorrowTransactionRepository;
import com.library.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final AuthorRepository authorRepository;
    private final BorrowTransactionRepository borrowTransactionRepository;

    public DashboardService(BookRepository bookRepository,
                            MemberRepository memberRepository,
                            AuthorRepository authorRepository,
                            BorrowTransactionRepository borrowTransactionRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.authorRepository = authorRepository;
        this.borrowTransactionRepository = borrowTransactionRepository;
    }

    public DashboardResponse getDashboardStats() {
        DashboardResponse response = new DashboardResponse();

        response.setTotalBooks(bookRepository.count());
        response.setTotalMembers(memberRepository.count());
        response.setTotalAuthors(authorRepository.count());
        response.setAvailableBooks(bookRepository.countAvailableBooks());
        response.setIssuedBooks(borrowTransactionRepository.countByStatus(BorrowStatus.ISSUED));

        List<BorrowTransaction> recentTransactions = borrowTransactionRepository.findRecentBorrowings();
        List<BorrowResponse> recentBorrowings = recentTransactions.stream()
                .limit(5)
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        response.setRecentBorrowings(recentBorrowings);

        return response;
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
