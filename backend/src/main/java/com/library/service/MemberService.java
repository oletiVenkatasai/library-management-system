package com.library.service;

import com.library.dto.MemberRequest;
import com.library.dto.MemberResponse;
import com.library.entity.Member;
import com.library.exception.BusinessException;
import com.library.exception.DuplicateResourceException;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.BorrowTransactionRepository;
import com.library.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BorrowTransactionRepository borrowTransactionRepository;

    public MemberService(MemberRepository memberRepository,
                         BorrowTransactionRepository borrowTransactionRepository) {
        this.memberRepository = memberRepository;
        this.borrowTransactionRepository = borrowTransactionRepository;
    }

    public List<MemberResponse> getAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public MemberResponse getMemberById(Long id) {
        Member member = findMemberOrThrow(id);
        return mapToResponse(member);
    }

    public MemberResponse addMember(MemberRequest request) {
        Optional<Member> existingMember = memberRepository.findByEmail(request.getEmail());
        if (existingMember.isPresent()) {
            throw new DuplicateResourceException("A member with email '" + request.getEmail() + "' already exists");
        }

        Member member = new Member();
        member.setName(request.getName());
        member.setEmail(request.getEmail());
        member.setPhone(request.getPhone());
        member.setAddress(request.getAddress());
        member.setMembershipDate(request.getMembershipDate());

        Member saved = memberRepository.save(member);
        return mapToResponse(saved);
    }

    public MemberResponse updateMember(Long id, MemberRequest request) {
        Member member = findMemberOrThrow(id);

        Optional<Member> existingMember = memberRepository.findByEmail(request.getEmail());
        if (existingMember.isPresent() && !existingMember.get().getId().equals(id)) {
            throw new DuplicateResourceException("A member with email '" + request.getEmail() + "' already exists");
        }

        member.setName(request.getName());
        member.setEmail(request.getEmail());
        member.setPhone(request.getPhone());
        member.setAddress(request.getAddress());
        member.setMembershipDate(request.getMembershipDate());

        Member updated = memberRepository.save(member);
        return mapToResponse(updated);
    }

    public void deleteMember(Long id) {
        Member member = findMemberOrThrow(id);

        if (!borrowTransactionRepository.findByMemberId(id).isEmpty()) {
            throw new BusinessException("Cannot delete member. Borrowing records are associated with this member.");
        }

        memberRepository.delete(member);
    }

    public List<MemberResponse> searchMembers(String keyword) {
        return memberRepository.searchMembers(keyword)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private Member findMemberOrThrow(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
    }

    private MemberResponse mapToResponse(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getPhone(),
                member.getAddress(),
                member.getMembershipDate()
        );
    }
}
