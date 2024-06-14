package com.example.backend.implementation.customer;

public record CustomerResponse(
        int id,
        String firstname,
        String lastname,
        String email
) {
}
