package com.challnege.delivery.domain.owner.controller;

import com.challnege.delivery.domain.owner.dto.OwnerRequestDto;
import com.challnege.delivery.domain.owner.dto.OwnerResponseDto;
import com.challnege.delivery.domain.owner.service.OwnerService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{ownerId}")
    public ResponseEntity<OwnerResponseDto> readOwner(@PathVariable("{OwnerId}") long ownerId) {
        OwnerResponseDto ownerResponseDto = ownerService.readOwner(ownerId);
        return new ResponseEntity<>(ownerResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{ownerId}")
    public ResponseEntity deleteOwner(@PathVariable @Positive long ownerId) {
        ownerService.deleteOwner(ownerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
