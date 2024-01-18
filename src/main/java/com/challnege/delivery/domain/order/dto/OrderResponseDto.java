package com.challnege.delivery.domain.order.dto;

import com.challnege.delivery.domain.order.entity.Order;
import com.challnege.delivery.domain.order.entity.Status;
import com.challnege.delivery.domain.ordermenu.entity.OrderMenu;
import com.challnege.delivery.domain.restaurant.entity.Restaurant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderResponseDto {

    private List<OrderMenu> orderMenuList;
    private Long totalPrice;
    private Status status;
    private String restaurantName;
    private String restaurantNumber;
    private String foodName;


    @Builder
    public OrderResponseDto(List<OrderMenu> orderMenuList, Long totalPrice, Status status, String restaurantName, String restaurantNumber, String foodName) {
        this.orderMenuList = orderMenuList;
        this.totalPrice = totalPrice;
        this.status = status;
        this.restaurantName = restaurantName;
        this.restaurantNumber = restaurantNumber;
        this.foodName = foodName;
    }

    public static OrderResponseDto fromEntity(Order order, Restaurant restaurant) {
        return OrderResponseDto.builder()
                .orderMenuList(order.getOrderMenuList())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .restaurantName(restaurant.getRestaurantName())
                .restaurantNumber(restaurant.getResNumber())
                .build();
    }

    public static OrderResponseDto fromEntityByOwner(Order order) {
        return OrderResponseDto.builder()
                .orderMenuList(order.getOrderMenuList())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .restaurantName(order.getRestaurant().getRestaurantName())
                .restaurantNumber(order.getRestaurant().getResNumber())
//                .foodName(order.getOrderMenuList().get)
                .build();
    }

    public static List<OrderResponseDto> fromEntityList(List<Order> orderList) {
        return orderList.stream()
                .map(OrderResponseDto::fromEntityByOwner)
                .collect(Collectors.toList());
    }
}
