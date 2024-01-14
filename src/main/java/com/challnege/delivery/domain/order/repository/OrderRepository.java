package com.challnege.delivery.domain.order.repository;

import com.challnege.delivery.domain.order.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByMember_MemberId(long memberId);
}