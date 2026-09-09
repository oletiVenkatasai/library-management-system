package com.library.controller;

import com.library.dto.BorrowRequest;
import com.library.dto.BorrowResponse;
import com.library.service.BorrowService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/borrowings")
public class BorrowController {

    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @GetMapping
    public ResponseEntity<List<BorrowResponse>> getAllBorrowings() {
        return ResponseEntity.ok(borrowService.getAllBorrowings());
    }

    @GetMapping("/active")
    public ResponseEntity<List<BorrowResponse>> getActiveBorrowings() {
        return ResponseEntity.ok(borrowService.getActiveBorrowings());
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<BorrowResponse>> getOverdueBorrowings() {
        return ResponseEntity.ok(borrowService.getOverdueBorrowings());
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<BorrowResponse>> getMemberBorrowingHistory(@PathVariable Long memberId) {
        return ResponseEntity.ok(borrowService.getMemberBorrowingHistory(memberId));
    }

    @PostMapping("/issue")
    public ResponseEntity<BorrowResponse> issueBook(@Valid @RequestBody BorrowRequest request) {
        BorrowResponse response = borrowService.issueBook(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/return/{id}")
    public ResponseEntity<BorrowResponse> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(borrowService.returnBook(id));
    }
}
