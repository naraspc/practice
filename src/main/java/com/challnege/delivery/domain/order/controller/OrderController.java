package com.challnege.delivery.domain.order.controller;

import com.challnege.delivery.domain.order.dto.OrderResponseDto;
import com.challnege.delivery.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurants/{restaurantId}/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/{menuId}")
    private String addToOrder(@PathVariable("restaurantId") long restaurantId,
                              @PathVariable("menuId") long menuId,
                              @RequestParam long quantity,
                              @RequestParam long memberId) {
        OrderResponseDto orderResponseDto = orderService.addToOrder(restaurantId, menuId, quantity, memberId);
        return "redirect:/restaurants/{restaurantId}" + restaurantId;
    }

    @GetMapping("/{memberId}")//시큐리티 적용 후 path삭제 후 principal로 조회
    public String readCurrentOrder(@PathVariable("memberId") long memberId, Model model) {
        OrderResponseDto orderResponseDto = orderService.readCurrentOrder(memberId);
        model.addAttribute("orderResponseDto", orderResponseDto);
        return "orders";//responseEntity로 뿌림?
    }

    @PatchMapping("/{orderId}")
    public String makeOrder(@PathVariable("restaurantId") long restaurantId,
                            @PathVariable("orderId") long orderId,
                            @RequestParam long memberId, Model model) {
        OrderResponseDto orderResponseDto = orderService.makeOrder(orderId, memberId);
        return "orders";
    }
}