package com.challnege.delivery.domain.wallet.entity;

import com.challnege.delivery.domain.member.entity.Member;
import com.challnege.delivery.global.audit.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Wallet extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletId;

    private Long point;

    private Long spending;

    @Builder
    public Wallet(Long point, Long spending) {
        this.point = point;
        this.spending = spending;
    }
}
