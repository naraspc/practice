package com.challnege.delivery.domain.restaurant.entity;

import com.challnege.delivery.domain.menu.entity.Menu;
import com.challnege.delivery.domain.owner.entity.Owner;
import com.challnege.delivery.global.audit.Category;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Menu> menu;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Column()
    private int salesOfMonth;


    @Builder
    public Restaurant(long id, String restaurantName, String address, Category category, String resNumber, List<Menu> menu, Owner owner, int salesOfMonth) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.address = address;
        this.category = category;
        this.resNumber = resNumber;
        this.menu = menu;
        this.owner = owner;
        this.salesOfMonth = salesOfMonth;
    }


}
