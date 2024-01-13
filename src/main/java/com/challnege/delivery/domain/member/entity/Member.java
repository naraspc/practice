package com.challnege.delivery.domain.member.entity;

import com.challnege.delivery.domain.order.entity.Order;
import com.challnege.delivery.domain.wallet.entity.Wallet;
import com.challnege.delivery.global.audit.Auditable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String email;

    private String password;

    private String NickName;

    private String phoneNumber;

    private String address;

    @OneToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Builder
    public Member(String email, String password, String nickName, String phoneNumber, String address, Wallet wallet) {
        this.email = email;
        this.password = password;
        this.NickName = nickName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.wallet = wallet;
    }
}
