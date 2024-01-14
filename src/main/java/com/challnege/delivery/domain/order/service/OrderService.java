package com.challnege.delivery.domain.order.service;

import com.challnege.delivery.domain.member.entity.Member;
import com.challnege.delivery.domain.member.service.MemberService;
import com.challnege.delivery.domain.order.dto.OrderResponseDto;
import com.challnege.delivery.domain.order.entity.Order;
import com.challnege.delivery.domain.order.entity.Status;
import com.challnege.delivery.domain.order.repository.OrderRepository;
import com.challnege.delivery.domain.ordermenu.entity.OrderMenu;
import com.challnege.delivery.domain.ordermenu.repository.OrderMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberService memberService;
    private final OrderMenuRepository orderMenuRepository;
    @Transactional(readOnly = true)
    public OrderResponseDto readCurrentOrder(long memberId) {
        memberService.isMemberExist(memberId);
        Order order = findOrderByMemberId(memberId);
        return OrderResponseDto.fromEntity(order);
    }

    public Order findOrderByMemberId(long memberId) {
        Optional<Order> optionalOrder = orderRepository.findByMember_MemberId(memberId);
        Order order = optionalOrder.orElseThrow(() -> new NullPointerException("주문 사항이 없습니다"));
        return order;

    }

    public OrderResponseDto addToOrder(long restaurantId, long menuId, long quantity, long memberId) {
        Member member = memberService.findMemberById(memberId);
//        Menu menu = menuService.findMenuById(menuId);
        Order order = getOrderByMember(member);
//        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId)

        OrderMenu orderMenu = OrderMenu.builder()
//                .resName(restaurant.getResName)
                .order(order)
//                .menu(menu)
                .quantity(quantity)
//                .totalPrice(menu.getPrice * quantity)
                .build();
        orderMenuRepository.save(orderMenu);
//        order.updateTotalPrice(menu.getPrice * quantity);
        return OrderResponseDto.fromEntity(order);
    }

    private Order getOrderByMember(Member member) {
        Order order = orderRepository.findByMember_MemberId(member.getMemberId())
                .orElseGet(()-> {
                    Order newOrder = Order.builder()
                            .member(member)
                            .totalPrice(0L)
                            .status(Status.BEFORE_ORDER)
                            .build();
                    return orderRepository.save(newOrder);
                });
        return order;
    }
}