package com.example.backend.implementation.tickets;

import com.example.backend.data.sql.Customer;
import com.example.backend.data.sql.Employee;
import com.example.backend.data.sql.MovieScreening;

import java.util.List;

public record CreateTicketRequestBody(
        MovieScreening movieScreening,
        Integer userId,
        Boolean isEmployee,
        List<Integer> seatId

) {
}
