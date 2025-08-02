package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        log.info("GET /test/hello - Request received");
        String response = "Hello, World!";
        log.info("GET /test/hello - Response sent: {}", response);
        return response;
    }
}
