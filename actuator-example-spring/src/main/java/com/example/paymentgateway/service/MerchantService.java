package com.example.paymentgateway.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MerchantService {

    private final Random random = new Random();
    private final Counter merchantOnboardCounter;

    public MerchantService(MeterRegistry registry) {
        this.merchantOnboardCounter = Counter.builder("payment.gateway.merchant.onboard")
                .description("Number of merchants onboarded")
                .register(registry);
    }

    public String onboardMerchant(String merchantName) {
        // Simulate some work
        try {
            Thread.sleep(random.nextInt(200));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        merchantOnboardCounter.increment();
        return "Merchant " + merchantName + " onboarded successfully";
    }

    public String checkBalance(String merchantId) {
        // Random balance
        return "Balance for merchant " + merchantId + ": $" + (random.nextDouble() * 10000);
    }
}
