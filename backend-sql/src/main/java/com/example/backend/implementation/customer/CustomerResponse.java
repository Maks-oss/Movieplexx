package com.example.backend.implementation.customer;

public record CustomerResponse(
        int customerId,
        String firstName,
        String lastName,
        String email
) {
}
