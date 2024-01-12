package com.challnege.delivery.domain.menu.controller;


import com.challnege.delivery.domain.menu.dto.MenuRequestDto;
import com.challnege.delivery.domain.menu.dto.MenuResponseDto;
import com.challnege.delivery.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;
    @PostMapping("/restaurants/{restaurantsId}/menus")
    public ResponseEntity<MenuResponseDto> createMenu(
            @PathVariable Long restaurantsId,
            @RequestPart MultipartFile image,
            @RequestPart MenuRequestDto menuRequestDto) {
        return menuService.createMenu(restaurantsId, image, menuRequestDto);
    }
}
