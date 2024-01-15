package com.challnege.delivery.domain.owner.controller;

import com.challnege.delivery.domain.owner.dto.OwnerRequestDto;
import com.challnege.delivery.domain.owner.dto.OwnerResponseDto;
import com.challnege.delivery.domain.owner.service.OwnerService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping
    public String createOwner(@ModelAttribute OwnerRequestDto requestDto, Model model) {
        OwnerResponseDto ownerResponseDto = ownerService.createOwner(requestDto);
        model.addAttribute("ownerResponseDto", ownerResponseDto);
        return "ownerCreated";  // 뷰의 이름, 필요에 따라 수정
    }

    @GetMapping("/{ownerId}")
    public String readOwner(@PathVariable long ownerId, Model model) {
        OwnerResponseDto ownerResponseDto = ownerService.readOwner(ownerId);
        model.addAttribute("ownerResponseDto", ownerResponseDto);
        return "ownerDetails";  // 뷰의 이름, 필요에 따라 수정
    }

    @DeleteMapping("/{ownerId}")
    public String deleteOwner(@PathVariable @Positive long ownerId) {
        ownerService.deleteOwner(ownerId);
        return "redirect:/owners";  // 삭제 후 리다이렉트할 경로, 필요에 따라 수정
    }
}
