package com.library.dto;

import java.time.LocalDate;

/**
 * Member Response DTO.
 */
public class MemberResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private LocalDate membershipDate;

    // Default constructor
    public MemberResponse() {
    }

    public MemberResponse(Long id, String name, String email, String phone,
                          String address, LocalDate membershipDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.membershipDate = membershipDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getMembershipDate() {
        return membershipDate;
    }

    public void setMembershipDate(LocalDate membershipDate) {
        this.membershipDate = membershipDate;
    }
}
