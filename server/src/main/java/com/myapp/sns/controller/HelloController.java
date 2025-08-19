package com.myapp.sns.controller;

import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class HelloController {

     @Operation(summary = "Hello 메시지 조회", description = "간단한 인사말을 반환합니다.")
    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Boot!";
    }
}
