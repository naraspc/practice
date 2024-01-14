package com.challnege.delivery.domain.restaurant.repository;

import com.challnege.delivery.domain.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}
