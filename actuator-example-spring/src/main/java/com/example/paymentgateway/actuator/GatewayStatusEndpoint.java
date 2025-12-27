package com.example.paymentgateway.actuator;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "gatewayStatus")
public class GatewayStatusEndpoint {

    private final RestTemplate restTemplate = new RestTemplate();
    private String getMockServiceUrl() {
        String mockHost = System.getenv("MOCK_SERVICE_HOST");
        if (mockHost == null) {
            mockHost = "localhost";
        }
        return "http://" + mockHost + ":8081/provider/status/";
    }

    @ReadOperation
    public Map<String, Object> getAllStatus() {
        Map<String, Object> statuses = new HashMap<>();
        statuses.put("DOKU", fetchStatus("DOKU"));
        statuses.put("VISA", fetchStatus("VISA"));
        statuses.put("MASTERCARD", fetchStatus("MASTERCARD"));
        return statuses;
    }

    @ReadOperation
    public Map<String, Object> getProviderStatus(@Selector String provider) {
        return fetchStatus(provider);
    }

    private Map<String, Object> fetchStatus(String provider) {
        try {
            return restTemplate.getForObject(getMockServiceUrl() + provider, Map.class);
        } catch (Exception e) {
            return Map.of("provider", provider, "status", "UNKNOWN", "error", e.getMessage());
        }
    }
}
