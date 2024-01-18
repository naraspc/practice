package com.challnege.delivery.domain.order.entity;

import com.challnege.delivery.domain.member.entity.Member;
import com.challnege.delivery.domain.ordermenu.entity.OrderMenu;
import com.challnege.delivery.domain.restaurant.entity.Restaurant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @NonNull
    private Long totalPrice;

    @Enumerated(EnumType.STRING)
    private Status status;


    @JsonManagedReference
    @OneToMany(mappedBy = "order")
    private List<OrderMenu> orderMenuList;


    @Builder
    public Order(Member member, Long totalPrice, Status status, List<OrderMenu> orderMenuList, Restaurant restaurant) {
        this.member = member;
        this.totalPrice = totalPrice;
        this.status = Status.BEFORE_ORDER;
        this.orderMenuList = orderMenuList;
        this.restaurant = restaurant;
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

    public void updateRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
