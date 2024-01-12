package com.challnege.delivery.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String email;

    private String password;

    private String NickName;

    private String phoneNumber;

    private String address;

    private Role role;

    @Builder
    public Member(String email, String password, String nickName, String phoneNumber, String address, Role role) {
        this.email = email;
        this.password = password;
        NickName = nickName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
    }
}
