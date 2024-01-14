package com.challnege.delivery.domain.owner.controller;

import com.challnege.delivery.domain.owner.dto.OwnerRequestDto;
import com.challnege.delivery.domain.owner.dto.OwnerResponseDto;
import com.challnege.delivery.domain.owner.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping
    public ResponseEntity<OwnerResponseDto> createOwner(@RequestBody OwnerRequestDto requestDto){
        OwnerResponseDto ownerResponseDto = ownerService.createOwner(requestDto);
        return new ResponseEntity<>(ownerResponseDto, HttpStatus.CREATED);
    }
}
