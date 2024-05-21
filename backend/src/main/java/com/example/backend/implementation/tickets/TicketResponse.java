package com.example.backend.implementation.tickets;

import jakarta.persistence.Column;

import java.time.LocalDate;
import java.util.List;

public record TicketResponse(
        Integer ticketId,
        String movieName,
        Float price,
        LocalDate dateOfIssue,
        String movieHall,
        String cinemaBranch,
        String movieStartTime,
        List<String> seats
) {
}
