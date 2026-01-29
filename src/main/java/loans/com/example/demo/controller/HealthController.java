package loans.com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class HealthController {
    @GetMapping("/")
    public String home() {
        return "Loan Backend API is running";
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
