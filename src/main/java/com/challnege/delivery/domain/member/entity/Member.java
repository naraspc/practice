package com.challnege.delivery.domain.member.entity;

import com.challnege.delivery.domain.order.entity.Order;
import com.challnege.delivery.domain.wallet.entity.Wallet;
import com.challnege.delivery.global.audit.Auditable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(unique = true)
    private String email;

    private String password;

    private String nickName;

    private String phoneNumber;

    private String address;

    @OneToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @OneToMany(mappedBy = "member")
    private List<Order> orderList;

    @Builder
    public Member(String email, String password, String nickName, String phoneNumber, String address, Wallet wallet) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.wallet = wallet;
    }
}
