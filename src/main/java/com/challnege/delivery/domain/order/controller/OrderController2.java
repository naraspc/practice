package com.challnege.delivery.domain.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController2 {
    @GetMapping("/orders.html")
    public String showOrderPage() {
        return "orders"; // orders.html 파일의 이름 (확장자 없음)
    }
}

