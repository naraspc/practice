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
        return "redirect:/restaurants/" + restaurantId;
    }

    @GetMapping
    public String readCurrentOrder(@RequestParam long memberId,
//            @PathVariable("memberId") long memberId,
                                   Model model) {
        OrderResponseDto orderResponseDto = orderService.readCurrentOrder(memberId);
        model.addAttribute("orderResponseDto", orderResponseDto);
        return "orders";
    }

    @PatchMapping("/{orderId}")
    public String makeOrder(@PathVariable("restaurantId") long restaurantId,
                            @PathVariable("orderId") long orderId,
                            @RequestParam long memberId,
                            Model model) {
        OrderResponseDto orderResponseDto = orderService.makeOrder(orderId, memberId);
        model.addAttribute("orderResponseDto", orderResponseDto);
        return "index";
    }

    @PatchMapping("/owner/{orderId}")
    public String completeOrder(@PathVariable("restaurantId") long restaurantId,
                            @PathVariable("orderId") long orderId,
                            @RequestParam long memberId,
                            Model model) {
        OrderResponseDto orderResponseDto = orderService.completeOrder(orderId, memberId);
        model.addAttribute("orderResponseDto", orderResponseDto);
        return "index";
    }
}