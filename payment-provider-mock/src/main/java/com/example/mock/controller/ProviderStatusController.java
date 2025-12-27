package com.example.mock.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/provider")
public class ProviderStatusController {

    private final Random random = new Random();

    @GetMapping("/status/{provider}")
    public Map<String, String> getStatus(@PathVariable String provider) {
        // Simulate dynamic status
        int r = random.nextInt(10);
        String status = "UP";
        if (r > 8) {
            status = "DOWN";
        } else if (r > 6) {
            status = "MAINTENANCE";
        }

        return Map.of(
                "provider", provider,
                "status", status,
                "timestamp", String.valueOf(System.currentTimeMillis()));
    }
}
