package com.challnege.delivery.domain.order.controller;

import com.challnege.delivery.domain.order.dto.OrderResponseDto;
import com.challnege.delivery.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurants/{restaurantId}/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/{menuId}")
    private String addToOrder(@PathVariable("restaurantId") long restaurantId,
                              @PathVariable("menuId") long menuId,
                              @RequestParam long quantity,
                              @AuthenticationPrincipal UserDetails auth) {
        OrderResponseDto orderResponseDto = orderService.addToOrder(restaurantId, menuId, quantity, auth);
        return "redirect:/restaurants/" + restaurantId;
    }

    @GetMapping
    public String readCurrentOrder(@AuthenticationPrincipal UserDetails auth,
                                   Model model) {
        OrderResponseDto orderResponseDto = orderService.readCurrentOrder(auth);
        model.addAttribute("orderResponseDto", orderResponseDto);
        return "orders";
    }

//    @GetMapping("/owner")
//    public ResponseEntity readOrdersByOwner(@PathVariable("restaurantId") long restaurantId,
//                                            @AuthenticationPrincipal UserDetails auth) {
//        List<OrderResponseDto> orderResponseDtoList = orderService.readOrdersList(restaurantId,auth);
//        return new ResponseEntity<>(orderResponseDtoList, HttpStatus.OK);
//    }
    @GetMapping("/owner")
    public String readOrdersByOwner(@PathVariable("restaurantId") long restaurantId,
                                    @AuthenticationPrincipal UserDetails auth,
                                    Model model) {
        List<OrderResponseDto> orderResponseDtoList = orderService.readOrdersListByOwner(restaurantId, auth);

        // Model에 주문 목록 추가
        model.addAttribute("orders", orderResponseDtoList);

        // Thymeleaf 템플릿의 경로 반환
        return "orderOfOwner";
    }

    @PatchMapping("/{orderId}")
    public String makeOrder(@PathVariable("restaurantId") long restaurantId,
                            @PathVariable("orderId") long orderId,
                            @AuthenticationPrincipal UserDetails auth,
                            Model model) {
        OrderResponseDto orderResponseDto = orderService.makeOrder(orderId, auth);
        model.addAttribute("orderResponseDto", orderResponseDto);
        return "index";
    }

    @PatchMapping("/owner/{orderId}")
    public String completeOrder(@PathVariable("restaurantId") long restaurantId,
                                @PathVariable("orderId") long orderId,
                                @AuthenticationPrincipal UserDetails auth,
                            Model model) {
        OrderResponseDto orderResponseDto = orderService.completeOrder(restaurantId,orderId, auth);
        model.addAttribute("orderResponseDto", orderResponseDto);
        return "index";
    }
}