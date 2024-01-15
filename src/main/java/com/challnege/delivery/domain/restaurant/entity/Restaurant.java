package com.challnege.delivery.domain.restaurant.entity;

import com.challnege.delivery.domain.menu.entity.Menu;
import com.challnege.delivery.global.audit.Category;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private List<Menu> Menu;

 /*   @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;*/

    @Column(name = "sales_of_month")
    private int salesOfMonth;


    @Builder
    public Restaurant(long id, String restaurantName, String address, Category category, String resNumber) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.address = address;
        this.category = category;
        this.resNumber = resNumber;
    }


}
