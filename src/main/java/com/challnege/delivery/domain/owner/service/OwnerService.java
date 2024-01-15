package com.challnege.delivery.domain.owner.service;

import com.challnege.delivery.domain.member.dto.MemberResponseDto;
import com.challnege.delivery.domain.member.entity.Member;
import com.challnege.delivery.domain.owner.dto.OwnerRequestDto;
import com.challnege.delivery.domain.owner.dto.OwnerResponseDto;
import com.challnege.delivery.domain.owner.entity.Owner;
import com.challnege.delivery.domain.owner.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    @Transactional(readOnly = true)
    public OwnerResponseDto readOwner(long ownerId) {
        Owner owner = findOwnerById(ownerId);
        return OwnerResponseDto.fromEntity(owner);
    }

    public void deleteOwner(long ownerId) {
        isOwnerExist(ownerId);
        ownerRepository.deleteById(ownerId);
    }

    public Owner findOwnerById(long ownerId) {
        Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
        Owner owner = optionalOwner.orElseThrow(() -> new NullPointerException("존재하지 않는 계정입니다."));
        return owner;
    }

    public void isOwnerExist(long ownerId) {
        boolean isExist = ownerRepository.existsById(ownerId);
        if (!isExist) {
            throw new NullPointerException("존재하지 않는 계정입니다.");
        }
    }

}

