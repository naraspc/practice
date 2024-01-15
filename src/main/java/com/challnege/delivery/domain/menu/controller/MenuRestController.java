package com.challnege.delivery.domain.menu.controller;

import com.challnege.delivery.domain.menu.dto.MenuResponseDto;
import com.challnege.delivery.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class MenuRestController {

    private final MenuService menuService;

    // 메뉴 전체 조회
    @GetMapping("/{restaurantsId}/menus")
    public ResponseEntity<List<MenuResponseDto>> readAllMenu(@PathVariable Long restaurantsId) {
        List<MenuResponseDto> menuResponseDtoList = menuService.readAllMenu(restaurantsId);
        return ResponseEntity.ok(menuResponseDtoList);
    }
}
