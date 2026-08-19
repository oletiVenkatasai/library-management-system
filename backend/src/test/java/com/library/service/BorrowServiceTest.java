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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for BorrowService using Mockito.
 */
@ExtendWith(MockitoExtension.class)
class BorrowServiceTest {

    @Mock
    private BorrowTransactionRepository borrowTransactionRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private BorrowService borrowService;

    private Book book;
    private Member member;
    private BorrowTransaction transaction;
    private BorrowRequest borrowRequest;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(10L);
        book.setTitle("Clean Code");
        book.setQuantity(5);
        book.setAvailableQuantity(1);

        member = new Member();
        member.setId(20L);
        member.setName("Sai Kumar");
        member.setEmail("sai@example.com");

        transaction = new BorrowTransaction();
        transaction.setId(100L);
        transaction.setBook(book);
        transaction.setMember(member);
        transaction.setIssueDate(LocalDate.now());
        transaction.setDueDate(LocalDate.now().plusDays(14));
        transaction.setStatus(BorrowStatus.ISSUED);

        borrowRequest = new BorrowRequest();
        borrowRequest.setBookId(10L);
        borrowRequest.setMemberId(20L);
        borrowRequest.setDueDate(LocalDate.now().plusDays(14));
    }

    @Test
    void testIssueBook_Success() {
        when(memberRepository.findById(20L)).thenReturn(Optional.of(member));
        when(bookRepository.findById(10L)).thenReturn(Optional.of(book));
        when(borrowTransactionRepository.save(any(BorrowTransaction.class))).thenReturn(transaction);

        BorrowResponse response = borrowService.issueBook(borrowRequest);

        assertNotNull(response);
        assertEquals("ISSUED", response.getStatus());
        assertEquals(0, book.getAvailableQuantity()); // Decremented from 1 to 0
        verify(bookRepository, times(1)).save(book);
        verify(borrowTransactionRepository, times(1)).save(any(BorrowTransaction.class));
    }

    @Test
    void testIssueBook_Unavailable_ThrowsException() {
        book.setAvailableQuantity(0);

        when(memberRepository.findById(20L)).thenReturn(Optional.of(member));
        when(bookRepository.findById(10L)).thenReturn(Optional.of(book));

        assertThrows(BusinessException.class, () -> borrowService.issueBook(borrowRequest));
        verify(borrowTransactionRepository, never()).save(any(BorrowTransaction.class));
    }

    @Test
    void testReturnBook_Success() {
        when(borrowTransactionRepository.findById(100L)).thenReturn(Optional.of(transaction));
        when(borrowTransactionRepository.save(any(BorrowTransaction.class))).thenReturn(transaction);

        BorrowResponse response = borrowService.returnBook(100L);

        assertNotNull(response);
        assertEquals(BorrowStatus.RETURNED, transaction.getStatus());
        assertEquals(2, book.getAvailableQuantity()); // Incremented from 1 to 2
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testReturnBook_AlreadyReturned_ThrowsException() {
        transaction.setStatus(BorrowStatus.RETURNED);

        when(borrowTransactionRepository.findById(100L)).thenReturn(Optional.of(transaction));

        assertThrows(BusinessException.class, () -> borrowService.returnBook(100L));
    }
}
