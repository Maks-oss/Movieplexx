package com.example.backend.data.response;

public record CustomerResponse(
        int id,
        String firstname,
        String lastname,
        String email
) {
}
