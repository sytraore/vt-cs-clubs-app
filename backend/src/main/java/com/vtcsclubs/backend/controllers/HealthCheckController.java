package com.vtcsclubs.backend.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/vtcsclubevents")
public class HealthCheckController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = Map.of("status", "UP");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/demo")
    public ResponseEntity<Map<String, String>> demo() {
        Map<String, String> response = Map.of("message", "Hello from a protected endpoint!");
        return ResponseEntity.ok(response);
    }
}
