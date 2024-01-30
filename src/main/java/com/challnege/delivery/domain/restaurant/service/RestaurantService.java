package com.challnege.delivery.domain.restaurant.service;

import com.challnege.delivery.domain.member.entity.Member;
import com.challnege.delivery.domain.member.service.MemberService;
import com.challnege.delivery.domain.restaurant.dto.RestaurantRequestDto;
import com.challnege.delivery.domain.restaurant.dto.RestaurantResponseDto;
import com.challnege.delivery.domain.restaurant.entity.Restaurant;
import com.challnege.delivery.domain.restaurant.repository.RestaurantRepository;
import com.challnege.delivery.global.audit.Category;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.core.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final StringRedisTemplate redisTemplate;
    private final RestaurantRepository restaurantRepository;
    private final MemberService memberService;
    private final ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);



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

    public Page<RestaurantResponseDto> pageFindRestaurantByAll(Pageable pageable) {
        String key = "restaurants";
        long start = pageable.getOffset();
        long end = start + pageable.getPageSize() - 1;

        logger.info("Fetching restaurants from Redis for page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
        Set<String> restaurantIds = redisTemplate.opsForZSet().range(key, start, end);

        if (restaurantIds == null || restaurantIds.isEmpty()) {
            logger.warn("No restaurant IDs found in Redis for the given range.");
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        List<RestaurantResponseDto> restaurants = restaurantIds.stream()
                .map(id -> {
                    String restaurantKey = "restaurant:" + id;
                    String json = redisTemplate.opsForValue().get(restaurantKey);
                    if (json == null) {
                        logger.warn("No data found in Redis for key: {}", restaurantKey);
                        return null;
                    }
                    try {
                        return deserializeJsonToRestaurantResponseDto(json);
                    } catch (Exception e) {
                        logger.error("Error deserializing JSON for key {}: {}", restaurantKey, e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        long total = Optional.ofNullable(redisTemplate.opsForZSet().size(key)).orElse(0L);
        return new PageImpl<>(restaurants, pageable, total);
    }

    public void syncRestaurantsToRedis() {
        String key = "restaurants";
        List<Restaurant> restaurants = restaurantRepository.findAll();
        logger.info("Syncing {} restaurants to Redis.", restaurants.size());

        for (Restaurant restaurant : restaurants) {
            try {
                RestaurantResponseDto dto = RestaurantResponseDto.convertToDto(restaurant);
                String json = objectMapper.writeValueAsString(dto);
                double score = restaurant.getId();
                String restaurantKey = "restaurant:" + restaurant.getId();

                redisTemplate.opsForZSet().add(key, restaurantKey, score);
                redisTemplate.opsForValue().set(restaurantKey, json);

                logger.info("Restaurant with id {} synced to Redis.", restaurant.getId());
            } catch (JsonProcessingException e) {
                logger.error("Error processing JSON for restaurant with id {}: {}", restaurant.getId(), e.getMessage());
            }
        }
    }

    private String serializeToJson(RestaurantResponseDto restaurant) {
        try {
            return objectMapper.writeValueAsString(restaurant);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing restaurant to JSON", e);
        }
    }

    private RestaurantResponseDto deserializeJsonToRestaurantResponseDto(String json) {
        try {
            return objectMapper.readValue(json, RestaurantResponseDto.class);
        } catch (IOException e) {
            throw new RuntimeException("Error deserializing JSON", e);
        }
    }


//    @CacheEvict(value = "restaurants", allEntries = true)
public RestaurantResponseDto updateRestaurant(Long id, RestaurantRequestDto restaurantRequestDto) {
    Restaurant restaurant = restaurantRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Not found restaurant"));
    restaurant.updateRestaurant(restaurantRequestDto);
    restaurantRepository.save(restaurant);

    // 업데이트된 레스토랑 정보를 JSON으로 변환하여 Redis에 저장
    updateRestaurantInRedis(restaurant);

    return RestaurantResponseDto.fromRestaurantEntity(restaurant);
}

    private void updateRestaurantInRedis(Restaurant restaurant) {
        try {
            RestaurantResponseDto dto = RestaurantResponseDto.convertToDto(restaurant);
            String json = objectMapper.writeValueAsString(dto);
            String restaurantKey = "restaurant:" + restaurant.getId();

            redisTemplate.opsForValue().set(restaurantKey, json);
            logger.info("Updated restaurant with id {} in Redis.", restaurant.getId());
        } catch (JsonProcessingException e) {
            logger.error("Error updating restaurant in Redis with id {}: {}", restaurant.getId(), e.getMessage());
        }
    }

    //Read by category and name
    @Transactional(readOnly = true)
    public Page<RestaurantResponseDto> searchRestaurantsPageable(String keyword, Pageable pageable) {
        List<Category> categories = findCategoriesByKeyword(keyword);

        if (!categories.isEmpty()) {
            return searchByCategoriesPageable(categories, pageable);
        } else {
            return searchByNamePageable(keyword, pageable);
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

    public Page<RestaurantResponseDto> searchByCategoriesPageable(List<Category> categories, Pageable pageable) {
        Page<Restaurant> findByCategories = restaurantRepository.findRestaurantsByCategoryIn(categories, pageable);
        return findByCategories.map(RestaurantResponseDto::fromRestaurantEntity);
    }

    public Page<RestaurantResponseDto> searchByNamePageable(String keyword, Pageable pageable) {
        Page<Restaurant> findByName = restaurantRepository.findRestaurantsByRestaurantNameContaining(keyword, pageable);
        return findByName.map(RestaurantResponseDto::fromRestaurantEntity);
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
        return optionalRestaurant.orElseThrow(() -> new NullPointerException("식당을 찾을 수 없습니다."));
    }
}
