package com.example.backend.data.request;

import com.example.backend.data.persistence.MovieScreening;

import java.util.List;

public record TicketRequestBody(
        MovieScreening movieScreening,
        Integer userId,
        Boolean isEmployee,
        List<Integer> seatId

) {
}
