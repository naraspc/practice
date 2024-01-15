package com.challnege.delivery.domain.member.dto;

import com.challnege.delivery.domain.member.entity.Member;
import com.challnege.delivery.domain.member.entity.Role;
import com.challnege.delivery.domain.wallet.entity.Wallet;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponseDto {

    private long memberId;
    private String email;
    private String nickName;
    private String phoneNumber;
    private String address;
    private Role role;
    private long point;

    @Builder
    public MemberResponseDto(long memberId, String email, String nickName, String phoneNumber, String address, Role role, Long point) {
        this.memberId = memberId;
        this.email = email;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
        this.point = point;
    }

    public static MemberResponseDto fromEntity(Member member) {
        return MemberResponseDto.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .nickName(member.getNickName())
                .phoneNumber(member.getPhoneNumber())
                .address(member.getAddress())
                .role(member.getRole())
                .point(member.getWallet().getPoint())
                .build();
    }
}
