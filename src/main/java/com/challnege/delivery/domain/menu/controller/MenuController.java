package com.challnege.delivery.domain.menu.controller;


import com.challnege.delivery.domain.menu.dto.MenuRequestDto;
import com.challnege.delivery.domain.menu.dto.MenuResponseDto;
import com.challnege.delivery.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    // 메뉴 등록
    @PostMapping("/{restaurantsId}/menus")
    public ResponseEntity<MenuResponseDto> createMenu(
            @PathVariable Long restaurantsId,
//            @RequestPart MultipartFile image,
            @RequestPart MenuRequestDto menuRequestDto) {
        MenuResponseDto menuResponseDto = menuService.createMenu(restaurantsId, /*image,*/ menuRequestDto);
        return ResponseEntity.ok(menuResponseDto);
    }

    // 메뉴 전체 조회
    @GetMapping("/{restaurantsId}/menus")
    public ResponseEntity<List<MenuResponseDto>> readAllMenu(@PathVariable Long restaurantsId) {
        List<MenuResponseDto> menuResponseDtoList = menuService.readAllMenu(restaurantsId);
        return ResponseEntity.ok(menuResponseDtoList);
    }

    // 메뉴 수정
    @PutMapping("/{restaurantsId}/menus/{menuId}")
    public ResponseEntity<MenuResponseDto> updateMenu(
            @PathVariable Long restaurantsId,
            @PathVariable Long menuId,
//            @RequestPart MultipartFile image,
            @RequestPart MenuRequestDto menuRequestDto) {
        MenuResponseDto menuResponseDto = menuService.updateMenu(restaurantsId, menuId, /*image,*/ menuRequestDto);
        return ResponseEntity.ok(menuResponseDto);
    }

    // 메뉴 삭제 (Delete는 서비스에서 안정성 문제가 있어서 고려해봐야함)
    @DeleteMapping("/{restaurantsId}/menus/{menuId}")
    public ResponseEntity<String> deleteMenu(@PathVariable Long restaurantsId, @PathVariable Long menuId) {
        menuService.deleteMenu(restaurantsId, menuId);
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }
}
