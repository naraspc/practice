package com.challnege.delivery.domain.restaurant.repository;

import com.challnege.delivery.domain.restaurant.entity.Restaurant;
import com.challnege.delivery.global.audit.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findAllRestaurantsByRestaurantNameContainingAndCategory(String name, Category category);
}
