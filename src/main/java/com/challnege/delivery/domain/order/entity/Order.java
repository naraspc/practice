package com.challnege.delivery.domain.order.entity;

import com.challnege.delivery.domain.member.entity.Member;
import com.challnege.delivery.domain.ordermenu.entity.OrderMenu;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private Long totalPrice;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String resName;

    @OneToMany(mappedBy = "order")
    private List<OrderMenu> orderMenuList = new ArrayList<>();

    @Builder
    public Order(Member member, Long totalPrice, Status status, List<OrderMenu> orderMenuList) {
        this.member = member;
        this.totalPrice = totalPrice;
        this.status = Status.BEFORE_ORDER;
        this.orderMenuList = orderMenuList;
    }

    public void updateTotalPrice(long price) {
        this.totalPrice += price;
    }

    public void makeOnDelivery() {
        this.status = Status.ON_DELIVERY;
    }

    public void makeOnComplete() {
        this.status = Status.COMPLETED;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }
}
