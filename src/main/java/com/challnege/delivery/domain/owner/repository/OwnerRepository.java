package com.challnege.delivery.domain.owner.repository;

import com.challnege.delivery.domain.owner.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
