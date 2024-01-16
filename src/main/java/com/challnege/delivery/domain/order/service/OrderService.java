package com.challnege.delivery.domain.order.service;

import com.challnege.delivery.domain.member.entity.Member;
import com.challnege.delivery.domain.member.service.MemberService;
import com.challnege.delivery.domain.menu.entity.Menu;
import com.challnege.delivery.domain.menu.repository.MenuRepository;
import com.challnege.delivery.domain.order.dto.OrderResponseDto;
import com.challnege.delivery.domain.order.entity.Order;
import com.challnege.delivery.domain.order.entity.Status;
import com.challnege.delivery.domain.order.repository.OrderRepository;
import com.challnege.delivery.domain.ordermenu.entity.OrderMenu;
import com.challnege.delivery.domain.ordermenu.repository.OrderMenuRepository;
import com.challnege.delivery.domain.restaurant.entity.Restaurant;
import com.challnege.delivery.domain.restaurant.repository.RestaurantRepository;
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
    //-------------------------------------------------------------------------//
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private Menu findMenuByMenuId(long menuId) {
        Optional<Menu> optionalMenu = menuRepository.findById(menuId);
        Menu menu = optionalMenu.orElseThrow(() -> new NullPointerException("can not find menu"));
        return menu;
    }

    private Restaurant findRestaurantById(long restaurantId) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        Restaurant restaurant = optionalRestaurant.orElseThrow(() -> new NullPointerException("can not find restaurant"));
        return restaurant;
    }

    private void isExistingRes(long restaurantId) {
        boolean isExist = restaurantRepository.existsById(restaurantId);
        if (!isExist) {
            throw new NullPointerException("존재하지 않는 식당입니다.");
        }
    }
    //-------------------------------------------------------------------------//
    @Transactional(readOnly = true)
    public OrderResponseDto readCurrentOrder(long memberId) {
        memberService.isMemberExist(memberId);
        Order order = findBeforeOrderById(memberId);
        return OrderResponseDto.fromEntityByOwner(order);
    }




    public OrderResponseDto addToOrder(long restaurantId, long menuId, long quantity, long memberId) {
        Member member = memberService.findMemberById(memberId);
        Menu menu = findMenuByMenuId(menuId);
        Order order = getOrderByMember(member);
        Restaurant restaurant = findRestaurantById(restaurantId);

        OrderMenu orderMenu = OrderMenu.builder()
                .order(order)
                .menu(menu)
                .quantity(quantity)
                .totalPrice(menu.getPrice() * quantity)
                .build();

        orderMenuRepository.save(orderMenu);
        order.updateRestaurant(restaurant);
        order.updateTotalPrice(menu.getPrice() * quantity);
        return OrderResponseDto.fromEntity(order, restaurant);
    }


    public OrderResponseDto makeOrder(long orderId, long memberId) {
        Order order = findOrderByOrderId(orderId);
        memberService.isMemberExist(memberId);

        order.makeOnDelivery();

        return OrderResponseDto.fromEntityByOwner(order);
    }

    public OrderResponseDto completeOrder(long orderId, long memberId) {
        Order order = findOrderByOrderId(orderId);
        memberService.isMemberExist(memberId);

        order.makeOnComplete();

        return OrderResponseDto.fromEntityByOwner(order);

    }

    private Order getOrderByMember(Member member) {
        Order order = orderRepository.findBeforeOrderById(member.getMemberId())
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

    @Transactional(readOnly = true)
    public Order findBeforeOrderById(long memberId) {
        Optional<Order> optionalOrder = orderRepository.findBeforeOrderById(memberId);
        Order order = optionalOrder.orElseThrow(() -> new NullPointerException("can not find order"));
        return order;

    }

    @Transactional(readOnly = true)
    public Order findOrderByOrderId(long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Order order = optionalOrder.orElseThrow(() -> new NullPointerException("can not find order"));
        return order;
    }
}