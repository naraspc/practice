package com.challnege.delivery.domain.member.dto;

import com.challnege.delivery.domain.member.entity.Member;
import com.challnege.delivery.domain.member.entity.Role;
import com.challnege.delivery.domain.order.entity.Order;
import com.challnege.delivery.domain.wallet.entity.Wallet;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MemberResponseDto {

    private long memberId;
    private String email;
    private String nickName;
    private String phoneNumber;
    private String address;
    private long point;

    private List<Order> orderList;

    @Builder
    public MemberResponseDto(long memberId, String email, String nickName, String phoneNumber, String address, Long point, List<Order> orderList) {
        this.memberId = memberId;
        this.email = email;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.point = point;
        this.orderList = orderList;
    }

    public static MemberResponseDto fromEntity(Member member) {
        return MemberResponseDto.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .nickName(member.getNickName())
                .phoneNumber(member.getPhoneNumber())
                .address(member.getAddress())
                .point(member.getWallet().getPoint())
                .orderList(member.getOrderList())
                .build();
    }
}
