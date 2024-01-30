package com.challnege.delivery.domain.kafka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/send/{message}")
    public String sendMessage(@PathVariable String message){
        kafkaTemplate.send("my-topic" , message);
        return "Message sent to Kafka: " + message;
    }
}
