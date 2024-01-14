package com.challnege.delivery.domain.owner.service;

import com.challnege.delivery.domain.owner.dto.OwnerRequestDto;
import com.challnege.delivery.domain.owner.dto.OwnerResponseDto;
import com.challnege.delivery.domain.owner.entity.Owner;
import com.challnege.delivery.domain.owner.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerResponseDto createOwner(OwnerRequestDto requestDto) {
        Owner owner = requestDto.toEntity();
        ownerRepository.save(owner);
        return OwnerResponseDto.fromEntity(owner);
    }
}
