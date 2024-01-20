package com.challnege.delivery.global.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AlbHealthcheck {

    @GetMapping("/healthcheck")
    public String healthcheck() {
        return "OK";
    }
}
