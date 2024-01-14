package com.challnege.delivery.domain.restaurant.entity;

import com.challnege.delivery.domain.menu.entity.Menu;
import com.challnege.delivery.global.audit.Category;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String restaurantName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Category category;

    @Column(nullable = false)
    private String resNumber;

//
//    @OneToMany(FetchType.LAZY)
//    @JoinColumn(name = "menu_id")
//    private Menu Menu;

    @Builder
    public Restaurant(long id, String restaurantName, String address, Category category, String resNumber) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.address = address;
        this.category = category;
        this.resNumber = resNumber;
    }


}