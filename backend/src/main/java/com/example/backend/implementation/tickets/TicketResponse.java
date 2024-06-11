package com.example.backend.implementation.tickets;

import java.time.LocalDate;
import java.util.List;

public record TicketResponse(
        String movieName,
        Float price,
        LocalDate dateOfIssue,
        String movieHall,
        String cinemaBranch,
        String movieStartTime,
        String seat
) {
}
