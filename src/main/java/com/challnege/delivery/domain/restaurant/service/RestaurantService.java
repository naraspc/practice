<<<<<<<<< Temporary merge branch 1
package com.challnege.delivery.restaurant.service;

=========
package com.challnege.delivery.domain.restaurant.service;

import com.challnege.delivery.domain.restaurant.dto.RestaurantRequestDto;
import com.challnege.delivery.domain.restaurant.dto.RestaurantResponseDto;
import com.challnege.delivery.domain.restaurant.entity.Restaurant;
import com.challnege.delivery.domain.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    //Create
    public RestaurantResponseDto createRestaurant(RestaurantRequestDto restaurantRequestDto) {
        Restaurant restaurant = Restaurant.builder()
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
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("cannot found restaurant"));

        return RestaurantResponseDto.fromRestaurantEntity(restaurant);
    }


    //Read find all
    public List<RestaurantResponseDto> findRestaurantByAll() {
        List<Restaurant> restaurants = restaurantRepository.findAll();

        return RestaurantResponseDto.fromListRestaurantEntity(restaurants);

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
}
