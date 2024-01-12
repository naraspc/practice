package com.challnege.delivery.member.dto;

import com.challnege.delivery.member.entity.Member;
import com.challnege.delivery.member.entity.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class MemberResponseDto {

    private long memberId;
    private String email;
    private String nickName;
    private String phoneNumber;
    private String address;
    private Role role;

    @Builder
    public MemberResponseDto(long memberId, String email, String nickName, String phoneNumber, String address, Role role) {
        this.memberId = memberId;
        this.email = email;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
    }

    public static MemberResponseDto fromEntity(Member member) {
        return MemberResponseDto.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .nickName(member.getNickName())
                .phoneNumber(member.getPhoneNumber())
                .address(member.getAddress())
                .role(member.getRole())
                .build();
    }
}
