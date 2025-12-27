package com.example.paymentgateway.controller;

import com.example.paymentgateway.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final PaymentService paymentService;

    public TransactionController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/pay")
    public ResponseEntity<String> pay(@RequestParam String orderId, @RequestParam Double amount) {
        try {
            String result = paymentService.processPayment(orderId, amount);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/refund")
    public ResponseEntity<String> refund(@RequestParam String orderId) {
        return ResponseEntity.ok(paymentService.processRefund(orderId));
    }
}
