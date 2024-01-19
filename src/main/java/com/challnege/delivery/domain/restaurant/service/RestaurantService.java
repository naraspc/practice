package com.challnege.delivery.domain.restaurant.service;

import com.challnege.delivery.domain.member.entity.Member;
import com.challnege.delivery.domain.member.service.MemberService;
import com.challnege.delivery.domain.restaurant.dto.RestaurantRequestDto;
import com.challnege.delivery.domain.restaurant.dto.RestaurantResponseDto;
import com.challnege.delivery.domain.restaurant.entity.Restaurant;
import com.challnege.delivery.domain.restaurant.repository.RestaurantRepository;
import com.challnege.delivery.global.audit.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MemberService memberService;

    //Create
    public RestaurantResponseDto createRestaurant(RestaurantRequestDto restaurantRequestDto, UserDetails auth) {
        Member member = memberService.findMemberByEmail(auth.getUsername());

        Restaurant restaurant = Restaurant.builder()
                .member(member)
                .restaurantName(restaurantRequestDto.getRestaurantName())
                .category(restaurantRequestDto.getCategory())
                .address(restaurantRequestDto.getAddress())
                .resNumber(restaurantRequestDto.getResNumber())
                .build();

        restaurantRepository.save(restaurant);

        return RestaurantResponseDto.fromRestaurantEntity(restaurant);
    }


    //Read find by one
    public RestaurantResponseDto findRestaurantById(Long id) {
        Restaurant restaurant = findRestaurantByResId(id);

        return RestaurantResponseDto.fromRestaurantEntity(restaurant);
    }


    //Read find all
    public List<RestaurantResponseDto> findRestaurantByAll() {
        List<Restaurant> restaurants = restaurantRepository.findAll();

        return RestaurantResponseDto.fromListRestaurantEntity(restaurants);

    }

    //Read by category and name
    @Transactional(readOnly = true)
    public List<Restaurant> searchRestaurants(String keyword) {
        List<Category> categories = findCategoriesByKeyword(keyword);

        if (!categories.isEmpty()) {
            return searchByCategories(categories);
        } else {
            return searchByName(keyword);
        }
    }

    public List<Category> findCategoriesByKeyword(String keyword) {
        List<Category> categories = new ArrayList<>();
        for (Category category : Category.values()) {
            if (category.name().contains(keyword)) {
                categories.add(category);
            }
        }
        return categories;
    }

    public List<Restaurant> searchByCategories(List<Category> categories) {
        return restaurantRepository.findRestaurantsByCategoryIn(categories);
    }

    public List<Restaurant> searchByName(String keyword) {
        return restaurantRepository.findRestaurantsByRestaurantNameContaining(keyword);
    }

    //Delete
    public Boolean deleteRestaurant(long id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);

        if (restaurant.isPresent()) {
            restaurantRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Transactional(readOnly = true)
    public Restaurant findRestaurantByResId(long restaurantId) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        Restaurant restaurant = optionalRestaurant.orElseThrow(() -> new NullPointerException("식당을 찾을 수 없습니다."));
        return restaurant;
    }
}
