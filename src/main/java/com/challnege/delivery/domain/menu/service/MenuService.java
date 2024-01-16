package com.challnege.delivery.domain.menu.service;


import com.challnege.delivery.domain.menu.dto.MenuImageResponseDto;
import com.challnege.delivery.domain.menu.dto.MenuRequestDto;
import com.challnege.delivery.domain.menu.dto.MenuResponseDto;
import com.challnege.delivery.domain.menu.entity.Menu;
import com.challnege.delivery.domain.menu.repository.MenuRepository;
import com.challnege.delivery.domain.restaurant.entity.Restaurant;
import com.challnege.delivery.domain.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    
    // 메뉴 등록
    @Transactional
    public MenuResponseDto createMenu(Long restaurantsId, String imageUrl, MenuRequestDto menuRequestDto) {
        Restaurant restaurant = restaurantRepository.findById(restaurantsId).orElseThrow(
                () -> new NoSuchElementException("음식점을 찾을 수 없습니다."));

        Menu menu = new Menu(restaurant, imageUrl, menuRequestDto);
        Menu createMenu = menuRepository.save(menu);
        return new MenuResponseDto(createMenu);
    }

    // 메뉴목록 전체 조회
    @Transactional(readOnly = true)
    public List<MenuResponseDto> readAllMenu(Long restaurantsId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantsId).orElseThrow(
                () -> new NoSuchElementException("음식점을 찾을 수 없습니다."));
        return menuRepository.findAllByRestaurant(restaurant).stream().map(MenuResponseDto::new).toList();
    }


    // 메뉴 수정
    @Transactional
    public MenuResponseDto updateMenu(Long restaurantsId, Long menuId, MenuRequestDto menuRequestDto) {
        Restaurant restaurant = restaurantRepository.findById(restaurantsId).orElseThrow(
                () -> new NoSuchElementException("음식점을 찾을 수 없습니다."));
        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> new NoSuchElementException("메뉴를 찾을 수 없습니다."));

        menu.updateMenu(restaurant, menuRequestDto);

        return new MenuResponseDto(menu); // 빌더패턴? -> 등록일말고 수정일만 넘기게
    }

    // 메뉴 이미지 수정
    @Transactional
    public MenuImageResponseDto updateMenuImage(Long restaurantsId, Long menuId, String imageUrl) {
        Restaurant restaurant = restaurantRepository.findById(restaurantsId).orElseThrow(
                () -> new NoSuchElementException("음식점을 찾을 수 없습니다."));
        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> new NoSuchElementException("메뉴를 찾을 수 없습니다."));

        menu.updateMenuImage(restaurant, imageUrl);

        return new MenuImageResponseDto(menu);
    }

    // 메뉴 삭제
    @Transactional
    public void deleteMenu(Long restaurantsId, Long menuId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantsId).orElseThrow(
                () -> new NoSuchElementException("음식점을 찾을 수 없습니다."));
        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> new NoSuchElementException("메뉴를 찾을 수 없습니다."));
        restaurant.getMenu().remove(menu);
        menuRepository.deleteById(menuId);
    }
}
