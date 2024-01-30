package com.challnege.delivery.domain.restaurant.repository;

import com.challnege.delivery.domain.restaurant.entity.Restaurant;
import com.challnege.delivery.global.audit.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Page<Restaurant> findRestaurantsByCategoryIn(List<Category> categories, Pageable pageable);

    Page<Restaurant> findRestaurantsByRestaurantNameContaining(String keyword, Pageable pageable);
    @Query("SELECT DISTINCT r FROM Restaurant r " +
            "LEFT JOIN FETCH r.menu m " +
            "LEFT JOIN FETCH r.reviews rv " +
            "LEFT JOIN FETCH r.member mem " +
            "WHERE r.id IN :ids")
    List<Restaurant> findAllWithDetailsByIds(@Param("ids") Set<Long> ids);
}