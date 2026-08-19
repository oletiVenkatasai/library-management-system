package com.library.controller;

import com.library.dto.MemberRequest;
import com.library.dto.MemberResponse;
import com.library.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Member Controller - Handles HTTP requests for member operations.
 */
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /** GET /api/members - Get all members */
    @GetMapping
    public ResponseEntity<List<MemberResponse>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    /** GET /api/members/{id} - Get member by ID */
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    /** POST /api/members - Add a new member */
    @PostMapping
    public ResponseEntity<MemberResponse> addMember(@Valid @RequestBody MemberRequest request) {
        MemberResponse created = memberService.addMember(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    /** PUT /api/members/{id} - Update a member */
    @PutMapping("/{id}")
    public ResponseEntity<MemberResponse> updateMember(
            @PathVariable Long id,
            @Valid @RequestBody MemberRequest request) {
        return ResponseEntity.ok(memberService.updateMember(id, request));
    }

    /** DELETE /api/members/{id} - Delete a member */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    /** GET /api/members/search?keyword=value - Search members */
    @GetMapping("/search")
    public ResponseEntity<List<MemberResponse>> searchMembers(@RequestParam String keyword) {
        return ResponseEntity.ok(memberService.searchMembers(keyword));
    }
}
