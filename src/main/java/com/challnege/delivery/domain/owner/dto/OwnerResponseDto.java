package com.challnege.delivery.domain.owner.dto;

import com.challnege.delivery.domain.member.entity.Role;
import com.challnege.delivery.domain.owner.entity.Owner;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OwnerResponseDto {

    private long memberId;
    private String email;
    private String ownerName;
    private String phoneNumber;
    private String address;
    private Role role;

    @Builder
    public OwnerResponseDto(long memberId, String email, String ownerName, String phoneNumber, String address, Role role) {
        this.memberId = memberId;
        this.email = email;
        this.ownerName = ownerName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
    }

    public static OwnerResponseDto fromEntity(Owner owner) {
        return OwnerResponseDto.builder()
                .memberId(owner.getOwnerId())
                .email(owner.getEmail())
                .ownerName(owner.getOwnerName())
                .phoneNumber(owner.getPhoneNumber())
                .address(owner.getAddress())
                .role(owner.getRole())
                .build();
    }
}
