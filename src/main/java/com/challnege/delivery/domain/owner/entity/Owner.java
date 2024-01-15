package com.challnege.delivery.domain.owner.entity;

import com.challnege.delivery.domain.member.entity.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ownerId;

    private String email;

    private String password;

    private String ownerName;

    private String phoneNumber;

    private String address;



    @Builder
    public Owner(String email, String password, String ownerName, String phoneNumber, String address, Role role) {
        this.email = email;
        this.password = password;
        this.ownerName = ownerName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
