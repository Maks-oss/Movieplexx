package com.example.backend.implementation.payment;

import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class PaymentService {
    private final Logger paymentLogger = Logger.getLogger(PaymentService.class.getSimpleName());
    public void processPayment(String paymentMethod) {
        // simulate payment
        paymentLogger.log(Level.INFO, "processing payment with " + paymentMethod);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException("Payment failure: " + e.getMessage());
        }
        paymentLogger.log(Level.INFO, "payment processed Successfully");

    }
}
