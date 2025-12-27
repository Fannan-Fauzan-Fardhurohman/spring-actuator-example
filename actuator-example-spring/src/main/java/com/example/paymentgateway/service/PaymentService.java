package com.example.paymentgateway.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    private final Random random = new Random();
    private final Counter paymentSuccessCounter;
    private final Counter paymentFailureCounter;
    private final Timer paymentTimer;

    public PaymentService(MeterRegistry registry) {
        // Define Custom Metrics
        this.paymentSuccessCounter = Counter.builder("payment.gateway.transaction.success")
                .description("Number of successful transactions")
                .register(registry);

        this.paymentFailureCounter = Counter.builder("payment.gateway.transaction.failure")
                .description("Number of failed transactions")
                .register(registry);

        this.paymentTimer = Timer.builder("payment.gateway.transaction.time")
                .description("Time taken to process payment")
                .register(registry);
    }

    public String processPayment(String orderId, Double amount) {
        return paymentTimer.record(() -> {
            try {
                // Simulate processing delay
                Thread.sleep(random.nextInt(500) + 100);

                // Simulate random failure (approx 20% failure rate)
                if (random.nextInt(10) < 2) {
                    paymentFailureCounter.increment();
                    throw new RuntimeException("Payment Failed for order: " + orderId);
                }

                paymentSuccessCounter.increment();
                return "Payment Successful for Order " + orderId;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Processing interrupted", e);
            }
        });
    }

    public String processRefund(String orderId) {
        // Simple logic for refund
        try {
            Thread.sleep(random.nextInt(300) + 50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Refund Successful for Order " + orderId;
    }
}
