package com.challnege.delivery.domain.ordermenu.entity;

import com.challnege.delivery.domain.menu.entity.Menu;
import com.challnege.delivery.domain.order.entity.Order;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderMenuId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private Long quantity;

    private Long totalPrice;
    @Getter
    private String foodName;

    @Builder
    public OrderMenu(Order order, Long quantity, Long totalPrice, Menu menu, String foodName) {
        this.order = order;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.menu = menu;
        this.foodName = foodName;
    }
}