package com.challnege.delivery.domain.menu.service;


import com.challnege.delivery.domain.menu.dto.MenuRequestDto;
import com.challnege.delivery.domain.menu.dto.MenuResponseDto;
import com.challnege.delivery.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    public ResponseEntity<MenuResponseDto> createMenu(Long restaurantsId, MultipartFile image, MenuRequestDto menuRequestDto) {
        restaurantRepository.findById(restaurantsId)
        return null;
    }
}
