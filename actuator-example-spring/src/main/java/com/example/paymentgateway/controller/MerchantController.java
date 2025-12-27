package com.example.paymentgateway.controller;

import com.example.paymentgateway.service.MerchantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/merchants")
public class MerchantController {

    private final MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @PostMapping("/onboard")
    public ResponseEntity<String> onboard(@RequestParam String name) {
        return ResponseEntity.ok(merchantService.onboardMerchant(name));
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<String> getBalance(@PathVariable String id) {
        return ResponseEntity.ok(merchantService.checkBalance(id));
    }
}
