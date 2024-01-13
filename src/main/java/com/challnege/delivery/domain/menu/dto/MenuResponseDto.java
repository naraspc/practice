package com.challnege.delivery.domain.menu.dto;

import com.challnege.delivery.domain.menu.entity.Menu;

import java.time.LocalDateTime;

public class MenuResponseDto {
    private Long id;
    private String foodName;
    private int price;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public MenuResponseDto(Menu createMenu) {
        this.id = createMenu.getId();
        this.foodName = createMenu.getFoodName();
        this.price = createMenu.getPrice();
        this.createdAt = createMenu.getCreatedAt();
        this.modifiedAt = createMenu.getModifiedAt();
    }
}
