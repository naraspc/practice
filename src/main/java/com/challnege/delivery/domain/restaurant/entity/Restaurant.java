package com.challnege.delivery.domain.restaurant.entity;

import com.challnege.delivery.domain.member.entity.Member;
import com.challnege.delivery.domain.menu.entity.Menu;
import com.challnege.delivery.domain.restaurant.dto.RestaurantRequestDto;
import com.challnege.delivery.domain.review.entity.Review;
import com.challnege.delivery.global.audit.Category;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Restaurant implements Serializable {
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Menu> menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Review> reviews;


    @Builder
    public Restaurant(long id, String restaurantName, String address, Category category, String resNumber, List<Menu> menu, Member member, List<Review> reviews) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.address = address;
        this.category = category;
        this.resNumber = resNumber;
        this.menu = menu;
        this.member = member;
        this.reviews = reviews;
    }


    public void updateRestaurant(RestaurantRequestDto restaurantRequestDto) {
        this.restaurantName = restaurantRequestDto.getRestaurantName();
        this.address = restaurantRequestDto.getAddress();
        this.category = restaurantRequestDto.getCategory();
        this.resNumber = restaurantRequestDto.getResNumber();
    }


}
